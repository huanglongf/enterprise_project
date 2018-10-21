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
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerQuery;
import com.jumbo.wms.model.warehouse.PickingListCommand;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.web.action.BaseJQGridProfileAction;

public class SalesPickingCheckAction extends BaseJQGridProfileAction {

    private static final long serialVersionUID = 7303787769148135283L;

    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private WareHouseManagerQuery wareHouseManagerQuery;

    private PickingListCommand cmd;

    private StockTransApplicationCommand sta;

    private String lineNo;

    public String entPlCheck() {
        return SUCCESS;
    }

    public String getOcpStalByPl() {
        setTableConfig();
        List<StaLineCommand> list = wareHouseManagerQuery.findOccpiedStaLineByPlId(cmd.getId(), tableConfig.getSorts());
        request.put(JSON, toJson(list));
        return JSON;
    }

    /**
     * 执行核对
     * 
     * @return
     * @throws JSONException
     */
    public String checkStaList() throws JSONException {
        JSONObject result = new JSONObject();
        result.put("result", SUCCESS);
        try {
            wareHouseManager.checkSta(sta.getId(), sta.getTrackingNo(), lineNo, userDetails.getUser().getId());
        } catch (BusinessException e) {
            result.put("result", ERROR);
            result.put("msg", getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
            if (e.getErrorCode() == ErrorCode.STA_CANCELED) {
                result.put("isCancel", true);
            }
        }
        request.put(JSON, result);
        return JSON;
    }

    public StockTransApplicationCommand getSta() {
        return sta;
    }

    public void setSta(StockTransApplicationCommand sta) {
        this.sta = sta;
    }

    public PickingListCommand getCmd() {
        return cmd;
    }

    public void setCmd(PickingListCommand cmd) {
        this.cmd = cmd;
    }

    public String getLineNo() {
        return lineNo;
    }

    public void setLineNo(String lineNo) {
        this.lineNo = lineNo;
    }

}
