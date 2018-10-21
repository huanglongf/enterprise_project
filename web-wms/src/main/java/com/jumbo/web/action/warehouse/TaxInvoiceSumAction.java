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
import com.jumbo.wms.manager.DropDownListManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.web.action.BaseJQGridProfileAction;

/***
 * 税控发票导出
 * 
 * @author xiazhongbin
 * 
 */
public class TaxInvoiceSumAction extends BaseJQGridProfileAction {

    private static final long serialVersionUID = 5029420869862210452L;

    @Autowired
    private WareHouseManager wareHouseManager;

    @Autowired
    private DropDownListManager dropDownListManager;

    private List<ChooseOption> plStatus;
    private Long staid;
    private Long plid;

    public String taxInvoiceExportSum() {
        plStatus = dropDownListManager.findPickingListStatusOptionList();
        return SUCCESS;
    }

    public String updateStaInvoice() throws JSONException {
        JSONObject obj = new JSONObject();
        try {
            wareHouseManager.resetInvoiceExecuteCount(plid, staid);
            obj.put("result", SUCCESS);
        } catch (BusinessException e) {
            obj.put("result", ERROR);
        }
        request.put(JSON, obj);
        return JSON;
    }

    public List<ChooseOption> getPlStatus() {
        return plStatus;
    }

    public void setPlStatus(List<ChooseOption> plStatus) {
        this.plStatus = plStatus;
    }

    public Long getStaid() {
        return staid;
    }

    public void setStaid(Long staid) {
        this.staid = staid;
    }

    public Long getPlid() {
        return plid;
    }

    public void setPlid(Long plid) {
        this.plid = plid;
    }

}
