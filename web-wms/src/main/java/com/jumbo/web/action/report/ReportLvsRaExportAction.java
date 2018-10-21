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
import java.util.Date;
import javax.servlet.ServletOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import com.jumbo.Constants;
import com.jumbo.wms.manager.report.ReportManager;
import com.jumbo.wms.model.report.SalesRaDataCommand;
import com.jumbo.util.FormatUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;

public class ReportLvsRaExportAction extends BaseJQGridProfileAction {
    private static final long serialVersionUID = -3939731712207447549L;

    private Date fromDate;
    private Date toDate;

    private SalesRaDataCommand cmd;

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
    public void reportExportRa() throws UnsupportedEncodingException {
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        String fileName = new String(getMessage("excel.tplt_report_export_for_levis_ra", new Object[] {}).getBytes("GBK"), "ISO8859-1");
        fileName += FormatUtil.formatDate(fromDate, "yyyyMMdd") + "-";
        fileName += FormatUtil.formatDate(toDate, "yyyyMMdd") + "-";
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + Constants.EXCEL_XLS);
        ServletOutputStream outs = null;
        try {
            outs = response.getOutputStream();
            reportManager.reportExportForLvsRa(outs, cmd, fromDate, addOneDay(toDate), userDetails.getCurrentOu().getId());
            if (outs != null) {
                outs.flush();
                outs.close();
            }
        } catch (Exception e) {
            log.error("",e);
        }
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public SalesRaDataCommand getCmd() {
        return cmd;
    }

    public void setCmd(SalesRaDataCommand cmd) {
        this.cmd = cmd;
    }

}
