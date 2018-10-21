package com.jumbo.wms.model.warehouse;


import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;
@Entity
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class DistriButionAreaType extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1528394775328918472L;
	
	
	private Long id;
	private String distriButionAreaCode;
	private String distriButionAreaName;
	private Long ouid;

	@Id
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public Long getOuid() {
		return ouid;
	}
	public void setOuid(Long ouid) {
		this.ouid = ouid;
	}
}
