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

/**
 * pda收货商品库位情况
 * 
 * @author fyl
 * 
 */
@Entity
@Table(name = "T_WH_PDA_SKU_LOCATION")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class PdaSkuLocation extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = -2512609766211300704L;

    /**
     * PK
     */
    private Long id;

    /**
     * 商品skuId
     */
    private Long skuId;

    /**
     * 库位id
     */
    private WarehouseLocation location;

    /**
     * 商品编码
     */
    private String code;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_PDA_SKU_LOCATION", sequenceName = "S_T_WH_PDA_SKU_LOCATION", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PDA_SKU_LOCATION")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "SKU_ID")
    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LOCATION_ID")
    @Index(name = "IDX_PDA_LOCATION")
    public WarehouseLocation getLocation() {
        return location;
    }

    public void setLocation(WarehouseLocation location) {
        this.location = location;
    }

    @Column(name = "CODE", length = 100)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
