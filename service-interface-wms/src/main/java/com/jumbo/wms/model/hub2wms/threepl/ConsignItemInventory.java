package com.jumbo.wms.model.hub2wms.threepl;

import java.io.Serializable;
import java.util.Date;

public class ConsignItemInventory implements Serializable {

    private static final long serialVersionUID = 7364320679860435453L;

    /**
     * 库存类型：1 可销售库存 (正品)
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
     * sn编码 需要回传sn时，如果有运单号时必传，其他不涉及sn的业务忽略
     */
    private String snCode;
    /**
     * 采购单号，是批次属性
     */
    private String purchaseOrderCode;
    /**
     * 运单号
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

    public String getSnCode() {
        return snCode;
    }

    public void setSnCode(String snCode) {
        this.snCode = snCode;
    }

    public String getPurchaseOrderCode() {
        return purchaseOrderCode;
    }

    public void setPurchaseOrderCode(String purchaseOrderCode) {
        this.purchaseOrderCode = purchaseOrderCode;
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
