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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;

import loxia.dao.Pagination;
import loxia.support.excel.ExcelKit;
import loxia.support.excel.ReadStatus;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.manager.ExcelExportManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerCancel;
import com.jumbo.wms.manager.warehouse.excel.ExcelReadManager;
import com.jumbo.wms.model.WmsOtherOutBoundInvNoticeOmsStatus;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.util.FormatUtil;
import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;

/**
 * 预定义出库
 * 
 * @author dly
 * 
 */
public class PredefinedOutAction extends BaseJQGridProfileAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private WareHouseManagerCancel wareHouseManagerCancel;
    @Autowired
    private ExcelReadManager excelReadManager;
    private StockTransApplicationCommand sta;
    @Autowired
    private ExcelExportManager excelExportManager;

    private File file;

    public String operationOutCreate() {
        return SUCCESS;
    }

    public String operationOutQuery() {
        return SUCCESS;
    }

    /**
     * 预定义出库导入创建
     * 
     * @return
     */
    public String createOperationOut() {
        List<String> errorMsg = new ArrayList<String>();
        if (file == null) {
            request.put("result", ERROR);
            return ERROR;
        } else {
            try {
                ReadStatus rs = excelReadManager.createOperationOut(file, sta, userDetails.getCurrentOu().getId(), userDetails.getUser().getId());
                if (rs.getStatus() == ReadStatus.STATUS_SUCCESS) {
                    request.put("result", SUCCESS);
                } else if (rs.getStatus() > 0) {
                    request.put("result", ERROR);
                    List<String> list = ExcelKit.getInstance().getReadStatusMessages(rs, this.getLocale());
                    for (String s : list) {
                        errorMsg.add(s);
                    }
                } else if (rs.getStatus() < 0) {
                    request.put("result", ERROR);
                    for (Exception ex : rs.getExceptions()) {
                        if (ex instanceof BusinessException) {
                            BusinessException bex = (BusinessException) ex;
                            String msg = this.getMessage(ErrorCode.BUSINESS_EXCEPTION + bex.getErrorCode(), bex.getArgs());
                            errorMsg.add(msg);
                        }
                    }
                }
            } catch (BusinessException e) {
                log.error("", e);
                request.put("result", ERROR);
                if (e.getErrorCode() > 0) {
                    errorMsg.add(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
                }
                String ss = null;
                BusinessException current = e;
                while (current.getLinkedException() != null) {
                    current = current.getLinkedException();
                    ss = this.getMessage(ErrorCode.BUSINESS_EXCEPTION + current.getErrorCode(), current.getArgs());
                    errorMsg.add(ss);
                }
            } catch (Exception e) {
                log.error("", e);
                if (e instanceof BusinessException) {
                    BusinessException be = (BusinessException) e;
                    request.put("result", ERROR);
                    String msg = this.getMessage(ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode(), be.getArgs());
                    errorMsg.add(msg);
                } else {
                    request.put("result", ERROR);
                    errorMsg.add(e.getMessage());
                }
            }
            request.put("message", JsonUtil.collection2json(errorMsg));
        }
        return SUCCESS;
    }

    /**
     * 预定义出库查寻
     * 
     * @return
     */
    public String queryPredefinedOutStaList() {
        setTableConfig();
        if (sta != null) {
            sta.setCreateTime(FormatUtil.getDate(sta.getCreateTime1()));
            sta.setEndCreateTime(FormatUtil.getDate(sta.getEndCreateTime1()));
        }
        Pagination<StockTransApplicationCommand> list = wareHouseManager.queryPredefinedOutStaList(tableConfig.getStart(), tableConfig.getPageSize(), userDetails.getCurrentOu().getId(), sta, tableConfig.getSorts());
        request.put(JSON, toJson(list));
        return JSON;
    }

    /**
     * 预定义出库占用文件模版导出
     * 
     * @throws Exception
     */
    public void predefinedOutExport() throws Exception {
        StockTransApplication s = wareHouseManager.findStaById(sta.getId());
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=STA_" + s.getCode() + Constants.EXCEL_XLS);
        ServletOutputStream outs = null;
        try {
            outs = response.getOutputStream();
            excelExportManager.predefinedOutExport(outs, s);
        } catch (Exception e) {
            log.error("", e);
        } finally {
            if (outs != null) {
                outs.flush();
                outs.close();
            }
        }
    }

    /**
     * 预定义出库占用 导入
     * 
     * @return
     * @throws Exception -----------------------------------------------------
     */
    public String predefinedOutImport() throws Exception {
        String msg = SUCCESS;
        request.put("msg", msg);
        if (sta == null || file == null) {
            msg = (sta == null ? "sta.id must not be null" : "") + (file == null ? " The upload file must not be null" : "");
            log.error(msg);
            request.put("msg", msg);
        } else {
            try {
                ReadStatus rs = excelReadManager.predefinedOutImport(sta.getId(), file, userDetails.getUser(), null);
                if (rs.getStatus() > ReadStatus.STATUS_SUCCESS) {
                    request.put("msg", "");
                    List<String> list = ExcelKit.getInstance().getReadStatusMessages(rs, this.getLocale());
                    request.put("msg", list);
                } else if (rs.getStatus() < ReadStatus.STATUS_SUCCESS) {
                    request.put("msg", "");
                    List<String> list = new ArrayList<String>();
                    for (Exception ex : rs.getExceptions()) {
                        if (ex instanceof BusinessException) {
                            BusinessException be = (BusinessException) ex;
                            list.add(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode(), be.getArgs()) + Constants.HTML_LINE_BREAK);
                        }
                    }
                    request.put("msg", list);
                }
            } catch (BusinessException e) {
                request.put("request", "error");
                log.error("", e);
                StringBuffer sb = new StringBuffer();
                if (e.getErrorCode() > 0) {
                    sb.append(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
                }
                BusinessException be = e;
                while ((be = be.getLinkedException()) != null) {
                    sb.append(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode(), be.getArgs()));
                }
                log.error(e.getMessage());
                request.put("msg", sb.toString());
            } catch (Exception e) {
                request.put("request", "error");
                log.error("", e);
                log.error(e.getMessage());
                request.put("msg", e.getMessage());
            }
        }
        return SUCCESS;
    }

    /**
     * 预定义出库自动占用
     * 
     * @return
     * @throws Exception
     */
    public String predefinedOutOccupation() throws Exception {
        JSONObject obj = new JSONObject();
        try {
            wareHouseManager.predefinedOutOccupation(sta.getId(), userDetails.getUser());
            obj.put(SUCCESS, SUCCESS);
        } catch (BusinessException e) {
            log.error("", e);
            obj.put(ERROR, ERROR);
            obj.put("msg", catchBusinessException(e));
        } catch (Exception e) {
            log.error("", e);
            obj.put(ERROR, ERROR);
            obj.put("msg", catchException(e));
        }
        request.put(JSON, obj);
        return JSON;
    }

    /**
     * 取消
     * 
     * @return
     * @throws Exception
     */
    public String predefinedOutCanceled() throws Exception {
        JSONObject obj = new JSONObject();
        try {
        	wareHouseManagerCancel.predefinedOutCanceled(sta.getId(), sta.getMemo(), userDetails.getUser());
            wareHouseManager.updateWmsOtherOutBoundInvNoticeOms(sta.getId(), 17l, WmsOtherOutBoundInvNoticeOmsStatus.OTHER_OUTBOUND);
            obj.put(SUCCESS, SUCCESS);
        } catch (BusinessException e) {
            log.error("", e);
            obj.put(ERROR, ERROR);
            obj.put("msg", catchBusinessException(e));
        } catch (Exception e) {
            log.error("", e);
            obj.put(ERROR, ERROR);
            obj.put("msg", catchException(e));
        }
        request.put(JSON, obj);
        return JSON;
    }

    /**
     * 执行
     * 
     * @return
     * @throws Exception
     */
    public String predefinedOutExecution() throws Exception {
        JSONObject obj = new JSONObject();
        try {
            int Status = wareHouseManager.predefinedOutExecution(sta.getId(), userDetails.getUser());
			wareHouseManager.updateWmsOtherOutBoundInvNoticeOms(sta.getId(), 10l, WmsOtherOutBoundInvNoticeOmsStatus.OTHER_OUTBOUND); // 执行出库，库存释放记录日志
            obj.put(SUCCESS, SUCCESS);
            obj.put("sta", Status);
            obj.put("id", sta.getId());
        } catch (BusinessException e) {
            log.error("", e);
            obj.put(ERROR, ERROR);
            obj.put("msg", catchBusinessException(e));
        } catch (Exception e) {
            log.error("", e);
            obj.put(ERROR, ERROR);
            obj.put("msg", catchException(e));
        }
        request.put(JSON, obj);
        return JSON;
    }


    /**
     * 包材导入
     * 
     * @return
     * @throws Exception -----------------------------------------------------
     */
    public String packagingMaterialsImport() throws Exception {
        String msg = SUCCESS;
        request.put("msg", msg);
        if (sta == null || file == null) {
            msg = (sta == null ? "sta.id must not be null" : "") + (file == null ? " The upload file must not be null" : "");
            log.error(msg);
            request.put("msg", msg);
        } else {
            try {
                ReadStatus rs = excelReadManager.packagingMaterialsImport(sta.getId(), file, userDetails.getUser());
                if (rs.getStatus() > ReadStatus.STATUS_SUCCESS) {
                    request.put("msg", "");
                    List<String> list = ExcelKit.getInstance().getReadStatusMessages(rs, this.getLocale());
                    request.put("msg", list);
                } else if (rs.getStatus() < ReadStatus.STATUS_SUCCESS) {
                    request.put("msg", "");
                    List<String> list = new ArrayList<String>();
                    for (Exception ex : rs.getExceptions()) {
                        if (ex instanceof BusinessException) {
                            BusinessException be = (BusinessException) ex;
                            list.add(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode(), be.getArgs()) + Constants.HTML_LINE_BREAK);
                        }
                    }
                    request.put("msg", list);
                }
            } catch (BusinessException e) {
                request.put("request", "error");
                log.error("", e);
                StringBuffer sb = new StringBuffer();
                if (e.getErrorCode() > 0) {
                    sb.append(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
                }
                BusinessException be = e;
                while ((be = be.getLinkedException()) != null) {
                    sb.append(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode(), be.getArgs()));
                }
                sb.append(catchBusinessException(e));
                log.error(e.getMessage());
                request.put("msg", sb.toString());
            } catch (Exception e) {
                request.put("request", "error");
                log.error("", e);
                log.error(e.getMessage());
                request.put("msg", e.getMessage());
            }
        }
        return SUCCESS;
    }



    public StockTransApplicationCommand getSta() {
        return sta;
    }

    public void setSta(StockTransApplicationCommand sta) {
        this.sta = sta;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    private String businessExceptionPost(BusinessException e) {
        if (ErrorCode.ERROR_NOT_SPECIFIED == e.getErrorCode()) {
            BusinessException[] errors = (BusinessException[]) e.getArgs();
            StringBuilder sb = new StringBuilder();
            for (BusinessException be : errors) {
                sb.append(getMessage(ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode(), be.getArgs()) + Constants.HTML_LINE_BREAK);
            }
            return sb.toString();
        } else {
            return getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs());
        }
    }

    private String catchBusinessException(BusinessException e) throws Exception {
        String error = businessExceptionPost(e);
        log.error(error);
        return error;
    }

    private String catchException(Exception e) throws Exception {
        log.error(e.getMessage());
        return e.getMessage();
    }
}
