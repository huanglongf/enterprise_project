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
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.SkuSnCardStatus;

/**
 * sku sn号
 */
@Entity
@Table(name = "T_WH_SKU_SN", uniqueConstraints = {@UniqueConstraint(columnNames = {"SN"})})
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class SkuSn extends BaseModel {

    private static final long serialVersionUID = -4134844323001157104L;

    /**
     * PK
     */
    private Long id;
    /**
     * version
     */
    private Integer version;
    /**
     * sku sn号
     */
    private String sn;
    /**
     * 批次号
     */
    private String batchCode;
    /**
     * 状态
     */
    private SkuSnStatus status;
    /**
     * sku
     */
    private Sku sku;

    private OperationUnit ou;
    /**
     * stv
     */
    private StockTransVoucher stv;
    /**
     * 盘点表
     */
    private InventoryCheck invCk;

    /**
     * 卡状态
     */
    private SkuSnCardStatus cardStatus;

    /**
     * 作业单ID
     */
    private Long staId;

    /**
     * 最后修改时间
     */
    private Date lastModifyTime;


    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_SN", sequenceName = "S_T_WH_SKU_SN", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SN")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Version
    @Column(name = "VERSION")
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Column(name = "SN", length = 200)
    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    @Column(name = "STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.SkuSnStatus")})
    public SkuSnStatus getStatus() {
        return status;
    }

    public void setStatus(SkuSnStatus status) {
        this.status = status;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STV_ID")
    @Index(name = "IDX_SKU_SN_STV_ID")
    public StockTransVoucher getStv() {
        return stv;
    }

    public void setStv(StockTransVoucher stv) {
        this.stv = stv;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SKU_ID")
    @Index(name = "IDX_SKU_SN_SKU_ID")
    public Sku getSku() {
        return sku;
    }

    public void setSku(Sku sku) {
        this.sku = sku;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OU_ID")
    @Index(name = "IDX_SKU_SN_OU_ID")
    public OperationUnit getOu() {
        return ou;
    }

    public void setOu(OperationUnit ou) {
        this.ou = ou;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INV_CK_ID")
    @Index(name = "IDX_SKU_SN_INV_CK_ID")
    public InventoryCheck getInvCk() {
        return invCk;
    }

    public void setInvCk(InventoryCheck invCk) {
        this.invCk = invCk;
    }

    @Column(name = "BATCH_CODE", length = 100)
    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    @Column(name = "CARD_STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.baseinfo.SkuSnCardStatus")})
    public SkuSnCardStatus getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(SkuSnCardStatus cardStatus) {
        this.cardStatus = cardStatus;
    }

    @Column(name = "STA_ID")
    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

    @Column(name = "LAST_MODIFY_TIME")
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

}
