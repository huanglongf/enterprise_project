package com.jumbo.wms.model.pda;

import java.io.Serializable;

public class InboundOnShelvesSkuCommand implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -8245165662624666219L;
	private Long stvLineId;
    private String skuCode;
    private String skuName;
    private String color;
    private String keyProp;
    private String dsize;
    private String isDateSku;
    private Boolean isSn;
    private String supplierCode;
    private String barCode;

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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getKeyProp() {
        return keyProp;
    }

    public void setKeyProp(String keyProp) {
        this.keyProp = keyProp;
    }

    public String getDsize() {
        return dsize;
    }

    public void setDsize(String dsize) {
        this.dsize = dsize;
    }

    public String getIsDateSku() {
        return isDateSku;
    }

    public void setIsDateSku(String isDateSku) {
        this.isDateSku = isDateSku;
    }

    public Boolean getIsSn() {
        return isSn;
    }

    public void setIsSn(Boolean isSn) {
        this.isSn = isSn;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public Long getStvLineId() {
        return stvLineId;
    }

    public void setStvLineId(Long stvLineId) {
        this.stvLineId = stvLineId;
    }
}
