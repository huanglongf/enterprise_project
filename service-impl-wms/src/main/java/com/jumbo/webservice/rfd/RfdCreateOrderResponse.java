package com.jumbo.webservice.rfd;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * 2.1	订单导入
 */
public class RfdCreateOrderResponse extends RfdCommonResponse {
	
	@JsonProperty(value = "ResultData")
	private List<RfdCreateOrderResDetail> resultData;	// 结果明细

	public List<RfdCreateOrderResDetail> getResultData() {
		return resultData;
	}

	public void setResultData(List<RfdCreateOrderResDetail> resultData) {
		this.resultData = resultData;
	}
}
