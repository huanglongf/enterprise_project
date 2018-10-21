package com.jumbo.web.action.warehouse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import loxia.dao.Pagination;
import loxia.support.excel.ExcelKit;
import loxia.support.excel.ReadStatus;
import loxia.support.json.JSONObject;

import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.vmi.espData.ESPOrderManager;
import com.jumbo.wms.manager.warehouse.excel.ExcelReadManager;
import com.jumbo.wms.model.vmi.espData.ESPInvoicePercentage;
import com.jumbo.wms.model.vmi.espData.ESPInvoicePercentageCommand;
import com.jumbo.util.FormatUtil;
import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;

public class VMIEspritInvoicePercentageAction extends BaseJQGridProfileAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = -622451033826253507L;

    private File file;

    private ESPInvoicePercentageCommand ipCommand;
    @Autowired
    private ESPOrderManager ordManage;
    @Autowired
    private ExcelReadManager excelReadManager;

    private String invoiceNum;

    private String po;


    public String vmiInvoicePercentageEntry() {
        return SUCCESS;
    }

    public String importInvoicePertentage() {
        List<String> errorMsg = new ArrayList<String>();
        if (file == null) {
            return ERROR;
        }
        try {
            ReadStatus rs = excelReadManager.importInvoicePercentage(file);
            request.put("result", ERROR);
            if (rs.getStatus() == ReadStatus.STATUS_SUCCESS) {
                request.put("result", SUCCESS);
            } else if (rs.getStatus() > ReadStatus.STATUS_SUCCESS) {
                List<String> list = ExcelKit.getInstance().getReadStatusMessages(rs, this.getLocale());
                for (String s : list) {
                    errorMsg.add(s);
                }
            } else if (rs.getStatus() < ReadStatus.STATUS_SUCCESS) {
                for (Exception ex : rs.getExceptions()) {
                    if (ex instanceof BusinessException) {
                        BusinessException bex = (BusinessException) ex;
                        String msg = this.getMessage(ErrorCode.BUSINESS_EXCEPTION + bex.getErrorCode(), bex.getArgs());
                        errorMsg.add(msg);
                    }
                }
            }
        } catch (BusinessException e) {
            request.put("result", ERROR);
            String msg = this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs());
            errorMsg.add(msg);
        } catch (Exception e) {
            log.error("",e);
            request.put("result", ERROR);
            errorMsg.add(e.getCause() + " " + e.getMessage());
        }
        request.put("message", JsonUtil.collection2json(errorMsg));
        return SUCCESS;
    }

    public String findInvoicePertentage() {
        setTableConfig();
        if (ipCommand != null) {
            ipCommand.setStartTime(FormatUtil.getDate(ipCommand.getStartTime1()));
            ipCommand.setEndTime(FormatUtil.getDate(ipCommand.getEndTime1()));
        }
        Pagination<ESPInvoicePercentage> poTypeList = ordManage.findInvoicePertentage(tableConfig.getStart(), tableConfig.getPageSize(), ipCommand, tableConfig.getSorts());
        request.put(JSON, toJson(poTypeList));
        return JSON;
    }

    /**
     * 进入确认收货编辑页面
     * 
     * @return
     * @throws Exception
     */
    public String findInvoiceNum() throws Exception {
        List<String> errorMsg = new ArrayList<String>();
        try {
            ESPInvoicePercentage ip = ordManage.findIP(ipCommand.getInvoiceNum());
            request.put(JSON, JsonUtil.obj2json(ip));
            if (ip == null) {
                request.put("result", ERROR);
            }
            return JSON;
        } catch (BusinessException e) {
            request.put("result", ERROR);
            String msg = this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs());
            errorMsg.add(msg);
        } catch (Exception e) {
            log.error("",e);
            request.put("result", ERROR);
            errorMsg.add(e.getCause() + " " + e.getMessage());
        }
        request.put("message", JsonUtil.collection2json(errorMsg));
        return JSON;
    }

    /**
     * PO与发票系数绑定
     * 
     * @return
     */
    public String vmiInvoicePercentageBD() {
        return SUCCESS;
    }

    public String espPoAndInvoiceBD() throws Exception {
        JSONObject result = new JSONObject();
        try {
            ordManage.espPoAndInvoiceBD(po, invoiceNum);
            result.put("rs", SUCCESS);
        } catch (BusinessException e) {
            log.error(getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
            result.put("msg", getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        } catch (Exception e) {
            log.error("",e);
            result.put("msg", e.getCause() + " " + e.getMessage());
        }
        request.put(JSON, result);
        return JSON;
    }

    public String findESPPoInvoiceBD() {
        setTableConfig();
        request.put(JSON, toJson(ordManage.findESPPoInvoiceBD(tableConfig.getStart(), tableConfig.getPageSize(), po, invoiceNum, tableConfig.getSorts())));
        return JSON;
    }


    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public ESPInvoicePercentageCommand getIpCommand() {
        return ipCommand;
    }

    public void setIpCommand(ESPInvoicePercentageCommand ipCommand) {
        this.ipCommand = ipCommand;
    }

    public String getInvoiceNum() {
        return invoiceNum;
    }

    public void setInvoiceNum(String invoiceNum) {
        this.invoiceNum = invoiceNum;
    }

    public String getPo() {
        return po;
    }

    public void setPo(String po) {
        this.po = po;
    }



}
