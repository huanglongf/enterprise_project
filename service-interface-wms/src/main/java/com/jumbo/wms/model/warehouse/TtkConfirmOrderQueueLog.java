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
package com.jumbo.wms.model.warehouse;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.jumbo.wms.model.BaseModel;

/**
 * @author lichuan
 * 
 */
@Entity
@Table(name = "T_TTK_CONFIRM_ORDER_QUEUE_LOG")
public class TtkConfirmOrderQueueLog extends BaseModel {

    private static final long serialVersionUID = -4435253452810502502L;
    /**
     * PK
     */
    private Long id;
    /**
     * 作业单号
     */
    private String staCode;
    /**
     * 执行回传次数
     */
    private Long exeCount;
    /**
     * 填写公司名称简称
     */
    private String ecCompanyId;
    /**
     * 固定为TTKDEX
     */
    private String logisticProviderID;
    /**
     * VIP客户编号
     */
    private String customerId;
    /**
     * 网点名称(合作网点)
     */
    private String siteName;
    /**
     * 订单号，必填且唯一
     */
    private String txLogisticID;
    /**
     * 业务交易号（可选）
     */
    private String tradeNo;
    /**
     * 物流运单号
     */
    private String mailNo;
    /**
     * 订单类型(0-COD 1-普通订单 3 - 退货单)
     */
    private Integer orderType;
    /**
     * 服务类型(0-自己联系 1-在线下单（上门揽收）4-限时物流 8-快捷COD10-我要寄快递16-快递保障)
     */
    private Long serviceType;
    /**
     * 用户姓名
     */
    private String name;
    /**
     * 用户邮编
     */
    private String postCode;
    /**
     * 用户电话
     */
    private String phone;
    /**
     * 用户移动电话
     */
    private String mobile;
    /**
     * 用户所在省
     */
    private String prov;
    /**
     * 用户所在市
     */
    private String city;
    /**
     * 用户所在区
     */
    private String area;
    /**
     * 用户详细地址
     */
    private String address;
    /**
     * 物流公司上门取货时间段
     */
    private Date sendStartTime;
    /**
     * 物流公司上门取货时间段
     */
    private Date sendEndTime;
    /**
     * 商品金额，但无服务费；(单位：分)
     */
    private BigDecimal goodsValue;
    /**
     * 商品金额+服务费；(单位：分)
     */
    private BigDecimal itemsValue;
    /**
     * 商品名称
     */
    private String itemName;
    /**
     * 商品数量
     */
    private Integer number;
    /**
     * 商品单价（单位：分）
     */
    private BigDecimal itemValue;
    /**
     * 商品类型（保留字段，暂时不用）
     */
    private Integer special;
    /**
     * 备注
     */
    private String remark;
    /**
     * 总服务费[COD]；(单位：分)
     */
    private BigDecimal totalServiceFee;
    /**
     * 买家服务费[COD]；(单位：分)
     */
    private BigDecimal buyServiceFee;
    /**
     * 物流公司分润[COD]；(单位：分)
     */
    private BigDecimal codSplitFee;
    /**
     * 订单信息回传完成时间
     */
    private Date finishTime;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_TTK_CONFIRM_ORDER_Q_LOG", sequenceName = "S_T_TTK_CONFIRM_ORDER_Q_LOG", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TTK_CONFIRM_ORDER_Q_LOG")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "STA_CODE")
    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

    @Column(name = "EXE_COUNT")
    public Long getExeCount() {
        return exeCount;
    }

    public void setExeCount(Long exeCount) {
        this.exeCount = exeCount;
    }

    @Column(name = "EC_COMPANY_ID", length = 64)
    public String getEcCompanyId() {
        return ecCompanyId;
    }

    public void setEcCompanyId(String ecCompanyId) {
        this.ecCompanyId = ecCompanyId;
    }

    @Column(name = "LOGISTIC_PROVIDER_ID", length = 64)
    public String getLogisticProviderID() {
        return logisticProviderID;
    }

    public void setLogisticProviderID(String logisticProviderID) {
        this.logisticProviderID = logisticProviderID;
    }

    @Column(name = "CUSTOMER_ID", length = 64)
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Column(name = "SITE_NAME", length = 64)
    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    @Column(name = "TX_LOGISTIC_ID", length = 64)
    public String getTxLogisticID() {
        return txLogisticID;
    }

    public void setTxLogisticID(String txLogisticID) {
        this.txLogisticID = txLogisticID;
    }

    @Column(name = "TRADE_NO", length = 64)
    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    @Column(name = "MAIL_NO", length = 64)
    public String getMailNo() {
        return mailNo;
    }

    public void setMailNo(String mailNo) {
        this.mailNo = mailNo;
    }

    @Column(name = "ORDER_TYPE", columnDefinition = "integer")
    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    @Column(name = "SERVICE_TYPE")
    public Long getServiceType() {
        return serviceType;
    }

    public void setServiceType(Long serviceType) {
        this.serviceType = serviceType;
    }

    @Column(name = "USER_NAME", length = 32)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "POST_CODE", length = 32)
    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    @Column(name = "PHONE", length = 32)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "MOBILE", length = 32)
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Column(name = "PROV", length = 32)
    public String getProv() {
        return prov;
    }

    public void setProv(String prov) {
        this.prov = prov;
    }

    @Column(name = "CITY", length = 32)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "AREA", length = 32)
    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Column(name = "ADDRESS", length = 256)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "SEND_START_TIME")
    public Date getSendStartTime() {
        return sendStartTime;
    }

    public void setSendStartTime(Date sendStartTime) {
        this.sendStartTime = sendStartTime;
    }

    @Column(name = "SEND_END_TIME")
    public Date getSendEndTime() {
        return sendEndTime;
    }

    public void setSendEndTime(Date sendEndTime) {
        this.sendEndTime = sendEndTime;
    }

    @Column(name = "GOODS_VALUE")
    public BigDecimal getGoodsValue() {
        return goodsValue;
    }

    public void setGoodsValue(BigDecimal goodsValue) {
        this.goodsValue = goodsValue;
    }

    @Column(name = "ITEMS_VALUE")
    public BigDecimal getItemsValue() {
        return itemsValue;
    }

    public void setItemsValue(BigDecimal itemsValue) {
        this.itemsValue = itemsValue;
    }

    @Column(name = "ITEM_NAME", length = 256)
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @Column(name = "NUMBERS", columnDefinition = "integer")
    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Column(name = "ITEM_VALUE")
    public BigDecimal getItemValue() {
        return itemValue;
    }

    public void setItemValue(BigDecimal itemValue) {
        this.itemValue = itemValue;
    }

    @Column(name = "SPECIAL", columnDefinition = "integer")
    public Integer getSpecial() {
        return special;
    }

    public void setSpecial(Integer special) {
        this.special = special;
    }

    @Column(name = "REMARK", length = 512)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "TOTAL_SERVICE_FEE")
    public BigDecimal getTotalServiceFee() {
        return totalServiceFee;
    }

    public void setTotalServiceFee(BigDecimal totalServiceFee) {
        this.totalServiceFee = totalServiceFee;
    }

    @Column(name = "BUY_SERVICE_FEE")
    public BigDecimal getBuyServiceFee() {
        return buyServiceFee;
    }

    public void setBuyServiceFee(BigDecimal buyServiceFee) {
        this.buyServiceFee = buyServiceFee;
    }

    @Column(name = "COD_SPLIT_FEE")
    public BigDecimal getCodSplitFee() {
        return codSplitFee;
    }

    public void setCodSplitFee(BigDecimal codSplitFee) {
        this.codSplitFee = codSplitFee;
    }

    @Column(name = "FINISH_TIME")
    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

}
