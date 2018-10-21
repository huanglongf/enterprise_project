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

import com.jumbo.util.StringUtils;

public class HandOverListCommand extends HandOverList {

    /**
	 * 
	 */
    private static final long serialVersionUID = -1645380586127176150L;

    /**
     * 物流供应商名称
     */
    private String expName;

    /**
     * 已交接数量
     */
    private Integer handoveredQty;

    /**
     * 未交接数量
     */
    private Integer unHandoverQty;


    /**
     * 创建起始时间
     */
    private Date createStratTime;

    /**
     * 创建结束时间
     */
    private Date createEndTime;

    /**
     * 交接起始时间
     */
    private Date handOverStartTime;

    /**
     * 交接结束时间
     */
    private Date handOverEndTime;

    /**
     * 状态
     */
    private Integer hoIntStatus;

    /**
     * 快递单号
     */
    private String trackingNo;

    /**
     * 用户名
     */
    private String userName;
    /*
     * 新增String类型时间字段，辅助完成查询时间精确到时分秒
     */
    private String createStratTime1;
    private String handOverStartTime1;
    private String createEndTime1;
    private String handOverEndTime1;

    private Long modifierId;

    private Long operatorId;

    private Long ouId;

    public String getExpName() {
        return expName;
    }

    public void setExpName(String expName) {
        this.expName = expName;
    }

    public Date getCreateStratTime() {
        return createStratTime;
    }

    public void setCreateStratTime(Date createStratTime) {
        this.createStratTime = createStratTime;
    }

    public Date getCreateEndTime() {
        return createEndTime;
    }

    public void setCreateEndTime(Date createEndTime) {
        this.createEndTime = createEndTime;
    }

    public Date getHandOverStartTime() {
        return handOverStartTime;
    }

    public void setHandOverStartTime(Date handOverStartTime) {
        this.handOverStartTime = handOverStartTime;
    }

    public Date getHandOverEndTime() {
        return handOverEndTime;
    }

    public void setHandOverEndTime(Date handOverEndTime) {
        this.handOverEndTime = handOverEndTime;
    }

    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    public Integer getHoIntStatus() {
        return hoIntStatus;
    }

    public void setHoIntStatus(Integer hoIntStatus) {
        this.hoIntStatus = hoIntStatus;
    }

    public Integer getHandoveredQty() {
        return handoveredQty;
    }

    public void setHandoveredQty(Integer handoveredQty) {
        this.handoveredQty = handoveredQty;
    }

    public Integer getUnHandoverQty() {
        return unHandoverQty;
    }

    public void setUnHandoverQty(Integer unHandoverQty) {
        this.unHandoverQty = unHandoverQty;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setQueryLikeParam() {
        if (!StringUtils.hasText(this.getLpcode())) {
            this.setLpcode(null);
        }
        if (StringUtils.hasText(trackingNo)) {
            this.setTrackingNo(trackingNo + "%");
        } else {
            this.setTrackingNo(null);
        }
        if (StringUtils.hasText(this.getCode())) {
            this.setCode(this.getCode() + "%");
        } else {
            this.setCode(null);
        }
        if (StringUtils.hasText(this.getPartyAOperator())) {
            this.setPartyAOperator("%" + this.getPartyAOperator() + "%");
        } else {
            this.setPartyAOperator(null);
        }
        if (StringUtils.hasText(this.getPartyBOperator())) {
            this.setPartyBOperator("%" + this.getPartyBOperator() + "%");
        } else {
            this.setPartyBOperator(null);
        }
        if (StringUtils.hasText(this.getPaytyAMobile())) {
            this.setPaytyAMobile("%" + this.getPaytyAMobile() + "%");
        } else {
            this.setPaytyAMobile(null);
        }
        if (StringUtils.hasText(this.getPaytyBPassPort())) {
            this.setPaytyBPassPort("%" + this.getPaytyBPassPort() + "%");
        } else {
            this.setPaytyBPassPort(null);
        }
        if (this.getCreateStratTime() == null) {
            this.setCreateStratTime(null);
        }
        if (this.getCreateEndTime() == null) {
            this.setCreateEndTime(null);
        }
        if (this.getHandOverStartTime() == null) {
            this.setHandOverStartTime(null);
        }
        if (this.getHandOverEndTime() == null) {
            this.setHandOverEndTime(null);
        }
    }

    public String getCreateStratTime1() {
        return createStratTime1;
    }

    public void setCreateStratTime1(String createStratTime1) {
        this.createStratTime1 = createStratTime1;
    }

    public String getHandOverStartTime1() {
        return handOverStartTime1;
    }

    public void setHandOverStartTime1(String handOverStartTime1) {
        this.handOverStartTime1 = handOverStartTime1;
    }

    public String getCreateEndTime1() {
        return createEndTime1;
    }

    public void setCreateEndTime1(String createEndTime1) {
        this.createEndTime1 = createEndTime1;
    }

    public String getHandOverEndTime1() {
        return handOverEndTime1;
    }

    public void setHandOverEndTime1(String handOverEndTime1) {
        this.handOverEndTime1 = handOverEndTime1;
    }

    public Long getModifierId() {
        return modifierId;
    }

    public void setModifierId(Long modifierId) {
        this.modifierId = modifierId;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public Long getOuId() {
        return ouId;
    }

    public void setOuId(Long ouId) {
        this.ouId = ouId;
    }
}
