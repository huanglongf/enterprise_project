package com.jumbo.wms.model.jasperReport;

import java.io.Serializable;
import java.math.BigDecimal;

public class ImportEntryListLinesObj implements Serializable {

    private static final long serialVersionUID = 6745934406317717831L;

    private String skuName;

    private String color;// 颜色

    private String skuSize;// 尺码

    private BigDecimal unitPrice;// 单价

    private String upc;

    private String qty;

    private String countryOfOrigin;// 原产地

    private BigDecimal orderTransferFree;// 运费

    private int ordinal;

    private BigDecimal price;

    private String supplineCode;

    private String htsCode; // 报关编号

    private String unitName; // 单位 双，件 等

    private Integer num;

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getHtsCode() {
        return htsCode;
    }

    public void setHtsCode(String htsCode) {
        this.htsCode = htsCode;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getSupplineCode() {
        return supplineCode;
    }

    public void setSupplineCode(String supplineCode) {
        this.supplineCode = supplineCode;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

    public String getSkuSize() {
        return skuSize;
    }

    public void setSkuSize(String skuSize) {
        this.skuSize = skuSize;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    public BigDecimal getOrderTransferFree() {
        return orderTransferFree;
    }

    public void setOrderTransferFree(BigDecimal orderTransferFree) {
        this.orderTransferFree = orderTransferFree;
    }


}
