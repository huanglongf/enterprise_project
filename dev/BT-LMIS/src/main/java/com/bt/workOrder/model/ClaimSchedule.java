package com.bt.workOrder.model;

import java.util.Date;

/**
 * @Title:ClaimSchedule
 * @Description: TODO  
 * @author Ian.Huang 
 * @date 2017年4月5日下午8:53:47
 */
public class ClaimSchedule {
	private String id;
	private String createBy;
	private Date createTime;
	private String updateBy;
	private Date updateTime;
	private String woId;
	private String woType;
	private String triggerTime;
	public ClaimSchedule() {}
	public ClaimSchedule(String id, String createBy, String woId, String woType, String triggerTime) {
		this.id = id;
		this.createBy = createBy;
		this.woId = woId;
		this.woType = woType;
		this.triggerTime = triggerTime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getWoId() {
		return woId;
	}
	public void setWoId(String woId) {
		this.woId = woId;
	}
	public String getWoType() {
		return woType;
	}
	public void setWoType(String woType) {
		this.woType = woType;
	}
	public String getTriggerTime() {
		return triggerTime;
	}
	public void setTriggerTime(String triggerTime) {
		this.triggerTime = triggerTime;
	}
	
}