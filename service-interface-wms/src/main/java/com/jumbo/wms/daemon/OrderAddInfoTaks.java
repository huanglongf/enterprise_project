package com.jumbo.wms.daemon;

import com.jumbo.wms.manager.BaseManager;

public interface OrderAddInfoTaks extends BaseManager {
    /**
     * 物流推荐
     */
    public void trasnInfo();

    /**
     * 获取物流单号
     */
    public void matchingTransNo();

}
