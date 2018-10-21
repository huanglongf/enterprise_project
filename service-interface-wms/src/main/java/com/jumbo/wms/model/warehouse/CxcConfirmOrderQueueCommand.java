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

/**
 * 接口/类说明： CXC 订单数据组装
 * @ClassName: CxcConfirmOrderQueueCommand 
 * @author LuYingMing
 */
public class CxcConfirmOrderQueueCommand extends CxcConfirmOrderQueue {


    private static final long serialVersionUID = 5166376959564078298L;
    private String address;
    private String mobile;
    private String receiver;
    private String trackingNo;
    private Boolean isCod;
    private BigDecimal insuranceAmount;
    private BigDecimal weight;
    private BigDecimal orderTotalActual;
    private BigDecimal orderTransferFree;
    private BigDecimal totalActual;
    private String owner;
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getTrackingNo() {
		return trackingNo;
	}
	public void setTrackingNo(String trackingNo) {
		this.trackingNo = trackingNo;
	}
	public Boolean getIsCod() {
		return isCod;
	}
	public void setIsCod(Boolean isCod) {
		this.isCod = isCod;
	}
	public BigDecimal getInsuranceAmount() {
		return insuranceAmount;
	}
	public void setInsuranceAmount(BigDecimal insuranceAmount) {
		this.insuranceAmount = insuranceAmount;
	}
	public BigDecimal getWeight() {
		return weight;
	}
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	public BigDecimal getOrderTotalActual() {
		return orderTotalActual;
	}
	public void setOrderTotalActual(BigDecimal orderTotalActual) {
		this.orderTotalActual = orderTotalActual;
	}
	public BigDecimal getOrderTransferFree() {
		return orderTransferFree;
	}
	public void setOrderTransferFree(BigDecimal orderTransferFree) {
		this.orderTransferFree = orderTransferFree;
	}
	public BigDecimal getTotalActual() {
		return totalActual;
	}
	public void setTotalActual(BigDecimal totalActual) {
		this.totalActual = totalActual;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
    
    

}
