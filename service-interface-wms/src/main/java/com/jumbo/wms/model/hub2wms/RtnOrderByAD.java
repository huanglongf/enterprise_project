package com.jumbo.wms.model.hub2wms;

import com.jumbo.wms.model.BaseModel;

public class RtnOrderByAD extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2327528334297773848L;
	
	private String orderCode;
	
	private String lpCode;
	
	private String trackingNo;

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getLpCode() {
		return lpCode;
	}

	public void setLpCode(String lpCode) {
		this.lpCode = lpCode;
	}

	public String getTrackingNo() {
		return trackingNo;
	}

	public void setTrackingNo(String trackingNo) {
		this.trackingNo = trackingNo;
	}
	
}
