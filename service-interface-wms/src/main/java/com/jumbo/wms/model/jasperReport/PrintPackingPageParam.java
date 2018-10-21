package com.jumbo.wms.model.jasperReport;

import com.jumbo.wms.model.BaseModel;

public class PrintPackingPageParam extends BaseModel {

	private static final long serialVersionUID = 101256382899870147L;
	
	private Long plId;	// 配货清单Id
	private Long staId;	// 工作单Id
	
	public Long getPlId() {
		return plId;
	}
	public void setPlId(Long plId) {
		this.plId = plId;
	}
	public Long getStaId() {
		return staId;
	}
	public void setStaId(Long staId) {
		this.staId = staId;
	}
	
}
