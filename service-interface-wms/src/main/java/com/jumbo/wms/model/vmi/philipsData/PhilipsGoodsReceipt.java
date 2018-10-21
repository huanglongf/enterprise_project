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


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;

@Entity
@Table(name = "T_PHILIPS_GOODS_REC")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class PhilipsGoodsReceipt extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = -982491820149185975L;

    private Long id;
    // 收货时间
    private Date receiveTime;
    // 收货单号
    private String receiptCode;
    // 发货单号
    private String deliveryNo;
    private Long batchId;
    // 入库单号
    private String inboundCode;

    private Date createTime;

    private Integer status;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_PHILIPS_GOODS_REC", sequenceName = "S_T_PHILIPS_GOODS_REC", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PHILIPS_GOODS_REC")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "RECEIVE_TIME")
    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    @Column(name = "RECEIPT_CODE", length = 50)
    public String getReceiptCode() {
        return receiptCode;
    }

    public void setReceiptCode(String receiptCode) {
        this.receiptCode = receiptCode;
    }

    @Column(name = "DELIVERY_NO", length = 50)
    public String getDeliveryNo() {
        return deliveryNo;
    }

    public void setDeliveryNo(String deliveryNo) {
        this.deliveryNo = deliveryNo;
    }

    @Column(name = "INBOUND_CODE", length = 50)
    public String getInboundCode() {
        return inboundCode;
    }

    public void setInboundCode(String inboundCode) {
        this.inboundCode = inboundCode;
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

    @Column(name = "BATCH_ID")
    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

}
