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

import java.math.BigDecimal;
import java.util.Date;


/**
 * 包裹Command
 * 
 * @author lzb
 * 
 */
public class TransPackageCommand extends TransPackage {

    /**
     * 
     */
    private static final long serialVersionUID = 9061110295222105118L;
    /**
     * PK
     */
    // private Long id;

    /**
     * 订单号
     */
    private String code;
    /**
     * 组织ID（仓库）
     */
    private Long ouId;

    /**
     * 业务类型
     */
    private String businessType;

    /**
     * 成本中心类型
     */
    private String costCenterType;
    /**
     * 部门/店铺
     */
    private String costCenterDetail;
    /**
     * 寄件人
     */
    private String sender;
    /**
     * ji联系方式
     */
    private String senderTel;

    /**
     * 寄国家
     */
    private String senderCountry;
    /**
     * 寄省
     */
    private String senderProvince;
    /**
     * 寄市
     */
    private String senderCity;
    /**
     * 寄区(县)
     */
    private String senderArea;
    /**
     * 寄详细地址
     */
    private String senderAddress;
    /**
     * 物流商编码
     */
    private String transportatorCode;
    /**
     * 时效类型
     */
    private String timeType;
    /**
     * 包裹数量
     */
    private int packageNum;
    /**
     * 收件人
     */
    private String receiver;
    /**
     * shou联系方式
     */
    private String receiverTel;
    /**
     * 收国家
     */
    private String receiverCountry;
    /**
     * 收省
     */
    private String receiverProvince;
    /**
     * 收市
     */
    private String receiverCity;
    /**
     * 收区(县)
     */
    private String receiverArea;
    /**
     * 收详细地址
     */
    private String receiverAddress;

    /**
     * 创建人
     */
    private String createUserId;
    /**
     * 最后修改时间
     */
    private Date lastModifyTime;
    /**
     * 是否陆运(0:否 1:是)
     */
    private int isnotLandTrans;
    /**
     * 保价金额
     */
    private BigDecimal insuranceAmount;

    /**
     * 状态(0:作废 1:正常)
     */
    // private int status;

    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 用户名
     * 
     * @return
     */
    private String userName;

    /**
     * 物流商名称
     * 
     * @return
     */
    private String tranName;

    /**
     * orderId
     * 
     * @return
     */
    private long orderId;

    /**
     * order状态
     * 
     * @return
     */
    private Integer orderStatus;

    private String remark;// 备注

    private Integer staType;// 指令类型

    private String staCode2;// 指令号
    
    private String slipCode;//相关单据号

    public Integer getStaType() {
        return staType;
    }

    public void setStaType(Integer staType) {
        this.staType = staType;
    }

    public String getStaCode2() {
        return staCode2;
    }

    public void setStaCode2(String staCode2) {
        this.staCode2 = staCode2;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getTranName() {
        return tranName;
    }

    public void setTranName(String tranName) {
        this.tranName = tranName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    // public Long getId() {
    // return id;
    // }
    //
    // public void setId(Long id) {
    // this.id = id;
    // }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getOuId() {
        return ouId;
    }

    public void setOuId(Long ouId) {
        this.ouId = ouId;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getCostCenterType() {
        return costCenterType;
    }

    public void setCostCenterType(String costCenterType) {
        this.costCenterType = costCenterType;
    }

    public String getCostCenterDetail() {
        return costCenterDetail;
    }

    public void setCostCenterDetail(String costCenterDetail) {
        this.costCenterDetail = costCenterDetail;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSenderTel() {
        return senderTel;
    }

    public void setSenderTel(String senderTel) {
        this.senderTel = senderTel;
    }

    public String getSenderCountry() {
        return senderCountry;
    }

    public void setSenderCountry(String senderCountry) {
        this.senderCountry = senderCountry;
    }

    public String getSenderProvince() {
        return senderProvince;
    }

    public void setSenderProvince(String senderProvince) {
        this.senderProvince = senderProvince;
    }

    public String getSenderCity() {
        return senderCity;
    }

    public void setSenderCity(String senderCity) {
        this.senderCity = senderCity;
    }

    public String getSenderArea() {
        return senderArea;
    }

    public void setSenderArea(String senderArea) {
        this.senderArea = senderArea;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    public String getTransportatorCode() {
        return transportatorCode;
    }

    public void setTransportatorCode(String transportatorCode) {
        this.transportatorCode = transportatorCode;
    }

    public String getTimeType() {
        return timeType;
    }

    public void setTimeType(String timeType) {
        this.timeType = timeType;
    }

    public int getPackageNum() {
        return packageNum;
    }

    public void setPackageNum(int packageNum) {
        this.packageNum = packageNum;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getReceiverTel() {
        return receiverTel;
    }

    public void setReceiverTel(String receiverTel) {
        this.receiverTel = receiverTel;
    }

    public String getReceiverCountry() {
        return receiverCountry;
    }

    public void setReceiverCountry(String receiverCountry) {
        this.receiverCountry = receiverCountry;
    }

    public String getReceiverProvince() {
        return receiverProvince;
    }

    public void setReceiverProvince(String receiverProvince) {
        this.receiverProvince = receiverProvince;
    }

    public String getReceiverCity() {
        return receiverCity;
    }

    public void setReceiverCity(String receiverCity) {
        this.receiverCity = receiverCity;
    }

    public String getReceiverArea() {
        return receiverArea;
    }

    public void setReceiverArea(String receiverArea) {
        this.receiverArea = receiverArea;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }



    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public int getIsnotLandTrans() {
        return isnotLandTrans;
    }

    public void setIsnotLandTrans(int isnotLandTrans) {
        this.isnotLandTrans = isnotLandTrans;
    }

    public BigDecimal getInsuranceAmount() {
        return insuranceAmount;
    }

    public void setInsuranceAmount(BigDecimal insuranceAmount) {
        this.insuranceAmount = insuranceAmount;
    }

    public String getSlipCode() {
        return slipCode;
    }

    public void setSlipCode(String slipCode) {
        this.slipCode = slipCode;
    }
    
    //
    // public int getStatus() {
    // return status;
    // }
    //
    // public void setStatus(int status) {
    // this.status = status;
    // }


}
