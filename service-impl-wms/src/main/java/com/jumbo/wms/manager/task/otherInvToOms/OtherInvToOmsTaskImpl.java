package com.jumbo.wms.manager.task.otherInvToOms;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.mail.MailTemplateDao;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.warehouse.InventoryCheckDao;
import com.jumbo.dao.warehouse.InventoryStatusDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.StockTransTxLogDao;
import com.jumbo.dao.warehouse.StockTransVoucherDao;
import com.jumbo.dao.warehouse.StvLineDao;
import com.jumbo.dao.warehouse.WmsOtherOutBoundInvNoticeOmsDao;
import com.jumbo.dao.warehouse.WmsOtherOutBoundInvNoticeOmsLogDao;
import com.jumbo.pac.manager.extsys.wms.rmi.Rmi4Wms;
import com.jumbo.pac.manager.extsys.wms.rmi.model.BaseResult;
import com.jumbo.pac.manager.extsys.wms.rmi.model.InvOccupyLine;
import com.jumbo.pac.manager.extsys.wms.rmi.model.OperationBill;
import com.jumbo.pac.manager.extsys.wms.rmi.model.OperationBillLine;
import com.jumbo.util.MailUtil;
import com.jumbo.util.StringUtil;
import com.jumbo.wms.daemon.OtherInvToOmsTask;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.command.SkuCommand;
import com.jumbo.wms.model.mail.MailTemplate;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.warehouse.InventoryCheck;
import com.jumbo.wms.model.warehouse.InventoryCheckCommand;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.StockTransTxLogCommand;
import com.jumbo.wms.model.warehouse.StockTransVoucher;
import com.jumbo.wms.model.warehouse.StvLine;
import com.jumbo.wms.model.warehouse.WmsOtherOutBoundInvNoticeOms;
import com.jumbo.wms.model.warehouse.WmsOtherOutBoundInvNoticeOmsLog;


public class OtherInvToOmsTaskImpl extends BaseManagerImpl implements OtherInvToOmsTask {
    private static final long serialVersionUID = 1557541729772643785L;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private StockTransVoucherDao stvDao;
    @Autowired
    private StvLineDao stvLineDao;
    @Autowired
    private WmsOtherOutBoundInvNoticeOmsDao wtoDao;
    @Autowired
    public Rmi4Wms rmi4Wms;
    @Autowired
    private WmsOtherOutBoundInvNoticeOmsLogDao wtolDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private OperationUnitDao operationUnitDao;
    @Autowired
    private InventoryStatusDao inventoryStatusDao;
    @Autowired
    private ChooseOptionDao chooseOptionDao;
    @Autowired
    private MailTemplateDao mailTemplateDao;
    @Autowired
    private InventoryCheckDao inventoryCheckDao;
    @Autowired
    private StockTransTxLogDao stockTransTxLogDao;

    @Override
    @Deprecated
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void otherInvToOms() {
        log.debug("OtherInvToOms begin-----------");
        // 1、其他出库作业单出库库存占用通知pac
        List<Long> staIdOccu = wtoDao.findAllOtherOutInvNoticeOms("occupation", new SingleColumnRowMapper<Long>(Long.class));
        for (Long staId : staIdOccu) {
            StockTransApplication sta = staDao.getByPrimaryKey(staId);
            StockTransVoucher stv = stvDao.findStvByStaId(sta.getId());
            List<StvLine> stvl = stvLineDao.findStvLineListByStvId(stv.getId());
            List<InvOccupyLine> iolList = new ArrayList<InvOccupyLine>();
            WmsOtherOutBoundInvNoticeOms wto = wtoDao.findOtherOutInvNoticeOmsByStaCode(sta.getCode());
            Long tempQty1 = 0l;
            for (StvLine stvLine : stvl) {
                Sku sku = skuDao.getByPrimaryKey(stvLine.getSku().getId());
                SkuCommand sc = skuDao.getSkuIsForsale(sku.getBarCode(), stvLine.getWarehouse().getId(), stvLine.getId(), new BeanPropertyRowMapper<SkuCommand>(SkuCommand.class));
                if (sc.getMarketAbility() == 0) {
                    continue;// 不可销售商品不传
                }
                InvOccupyLine iol = new InvOccupyLine();
                iol.setInnerShopCode(stvLine.getOwner());
                iol.setQuantity(stvLine.getQuantity());
                tempQty1 += stvLine.getQuantity();
                iol.setSkuCode(sku.getCustomerSkuCode());
                iolList.add(iol);
            }
            wto.setPushForsaleQty(tempQty1);
            if (iolList.size() == 0) {// 没有数据就不传
                wto.setStatus(20l);// 20表示单据商品所有都不可销售，作业单中间表记录作废
                wtoDao.save(wto);
                continue;
            }
            BaseResult baseResult = null;
            try {
                log.debug("Call oms outbound occupation response interface------------------->BEGIN");
                baseResult = rmi4Wms.occupyInventory(sta.getCode(), iolList, wto.getBatchCode(), true);
                if (baseResult.getStatus() == 0) {
                    /** --调用失败 --更新中间表错误次数、反馈信息-- **/
                    wto.setOccupationErrorCount(wto.getOccupationErrorCount() + 1);
                    wto.setOccupationReturnMsg(baseResult.getMsg());
                    if (wto.getOccupationErrorCount() >= 10) {
                        emailNotice("OCCUPY_ERROR_TEN_TO_PAC");
                    }
                } else if (baseResult.getStatus() == 1) {
                    wto.setOccupationIsSend(1l);
                }
                wtoDao.save(wto);
                log.debug("Call oms outbound occupation response interface------------------->END");
            } catch (Exception e) {
                /** --调用失败 --更新中间表错误次数、反馈信息-- **/
                wto.setOccupationErrorCount(wto.getOccupationErrorCount() + 1);
                wto.setOccupationReturnMsg(e.getMessage());
                if (wto.getOccupationErrorCount() >= 5) {
                    emailNotice("OCCUPY_ERROR_TEN_TO_PAC");
                }
                wtoDao.save(wto);
                log.error("Call oms outbound occupation response interface1------------------->ERROR");
            }
            wtoDao.flush();
        }
        // 2、其他出库作业单取消出库库存释放通知pac
        List<Long> staIdsCancel = wtoDao.findAllOtherOutInvNoticeOms("cancel", new SingleColumnRowMapper<Long>(Long.class));
        for (Long staIdCancel : staIdsCancel) {
            StockTransApplication sta = staDao.getByPrimaryKey(staIdCancel);
            StockTransVoucher stv = stvDao.findStvByStaId(sta.getId());
            List<StvLine> stvl = stvLineDao.findStvLineListByStvId(stv.getId());
            List<InvOccupyLine> iolList = new ArrayList<InvOccupyLine>();
            WmsOtherOutBoundInvNoticeOms wto = wtoDao.findOtherOutInvNoticeOmsByStaCode(sta.getCode());
            Long tempQty2 = 0l;
            for (StvLine stvLine : stvl) {
                Sku sku = skuDao.getByPrimaryKey(stvLine.getSku().getId());
                SkuCommand sc = skuDao.getSkuIsForsale(sku.getBarCode(), stvLine.getWarehouse().getId(), stvLine.getId(), new BeanPropertyRowMapper<SkuCommand>(SkuCommand.class));
                if (sc.getMarketAbility() == 0) {
                    continue;// 不可销售商品不传
                }
                InvOccupyLine iol = new InvOccupyLine();
                iol.setInnerShopCode(stvLine.getOwner());
                iol.setQuantity(stvLine.getQuantity());
                tempQty2 += stvLine.getQuantity();
                iol.setSkuCode(sku.getCustomerSkuCode());
                iolList.add(iol);
            }
            wto.setPushForsaleQty(tempQty2);
            if (iolList.size() == 0) {// 没有数据就不传
                wto.setStatus(20l);// 20表示单据商品所有都不可销售，作业单中间表记录作废
                wtoDao.save(wto);
                continue;
            }
            BaseResult baseResult = null;
            try {
                log.debug("Call oms outbound cancel response interface------------------->BEGIN");
                baseResult = rmi4Wms.occupyInventory(sta.getCode(), iolList, wto.getBatchCode(), false);
                if (baseResult.getStatus() == 0) {
                    /** --调用失败 --更新中间表错误次数、反馈信息-- **/
                    // 暂时没有其他出库取消失败的记录
                    wto.setCancelErrorCount(wto.getCancelErrorCount() + 1);
                    wto.setCancelReturnMsg(baseResult.getMsg());
                    if (wto.getOccupationErrorCount() >= 5) {
                        emailNotice("CANCEL_ERROR_TEN_TO_PAC");
                    }
                    wtoDao.save(wto);
                } else if (baseResult.getStatus() == 1) {
                    /** --其他出库取消调用成功 --删除中间表信息，记录成功日志-- **/
                    WmsOtherOutBoundInvNoticeOmsLog wtol = new WmsOtherOutBoundInvNoticeOmsLog();
                    wtol.setCreateTime(new Date());
                    wtol.setStaId(wto.getStaId());
                    wtol.setStaCode(wto.getStaCode());
                    wtol.setWhOuId(wto.getWhOuId());
                    wtol.setOwner(wto.getOwner());
                    wtol.setPushForsaleQty(wto.getPushForsaleQty());
                    // 添加一个取消成功的标志
                    wtol.setFinish_status(17);// 删除成功添加结束标志，17为取消成功标志
                    wtol.setFinishTime(new Date());
                    wtolDao.save(wtol);
                    wtoDao.delete(wto);
                }
                wtolDao.flush();
                log.debug("Call oms outbound cancel response interface------------------->END");
            } catch (Exception e) {
                wto.setCancelErrorCount(wto.getCancelErrorCount() + 1);
                wto.setCancelReturnMsg(e.getMessage());
                if (wto.getOccupationErrorCount() >= 5) {
                    emailNotice("CANCEL_ERROR_TEN_TO_PAC");
                }
                wtoDao.save(wto);
                log.error("Call oms outbound occupation response interface2------------------->ERROR");
            }
            wtoDao.flush();
        }
        // 3、其他出库完成通知pac释放库存
        List<Long> staIdsFinish = wtoDao.findAllOtherOutInvNoticeOms("finish", new SingleColumnRowMapper<Long>(Long.class));
        for (Long staIdFinish : staIdsFinish) {
            StockTransApplication sta = staDao.getByPrimaryKey(staIdFinish);
            StockTransVoucher stv = stvDao.findStvByStaId(sta.getId());
            Date maxTransactionTime = staDao.getMaxTransactionTime(staIdFinish, new SingleColumnRowMapper<Date>(Date.class));
            OperationBill operationBill = new OperationBill();
            operationBill.setMaxTransactionTime(maxTransactionTime);
            operationBill.setCode(sta.getCode());
            OperationUnit op = operationUnitDao.getByPrimaryKey(sta.getMainWarehouse().getId());
            operationBill.setWhCode(op.getCode());
            operationBill.setType(StockTransApplicationType.WMS_OTHER_IOBOUND.getValue());// 222其他出入库类型
            List<OperationBillLine> billLines = new ArrayList<OperationBillLine>();
            List<StvLine> list = stvLineDao.findStvLineListByStvId(stv.getId());
            operationBill.setDirection(OperationBill.ONLY_OUTBOUND);// 只有出库
            WmsOtherOutBoundInvNoticeOms wto = wtoDao.findOtherOutInvNoticeOmsByStaCode(sta.getCode());
            Long tempQty3 = 0l;
            for (int i = 0; i < list.size(); i++) {
                StvLine line = list.get(i);
                Sku sku = skuDao.getByPrimaryKey(line.getSku().getId());
                SkuCommand sc = skuDao.getSkuIsForsale(sku.getBarCode(), line.getWarehouse().getId(), line.getId(), new BeanPropertyRowMapper<SkuCommand>(SkuCommand.class));
                if (sc.getMarketAbility() == 0) {
                    continue;// 不可销售商品不传
                }
                OperationBillLine operationBillLine = new OperationBillLine();
                operationBillLine.setInvBatchCode(line.getBatchCode());
                InventoryStatus inventoryStatus = inventoryStatusDao.getByPrimaryKey(line.getInvStatus().getId());
                operationBillLine.setInvStatusCode(inventoryStatus.getName());
                operationBillLine.setQty(line.getQuantity());
                tempQty3 += line.getQuantity();
                operationBillLine.setSkuCode(sku.getCustomerSkuCode());// Sku编码还是客户商品编码
                OperationUnit wh = operationUnitDao.getByPrimaryKey(line.getWarehouse().getId());
                operationBillLine.setWhCode(wh.getCode());
                // 新增是否可销售
                operationBillLine.setMarketability(sc.getMarketAbility() == 1 ? true : false);
                // 店铺切换 接口调整-调整渠道编码
                operationBillLine.setShopCode(line.getOwner());
                billLines.add(operationBillLine);
            }
            operationBill.setOutboundLines(billLines);
            wto.setPushForsaleQty(tempQty3);
            if (billLines.size() == 0) {// 没有数据就不传
                wto.setStatus(20l);// 20表示单据商品所有都不可销售，作业单中间表记录作废
                wtoDao.save(wto);
                continue;
            }
            BaseResult baseResult = null;
            try {
                log.debug("Call oms outbound finish response interface------------------->BEGIN");
                baseResult = rmi4Wms.wmsOperationsFeedback(operationBill);
                if (baseResult.getStatus() == 0) {
                    /** --调用失败 --更新中间表错误次数、反馈信息-- **/
                    wto.setFinishErrorCount(wto.getFinishErrorCount() + 1);
                    wto.setFinishReturnMsg(baseResult.getMsg());
                    if (wto.getFinishErrorCount() >= 10) {
                        emailNotice("FINISH_ERROR_TEN_TO_PAC");
                    }
                    wtoDao.save(wto);
                } else if (baseResult.getStatus() == 1) {
                    /** --其他出库完成调用成功 --删除中间表信息，记录成功日志-- **/
                    // wto.setFinishIsSend(1l);//暂时保留
                    WmsOtherOutBoundInvNoticeOmsLog wtol = new WmsOtherOutBoundInvNoticeOmsLog();
                    wtol.setCreateTime(new Date());
                    wtol.setStaId(wto.getStaId());
                    wtol.setStaCode(wto.getStaCode());
                    wtol.setWhOuId(wto.getWhOuId());
                    wtol.setOwner(wto.getOwner());
                    wtol.setPushForsaleQty(wto.getPushForsaleQty());
                    wtol.setFinish_status(10);// 删除成功添加结束标志，10为完成成功标志
                    wtol.setFinishTime(new Date());
                    wtolDao.save(wtol);
                    wtoDao.delete(wto);
                }
                wtolDao.flush();
                log.debug("Call oms outbound finish response interface------------------->END");
            } catch (Exception e) {
                wto.setFinishErrorCount(wto.getFinishErrorCount() + 1);
                wto.setFinishReturnMsg(e.getMessage());
                if (wto.getFinishErrorCount() >= 10) {
                    emailNotice("FINISH_ERROR_TEN_TO_PAC");
                }
                wtoDao.save(wto);
                log.error("Call oms outbound occupation response interface3------------------->ERROR");
            }
            wtoDao.flush();


        }
        // 4、其他入库及预定义入库及VMI创入库单通知pac
        List<Long> inboundStaIds = wtoDao.findAllOtherOutInvNoticeOms("inbound", new SingleColumnRowMapper<Long>(Long.class));
        for (Long inboundStaId : inboundStaIds) {
            StockTransApplication sta = staDao.getByPrimaryKey(inboundStaId);
            StockTransVoucher stv = stvDao.findStvByStaId(sta.getId());
            Date maxTransactionTime = staDao.getMaxTransactionTime(inboundStaId, new SingleColumnRowMapper<Date>(Date.class));
            OperationBill operationBill = new OperationBill();
            operationBill.setMaxTransactionTime(maxTransactionTime);
            operationBill.setCode(sta.getCode());
            OperationUnit op = operationUnitDao.getByPrimaryKey(sta.getMainWarehouse().getId());
            operationBill.setWhCode(op.getCode());
            operationBill.setType(StockTransApplicationType.WMS_OTHER_IOBOUND.getValue());// 222其他出入库类型
            List<OperationBillLine> billLines = new ArrayList<OperationBillLine>();
            List<StvLine> list = stvLineDao.findStvLineListByStvId(stv.getId());
            operationBill.setDirection(OperationBill.ONLY_INBOUND);// 只有入库
            WmsOtherOutBoundInvNoticeOms wto = wtoDao.findOtherOutInvNoticeOmsByStaCode(sta.getCode());
            Long tempQty4 = 0l;
            for (int i = 0; i < list.size(); i++) {
                StvLine line = list.get(i);
                Sku sku = skuDao.getByPrimaryKey(line.getSku().getId());
                SkuCommand sc = skuDao.getSkuIsForsale(sku.getBarCode(), line.getWarehouse().getId(), line.getId(), new BeanPropertyRowMapper<SkuCommand>(SkuCommand.class));
                if (sc.getMarketAbility() == 0) {
                    continue;// 不可销售商品不传
                }
                OperationBillLine operationBillLine = new OperationBillLine();
                operationBillLine.setInvBatchCode(line.getBatchCode());
                InventoryStatus inventoryStatus = inventoryStatusDao.getByPrimaryKey(line.getInvStatus().getId());
                operationBillLine.setInvStatusCode(inventoryStatus.getName());
                operationBillLine.setQty(line.getQuantity());
                tempQty4 += line.getQuantity();
                operationBillLine.setSkuCode(sku.getCustomerSkuCode());// Sku编码还是客户商品编码
                OperationUnit wh = operationUnitDao.getByPrimaryKey(line.getWarehouse().getId());
                operationBillLine.setWhCode(wh.getCode());
                // 新增是否可销售
                operationBillLine.setMarketability(sc.getMarketAbility() == 1 ? true : false);
                // 店铺切换 接口调整-调整渠道编码
                operationBillLine.setShopCode(line.getOwner());
                billLines.add(operationBillLine);
            }
            operationBill.setInboundLines(billLines);
            wto.setPushForsaleQty(tempQty4);
            if (billLines.size() == 0) {// 没有数据就不传
                wto.setStatus(20l);// 20表示单据商品所有都不可销售，作业单中间表记录作废
                wtoDao.save(wto);
                continue;
            }
            BaseResult baseResult = null;
            try {
                log.debug("Call oms inbound finish response interface------------------->BEGIN");
                baseResult = rmi4Wms.wmsOperationsFeedback(operationBill);
                if (baseResult.getStatus() == 0) {
                    /** --调用失败 --更新中间表错误次数、反馈信息-- **/
                    wto.setFinishErrorCount(wto.getFinishErrorCount() + 1);
                    wto.setFinishReturnMsg(baseResult.getMsg());
                    if (wto.getFinishErrorCount() >= 5) {
                        emailNotice("FINISH_ERROR_TEN_TO_PAC");
                    }
                    wtoDao.save(wto);
                } else if (baseResult.getStatus() == 1) {
                    /** --其他出库完成调用成功 --删除中间表信息，记录成功日志-- **/
                    // wto.setFinishIsSend(1l);//暂时保留
                    WmsOtherOutBoundInvNoticeOmsLog wtol = new WmsOtherOutBoundInvNoticeOmsLog();
                    wtol.setCreateTime(new Date());
                    wtol.setStaId(wto.getStaId());
                    wtol.setStaCode(wto.getStaCode());
                    wtol.setWhOuId(wto.getWhOuId());
                    wtol.setOwner(wto.getOwner());
                    wtol.setPushForsaleQty(wto.getPushForsaleQty());
                    wtol.setFinish_status(10);// 删除成功添加结束标志，10为完成成功标志
                    wtol.setFinishTime(new Date());
                    wtolDao.save(wtol);
                    wtoDao.delete(wto);
                }
                wtolDao.flush();
                log.debug("Call oms inbound finish response interface------------------->END");
            } catch (Exception e) {
                wto.setFinishErrorCount(wto.getFinishErrorCount() + 1);
                wto.setFinishReturnMsg(e.getMessage());
                if (wto.getFinishErrorCount() >= 5) {
                    emailNotice("FINISH_ERROR_TEN_TO_PAC");
                }
                wtoDao.save(wto);
                log.error("Call oms outbound occupation response interface4------------------->ERROR");
            }
            wtoDao.flush();
        }

        // 5、VMI库存调试
        List<Long> vmiIc = wtoDao.findAllVmiAdjustNoticeOms("vmiadjustment", new SingleColumnRowMapper<Long>(Long.class));
        // 取本次反馈日志最大的执行时间
        for (Long iclong : vmiIc) {
            List<StockTransTxLogCommand> list = stockTransTxLogDao.getDetailByInventoryCheckId(iclong, true, new BeanPropertyRowMapper<StockTransTxLogCommand>(StockTransTxLogCommand.class));
            if (list.size() > 0) {
                InventoryCheck ic1 = inventoryCheckDao.getByPrimaryKey(iclong);
                inventoryCheckDao.flush();
                InventoryCheckCommand inv = inventoryCheckDao.findIMaxTransactionTime(ic1.getId(), new BeanPropertyRowMapper<InventoryCheckCommand>(InventoryCheckCommand.class));
                OperationBill ob = new OperationBill();
                ob.setCode(ic1.getCode());
                ob.setMaxTransactionTime(inv.getManagerTime());
                ob.setDirection(OperationBill.IN_AND_OUT);
                ob.setSlipCode(ic1.getSlipCode());
                ob.setType(StockTransApplicationType.WMS_OTHER_IOBOUND.getValue());// 其他出入库通知PAC
                ob.setMemo(ic1.getRemork());
                OperationUnit op = operationUnitDao.getByPrimaryKey(ic1.getOu().getId());
                ob.setWhCode(op.getCode());
                List<OperationBillLine> outLine = new ArrayList<OperationBillLine>();
                List<OperationBillLine> inLine = new ArrayList<OperationBillLine>();
                WmsOtherOutBoundInvNoticeOms wto = wtoDao.findOtherOutInvNoticeOmsByStaCode(ic1.getCode());
                Long tempQty5 = 0l;
                for (StockTransTxLogCommand sl : list) {
                    OperationBillLine bl = new OperationBillLine();
                    if (sl.getMarketAbility() == 0) {
                        continue;// 不可销售商品不传
                    }
                    bl.setSkuCode(sl.getSkuCode());
                    // 新增是否可销售
                    bl.setMarketability(sl.getMarketAbility() == 1 ? true : false);
                    bl.setInvBatchCode(sl.getBatchCode());
                    bl.setInvStatusCode(sl.getInvStatus());
                    bl.setQty(sl.getQuantity());
                    tempQty5 += sl.getQuantity();
                    bl.setShopCode(sl.getOwner());
                    bl.setWhCode(sl.getWhouCode());
                    if (sl.getIntDirection() == 1) {
                        inLine.add(bl);
                    } else {
                        outLine.add(bl);
                    }
                }
                ob.setInboundLines(inLine);
                ob.setOutboundLines(outLine);
                wto.setPushForsaleQty(tempQty5);
                BaseResult br = null;
                try {
                    log.debug("Call pac vmiinventory confirm interface------------------->BEGIN");
                    br = rmi4Wms.wmsOperationsFeedback(ob);
                    if (br.getStatus() == 0) {
                        wto.setFinishErrorCount(wto.getFinishErrorCount() + 1);
                        wto.setFinishReturnMsg(br.getMsg());
                        if (wto.getFinishErrorCount() >= 5) {
                            emailNotice("FINISH_ERROR_TEN_TO_PAC");
                        }
                        wtoDao.save(wto);
                    } else {
                        /** --其他出库完成调用成功 --删除中间表信息，记录成功日志-- **/
                        WmsOtherOutBoundInvNoticeOmsLog wtol = new WmsOtherOutBoundInvNoticeOmsLog();
                        wtol.setCreateTime(new Date());
                        wtol.setStaId(wto.getStaId());
                        wtol.setStaCode(wto.getStaCode());
                        wtol.setWhOuId(wto.getWhOuId());
                        wtol.setOwner(wto.getOwner());
                        wtol.setPushForsaleQty(wto.getPushForsaleQty());
                        wtol.setFinish_status(10);// 删除成功添加结束标志，10为完成成功标志
                        wtol.setFinishTime(new Date());
                        wtolDao.save(wtol);
                        wtoDao.delete(wto);
                    }
                    wtolDao.flush();
                    log.debug("Call pac vmiinventory confirm interface------------------->END");
                } catch (Exception e) {
                    wto.setFinishErrorCount(wto.getFinishErrorCount() + 1);
                    wto.setFinishReturnMsg(e.getMessage());
                    if (wto.getFinishErrorCount() >= 5) {
                        emailNotice("FINISH_ERROR_TEN_TO_PAC");
                    }
                    wtoDao.save(wto);
                    log.error("Call oms outbound occupation response interface5------------------->ERROR");
                }
                wtoDao.flush();
            }
        }

        // 6、转店
        /*
         * List<Long> transStaIds = wtoDao.findAllOtherOutInvNoticeOms("vmiownertransfer", new
         * SingleColumnRowMapper<Long>(Long.class)); for (Long tStaIds : transStaIds) {
         * StockTransApplication sta = staDao.getByPrimaryKey(tStaIds); List<StockTransVoucher>
         * stvList = stvDao.findStvFinishListByStaId(sta.getId()); if (stvList.size() > 0) { Date
         * maxTransactionTime = staDao.getMaxTransactionTime(tStaIds, new
         * SingleColumnRowMapper<Date>(Date.class)); OperationBill operationBill = new
         * OperationBill(); operationBill.setMaxTransactionTime(maxTransactionTime);
         * operationBill.setCode(sta.getCode()); OperationUnit op =
         * operationUnitDao.getByPrimaryKey(sta.getMainWarehouse().getId());
         * operationBill.setWhCode(op.getCode());
         * operationBill.setType(StockTransApplicationType.WMS_OTHER_IOBOUND.getValue());//
         * 222其他出入库类型 operationBill.setDirection(OperationBill.IN_AND_OUT);
         * WmsOtherOutBoundInvNoticeOms wto =
         * wtoDao.findOtherOutInvNoticeOmsByStaCode(sta.getCode()); List<OperationBillLine> outLine
         * = new ArrayList<OperationBillLine>(); List<OperationBillLine> inLine = new
         * ArrayList<OperationBillLine>(); Long tempQty6 = 0l;// 转店销售可用量转出和转入总变化量为0 for
         * (StockTransVoucher stv : stvList) { List<StvLineCommand> list =
         * stvLineDao.findInfoByStvId(stv.getId(), new
         * BeanPropertyRowMapper<StvLineCommand>(StvLineCommand.class)); for (StvLineCommand line :
         * list) { OperationBillLine bl = new OperationBillLine();
         * bl.setInvBatchCode(line.getBatchCode()); bl.setInvStatusCode(line.getIntInvstatusName());
         * bl.setQty(line.getQuantity()); bl.setSkuCode(line.getCustomerSkuCode());// Sku编码还是客户商品编码
         * bl.setWhCode(operationBill.getWhCode()); // 新增是否可销售 (484行的逻辑判断 不可销售直接continue,所以默认都是
         * true) bl.setMarketability(true); // 店铺切换 接口调整-调整渠道编码 bl.setShopCode(line.getOwner()); if
         * (line.getDirectionInt() == 1) { inLine.add(bl); } else { outLine.add(bl); } }
         * operationBill.setInboundLines(inLine); operationBill.setOutboundLines(outLine); }
         * wto.setPushForsaleQty(tempQty6); // 这个值默认0？ BaseResult br = null; try {
         * log.error("Call pac vmiownertransfer confirm interface------------------->BEGIN"); br =
         * rmi4Wms.wmsOperationsFeedback(operationBill); if (br.getStatus() == 0) {
         * wto.setFinishErrorCount(wto.getFinishErrorCount() + 1);
         * wto.setFinishReturnMsg(br.getMsg()); if (wto.getFinishErrorCount() >= 10) {
         * emailNotice("FINISH_ERROR_TEN_TO_PAC"); } wtoDao.save(wto); } else { //其他出库完成调用成功
         * --删除中间表信息，记录成功日志 WmsOtherOutBoundInvNoticeOmsLog wtol = new
         * WmsOtherOutBoundInvNoticeOmsLog(); wtol.setCreateTime(new Date());
         * wtol.setStaId(wto.getStaId()); wtol.setStaCode(wto.getStaCode());
         * wtol.setWhOuId(wto.getWhOuId()); wtol.setOwner(wto.getOwner());
         * wtol.setPushForsaleQty(wto.getPushForsaleQty()); wtol.setFinish_status(10);//
         * 删除成功添加结束标志，10为完成成功标志 wtol.setFinishTime(new Date()); wtolDao.save(wtol);
         * wtoDao.delete(wto); } wtolDao.flush();
         * log.error("Call pac vmiownertransfer confirm interface------------------->END"); } catch
         * (Exception e) {
         * log.error("Call pac vmiownertransfer confirm interface6------------------->ERROR--" +
         * sta.getCode()); wto.setFinishErrorCount(wto.getFinishErrorCount() + 1);
         * wto.setFinishReturnMsg(e.getMessage()); if (wto.getFinishErrorCount() >= 3) {
         * emailNotice("FINISH_ERROR_TEN_TO_PAC"); } wtoDao.save(wto); } wtoDao.flush(); } }
         */


        // 7、删除多余数据
        wtoDao.deleteAllNoUsedData();
        log.debug("OtherInvToOms end-----------");
    }

    /**
     * 邮件通知方法
     */
    public void emailNotice(String code) {
        // 查询常量表 邮件推送信息
        ChooseOption option = chooseOptionDao.findByCategoryCode("ORDER_INV_NOTICE_PAC");
        // 查询邮件模板
        MailTemplate template = mailTemplateDao.findTemplateByCode(code);
        // 查询系统常量表 收件人
        if (option != null && !StringUtil.isEmpty(option.getOptionValue())) {
            if (template != null) {
                String mailBody = template.getMailBody();// 邮件内容
                String subject = template.getSubject();// 标题
                String addressee = option.getOptionValue(); // 查询收件人
                boolean bool = false;
                bool = MailUtil.sendMail(subject, addressee, "", mailBody, true, null);
                if (bool) {
                    log.debug("邮件通知成功！");
                } else {
                    log.debug("邮件通知失败,请联系系统管理员！");
                }
            } else {
                log.debug("邮件模板不存在或被禁用");
            }
        } else {
            log.debug("邮件通知失败,收件人为空！");
        }
    }

}
