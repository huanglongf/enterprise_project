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

import com.jumbo.wms.model.BaseModel;

/**
 * 仓库作业申请单，所有仓库作业最终都表现为此作业单
 * 
 * @author wanghua
 * 
 */
public class StockTransApplicationExtInfoCommand extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 3871175433413527109L;
    private String transNo;
    private String lpcode;
    /**
     * 是否需要发票
     */
    private Boolean isNeedInvoice;
    /**
     * 退货原因
     */
    private String returnReasonMemo;
    private Boolean isPDA;
    /**
     * 已经收货未上架的作业明细单
     */
    private Long stvId;

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    public String getLpcode() {
        return lpcode;
    }

    public void setLpcode(String lpcode) {
        this.lpcode = lpcode;
    }

    public Boolean getIsNeedInvoice() {
        return isNeedInvoice;
    }

    public void setIsNeedInvoice(Boolean isNeedInvoice) {
        this.isNeedInvoice = isNeedInvoice;
    }

    public String getReturnReasonMemo() {
        return returnReasonMemo;
    }

    public void setReturnReasonMemo(String returnReasonMemo) {
        this.returnReasonMemo = returnReasonMemo;
    }

    public Boolean getIsPDA() {
        return isPDA;
    }

    public void setIsPDA(Boolean isPDA) {
        this.isPDA = isPDA;
    }

    public Long getStvId() {
        return stvId;
    }

    public void setStvId(Long stvId) {
        this.stvId = stvId;
    }

    public Long getSkuQty() {
        return skuQty;
    }

    public void setSkuQty(Long skuQty) {
        this.skuQty = skuQty;
    }

    public Long getStvMode() {
        return stvMode;
    }

    public void setStvMode(Long stvMode) {
        this.stvMode = stvMode;
    }

    private Long skuQty;
    /**
     * 已经收货未上架的作业明细单的上架模式
     */
    private Long stvMode;
}
