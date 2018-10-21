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

package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.WhInfoTimeRef;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefCommand;

@Transactional
public interface WhInfoTimeRefDao extends GenericEntityDao<WhInfoTimeRef, Long> {

    @NativeUpdate
    void insertWhInfoTime(@QueryParam(value = "slipCode") String slipCode, @QueryParam(value = "billType") int billType, @QueryParam(value = "nodeType") int nodeType, @QueryParam(value = "createId") Long createId);


    @NativeUpdate
    void insertWhInfoTime2(@QueryParam(value = "slipCode") String slipCode, @QueryParam(value = "billType") int billType, @QueryParam(value = "nodeType") int nodeType, @QueryParam(value = "createId") Long createId, @QueryParam(value = "ouId") Long ouId);

    @NativeQuery
    WhInfoTimeRef getFirstPrintDate(@QueryParam("slipCode") String slipCode, @QueryParam(value = "billType") Integer billType, @QueryParam(value = "nodeType") Integer nodeType, BeanPropertyRowMapper<WhInfoTimeRef> beanPropertyRowMapper);

    @NativeQuery
    List<WhInfoTimeRef> getInfoByUserId(@QueryParam(value = "userId") Long userId, RowMapper<WhInfoTimeRef> rowMapper);

    @NativeQuery
    WhInfoTimeRef getInfoBySlipCode(@QueryParam("slipCode") String slipCode, @QueryParam(value = "nodeType") int nodeType, BeanPropertyRowMapper<WhInfoTimeRef> beanPropertyRowMapper);

    @NativeQuery
    List<WhInfoTimeRef> findEdwTwhInfoTimeRef(RowMapper<WhInfoTimeRef> rowMapper);

    @NativeQuery(pagable = true)
    Pagination<WhInfoTimeRefCommand> findWhInfoTimeListBySlipCode(int start, int pageSize, @QueryParam(value = "slipCode") String slipCode, @QueryParam(value = "code") String code, @QueryParam(value = "ouId") Long ouId, Sort[] sorts,
            BeanPropertyRowMapper<WhInfoTimeRefCommand> beanPropertyRowMapper);

}
