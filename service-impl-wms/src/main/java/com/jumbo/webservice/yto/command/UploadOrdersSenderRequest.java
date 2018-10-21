package com.jumbo.webservice.yto.command;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class UploadOrdersSenderRequest {
	/**
	 * 用户姓名
	 */
	@XmlElement(required = true, name = "name")
	private String name;

	/**
	 * 用户邮编
	 */
	@XmlElement(required = true, name = "postCode")
	private String postCode;

	/**
	 * 用户电话，包括区号、电话号码及分机号，中间用“-”分隔；
	 */
	@XmlElement(required = false, name = "phone")
	private String phone;

	/**
	 * 用户移动电话
	 */
	@XmlElement(required = false, name = "mobile")
	private String mobile;

	/**
	 * 用户所在省
	 */
	@XmlElement(required = true, name = "prov")
	private String prov;

	/**
	 * 用户所在市县（区），市区中间用“,”分隔；注意有些市下面是没有区
	 */
	@XmlElement(required = true, name = "city")
	private String city;

	/**
	 * 用户详细地址
	 */
	@XmlElement(required = true, name = "address")
	private String address;

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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getProv() {
		return prov;
	}

	public void setProv(String prov) {
		this.prov = prov;
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
}
