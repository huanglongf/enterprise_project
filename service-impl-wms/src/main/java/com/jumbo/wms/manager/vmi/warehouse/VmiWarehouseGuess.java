package com.jumbo.wms.manager.vmi.warehouse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.baozun.bizhub.exception.BusinessException;
import com.baozun.bizhub.manager.brand.guess.GuessCancelationManager;
import com.baozun.bizhub.model.brand.guess.GuessCancelationRequest;
import com.baozun.bizhub.model.brand.guess.GuessCancelationResponse;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.model.vmi.warehouse.MsgInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCancel;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.WarehouseLocation;

public class VmiWarehouseGuess extends AbstractVmiWarehouse {

    /**
     * 
     */
    private static final long serialVersionUID = 371917324030446264L;

    private static final Logger log = LoggerFactory.getLogger(VmiWarehouseGuess.class);

   // @Autowired
   // private GuessCancelationManager guessCancelationManager;
    

    public void inboundNotice(MsgInboundOrder msgInorder) {

    }

    public void inboundReturnRequestAnsToWms(MsgInboundOrder msg) {

    }

    public boolean cancelSalesOutboundSta(String staCode, MsgOutboundOrderCancel msg) {
        /**
         * msgOutboundOrderDao.flush(); boolean isCancle = false; MsgOutboundOrder order =
         * msgOutboundOrderDao.findOutBoundByStaCode(staCode); // 如果未给外包仓直接标记为取消,取消单据 if (order !=
         * null && order.getStatus().equals(DefaultStatus.CREATED)) {
         * msgOutboundOrderDao.updateStatusByStaCode(DefaultStatus.FINISHED.getValue(), staCode);
         * isCancle = true; msg.setStatus(MsgOutboundOrderCancelStatus.FINISHED);
         * msg.setUpdateTime(new Date()); msg.setMsg("WMS 端取消");
         * msgOutboundOrderCancelDao.save(msg); }
         **/
    /*  GuessCancelationRequest request;
        try {
            request = new GuessCancelationRequest();
            request.setBaozunOrderNumber(staCode);
            request.setRequestedBy("admin");
            //请求hub接口
            GuessCancelationResponse response = guessCancelationManager.Cancelation("GUESS-CANCLE", request);
            if (response != null) {
                if (response.getStatus().equals("Success")) {
                    return true;
                }
            } else {
                throw new BusinessException(ErrorCode.STA_CANCELED_ERROR_VMI_WH);
            }
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.STA_CANCELED_ERROR_VMI_WH);
        }*/
        return true;
    }



    @Override
    public WarehouseLocation findLocByInvStatus(InventoryStatus invStatus) {
        return null;
    }

    @Override
    public boolean cancelReturnRequest(Long msgLog) {
        return true;
    }

}
