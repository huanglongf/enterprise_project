package com.jumbo.wms.model.hub2wms;

import java.util.Date;

import com.jumbo.wms.model.BaseModel;

public class WmsAdidasTotalInventory extends BaseModel {

    private static final long serialVersionUID = 7560661166612604106L;

    private String inventoryProperty;// 库存属性
    private String skuCode;// sku_Code
    private String skuBarCode;// SKU_BARCODE
    private Long quantity;// 库存数量
    private Date createTime;// 创建时间
    private String whCode;// 仓库编码

    public String getInventoryProperty() {
        return inventoryProperty;
    }

    public void setInventoryProperty(String inventoryProperty) {
        this.inventoryProperty = inventoryProperty;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getSkuBarCode() {
        return skuBarCode;
    }

    public void setSkuBarCode(String skuBarCode) {
        this.skuBarCode = skuBarCode;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getWhCode() {
        return whCode;
    }

    public void setWhCode(String whCode) {
        this.whCode = whCode;
    }

}
