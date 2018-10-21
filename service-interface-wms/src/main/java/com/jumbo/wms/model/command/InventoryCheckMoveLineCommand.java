package com.jumbo.wms.model.command;

import com.jumbo.wms.model.warehouse.InventoryCheckMoveLine;

public class InventoryCheckMoveLineCommand extends InventoryCheckMoveLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5854988671032969268L;
	 /**
     * 库位编码
     */
    private String locationCode;
    /**
     * 库存状态
     */
    private String invStatusName;
    /**
     * 商品 jsmskucode
     */
    private String skuCode;
    /**
     * 商品名称
     */
    private String skuName;
    /**
     * 商品条形码
     */
    private String barCode;
    /**
     * 相关单据号
     */
    private String staCode;
    
	public String getBarCode() {
		return barCode;
	}
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	public String getStaCode() {
		return staCode;
	}
	public void setStaCode(String staCode) {
		this.staCode = staCode;
	}
	public String getLocationCode() {
		return locationCode;
	}
	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}
	public String getInvStatusName() {
		return invStatusName;
	}
	public void setInvStatusName(String invStatusName) {
		this.invStatusName = invStatusName;
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
    
	

}
