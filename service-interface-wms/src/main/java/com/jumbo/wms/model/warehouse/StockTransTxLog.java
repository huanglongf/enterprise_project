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
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;

/**
 * 仓库库存日志
 * 
 * @author benjamin
 * 
 */
@Entity
@Table(name = "T_WH_ST_LOG")
public class StockTransTxLog extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = -8321418771296846602L;

    /**
     * PK
     */
    private Long id;

    /**
     * 事务方向
     */
    private TransactionDirection direction;

    /**
     * 事务类型
     */
    private TransactionType transactionType;

    /**
     * 事务时间
     */
    private Date transactionTime;

    /**
     * Sku Id
     */
    private Long skuId;

    /**
     * 货主
     */
    private String owner;

    /**
     * 数量
     */
    private Long quantity;

    /**
     * 仓库Ou Id
     */
    private Long warehouseOuId;

    /**
     * 库区Id
     */
    private Long districtId;

    /**
     * 库位Id
     */
    private Long locationId;

    /**
     * 库存状态Id
     */
    private Long invStatusId;

    /**
     * 仓库作业明细单Id
     */
    private Long stvId;

    /**
     * 盘点批id
     */
    private Long inventoryCheckId;

    /**
     * 入库时间
     */
    private Date inboundTime;

    /**
     * 批次号
     */
    private String batchCode;

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

    /**
     * 作业单号
     */
    private String staCode;

    /**
     * 相关单据号（PAC对接码）
     */
    private String slipCode;

    /**
     * 相关单据号1（外部平台对接码）
     */
    private String slipCode1;

    /**
     * 相关单据号2（外部平台对接码）
     */
    private String slipCode2;

    /**
     * 操作员
     */
    private String opUserName;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_ST_LOG", sequenceName = "S_T_WH_ST_LOG", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ST_LOG")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "DIRECTION", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.TransactionDirection")})
    public TransactionDirection getDirection() {
        return direction;
    }

    public void setDirection(TransactionDirection direction) {
        this.direction = direction;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRANSTYPE_ID")
    @Index(name = "IDX_STA_LOG_TRANTYPE")
    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    @Column(name = "TRAN_TIME")
    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
    }

    @Column(name = "SKU_ID")
    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
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

    @Column(name = "WH_ID")
    public Long getWarehouseOuId() {
        return warehouseOuId;
    }

    public void setWarehouseOuId(Long warehouseOuId) {
        this.warehouseOuId = warehouseOuId;
    }

    @Column(name = "DISTRICT_ID")
    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    @Column(name = "LOCATION_ID")
    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    @Column(name = "INVS_TATUS_ID")
    public Long getInvStatusId() {
        return invStatusId;
    }

    public void setInvStatusId(Long invStatusId) {
        this.invStatusId = invStatusId;
    }

    @Column(name = "STV_ID")
    public Long getStvId() {
        return stvId;
    }

    public void setStvId(Long stvId) {
        this.stvId = stvId;
    }

    @Column(name = "INVENTORY_CHECK_ID")
    public Long getInventoryCheckId() {
        return inventoryCheckId;
    }

    public void setInventoryCheckId(Long inventoryCheckId) {
        this.inventoryCheckId = inventoryCheckId;
    }

    @Column(name = "INBOUND_TIME")
    public Date getInboundTime() {
        return inboundTime;
    }

    public void setInboundTime(Date inboundTime) {
        this.inboundTime = inboundTime;
    }

    @Column(name = "BATCH_CODE")
    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
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

    @Column(name = "STA_CODE", length = 50)
    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

    @Column(name = "SLIP_CODE", length = 50)
    public String getSlipCode() {
        return slipCode;
    }

    public void setSlipCode(String slipCode) {
        this.slipCode = slipCode;
    }

    @Column(name = "SLIP_CODE1", length = 50)
    public String getSlipCode1() {
        return slipCode1;
    }

    public void setSlipCode1(String slipCode1) {
        this.slipCode1 = slipCode1;
    }

    @Column(name = "SLIP_CODE2", length = 50)
    public String getSlipCode2() {
        return slipCode2;
    }

    public void setSlipCode2(String slipCode2) {
        this.slipCode2 = slipCode2;
    }

    @Column(name = "OP_USER_NAME", length = 100)
    public String getOpUserName() {
        return opUserName;
    }

    public void setOpUserName(String opUserName) {
        this.opUserName = opUserName;
    }

}
