package com.bt.lmis.model;

import java.io.Serializable;
import java.util.Date;

/** 
* @ClassName: Employee 
* @Description: TODO(员工) 
* @author Yuriy.Jiang
* @date 2016年5月23日 上午10:50:55 
*  
*/
public class Employee implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9219277265268840127L;
	private Integer id;
	private String employee_number;
	private String username;
	private String name;
	private String password;
	private String email;
	private String iphone;
	private String status;
	private Date createtime;
	private String createby;
	private Date updatetime;
	private String updateby;
	private String project_id;
	private String token;
	private String teamId;
	private String tmpStatus;
	private String isWoEmail;
	public Employee() {}
	public Employee(Integer id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public String getIsWoEmail() {
		return isWoEmail;
	}
	public void setIsWoEmail(String isWoEmail) {
		this.isWoEmail = isWoEmail;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEmployee_number() {
		return employee_number;
	}
	public void setEmployee_number(String employee_number) {
		this.employee_number = employee_number;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getTmpStatus() {
		return tmpStatus;
	}
	public void setTmpStatus(String tmpStatus) {
		this.tmpStatus = tmpStatus;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getIphone() {
		return iphone;
	}
	public void setIphone(String iphone) {
		this.iphone = iphone;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public String getCreateby() {
		return createby;
	}
	public void setCreateby(String createby) {
		this.createby = createby;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	public String getUpdateby() {
		return updateby;
	}
	public void setUpdateby(String updateby) {
		this.updateby = updateby;
	}
	public String getProject_id() {
		return project_id;
	}
	public void setProject_id(String project_id) {
		this.project_id = project_id;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getTeamId() {
		return teamId;
	}
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}
	
}
