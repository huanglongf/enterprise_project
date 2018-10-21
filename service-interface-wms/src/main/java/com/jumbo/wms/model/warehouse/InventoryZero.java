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
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.Sku;

/**
 * 仓库库存
 * 
 * @author benjamin
 * 
 */
@Entity
@Table(name = "T_WH_SKU_INVENTORY_ZERO")
public class InventoryZero extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 52104219999862981L;

    /**
     * PK
     */
    private Long id;

    /**
     * 批次号
     */
    private String batchCode;

    /**
     * 上架时间
     */
    private Date inboundTime;

    /**
     * 是否占用且锁定
     */
    private Boolean isOccupied = false;

    /**
     * 占用码
     */
    private String occupationCode;

    /**
     * 库存数量
     */
    private Long quantity;

    /**
     * 商品入库单位成本（用于先入先出等方式的成本计算用，现在保留）
     */
    private BigDecimal skuCost;

    /**
     * 所属店铺标识
     */
    private String owner;

    /**
     * 相关Sku
     */
    private Sku sku;

    /**
     * 相关仓库组织
     */
    private OperationUnit ou;

    /**
     * 相关库区
     */
    private WarehouseDistrict district;

    /**
     * 相关库位
     */
    private WarehouseLocation location;

    /**
     * 相关库存状态
     */
    private InventoryStatus status;

    /**
     * 生产日期
     */
    private Date productionDate;

    /**
     * 有效期天数
     */
    private Integer validDate;

    /**
     * 过期时间
     */
    private Date expireDate;

    /**
     * 占用批编码
     */
    private String ocpCode;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_INV", sequenceName = "S_T_WH_INVENTORY", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_INV")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "BATCH_CODE", length = 100)
    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    @Column(name = "INBOUND_TIME")
    public Date getInboundTime() {
        return inboundTime;
    }

    public void setInboundTime(Date inboundTime) {
        this.inboundTime = inboundTime;
    }

    @Column(name = "IS_OCCUPIED")
    public Boolean getIsOccupied() {
        return isOccupied;
    }

    public void setIsOccupied(Boolean isOccupied) {
        this.isOccupied = isOccupied;
    }

    @Column(name = "OCCUPATION_CODE", length = 100)
    public String getOccupationCode() {
        return occupationCode;
    }

    public void setOccupationCode(String occupationCode) {
        this.occupationCode = occupationCode;
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

    @Column(name = "INV_OWNER", length = 100)
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SKU_ID")
    @Index(name = "IDX_INV_SKU")
    public Sku getSku() {
        return sku;
    }

    public void setSku(Sku sku) {
        this.sku = sku;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OU_ID")
    @Index(name = "IDX_INV_OU")
    public OperationUnit getOu() {
        return ou;
    }

    public void setOu(OperationUnit ou) {
        this.ou = ou;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DISTRICT_ID")
    @Index(name = "IDX_INV_WHD")
    public WarehouseDistrict getDistrict() {
        return district;
    }

    public void setDistrict(WarehouseDistrict district) {
        this.district = district;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LOCATION_ID")
    @Index(name = "IDX_INV_WHL")
    public WarehouseLocation getLocation() {
        return location;
    }

    public void setLocation(WarehouseLocation location) {
        this.location = location;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STATUS_ID")
    @Index(name = "IDX_INV_INVS")
    public InventoryStatus getStatus() {
        return status;
    }

    public void setStatus(InventoryStatus status) {
        this.status = status;
    }

    @Column(name = "PRODUCTION_DATE")
    public Date getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    @Column(name = "VALID_DATE")
    public Integer getValidDate() {
        return validDate;
    }

    public void setValidDate(Integer validDate) {
        this.validDate = validDate;
    }

    @Column(name = "EXPIRE_DATE")
    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    @Column(name = "OCP_CODE", length = 50)
    public String getOcpCode() {
        return ocpCode;
    }

    public void setOcpCode(String ocpCode) {
        this.ocpCode = ocpCode;
    }


}
