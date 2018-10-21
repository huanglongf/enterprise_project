package com.jumbo.wms.model.warehouse;

import javax.persistence.Entity;
import javax.persistence.Id;
import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;
@Entity
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class BingdingDetai extends BaseModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4033110679930520807L;
    
	private Long id;
	
	private String distriButionAreaCode;
	
	private String distriButionAreaName;
	
    private String code;

    
    private String name;

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


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
    
    
    

}
