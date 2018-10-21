package com.jumbo.wms.manager.expressDelivery;

import java.util.List;

public interface TransOlManagerProxy {
    /**
     * 匹配快递运单号
     * 
     * @param plId
     */
    void matchingTransNo(Long plId, Long locId);

    /**
     * O2O+QS，以箱为基准获取运单号
     */
    void matchingTransNoByPackage(Long plId);

    /**
     * 发票订单批次获取运单号
     */
    void matchingTransNoByInvoiceOrder(List<Long> wioId);
}
