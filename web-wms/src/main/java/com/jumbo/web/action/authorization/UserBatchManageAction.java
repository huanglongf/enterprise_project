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
package com.jumbo.web.action.authorization;

import java.util.List;

import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.Constants;
import com.jumbo.wms.manager.DropDownListManager;
import com.jumbo.wms.manager.authorization.AuthorizationManager;
import com.jumbo.wms.model.authorization.OperationUnitType;
import com.jumbo.wms.model.authorization.UserGroup;
import com.jumbo.web.action.BaseProfileAction;


/**
 * 用户批量管理，（用户有效性管理，用户批量角色授予或脱离）
 * 
 * @author jumbo
 * 
 */
public class UserBatchManageAction extends BaseProfileAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = 4841836610532787440L;

    @Autowired
    AuthorizationManager authorizationManager;
    @Autowired
    private DropDownListManager dropDownListManager;

    private List<OperationUnitType> operationUnitTypeList;
    private List<UserGroup> userGroupList;

    private UserGroup userGroup;
    private String isEnabled;

    private String userGroupId;
    private String operationUnitId;
    private String roleId;

    /**
	 * 
	 */
    @Override
    public String execute() {
        userGroupList = authorizationManager.finduserGroupList();
        operationUnitTypeList = dropDownListManager.findOperationUnitTypeList(true);
        request.put("statusOptionList", dropDownListManager.findStatusChooseOptionList());
        return SUCCESS;
    }

    /**
     * 批量更改用户有效性
     * 
     * @param
     * @return
     */
    public String changeUserisEnabled() throws JSONException {
        if (userGroupId == null || userGroupId.length() == 0) {
            request.put(JSON, new JSONObject().put("rs", Constants.RS_FALSE));
            return JSON;
        }
        int intEnabled;
        if (isEnabled.equals("true")) {
            intEnabled = 1;
        } else {
            intEnabled = 0;
        }
        authorizationManager.updateUserisEnabled(Long.parseLong(userGroupId), intEnabled);
        request.put(JSON, new JSONObject().put("rs", Constants.RS_TRUE));
        return JSON;
    }

    /**
     * 批量授权角色
     * 
     * @return
     */
    public String grantRoles() throws JSONException {
        if (userGroupId == null || operationUnitId == null || roleId == null) {
            request.put(JSON, new JSONObject().put("rs", Constants.RS_FALSE));
            return JSON;
        }
        Long userGroupid = Long.parseLong(userGroupId);
        Long operationUnitid = Long.parseLong(operationUnitId);
        Long roleid = Long.parseLong(roleId);
        if (authorizationManager.grantUserRoles(userGroupid, operationUnitid, roleid) > 0) {
            request.put(JSON, new JSONObject().put("rs", Constants.RS_TRUE));
        } else {
            request.put(JSON, new JSONObject().put("rs", Constants.RS_FALSE));
        }
        return JSON;
    }

    /**
     * 批量脱离角色
     * 
     * @return
     */
    public String deleteRoles() throws JSONException {
        if (userGroupId == null || roleId == null) {
            request.put(JSON, new JSONObject().put("rs", Constants.RS_FALSE));
            return JSON;
        }
        Long userGroupid = Long.parseLong(userGroupId);
        Long operationUnitid = Long.parseLong(operationUnitId);
        Long roleid = Long.parseLong(roleId);
        if (authorizationManager.deleteUserRoles(userGroupid, operationUnitid, roleid) > 0) {
            request.put(JSON, new JSONObject().put("rs", Constants.RS_TRUE));
        } else {
            request.put(JSON, new JSONObject().put("rs", Constants.RS_FALSE));
        }
        return JSON;
    }

    // GETTER && SETTER
    public String getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(String isEnabled) {
        this.isEnabled = isEnabled;
    }

    public UserGroup getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }


    public List<OperationUnitType> getOperationUnitTypeList() {
        return operationUnitTypeList;
    }

    public void setOperationUnitTypeList(List<OperationUnitType> operationUnitTypeList) {
        this.operationUnitTypeList = operationUnitTypeList;
    }

    public List<UserGroup> getUserGroupList() {
        return userGroupList;
    }

    public void setUserGroupList(List<UserGroup> userGroupList) {
        this.userGroupList = userGroupList;
    }

    public String getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(String userGroupId) {
        this.userGroupId = userGroupId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getOperationUnitId() {
        return operationUnitId;
    }

    public void setOperationUnitId(String operationUnitId) {
        this.operationUnitId = operationUnitId;
    }

}
