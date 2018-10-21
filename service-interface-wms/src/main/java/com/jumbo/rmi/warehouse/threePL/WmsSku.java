package com.jumbo.rmi.warehouse.threePL;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商品信息
 * 
 */
public class WmsSku implements Serializable {

    private static final long serialVersionUID = -7920673492439304355L;

    /**
     * 数据唯一标识
     */
    private String uuid;

    /**
     * 对接唯一编码
     */
    private String code;

    /**
     * 商品条码
     */
    private String barcode;

    /**
     * 多条码列表
     */
    private String[] barcodes;

    /**
     * 供应商编码
     */
    private String supplierCode;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品英文名称
     */
    private String enName;

    /**
     * 颜色
     */
    private String color;

    /**
     * 尺码
     */
    private String size;

    /**
     * 品牌
     */
    private String brand;

    /**
     * 长(单位MM)
     */
    private BigDecimal length;

    /**
     * 宽(单位MM)
     */
    private BigDecimal width;

    /**
     * 高(单位MM)
     */
    private BigDecimal height;

    /**
     * 是否SN号管理商品
     */
    private Boolean isSn;

    /**
     * 扩展字段，json格式
     */
    private String extMemo;
    /**
     * 接口方
     */
    private String source;

    /**
     * 仓库编码
     */
    private String whCode;
    /**
     * 商品客户编码
     */
    private String customer;
    /**
     * 是否保质期商品
     */
    private Boolean isValidityPeriod;

    /**
     * 有效期天數
     */
    private int validDate;

    /**
     * 商品分类
     */
    private String categories;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String[] getBarcodes() {
        return barcodes;
    }

    public void setBarcodes(String[] barcodes) {
        this.barcodes = barcodes;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public BigDecimal getLength() {
        return length;
    }

    public void setLength(BigDecimal length) {
        this.length = length;
    }

    public BigDecimal getWidth() {
        return width;
    }

    public void setWidth(BigDecimal width) {
        this.width = width;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public Boolean getIsSn() {
        return isSn;
    }

    public void setIsSn(Boolean isSn) {
        this.isSn = isSn;
    }

    public String getExtMemo() {
        return extMemo;
    }

    public void setExtMemo(String extMemo) {
        this.extMemo = extMemo;
    }

    public Boolean getIsValidityPeriod() {
        return isValidityPeriod;
    }

    public void setIsValidityPeriod(Boolean isValidityPeriod) {
        this.isValidityPeriod = isValidityPeriod;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getWhCode() {
        return whCode;
    }

    public void setWhCode(String whCode) {
        this.whCode = whCode;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public int getValidDate() {
        return validDate;
    }

    public void setValidDate(int validDate) {
        this.validDate = validDate;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

}
