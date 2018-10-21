package com.bt.lmis.model;

/** 
* @ClassName: Role 
* @Description: TODO(角色) 
* @author Yuriy.Jiang
* @date 2016年5月27日 下午5:38:02 
*  
*/
public class Role {
	
	public int id;
	public String name;
	public String remark;
	public int status;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}
