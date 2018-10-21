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

import java.util.Date;
import java.util.List;
import java.util.Map;

import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.support.json.JSONArray;
import loxia.support.json.JSONException;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.authorization.Privilege;
import com.jumbo.wms.model.authorization.Role;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.authorization.UserGroup;
import com.jumbo.wms.model.authorization.UserInfo;
import com.jumbo.wms.model.authorization.UserRole;
import com.jumbo.wms.model.command.UserCommand;
import com.jumbo.wms.model.command.UserGroupCommand;
import com.jumbo.wms.model.command.authorization.RoleCmd;
import com.jumbo.wms.model.command.authorization.UserDetailsCmd;
import com.jumbo.wms.model.command.authorization.UserRoleCmd;
import com.jumbo.wms.model.system.SysLoginLog;
import com.jumbo.wms.model.system.SysLoginLogCommand;
import com.jumbo.wms.model.warehouse.PhysicalWarehouse;

public interface AuthorizationManager extends BaseManager {
    /**
     * 查询登陆名称是否存在
     * 
     * @param loginName
     * @return
     */
    boolean existsUser(String loginName);

    UserDetailsCmd loadUserByUsername(String username);

    void saveLoginLog(SysLoginLog loginLog);

    Pagination<SysLoginLogCommand> findSystemLoginLogPagByCommandSql(int start, int pageSize, Map<String, Object> cmd, Sort[] sorts);

    void updateUserAccessTime(Date loginTime, String loginName);

    // OperationUnit
    /**
     * 新增组织
     * 
     * @param operationUnit
     */
    OperationUnit createOperationUnit(OperationUnit operationUnit);

    /**
     * 查找组织列表（含不可用）[JSON格式]
     * 
     * @return
     */
    List<OperationUnit> findOperationUnitListSql();

    /**
     * 
     * @param ouTypeId
     * @return
     */
    JSONArray findOperationUnitByOuTypeID(Long ouTypeId, Boolean isAvailable);

    /**
     * 
     * @param id
     * @return
     */
    OperationUnit getOUByPrimaryKey(Long id);

    /**
     * 修改组织
     * 
     * @param operationUnit
     * @return
     */
    OperationUnit updateOperationUnit(OperationUnit operationUnit);

    // OperationUnitType
    // Privilege
    // Role
    Role createRole(Role role);

    /**
     * 
     * @param id
     * @return
     */
    JSONArray findRoleById(Long id);

    /**
     * 
     * @param id
     * @return
     */
    Role findRoleInfoById(Long id);

    /**
     * 
     * @param ouTypeId
     * @return
     */
    JSONArray findRoleByOuTypeID(Long ouTypeId, Boolean isAvailable);

    /**
     * 根据角色名和组织类型Id查找角色列表
     * 
     * @param currentPage 当前页码
     * @param role
     * @param sorts
     * @return
     */
    Pagination<Role> findRoleList(int start, int currentPage, Role role, Sort[] sorts);

    /**
     * 根据角色名和组织类型Id查找角色列表
     * 
     * @param currentPage 当前页码
     * @param role
     * @param sorts
     * @return
     */
    Pagination<RoleCmd> findRoleCmdList(int start, int currentPage, Role role, Sort[] sorts);

    /**
     * 保存或者更新Role和role对应的角色
     * 
     * @param role
     * @param privlist
     * @return
     */
    void updateOrSaveRoleAndPriv(Role role, List<Privilege> privlist);

    /**
     * 批量设定角色无效
     * 
     * @param roleList
     */
    void deleteRoleList(List<Role> roleList);

    // User
    // -开始操作User;
    /**
     * 创建角色
     */
    public void createUser(User user);

    /**
     * 根据用户的Id查用户
     * 
     * @param id 用户Id
     * @return
     */
    public User findUserByID(Long id);

    /**
     * 根据用户的loginName查用户
     * 
     * @param loginName
     * @return
     */
    public List<User> findUserListByLoginName(String loginName);

    public UserDetailsCmd findUserDetails(Long userId, Long roleId, Long ouId);

    /**
     * 根据用户的基本信息和用户分组信息查询用户列表
     * 
     * @param user
     * @param userGroup
     * @param start
     * @param pageSize
     * @param srots
     * @return
     */
    public Pagination<UserInfo> findUserList(Long whOuId, User user, UserGroup userGroup, int start, int pageSize, Sort[] srots);

    /**
     * 创建用户和起对应的角色
     * 
     * @param list 用户角色列表
     * @param user
     */
    public void createUserAndUserRoleList(List<UserRole> list, User user);

    /**
     * 修改用户和起对应的角色
     * 
     * @param list用户角色列表 ，如果希望清空角色列表则传递空或一个空列表，否则传递对应的角色列表
     * @param user
     */
    public String updateUserAndUserRoleList(List<UserRole> list, User user, String adminPwd) throws JSONException;

    /**
     * 批量设定用户为无效
     * 
     * @param userList
     */
    public void deleleUserList(List<User> userList);

    public List<UserGroup> finduserGroupList();

    /**
     * 批量更改用户有效性
     * 
     * @param userGroupId
     * @param isEnabled
     * @return
     */
    public int updateUserisEnabled(Long userGroupId, int isEnabled);

    public List<User> findUserListByUserGroupId(Long userGroupid);

    /**
     * 根据UserGroupId分页查询User
     * 
     * @param start
     * @param pageSize
     * @param userGroupId
     * @param sorts
     * @return
     */
    Pagination<User> findUserPByUserGroupCommand(int start, int pageSize, UserGroupCommand command, Sort[] sorts);

    // 结束User的操作
    // UserGroup
    /**
     * 保存用户列表时的操作
     */
    Map<String, List<String>> updateUserGroupList(List<UserGroup> addList, List<UserGroup> updateList, List<UserGroup> deleteList);

    /**
     * 关联用户到用户分组
     */
    void addUsers(Long userGroupId, List<Long> keyList);

    /**
     * 删除用户分组中的用户
     */
    void removeUsers(Long userGroupId, List<Long> keyList);

    /**
     * 
     * @param ids
     * @return
     */
    List<Long> checkUserGroupDelable(List<Long> keyList);

    // UserRole
    /**
     * 根据userId查询UserRole
     * 
     * @param userId
     * @param sorts
     * @return
     */
    List<UserRole> findUserRoleListByUserId(Long userId, Sort[] sorts);

    /**
     * set UserRole to Default
     * 
     * @param roleId
     * @param ouId
     * @parem userId
     * @return
     */
    int updateUserRoleToDefault(Long roleId, Long ouId, Long userId);

    /**
     * 批量脱离用户角色
     * 
     * @param userGroupid
     * @param roleid
     * @param operationUnitid
     * @return
     */
    int deleteUserRoles(Long userGroupid, Long operationUnitid, Long roleid);

    /**
     * 根据用户id和角色id查找用户角色信息
     * 
     * @param userid
     * @param roleid
     * @return
     */
    UserRole findUserRole(Long userid, Long roleid);

    /**
     * 根据用户组id查找UserRole列表
     * 
     * @return list
     * @param userGroupid
     */
    List<UserRole> findUserRoleByuserGroupId(Long userGroupid);

    /**
     * @param userGroupid
     * @param operationUnitid
     * @param roleid
     */
    int grantUserRoles(Long userGroupid, Long operationUnitid, Long roleid);

    /**
     * 当前组织（运营中心）下的仓库列表
     * 
     * @param id
     * @return
     */
    List<OperationUnit> findOperationUnitList(Long id);

    User passwordModifyIndex(UserCommand usercmd);

    /**
     * 检查roleName是和数据库的name重复
     * 
     * @param role
     * @return
     */
    Boolean checkRoleName(Role role);

    /**
     * 仓库附加信息未填写的组织列表
     * 
     * @param start
     * @param pageSize
     * @param list
     * @param sorts
     * @return
     */
    Pagination<OperationUnit> findOuWarehouseList(int start, int pageSize, List<Object> list, Sort[] sorts);

    /**
     * 查询组织是否存在
     * 
     * @param operationUnit
     * @return
     */
    Long findOperationUnit(OperationUnit operationUnit);

    /**
     * 得到所有公司基本信息
     * 
     * @return
     */
    JSONArray selectAllCompany();

    /**
     * 根据公司查询仓库
     * 
     * @param comid
     * @return
     */
    JSONArray selectCenterByCompanyId(Long comid);

    /**
     * 根据运营中心查询仓库
     * 
     * @param cenid
     * @return
     */
    List<OperationUnit> selectWarehouseByCenId(Long cenid);

    /**
     * 添加新的物理仓
     * 
     * @param phWarehouse
     */
    public void savePhysicalWareHouse(PhysicalWarehouse phWarehouse);

    /**
     * 
     * @return
     */
    JSONArray selectAllPhyWarehouse();

    /**
     * 查找所有物理仓
     * 
     * @return
     */
    List<PhysicalWarehouse> findAllPhyWarehouse();

    /**
     * 判断虚拟仓是否已经关联给定的物理仓
     * 
     * @param phid
     * @param list
     * @return
     */
    boolean ifExistVirtual(List<Long> list);

    /**
     * 根据物理仓查找所有虚拟仓
     * 
     * @param phyid
     * @return
     */
    JSONArray selectAllWarehouseByPhyId(Long phyid);

    /**
     * 保存对应关系,返回结果
     * 
     * @param phid
     * @param list
     */
    boolean saveRelationShip(Long phid, List<Long> list);

    String post(String casUserRegister, String string) throws Exception;

    String toCasRegister(User user) throws Exception;

    public String toCasPwdModify(String loginName, UserCommand usercmd) throws Exception;

    public String toCasPwdLogin(String userName, String passWord, String appKey) throws Exception;

    List<OperationUnit> findDivisionList(Long id);

    /**
     * 根据运营中心查询所有仓库
     * 
     * @param cenid
     * @return
     */
    public List<OperationUnit> selectWarehouseByCenid(Long cenid);

    /**
     * 查找仓库对象
     * 
     * @param id
     * @return
     */
    public OperationUnit findOperationUnitWarehouseopc(Long ouid);

    /**
     * 查询用户所有组织角色列表
     * 
     * @param userId
     * @param sorts
     * @return
     */
    List<UserRoleCmd> findUserRoleCmdListByUserId(Long userId, Sort[] sorts);

    public int updateUserAccessTimeSql(Date accessTime, String loginName);

    public User findUserByUsername(String username);

    public void createOrUpdateUserForUAC(User user);

    public String uacPost(String casUserRegister, String string) throws Exception;

    public User findUserByUsernameForUac(String username);
}
