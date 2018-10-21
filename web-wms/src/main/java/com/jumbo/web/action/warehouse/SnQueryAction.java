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
package com.jumbo.web.action.warehouse;

import loxia.dao.Pagination;

import org.springframework.beans.factory.annotation.Autowired;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.model.warehouse.SkuSnLogCommand;
import com.jumbo.web.action.BaseJQGridProfileAction;

public class SnQueryAction extends BaseJQGridProfileAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = -3174070359613322211L;
    @Autowired
    private WareHouseManager wareHouseManager;

    private SkuSnLogCommand cmd;

    public String snQueryEntry() {
        return SUCCESS;
    }

    public String snLogQuery() {
        return SUCCESS;
    }

    public String findPoSoLog() {
        setTableConfig();
        Pagination<SkuSnLogCommand> list = wareHouseManager.findSnPoSoLog(tableConfig.getStart(), tableConfig.getPageSize(), cmd, userDetails.getCurrentOu().getId());
        request.put(JSON, toJson(list));
        return JSON;
    }

    public String querySnLog() {
        setTableConfig();
        Pagination<SkuSnLogCommand> list = wareHouseManager.findSnLog(tableConfig.getStart(), tableConfig.getPageSize(), cmd, userDetails.getCurrentOu().getId());
        request.put(JSON, toJson(list));
        return JSON;
    }

    public SkuSnLogCommand getCmd() {
        return cmd;
    }

    public void setCmd(SkuSnLogCommand cmd) {
        this.cmd = cmd;
    }

}
