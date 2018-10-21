package com.jumbo.wms.model.pda;

import com.jumbo.wms.model.BaseModel;

public class PdaStockTransApplicationCommand extends BaseModel {
    /**
	 * 
	 */
    private static final long serialVersionUID = -7216961932881639378L;
    private String code;
    private String slipCode;
    private String uniqCode;
    private String skuCode;
    private String skuName;
    private String supplierCode;
    private String keyProperty;
    private String color;
    private Boolean isSn;
    private String isDate;
    private String dsize;
    private Long lineId;
    private String barCode;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSlipCode() {
        return slipCode;
    }

    public void setSlipCode(String slipCode) {
        this.slipCode = slipCode;
    }

    public String getUniqCode() {
        return uniqCode;
    }

    public void setUniqCode(String uniqCode) {
        this.uniqCode = uniqCode;
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

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getKeyProperty() {
        return keyProperty;
    }

    public void setKeyProperty(String keyProperty) {
        this.keyProperty = keyProperty;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Boolean getIsSn() {
        return isSn;
    }

    public void setIsSn(Boolean isSn) {
        this.isSn = isSn;
    }

    public String getDsize() {
        return dsize;
    }

    public void setDsize(String dsize) {
        this.dsize = dsize;
    }

    public String getIsDate() {
        return isDate;
    }

    public void setIsDate(String isDate) {
        this.isDate = isDate;
    }

    public Long getLineId() {
        return lineId;
    }

    public void setLineId(Long lineId) {
        this.lineId = lineId;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }
}
