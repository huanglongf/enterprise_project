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
package com.jumbo.wms.model.jasperReport;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class OutBoundSendInfo implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1762034829686016265L;
    /**
     * 作业单号
     */
    private String code;
    /**
     * 打印信息：送货地址（目标仓库地址）
     */
    private String targetAddress;
    /**
     * 负责人姓名
     */
    private String principal;
    /**
     * 负责人联系方式
     */
    private String principalPhone;
    /**
     * 其他联系人及方式1
     */
    private String otherPhone1;
    /**
     * 其他联系人及方式2
     */
    private String otherPhone2;
    /**
     * 其他联系人及方式3
     */
    private String otherPhone3;

    private Date createTime;
    /**
     * 占用明细
     */
    private List<OutBoundSendInfoLine> lines;

    // private Integer index;
    private Integer detailSize;

    /**
     * pickingListNumber
     */
    private String pickingListNumber;

    /**
     * 退仓装箱单号
     */
    private String returnCode;

    /**
     * 客户
     */
    private String customer;

    /**
     * 地址1
     */
    private String address1;
    /**
     * 地址2
     */
    private String address2;
    /**
     * 地址3
     */
    private String address3;
    /**
     * 地址4
     */
    private String address4;
    /**
     * 日期
     */
    private String returnDate;
    /**
     * 预计抵达日期
     */
    private String expectedTime;
    
    private String orderKey;
    
    private String pplNo;
    
    private String caseNumber;//箱号
    
    private String whCode;
    
    /**
     * 发货方式
     */
    private String transMethod;
    
    /**
     * 是否多仓发货
     */
    private Boolean isMoreWh;

    // GETTER && SETTER
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getDetailSize() {
        return detailSize;
    }

    public void setDetailSize(Integer detailSize) {
        this.detailSize = detailSize;
    }

    public String getTargetAddress() {
        return targetAddress;
    }

    public void setTargetAddress(String targetAddress) {
        this.targetAddress = targetAddress;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getPrincipalPhone() {
        return principalPhone;
    }

    public void setPrincipalPhone(String principalPhone) {
        this.principalPhone = principalPhone;
    }

    public String getOtherPhone1() {
        return otherPhone1;
    }

    public void setOtherPhone1(String otherPhone1) {
        this.otherPhone1 = otherPhone1;
    }

    public String getOtherPhone2() {
        return otherPhone2;
    }

    public void setOtherPhone2(String otherPhone2) {
        this.otherPhone2 = otherPhone2;
    }

    public String getOtherPhone3() {
        return otherPhone3;
    }

    public void setOtherPhone3(String otherPhone3) {
        this.otherPhone3 = otherPhone3;
    }

    public List<OutBoundSendInfoLine> getLines() {
        return lines;
    }

    public void setLines(List<OutBoundSendInfoLine> lines) {
        this.lines = lines;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPickingListNumber() {
        return pickingListNumber;
    }

    public void setPickingListNumber(String pickingListNumber) {
        this.pickingListNumber = pickingListNumber;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getAddress4() {
        return address4;
    }

    public void setAddress4(String address4) {
        this.address4 = address4;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getExpectedTime() {
        return expectedTime;
    }

    public void setExpectedTime(String expectedTime) {
        this.expectedTime = expectedTime;
    }

    public String getOrderKey() {
        return orderKey;
    }

    public void setOrderKey(String orderKey) {
        this.orderKey = orderKey;
    }

    public String getPplNo() {
        return pplNo;
    }

    public void setPplNo(String pplNo) {
        this.pplNo = pplNo;
    }

    public String getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }

	public String getWhCode() {
		return whCode;
	}

	public void setWhCode(String whCode) {
		this.whCode = whCode;
	}

	public String getTransMethod() {
		return transMethod;
	}

	public void setTransMethod(String transMethod) {
		this.transMethod = transMethod;
	}

	public Boolean getIsMoreWh() {
		return isMoreWh;
	}

	public void setIsMoreWh(Boolean isMoreWh) {
		this.isMoreWh = isMoreWh;
	}
    
    
}
