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

import loxia.annotation.DynamicQuery;
import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.authorization.UserGroup;

@Transactional
public interface UserGroupDao extends GenericEntityDao<UserGroup, Long> {
    /**
     * 查询用户分组列表
     * 
     * @return
     */
    @NamedQuery
    List<UserGroup> findUserGroupList();

    /**
     * 根据name查询是否有同名的UserGroup
     * 
     * @param name
     * @return
     */
    @DynamicQuery
    UserGroup findUserGroupByName(@QueryParam("name") String name, @QueryParam("id") Long id);

    /**
     * 修改userIds列表中的用户组为userGroupId
     * 
     * @param userGroupId
     * @param userIds
     */
    @NativeUpdate
    void addUsers(@QueryParam("userGroupId") Long userGroupId, @QueryParam("userIds") List<Long> userIds);

    /**
     * 修改userIds列表中的用户组为null
     * 
     * @param userGroupId
     * @param userIds
     */
    @NativeUpdate
    void removeUsers(@QueryParam("userGroupId") Long userGroupId, @QueryParam("userIds") List<Long> userIds);

    /**
     * 返回指定用户分组中依然有用户的用户分组
     * 
     * @param ids
     * @return
     */
    @NativeQuery
    List<Long> findUserGroupIdsWithUsers(@QueryParam("ids") List<Long> ids, RowMapper<Long> rowMapper);
}
