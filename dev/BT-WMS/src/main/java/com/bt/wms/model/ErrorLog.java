package com.bt.wms.model;

import java.util.Date;

public class ErrorLog {

	private String container_code;
	private String localhost_code;
	private String create_user;
	private Date create_time;
	private String sku;
	private String num;
	
	public String getContainer_code() {
		return container_code;
	}
	public void setContainer_code(String container_code) {
		this.container_code = container_code;
	}
	public String getCreate_user() {
		return create_user;
	}
	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getLocalhost_code() {
		return localhost_code;
	}
	public void setLocalhost_code(String localhost_code) {
		this.localhost_code = localhost_code;
	}
}
