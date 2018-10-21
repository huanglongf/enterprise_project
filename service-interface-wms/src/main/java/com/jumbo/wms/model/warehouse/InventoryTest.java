package com.jumbo.wms.model.warehouse;

import com.jumbo.wms.model.BaseModel;

public class InventoryTest extends BaseModel implements Comparable<InventoryTest> {

    /**
     * 
     */
    private static final long serialVersionUID = 1845225242867376311L;

    
    private String innerShopCode;

    private String warehouseCode;

    private String skuCode;

    private String partion;

    private Long qty;

    private int warehouseStatus;

    private Long warehouseLocationId;

    public Long getWarehouseLocationId() {
        return warehouseLocationId;
    }

    public void setWarehouseLocationId(Long warehouseLocationId) {
        this.warehouseLocationId = warehouseLocationId;
    }

    public String getInnerShopCode() {
        return innerShopCode;
    }

    public void setInnerShopCode(String innerShopCode) {
        this.innerShopCode = innerShopCode;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getPartion() {
        return partion;
    }

    public void setPartion(String partion) {
        this.partion = partion;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public int getWarehouseStatus() {
        return warehouseStatus;
    }

    public void setWarehouseStatus(int warehouseStatus) {
        this.warehouseStatus = warehouseStatus;
    }

    @Override
    public int compareTo(InventoryTest o) {
        if (this.warehouseCode.compareTo(o.warehouseCode) == 0) {
            return this.partion.compareTo(o.partion);
        } else {
            return this.warehouseCode.compareTo(o.warehouseCode);
        }
    }

}
