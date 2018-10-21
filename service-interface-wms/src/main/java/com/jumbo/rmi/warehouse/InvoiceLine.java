package com.jumbo.rmi.warehouse;

import java.io.Serializable;
import java.math.BigDecimal;

public class InvoiceLine implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7122985766873457095L;
	/**
	 * 商品
	 */
	private String iteam;
	/**
	 * 件数
	 */
	private Integer qty;
	/**
	 * 单价
	 */
	private BigDecimal unitPrice;
	/**
	 * 含总金额
	 */
	private BigDecimal amt;
	
	public void setIteam(String iteam) {
		this.iteam = iteam;
	}
	public String getIteam() {
		return iteam;
	}
	public void setQty(Integer qty) {
		this.qty = qty;
	}
	public Integer getQty() {
		return qty;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}
	public BigDecimal getAmt() {
		return amt;
	}
}

