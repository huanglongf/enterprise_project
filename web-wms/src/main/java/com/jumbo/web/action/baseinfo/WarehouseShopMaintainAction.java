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
package com.jumbo.web.action.baseinfo;

/**
 * 
 */


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.wms.manager.baseinfo.BaseinfoManager;
import com.jumbo.web.action.BaseJQGridProfileAction;

/***
 * 店铺信息维护
 * 
 * @author dailingyun
 * 
 */
public class WarehouseShopMaintainAction extends BaseJQGridProfileAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = -2204986684252296470L;

    @Autowired
    private BaseinfoManager baseinfoManager;

    private Long shopId;
    private List<Long> parametIds;


    @Override
    public String execute() {
        return SUCCESS;
    }

    public String findWarehouse() {
        setTableConfig();
        request.put(JSON, toJson(baseinfoManager.findWarehouseByCompany(this.userDetails.getCurrentOu().getId(), shopId)));
        return JSON;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public List<Long> getParametIds() {
        return parametIds;
    }

    public void setParametIds(List<Long> parametIds) {
        this.parametIds = parametIds;
    }


}
