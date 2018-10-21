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
import java.util.Map;

import loxia.support.json.JSONArray;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.wms.manager.DropDownListManager;
import com.jumbo.wms.manager.authorization.AuthorizationManager;
import com.jumbo.wms.model.authorization.UserGroup;
import com.jumbo.wms.model.command.UserGroupCommand;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;

/**
 * 
 * @author wanghua
 * 
 */
public class UserGroupAction extends BaseJQGridProfileAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = 6472266439882073082L;
    @Autowired
    private DropDownListManager dropDownListManager;
    @Autowired
    private AuthorizationManager authorizationManager;
    /**
     * 显示到可编辑表格/需要跟新的userGroupList/下拉列表
     */
    private List<UserGroup> userGroupList;
    /**
     * 新增的UserGroupList
     */
    private List<UserGroup> addList;
    private List<UserGroup> deleteList;
    /**
     * 要删除的用户分组的ID/要关联/脱离的的用户ID
     */
    private List<Long> keyList;
    /**
     * 状态下拉列表
     */
    private List<ChooseOption> statusOptionList;
    /**
     * 查询用户的查询参数
     */
    private UserGroupCommand userGroupCommand;

    @Override
    public String execute() throws Exception {
        userGroupList = dropDownListManager.findUserGroupList();
        statusOptionList = dropDownListManager.findStatusChooseOptionList();
        return SUCCESS;
    }

    /**
     * 保存用户分组列表
     * 
     * @return
     * @throws Exception
     */
    public String save() throws Exception {
        try {
            Map<String, List<String>> failures = authorizationManager.updateUserGroupList(addList, userGroupList, deleteList);

            request.put(JSON, new JSONObject(failures));
        } catch (Exception e) {
            log.error("## UserGroupAction.save.Exception");
            request.put(JSON, new JSONObject().put("msg", "failure"));
        }
        return JSON;
    }

    /**
     * 关联用户分组
     * 
     * @return
     * @throws Exception
     */
    public String addUsers() throws Exception {
        if (userGroupCommand != null && userGroupCommand.getGroupId() != null && keyList != null && !keyList.isEmpty()) {
            authorizationManager.addUsers(userGroupCommand.getGroupId(), keyList);
        }
        request.put(JSON, new JSONObject().put("msg", SUCCESS));
        return JSON;
    }

    /**
     * 脱离用户分组
     * 
     * @return
     * @throws Exception
     */
    public String removeUsers() throws Exception {
        if (userGroupCommand != null && userGroupCommand.getGroupId() != null && keyList != null && !keyList.isEmpty()) {
            authorizationManager.removeUsers(userGroupCommand.getGroupId(), keyList);
        }
        request.put(JSON, new JSONObject().put("msg", SUCCESS));
        return JSON;
    }

    /**
     * 用户表格
     * 
     * @return
     */
    public String userList() {
        try {
            setTableConfig();
            request.put(JSON, toJson(authorizationManager.findUserPByUserGroupCommand(tableConfig.getStart(), tableConfig.getPageSize(), userGroupCommand, tableConfig.getSorts())));
        } catch (Exception e) {
            log.error("----------UserGroupAction.userList{}", e.getMessage());
        }

        return JSON;
    }

    /**
     * 检验用户分组中是否有用户
     * 
     * @return
     */
    public String checkUsergroupDelable() {
        JSONArray json = null;
        if (keyList != null && !keyList.isEmpty()) {
            List<Long> list = authorizationManager.checkUserGroupDelable(keyList);
            if (list != null && !list.isEmpty()) {
                json = JsonUtil.collection2json(list);
            }
        }
        request.put(JSON, json);
        return JSON;
    }

    public List<UserGroup> getUserGroupList() {
        return userGroupList;
    }

    public void setUserGroupList(List<UserGroup> userGroupList) {
        this.userGroupList = userGroupList;
    }

    public List<UserGroup> getAddList() {
        return addList;
    }

    public void setAddList(List<UserGroup> addList) {
        this.addList = addList;
    }

    public UserGroupCommand getUserGroupCommand() {
        return userGroupCommand;
    }

    public void setUserGroupCommand(UserGroupCommand userGroupCommand) {
        this.userGroupCommand = userGroupCommand;
    }

    public List<Long> getKeyList() {
        return keyList;
    }

    public void setKeyList(List<Long> keyList) {
        this.keyList = keyList;
    }

    public List<ChooseOption> getStatusOptionList() {
        return statusOptionList;
    }

    public void setStatusOptionList(List<ChooseOption> statusOptionList) {
        this.statusOptionList = statusOptionList;
    }

    public List<UserGroup> getDeleteList() {
        return deleteList;
    }

    public void setDeleteList(List<UserGroup> deleteList) {
        this.deleteList = deleteList;
    }

}
