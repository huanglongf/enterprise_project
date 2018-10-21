package com.bt.vims.model;

/** 
* @ClassName: User 
* @Description: TODO(系统用户) 
* @author Yuriy.Jiang
* @date 2017年1月7日 下午6:42:33 
*  
*/
public class User {
	
	//主键
	private String id;
	//工号&用户名
	private String username;
	//姓名
	private String name;
	//密码(MD5)
	private String password;
	//公司编号
	private String company_code;
	//状态[0停用|1启用]
	private String active;
	//部门编号
	private String department_code;
	//联系电话&短号
	private String telephone;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCompany_code() {
		return company_code;
	}
	public void setCompany_code(String company_code) {
		this.company_code = company_code;
	}
	public String getDepartment_code() {
		return department_code;
	}
	public void setDepartment_code(String department_code) {
		this.department_code = department_code;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
