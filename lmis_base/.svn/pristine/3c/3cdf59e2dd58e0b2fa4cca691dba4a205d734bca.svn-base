package com.lmis.sys.userRoleOrg.model;

import java.util.List;

import com.lmis.framework.baseModel.PersistentObject;

import io.swagger.annotations.ApiModelProperty;

/** 
 * @ClassName: SysUserRoleOrg
 * @Description: TODO(用户/角色与组织机构关系表)
 * @author codeGenerator
 * @date 2018-01-09 17:10:23
 * 
 */
public class SysUserRoleOrg extends PersistentObject {

    /** 
	 * @Fields serialVersionUID : TODO(序列号) 
	 */
	private static final long serialVersionUID = 1L;
	
    public static long getSerialversionuid() {
    	return serialVersionUID;	
    }
    
	
    private List<SysUserRoleOrg> orgList;
    
    public List<SysUserRoleOrg> getOrgList() {
		return orgList;
	}
	public void setOrgList(List<SysUserRoleOrg> orgList) {
		this.orgList = orgList;
	}


	@ApiModelProperty(value = "用户ID")
	private String userId;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
    @ApiModelProperty(value = "角色ID")
	private String roleId;
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
    @ApiModelProperty(value = "组织机构ID")
	private String orgId;
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
}
