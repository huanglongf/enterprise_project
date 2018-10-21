/**
 * SkuPriceImpl.java com.erry.model.it.impl
 * 
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

package com.jumbo.wms.model.vmi.itData;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;

/**
 * ClassName:SkuPriceImpl
 * 
 * @author shanshan.yu
 * @version
 * @Date 2011-9-6 02:42:49
 * 
 */
@Entity
@Table(name = "T_IT_SKU_PRICE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class SkuPrice extends BaseModel {
    /**
	 * 
	 */
    private static final long serialVersionUID = -5095313076094104709L;
    private Long id;
    private Date effDate;
    private String skuKO;
    private BigDecimal retailPrice;
    private BigDecimal proPrice; // promotional price
    private BigDecimal prePrice;// pre-sale price
    private Integer mode; // 0-seasonal price;1-promtional price;2-pre-sale price
    private Integer flag;
    private String batchNum;


    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_IT_SKU_PRICE", sequenceName = "S_IT_SKU_PRICE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_IT_SKU_PRICE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "EFFDATE")
    public Date getEffDate() {
        return effDate;
    }

    public void setEffDate(Date effDate) {
        this.effDate = effDate;
    }

    @Column(name = "SKUKO", length = 20)
    public String getSkuKO() {
        return skuKO;
    }

    public void setSkuKO(String skuKO) {
        this.skuKO = skuKO;
    }

    @Column(name = "RETAILPRICE")
    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
    }

    @Column(name = "PROPRICE")
    public BigDecimal getProPrice() {
        return proPrice;
    }

    public void setProPrice(BigDecimal proPrice) {
        this.proPrice = proPrice;
    }

    @Column(name = "PREPRICE")
    public BigDecimal getPrePrice() {
        return prePrice;
    }

    public void setPrePrice(BigDecimal prePrice) {
        this.prePrice = prePrice;
    }

    @Column(name = "PRICEMODE")
    public Integer getMode() {
        return mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }

    @Column(name = "FLAG")
    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    @Column(name = "BATCH_NUM", length = 20)
    public String getBatchNum() {
        return batchNum;
    }

    public void setBatchNum(String batchNum) {
        this.batchNum = batchNum;
    }

}
