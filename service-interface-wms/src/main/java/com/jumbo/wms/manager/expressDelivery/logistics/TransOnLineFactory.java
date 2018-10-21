package com.jumbo.wms.manager.expressDelivery.logistics;

public interface TransOnLineFactory {
    /**
     * 获取物流模型
     * 
     * @param lpcode
     * @param whOuId
     * @return
     */
    TransOlInterface getTransOnLine(String lpcode, Long whOuId);
}
