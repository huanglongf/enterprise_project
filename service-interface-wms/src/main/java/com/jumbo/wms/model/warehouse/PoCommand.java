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

import java.util.Date;

import com.jumbo.wms.model.BaseModel;

public class PoCommand extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = -3954000826822679637L;

    /**
     * 申请时间
     */
    private Date applyTime;

    /**
     * 编码
     */
    private String code;

    /**
     * 店铺名称
     */
    private String shopName;

    /**
     * 预计到货时间
     */
    private Date planTime;

    /**
     * 供应商名称
     */
    private String supportName;

    /**
     * 付款日期
     */
    private Date paymentDate;

    /**
     * 仓库名称
     */
    private String whName;

    /**
     * 付款类型
     */
    private String paymentType;

    /**
     * 批次号
     */
    private String batchCode;

    /**
     * 到货类型
     */
    private String arrivalType;

    /**
     * 备注
     * 
     * @return
     */
    private String remark;


    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Date getPlanTime() {
        return planTime;
    }

    public void setPlanTime(Date planTime) {
        this.planTime = planTime;
    }

    public String getSupportName() {
        return supportName;
    }

    public void setSupportName(String supportName) {
        this.supportName = supportName;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getWhName() {
        return whName;
    }

    public void setWhName(String whName) {
        this.whName = whName;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public String getArrivalType() {
        return arrivalType;
    }

    public void setArrivalType(String arrivalType) {
        this.arrivalType = arrivalType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
