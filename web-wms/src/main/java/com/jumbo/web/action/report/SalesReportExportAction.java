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
import com.jumbo.wms.model.report.SalesDataDetailCommand;
import com.jumbo.web.action.BaseJQGridProfileAction;

public class SalesReportExportAction extends BaseJQGridProfileAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = -8165109067430587930L;

    private Date startTime;
    private Date endTime;
    private String supplierSkuCode;
    private String pomotionCode;
    private SalesDataDetailCommand cmd;

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
    public void reportExportForSalesSum() throws UnsupportedEncodingException {
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        String fileName = new String(getMessage("excel.tplt_report_export_for_sales", new Object[] {}).getBytes("GBK"), "ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + Constants.EXCEL_XLS);
        ServletOutputStream outs = null;
        try {
            outs = response.getOutputStream();
            reportManager.reportExportForSalesSum(outs, startTime, addOneDay(endTime), userDetails.getCurrentOu().getId());
            if (outs != null) {
                outs.flush();
                outs.close();
            }
        } catch (Exception e) {
            log.error("",e);
        }
    }

    public void reportExportForSalesDetial() throws UnsupportedEncodingException {
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        String fileName = new String(getMessage("excel.tplt_report_export_for_sales_detial", new Object[] {}).getBytes("GBK"), "ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + Constants.EXCEL_XLS);
        ServletOutputStream outs = null;
        try {
            outs = response.getOutputStream();
            reportManager.reportExportForSalesDetial(cmd, outs, startTime, addOneDay(endTime), supplierSkuCode, pomotionCode, userDetails.getCurrentOu().getId());
            if (outs != null) {
                outs.flush();
                outs.close();
            }
        } catch (Exception e) {
            log.error("",e);
        }
    }



    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getSupplierSkuCode() {
        return supplierSkuCode;
    }

    public void setSupplierSkuCode(String supplierSkuCode) {
        this.supplierSkuCode = supplierSkuCode;
    }

    public String getPomotionCode() {
        return pomotionCode;
    }

    public void setPomotionCode(String pomotionCode) {
        this.pomotionCode = pomotionCode;
    }

    public SalesDataDetailCommand getCmd() {
        return cmd;
    }

    public void setCmd(SalesDataDetailCommand cmd) {
        this.cmd = cmd;
    }

}
