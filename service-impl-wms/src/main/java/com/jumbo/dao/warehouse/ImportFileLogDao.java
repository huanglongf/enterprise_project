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

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.ImportFileLog;

@Transactional
public interface ImportFileLogDao extends GenericEntityDao<ImportFileLog, Long> {

    @NativeQuery
    List<ImportFileLog> findAllTodoFile(@QueryParam("type") Integer type, @QueryParam("status") Integer status, @QueryParam("whId") Long whId, RowMapper<ImportFileLog> r);

    @NativeUpdate
    int bindFileByStaCode(@QueryParam("id") Long id, @QueryParam("memo") String memo, @QueryParam("code") String code);

    @NativeQuery
    List<ImportFileLog> findAllToDeleteFile(RowMapper<ImportFileLog> r);

    @NativeQuery
    List<Long> findAllTodoWhId(@QueryParam("type") Integer type, @QueryParam("status") Integer status, SingleColumnRowMapper<Long> r);
}
