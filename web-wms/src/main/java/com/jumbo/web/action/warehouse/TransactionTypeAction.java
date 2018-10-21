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


import java.util.Date;
import java.util.List;

import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.Constants;
import com.jumbo.wms.manager.system.ChooseOptionManager;
import com.jumbo.wms.manager.warehouse.TransactionTypeManager;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.warehouse.TransactionType;
import com.jumbo.web.action.BaseJQGridProfileAction;


public class TransactionTypeAction extends BaseJQGridProfileAction {

    private static final long serialVersionUID = 6148607087947176924L;
    private TransactionType transactionType;
    @Autowired
    private TransactionTypeManager transactionTypeManager;
    @Autowired
    private ChooseOptionManager chooseOptionManager;

    private List<ChooseOption> directionList;

    private List<ChooseOption> controlList;

    private List<ChooseOption> statusList;

    public String transactionMaintainEntry() {
        directionList = chooseOptionManager.findOptionListByCategoryCode(Constants.CHOOSEOPTION_CATEGORY_CODE_WAREHOUSE_DIRECTION);
        controlList = chooseOptionManager.findOptionListByCategoryCode(Constants.CHOOSEOPTION_CATEGORY_CODE_WAREHOUSE_TRABSACTIONCONTROL);
        statusList = chooseOptionManager.findOptionListByCategoryCode(Constants.CHOOSEOPTION_CATEGORY_CODE_STATUS);
        return SUCCESS;
    }

    public String findTranstypelist() {
        OperationUnit ou = userDetails.getCurrentOu();
        this.setTableConfig();
        List<TransactionType> list = transactionTypeManager.findTranstypelist(transactionType, ou.getId(), tableConfig.getSorts());
        request.put(JSON, toJson(list));
        return JSON;
    }

    public String addorModifyTransactionType() throws Exception {
        transactionType.setDescription(this.CharacterReplace(transactionType.getDescription()));
        OperationUnit ou = userDetails.getCurrentOu();
        transactionType.setOu(ou);
        transactionType.setLastModifyTime(new Date());
        transactionType = transactionTypeManager.createorModifyTransactionType(transactionType);
        if (transactionType == null) {
            request.put(JSON, new JSONObject().put("code", ""));
        } else {
            this.asynReturnTrueValue();
        }
        return JSON;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public List<ChooseOption> getDirectionList() {
        return directionList;
    }

    public void setDirectionList(List<ChooseOption> directionList) {
        this.directionList = directionList;
    }

    public List<ChooseOption> getControlList() {
        return controlList;
    }

    public void setControlList(List<ChooseOption> controlList) {
        this.controlList = controlList;
    }

    public List<ChooseOption> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<ChooseOption> statusList) {
        this.statusList = statusList;
    }


}
