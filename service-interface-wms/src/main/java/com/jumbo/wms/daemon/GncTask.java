package com.jumbo.wms.daemon;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.warehouse.HubSku;

/**
 * Gnc定时任务 通用 hub
 * 
 * @author lzb
 * 
 */

public interface GncTask extends BaseManager {

    /*
     * 创建商品SKU 通用
     */
    public void createHubSku(String brand);


    /**
     * 全量可销售库存
     */
    public void salesInventoryGnc(String brand);

    /**
     * 增量库存
     */
    public void salesInventoryGncAdd();


    Sku updateSku(HubSku hubSku, String brand2);



}
