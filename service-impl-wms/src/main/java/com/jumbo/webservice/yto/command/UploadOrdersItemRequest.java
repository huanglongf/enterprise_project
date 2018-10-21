package com.jumbo.webservice.yto.command;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class UploadOrdersItemRequest {
	/**
	 * 商品名称
	 */
	@XmlElement(required = true, name = "itemName")
	private String itemName;

	/**
	 * 商品数量
	 */
	@XmlElement(required = true, name = "number")
	private int number;

	/**
	 * 商品单价（两位小数）
	 */
	@XmlElement(required = true, name = "itemValue")
	private Double itemValue;

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Double getItemValue() {
		return itemValue;
	}

	public void setItemValue(Double itemValue) {
		this.itemValue = itemValue;
	}
}
