package com.jumbo.wms.model.warehouse;

/**
 * 库存盘点明细
 * 
 * @author sjk
 * 
 */

public class InventoryCheckDifferenceLineCommand extends InventoryCheckDifferenceLine {
	

    private static final long serialVersionUID = 8801774383296910424L;

    private String ouCode;
    
    private Long locationId;
    /**
     * 库位编码
     */
    private String locationCode;
    /**
     * 库区编码
     */
    private String districtCode;
    /**
     * 库区名称
     */
    private String districtName;
    /**
     * 库存状态
     */
    private String invStatusName;
    /**
     * 盘点结果
     */
    private Long checkedQty;
    /**
     * 差异量
     */
    private Long difQty;
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
    private String barcode;
    /**
     * 淘宝对接编码
     */
    private String extensionCode2;
    /**
     * 仓库组织ID
     */
    private Long ouId;
    /**
     * 店铺组织ID
     */
    private Long shopOuId;
    /**
     * 库存状态ID
     */
    private Long invStatusId;
    
    private Long skuId;

    private Long originalQuantity;

    private String sexpireDate;

    public String getExtensionCode2() {
        return extensionCode2;
    }

    public void setExtensionCode2(String extensionCode2) {
        this.extensionCode2 = extensionCode2;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getInvStatusName() {
        return invStatusName;
    }

    public void setInvStatusName(String invStatusName) {
        this.invStatusName = invStatusName;
    }

    public Long getCheckedQty() {
        return checkedQty;
    }

    public void setCheckedQty(Long checkedQty) {
        this.checkedQty = checkedQty;
    }

    public Long getDifQty() {
        return difQty;
    }

    public void setDifQty(Long difQty) {
        this.difQty = difQty;
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

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Long getOuId() {
        return ouId;
    }

    public void setOuId(Long ouId) {
        this.ouId = ouId;
    }

    public Long getShopOuId() {
        return shopOuId;
    }

    public void setShopOuId(Long shopOuId) {
        this.shopOuId = shopOuId;
    }

    public Long getInvStatusId() {
        return invStatusId;
    }

    public void setInvStatusId(Long invStatusId) {
        this.invStatusId = invStatusId;
    }

    public String getOuCode() {
        return ouCode;
    }

    public void setOuCode(String ouCode) {
        this.ouCode = ouCode;
    }

    public String getSexpireDate() {
        return sexpireDate;
    }

    public void setSexpireDate(String sexpireDate) {
        this.sexpireDate = sexpireDate;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Long getOriginalQuantity() {
        return originalQuantity;
    }

    public void setOriginalQuantity(Long originalQuantity) {
        this.originalQuantity = originalQuantity;
    }

}
