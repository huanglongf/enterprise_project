package com.bt.radar.model;

import java.math.BigDecimal;
import java.util.Date;

public class TimelinessDetail {
	private String id;
	private Date create_time;
	private String create_user;
	private Date update_time;
	private String update_user;
	private int number;
	private String province_code;
	private String city_code;
	private String state_code;
	private String street_code;
	private BigDecimal efficiency;
	private String efficiency_unit;
	private String warningtype_code;
	private String valibity;
	private String pid;
	private String dl_flag;
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
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getProvince_code() {
		return province_code;
	}
	public void setProvince_code(String province_code) {
		this.province_code = province_code;
	}
	public String getCity_code() {
		return city_code;
	}
	public void setCity_code(String city_code) {
		this.city_code = city_code;
	}
	public String getState_code() {
		return state_code;
	}
	public void setState_code(String state_code) {
		this.state_code = state_code;
	}
	public String getStreet_code() {
		return street_code;
	}
	public void setStreet_code(String street_code) {
		this.street_code = street_code;
	}
	public BigDecimal getEfficiency() {
		return efficiency;
	}
	public void setEfficiency(BigDecimal efficiency) {
		this.efficiency = efficiency;
	}
	public String getEfficiency_unit() {
		return efficiency_unit;
	}
	public void setEfficiency_unit(String efficiency_unit) {
		this.efficiency_unit = efficiency_unit;
	}
	public String getWarningtype_code() {
		return warningtype_code;
	}
	public void setWarningtype_code(String warningtype_code) {
		this.warningtype_code = warningtype_code;
	}
	public String getValibity() {
		return valibity;
	}
	public void setValibity(String valibity) {
		this.valibity = valibity;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getDl_flag() {
		return dl_flag;
	}
	public void setDl_flag(String dl_flag) {
		this.dl_flag = dl_flag;
	}
}
