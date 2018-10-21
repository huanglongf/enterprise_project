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
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.Sku;

/**
 * 仓库作业明细单行
 * 
 * @author benjamin
 * 
 */
@Entity
@Table(name = "T_WH_STV_LINE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class StvLine extends BaseModel implements Cloneable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 8692035228092885800L;

    /**
     * PK
     */
    private Long id;

    /**
     * 批号
     */
    private String batchCode;

    /**
     * 货主
     */
    private String owner;


    /**
     * 剩余计划量
     */
    private Long remainPlanQty;


    /**
     * 收货量
     */
    private Long receiptQty;

    /**
     * 差异量
     */
    private Long differenceQty;

    /**
     * 数量(已核对量) = 收货量 + 差异量
     */
    private Long quantity;

    /**
     * 已上架量
     */
    private Long addedQty;

    /**
     * 库存成本
     */
    private BigDecimal skuCost;

    /**
     * 事务方向
     */
    private TransactionDirection direction;

    /**
     * 事务类型
     */
    private TransactionType transactionType;
    /**
     * version
     */
    private int version;

    /**
     * 相关Sku
     */
    private Sku sku;

    /**
     * 相关仓库组织
     */
    private OperationUnit warehouse;

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
    private InventoryStatus invStatus;

    /**
     * 入库时间
     */
    private Date inBoundTime;

    /**
     * 相关仓库作业明细单
     */
    private StockTransVoucher stv;
    /**
     * 相关申请单行
     */
    private StaLine staLine;
    
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

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_STV_LINE", sequenceName = "S_T_WH_STV_LINE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_STV_LINE")
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

    @Column(name = "OWNER", length = 100)
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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

    @Column(name = "DIRECTION", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.TransactionDirection")})
    public TransactionDirection getDirection() {
        return direction;
    }

    public void setDirection(TransactionDirection direction) {
        this.direction = direction;
    }

    @Transient
    public int getIntDirection() {
        return direction == null ? -1 : direction.getValue();
    }

    public void setIntDirection(int intDirection) {
        setDirection(TransactionDirection.valueOf(intDirection));
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRANSTYPE_ID")
    @Index(name = "IDX_STV_LINE_TRANTYPE")
    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
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
    @Index(name = "IDX_STV_LINE_SKU")
    public Sku getSku() {
        return sku;
    }

    public void setSku(Sku sku) {
        this.sku = sku;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WH_ID")
    @Index(name = "IDX_STV_LINE_WH")
    public OperationUnit getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(OperationUnit warehouse) {
        this.warehouse = warehouse;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DISTRICT_ID")
    @Index(name = "IDX_STV_LINE_DISTRICT")
    public WarehouseDistrict getDistrict() {
        return district;
    }

    public void setDistrict(WarehouseDistrict district) {
        this.district = district;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LOCATION_ID")
    @Index(name = "IDX_STV_LINE_LOCATION")
    public WarehouseLocation getLocation() {
        return location;
    }

    public void setLocation(WarehouseLocation location) {
        this.location = location;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INV_STATUS_ID")
    @Index(name = "IDX_STV_LINE_INV_STATUS")
    public InventoryStatus getInvStatus() {
        return invStatus;
    }

    public void setInvStatus(InventoryStatus invStatus) {
        this.invStatus = invStatus;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STV_ID")
    @Index(name = "IDX_STV_LINE_STV")
    public StockTransVoucher getStv() {
        return stv;
    }

    public void setStv(StockTransVoucher stv) {
        this.stv = stv;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STALINE_ID")
    @Index(name = "IDX_STV_LINE_STA_LINE")
    public StaLine getStaLine() {
        return staLine;
    }

    public void setStaLine(StaLine staLine) {
        this.staLine = staLine;
    }

    @Column(name = "INBOUND_TIME")
    public Date getInBoundTime() {
        return inBoundTime;
    }

    public void setInBoundTime(Date inBoundTime) {
        this.inBoundTime = inBoundTime;
    }

    @Column(name = "REMAIN_PLAN_QTY")
    public Long getRemainPlanQty() {
        return remainPlanQty;
    }

    public void setRemainPlanQty(Long remainPlanQty) {
        this.remainPlanQty = remainPlanQty;
    }

    @Column(name = "RECEIPT_QTY")
    public Long getReceiptQty() {
        return receiptQty;
    }

    public void setReceiptQty(Long receiptQty) {
        this.receiptQty = receiptQty;
    }

    @Column(name = "DIFFERENCE_QTY")
    public Long getDifferenceQty() {
        return differenceQty;
    }

    public void setDifferenceQty(Long differenceQty) {
        this.differenceQty = differenceQty;
    }

    @Column(name = "ADDED_QTY")
    public Long getAddedQty() {
        return addedQty;
    }

    public void setAddedQty(Long addedQty) {
        this.addedQty = addedQty;
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
    
    @Override
    public StvLine clone() throws CloneNotSupportedException {
        super.clone();
        StvLine cloneObj = new StvLine();
        cloneObj.setBatchCode(batchCode);
        cloneObj.setDirection(direction);
        cloneObj.setInvStatus(invStatus);
        cloneObj.setOwner(owner);
        // cloneObj.setQuantity(quantity);
        // cloneObj.setLocation(location);
        // cloneObj.setDistrict(district);
        cloneObj.setSku(sku);
        cloneObj.setInBoundTime(inBoundTime);
        cloneObj.setSkuCost(skuCost);
        cloneObj.setStaLine(staLine);
        cloneObj.setStv(stv);
        cloneObj.setTransactionType(transactionType);
        cloneObj.setVersion(0);
        cloneObj.setWarehouse(warehouse);
        cloneObj.setSns(sns);
        cloneObj.setProductionDate(productionDate);
        cloneObj.setValidDate(validDate);
        cloneObj.setExpireDate(expireDate);
        return cloneObj;
    }

    /**
     * SN序列号,以逗号分割
     */
    private String sns;

    @Transient
    public String getSns() {
        return sns;
    }

    public void setSns(String sns) {
        this.sns = sns;
    }
}
