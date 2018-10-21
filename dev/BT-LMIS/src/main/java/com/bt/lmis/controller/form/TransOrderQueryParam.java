package com.bt.lmis.controller.form;
import com.bt.lmis.page.QueryParameter;

public class TransOrderQueryParam extends QueryParameter {
	private String storeName;
	private String transportName;
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
