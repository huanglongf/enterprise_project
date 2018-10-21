package com.jumbo.wms.manager.task.inv;



import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.dao.vmi.warehouse.MsgInboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderCancelDao;
import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnAdjustmentDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnInboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnOutboundDao;
import com.jumbo.dao.vmi.warehouse.RtnSnDetailDao;
import com.jumbo.dao.warehouse.InventoryCheckDao;
import com.jumbo.dao.warehouse.SkuSnDao;
import com.jumbo.dao.warehouse.SkuWarehouseRefDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.WXConfirmOrderQueueDao;
import com.jumbo.dao.warehouse.WarehouseMsgSkuDao;
import com.jumbo.wms.Constants;
import com.jumbo.wms.daemon.ThreePLTask;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.manager.system.ChooseOptionManager;
import com.jumbo.wms.manager.task.ThreePLManager;
import com.jumbo.wms.manager.warehouse.SkuSnManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerProxy;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.vmi.warehouse.MsgInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCancel;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnAdjustment;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutbound;
import com.jumbo.wms.model.warehouse.InventoryCheck;
import com.jumbo.wms.model.warehouse.SkuSnCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.wms.model.warehouse.WarehouseMsgSku;
import com.jumbo.wms.model.warehouse.WxConfirmOrderQueue;

import loxia.dao.support.BeanPropertyRowMapperExt;

public class ThreePLTaskImpl implements ThreePLTask {

    private static final long serialVersionUID = -2891572668142881052L;
    protected static final Logger log = LoggerFactory.getLogger(ThreePLTaskImpl.class);

    @Autowired
    private ThreePLManager threePLManager;
    @Autowired
    private WarehouseMsgSkuDao warehouseMsgSkuDao;
    @Autowired
    private MsgInboundOrderDao msgInboundOrderDao;
    @Autowired
    private MsgOutboundOrderDao msgOutboundOrderDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private MsgOutboundOrderCancelDao msgOutboundOrderCancelDao;
    @Autowired
    private MsgRtnInboundOrderDao msgRtnInboundOrderDao;
    @Autowired
    private WareHouseManagerProxy wareHouseManagerProxy;
    @Autowired
    private MsgRtnOutboundDao msgRtnOutboundDao;
    @Autowired
    private SkuWarehouseRefDao skuWarehouseRefDao;
    @Autowired
    private WXConfirmOrderQueueDao wXConfirmOrderQueueDao;
    @Autowired
    private MsgRtnAdjustmentDao msgRtnAdjustmentDao;
    @Autowired
    private InventoryCheckDao inventoryCheckDao;
    @Autowired
    private WareHouseManager warehouseManager;
    @Autowired
    private ChooseOptionManager chooseOptionManager;
    @Autowired
    private RtnSnDetailDao rtnSnDetailDao;
    @Autowired
    private SkuSnDao skuSnDao;
    @Autowired
    private SkuSnManager skuSnManager;

    /**
     * 外包仓入库反馈执行
     */
    public void threePLInboundExecute() {
        log.debug(".......execute threePLInbound start........");
        // 查找常量表外包仓集合
        // 按照时间升序查找状态为新建和失败的外包仓数据
        List<String> sourceList = new ArrayList<String>();
        List<ChooseOption> chooselist = chooseOptionManager.findOptionListByCategoryCode(Constants.THREE_PL_USER_CONFIG);
        for (ChooseOption chooseOption : chooselist) {
            sourceList.add(chooseOption.getOptionKey());
        }
        sourceList.add("WLB");
        sourceList.add("OMSTmall");
        sourceList.add("CJ");
        List<MsgRtnInboundOrder> orderList = msgRtnInboundOrderDao.findRtnInboundByStatus(sourceList, new BeanPropertyRowMapperExt<MsgRtnInboundOrder>(MsgRtnInboundOrder.class));
        if (orderList != null && orderList.size() > 0) {
            log.debug(".......threePL Inbound execute begin........");
            // 调用入库反馈执行方法
            for (MsgRtnInboundOrder msgRtnInboundOrder : orderList) {
                try {
                    wareHouseManagerProxy.msgInBoundWareHouse(msgRtnInboundOrder);
                } catch (Exception e) {
                    if (e instanceof BusinessException) {
                        log.error("threePLInboundExecute error ,error code is : {}", ((BusinessException) e).getErrorCode());
                    } else {
                        log.error("", e);
                    }
                    MsgRtnInboundOrder rtnIn = msgRtnInboundOrderDao.getByPrimaryKey(msgRtnInboundOrder.getId());
                    if (rtnIn.getErrorCount() == null) {
                        rtnIn.setErrorCount(1l);
                    } else {
                        rtnIn.setErrorCount(rtnIn.getErrorCount() + 1);
                    }
                    rtnIn.setStatus(DefaultStatus.CANCELED);
                    rtnIn.setRemark(e.getMessage());
                    msgRtnInboundOrderDao.save(rtnIn);
                }

            }
            log.debug(".......threePL Inbound execute end........");
        }
        log.debug(".......execute threePLInbound end........");
    }

    /**
     * 外包仓出库反馈执行
     */
    public void threePLOutboundExecute() {
        log.debug(".......execute threePLOutbound start........");
        // 查找常量表外包仓集合
        // 按照时间升序查找状态为新建和失败的外包仓数据
        List<String> sourceList = new ArrayList<String>();
        List<ChooseOption> chooselist = chooseOptionManager.findOptionListByCategoryCode(Constants.THREE_PL_USER_CONFIG);
        for (ChooseOption chooseOption : chooselist) {
            sourceList.add(chooseOption.getOptionKey());
        }
        sourceList.add("WLB");
        sourceList.add("OMSTmall");
        sourceList.add("CJ");
        List<MsgRtnOutbound> orderList = msgRtnOutboundDao.findRtnOutboundByStatus(sourceList, new BeanPropertyRowMapperExt<MsgRtnOutbound>(MsgRtnOutbound.class));
        if (orderList != null && orderList.size() > 0) {
            log.debug(".......threePL Outbound execute begin........");
            for (MsgRtnOutbound msgRtnOutbound : orderList) {
                try {
                    wareHouseManagerProxy.callVmiSalesStaOutBound(msgRtnOutbound.getId());
                } catch (BusinessException e) {
                    log.error("threePLOutboundExecute error ! OUT STA :" + e.getErrorCode());
                } catch (Exception e) {
                    log.debug(".......threePL Outbound execute failed........");
                    log.error("" + e);
                    MsgRtnOutbound rtnIn = msgRtnOutboundDao.getByPrimaryKey(msgRtnOutbound.getId());
                    if (rtnIn.getErrorCount() == null) {
                        rtnIn.setErrorCount(1l);
                    } else {
                        rtnIn.setErrorCount(rtnIn.getErrorCount() + 1);
                    }
                    rtnIn.setStatus(DefaultStatus.CANCELED);
                    rtnIn.setRemark(e.getMessage());
                    msgRtnOutboundDao.save(rtnIn);
                }
            }
            log.debug(".......threePL Outbound execute end........");
        }
        log.debug(".......execute threePLOutbound end........");
    }

    /**
     * 物流宝商品同步
     */
    @SingleTaskLock(timeout = Constants.TASK_LOCK_TIMEOUT, value = Constants.TASK_LOCK_VALUE)
    public void sendSkuToHab() {
        log.debug(".......syncSkuToHab start........");
        // 按照时间升序查找状态为新建的外包仓数据
        List<WarehouseMsgSku> skuList = warehouseMsgSkuDao.getMsgSkuByStatus("WLB", new BeanPropertyRowMapperExt<WarehouseMsgSku>(WarehouseMsgSku.class));
        for (WarehouseMsgSku warehouseMsgSku : skuList) {
            try {
                threePLManager.syncWmsSkuToHab(warehouseMsgSku);
            } catch (Exception e) {
                log.debug(".......Sync sku to Hab error........");
            }
        }
        log.debug(".......syncSkuToHab end........");
    }

    /**
     * 物流宝入库同步
     */
    @SingleTaskLock(timeout = Constants.TASK_LOCK_TIMEOUT, value = Constants.TASK_LOCK_VALUE)
    public void sendInboundHab() {
        log.debug(".......sendInboundHab start........");
        // 按照时间升序查找状态为新建和失败的外包仓数据
        List<MsgInboundOrder> orderList = msgInboundOrderDao.getInboundOrderByStatus("WLB", new BeanPropertyRowMapperExt<MsgInboundOrder>(MsgInboundOrder.class));
        // List<MsgInboundOrder> orderList = new ArrayList<MsgInboundOrder>();
        // MsgInboundOrder msgInd1 = msgInboundOrderDao.getByPrimaryKey(199056l);
        // orderList.add(msgInd1);
        for (MsgInboundOrder msgInboundOrder : orderList) {
            try {
                threePLManager.sendWlbInboundToHab(msgInboundOrder);
            } catch (Exception e) {
                log.debug(".......Send Inbound to ThreePL error........");
            }
        }
        log.debug(".......sendInboundHab end........");
    }

    /**
     * 物流宝新入库查询， 替代之前的状态和差异查询
     */
    @SingleTaskLock(timeout = Constants.TASK_LOCK_TIMEOUT, value = Constants.TASK_LOCK_VALUE)
    public void wlbNewInboundQuery() {
        log.debug(".......wlbNewInboundQuery start........");
        List<StockTransApplicationCommand> wlbOrderList = staDao.findwlbInOrderCodeByStatus(new BeanPropertyRowMapper<StockTransApplicationCommand>(StockTransApplicationCommand.class));
        for (StockTransApplicationCommand stockTransApplicationCommand : wlbOrderList) {
            try {
                threePLManager.queryWlbNewInbound(stockTransApplicationCommand);
            } catch (Exception e) {
                log.debug(".......query new wlb Inbound error........");
            }
        }
    }


    /**
     * 物流宝出库同步
     */
    @SingleTaskLock(timeout = Constants.TASK_LOCK_TIMEOUT, value = Constants.TASK_LOCK_VALUE)
    public void sendOutboundHab() {
        log.debug(".......sendOutboundHab start........");
        // 按照时间升序查找状态为新建和失败的外包仓数据
        List<MsgOutboundOrder> orderList = msgOutboundOrderDao.getOutboundOrderByStatus("WLB", new BeanPropertyRowMapperExt<MsgOutboundOrder>(MsgOutboundOrder.class));
        // List<MsgOutboundOrder> orderList = new ArrayList<MsgOutboundOrder>();
        // MsgOutboundOrder msgInd = msgOutboundOrderDao.getByPrimaryKey(1862916l);
        // orderList.add(msgInd);
        for (MsgOutboundOrder msgOutboundOrder : orderList) {
            try {
                threePLManager.sendWlbOutboundToHab(msgOutboundOrder);
            } catch (Exception e) {
                log.debug(".......Send Outbound to ThreePL error........");
            }
        }
        log.debug(".......sendOutboundHab end........");
    }


    /**
     * 物流宝交易单出库查询 销售出 - 全部出 库间移动、移库入库 - 支持部分出
     */
    @SingleTaskLock(timeout = Constants.TASK_LOCK_TIMEOUT, value = Constants.TASK_LOCK_VALUE)
    public void wlbOutboundQuery() {
        log.debug(".......wlbOutboundQuery start........");
        List<StockTransApplicationCommand> wlbOrderList = staDao.findwlbOutOrderCodeByStatus(new BeanPropertyRowMapper<StockTransApplicationCommand>(StockTransApplicationCommand.class));
        for (StockTransApplicationCommand stockTransApplicationCommand : wlbOrderList) {
            try {
                threePLManager.queryWlbOutbound(stockTransApplicationCommand);
            } catch (Exception e) {
                log.debug(".......wlbOutboundQuery error........");
            }
        }
        log.debug(".......wlbOutboundQuery end........");
    }


    /**
     * 物流宝入库差异查询 type = 1
     */
    // public void wlbInboundNotifyPageGet() {
    // log.debug(".......wlbInboundNoticeQuery start........");
    // Date beginDate = staDao.findInboundQueryDate(1, new SingleColumnRowMapper<Date>(Date.class));
    // // 查询最后一次执行时间
    // long currentTime = System.currentTimeMillis() - 60 * 60 * 1000; // 当前时间减去1小时
    // Date endDate = new Date(currentTime);
    // SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    // Date time = (Date) Timestamp.valueOf(sf.format(endDate));
    // threePLManager.queryInboundNotice(beginDate, time);
    // log.debug(".......wlbInboundNoticeQuery end........");
    // }

    /**
     * 物流宝单据取消
     */
    @SingleTaskLock(timeout = Constants.TASK_LOCK_TIMEOUT, value = Constants.TASK_LOCK_VALUE)
    public void wlbOutboundCancel() {
        log.debug(".......wlbOutboundCancel start........");
        // 按照时间升序查找状态为新建的外包仓取消数据
        List<MsgOutboundOrderCancel> skuList = msgOutboundOrderCancelDao.getMsgOutboundByStatus("WLB", new BeanPropertyRowMapperExt<MsgOutboundOrderCancel>(MsgOutboundOrderCancel.class));
        // List<MsgOutboundOrderCancel> skuList = new ArrayList<MsgOutboundOrderCancel>();
        // MsgOutboundOrderCancel msgInd = msgOutboundOrderCancelDao.getByPrimaryKey(96386l);
        // skuList.add(msgInd);
        if (skuList != null && skuList.size() > 0) {
            log.debug(".......Sync outBound Cancel to Hab begin........");
            threePLManager.wlbOutboundCancelToHab(skuList);
            log.debug(".......Sync outBound Cancel to Hab end........");
        }
        log.debug(".......wlbOutboundCancel end........");
    }

    /**
     * 物流宝单据取消查询 时间段来查询 type = 2
     */
    @SingleTaskLock(timeout = Constants.TASK_LOCK_TIMEOUT, value = Constants.TASK_LOCK_VALUE)
    public void wlbOutboundCancelQuery() {
        log.debug(".......wlbOutboundCancelQuery start........");
        Date beginDate = staDao.findInboundQueryDate(2, new SingleColumnRowMapper<Date>(Date.class)); // 查询最后一次执行时间
        long currentTime = System.currentTimeMillis() - 30 * 30 * 1000; // 当前时间减去50分钟
        Date endDate = new Date(currentTime);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time = (Date) Timestamp.valueOf(sf.format(endDate));
        List<Long> biList = skuWarehouseRefDao.findSkuRefBinnael("WLB", new SingleColumnRowMapper<Long>(Long.class));
        for (Long channel : biList) {
            threePLManager.queryOrderCancel(beginDate, time, channel);
        }

        log.debug(".......wlbOutboundCancelQuery end........");
    }

    /**
     * 外包仓接口失败邮件通知
     */
    @SingleTaskLock(timeout = Constants.TASK_LOCK_TIMEOUT, value = Constants.TASK_LOCK_VALUE)
    public void threePLEmailNotice() {
        log.debug(".......threePL emailNotice start........");
        // 外包仓商品创建失败邮件通知
        try {
            List<WarehouseMsgSku> msgList = warehouseMsgSkuDao.getMsgSkuByErrorCount(5l);
            if (msgList != null && msgList.size() > 0) {
                log.debug(".......msgSku Failed Email Notice begin........");
                threePLManager.msgThreePLEmailNotice("THREEPL_SKU_NOTICE", "MSG_SKU_NOTICE", msgList, null, null, null, null, null, "");
                log.debug(".......msgSku Failed Email Notice end........");
            }
        } catch (Exception e) {
            log.debug(".......threePL emailNotice Failed........");
            log.error("", e);
        }

        // 外包仓入库失败邮件通知
        try {
            List<MsgInboundOrder> msgInboundList = msgInboundOrderDao.getMsgInboundByErrorCount(5l);
            if (msgInboundList != null && msgInboundList.size() > 0) {
                log.debug(".......msgInbound Failed Email Notice begin........");
                threePLManager.msgThreePLEmailNotice("THREEPL_INBOUND_NOTICE", "MSG_INBOUND_NOTICE", null, msgInboundList, null, null, null, null, "");
                log.debug(".......msgInbound Failed Email Notice end........");
            }
        } catch (Exception e) {
            log.debug(".......threePL msgInboundemailNotice Failed........");
            log.error("", e);
        }


        // 外包仓出库失败邮件通知
        try {
            List<MsgOutboundOrder> msgOutboundList = msgOutboundOrderDao.getMsgOutboundByErrorCount(5l);
            if (msgOutboundList != null && msgOutboundList.size() > 0) {
                log.debug(".......msgOutbound Failed Email Notice begin........");
                threePLManager.msgThreePLEmailNotice("THREEPL_OUTBOUND_NOTICE", "MSG_OUTBOUND_NOTICE", null, null, msgOutboundList, null, null, null, "");
                log.debug(".......msgOutbound failed Email Notice end........");
            }
        } catch (Exception e) {
            log.debug(".......threePL msgOutbound emailNotice Failed........");
            log.error("", e);
        }


        // 外包仓入库执行失败邮件通知
        try {
            List<MsgRtnInboundOrder> msgRtnInboundList = msgRtnInboundOrderDao.getRtnInboundByErrorCount(5l);
            if (msgRtnInboundList != null && msgRtnInboundList.size() > 0) {
                log.debug(".......msgInExecute  Falied Email Notice begin........");
                threePLManager.msgThreePLEmailNotice("THREEPL_INEXECUTE_NOTICE", "MSG_INEXECUTE_NOTICE", null, null, null, msgRtnInboundList, null, null, "");
                log.debug(".......msgInExecute  Falied Email Notice end........");
            }
        } catch (Exception e) {
            log.debug(".......threePL msgInExecute  falied emailNotice Failed........");
            log.error("", e);
        }

        // 外包仓出库执行失败邮件通知
        try {
            List<MsgRtnOutbound> msgRtnOutboundList = msgRtnOutboundDao.getRtnOutboundByErrorCount(5l);
            if (msgRtnOutboundList != null && msgRtnOutboundList.size() > 0) {
                log.debug(".......msgOutExecute  Falied Email Notice begin........");
                threePLManager.msgThreePLEmailNotice("THREEPL_OUTEXECUTE_NOTICE", "MSG_OUTEXECUTE_NOTICE", null, null, null, null, msgRtnOutboundList, null, "");
                log.debug(".......msgOutExecute  Falied Email Notice end........");
            }
        } catch (Exception e) {
            log.debug(".......threePL msgOutExecute  Falied emailNotice Failed........");
            log.error("", e);
        }

        // guess-外包仓出库执行失败邮件通知
        try {
            List<MsgRtnOutbound> msgRtnOutboundList = msgRtnOutboundDao.getRtnOutboundByErrorCountAndSource("guess", 5l);
            if (msgRtnOutboundList != null && msgRtnOutboundList.size() > 0) {
                log.debug(".......guess_msgOutExecute  Falied Email Notice begin........");
                threePLManager.msgThreePLEmailNotice("THREEPL_SOURCE_OUTEXECUTE_NOTICE", "MSG_SOURCE_OUTEXECUTE_NOTICE", null, null, null, null, msgRtnOutboundList, null, "GUESS_");
                log.debug(".......guess_msgOutExecute  Falied Email Notice end........");
            }
        } catch (Exception e) {
            log.debug(".......threePL guess_msgOutExecute  Falied emailNotice Failed........");
            log.error("", e);
        }

        // 外包仓取消执行邮件通知
        try {
            List<MsgOutboundOrderCancel> msgOrderCancelList = msgOutboundOrderCancelDao.getOrderCancelByErrorCount(5l);
            if (msgOrderCancelList != null && msgOrderCancelList.size() > 0) {
                log.debug(".......msgOutExecute  Falied Email Notice begin........");
                threePLManager.msgThreePLEmailNotice("THREEPL_CANCEL_NOTICE", "MSG_CANCEL_NOTICE", null, null, null, null, null, msgOrderCancelList, "");
                log.debug(".......msgOutExecute  Falied Email Notice end........");
            }
        } catch (Exception e) {
            log.debug(".......threePL msgOutExecute  Falied emailNotice Failed........");
            log.error("", e);
        }

        log.debug(".......threePL emailNotice end........");
    }

    // 发货接口实现类
    @SingleTaskLock(timeout = Constants.TASK_LOCK_TIMEOUT, value = Constants.TASK_LOCK_VALUE)
    public void wxOutBoundQueryMq() {
        // 1.查询出所有hub所需要的数据
        try {
            if (log.isDebugEnabled()) {
                log.debug("ThreePLTask WXOutbound Begin ..................");
            }
            List<WxConfirmOrderQueue> wxlist = wXConfirmOrderQueueDao.findExtOrder(5L);
            for (WxConfirmOrderQueue wx : wxlist) {
                try {
                    threePLManager.wxOutountMq(wx.getStaCode(), wx.getMailno());
                    WxConfirmOrderQueue queue = wXConfirmOrderQueueDao.getByPrimaryKey(wx.getId());
                    queue.setFilter2("1"); // 临时字段，用来标示是否已发送
                    wXConfirmOrderQueueDao.save(queue);
                } catch (Exception e) {
                    log.error("wxOutBoundQueryMq,:", e);
                    continue;
                }
            }
            if (log.isInfoEnabled()) {
                log.info("ThreePLTask WXlOutbound End ..................");
            }
        } catch (Exception e) {
            log.error("ThreePLTask WXlOutbound Exception,:", e);
        }
    }

    /**
     * 外包仓库存状态修改 1. 创建库存调整作业单 。2.占用库存。 3. 调用OMS接口。
     */
    @SingleTaskLock(timeout = Constants.TASK_LOCK_TIMEOUT, value = Constants.TASK_LOCK_VALUE)
    public void threePlInvStatusChange() {
        log.debug("===========================threePL invStatus Change ===================== ");
        // 根据中间表生成库存状态调整作业单
        List<String> sourceList = new ArrayList<String>();
        List<ChooseOption> chooselist = chooseOptionManager.findOptionListByCategoryCode(Constants.THREE_PL_USER_CONFIG);
        for (ChooseOption chooseOption : chooselist) {
            sourceList.add(chooseOption.getOptionKey());
        }
        List<MsgRtnAdjustment> msgRtnASJList = msgRtnAdjustmentDao.getBySourceAndType(sourceList, 50l, new BeanPropertyRowMapper<MsgRtnAdjustment>(MsgRtnAdjustment.class));
        if (msgRtnASJList.size() > 0) {
            StockTransApplication sta = null;
            for (MsgRtnAdjustment msgRtnAdj : msgRtnASJList) {
                sta = null;
                try {
                    sta = threePLManager.createAdjustmentSta(msgRtnAdj);
                } catch (BusinessException be) {
                    // 创建失败的数据
                    msgRtnAdjustmentDao.updateStatusById(msgRtnAdj.getId(), 0);
                    log.error("threePlInvStatusChange1 error msg :"+msgRtnAdj.getId(), be);
                    // log.error("BusinessException threePlInvStatusChange,error code is : {} , {}",
                    // be.getErrorCode(), be.getArgs());
                } catch (Exception e) {
                    // 创建失败的数据
                    log.error("threePlInvStatusChange2"+msgRtnAdj.getId(), e);
                }
                if (sta != null) {
                    msgRtnAdjustmentDao.updateStatusById(msgRtnAdj.getId(), 10);
                }
            }
        }
    }

    /**
     * 外包仓库存状态修改执行 1.释放库存，新增调整库存 2.通知OMS
     */
    @SingleTaskLock(timeout = Constants.TASK_LOCK_TIMEOUT, value = Constants.TASK_LOCK_VALUE)
    public void threePlInvStatusEexcute() {
        log.debug("===========================threePL invStatus Execute ===================== ");
        // 查询oms已解锁的库存调整作业单 -- to execute...
        List<String> sourceList = new ArrayList<String>();
        List<ChooseOption> chooselist = chooseOptionManager.findOptionListByCategoryCode(Constants.THREE_PL_USER_CONFIG);
        for (ChooseOption chooseOption : chooselist) {
            sourceList.add(chooseOption.getOptionKey());
        }
        List<StockTransApplicationCommand> staList = staDao.findInvStatusChangeStaBySource(sourceList, 5, new BeanPropertyRowMapper<StockTransApplicationCommand>(StockTransApplicationCommand.class));
        for (StockTransApplicationCommand sta : staList) {
            try {
                threePLManager.executeAdjustmentSta(sta.getId());
            } catch (Exception e) {
                MsgRtnAdjustment msg = msgRtnAdjustmentDao.getByPrimaryKey(sta.getAdjustMentId());
                // 更新错误次数
                msgRtnAdjustmentDao.updateStatusById(msg.getId(), 10);
                log.error("threePlInvStatusEexcute"+sta.getAdjustMentId(), e);
            }
        }

    }

    /**
     * 反馈信息任务
     */
    @SingleTaskLock(timeout = Constants.TASK_LOCK_TIMEOUT, value = Constants.TASK_LOCK_VALUE)
    public void threePlInvQtyChange() {
        log.debug("===========================threePL invChange Execute ===================== ");
        // 根据中间表生成库存调整
        List<String> sourceList = new ArrayList<String>();
        List<ChooseOption> chooselist = chooseOptionManager.findOptionListByCategoryCode(Constants.THREE_PL_USER_CONFIG);
        for (ChooseOption chooseOption : chooselist) {
            sourceList.add(chooseOption.getOptionKey());
        }
        List<MsgRtnAdjustment> msgRtnASJList = msgRtnAdjustmentDao.getBySourceAndType(sourceList, 51l, new BeanPropertyRowMapper<MsgRtnAdjustment>(MsgRtnAdjustment.class));
        if (msgRtnASJList.size() > 0) {
            for (MsgRtnAdjustment msgRtnADJ : msgRtnASJList) {
                try {
                    // 创建库存调整
                    // ic = idsManager.createVmiAdjustment(msgRtnADJ);
                    threePLManager.createThreePLAdjustment(msgRtnADJ);
                } catch (Exception e) {
                    // 创建失败的数据
                    msgRtnAdjustmentDao.updateStatusById(msgRtnADJ.getId(), 0);
                    log.error("threePlInvQtyChange error : ", e);
                }
            }
        }
    }

    /**
     * 库存数量执行。 1.释放库存，2 通知OMS
     */
    @SingleTaskLock(timeout = Constants.TASK_LOCK_TIMEOUT, value = Constants.TASK_LOCK_VALUE)
    public void threePlInvQtyExecute() {
        log.debug("===========================threePL invQty Change Execute ===================== ");
        // 查询YH 状态为 OMS已确认的盘点单，做执行。
        List<String> sourceList = new ArrayList<String>();
        List<ChooseOption> chooselist = chooseOptionManager.findOptionListByCategoryCode(Constants.THREE_PL_USER_CONFIG);
        for (ChooseOption chooseOption : chooselist) {
            sourceList.add(chooseOption.getOptionKey());
        }
        List<InventoryCheck> invList = inventoryCheckDao.getInventoryCheckByStatus(8l, sourceList, new BeanPropertyRowMapper<InventoryCheck>(InventoryCheck.class));
        if (invList.size() > 0) {
            for (InventoryCheck inventoryCheck : invList) {
                try {
                    warehouseManager.confirmInventoryCheckEbs(inventoryCheck.getRemork(), inventoryCheck.getId(), null, true);
                } catch (Exception e) {
                    inventoryCheckDao.updateStatusByCode(inventoryCheck.getCode());
                    log.error("", e);
                }
            }
        }
    }

    /**
     * 外包仓SN定时管理
     */
    @SingleTaskLock(timeout = Constants.TASK_LOCK_TIMEOUT, value = Constants.TASK_LOCK_VALUE)
    public void threePlSNManager() {
        log.debug("===========================threePL SN Manager Execute ===================== ");
        // 查询出T_WH_MSG_RTN_SN_DETAIL表中is_send为空的id并且in_line有值的数据
        List<Long> rtnSnId = rtnSnDetailDao.findRtnSnDetailSendIsNullAndInLine(new SingleColumnRowMapper<Long>(Long.class));
        try {
            // 根据rtnSnId来往T_WH_SKU_SN中添加数据
            for (Long id : rtnSnId) {
                // 根据inLineId来查询所有的SN记录
                List<SkuSnCommand> skuSnCommands = skuSnDao.getSkuSnsByInLineId(id, new BeanPropertyRowMapper<SkuSnCommand>(SkuSnCommand.class));
                // 循环保存查询出来的所有数据到SkuSn
                for (SkuSnCommand ssc : skuSnCommands) {
                    skuSnManager.saveSkuSnAndLog(ssc);
                }
            }
            // 查询出T_WH_MSG_RTN_SN_DETAIL表中is_send为空的id并且out_id有值的数据
            rtnSnId = rtnSnDetailDao.findRtnSnDetailSendIsNullAndOutLine(new SingleColumnRowMapper<Long>(Long.class));
            // 根据rtnSnId来往T_WH_SKU_SN中删除数据
            for (Long id : rtnSnId) {
                // 根据outId来查询所有的SN记录
                List<SkuSnCommand> skuSnCommands = skuSnDao.getSkuSnsByOutId(id, new BeanPropertyRowMapper<SkuSnCommand>(SkuSnCommand.class));
                for (SkuSnCommand ssc : skuSnCommands) {
                    skuSnManager.deleteSkuSnAndLog(ssc);
                }
            }
        } catch (Exception e) {
            log.error("ThreePL SN Manager Exception,:", e);
        }

        log.debug("===========================threePL SN Manager End ===================== ");
    }
}
