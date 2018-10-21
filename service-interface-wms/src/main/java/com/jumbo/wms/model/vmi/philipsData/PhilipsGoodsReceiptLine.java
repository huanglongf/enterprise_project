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
package com.jumbo.wms.model.vmi.philipsData;


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
import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;

@Entity
@Table(name = "T_PHILIPS_GOODS_REC_LINE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class PhilipsGoodsReceiptLine extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = -982491820149185975L;

    private Long id;
    // 行号
    private String lineNumber;
    // 物料唯一编码
    private String skuCode;
    // 条码
    private String barcode;
    // 采购单号
    private String poCode;
    // 计划入库数量
    private BigDecimal planQuantity;
    // 实际收货数量
    private BigDecimal receivedQty;

    private Date createTime;

    private Integer status;

    private PhilipsGoodsReceipt philipsGoodsReceipt;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_PHILIPS_GOODS_REC_LINE", sequenceName = "S_T_PHILIPS_GOODS_REC_LINE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PHILIPS_GOODS_REC_LINE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "LINE_NUMBER", length = 10)
    public String getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }

    @Column(name = "SKU_CODE", length = 50)
    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    @Column(name = "BARCODE", length = 50)
    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    @Column(name = "PLAN_QUANTITY")
    public BigDecimal getPlanQuantity() {
        return planQuantity;
    }

    public void setPlanQuantity(BigDecimal planQuantity) {
        this.planQuantity = planQuantity;
    }

    @Column(name = "RECEIVED_QTY")
    public BigDecimal getReceivedQty() {
        return receivedQty;
    }

    public void setReceivedQty(BigDecimal receivedQty) {
        this.receivedQty = receivedQty;
    }

    @Column(name = "PO_CODE", length = 50)
    public String getPoCode() {
        return poCode;
    }

    public void setPoCode(String poCode) {
        this.poCode = poCode;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "STATUS")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GOODSR_ID")
    @Index(name = "IDX_PHILIPSGOODSR_LINE")
    public PhilipsGoodsReceipt getPhilipsGoodsReceipt() {
        return philipsGoodsReceipt;
    }

    public void setPhilipsGoodsReceipt(PhilipsGoodsReceipt philipsGoodsReceipt) {
        this.philipsGoodsReceipt = philipsGoodsReceipt;
    }


}
