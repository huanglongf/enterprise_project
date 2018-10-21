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
package com.jumbo.web.action.system;

import java.util.List;

import loxia.dao.Pagination;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.wms.manager.DropDownListManager;
import com.jumbo.wms.manager.system.SystemManager;
import com.jumbo.wms.model.authorization.UserGroup;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.system.SysLoginLogCommand;
import com.jumbo.util.FormatUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;

/**
 * 
 * @author wanghua
 * 
 */
public class SysLoginLogAction extends BaseJQGridProfileAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = -4869126232913602181L;
    @Autowired
    private DropDownListManager dropDownListManager;
    @Autowired
    private SystemManager systemManager;
    /**
     * 前台查询参数
     */
    private SysLoginLogCommand sysLoginLogCommand;
    private List<UserGroup> userGroup;
    /**
     * 状态下拉列表
     */
    private List<ChooseOption> statusOptionList;

    public String execute() {
        userGroup = dropDownListManager.findUserGroupList();
        statusOptionList = dropDownListManager.findStatusChooseOptionList();
        return SUCCESS;
    }

    public String sysLoginLog() {
        try {
            // 1)根据参数构造TableConfig,显示调用
            setTableConfig();

            // 2)根据TableConfig获取查询参数
            // 返回结果为List的默认是不分页,返回结果为Pagination为分页
            int start = (tableConfig.getCurrentPage() - 1) * tableConfig.getPageSize();
            if (sysLoginLogCommand != null) {
                sysLoginLogCommand.setLoginTimeFrom(FormatUtil.getDate(sysLoginLogCommand.getLoginTimeFrom1()));
                sysLoginLogCommand.setLoginTimeTo(FormatUtil.getDate(sysLoginLogCommand.getLoginTimeTo1()));
            }
            Pagination<SysLoginLogCommand> sysLoginLogList = systemManager.findSystemLoginLogPagByCommandSql(start, tableConfig.getPageSize(), sysLoginLogCommand, tableConfig.getSorts());
            // 3)把数据转为json格式并放入request
            request.put(JSON, toJson(sysLoginLogList));
        } catch (Exception e) {
            log.error("",e);
            log.error("----------SysLoginLogAction.sysLoginLog({})", e.getMessage());
        }

        return JSON;
    }

    public SysLoginLogCommand getSysLoginLogCommand() {
        return sysLoginLogCommand;
    }

    public void setSysLoginLogCommand(SysLoginLogCommand sysLoginLogCommand) {
        this.sysLoginLogCommand = sysLoginLogCommand;
    }

    public List<UserGroup> getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(List<UserGroup> userGroup) {
        this.userGroup = userGroup;
    }

    public List<ChooseOption> getStatusOptionList() {
        return statusOptionList;
    }

    public void setStatusOptionList(List<ChooseOption> statusOptionList) {
        this.statusOptionList = statusOptionList;
    }


}
