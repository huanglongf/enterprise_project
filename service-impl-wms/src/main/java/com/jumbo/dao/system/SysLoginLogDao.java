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

package com.jumbo.dao.system;

import java.util.Map;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.system.SysLoginLog;
import com.jumbo.wms.model.system.SysLoginLogCommand;

@Transactional
public interface SysLoginLogDao extends GenericEntityDao<SysLoginLog, Long> {
    /**
     * 根据参数返回分页对象
     * 
     * @param start 起始
     * @param pageSize 每页条数
     * @param sysLoginLogCommand 参数实体
     * @param sorts 排序
     * @param rowMapper ResultSet-->SysLoginLogCommand
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<SysLoginLogCommand> findSystemLoginLogPagByCommandSql(int start, int pageSize, @QueryParam Map<String, Object> sysLoginLogCommand, Sort[] sorts, RowMapper<SysLoginLogCommand> rowMapper);
}
