package com.jumbo.pms.model.command;

import java.io.Serializable;

/**
 * 包裹明细Command
 * 
 * @author ShengGe
 * 
 */
public class ParcelInfoLineCommand implements Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
    
	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
}
