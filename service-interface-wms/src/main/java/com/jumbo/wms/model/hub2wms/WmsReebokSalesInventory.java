package com.jumbo.wms.model.hub2wms;

import java.util.Date;

import com.jumbo.wms.model.BaseModel;

public class WmsReebokSalesInventory extends BaseModel {


    private static final long serialVersionUID = -1694042340236101592L;

    private String inventoryProperty;// 库存属性
    private String skuBarCode;// SKU_BARCODE
    private Long quantity;// 库存数量
    private Date inventoryTime;// 库存时间
    private String whCode;// 库存编码
    private Date createTime;// 创建时间

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

    public String getInventoryProperty() {
        return inventoryProperty;
    }

    public void setInventoryProperty(String inventoryProperty) {
        this.inventoryProperty = inventoryProperty;
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

    public Date getInventoryTime() {
        return inventoryTime;
    }

    public void setInventoryTime(Date inventoryTime) {
        this.inventoryTime = inventoryTime;
    }

}
