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

import org.hibernate.annotations.Index;
import org.hibernate.annotations.OptimisticLockType;

/**
 * 菜鸟盘点单商品明细
 * 
 */
@Entity
@Table(name = "T_WH_CN_INV_COUNT_RT_ITEN")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class CnInvCountReturnOrderItem implements Serializable {

    private static final long serialVersionUID = 6034417077415583504L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 菜鸟盘点单
     */
    private CnWmsInventoryCount cnInventoryCount;
    /**
     * 商品ID
     */
    private String itemId;
    /**
     * 明细外部业务编号，差异单的盘点需要填写差异单生成的明细外部业务编号 无差异单业务不填 差异单：主要是为了先锁定库存
     */
    private String detailOutBizCode;
    /**
     * 库存类型
     */
    private Integer inventoryType;
    /**
     * 商品数量
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
     * 备注
     */
    private String remark;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_WCSI", sequenceName = "S_T_WH_CN_INV_COUNT_RT_ITEN", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WCSI")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INV_COUNT_ID")
    @Index(name = "IDX_INV_COUNT_ID")
    public CnWmsInventoryCount getCnInventoryCount() {
        return cnInventoryCount;
    }

    public void setCnInventoryCount(CnWmsInventoryCount cnInventoryCount) {
        this.cnInventoryCount = cnInventoryCount;
    }

    @Column(name = "ITEM_ID")
    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    @Column(name = "DETAIL_OUT_BIZ_CODE")
    public String getDetailOutBizCode() {
        return detailOutBizCode;
    }

    public void setDetailOutBizCode(String detailOutBizCode) {
        this.detailOutBizCode = detailOutBizCode;
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

    @Column(name = "REMARK")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
