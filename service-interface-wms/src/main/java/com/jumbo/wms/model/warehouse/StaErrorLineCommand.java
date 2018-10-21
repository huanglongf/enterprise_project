package com.jumbo.wms.model.warehouse;


public class StaErrorLineCommand extends StaErrorLine {

    /**
     * 
     */
    private static final long serialVersionUID = 8358941271506050851L;

    /**
     * sta订单号
     */
    private String code;
    /**
     * 平台订单号
     */
    private String slipCode2;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 商品编码
     */
    private String skuCode;
    /**
     * 商品名称
     */
    private String skuName;
    /**
     * 商品条码
     */
    private String barCode;
    /**
     * 货号
     */
    private String supplierCode;
    /**
     * 关键属性
     */
    private String keyProperties;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSlipCode2() {
        return slipCode2;
    }

    public void setSlipCode2(String slipCode2) {
        this.slipCode2 = slipCode2;
    }



    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getKeyProperties() {
        return keyProperties;
    }

    public void setKeyProperties(String keyProperties) {
        this.keyProperties = keyProperties;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }



}
