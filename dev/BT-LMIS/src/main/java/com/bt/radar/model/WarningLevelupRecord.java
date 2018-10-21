package com.bt.radar.model;

import java.util.Date;

public class WarningLevelupRecord {
	private String id;
	private Date create_time;
	private String create_user;
	private Date update_time;
	private String update_user;
	private String waybill;
	private String pid;
	private String before_level;
	private String after_level;
	private Date levelup_time;
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
	public String getWaybill() {
		return waybill;
	}
	public void setWaybill(String waybill) {
		this.waybill = waybill;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getBefore_level() {
		return before_level;
	}
	public void setBefore_level(String before_level) {
		this.before_level = before_level;
	}
	public String getAfter_level() {
		return after_level;
	}
	public void setAfter_level(String after_level) {
		this.after_level = after_level;
	}
	public Date getLevelup_time() {
		return levelup_time;
	}
	public void setLevelup_time(Date levelup_time) {
		this.levelup_time = levelup_time;
	}
}
