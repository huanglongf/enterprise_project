package com.jumbo.web.action.baseinfo;



import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletOutputStream;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.Constants;
import com.jumbo.manager.ExcelExportManager;
import com.jumbo.util.FormatUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;

public class SalesReportFormAction extends BaseJQGridProfileAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = -8640131201444847304L;

    @Autowired
    private ExcelExportManager excelExportManager;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
    private String outboundTime;
    private String endOutboundTime;

    public String salesReportForm() {
        return SUCCESS;
    }

    public void exportSalesReportForm() throws IOException {
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=SALES_REPORT_FROM_" + sdf.format(new Date()) + Constants.EXCEL_XLS);
        ServletOutputStream outs = null;
        try {
            outs = response.getOutputStream();
            excelExportManager.exportSalesReportForm(getDateByString(outboundTime), getDateByString(endOutboundTime), outs);
        } catch (Exception e) {
            log.error("", e);
        } finally {
            if (outs != null) {
                outs.flush();
                outs.close();
            }
        }
    }

    public Date getDateByString(String date) {
        if (date != null && !"".equals(date)) {
            try {
                return FormatUtil.stringToDate(date);
            } catch (ParseException e) {
                log.error("", e);
            }
        }
        return null;
    }

    public String getOutboundTime() {
        return outboundTime;
    }

    public void setOutboundTime(String outboundTime) {
        this.outboundTime = outboundTime;
    }

    public String getEndOutboundTime() {
        return endOutboundTime;
    }

    public void setEndOutboundTime(String endOutboundTime) {
        this.endOutboundTime = endOutboundTime;
    }


}
