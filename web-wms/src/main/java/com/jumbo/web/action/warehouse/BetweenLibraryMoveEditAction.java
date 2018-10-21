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
import com.jumbo.manager.ExcelExportManager;
import com.jumbo.util.FormatUtil;
import com.jumbo.util.JsonUtil;
import com.jumbo.util.StringUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.web.manager.print.PrintManager;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.DropDownListManager;
import com.jumbo.wms.manager.print.PrintCustomizeManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerCancel;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExe;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExecute;
import com.jumbo.wms.manager.warehouse.excel.ExcelReadManager;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.print.PrintCustomize;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.warehouse.BetweenLabaryMoveCommand;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.StockTransVoucher;

public class BetweenLibraryMoveEditAction extends BaseJQGridProfileAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = -8144325317376923074L;

    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private WareHouseManagerExe wareHouseManagerExe;
    @Autowired
    private WareHouseManagerCancel wareHouseManagerCancel;
    @Autowired
    private WareHouseManagerExecute wareHouseManagerExecute;
    @Autowired
    private DropDownListManager dropDownManager;
    @Autowired
    private ExcelReadManager excelReadManager;
    @Autowired
    private ExcelExportManager excelExportManager;

    @Autowired
    private PrintManager printManager;
    @Autowired
    private PrintCustomizeManager printCustomizeManager;

    private List<BetweenLabaryMoveCommand> staLineCmd = new ArrayList<BetweenLabaryMoveCommand>();

    /**
     * 仓库下拉列表
     */
    private List<OperationUnit> whList;
    /**
     * 
     */
    private List<InventoryStatus> invList;
    /**
     * 作业单状态列表
     */
    private List<ChooseOption> staStatusList;
    /**
     * 库间移动作业单查询参数
     */
    private StockTransApplication sta;

    private StockTransVoucher stv;
    /**
     * 批次上架模式
     */
    private List<ChooseOption> inMode;
    private File file;
    private Long staId;

    /**
     * 店铺
     */
    private List<BiChannel> shops;

    private BetweenLabaryMoveCommand betwenMoveCmd;

    private List<InventoryStatus> invStatusList;

    public String intBetweenLibraryEdit() {
        // shops = baseinfoManager.getCompanyShop();
        invList = dropDownManager.findInvStatusListByCompany(userDetails.getCurrentOu().getId(), null);
        whList = dropDownManager.findWarehouseOuListByComOuId(userDetails.getCurrentOu().getId(), null);
        return SUCCESS;
    }

    /**
     * 仓库 库间移出
     * 
     * @return
     */
    public String externalOutOfEntry() {
        invStatusList = wareHouseManager.findInvStatusByOuid(userDetails.getCurrentOu().getParentUnit().getParentUnit().getId());
        return SUCCESS;
    }

    /**
     * staLine.jqgrid
     * 
     * @return
     */
    public String betweenMovestvLineListJson() {
        if (sta != null) {
            setTableConfig();
            request.put(JSON, toJson(wareHouseManager.findStvLinesListByStaId(sta.getId())));
        }
        return JSON;
    }

    /***
     * 库存占用打印 - 拣货使用
     * 
     * @return
     */
    public String PrintInventoryOccupay() {
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



    /***
     * 移库出库送货信息打印 - 送货使用
     * 
     * @return
     */
    public String printOutBoundSendInfo() {
        JasperPrint jp;
        try {
            sta = wareHouseManager.findSta(sta.getId());
            PrintCustomize pc = printCustomizeManager.getPrintCustomizeByOwnerAndType(sta.getOwner(), 10);
            if (pc != null) {
                if (!StringUtil.isEmpty(pc.getDataType())) {
                    jp = printManager.printReturnWarehousePackingInfo(sta.getId(), userDetails.getCurrentOu().getId(), pc);
                    return printObject(jp);
                } else {
                    jp = printManager.printOutBoundSendInfo(sta.getId(), userDetails.getCurrentOu().getId(), pc);
                    return printObject(jp);
                }
            } else {
                jp = printManager.printOutBoundSendInfo(sta.getId(), userDetails.getCurrentOu().getId(), null);
                return printObject(jp);
            }
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
     * 仓库 库间移出--库存占用
     * 
     * @return
     * @throws JSONException
     */
    public String transitCrossStaOccupaid() throws JSONException {
        List<String> errorMsg = new ArrayList<String>();
        JSONObject result = new JSONObject();
        try {
            wareHouseManagerExe.transitCrossStaOccupaid(betwenMoveCmd.getStaId(), betwenMoveCmd.getInvStatusId(), userDetails.getUser().getId());
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
     * 仓库 库间移出 确认 移库
     * 
     * @return
     * @throws JSONException
     */
    public String transitCrossStaOutBound() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            wareHouseManagerExecute.transitCrossStaOutBound(betwenMoveCmd.getStaId(), userDetails.getUser().getId());
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
     * 仓库 库间移出 释放库存占用
     * 
     * @return
     * @throws JSONException
     */
    public String releaseBetweenLibaryInventory() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            wareHouseManager.releaseLibaryInventoryByStaId(betwenMoveCmd.getStaId(), userDetails.getUser().getId());
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
     * sta.jqgrid
     * 
     * @return
     */
    public String staOutOfListJson() {
        if (sta != null) {
            setTableConfig();
            if (StockTransApplicationType.TRANSIT_CROSS.equals(sta.getType())) {
                request.put(JSON, toJson(wareHouseManager.findOutOfCossStaNotFinishedListByType(sta.getType(), userDetails.getCurrentOu(), tableConfig.getSorts())));
            }

        }
        return JSON;
    }

    /**
     * 仓库 库间移入
     * 
     * @return
     */
    public String externalTransEntry() {
        inMode = dropDownManager.findStaInboundStoreModeChooseOptionList();
        return SUCCESS;
    }

    /**
     * 库间移动作业单申请导入
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    public String importBetweenLibraryMoveApplication() {
        List<String> errorMsg = new ArrayList<String>();
        if (file == null) {
            return ERROR;
        }
        try {
            Map<String, Object> map = excelReadManager.importBetweenLibraryMoveApplication(file, betwenMoveCmd.getStartWarehouseId());

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
     * 创建库间移动申请作业单
     * 
     * @return
     * @throws Exception
     */
    public String createBetweenLibaryMoveSta() throws Exception {
        List<String> errorMsg = new ArrayList<String>();
        JSONObject result = new JSONObject();
        try {
            wareHouseManagerExecute.createBetweenMoveSta(betwenMoveCmd, userDetails.getUser(), userDetails.getCurrentOu(), staLineCmd);
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

    public String staTransitCrossQueryEntry() {
        whList = dropDownManager.findWarehouseOuListByComOuId(userDetails.getCurrentOu().getId(), null);
        staStatusList = dropDownManager.findStaStatusChooseOptionList();
        return SUCCESS;
    }

    /**
     * 取消
     * 
     * @return
     * @throws JSONException
     */
    public String cancelTransitCrossSta() throws JSONException {
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
     * 入库 收货
     * 
     * @return
     * @throws JSONException
     */
    public String transCrossInboundReceive() throws JSONException {
        try {
            StockTransVoucher stv = wareHouseManager.createTransCrossInboundStv(staId, userDetails.getUser().getId(), this.stv, userDetails.getCurrentOu().getId());
            request.put(JSON, new JSONObject().put("stvId", stv.getId()));
        } catch (BusinessException e) {
            String msg = this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs());
            request.put(JSON, new JSONObject().put("msg", msg));
        }
        return JSON;
    }

    public String staTransitCrossQuery() {
        setTableConfig();
        if (sta == null) {
            sta = new StockTransApplication();
        }
        if (sta != null) {
            sta.setCreateTime(FormatUtil.getDate(sta.getCreateTime1()));
            sta.setFinishTime(FormatUtil.getDate(sta.getFinishTime1()));
        }
        sta.setId(userDetails.getCurrentOu().getId());
        sta.setType(StockTransApplicationType.TRANSIT_CROSS);
        request.put(JSON, toJson(wareHouseManager.findStaForTransitCrossByModel(tableConfig.getStart(), tableConfig.getPageSize(), sta, tableConfig.getSorts())));
        return JSON;
    }

    /***
     * 出库sn号导入
     * 
     * @return
     */
    public String outboundSnImport() {
        List<String> errorMsg = new ArrayList<String>();
        if (file == null) {
            return ERROR;
        } else {
            try {
                ReadStatus rs = excelReadManager.outboundSnImportByStv(file, staId, userDetails.getCurrentOu().getId());
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

    public String findIsImportSn() throws JSONException {
        JSONObject result = new JSONObject();
        boolean isImport = wareHouseManager.isImportSnByStv(staId);
        result.put("result", SUCCESS);
        result.put("msg", isImport);
        request.put(JSON, result);
        return JSON;
    }


    /**
     * 移动入库 模版导出
     * 
     * @throws Exception
     */
    public void externalInExport() throws Exception {
        sta = wareHouseManager.findStaById(sta.getId());
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=STA_" + sta.getCode() + Constants.EXCEL_XLS);
        ServletOutputStream outs = null;
        try {
            outs = response.getOutputStream();
            excelExportManager.externalInExport(outs, sta);
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
     * 移动入库 导入
     * 
     * @return
     * @throws Exception -----------------------------------------------------
     */
    public String externalInImport() throws Exception {
        String msg = SUCCESS;
        request.put("msg", msg);
        if (sta == null || file == null) {
            msg = (sta == null ? "sta.id must not be null" : "") + (file == null ? " The upload file must not be null" : "");
            log.error(msg);
            request.put("msg", msg);
        } else {
            try {
                ReadStatus rs = excelReadManager.externalInImport(sta.getId(), file, userDetails.getUser(), null);
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

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public List<OperationUnit> getWhList() {
        return whList;
    }

    public void setWhList(List<OperationUnit> whList) {
        this.whList = whList;
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

    public List<ChooseOption> getInMode() {
        return inMode;
    }

    public void setInMode(List<ChooseOption> inMode) {
        this.inMode = inMode;
    }

    public List<BetweenLabaryMoveCommand> getStaLineCmd() {
        return staLineCmd;
    }

    public void setStaLineCmd(List<BetweenLabaryMoveCommand> staLineCmd) {
        this.staLineCmd = staLineCmd;
    }

    public BetweenLabaryMoveCommand getBetwenMoveCmd() {
        return betwenMoveCmd;
    }

    public void setBetwenMoveCmd(BetweenLabaryMoveCommand betwenMoveCmd) {
        this.betwenMoveCmd = betwenMoveCmd;
    }

    public List<InventoryStatus> getInvStatusList() {
        return invStatusList;
    }

    public void setInvStatusList(List<InventoryStatus> invStatusList) {
        this.invStatusList = invStatusList;
    }

    public List<BiChannel> getShops() {
        return shops;
    }

    public void setShops(List<BiChannel> shops) {
        this.shops = shops;
    }

    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

    public StockTransVoucher getStv() {
        return stv;
    }

    public void setStv(StockTransVoucher stv) {
        this.stv = stv;
    }

    public List<InventoryStatus> getInvList() {
        return invList;
    }

    public void setInvList(List<InventoryStatus> invList) {
        this.invList = invList;
    }

}
