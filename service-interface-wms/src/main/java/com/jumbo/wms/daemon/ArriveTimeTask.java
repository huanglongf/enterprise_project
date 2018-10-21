package com.jumbo.wms.daemon;

public interface ArriveTimeTask {
    /**
     * 解锁
     */
    public void deblocking();

    /**
     * 邮件通知解锁失败的订单
     */
    public void errorEmail();

}
