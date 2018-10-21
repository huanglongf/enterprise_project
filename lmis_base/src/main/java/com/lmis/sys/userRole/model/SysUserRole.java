package com.lmis.sys.userRole.model;

import com.lmis.framework.baseModel.PersistentObject;
import io.swagger.annotations.ApiModelProperty;

/** 
 * @ClassName: SysUserRole
 * @Description: TODO(用户角色关系表)
 * @author codeGenerator
 * @date 2018-01-09 16:43:38
 * 
 */
public class SysUserRole extends PersistentObject {

    /** 
	 * @Fields serialVersionUID : TODO(序列号) 
	 */
	private static final long serialVersionUID = 1L;
	
    public static long getSerialversionuid() {
    	return serialVersionUID;	
    }
    
	
    @ApiModelProperty(value = "对应用户ID")
	private String userId;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
    @ApiModelProperty(value = "对应角色ID")
	private String roleId;
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
}
