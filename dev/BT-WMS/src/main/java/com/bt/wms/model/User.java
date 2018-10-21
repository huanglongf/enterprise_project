package com.bt.wms.model;

/** 
* @ClassName: User 
* @Description: TODO(系统用户) 
* @author Yuriy.Jiang
* @date 2017年1月7日 下午6:42:33 
*  
*/
public class User {
	
	//主键
	private int id;
	//工号&用户名
	private String username;
	//姓名
	private String name;
	//密码(MD5)
	private String password;
	//容器号
	private String container_code;
	//货位号
	private String localhost_code;
	//操作类型 1下架 2上架
	private String opertion_type;
	
	public String getOpertion_type() {
		return opertion_type;
	}
	public void setOpertion_type(String opertion_type) {
		this.opertion_type = opertion_type;
	}
	public String getContainer_code() {
		return container_code;
	}
	public void setContainer_code(String container_code) {
		this.container_code = container_code;
	}
	public String getLocalhost_code() {
		return localhost_code;
	}
	public void setLocalhost_code(String localhost_code) {
		this.localhost_code = localhost_code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
