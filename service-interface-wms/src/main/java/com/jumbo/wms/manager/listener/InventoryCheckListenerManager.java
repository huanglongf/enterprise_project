package com.jumbo.wms.manager.listener;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.warehouse.InventoryCheck;

/**
 * 库存盘点单据状态监听处理接口
 * 
 * @author jinlong.ke
 * 
 */
public interface InventoryCheckListenerManager extends BaseManager {
    /**
     * 盘点批完成处理方法
     * 
     * @param ic
     */
    void inventoryCheckFinished(InventoryCheck ic);

    void recordFinishDataToOms();

}
