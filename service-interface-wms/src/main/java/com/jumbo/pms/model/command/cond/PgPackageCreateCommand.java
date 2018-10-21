/**
 * Copyright (c) 2015 Jumbomart All Rights Reserved.
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
package com.jumbo.pms.model.command.cond;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 此类是SD jar中的包裹快照类
 * @author Double S
 *
 */
public class PgPackageCreateCommand implements Serializable {


    /**
     * 
     */
    private static final long serialVersionUID = 8241156835563847608L;
    /** 平台OR商城订单号 */
    private String orderCode;
    /** 对接单号(OMS订单号，子订单号) */
    private String omsCode;
    /** 退货单号 */
    private String roCode;
    /** 来源系统(TOMS、GOMS等) */
    private String sourceSys;
    /** 业务店铺CODE(渠道编码) */
    private String shopCode;
    /** 是否货到付款订单 */
    private Boolean isCod;
    /** 下单时间 */
    private Date platCreateTime;
    
    /** 门店编码 */
    private String storeCode;
    /** 业务服务对接码（必须唯一） */
    private String extCode;
    /** 包裹费用 */
    private BigDecimal totalCharges;
    /** 商品数量 */
    private Integer totalQty;
    /** 物流商编码 */
    private String lpCode;
    /** 物流商名称 */
    private String lpName;
    /** 运单号 */
    private String trackNo;
    /** 包裹创建时间 */
    private Date createTime;
    /** 发货时间 */
    private Date shipTime;
    /** 收件人 */
    private String receiver;
    /** 收件人手机号 */
    private String receiverPhone;
    /** 收件人邮箱 */
    private String receiverEmail;
    /** 包裹来源编码(门店OR大仓) */
    private String sourceCode;
    /** 派件时间类型 */
    private Integer transtimeType;

    public PgPackageCreateCommand() { }

    public Integer getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(Integer totalQty) {
        this.totalQty = totalQty;
    }

    public Date getPlatCreateTime() {
        return platCreateTime;
    }

    public void setPlatCreateTime(Date platCreateTime) {
        this.platCreateTime = platCreateTime;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public String getOmsCode() {
        return omsCode;
    }

    public String getRoCode() {
        return roCode;
    }

    public String getSourceSys() {
        return sourceSys;
    }

    public String getShopCode() {
        return shopCode;
    }

    public Boolean getIsCod() {
        return isCod;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public String getExtCode() {
        return extCode;
    }

    public BigDecimal getTotalCharges() {
        return totalCharges;
    }

    public String getLpCode() {
        return lpCode;
    }

    public String getLpName() {
        return lpName;
    }

    public String getTrackNo() {
        return trackNo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public Integer getTranstimeType() {
        return transtimeType;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public void setOmsCode(String omsCode) {
        this.omsCode = omsCode;
    }

    public void setRoCode(String roCode) {
        this.roCode = roCode;
    }

    public void setSourceSys(String sourceSys) {
        this.sourceSys = sourceSys;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public void setIsCod(Boolean isCod) {
        this.isCod = isCod;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public void setExtCode(String extCode) {
        this.extCode = extCode;
    }

    public void setTotalCharges(BigDecimal totalCharges) {
        this.totalCharges = totalCharges;
    }

    public void setLpCode(String lpCode) {
        this.lpCode = lpCode;
    }

    public void setLpName(String lpName) {
        this.lpName = lpName;
    }

    public void setTrackNo(String trackNo) {
        this.trackNo = trackNo;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public void setTranstimeType(Integer transtimeType) {
        this.transtimeType = transtimeType;
    }

    public Date getShipTime() {
        return shipTime;
    }

    public void setShipTime(Date shipTime) {
        this.shipTime = shipTime;
    }

    public String getReceiverEmail() {
        return receiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }

}
