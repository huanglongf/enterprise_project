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


import java.util.List;

import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.web.action.BaseJQGridProfileAction;

/**
 * 物流配送价格租查寻
 * 
 * @author dly
 * 
 */
public class PadcodeOperateAction extends BaseJQGridProfileAction {

    private static final long serialVersionUID = 8133641551965456462L;

    @Autowired
    private WareHouseManager wareHouseManager;

    private String code;

    private List<Long> ids;

    @Override
    public String execute() {
        return SUCCESS;
    }

    /**
     * 查寻 PDA 数据
     * 
     * @return
     */
    public String queryPadcodeOperate() {
        setTableConfig();
        request.put(JSON, toJson(wareHouseManager.queryPadcodeOperate(code)));
        return JSON;
    }

    /**
     * 添加PDA
     * 
     * @return
     * @throws JSONException
     */
    public String savePadcodeOperate() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            wareHouseManager.savePadcodeOperate(code);
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            result.put("result", ERROR);
            result.put("message", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 删除PDA
     * 
     * @return
     * @throws JSONException
     */
    public String deletePadcodeOperate() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            wareHouseManager.deletePadcodeOperate(ids);
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            result.put("result", ERROR);
            result.put("message", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, result);
        return JSON;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }


}
