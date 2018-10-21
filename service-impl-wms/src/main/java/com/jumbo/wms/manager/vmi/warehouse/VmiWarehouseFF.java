package com.jumbo.wms.manager.vmi.warehouse;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderCancelDao;
import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderDao;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.vmi.warehouse.MsgInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCancel;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCancelStatus;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.WarehouseLocation;

public class VmiWarehouseFF extends AbstractVmiWarehouse {

    /**
     * 
     */
    private static final long serialVersionUID = 371917324030446264L;

    @Autowired
    private MsgOutboundOrderDao msgOutboundOrderDao;
    
    @Autowired
    private MsgOutboundOrderCancelDao msgOutboundOrderCancelDao;

    public void inboundNotice(MsgInboundOrder msgInorder) {

    }

    public void inboundReturnRequestAnsToWms(MsgInboundOrder msg) {

    }

    public boolean cancelSalesOutboundSta(String staCode, MsgOutboundOrderCancel msg) {
        msgOutboundOrderDao.flush();
        boolean isCancle = false;
        MsgOutboundOrder order = msgOutboundOrderDao.findOutBoundByStaCode(staCode);
        // 如果未给外包仓直接标记为取消,取消单据
        if (order != null && order.getStatus().equals(DefaultStatus.CREATED)) {
            msgOutboundOrderDao.updateStatusByStaCode(DefaultStatus.FINISHED.getValue(), staCode);
            isCancle = true;
            msg.setStatus(MsgOutboundOrderCancelStatus.FINISHED);
            msg.setUpdateTime(new Date());
            msg.setMsg("WMS 端取消");
            msgOutboundOrderCancelDao.save(msg);
        }
        return isCancle;
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
