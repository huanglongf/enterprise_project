package com.bt.lmis.model;

import java.math.BigDecimal;
import java.util.Date;

public class BalanceSummaryEx {
	private int id;
	private int ver_id;
	private int con_id;
	private String express;
	private String balance_month;
	private String cost_center;
	private String store_code;
	private String store_name;
	private String warehouse_name;
	private int order_num;
	private String product_type_code;
	private String product_type_name;
	private BigDecimal product_type_freight;
	private BigDecimal product_type_discount;
	private BigDecimal product_type_total_discount;
	private BigDecimal insurance;
	private String default_1;
	private String default_2;
	private String default_3;
	private BigDecimal total_fee;
	private String create_by;
	private Date createi_time;
	private String update_by;
	private Date update_time;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getVer_id() {
		return ver_id;
	}
	public void setVer_id(int ver_id) {
		this.ver_id = ver_id;
	}
	public int getCon_id() {
		return con_id;
	}
	public void setCon_id(int con_id) {
		this.con_id = con_id;
	}
	public String getExpress() {
		return express;
	}
	public void setExpress(String express) {
		this.express = express;
	}
	public String getBalance_month() {
		return balance_month;
	}
	public void setBalance_month(String balance_month) {
		this.balance_month = balance_month;
	}
	public String getCost_center() {
		return cost_center;
	}
	public void setCost_center(String cost_center) {
		this.cost_center = cost_center;
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
	public String getWarehouse_name() {
		return warehouse_name;
	}
	public void setWarehouse_name(String warehouse_name) {
		this.warehouse_name = warehouse_name;
	}
	public int getOrder_num() {
		return order_num;
	}
	public void setOrder_num(int order_num) {
		this.order_num = order_num;
	}
	public String getProduct_type_code() {
		return product_type_code;
	}
	public void setProduct_type_code(String product_type_code) {
		this.product_type_code = product_type_code;
	}
	public String getProduct_type_name() {
		return product_type_name;
	}
	public void setProduct_type_name(String product_type_name) {
		this.product_type_name = product_type_name;
	}
	public BigDecimal getProduct_type_freight() {
		return product_type_freight;
	}
	public void setProduct_type_freight(BigDecimal product_type_freight) {
		this.product_type_freight = product_type_freight;
	}
	public BigDecimal getProduct_type_discount() {
		return product_type_discount;
	}
	public void setProduct_type_discount(BigDecimal product_type_discount) {
		this.product_type_discount = product_type_discount;
	}
	public BigDecimal getProduct_type_total_discount() {
		return product_type_total_discount;
	}
	public void setProduct_type_total_discount(BigDecimal product_type_total_discount) {
		this.product_type_total_discount = product_type_total_discount;
	}
	public BigDecimal getInsurance() {
		return insurance;
	}
	public void setInsurance(BigDecimal insurance) {
		this.insurance = insurance;
	}
	public String getDefault_1() {
		return default_1;
	}
	public void setDefault_1(String default_1) {
		this.default_1 = default_1;
	}
	public String getDefault_2() {
		return default_2;
	}
	public void setDefault_2(String default_2) {
		this.default_2 = default_2;
	}
	public String getDefault_3() {
		return default_3;
	}
	public void setDefault_3(String default_3) {
		this.default_3 = default_3;
	}
	public BigDecimal getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(BigDecimal total_fee) {
		this.total_fee = total_fee;
	}
	public String getCreate_by() {
		return create_by;
	}
	public void setCreate_by(String create_by) {
		this.create_by = create_by;
	}
	public Date getCreatei_time() {
		return createi_time;
	}
	public void setCreatei_time(Date createi_time) {
		this.createi_time = createi_time;
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
