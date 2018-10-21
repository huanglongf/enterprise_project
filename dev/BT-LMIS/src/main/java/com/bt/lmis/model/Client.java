package com.bt.lmis.model;

import java.util.Date;

/**
 * @Title:Client
 * @Description: TODO  
 * @author Ian.Huang 
 * @date 2016年12月7日上午11:08:16
 */
public class Client {
	
	private Integer id;
	private Date create_time;
	private String create_user;
	private Date update_time;
	private String update_user;
	private String client_code;
	private String client_name;
	private String client_type;
	private String contact;
	private String phone;
	private String address;
	private Integer settlement_method;
	private String invoice_type;
	private String invoice_info;
	private String company;
	private String remark;
	private Boolean validity;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
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
	public String getClient_code() {
		return client_code;
	}
	public void setClient_code(String client_code) {
		this.client_code = client_code;
	}
	public String getClient_name() {
		return client_name;
	}
	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}
	public String getClient_type() {
		return client_type;
	}
	public void setClient_type(String client_type) {
		this.client_type = client_type;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getSettlement_method() {
		return settlement_method;
	}
	public void setSettlement_method(Integer settlement_method) {
		this.settlement_method = settlement_method;
	}
	public String getInvoice_type() {
		return invoice_type;
	}
	public void setInvoice_type(String invoice_type) {
		this.invoice_type = invoice_type;
	}
	public String getInvoice_info() {
		return invoice_info;
	}
	public void setInvoice_info(String invoice_info) {
		this.invoice_info = invoice_info;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Boolean getValidity() {
		return validity;
	}
	public void setValidity(Boolean validity) {
		this.validity = validity;
	}
	
}
