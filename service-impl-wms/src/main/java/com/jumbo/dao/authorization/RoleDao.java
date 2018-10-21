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
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.authorization.Role;


@Transactional
public interface RoleDao extends GenericEntityDao<Role, Long> {
    @NativeQuery
    List<Role> findRoleByOuTypeIDSql(@QueryParam("ouTypeId") Long ouTypeId, @QueryParam("isAvailable") Boolean isAvailable, BeanPropertyRowMapper<Role> beanPropertyRowMapper);

    /**
     * 查询角色列表
     * 
     * @param roleName
     * @param ouTypeId
     * @param rowMapper
     * @return
     */
    @DynamicQuery(pagable = true)
    Pagination<Role> findRoleByRoleNameAndOuTypeId(int start, int size, @QueryParam("roleName") String roleName, @QueryParam("ouTypeId") Long ouTypeId, Sort[] sorts);

    @NamedQuery
    Role findRoleByID(@QueryParam("id") Long id);

    @NamedQuery
    List<Role> findRoleByName(@QueryParam("name") String name);



}
