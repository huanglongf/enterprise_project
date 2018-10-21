package com.jumbo.wms.manager.vmi.warehouse;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.webservice.ids.manager.IdsManagerProxy;
import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.model.vmi.ids.OrderConfirm.ConfirmResult;
import com.jumbo.wms.model.vmi.warehouse.MsgInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCancel;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCancelStatus;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.WarehouseLocation;

public class VmiWarehouseNBJ01 extends AbstractVmiWarehouse {

    /**
     * 
     */
    private static final long serialVersionUID = -3330829912481219232L;
    @Autowired
    private IdsManagerProxy idsManagerProxy;

    @Override
    public boolean cancelSalesOutboundSta(String staCode, MsgOutboundOrderCancel msg) {
        log.debug("LF CaneleOrderToLFTask1..." + staCode);
        boolean isCancle = false;
        try {
            log.debug("LF CaneleOrderToLFTask..." + Constants.VIM_WH_SOURCE_NBJ01UA_IDS);
            // 调用LF接口，获取取消结果
            ConfirmResult result = idsManagerProxy.siginOrderCancelResponseToLF(msg, Constants.VIM_WH_SOURCE_NBJ01UA_IDS);
            if (result != null) {
                if (result.getSuccess().equals("true")) {
                    isCancle = true;
                } else if (result.getSuccess().equals("false")) {
                    if (result.getReason().equals("B11")) {
                        isCancle = true;
                    } else {
                        isCancle = false;
                    }
                }
                if (isCancle) {
                    msg.setStatus(MsgOutboundOrderCancelStatus.FINISHED);
                    msg.setIsCanceled(true);
                    msg.setMsg(result.getDescription());
                    msg.setUpdateTime(new Date());
                    msg.setMsg("WMS推送LF实时取消");
                } else {
                    throw new BusinessException(ErrorCode.STA_CANCELED_ERROR_VMI_WH);
                }
            } else {
                throw new BusinessException(ErrorCode.STA_CANCELED_ERROR_VMI_WH);
            }
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.STA_CANCELED_ERROR_VMI_WH);
        }
        return isCancle;
    }

    @Override
    public void inboundNotice(MsgInboundOrder msgInorder) {

    }

    @Override
    public void inboundReturnRequestAnsToWms(MsgInboundOrder msg) {

    }

    @Override
    public boolean cancelReturnRequest(Long msgLog) {


        return false;
    }

    @Override
    public WarehouseLocation findLocByInvStatus(InventoryStatus invStatus) {
        return null;
    }



}
