package com.jumbo.wms.model.hub2wms.threepl;

import java.io.Serializable;

public class WmsStockInCaseItem implements Serializable {

    private static final long serialVersionUID = -6598926268560543554L;

    /**
     * 商品ID
     */
    private String itemId;
    /**
     * 商品名称
     */
    private String itemName;
    /**
     * 商家对商品的编码
     */
    private String itemCode;
    /**
     * 条形码，多条码请用”;”分隔；仓库入库扫码使用
     */
    private String barCode;
    /**
     * 库存类型（1 可销售库存(正品) 101 残次 102 机损 103 箱损301 在途库存 201 冻结库存） 注意：采购入库单下发的库存类型是301
     */
    private Integer inventoryType;
    /**
     * 商品数量
     */
    private Integer itemQuantity;
    /**
     * 商品版本
     */
    private Integer itemVersion;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public Integer getInventoryType() {
        return inventoryType;
    }

    public void setInventoryType(Integer inventoryType) {
        this.inventoryType = inventoryType;
    }

    public Integer getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(Integer itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public Integer getItemVersion() {
        return itemVersion;
    }

    public void setItemVersion(Integer itemVersion) {
        this.itemVersion = itemVersion;
    }

}
