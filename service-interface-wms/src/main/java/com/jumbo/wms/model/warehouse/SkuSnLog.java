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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.baseinfo.SkuSnCardStatus;

/**
 * sku sn号操作日志
 */
@Entity
@Table(name = "T_WH_SKU_SN_LOG")
public class SkuSnLog extends BaseModel {

    private static final long serialVersionUID = -7970841295787027292L;
    /**
     * PK
     */
    private Long id;
    /**
     * sku sn号
     */
    private String sn;
    /**
     * 出入库方向
     */
    private TransactionDirection direction;
    /**
     * 事务时间
     */
    private Date transactionTime;
    /**
     * stv id
     */
    private Long stvId;
    /**
     * sku
     */
    private Long skuId;
    /**
     * 仓库 ou id
     */
    private Long ouId;
    /**
     * 批次号
     */
    private String batchCode;
    /**
     * 盘点表ID
     */
    private Long invCkId;

    /**
     * 商品成本
     */
    private BigDecimal skuCost;

    /**
     * 卡状态
     */
    private SkuSnCardStatus cardStatus;

    /**
     * 最后修改时间
     */
    private Date lastModifyTime = new Date();

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_SN_LOG", sequenceName = "S_T_WH_SKU_SN_LOG", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SN_LOG")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "SN", length = 200)
    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    @Column(name = "DIRECTION", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.TransactionDirection")})
    public TransactionDirection getDirection() {
        return direction;
    }

    public void setDirection(TransactionDirection direction) {
        this.direction = direction;
    }

    @Column(name = "TRANSACTION_TIME")
    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
    }

    @Column(name = "STV_ID")
    public Long getStvId() {
        return stvId;
    }

    public void setStvId(Long stvId) {
        this.stvId = stvId;
    }

    @Column(name = "SKU_ID")
    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    @Column(name = "OU_ID")
    public Long getOuId() {
        return ouId;
    }

    public void setOuId(Long ouId) {
        this.ouId = ouId;
    }

    @Column(name = "INV_CK_ID")
    public Long getInvCkId() {
        return invCkId;
    }

    public void setInvCkId(Long invCkId) {
        this.invCkId = invCkId;
    }

    @Column(name = "BATCH_CODE", length = 100)
    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    @Column(name = "SKU_COST")
    public BigDecimal getSkuCost() {
        return skuCost;
    }

    public void setSkuCost(BigDecimal skuCost) {
        this.skuCost = skuCost;
    }

    @Column(name = "CARD_STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.baseinfo.SkuSnCardStatus")})
    public SkuSnCardStatus getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(SkuSnCardStatus cardStatus) {
        this.cardStatus = cardStatus;
    }

    @Column(name = "LAST_MODIFY_TIME")
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }
}
