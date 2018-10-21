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

import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.model.warehouse.PickingListCommand;
import com.jumbo.util.FormatUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;

/***
 * 二次分拣核对
 * 
 * @author jumbo
 * 
 */
public class PickingSuggestAction extends BaseJQGridProfileAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1983985412302733901L;
    /**
     * 批次号
     */
    private PickingListCommand plCmd;

    @Autowired
    private WareHouseManager wareHouseManager;

    private Long pickinglistId;

    public String pickingSuggestEntry() {
        return SUCCESS;

    }

    /**
     * 配货中批次列表
     * 
     * @return
     */
    public String findPickingListForPickOut() {
        setTableConfig();
        if (plCmd != null) {
            plCmd.setCreateTime(FormatUtil.getDate(plCmd.getCreateTime1()));
            plCmd.setPickingTime(FormatUtil.getDate(plCmd.getPickingTime1()));
        }
        request.put(JSON, toJson(wareHouseManager.findPickingListInfo(plCmd, this.userDetails.getCurrentOu().getId(),0,tableConfig.getSorts())));
        return JSON;
    }

    /**
     * sku明细
     * 
     * @return
     */
    public String findStalineForPickingInfo() {
        setTableConfig();
        request.put(JSON, toJson(wareHouseManager.findPickingSku(pickinglistId, this.userDetails.getCurrentOu().getId(), tableConfig.getSorts())));
        return JSON;
    }

    public PickingListCommand getPlCmd() {
        return plCmd;
    }

    public void setPlCmd(PickingListCommand plCmd) {
        this.plCmd = plCmd;
    }

    public Long getPickinglistId() {
        return pickinglistId;
    }

    public void setPickinglistId(Long pickinglistId) {
        this.pickinglistId = pickinglistId;
    }

}
