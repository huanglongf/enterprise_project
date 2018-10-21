package com.bt.wms.model;

import java.util.Date;

/** 
* @ClassName: LowerRecord 
* @Description: TODO(下架记录) 
* @author Yuriy.Jiang
* @date 2017年2月18日 下午4:31:11 
*  
*/
public class LowerRecord {
	
	private String id;
	private Date create_time;
	private String create_user;
	private String container_code;
	private String location;
	private int num;
	private String sku;
	private String bat_id;
	
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public String getCreate_user() {
		return create_user;
	}
	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}
	public String getContainer_code() {
		return container_code;
	}
	public void setContainer_code(String container_code) {
		this.container_code = container_code;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getBat_id() {
		return bat_id;
	}
	public void setBat_id(String bat_id) {
		this.bat_id = bat_id;
	}
	
}
