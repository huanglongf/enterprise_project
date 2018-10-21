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

package com.jumbo.wms.manager.authorization;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.dao.support.BeanPropertyRowMapperExt;
import loxia.support.json.JSONArray;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;
import loxia.utils.PropListCopyable;
import loxia.utils.PropertyUtil;

import org.apache.commons.httpclient.HttpException;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.authorization.OperationUnitRowMapper;
import com.jumbo.dao.authorization.PhysicalWarehouseDao;
import com.jumbo.dao.authorization.PrivilegeDao;
import com.jumbo.dao.authorization.RoleDao;
import com.jumbo.dao.authorization.SysPwdPolicyDao;
import com.jumbo.dao.authorization.UserDao;
import com.jumbo.dao.authorization.UserGroupDao;
import com.jumbo.dao.authorization.UserRoleDao;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.system.SysLoginLogDao;
import com.jumbo.util.JsonUtil;
import com.jumbo.util.zip.AppSecretUtil;
import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.authorization.OperationUnitType;
import com.jumbo.wms.model.authorization.Privilege;
import com.jumbo.wms.model.authorization.Role;
import com.jumbo.wms.model.authorization.SysPwdPolicy;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.authorization.UserGroup;
import com.jumbo.wms.model.authorization.UserInfo;
import com.jumbo.wms.model.authorization.UserRole;
import com.jumbo.wms.model.command.OperationUnitCommand;
import com.jumbo.wms.model.command.UserCommand;
import com.jumbo.wms.model.command.UserGroupCommand;
import com.jumbo.wms.model.command.authorization.RoleCmd;
import com.jumbo.wms.model.command.authorization.UserDetailsCmd;
import com.jumbo.wms.model.command.authorization.UserRoleCmd;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.system.SysLoginLog;
import com.jumbo.wms.model.system.SysLoginLogCommand;
import com.jumbo.wms.model.warehouse.PhysicalWarehouse;

@Transactional
@Service("authorizationManager")
public class AuthorizationManagerImpl extends BaseManagerImpl implements AuthorizationManager {

    /**
	 * 
	 */
    private static final long serialVersionUID = 8310122118984383608L;

    @Autowired
    private SysLoginLogDao sysLoginLogDao;
    @Autowired
    private OperationUnitDao operationUnitDao;
    @Autowired
    private PhysicalWarehouseDao physicalWarehouseDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private PrivilegeDao privilegeDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserGroupDao userGroupDao;
    @Autowired
    private SysPwdPolicyDao sysPwdPolicyDao;
    @Autowired
    private ChooseOptionDao chooseOptionDao;

    public boolean existsUser(String loginName) {
        List<User> users = userDao.findUserListByLoginName(loginName);
        return users != null && users.size() > 0;
    }

    // Add方法
    public OperationUnit createOperationUnit(OperationUnit operationUnit) {
        if (operationUnitDao.findOperationUnitCountByCodeName(operationUnit.getCode(), operationUnit.getName(), null) > 0) {
            throw new IllegalArgumentException("AuthorizationManagerImpl.addOperationUnit失败,数据库已经存在相同的编码/名字" + operationUnit.getCode() + "-" + operationUnit.getName());
        }
        operationUnit.setLastModifyTime(new Date());
        return operationUnitDao.save(operationUnit);
    }

    // Find方法

    @Transactional(readOnly = true)
    public List<OperationUnit> findOperationUnitListSql() {
        return build(operationUnitDao.findOperationUnitListSql(new OperationUnitRowMapper()));
    }

    @Transactional(readOnly = true)
    public Pagination<Role> findRoleList(int currentPage, int size, Role role, Sort[] sorts) {
        String name = null;
        Long ouTypeId = null;
        if (role != null) {
            ouTypeId = role.getOuType() == null ? null : role.getOuType().getId();
            if (role.getName() != null && role.getName().trim().length() > 0) {
                name = "%" + role.getName().trim() + "%";
            }
        }
        return roleDao.findRoleByRoleNameAndOuTypeId(currentPage, size, name, ouTypeId, sorts);
    }

    public Pagination<RoleCmd> findRoleCmdList(int currentPage, int size, Role role, Sort[] sorts) {
        Pagination<Role> scpg = findRoleList(currentPage, size, role, sorts);
        Pagination<RoleCmd> pg = new Pagination<RoleCmd>();
        try {
            PropertyUtil.copyProperties(scpg, pg, new PropListCopyable("count", "currentPage", "totalPages", "start", "size"));
            List<RoleCmd> items = new ArrayList<RoleCmd>();
            for (Role r : scpg.getItems()) {
                RoleCmd cmd = new RoleCmd();
                PropertyUtil.copyProperties(r, cmd, new PropListCopyable("id", "name", "description", "isAvailable"));
                cmd.setOuTypeDisplayName(r.getOuType().getDisplayName());
                cmd.setOuTypeid(r.getOuType().getId());
                items.add(cmd);
            }
            pg.setItems(items);
        } catch (IllegalAccessException e) {
            log.error("", e);
        } catch (InvocationTargetException e) {
            log.error("", e);
        } catch (NoSuchMethodException e) {
            log.error("", e);
        }
        return pg;
    }

    @Transactional(readOnly = true)
    public List<UserRole> findUserRoleListByUserId(Long userId, Sort[] sorts) {
        List<UserRole> list = userRoleDao.findUserRoleListByUserId(userId, sorts);
        return list;
    }

    public List<UserRoleCmd> findUserRoleCmdListByUserId(Long userId, Sort[] sorts) {
        List<UserRole> list = userRoleDao.findUserRoleListByUserId(userId, sorts);
        List<UserRoleCmd> cmdlist = new ArrayList<UserRoleCmd>();
        if (list != null) {
            for (UserRole ur : list) {
                UserRoleCmd cmd = new UserRoleCmd();
                cmd.setIsDefault(ur.getIsDefault());
                cmd.setUserid(ur.getUser().getId());
                cmd.setId(ur.getId());
                cmd.setOuid(ur.getOu().getId());
                cmd.setOuName(ur.getOu().getName());
                cmd.setRoleid(ur.getRole().getId());
                cmd.setRoleName(ur.getRole().getName());
                cmdlist.add(cmd);
            }
        }
        return cmdlist;
    }

    /**
     * 设为默认登录角色
     * 
     * @param roleId
     * @param ouId
     * @parem userId
     */
    @Transactional
    public int updateUserRoleToDefault(Long roleId, Long ouId, Long userId) {
        return userRoleDao.updateUserRoleToDefault(roleId, ouId, userId);
    }

    // Update方法
    @Transactional
    public OperationUnit updateOperationUnit(OperationUnit operationUnit) {
        if (operationUnitDao.findOperationUnitCountByCodeName(null, operationUnit.getName(), operationUnit.getId()) > 0) {
            throw new IllegalArgumentException("AuthorizationManagerImpl.addOperationUnit失败,数据库已经存在相同的名字" + operationUnit.getName());
        }
        OperationUnit dbOu = operationUnitDao.getByPrimaryKey(operationUnit.getId());
        try {
            PropertyUtil.copyProperties(operationUnit, dbOu, new PropListCopyable(new String[] {"name", "comment", "isAvailable"}));
        } catch (Exception e) {
            log.error("Copy Bean properties error for OperationUnit");
            log.error("", e);
            throw new RuntimeException("Copy Bean properties error for OperationUnit");
        }
        if (!operationUnit.getIsAvailable()) {
            userRoleDao.deleteUserRolesByOu(operationUnit.getId());
        }
        dbOu.setLastModifyTime(new Date());
        operationUnitDao.save(dbOu);
        return null;
    }

    public void deleteRoleList(List<Role> roleList) {
        for (int i = 0; i < roleList.size(); i++) {
            Role dbRole = roleDao.getByPrimaryKey(roleList.get(i).getId());
            dbRole.setIsAvailable(false);
            roleDao.save(dbRole);
        }
    }

    public Role createRole(Role role) {
        Role r = roleDao.save(role);
        return r;
    }

    /**
     * 把Oracle的层级结构转成多级结构
     * 
     * @param list
     * @return
     */
    private List<OperationUnit> build(List<OperationUnit> list) {
        List<OperationUnit> root = new ArrayList<OperationUnit>();
        if (list == null || list.isEmpty()) {
            return list;
        }
        Map<Long, OperationUnit> map = new HashMap<Long, OperationUnit>();
        // for(OperationUnit each:list){
        // map.put(each.getId(), each);
        // }
        for (OperationUnit each : list) {
            if (each.getParentUnit() == null) {
                root.add(each);
            } else {
                getChildrenForOu(map.get(each.getParentUnit().getId())).add(each);
            }
            map.put(each.getId(), each);
        }
        return root;
    }

    /**
     * List转为JSONArray
     * 
     * @param list
     * @return
     * @throws Exception
     */
    private JSONArray rebuild(List<OperationUnit> list) throws Exception {
        List<JSONObject> listJson = new ArrayList<JSONObject>();
        for (OperationUnit each : list) {
            listJson.add(ouToJson(each));
        }
        return new JSONArray(listJson);
    }

    /**
     * 把OperationUnit转为前台需要的JSON格式
     * 
     * @param obj
     * @return
     * @throws Exception
     */
    private JSONObject ouToJson(OperationUnit obj) throws Exception {
        JSONObject json = new JSONObject();
        json.put("id", obj.getId());
        json.put("text", obj.getName() + "(" + obj.getOuType().getName() + ")-" + obj.getCode());
        JSONObject attributes = new JSONObject();
        attributes.put("name", obj.getName());
        attributes.put("type", obj.getOuType().getDisplayName());
        attributes.put("code", obj.getCode());
        attributes.put("available", obj.getIsAvailable());
        attributes.put("comment", obj.getComment());
        json.put("attributes", attributes);
        if (obj.getChildrenUnits() != null && obj.getChildrenUnits().size() > 0) {
            json.put("children", rebuild(obj.getChildrenUnits()));
        }
        return json;
    }

    private List<OperationUnit> getChildrenForOu(OperationUnit obj) {
        List<OperationUnit> children = obj.getChildrenUnits();
        if (children == null) {
            children = new ArrayList<OperationUnit>();
        }
        return children;
    }

    /**
     * role的Id,为空的时候为Save Role 否则为update
     */
    public void updateOrSaveRoleAndPriv(Role role, List<Privilege> privlist) {
        Role r = null;
        if (role.getId() == null) {
            r = roleDao.save(role);
        } else {
            r = roleDao.findRoleByID(role.getId());
        }
        if (role.getDescription() != null) {
            r.setDescription(role.getDescription());
        }
        r.setName(role.getName());
        List<Privilege> list = new LinkedList<Privilege>();
        /**
         * 非本系统不做调整
         */
        for (Privilege pr : r.getPrivileges()) {
            if (!Constants.SYSTEM_CODE.equals(pr.getSystemName())) {
                list.add(pr);
            }
        }
        if (privlist != null) {
            for (int i = 0; i < privlist.size(); i++) {
                Privilege p = privlist.get(i);
                if (p == null) {
                    continue;
                }
                p = privilegeDao.getByPrimaryKey(p.getAcl());
                p.setRoles(null);
                list.add(p);
            }
        }
        r.setPrivileges(list);
        roleDao.save(r);
    }

    public JSONArray findRoleById(Long id) {
        return JsonUtil.collection2json(privilegeDao.findPrivilegeAclByRoleId(id, new SingleColumnRowMapper<String>(String.class)));
    }

    public OperationUnit getOUByPrimaryKey(Long id) {
        OperationUnit ou = operationUnitDao.getByPrimaryKey(id);
        OperationUnitCommand ouc = null;
        if (ou != null) {
            ouc = new OperationUnitCommand();
            try {
                org.springframework.beans.BeanUtils.copyProperties(ou, ouc);
                OperationUnitType out = new OperationUnitType();
                if (ou.getOuType() != null) {
                    org.springframework.beans.BeanUtils.copyProperties(ou.getOuType(), out);
                    out.setOus(null);
                    out.setParentUnitType(null);
                    ouc.setOuType(out);
                }
                if (ou.getParentUnit() != null) {
                    OperationUnitCommand pouc = new OperationUnitCommand();
                    org.springframework.beans.BeanUtils.copyProperties(ou.getParentUnit(), pouc);

                    if (ou.getParentUnit().getParentUnit() != null) {
                        OperationUnitCommand ppouc = new OperationUnitCommand();
                        org.springframework.beans.BeanUtils.copyProperties(ou.getParentUnit().getParentUnit(), ppouc);
                        ppouc.setParentUnit(null);
                        ppouc.setOuType(null);
                        ppouc.setChildrenUnits(null);
                        pouc.setParentUnit(ppouc);
                    }
                    pouc.setOuType(null);
                    pouc.setChildrenUnits(null);
                    ouc.setParentUnit(pouc);
                }
                ouc.setChildrenUnits(null);
            } catch (Exception e) {
                log.error("Copy Bean properties error for OperationUnit");
                log.error("", e);
                throw new RuntimeException("Copy Bean properties error for OperationUnit");
            }
        }

        return ouc;
    }

    public Role findRoleInfoById(Long id) {
        RoleCmd ro = new RoleCmd();
        Role r = roleDao.findRoleByID(id);
        BeanUtils.copyProperties(r, ro);
        ro.setPrivileges(null);
        return ro;
    }

    // ------------------------user
    public void createUser(User user) {
        user.setCreateTime(new Date());
        userDao.save(user);
    }

    @Transactional(readOnly = true)
    public Pagination<UserInfo> findUserList(Long whOuId, User user, UserGroup userGroup, int start, int pageSize, Sort[] sorts) {
        if (user == null) {
            user = new User();
        }
        Map<String, Object> m = queryUserMap(whOuId, user, userGroup);
        Pagination<UserInfo> list = null;
        list = userDao.findUserListSql((start - 1) * pageSize, pageSize, m, new BeanPropertyRowMapper<UserInfo>(UserInfo.class), sorts);
        return list;
    }

    @Transactional(readOnly = true)
    public User findUserByID(Long id) {
        User user = userDao.getByPrimaryKey(id);
        return user;

    }

    @Transactional(readOnly = true)
    public List<UserRole> findRoleListByUserId(Long id) {
        return userRoleDao.findUserRoleListByUserId(id, null);
    }

    public void createUserAndUserRoleList(List<UserRole> list, User user) {
        user.setCreateTime(new Date());
        OperationUnit oux = operationUnitDao.findOperationUnitListByCode(OperationUnitType.OUTYPE_ROOT).get(0);
        user.setOu(oux);
        // 从User对象中取出取出密码，然后调用MD5加密方法加密
        String password = user.getPassword();
        String loginName = user.getLoginName();
        String md5Password = AppSecretUtil.getMD5Str(password + "{" + loginName + "}");
        user.setPassword(md5Password);
        userDao.save(user);
        if (list == null) {
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            UserRole ur = list.get(i);
            ur.setUser(user);
            userRoleDao.save(ur);
        }

    }

    public String updateUserAndUserRoleList(List<UserRole> list, User user, String adminPwd) throws JSONException {
        User u = userDao.getByPrimaryKey(user.getId());
        u.setMaxNum(user.getMaxNum());
        String r = "";
        ChooseOption c = chooseOptionDao.findByCategoryCode(ChooseOption.CATEGORY_CODE_ADMIN_PASSWORD);
        try {
            PropertyUtil.copyProperties(user, u, new PropListCopyable(new String[] {"userName", "email", "phone", "jobNumber", "memo"}));
            if (StringUtils.hasText(user.getPassword())) {
                String k = "u=" + u.getLoginName() + "p=" + user.getPassword() + "sp=" + c.getOptionValue() + "f=wms" + USER_CLIENT_KEY;
                String params = "u=" + u.getLoginName() + "&p=" + user.getPassword() + "&sp=" + c.getOptionValue() + "&f=wms" + "&k=" + AppSecretUtil.getMD5Str(k);
                r = this.post(CAS_USER_PWD_UPDATE_SU, params);
            }
        } catch (Exception e) {
            log.error("", e);
            throw new RuntimeException("Copy Bean properties error for User");
        }
        if (!r.equals("")) {
            r = new JSONObject(r).get("code") + "";
            if (!r.equals(Constants.CAS_SUPERMODIFY_SUCCESS)) {// 如果操作不成功
                return r;
            }
        }
        u.setLatestUpdateTime(new Date());
        User newUser = userDao.save(u);
        userRoleDao.delUserRoleByUserIdSql(user.getId());
        if (list == null) {
            return r;
        }
        for (int i = 0; i < list.size(); i++) {
            UserRole ur = list.get(i);
            ur.setUser(newUser);
            userRoleDao.save(ur);
        }
        return r;
    }

    public void deleleUserList(List<User> userList) {
        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);
            User u = userDao.getByPrimaryKey(user.getId());
            u.setIsEnabled(false);
            userDao.save(u);
        }
    }

    private Map<String, Object> queryUserMap(Long whOuId, User user, UserGroup userGroup) {
        Map<String, Object> m = new HashMap<String, Object>();
        if (userGroup != null && userGroup.getId() > 0) {
            m.put("groupId", userGroup.getId());
        }
        if (StringUtils.hasLength(user.getLoginName())) {
            m.put("loginName", "%" + user.getLoginName() + "%");
        }
        if (StringUtils.hasLength(user.getUserName())) {
            m.put("userName", "%" + user.getUserName() + "%");
        }
        if (StringUtils.hasLength(user.getEmail())) {
            m.put("email", "%" + user.getEmail() + "%");
        }
        if (StringUtils.hasLength(user.getPhone())) {
            m.put("phone", "%" + user.getPhone() + "%");
        }
        if (StringUtils.hasLength(user.getJobNumber())) {
            m.put("jobNumber", "%" + user.getJobNumber() + "%");
        }
        if (StringUtils.hasLength(user.getMemo())) {
            m.put("isEnabled", Boolean.valueOf(user.getMemo()));
        }
        if (whOuId != null) {
            m.put("whOuId", whOuId);
        }
        return m;
    }

    /**
     * 查询userGroup列表
     * 
     * @return list
     */
    @Transactional(readOnly = true)
    public List<UserGroup> finduserGroupList() {
        return userGroupDao.findUserGroupList();
    }

    /**
     * 根据用户组id，批量修改用户有效性
     * 
     * @return
     * @param userGroupId 用户组id
     * @param isEnabled 有效性
     */
    public int updateUserisEnabled(Long userGroupId, int isEnabled) {
        return userDao.updateUserisEnabled(userGroupId, isEnabled);
    }

    /**
     * 根据用户组id查找用户列表
     * 
     * @return userList
     * @param userGroupid 用户组id
     */
    @Transactional(readOnly = true)
    public List<User> findUserListByUserGroupId(Long userGroupid) {
        return userDao.findUserListByUserGroupCommand(userGroupid, new BeanPropertyRowMapper<User>(User.class));
    }

    /**
     * 根据UserGroupId分页查询User
     * 
     * @param start
     * @param pageSize
     * @param userGroupId
     * @param sorts
     * @return
     */
    public Pagination<User> findUserPByUserGroupCommand(int start, int pageSize, UserGroupCommand command, Sort[] sorts) {
        return userDao.findUserPByUserGroupCommand(start, pageSize, command.toMap(), sorts, new BeanPropertyRowMapper<User>(User.class));
    }

    public List<User> findUserListByLoginName(String loginName) {
        return userDao.findUserListByLoginName(loginName);
    }

    @Transactional
    public UserDetailsCmd findUserDetails(Long userId, Long roleId, Long ouId) {
        User user = userDao.getByPrimaryKey(userId);
        if (user == null) {
            throw new IllegalArgumentException(userId + "[user] is not existed.");
        }
        UserRole ur = userRoleDao.findUserRoleByUserIdRoleIdAndOuId(userId, roleId, ouId);
        if (ur != null) {
            updateUserRoleToDefault(roleId, ouId, userId);
        }
        return constructUserDetails(user, ur);
    }

    public UserDetailsCmd loadUserByUsername(String username) {
        User user = userDao.findByLoginName(username);
        if (user == null) {
            throw new BusinessException(username + " is not existed.");
        }
        UserRole ur = userRoleDao.findDefaultUserRole(user.getId());
        // 如果默认权限为空，随机查询一条权限
        if (ur == null) {
            ur = userRoleDao.findUserRoleByStochastic(user.getId());
        }
        return constructUserDetails(user, ur);
    }

    /**
     * 得到组织类型对应的角色
     * 
     */
    public JSONArray findRoleByOuTypeID(Long ouTypeId, Boolean isAvailable) {
        JSONArray ja = new JSONArray();
        try {
            List<Role> list = roleDao.findRoleByOuTypeIDSql(ouTypeId, isAvailable, new BeanPropertyRowMapper<Role>(Role.class));
            for (int i = 0; i < list.size(); i++) {
                Role r = list.get(i);
                JSONObject jo = new JSONObject();
                jo.put("name", r.getName());
                jo.put("id", r.getId());
                ja.put(jo);
            }
        } catch (Exception e) {
            log.error("", e);
        }
        return ja;
    }

    public JSONArray findOperationUnitByOuTypeID(Long ouTypeId, Boolean isAvailable) {
        JSONArray ja = new JSONArray();
        try {
            List<OperationUnit> list = operationUnitDao.findOperationUnitByOuTypeIDSql(ouTypeId, isAvailable, new BeanPropertyRowMapper<OperationUnit>(OperationUnit.class));
            for (int i = 0; i < list.size(); i++) {
                OperationUnit ou = list.get(i);
                JSONObject jo = new JSONObject();
                jo.put("name", ou.getName());
                jo.put("id", ou.getId());
                ja.put(jo);
            }
        } catch (Exception e) {
            log.error("", e);
        }
        return ja;
    }

    // UserGroup========================================================================
    /**
     * 创建用户分组
     * 
     * @param addList
     * @return
     */
    private List<String> createUserGroupList(List<UserGroup> addList) {
        if (addList == null || addList.isEmpty()) return null;
        List<String> addFailures = new ArrayList<String>();
        for (UserGroup each : addList) {
            // 是否需要根据name判断是否有同名UserGroup。
            if (each.getName().length() == 0) continue;
            try {
                if (userGroupDao.findUserGroupByName(each.getName(), null) != null) {
                    addFailures.add(each.getName());
                } else {
                    userGroupDao.save(each);
                }

            } catch (Exception e) {
                log.error("", e);
                log.error("----------Create UserGroup failure:name={},description={}", each.getName(), each.getDescription());
                addFailures.add(each.getName());
            }
        }
        return addFailures;
    }

    /**
     * 更新用户分组
     * 
     * @param updateList
     * @return
     */
    private List<String> upadteUserGroupList(List<UserGroup> updateList) {
        List<String> updateFailures = new ArrayList<String>();
        if (updateList == null || updateList.isEmpty()) return null;
        for (int i = 0; i < updateList.size(); i++) {
            UserGroup each = updateList.get(i);
            if (each == null) continue;
            try {
                // 是否需要根据name判断是否有同名UserGroup。
                UserGroup ug = userGroupDao.getByPrimaryKey(each.getId());
                ug.setDescription(each.getDescription());
                if (userGroupDao.findUserGroupByName(each.getName(), each.getId()) != null) {
                    updateFailures.add(each.getName());
                } else {
                    ug.setName(each.getName());
                    userGroupDao.save(ug);
                }

            } catch (Exception e) {
                log.error("", e);
                log.error("----------update UserGroup failure:name={},description={}", each.getName(), each.getDescription());
                updateFailures.add(each.getName());
            }
        }
        return updateFailures;
    }

    /**
     * 删除
     * 
     * @param deleteList
     */
    private void deleteUserGroupList(List<Long> deleteList) {
        userGroupDao.deleteAllByPrimaryKey(deleteList);
    }

    /**
     * 保存用户列表时的操作
     */
    @Transactional
    public Map<String, List<String>> updateUserGroupList(List<UserGroup> addList, List<UserGroup> updateList, List<UserGroup> deleteList) {
        Map<String, List<String>> failures = new HashMap<String, List<String>>();
        List<String> addFailures = addList == null || addList.isEmpty() ? null : createUserGroupList(addList);
        List<String> updateFailures = updateList == null || updateList.isEmpty() ? null : upadteUserGroupList(updateList);
        if (addFailures != null && !addFailures.isEmpty()) {
            failures.put("addFailures", addFailures);
        }
        if (updateFailures != null && !updateFailures.isEmpty()) {
            failures.put("updateFailures", updateFailures);
        }
        if (deleteList != null && !deleteList.isEmpty()) {
            List<Long> idList = new ArrayList<Long>();
            for (UserGroup each : deleteList) {
                idList.add(each.getId());
            }
            // 事物未提交,try-catch失败,整个方法的返回结果失败,但是预留
            try {
                deleteUserGroupList(idList);
            } catch (Exception e) {
                List<String> nameList = new ArrayList<String>();
                for (UserGroup each : deleteList) {
                    nameList.add(each.getName());
                }
                failures.put("deleteFailures", nameList);
            }

        }
        return failures;
    }

    /**
     * 关联用户到用户分组
     */
    @Transactional
    public void addUsers(Long userGroupId, List<Long> keyList) {
        if (keyList != null && !keyList.isEmpty()) {
            userGroupDao.addUsers(userGroupId, keyList);
        }
    }

    /**
     * 删除用户分组中的用户
     */
    @Transactional
    public void removeUsers(Long userGroupId, List<Long> keyList) {
        if (keyList != null && !keyList.isEmpty()) {
            userGroupDao.removeUsers(userGroupId, keyList);
        }
    }

    @Transactional
    public List<Long> checkUserGroupDelable(List<Long> keyList) {
        return userGroupDao.findUserGroupIdsWithUsers(keyList, new SingleColumnRowMapper<Long>(Long.class));
    }

    // UserUserRole========================================================================
    /**
     * 批量脱离用户角色 delete
     * 
     * @return
     * @param userGroupid 用户组id
     * @param roleid 角色id
     */
    public int deleteUserRoles(Long userGroupid, Long operationUnitid, Long roleid) {
        return userRoleDao.deleteUserRoles(userGroupid, operationUnitid, roleid);
    }

    /**
     * 批量授权用户角色
     * 
     * @param userGroupid
     * @param operationUnitid
     * @param roleid
     */
    @Transactional
    public int grantUserRoles(Long userGroupid, Long operationUnitid, Long roleid) {
        if (userGroupid == null || operationUnitid == null || roleid == null) return -1;
        return userRoleDao.addUserRoles(userGroupid, operationUnitid, roleid);

    }

    /**
     * 根据userid和roleid从UserRole表中查找UserRole Object
     * 
     * @return UserRole
     * @param userid 用户id
     * @param roleid 角色id
     */
    @Transactional(readOnly = true)
    public UserRole findUserRole(Long userid, Long roleid) {
        return userRoleDao.findUserRole(userid, roleid);
    }

    /**
     * 根据用户组id查找UserRole列表
     * 
     * @return list
     * @param userGroupid
     */
    @Transactional(readOnly = true)
    public List<UserRole> findUserRoleByuserGroupId(Long userGroupid) {
        return userRoleDao.findUserRoleByuserGroupId(userGroupid);
    }

    /**
     * 当前组织（运营中心）下的仓库列表
     * 
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public List<OperationUnit> findOperationUnitList(Long id) {
        List<OperationUnit> list = operationUnitDao.findOperationUnitList(id);
        List<OperationUnit> list2 = new ArrayList<OperationUnit>();
        if (list != null && !list.isEmpty()) {

            for (OperationUnit ou : list) {
                OperationUnit o = new OperationUnit();
                o.setId(ou.getId());
                o.setName(ou.getName());
                o.setCode(ou.getCode());
                o.setFullName(ou.getFullName());

                list2.add(o);
            }

            return list2;
        }
        return list;

    }

    public Boolean checkRoleName(Role role) {
        List<Role> list = roleDao.findRoleByName(role.getName());
        Boolean rs = false;
        if (role.getId() == null) {
            if (list.size() > 0) return true;
        } else {
            if (list.size() > 0) {
                Role r = list.get(0);
                if (!r.getId().equals(role.getId())) {
                    return true;
                }
            }
        }
        return rs;
    }

    /**
     * 仓库附加信息未填写的组织列表
     * 
     * @param start
     * @param pageSize
     * @param list
     * @param sorts
     * @return
     */
    @Transactional(readOnly = true)
    public Pagination<OperationUnit> findOuWarehouseList(int start, int pageSize, List<Object> list, Sort[] sorts) {
        if (list == null || list.isEmpty()) return null;
        List<Long> ouids = new ArrayList<Long>();
        for (int i = 0; i < list.size(); i++) {
            ouids.add(((OperationUnit) list.get(i)).getId());
        }
        return operationUnitDao.findOperationUnitWarehouseList(start, pageSize, ouids, sorts);
    }

    private User cloneUser(User user) {
        User u = new User();
        try {
            PropertyUtil.copyProperties(user, u, new PropListCopyable("id", "loginName", "userName", "password", "isSystem", "isAccNonExpired", "isPwdNonExpired", "isAccNonLocked", "isEnabled", "createTime", "latestUpdateTime", "latestAccessTime"));
        } catch (Exception e) {
            log.error("", e);
            throw new RuntimeException("Failed to construct UserDetails.");
        }
        u.setOu(cloneOperationUnit(user.getOu()));
        return u;
    }

    private UserDetailsCmd constructUserDetails(User user, UserRole ur) {
        UserDetailsCmd cmd = new UserDetailsCmd();
        cmd.setUser(cloneUser(user));
        if (ur == null) {
            return cmd;
        } else {
            List<String> grantedAuthorities = new ArrayList<String>();
            if (ur.getRole() != null) {
                for (Privilege p : ur.getRole().getPrivileges())
                    grantedAuthorities.add(p.getAcl());
            }
            cmd.setAuthorities(grantedAuthorities);
            UserRole tmpRole = cloneUserRole(cmd.getUser(), ur);
            cmd.setCurrentUserRole(tmpRole);
            return cmd;
        }
    }

    private UserRole cloneUserRole(User u, UserRole ur) {
        if (ur == null) {
            return null;
        }
        UserRole tmp = new UserRole();
        tmp.setId(ur.getId());
        tmp.setUser(u);
        tmp.setRole(cloneRole(ur.getRole()));
        tmp.setOu(cloneOperationUnit(ur.getOu()));
        return tmp;
    }

    private Role cloneRole(Role role) {
        if (role == null) {
            return null;
        }
        Role tmp = new Role();
        tmp.setId(role.getId());
        tmp.setName(role.getName());
        tmp.setDescription(role.getDescription());
        tmp.setIsSystem(role.getIsSystem());
        tmp.setOuType(cloneOperationUnitType(role.getOuType()));
        return tmp;
    }

    private OperationUnit cloneOperationUnit(OperationUnit ou) {
        if (ou == null) {
            return null;
        }
        OperationUnit tmp = new OperationUnit();
        try {
            PropertyUtil.copyProperties(ou, tmp, new PropListCopyable("id", "code", "name", "fullName", "isAvailable"));
        } catch (Exception e) {
            log.error("", e);
            throw new RuntimeException("Failed to clone OperationUnit.");
        }
        tmp.setParentUnit(cloneOperationUnit(ou.getParentUnit()));
        tmp.setOuType(cloneOperationUnitType(ou.getOuType()));
        tmp.setLastModifyTime(new Date());
        return tmp;
    }

    private OperationUnitType cloneOperationUnitType(OperationUnitType out) {
        if (out == null) return null;
        OperationUnitType tmp = new OperationUnitType();
        try {
            PropertyUtil.copyProperties(out, tmp, new PropListCopyable("id", "name", "displayName", "isAvailable", "description"));
        } catch (Exception e) {
            log.error("", e);
            throw new RuntimeException("Failed to clone OperationUnit.");
        }
        return tmp;
    }

    public Long findOperationUnit(OperationUnit operationUnit) {
        return operationUnitDao.findOperationUnitCountByCodeName(operationUnit.getCode(), operationUnit.getName(), null);
    }

    @Override
    public JSONArray selectAllCompany() {
        JSONArray ja = new JSONArray();
        try {
            List<OperationUnit> comList = operationUnitDao.selectAllCompany(new BeanPropertyRowMapper<OperationUnit>(OperationUnit.class));
            for (int i = 0; i < comList.size(); i++) {
                OperationUnit ou = comList.get(i);
                JSONObject jo = new JSONObject();
                jo.put("name", ou.getName());
                jo.put("id", ou.getId());
                ja.put(jo);
            }
        } catch (JSONException e) {
            log.error("", e);
        }
        return ja;
    }

    @Override
    public JSONArray selectCenterByCompanyId(Long comid) {
        JSONArray ja = new JSONArray();
        try {
            List<OperationUnit> cenList = operationUnitDao.selectCenterByCompanyId(comid, new BeanPropertyRowMapper<OperationUnit>(OperationUnit.class));
            for (int i = 0; i < cenList.size(); i++) {
                OperationUnit ou = cenList.get(i);
                JSONObject jo = new JSONObject();
                jo.put("name", ou.getName());
                jo.put("id", ou.getId());
                ja.put(jo);
            }
        } catch (Exception e) {
            log.error("", e);
        }
        return ja;
    }

    @Override
    @Transactional(readOnly = true)
    public List<OperationUnit> selectWarehouseByCenId(Long cenid) {
        List<OperationUnit> scList = new ArrayList<OperationUnit>();
        List<OperationUnit> wareList = operationUnitDao.selectWarehouseByCenid(cenid, new BeanPropertyRowMapper<OperationUnit>(OperationUnit.class));

        for (OperationUnit ou : wareList) {
            OperationUnit ouc = new OperationUnit();
            try {
                PropertyUtil.copyProperties(ou, ouc);
                ouc.setChildrenUnits(null);
                ouc.setParentUnit(null);
                ouc.setOuType(null);
                scList.add(ouc);
            } catch (Exception e) {
                log.error("Copy Bean properties error for OperationUnit");
                log.error("", e);
                throw new RuntimeException("Copy Bean properties error for OperationUnit");
            }
        }

        return scList;
    }

    @Override
    public void savePhysicalWareHouse(PhysicalWarehouse phWarehouse) {
        try {
            physicalWarehouseDao.save(phWarehouse);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public JSONArray selectAllPhyWarehouse() {
        JSONArray ja = new JSONArray();
        try {
            List<PhysicalWarehouse> pwlist = physicalWarehouseDao.selectAllPhyWarehouse(new BeanPropertyRowMapper<PhysicalWarehouse>(PhysicalWarehouse.class));
            for (int i = 0; i < pwlist.size(); i++) {
                PhysicalWarehouse pw = pwlist.get(i);
                JSONObject jo = new JSONObject();
                jo.put("name", pw.getName());
                jo.put("id", pw.getId());
                ja.put(jo);
            }
        } catch (Exception e) {
            log.error("", e);
        }
        return ja;
    }

    public List<PhysicalWarehouse> findAllPhyWarehouse() {
        return physicalWarehouseDao.selectAllPhyWarehouse(new BeanPropertyRowMapper<PhysicalWarehouse>(PhysicalWarehouse.class));
    }


    @Override
    public boolean ifExistVirtual(List<Long> list) {
        Boolean b = false;
        List<OperationUnit> ou = operationUnitDao.selectOperationUnitByPhyId(new BeanPropertyRowMapper<OperationUnit>(OperationUnit.class));
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < ou.size(); j++) {
                if (list.get(i).equals(ou.get(j).getId())) {
                    b = true;
                    return b;
                }
            }
        }
        return b;
    }

    @Override
    public JSONArray selectAllWarehouseByPhyId(Long phyid) {
        JSONArray ja = new JSONArray();
        List<OperationUnit> ou = operationUnitDao.selectAllWarehouseByPhyId(phyid, new BeanPropertyRowMapper<OperationUnit>(OperationUnit.class));
        for (int i = 0; i < ou.size(); i++) {
            JSONObject jo = new JSONObject();
            OperationUnit o = ou.get(i);
            try {
                jo.put("id", o.getId());
                jo.put("name", o.getName());
            } catch (JSONException e) {
                log.error("", e);
            }
            ja.put(jo);
        }
        return ja;
    }

    @Override
    public boolean saveRelationShip(Long phid, List<Long> list) {
        boolean b = false;
        try {
            operationUnitDao.deleteRelationShip(phid);
            operationUnitDao.saveRelationShip(phid, list);
            b = true;
        } catch (Exception e) {
            log.error("", e);
        }
        return b;
    }

    @Override
    public String toCasRegister(User user) throws Exception {
        String k = "g=" + user.getJobNumber() + "u=" + user.getLoginName() + "p=" + user.getPassword() + "f=wms" + USER_CLIENT_KEY;
        String params = "g=" + user.getJobNumber() + "&u=" + user.getLoginName() + "&p=" + user.getPassword() + "&f=wms" + "&k=" + AppSecretUtil.getMD5Str(k);
        return this.post(CAS_USER_REGISTER, params);
    }

    @Override
    public String toCasPwdModify(String loginName, UserCommand usercmd) throws Exception {
        String k = "u=" + loginName + "p=" + usercmd.getNewPassword() + "op=" + usercmd.getPassword() + "f=wms" + USER_CLIENT_KEY;
        String params = "u=" + loginName + "&p=" + usercmd.getNewPassword() + "&op=" + usercmd.getPassword() + "&f=wms" + "&k=" + AppSecretUtil.getMD5Str(k);
        return this.post(CAS_USER_PWD_UPDATE, params);
    }

    @Override
    public String toCasPwdLogin(String userName, String passWord, String appKey) throws Exception {
        // String k = "u=" + userName + "p=" + passWord + "f=wms" + USER_CLIENT_KEY;
        // String params = "u=" + userName + "&p=" + passWord + "&f=wms" + "&a=" + appKey + "&k=" +
        // AppSecretUtil.getMD5Str(k);
        String params = "loginName=" + userName + "&password=" + passWord + "&appkey=" + appKey;
        return this.uacPost(OAUTH_USER_CLIENT_LOGIN_SU, params);
    }

    @Override
    public User passwordModifyIndex(UserCommand usercmd) {
        User user = userDao.findByLoginName(usercmd.getLoginName());
        if (user == null) {
            throw new BusinessException(ErrorCode.USER_LOGIN_NAME_IS_NULL, new Object[] {usercmd.getLoginName()});
        }
        log.info("===========user login name: {} ============", user.getLoginName());
        return passwordModify(usercmd, user.getId());
    }

    public User passwordModify(UserCommand usercmd, Long userid) {
        User user = userDao.getByPrimaryKey(userid);
        String md5Password = AppSecretUtil.getMD5Str(usercmd.getPassword() + "{" + user.getLoginName() + "}");
        if (!StringUtils.hasLength(usercmd.getPassword())) {
            throw new BusinessException(ErrorCode.USER_RAW_PASSWORD_IS_NULL);
        }
        if (!user.getPassword().equals(md5Password)) {
            throw new BusinessException(ErrorCode.USER_RAW_PASSWORD_IS_ERROR);
        }
        if (!StringUtils.hasLength(usercmd.getNewPassword())) {
            throw new BusinessException(ErrorCode.USER_PASSWORD_NEW_IS_NULL);
        }
        if (!StringUtils.hasLength(usercmd.getConfirmPassword())) {
            throw new BusinessException(ErrorCode.USER_CONFIRMPASSWORD_IS_NULL);
        }
        if (!usercmd.getNewPassword().equals(usercmd.getConfirmPassword())) {
            throw new BusinessException(ErrorCode.USER_PASSWORD_AND_CONFIRMPASSWORD_NOT_EQUAL);
        }
        List<SysPwdPolicy> sysPwdPolicys = sysPwdPolicyDao.findSysPwdPolicy(new BeanPropertyRowMapperExt<SysPwdPolicy>(SysPwdPolicy.class));
        if (sysPwdPolicys != null && !sysPwdPolicys.isEmpty()) {
            SysPwdPolicy sysPwdPolicy = sysPwdPolicys.get(0);
            Integer length = sysPwdPolicy.getPwdMinLength() == null ? new Integer(6) : sysPwdPolicy.getPwdMinLength();
            String pwdRegulation = sysPwdPolicy.getPolicy();
            if (!StringUtils.hasLength(pwdRegulation)) {
                throw new BusinessException(ErrorCode.USER_PASSWORD_REGULATION_IS_NULL);
            }
            if (usercmd.getNewPassword().length() < length.intValue() || usercmd.getNewPassword().length() > 20) {
                throw new BusinessException(ErrorCode.USER_PASSWORD_LENGTH_IS_ERROR, new Object[] {length.intValue()});
            }
            Pattern p = Pattern.compile(pwdRegulation);
            Matcher m = p.matcher(usercmd.getNewPassword());
            if (!m.matches()) {
                throw new BusinessException(ErrorCode.USER_PASSWORD_REGULATION_ERROR);
            }
        } else {
            if (usercmd.getNewPassword().length() < 6 || usercmd.getNewPassword().length() > 20) {
                throw new BusinessException(ErrorCode.USER_PASSWORD_LENGTH_IS_ERROR_2);
            }
        }
        String newPwd = AppSecretUtil.getMD5Str(usercmd.getNewPassword() + "{" + user.getLoginName() + "}");
        user.setPassword(newPwd);
        return user;
    }

    @SuppressWarnings("deprecation")
    @Override
    public String post(String url, String params) throws Exception {
        String resCode = null;
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpMethod = new HttpPost(url);

        if (params != null && !"".equals(params)) {
            String[] _params = params.split("&");
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            for (int i = 0; i < _params.length; i++) {
                String _p = _params[i];
                int j = _p.indexOf("=");
                nvps.add(new BasicNameValuePair(_p.substring(0, j), (_p.length() > j + 1) ? _p.substring(j + 1) : ""));
            }
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nvps, HTTP.UTF_8);
            httpMethod.setEntity(entity);
        }

        HttpResponse response = null;
        try {
            response = httpClient.execute(httpMethod);
            HttpEntity entity = response.getEntity();

            Header contentEncoding = entity.getContentEncoding();
            boolean isGzip = (contentEncoding != null && contentEncoding.getValue().toLowerCase().indexOf("gzip") > -1);
            resCode = new String(getResByteBody(entity, isGzip), "UTF-8");

        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("AuthorizationManagerImpl post error" + params, e);
            }
        } finally {
            /*
             * if (response != null) { response.close(); }
             */
            if (httpMethod != null) {
                httpMethod.releaseConnection();
            }
        }
        return resCode;
    }

    private static byte[] getResByteBody(HttpEntity entity, boolean isGzip) {
        InputStream is = null;

        List<Byte> resList = null;
        try {
            resList = new ArrayList<Byte>();
            if (isGzip) {
                is = new GZIPInputStream(entity.getContent());
            } else {
                is = entity.getContent();
            }
            byte[] b = new byte[1];
            while (is.read(b) != -1) {
                resList.add(new Byte(b[0]));
                b = new byte[1];
            }
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("getResByteBody", e);
            }
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    if (log.isErrorEnabled()) {
                        log.error("getResByteBody", e);
                    }
                }
            }
        }

        byte[] res = new byte[resList.size()];
        for (int i = 0; i < resList.size(); i++) {
            res[i] = ((Byte) resList.get(i)).byteValue();
        }

        return res;
    }

    public List<OperationUnit> findDivisionList(Long id) {

        return operationUnitDao.findDivisionList(id);
    }

    /**
     * 根据运营中心查询所有仓库
     * 
     * @param cenid
     * @param beanPropertyRowMapper
     * @return
     */
    public List<OperationUnit> selectWarehouseByCenid(Long cenid) {
        return operationUnitDao.selectWarehouseByCenid(cenid, new BeanPropertyRowMapper<OperationUnit>(OperationUnit.class));
    }

    /**
     * 查找仓库对象
     * 
     * @param id
     * @return
     */
    public OperationUnit findOperationUnitWarehouseopc(Long ouid) {
        return operationUnitDao.findOperationUnitWarehouseopc(ouid);
    }

    public int updateUserAccessTimeSql(Date accessTime, String loginName) {
        return userDao.updateUserAccessTimeSql(accessTime, loginName);
    }

    public void saveLoginLog(SysLoginLog loginLog) {
        sysLoginLogDao.save(loginLog);
    }

    public Pagination<SysLoginLogCommand> findSystemLoginLogPagByCommandSql(int start, int pageSize, Map<String, Object> cmd, Sort[] sorts) {
        return sysLoginLogDao.findSystemLoginLogPagByCommandSql(0, -1, cmd, null, new BeanPropertyRowMapperExt<SysLoginLogCommand>(SysLoginLogCommand.class));
    }

    public void updateUserAccessTime(Date loginTime, String loginName) {
        userDao.updateUserAccessTimeSql(loginTime, loginName);
    }

    public User findUserByUsername(String username) {
        return userDao.findByLoginName(username);
    }

    @Override
    public void createOrUpdateUserForUAC(User user) {
        if (null == user.getId()) {
            user.setCreateTime(new Date());
            OperationUnit oux = operationUnitDao.findOperationUnitListByCode(OperationUnitType.OUTYPE_ROOT).get(0);
            user.setOu(oux);
        } else {
            user.setLatestUpdateTime(new Date());
        }
        try {
            userDao.save(user);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("createOrUpdateUserForUAC exception", e);
            }
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public String uacPost(String url, String params) throws Exception {
        Integer statusCode = -1;
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpMethod = new HttpPost(url);
        HttpResponse response = null;
        String tempLine = null;

        if (params != null && !"".equals(params)) {
            String[] _params = params.split("&");
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            for (int i = 0; i < _params.length; i++) {
                String _p = _params[i];
                int j = _p.indexOf("=");
                nvps.add(new BasicNameValuePair(_p.substring(0, j), (_p.length() > j + 1) ? _p.substring(j + 1) : ""));
            }
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nvps, HTTP.UTF_8);
            httpMethod.setEntity(entity);
        }

        try {
            response = httpClient.execute(httpMethod);
            statusCode = response.getStatusLine().getStatusCode();
            if (HttpStatus.OK.value() != statusCode) {
                throw new HttpException("Http Status is error.");
            }
            HttpEntity entity = response.getEntity();
            BufferedReader rd = new BufferedReader(new InputStreamReader(entity.getContent(), HTTP.UTF_8));
            tempLine = rd.readLine();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("uacPost" + params, e);
            }
        } finally {
            /*
             * if (response != null) { response.close(); }
             */
            if (httpMethod != null) {
                httpMethod.releaseConnection();
            }
        }
        return tempLine;
    }

    @Override
    public User findUserByUsernameForUac(String username) {
        return userDao.findByLoginNameForUac(username);
    }
}
