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
package com.jumbo.wms.model.vmi.coachData;

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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StockTransApplication;

@Entity
@Table(name = "T_COACH_INBOUND_INSTRUCTION")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class CoachInboundInstructionData extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1325072750068320621L;

    private Long id;

    private String barCode;

    private BigDecimal quantity;

    private String innerShopCode;

    private String toLocation;

    private StockTransApplication sta;

    private StaLine staLine;

    private Date createTime;

    private Integer status;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_COACH_INBOUND_INSTRUCTION", sequenceName = "S_COACH_INBOUND_INSTRUCTION", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_COACH_INBOUND_INSTRUCTION")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "BAR_CODE", length = 20)
    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "STATUS", length = 2)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "QUANTITY")
    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    @Column(name = "INNER_SHOP_CODE", length = 20)
    public String getInnerShopCode() {
        return innerShopCode;
    }

    public void setInnerShopCode(String innerShopCode) {
        this.innerShopCode = innerShopCode;
    }

    @Column(name = "TO_LOCATION", length = 20)
    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STA_ID")
    @Index(name = "IDX_WH_COACH_INBOUND_STA")
    public StockTransApplication getSta() {
        return sta;
    }

    public void setSta(StockTransApplication sta) {
        this.sta = sta;
    }

    @OneToOne
    public StaLine getStaLine() {
        return staLine;
    }

    public void setStaLine(StaLine staLine) {
        this.staLine = staLine;
    }

}
