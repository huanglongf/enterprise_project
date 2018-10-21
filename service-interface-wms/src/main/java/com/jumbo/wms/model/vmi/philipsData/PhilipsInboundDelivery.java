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
import com.jumbo.wms.model.warehouse.StockTransApplication;

@Entity
@Table(name = "T_PHILIPS_IN_DELIVERY")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class PhilipsInboundDelivery extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = -5694475592318540061L;

    private Long id;
    // 发货单号
    private String deliveryNo;
    // 入库单号
    private String inboundCode;

    private String confirmId;

    private Date createTime;

    private Date version;

    private StockTransApplication sta;

    private Integer status;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_PHILIPS_IN_DELIVERY", sequenceName = "S_T_PHILIPS_IN_DELIVERY", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PHILIPS_IN_DELIVERY")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Column(name = "VERSION")
    public Date getVersion() {
        return version;
    }

    public void setVersion(Date version) {
        this.version = version;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STA_ID")
    @Index(name = "IDX_WH_VMI_PHILIPS_STA")
    public StockTransApplication getSta() {
        return sta;
    }

    public void setSta(StockTransApplication sta) {
        this.sta = sta;
    }

    @Column(name = "STATUS")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "CONFIRM_ID", length = 50)
    public String getConfirmId() {
        return confirmId;
    }

    public void setConfirmId(String confirmId) {
        this.confirmId = confirmId;
    }

}
