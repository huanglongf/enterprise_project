package com.jumbo.webservice.express;

import java.io.Serializable;

public class ExpressContactInformation implements Serializable {
	
	private static final long serialVersionUID = -2673553323536238021L;
	
	private String name;		// 发件人姓名
	private String postCode;	// 邮编
	private String mobile;		// 手机
	private String phone;		// 电话
	private String province;	// 省
	private String city;		// 市
	private String district;	// 区
	private String address;		// 寄件地址
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	
}
