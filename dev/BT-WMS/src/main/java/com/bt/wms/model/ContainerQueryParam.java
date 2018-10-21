package com.bt.wms.model;

import com.bt.wms.utils.QueryParameter;

public class ContainerQueryParam extends QueryParameter{
	private String container_code;
	private String type;
	public String getContainer_code() {
		return container_code;
	}
	public void setContainer_code(String container_code) {
		this.container_code = container_code;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
