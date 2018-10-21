package com.jumbo.webservice.rfd;

import java.math.BigDecimal;

public class RfdCreateOrderDetails {
	
	private String productName;			// 货物名称
	private Integer count;				// 数量
	private String unit;				// 计量单位
	private BigDecimal sellPrice;		// 单价
	private String size;				// 尺寸
	private String productCode;			// 货物编码
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public BigDecimal getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(BigDecimal sellPrice) {
		this.sellPrice = sellPrice;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
}
