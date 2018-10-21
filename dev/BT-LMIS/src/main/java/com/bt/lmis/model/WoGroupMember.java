package com.bt.lmis.model;

import java.io.Serializable;

public class WoGroupMember implements Serializable {

	
	/** serialVersionUID*/
	private static final long serialVersionUID = 8596960934676526642L;
	private String id;
	private String createby;
	private String updateby;
	private String remark;
	private Integer groupId; 
	private Integer employeeId;
	private String isAutoAllct;
	
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCreateby() {
		return createby;
	}
	public void setCreateby(String createby) {
		this.createby = createby;
	}
	public String getUpdateby() {
		return updateby;
	}
	public void setUpdateby(String updateby) {
		this.updateby = updateby;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	public Integer getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}
	public String getIsAutoAllct() {
		return isAutoAllct;
	}
	public void setIsAutoAllct(String isAutoAllct) {
		this.isAutoAllct = isAutoAllct;
	}
	
	
	
	
	
}
