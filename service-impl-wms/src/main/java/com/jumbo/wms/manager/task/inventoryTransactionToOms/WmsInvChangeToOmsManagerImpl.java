package com.jumbo.wms.manager.task.inventoryTransactionToOms;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;

import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.warehouse.InventoryDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.StvLineDao;
import com.jumbo.dao.warehouse.WmsInvChangeToOmsDao;
import com.jumbo.dao.warehouse.WmsInvChangeToOmsLogDao;
import com.jumbo.pac.manager.extsys.wms.rmi.Rmi4Wms;
import com.jumbo.pac.manager.extsys.wms.rmi.model.BaseResult;
import com.jumbo.pac.manager.extsys.wms.rmi.model.InvOccupyLine;
import com.jumbo.pac.manager.extsys.wms.rmi.model.OperationBill;
import com.jumbo.pac.manager.extsys.wms.rmi.model.OperationBillLine;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.listener.InventoryCheckListenerManager;
import com.jumbo.wms.manager.listener.StvListenerManager;
import com.jumbo.wms.model.warehouse.InventoryCheck;
import com.jumbo.wms.model.warehouse.InventoryCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.StockTransVoucher;
import com.jumbo.wms.model.warehouse.StvLineCommand;
import com.jumbo.wms.model.warehouse.WmsInvChangeToOms;
import com.jumbo.wms.model.warehouse.WmsInvChangeToOmsLog;

/**
 * 
 * @author jinlong.ke
 * 
 */
public class WmsInvChangeToOmsManagerImpl extends BaseManagerImpl implements WmsInvChangeToOmsManager {

    /**
     * 
     */
    private static final long serialVersionUID = 3709994183618618393L;
    private static final Logger log = LoggerFactory.getLogger(WmsInvChangeToOmsManagerImpl.class);
    @Autowired
    private WmsInvChangeToOmsDao wmsInvChangeToOmsDao;
    @Autowired
    private InventoryDao invDao;
    @Autowired
    private Rmi4Wms rmi4Wms;
    @Autowired
    private WmsInvChangeToOmsLogDao wmsInvChangeToOmsLogDao;
    @Autowired
    private StvListenerManager stvListenerManager;
    @Resource
    private ApplicationContext applicationContext;
    @Autowired
    private InventoryCheckListenerManager inventoryCheckListenerManager;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private StvLineDao stvLineDao;
    @Autowired
    private OperationUnitDao operationUnitDao;

    @Override
    public List<WmsInvChangeToOms> getAllNeedData() {
        return wmsInvChangeToOmsDao.findAllNeedDataList(new BeanPropertyRowMapper<WmsInvChangeToOms>(WmsInvChangeToOms.class));
    }

    @Override
    public void noticeOmsThisInvChange(Long id) {
        if(log.isInfoEnabled()){
            log.info("noticeOmsThisInvChange begin id is:" + id);
        }
        WmsInvChangeToOms wio = wmsInvChangeToOmsDao.getByPrimaryKey(id);
        // 配货中或者取消，调用占用或者释放接口
        if (wio.getDataStatus().equals(StockTransApplicationStatus.OCCUPIED) || wio.getDataStatus().equals(StockTransApplicationStatus.CANCELED)) {
            boolean b = true;
            if (wio.getDataStatus().equals(StockTransApplicationStatus.CANCELED)) {
                b = false;
            }
            occupyChangeToPAC(id, b);
        } else {
            if (wio.getOrderType() == 1) {// 完成作业单流程
                if (wio.getStvCode() != null) {// 入库/部分入库流程
                    inboundChangeToPAC(id, wio.getStvId());
                } else {// 出库流程
                    outboundChangeToPAC(id, wio.getStaId());
                }
            } else {// 完成盘点/调整单流程
                invCheckChangeToPAC(id, wio.getStaId());
            }
        }
        if(log.isInfoEnabled()){
            log.info("noticeOmsThisInvChange end id is:" + id);
        }
    }

    private void outboundChangeToPAC(Long id, Long staId) {
        WmsInvChangeToOms wio = wmsInvChangeToOmsDao.getByPrimaryKey(id);
        try {
            staFinishToOms(staId);
            invChangeToPACSuccess(id);
        } catch (BusinessException e) {
            String s = "";
            if (e.getErrorCode() != ErrorCode.ERROR_NOT_SPECIFIED) {
                s += applicationContext.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs(), Locale.SIMPLIFIED_CHINESE);
            }
            log.error(s);
            log.error("", e);
            wio.setErrorMsg(s);
            wio.setErrorCount(wio.getErrorCount() + 1);
            wmsInvChangeToOmsDao.save(wio);
        } catch (Exception e) {
            log.error("Pacs inv Change Error:");
            log.error("", e);
            wio.setErrorMsg("调用接口出现系统异常");
            wio.setErrorCount(wio.getErrorCount() + 1);
            wmsInvChangeToOmsDao.save(wio);
        }

    }



    private void staFinishToOms(Long staId) {
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        Date maxTransactionTime = staDao.getMaxTransactionTime(staId, new SingleColumnRowMapper<Date>(Date.class));
        OperationBill operationBill = new OperationBill();
        operationBill.setMaxTransactionTime(maxTransactionTime);
        operationBill.setCode(sta.getCode());
        String whCode = operationUnitDao.selectWhCodeByStaId(staId, new SingleColumnRowMapper<String>(String.class));
        operationBill.setWhCode(whCode);
        operationBill.setType(222);// 222其他出入库类型
        if (!StockTransApplicationType.VMI_OWNER_TRANSFER.equals(sta.getType())) {
            operationBill.setDirection(OperationBill.ONLY_OUTBOUND);// 只有出库
            List<StvLineCommand> list = stvLineDao.getFinishedStvLineByStaId(staId, new BeanPropertyRowMapper<StvLineCommand>(StvLineCommand.class));
            List<OperationBillLine> list1 = new ArrayList<OperationBillLine>();
            for (StvLineCommand sc : list) {
                OperationBillLine line = new OperationBillLine();
                line.setSkuCode(sc.getSkuCode());
                line.setMarketability(sc.getMarketAbility() == 1 ? true : false);
                line.setInvBatchCode(sc.getBatchCode());
                line.setInvStatusCode(sc.getIntInvstatusName());
                line.setQty(sc.getQuantity());
                line.setShopCode(sc.getOwner());
                line.setWhCode(sc.getOuCode());
                list1.add(line);
            }
            operationBill.setOutboundLines(list1);
        } else {
            operationBill.setDirection(OperationBill.IN_AND_OUT);
            List<StvLineCommand> allList = stvLineDao.getFinishedStvLineByStaId(staId, new BeanPropertyRowMapper<StvLineCommand>(StvLineCommand.class));
            List<OperationBillLine> outList = new ArrayList<OperationBillLine>();
            List<OperationBillLine> inList = new ArrayList<OperationBillLine>();
            for (StvLineCommand sc : allList) {
                OperationBillLine line = new OperationBillLine();
                line.setSkuCode(sc.getSkuCode());
                // 新增是否可销售
                line.setMarketability(sc.getMarketAbility() == 1 ? true : false);
                line.setInvBatchCode(sc.getBatchCode());
                line.setInvStatusCode(sc.getIntInvstatusName());

                line.setQty(sc.getQuantity());
                line.setShopCode(sc.getOwner());
                line.setWhCode(sc.getOuCode());
                if (sc.getDirectionInt() == 2) {
                    outList.add(line);
                } else {
                    inList.add(line);
                }
            }
            operationBill.setOutboundLines(outList);
            operationBill.setInboundLines(inList);
        }
        try {
            BaseResult baseResult = rmi4Wms.wmsOperationsFeedback(operationBill);
            if (baseResult.getStatus() == 0) {
                throw new BusinessException(ErrorCode.OMS_SYSTEM_ERROR, new Object[] {baseResult.getMsg()});
            }
            log.info("Call oms outbound response interface------------------->END");
        } catch (BusinessException e) {
            log.error("", e);
            throw e;
        } catch (Exception e) {
            log.error("", e);
            throw new BusinessException(ErrorCode.RMI_OMS_FAILURE, new Object[] {""});
        }
    }

    /**
     * VMI库存调整通知PACS
     * 
     * @param id
     * @param staId
     */
    private void invCheckChangeToPAC(Long id, Long staId) {
        WmsInvChangeToOms wio = wmsInvChangeToOmsDao.getByPrimaryKey(id);
        InventoryCheck ic = new InventoryCheck();
        ic.setId(staId);
        try {
            inventoryCheckListenerManager.inventoryCheckFinished(ic);
            invChangeToPACSuccess(id);
        } catch (BusinessException e) {
            String s = "";
            if (e.getErrorCode() != ErrorCode.ERROR_NOT_SPECIFIED) {
                s += applicationContext.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs(), Locale.SIMPLIFIED_CHINESE);
            }
            log.error(s);
            log.error("", e);
            wio.setErrorMsg(s);
            wio.setErrorCount(wio.getErrorCount() + 1);
            wmsInvChangeToOmsDao.save(wio);
        } catch (Exception e) {
            log.error("Pacs inv Change Error:");
            log.error("", e);
            wio.setErrorMsg("调用接口出现系统异常");
            wio.setErrorCount(wio.getErrorCount() + 1);
            wmsInvChangeToOmsDao.save(wio);
        }
    }

    /**
     * （部分）入库完成通知PACS
     * 
     * @param id
     * @param stvId
     */
    private void inboundChangeToPAC(Long id, Long stvId) {
        WmsInvChangeToOms wio = wmsInvChangeToOmsDao.getByPrimaryKey(id);
        StockTransVoucher stv = new StockTransVoucher();
        stv.setId(stvId);
        try {
            stvListenerManager.inventoryTransactionToOms(stv, false);
            invChangeToPACSuccess(id);
        } catch (BusinessException e) {
            String s = "";
            if (e.getErrorCode() != ErrorCode.ERROR_NOT_SPECIFIED) {
                s += applicationContext.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs(), Locale.SIMPLIFIED_CHINESE);
            }
            log.error("inboundChangeToPAC" + s);
            wio.setErrorMsg(s);
            wio.setErrorCount(wio.getErrorCount() + 1);
            wmsInvChangeToOmsDao.save(wio);
        } catch (Exception e) {
            log.error("Pacs inv Change Error: ",e);
            wio.setErrorMsg("调用接口出现系统异常");
            wio.setErrorCount(wio.getErrorCount() + 1);
            wmsInvChangeToOmsDao.save(wio);
        }

    }

    /**
     * 通知成功记录日志删除中间表信息
     * 
     * @param id
     */
    private void invChangeToPACSuccess(Long id) {
        WmsInvChangeToOms wio = wmsInvChangeToOmsDao.getByPrimaryKey(id);
        WmsInvChangeToOmsLog wil = new WmsInvChangeToOmsLog();
        wil.setDataStatus(wio.getDataStatus());
        wil.setStaCode(wio.getStaCode());
        wil.setStaId(wio.getStaId());
        wil.setStvCode(wio.getStvCode());
        wil.setStvId(wio.getStvId());
        wil.setLogTime(new Date());
        wmsInvChangeToOmsLogDao.save(wil);
        wmsInvChangeToOmsDao.deleteByPrimaryKey(id);
    }

    /**
     * 占用或者取消占用通知pacs
     * 
     * @param id
     * @param flag
     */
    private void occupyChangeToPAC(Long id, boolean flag) {
        WmsInvChangeToOms wio = wmsInvChangeToOmsDao.getByPrimaryKey(id);
        List<InvOccupyLine> iol = new ArrayList<InvOccupyLine>();
        List<InventoryCommand> ilist = invDao.getOccupationDataByStaCode(wio.getStaId(), new BeanPropertyRowMapper<InventoryCommand>(InventoryCommand.class));
        for (InventoryCommand c : ilist) {
            InvOccupyLine il = new InvOccupyLine();
            il.setInnerShopCode(c.getOwner());
            il.setQuantity(c.getQuantity());
            il.setSkuCode(c.getSkuCode());
            iol.add(il);
        }
        try {
            BaseResult br = rmi4Wms.occupyInventory(wio.getStaCode(), iol, wio.getStaCode(), flag);
            if (br.getStatus() == BaseResult.BASE_RESULT_STATUS_SUCCESS) {
                invChangeToPACSuccess(id);
            } else {
                log.error("Pacs inv Change msg:" + br.getMsg());
                wio.setErrorMsg(br.getMsg());
                wio.setErrorCount(wio.getErrorCount() + 1);
                wmsInvChangeToOmsDao.save(wio);
            }
        } catch (Exception e) {
            log.error("Pacs inv Change Error:");
            log.error("", e);
            wio.setErrorMsg("调用接口出现系统异常");
            wio.setErrorCount(wio.getErrorCount() + 1);
            wmsInvChangeToOmsDao.save(wio);
        }
    }
}
