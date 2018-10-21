package com.bt.orderPlatform.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SfexpressServiceResponseRouteBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@XmlAttribute
	private String remark;
	@XmlAttribute
	private String opcode;
	@XmlAttribute(name="accept_time")
	private String accept_time;
	@XmlAttribute(name="accept_address")
	private String accept_address;
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOpcode() {
		return opcode;
	}
	public void setOpcode(String opcode) {
		this.opcode = opcode;
	}
	public String getAccept_time() {
		return accept_time;
	}
	public void setAccept_time(String accept_time) {
		this.accept_time = accept_time;
	}
	public String getAccept_address() {
		return accept_address;
	}
	public void setAccept_address(String accept_address) {
		this.accept_address = accept_address;
	}
	
	
	
	
	
}
