package com.bt.workOrder.model;

import java.util.Date;

public class GroupWorkPower {
	private String id;
	private String create_by;
	private Date create_time;
	private String update_by;
	private Date update_time;
	private Integer group;
	private String carrier;
	private String wo_type;
	private String wo_level;
	private Boolean dFlag;
	public GroupWorkPower() {
		
	}
	public GroupWorkPower(String id, String update_by, Boolean dFlag) {
		this.id= id;
		this.update_by= update_by;
		this.dFlag= dFlag;
		
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
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
	public Integer getGroup() {
		return group;
	}
	public void setGroup(Integer group) {
		this.group = group;
	}
	public String getCarrier() {
		return carrier;
	}
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	public String getWo_type() {
		return wo_type;
	}
	public void setWo_type(String wo_type) {
		this.wo_type = wo_type;
	}
	public String getWo_level() {
		return wo_level;
	}
	public void setWo_level(String wo_level) {
		this.wo_level = wo_level;
	}
	public Boolean getdFlag() {
		return dFlag;
	}
	public void setdFlag(Boolean dFlag) {
		this.dFlag = dFlag;
	}
	
}
