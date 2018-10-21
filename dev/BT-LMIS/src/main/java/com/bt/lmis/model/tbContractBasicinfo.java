package com.bt.lmis.model;

import java.util.Date;

public class tbContractBasicinfo {
	private int id;
	private Date create_time;
	private String create_user;
	private Date update_time;
	private String update_user;
	private String contract_no;
	private String contract_name;
	private int contract_type;
	private String contract_owner;
	private String contract_version;
	private Date contract_start;
	private Date contract_end;
	private int settle_date;
	private int validity;
	private int balanced;
	private String contact;
	private String tel;
	private int settlement_body;

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public int getContract_type() {
		return contract_type;
	}

	public void setContract_type(int contract_type) {
		this.contract_type = contract_type;
	}

	public String getContract_owner() {
		return contract_owner;
	}

	public void setContract_owner(String contract_owner) {
		this.contract_owner = contract_owner;
	}

	public String getContract_version() {
		return contract_version;
	}

	public void setContract_version(String contract_version) {
		this.contract_version = contract_version;
	}

	public Date getContract_start() {
		return contract_start;
	}

	public void setContract_start(Date contract_start) {
		this.contract_start = contract_start;
	}

	public Date getContract_end() {
		return contract_end;
	}

	public void setContract_end(Date contract_end) {
		this.contract_end = contract_end;
	}

	public int getSettle_date() {
		return settle_date;
	}

	public void setSettle_date(int settle_date) {
		this.settle_date = settle_date;
	}

	public int getValidity() {
		return validity;
	}

	public void setValidity(int validity) {
		this.validity = validity;
	}

	public int getBalanced() {
		return balanced;
	}

	public void setBalanced(int balanced) {
		this.balanced = balanced;
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

	public int getSettlement_body() {
		return settlement_body;
	}

	public void setSettlement_body(int settlement_body) {
		this.settlement_body = settlement_body;
	}

}
