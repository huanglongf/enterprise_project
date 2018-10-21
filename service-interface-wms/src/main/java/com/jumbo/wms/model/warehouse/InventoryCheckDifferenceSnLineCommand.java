package com.jumbo.wms.model.warehouse;

public class InventoryCheckDifferenceSnLineCommand extends InventoryCheckDifferenceSnLine {

    /**
	 * 
	 */
    private static final long serialVersionUID = 7832086001199371178L;

    private String skuName;
    private String skuCode;
    private String skuBarcode;
    private Integer intType;

    private String sourceSkuName;
    private String sourceSkuCode;
    private String sourceSkuBarcode;

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public Integer getIntType() {
        return intType;
    }

    public void setIntType(Integer intType) {
        this.intType = intType;
    }

    public String getSkuBarcode() {
        return skuBarcode;
    }

    public void setSkuBarcode(String skuBarcode) {
        this.skuBarcode = skuBarcode;
    }

    public String getSourceSkuName() {
        return sourceSkuName;
    }

    public void setSourceSkuName(String sourceSkuName) {
        this.sourceSkuName = sourceSkuName;
    }

    public String getSourceSkuCode() {
        return sourceSkuCode;
    }

    public void setSourceSkuCode(String sourceSkuCode) {
        this.sourceSkuCode = sourceSkuCode;
    }

    public String getSourceSkuBarcode() {
        return sourceSkuBarcode;
    }

    public void setSourceSkuBarcode(String sourceSkuBarcode) {
        this.sourceSkuBarcode = sourceSkuBarcode;
    }

}
