package com.jumbo.wms.model.warehouse;

import java.io.Serializable;

public class AreaType implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3961795565167136445L;
	private Long id;
	private String distriButionAreaCode;
	
	private String distriButionAreaName;
	
	private Integer num;

	public String getDistriButionAreaCode() {
		return distriButionAreaCode;
	}

	public void setDistriButionAreaCode(String distriButionAreaCode) {
		this.distriButionAreaCode = distriButionAreaCode;
	}

	public String getDistriButionAreaName() {
		return distriButionAreaName;
	}

	public void setDistriButionAreaName(String distriButionAreaName) {
		this.distriButionAreaName = distriButionAreaName;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	
	
	
}
