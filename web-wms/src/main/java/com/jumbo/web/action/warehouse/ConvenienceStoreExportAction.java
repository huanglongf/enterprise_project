package com.jumbo.web.action.warehouse;

import java.util.Date;

import javax.servlet.ServletOutputStream;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.Constants;
import com.jumbo.manager.ExcelExportManager;
import com.jumbo.util.FormatUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.exception.BusinessException;

/**
 * 便利店自提信息导出
 * 
 * @author jinlong.ke
 * 
 */
public class ConvenienceStoreExportAction extends BaseJQGridProfileAction {

    /**
     * 
     */
    private static final long serialVersionUID = 1110651698675365676L;
    @Autowired
    private ExcelExportManager excelExportManager;
    private String fromDate;
    private String endDate;

    /**
     * 简单页面跳转
     * 
     * @return
     */
    public String redirect() {
        return SUCCESS;
    }

    public void exportConvenienceStoreOrderInfo() throws Exception {
        if (fromDate == null) {
            throw new BusinessException("查询开始时间不能为空");
        }
        if (endDate == null) {
            throw new BusinessException("查询结束时间不能为空");
        }
        response.setContentType("application/octet-stream;charset=ISO8859-1");
        Date _fromDate = FormatUtil.stringToDate(fromDate, "yyyy-MM-dd HH:mm:ss");
        Date _endDate = FormatUtil.stringToDate(endDate, "yyyy-MM-dd HH:mm:ss");
        String _fileName = fromDate + "-" + endDate;
        _fileName = _fileName.replace(" ", "-");
        // fileName = new String(fileName.getBytes("GBK"), "ISO8859-1");
        log.info("==============fileName : {}=================", _fileName);
        response.setHeader("Content-Disposition", "attachment;filename=" + _fileName + Constants.EXCEL_XLS);
        try {
            // excelExportManager.exportVMIInvoiceRecord(response.getOutputStream(),
            // this.getCurrentOu().getId(), _fromDate, _endDate);
            excelExportManager.exportConvenienceStoreOrderInfo(response.getOutputStream(), this.getCurrentOu().getId(), _fromDate, _endDate);
        } catch (Exception e) {
            log.error("", e);
        }
        ServletOutputStream outs = response.getOutputStream();
        outs.flush();
        outs.close();
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
