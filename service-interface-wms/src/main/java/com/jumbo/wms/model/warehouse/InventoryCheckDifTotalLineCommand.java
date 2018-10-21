package com.jumbo.wms.model.warehouse;


/**
 * 库存盘点差异汇总
 * 
 * @author yushanshan
 * 
 */

public class InventoryCheckDifTotalLineCommand extends InventoryCheckDifTotalLine {

    /**
	 * 
	 */
    private static final long serialVersionUID = -7737040169080034422L;

    private String skuCode;

    private String skuBarCode;

    private String skuName;

    // private Long quantity;

    /**
     * 库位
     */
    private String locationCode;

    private String inventoryStatusName;

    private String poductionDate;

    private String sexpireDate;


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

    // public Long getQuantity() {
    // return quantity;
    // }
    //
    // public void setQuantity(Long quantity) {
    // this.quantity = quantity;
    // }

    public String getSkuBarCode() {
        return skuBarCode;
    }

    public void setSkuBarCode(String skuBarCode) {
        this.skuBarCode = skuBarCode;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getInventoryStatusName() {
        return inventoryStatusName;
    }

    public void setInventoryStatusName(String inventoryStatusName) {
        this.inventoryStatusName = inventoryStatusName;
    }

    public String getPoductionDate() {
        return poductionDate;
    }

    public void setPoductionDate(String poductionDate) {
        this.poductionDate = poductionDate;
    }

    public String getSexpireDate() {
        return sexpireDate;
    }

    public void setSexpireDate(String sexpireDate) {
        this.sexpireDate = sexpireDate;
    }

}
