package com.jumbo.wms.model.warehouse;

import java.util.Date;

public class PdaPostLogCommand extends PdaPostLog {

    private static final long serialVersionUID = 5728988510105094898L;

    private String skuName;
    private String skuBarcode;
    private String skuCode;
    private String skukeyProperties;
    private String skuSupplierCode;
    private String locCode;

    private String invStatusName;
    private Date fromDate;
    private Date toDate;

    private String fromDateStr;
    private String toDateStr;
    private Long num;

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getSkuBarcode() {
        return skuBarcode;
    }

    public void setSkuBarcode(String skuBarcode) {
        this.skuBarcode = skuBarcode;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getSkuSupplierCode() {
        return skuSupplierCode;
    }

    public void setSkuSupplierCode(String skuSupplierCode) {
        this.skuSupplierCode = skuSupplierCode;
    }

    public String getLocCode() {
        return locCode;
    }

    public void setLocCode(String locCode) {
        this.locCode = locCode;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public String getSkukeyProperties() {
        return skukeyProperties;
    }

    public void setSkukeyProperties(String skukeyProperties) {
        this.skukeyProperties = skukeyProperties;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getInvStatusName() {
        return invStatusName;
    }

    public void setInvStatusName(String invStatusName) {
        this.invStatusName = invStatusName;
    }

    public String getFromDateStr() {
        return fromDateStr;
    }

    public void setFromDateStr(String fromDateStr) {
        this.fromDateStr = fromDateStr;
    }

    public String getToDateStr() {
        return toDateStr;
    }

    public void setToDateStr(String toDateStr) {
        this.toDateStr = toDateStr;
    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

}
