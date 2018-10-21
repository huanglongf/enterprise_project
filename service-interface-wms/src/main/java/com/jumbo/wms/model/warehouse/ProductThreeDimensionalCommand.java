package com.jumbo.wms.model.warehouse;

import java.math.BigDecimal;

import com.jumbo.wms.model.BaseModel;



public class ProductThreeDimensionalCommand extends BaseModel {


    private static final long serialVersionUID = 5249554166021873640L;

    /**
     * skuId
     */
    private Long id;
    /**
     * 供应商商品编码(货号)
     */
    private String supplierCode;
    /**
     * Sku条码，唯一标示Sku的编码
     */
    private String barCode;
    /**
     * 编码，唯一标示Sku的编码
     */
    private String code;
    /**
     * 扩展属性
     */
    private String keyProperties;
    /**
     * Sku名称，一般来说这个信息=Product的名称
     */
    private String name;
    /**
     * 客户名称
     */
    private String customerName;
    /**
     * 商品长度（mm）
     */
    private BigDecimal length;
    /**
     * 商品宽度（mm）
     */
    private BigDecimal width;
    /**
     * 商品高度（mm）
     */
    private BigDecimal height;
    /**
     * 商品毛重（kg）
     */
    private BigDecimal grossWeight;
    /**
     * 是否更新到货号 1:是 0:否
     */
    private String isSupplierCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public BigDecimal getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(BigDecimal grossWeight) {
        this.grossWeight = grossWeight;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getKeyProperties() {
        return keyProperties;
    }

    public void setKeyProperties(String keyProperties) {
        this.keyProperties = keyProperties;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getIsSupplierCode() {
        return isSupplierCode;
    }

    public void setIsSupplierCode(String isSupplierCode) {
        this.isSupplierCode = isSupplierCode;
    }

}
