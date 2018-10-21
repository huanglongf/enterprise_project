package com.jumbo.webservice.kerry;

public class KerryOrderCancelItem {
    
    private String orderNo;    // 客户订单号
    private String deliveryNo; // 运单号
    private String memo;       // 备注
    
    public String getOrderNo() {
        return orderNo;
    }
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    public String getDeliveryNo() {
        return deliveryNo;
    }
    public void setDeliveryNo(String deliveryNo) {
        this.deliveryNo = deliveryNo;
    }
    public String getMemo() {
        return memo;
    }
    public void setMemo(String memo) {
        this.memo = memo;
    }
}
