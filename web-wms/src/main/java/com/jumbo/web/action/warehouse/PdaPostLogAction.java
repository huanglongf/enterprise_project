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

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.servlet.ServletOutputStream;

import loxia.dao.Pagination;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.jumbo.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.manager.ExcelExportManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerCancel;
import com.jumbo.wms.model.warehouse.PdaPostLogCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.util.DateUtil;
import com.jumbo.util.FormatUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;

/**
 * 
 * @author sjk
 * 
 */
public class PdaPostLogAction extends BaseJQGridProfileAction {

    private static final long serialVersionUID = 8957296802631189177L;

    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private WareHouseManagerCancel wareHouseManagerCancel;
    @Autowired
    private ExcelExportManager excelExportManager;

    private PdaPostLogCommand cmd;

    private Long staid;

    private String code;

    private List<Long> logids;

    private boolean isFinish = false;

    public String ent() {
        return SUCCESS;
    }

    public String pdaExecuteInbound() throws JSONException {
        JSONObject obj = new JSONObject();
        try {
            wareHouseManager.pdaInbound(staid, userDetails.getUser().getId(), isFinish);
            obj.put(RESULT, SUCCESS);
        } catch (Exception e) {
            String msg = getErrorMessage(e, true);
            obj.put(RESULT, ERROR);
            obj.put(MESSAGE, msg);
        }
        request.put(JSON, obj);
        return JSON;
    }

    public String findPdaPostLogBySta() {
        setTableConfig();
        if (cmd != null) {
            if (StringUtils.hasText(cmd.getFromDateStr())) {
                try {
                    cmd.setFromDate(FormatUtil.stringToDate(cmd.getFromDateStr()));
                } catch (ParseException e) {
                    log.error("", e);
                }
            }
            if (StringUtils.hasText(cmd.getToDateStr())) {
                try {
                    cmd.setToDate(FormatUtil.stringToDate(cmd.getToDateStr()));
                    if (DateUtil.isYearDate(cmd.getToDateStr())) {
                        cmd.setToDate(addOneDay(cmd.getToDate()));
                    }
                } catch (ParseException e) {
                    log.error("", e);
                }
            }
        }
        Pagination<PdaPostLogCommand> list = wareHouseManager.findPdaPostLogBySta(tableConfig.getStart(), tableConfig.getPageSize(), staid, cmd, tableConfig.getSorts());
        request.put(JSON, toJson(list));
        return JSON;
    }

    public void pdaLogExport() throws IOException {
        response.setContentType("application/octet-stream;charset=ISO8859-1");
        String fileName = getMessage("excel.tplt_pda_post_log_export");
        fileName = new String(fileName.getBytes("GBK"), "ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + Constants.EXCEL_XLS);
        ServletOutputStream os = response.getOutputStream();
        try {
            excelExportManager.exportPdaLogReport(os, code);
            os.flush();
            os.close();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    public String deleteAllPdaLog() throws JSONException {
        JSONObject rs = new JSONObject();
        rs.put(RESULT, SUCCESS);
        String msg = "";
        try {
            wareHouseManager.deleteAllPdaPostLog(code, userDetails.getUser().getId());
        } catch (BusinessException be) {
            rs.put(RESULT, ERROR);
            msg += getErrorMessage(be, true);
        } catch (Exception e) {
            request.put(REQUEST, ERROR);
            msg += getErrorMessage(e, true);
        }
        rs.put(MESSAGE, msg);
        request.put(JSON, rs);
        return JSON;
    }

    /**
     * 更新pda日志状态为取消
     * 
     * @return
     * @throws JSONException
     */
    public String deletePdaLog() throws JSONException {
        JSONObject rs = new JSONObject();
        rs.put(RESULT, SUCCESS);
        String msg = "";
        for (Long logid : logids) {
            try {
            	wareHouseManagerCancel.deletePdaPostLog(logid, userDetails.getUser().getId());
            } catch (BusinessException be) {
                rs.put(RESULT, ERROR);
                msg += getErrorMessage(be, true);
            } catch (Exception e) {
                request.put(REQUEST, ERROR);
                msg += getErrorMessage(e, true);
            }
        }
        rs.put(MESSAGE, msg);
        request.put(JSON, rs);
        return JSON;
    }


    // 处理
    public String exePostPdaLog() throws Exception {
        JSONObject result = new JSONObject();
        try {
            wareHouseManager.executePostPdaLog(staid, userDetails.getCurrentOu().getId());
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            result.put("result", ERROR);
            result.put("message", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, result);
        return JSON;
    }

    // 导出
    public void exportPdaErrorLog() throws Exception {
        response.setContentType("application/octet-stream;charset=ISO8859-1");
        StockTransApplication sta = wareHouseManager.findStaById(staid);
        String fileName = "PDA上架无效记录_" + sta.getCode();
        fileName = new String(fileName.getBytes("GBK"), "ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + Constants.EXCEL_XLS);
        try {
            excelExportManager.exportPdaErrorLog(response.getOutputStream(), staid);
        } catch (Exception e) {
            log.error("", e);
        }
        ServletOutputStream outs = response.getOutputStream();
        outs.flush();
        outs.close();
    }

    // 删除
    public String delPostPdaErrorLog() throws Exception {
        JSONObject result = new JSONObject();
        try {
            wareHouseManager.deletePostPdaErrorLog(staid, userDetails.getCurrentOu().getId(), userDetails.getUser().getId());
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            result.put("result", ERROR);
            result.put("message", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, result);
        return JSON;
    }


    public PdaPostLogCommand getCmd() {
        return cmd;
    }

    public void setCmd(PdaPostLogCommand cmd) {
        this.cmd = cmd;
    }

    public Long getStaid() {
        return staid;
    }

    public void setStaid(Long staid) {
        this.staid = staid;
    }

    public List<Long> getLogids() {
        return logids;
    }

    public void setLogids(List<Long> logids) {
        this.logids = logids;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(boolean isFinish) {
        this.isFinish = isFinish;
    }
}
