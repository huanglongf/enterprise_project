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

import org.hibernate.annotations.Index;
import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.authorization.User;

/**
 * 快递订单(包裹)
 * 
 * @author benjamin
 * 
 */
@Entity
@Table(name = "T_WH_TRANS_ORDER")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class TransOrder extends BaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = -171916382241139394L;

    /**
     * PK
     */
    private Long id;

    /**
     * 订单号
     */
    private String code;
    /**
     * 组织ID（仓库）
     */
    private OperationUnit opUnit;

    /**
     * 业务类型
     */
    private String businessType;

    /**
     * 成本中心类型
     */
    private String costCenterType;
    /**
     * 部门/店铺
     */
    private String costCenterDetail;
    /**
     * 寄件人
     */
    private String sender;
    /**
     * ji联系方式
     */
    private String senderTel;

    /**
     * 寄国家
     */
    private String senderCountry;
    /**
     * 寄省
     */
    private String senderProvince;
    /**
     * 寄市
     */
    private String senderCity;
    /**
     * 寄区(县)
     */
    private String senderArea;
    /**
     * 寄详细地址
     */
    private String senderAddress;
    /**
     * 物流商编码
     */
    private String transportatorCode;
    /**
     * 时效类型
     */
    private Integer timeType;
    /**
     * 包裹数量
     */
    private int packageNum;
    /**
     * 收件人
     */
    private String receiver;
    /**
     * shou联系方式
     */
    private String receiverTel;
    /**
     * 收国家
     */
    private String receiverCountry;
    /**
     * 收省
     */
    private String receiverProvince;
    /**
     * 收市
     */
    private String receiverCity;
    /**
     * 收区(县)
     */
    private String receiverArea;
    /**
     * 收详细地址
     */
    private String receiverAddress;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建人
     */
    private User createUser;
    /**
     * 最后修改时间
     */
    private Date lastModifyTime;
    /**
     * 是否陆运(0:否 1:是)
     */
    private int isnotLandTrans;
    /**
     * 保价金额
     */
    private BigDecimal insuranceAmount;

    /**
     * 状态(0:作废 1:正常)
     */
    private int status;


    /**
     * ji联系方式(电话)
     */
    private String senderTel2;

    /**
     * shou联系方式(电话)
     */
    private String receiverTel2;

    /*
     * 备注
     */
    private String remark;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_WHL", sequenceName = "S_T_WH_TRANS_ORDER", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WHL")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "S_TEL2")
    public String getSenderTel2() {
        return senderTel2;
    }

    public void setSenderTel2(String senderTel2) {
        this.senderTel2 = senderTel2;
    }

    @Column(name = "R_TEL2")
    public String getReceiverTel2() {
        return receiverTel2;
    }

    public void setReceiverTel2(String receiverTel2) {
        this.receiverTel2 = receiverTel2;
    }

    @Column(name = "REMARK")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OU_ID")
    @Index(name = "IDX_FK_OU_ID")
    public OperationUnit getOpUnit() {
        return opUnit;
    }

    public void setOpUnit(OperationUnit opUnit) {
        this.opUnit = opUnit;
    }

    @Column(name = "CODE")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    @Column(name = "BUSINESS_TYPE")
    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    @Column(name = "COST_CENTER_TYPE")
    public String getCostCenterType() {
        return costCenterType;
    }

    public void setCostCenterType(String costCenterType) {
        this.costCenterType = costCenterType;
    }

    @Column(name = "COST_CENTER_DETAIL")
    public String getCostCenterDetail() {
        return costCenterDetail;
    }

    public void setCostCenterDetail(String costCenterDetail) {
        this.costCenterDetail = costCenterDetail;
    }

    @Column(name = "SENDER")
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    @Column(name = "S_TEL")
    public String getSenderTel() {
        return senderTel;
    }

    public void setSenderTel(String senderTel) {
        this.senderTel = senderTel;
    }



    @Column(name = "TRANSPORTATOR_CODE")
    public String getTransportatorCode() {
        return transportatorCode;
    }

    public void setTransportatorCode(String transportatorCode) {
        this.transportatorCode = transportatorCode;
    }


    @Column(name = "TIME_TYPE")
    public Integer getTimeType() {
        return timeType;
    }

    public void setTimeType(Integer timeType) {
        this.timeType = timeType;
    }

    @Column(name = "PACKAGE_NUM")
    public int getPackageNum() {
        return packageNum;
    }

    public void setPackageNum(int packageNum) {
        this.packageNum = packageNum;
    }

    @Column(name = "RECEIVER")
    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }



    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATE_USER_ID")
    @Index(name = "FK_CREATE_USER_ID")
    public User getCreateUser() {
        return createUser;
    }

    public void setCreateUser(User createUser) {
        this.createUser = createUser;
    }

    @Column(name = "LAST_MODIFY_TIME")
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    @Column(name = "S_COUNTRY")
    public String getSenderCountry() {
        return senderCountry;
    }

    public void setSenderCountry(String senderCountry) {
        this.senderCountry = senderCountry;
    }

    @Column(name = "S_PROVINCE")
    public String getSenderProvince() {
        return senderProvince;
    }

    public void setSenderProvince(String senderProvince) {
        this.senderProvince = senderProvince;
    }

    @Column(name = "S_CITY")
    public String getSenderCity() {
        return senderCity;
    }

    public void setSenderCity(String senderCity) {
        this.senderCity = senderCity;
    }

    @Column(name = "S_AREA")
    public String getSenderArea() {
        return senderArea;
    }

    public void setSenderArea(String senderArea) {
        this.senderArea = senderArea;
    }

    @Column(name = "S_ADDRESS")
    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    @Column(name = "R_TEL")
    public String getReceiverTel() {
        return receiverTel;
    }

    public void setReceiverTel(String receiverTel) {
        this.receiverTel = receiverTel;
    }

    @Column(name = "R_COUNTRY")
    public String getReceiverCountry() {
        return receiverCountry;
    }

    public void setReceiverCountry(String receiverCountry) {
        this.receiverCountry = receiverCountry;
    }

    @Column(name = "R_PROVINCE")
    public String getReceiverProvince() {
        return receiverProvince;
    }

    public void setReceiverProvince(String receiverProvince) {
        this.receiverProvince = receiverProvince;
    }

    @Column(name = "R_CITY")
    public String getReceiverCity() {
        return receiverCity;
    }

    public void setReceiverCity(String receiverCity) {
        this.receiverCity = receiverCity;
    }

    @Column(name = "R_AREA")
    public String getReceiverArea() {
        return receiverArea;
    }

    public void setReceiverArea(String receiverArea) {
        this.receiverArea = receiverArea;
    }

    @Column(name = "R_ADDRESS")
    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }


    @Column(name = "IS_LAND_TRANS")
    public int getIsnotLandTrans() {
        return isnotLandTrans;
    }

    public void setIsnotLandTrans(int isnotLandTrans) {
        this.isnotLandTrans = isnotLandTrans;
    }

    @Column(name = "INSURANCE_AMOUNT")
    public BigDecimal getInsuranceAmount() {
        return insuranceAmount;
    }

    public void setInsuranceAmount(BigDecimal insuranceAmount) {
        this.insuranceAmount = insuranceAmount;
    }

    @Column(name = "STATUS")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


}
