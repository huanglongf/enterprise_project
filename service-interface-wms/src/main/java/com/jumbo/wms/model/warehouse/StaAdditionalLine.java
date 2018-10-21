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

import org.hibernate.annotations.Index;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.baseinfo.Sku;

/**
 * 仓库作业申请单附加行行
 * 
 */
@Entity
@Table(name = "T_WH_STA_ADD_LINE")
public class StaAdditionalLine extends BaseModel {

    private static final long serialVersionUID = -1079953361372113161L;

    /**
     * PK
     */
    private Long id;

    /**
     * Sku数量
     */
    private Long quantity;

    /**
     * 库存成本
     */
    private BigDecimal skuCost;

    /**
     * 货主，如果单头中指定，则必须和单头中一致；如果单头中不指定，则在行中的每行可以根据实际情况指定，如不指定代表不限定货主
     */
    private String owner;

    /**
     * 相关Sku
     */
    private Sku sku;

    /**
     * 相关库存状态（对于移库和库内移动操作，如果在申请单头中指定有库存状态，则此处状态与单头中原库存状态必须一致；
     * 如果单头中不指定，则在行中的每行可以根据实际情况指定，如不指定代表不限定库存状态）
     */
    private InventoryStatus invStatus;

    /**
     * 相关仓库作业申请单
     */
    private StockTransApplication sta;

    /**
     * 物流编码
     */
    private String lpcode;

    /**
     * 快递单号
     */
    private String trackingNo;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 退仓 箱号id
     * 
     * @return
     */
    private Long cId;

    @Column(name = "C_ID")
    public Long getcId() {
        return cId;
    }

    public void setcId(Long cId) {
        this.cId = cId;
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_STA_ADD_LINE", sequenceName = "S_T_WH_STA_ADD_LINE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_STA_ADD_LINE")
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

    @Column(name = "OWNER", length = 100)
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SKU_ID")
    @Index(name = "IDX_STA_ADD_LINE_SKU")
    public Sku getSku() {
        return sku;
    }

    public void setSku(Sku sku) {
        this.sku = sku;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INV_STATUS_ID")
    @Index(name = "IDX_STAL_ADD_INV_STATUS")
    public InventoryStatus getInvStatus() {
        return invStatus;
    }

    public void setInvStatus(InventoryStatus invStatus) {
        this.invStatus = invStatus;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STA_ID")
    @Index(name = "IDX_STA_ADD_LINE_STA")
    public StockTransApplication getSta() {
        return sta;
    }

    public void setSta(StockTransApplication sta) {
        this.sta = sta;
    }

    @Column(name = "LP_CODE", length = 20)
    public String getLpcode() {
        return lpcode;
    }

    public void setLpcode(String lpcode) {
        this.lpcode = lpcode;
    }

    @Column(name = "TRACKING_NO", length = 50)
    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
