package com.jumbo.webservice.kerry;

public class KerryOrderCreateResponse {
    
    private String orderNo;             // 客户订单号
    private String destinationiStation; // 目标地址
    private String deliveryNo;          // 运单号
    
    public String getOrderNo() {
        return orderNo;
    }
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
	public String getDestinationiStation() {
		return destinationiStation;
	}
	public void setDestinationiStation(String destinationiStation) {
		this.destinationiStation = destinationiStation;
	}
	public String getDeliveryNo() {
		return deliveryNo;
	}
	public void setDeliveryNo(String deliveryNo) {
		this.deliveryNo = deliveryNo;
	}
}
