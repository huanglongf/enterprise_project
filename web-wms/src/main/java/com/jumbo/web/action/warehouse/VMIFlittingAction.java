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
import java.util.Map;

import javax.servlet.ServletOutputStream;

import loxia.dao.Pagination;
import loxia.support.excel.ExcelKit;
import loxia.support.excel.ReadStatus;
import loxia.support.jasperreport.JasperPrintFailureException;
import loxia.support.jasperreport.JasperReportNotFoundException;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.DropDownListManager;
import com.jumbo.manager.ExcelExportManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerCancel;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExe;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExecute;
import com.jumbo.wms.manager.warehouse.excel.ExcelReadManager;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.warehouse.BetweenLabaryMoveCommand;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.StockTransVoucher;
import com.jumbo.wms.model.warehouse.VMIFlittingCommand;
import com.jumbo.util.FormatUtil;
import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.web.manager.print.PrintManager;

public class VMIFlittingAction extends BaseJQGridProfileAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = 5308813117356821238L;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private WareHouseManagerCancel wareHouseManagerCancel;
    @Autowired
    private WareHouseManagerExecute wareHouseManagerExecute;
    @Autowired
    private WareHouseManagerExe wareHouseManagerExe;
    @Autowired
    private DropDownListManager dropDownManager;
    @Autowired
    private ExcelReadManager excelReadManager;
    @Autowired
    private PrintManager printManager;
    @Autowired
    private ExcelExportManager excelExportManager;

    private File file;
    private VMIFlittingCommand comd;
    private StockTransApplication sta;
    private StockTransVoucher stv;
    private List<VMIFlittingCommand> staLineCmd = new ArrayList<VMIFlittingCommand>();
    private List<OperationUnit> whList;
    private List<InventoryStatus> invStatusList;
    // 作业单状态列表
    private List<ChooseOption> staStatusList;

    private Long staid;
    private Integer ptType;

    public String initToVMIFlittingCreate() {
        whList = dropDownManager.findWarehouseOuListByComOuId(userDetails.getCurrentOu().getId(), null);
        invStatusList = wareHouseManager.findInvStatusByOuid(userDetails.getCurrentOu().getId());
        return SUCCESS;
    }

    public String initToVMIFlittingQuery() {
        staStatusList = dropDownManager.findStaStatusChooseOptionList();
        whList = dropDownManager.findWarehouseOuListByComOuId(userDetails.getCurrentOu().getId(), null);
        return SUCCESS;
    }

    public String initToVMIFlittingEnter() {
        return SUCCESS;
    }

    public String initToVMIFlittingOut() {
        return SUCCESS;
    }

    /***
     * vmi 调拨 - 作业单解冻
     * 
     * @return
     */
    public String unfreezeEntry() {
        return SUCCESS;
    }

    /**
     * vmi 调拨 - 查询所有作业单 sta
     * 
     * @return
     */
    public String findVMIFlittingStas() {
        setTableConfig();
        request.put(
                JSON,
                toJson(wareHouseManager.findVMIFlittingStas(sta, tableConfig.getStart(), tableConfig.getPageSize(), this.userDetails.getCurrentOu().getId(), StockTransApplicationType.VMI_INBOUND_CONSIGNMENT.getValue(),
                        StockTransApplicationStatus.FROZEN.getValue(), tableConfig.getSorts())));
        return JSON;
    }

    /***
     * details
     * 
     * @return
     */
    public String findVmiUnfreezeDetails() {
        setTableConfig();
        request.put(JSON, toJson(wareHouseManager.findVmiUnfreezeDetails(staid)));
        return JSON;
    }

    /**
     * 执行解冻
     * 
     * @return
     * @throws Exception
     */

    public String unfreezeByStaid() throws Exception {
        JSONObject result = new JSONObject();
        try {
        	wareHouseManagerExe.unfreezeByStaid(sta, userDetails.getUser().getId());
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            result.put("result", ERROR);
            result.put("message", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 创建库间移动申请作业单
     * 
     * @return
     * @throws Exception
     */
    public String createVMIFlittingSta() throws Exception {

        List<String> errorMsg = new ArrayList<String>();
        JSONObject result = new JSONObject();
        try {
            StockTransApplication stacode = new StockTransApplication();
            stacode.setType(StockTransApplicationType.SAME_COMPANY_TRANSFER);
            wareHouseManager.createVMIFlittingSta(stacode, comd, userDetails.getUser(), userDetails.getCurrentOu(), staLineCmd);
            result.put("msg", SUCCESS);
        } catch (BusinessException e) {
            BusinessException bex = e;
            do {
                String msg = this.getMessage(ErrorCode.BUSINESS_EXCEPTION + bex.getErrorCode(), bex.getArgs());
                log.error(msg);
                errorMsg.add(msg);
                bex = bex.getLinkedException();
            } while (bex != null);
            result.put("msg", ERROR);
            result.put("errMsg", JsonUtil.collection2json(errorMsg));
        } catch (Exception e) {
            log.error("", e);
            result.put("msg", e.getCause() + " " + e.getMessage());
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * VMI调拨作业单申请导入
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    public String importVMIFlittingApplication() {
        List<String> errorMsg = new ArrayList<String>();
        if (file == null) {
            return ERROR;
        }
        try {
            Map<String, Object> map = excelReadManager.importVMIFlittingApplication(file, comd.getStartWarehouseId(), comd.getOwner());
            ReadStatus rs = (ReadStatus) map.get(Constants.IMPORT_EXL_ERROR);
            if (rs.getStatus() == ReadStatus.STATUS_SUCCESS) {
                request.put("result", SUCCESS);
                request.put(Constants.IMPORT_EXL_RESULT, JsonUtil.collection2json((List<BetweenLabaryMoveCommand>) map.get(Constants.IMPORT_EXL_RESULT)).toString());
            } else if (rs.getStatus() > ReadStatus.STATUS_SUCCESS) {
                request.put("result", ERROR);
                List<String> list = ExcelKit.getInstance().getReadStatusMessages(rs, this.getLocale());
                for (String s : list) {
                    errorMsg.add(s);
                }
            } else if (rs.getStatus() < ReadStatus.STATUS_SUCCESS) {
                request.put("result", ERROR);
                for (Exception ex : rs.getExceptions()) {
                    if (ex instanceof BusinessException) {
                        BusinessException bex = (BusinessException) ex;
                        String msg = this.getMessage(ErrorCode.BUSINESS_EXCEPTION + bex.getErrorCode(), bex.getArgs());
                        errorMsg.add(msg);
                        log.error(msg);
                    }
                }
            }
        } catch (BusinessException e) {
            request.put("result", ERROR);
            String msg = this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs());
            errorMsg.add(msg);
            log.error(msg);
        }
        request.put("message", JsonUtil.collection2json(errorMsg));
        return SUCCESS;
    }

    /**
     * VMI调拨查寻
     * 
     * @return
     */
    public String queryVMIFlittingSta() {
        setTableConfig();
        if (sta == null) {
            sta = new StockTransApplication();
        }
        if (sta != null) {
            sta.setCreateTime(FormatUtil.getDate(sta.getCreateTime1()));
            sta.setFinishTime(FormatUtil.getDate(sta.getFinishTime1()));
        }
        sta.setId(userDetails.getCurrentOu().getId());
        sta.setType(StockTransApplicationType.SAME_COMPANY_TRANSFER);
        Pagination<StockTransApplicationCommand> t = wareHouseManager.findStaForTransitCrossByModel(tableConfig.getStart(), tableConfig.getPageSize(), sta, tableConfig.getSorts());
        request.put(JSON, toJson(t));
        return JSON;
    }

    /**
     * 取消
     * 
     * @return
     * @throws JSONException
     */
    public String cancelVMIFlittingSta() throws JSONException {
        JSONObject result = new JSONObject();
        try {
        	wareHouseManagerCancel.cancelTransitCrossSta(this.userDetails.getUser().getId(), sta.getId());
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
     * 根据作业单类型查寻数据
     * 
     * @return
     */
    public String queryVMIFlittingOut() {
        if (sta != null) {
            setTableConfig();
            if (StockTransApplicationType.SAME_COMPANY_TRANSFER.equals(sta.getType())) {
                request.put(JSON, toJson(wareHouseManager.findOutOfCossStaNotFinishedListByType(sta.getType(), userDetails.getCurrentOu(), tableConfig.getSorts())));
            }
        }
        return JSON;
    }

    /**
     * 仓库 VMI调拨出库--库存占用
     * 
     * @return
     * @throws JSONException
     */
    public String occupiedVMIFlitting() throws JSONException {
        List<String> errorMsg = new ArrayList<String>();
        JSONObject result = new JSONObject();
        try {
            wareHouseManagerExe.transitCrossStaOccupaid(comd.getStaId(), null, userDetails.getUser().getId());
            result.put("msg", SUCCESS);
        } catch (BusinessException e) {
            BusinessException bex = e;
            do {
                String msg = this.getMessage(ErrorCode.BUSINESS_EXCEPTION + bex.getErrorCode(), bex.getArgs());
                log.error(msg);
                errorMsg.add(msg);
                bex = bex.getLinkedException();
            } while (bex != null);
            result.put("msg", ERROR);
            result.put("errMsg", JsonUtil.collection2json(errorMsg));
        } catch (Exception e) {
            log.error("", e);
            result.put("msg", e.getCause() + " " + e.getMessage());
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 查寻VMI调拨出库stvLine
     * 
     * @return
     */
    public String queryVMIFlittingStvLine() {
        if (sta != null) {
            setTableConfig();
            request.put(JSON, toJson(wareHouseManager.findStvLinesListByStaId(sta.getId())));
        }
        return JSON;
    }

    /***
     * 出库SN号导入
     * 
     * @return
     */
    public String importVMIFlittingOutBoundSn() {
        List<String> errorMsg = new ArrayList<String>();
        if (file == null) {
            return ERROR;
        } else {
            try {
                ReadStatus rs = excelReadManager.outboundSnImportByStv(file, sta.getId(), userDetails.getCurrentOu().getId());
                request.put("result", ERROR);
                if (rs.getStatus() == ReadStatus.STATUS_SUCCESS) {
                    request.put("result", SUCCESS);
                }
                errorMsg.addAll(getExcelImportErrorMsg(rs));
            } catch (BusinessException e) {
                request.put("result", ERROR);
                String msg = this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs());
                errorMsg.add(msg);
            }
            request.put("message", JsonUtil.collection2json(errorMsg));
        }
        return SUCCESS;
    }

    /**
     * 查寻 sn号是否导入
     * 
     * @return
     * @throws JSONException
     */
    public String findIsImportSn() throws JSONException {
        JSONObject result = new JSONObject();
        boolean isImport = wareHouseManager.isImportSnByStv(sta.getId());
        result.put("result", SUCCESS);
        result.put("msg", isImport);
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 仓库 VMI调拨出库 确认 移库
     * 
     * @return
     * @throws JSONException
     */
    public String outBoundVMIFlitting() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            wareHouseManagerExecute.transitCrossStaOutBound(sta.getId(), userDetails.getUser().getId());
            result.put("msg", SUCCESS);
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
     * 仓库 VMI调拨出库取消 释放库存占用
     * 
     * @return
     * @throws JSONException
     */
    public String releaseVMIFlittingInventory() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            wareHouseManager.releaseLibaryInventoryByStaId(sta.getId(), userDetails.getUser().getId());
            result.put("msg", SUCCESS);
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

    /***
     * 库存占用打印
     * 
     * @return
     */
    public String printVMIFlittingInventory() {
        JasperPrint jp;
        try {
            jp = printManager.PrintInventoryOccupay(sta.getId(), userDetails.getCurrentOu().getId(), false);
            return printObject(jp);
        } catch (JasperPrintFailureException e) {
            log.error("", e);
        } catch (JRException e) {
            log.error("", e);
        } catch (JasperReportNotFoundException e) {
            log.error("", e);
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    /**
     * 查寻vmi入库书库明细
     * 
     * @return
     */
    public String queryVMIFlittingEnterLine() {
        if (sta != null) {
            setTableConfig();
            request.put(JSON, toJson(wareHouseManager.findVMIFlittingEnterLine(sta.getId(), tableConfig.getSorts())));
        }
        return JSON;
    }

    public void externalOutOutport() throws Exception {
        sta = wareHouseManager.findStaById(sta.getId());
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=STA_" + sta.getCode() + Constants.EXCEL_XLS);
        ServletOutputStream outs = null;
        try {
            outs = response.getOutputStream();
            excelExportManager.externalOutOutport(outs, sta);
        } catch (Exception e) {
            log.error("", e);
        } finally {
            if (outs != null) {
                outs.flush();
                outs.close();
            }
        }
    }

    public List<OperationUnit> getWhList() {
        return whList;
    }

    public void setWhList(List<OperationUnit> whList) {
        this.whList = whList;
    }

    public List<InventoryStatus> getInvStatusList() {
        return invStatusList;
    }

    public void setInvStatusList(List<InventoryStatus> invStatusList) {
        this.invStatusList = invStatusList;
    }

    public VMIFlittingCommand getComd() {
        return comd;
    }

    public void setComd(VMIFlittingCommand comd) {
        this.comd = comd;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public List<VMIFlittingCommand> getStaLineCmd() {
        return staLineCmd;
    }

    public void setStaLineCmd(List<VMIFlittingCommand> staLineCmd) {
        this.staLineCmd = staLineCmd;
    }

    public List<ChooseOption> getStaStatusList() {
        return staStatusList;
    }

    public void setStaStatusList(List<ChooseOption> staStatusList) {
        this.staStatusList = staStatusList;
    }

    public StockTransApplication getSta() {
        return sta;
    }

    public void setSta(StockTransApplication sta) {
        this.sta = sta;
    }

    public StockTransVoucher getStv() {
        return stv;
    }

    public void setStv(StockTransVoucher stv) {
        this.stv = stv;
    }

    public Long getStaid() {
        return staid;
    }

    public void setStaid(Long staid) {
        this.staid = staid;
    }

    public Integer getPtType() {
        return ptType;
    }

    public void setPtType(Integer ptType) {
        this.ptType = ptType;
    }
}
