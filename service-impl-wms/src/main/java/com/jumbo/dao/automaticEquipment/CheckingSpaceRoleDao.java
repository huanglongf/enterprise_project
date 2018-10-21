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
 */
package com.jumbo.dao.automaticEquipment;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.automaticEquipment.CheckingSpaceRole;
import com.jumbo.wms.model.command.automaticEquipment.CheckingSpaceRoleCommand;


/**
 * 
 * @author xiaolong.fei
 * 
 */
@Transactional
public interface CheckingSpaceRoleDao extends GenericEntityDao<CheckingSpaceRole, Long> {

    @NativeQuery(pagable = true)
    Pagination<CheckingSpaceRoleCommand> findCheckingSpaceRole(int start, int pageSize, @QueryParam(value = "ouId") Long ouId, @QueryParam(value = "type") Integer type, @QueryParam(value = "owner") String owner,
            BeanPropertyRowMapper<CheckingSpaceRoleCommand> r, Sort[] sorts);

    @NamedQuery
    CheckingSpaceRole getCheckRoleLineBySort(@QueryParam(value = "sort") Integer sort, @QueryParam(value = "type") Integer type, @QueryParam(value = "ouId") Long ouId);

    /**
     * @param pbId
     * @param staId
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    CheckingSpaceRole findCheckSpaceByCondition(@QueryParam("pbId") Long pbId, @QueryParam(value = "type") Integer type, @QueryParam("staId") Long staId, BeanPropertyRowMapper<CheckingSpaceRole> beanPropertyRowMapper);

    @NativeQuery
    List<CheckingSpaceRoleCommand> findCheckingSpaceList(@QueryParam(value = "ouId") Long ouId, @QueryParam(value = "type") Integer type, RowMapper<CheckingSpaceRoleCommand> row);
}
