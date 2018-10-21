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

@Entity
@Table(name = "T_WH_CN_OUT_ORDER_ITEM")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class CnOutOrderItem implements Serializable {
    
    private static final long serialVersionUID = 4098656776156926507L;
    private Long id;
    /**
     * 出库单明细ID
     */
    private String orderItemId;
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
     * 库存类型：1 可销售库存 正品 101 残次 102 机损 103 箱损 201 冻结库存
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
     * 采购单号，是批次属性，指定PO出库
     */
    private String purchaseOrderCode;
    /**
     * 批次备注
     */
    private String batchRemark;

    /**
     * 拓展属性数据 详见 订单下发扩展字段
     */
    private String extendFields;
    
    private CnOutOrderNotify cnOutOrderNotify;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_COOI", sequenceName = "S_CN_OUT_ORDER_ITEM", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_COOI")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "ORDER_ITEM_ID")
    public String getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(String orderItemId) {
        this.orderItemId = orderItemId;
    }

    @Column(name = "ITEM_ID")
    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    @Column(name = "ITEM_NAME")
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @Column(name = "ITEM_CODE")
    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    @Column(name = "INVENTORY_TYPE")
    public Integer getInventoryType() {
        return inventoryType;
    }

    public void setInventoryType(Integer inventoryType) {
        this.inventoryType = inventoryType;
    }

    @Column(name = "ITEM_QUANTITY")
    public Integer getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(Integer itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    @Column(name = "ITEM_VERSION")
    public Integer getItemVersion() {
        return itemVersion;
    }

    public void setItemVersion(Integer itemVersion) {
        this.itemVersion = itemVersion;
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

    @Column(name = "BATCH_REMARK")
    public String getBatchRemark() {
        return batchRemark;
    }

    public void setBatchRemark(String batchRemark) {
        this.batchRemark = batchRemark;
    }

    @Column(name = "EXTENDFIELDS")
    public String getExtendFields() {
        return extendFields;
    }

    public void setExtendFields(String extendFields) {
        this.extendFields = extendFields;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OUT_ORDER_NOTIFY_ID")
    public CnOutOrderNotify getCnOutOrderNotify() {
        return cnOutOrderNotify;
    }

    public void setCnOutOrderNotify(CnOutOrderNotify cnOutOrderNotify) {
        this.cnOutOrderNotify = cnOutOrderNotify;
    }



}
