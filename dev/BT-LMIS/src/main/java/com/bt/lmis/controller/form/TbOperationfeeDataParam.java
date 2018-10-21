package com.bt.lmis.controller.form;

import com.bt.lmis.page.QueryParameter;

public class TbOperationfeeDataParam extends QueryParameter {
	private int id;
	private java.util.Date create_time;
	private String create_user;
	private java.util.Date update_time;
	private String update_user;
	private String order_type;
	private java.util.Date operation_time;
	private String store_name;
	private String job_orderno;
	private String related_orderno;
	private String job_type;
	private String storaggelocation_code;
	private java.math.BigDecimal in_num;
	private java.math.BigDecimal out_num;
	private String item_number;
	private String sku_number;
	private String art_no;
	private String item_name;
	private String item_size;
	private String inventory_status;
	private String operator;
	private int store_id;
	private int settle_flag;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public java.util.Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(java.util.Date create_time) {
		this.create_time = create_time;
	}

	public String getCreate_user() {
		return create_user;
	}

	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}

	public java.util.Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(java.util.Date update_time) {
		this.update_time = update_time;
	}

	public String getUpdate_user() {
		return update_user;
	}

	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
	}

	public String getOrder_type() {
		return order_type;
	}

	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}

	public java.util.Date getOperation_time() {
		return operation_time;
	}

	public void setOperation_time(java.util.Date operation_time) {
		this.operation_time = operation_time;
	}

	public String getStore_name() {
		return store_name;
	}

	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}

	public String getJob_orderno() {
		return job_orderno;
	}

	public void setJob_orderno(String job_orderno) {
		this.job_orderno = job_orderno;
	}

	public String getRelated_orderno() {
		return related_orderno;
	}

	public void setRelated_orderno(String related_orderno) {
		this.related_orderno = related_orderno;
	}

	public String getJob_type() {
		return job_type;
	}

	public void setJob_type(String job_type) {
		this.job_type = job_type;
	}

	public String getStoraggelocation_code() {
		return storaggelocation_code;
	}

	public void setStoraggelocation_code(String storaggelocation_code) {
		this.storaggelocation_code = storaggelocation_code;
	}

	public java.math.BigDecimal getIn_num() {
		return in_num;
	}

	public void setIn_num(java.math.BigDecimal in_num) {
		this.in_num = in_num;
	}

	public java.math.BigDecimal getOut_num() {
		return out_num;
	}

	public void setOut_num(java.math.BigDecimal out_num) {
		this.out_num = out_num;
	}

	public String getItem_number() {
		return item_number;
	}

	public void setItem_number(String item_number) {
		this.item_number = item_number;
	}

	public String getSku_number() {
		return sku_number;
	}

	public void setSku_number(String sku_number) {
		this.sku_number = sku_number;
	}

	public String getArt_no() {
		return art_no;
	}

	public void setArt_no(String art_no) {
		this.art_no = art_no;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public String getItem_size() {
		return item_size;
	}

	public void setItem_size(String item_size) {
		this.item_size = item_size;
	}

	public String getInventory_status() {
		return inventory_status;
	}

	public void setInventory_status(String inventory_status) {
		this.inventory_status = inventory_status;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public int getStore_id() {
		return store_id;
	}

	public void setStore_id(int store_id) {
		this.store_id = store_id;
	}

	public int getSettle_flag() {
		return settle_flag;
	}

	public void setSettle_flag(int settle_flag) {
		this.settle_flag = settle_flag;
	}
}
