package com.jumbo.wms.model.hub2wms;

import java.io.Serializable;
import java.math.BigDecimal;

public class WmsSku implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8367503894533001723L;

    private String supplierCode;// 供应商商品编码(货号)

    private String extProp2;// 性别

    private String category;// 种类

    private String extProp1;// businessSegment

    private BigDecimal listPrice;// 单价

    private BigDecimal extProp3;// 原始价

    private String code;// Sku_CD/barcode

    private String extProp4;// 技术尺码

    private String skuSize;// 尺码

    private String barcode;// 条码

    private String brandCode;// 固定值: adidas

    private String brandName;// 固定值: adidas

    private String customerCode;// 固定值: adidas

    private String type;// 1：产品product 5：sku

    // /////////////新增///////////////////////
    private String name;// 中文名称
    private String enName;// 英文名称

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
    private BigDecimal weight;


    private String isValid;// 是否效期商品 0：否 1 是

    private Integer productDate;// 保质期天数

    private String spType;// 是否是耗材 1:是 0 :不是

    private String statusCode;// 状态代码(记录标记) 新增和修改（Y）、删除（N）

    // ///

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

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public Integer getProductDate() {
        return productDate;
    }

    public void setProductDate(Integer productDate) {
        this.productDate = productDate;
    }

    public String getSpType() {
        return spType;
    }

    public void setSpType(String spType) {
        this.spType = spType;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }
    // ///

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getExtProp2() {
        return extProp2;
    }

    public void setExtProp2(String extProp2) {
        this.extProp2 = extProp2;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getExtProp1() {
        return extProp1;
    }

    public void setExtProp1(String extProp1) {
        this.extProp1 = extProp1;
    }

    public BigDecimal getListPrice() {
        return listPrice;
    }

    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
    }

    public BigDecimal getExtProp3() {
        return extProp3;
    }

    public void setExtProp3(BigDecimal extProp3) {
        this.extProp3 = extProp3;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getExtProp4() {
        return extProp4;
    }

    public void setExtProp4(String extProp4) {
        this.extProp4 = extProp4;
    }

    public String getSkuSize() {
        return skuSize;
    }

    public void setSkuSize(String skuSize) {
        this.skuSize = skuSize;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
