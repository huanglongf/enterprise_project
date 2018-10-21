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

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;

import loxia.dao.Pagination;
import loxia.support.jasperreport.JasperPrintFailureException;
import loxia.support.jasperreport.JasperReportNotFoundException;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.jumbo.Constants;
import com.jumbo.manager.ExcelExportManager;
import com.jumbo.util.FormatUtil;
import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.web.manager.print.PrintManager;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.system.ChooseOptionManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.model.command.TransportatorCommand;
import com.jumbo.wms.model.lf.ZdhPiciCommand;
import com.jumbo.wms.model.lf.ZdhPiciLineCommand;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.PackageInfo;
import com.jumbo.wms.model.warehouse.PackageInfoCommand;
import com.jumbo.wms.model.warehouse.StaCartonCommand;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.wms.model.warehouse.StaOpLogCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.wms.model.warehouse.StvLineCommand;
import com.jumbo.wms.model.warehouse.TransactionType;
import com.jumbo.wms.model.warehouse.WmsCancelOrder;
import com.jumbo.wms.model.warehouse.WmsCancelOrderLine;

public class HistoricalOrderInquiryAction extends BaseJQGridProfileAction {

    public StockTransApplication getStap() {
        return stap;
    }

    public void setStap(StockTransApplication stap) {
        this.stap = stap;
    }

    /**
     * yitaofen
     */
    private static final long serialVersionUID = 449818327091372884L;

    @Autowired
    private WareHouseManager manager;
    @Autowired
    private ExcelExportManager excelExportManager;
    @Autowired
    private ChooseOptionManager chooseOptionManager;
    @Autowired
    private PrintManager printManager;

    private String createTime;
    private String endCreateTime;
    private String finishTime;
    private String endFinishTime;
    private StockTransApplicationCommand sta;

    /**
     * 平台订单开始时间
     */
    private String orderCreateTime;
    /**
     * 平台订单结束时间
     */
    private String toOrderCreateTime;

    /**
     * STA
     */
    private StockTransApplication stap;

    private List<TransactionType> typeList;

    private List<InventoryStatus> statusList;

    private StvLineCommand stvl;

    private Long staid;

    private String trackingNo;

    private String lpCode;

    private String id;

    private Long zdhId;

    private Boolean noLike = false;

    private Long ouId;

    /**
     * 优先发货城市
     */
    private String city;

    /**
     * 优先发货省
     */
    private String priority;

    private List<WmsCancelOrderLine> wmsCancelList;

    private String staCode;

    private int intStatus;
    
    private Boolean isPost=false;


    public Long getZdhId() {
        return zdhId;
    }

    public void setZdhId(Long zdhId) {
        this.zdhId = zdhId;
    }

    public Long getOuId() {
        return ouId;
    }

    public void setOuId(Long ouId) {
        this.ouId = ouId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    public String getLpCode() {
        return lpCode;
    }

    public void setLpCode(String lpCode) {
        this.lpCode = lpCode;
    }

    public Long getStaid() {
        return staid;
    }

    public void setStaid(Long staid) {
        this.staid = staid;
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

    public StockTransApplicationCommand getSta() {
        return sta;
    }

    public void setSta(StockTransApplicationCommand sta) {
        this.sta = sta;
    }

    private Boolean isOnlyParent = false;

    public String orderQueryEntry() {
        typeList = manager.findTransactionListByOu(userDetails.getCurrentOu().getParentUnit().getId());
        statusList = manager.findInvStatusByOuid(userDetails.getCurrentOu().getParentUnit().getParentUnit().getId());
        return SUCCESS;
    }


    public String insertToIMById() throws JSONException {
        JSONObject obj = new JSONObject();
        try {
            String msg = manager.insertToIMById(zdhId);
            obj.put("result", msg);
        } catch (Exception e) {
            obj.put("result", ERROR);
        }
        request.put(JSON, obj);
        return JSON;
    }


    public String checkStatusById() throws JSONException {
        JSONObject obj = new JSONObject();
        try {
            String msg = manager.checkStatusById(zdhId);
            obj.put("result", msg);
        } catch (Exception e) {
            obj.put("result", ERROR);
        }
        request.put(JSON, obj);
        return JSON;
    }

    public String backUpStaStatus() throws JSONException {
        JSONObject obj = new JSONObject();
        try {
            String msg = manager.backUpStaStatus(zdhId);
            obj.put("result", msg);
        } catch (Exception e) {
            obj.put("result", ERROR);
        }
        request.put(JSON, obj);
        return JSON;
    }



    public void importStaByOwner() throws Exception {

        response.setContentType("application/octet-stream;charset=ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=" + "import_sta_status" + Constants.EXCEL_XLS);
        ServletOutputStream outs = null;
        try {
            outs = response.getOutputStream();
            excelExportManager.importStaByOwner(zdhId, outs);
        } catch (Exception e) {
            log.error("importStaByOwner", e);
        } finally {
            if (outs != null) {
                outs.flush();
                outs.close();
            }
        }

    }



    public String backUpSta() throws JSONException {
        JSONObject obj = new JSONObject();
        try {
            String msg = manager.backUpSta(zdhId);
            obj.put("result", msg);

        } catch (Exception e) {
            obj.put("result", ERROR);
        }
        request.put(JSON, obj);
        return JSON;
    }

    public String backUpInv() throws JSONException {
        JSONObject obj = new JSONObject();
        try {
            String msg = manager.backUpInv(zdhId);
            obj.put("result", msg);
        } catch (Exception e) {
            obj.put("result", ERROR);
        }
        request.put(JSON, obj);
        return JSON;
    }

    public String createInvTxt() throws JSONException {
        JSONObject obj = new JSONObject();
        try {
            String msg = manager.createInvTxt(zdhId);
            obj.put("result", msg);
        } catch (Exception e) {
            obj.put("result", ERROR);
        }
        request.put(JSON, obj);
        return JSON;
    }

    public String cleanInvByCode() throws JSONException {
        JSONObject obj = new JSONObject();
        try {
            String msg = manager.cleanInvByCode(zdhId);
            obj.put("result", msg);
        } catch (BusinessException bex) {
            List<String> errorMsg = new ArrayList<String>();
            String msg = this.getMessage(ErrorCode.BUSINESS_EXCEPTION + bex.getErrorCode(), bex.getArgs());
            errorMsg.add(msg);
            obj.put("errMsg", JsonUtil.collection2json(errorMsg));
            obj.put("result", "errorSku");
        } catch (Exception e) {
            obj.put("result", ERROR);
        }
        request.put(JSON, obj);
        return JSON;
    }



    public String orderQueryEntryNew() {
        return SUCCESS;
    }

    public String staQueryPrintPreNew() {
        return SUCCESS;
    }

    public String initPdaCheckQueryPage() {
        return SUCCESS;
    }

    /**
     * 通过sta.code查找sta
     * 
     * @return
     */
    public String findStaByCode() throws JSONException {
        JSONObject obj = new JSONObject();
        try {
            StockTransApplication sta = manager.findStaByCode(this.sta.getCode());
            if (sta != null) {
                obj.put("result", SUCCESS);
                obj.put("sta", JsonUtil.obj2json(sta));
            }
        } catch (BusinessException e) {
            obj.put("result", ERROR);
        }
        request.put(JSON, obj);
        return JSON;
    }

    /**
     * jinggnag.chen 20160511 根据sta.id查找sta并完成状修改
     */
    public String staStateModify() {
        // 根据id查找sta
        JSONObject result = new JSONObject();
        StockTransApplicationCommand sta = manager.getStaById(staid);
        // 执行修改操作
        if (manager.staStateUpdate(sta, userDetails.getCurrentUserRole().getUser().getId(), userDetails.getCurrentOu().getId()) != null) {
            String rs = manager.staStateUpdate(sta, userDetails.getCurrentUserRole().getUser().getId(), userDetails.getCurrentOu().getId());
            try {
                if (rs.equals("success1")) {
                    result.put("rs", "success1");

                }
                // 返回值为success2表示为外包仓作业单取消，需要在页面提示"请确认外包仓是否已取消"
                if (rs.equals("success2")) {
                    result.put("rs", "success2");
                }
                if (rs.equals("error1")) {
                    result.put("rs", "error1");
                }
            } catch (JSONException e) {
                // e.printStackTrace();
                if (log.isErrorEnabled()) {
                    log.error("staStateModify error:" + staid, e);
                };
            }
        }
        // 返回执行后的作业单列表
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 获取所有物流供应商信息
     * 
     * @return
     * @throws Exception
     */
    public String getTransactionType() throws Exception {
        List<TransportatorCommand> list = chooseOptionManager.findTransportator(null);
        request.put(JSON, JsonUtil.collection2json(list));
        return JSON;
    }


    public String queryCountByOuId() {
        JSONObject result = new JSONObject();
        try {
            String msg = chooseOptionManager.queryByOuId(ouId);
            result.put("msg", msg);
        } catch (JSONException e) {
            // e.printStackTrace();
            if (log.isErrorEnabled()) {
                log.error("queryCountByOuId error:" + ouId, e);
            };
        }
        request.put(JSON, result);
        return JSON;
    }


    public void clearStaByOuId() {
        chooseOptionManager.clear(ouId);
    }

    /**
     * 获取历史作业单列表
     * 
     * @return
     */
    public String gethistoricalOrderList() throws JSONException {
        setTableConfig();
        // Pagination<StockTransApplicationCommand> list =
        // manager.findStaList(tableConfig.getStart(),
        // tableConfig.getPageSize(),
        // userDetails.getCurrentOu().getId(), FormatUtil.getDate(createTime),
        // FormatUtil.getDate(endCreateTime), FormatUtil.getDate(finishTime),
        // FormatUtil.getDate(endFinishTime),
        // FormatUtil.getDate(orderCreateTime),
        // FormatUtil.getDate(toOrderCreateTime), sta, tableConfig.getSorts());
        Pagination<StockTransApplicationCommand> list =
                manager.findHistoricalOrderList(tableConfig.getStart(), tableConfig.getPageSize(), userDetails.getCurrentOu().getId(), FormatUtil.getDate(createTime), FormatUtil.getDate(endCreateTime), FormatUtil.getDate(finishTime),
                        FormatUtil.getDate(endFinishTime), FormatUtil.getDate(orderCreateTime), FormatUtil.getDate(toOrderCreateTime), sta, tableConfig.getSorts(), noLike, getCityList(city), getCityList(priority));
        request.put(JSON, toJson(list));
        return JSON;
    }

    public String getHistoricalCodeList() throws JSONException {
        setTableConfig();
        Pagination<ZdhPiciCommand> list = manager.getHistoricalCodeList(tableConfig.getStart(), tableConfig.getPageSize(), tableConfig.getSorts());
        request.put(JSON, toJson(list));
        return JSON;
    }


    public String getHistoricalCodeListLine() throws JSONException {
        setTableConfig();
        Pagination<ZdhPiciLineCommand> list = manager.getHistoricalCodeListLine(tableConfig.getStart(), tableConfig.getPageSize(), tableConfig.getSorts(), zdhId);
        request.put(JSON, toJson(list));
        return JSON;
    }

    /**
     * 获取历史作业单列表2(只查询t_wh_sta)
     * 
     * @return
     */
    public String gethistoricalOrderList2() throws JSONException {
        setTableConfig();
        // Pagination<StockTransApplicationCommand> list =
        // manager.findStaList(tableConfig.getStart(),
        // tableConfig.getPageSize(),
        // userDetails.getCurrentOu().getId(), FormatUtil.getDate(createTime),
        // FormatUtil.getDate(endCreateTime), FormatUtil.getDate(finishTime),
        // FormatUtil.getDate(endFinishTime),
        // FormatUtil.getDate(orderCreateTime),
        // FormatUtil.getDate(toOrderCreateTime), sta, tableConfig.getSorts());
        Pagination<StockTransApplicationCommand> list =
                manager.findHistoricalOrderList2(tableConfig.getStart(), tableConfig.getPageSize(), userDetails.getCurrentOu().getId(), FormatUtil.getDate(createTime), FormatUtil.getDate(endCreateTime), FormatUtil.getDate(finishTime),
                        FormatUtil.getDate(endFinishTime), FormatUtil.getDate(orderCreateTime), FormatUtil.getDate(toOrderCreateTime), sta, getCityList(city), getCityList(priority), tableConfig.getSorts());
        request.put(JSON, toJson(list));
        return JSON;
    }

    public String findTrackingNo() throws JSONException {
        setTableConfig();
        // Pagination<StockTransApplicationCommand> list =
        // manager.findStaList(tableConfig.getStart(),
        // tableConfig.getPageSize(),
        // userDetails.getCurrentOu().getId(), FormatUtil.getDate(createTime),
        // FormatUtil.getDate(endCreateTime), FormatUtil.getDate(finishTime),
        // FormatUtil.getDate(endFinishTime),
        // FormatUtil.getDate(orderCreateTime),
        // FormatUtil.getDate(toOrderCreateTime), sta, tableConfig.getSorts());

        Pagination<PackageInfoCommand> list = manager.findByTrackingNoAndLpCode(tableConfig.getStart(), tableConfig.getPageSize(), trackingNo, lpCode, tableConfig.getSorts());
        request.put(JSON, toJson(list));

        return JSON;
    }



    public String findTrackingNoPre() throws JSONException {
        setTableConfig();
        Pagination<PackageInfo> list = manager.findByTrackingNoAndLpCode1(tableConfig.getStart(), tableConfig.getPageSize(), trackingNo, lpCode, userDetails.getCurrentOu().getId(), tableConfig.getSorts());
        request.put(JSON, toJson(list));

        return JSON;
    }

    public void exportPoConfirmReport() throws Exception {
        response.setContentType("application/octet-stream;charset=UTF-8");
        String fileName = this.getMessage("excel.tplt_po_confirm_export", new Object[] {}, this.getLocale()) + Constants.EXCEL_XLS;
        fileName = new String(fileName.getBytes("GBK"), Constants.ISO_8859_1);
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        try {
            excelExportManager.exportPoConfirmReport(response.getOutputStream(), sta.getId());
        } catch (Exception e) {
            log.error("", e);
        }
        ServletOutputStream outs = response.getOutputStream();
        outs.flush();
        outs.close();
    }

    /**
     * 打印装箱清单列表
     * 
     * @return
     * @throws Exception
     */
    public String printPoConfirmReportMode1() {
        JasperPrint jp;
        try {
            jp = printManager.printPoConfirmReport(sta.getId());
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
     * 获取作业单明细
     * 
     * @return
     */
    public String gethistoricalOrderDetailList() throws JSONException {
        setTableConfig();
        // request.put(JSON,
        // toJson(manager.findStaLineCommandListByStaId(tableConfig.getStart(),
        // tableConfig.getPageSize(), sta.getId(), tableConfig.getSorts())));
        request.put(JSON, toJson(manager.findHistoricalOrderDetailList(tableConfig.getStart(), tableConfig.getPageSize(), sta.getId(), tableConfig.getSorts())));
        return JSON;
    }

    public String selectOperateLog() {
        setTableConfig();
        request.put(JSON, toJson(manager.findWhInfoTimeListBySlipCode(tableConfig.getStart(), tableConfig.getPageSize(), sta.getRefSlipCode(), sta.getCode(), userDetails.getCurrentOu().getId(), tableConfig.getSorts())));
        return JSON;
    }

    /**
     * 行特殊显示
     * 
     * @return
     * @throws JSONException
     */

    public String selectSpecialLog() {
        setTableConfig();
        request.put(JSON, toJson(manager.selectSpecialLog(tableConfig.getStart(), tableConfig.getPageSize(), sta.getId(), tableConfig.getSorts())));
        return JSON;
    }

    public String showSnDetail() throws JSONException {
        setTableConfig();
        request.put(JSON, toJson(manager.findStvLineSnListByStaId(tableConfig.getStart(), tableConfig.getPageSize(), sta.getId(), tableConfig.getSorts())));
        return JSON;
    }

    /**
     * 获取作业单明细
     * 
     * @return
     */
    public String gethistoricalOrderDetailOperateList() throws JSONException {
        setTableConfig();
        // request.put(
        // JSON,
        // toJson(manager.findStvLineCommandListByStaId(tableConfig.getStart(),
        // tableConfig.getPageSize(), sta.getId(),
        // FormatUtil.getDate(createTime),
        // FormatUtil.getDate(endCreateTime), FormatUtil.getDate(finishTime),
        // FormatUtil.getDate(endFinishTime), stvl, tableConfig.getSorts())));
        request.put(
                JSON,
                toJson(manager.findHistoricalOrderDetailOperateList(tableConfig.getStart(), tableConfig.getPageSize(), sta.getId(), FormatUtil.getDate(createTime), FormatUtil.getDate(endCreateTime), FormatUtil.getDate(finishTime),
                        FormatUtil.getDate(endFinishTime), stvl, tableConfig.getSorts())));
        return JSON;
    }

    /**
     * 作业单查询-打印物流面单 bin.hu
     * 
     * @return
     */
    public String printSingleOrderDetailOutMode1() {
        JasperPrint jp;
        try {
            jp = printManager.printExpressBillBySta(stap.getId(), isOnlyParent, true, userDetails.getUser().getId());
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

    public String pdaQueryOrderList() {
        setTableConfig();
        Pagination<StockTransApplicationCommand> list =
                manager.pdaQueryOrderList(tableConfig.getStart(), tableConfig.getPageSize(), userDetails.getCurrentOu().getId(), FormatUtil.getDate(createTime), FormatUtil.getDate(endCreateTime), FormatUtil.getDate(finishTime),
                        FormatUtil.getDate(endFinishTime), FormatUtil.getDate(orderCreateTime), FormatUtil.getDate(toOrderCreateTime), sta, tableConfig.getSorts());
        request.put(JSON, toJson(list));
        return JSON;
    }


    public String getPdaDetail() {
        setTableConfig();
        Pagination<StaCartonCommand> list = manager.getPdaDetail(tableConfig.getStart(), tableConfig.getPageSize(), sta.getId(), tableConfig.getSorts());
        request.put(JSON, toJson(list));
        return JSON;
    }


    public String getPdaSnDetail() {
        setTableConfig();
        Pagination<StaOpLogCommand> list = manager.getPdaSNDetail(tableConfig.getStart(), tableConfig.getPageSize(), sta.getId(), tableConfig.getSorts());
        request.put(JSON, toJson(list));
        return JSON;
    }

    public String showShelvesDetail() {
        setTableConfig();
        Pagination<StaOpLogCommand> list = manager.showShelvesDetail(tableConfig.getStart(), tableConfig.getPageSize(), sta.getId(), tableConfig.getSorts());
        request.put(JSON, toJson(list));
        return JSON;
    }

    public String showShelvesSNDetail() {
        setTableConfig();
        Pagination<StaOpLogCommand> list = manager.showShelvesSNDetail(tableConfig.getStart(), tableConfig.getPageSize(), sta.getId(), tableConfig.getSorts());
        request.put(JSON, toJson(list));
        return JSON;
    }

    public String printSingleOrderDetail1() {
        List<JasperPrint> jp;
        try {
            jp = printManager.printExpressBillBySta1(id, isOnlyParent, true, userDetails.getUser().getId());
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


    public String printSingleOrderDetail2() {
        List<JasperPrint> jp;
        try {
            jp = printManager.printExpressBillBySta5(id, isOnlyParent, true, userDetails.getUser().getId());
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

    public String getPoStaList() {
        setTableConfig();
        Pagination<StockTransApplicationCommand> list = manager.findPoStaList(tableConfig.getStart(), tableConfig.getPageSize(), userDetails.getCurrentOu().getId(), tableConfig.getSorts(), sta);
        request.put(JSON, toJson(list));
        return JSON;
    }

    public String getPoLineStaList() {
        setTableConfig();
        List<StaLineCommand> list = manager.findPoLineStaList(sta.getId());
        request.put(JSON, toJson(list));
        return JSON;
    }

    /**
     * 查询配货失败的单据
     * 
     * @return
     */
    public String getDeliveryFailureSta() {
        setTableConfig();
        Pagination<WmsCancelOrder> list =
                manager.findCancelSta(tableConfig.getStart(), tableConfig.getPageSize(), sta.getCode(), sta.getRefSlipCode(), sta.getOwner(), sta.getIntStaStatus(), sta.getStartCreateTime(), sta.getEndCreateTime(), userDetails.getCurrentOu().getId(),
                        tableConfig.getSorts());
        request.put(JSON, toJson(list));
        return JSON;
    }

    /**
     * 查询配货失败的单据明细
     * 
     * @return
     */
    public String getDeliveryFailureStaLine() {
        setTableConfig();
        List<WmsCancelOrderLine> list = manager.findCancelStaLine(sta.getId());
        request.put(JSON, toJson(list));
        return JSON;
    }

    /**
     * 保存库内取消单据
     * 
     * @return
     * @throws JSONException 
     */
    public String saveDeliveryFailureStaInfo() throws JSONException {
        JSONObject result = new JSONObject();
        if (wmsCancelList != null && wmsCancelList.size() > 0) {
            Pagination<WmsCancelOrder> page = manager.findCancelSta(0, 20, staCode, null, null, intStatus, null, null, userDetails.getCurrentOu().getId(), null);
            if (page.getItems() != null && page.getItems().size() > 0) {
                if (isPost) {
                    try {
                        manager.saveAndSendCancelStaInfo(page.getItems().get(0), wmsCancelList);
                        result.put("re", "success");
                    } catch (Exception e) {
                        result.put("re", "保存失败:" + e);
                    }
                } else {
                    try {
                        manager.saveCancelStaInfo(page.getItems().get(0), wmsCancelList);
                        result.put("re", "success");
                    } catch (Exception e) {
                        result.put("re", "取消失败:" + e);
                    }
                }
            }
            request.put(JSON, result);
        }
        return JSON;
    }

    public StvLineCommand getStvl() {
        return stvl;
    }

    public void setStvl(StvLineCommand stvl) {
        this.stvl = stvl;
    }

    public List<TransactionType> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<TransactionType> typeList) {
        this.typeList = typeList;
    }

    public List<InventoryStatus> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<InventoryStatus> statusList) {
        this.statusList = statusList;
    }

    public String getOrderCreateTime() {
        return orderCreateTime;
    }

    public void setOrderCreateTime(String orderCreateTime) {
        this.orderCreateTime = orderCreateTime;
    }

    public String getToOrderCreateTime() {
        return toOrderCreateTime;
    }

    public void setToOrderCreateTime(String toOrderCreateTime) {
        this.toOrderCreateTime = toOrderCreateTime;
    }

    public Boolean getIsOnlyParent() {
        return isOnlyParent;
    }

    public void setIsOnlyParent(Boolean isOnlyParent) {
        this.isOnlyParent = isOnlyParent;
    }

    public Boolean getNoLike() {
        return noLike;
    }

    public void setNoLike(Boolean noLike) {
        this.noLike = noLike;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public List<WmsCancelOrderLine> getWmsCancelList() {
        return wmsCancelList;
    }

    public void setWmsCancelList(List<WmsCancelOrderLine> wmsCancelList) {
        this.wmsCancelList = wmsCancelList;
    }


    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

    public int getIntStatus() {
        return intStatus;
    }

    public void setIntStatus(int intStatus) {
        this.intStatus = intStatus;
    }
    

    public Boolean getIsPost() {
        return isPost;
    }

    public void setIsPost(Boolean isPost) {
        this.isPost = isPost;
    }

    /**
     * 转换城市字符串为list
     * 
     * @param str
     * @return
     */
    private List<String> getCityList(String str) {
        if (!StringUtils.hasText(str)) {
            return null;
        }
        List<String> list = new ArrayList<String>();
        if (str != null && StringUtils.hasText(str)) {
            for (String s : str.split(",")) {
                list.add(s);
            }
        }
        return list;
    }

}
