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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.Constants;
import com.jumbo.manager.ExcelExportManager;
import com.jumbo.util.FormatUtil;
import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.sku.SkuManager;
import com.jumbo.wms.manager.warehouse.BatchReceivingManagerProxy;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExe;
import com.jumbo.wms.model.command.SkuCommand;
import com.jumbo.wms.model.warehouse.ReturnApplication;
import com.jumbo.wms.model.warehouse.ReturnApplicationCommand;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.wms.model.warehouse.StockTransVoucher;
import com.jumbo.wms.model.warehouse.StvLineCommand;

import loxia.dao.Pagination;
import loxia.support.excel.ExcelKit;
import loxia.support.excel.ReadStatus;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

/**
 * @author xiaolong.fei 退货申请
 */
public class WarehouseRentrnApplicationAction extends BaseJQGridProfileAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = 4972651542655689658L;
    private ReturnApplicationCommand app;
    private String feedBackstartTime;
    private String feedBackendTime;
    private String startTime; // 开始事件
    private String endTime; // 结束时间
    private String barCode; // 商品条码
    private String code; // 退货编码
    private Long raId; // 退货申请ID
    private File file; // 导入文件
    private String tranckNo;// 运单号
    private Long typeId; // 登记ID
    private StockTransVoucher stv;
    private String stvList;
    private Long staId;
    private Boolean isStvId;
    private StockTransApplicationCommand stacmd;
    private List<StvLineCommand> stvlineList;
    private Boolean isForced;
    private File staFile;
    private String companyshop;
    private String nikeRFIDCode;

    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private WareHouseManagerExe wareHouseManagerExe;
    @Autowired
    private SkuManager skuManager;
    @Autowired
    private ExcelExportManager excelExportManager;
    @Autowired
    private BatchReceivingManagerProxy batchReceivingManagerProxy;


    public String getFeedBackstartTime() {
        return feedBackstartTime;
    }

    public void setFeedBackstartTime(String feedBackstartTime) {
        this.feedBackstartTime = feedBackstartTime;
    }

    public String getFeedBackendTime() {
        return feedBackendTime;
    }

    public void setFeedBackendTime(String feedBackendTime) {
        this.feedBackendTime = feedBackendTime;
    }

    public String getCompanyshop() {
        return companyshop;
    }

    public void setCompanyshop(String companyshop) {
        this.companyshop = companyshop;
    }

    public String returnApplication() {
        return SUCCESS;
    }
    
    public String adReturnApplication() {//AD异常包裹申请
        return SUCCESS;
    }

    /**
     * 查询退货申请
     * 
     * @return
     */
    public String findReturnAppList() {
        setTableConfig();
        Pagination<ReturnApplicationCommand> returnApp =
                wareHouseManager.findReturnAppList(userDetails.getCurrentOu().getId(), app, FormatUtil.getDate(startTime), FormatUtil.getDate(endTime), tableConfig.getStart(), tableConfig.getPageSize(), tableConfig.getSorts(),
                        FormatUtil.getDate(feedBackstartTime), FormatUtil.getDate(feedBackendTime));
        request.put(JSON, toJson(returnApp));
        return JSON;
    }


    public void sessionNikeRFID() {
        HttpServletRequest httpServletRequest = ServletActionContext.getRequest();
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute("RFIDCode", nikeRFIDCode);
    }

    /**
     * 查询反向采购退货入库单据
     * 
     * @return
     */
    public String findStaProcurementReturnInboundList() {
        setTableConfig();
        Pagination<StockTransApplicationCommand> staList =
                wareHouseManager.findStaProcurementReturnInboundList(stacmd, isStvId, userDetails.getCurrentOu().getId(), FormatUtil.getDate(startTime), FormatUtil.getDate(endTime), tableConfig.getStart(), tableConfig.getPageSize(),
                        tableConfig.getSorts());
        request.put(JSON, toJson(staList));
        return JSON;
    }

    /**
     * 查询反向采购退货入库单据
     * 
     * @return
     */
    public String findStalinelist() {
        setTableConfig();
        List<StaLineCommand> staList = wareHouseManager.findStalinelist(staId, tableConfig.getSorts());
        request.put(JSON, toJson(staList));
        return JSON;
    }

    /**
     * 反向采购退货入库确认
     * 
     * @return
     */
    public String procurementReturnInboundReceipt() throws Exception {
        JSONObject result = new JSONObject();
        if (stv != null && stv.getStvLines() != null) {
            try {
                wareHouseManager.procurementReturnInboundReceiptConfirm(stv.getSta().getId(), stv.getStvLines(), userDetails.getUser());
                result.put("result", SUCCESS);
            } catch (BusinessException e) {
                result.put("result", ERROR);
                result.put("message", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
            }
        }
        request.put(JSON, result);
        return JSON;

    }

    /**
     * 反向采购退货入库批量导出模板
     * 
     * @return
     */
    public void exportPutawayTemplate() throws Exception {
        response.setContentType("application/octet-stream;charset=UTF-8");
        String fileName = this.getMessage("procurement_return_inbound", new Object[] {}, this.getLocale());
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + Constants.EXCEL_XLS);
        List<Long> stvId = new ArrayList<Long>();
        String[] stvListId = stvList.split(",");
        for (int i = 0; i < stvListId.length; i++) {
            stvId.add(Long.valueOf(stvListId[i]));
        }
        ServletOutputStream outs = null;
        try {
            outs = response.getOutputStream();
            excelExportManager.exportProcurementReturnInboud(response.getOutputStream(), stvId);
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
     * 负向采购退货入库批量导入
     * 
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public String importProcurementInbound() throws Exception {
        String msg = SUCCESS;
        request.put("msg", msg);
        if (staFile == null) {
            msg = (staFile == null ? " The upload file must not be null" : "");
            log.error(msg);
            request.put("msg", msg);
        } else {
            Map<String, Object> readStatus = new HashMap<String, Object>();
            Map<String, Object> beans = new HashMap<String, Object>();
            readStatus = batchReceivingManagerProxy.readPutawayDataFromExcel(staFile, beans);
            beans = (Map<String, Object>) readStatus.get("beans");
            ReadStatus rs = (ReadStatus) readStatus.get("readStatus");
            if (rs.getStatus() == ReadStatus.STATUS_SUCCESS) {
                ReadStatus rs2 = batchReceivingManagerProxy.procurementBatchPutaway(beans, userDetails.getUser(), userDetails.getCurrentOu().getId());
                if (rs2.getStatus() < ReadStatus.STATUS_SUCCESS) {
                    request.put("msg", "");
                    List<String> list = new ArrayList<String>();
                    for (Exception ex : rs2.getExceptions()) {
                        if (ex instanceof BusinessException) {
                            BusinessException be = (BusinessException) ex;
                            list.add(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode(), be.getArgs()) + Constants.HTML_LINE_BREAK);
                        }
                    }
                    request.put("msg", list);
                } else {
                    request.put("msg", "上架成功");
                }
            } else if (rs.getStatus() > ReadStatus.STATUS_SUCCESS) {
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
        }

        return SUCCESS;
    }

    /**
     * 执行负向采购退货入库上架
     * 
     * @return
     * @throws JSONException
     */
    public String executeProcurementReturnPutaway() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            wareHouseManagerExe.executeProcurementReturnPutaway(stv, stvlineList, true, userDetails.getUser(), isForced);
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            result.put("result", ERROR);
            result.put("message", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 上传文件
     * 
     * @return
     * @throws JSONException
     */
    public String uploadReturnDoc() throws JSONException {
        String msg = SUCCESS;
        request.put("msg", msg);
        request.put("result", SUCCESS);
        if (file == null) {
            msg = "The upload file can not be null...";
            log.error(msg);
            request.put("msg", msg);
        } else {
            try {
                wareHouseManager.updateReturnDoc(file, code);
                request.put("result", SUCCESS);
            } catch (BusinessException e) {
                request.put("msg", ERROR);
            }
        }
        return SUCCESS;
    }

    /**
     * 新增/修改 退货申请
     * 
     * @return
     * @throws JSONException
     */
    public String addReturnApplication() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            String code = wareHouseManagerExe.addReturnApplication(userDetails.getCurrentOu().getId(), userDetails.getUser(), app);
            result.put("result", SUCCESS);
            result.put("code", code);
        } catch (BusinessException e) {
            result.put("msg", getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 根据条形码查找商品并返还商品信息
     * 
     * @return
     * @throws JSONException
     */
    public String getSkuByBarCodeAndReturnInfo() throws JSONException {
        JSONObject result = new JSONObject();
        SkuCommand sku = null;
        sku = skuManager.getByBarcode1(barCode);
        if (sku != null) {
            result.put("skuId", sku.getId());
            result.put("skuCode", sku.getCode());
            result.put("barCode", sku.getBarCode());
            result.put("skuName", sku.getName());
        }
        request.put(JSON, result);
        return JSON;
    }


    public String getSkuByBarCodeAndReturnInfo1() throws JSONException {
        JSONObject result = new JSONObject();
        SkuCommand sku = null;
        sku = skuManager.getByBarcode2(barCode, companyshop);
        if (sku != null) {
            result.put("skuId", sku.getId());
            result.put("skuCode", sku.getCode());
            result.put("barCode", sku.getBarCode());
            result.put("skuName", sku.getName());
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 根据编码查找信息
     * 
     * @return
     * @throws JSONException
     */
    public String findReturnListByParam() throws JSONException {
        JSONObject result = new JSONObject();
        ReturnApplication app = null;
        app = wareHouseManager.getReturnByCode(barCode);
        if (app != null) {
            result.put("raId", app.getId());
        }
        request.put(JSON, result);
        return JSON;
    }


    /**
     * 根据退货申请ID查找明细，并返回商品信息。
     * 
     * @return
     * @throws JSONException
     */
    public String fingReturnSkuByRaId() throws JSONException {
        JSONObject result = new JSONObject();
        List<ReturnApplicationCommand> raList = wareHouseManager.fingReturnSkuByRaId(raId);
        if (raList.size() > 0) {
            result.put("skuList", JsonUtil.collection2json(raList));
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 根据运单号查找物流商
     * 
     * @return
     * @throws JSONException
     */
    public String findlpCodeByTrackNo() throws JSONException {
        JSONObject result = new JSONObject();
        String lpCode = wareHouseManager.findLpCodeByTrackNo(tranckNo);
        result.put("lpCode", lpCode);
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 修改快递登记状态
     * 
     * @return
     * @throws JSONException
     */
    public String returnApplicationUpdate() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            wareHouseManager.updateReTrackNoStatus(raId, typeId);
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            result.put("msg", getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, result);
        return JSON;
    }

    public ReturnApplicationCommand getApp() {
        return app;
    }

    public void setApp(ReturnApplicationCommand app) {
        this.app = app;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public Long getRaId() {
        return raId;
    }

    public void setRaId(Long raId) {
        this.raId = raId;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTranckNo() {
        return tranckNo;
    }

    public void setTranckNo(String tranckNo) {
        this.tranckNo = tranckNo;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public StockTransApplicationCommand getStacmd() {
        return stacmd;
    }

    public void setStacmd(StockTransApplicationCommand stacmd) {
        this.stacmd = stacmd;
    }

    public StockTransVoucher getStv() {
        return stv;
    }

    public void setStv(StockTransVoucher stv) {
        this.stv = stv;
    }

    public String getStvList() {
        return stvList;
    }

    public void setStvList(String stvList) {
        this.stvList = stvList;
    }

    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

    public Boolean getIsStvId() {
        return isStvId;
    }

    public void setIsStvId(Boolean isStvId) {
        this.isStvId = isStvId;
    }

    public List<StvLineCommand> getStvlineList() {
        return stvlineList;
    }

    public void setStvlineList(List<StvLineCommand> stvlineList) {
        this.stvlineList = stvlineList;
    }

    public File getStaFile() {
        return staFile;
    }

    public void setStaFile(File staFile) {
        this.staFile = staFile;
    }

    public String getNikeRFIDCode() {
        return nikeRFIDCode;
    }

    public void setNikeRFIDCode(String nikeRFIDCode) {
        this.nikeRFIDCode = nikeRFIDCode;
    }


}
