package com.bt.orderPlatform.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
public class SfexpressServiceResponseErrorBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@XmlAttribute(name = "code")
	private String code;
	@XmlValue
	private String value;
	
	
	
	
	public String getCode() {
		return code;
	}




	public void setCode(String code) {
		this.code = code;
	}




	public String getValue() {
		return value;
	}




	public void setValue(String value) {
		this.value = value;
	}


	public String toString(){
		return "{code = \"" + code + "\",value = \"" + value +"\"}";
	}
	
	
	
	
	
}
