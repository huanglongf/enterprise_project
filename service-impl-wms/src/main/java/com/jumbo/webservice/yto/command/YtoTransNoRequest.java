package com.jumbo.webservice.yto.command;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "MailNoRequest")
public class YtoTransNoRequest {
	/**
	 * 客户代码
	 */
	@XmlElement(required = true, name = "customerCode")
	private String customerCode;

	/**
	 * 一次申请数量
	 */
	@XmlElement(required = false, name = "quantity")
	private Long quantity;

	/**
	 * 代收/非代收（可不填，不填时默认为普通面单） DZ100301：普通电子面单、DZ100302：COD电子面单
	 */
	@XmlElement(required = false, name = "materialCode")
	private String materialCode;

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

}
