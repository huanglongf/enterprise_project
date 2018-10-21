package com.jumbo.wms.model.jasperReport;

import java.io.Serializable;
import java.math.BigDecimal;

public class ImportHGDEntryListLinesObj implements Serializable {

    private static final long serialVersionUID = 7515611101573374856L;

    private String skuName;// 商品名称
    private String supplineCode;
    private BigDecimal qty;
    private String countryOfOrigin;// 原产地
    private BigDecimal suttleWeight;// 净重
    private BigDecimal unitPrice;// 单价

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getSupplineCode() {
        return supplineCode;
    }

    public void setSupplineCode(String supplineCode) {
        this.supplineCode = supplineCode;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public BigDecimal getSuttleWeight() {
        return suttleWeight;
    }

    public void setSuttleWeight(BigDecimal suttleWeight) {
        this.suttleWeight = suttleWeight;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

}
