package com.bt.workOrder.model;

import java.util.Date;

public class ManhoursAlter {
	private String id;
	private Date create_time;
	private String create_by;
	private Date update_time;
	private String update_by;
	private String mh_id;
	private String wo_id;
	private Boolean alter_type;
	private String alter_amount;
	private String remark;
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
	public String getCreate_by() {
		return create_by;
	}
	public void setCreate_by(String create_by) {
		this.create_by = create_by;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	public String getUpdate_by() {
		return update_by;
	}
	public void setUpdate_by(String update_by) {
		this.update_by = update_by;
	}
	public String getMh_id() {
		return mh_id;
	}
	public void setMh_id(String mh_id) {
		this.mh_id = mh_id;
	}
	public String getWo_id() {
		return wo_id;
	}
	public void setWo_id(String wo_id) {
		this.wo_id = wo_id;
	}
	public Boolean getAlter_type() {
		return alter_type;
	}
	public void setAlter_type(Boolean alter_type) {
		this.alter_type = alter_type;
	}
	public String getAlter_amount() {
		return alter_amount;
	}
	public void setAlter_amount(String alter_amount) {
		this.alter_amount = alter_amount;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
