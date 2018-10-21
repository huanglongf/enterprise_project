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

package com.jumbo.wms.model.baseinfo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

import com.jumbo.wms.model.BaseModel;

/**
 * 商品分类
 * 
 * @author fanht
 * 
 */
@Entity
@Table(name = "T_MA_SKU_CATEGORIES")
public class SkuCategories extends BaseModel {
    private static final long serialVersionUID = -769507239268171181L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 父节点
     */
    private SkuCategories skuCategories;

    /**
     * 是否是配货时显示分类
     */
    private Boolean isPickingCategories;

    /**
     * 分类数量
     */
    private Long sedPickingskuQty;

    /**
     * 分类名称
     */
    private String skuCategoriesName;
    /**
     * 子类
     */
    private List<SkuCategories> childrenSkuCategoriesList = new ArrayList<SkuCategories>();

    /**
     * 商品小批次容量（自动化）
     */
    private Integer skuMaxLimit;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_SKU_CATEGORIES", sequenceName = "S_T_MA_SKU_CATEGORIES", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SKU_CATEGORIES")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_SKU_CATEGORIES_ID")
    @Index(name = "IDX_PARENT_SKU_CATEGORIES")
    public SkuCategories getSkuCategories() {
        return skuCategories;
    }

    public void setSkuCategories(SkuCategories skuCategories) {
        this.skuCategories = skuCategories;
    }

    @Column(name = "IS_PICKING_CATEGORIES")
    public Boolean getIsPickingCategories() {
        return isPickingCategories;
    }

    public void setIsPickingCategories(Boolean isPickingCategories) {
        this.isPickingCategories = isPickingCategories;
    }

    @Column(name = "SED_PICKINGSKU_QTY")
    public Long getSedPickingskuQty() {
        return sedPickingskuQty;
    }

    public void setSedPickingskuQty(Long sedPickingskuQty) {
        this.sedPickingskuQty = sedPickingskuQty;
    }

    @Column(name = "SKU_CATEGORIES_NAME", length = 100)
    public String getSkuCategoriesName() {
        return skuCategoriesName;
    }

    public void setSkuCategoriesName(String skuCategoriesName) {
        this.skuCategoriesName = skuCategoriesName;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "skuCategories", orphanRemoval = true)
    @OrderBy("id")
    public List<SkuCategories> getChildrenSkuCategoriesList() {
        return childrenSkuCategoriesList;
    }

    public void setChildrenSkuCategoriesList(List<SkuCategories> childrenSkuCategoriesList) {
        this.childrenSkuCategoriesList = childrenSkuCategoriesList;
    }

    @Column(name = "sku_max_limit", length = 6)
    public Integer getSkuMaxLimit() {
        return skuMaxLimit;
    }

    public void setSkuMaxLimit(Integer skuMaxLimit) {
        this.skuMaxLimit = skuMaxLimit;
    }



}
