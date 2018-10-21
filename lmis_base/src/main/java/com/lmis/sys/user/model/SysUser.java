package com.lmis.sys.user.model;

import com.lmis.framework.baseModel.PersistentObject;
import io.swagger.annotations.ApiModelProperty;

/** 
 * @ClassName: SysUser
 * @Description: TODO(用户表)
 * @author codeGenerator
 * @date 2018-01-09 12:51:12
 * 
 */
public class SysUser extends PersistentObject {

    /** 
	 * @Fields serialVersionUID : TODO(序列号) 
	 */
	private static final long serialVersionUID = 1L;
	
    public static long getSerialversionuid() {
    	return serialVersionUID;	
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
	
    @ApiModelProperty(value = "备注")
	private String userRemark;
	public String getUserRemark() {
		return userRemark;
	}
	public void setUserRemark(String userRemark) {
		this.userRemark = userRemark;
	}
	
	@ApiModelProperty(value = "旧密码")
	private String oldUserPwd;
	public String getOldUserPwd() {
		return oldUserPwd;
	}
	public void setOldUserPwd(String oldUserPwd) {
		this.oldUserPwd = oldUserPwd;
	}
	
	
	
}
