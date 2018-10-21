package com.jumbo.wms.daemon;

public interface RtnOrderTask {
    /**
     * 创建退换货单据
     */
    public void createRtnOrder();
    
    public void createRtnOrderBatchCode(String batchCode);

}
