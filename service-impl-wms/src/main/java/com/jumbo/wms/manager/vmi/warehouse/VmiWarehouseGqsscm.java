package com.jumbo.wms.manager.vmi.warehouse;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderDao;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.vmi.warehouse.MsgInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCancel;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.WarehouseLocation;

public class VmiWarehouseGqsscm extends AbstractVmiWarehouse {

    /**
     * 
     */
    private static final long serialVersionUID = 8495288348459130823L;
    @Autowired
    private MsgOutboundOrderDao msgOutboundOrderDao;

    public void inboundNotice(MsgInboundOrder msgInorder) {

    }

    public void inboundReturnRequestAnsToWms(MsgInboundOrder msg) {

    }

    public boolean cancelSalesOutboundSta(String staCode, MsgOutboundOrderCancel msg) {
        boolean isCancle = false;
        MsgOutboundOrder order = msgOutboundOrderDao.findOutBoundByStaCode(staCode);
        if (order != null && order.getStatus().equals(DefaultStatus.CREATED)) {
            msgOutboundOrderDao.updateStatusByStaCode(DefaultStatus.FINISHED.getValue(), staCode);
            isCancle = true;
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
