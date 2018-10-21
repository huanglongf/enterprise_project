package com.jumbo.wms.manager.oms;

import java.util.List;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.warehouse.Inventory;
import com.jumbo.wms.model.warehouse.InventoryCommand;

/**
 * 库存查询接口
 * 
 * @author CSH5574
 * 
 */
public interface Wms2TomsService extends BaseManager {
    
    /**
     * 
     * @param skuCode 商品编码
     * @param shop  店铺code
     * @return
     */
    public List<InventoryCommand> getskuInventory(String skuCode,String shop);
}
