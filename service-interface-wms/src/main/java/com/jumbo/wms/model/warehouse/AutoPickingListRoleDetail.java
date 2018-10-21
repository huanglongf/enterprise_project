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
 */
package com.jumbo.wms.model.warehouse;

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
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.baseinfo.SkuCategories;

/**
 * 仓库配货清单自动创建规则明细
 * 
 * @author lichuan
 * 
 */
@Entity
@Table(name = "T_WH_AUTO_PL_ROLE_DETIAL")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class AutoPickingListRoleDetail extends BaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = -2828519273510558202L;
    /**
     * PK
     */
    private Long id;
    /**
     * 优先级
     */
    private Integer sort;
    /**
     * 批次最大订单数
     */
    private Integer maxOrder;
    /**
     * 批次最小订单数
     */
    private Integer minOrder;
    /**
     * 作业单配货类型
     */
    private StockTransApplicationPickingType pickingType;
    /**
     * 商品分类
     */
    private SkuCategories skuCategories;
    /**
     * 商品大小分类配置
     */
    private SkuSizeConfig skuSizeConfig;
    /**
     * 仓库配货清单自动创建规则
     */
    private AutoPickingListRole autoPickingListRole;
    /**
     * 优先发货城市
     */
    private String city;
    /**
     * 是否包含SN订单
     */
    private Boolean isSn;
    /**
     * 是否运单后置  必填，是：混物流配，否：单物流配
     */
    private Boolean isTransAfter;
    
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_APLRD", sequenceName = "S_T_WH_AUTO_PL_ROLE_DETIAL", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_APLRD")
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

    @Column(name = "MAX_ORDER")
    public Integer getMaxOrder() {
        return maxOrder;
    }

    public void setMaxOrder(Integer maxOrder) {
        this.maxOrder = maxOrder;
    }

    @Column(name = "MIN_ORDER")
    public Integer getMinOrder() {
        return minOrder;
    }

    public void setMinOrder(Integer minOrder) {
        this.minOrder = minOrder;
    }

    @Column(name = "PICKING_TYPE", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.StockTransApplicationPickingType")})
    public StockTransApplicationPickingType getPickingType() {
        return pickingType;
    }

    public void setPickingType(StockTransApplicationPickingType pickingType) {
        this.pickingType = pickingType;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SKU_CATEGORY_ID")
    public SkuCategories getSkuCategories() {
        return skuCategories;
    }

    public void setSkuCategories(SkuCategories skuCategories) {
        this.skuCategories = skuCategories;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SKU_SIZE_CONFIG_ID")
    public SkuSizeConfig getSkuSizeConfig() {
        return skuSizeConfig;
    }

    public void setSkuSizeConfig(SkuSizeConfig skuSizeConfig) {
        this.skuSizeConfig = skuSizeConfig;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROLE_ID")
    public AutoPickingListRole getAutoPickingListRole() {
        return autoPickingListRole;
    }

    public void setAutoPickingListRole(AutoPickingListRole autoPickingListRole) {
        this.autoPickingListRole = autoPickingListRole;
    }

    @Column(name = "SEND_CITY", length = 100)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    @Column(name = "IS_TRANS_AFTER")
    public Boolean getIsTransAfter() {
        return isTransAfter;
    }

    public void setIsTransAfter(Boolean isTransAfter) {
        this.isTransAfter = isTransAfter;
    }
    @Column(name="IS_SN")
    public Boolean getIsSn() {
        return isSn;
    }

    public void setIsSn(Boolean isSn) {
        this.isSn = isSn;
    }
}
