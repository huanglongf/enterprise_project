package com.jumbo.wms.daemon;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.warehouse.BlSkuCommand;

/**
 * Ad定时任务
 * 
 * @author lzb
 * 
 */

public interface AdidasTask extends BaseManager {

    /*
     * ad 创建商品SKU
     */
    public void createSku();

    /**
     * 全量库存
     */
    public void totalInventoryAdidas();

    /**
     * 全量可销售库存
     */
    public void salesInventoryAdidas();

    /**
     * ad创建商品
     * 
     * @param blSkuCommand
     */
    public Sku updateSku(BlSkuCommand blSkuCommand);

    /**
     * 增量库存
     */
    public void salesIncrementalInvToAdidas();
}
