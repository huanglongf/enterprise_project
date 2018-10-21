package com.jumbo.wms.manager.task.vmiwarehouse;

import java.util.ArrayList;
import java.util.List;

import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.dao.vmi.warehouse.MsgInboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnAdjustmentDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnInboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnOutboundDao;
import com.jumbo.dao.vmi.warehouse.MsgSKUSyncDao;
import com.jumbo.webservice.ids.manager.IdsManager;
import com.jumbo.wms.Constants;
import com.jumbo.wms.daemon.TaskSf;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.task.TaskSfManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerProxy;
import com.jumbo.wms.manager.warehouse.WareHouseManagerQuery;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.vmi.warehouse.MsgInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnAdjustment;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutbound;
import com.jumbo.wms.model.vmi.warehouse.MsgSKUSync;

/***
 * 
 * @author jinlong.ke
 * 
 */
public class TaskSfImpl extends BaseManagerImpl implements TaskSf {

    /**
     * 
     */
    private static final long serialVersionUID = -3828040677756857356L;
    @Autowired
    private TaskSfManager taskSfManager;
    @Autowired
    private MsgSKUSyncDao msgSKUSyncDao;
    @Autowired
    private MsgRtnInboundOrderDao msgRtnInboundOrderDao;
    @Autowired
    private WareHouseManagerProxy wareHouseManagerProxy;
    @Autowired
    private WareHouseManagerQuery wareHouseManagerQuery;
    @Autowired
    private MsgInboundOrderDao msgInboundOrderDao;
    @Autowired
    private MsgOutboundOrderDao msgOutboundOrderDao;
    @Autowired
    private MsgRtnAdjustmentDao msgRtnAdjustmentDao;
    @Autowired
    private IdsManager idsManager;
    @Autowired
    private MsgRtnOutboundDao msgRtnOutboundDao;

    /**
     * 单个推送商品到顺丰
     */
    public void sendSingleSkuToSf() {
        List<MsgSKUSync> skus = msgSKUSyncDao.findVmiMsgSKUSync(Constants.VIM_WH_SOURCE_SF, new BeanPropertyRowMapper<MsgSKUSync>(MsgSKUSync.class));
        for (MsgSKUSync s : skus) {
            try {
                taskSfManager.sendSingleSkuToSf(s);
            } catch (Exception e) {
                log.error("", e);
            }

        }
    }

    /**
     * 入库推送定时任务入口
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void sendInboundOrderToSf() {
        sendSingleSkuToSf();
        List<MsgInboundOrder> list = msgInboundOrderDao.findVmiInboundNeedSend(Constants.VIM_WH_SOURCE_SF);
        for (MsgInboundOrder inOrder : list) {
            try {
                taskSfManager.sendInboundOrderToSf(inOrder);
            } catch (Exception e) {
                log.error("", e);
            }
        }
    }

    /**
     * 入库推送定时任务入口 加字段
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void sendInboundOrderToSf1() {
        log.info("SF sendInboundOrderToSf1 exe begin--------------------");
        sendBatchSkuToSf();// 同步商品信息(批量)
        // sendSingleSkuToSf();
        List<MsgInboundOrder> list = msgInboundOrderDao.findVmiInboundNeedSend(Constants.VIM_WH_SOURCE_SF);
        for (MsgInboundOrder inOrder : list) {
            try {
                taskSfManager.sendInboundOrderToSf1(inOrder);
            } catch (BusinessException e) {
                log.warn("sendInboundOrderToSf1, error :{}", e.getErrorCode());
            } catch (Exception e) {
                log.error("", e);
            }
        }
        log.info("SF sendInboundOrderToSf1 exe end--------------------");
    }

    /**
     * 出库推送定时任务入口
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void sendOutboundOrderToSf() {
        List<MsgOutboundOrder> list = msgOutboundOrderDao.findVmiOutboundNeedSend(Constants.VIM_WH_SOURCE_SF);
        for (MsgOutboundOrder outOrder : list) {
            try {
                taskSfManager.sendOutboundOrderToSf(outOrder);
            } catch (Exception e) {
                log.error("", e);
            }
        }
    }

    /**
     * 出库推送定时任务入口 加字段
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void sendOutboundOrderToSf1() {
        List<MsgOutboundOrder> list = msgOutboundOrderDao.findVmiOutboundNeedSend(Constants.VIM_WH_SOURCE_SF);
        for (MsgOutboundOrder outOrder : list) {
            try {
                taskSfManager.sendOutboundOrderToSf1(outOrder);
            } catch (Exception e) {
                log.error("", e);
            }
        }
    }

    /**
     * 批量推送商品到顺丰
     */
    public void sendBatchSkuToSf() {
        log.info("SF sendBatchSkuToSf exe begin--------------------");
        // 获取所有能同步给SF商品的货主编码
        List<String> sfFlagList = msgSKUSyncDao.findSfFlagSKUSync(Constants.VIM_WH_SOURCE_SF, new SingleColumnRowMapper<String>(String.class));
        for (String sfFlag : sfFlagList) {
            List<MsgSKUSync> skus = msgSKUSyncDao.findVmiMsgSKUSyncForSfFlag(Constants.VIM_WH_SOURCE_SF, sfFlag, new BeanPropertyRowMapper<MsgSKUSync>(MsgSKUSync.class));
            int skusCount = skus.size();
            List<MsgSKUSync> msgSkuList = new ArrayList<MsgSKUSync>();
            for (int i = 0; i < skus.size(); i++) {
                // 每次批量同步10条
                msgSkuList.add(skus.get(i));
                if (skusCount >= 10) {
                    if (msgSkuList.size() % 10 == 0) {
                        try {
                            taskSfManager.sendBatchSkuToSf(msgSkuList, sfFlag);
                        } catch (Exception e) {
                            log.debug("TaskSf-->sendBatchSkuToSf-->Exception");
                            log.error("", e);
                        }
                        skusCount = skusCount - msgSkuList.size();
                        msgSkuList = new ArrayList<MsgSKUSync>();
                    }
                } else {
                    if (msgSkuList.size() % skusCount == 0) {
                        try {
                            taskSfManager.sendBatchSkuToSf(msgSkuList, sfFlag);
                        } catch (Exception e) {
                            log.debug("TaskSf-->sendBatchSkuToSf-->Exception");
                            log.error("", e);
                        }
                        msgSkuList = new ArrayList<MsgSKUSync>();
                    }
                }
            }
        }
        log.info("SF sendBatchSkuToSf exe end--------------------");
    }

    /**
     * SF出库反馈定时任务入口
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void vmiSfOutboundRtn() {
        List<MsgRtnOutbound> rtns = wareHouseManagerQuery.findAllMsgRtnOutbound(Constants.VIM_WH_SOURCE_SF);
        for (MsgRtnOutbound rtn : rtns) {
            try {
                // 1.执行出库流程
                wareHouseManagerProxy.callVmiSalesStaOutBound(rtn.getId());
            } catch (BusinessException e) {
                log.error("vmiSfOutboundRtn error ! OUT STA :" + e.getErrorCode());
            } catch (Exception e) {
                log.error("", e);
            }
        }
    }

    /**
     * SF入库反馈定时任务入口
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void inOutBoundRtn() {
        log.info("SF inOutBoundRtn exe begin--------------------");
        List<MsgRtnInboundOrder> msgOrderNewInfo = msgRtnInboundOrderDao.findInboundByStatus(Constants.VIM_WH_SOURCE_SF, new BeanPropertyRowMapperExt<MsgRtnInboundOrder>(MsgRtnInboundOrder.class));
        if (msgOrderNewInfo.size() > 0) {
            for (MsgRtnInboundOrder msgNewInorder : msgOrderNewInfo) {
                try {
                    wareHouseManagerProxy.msgInBoundWareHouse(msgNewInorder);
                } catch (Exception e) {
                    if (e instanceof BusinessException) {
                        log.error("inOutBoundRtn error ,error code is : {}", ((BusinessException) e).getErrorCode());
                    } else {
                        log.error("", e);
                    }
                }
            }
        }
        log.info("SF inOutBoundRtn exe end--------------------");
    }

    /**
     * SF库存调整定时任务
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void vmiAdjustMentRtn() {
        log.info("SF vmiAdjustMentRtn exe begin--------------------");
        List<MsgRtnAdjustment> msgRtnASJList = msgRtnAdjustmentDao.queryByCanceledAndCreated(Constants.VIM_WH_SOURCE_SF);
        if (msgRtnASJList.size() > 0) {
            // InventoryCheck ic = null;
            for (MsgRtnAdjustment msgRtnADJ : msgRtnASJList) {
                // ic = null;
                try {
                    // 库存调整
                    // ic = idsManager.createVmiAdjustment(msgRtnADJ);
                    taskSfManager.vmiAdjustMentRtn(msgRtnADJ);
                    idsManager.updateADJStatus(msgRtnADJ, DefaultStatus.FINISHED);
                } catch (BusinessException e) {
                    // 创建错误的数据
                    idsManager.updateADJStatus(msgRtnADJ, DefaultStatus.ERROR);
                    log.error("", e);
                } catch (Exception e) {
                    // 创建失败的数据
                    idsManager.updateADJStatus(msgRtnADJ, DefaultStatus.CANCELED);
                    log.error("", e);
                }
                // if (ic != null) {
                // try {
                // // 执行库存调整
                // idsManager.executionVmiAdjustment(ic);
                // } catch (Exception e) {
                // log.error("----------->>IDS->WMS Error ! Adjustment execution Error!");
                // log.error("", e);
                // }
                // }
            }
        }
        log.info("SF vmiAdjustMentRtn exe end--------------------");
    }

    /**
     * 补充出库
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void replenishOutbound() {
        log.debug("SF replenishOutbound exe begin--------------------");
        List<MsgRtnOutbound> ml = msgRtnOutboundDao.getNeedExeReplenishOrder(Constants.VIM_WH_SOURCE_SF);
        for (MsgRtnOutbound m : ml) {
            try {
                taskSfManager.exeReplenishOutbound(m);
            } catch (Exception e) {
                log.error("", e);
            }
        }
        log.debug("SF replenishOutbound exe end--------------------");
    }
}
