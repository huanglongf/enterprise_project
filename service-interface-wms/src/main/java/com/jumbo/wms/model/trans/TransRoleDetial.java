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

package com.jumbo.wms.model.trans;


import java.math.BigDecimal;

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

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;

/**
 * 物流推荐规则明细
 * 
 * @author lingyun.dai
 * 
 */
@Entity
@Table(name = "T_BI_TRANS_ROLE_DETIAL")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class TransRoleDetial extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 503343371759238107L;

    /**
     * PK
     */
    private Long id;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 最小金额
     */
    private BigDecimal minAmount;

    /**
     * 最大金额
     */
    private BigDecimal maxWeight;

    /**
     * 最小金额
     */
    private BigDecimal minWeight;

    /**
     * 最大金额
     */
    private BigDecimal maxAmount;

    /**
     * 配送范围组
     */
    private TransAreaGroup areaGroup;

    /**
     * * 是否COD
     */
    private Boolean isCod;

    /**
     * 规则
     */
    private TransRole transRole;

    /**
     * 排除关键字
     * 
     * @return
     */
    private String removeKeyword;

    /**
     * 时效类型
     * 
     * @return
     */
    private Integer timeType;


    @Column(name = "TIME_TYPE")
    public Integer getTimeType() {
        return timeType;
    }

    public void setTimeType(Integer timeType) {
        this.timeType = timeType;
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_TRANS_ROLE_DETIAL", sequenceName = "S_T_BI_TRANS_ROLE_DETIAL", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TRANS_ROLE_DETIAL")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "SORT")
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Column(name = "MIN_AMOUNT")
    public BigDecimal getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(BigDecimal minAmount) {
        this.minAmount = minAmount;
    }

    @Column(name = "MAX_AMOUNT")
    public BigDecimal getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(BigDecimal maxAmount) {
        this.maxAmount = maxAmount;
    }

    @Column(name = "MAX_WEIGHT")
    public BigDecimal getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(BigDecimal maxWeight) {
        this.maxWeight = maxWeight;
    }

    @Column(name = "MIN_WEIGHT")
    public BigDecimal getMinWeight() {
        return minWeight;
    }

    public void setMinWeight(BigDecimal minWeight) {
        this.minWeight = minWeight;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRANS_AREA_GROUP_ID")
    public TransAreaGroup getAreaGroup() {
        return areaGroup;
    }

    public void setAreaGroup(TransAreaGroup areaGroup) {
        this.areaGroup = areaGroup;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRANS_ROLE_ID")
    public TransRole getTransRole() {
        return transRole;
    }

    public void setTransRole(TransRole transRole) {
        this.transRole = transRole;
    }

    @Column(name = "IS_COD")
    public Boolean getIsCod() {
        return isCod;
    }

    public void setIsCod(Boolean isCod) {
        this.isCod = isCod;
    }

    @Column(name = "REMOVE_KEYWORD")
    public String getRemoveKeyword() {
        return removeKeyword;
    }

    public void setRemoveKeyword(String removeKeyword) {
        this.removeKeyword = removeKeyword;
    }
}
