package com.jumbo.wms.daemon;

import com.jumbo.wms.manager.BaseManager;

/**
 * 适配器定时任务
 * 
 */

public interface AdapterTask extends BaseManager {

    /**
     * 更新创单和出库反馈推送标识
     */
    public void updateCreateOrderAndOutboundFlag();


}
