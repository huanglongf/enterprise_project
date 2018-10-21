package com.bt.lmis.model;

public class CustomserTransSettleBean {
 private String transport_name;
 private String total_price;
 private String bill_num;
 private String contract_id;
 private String create_date;
	public String getTransport_name() {
		return transport_name;
	}
	public void setTransport_name(String transport_name) {
		this.transport_name = transport_name;
	}
	public String getTotal_price() {
		return total_price;
	}
	public void setTotal_price(String total_price) {
		this.total_price = total_price;
	}
	public String getContract_id() {
		return contract_id;
	}
	public void setContract_id(String contract_id) {
		this.contract_id = contract_id;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public String getBill_num() {
		return bill_num;
	}
	public void setBill_num(String bill_num) {
		this.bill_num = bill_num;
	}
 
}
