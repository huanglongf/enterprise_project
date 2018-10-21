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

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.wms.manager.warehouse.WareHouseManagerQuery;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.web.action.BaseJQGridProfileAction;

/**
 * 
 * @author jinlong.ke
 * 
 */
public class CacheTicketPrintAction extends BaseJQGridProfileAction {
    /**
	 * 
	 */
    private static final long serialVersionUID = 2280702260698706655L;
    @Autowired
    private WareHouseManagerQuery whManagerQuery;

    private StockTransApplicationCommand sta;

    public StockTransApplicationCommand getSta() {
        return sta;
    }

    public void setSta(StockTransApplicationCommand sta) {
        this.sta = sta;
    }

    /**
     * 初始跳转页面，首次进入，不做任何操作，只负责页面跳转
     * 
     * @return
     */
    public String cacheTicketPrint() {
        return SUCCESS;
    }

    /**
     * 执行查询，查询配货清单列表
     * 
     * @return
     */
    public String queryCoachSta() {
        setTableConfig();
        request.put(JSON, toJson(whManagerQuery.queryCoachSta(tableConfig.getStart(), tableConfig.getPageSize(), userDetails.getCurrentOu().getId(), sta)));
        return JSON;
    }
}
