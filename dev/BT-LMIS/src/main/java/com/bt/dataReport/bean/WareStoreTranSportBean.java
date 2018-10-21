
package com.bt.dataReport.bean;

/**
 *@author jinggong.li
 *@date 2018年7月3日  
 */
public class WareStoreTranSportBean {
	//仓库名称                  warehouse_name
	private String warehouseName;
	
	//店铺名称                  store_name
	private String storeName;
	
	//快递名称                  transport_name
	private String transportName;


	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getTransportName() {
		return transportName;
	}

	public void setTransportName(String transportName) {
		this.transportName = transportName;
	}
	
	
}
