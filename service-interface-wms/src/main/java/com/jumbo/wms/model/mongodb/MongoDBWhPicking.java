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
package com.jumbo.wms.model.mongodb;

import java.util.Set;

import com.jumbo.wms.model.BaseModel;

/**
 * @author lihui
 * 
 */
public class MongoDBWhPicking extends BaseModel {

    private static final long serialVersionUID = -4616456349238774277L;

    private Long id;

    /**
     * 扫描条码
     */
    private String barCode;
    /**
     * 拣货单号
     */
    private String code;
    /**
     * 库位编码
     */
    private String locationCode;
    /**
     * 库位ID
     */
    private Long locationId;
    /**
     * 商品条码列表
     */
    private Set<String> barcodes;
    /**
     * 商品ID
     */
    private Long skuId;
    /**
     * 剩余需要拣货数量
     */
    private Long qty;
    /**
     * 0:拣货；1:暂停拣货
     */
    private int status;

    /**
     * 排序
     */
    private Integer sort;
    /**
     * 货号
     */
    private String supplierCode;
    /**
     * 属性
     */
    private String keyProperties;
    /**
     * 过期时间
     */
    private String expDate;
    /**
     * 商品名称
     */
    private String skuName;

    /**
     * 计划数量
     */
    private Long planQty;


    private Long pickingLineId;

    private Integer locSort;

    /**
     * 库存状态
     */
    private String skuStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }



    public Set<String> getBarcodes() {
        return barcodes;
    }

    public void setBarcodes(Set<String> barcodes) {
        this.barcodes = barcodes;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getKeyProperties() {
        return keyProperties;
    }

    public void setKeyProperties(String keyProperties) {
        this.keyProperties = keyProperties;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public Long getPlanQty() {
        return planQty;
    }

    public void setPlanQty(Long planQty) {
        this.planQty = planQty;
    }

    public Long getPickingLineId() {
        return pickingLineId;
    }

    public void setPickingLineId(Long pickingLineId) {
        this.pickingLineId = pickingLineId;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public Integer getLocSort() {
        return locSort;
    }

    public void setLocSort(Integer locSort) {
        this.locSort = locSort;
    }

    public String getSkuStatus() {
        return skuStatus;
    }

    public void setSkuStatus(String skuStatus) {
        this.skuStatus = skuStatus;
    }


}
