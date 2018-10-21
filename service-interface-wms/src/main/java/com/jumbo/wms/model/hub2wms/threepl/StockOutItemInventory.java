package com.jumbo.wms.model.hub2wms.threepl;

import java.io.Serializable;
import java.util.Date;

public class StockOutItemInventory implements Serializable {

    private static final long serialVersionUID = 4706399185560122074L;
    /**
     * 库存类型（1 可销售库存(正品) 101 残次 102 机损 103 箱损201 冻结库存）
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
     * 失效日期
     */
    private Date dueDate;
    /**
     * 生产日期YYYY-MM-DD hh:mm:ss
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
    /**
     * 运单号 需要回传sn时，如果有运单号时必传，其他不涉及sn的业务忽略
     */
    private String tmsOrderCode;
    /**
     * 包裹号 需要回传sn时，如果有包裹号时必传，其他不涉及sn的业务忽略
     */
    private String packageCode;

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

    public String getTmsOrderCode() {
        return tmsOrderCode;
    }

    public void setTmsOrderCode(String tmsOrderCode) {
        this.tmsOrderCode = tmsOrderCode;
    }

    public String getPackageCode() {
        return packageCode;
    }

    public void setPackageCode(String packageCode) {
        this.packageCode = packageCode;
    }

}
