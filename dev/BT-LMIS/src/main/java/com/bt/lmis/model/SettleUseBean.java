package com.bt.lmis.model;

import java.sql.Date;

public class SettleUseBean {
	private int id;
	private int bat_id;
	private String ib_time;
	private String store_code;
	private String store_name;
	private String sku_code;
	private String sku_name;
	private String use_amount;
	private String use_amountunit;
	private Date create_date;
	private String pro_flag;
	private String pro_remark;
	private String operation_time;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBat_id() {
		return bat_id;
	}

	public void setBat_id(int bat_id) {
		this.bat_id = bat_id;
	}

	public String getIb_time() {
		return ib_time;
	}

	public void setIb_time(String ib_time) {
		this.ib_time = ib_time;
	}

	public String getStore_code() {
		return store_code;
	}

	public void setStore_code(String store_code) {
		this.store_code = store_code;
	}

	public String getStore_name() {
		return store_name;
	}

	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}

	public String getSku_code() {
		return sku_code;
	}

	public void setSku_code(String sku_code) {
		this.sku_code = sku_code;
	}

	public String getSku_name() {
		return sku_name;
	}

	public void setSku_name(String sku_name) {
		this.sku_name = sku_name;
	}

	public String getUse_amount() {
		return use_amount;
	}

	public void setUse_amount(String use_amount) {
		this.use_amount = use_amount;
	}

	public String getUse_amountunit() {
		return use_amountunit;
	}

	public void setUse_amountunit(String use_amountunit) {
		this.use_amountunit = use_amountunit;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public String getPro_flag() {
		return pro_flag;
	}

	public void setPro_flag(String pro_flag) {
		this.pro_flag = pro_flag;
	}

	public String getPro_remark() {
		return pro_remark;
	}

	public void setPro_remark(String pro_remark) {
		this.pro_remark = pro_remark;
	}

	public String getOperation_time() {
		return operation_time;
	}

	public void setOperation_time(String operation_time) {
		this.operation_time = operation_time;
	}

}
