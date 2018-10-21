package com.jumbo.wms.manager.task.vmi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.wms.daemon.VmiDefaultTask;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.vmi.Default.VmiDefaultManager;
import com.jumbo.wms.model.vmi.Default.VmiAdjCommand;
import com.jumbo.wms.model.vmi.Default.VmiAdjDefault;
import com.jumbo.wms.model.vmi.Default.VmiAsnCommand;
import com.jumbo.wms.model.vmi.Default.VmiRsnCommand;
import com.jumbo.wms.model.vmi.Default.VmiRsnDefault;
import com.jumbo.wms.model.vmi.Default.VmiRtwCommand;
import com.jumbo.wms.model.vmi.Default.VmiRtwDefault;
import com.jumbo.wms.model.vmi.Default.VmiTfxCommand;
import com.jumbo.wms.model.vmi.Default.VmiTfxDefault;

public class VmiDefaultTaskImpl extends BaseManagerImpl implements VmiDefaultTask {

    private static final long serialVersionUID = -4338593723497493635L;

    @Autowired
    private VmiDefaultManager vmiDefaultManager;


    /**
     * 通过收货指令创建STA
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void createStaForVmiDefault() {
        log.debug("==============================VmiDefaultTask createStaForVmiDefault start");
        List<VmiAsnCommand> vaList = vmiDefaultManager.findVmiAsnAll();
        for (VmiAsnCommand va : vaList) {
            // 查询是否有转店作业单
            try {
                vmiDefaultManager.updateVmiRsnStatus(va.getStoreCode(), va.getVmiSource());
            } catch (Exception e) {
                log.error("", e);
                continue;
            }
            // 创建作业单
            try {
                vmiDefaultManager.createStaForVmiDefault(va.getStoreCode(), va.getVmiSource());// 创建STA
            } catch (BusinessException e) {
                log.warn("BusinessException ,error code :{},store :{}", e.getErrorCode(), va.getStoreCode());
            } catch (Exception e) {
                log.error("", e);
            }
            // 修改对应vmiAsn状态
            try {
                vmiDefaultManager.updateVmiAsnStatus(va.getStoreCode(), va.getVmiSource());
            } catch (BusinessException e) {
                log.warn("BusinessException ,error code :{},store :{}", e.getErrorCode(), va.getStoreCode());
            } catch (Exception e) {
                log.error("", e);
            }
        }
        log.debug("==============================VmiDefaultTask createStaForVmiDefault end");
    }


    /**
     * 反馈数据同步给HUB
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void uploadVmiToHub() {
        log.debug("==============================VmiDefaultTask uploadVmiToHub start");
        List<VmiRsnCommand> vrList = vmiDefaultManager.findVmiRsnAll();
        // 同步收货反馈给HUB
        for (VmiRsnCommand vmiRsnCommand : vrList) {
            try {
                vmiDefaultManager.uploadVmiRsnToHub(vmiRsnCommand);
            } catch (Exception e) {
                log.error("", e);
                vmiDefaultManager.updateVmiErrorCount(VmiRsnDefault.vmirsn, vmiRsnCommand.getId());
            }
        }

        List<VmiTfxCommand> vtList = vmiDefaultManager.findVmiTfxAll();
        // 同步转出数据给HUB
        for (VmiTfxCommand vmiTfxCommand : vtList) {
            try {
                vmiDefaultManager.uploadVmiTfxToHub(vmiTfxCommand);
            } catch (Exception e) {
                log.error("", e);
                vmiDefaultManager.updateVmiErrorCount(VmiTfxDefault.vmitfx, vmiTfxCommand.getId());
            }
        }

        List<VmiAdjCommand> adjList = vmiDefaultManager.findVmiAdjAll();
        // 同步调整数据给HUB
        for (VmiAdjCommand vmiAdjCommand : adjList) {
            try {
                vmiDefaultManager.uploadVmiAdjToHub(vmiAdjCommand);
            } catch (Exception e) {
                log.error("", e);
                vmiDefaultManager.updateVmiErrorCount(VmiAdjDefault.vmiadj, vmiAdjCommand.getId());
            }
        }

        List<VmiRtwCommand> rtwList = vmiDefaultManager.findVmiRtwAll();
        // 同步退仓数据给HUB
        for (VmiRtwCommand vmiRtwCommand : rtwList) {
            try {
                vmiDefaultManager.uploadVmiRtwToHub(vmiRtwCommand);
            } catch (Exception e) {
                log.error("", e);
                vmiDefaultManager.updateVmiErrorCount(VmiRtwDefault.vmirtw, vmiRtwCommand.getId());
            }
        }
        log.debug("==============================VmiDefaultTask uploadVmiToHub end");
    }

    /**
     * 每天早上10点 邮件通知异常数据
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void vmiErrorEmailInform() {
        log.debug("==============================VmiDefaultTask vmiErrorEmailInform start");
        try {
            // 发送asn异常信息
            vmiDefaultManager.vmiAsnErrorEmailInform();
        } catch (Exception e) {
            log.error("发送vmiAsn异常信息失败", e);
        }
        // 发送rsn异常信息
        try {
            vmiDefaultManager.vmiRsnErrorEmailInform();
        } catch (Exception e) {
            log.error("发送vmiRsn异常信息失败", e);
        }
        // 发送tfx异常信息
        try {
            vmiDefaultManager.vmiTfxErrorEmailInform();
        } catch (Exception e) {
            log.error("发送vmiTfx异常信息失败", e);
        }
        // 发送adj异常信息
        try {
            vmiDefaultManager.vmiAdjErrorEmailInform();
        } catch (Exception e) {
            log.error("发送vmiAdj异常信息失败", e);
        }
        // 发送rtw异常信息
        try {
            vmiDefaultManager.vmiRtwErrorEmailInform();
        } catch (Exception e) {
            log.error("发送vmiRtw异常信息失败", e);
        }
        log.debug("==============================VmiDefaultTask vmiErrorEmailInform end");
    }

    /**
     * 星巴克出库/退换货入信息同步给HUB
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void vmiInOutBoundForStarbucks() {
        log.debug("==============================VmiDefaultTask vmiInOutBoundForStarbucks start");
        // 出库信息
        try {
            vmiDefaultManager.uploadVmiOutBoundToHub("starbucks");
        } catch (Exception e) {
            log.error("", e);
        }
        // 退换货入信息
        try {
            vmiDefaultManager.uploadVmiInBoundToHub("starbucks");
        } catch (Exception e) {
            log.error("", e);
        }
        log.debug("==============================VmiDefaultTask vmiInOutBoundForStarbucks end");
    }

    /**
     * 强生非销售出库数据和非退换货入和非vmi入库数据推送给HUB
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void vmiInboundAndOutboundJohnson() {
        log.error("==============VmiDefaultTask  vmiInboundAndOutboundJohnson start");
        try {
            // 非销售出库数据
            vmiDefaultManager.pushNotSalesOutboundDataToHub("johnson");
        } catch (Exception e) {
            log.error("", e);
        }
        // 非退换货入及vmi入库数据
        try {
            vmiDefaultManager.pushNotSalesInboundDataToHub("johnson");
        } catch (Exception e) {
            log.error("", e);
        }
        log.error("==============================VmiDefaultTask vmiInboundAndOutboundJohnson end");
    }

    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void summaryInventoryToEmail() {
        vmiDefaultManager.summaryInventoryToEmail();
    }
}
