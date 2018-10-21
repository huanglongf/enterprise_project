package com.bt.lmis.model;

import java.util.Date;

public class CarrierFeeFlag {
	private int id;
	private String create_by;
	private Date create_time;
	private String update_by;
	private Date update_time;
	private int con_id;
	private Boolean totalFreightDiscount_flag;
	private Boolean managementFee_flag;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCreate_by() {
		return create_by;
	}
	public void setCreate_by(String create_by) {
		this.create_by = create_by;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public String getUpdate_by() {
		return update_by;
	}
	public void setUpdate_by(String update_by) {
		this.update_by = update_by;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	public int getCon_id() {
		return con_id;
	}
	public void setCon_id(int con_id) {
		this.con_id = con_id;
	}
	public Boolean getTotalFreightDiscount_flag() {
		return totalFreightDiscount_flag;
	}
	public void setTotalFreightDiscount_flag(Boolean totalFreightDiscount_flag) {
		this.totalFreightDiscount_flag = totalFreightDiscount_flag;
	}
	public Boolean getManagementFee_flag() {
		return managementFee_flag;
	}
	public void setManagementFee_flag(Boolean managementFee_flag) {
		this.managementFee_flag = managementFee_flag;
	}
}
