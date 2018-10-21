package com.bt.radar.model;

import java.util.Date;

public class WaybillWarninginfoMaster {
	private String id;
	private Date create_time;
	private String create_user;
	private Date update_time;
	private String update_user;
	private Date warning_time;
	private String warningtype_code;
	private String warning_level;
	private String warining_status;
	private String waybill;
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
	public Date getWarning_time() {
		return warning_time;
	}
	public void setWarning_time(Date warning_time) {
		this.warning_time = warning_time;
	}
	public String getWarningtype_code() {
		return warningtype_code;
	}
	public void setWarningtype_code(String warningtype_code) {
		this.warningtype_code = warningtype_code;
	}
	public String getWarning_level() {
		return warning_level;
	}
	public void setWarning_level(String warning_level) {
		this.warning_level = warning_level;
	}
	public String getWarining_status() {
		return warining_status;
	}
	public void setWarining_status(String warining_status) {
		this.warining_status = warining_status;
	}
	public String getWaybill() {
		return waybill;
	}
	public void setWaybill(String waybill) {
		this.waybill = waybill;
	}
}
