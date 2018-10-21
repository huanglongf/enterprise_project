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

import java.security.MessageDigest;
import java.util.List;

import loxia.dao.Pagination;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.Constants;
import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseAction;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.DropDownListManager;
import com.jumbo.wms.manager.authorization.AuthorizationManager;
import com.jumbo.wms.manager.system.ChooseOptionManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.model.authorization.OperationUnitType;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.authorization.UserGroup;
import com.jumbo.wms.model.authorization.UserInfo;
import com.jumbo.wms.model.authorization.UserRole;
import com.jumbo.wms.model.command.UserCommand;
import com.jumbo.wms.model.command.authorization.UserRoleCmd;
import com.jumbo.wms.model.system.ChooseOption;

/**
 * 
 * @author zhl
 * 
 */
public class UserManageAction extends BaseJQGridProfileAction {
    protected static final Logger logger = LoggerFactory.getLogger(BaseAction.class);
    private static final long serialVersionUID = 3580808953496383993L;
    @Autowired
    private AuthorizationManager authorizationManager;
    private User user;
    private UserGroup userGroup;
    private List<OperationUnitType> opeUnitType;
    private List<UserGroup> userGroupList;
    private List<ChooseOption> statusOptionList;
    @Autowired
    private DropDownListManager dropDownListManager;
    @Autowired
    private ChooseOptionManager chooseOptionManager;
    @Autowired
    private WareHouseManager wareHouseManager;
    private List<User> userList;
    private List<UserRole> userRoleList;

    private UserCommand usercmd;

    private String adminPwd;

    /**
     * 用户关联仓库Id
     */
    private Long whOuId;
    /**
     * 用户关联用户Id
     */
    private Long userId;

    /**
     * 进入增加用户页面
     * 
     * @return
     */
    public String addUserEntry() {
        opeUnitType = dropDownListManager.findOperationUnitTypeList(true);
        userGroupList = dropDownListManager.findUserGroupList();
        return SUCCESS;
    }

    /**
     * 增加用户
     * 
     * @return
     * @throws Exception
     */
    public String addUserAndUserRoleList() throws Exception {
        // 调用cas接口 "
        String result = authorizationManager.toCasRegister(user);
        result = new JSONObject(result).get("code") + "";
        if (result.equals(Constants.CAS_REGISTER_SUCCESS)) {
            user.setMemo(this.CharacterReplace(user.getMemo()));
            authorizationManager.createUserAndUserRoleList(userRoleList, user);
            this.asynReturnTrueValue();
        } else if (result.equals(Constants.CAS_REGISTER_ERROR)) {
            // CAS 中已经存在 需要添加的用户，判断当前系统中是否存在此用户 如果不存在那么创建此用户 并且提示创建成功 如果存在提示CAS错误
            if (!authorizationManager.existsUser(user.getLoginName())) {
                user.setMemo(this.CharacterReplace(user.getMemo()));
                authorizationManager.createUserAndUserRoleList(userRoleList, user);
                this.asynReturnTrueValue();
            } else {
                request.put(JSON, new JSONObject().put("result", "userIsExists"));
            }
        } else if (result.equals(Constants.CAS_REGISTER_ERROR1)) {
            request.put(JSON, new JSONObject().put("result", "userIsExists1"));// 用户存在但工号不同
        } else {
            request.put(JSON, new JSONObject().put("result", "isNotSubmit"));
        }
        return JSON;
    }

    /***
     * 修改用户
     * 
     * @return
     * @throws JSONException
     */
    public String modifyUserAndUserRoleList() throws Exception {
        user.setMemo(this.CharacterReplace(user.getMemo()));
        String r = authorizationManager.updateUserAndUserRoleList(userRoleList, user, adminPwd);
        if (r.equals(Constants.CAS_SUPERMODIFY_ERROR)) {// 超级用户更新密码失败，找不到用户
            request.put(JSON, new JSONObject().put("result", "userIsNotExists"));
        } else if (r.equals(Constants.CAS_SUPERMODIFY_ERROR1)) {// 超级用户更新密码失败，超级用户密码错误
            request.put(JSON, new JSONObject().put("result", "adminPwdError"));
        } else if ("".equals(r) || r.equals(Constants.CAS_SUPERMODIFY_SUCCESS)) {
            this.asynReturnTrueValue();
        }
        return JSON;
    }

    /**
     * 用户密码修改
     * 
     * @return
     */
    public String passwordModifyEntry() {
        return SUCCESS;
    }

    /**
     * 用户密码修改
     * 
     * @return
     */
    public String passwordModify() throws Exception {
        logger.debug("usercmd password {}", usercmd.getPassword());
        logger.debug("usercmd new password {}", usercmd.getNewPassword());
        logger.debug("usercmd confirm password {}", usercmd.getConfirmPassword());
        JSONObject result = new JSONObject();
        try {
            String r = authorizationManager.toCasPwdModify(userDetails.getUser().getLoginName(), usercmd);
            r = new JSONObject(r).get("code") + "";
            if (r.equals(Constants.CAS_PWDMODFIFY_SUCCESS)) {
                result.put("user", JsonUtil.obj2json(userDetails.getUser()));
                result.put("result", SUCCESS);
            } else if (r.equals(Constants.CAS_PWDMODFIFY_ERROR)) {
                throw new BusinessException(ErrorCode.USER_LOGIN_NAME_IS_NULL);
            } else if (r.equals(Constants.CAS_PWDMODFIFY_ERROR1)) {
                throw new BusinessException(ErrorCode.USER_RAW_PASSWORD_IS_ERROR);
            } else {
                request.put(JSON, new JSONObject().put("result", "isNotSubmit"));
            }
        } catch (BusinessException e) {
            result.put("result", ERROR);
            result.put("message", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, result);
        return JSON;
    }

    public String viewUserEntry() {
        statusOptionList = chooseOptionManager.findOptionListByCategoryCode(Constants.CHOOSEOPTION_CATEGORY_CODE_STATUS);
        userGroupList = dropDownListManager.findUserGroupList();
        return SUCCESS;
    }

    public String deleleUserList() {
        authorizationManager.deleleUserList(userList);
        this.asynReturnTrueValue();
        return JSON;
    }

    public String modifyUserEntry() {
        statusOptionList = chooseOptionManager.findOptionListByCategoryCode(Constants.CHOOSEOPTION_CATEGORY_CODE_STATUS);
        userGroupList = dropDownListManager.findUserGroupList();
        opeUnitType = dropDownListManager.findOperationUnitTypeList(true);
        return SUCCESS;
    }

    /**
     * 用户列表
     * 
     * @return
     */
    public String findUserList() {
        Pagination<UserInfo> list = new Pagination<UserInfo>();
        setTableConfig();
        list = authorizationManager.findUserList(whOuId, user, userGroup, tableConfig.getCurrentPage(), tableConfig.getPageSize(), tableConfig.getSorts());
        request.put(JSON, toJson(list));
        return JSON;
    }

    public String findUserByID() {
        User u = authorizationManager.findUserByID(user.getId());
        request.put(JSON, new JSONObject(u));
        return JSON;
    }

    public String viewUserInfoEntry() {
        user = authorizationManager.findUserByID(user.getId());
        return SUCCESS;
    }

    /**
     * 用户的角色列表
     * 
     * @return
     */
    public String findRoleListById() {
        if (user.getId() == null) {
            return JSON;
        }
        setTableConfig();
        List<UserRoleCmd> list = authorizationManager.findUserRoleCmdListByUserId(user.getId(), null);
        request.put(JSON, toJson(list));
        return JSON;
    }

    /**
     * 得到组织类型对应的组织和角色
     * 
     */
    public String findOperationUnitByOuTypeID() {
        JSONObject json = new JSONObject();
        try {
            Long ouTypeId = user.getOu().getOuType().getId();
            System.out.println("---------------ouTypeId:" + ouTypeId);
            json.put("oulist", authorizationManager.findOperationUnitByOuTypeID(ouTypeId, true));
            json.put("rolelist", authorizationManager.findRoleByOuTypeID(ouTypeId, true));
        } catch (Exception e) {
            // e.printStackTrace();
            if (log.isErrorEnabled()) {
                log.error("UserManageAction.findOperationUnitByOuTypeID.exception", e);
            }
            // log.error("UserManageAction.findOperationUnitByOuTypeID.exception");
        }
        request.put(JSON, json);
        return JSON;
    }

    /**
     * 用户仓库绑定
     * 
     * @return
     */
    public String whUserRef() {
        statusOptionList = chooseOptionManager.findOptionListByCategoryCode(Constants.CHOOSEOPTION_CATEGORY_CODE_STATUS);
        userGroupList = dropDownListManager.findUserGroupList();
        opeUnitType = dropDownListManager.findOperationUnitTypeList(true);
        return SUCCESS;
    }

    /**
     * 保存用户仓库绑定
     * 
     * @return
     * @throws JSONException
     */
    public String saveUserWhRef() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            wareHouseManager.saveUserWhRef(userId, whOuId);
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            result.put("msg", getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, result);
        return JSON;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OperationUnitType> getOpeUnitType() {
        return opeUnitType;
    }

    public void setOpeUnitType(List<OperationUnitType> opeUnitType) {
        this.opeUnitType = opeUnitType;
    }

    public List<UserGroup> getUserGroupList() {
        return userGroupList;
    }

    public void setUserGroupList(List<UserGroup> userGroupList) {
        this.userGroupList = userGroupList;
    }

    public List<ChooseOption> getStatusOptionList() {
        return statusOptionList;
    }

    public void setStatusOptionList(List<ChooseOption> statusOptionList) {
        this.statusOptionList = statusOptionList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<UserRole> getUserRoleList() {
        return userRoleList;
    }

    public void setUserRoleList(List<UserRole> userRoleList) {
        this.userRoleList = userRoleList;
    }

    public UserGroup getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }

    public UserCommand getUsercmd() {
        return usercmd;
    }

    public void setUsercmd(UserCommand usercmd) {
        this.usercmd = usercmd;
    }

    public String getAdminPwd() {
        return adminPwd;
    }

    public void setAdminPwd(String adminPwd) {
        this.adminPwd = adminPwd;
    }

    public static String getMD5(String code) throws Exception {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        byte[] bytes = code.getBytes();
        byte[] results = messageDigest.digest(bytes);
        StringBuilder stringBuilder = new StringBuilder();

        for (byte result : results) {
            stringBuilder.append(String.format("%02x", result));
        }

        return stringBuilder.toString();
    }

    public Long getWhOuId() {
        return whOuId;
    }

    public void setWhOuId(Long whOuId) {
        this.whOuId = whOuId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
