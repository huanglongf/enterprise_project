package com.jumbo.wms.model.jasperReport;

import java.io.Serializable;

public class InboundDetailObj implements Serializable {
	private static final long serialVersionUID = 7953153271502992812L;
	
	private Integer index;
	private String brand;
	private String code;
	private String jmCode;
	private String status;
	private String barCode;
	private String keyProperties;
	private String owner;
	private String skuCode;
	private String jmskucode;
	private String skuName;
	
	
	public String getJmskucode() {
		return jmskucode;
	}
	public void setJmskucode(String jmskucode) {
		this.jmskucode = jmskucode;
	}
	public String getJmCode() {
		return jmCode;
	}
	public void setJmCode(String jmCode) {
		this.jmCode = jmCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getBarCode() {
		return barCode;
	}
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	public String getKeyProperties() {
		return keyProperties;
	}
	public void setKeyProperties(String keyProperties) {
		this.keyProperties = keyProperties;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getSkuCode() {
		return skuCode;
	}
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
	public String getSkuName() {
		return skuName;
	}
	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	
}
