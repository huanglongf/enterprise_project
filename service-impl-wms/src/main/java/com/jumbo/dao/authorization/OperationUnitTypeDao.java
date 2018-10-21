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

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.authorization.OperationUnitType;

@Transactional
public interface OperationUnitTypeDao extends GenericEntityDao<OperationUnitType, Long> {
    @NativeQuery
    List<OperationUnitType> findOperationUnitTypeList(@QueryParam("containsRoot") Boolean containsRoot, RowMapper<OperationUnitType> rowMapper);


    @NativeQuery
    List<OperationUnitType> findChildOUPList(@QueryParam("parentOUTId") Long parentId, RowMapper<OperationUnitType> rowMapper);
    
    /**
     * 弹出框-查询
     * @param start
     * @param pageSize
     * @param ouid
     * @param rowMapper
     * @param sorts
     * @return
     */
    @NativeQuery(pagable = true, withGroupby = true)
    Pagination<OperationUnitType> queryOperationUnitlist(int start, int pageSize, @QueryParam("ouName") String ouName, Sort[] sorts);
}
