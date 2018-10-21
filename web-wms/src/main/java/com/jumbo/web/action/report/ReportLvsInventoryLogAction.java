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
import java.util.List;

import javax.servlet.ServletOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import com.jumbo.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.report.ReportManager;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.StockTransTxLogCommand;
import com.jumbo.util.FormatUtil;
import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;

public class ReportLvsInventoryLogAction extends BaseJQGridProfileAction {
    private static final long serialVersionUID = -860187972765066015L;

    private StockTransTxLogCommand cmd;

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
    public String reportExportLvsInvLog() throws UnsupportedEncodingException {
        cmd.setStockEndTime(addOneDay(cmd.getStockEndTime()));
        try {
            reportManager.checkLineForReportExportForLvsInventoryLog( cmd, userDetails.getCurrentOu().getId());
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            String fileName = new String(getMessage("excel.tplt_report_export_for_levis_inventory_log", new Object[] {}).getBytes("GBK"), "ISO8859-1");
            fileName += FormatUtil.formatDate(cmd.getStockStartTime(), "yyyyMMdd") + "-";
            fileName += FormatUtil.formatDate(cmd.getStockEndTime(), "yyyyMMdd");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName + Constants.EXCEL_XLS);
            ServletOutputStream outs = response.getOutputStream();
            reportManager.reportExportForLvsInventoryLog(outs, cmd, userDetails.getCurrentOu().getId());
            if (outs != null) {
                outs.flush();
                outs.close();
            }
        } catch (BusinessException e) {
            request.put("msg", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
            return ERROR;
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    public String findInvStatus() {
        List<InventoryStatus> list = reportManager.findInventoryStatusByShop(userDetails.getCurrentOu().getParentUnit().getParentUnit().getId());
        request.put(JSON, JsonUtil.collection2json(list));
        return JSON;
    }

    public String findTransType() {
        List<String> list = reportManager.findTransTypeByShop(userDetails.getCurrentOu().getId());
        request.put(JSON, JsonUtil.collection2json(list));
        return JSON;
    }

    public StockTransTxLogCommand getCmd() {
        return cmd;
    }

    public void setCmd(StockTransTxLogCommand cmd) {
        this.cmd = cmd;
    }

}
