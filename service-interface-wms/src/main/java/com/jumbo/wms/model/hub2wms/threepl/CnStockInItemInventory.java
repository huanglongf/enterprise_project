package com.jumbo.wms.model.hub2wms.threepl;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OptimisticLockType;

/**
 * 菜鸟入库单确认--商品库存
 */
@Entity
@Table(name = "T_WH_CN_STOCK_IN_ITEM_INV")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class CnStockInItemInventory implements Serializable {

    private static final long serialVersionUID = -2668345813571422993L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 入库确认商品明细
     */
    private CnStockInOrderLine stockInOrderLine;
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

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_WCSI", sequenceName = "S_T_WH_CN_STOCK_IN_ITEM_INV", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WCSI")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STOCK_IN_CONFIRM_LINE_ID")
    public CnStockInOrderLine getStockInOrderLine() {
        return stockInOrderLine;
    }

    public void setStockInOrderLine(CnStockInOrderLine stockInOrderLine) {
        this.stockInOrderLine = stockInOrderLine;
    }

    @Column(name = "INVENTORY_TYPE")
    public Integer getInventoryType() {
        return inventoryType;
    }

    public void setInventoryType(Integer inventoryType) {
        this.inventoryType = inventoryType;
    }

    @Column(name = "QUANTITY")
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Column(name = "BATCH_CODE")
    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    @Column(name = "DUE_DATE")
    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    @Column(name = "PRODUCE_DATE")
    public Date getProduceDate() {
        return produceDate;
    }

    public void setProduceDate(Date produceDate) {
        this.produceDate = produceDate;
    }

    @Column(name = "PRODUCE_CODE")
    public String getProduceCode() {
        return produceCode;
    }

    public void setProduceCode(String produceCode) {
        this.produceCode = produceCode;
    }

    @Column(name = "PURCHASE_ORDER_CODE")
    public String getPurchaseOrderCode() {
        return purchaseOrderCode;
    }

    public void setPurchaseOrderCode(String purchaseOrderCode) {
        this.purchaseOrderCode = purchaseOrderCode;
    }

    @Column(name = "SN_CODE")
    public String getSnCode() {
        return snCode;
    }

    public void setSnCode(String snCode) {
        this.snCode = snCode;
    }

}
