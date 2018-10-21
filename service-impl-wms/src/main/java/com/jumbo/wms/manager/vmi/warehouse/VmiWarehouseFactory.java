package com.jumbo.wms.manager.vmi.warehouse;



public interface VmiWarehouseFactory {

    VmiWarehouseInterface getVmiWarehouse(String sourceCode);
}
