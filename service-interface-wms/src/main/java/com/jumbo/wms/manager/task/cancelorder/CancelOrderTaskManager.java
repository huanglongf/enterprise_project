package com.jumbo.wms.manager.task.cancelorder;

import com.jumbo.wms.manager.BaseManager;

/**
 * 
 * @author jinlong.ke
 * 
 */
public interface CancelOrderTaskManager extends BaseManager {
    /**
     * 根据ID 释放库存占用，取消单子
     * 
     * @param id
     */
    void cancelOrderById(Long id);

}
