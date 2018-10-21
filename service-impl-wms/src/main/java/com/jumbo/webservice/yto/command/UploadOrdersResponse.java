package com.jumbo.webservice.yto.command;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Response")
public class UploadOrdersResponse {
	/**
	 * 圆通速递编号 YTO
	 */
	@XmlElement(required = true, name = "logisticProviderID")
	private String logisticProviderID;

	/**
	 * 是否成功
	 */
	@XmlElement(required = true, name = "success")
	private String success;
	
	
	/**
	 * 提示信息
	 */
	@XmlElement(required = true, name = "noticeMessage")
	private String noticeMessage;
	
	/**
	 * 失败原因
	 */
	@XmlElement(required = false, name = "reason")
	private String reason;
	
	/**
	 * 订单详情
	 */
	@XmlElement(required = false, name = "orderMessage")
	private UploadOrdersRequest orderMessage;
	
	public String getLogisticProviderID() {
		return logisticProviderID;
	}

	public void setLogisticProviderID(String logisticProviderID) {
		this.logisticProviderID = logisticProviderID;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getNoticeMessage() {
		return noticeMessage;
	}

	public void setNoticeMessage(String noticeMessage) {
		this.noticeMessage = noticeMessage;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public UploadOrdersRequest getOrderMessage() {
		return orderMessage;
	}

	public void setOrderMessage(UploadOrdersRequest orderMessage) {
		this.orderMessage = orderMessage;
	}

}
