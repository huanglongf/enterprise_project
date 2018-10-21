package com.jumbo.webservice.yto.command;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "MailNoRequest")
public class ConfirmYtoTransNoRequest {
	/**
	 * 客户代码
	 */
	@XmlElement(required = true, name = "customerCode")
	private String customerCode;

	/**
	 * 批次号
	 */
	@XmlElement(required = true, name = "sequence")
	private String sequence;

	/**
	 * 是否成功
	 */
	@XmlElement(required = false, name = "success")
	private String success;

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}
}
