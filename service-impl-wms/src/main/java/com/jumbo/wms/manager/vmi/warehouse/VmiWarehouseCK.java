package com.jumbo.wms.manager.vmi.warehouse;


import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.wms.Constants;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.warehouse.WarehouseLocationDao;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.vmi.warehouse.MsgInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCancel;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.WarehouseLocation;

public class VmiWarehouseCK extends AbstractVmiWarehouse {

    /**
     * 
     */
    private static final long serialVersionUID = 1116313569301826967L;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private WarehouseLocationDao warehouseLocationDao;

    @Override
    public boolean cancelSalesOutboundSta(String staCode, MsgOutboundOrderCancel msg) {
        return true;
    }

    @Override
    public void inboundNotice(MsgInboundOrder msgInorder) {}

    @Override
    public void inboundReturnRequestAnsToWms(MsgInboundOrder msg) {}

    @Override
    public WarehouseLocation findLocByInvStatus(InventoryStatus invStatus) {
        Warehouse wh = warehouseDao.getBySource(Constants.CK_WH_SOURCE, null);
        WarehouseLocation location = warehouseLocationDao.findOneWarehouseLocationByOuid(wh.getOu().getId());
        return location;
    }

    @Override
    public boolean cancelReturnRequest(Long msgLog) {
        return true;
    }


}
