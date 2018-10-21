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

package com.jumbo.wms.model.command;

import java.util.Date;

import com.jumbo.wms.model.warehouse.RtwDieking;


/**
 * 退仓拣货头表
 * 
 * @author wen
 * 
 */
public class RtwDiekingCommend extends RtwDieking {

    /**
     * 
     */
    private static final long serialVersionUID = 7959755374278093527L;

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
     * 库存编码
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
     * 短拣数量
     */
    private Long shortPickQty;

    /**
     * 短拣标识
     */
    private String shortPickStatus;

    /**
     * 出库单创建时间（开始）
     */
    private Date startCreateTime1;

    /**
     * 出库单创建时间（结束）
     */
    private Date EndCreateTime1;

    private String taskStatus;

    /**
     * 是否VAS拣货任务
     */
    private String vas;

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getSkuBarCode() {
        return skuBarCode;
    }

    public void setSkuBarCode(String skuBarCode) {
        this.skuBarCode = skuBarCode;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getSkuSupplierCode() {
        return skuSupplierCode;
    }

    public void setSkuSupplierCode(String skuSupplierCode) {
        this.skuSupplierCode = skuSupplierCode;
    }

    public String getSkuKeyProperties() {
        return skuKeyProperties;
    }

    public void setSkuKeyProperties(String skuKeyProperties) {
        this.skuKeyProperties = skuKeyProperties;
    }

    public String getSkuInvStatus() {
        return skuInvStatus;
    }

    public void setSkuInvStatus(String skuInvStatus) {
        this.skuInvStatus = skuInvStatus;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getDiekingAreaCode() {
        return diekingAreaCode;
    }

    public void setDiekingAreaCode(String diekingAreaCode) {
        this.diekingAreaCode = diekingAreaCode;
    }

    public String getWhAreaCode() {
        return whAreaCode;
    }

    public void setWhAreaCode(String whAreaCode) {
        this.whAreaCode = whAreaCode;
    }

    public Long getShortPickQty() {
        return shortPickQty;
    }

    public void setShortPickQty(Long shortPickQty) {
        this.shortPickQty = shortPickQty;
    }

    public String getShortPickStatus() {
        return shortPickStatus;
    }

    public void setShortPickStatus(String shortPickStatus) {
        this.shortPickStatus = shortPickStatus;
    }

    public Date getStartCreateTime1() {
        return startCreateTime1;
    }

    public void setStartCreateTime1(Date startCreateTime1) {
        this.startCreateTime1 = startCreateTime1;
    }

    public Date getEndCreateTime1() {
        return EndCreateTime1;
    }

    public void setEndCreateTime1(Date endCreateTime1) {
        EndCreateTime1 = endCreateTime1;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getVas() {
        return vas;
    }

    public void setVas(String vas) {
        this.vas = vas;
    }



}
