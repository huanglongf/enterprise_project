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

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.Sku;

/**
 * 团购商品计时器表
 * 
 * @author fanht
 * 
 */
@Entity
@Table(name = "T_WH_GRPBUY_SKU_COUNTER")
public class GroupBuyingSkuCounter extends BaseModel {
    private static final long serialVersionUID = -5503361059847810751L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 相关仓库组织
     */
    private OperationUnit ou;

    /**
     * 单件订单计数器
     */
    private Long singelOrderQty;

    /**
     * 单品订单计数器
     */
    private Long singelGoodsQty;

    /**
     * STA主键
     */
    private Long staId;

    /**
     * sku
     */
    private Sku sku;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_GRPCOT", sequenceName = "S_T_WH_GRPBUY_SKU_COUNTER", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GRPCOT")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OU_ID")
    @Index(name = "IDX_GRPCOT_OU")
    public OperationUnit getOu() {
        return ou;
    }

    public void setOu(OperationUnit ou) {
        this.ou = ou;
    }

    @Column(name = "SINGEL_ORDER_QTY")
    public Long getSingelOrderQty() {
        return singelOrderQty;
    }

    public void setSingelOrderQty(Long singelOrderQty) {
        this.singelOrderQty = singelOrderQty;
    }

    @Column(name = "SINGEL_GOODS_QTY")
    public Long getSingelGoodsQty() {
        return singelGoodsQty;
    }

    public void setSingelGoodsQty(Long singelGoodsQty) {
        this.singelGoodsQty = singelGoodsQty;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SKU_ID")
    @Index(name = "IDX_GRPCOT_SKU")
    public Sku getSku() {
        return sku;
    }

    public void setSku(Sku sku) {
        this.sku = sku;
    }

    @Column(name = "STA_ID")
    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

}
