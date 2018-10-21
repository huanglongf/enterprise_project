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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import com.jumbo.wms.model.BaseModel;


/**
 * 退仓拣货明细表
 * 
 * @author wen
 * 
 */
@Entity
@Table(name = "T_WH_RTW_DIEKING_LINE")
public class RtwDiekingLine extends BaseModel {


    /**
     * 
     */
    private static final long serialVersionUID = -3875368769511164284L;
    /**
     * 主键
     */
    private Long id;

    /**
     * 退仓拣货头表ID
     */
    private Long rtwDiekingId;

    /**
     * 商品ID
     */
    private Long skuId;

    /**
     * 商品编码
     */
    private String skuCode;

    /**
     * 商品条码
     */
    private String skuBarCode;

    /**
     * 商品名称
     */
    private String skuName;

    /**
     * 货号
     */
    private String skuSupplierCode;

    /**
     * 商品关键属性
     */
    private String skuKeyProperties;

    /**
     * 库存状态
     */
    private String skuInvStatus;

    /**
     * 库位ID
     */
    private Long locationId;

    /**
     * 库位编码
     */
    private String locationCode;

    /**
     * 拣货区域编码
     */
    private String diekingAreaCode;

    /**
     * 仓库区域编码
     */
    private String whAreaCode;

    /**
     * 计划退仓数量
     */
    private Long planQuantity;

    /**
     * 实际退仓数量
     */
    private Long realityQuantity;

    /**
     * 仓库ID
     */
    private Long mainWhId;

    /**
     * 拣货顺序
     */
    private Integer sort;

    /**
     * 版本
     */
    private int version;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_WH_RTW_DIEKING_LINE", sequenceName = "S_T_WH_RTW_DIEKING_LINE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WH_RTW_DIEKING_LINE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "RTW_DIEKING_ID")
    public Long getRtwDiekingId() {
        return rtwDiekingId;
    }

    public void setRtwDiekingId(Long rtwDiekingId) {
        this.rtwDiekingId = rtwDiekingId;
    }

    @Column(name = "SKU_CODE")
    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    @Column(name = "SKU_BAR_CODE")
    public String getSkuBarCode() {
        return skuBarCode;
    }

    public void setSkuBarCode(String skuBarCode) {
        this.skuBarCode = skuBarCode;
    }

    @Column(name = "SKU_NAME")
    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    @Column(name = "SKU_SUPPLIER_CODE")
    public String getSkuSupplierCode() {
        return skuSupplierCode;
    }

    public void setSkuSupplierCode(String skuSupplierCode) {
        this.skuSupplierCode = skuSupplierCode;
    }

    @Column(name = "SKU_KEY_PROPERTIES")
    public String getSkuKeyProperties() {
        return skuKeyProperties;
    }

    public void setSkuKeyProperties(String skuKeyProperties) {
        this.skuKeyProperties = skuKeyProperties;
    }

    @Column(name = "SKU_INV_STATUS")
    public String getSkuInvStatus() {
        return skuInvStatus;
    }

    public void setSkuInvStatus(String skuInvStatus) {
        this.skuInvStatus = skuInvStatus;
    }

    @Column(name = "LOCATION_CODE")
    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    @Column(name = "DIEKING_AREA_CODE")
    public String getDiekingAreaCode() {
        return diekingAreaCode;
    }

    public void setDiekingAreaCode(String diekingAreaCode) {
        this.diekingAreaCode = diekingAreaCode;
    }

    @Column(name = "WH_AREA_CODE")
    public String getWhAreaCode() {
        return whAreaCode;
    }

    public void setWhAreaCode(String whAreaCode) {
        this.whAreaCode = whAreaCode;
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

    @Column(name = "MAIN_WH_ID")
    public Long getMainWhId() {
        return mainWhId;
    }

    public void setMainWhId(Long mainWhId) {
        this.mainWhId = mainWhId;
    }

    @Column(name = "SORT")
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Version
    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Column(name = "SKU_ID")
    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    @Column(name = "LOCATION_ID")
    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }



}
