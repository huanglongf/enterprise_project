package com.lmis.sys.roleFunctionButton.model;

import com.lmis.framework.baseModel.PersistentObject;

import io.swagger.annotations.ApiModelProperty;

/** 
 * @ClassName: SysRoleFunctionButton
 * @Description: TODO(角色与功能菜单关系表)
 * @author codeGenerator
 * @date 2018-01-09 17:35:11
 * 
 */
public class SysRoleFunctionButton extends PersistentObject {

    /** 
	 * @Fields serialVersionUID : TODO(序列号) 
	 */
	private static final long serialVersionUID = 1L;
	
    public static long getSerialversionuid() {
    	return serialVersionUID;	
    }
    
	
    @ApiModelProperty(value = "角色ID")
	private String roleId;
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
    @ApiModelProperty(value = "功能ID")
	private String fbId;
	public String getFbId() {
		return fbId;
	}
	public void setFbId(String fbId) {
		this.fbId = fbId;
	}
	
    @ApiModelProperty(value = "查询结果树上展示图片的ID")
	private String fbGifId;
	public String getFbGifId() {
		return fbGifId;
	}
	public void setFbGifId(String fbGifId) {
		this.fbGifId = fbGifId;
	}
	
}
