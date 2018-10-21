package com.jumbo.wms.model.hub2wms.threepl;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class WmsConsignOrderItem implements Serializable {

    private static final long serialVersionUID = 5477348097531087096L;

    /**
     * 订单商品ID
     */
    private String orderItemId;
    /**
     * 交易编码
     */
    private String orderSourceCode;
    /**
     * 子交易编码
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
     * 批次号 指定批次发货，目前是俪人购，喵生鲜业务，一般不会指定
     */
    private String batchCode;
    /**
     * 生产日期,保质期商品信息，如果商品启用了保质期管理，需要仓库按指定保质期生产 指定批次发货，目前是俪人购，喵生鲜业务，一般不会指定
     */
    private Date produceDate;
    /**
     * 到效日期,保质期商品信息，如果商品启用了保质期管理，需要仓库按指定保质期生产 指定批次发货，目前是俪人购，喵生鲜业务，一般不会指定
     */
    private Date dueDate;
    /**
     * 采购单号，是批次属性，指定PO发货
     */
    private String purchaseOrderCode;
    /**
     * 库存类型(1 正品 )
     */
    private Integer inventoryType;
    /**
     * 商品数量
     */
    private Long itemQuantity;
    /**
     * 商品实际价格
     */
    private Long actualPrice;
    /**
     * 商品销售价格
     */
    private Long itemPrice;
    /**
     * 商品优惠金额
     */
    private Long discountAmount;
    /**
     * 商品版本
     */
    private Integer itemVersion;
    /**
     * 批次备注
     */
    private String batchRemark;
    /**
     * 拓展属性数据 详见 订单明细下发扩展字段
     */
    private Map<Object, Object> extendFields;

    public String getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(String orderItemId) {
        this.orderItemId = orderItemId;
    }

    public String getOrderSourceCode() {
        return orderSourceCode;
    }

    public void setOrderSourceCode(String orderSourceCode) {
        this.orderSourceCode = orderSourceCode;
    }

    public String getSubSourceCode() {
        return subSourceCode;
    }

    public void setSubSourceCode(String subSourceCode) {
        this.subSourceCode = subSourceCode;
    }

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

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public Date getProduceDate() {
        return produceDate;
    }

    public void setProduceDate(Date produceDate) {
        this.produceDate = produceDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getPurchaseOrderCode() {
        return purchaseOrderCode;
    }

    public void setPurchaseOrderCode(String purchaseOrderCode) {
        this.purchaseOrderCode = purchaseOrderCode;
    }

    public Integer getInventoryType() {
        return inventoryType;
    }

    public void setInventoryType(Integer inventoryType) {
        this.inventoryType = inventoryType;
    }

    public Long getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(Long itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public Long getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(Long actualPrice) {
        this.actualPrice = actualPrice;
    }

    public Long getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Long itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Long getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Long discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Integer getItemVersion() {
        return itemVersion;
    }

    public void setItemVersion(Integer itemVersion) {
        this.itemVersion = itemVersion;
    }

    public String getBatchRemark() {
        return batchRemark;
    }

    public void setBatchRemark(String batchRemark) {
        this.batchRemark = batchRemark;
    }

    public Map<Object, Object> getExtendFields() {
        return extendFields;
    }

    public void setExtendFields(Map<Object, Object> extendFields) {
        this.extendFields = extendFields;
    }

}
