/**
 * 
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
package com.jumbo.web.action.authorization;

import java.util.List;

import loxia.dao.Pagination;
import loxia.support.json.JSONArray;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.Constants;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.manager.DropDownListManager;
import com.jumbo.wms.manager.authorization.AuthorizationManager;
import com.jumbo.wms.manager.baseinfo.MenuManager;
import com.jumbo.wms.model.authorization.OperationUnitType;
import com.jumbo.wms.model.authorization.Privilege;
import com.jumbo.wms.model.authorization.Role;
import com.jumbo.wms.model.command.authorization.RoleCmd;

/**
 * 
 * @author zhl
 * 
 */
public class RoleManageAction extends BaseJQGridProfileAction {
    private static final long serialVersionUID = -3814719696717454393L;

    @Autowired
    private AuthorizationManager authorizationManager;
    @Autowired
    private DropDownListManager dropDownListManager;
    @Autowired
    private MenuManager menuManager;
    private Role role;
    private String sortString;
    private List<OperationUnitType> opeUnitType;
    private List<Privilege> privlist;
    private List<Role> roleList;

    /**
     * 进入role创建和编辑页面
     * 
     * @return
     */
    public String roleMaintainEntry() {
        opeUnitType = dropDownListManager.findOperationUnitTypeList(true);
        return SUCCESS;
    }

    public String roleMaintainDetailEntry() {
        role = authorizationManager.findRoleInfoById(role.getId());
        return SUCCESS;
    }

    public String findRoleList() {
        setTableConfig();
        Pagination<RoleCmd> roleList = authorizationManager.findRoleCmdList(tableConfig.getStart(), tableConfig.getPageSize(), role, tableConfig.getSorts());
        request.put(JSON, toJson(roleList));
        return JSON;
    }

    public String findRolePrivilegeList() {
        JSONArray ja = menuManager.findRolePrivilegeList(role.getId(), Constants.SYSTEM_CODE);
        request.put(JSON, ja);
        return JSON;
    }

    public String findPrivilegeList() {
        JSONArray ja = menuManager.getMenuListByOuTypeId(role.getOuType().getId(), role.getId(), Constants.SYSTEM_CODE);
        request.put(JSON, ja);
        return JSON;
    }

    public String deleteRoleList() {
        authorizationManager.deleteRoleList(roleList);
        this.asynReturnTrueValue();
        return JSON;
    }

    public String findRoleById() {
        request.put(JSON, authorizationManager.findRoleById(role.getId()));
        return JSON;
    }

    public String modifyRole() throws JSONException {
        if (authorizationManager.checkRoleName(role)) {
            request.put(JSON, new JSONObject().put("name", ""));
            return JSON;
        }
        authorizationManager.updateOrSaveRoleAndPriv(role, privlist);
        this.asynReturnTrueValue();
        return JSON;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getSortString() {
        return sortString;
    }

    public void setSortString(String sortString) {
        this.sortString = sortString;
    }

    public List<OperationUnitType> getOpeUnitType() {
        return opeUnitType;
    }

    public void setOpeUnitType(List<OperationUnitType> opeUnitType) {
        this.opeUnitType = opeUnitType;
    }

    public List<Privilege> getPrivlist() {
        return privlist;
    }

    public void setPrivlist(List<Privilege> privlist) {
        this.privlist = privlist;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

}
