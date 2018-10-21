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

import java.util.Date;
import java.util.List;
import java.util.Map;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.authorization.UserInfo;
import com.jumbo.wms.model.command.UserCommand;

@Transactional
public interface UserDao extends GenericEntityDao<User, Long> {

    @NamedQuery
    User findByLoginName(@QueryParam("loginName") String loginName);

    @NamedQuery
    User findByjobNumber(@QueryParam("jobNumber") String jobNumber);

    /**
     * 查有效和无效的数据
     * 
     * @param loginName
     * @return
     */
    @NamedQuery
    List<User> findUserListByLoginName(@QueryParam("loginName") String loginName);

    @NativeUpdate
    int updateUserAccessTimeSql(@QueryParam("accessTime") Date accessTime, @QueryParam("loginName") String loginName);

    @NativeQuery(pagable = true)
    Pagination<UserInfo> findUserListSql(int start, int pageSize, @QueryParam Map<String, Object> m, RowMapper<UserInfo> rowMapper, Sort[] srots);

    @NativeQuery(pagable = true)
    Pagination<UserInfo> findUserListSqlNoGroup(int start, int pageSize, @QueryParam Map<String, Object> m, RowMapper<UserInfo> rowMapper, Sort[] srots);

    @NativeUpdate
    int updateUserisEnabled(@QueryParam("userGroupId") Long userGroupId, @QueryParam("isEnabled") int isEnabled);

    /**
     * 根据userGroupid查找用户列表
     * 
     * @param userGroupid
     * @return
     */
    @NativeQuery
    List<User> findUserListByUserGroupCommand(@QueryParam("groupId") Long groupId, RowMapper<User> rowMapper);

    /**
     * 根据查询参数做分页查询
     * 
     * @param start
     * @param pageSize
     * @param userGroupCommand
     * @param sorts
     * @return
     */
    @NativeQuery(value = "User.findUserListByUserGroupCommand", pagable = true)
    Pagination<User> findUserPByUserGroupCommand(int start, int pageSize, @QueryParam Map<String, Object> userGroupCommand, Sort[] sorts, RowMapper<User> rowMapper);

    @NamedQuery
    List<User> findAllUserList();

    @NativeQuery
    List<UserCommand> findEdwTauUser(RowMapper<UserCommand> r);

    @NamedQuery
    User findByLoginNameForUac(@QueryParam("loginName") String loginName);

}
