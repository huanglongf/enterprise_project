package com.lmis.sys.user.model;

import io.swagger.annotations.ApiModelProperty;

/** 
 * @ClassName: ViewSysUser
 * @Description: TODO(VIEW)
 * @author codeGenerator
 * @date 2018-01-18 13:22:20
 * 
 */
public class ViewSysUser {
	
	@ApiModelProperty(value = "")
	private String createUserName;
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	
	@ApiModelProperty(value = "")
	private String updateUserName;
	public String getUpdateUserName() {
		return updateUserName;
	}
	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}
	
	@ApiModelProperty(value = "")
	private String pwrOrgName;
	public String getPwrOrgName() {
		return pwrOrgName;
	}
	public void setPwrOrgName(String pwrOrgName) {
		this.pwrOrgName = pwrOrgName;
	}
	
	@ApiModelProperty(value = "用户编号")
	private String userCode;
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	
	@ApiModelProperty(value = "用户名称")
	private String userName;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@ApiModelProperty(value = "登陆密码")
	private String userPwd;
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	
	@ApiModelProperty(value = "手机")
	private String userPhone;
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	
	@ApiModelProperty(value = "所属机构")
	private String userOrgCode;
	public String getUserOrgCode() {
		return userOrgCode;
	}
	public void setUserOrgCode(String userOrgCode) {
		this.userOrgCode = userOrgCode;
	}
	
	@ApiModelProperty(value = "")
	private String userOrgName;
	public String getUserOrgName() {
		return userOrgName;
	}
	public void setUserOrgName(String userOrgName) {
		this.userOrgName = userOrgName;
	}
	
	@ApiModelProperty(value = "备注")
	private String userRemark;
	public String getUserRemark() {
		return userRemark;
	}
	public void setUserRemark(String userRemark) {
		this.userRemark = userRemark;
	}
	
}
