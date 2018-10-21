package com.jumbo.pms.model;

import java.io.Serializable;

import javax.persistence.Column;

/**
 * 包裹明细
 * 
 * @author ShengGe
 * 
 */
public class ParcelLineBill implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7188294632388325812L;

	/**
     * SKU编码
     */
    private String skuCode;
    
    /**
     * 条形码
     */
    private String barCode;

    /**
     * 商品名称
     */
    private String skuName;
    
    /**
     * 购买数量
     */
    private Long quantity;
    

	@Column(name = "SKU_CODE")
	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	@Column(name = "BAR_CODE")
	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	@Column(name = "SKU_NAME")
	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	@Column(name = "QUANTITY")
	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

}
