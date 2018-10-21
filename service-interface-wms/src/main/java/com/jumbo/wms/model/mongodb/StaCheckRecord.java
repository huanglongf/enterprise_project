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

import com.jumbo.wms.model.BaseModel;

/**
 * @author lihui
 * 
 */
public class StaCheckRecord extends BaseModel {
    private static final long serialVersionUID = -2242285706009860319L;
    private String staCode;
    private String ruleCode;
    private String pgIndex;
    private Long staLineId;
    private String skuBarcode;
    private String skuCode;
    private String skuName;
    private Integer intStatus;
    private String statusS;
    private Long staId;

    /**
     * 总数量
     */
    private Integer qty;
    /**
     * 已核对数量
     */
    private Integer completeQty;

    /**
     * 行取消的数量
     */
    private Integer cancelQty;

    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

    public String getRuleCode() {
        return ruleCode;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }

    public String getPgIndex() {
        return pgIndex;
    }

    public void setPgIndex(String pgIndex) {
        this.pgIndex = pgIndex;
    }

    public Long getStaLineId() {
        return staLineId;
    }

    public void setStaLineId(Long staLineId) {
        this.staLineId = staLineId;
    }

    public String getSkuBarcode() {
        return skuBarcode;
    }

    public void setSkuBarcode(String skuBarcode) {
        this.skuBarcode = skuBarcode;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Integer getCompleteQty() {
        return completeQty;
    }

    public void setCompleteQty(Integer completeQty) {
        this.completeQty = completeQty;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public Integer getIntStatus() {
        return intStatus;
    }

    public void setIntStatus(Integer intStatus) {
        this.intStatus = intStatus;
    }

    public String getStatusS() {
        return statusS;
    }

    public void setStatusS(String statusS) {
        this.statusS = statusS;
    }

    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

    public Integer getCancelQty() {
        return cancelQty;
    }

    public void setCancelQty(Integer cancelQty) {
        this.cancelQty = cancelQty;
    }


}