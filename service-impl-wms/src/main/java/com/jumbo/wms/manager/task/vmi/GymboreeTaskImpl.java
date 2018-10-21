package com.jumbo.wms.manager.task.vmi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.dao.vmi.gymboreeData.GymboreeReceiveDataDao;
import com.jumbo.dao.vmi.gymboreeData.GymboreeReceiveRtnDataDao;
import com.jumbo.dao.vmi.gymboreeData.GymboreeRtnOutDataDao;
import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderCancelDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnAdjustmentDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnAdjustmentLineDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnInboundOrderDao;
import com.jumbo.webservice.ids.manager.IdsManager;
import com.jumbo.wms.Constants;
import com.jumbo.wms.daemon.GymboreeTask;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.vmi.gymboreeData.GymboreeTaskManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerProxy;
import com.jumbo.wms.manager.warehouse.WareHouseManagerQuery;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.vmi.gymboreeData.GymboreeReceiveData;
import com.jumbo.wms.model.vmi.gymboreeData.GymboreeReceiveRtnData;
import com.jumbo.wms.model.vmi.gymboreeData.GymboreeRtnOutData;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCancel;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnAdjustment;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutbound;
import com.jumbo.wms.model.warehouse.InventoryCheck;

/**
 * 金宝贝品牌对接方法
 * 
 * @author jinlong.ke
 * 
 */
public class GymboreeTaskImpl extends BaseManagerImpl implements GymboreeTask {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @Autowired
    private GymboreeTaskManager gymboreeTaskManager;
    @Autowired
    private MsgOutboundOrderCancelDao msgOutboundOrderCancelDao;
    @Autowired
    private WareHouseManagerQuery wareHouseManagerQuery;
    @Autowired
    private WareHouseManagerProxy wareHouseManagerProxy;
    @Autowired
    private MsgRtnInboundOrderDao msgRtnInboundOrderDao;
    @Autowired
    private GymboreeReceiveDataDao gymboreeReceiveDataDao;
    @Autowired
    private GymboreeReceiveRtnDataDao gymboreeReceiveRtnDataDao;
    @Autowired
    private GymboreeRtnOutDataDao gymboreeRtnOutDataDao;
    @Autowired
    private MsgRtnAdjustmentDao msgRtnAdjustmentDao;
    @Autowired
    private MsgRtnAdjustmentLineDao msgRtnAdjustmentLineDao;
    @Autowired
    private IdsManager idsManager;

    /**
     * 零售单指示定时任务入口
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void outboundNotice() {
        try {
            log.debug("GymboreeTask outboundNotice Begin .................GYM_TMALL");
            gymboreeTaskManager.outboundNotice(Constants.GYM_TMALL);
            log.debug("GymboreeTask outboundNotice End .................GYM_TMALL");
        } catch (Exception e) {
            log.error("", e);
        }
        try {
            log.debug("GymboreeTask outboundNotice Begin .................GYM_SC");
            gymboreeTaskManager.outboundNotice(Constants.GYM_SC);
            log.debug("GymboreeTask outboundNotice End .................GYM_SC");
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /**
     * 取消零售单指示定时任务入口
     */
    public void cancelOutbound() {
        try {
            log.debug("GymboreeTask cancelOutbound Begin ..................");
            List<MsgOutboundOrderCancel> list = msgOutboundOrderCancelDao.findOneVimCancelInfoByStatus(Constants.VMI_WH_SOURCE_GYMBOREE);
            for (MsgOutboundOrderCancel cl : list) {
                try {
                    gymboreeTaskManager.cancelOutbound(cl);
                } catch (Exception e) {
                    log.error("", e);
                    continue;
                }
            }
            log.debug("GymboreeTask cancelOutbound End ..................");
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /**
     * 金宝贝零售单实绩，执行出库
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void exeGymboreeOutboundOrder() {
        log.debug("Gymboree salesoutbound exe begin-------------------");
        gymboreeTaskManager.sendMailForErrorReback();
        List<MsgRtnOutbound> rtns = wareHouseManagerQuery.findAllMsgRtnOutbound(Constants.VMI_WH_SOURCE_GYMBOREE);
        for (MsgRtnOutbound rtn : rtns) {
            try {
                // 1.执行出库流程
                wareHouseManagerProxy.callVmiSalesStaOutBound(rtn.getId());
            } catch (BusinessException e) {
                log.debug("exeGymboreeOutboundOrder error ! OUT STA :" + e.getErrorCode());
            } catch (Exception e) {
                log.error("", e);
            }
        }
        log.debug("Gymboree salesoutbound exe end-------------------");
    }

    /**
     * 店存入库接收
     * 
     * @param msg
     */
    public void createInboundOrderAndExe(String msg) throws Exception {
        log.debug("Gymboree inbound receive begin-------------------");
        gymboreeTaskManager.receiveInboundData(msg);
        log.debug("Gymboree inbound receive end-------------------");
    }

    /**
     * 店存入库创建执行
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void createStaByReceive() {
        log.debug("Gymboree inbound create begin-------------------");
        List<GymboreeReceiveData> rds = gymboreeReceiveDataDao.findReceiveDataByStatus();
        for (GymboreeReceiveData rd : rds) {
            try {
                gymboreeTaskManager.createStaForInBound(rd);
            } catch (Exception e) {
                log.error("", e);
            }
        }
        log.debug("Gymboree inbound create end-------------------");
    }

    /**
     * 金宝贝退货指示
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void rtnInboundNotice() {
        try {
            log.debug("GymboreeTask rtnInboundNotice Begin .................GYM_SC");
            gymboreeTaskManager.rtnInboundNotice(Constants.GYM_SC);
            log.debug("GymboreeTask rtnInboundNotice End .................GYM_SC");
        } catch (Exception e) {
            log.error("", e);
        }
        try {
            log.debug("GymboreeTask rtnInboundNotice Begin .................GYM_TMALL");
            gymboreeTaskManager.rtnInboundNotice(Constants.GYM_TMALL);
            log.debug("GymboreeTask rtnInboundNotice End .................GYM_TMALL");
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /**
     * 金宝贝入库执行 店存入库，退货入库
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void exeInboundOrder() {
        log.debug("Gymboree inbound exe begin-------------------");
        List<MsgRtnInboundOrder> ol = msgRtnInboundOrderDao.findInboundByStatus(Constants.VMI_WH_SOURCE_GYMBOREE, new BeanPropertyRowMapperExt<MsgRtnInboundOrder>(MsgRtnInboundOrder.class));
        for (MsgRtnInboundOrder msgNewInorder : ol) {
            try {
                wareHouseManagerProxy.msgInBoundWareHouse(msgNewInorder);
            } catch (Exception e) {
                if (e instanceof BusinessException) {
                    log.error("exeInboundOrder error ,error code is : {}", ((BusinessException) e).getErrorCode());
                } else {
                    log.error("", e);
                }
            }
        }
        log.debug("Gymboree inbound exe end-------------------");
    }

    /**
     * 金宝贝店存出库接收
     * 
     * @param msg
     */
    public void rtnOutboundRecieveAndCreate(String msg) throws Exception {
        log.debug("GymboreeTask rtnOutboundRecieveAndCreate Begin .................");
        gymboreeTaskManager.receiveRtnOutboundMsg(msg);
        log.debug("GymboreeTask rtnOutboundRecieveAndCreate End .................");
    }

    /**
     * 金宝贝店存出库创单执行
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void rtnOutboundCreateSta() {
        log.info("GymboreeTask rtnOutboundRecieveAndCreate staCreate Begin .................");
        gymboreeReceiveRtnDataDao.updateBasicalInfo(Constants.VMI_WH_SOURCE_GYMBOREE);
        List<GymboreeReceiveRtnData> list = gymboreeReceiveRtnDataDao.getNeedExeReceiveData(new BeanPropertyRowMapper<GymboreeReceiveRtnData>(GymboreeReceiveRtnData.class));
        Map<String, List<GymboreeReceiveRtnData>> map = new HashMap<String, List<GymboreeReceiveRtnData>>();
        for (GymboreeReceiveRtnData rrd : list) {
            if (map.get(rrd.getErpVouchCode()) == null) {
                List<GymboreeReceiveRtnData> li = new ArrayList<GymboreeReceiveRtnData>();
                li.add(rrd);
                map.put(rrd.getErpVouchCode(), li);
            } else {
                map.get(rrd.getErpVouchCode()).add(rrd);
            }
        }
        for (String set : map.keySet()) {
            try {
                gymboreeTaskManager.careateRtnOutboundSta(set, map.get(set));
            } catch (BusinessException be) {
                log.warn("BusinessException rtnOutboundCreateSta,error code is : {}", be.getErrorCode());
            } catch (Exception e) {
                log.error("", e);
            }


        }
        log.debug("GymboreeTask rtnOutboundRecieveAndCreate staCreate End .................");
    }

    /**
     * 金宝贝店存出库确认
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void sendRtnOutboundResult() {
        try {
            log.info("GymboreeTask sendRtnOutboundResult Begin .................");
            List<GymboreeRtnOutData> dl = gymboreeRtnOutDataDao.getNeedSendData(GymboreeRtnOutData.OUTBOUND, new BeanPropertyRowMapper<GymboreeRtnOutData>(GymboreeRtnOutData.class));
            Map<String, List<GymboreeRtnOutData>> map = new HashMap<String, List<GymboreeRtnOutData>>();
            for (GymboreeRtnOutData gd : dl) {
                if (map.get(gd.getFchrSourceCode()) == null) {
                    List<GymboreeRtnOutData> li = new ArrayList<GymboreeRtnOutData>();
                    li.add(gd);
                    map.put(gd.getFchrSourceCode(), li);
                } else {
                    map.get(gd.getFchrSourceCode()).add(gd);
                }
            }
            for (String set : map.keySet()) {
                try {
                    gymboreeTaskManager.sendRtnOutboundResult(set, map.get(set));
                } catch (BusinessException be) {
                    log.error("BusinessException sendRtnOutboundResult,error code is : {}", be.getErrorCode());
                } catch (Exception e) {
                    log.error("", e);
                }
            }
            log.info("GymboreeTask sendRtnOutboundResult End .................");
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /**
     * 金宝贝店存入库确认
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void sendRtnInboundResult() {
        try {
            log.info("GymboreeTask sendRtnInboundResult Begin .................");
            List<GymboreeRtnOutData> dl = gymboreeRtnOutDataDao.getNeedSendData(GymboreeRtnOutData.INBOUND, new BeanPropertyRowMapper<GymboreeRtnOutData>(GymboreeRtnOutData.class));
            Map<String, List<GymboreeRtnOutData>> map = new HashMap<String, List<GymboreeRtnOutData>>();
            for (GymboreeRtnOutData gd : dl) {
                if (map.get(gd.getFchrSourceCode()) == null) {
                    List<GymboreeRtnOutData> li = new ArrayList<GymboreeRtnOutData>();
                    li.add(gd);
                    map.put(gd.getFchrSourceCode(), li);
                } else {
                    map.get(gd.getFchrSourceCode()).add(gd);
                }
            }
            for (String set : map.keySet()) {
                try {
                    gymboreeTaskManager.sendRtnInboundResult(set, map.get(set));
                } catch (BusinessException be) {
                    log.error("BusinessException sendRtnInboundResult,error code is : {}", be.getErrorCode());
                } catch (Exception e) {
                    log.error("", e);
                }
            }
            log.info("GymboreeTask sendRtnInboundResult End .................");
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /**
     * 金宝贝其他出入库
     * 
     * @param msg
     * @throws Exception
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void receiveOtherInOut(String msg) throws Exception {
        log.info("Gymboree othereinoutbound receive begin-------------------");
        gymboreeTaskManager.receiveOtherInOut(msg);
        log.info("Gymboree othereinoutbound receive end-------------------");

    }

    /**
     * 金宝贝其他出入库创建执行
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void exeInventoryChange() {
        log.info("Gymboree othereinoutbound create begin-------------------");
        msgRtnAdjustmentLineDao.updateSkuIdForGymboree();
        msgRtnAdjustmentDao.updateStatusForGymboree();
        List<MsgRtnAdjustment> msgRtnASJList = msgRtnAdjustmentDao.queryByCanceledAndCreated(Constants.VMI_WH_SOURCE_GYMBOREE);
        if (msgRtnASJList.size() > 0) {
            InventoryCheck ic = null;
            for (MsgRtnAdjustment msgRtnADJ : msgRtnASJList) {
                ic = null;
                try {
                    // 创建库存调整
                    ic = idsManager.createVmiAdjustment(msgRtnADJ);
                } catch (BusinessException e) {
                    // 创建错误的数据
                    idsManager.updateADJStatus(msgRtnADJ, DefaultStatus.ERROR);
                    log.error("BusinessException exeInventoryChange,error code is : ", e.getErrorCode());
                } catch (Exception e) {
                    // 创建失败的数据
                    idsManager.updateADJStatus(msgRtnADJ, DefaultStatus.CANCELED);
                    log.error("", e);
                }
                if (ic != null) {
                    try {
                        // 执行库存调整
                        idsManager.executionVmiAdjustment(ic);
                    } catch (BusinessException e) {
                        log.error("BusinessException exeInventoryChange,error code is : ", e.getErrorCode());
                    } catch (Exception e) {
                        log.error("", e);
                    }
                }
            }
        }
        log.info("Gymboree othereinoutbound create end-------------------");
    }

    /**
     * 其他入库反馈HUB
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void otherInboundRtn() {
        try {
            log.info("GymboreeTask otherInboundRtn Begin .................");
            List<GymboreeRtnOutData> dl = gymboreeRtnOutDataDao.getNeedSendData(GymboreeRtnOutData.INVIN, new BeanPropertyRowMapper<GymboreeRtnOutData>(GymboreeRtnOutData.class));
            Map<String, List<GymboreeRtnOutData>> map = new HashMap<String, List<GymboreeRtnOutData>>();
            for (GymboreeRtnOutData gd : dl) {
                if (map.get(gd.getFchrSourceCode()) == null) {
                    List<GymboreeRtnOutData> li = new ArrayList<GymboreeRtnOutData>();
                    li.add(gd);
                    map.put(gd.getFchrSourceCode(), li);
                } else {
                    map.get(gd.getFchrSourceCode()).add(gd);
                }
            }
            for (String set : map.keySet()) {
                try {
                    gymboreeTaskManager.otherInboundRtn(set, map.get(set));
                } catch (BusinessException e) {
                    log.error("BusinessException otherInboundRtn,error code is : ", e.getErrorCode());
                } catch (Exception e) {
                    log.error("", e);
                }
            }
            log.info("GymboreeTask otherInboundRtn End .................");
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /**
     * 其他出库反馈HUB
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void otherOutboundRtn() {
        try {
            log.info("GymboreeTask otherOutboundRtn Begin .................");
            List<GymboreeRtnOutData> dl = gymboreeRtnOutDataDao.getNeedSendData(GymboreeRtnOutData.INVOUT, new BeanPropertyRowMapper<GymboreeRtnOutData>(GymboreeRtnOutData.class));
            Map<String, List<GymboreeRtnOutData>> map = new HashMap<String, List<GymboreeRtnOutData>>();
            for (GymboreeRtnOutData gd : dl) {
                if (map.get(gd.getFchrSourceCode()) == null) {
                    List<GymboreeRtnOutData> li = new ArrayList<GymboreeRtnOutData>();
                    li.add(gd);
                    map.put(gd.getFchrSourceCode(), li);
                } else {
                    map.get(gd.getFchrSourceCode()).add(gd);
                }
            }
            for (String set : map.keySet()) {
                try {
                    gymboreeTaskManager.otherOutboundRtn(set, map.get(set));
                } catch (BusinessException e) {
                    log.error("BusinessException otherOutboundRtn,error code is : ", e.getErrorCode());
                } catch (Exception e) {
                    log.error("", e);
                }
            }
            log.info("GymboreeTask otherOutboundRtn End .................");
        } catch (Exception e) {
            log.error("", e);
        }
    }

}
