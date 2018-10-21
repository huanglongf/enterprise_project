package com.jumbo.wms.manager.expressDelivery.logistics;

import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StockTransApplication;

public interface TransOlInterface {
    /**
     * 匹配作业单运单号
     * 
     * @param staId
     * @return
     */
    StaDeliveryInfo matchingTransNo(Long staId);
    
    /**
     * 匹配作业单运单号
     * 
     * @param staId
     * @return
     */
    StaDeliveryInfo matchingTransNoByPackId(Long packId);
    

    /**
     * 注册确认订单
     * 
     * @param sta
     */
    void setRegistConfirmOrder(StockTransApplication sta);
}
