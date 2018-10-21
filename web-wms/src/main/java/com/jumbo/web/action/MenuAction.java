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

package com.jumbo.web.action;

import java.util.ArrayList;
import java.util.List;

import loxia.support.json.JSONArray;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.jumbo.Constants;
import com.jumbo.wms.manager.authorization.AuthorizationManager;
import com.jumbo.wms.manager.baseinfo.MenuManager;
import com.jumbo.wms.model.baseinfo.MenuItem;
import com.jumbo.wms.model.command.authorization.UserDetailsCmd;
import com.jumbo.wms.model.command.authorization.UserRoleCmd;
import com.jumbo.web.security.GrantedAuthorityImpl;
import com.jumbo.web.security.UserDetails;

/**
 * 
 * @author wanghua
 * 
 */
public class MenuAction extends BaseJQGridProfileAction {
	private static final long serialVersionUID = 7476783124881394050L;
	private Long roleId;
	private Long ouId;
	@Autowired
	private MenuManager menuManager;
	@Autowired
	private AuthorizationManager authorizationManager;

	/**
	 * 直接跳转jsp页面
	 */
	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}

	private JSONArray rebuildRoleTree(List<MenuItem> list) throws Exception {
		List<JSONObject> listJson = new ArrayList<JSONObject>();
		if (list == null || list.isEmpty()) {
			return new JSONArray(listJson);
		}
		for (MenuItem each : list) {
			if (each.getIsGroup() && each.getChildren().isEmpty()) continue;
			listJson.add(ouToJson(each));
		}
		return new JSONArray(listJson);
	}

	private JSONObject ouToJson(MenuItem obj) throws Exception {
		JSONObject json = new JSONObject();
		json.put("menuId", obj.getMenuId());
		json.put("name", obj.getName());
		json.put("sortNo", obj.getSortNo());
		json.put("isGroup", obj.getIsGroup());
		json.put("isSeperator", obj.getIsSeperator());
		json.put("acl", obj.getAcl());
		json.put("entryURL", obj.getEntryURL());
		if (obj.getChildren() != null && obj.getChildren().size() > 0) {
			json.put("children", rebuildRoleTree(obj.getChildren()));
		}
		return json;
	}

	/**
	 * JSON数据
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getMenuList() throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("{}MenuAction.getMenuList.start", "----------");
		}
		if (userDetails != null && userDetails.getCurrentRole() != null) {
			Long roleId = userDetails.getCurrentRole().getId();
			List<MenuItem> list = menuManager.getMenuListByRoleId(roleId, Constants.SYSTEM_CODE);
			JSONArray json = null;
			// if (list != null && !list.isEmpty()) {
			// StringBuilder sb = new StringBuilder();
			// sb.append("menuId").append(",").append("name").append(",").append("sortNo").append(",").append("isGroup").append(",").append("isSeperator").append(",").append("acl").append(",").append("entryURL").append(",").append("children")
			// .append(",").append("children.menuId").append(",").append("children.name").append(",").append("children.sortNo").append(",").append("children.isGroup").append(",").append("children.isSeperator").append(",").append("children.acl")
			// .append(",").append("children.entryURL").append(",").append("children.children").append(",").append("children.children.children").append(",").append("-class,parent");
			// json = JsonUtil.collection2json(list, sb.toString());
			// }
			json = rebuildRoleTree(list);
			request.put(JSON, json);
			return JSON;
		} else {
			log.error("userDetails.getCurrentRole().getId() is null");
			return JSON;
		}
	}

	public String updateUserRole() throws Exception {
		if (roleId != null && ouId != null) {
			UserDetailsCmd cmd = authorizationManager.findUserDetails(userDetails.getUser().getId(), roleId, ouId);
			org.springframework.security.core.userdetails.UserDetails user = constructUserDetails(cmd);
			Authentication authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		request.put(JSON, new JSONObject().put(SUCCESS, SUCCESS));
		return JSON;
	}

	private org.springframework.security.core.userdetails.UserDetails constructUserDetails(UserDetailsCmd cmd) {
		userDetails.setCurrentUserRole(cmd.getCurrentUserRole());
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (String acl : cmd.getAuthorities()) {
			GrantedAuthority auth = new GrantedAuthorityImpl(acl);
			authorities.add(auth);
		}
		UserDetails userDetails = new UserDetails(cmd.getCurrentUserRole(), authorities);
		return userDetails;
	}

	public String userRoleList() {
		try {
			// 根据参数构造TableConfig,显示调用
			setTableConfig();
			// 根据TableConfig获取查询参数
			// 获取数据,返回结果为List的默认是不分页,返回结果为Pagination为分页
			// List<UserRole> userRoleList =
			// authorizationManager.findUserRoleListByUserId(userDetails == null
			// ? -1 : userDetails.getUser().getId(), tableConfig.getSorts());
			List<UserRoleCmd> userRoleList = authorizationManager.findUserRoleCmdListByUserId(userDetails == null ? -1 : userDetails.getUser().getId(), tableConfig.getSorts());
			// 把数据转为json格式并放入request
			request.put(JSON, toJson(userRoleList));
		} catch (Exception e) {
			log.error("", e);
			log.error("----------UserRoleAction.userRoleList{}", e.getMessage());
		}

		return JSON;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getOuId() {
		return ouId;
	}

	public void setOuId(Long ouId) {
		this.ouId = ouId;
	}

}
