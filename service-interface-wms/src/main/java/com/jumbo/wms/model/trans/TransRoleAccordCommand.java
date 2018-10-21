/**
 * Copyright (c) 2010 Jumbomart All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Jumbomart. You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jumbo.
 * 
 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 * 
 */

package com.jumbo.wms.model.trans;

/**
 * 渠道快递规则状态变更
 * 
 * @author lingyun.dai
 * 
 */

public class TransRoleAccordCommand extends TransRoleAccord {
	private static final long serialVersionUID = 4850250418667066137L;
	
	private String roleCode;// 规则编码
	private String roleName;// 规则名称
	private String channelCode;// 渠道编码
	private String channelName;// 渠道名称
	private Long lRoleId;
	private String channelTransStatus;
	private String createName;
	private String strRoleIsAvailable;
	private Integer intRoleIsAvailable;
	private Integer intStatus;
	private String strChannelTransStatus;
	private Integer intChannelTransStatus;
	private String strChangeTime;
	private String strPriority;
	
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public Long getlRoleId() {
		return lRoleId;
	}
	public void setlRoleId(Long lRoleId) {
		this.lRoleId = lRoleId;
	}
	public String getChannelTransStatus() {
		return channelTransStatus;
	}
	public void setChannelTransStatus(String channelTransStatus) {
		this.channelTransStatus = channelTransStatus;
	}
	public String getCreateName() {
		return createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	public String getStrRoleIsAvailable() {
		return strRoleIsAvailable;
	}
	public void setStrRoleIsAvailable(String strRoleIsAvailable) {
		this.strRoleIsAvailable = strRoleIsAvailable;
	}
	public Integer getIntStatus() {
		return intStatus;
	}
	public void setIntStatus(Integer intStatus) {
		this.intStatus = intStatus;
	}
	public String getStrChannelTransStatus() {
		return strChannelTransStatus;
	}
	public void setStrChannelTransStatus(String strChannelTransStatus) {
		this.strChannelTransStatus = strChannelTransStatus;
	}
	public Integer getIntChannelTransStatus() {
		return intChannelTransStatus;
	}
	public void setIntChannelTransStatus(Integer intChannelTransStatus) {
		this.intChannelTransStatus = intChannelTransStatus;
	}
	public String getStrChangeTime() {
		return strChangeTime;
	}
	public void setStrChangeTime(String strChangeTime) {
		this.strChangeTime = strChangeTime;
	}
	public String getStrPriority() {
		return strPriority;
	}
	public void setStrPriority(String strPriority) {
		this.strPriority = strPriority;
	}
	public Integer getIntRoleIsAvailable() {
		return intRoleIsAvailable;
	}
	public void setIntRoleIsAvailable(Integer intRoleIsAvailable) {
		this.intRoleIsAvailable = intRoleIsAvailable;
	}
}
