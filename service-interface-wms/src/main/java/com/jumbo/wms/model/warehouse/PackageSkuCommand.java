package com.jumbo.wms.model.warehouse;

import com.jumbo.wms.model.warehouse.PackageSku;

public class PackageSkuCommand extends PackageSku {
    /**
     * 
     */
    private static final long serialVersionUID = -8842167157209005422L;
    private String skus1BarCode;
    private String skus1SupportCode;
    private Long skus1Qty;
    private String skus2BarCode;
    private String skus2SupportCode;
    private Long skus2Qty;
    private String skus3BarCode;
    private String skus3SupportCode;
    private Long skus3Qty;
    private String skusName;
    private String skusIdAndQty; // 记录skuId和qty。 多件商品以“|”拼接
    public String getSkus1BarCode() {
        return skus1BarCode;
    }
    public void setSkus1BarCode(String skus1BarCode) {
        this.skus1BarCode = skus1BarCode;
    }
    public String getSkus1SupportCode() {
        return skus1SupportCode;
    }
    public void setSkus1SupportCode(String skus1SupportCode) {
        this.skus1SupportCode = skus1SupportCode;
    }
    public String getSkus2BarCode() {
        return skus2BarCode;
    }
    public void setSkus2BarCode(String skus2BarCode) {
        this.skus2BarCode = skus2BarCode;
    }
    public String getSkus2SupportCode() {
        return skus2SupportCode;
    }
    public void setSkus2SupportCode(String skus2SupportCode) {
        this.skus2SupportCode = skus2SupportCode;
    }
    public String getSkus3BarCode() {
        return skus3BarCode;
    }
    public void setSkus3BarCode(String skus3BarCode) {
        this.skus3BarCode = skus3BarCode;
    }
    public String getSkus3SupportCode() {
        return skus3SupportCode;
    }
    public void setSkus3SupportCode(String skus3SupportCode) {
        this.skus3SupportCode = skus3SupportCode;
    }
    public Long getSkus1Qty() {
        return skus1Qty;
    }
    public void setSkus1Qty(Long skus1Qty) {
        this.skus1Qty = skus1Qty;
    }
    public Long getSkus2Qty() {
        return skus2Qty;
    }
    public void setSkus2Qty(Long skus2Qty) {
        this.skus2Qty = skus2Qty;
    }
    public Long getSkus3Qty() {
        return skus3Qty;
    }
    public void setSkus3Qty(Long skus3Qty) {
        this.skus3Qty = skus3Qty;
    }
    public String getSkusName() {
        return skusName;
    }
    public void setSkusName(String skusName) {
        this.skusName = skusName;
    }
    public String getSkusIdAndQty() {
        return skusIdAndQty;
    }
    public void setSkusIdAndQty(String skusIdAndQty) {
        this.skusIdAndQty = skusIdAndQty;
    }
    
}
