package com.jumbo.wms.model.warehouse;

import java.io.Serializable;

public class WhAddStatusSourceCommand implements Serializable {

	private static final long serialVersionUID = -1890879500602533045L;

	private Long id;

	private int type;

	private Integer status;

	private int sort;

	private int isNecessary;

	private String statusName;

	private String typeName;

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public int getIsNecessary() {
		return isNecessary;
	}

	public void setIsNecessary(int isNecessary) {
		this.isNecessary = isNecessary;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}
}
