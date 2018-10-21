package com.jumbo.webservice.express;

import java.io.Serializable;

public class ExpressAddedService implements Serializable {
	
	private static final long serialVersionUID = 3624975396976910628L;
	
	private String name;	// 增值服务编码 
	private String value;	// 增值服务value
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
