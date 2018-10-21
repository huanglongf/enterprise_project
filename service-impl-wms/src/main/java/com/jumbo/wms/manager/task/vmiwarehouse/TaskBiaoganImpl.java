package com.jumbo.wms.manager.task.vmiwarehouse;

import java.util.List;

import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.beans.factory.annotation.Autowired;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.dao.vmi.warehouse.MsgInboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnInboundOrderDao;
import com.jumbo.dao.warehouse.MsgRaCancelDao;
import com.jumbo.webservice.biaogan.command.InOutBoundResponse;
import com.jumbo.webservice.biaogan.manager.InOutBoundManager;
import com.jumbo.wms.Constants;
import com.jumbo.wms.daemon.TaskBiaogan;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.outwh.biaogan.BiaoGanManagerProxy;
import com.jumbo.wms.manager.vmi.warehouse.VmiWarehouseFactory;
import com.jumbo.wms.manager.vmi.warehouse.VmiWarehouseInterface;
import com.jumbo.wms.manager.warehouse.WareHouseManagerProxy;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.vmi.warehouse.MsgInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgRaCancel;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnInboundOrder;

public class TaskBiaoganImpl extends BaseManagerImpl implements TaskBiaogan {
    /**
	 * 
	 */
    private static final long serialVersionUID = 6105361080357123463L;
    @Autowired
    private InOutBoundManager inOutBoundManager;
    @Autowired
    private WareHouseManagerProxy wareHouseManagerProxy;
    @Autowired
    private MsgRtnInboundOrderDao msgRtnInboundOrder;
    @Autowired
    private MsgInboundOrderDao msgInboundOrder;
    @Autowired
    private MsgInboundOrderDao msgInboundDao;
    @Autowired
    private MsgRaCancelDao msgRaCancelDao;
    @Autowired
    private VmiWarehouseFactory vmiWarehouseFactory;
    @Autowired
    private BiaoGanManagerProxy biaoGanManagerProxy;

    /**
     * 发送发票信息
     */
    public void sendInvoices() {
        try {
            biaoGanManagerProxy.sendInvoices();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /**
     * 发送订单信息
     */
    public void sendSalesOutOrder() {
        try {
            biaoGanManagerProxy.sendSalesOutOrder();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /**
     * VML 销售出库通知，推送发票
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void vmiSaleOutbound() {
        sendSalesOutOrder();
        sendInvoices();
    }

    /**
     * 销售出库反馈
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void vmiSaleOutboundRtn() {
        wareHouseManagerProxy.outboundToBzProxyTask();
    }

    /**
     * SKU同步
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void singleSkuToWms() {
        biaoGanManagerProxy.singleSkuToWmsProxy();
    }

    /**
     * 处理流程为判断当日出库作业单是否都同步到，发现未同步的订单直接通过查询接口反馈信息同步出库信息
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void orderDetailQueryTodayTask() {
        biaoGanManagerProxy.orderDetailQueryToday();
        try {
            wareHouseManagerProxy.outboundToBzProxyTask();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /**
     * 入库反馈
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void inOutBoundRtn() {
        List<MsgRtnInboundOrder> msgOrderNewInfo = msgRtnInboundOrder.findInboundByStatus(Constants.VIM_WH_SOURCE_BIAOGAN, new BeanPropertyRowMapperExt<MsgRtnInboundOrder>(MsgRtnInboundOrder.class));
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
    }

    /**
     * 入库通知
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void taskAnsToWms() {
        List<MsgInboundOrder> findcreateInbound = msgInboundOrder.findMsgInboundByStatus(Constants.VIM_WH_SOURCE_BIAOGAN, null, DefaultStatus.CREATED, true);
        for (MsgInboundOrder msgInbound : findcreateInbound) {
            try {
                biaoGanManagerProxy.singleSkuToWmsProxy();// 同步SKU
            } catch (Exception e) {
                log.error("", e);
            }
            InOutBoundResponse response = inOutBoundManager.ansToWms(msgInbound);
            if (response.getSuccess().equals("true")) {
                msgInboundDao.updateMsgInboundStatus(msgInbound.getId(), DefaultStatus.FINISHED.getValue());
            } else if (response.getSuccess().equals("false")) {
                msgInboundDao.updateMsgInboundStatus(msgInbound.getId(), DefaultStatus.CANCELED.getValue());
            }
        }
    }

    /**
     * 退换货入库取消通知
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void cancelReturnRequest() {
        List<MsgRaCancel> msgRaList = msgRaCancelDao.findNewMsgBySource(Constants.VIM_WH_SOURCE_BIAOGAN, new BeanPropertyRowMapperExt<MsgRaCancel>(MsgRaCancel.class));
        VmiWarehouseInterface vw = vmiWarehouseFactory.getVmiWarehouse(Constants.VIM_WH_SOURCE_BIAOGAN);
        for (MsgRaCancel msg : msgRaList) {
            try {
                vw.cancelReturnRequest(msg.getId());
            } catch (Exception e) {
                log.error("", e);
            }
        }
    }

    /**
     * 通知标杆其他出库
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void taskOutBoundReturn() {
        biaoGanManagerProxy.outBoundReturn();
    }
}
