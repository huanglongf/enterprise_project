package com.jumbo.wms.manager.expressDelivery.logistics;

import com.jumbo.wms.model.warehouse.PickingListPackage;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.TransOrder;
import com.jumbo.wms.model.warehouse.WmsInvoiceOrder;

public interface TransOlInterface {
    /**
     * 线下包裹匹配作业单运单号
     * 
     * @param staId
     * @return
     */
    StaDeliveryInfo matchingTransNoOffLine(TransOrder order, Long whOuId);

    /**
     * 匹配作业单运单号
     * 
     * @param staId
     * @return
     */
    StaDeliveryInfo matchingTransNo(Long staId) throws Exception;

    /**
     * 匹配作业单运单号 esprit 定制 转店退仓
     * 
     * @param staId
     * @return
     */
    StaDeliveryInfo matchingTransNoEs(Long staId) throws Exception;

    /**
     * 匹配作业单运单号
     * 
     * @param staId
     * @return
     */
    StaDeliveryInfo matchingTransNoByPackId(Long packId, String type);


    /**
     * 注册确认订单
     * 
     * @param sta
     */
    void setRegistConfirmOrder(StockTransApplication sta, String newTrackingNo);

    /**
     * 注册批量发票确认订单
     * 
     * @param sta
     */
    void setRegistConfirmInvoiceOrder(WmsInvoiceOrder wio);

    /**
     * O2O+QS匹配作业单运单号
     * 
     * @param plId
     * @return
     */
    PickingListPackage matchingTransNoByPackage(Long plId);

    /**
     * 匹配发票单运单号
     * 
     * @param staId
     * @return
     */
    WmsInvoiceOrder invoiceOrderMatchingTransNo(Long wioId);

    WmsInvoiceOrder matchingTransNoForInvoiceOrder(Long id);

}
