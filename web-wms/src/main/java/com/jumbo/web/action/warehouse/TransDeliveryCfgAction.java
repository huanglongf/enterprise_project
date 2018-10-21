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

import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.web.action.BaseJQGridProfileAction;

/**
 * 物流发货控制
 * 
 * @author dly
 * 
 */
public class TransDeliveryCfgAction extends BaseJQGridProfileAction {

    private static final long serialVersionUID = 8133641551965456462L;

    @Autowired
    private WareHouseManager wareHouseManager;

    private Long qty;

    private Long transId;

    public String transCfgEntry() {
        return SUCCESS;
    }

    public String queryTransportatorCfg() {
        setTableConfig();
        request.put(JSON, toJson(wareHouseManager.findPredefinedStaByType(userDetails.getCurrentOu().getId())));
        return JSON;
    }

    public String saveCfg() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            wareHouseManager.updateTransDeliveryCfg(userDetails.getCurrentOu().getId(), transId, qty);
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            result.put("result", ERROR);
            result.put("msg", getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, result);
        return JSON;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public Long getTransId() {
        return transId;
    }

    public void setTransId(Long transId) {
        this.transId = transId;
    }

}
