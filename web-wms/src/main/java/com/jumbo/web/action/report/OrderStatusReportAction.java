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
package com.jumbo.web.action.report;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.jumbo.util.FormatUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.manager.report.ReportManager;
import com.jumbo.wms.model.authorization.OperationUnitType;
import com.jumbo.wms.model.command.report.OrderStatusCountCommand;

/**
 * 仓库单据状态汇总报表
 * 
 * @author sjk
 *
 */
public class OrderStatusReportAction extends BaseJQGridProfileAction {

    private static final long serialVersionUID = 6839039220499366486L;

    @Autowired
    private ReportManager reportManager;

    private String startDate;
    private String endDate;

    public String entry() {
        return SUCCESS;
    }

    /**
     * 查询仓库订单状态汇总
     * 
     * @return
     * @throws JSONException
     */
    public String findOrderStatusReporyByOu() throws JSONException {
        JSONObject result = new JSONObject();
        List<OrderStatusCountCommand> list;
        if (OperationUnitType.OUTYPE_ROOT.equals(userDetails.getCurrentOu().getOuType().getName())) {
            list = reportManager.findOrderStatusByOuId(null, getStartDate(), getEndDate());
        } else {
            list = reportManager.findOrderStatusByOuId(userDetails.getCurrentOu().getId(), getStartDate(), getEndDate());
        }
        result.put("result", list);
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 查询快递状态汇总
     * 
     * @return
     * @throws JSONException
     */
    public String findTransOrderStatusReporyByOu() throws JSONException {
        JSONObject result = new JSONObject();
        List<OrderStatusCountCommand> list;
        if (OperationUnitType.OUTYPE_ROOT.equals(userDetails.getCurrentOu().getOuType().getName())) {
            list = reportManager.findTransOrderStatusByOuId(null, getStartDate(), getEndDate());
        } else {
            list = reportManager.findTransOrderStatusByOuId(userDetails.getCurrentOu().getId(), getStartDate(), getEndDate());
        }
        result.put("result", list);
        request.put(JSON, result);
        return JSON;
    }

    public String getStartDate() {
        if (!StringUtils.hasLength(startDate)) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);
            return FormatUtil.formatDate(cal.getTime(), FormatUtil.DATE_FORMATE_YYYYMMDDHHMMSS);
        } else {
            return startDate;
        }
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        if (!StringUtils.hasLength(endDate)) {
            return FormatUtil.formatDate(new Date(), FormatUtil.DATE_FORMATE_YYYYMMDDHHMMSS);
        } else {
            return endDate;
        }
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

}
