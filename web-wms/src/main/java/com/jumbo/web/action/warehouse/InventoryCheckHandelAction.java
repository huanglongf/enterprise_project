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
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.Constants;
import com.jumbo.manager.ExcelExportManager;
import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerCancel;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExe;
import com.jumbo.wms.manager.warehouse.WareHouseManagerQuery;
import com.jumbo.wms.manager.warehouse.excel.ExcelReadManager;
import com.jumbo.wms.model.command.InventoryCheckMoveLineCommand;
import com.jumbo.wms.model.warehouse.InventoryCheck;
import com.jumbo.wms.model.warehouse.InventoryCheckDifferenceLineCommand;
import com.jumbo.wms.model.warehouse.InventoryCheckLineCommand;
import com.jumbo.wms.model.warehouse.InventoryCheckType;

public class InventoryCheckHandelAction extends BaseJQGridProfileAction {

    private static final long serialVersionUID = -2378309681628400263L;
    @Autowired
    private WareHouseManager warehouseManager;
    @Autowired
    private WareHouseManagerExe warehouseManagerExe;
    @Autowired
    private WareHouseManagerQuery wareHouseManagerQuery;
    @Autowired
    private WareHouseManagerCancel wareHouseManagerCancel;
    @Autowired
    private ExcelReadManager excelReadManager;
    @Autowired
    private ExcelExportManager excelExportManager;

    @Autowired
    private WareHouseManagerExe wareHouseManagerExe;

    private List<InventoryCheckLineCommand> icclist = new ArrayList<InventoryCheckLineCommand>();
    private Long invcheckid;
    private File file;
    private InventoryCheck invcheck;
    private String code;
    private String operatorType;

    public String getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(String operatorType) {
        this.operatorType = operatorType;
    }

    public String invCKHandelEntry() {
        return SUCCESS;
    }

    /**
     * 盘点批列表
     * 
     * @return
     */
    public String findInventoryCheckList() {
        setTableConfig();
        List<InventoryCheck> list = warehouseManager.findInventoryCheckListByTypes(this.userDetails.getCurrentOu().getId(), this.tableConfig.getSorts());
        request.put(JSON, toJson(list));
        return JSON;
    }

    /**
     * 盘点批列表（盘点库存已确认）
     * 
     * @return
     */
    public String findInventoryCheckListManager() {
        setTableConfig();
        List<InventoryCheck> list = warehouseManager.findInventoryCheckListManager(userDetails.getCurrentOu().getId(), invcheck, tableConfig.getSorts());
        request.put(JSON, toJson(list));
        return JSON;
    }

    /**
     * 盘点结果列表(差异表)
     * 
     * @return
     */
    public String findInvCheckDiffLine() {
        setTableConfig();
        Pagination<InventoryCheckDifferenceLineCommand> list = warehouseManager.findInvCkDifLineByInvCkId(tableConfig.getStart(), tableConfig.getPageSize(), invcheckid, tableConfig.getSorts());
        request.put(JSON, toJson(list));
        return JSON;
    }

    /**
     * 盘点结果列表(差异表)
     * 
     * @return
     */
    public String findInvCheckMoveLine() {
        setTableConfig();
        Pagination<InventoryCheckMoveLineCommand> list = wareHouseManagerQuery.findInvCheckMoveLineId(tableConfig.getStart(), tableConfig.getPageSize(), invcheckid, tableConfig.getSorts());
        request.put(JSON, toJson(list));
        return JSON;
    }


    /**
     * 
     * @return
     */

    public String findInvCheckCountNoLocation() {
        setTableConfig();
        Pagination<InventoryCheckDifferenceLineCommand> list = warehouseManager.findInvCheckCountNoLocation(tableConfig.getStart(), tableConfig.getPageSize(), invcheckid, tableConfig.getSorts());
        request.put(JSON, toJson(list));
        return JSON;
    }

    /**
     * 导入
     * 
     * @return
     */
    public String importInvCheck() {
        List<String> errorMsg = new ArrayList<String>();
        if (file == null) {
            return ERROR;
        }
        try {
            Long whOUID = this.userDetails.getCurrentOu().getId();
            ReadStatus rs = excelReadManager.inventoryCheckImport(file, invcheckid, userDetails.getCurrentOu().getId(), whOUID, this.userDetails.getUser().getId());
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
        }
        request.put("message", JsonUtil.collection2json(errorMsg));
        return SUCCESS;
    }

    /**
     * 取消
     * 
     * @return
     */
    public String cancelInventoryCheck() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            wareHouseManagerCancel.cancelInventoryCheck(invcheckid, this.userDetails.getUser().getId());
            result.put("rs", SUCCESS);
        } catch (BusinessException e) {
            log.error(getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
            result.put("msg", getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        } catch (Exception e) {
            log.error("", e);
            result.put("msg", e.getCause() + " " + e.getMessage());
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 变更为差异未处理
     * 
     * @return
     */
    public String cancelInventoryCheckManager() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            wareHouseManagerCancel.cancelInventoryCheckManager(invcheckid, this.userDetails.getUser());
            result.put("rs", SUCCESS);
        } catch (BusinessException e) {
            log.error(getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
            result.put("msg", getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        } catch (Exception e) {
            log.error("", e);
            result.put("msg", e.getCause() + " " + e.getMessage());
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 差异确认
     * 
     * @return
     * @throws JSONException
     */
    public String confirmInventoryCheck() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            warehouseManagerExe.confirmInventoryCheck(invcheck.getConfirmUser(), invcheck.getId());
            result.put("rs", SUCCESS);
        } catch (BusinessException e) {
            log.error(getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
            result.put("msg", getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        } catch (Exception e) {
            log.error("", e);
            result.put("msg", e.getCause() + " " + e.getMessage());
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 修改盘点表状态为 盘点库存已确认
     * 
     * @return
     * @throws JSONException
     */
    public String changeStatus() throws JSONException {
        JSONObject result = new JSONObject();
        InventoryCheck inventoryCheck = warehouseManager.findInventoryCheckByCode(code);
        try {
            if (inventoryCheck.getType().equals(InventoryCheckType.NORMAL) || inventoryCheck.getType().equals(InventoryCheckType.DAILY)) {
                wareHouseManagerExe.invCheckDetermine(code, userDetails.getUser());
                result.put("rs", SUCCESS);
            }
        } catch (BusinessException e) {
            result.put("rs", "error");
            result.put("msg", e.getMessage());
        } catch (Exception e) {
            result.put("rs", "error");
            // e.printStackTrace();
            if (log.isErrorEnabled()) {
                log.error("changeStatus error:" + code, e);
            };
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 仓库经理确认调用OMS接口
     * 
     * @return
     * @throws JSONException
     */
    public String managerCheck() throws JSONException {
        JSONObject result = new JSONObject();
        InventoryCheck inventoryCheck = warehouseManager.findInventoryCheckByCode(code);
        try {
            // 是否有无店铺的
            Long checknumber = warehouseManager.managerchecknumber(code);
            if (checknumber == 0) {
                wareHouseManagerExe.managerConfirmCheck(inventoryCheck.getId(), code, userDetails.getUser(), true);
                result.put("rs", SUCCESS);
            } else {
                throw new BusinessException(ErrorCode.INV_CHECK_IS_UNTREATED);
            }
        } catch (BusinessException e) {
            result.put("rs", "error");
            result.put("msg", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        } catch (Exception e) {
            log.debug("", e);
            result.put("rs", "error");
            result.put("msg", "系统错误");
        }
        request.put(JSON, result);
        return JSON;
    }

    public void exportCheckOverage() throws Exception {
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=INV_CHECK_" + code + Constants.EXCEL_XLS);
        ServletOutputStream outs = null;
        try {
            outs = response.getOutputStream();
            excelExportManager.exportCheckOverage(outs, invcheckid, userDetails.getCurrentOu());
        } catch (Exception e) {
            log.error("", e);
        } finally {
            if (outs != null) {
                outs.flush();
                outs.close();
            }
        }
    }

    public String importCheckOverage() {
        List<String> errorMsg = new ArrayList<String>();
        if (file == null) {
            return ERROR;
        }
        try {
            ReadStatus rs = excelReadManager.importCheckOverage(file, invcheckid);
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
        }
        request.put("message", JsonUtil.collection2json(errorMsg));
        return SUCCESS;
    }

    public String invCheckExecute() throws JSONException {
        JSONObject json = new JSONObject();
        try {
            InventoryCheck invcheck = warehouseManager.findInventoryCheckByCode(code);
            warehouseManager.confirmInventoryCheckEbs(userDetails.getUsername(), invcheck.getId(), userDetails.getUser(), true);
            json.put("rs", "success");
        } catch (BusinessException e) {
            json.put("rs", "error");
            String msg = this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs());
            msg += getErrorMessage(e, true);
            json.put("msg", msg);
        } catch (Exception e) {
            log.debug("", e);
            json.put("rs", "error");
            json.put("msg", "系统错误");
        }
        request.put(JSON, json);
        return JSON;
    }

    public List<InventoryCheckLineCommand> getIcclist() {
        return icclist;
    }

    public void setIcclist(List<InventoryCheckLineCommand> icclist) {
        this.icclist = icclist;
    }

    public Long getInvcheckid() {
        return invcheckid;
    }

    public void setInvcheckid(Long invcheckid) {
        this.invcheckid = invcheckid;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public InventoryCheck getInvcheck() {
        return invcheck;
    }

    public void setInvcheck(InventoryCheck invcheck) {
        this.invcheck = invcheck;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
