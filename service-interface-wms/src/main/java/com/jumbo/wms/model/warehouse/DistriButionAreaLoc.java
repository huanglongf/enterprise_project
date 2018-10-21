package com.jumbo.wms.model.warehouse;

import java.util.Date;


import javax.persistence.Entity;
import javax.persistence.Id;



import org.hibernate.annotations.OptimisticLockType;
import com.jumbo.wms.model.BaseModel;


@Entity
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class DistriButionAreaLoc extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String codeName;
	private String code;
	private String distriButionAreaCode;
	private String distriButionAreaName;
    private Date createTime;
    private String createUser;
    
    @Id
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
   
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getCodeName() {
		return codeName;
	}
	public void setCodeName(String codeName) {
		this.codeName = codeName;
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
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	

	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
    

}
