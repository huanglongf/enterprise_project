package com.jumbo.wms.model.hub2wms.threepl;

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

import com.jumbo.wms.model.BaseModel;

/**
 * 菜鸟入库单商品明细
 */
@Entity
@Table(name = "T_WH_CN_STOCK_IN_NOTIFY_LINE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class CnWmsStockInOrderLine extends BaseModel {

    private static final long serialVersionUID = -4203203428962945595L;
    /**
     * 主键
     */
    private Long id;

    /**
     * 菜鸟入库通知单
     */
    private CnWmsStockInOrderNotify stockInOrderNotify;

    /**
     * 入库单明细ID
     */
    private String orderItemId;
    /**
     * 平台交易编码：只在销退订单时使用，原销售订单的主交易单号
     */
    private String orderSourceCode;
    /**
     * 平台子交易编码:只在销退订单时使用，原销售订单的子交易单号
     */
    private String subSourceCode;
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
     * 库存类型（1 可销售库存(正品) 101 残次 102 机损 103 箱损301 在途库存 201 冻结库存）注意：采购入库单下发的库存类型是301
     */
    private Integer inventoryType;
    /**
     * 商品数量
     */
    private Integer itemQuantity;
    /**
     * 商品版本，如果版本和WMS系统中的版本不一致，需要CP使用商品抓取接口，将最新的商品信息抓取下来
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
     * 采购单号
     */
    private String purchaseOrderCode;
    /**
     * 批次备注
     */
    private String batchRemark;
    /**
     * 拓展属性数据 详见 订单明细下发扩展字段
     */
    private String extendFields;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_WCSS", sequenceName = "S_T_WH_CN_STOCK_IN_NOTIFY_LINE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WCSS")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STOCK_IN_ORDER_ID")
    @Index(name = "IDX_STOCK_IN_ORDER_ID")
    public CnWmsStockInOrderNotify getStockInOrderNotify() {
        return stockInOrderNotify;
    }

    public void setStockInOrderNotify(CnWmsStockInOrderNotify stockInOrderNotify) {
        this.stockInOrderNotify = stockInOrderNotify;
    }

    @Column(name = "ORDER_ITEM_ID")
    public String getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(String orderItemId) {
        this.orderItemId = orderItemId;
    }

    @Column(name = "ORDER_SOURCE_CODE")
    public String getOrderSourceCode() {
        return orderSourceCode;
    }

    public void setOrderSourceCode(String orderSourceCode) {
        this.orderSourceCode = orderSourceCode;
    }

    @Column(name = "SUB_SOURCE_CODE")
    public String getSubSourceCode() {
        return subSourceCode;
    }

    public void setSubSourceCode(String subSourceCode) {
        this.subSourceCode = subSourceCode;
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

    @Column(name = "BAR_CODE")
    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
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

    @Column(name = "EXTEND_FIELDS")
    public String getExtendFields() {
        return extendFields;
    }

    public void setExtendFields(String extendFields) {
        this.extendFields = extendFields;
    }

}
