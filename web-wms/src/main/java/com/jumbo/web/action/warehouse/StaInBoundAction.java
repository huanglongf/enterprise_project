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
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.Constants;
import com.jumbo.manager.ExcelExportManager;
import com.jumbo.util.FormatUtil;
import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.web.manager.MsgToWcsThread;
import com.jumbo.web.manager.print.PrintManager;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.task.pda.PdaTaskManager;
import com.jumbo.wms.manager.warehouse.StarbucksManagerProxy;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExe;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExecute;
import com.jumbo.wms.manager.warehouse.WareHouseManagerQuery;
import com.jumbo.wms.model.automaticEquipment.WcsInterfaceType;
import com.jumbo.wms.model.pda.PdaOrder;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.wms.model.warehouse.StvLineCommand;
import com.jumbo.wms.model.warehouse.WarehouseLocation;

/**
 * 库内移动
 * 
 * @author dly
 * 
 */
public class StaInBoundAction extends BaseJQGridProfileAction implements ServletResponseAware {

    private static final long serialVersionUID = 5029420869862210452L;

    @Autowired
    private WareHouseManagerQuery whQuery;
    @Autowired
    private WareHouseManagerExecute whExecute;
    @Autowired
    private WareHouseManagerExe whExe;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private PrintManager printManager;
    @Autowired
    private ExcelExportManager excelExportManager;
    @Autowired
    private PdaTaskManager pdaTaskManager;
    @Autowired
    private StarbucksManagerProxy starBucksManagerProxy;

    private StockTransApplicationCommand sta;

    private StockTransApplicationCommand staCmd;
    private String createTime;
    private String endCreateTime;
    private String finishTime;
    private String endFinishTime;
    private String fileName;
    private String locationCode;
    private File file;
    private List<Long> stvList;
    private List<StvLineCommand> stvLineList;
    private List<StvLineCommand> stvLineLists;
    private StvLineCommand line;
    private String ids;
    private List<InventoryStatus> invstatus;
    private String skuCode;
    private StaLineCommand staLine;
    private String mainWarehouseName;

    private String type;

    private String silpCode;

    private Long staId;
    
    private Integer cartonNum;


    public StockTransApplicationCommand getStaCmd() {
        return staCmd;
    }

    public void setStaCmd(StockTransApplicationCommand staCmd) {
        this.staCmd = staCmd;
    }

    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }
    
    
    public Integer getCartonNum(){
        return cartonNum;
    }

    public void setCartonNum(Integer cartonNum){
        this.cartonNum = cartonNum;
    }

    public String getMainWarehouseName() {
        return mainWarehouseName;
    }

    public void setMainWarehouseName(String mainWarehouseName) {
        this.mainWarehouseName = mainWarehouseName;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String staInBound() {
        return SUCCESS;
    }

    public String staInBoundTurn() {
        return SUCCESS;
    }

    public String staInBoundHand() {
        return SUCCESS;
    }
    
    /***
     * 入库单纸箱数维护菜单入口
     * @author weiwei.wu@baozun.com
     * @version 2018年8月15日 下午5:58:48
     */
    public String staInBoundCartonNum() {
        return SUCCESS;
    }

    // /////////////////////////////////////////////////////////////////////////////////PDA_START////////////////////////////////
    /**
     * 上架审核
     */
    public String staInBoundShelvesCheck() {
        return SUCCESS;
    }

    /**
     * 查询所有已经核对的商品数据(PDA上架审核)
     * 
     * @return
     * @throws JSONException
     */
    public String findPdaShelvesSta() throws JSONException {// run
        setTableConfig();
        request.put(JSON, toJson(whQuery.findPdaShelvesSta(tableConfig.getStart(), tableConfig.getPageSize(), userDetails.getCurrentOu().getId(), FormatUtil.getDate(createTime), FormatUtil.getDate(endCreateTime), sta, tableConfig.getSorts())));
        return JSON;
    }

    /**
     * PDA上架审核明细
     */
    public String checkPdaShelvesStaLine() throws JSONException {
        setTableConfig();
        request.put(JSON, toJson(whQuery.checkPdaShelvesStaLine(tableConfig.getStart(), tableConfig.getPageSize(), userDetails.getCurrentOu().getId(), FormatUtil.getDate(finishTime), FormatUtil.getDate(endFinishTime), sta, tableConfig.getSorts())));
        return JSON;
    }

    /**
     * 审核上架，更新sta状态，删除缓存
     */
    public String checkShelves() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            whQuery.checkShelves(staId, userDetails.getCurrentOu(), userDetails.getUser());
            result.put("flag", SUCCESS);
        } catch (BusinessException e) {
            result.put("flag", ERROR);
            result.put("msg", e.getMessage());
        }
        request.put(JSON, result);
        return JSON;
    }
 
    
    // ////////////////////////////////////////////////////////////////////////////////PDA_END///////////////////////////////////////
    /**
     * 收货-手动/星巴克
     * 
     * @return
     */
    public String staHandInBoundStarbucks() {
        return SUCCESS;
    }

    /**
     * 手动收货明细（星巴克）
     * 
     * @return
     */
    public String findHandStaLineStarbucksBySta() {
        if (sta != null) {
            setTableConfig();
            request.put(JSON, toJson(wareHouseManager.findHandStaLineStarbucksBySta(sta.getId(), tableConfig.getSorts())));
        }
        return JSON;
    }

    public String procurementreturninbound() {
        return SUCCESS;
    }

    public String procuremeninboundputaway() {
        return SUCCESS;
    }

    /**
     * 验证星巴克
     * 
     * @return
     * @throws JSONException
     */
    public String validationStarbucksInfo() throws JSONException {

        JSONObject result = new JSONObject();

        StaLineCommand pl;
        try {
            pl = starBucksManagerProxy.validationStarbucksInfo(staLine);
            result.put("result", SUCCESS);
            result.put("pl", JsonUtil.obj2json(pl));
        } catch (BusinessException e) {
            result.put("result", ERROR);
            result.put("message", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }

        request.put(JSON, result);
        return JSON;

    }

    /**
     * 获取星巴克星享卡aes加密key
     * 
     * @return
     * @throws JSONException
     */
    public String getStarbucksAESKey() throws JSONException {
        JSONObject result = new JSONObject();
        String pl;
        try {
            pl = starBucksManagerProxy.getStarbucksAESKey();
            result.put("result", SUCCESS);
            result.put("aeskey", pl);
        } catch (BusinessException e) {
            result.put("result", ERROR);
            result.put("message", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 所有入库单 查询
     * 
     * @return
     * @throws JSONException
     */
    public String findInboundSta() throws JSONException {
        setTableConfig();
        Pagination<StockTransApplicationCommand> list =
                whQuery.findInboundSta(tableConfig.getStart(), tableConfig.getPageSize(), userDetails.getCurrentOu().getId(), FormatUtil.getDate(createTime), FormatUtil.getDate(endCreateTime), sta, tableConfig.getSorts());
        request.put(JSON, toJson(list));
        return JSON;
    }
    /**
     * 所有入库单 查询
     * 
     * @return
     * @throws JSONException
     */
    public String findInBoundCartonSta() throws JSONException {
        setTableConfig();
        Pagination<StockTransApplicationCommand> list =
                        whQuery.findInBoundCartonSta(tableConfig.getStart(), tableConfig.getPageSize(), userDetails.getCurrentOu().getId(), FormatUtil.getDate(createTime), FormatUtil.getDate(endCreateTime), sta, tableConfig.getSorts());
        request.put(JSON, toJson(list));
        return JSON;
    }

    /**
     * VMI入库单 查询
     * 
     * @return
     * @throws JSONException
     */
    public String findInboundStaVmi() throws JSONException {
        setTableConfig();
        Pagination<StockTransApplicationCommand> list =
                whQuery.findInboundStaVmi(tableConfig.getStart(), tableConfig.getPageSize(), userDetails.getCurrentOu().getId(), FormatUtil.getDate(createTime), FormatUtil.getDate(endCreateTime), sta, tableConfig.getSorts());
        request.put(JSON, toJson(list));
        return JSON;
    }

    /**
     * 所有入库单(部分完成 完成) 收货单关闭页面查询
     */
    public String findInboundStaFinish() throws JSONException {
        setTableConfig();
        Pagination<StockTransApplicationCommand> list = whQuery.findInboundStaFinish(tableConfig.getStart(), tableConfig.getPageSize(), userDetails.getCurrentOu().getId(), sta, tableConfig.getSorts());
        request.put(JSON, toJson(list));
        return JSON;
    }

    /**
     * 所有入库单 查询
     * 
     * @return
     * @throws JSONException
     */
    public String findInboundStaByType() {
        setTableConfig();
        Pagination<StockTransApplicationCommand> list =
                whQuery.findInboundStas(tableConfig.getStart(), tableConfig.getPageSize(), userDetails.getCurrentOu().getId(), FormatUtil.getDate(createTime), FormatUtil.getDate(endCreateTime), sta, tableConfig.getSorts());
        request.put(JSON, toJson(list));
        return JSON;
    }

    /**
     * 所有入库单 查询 包括库间移动，和VMI调拨
     * 
     * @return
     * @throws JSONException
     */
    public String findInBoundStaTransAndVMI() throws JSONException {
        setTableConfig();
        Pagination<StockTransApplicationCommand> list =
                whQuery.findInboundSta(tableConfig.getStart(), tableConfig.getPageSize(), userDetails.getCurrentOu().getId(), FormatUtil.getDate(createTime), FormatUtil.getDate(endCreateTime), sta, tableConfig.getSorts());
        request.put(JSON, toJson(list));
        return JSON;
    }

    /**
     * 查找入库单明细
     * 
     * @return
     */
    public String findStaLineBySta() {
        if (sta != null) {
            setTableConfig();
            request.put(JSON, toJson(wareHouseManager.findStaLineListByStaIdByPage(tableConfig.getStart(), tableConfig.getPageSize(), sta.getId(), tableConfig.getSorts())));
        }
        return JSON;
    }

    /**
     * 查找入库单明细(合并同一单的数据)
     * 
     * @return
     */
    public String findStaLineByStaHand() {
        if (sta != null) {
            setTableConfig();
            request.put(JSON, toJson(wareHouseManager.findStaLineListByStaIdHand(sta.getId(), tableConfig.getSorts())));
        }
        return JSON;
    }

    public String findStaLineByStaFinish() {
        if (sta != null) {
            setTableConfig();
            request.put(JSON, toJson(wareHouseManager.findStaLineByStaFinish(tableConfig.getStart(), tableConfig.getPageSize(), sta.getId(), tableConfig.getSorts())));
        }
        return JSON;
    }


    /**
     * 确认数量模版导出
     * 
     * @throws IOException
     * @throws UnsupportedEncodingException
     * @throws Exception
     */
    public void exportInBound() throws UnsupportedEncodingException, IOException {

        response.setContentType("application/octet-stream;charset=ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=" + sta.getCode() + "_INBOUND" + Constants.EXCEL_XLS);
        ServletOutputStream outs = null;
        try {
            outs = response.getOutputStream();
            excelExportManager.exportInBoundNumber(outs, sta);
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
     * 入库确认数量导入
     * 
     * @return
     * @throws Exception
     */
    public String importStaInbound() throws Exception {
        String msg = SUCCESS;
        request.put("msg", msg);
        if (sta == null || file == null) {
            msg = (sta == null ? "sta.id must not be null" : "") + (file == null ? " The upload file must not be null" : "");
            log.error(msg);
            request.put("msg", msg);
        } else {
            try {
                ReadStatus rs = whExecute.importStaInbound(sta.getId(), file, userDetails.getUser());
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
                request.put("msg", "error");
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
                request.put("msg", "error");
                log.error("", e);
                log.error(e.getMessage());
                request.put("msg", e.getMessage());
            }
        }
        return SUCCESS;
    }

    /**
     * 入库确认数量导入
     * 
     * @return
     * @throws Exception
     */
    public String importStaInboundTurn() throws Exception {
        String msg = SUCCESS;
        request.put("msg", msg);
        if (mainWarehouseName == null || file == null) {
            msg = (mainWarehouseName == null ? "mainWarehouseName must not be null" : "") + (file == null ? " The upload file must not be null" : "");
            log.error(msg);
            request.put("msg", msg);
        } else {
            try {
                ReadStatus rs = whExecute.importStaInboundTurn(mainWarehouseName, file, userDetails.getUser(), userDetails.getCurrentOu().getId());
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
                request.put("msg", "error");
                log.error("", e);
                StringBuffer sb = new StringBuffer();
                if (e.getErrorCode() < 0) {
                    sb.append(ErrorCode.BUSINESS_EXCEPTION + e.getMessage());
                }
                BusinessException be = e;
                while ((be = be.getLinkedException()) != null) {
                    sb.append(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode(), be.getArgs()));
                }
                log.error(e.getMessage());
                request.put("msg", sb.toString());
            } catch (Exception e) {
                request.put("msg", "error");
                log.error("", e);
                log.error(e.getMessage());
                request.put("msg", e.getMessage());
            }
        }
        return SUCCESS;
    }



    /**
     * 无条件确认收货
     */
    public String noConditionsRepAction() {

        return SUCCESS;
    }

    /**
     * 无条件确认收货
     */
    public String noChoseinBoundAffirm() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            whExecute.noConinBoundAffirm(sta.getId(), userDetails.getUser());
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            result.put("result", ERROR);
            result.put("message", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 入库确认
     * 
     * @return
     * @throws JSONException
     */
    public String inBoundAffirm() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            whExecute.inBoundAffirm(sta.getId(), userDetails.getUser());
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            result.put("result", ERROR);
            StringBuffer sb = new StringBuffer();
            if (e.getErrorCode() > 0) {
                sb.append(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
            }
            BusinessException be = e;
            while ((be = be.getLinkedException()) != null) {
                sb.append(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode(), be.getArgs()));
            }
            log.error(e.getMessage());
            result.put("message", sb.toString());
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 手动收货入库
     * 
     * @return
     * @throws JSONException
     * @throws CloneNotSupportedException
     */
    public String inBoundAffirmHand() throws JSONException, CloneNotSupportedException {
        JSONObject result = new JSONObject();
        try {
            Map<String, Object> m = whExecute.inBoundAffirmHand(sta.getId(), stvLineList, stvLineLists, userDetails.getUser(), userDetails.getCurrentOu().getId(), true);
            if (m != null) {

                try {
                    Long msgId = (Long) m.get("msgId");
                    if (msgId != null) {
                        MsgToWcsThread wt = new MsgToWcsThread();
                        wt.setMsgId(msgId);
                        wt.setType(WcsInterfaceType.SShouRongQi.getValue());
                        new Thread(wt).start();
                        result.put("autoFlag", true);
                    } else {
                        result.put("autoFlag", false);
                    }
                    String error = (String) m.get("ERROR");
                    result.put("errorMsg", error);
                } catch (Exception e) {
                    result.put("errorMsg", "");
                    log.error("auto inbound error:" + e.getMessage());
                }
            } else {
                result.put("errorMsg", "");
            }
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            result.put("result", ERROR);
            StringBuffer sb = new StringBuffer();
            if (e.getErrorCode() > 0) {
                sb.append(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
            }
            BusinessException be = e;
            while ((be = be.getLinkedException()) != null) {
                sb.append(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode(), be.getArgs()));
            }
            log.error(e.getMessage());
            result.put("message", sb.toString());
        }
        request.put(JSON, result);
        return JSON;
    }


    /**
     * 收货单关闭
     */
    public String closeInBoundFinish() throws JSONException, CloneNotSupportedException {
        JSONObject result = new JSONObject();
        try {
            whExecute.closeInBoundFinish(sta.getId(), userDetails.getUser());
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            result.put("result", ERROR);
            result.put("message", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, result);
        return JSON;
    }


    public String staInBoundConfirm() {
        return SUCCESS;
    }

    /**
     * 所有入库单 查询
     * 
     * @return
     * @throws JSONException
     */
    public String findInboundConfirmSta() throws JSONException {
        setTableConfig();
        request.put(JSON, toJson(whQuery.findInboundConfirmSta(tableConfig.getStart(), tableConfig.getPageSize(), userDetails.getCurrentOu().getId(), FormatUtil.getDate(createTime), FormatUtil.getDate(endCreateTime), sta, tableConfig.getSorts())));
        return JSON;
    }


    /**
     * 查找入库单
     * 
     * @return
     */
    public String findInboundStvLine() {
        if (sta != null) {
            setTableConfig();
            request.put(JSON, toJson(whQuery.findInboundStvLine(tableConfig.getStart(), tableConfig.getPageSize(), sta.getStvId(), tableConfig.getSorts())));
        }
        return JSON;
    }

    /**
     * 查找入库单(入库上架-手动)
     * 
     * @return
     */
    public String findInboundStvLineHand() {
        if (sta != null) {
            try {
                whExecute.locationRecommend(sta.getStvId(), userDetails.getCurrentOu().getId());
            } catch (Exception e) {
                log.error("");
            }
            request.put(JSON, JsonUtil.collection2json(whQuery.findInboundStvLineHand(sta.getStvId())));
        }
        return JSON;
    }

    /**
     * 推荐库位
     * 
     * @return
     * @throws JSONException
     */
    public String locationRecommend() {
        try {
            whExecute.locationRecommend(sta.getStvId(), userDetails.getCurrentOu().getId());
        } catch (Exception e) {
            log.error("推荐库位过程错误");
        }
        return null;
    }

    // 库位编码校验
    public String findLocationByCode() throws JSONException {
        JSONObject obj = new JSONObject();
        try {
            WarehouseLocation loc = wareHouseManager.findLocationByCode(locationCode, userDetails.getCurrentOu().getId());
            if (loc != null) {
                obj.put("result", SUCCESS);
                obj.put("location", JsonUtil.obj2json(loc));
            } else {
                obj.put("result", ERROR);
            }
        } catch (BusinessException e) {
            obj.put("result", ERROR);
            obj.put("message", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, obj);
        return JSON;
    }

    /**
     * 确认核对
     * 
     * @return
     * @throws JSONException
     */
    public String confirmInBoundSta() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            whExecute.confirmInBoundSta(sta.getStvId(), sta.getInvoiceNumber(), sta.getDutyPercentage(), sta.getMiscFeePercentage(), userDetails.getUser());
            result.put("result", SUCCESS);
            try {
                whExecute.locationRecommend(sta.getStvId(), userDetails.getCurrentOu().getId());
            } catch (Exception e) {
                log.error("推荐库位过程错误");
            }
        } catch (BusinessException e) {
            result.put("result", ERROR);
            StringBuffer sb = new StringBuffer();
            if (e.getErrorCode() > 0) {
                sb.append(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
            }
            BusinessException be = e;
            while ((be = be.getLinkedException()) != null) {
                sb.append(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode(), be.getArgs()));
            }
            log.error(e.getMessage());
            result.put("msg", sb.toString());
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 取消核对
     * 
     * @return
     * @throws JSONException
     */
    public String cancelInBoundConfirm() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            whExecute.cancelInBoundConfirm(sta.getStvId(), userDetails.getUser(), sta.getId());
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            result.put("result", ERROR);
            result.put("message", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, result);
        return JSON;
    }


    /**
     * 确认数量模版导出
     * 
     * @throws IOException
     * @throws UnsupportedEncodingException
     * @throws Exception
     */
    public void exportConfirmDiversity() throws UnsupportedEncodingException, IOException {
        response.setContentType("application/octet-stream;charset=ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=" + sta.getCode() + "_CONFIRM" + Constants.EXCEL_XLS);
        ServletOutputStream outs = null;
        try {
            outs = response.getOutputStream();
            excelExportManager.exportConfirmDiversity(outs, sta);
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
     * 商品基础信息维护模版导出
     * 
     * @throws IOException
     * @throws UnsupportedEncodingException
     * @throws Exception
     */
    public void exportSKUinfo() throws UnsupportedEncodingException, IOException {
        response.setContentType("application/octet-stream;charset=ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=SKUBasicInformation" + Constants.EXCEL_XLS);
        ServletOutputStream outs = null;
        try {
            outs = response.getOutputStream();
            excelExportManager.exportSKUinfo(outs, sta);
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
     * 入库确认数量导入
     * 
     * @return
     * @throws Exception
     */
    public String importConfirmDiversity() throws Exception {
        String msg = SUCCESS;
        request.put("msg", msg);
        if (sta == null || file == null) {
            msg = (sta == null ? "sta.id must not be null" : "") + (file == null ? " The upload file must not be null" : "");
            log.error(msg);
            request.put("msg", msg);
        } else {
            try {
                ReadStatus rs = whExecute.importConfirmDiversity(sta.getStvId(), file, userDetails.getUser());
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
                request.put("msg", "error");
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
                request.put("msg", "error");
                log.error("", e);
                log.error(e.getMessage());
                request.put("msg", e.getMessage());
            }
        }
        return SUCCESS;
    }

    /**
     * 上架入库
     * 
     * @return
     */
    public String staInBoundShelves() {
        return SUCCESS;
    }

    /**
     * 虚拟仓收货
     * 
     * @return
     */
    public String virtualReceipt() {
        return SUCCESS;
    }

    /**
     * 查询所有已经核对的商品数据
     * 
     * @return
     * @throws JSONException
     */
    public String findInboundShelvesSta() throws JSONException {
        setTableConfig();
        request.put(JSON, toJson(whQuery.findInboundShelvesSta(tableConfig.getStart(), tableConfig.getPageSize(), userDetails.getCurrentOu().getId(), FormatUtil.getDate(finishTime), FormatUtil.getDate(endFinishTime), sta, tableConfig.getSorts())));
        return JSON;
    }


    /**
     * 模版导出
     * 
     * @throws IOException
     * @throws UnsupportedEncodingException
     * @throws Exception
     */
    public void exportInboundShelves() throws UnsupportedEncodingException, IOException {
        response.setContentType("application/octet-stream;charset=ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=" + sta.getCode() + "_SHELVES" + Constants.EXCEL_XLS);
        ServletOutputStream outs = null;
        try {
            outs = response.getOutputStream();
            excelExportManager.exportInboundShelves(outs, sta);
        } catch (Exception e) {
            log.error("", e);
        } finally {
            if (outs != null) {
                outs.flush();
                outs.close();
            }
        }
    }


    public void exportCRW() throws UnsupportedEncodingException, IOException {
        response.setContentType("application/octet-stream;charset=ISO8859-1");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        response.setHeader("Content-Disposition", "attachment;filename=" + "Nike_CRW_Baozun_PackDetail_" + sdf.format(new Date()) + Constants.EXCEL_XLS);
        ServletOutputStream outs = null;
        try {
            if (staCmd != null) {
                staCmd.setStartCreateTime(FormatUtil.getDate(staCmd.getStartCreateTime1()));
                staCmd.setEndCreateTime(FormatUtil.getDate(staCmd.getEndCreateTime1()));
            }
            outs = response.getOutputStream();
            excelExportManager.exportNikeCRWCatonLine(outs, staCmd, userDetails.getCurrentOu().getId());
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
     * 入库上架导入
     * 
     * @return
     * @throws Exception
     */
    public String importInboundShelves() throws Exception {
        String msg = SUCCESS;
        request.put("msg", msg);
        if (sta == null || file == null) {
            msg = (sta == null ? "sta.id must not be null" : "") + (file == null ? " The upload file must not be null" : "");
            log.error(msg);
            request.put("msg", msg);
        } else {
            try {
                ReadStatus rs = whExecute.importInboundShelves(sta.getStvId(), file, userDetails.getUser());
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
                request.put("msg", "error");
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
                request.put("msg", "error");
                log.error("", e);
                log.error(e.getMessage());
                request.put("msg", e.getMessage());
            }
        }
        return SUCCESS;
    }

    /**
     * 虚拟仓收货导入
     * 
     * @return
     * @throws Exception
     */
    public String importStaInboundShelves() throws Exception {
        String msg = SUCCESS;
        request.put("msg", msg);
        if (sta == null || file == null) {
            msg = (sta == null ? "sta.id must not be null" : "") + (file == null ? " The upload file must not be null" : "");
            log.error(msg);
            request.put("msg", msg);
        } else {
            try {
                ReadStatus rs = whExecute.importStaInboundShelves(sta.getId(), file, userDetails.getUser(), userDetails.getCurrentOu().getId());
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
                request.put("msg", "error");
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
                request.put("msg", "error");
                log.error("", e);
                log.error(e.getMessage());
                request.put("msg", e.getMessage());
            }
        }
        return SUCCESS;
    }


    /**
     * GI入库上架
     * 
     * @return
     * @throws JSONException
     */
    public String inboundShelvesGI() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            whExecute.inboundShelvesGI(sta.getStvId(), userDetails.getUser());
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            result.put("result", ERROR);
            StringBuffer sb = new StringBuffer();
            if (e.getErrorCode() > 0) {
                sb.append(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
            }
            BusinessException be = e;
            while ((be = be.getLinkedException()) != null) {
                sb.append(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode(), be.getArgs()));
            }
            log.error(e.getMessage());
            result.put("msg", sb.toString());
        } catch (Exception e) {
            result.put("result", "error");
            log.error("", e);
            log.error(e.getMessage());
            result.put("msg", e.getMessage());
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 入库上架-手动
     * 
     * @return
     * @throws Exception
     */
    public String inboundShelvesHand() throws Exception {
        JSONObject result = new JSONObject();
        try {
            whExecute.inboundShelvesHand(sta.getId(), stvLineList, userDetails.getUser());
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            result.put("result", ERROR);
            StringBuffer sb = new StringBuffer();
            if (e.getErrorCode() > 0) {
                sb.append(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
            }
            BusinessException be = e;
            while ((be = be.getLinkedException()) != null) {
                sb.append(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode(), be.getArgs()));
            }
            log.error(e.getMessage());
            result.put("message", sb.toString());
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 查找入库单
     * 
     * @return
     */
    public String findMergeInboundStvLine() {
        if (stvList != null && stvList.size() > 0) {
            setTableConfig();
            request.put(JSON, toJson(whQuery.findMergeInboundStvLine(tableConfig.getStart(), tableConfig.getPageSize(), userDetails.getCurrentOu().getId(), stvList, tableConfig.getSorts())));
        }
        return JSON;
    }



    /**
     * 模版导出
     * 
     * @throws IOException
     * @throws UnsupportedEncodingException
     * @throws Exception
     */
    public void exportMergeInboundShelves() throws UnsupportedEncodingException, IOException {
        response.setContentType("application/octet-stream;charset=ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=MERGE_INBOUND_SHELVES" + Constants.EXCEL_XLS);
        ServletOutputStream outs = null;
        try {
            outs = response.getOutputStream();
            excelExportManager.exportMergeInboundShelves(outs, ids);
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
     * 合并入库上架导入
     * 
     * @return
     * @throws Exception
     */
    public String importMergeInboundShelves() throws Exception {
        String msg = SUCCESS;
        request.put("msg", msg);
        if (ids == null || file == null) {
            msg = (ids == null ? "STV_ID must not be null" : "") + (file == null ? " The upload file must not be null" : "");
            log.error(msg);
            request.put("msg", msg);
        } else {
            try {
                ReadStatus rs = whExecute.importMergeInboundShelves(ids, file, userDetails.getUser());
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
                request.put("msg", "error");
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
                request.put("msg", "error");
                log.error("", e);
                log.error(e.getMessage());
                request.put("msg", e.getMessage());
            }
        }
        return SUCCESS;
    }


    /**
     * 相关收货凭证导入
     * 
     * @return
     * @throws Exception
     */
    public String importInboundCertificateOfeceipt() throws Exception {
        String msg = SUCCESS;
        request.put("msg", msg);
        if (sta == null || file == null || fileName == null || sta.getCode() == null) {
            msg = (ids == null ? "Sta.Code must not be null" : "") + (file == null ? " The upload file must not be null" : "");
            log.error(msg);
            request.put("msg", msg);
        } else {
            try {
                whExe.importInboundCertificateOfeceipt(file, sta.getCode(), fileName);
            } catch (BusinessException e) {
                request.put("msg", "error");
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
                request.put("msg", "error");
                log.error("", e);
                log.error(e.getMessage());
                request.put("msg", e.getMessage());
            }
        }
        return SUCCESS;
    }

    /**
     * 入库单信息与打印
     * 
     * @return
     */
    public String staInBoundQuery() {
        return SUCCESS;
    }

    /**
     * 所有入库单 查询
     * 
     * @return
     * @throws JSONException
     */
    public String findInBoundStaQuery() throws JSONException {
        setTableConfig();
        Pagination<StockTransApplicationCommand> list =
                whQuery.findInboundStaQuery(tableConfig.getStart(), tableConfig.getPageSize(), userDetails.getCurrentOu().getId(), FormatUtil.getDate(createTime), FormatUtil.getDate(endCreateTime), FormatUtil.getDate(createTime),
                        FormatUtil.getDate(endCreateTime), sta, tableConfig.getSorts());
        request.put(JSON, toJson(list));
        return JSON;
    }

    /**
     * 查询入库单明细
     * 
     * @return
     * @throws JSONException
     */
    public String findInBoundLine() throws JSONException {
        setTableConfig();
        request.put(JSON, toJson(whQuery.findInboundLine(tableConfig.getStart(), tableConfig.getPageSize(), sta.getId(), tableConfig.getSorts())));
        return JSON;
    }

    /**
     * 查询入库单入库SN
     * 
     * @return
     * @throws JSONException
     */
    public String findInBoundSN() throws JSONException {
        setTableConfig();
        request.put(JSON, toJson(whQuery.findInBoundSN(tableConfig.getStart(), tableConfig.getPageSize(), sta.getId(), tableConfig.getSorts())));
        return JSON;
    }


    /**
     * 查询所有已经核对并且存在PDA收货指令的单据
     * 
     * @return
     * @throws JSONException
     */
    public String findPdaInboundShelves() throws JSONException {
        setTableConfig();
        request.put(JSON, toJson(whQuery.findPdaInboundShelves(tableConfig.getStart(), tableConfig.getPageSize(), userDetails.getCurrentOu().getId(), FormatUtil.getDate(createTime), FormatUtil.getDate(endCreateTime), sta)));
        return JSON;
    }


    /**
     * 查询不存在差异的明细数据
     * 
     * @return
     * @throws JSONException
     */
    public String findPdaNotDifLine() throws JSONException {
        setTableConfig();
        request.put(JSON, toJson(whQuery.findPdaInboundLine(tableConfig.getStart(), tableConfig.getPageSize(), line, false, tableConfig.getSorts())));
        return JSON;
    }

    /**
     * 差异明细
     * 
     * @return
     * @throws JSONException
     */
    public String findPdaDifLine() throws JSONException {
        setTableConfig();
        request.put(JSON, toJson(whQuery.findPdaInboundLine(tableConfig.getStart(), tableConfig.getPageSize(), line, true, tableConfig.getSorts())));
        return JSON;
    }

    /**
     * 操作明细
     * 
     * @return
     * @throws JSONException
     */
    public String findPdaLine() throws JSONException {
        setTableConfig();
        request.put(JSON, toJson(whQuery.findPdaInboundLine(tableConfig.getStart(), tableConfig.getPageSize(), line, null, tableConfig.getSorts())));
        return JSON;
    }


    /**
     * PDA无差异执行上架
     * 
     * @return
     * @throws JSONException
     */
    public String executeNotDifPdaLine() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            whExecute.executeNotDifPDALine(sta.getCode(), userDetails.getUser());
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            result.put("result", ERROR);
            StringBuffer sb = new StringBuffer();
            if (e.getErrorCode() > 0) {
                sb.append(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
            }
            BusinessException be = e;
            while ((be = be.getLinkedException()) != null) {
                sb.append(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode(), be.getArgs()));
            }
            log.error(e.getMessage());
            result.put("msg", sb.toString());
        } catch (Exception e) {
            result.put("result", "error");
            log.error("", e);
            log.error(e.getMessage());
            result.put("msg", e.getMessage());
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * PDA调整保存
     * 
     * @return
     * @throws JSONException
     */
    public String savePdaAddedQty() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            whExecute.savePdaAddedQty(line, userDetails.getUser());
            result.put("result", SUCCESS);
            result.put("userName", userDetails.getUser().getUserName());
        } catch (BusinessException e) {
            result.put("result", ERROR);
            StringBuffer sb = new StringBuffer();
            if (e.getErrorCode() > 0) {
                sb.append(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
            }
            BusinessException be = e;
            while ((be = be.getLinkedException()) != null) {
                sb.append(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode(), be.getArgs()));
            }
            log.error(e.getMessage());
            result.put("msg", sb.toString());
        } catch (Exception e) {
            result.put("result", "error");
            log.error("", e);
            log.error(e.getMessage());
            result.put("msg", e.getMessage());
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * PDA调整上架
     * 
     * @return
     * @throws JSONException
     */
    public String executePda() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            whExecute.executePda(sta.getCode(), line, userDetails.getUser());
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            result.put("result", ERROR);
            StringBuffer sb = new StringBuffer();
            if (e.getErrorCode() > 0) {
                sb.append(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
            }
            BusinessException be = e;
            while ((be = be.getLinkedException()) != null) {
                sb.append(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode(), be.getArgs()));
            }
            log.error(e.getMessage());
            result.put("msg", sb.toString());
        } catch (Exception e) {
            result.put("result", "error");
            log.error("", e);
            log.error(e.getMessage());
            result.put("msg", e.getMessage());
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 打印收货单明细列表
     * 
     * @return
     * @throws Exception
     */
    public String printInboundDetail() {
        log.info("printInboundDetail start");
        JasperPrint jp;
        try {
            jp = printManager.printInboundDetailReport(sta.getId());
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

    public String selectPdaUserName() {
        JSONObject result = new JSONObject();
        try {
            List<PdaOrder> list = whQuery.findPdaUserName(sta == null ? null : sta.getCode());
            result.put("pdalist", list);
        } catch (JSONException e) {
            log.error("", e);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 判断是否是SN号商品
     * 
     * @return
     */
    public String checkSkuSn() {
        JSONObject result = new JSONObject();
        try {
            String r = pdaTaskManager.checkSkuSn(ids);
            result.put("result", r);
        } catch (Exception e) {
            log.error("", e);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 获取对应PDA的所有SN号
     * 
     * @return
     */
    public String findPdaLineSnList() {
        setTableConfig();
        request.put(JSON, toJson(pdaTaskManager.findPdaLineSnList(ids)));
        return JSON;
    }

    /**
     * 修改对应SN号数据
     * 
     * @return
     * @throws JSONException
     */
    public String editPdaOrderSkuSn() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            pdaTaskManager.editPdaOrderSkuSn(ids, skuCode);
            result.put("result", "success");
        } catch (Exception e) {
            result.put("result", "error");
            log.error("", e);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 删除对应SN号信息
     * 
     * @return
     * @throws JSONException
     */
    public String deletePdaOrderSkuSn() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            pdaTaskManager.deletePdaOrderSkuSn(ids);
            result.put("result", "success");
        } catch (Exception e) {
            result.put("result", "error");
            log.error("", e);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 新增SN编码信息
     * 
     * @return
     * @throws JSONException
     */
    public String addPdaOrderSkuSn() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            pdaTaskManager.addPdaOrderSkuSn(ids, skuCode);
            result.put("result", "success");
        } catch (Exception e) {
            result.put("result", "error");
            log.error("", e);
        }
        request.put(JSON, result);
        return JSON;
    }

    protected Date addOneDay(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        return calendar.getTime();
    }


    public String findStaBySlipCode() {
        JSONObject result = new JSONObject();
        try {
            List<StockTransApplication> list = pdaTaskManager.findStaBySlipCode(silpCode);
            if (null != list && list.size() > 0) {
                result.put("pl", JsonUtil.obj2json(list.get(0)));
                result.put("result", SUCCESS);
            } else {
                result.put("result", NONE);
            }
        } catch (Exception e) {
            log.error("findStaBySlipCode" + e.toString(), e);
            try {
                result.put("result", ERROR);
            } catch (JSONException e1) {
                log.error("findStaBySlipCode-->" + e1.toString(), e1);
            }
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 自动化-打印货箱编码
     * 
     * @return
     */
    public String printContainerCode() {
        List<JasperPrint> jp;
        try {
            jp = printManager.printContainerCode(sta.getId(), type);
            return printObject(jp);
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    /**
     * 自动化-打印相关单据号
     * 
     * @return
     */
    public String printAutoSlipCode() {
        List<JasperPrint> jp;
        try {
            jp = printManager.printSlipCode(sta.getId());
            return printObject(jp);
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    /**
     * 修改作业单纸箱数量
     * 
     * @return
     * @throws JSONException
     * @author weiwei.wu@baozun.com
     * @version 2018年8月17日 下午3:24:49
     */
    public String editCartonNumSta() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            whExe.editCartonNumByStaId(staId, cartonNum);
            result.put("flag", SUCCESS);
        } catch (BusinessException e) {
            result.put("flag", ERROR);
            result.put("msg", e.getMessage());
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 
     * 入库废纸箱数量导入
     * @return
     * @throws Exception
     * @author weiwei.wu@baozun.com
     * @version 2018年8月20日 下午4:23:53
     */
    public String importCartonNum() throws Exception {
        String msg = SUCCESS;
        request.put("msg", msg);
        if (file == null) {
            msg = (file == null ? " The upload file must not be null" : "");
            log.error(msg);
            request.put("msg", msg);
        } else {
            try {
                ReadStatus rs = whExecute.importCartonNum(file,userDetails.getUser(), userDetails.getCurrentOu().getId());
                if (rs.getStatus() > ReadStatus.STATUS_SUCCESS) {
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
                request.put("msg", "error");
                log.error("", e);
                StringBuffer sb = new StringBuffer();
                if (e.getErrorCode() < 0) {
                    sb.append(ErrorCode.BUSINESS_EXCEPTION + e.getMessage());
                }
                BusinessException be = e;
                sb.append(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode(), be.getArgs()));
                log.error(e.getMessage());
                request.put("msg", sb.toString());
            } catch (Exception e) {
                request.put("msg", "error");
                log.error("", e);
                log.error(e.getMessage());
                request.put("msg", e.getMessage());
            }
        }
        return SUCCESS;
    }
    

    public String getSilpCode() {
        return silpCode;
    }

    public void setSilpCode(String silpCode) {
        this.silpCode = silpCode;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public StockTransApplicationCommand getSta() {
        return sta;
    }

    public void setSta(StockTransApplicationCommand sta) {
        this.sta = sta;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEndCreateTime() {
        return endCreateTime;
    }

    public void setEndCreateTime(String endCreateTime) {
        this.endCreateTime = endCreateTime;
    }

    public List<Long> getStvList() {
        return stvList;
    }

    public void setStvList(List<Long> stvList) {
        this.stvList = stvList;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getEndFinishTime() {
        return endFinishTime;
    }

    public void setEndFinishTime(String endFinishTime) {
        this.endFinishTime = endFinishTime;
    }

    public List<StvLineCommand> getStvLineList() {
        return stvLineList;
    }

    public void setStvLineList(List<StvLineCommand> stvLineList) {
        this.stvLineList = stvLineList;
    }

    public StvLineCommand getLine() {
        return line;
    }

    public void setLine(StvLineCommand line) {
        this.line = line;
    }

    public List<InventoryStatus> getInvstatus() {
        return invstatus;
    }

    public void setInvstatus(List<InventoryStatus> invstatus) {
        this.invstatus = invstatus;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public StaLineCommand getStaLine() {
        return staLine;
    }

    public void setStaLine(StaLineCommand staLine) {
        this.staLine = staLine;
    }

    public List<StvLineCommand> getStvLineLists() {
        return stvLineLists;
    }

    public void setStvLineLists(List<StvLineCommand> stvLineLists) {
        this.stvLineLists = stvLineLists;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



}
