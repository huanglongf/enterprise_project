package com.jumbo.wms.daemon;

public interface KerryOrderTask {
    
    /**
     * 订单信息回传，出库通知
     */
    public void exeKerryConfirmOrderQueue();
    
    /**
     * 取消订单回传
     */
    public void exeKerryCancelOrderQueue();
}
