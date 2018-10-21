package com.bt.lmis.model;

import java.math.BigDecimal;
import java.util.Date;

public class SpecialServiceEx {

	private Integer id;
	private int con_id;
	private String belong_to;
	private Integer cod_charge_method;
	private Byte charge_min_flag;
	private BigDecimal charge_min;
	private Integer charge_min_uom;
	private BigDecimal percent;
	private Integer percent_uom;
	private Integer accurate_decimal_place;
	private BigDecimal param_1;
	private Integer param_1_uom;
	private BigDecimal charge_1;
	private Integer charge_1_uom;
	private BigDecimal delegated_pickup;
	private Integer delegated_pickup_uom;
	private Boolean dFlag;
	private String create_by;
	private Date create_time;
	private String update_by;
	private Date update_time;
	public Boolean getdFlag() {
		return dFlag;
	}
	public void setdFlag(Boolean dFlag) {
		this.dFlag = dFlag;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public int getCon_id() {
		return con_id;
	}
	public void setCon_id(int con_id) {
		this.con_id = con_id;
	}
	public String getBelong_to() {
		return belong_to;
	}
	public void setBelong_to(String belong_to) {
		this.belong_to = belong_to;
	}
	public Integer getCod_charge_method() {
		return cod_charge_method;
	}
	public void setCod_charge_method(Integer cod_charge_method) {
		this.cod_charge_method = cod_charge_method;
	}
	public Byte getCharge_min_flag() {
		return charge_min_flag;
	}
	public void setCharge_min_flag(Byte charge_min_flag) {
		this.charge_min_flag = charge_min_flag;
	}
	public BigDecimal getCharge_min() {
		return charge_min;
	}
	public void setCharge_min(BigDecimal charge_min) {
		this.charge_min = charge_min;
	}
	public Integer getCharge_min_uom() {
		return charge_min_uom;
	}
	public void setCharge_min_uom(Integer charge_min_uom) {
		this.charge_min_uom = charge_min_uom;
	}
	public BigDecimal getPercent() {
		return percent;
	}
	public void setPercent(BigDecimal percent) {
		this.percent = percent;
	}
	public Integer getPercent_uom() {
		return percent_uom;
	}
	public void setPercent_uom(Integer percent_uom) {
		this.percent_uom = percent_uom;
	}
	public Integer getAccurate_decimal_place() {
		return accurate_decimal_place;
	}
	public void setAccurate_decimal_place(Integer accurate_decimal_place) {
		this.accurate_decimal_place = accurate_decimal_place;
	}
	public BigDecimal getParam_1() {
		return param_1;
	}
	public void setParam_1(BigDecimal param_1) {
		this.param_1 = param_1;
	}
	public Integer getParam_1_uom() {
		return param_1_uom;
	}
	public void setParam_1_uom(Integer param_1_uom) {
		this.param_1_uom = param_1_uom;
	}
	public BigDecimal getCharge_1() {
		return charge_1;
	}
	public void setCharge_1(BigDecimal charge_1) {
		this.charge_1 = charge_1;
	}
	public Integer getCharge_1_uom() {
		return charge_1_uom;
	}
	public void setCharge_1_uom(Integer charge_1_uom) {
		this.charge_1_uom = charge_1_uom;
	}
	public BigDecimal getDelegated_pickup() {
		return delegated_pickup;
	}
	public void setDelegated_pickup(BigDecimal delegated_pickup) {
		this.delegated_pickup = delegated_pickup;
	}
	public Integer getDelegated_pickup_uom() {
		return delegated_pickup_uom;
	}
	public void setDelegated_pickup_uom(Integer delegated_pickup_uom) {
		this.delegated_pickup_uom = delegated_pickup_uom;
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
}
