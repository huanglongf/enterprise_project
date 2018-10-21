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
import com.jumbo.wms.model.baseinfo.Sku;

/**
 * 商品标签
 * 
 * @author lingyun.dai
 * 
 */
@Entity
@Table(name = "T_BI_SKU_TAG_REF")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class SkuTagRef extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = -4602269987309164588L;

    /**
     * PK
     */
    private Long id;

    /**
     * 商品
     */
    private Sku sku;

    /**
     * 商品标签
     */
    private SkuTag skuTag;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_SKU_TAG_REF", sequenceName = "S_T_BI_SKU_TAG_REF", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SKU_TAG_REF")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SKU_ID")
    public Sku getSku() {
        return sku;
    }

    public void setSku(Sku sku) {
        this.sku = sku;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SKU_TAG_ID")
    public SkuTag getSkuTag() {
        return skuTag;
    }

    public void setSkuTag(SkuTag skuTag) {
        this.skuTag = skuTag;
    }
}
