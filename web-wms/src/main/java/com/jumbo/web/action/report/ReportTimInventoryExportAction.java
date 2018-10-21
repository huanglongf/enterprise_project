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

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.Constants;
import com.jumbo.util.FormatUtil;
import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.manager.report.ReportManager;
import com.jumbo.wms.model.warehouse.InventoryCommand;

public class ReportTimInventoryExportAction extends BaseJQGridProfileAction {

    private static final long serialVersionUID = 6394393758668574951L;

    private String time;
    private InventoryCommand cmd;

    private String code;

    @Autowired
    private ReportManager reportManager;

    public String entry() {
        return SUCCESS;
    }

    /**
     * 报表导出
     * 
     * @throws UnsupportedEncodingException
     * 
     * @throws Exception
     */
    public void reportExportTimeInventory() throws UnsupportedEncodingException {

        Date date;
        try {
            date = FormatUtil.stringToDate(time);
        } catch (ParseException e1) {
            // e1.printStackTrace();
            if (log.isErrorEnabled()) {
                log.error("ParseException", e1);
            };
            date = new Date();
        }
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        String fileName = new String(getMessage("excel.tplt_report_export_for_time_inventory_by_shop", new Object[] {}).getBytes("GBK"), "ISO8859-1");
        fileName += FormatUtil.formatDate(date, "yyyyMMdd");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + Constants.EXCEL_XLS);
        ServletOutputStream outs = null;
        try {
            outs = response.getOutputStream();
            reportManager.exportShopTimeInventory(outs, date, userDetails.getCurrentOu().getId(), cmd);
            if (outs != null) {
                outs.flush();
                outs.close();
            }
        } catch (Exception e) {
            log.error("", e);
        }
    }

    public String findLvsQuerySelect() {
        List<String> list;
        if (code.equals("consumerGroup")) {
            list = reportManager.findConsumerGroupList();
        } else if (code.equals("productLine")) {
            list = reportManager.findProductLineList();
        } else if (code.equals("productCategory")) {
            list = reportManager.findProductCategoryList();
        } else {
            list = new ArrayList<String>();
        }
        request.put(JSON, JsonUtil.collection2json(list));
        return JSON;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public InventoryCommand getCmd() {
        return cmd;
    }

    public void setCmd(InventoryCommand cmd) {
        this.cmd = cmd;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
