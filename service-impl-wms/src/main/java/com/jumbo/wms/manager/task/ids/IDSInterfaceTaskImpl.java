package com.jumbo.wms.manager.task.ids;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.hub2wms.WmsSalesOrderLogDao;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.vmi.ids.IdsInventorySynchronousDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnOutboundDao;
import com.jumbo.dao.warehouse.InventoryCheckDao;
import com.jumbo.dao.warehouse.InvetoryChangeDao;
import com.jumbo.webservice.ids.manager.IdsManager;
import com.jumbo.webservice.ids.manager.IdsManagerProxy;
import com.jumbo.wms.Constants;
import com.jumbo.wms.daemon.IDSInterfaceTask;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.task.boc.BocTaskImpl;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExe;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.hub2wms.WmsSalesOrderLogCommand;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutbound;
import com.jumbo.wms.model.warehouse.InventoryCheck;
import com.jumbo.wms.model.warehouse.InvetoryChange;

public class IDSInterfaceTaskImpl extends BaseManagerImpl implements IDSInterfaceTask {

    /**
     * 
     */
    private static final long serialVersionUID = -9098899496186654883L;
    protected static final Logger log = LoggerFactory.getLogger(IDSInterfaceTaskImpl.class);
    @Autowired
    private IdsManagerProxy idsManagerProxy;
    @Autowired
    private MsgRtnOutboundDao msgRtnOutbounddao;
    @Autowired
    private BocTaskImpl bocTask;
    @Autowired
    private InventoryCheckDao inventoryCheckDao;
    @Autowired
    private IdsInventorySynchronousDao idsInvSynDao;
    @Autowired
    private IdsManager idsManager;
    @Autowired
    private WarehouseDao warehouseDao;

    @Autowired
    private ChooseOptionDao chooseOptionDao;

    @Autowired
    private WmsSalesOrderLogDao wmsSalesOrderLogDao;

    @Autowired
    private InvetoryChangeDao invetoryChangeDao;

    @Autowired
    private WareHouseManagerExe wareHouseManagerExe;


    /**
     * 立峰 发送出库订单 双十一 此任务暂定
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void sendSalesOrderToLFTask() {
        log.debug("Debug LF sendSalesOrderToLFTask..");
        for (String source : Constants.VIM_WH_SOURCE_LF_LIST) {
            try {
                log.debug("LF sendSalesOrderToLFTask..." + source);
                idsManagerProxy.wmsOrderToIds(source, source);
            } catch (Exception e) {
                log.error("", e);
            }
        }
    }


    /**
     * NBAUA 出库订单 同步到立峰
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    @Deprecated
    public void sendNBAUASalesOrderToLFTask() {
        idsManagerProxy.wmsOrderToIds(Constants.VIM_WH_SOURCE_NBAUA_IDS, Constants.VIM_WH_SOURCE_NBAUA_IDS);
    }

    /**
     * UA 出库订单 同步到立峰
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    @Deprecated
    public void sendUASalesOrderToLFTask() {
        idsManagerProxy.wmsOrderToIds(Constants.VIM_WH_SOURCE_UA_IDS, Constants.VIM_WH_SOURCE_UA_IDS);
    }

    /**
     * AF 出库订单 同步到立峰
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    @Deprecated
    public void sendAFSalesOrderToLFTask() {
        idsManagerProxy.wmsOrderToIds(Constants.VIM_WH_SOURCE_AF_IDS, Constants.VIM_WH_SOURCE_AF_IDS);
    }

    /**
     * NewLook 出库订单 同步到立峰
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    @Deprecated
    public void sendNewLookSalesOrderToLFTask() {
        idsManagerProxy.wmsOrderToIds(Constants.VIM_WH_SOURCE_NEWLOOK_IDS, Constants.VIM_WH_SOURCE_NEWLOOK_IDS);
    }

    /**
     * NBJ01出库订单 同步到立峰
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    @Deprecated
    public void sendNBJ01SalesOrderToLFTask() {
        idsManagerProxy.wmsOrderToIds(Constants.VIM_WH_SOURCE_NBJ01UA_IDS, Constants.VIM_WH_SOURCE_NBJ01UA_IDS);
    }

    /**
     * AEO 出库订单 同步到立峰
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    @Deprecated
    public void sendAEOSalesOrderToLFTask() {
        // idsManagerProxy.wmsAeoOrderToIds(Constants.VIM_WH_SOURCE_AEO_IDS,
        // Constants.VIM_WH_SOURCE_AEO_IDS);
        idsManagerProxy.wmsOrderToIds(Constants.VIM_WH_SOURCE_AEO_IDS, Constants.VIM_WH_SOURCE_AEO_IDS);
    }

    /**
     * NIKE 出库订单 同步到立峰
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void sendNIKESalesOrderToLFTask() {
        idsManagerProxy.wmsOrderToIds(Constants.VIM_WH_SOURCE_NIKE_IDS, Constants.VIM_WH_SOURCE_NIKE_IDS);
        idsManagerProxy.wmsOrderToIds(Constants.VIM_WH_SOURCE_NIKE_IDS_TM, Constants.VIM_WH_SOURCE_NIKE_IDS_TM);
    }

    /**
     * UA 出库订单 反馈执行
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void exeUASalesOrderToLFTask() {
        List<MsgRtnOutbound> msgoutList = msgRtnOutbounddao.findAllVmiMsgOutbound(Constants.VIM_WH_SOURCE_UA_IDS, new BeanPropertyRowMapper<MsgRtnOutbound>(MsgRtnOutbound.class));
        try {
            bocTask.executeMsgRtnOutbound(msgoutList);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /**
     * AF 出库订单 反馈执行
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void exeAFSalesOrderToLFTask() {
        List<MsgRtnOutbound> msgoutList = msgRtnOutbounddao.findAllVmiMsgOutbound(Constants.VIM_WH_SOURCE_AF_IDS, new BeanPropertyRowMapper<MsgRtnOutbound>(MsgRtnOutbound.class));
        try {
            bocTask.executeMsgRtnOutbound(msgoutList);
        } catch (Exception e) {
            log.error("", e);
        }
    }



    /**
     * NBAUA 出库订单 反馈执行
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void exeNBAUASalesOrderToLFTask() {
        List<MsgRtnOutbound> msgoutList = msgRtnOutbounddao.findAllVmiMsgOutbound(Constants.VIM_WH_SOURCE_NBAUA_IDS, new BeanPropertyRowMapper<MsgRtnOutbound>(MsgRtnOutbound.class));
        try {
            bocTask.executeMsgRtnOutbound(msgoutList);
        } catch (Exception e) {
            log.error("", e);
        }
    }


    /**
     * AEO 出库订单 反馈执行
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void exeAEOSalesOrderToLFTask() {
        List<MsgRtnOutbound> msgoutList = msgRtnOutbounddao.findAllVmiMsgOutbound(Constants.VIM_WH_SOURCE_AEO_IDS, new BeanPropertyRowMapper<MsgRtnOutbound>(MsgRtnOutbound.class));
        try {
            bocTask.executeMsgRtnOutbound(msgoutList);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /**
     * NIKE 出库订单反馈执行
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT)
    public void exeNIKESalesOrderToLFTask() {
        List<MsgRtnOutbound> msgoutList = msgRtnOutbounddao.findAllVmiMsgOutbound(Constants.VIM_WH_SOURCE_NIKE_IDS, new BeanPropertyRowMapper<MsgRtnOutbound>(MsgRtnOutbound.class));
        try {
            bocTask.executeMsgRtnOutbound(msgoutList);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    @Override
    public void exeNIKESalesOrderToLFTaskTm() {
        List<MsgRtnOutbound> msgoutList = msgRtnOutbounddao.findAllVmiMsgOutbound(Constants.VIM_WH_SOURCE_NIKE_IDS_TM, new BeanPropertyRowMapper<MsgRtnOutbound>(MsgRtnOutbound.class));
        try {
            bocTask.executeMsgRtnOutbound(msgoutList);
        } catch (Exception e) {
            log.error("", e);
        }

    }

    /**
     * NBJ01 出库订单反馈执行
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT)
    public void exeNBJ01SalesOrderToLFTask() {
        List<MsgRtnOutbound> msgoutList = msgRtnOutbounddao.findAllVmiMsgOutbound(Constants.VIM_WH_SOURCE_NBJ01UA_IDS, new BeanPropertyRowMapper<MsgRtnOutbound>(MsgRtnOutbound.class));
        try {
            bocTask.executeMsgRtnOutbound(msgoutList);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /**
     * UA 发送入库订单
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT)
    public void sendUAReturnOrderToLFTask() {
        idsManagerProxy.returnNotifyRequesformLF(Constants.VIM_WH_SOURCE_UA_IDS);
    }

    /**
     * AF 发送入库订单
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT)
    public void sendAFReturnOrderToLFTask() {
        idsManagerProxy.returnNotifyRequesformLF(Constants.VIM_WH_SOURCE_AF_IDS);
    }

    /**
     * NewLook 发送入库订单
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT)
    public void sendNewLookReturnOrderToLFTask() {
        idsManagerProxy.returnNotifyRequesformLF(Constants.VIM_WH_SOURCE_NEWLOOK_IDS);
    }

    /**
     * AEO 发送入库订单
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT)
    public void sendAEOReturnOrderToLFTask() {
        idsManagerProxy.returnNotifyRequesformLF(Constants.VIM_WH_SOURCE_AEO_IDS);
    }

    /**
     * NBAUA 发送入库订单
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT)
    public void sendNBAUAReturnOrderToLFTask() {
        idsManagerProxy.returnNotifyRequesformLF(Constants.VIM_WH_SOURCE_NBAUA_IDS);
    }

    /**
     * NIKE 发送VMI入库订单
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT)
    public void sendNikeOrderToLFTask() {
        log.info("sendNikeOrderToLFTask1");
        idsManagerProxy.inboundIdsNike(Constants.VIM_WH_SOURCE_NIKE_IDS);
        idsManagerProxy.inboundIdsNike(Constants.VIM_WH_SOURCE_NIKE_IDS_TM);
        idsManagerProxy.inboundIdsNike(Constants.VIM_WH_SOURCE_NIKE_IDS_002);
        idsManagerProxy.inboundIdsNike(Constants.VIM_WH_SOURCE_NIKE_IDS_003);
        idsManagerProxy.inboundIdsNike(Constants.VIM_WH_SOURCE_NIKE_IDS_004);
        idsManagerProxy.inboundIdsNike(Constants.VIM_WH_SOURCE_NIKE_IDS_005);
        idsManagerProxy.inboundIdsNike(Constants.VIM_WH_SOURCE_CONVERSE_IDS);

        log.info("sendNikeOrderToLFTask2");
    }

    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT)
    public void sendIdsVsToLFTask() {
        idsManagerProxy.returnNotifyRequesformLF(Constants.VIM_WH_SOURCE_IDS_VS);
    }

    /**
     * 立峰 发送取消订单 newLook,其他改为实时接口
     * 
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT)
    public void sendCaneleOrderToLFTask() {
        try {
            log.debug("LF CaneleOrderToLFTask..." + Constants.VIM_WH_SOURCE_NEWLOOK_IDS);
            idsManagerProxy.orderCancelResponseToLF(Constants.VIM_WH_SOURCE_NEWLOOK_IDS);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT)
    public void executeAFIdsInventorySyn() {
        idsManagerProxy.idsInventorySyn(Constants.VIM_WH_SOURCE_AF_IDS);
    }

    /**
     * newLook 库存同步
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT)
    public void executeNewLookIdsInventorySyn() {
        idsManagerProxy.idsInventorySyn(Constants.VIM_WH_SOURCE_NEWLOOK_IDS);
    }

    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT)
    public void executeAEOIdsInventorySyn() {
        idsManagerProxy.idsInventorySyn(Constants.VIM_WH_SOURCE_AEO_IDS);
    }



    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT)
    public void executeLevisIdsInventorySyn() {
        idsManagerProxy.idsInventorySyn(Constants.VIM_WH_SOURCE_IDS);
    }

    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT)
    public void executeUAIdsInventorySyn() {
        idsManagerProxy.idsInventorySyn(Constants.VIM_WH_SOURCE_UA_IDS);
    }

    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT)
    public void executeNBAUAIdsInventorySyn() {
        idsManagerProxy.idsInventorySyn(Constants.VIM_WH_SOURCE_NBAUA_IDS);
    }

    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT)
    public void executeIdsVsInventorySyn() {
        idsManagerProxy.idsInventorySyn(Constants.VIM_WH_SOURCE_IDS_VS);
    }

    /**
     * 执行失败重复执行
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT)
    public void inventoryCheckGUESS() {

        List<InventoryCheck> checkList = inventoryCheckDao.getInventoryCheckStatusList(6075L);
        for (InventoryCheck inv : checkList) {
            try {
                InventoryCheck ic = inventoryCheckDao.findByCode(inv.getCode());
                idsManager.executionVmiAdjustment(ic);
                idsInvSynDao.updateMsgStatusBywmsCode(ic.getSlipCode(), ic.getCode());
            } catch (Exception e) {
                log.error("", e);
            }

        }
    }

    /**
     * UA 执行失败重复执行
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT)
    public void inventoryCheckUA() {

        List<InventoryCheck> checkList = inventoryCheckDao.getInventoryCheckStatusList(6031L);
        for (InventoryCheck inv : checkList) {
            try {
                InventoryCheck ic = inventoryCheckDao.findByCode(inv.getCode());
                idsManager.executionVmiAdjustment(ic);
                idsInvSynDao.updateMsgStatusBywmsCode(ic.getSlipCode(), ic.getCode());
            } catch (Exception e) {
                log.error("", e);
            }

        }
    }

    /**
     * aeo执行失败重复执行
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT)
    public void inventoryCheckAEO() {

        List<InventoryCheck> checkList = inventoryCheckDao.getInventoryCheckStatusList(4942L);
        for (InventoryCheck inv : checkList) {
            try {
                InventoryCheck ic = inventoryCheckDao.findByCode(inv.getCode());
                idsManager.executionVmiAdjustment(ic);
                idsInvSynDao.updateMsgStatusBywmsCode(ic.getSlipCode(), ic.getCode());
            } catch (Exception e) {
                log.error("", e);
            }

        }
    }



    /**
     * af执行失败重复执行
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT)
    public void inventoryCheckAF() {

        List<InventoryCheck> checkList = inventoryCheckDao.getInventoryCheckStatusList(4082L);

        for (InventoryCheck inv : checkList) {
            try {
                InventoryCheck ic = inventoryCheckDao.findByCode(inv.getCode());
                idsManager.executionVmiAdjustment(ic);
                idsInvSynDao.updateMsgStatusBywmsCode(ic.getSlipCode(), ic.getCode());
            } catch (Exception e) {
                log.error("", e);
            }
        }
    }

    /**
     * newLook执行失败重复执行
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT)
    public void inventoryCheckNewLook() {

        List<InventoryCheck> checkList = inventoryCheckDao.getInventoryCheckStatusList(6123L);

        for (InventoryCheck inv : checkList) {
            try {
                InventoryCheck ic = inventoryCheckDao.findByCode(inv.getCode());
                idsManager.executionVmiAdjustment(ic);
                idsInvSynDao.updateMsgStatusBywmsCode(ic.getSlipCode(), ic.getCode());
            } catch (Exception e) {
                log.error("", e);
            }
        }
    }

    /**
     * newLookJD执行失败重复执行
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT)
    public void inventoryCheckNewLookJD() {
        Warehouse wh = warehouseDao.getWarehouseByVmiSourceWh(Constants.VIM_WH_SOURCE_NEWLOOKJD_IDS);
        List<InventoryCheck> checkList = inventoryCheckDao.getInventoryCheckStatusList(wh.getOu().getId());

        for (InventoryCheck inv : checkList) {
            try {
                InventoryCheck ic = inventoryCheckDao.findByCode(inv.getCode());
                idsManager.executionVmiAdjustment(ic);
                idsInvSynDao.updateMsgStatusBywmsCode(ic.getSlipCode(), ic.getCode());
            } catch (Exception e) {
                log.error("", e);
            }
        }
    }

    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT)
    public void inventoryCheckVS() {
        Warehouse wh = warehouseDao.getWarehouseByVmiSourceWh(Constants.VIM_WH_SOURCE_IDS_VS);
        List<InventoryCheck> checkList = inventoryCheckDao.getInventoryCheckStatusList(wh.getOu().getId());

        for (InventoryCheck inv : checkList) {
            try {
                InventoryCheck ic = inventoryCheckDao.findByCode(inv.getCode());
                idsManager.executionVmiAdjustment(ic);
                idsInvSynDao.updateMsgStatusBywmsCode(ic.getSlipCode(), ic.getCode());
            } catch (BusinessException e) {
                log.warn("InventoryCheck error : {}, source : {}", e.getErrorCode(), Constants.VIM_WH_SOURCE_IDS_VS);
            } catch (Exception e) {
                log.error("", e);
            }
        }
    }

    /**
     * godiva执行失败重复执行(HAVI外包仓)
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT)
    public void inventoryCheckGodivaHavi() {
        Warehouse wh = warehouseDao.getWarehouseByVmiSourceWh(Constants.VIM_WH_SOURCE_GODIVA_HAVI);
        List<InventoryCheck> checkList = inventoryCheckDao.getInventoryCheckStatusList(wh.getOu().getId());

        for (InventoryCheck inv : checkList) {
            try {
                InventoryCheck ic = inventoryCheckDao.findByCode(inv.getCode());
                idsManager.executionVmiAdjustment(ic);
                idsInvSynDao.updateMsgStatusBywmsCode(ic.getSlipCode(), ic.getCode());
            } catch (Exception e) {
                log.error("", e);
            }
        }
    }


    public void inventoryStatusChangeToOms() {
        log.error("inventoryStatusChangeToOms--start");
        List<InvetoryChange> invetoryChangeList = invetoryChangeDao.queryInvetoryChange();
        for (InvetoryChange list : invetoryChangeList) {
            try {
                wareHouseManagerExe.invChangeLogNoticePac(list.getStaId(), list.getStvId());
                invetoryChangeDao.updateInvetoryChangeStatus(list.getId());
            } catch (Exception e) {
                log.error("inventoryStatusChangeToOms" + list.getId(), e);
            }
        }
        log.error("inventoryStatusChangeToOms--end");
    }


    /**
     * godiva出库订单 同步到立峰(HAVI外包仓)
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT)
    public void sendGodivaHaviSalesOrderToLFTask() {
        idsManagerProxy.wmsOrderToIds(Constants.VIM_WH_SOURCE_GODIVA_HAVI, Constants.VIM_WH_SOURCE_GODIVA_HAVI);

    }


    /**
     * Godiva 发送退换货入库同步到立峰(Havi外包仓)
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT)
    public void sendGodivaHaviReturnOrderToLFTask() {
        idsManagerProxy.returnNotifyRequesformLF(Constants.VIM_WH_SOURCE_GODIVA_HAVI);
    }

    /**
     * Godiva 库存同步(Havi外包仓)
     */
    public void executeGodivaHaviIdsInventorySyn() {
        idsManagerProxy.idsInventorySyn(Constants.VIM_WH_SOURCE_GODIVA_HAVI);
    }

    /**
     * Godiva-havi出库订单 反馈执行
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void exeGodivaHaviSalesOrderToLFTask() {
        List<MsgRtnOutbound> msgoutList = msgRtnOutbounddao.findAllVmiMsgOutbound(Constants.VIM_WH_SOURCE_GODIVA_HAVI, new BeanPropertyRowMapper<MsgRtnOutbound>(MsgRtnOutbound.class));
        try {
            bocTask.executeMsgRtnOutbound(msgoutList);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /**
     * NewLookJD 出库订单 同步到立峰
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    @Deprecated
    public void sendNewLookJDSalesOrderToLFTask() {
        idsManagerProxy.wmsOrderToIds(Constants.VIM_WH_SOURCE_NEWLOOKJD_IDS, Constants.VIM_WH_SOURCE_NEWLOOKJD_IDS);
    }

    /**
     * NewLookJD 发送入库订单
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT)
    public void sendNewLookJDReturnOrderToLFTask() {
        idsManagerProxy.returnNotifyRequesformLF(Constants.VIM_WH_SOURCE_NEWLOOKJD_IDS);
    }

    /**
     * 立峰 发送取消订单 newLookJD,其他改为实时接口
     * 
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT)
    public void sendNewLookJDCaneleOrderToLFTask() {
        try {
            log.debug("LF CaneleOrderToLFTask..." + Constants.VIM_WH_SOURCE_NEWLOOKJD_IDS);
            idsManagerProxy.orderCancelResponseToLF(Constants.VIM_WH_SOURCE_NEWLOOKJD_IDS);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /**
     * newLookJD 库存同步
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT)
    public void executeNewLookJDIdsInventorySyn() {
        idsManagerProxy.idsInventorySyn(Constants.VIM_WH_SOURCE_NEWLOOKJD_IDS);
    }

    /**
     * AEO RMI 发送销售订单
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT)
    @Deprecated
    public void sendAEOSalesOrderToRMSTask() {
        idsManagerProxy.wmsAeoOrderToIds(Constants.VIM_WH_SOURCE_AEO_IDS, Constants.VIM_WH_SOURCE_AEO_IDS);
    }

    @Override
    public void idsNikeFeedbackTask() {
        log.info("idsNikeFeedbackTask1");
        idsManagerProxy.idsNikeFeedbackTask(Constants.VIM_WH_SOURCE_NIKE_IDS);
        idsManagerProxy.idsNikeFeedbackTask(Constants.VIM_WH_SOURCE_NIKE_IDS_TM);
        idsManagerProxy.idsNikeFeedbackTask(Constants.VIM_WH_SOURCE_NIKE_IDS_002);
        idsManagerProxy.idsNikeFeedbackTask(Constants.VIM_WH_SOURCE_NIKE_IDS_003);
        idsManagerProxy.idsNikeFeedbackTask(Constants.VIM_WH_SOURCE_NIKE_IDS_004);
        idsManagerProxy.idsNikeFeedbackTask(Constants.VIM_WH_SOURCE_NIKE_IDS_005);
        idsManagerProxy.idsNikeFeedbackTask(Constants.VIM_WH_SOURCE_CONVERSE_IDS);
        log.info("idsNikeFeedbackTask2");
    }

    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT)
    public void deleteSoLogByTime() {
        WmsSalesOrderLogCommand wmsSalesOrderLogCommand = wmsSalesOrderLogDao.findWmsSalesOrderCount(new BeanPropertyRowMapper<WmsSalesOrderLogCommand>(WmsSalesOrderLogCommand.class));
        if (null != wmsSalesOrderLogCommand && null != wmsSalesOrderLogCommand.getQty() && !wmsSalesOrderLogCommand.getQty().equals(0L)) {
            Long count = wmsSalesOrderLogCommand.getQty();
            BigDecimal countBig = new BigDecimal(count);
            BigDecimal q = countBig.divide(new BigDecimal(10000), 0, BigDecimal.ROUND_HALF_UP);
            int q1 = q.intValue();
            for (int i = 0; i <= q1; i++) {
                idsManagerProxy.deleteSoLog();
            }
        }
    }

    /**
     * IDS 发送入库订单
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT)
    public void sendIDSReturnOrderToLFTask() {
        idsManagerProxy.returnNotifyRequesformLF(Constants.VIM_WH_SOURCE_IDS);
    }
}
