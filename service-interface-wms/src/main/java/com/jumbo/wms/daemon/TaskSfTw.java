package com.jumbo.wms.daemon;

/***
 * 
 * @author lichuan
 * 
 */
public interface TaskSfTw {
    /**
     * 同步商品给台湾顺丰 void
     * 
     * @throws
     */
    public void sendSkuService();

    /**
     * 入库通知台湾顺丰 void
     * 
     * @throws
     */
    public void sendInboundOrderService();

    /**
     * 出库通知台湾顺丰 void
     * 
     * @throws
     */
    public void sendOutboundOrderService();

    /**
     * 台湾顺丰入库反馈 void
     * 
     * @throws
     */
    public void inboundOrderRtnService();

    /**
     * 台湾顺丰出库反馈 void
     * 
     * @throws
     */
    public void outboundOrderRtnService();
}
