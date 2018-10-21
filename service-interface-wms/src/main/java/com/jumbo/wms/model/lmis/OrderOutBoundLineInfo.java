package com.jumbo.wms.model.lmis;

/**
 * 出库单明细
 * 
 * @author jinggang.chen
 * 
 */
public class OrderOutBoundLineInfo {

    private String barCode;// Y 条形码
    private String skuCode;// Y sku编码
    private String qty;// Y 计划执行量
    private String unitPrice;// Y 单价
    private String skuColor; // N 颜色
    private String skuSize;// N 尺码
    private String skuName;// Y 商品名称
    private String supplierSkuCode;// Y 货号


    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getSkuColor() {
        return skuColor;
    }

    public void setSkuColor(String skuColor) {
        this.skuColor = skuColor;
    }

    public String getSkuSize() {
        return skuSize;
    }

    public void setSkuSize(String skuSize) {
        this.skuSize = skuSize;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getSupplierSkuCode() {
        return supplierSkuCode;
    }

    public void setSupplierSkuCode(String supplierSkuCode) {
        this.supplierSkuCode = supplierSkuCode;
    }



}
