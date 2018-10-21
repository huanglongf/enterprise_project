package com.bt.lmis.controller.form;
import com.bt.lmis.page.QueryParameter;

public class InvitationQueryParam extends QueryParameter {
	private String storeName;
	private String hcNo;
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getHcNo() {
		return hcNo;
	}
	public void setHcNo(String hcNo) {
		this.hcNo = hcNo;
	}
	
}
