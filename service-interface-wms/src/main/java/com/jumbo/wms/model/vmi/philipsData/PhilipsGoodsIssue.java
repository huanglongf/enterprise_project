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
@Table(name = "T_PHILIPS_GOODS_ISSUE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class PhilipsGoodsIssue extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = -1680059979857142430L;

    private Long id;
    // 唯一单据号
    private String orderCode;
    // 物流公司缩写
    private String logisticCode;
    // 快递单号
    private String trackingNo;
    // 发货时间
    private Date despatchTime;

    private Date createTime;

    private Integer status;

    private Long batchId;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_PHILIPS_GOODS_ISSUE", sequenceName = "S_T_PHILIPS_GOODS_ISSUE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PHILIPS_GOODS_ISSUE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "ORDER_CODE", length = 50)
    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    @Column(name = "LOGISTIC_CODE", length = 20)
    public String getLogisticCode() {
        return logisticCode;
    }

    public void setLogisticCode(String logisticCode) {
        this.logisticCode = logisticCode;
    }

    @Column(name = "TRACKING_NO", length = 50)
    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    @Column(name = "DESPATCH_TIME")
    public Date getDespatchTime() {
        return despatchTime;
    }

    public void setDespatchTime(Date despatchTime) {
        this.despatchTime = despatchTime;
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
