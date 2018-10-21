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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.web.action.BaseJQGridProfileAction;

public class HandOverListCheckAction extends BaseJQGridProfileAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = 2427543061432972568L;

    @Autowired
    private WareHouseManager manager;

    private Long hoListId;
    private List<Long> holine = null;

    public String handOverListCheck() {
        return SUCCESS;
    }

    /**
     * 查询所有新建状态的交接清单
     * 
     * @return
     */
    public String queryCheckHandOverList() {
        setTableConfig();
        request.put(JSON, toJson(manager.queryCheckHandOverList(tableConfig.getStart(), tableConfig.getPageSize(), userDetails.getCurrentOu().getId(), tableConfig.getSorts())));
        return JSON;
    }


    /**
     * 根据交货订单id查询明细
     * 
     * @return
     */
    public String queryHandOverListLine() {
        setTableConfig();
        request.put(JSON, toJson(manager.findHoLineByHoListId(hoListId, tableConfig.getSorts())));
        return JSON;
    }

    // /**
    // * 交接
    // *
    // * @return
    // * @throws JSONException
    // */
    // public String checkHandOverList() throws JSONException {
    // JSONObject result = new JSONObject();
    // try {
    // manager.checkHandOverList(hoListId, holine, userDetails.getUser().getId());
    // result.put("result", SUCCESS);
    // } catch (BusinessException e) {
    // result.put("result", ERROR);
    // result.put("message", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(),
    // e.getArgs()));
    // }
    // request.put(JSON, result);
    // return JSON;
    // }

    public Long getHoListId() {
        return hoListId;
    }

    public void setHoListId(Long hoListId) {
        this.hoListId = hoListId;
    }

    public List<Long> getHoline() {
        return holine;
    }

    public void setHoline(List<Long> holine) {
        this.holine = holine;
    }
}
