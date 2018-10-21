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

package com.jumbo.dao.authorization;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Sort;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.authorization.UserRole;

@Transactional
public interface UserRoleDao extends GenericEntityDao<UserRole, Long> {

    @NamedQuery
    UserRole findDefaultUserRole(@QueryParam("userId") Long userId);

    /**
     * 根据角色,组织查询UserRole
     * 
     * @param userId
     * @param roleId
     * @param ouId
     * @return
     */
    @NamedQuery
    UserRole findUserRoleByUserIdRoleIdAndOuId(@QueryParam("userId") Long userId, @QueryParam("roleId") Long roleId, @QueryParam("ouId") Long ouId);

    @NamedQuery
    List<UserRole> findUserRoleListByUserId(@QueryParam("userId") Long userId, Sort[] sorts);

    @NativeUpdate
    void delUserRoleByUserIdSql(@QueryParam("userId") Long userId);

    /**
     * 当组织节点被禁用时，删除所有权限
     * 
     * @param ouId
     */
    @NativeUpdate
    void deleteUserRolesByOu(@QueryParam("ouId") Long ouId);

    /**
     * 根据id,把指定UserRole设为default
     * 
     * @param id
     * @return
     */
    @NativeUpdate
    int updateUserRoleToDefault(@QueryParam("roleId") Long roleId, @QueryParam("ouId") Long ouId, @QueryParam("userId") Long userId);

    /**
     * 根据user_id和role_id
     * 
     * @param userGroupid
     * @param roleid
     * @return
     */
    @NativeUpdate
    int deleteUserRoles(@QueryParam("userGroupid") Long userGroupid, @QueryParam("operationUnitid") Long operationUnitid, @QueryParam("roleid") Long roleid);

    // 添加用户分组
    @NativeUpdate
    int addUserRoles(@QueryParam("userGroupid") Long userGroupid, @QueryParam("operationUnitid") Long operationUnitid, @QueryParam("roleid") Long roleid);

    /**
     * 根据userid和roleid查询UserRole对象
     * 
     * @param userid
     * @param roleid
     * @return
     */
    @NamedQuery
    UserRole findUserRole(@QueryParam("userid") Long userid, @QueryParam("roleid") Long roleid);

    /**
     * 根据用户组id查找UserRole列表
     * 
     * @param userGroupid
     * @return
     */
    @NamedQuery
    List<UserRole> findUserRoleByuserGroupId(@QueryParam("userGroupid") Long userGroupid);
    
   /**
    * 如果默认权限为null,则从角色已有权限中随机取出一条
    * @param userId
    * @return
    */
    @NamedQuery
    UserRole findUserRoleByStochastic(@QueryParam("userId") Long userId);
}
