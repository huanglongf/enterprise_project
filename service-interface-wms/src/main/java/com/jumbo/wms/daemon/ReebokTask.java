package com.jumbo.wms.daemon;

import com.jumbo.wms.manager.BaseManager;

/**
 * REEBOK定时任务
 * 
 * @author lzb
 * 
 */

public interface ReebokTask extends BaseManager {
    /**
     * 全量库存
     */
    public void totalInventoryReebok();

    /**
     * 销售库存
     */
    public void salesInventoryReebok();


}
