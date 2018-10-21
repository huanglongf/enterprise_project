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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import com.jumbo.wms.model.BaseModel;


/**
 * 退仓拣货头表
 * 
 * @author wen
 * 
 */
@Entity
@Table(name = "T_WH_RTW_DIEKING")
public class RtwDieking extends BaseModel {


    private static final long serialVersionUID = -474394614651958969L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 拣货批次编码
     */
    private String batchCode;

    /**
     * 作业单编码
     */
    private String staCode;

    /**
     * 作业单类型
     */
    private Integer staType;

    /**
     * 作业单相关单据号
     */
    private String staRefSlipCode;

    /**
     * 店铺
     */
    private String owner;

    /**
     * 计划退仓数量
     */
    private Long planQuantity;

    /**
     * 实际退仓数量
     */
    private Long realityQuantity;

    /**
     * 批次状态
     */
    private Integer status;

    /**
     * 仓库ID
     */
    private Long mainWhId;

    /**
     * 开始拣货时间
     */
    private Date beginDiekingTime;

    /**
     * 结束拣货时间
     */
    private Date endDiekingTime;

    /**
     * 创建人ID
     */
    private Long createId;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 版本
     */
    private int version;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 当前拣货操作员
     */
    private String operatingUser;

    /**
     * 是否VAS拣货任务
     */
    private Boolean isVas = false;



    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_WH_RTW_DIEKING", sequenceName = "S_T_WH_RTW_DIEKING", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WH_RTW_DIEKING")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "BATCH_CODE")
    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    @Column(name = "STA_CODE")
    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

    @Column(name = "STA_TYPE")
    public Integer getStaType() {
        return staType;
    }

    public void setStaType(Integer staType) {
        this.staType = staType;
    }

    @Column(name = "STA_REF_SLIP_CODE")
    public String getStaRefSlipCode() {
        return staRefSlipCode;
    }

    public void setStaRefSlipCode(String staRefSlipCode) {
        this.staRefSlipCode = staRefSlipCode;
    }

    @Column(name = "OWNER")
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Column(name = "PLAN_QUANTITY")
    public Long getPlanQuantity() {
        return planQuantity;
    }

    public void setPlanQuantity(Long planQuantity) {
        this.planQuantity = planQuantity;
    }

    @Column(name = "REALITY_QUANTITY")
    public Long getRealityQuantity() {
        return realityQuantity;
    }

    public void setRealityQuantity(Long realityQuantity) {
        this.realityQuantity = realityQuantity;
    }

    @Column(name = "STATUS")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "MAIN_WH_ID")
    public Long getMainWhId() {
        return mainWhId;
    }

    public void setMainWhId(Long mainWhId) {
        this.mainWhId = mainWhId;
    }

    @Column(name = "BEGIN_DIEKING_TIME")
    public Date getBeginDiekingTime() {
        return beginDiekingTime;
    }

    public void setBeginDiekingTime(Date beginDiekingTime) {
        this.beginDiekingTime = beginDiekingTime;
    }

    @Column(name = "END_DIEKING_TIME")
    public Date getEndDiekingTime() {
        return endDiekingTime;
    }

    public void setEndDiekingTime(Date endDiekingTime) {
        this.endDiekingTime = endDiekingTime;
    }

    @Column(name = "CREATE_ID")
    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    @Column(name = "CREATE_USER")
    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    @Version
    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Column(name = "OPERATING_USER")
    public String getOperatingUser() {
        return operatingUser;
    }

    public void setOperatingUser(String operatingUser) {
        this.operatingUser = operatingUser;
    }

    @Column(name = "IS_VAS")
    public Boolean getIsVas() {
        return isVas;
    }

    public void setIsVas(Boolean isVas) {
        this.isVas = isVas;
    }



}
