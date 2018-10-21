package com.jumbo.webservice.rfd;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * 2.3	订单出库
 */
public class RfdOutStoreResponse extends RfdCommonResponse {
	
	@JsonProperty(value = "ResultData")
	private List<RfdOutStoreResDetail> resultData;
	
	public static class RfdOutStoreResDetail {
		
		@JsonProperty(value = "FormCode")
		private String formCode;	// 订单编号
		@JsonProperty(value = "WaybillNo")
		private Long waybillNo;		// 运单号
		@JsonProperty(value = "DeliverCode")
		private String deliverCode;	// 子单号
		@JsonProperty(value = "IsSuccess")
		private Boolean isSuccess;	// 是否成功
		@JsonProperty(value = "Message")
		private String message;		// 结果信息
		
		public String getFormCode() {
			return formCode;
		}
		public void setFormCode(String formCode) {
			this.formCode = formCode;
		}
		public Long getWaybillNo() {
			return waybillNo;
		}
		public void setWaybillNo(Long waybillNo) {
			this.waybillNo = waybillNo;
		}
		public String getDeliverCode() {
			return deliverCode;
		}
		public void setDeliverCode(String deliverCode) {
			this.deliverCode = deliverCode;
		}
		public Boolean getIsSuccess() {
			return isSuccess;
		}
		public void setIsSuccess(Boolean isSuccess) {
			this.isSuccess = isSuccess;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
	}

	public List<RfdOutStoreResDetail> getResultData() {
		return resultData;
	}

	public void setResultData(List<RfdOutStoreResDetail> resultData) {
		this.resultData = resultData;
	}
}
