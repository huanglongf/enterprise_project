package com.jumbo.wms.model.hub2wms.threepl;

import java.io.Serializable;
import java.util.Date;

public class StockInItemInventory implements Serializable {

    private static final long serialVersionUID = -5768595136880741215L;
    /**
     * 库存类型（1 可销售库存(正品) 101 残次 102 机损 103 箱损201 冻结库存） 注意： 入库单确认不能回传 301 在途库存
     */
    private Integer inventoryType;
    /**
     * 数量
     */
    private Integer quantity;
    /**
     * 批次号
     */
    private String batchCode;
    /**
     * 到效日期,保质期商品信息，如果商品启用了保质期管理，需要仓库按指定保质期生产
     */
    private Date dueDate;
    /**
     * 生产日期,保质期商品信息，如果商品启用了保质期管理，需要仓库按指定保质期生产
     */
    private Date produceDate;
    /**
     * 生产编码，同一商品可能因商家不同有不同编码
     */
    private String produceCode;
    /**
     * 采购单号，是批次属性
     */
    private String purchaseOrderCode;
    /**
     * sn编码
     */
    private String snCode;

    public Integer getInventoryType() {
        return inventoryType;
    }

    public void setInventoryType(Integer inventoryType) {
        this.inventoryType = inventoryType;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getProduceDate() {
        return produceDate;
    }

    public void setProduceDate(Date produceDate) {
        this.produceDate = produceDate;
    }

    public String getProduceCode() {
        return produceCode;
    }

    public void setProduceCode(String produceCode) {
        this.produceCode = produceCode;
    }

    public String getPurchaseOrderCode() {
        return purchaseOrderCode;
    }

    public void setPurchaseOrderCode(String purchaseOrderCode) {
        this.purchaseOrderCode = purchaseOrderCode;
    }

    public String getSnCode() {
        return snCode;
    }

    public void setSnCode(String snCode) {
        this.snCode = snCode;
    }

    @Override
    public String toString() {
        return "StockInCheckItem [inventoryType=" + inventoryType + ", quantity=" + quantity + ", batchCode=" + batchCode + ", dueDate=" + dueDate + ", produceDate=" + produceDate + ", produceCode=" + produceCode + ", purchaseOrderCode="
                + purchaseOrderCode + ", snCode=" + snCode + "]";
    }
}
