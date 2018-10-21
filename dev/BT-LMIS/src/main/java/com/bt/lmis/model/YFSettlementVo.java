package com.bt.lmis.model;

/**
 * @author Will.Wang
 * @date 2017年7月26日
 */
public class YFSettlementVo {

	private String costCenter;
	private String storeName;
	private String warehouse;
	private String transportName;
	private String itemtypeName;
	private String expressNumber;
	private String epistaticOrder;
	private String deliveryAddress;
	private String province;
	private String city;
	private String state;
	private String errorType;
	private String startTime;
	private String endTime;
	private String tabMark;
	public String getCostCenter() {
		return costCenter;
	}
	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	public String getTransportName() {
		return transportName;
	}
	public void setTransportName(String transportName) {
		this.transportName = transportName;
	}
	public String getItemtypeName() {
		return itemtypeName;
	}
	public void setItemtypeName(String itemtypeName) {
		this.itemtypeName = itemtypeName;
	}
	public String getExpressNumber() {
		return expressNumber;
	}
	public void setExpressNumber(String expressNumber) {
		this.expressNumber = expressNumber;
	}
	public String getEpistaticOrder() {
		return epistaticOrder;
	}
	public void setEpistaticOrder(String epistaticOrder) {
		this.epistaticOrder = epistaticOrder;
	}
	public String getDeliveryAddress() {
		return deliveryAddress;
	}
	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getErrorType() {
		return errorType;
	}
	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getTabMark() {
		return tabMark;
	}
	public void setTabMark(String tabMark) {
		this.tabMark = tabMark;
	}
	public YFSettlementVo(String costCenter, String storeName, String warehouse, String transportName,
			String itemtypeName, String expressNumber, String epistaticOrder, String deliveryAddress, String province,
			String city, String state, String errorType, String startTime, String endTime, String tabMark) {
		super();
		this.costCenter = costCenter;
		this.storeName = storeName;
		this.warehouse = warehouse;
		this.transportName = transportName;
		this.itemtypeName = itemtypeName;
		this.expressNumber = expressNumber;
		this.epistaticOrder = epistaticOrder;
		this.deliveryAddress = deliveryAddress;
		this.province = province;
		this.city = city;
		this.state = state;
		this.errorType = errorType;
		this.startTime = startTime;
		this.endTime = endTime;
		this.tabMark = tabMark;
	}
	public YFSettlementVo() {
		super();
	}
	@Override
	public String toString() {
		return "YFSettlementVo [costCenter=" + costCenter + ", storeName=" + storeName + ", warehouse=" + warehouse
				+ ", transportName=" + transportName + ", itemtypeName=" + itemtypeName + ", expressNumber="
				+ expressNumber + ", epistaticOrder=" + epistaticOrder + ", deliveryAddress=" + deliveryAddress
				+ ", province=" + province + ", city=" + city + ", state=" + state + ", errorType=" + errorType
				+ ", startTime=" + startTime + ", endTime=" + endTime + ", tabMark=" + tabMark + "]";
	}
	
	
}
