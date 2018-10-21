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

import java.util.HashMap;
import java.util.Map;

import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jumbo.dao.system.SysLoginLogDao;
import com.jumbo.wms.model.system.SysLoginLog;
import com.jumbo.wms.model.system.SysLoginLogCommand;

@Transactional
@Service("systemManager")
public class SystemManagerImpl implements SystemManager {
    /**
	 * 
	 */
    private static final long serialVersionUID = -6273075179007201859L;
    @Autowired
    private SysLoginLogDao sysLogindao;

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
    @Transactional(readOnly = true)
    public Pagination<SysLoginLogCommand> findSystemLoginLogPagByCommandSql(int start, int pageSize, SysLoginLogCommand command, Sort[] srots) {
        return sysLogindao.findSystemLoginLogPagByCommandSql(start, pageSize, buildCommandMap(command), srots, new BeanPropertyRowMapper<SysLoginLogCommand>(SysLoginLogCommand.class));
    }

    /**
     * 过滤参数
     * 
     * @param command
     * @return
     */
    private Map<String, Object> buildCommandMap(SysLoginLogCommand command) {
        Map<String, Object> commandMap = new HashMap<String, Object>();
        if (command == null) return commandMap;
        if (StringUtils.hasLength(command.getLoginName())) {
            commandMap.put("loginName", "%" + command.getLoginName() + "%");
        }
        if (StringUtils.hasLength(command.getUserName())) {
            commandMap.put("userName", "%" + command.getUserName() + "%");
        }
        if (StringUtils.hasLength(command.getJobNumber())) {
            commandMap.put("jobNumber", "%" + command.getJobNumber());
        }
        if (StringUtils.hasLength(command.getIsEnabled())) {
            commandMap.put("isEnabled", Boolean.valueOf(command.getIsEnabled()));
        }
        if (command.getGroupId() != null) {
            commandMap.put("groupId", command.getGroupId());
        }
        if (command.getLoginTimeFrom() != null) {
            commandMap.put("loginTimeFrom", command.getLoginTimeFrom());
        }
        if (command.getLoginTimeTo() != null) {
            commandMap.put("loginTimeTo", command.getLoginTimeTo());
        }
        return commandMap;
    }

    public void saveSysLoginLog(SysLoginLog sysLoginLog) {
        sysLogindao.save(sysLoginLog);
    }
}
