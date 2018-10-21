package com.jumbo.wms.model.warehouse;

import javax.persistence.Column;

public class LogQueueCode extends LogQueue {
	/**
     * 
     */
    private static final long serialVersionUID = -7347225504566150929L;
    private String skuCode;
	@Column(name="code")
	public String getSkuCode() {
		return skuCode;
	}
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

}
