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
package com.jumbo.wms.model.vmi.kerry;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OptimisticLockType;

@Entity
@Table(name = "T_CARRIER_CONSIGNMENT")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class CarrierConsignmentData implements Serializable {



    private static final long serialVersionUID = 5290615355277623021L;

    private Long id;

    private String orderNo;

    private String carrierNumber;

    private String carrierCode;

    private Date shippingTime;

    private String weight;

    private Date CreateDate;

    private Integer status;

    /**
     * 发货时间
     */
    private Date releaseDate;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_CARRIER_CONSIGNMENT", sequenceName = "S_T_CARRIER_CONSIGNMENT", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CARRIER_CONSIGNMENT")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "ORDER_NO", length = 20)
    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    @Column(name = "CARRIER_NUMBER", length = 30)
    public String getCarrierNumber() {
        return carrierNumber;
    }

    public void setCarrierNumber(String carrierNumber) {
        this.carrierNumber = carrierNumber;
    }

    @Column(name = "CARRIER_CODE", length = 20)
    public String getCarrierCode() {
        return carrierCode;
    }

    public void setCarrierCode(String carrierCode) {
        this.carrierCode = carrierCode;
    }

    @Column(name = "SHIPPING_TIME")
    public Date getShippingTime() {
        return shippingTime;
    }

    public void setShippingTime(Date shippingTime) {
        this.shippingTime = shippingTime;
    }

    @Column(name = "WEIGHT", length = 10)
    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    @Column(name = "CREATE_DATE")
    public Date getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(Date createDate) {
        CreateDate = createDate;
    }

    @Column(name = "STATUS", length = 2)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "RELEASE_DATE")
    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }


}
