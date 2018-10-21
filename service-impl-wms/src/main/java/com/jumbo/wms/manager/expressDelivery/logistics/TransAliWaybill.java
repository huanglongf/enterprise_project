package com.jumbo.wms.manager.expressDelivery.logistics;

import cn.baozun.model.top.TopWlbWaybillIGetResponse;

import com.jumbo.wms.model.warehouse.StaDeliveryInfo;

public interface TransAliWaybill {

    /**
     * 根据作业单ID获取运单号
     * 
     * @param staId
     * @return
     */
    public TopWlbWaybillIGetResponse waybillGetByStaId(Long staId, String packageNo);

    /**
     * 根据包裹ID获取包裹上的运单号
     * 
     * @param packId
     * @return
     */
    public StaDeliveryInfo waybillGetByPackage(Long packId);

    /**
     * 跟staId获取运单号
     * 
     * @param staId
     * @return
     */
    public StaDeliveryInfo getTransNoByStaId(Long staId);

    /**
     * 取消快递单号
     * 
     * @param staId
     */
    public void cancelTransNoByStaId(Long staId);

}
