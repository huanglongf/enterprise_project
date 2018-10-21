package com.bt.dataReport.bean;

import java.io.Serializable;

/**
 *@author jinggong.li
 *@date 2018年7月3日  
 */
public class DailyDeliveryDataBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//日期                               transport_time
	private String transportTime;
	//小时                               transport_hour
	private String transportHour;
	//仓库名称                        warehouse_name 
    private String warehouseName;
    //始发地(省)      start_province
	private String startProvince;
	//店铺代码                        store_code
	private String storeCode;
	//店铺名称                        store_name
	private String storeName;
	//快递代码                        transport_code
	private String transportCode; 
    //快递名称                         transport_name 
    private String transportName;
	//运单数                             order_count
	private String orderCount;
	//发货开始时间
	private String beginDateTime;
	//发货结束时间
	private String endDateTime;
	
	public String getBeginDateTime() {
		return beginDateTime;
	}
	public void setBeginDateTime(String beginDateTime) {
		this.beginDateTime = beginDateTime;
	}
	public String getEndDateTime() {
		return endDateTime;
	}
	public void setEndDateTime(String endDateTime) {
		this.endDateTime = endDateTime;
	}
	public String getTransportTime() {
		return transportTime;
	}
	public void setTransportTime(String transportTime) {
		this.transportTime = transportTime;
	}
	public String getTransportHour() {
		return transportHour;
	}
	public void setTransportHour(String transportHour) {
		this.transportHour = transportHour;
	}
	public String getStartProvince() {
		return startProvince;
	}
	public void setStartProvince(String startProvince) {
		this.startProvince = startProvince;
	}
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	public String getStoreCode() {
		return storeCode;
	}
	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getTransportCode() {
		return transportCode;
	}
	public void setTransportCode(String transportCode) {
		this.transportCode = transportCode;
	}
	public String getOrderCount() {
		return orderCount;
	}
	public void setOrderCount(String orderCount) {
		this.orderCount = orderCount;
	}
	public String getTransportName() {
		return transportName;
	}
	public void setTransportName(String transportName) {
		this.transportName = transportName;
	}

	
	
	
}
