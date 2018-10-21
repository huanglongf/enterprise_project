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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.Constants;
import com.jumbo.manager.ExcelExportManager;
import com.jumbo.util.FormatUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.manager.authorization.AuthorizationManager;
import com.jumbo.wms.model.authorization.OperationUnit;

/**
 * 
 * @author jumbo 换货出库，销售出库发票导出 选择条件 - 出库时间
 */
public class InvoiceRecordExportAction extends BaseJQGridProfileAction implements ServletResponseAware {

    /**
	 * 
	 */
    private static final long serialVersionUID = -5701503637076291286L;
    @Autowired
    private ExcelExportManager excelExportManager;
    private HttpServletResponse response;
    @Autowired
    private AuthorizationManager authorizationManager;

    private String fileName;
    private String fromDate;
    private String endDate;
    private String lpCode;
    private Long whId;

    /**
     * 
     * @return
     */
    public String invoiceRecordExportEntry() {
        return SUCCESS;
    }


    public String vmiInvoiceRecordExportEntry() {
        return SUCCESS;
    }


    // /**
    // * 导出税控发票
    // *
    // * @throws UnsupportedEncodingException
    // *
    // * @throws Exception
    // */
    // public String exportSoInvoiceForSalesReturnOrder_() {
    // if (fromDate == null) {
    // throw new BusinessException("查询开始时间不能为空");
    // }
    // if (endDate == null) {
    // throw new BusinessException("查询结束时间不能为空");
    // }
    // try {
    // Date _fromDate = FormatUtil.stringToDate(fromDate, "yyyy_MM_dd HH:mm:ss");
    // Date _endDate = FormatUtil.stringToDate(endDate, "yyyy_MM_dd HH:mm:ss");
    // String fileName = fromDate + "-" + endDate;
    // log.info("fileName {} ", fileName);
    // setFileName(fileName + Constants.EXCEL_XLS);
    // ByteArrayOutputStream out = new ByteArrayOutputStream();
    // excelWriterManager.exportInvoiceRecordForSalesOrderReturnOrder(out,
    // this.getCurrentOu().getId(), _fromDate, _endDate);
    // ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
    // setInputStream(in);
    // } catch (ParseException e) {
    // log.error("",e);
    // }
    // return SUCCESS;
    // }

    // 交接清单导出
    public void findHandOverListExportReturnOrderRecord() throws Exception {
        if (fromDate == null) {
            throw new BusinessException("查询开始时间不能为空");
        }
        if (endDate == null) {
            throw new BusinessException("查询结束时间不能为空");
        }
        response.setContentType("application/octet-stream;charset=ISO8859-1");
        Date _fromDate = FormatUtil.stringToDate(fromDate, "yyyy-MM-dd HH:mm:ss");
        Date _endDate = FormatUtil.stringToDate(endDate, "yyyy-MM-dd HH:mm:ss");
        String _fileName = "[物流交接清单]" + fromDate + "-" + endDate;
        _fileName = _fileName.replace(" ", "-");
        _fileName = new String(_fileName.getBytes("GBK"), "ISO8859-1");
        log.info("==============fileName : {}=================", _fileName);
        response.setHeader("Content-Disposition", "attachment;filename=" + _fileName + Constants.EXCEL_XLS);
        String typeName = userDetails.getCurrentOu().getOuType().getName();
        Long ouId = userDetails.getCurrentOu().getId();
        // Map<String, Object> map = new HashMap<String,Object>();
        try {
            // 如果是运营中心
            if (typeName.equals("OperationCenter")) {
                if (whId == null) {
                    List<OperationUnit> ouList = authorizationManager.findOperationUnitList(ouId);
                    List<Long> idList = new ArrayList<Long>();
                    for (OperationUnit ou : ouList) {
                        idList.add(ou.getId());
                    }
                    excelExportManager.findHandOverListExportReturnOrderRecord(response.getOutputStream(), _fromDate, _endDate, lpCode, idList, null);
                } else {
                    excelExportManager.findHandOverListExportReturnOrderRecord(response.getOutputStream(), _fromDate, _endDate, lpCode, null, whId);
                }
            } else if (typeName.equals("Warehouse")) {
                excelExportManager.findHandOverListExportReturnOrderRecord(response.getOutputStream(), _fromDate, _endDate, lpCode, null, ouId);
            }
        } catch (Exception e) {
            // e.printStackTrace();
            // log.error("", e);
            if (log.isErrorEnabled()) {
                log.error("findHandOverListExportReturnOrderRecord error:", e);
            };
        }
        ServletOutputStream outs = response.getOutputStream();
        outs.flush();
        outs.close();
    }

    // public void exportSoInvoiceForSalesReturnOrder() throws Exception {
    // if (fromDate == null) {
    // throw new BusinessException("查询开始时间不能为空");
    // }
    // if (endDate == null) {
    // throw new BusinessException("查询结束时间不能为空");
    // }
    // response.setContentType("application/octet-stream;charset=ISO8859-1");
    // Date _fromDate = FormatUtil.stringToDate(fromDate, "yyyy-MM-dd HH:mm:ss");
    // Date _endDate = FormatUtil.stringToDate(endDate, "yyyy-MM-dd HH:mm:ss");
    // String _fileName = fromDate + "-" + endDate;
    // _fileName = _fileName.replace(" ", "-");
    // // fileName = new String(fileName.getBytes("GBK"), "ISO8859-1");
    // log.info("==============fileName : {}=================", _fileName);
    // response.setHeader("Content-Disposition", "attachment;filename=" + _fileName +
    // Constants.EXCEL_XLS);
    // try {
    // excelWriterManager.exportInvoiceRecordForSalesOrderReturnOrder(response.getOutputStream(),
    // this.getCurrentOu().getId(), _fromDate, _endDate);
    // } catch (Exception e) {
    // log.error("",e);
    // }
    // ServletOutputStream outs = response.getOutputStream();
    // outs.flush();
    // outs.close();
    // }


    /**
     * 导出税控发票
     * 
     * @throws UnsupportedEncodingException
     * 
     * @throws Exception
     */
    public String exportVMIInvoice_() {
        if (fromDate == null) {
            throw new BusinessException("查询开始时间不能为空");
        }
        if (endDate == null) {
            throw new BusinessException("查询结束时间不能为空");
        }
        try {
            Date _fromDate = FormatUtil.stringToDate(fromDate, "yyyy_MM_dd HH:mm:ss");
            Date _endDate = FormatUtil.stringToDate(endDate, "yyyy_MM_dd HH:mm:ss");
            String fileName = fromDate + "-" + endDate;
            log.info("fileName {} ", fileName);
            setFileName(fileName + Constants.EXCEL_XLS);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            excelExportManager.exportVMIInvoiceRecord(out, this.getCurrentOu().getId(), _fromDate, _endDate);
            ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
            setInputStream(in);
        } catch (ParseException e) {
            log.error("", e);
        }
        return SUCCESS;
    }


    public void exportVMIInvoice() throws Exception {
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
            excelExportManager.exportVMIInvoiceRecord(response.getOutputStream(), this.getCurrentOu().getId(), _fromDate, _endDate);
        } catch (Exception e) {
            log.error("", e);
        }
        ServletOutputStream outs = response.getOutputStream();
        outs.flush();
        outs.close();
    }


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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

    public void setServletResponse(HttpServletResponse response) {
        this.response = response;
    }


    public String getLpCode() {
        return lpCode;
    }


    public void setLpCode(String lpCode) {
        this.lpCode = lpCode;
    }


    public Long getWhId() {
        return whId;
    }


    public void setWhId(Long whId) {
        this.whId = whId;
    }

}
