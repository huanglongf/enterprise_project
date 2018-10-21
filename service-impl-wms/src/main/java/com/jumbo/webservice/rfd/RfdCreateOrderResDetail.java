package com.jumbo.webservice.rfd;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RfdCreateOrderResDetail {
	
	@JsonProperty(value = "WaybillNo")
	private Long waybillNo;			// 分配的运单编号
	@JsonProperty(value = "FormCode")
	private String formCode;		// 订单编号
	@JsonProperty(value = "ResultCode")
	private String resultCode;		// 结果编号
	@JsonProperty(value = "ResultMessage")
	private String resultMessage;	// 结果信息
	@JsonProperty(value = "DistributionCode")
	private String distributionCode;// 分配配送商编码
	@JsonProperty(value = "DistributionName")
	private String distributionName;// 分配配送商名称
	@JsonProperty(value = "StationId")
	private Integer stationId;		// 分配站点编码
	@JsonProperty(value = "StationName")
	private String stationName;		// 分配站点名称
	@JsonProperty(value = "SiteNo")
	private String siteNo;			// 站点编码
	@JsonProperty(value = "CityNumber")
	private String cityNumber;		// 城市编号
	@JsonProperty(value = "LineNumber")
	private String lineNumber;		// 线路编码
	@JsonProperty(value = "Message")
	private String message;			// 分单详情
	
	public Long getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(Long waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getFormCode() {
		return formCode;
	}
	public void setFormCode(String formCode) {
		this.formCode = formCode;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultMessage() {
		return resultMessage;
	}
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
	public String getDistributionCode() {
		return distributionCode;
	}
	public void setDistributionCode(String distributionCode) {
		this.distributionCode = distributionCode;
	}
	public String getDistributionName() {
		return distributionName;
	}
	public void setDistributionName(String distributionName) {
		this.distributionName = distributionName;
	}
	public Integer getStationId() {
		return stationId;
	}
	public void setStationId(Integer stationId) {
		this.stationId = stationId;
	}
	public String getSiteNo() {
		return siteNo;
	}
	public void setSiteNo(String siteNo) {
		this.siteNo = siteNo;
	}
	public String getCityNumber() {
		return cityNumber;
	}
	public void setCityNumber(String cityNumber) {
		this.cityNumber = cityNumber;
	}
	public String getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	
}
