package com.bt.lmis.model;

import java.util.Date;

import com.bt.lmis.page.QueryParameter;

public class Group{
	private int id;
	private String group_code;
	private String group_name;
	private String remark;
	private int status;
	private Boolean dFlag;
	private String create_by;
	private Date create_time;
	private String update_by;
	private Date update_time;
	private String instruction;//说明
	private int superior;//上级
	private String if_allot;//是否分配
	private String process_control;//是否控制权限
	private String team_id;
	

	
	public String getProcess_control() {
		return process_control;
	}
	public void setProcess_control(String process_control) {
		this.process_control = process_control;
	}
	public int getSuperior() {
		return superior;
	}
	public void setSuperior(int superior) {
		this.superior = superior;
	}
	public String getIf_allot() {
		return if_allot;
	}
	public void setIf_allot(String if_allot) {
		this.if_allot = if_allot;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getGroup_code() {
		return group_code;
	}
	public void setGroup_code(String group_code) {
		this.group_code = group_code;
	}
	public String getGroup_name() {
		return group_name;
	}
	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	public Boolean getdFlag() {
		return dFlag;
	}
	public void setdFlag(Boolean dFlag) {
		this.dFlag = dFlag;
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
	
	public String getInstruction() {
		return instruction;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}
	public String getTeam_id() {
		return team_id;
	}
	public void setTeam_id(String team_id) {
		this.team_id = team_id;
	}
	
	
}
