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
import com.jumbo.wms.model.warehouse.WorkLineNo;
import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;

public class WorkLineNoAction extends BaseJQGridProfileAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = -3174070359613322211L;
    @Autowired
    private WareHouseManager wareHouseManager;


    private List<WorkLineNo> workLineNo;

    // 创建
    public String workLineNoCreate() {
        return SUCCESS;
    }

    // 查寻
    public String queryWorkLineNo() {
        List<WorkLineNo> list = wareHouseManager.queryWorkLineNoByOuid(this.getCurrentOu().getId());
        request.put(JSON, JsonUtil.collection2json(list));
        return JSON;
    }

    public String updateLineNo() throws JSONException {
        JSONObject json = new JSONObject();
        try {
            wareHouseManager.updateLineNoByOuId(workLineNo, userDetails.getCurrentOu().getId());
            json.put(RESULT, SUCCESS);
        } catch (BusinessException e) {
            json.put(RESULT, ERROR);
            json.put(MESSAGE, this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
            BusinessException le = e.getLinkedException();
            if (le != null) {
                json.put(MESSAGE, this.getMessage(ErrorCode.BUSINESS_EXCEPTION + le.getErrorCode(), le.getArgs()));
            }
        }
        request.put(JSON, json);
        return JSON;
    }

    public List<WorkLineNo> getWorkLineNo() {
        return workLineNo;
    }

    public void setWorkLineNo(List<WorkLineNo> workLineNo) {
        this.workLineNo = workLineNo;
    }
}
