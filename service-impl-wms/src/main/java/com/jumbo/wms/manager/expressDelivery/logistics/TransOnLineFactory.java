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

    /**
     * 补寄发票获取物流模版
     * 
     * @param lpCode
     * @return
     */
    TransOlInterface getTransOnLineForFillInInvoice(String lpCode);

    /**
     * 获取物流模型
     * 
     * @param lpcode
     * @param whOuId
     * @return
     */
    TransOlInterface getTransOnLine1(String lpcode, Long whOuId);
}
