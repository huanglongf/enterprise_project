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
package com.jumbo.wms.manager.system;

import loxia.dao.Pagination;
import loxia.dao.Sort;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.system.SysLoginLog;
import com.jumbo.wms.model.system.SysLoginLogCommand;

public interface SystemManager extends BaseManager {
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
    Pagination<SysLoginLogCommand> findSystemLoginLogPagByCommandSql(int start, int pageSize, SysLoginLogCommand command, Sort[] srots);
    
    public void saveSysLoginLog(SysLoginLog sysLoginLog);
}
