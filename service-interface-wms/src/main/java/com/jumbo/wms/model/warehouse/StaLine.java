/**
 * Copyright (c) 2010 Jumbomart All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Jumbomart. You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jumbo.
 * 
 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 * 
 */

package com.jumbo.wms.model.warehouse;

import java.math.BigDecimal;
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
import javax.persistence.Version;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.baseinfo.Sku;

/**
 * 仓库作业申请单行
 * 
 * @author benjamin
 * 
 */
@Entity
@Table(name = "T_WH_STA_LINE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class StaLine extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = 2247215822090901191L;

    /**
     * PK
     */
    private Long id;

    /**
     * Sku数量
     */
    private Long quantity;
    /**
     * 已执行数量
     */
    private Long completeQuantity;
    /**
     * 库存成本
     */
    private BigDecimal skuCost;

    /**
     * 采购(采购行总金额)/销售(销售单行实际总金额)
     */
    private BigDecimal totalActual;
    /**
     * 销售(销售单行单价)
     */
    private BigDecimal unitPrice;
    /**
     * 货主，如果单头中指定，则必须和单头中一致；如果单头中不指定，则在行中的每行可以根据实际情况指定，如不指定代表不限定货主
     */
    private String owner;
    private int version;

    /**
     * 相关Sku
     */
    private Sku sku;
    /**
     * sku商品名称
     */
    private String skuName;

    /**
     * 相关库存状态（对于移库和库内移动操作，如果在申请单头中指定有库存状态，则此处状态与单头中原库存状态必须一致；
     * 如果单头中不指定，则在行中的每行可以根据实际情况指定，如不指定代表不限定库存状态）
     */
    private InventoryStatus invStatus;

    /**
     * 活动
     */
    private String activitySource;

    /**
     * 相关仓库作业申请单
     */
    private StockTransApplication sta;
    /**
     * 行总金额
     */
    private BigDecimal orderTotalActual;
    /**
     * 订单折前金额
     */
    private BigDecimal orderTotalBfDiscount;
    /**
     * 吊牌价
     */
    private BigDecimal listPrice;
    /**
     * 过期时间
     */
    private Date expireDate;
    /**
     * 定制逻辑，收货默认状态
     */
    private Boolean isDefaultStatus;
    /**
     * 品牌备注
     */
    private String extMemo;

    /*
     * 行号
     */
    private String lineNo;
    /*
     * 行折扣
     */
    private BigDecimal lineDiscount;

    /**
     * 行标识取消
     */
    private Boolean isCancel;

    /**
     * 行号
     */
    private String orderLineNo;
    /**
     * 平台订单计划量
     */
    private Long orderQty;
    /**
     * 商品颜色
     */
    private String color;
    /**
     * 尺码
     */
    private String lineSize;

    /**
     * 库存行计算区域
     */
    private String ocpAreaCode;
    /**
     * 库存行计算结果
     */
    private String ocpAreaMemo;

    public StaLine() {

    }

    public StaLine(Long id) {
        super();
        this.id = id;
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_STA_LINE", sequenceName = "S_T_WH_STA_LINE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_STA_LINE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "QUANTITY")
    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    @Column(name = "SKU_COST", precision = 22, scale = 4)
    public BigDecimal getSkuCost() {
        return skuCost;
    }


    public void setSkuCost(BigDecimal skuCost) {
        this.skuCost = skuCost;
    }

    @Column(name = "order_qty")
    public Long getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(Long orderQty) {
        this.orderQty = orderQty;
    }

    @Column(name = "ORDER_LINE_NO")
    public String getOrderLineNo() {
        return orderLineNo;
    }

    public void setOrderLineNo(String orderLineNo) {
        this.orderLineNo = orderLineNo;
    }

    @Column(name = "OWNER", length = 100)
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Version
    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SKU_ID")
    @Index(name = "IDX_STA_LINE_SKU")
    public Sku getSku() {
        return sku;
    }

    @Column(name = "skuName")
    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public void setSku(Sku sku) {
        this.sku = sku;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INV_STATUS_ID")
    @Index(name = "IDX_STA_LINE_INV_STATUS")
    public InventoryStatus getInvStatus() {
        return invStatus;
    }

    public void setInvStatus(InventoryStatus invStatus) {
        this.invStatus = invStatus;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STA_ID")
    @Index(name = "IDX_STA_LINE_STA")
    public StockTransApplication getSta() {
        return sta;
    }

    public void setSta(StockTransApplication sta) {
        this.sta = sta;
    }

    @Column(name = "COMPLETE_QUANTITY")
    public Long getCompleteQuantity() {
        return completeQuantity;
    }

    public void setCompleteQuantity(Long completeQuantity) {
        this.completeQuantity = completeQuantity;
    }

    @Column(name = "TOTAL_ACTUAL")
    public BigDecimal getTotalActual() {
        return totalActual;
    }

    public void setTotalActual(BigDecimal totalActual) {
        this.totalActual = totalActual;
    }

    @Column(name = "UNIT_PRICE")
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Column(name = "ACTIVITY_SOURCE", length = 30)
    public String getActivitySource() {
        return activitySource;
    }

    public void setActivitySource(String activitySource) {
        this.activitySource = activitySource;
    }

    @Column(name = "order_total_actual")
    public BigDecimal getOrderTotalActual() {
        return orderTotalActual;
    }

    public void setOrderTotalActual(BigDecimal orderTotalActual) {
        this.orderTotalActual = orderTotalActual;
    }

    @Column(name = "order_total_bf_discount")
    public BigDecimal getOrderTotalBfDiscount() {
        return orderTotalBfDiscount;
    }

    public void setOrderTotalBfDiscount(BigDecimal orderTotalBfDiscount) {
        this.orderTotalBfDiscount = orderTotalBfDiscount;
    }

    @Column(name = "is_cancel")
    public Boolean getIsCancel() {
        return isCancel;
    }

    public void setIsCancel(Boolean isCancel) {
        this.isCancel = isCancel;
    }

    @Column(name = "list_price")
    public BigDecimal getListPrice() {
        return listPrice;
    }

    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
    }

    @Column(name = "EXPIRE_DATE")
    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    @Column(name = "DEFAULT_STATUS")
    public Boolean getIsDefaultStatus() {
        return isDefaultStatus;
    }

    public void setIsDefaultStatus(Boolean isDefaultStatus) {
        this.isDefaultStatus = isDefaultStatus;
    }

    @Column(name = "EXT_MEMO", length = 2000)
    public String getExtMemo() {
        return extMemo;
    }

    public void setExtMemo(String extMemo) {
        this.extMemo = extMemo;
    }

    @Column(name = "LINE_NO", length = 30)
    public String getLineNo() {
        return lineNo;
    }

    public void setLineNo(String lineNo) {
        this.lineNo = lineNo;
    }

    @Column(name = "LINE_DISCOUNT")
    public BigDecimal getLineDiscount() {
        return lineDiscount;
    }

    public void setLineDiscount(BigDecimal lineDiscount) {
        this.lineDiscount = lineDiscount;
    }

    @Column(name = "color")
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Column(name = "line_Size")
    public String getLineSize() {
        return lineSize;
    }

    public void setLineSize(String lineSize) {
        this.lineSize = lineSize;
    }

    @Column(name = "ocp_area_code")
    public String getOcpAreaCode() {
        return ocpAreaCode;
    }

    public void setOcpAreaCode(String ocpAreaCode) {
        this.ocpAreaCode = ocpAreaCode;
    }

    @Column(name = "ocp_area_memo")
    public String getOcpAreaMemo() {
        return ocpAreaMemo;
    }

    public void setOcpAreaMemo(String ocpAreaMemo) {
        this.ocpAreaMemo = ocpAreaMemo;
    }


}
