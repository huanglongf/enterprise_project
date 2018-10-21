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


public class SkuSnLogCommand extends SkuSnLog {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1077373329100616340L;
    /**
     * 商品名称
     */
    private String skuName;
    /**
     * 品牌
     */
    private String brandName;
    /**
     * 供应商
     */
    private String supportName;
    /**
     * 商品条码
     */
    private String barcode;
    /**
     * po
     */
    private String pocode;

    /**
     * 入库时间
     */
    private Date inboundTime;

    /**
     * so
     */
    private String socode;

    /**
     * 外部订单号
     */
    private String outSoCode;

    /**
     * 出库时间
     */
    private Date outboundTime;
    /**
     * 状态
     */
    private String status;
    /**
     * JMSKUCODE
     */
    private String skuCode;

    /**
     * 作业方向
     */
    private Integer directionInt;

    /**
     * 作业方向
     */
    private String directionString;

    /**
     * 作业单号
     */
    private String staCode;

    /**
     * 盘点号
     */
    private String invCkCode;

    /**
     * 盘点号
     */
    private String jmCode;

    private String slipCode;

    private Integer staType;

    private Long skuId;

    private Integer intCardStatus;

    private Long staId;


    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getSupportName() {
        return supportName;
    }

    public void setSupportName(String supportName) {
        this.supportName = supportName;
    }

    public String getPocode() {
        return pocode;
    }

    public void setPocode(String pocode) {
        this.pocode = pocode;
    }

    public Date getInboundTime() {
        return inboundTime;
    }

    public void setInboundTime(Date inboundTime) {
        this.inboundTime = inboundTime;
    }

    public String getSocode() {
        return socode;
    }

    public void setSocode(String socode) {
        this.socode = socode;
    }

    public String getOutSoCode() {
        return outSoCode;
    }

    public void setOutSoCode(String outSoCode) {
        this.outSoCode = outSoCode;
    }

    public Date getOutboundTime() {
        return outboundTime;
    }

    public void setOutboundTime(Date outboundTime) {
        this.outboundTime = outboundTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

    public Integer getDirectionInt() {
        return directionInt;
    }

    public void setDirectionInt(Integer directionInt) {
        this.directionInt = directionInt;
    }

    public String getDirectionString() {
        return directionString;
    }

    public void setDirectionString(String directionString) {
        this.directionString = directionString;
    }

    public String getInvCkCode() {
        return invCkCode;
    }

    public void setInvCkCode(String invCkCode) {
        this.invCkCode = invCkCode;
    }

    public String getJmCode() {
        return jmCode;
    }

    public void setJmCode(String jmCode) {
        this.jmCode = jmCode;
    }


    public String getSlipCode() {
        return slipCode;
    }

    public void setSlipCode(String slipCode) {
        this.slipCode = slipCode;
    }

    public Integer getStaType() {
        return staType;
    }

    public void setStaType(Integer staType) {
        this.staType = staType;
    }
    @Override
    public Long getSkuId() {
        return skuId;
    }
    @Override
    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Integer getIntCardStatus() {
        return intCardStatus;
    }

    public void setIntCardStatus(Integer intCardStatus) {
        this.intCardStatus = intCardStatus;
    }

}
