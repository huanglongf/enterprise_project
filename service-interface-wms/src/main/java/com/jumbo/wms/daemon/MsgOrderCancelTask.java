package com.jumbo.wms.daemon;

public interface MsgOrderCancelTask {
    /**
     * 生成订单取消确认数据
     */
    public void createMsgOrder();

}
