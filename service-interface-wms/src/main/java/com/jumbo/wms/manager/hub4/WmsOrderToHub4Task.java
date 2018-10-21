package com.jumbo.wms.manager.hub4;

import com.jumbo.wms.manager.BaseManager;

/**
 * 
 * @author HUB4.0 标准对接定时任务
 * 
 */
public interface WmsOrderToHub4Task extends BaseManager {

    void sendResponseInfoToHub4();
    
    /**
     * 平安投保信信息发送hub
     */
    void sendPinganConverToHub4();

    /**
     * 推送前海出入库单
     */
    void sendQianHaiOrderToHub4();

    /**
     * 推送前海商品报关
     */
    void sendQianHaiSkuInfoToHub4();
}
