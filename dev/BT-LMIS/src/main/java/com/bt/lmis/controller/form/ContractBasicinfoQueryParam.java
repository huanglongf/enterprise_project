package com.bt.lmis.controller.form;

import com.bt.lmis.page.QueryParameter;

/** 
* @ClassName: ContractBasicinfoQueryParam 
* @Description: TODO(合同分页查询条件) 
* @author Yuriy.Jiang
* @date 2016年6月20日 下午3:00:08 
*  
*/
public class ContractBasicinfoQueryParam extends QueryParameter{
	public String id;
	public String create_time;
	public String create_user;
	public String update_time;
	public String update_user;
	public String contract_no;
	public String contract_name;
	public String contract_type;
	public String contract_owner;
	public String contract_version;
	public String contract_start;
	public String contract_end;
	public String settle_date;
	public String settle_objtype;
	public Integer settlement_body;
	public String validity;
	public String contact;
	public String tel;
	public String mark;
	
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getCreate_user() {
		return create_user;
	}
	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public String getUpdate_user() {
		return update_user;
	}
	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
	}
	public String getContract_no() {
		return contract_no;
	}
	public void setContract_no(String contract_no) {
		this.contract_no = contract_no;
	}
	public String getContract_name() {
		return contract_name;
	}
	public void setContract_name(String contract_name) {
		this.contract_name = contract_name;
	}

	public String getContract_version() {
		return contract_version;
	}
	public void setContract_version(String contract_version) {
		this.contract_version = contract_version;
	}
	public String getContract_start() {
		return contract_start;
	}
	public void setContract_start(String contract_start) {
		this.contract_start = contract_start;
	}
	public String getContract_end() {
		return contract_end;
	}
	public void setContract_end(String contract_end) {
		this.contract_end = contract_end;
	}
	public String getContract_type() {
		return contract_type;
	}
	public void setContract_type(String contract_type) {
		this.contract_type = contract_type;
	}
	public String getContract_owner() {
		return contract_owner;
	}
	public void setContract_owner(String contract_owner) {
		this.contract_owner = contract_owner;
	}
	public String getSettle_date() {
		return settle_date;
	}
	public void setSettle_date(String settle_date) {
		this.settle_date = settle_date;
	}
	public String getSettle_objtype() {
		return settle_objtype;
	}
	public void setSettle_objtype(String settle_objtype) {
		this.settle_objtype = settle_objtype;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getValidity() {
		return validity;
	}
	public void setValidity(String validity) {
		this.validity = validity;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public Integer getSettlement_body() {
		return settlement_body;
	}
	public void setSettlement_body(Integer settlement_body) {
		this.settlement_body = settlement_body;
	}

	
}
