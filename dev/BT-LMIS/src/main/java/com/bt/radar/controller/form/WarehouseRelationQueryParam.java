package com.bt.radar.controller.form;
import java.util.Date;

import com.bt.lmis.page.QueryParameter;

public class WarehouseRelationQueryParam extends QueryParameter {
	private String id;
	private Date create_time;
	private String create_user;
	private Date update_time;
	private String update_user;
	private String physical_code;
	private String logic_code;
	private int dl_flag;
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
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	public String getUpdate_user() {
		return update_user;
	}
	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
	}
	public String getPhysical_code() {
		return physical_code;
	}
	public void setPhysical_code(String physical_code) {
		this.physical_code = physical_code;
	}
	public String getLogic_code() {
		return logic_code;
	}
	public void setLogic_code(String logic_code) {
		this.logic_code = logic_code;
	}
	public int getDl_flag() {
		return dl_flag;
	}
	public void setDl_flag(int dl_flag) {
		this.dl_flag = dl_flag;
	}
}
