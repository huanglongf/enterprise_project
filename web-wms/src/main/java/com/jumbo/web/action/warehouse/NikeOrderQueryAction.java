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

import loxia.dao.Pagination;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.warehouse.StaCreateQueueCommand;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.webservice.nike.manager.NikeOrderManager;


/**
 * nike 过单查寻
 * 
 * @author dailingyun
 * 
 */
public class NikeOrderQueryAction extends BaseJQGridProfileAction {
    /**
	 * 
	 */
    private static final long serialVersionUID = 433887006269649461L;

    @Autowired
    private NikeOrderManager nikeOrderManager;

    private StaCreateQueueCommand comd;

    private List<Long> idList;

    public String nikeOrderFailure() {
        return SUCCESS;
    }

    public String nikeOrderQuery() {
        setTableConfig();
        Pagination<StaCreateQueueCommand> list = nikeOrderManager.findNikeOrder(tableConfig.getStart(), tableConfig.getPageSize(), comd, userDetails.getCurrentOu().getId(), tableConfig.getSorts());
        if (list != null) {
            request.put(JSON, toJson(list));
        }
        return JSON;
    }

    public String forUpdateStatus() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            nikeOrderManager.forUpdateStatus(idList, DefaultStatus.INEXECUTION);
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            result.put("result", ERROR);
            result.put("message", getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, result);
        return JSON;

    }

    public StaCreateQueueCommand getComd() {
        return comd;
    }

    public void setComd(StaCreateQueueCommand comd) {
        this.comd = comd;
    }

    public List<Long> getIdList() {
        return idList;
    }

    public void setIdList(List<Long> idList) {
        this.idList = idList;
    }

}
