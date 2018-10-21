package com.jumbo.wms.manager.vmi.warehouse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderDao;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.vmi.warehouse.MsgInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCancel;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.WarehouseLocation;
/**
 * 
 * @author jinlong.ke
 *
 */
@Transactional
public class VmiWarehouseGymboree extends AbstractVmiWarehouse {

    /**
     * 
     */
    private static final long serialVersionUID = 4051039184502883134L;
    @Autowired
    private MsgOutboundOrderDao msgOutboundOrderDao;

    @Override
    public boolean cancelReturnRequest(Long msgLog) {
        return true;
    }

    @Override
    public boolean cancelSalesOutboundSta(String staCode, MsgOutboundOrderCancel msg) {
        msgOutboundOrderDao.flush();
        boolean isCancle = false;
        MsgOutboundOrder order = msgOutboundOrderDao.findOutBoundByStaCode(staCode);
        // 如果未给外包仓直接标记为取消,取消单据
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
    public void inboundNotice(MsgInboundOrder msgInorder) {
        
    }

    @Override
    public void inboundReturnRequestAnsToWms(MsgInboundOrder msg) {
        
    }

}
