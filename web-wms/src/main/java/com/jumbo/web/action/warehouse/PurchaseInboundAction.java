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

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import com.jumbo.Constants;
import com.jumbo.manager.ExcelExportManager;
import com.jumbo.util.FormatUtil;
import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.web.manager.print.PrintManager;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.DropDownListManager;
import com.jumbo.wms.manager.sku.SkuManager;
import com.jumbo.wms.manager.warehouse.StarbucksManagerProxy;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerCancel;
import com.jumbo.wms.manager.warehouse.WareHouseManagerQuery;
import com.jumbo.wms.manager.warehouse.excel.ExcelReadManager;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.command.GiftLineCommand;
import com.jumbo.wms.model.command.SkuCommand;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.warehouse.SkuSnLogCommand;
import com.jumbo.wms.model.warehouse.StaDeliveryInfoCommand;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.wms.model.warehouse.StaSnImportCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.StockTransVoucher;
import com.jumbo.wms.model.warehouse.StvLine;
import com.jumbo.wms.model.warehouse.StvLineCommand;
import com.jumbo.wms.model.warehouse.WarehouseLocation;

import loxia.dao.Pagination;
import loxia.support.excel.ExcelKit;
import loxia.support.excel.ReadStatus;
import loxia.support.json.JSONArray;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;
import net.sf.jasperreports.engine.JasperPrint;

/**
 * 
 * @author wanghua
 * 
 */
public class PurchaseInboundAction extends BaseJQGridProfileAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = -1820994651603346529L;

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(PurchaseInboundAction.class);

    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private WareHouseManagerCancel wareHouseManagerCancel;
    @Autowired
    private ExcelReadManager excelReadManager;
    @Autowired
    private WareHouseManagerQuery wareHouseManagerQuery;
    @Autowired
    private DropDownListManager dropDownManager;
    @Autowired
    private PrintManager printManager;
    @Autowired
    private ExcelExportManager excelExportManager;
    @Autowired
    private StarbucksManagerProxy starbucksManagerProxy;
    @Autowired
    private SkuManager skuManager;
    /**
     * STA
     */
    private StockTransApplication sta;

    private SkuCommand skuCommand;

    private String startTime;
    private String endTime;
    private String arriveStartTime;
    private String arriveEndTime;

    private String vmiCode;
    private StockTransVoucher stv;
    private Sku sku;
    private List<StvLineCommand> stvlineList;
    private WarehouseLocation location;
    private StvLine stvLine;
    private File staFile;
    private File goodsInfoFile;

    private Boolean isForced;


    /**
     * 批次上架模式
     */
    private List<ChooseOption> inMode;
    private String term;
    private Long staid;
    private String sn;
    private Long skuId;
    private Integer interfaceType;
    private Integer snType;
    private Integer spType;
    private String rfidCode;

    /**
     * 保修卡
     */
    private List<GiftLineCommand> giftLineList;

    private File file;

    /**
     * 是否需要发票
     */
    private Integer isNeedInvoice;

    /**
     * 物流服务商
     */
    private String lpcode;

    /**
     * 快递单号
     */
    private String trackingNo;

    /**
     * 原订单收货人
     */
    private String receiver;

    /**
     * 原收货人电话
     */
    private String receiverPhone;

    /**
     * 原始订单号
     */
    private String orderCode;

    /**
     * Taobao原始订单号
     */
    private String taobaoOrderCode;

    private String slipCode1;
    private String slipCode2;

    private Long returnReasonType;// 退货原因类型
    private String returnReasonMemo;// 备注

    private String snBarCode;// SN号的barCode

    /**
     * 商品条码
     */
    private String barCode;

    private String staCode;

    private Long staId;


    public Long getStaId() {
        return staId;
    }


    public void setStaId(Long staId) {
        this.staId = staId;
    }


    public String getStaCode() {
        return staCode;
    }


    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }


    public SkuCommand getSkuCommand() {
        return skuCommand;
    }


    public void setSkuCommand(SkuCommand skuCommand) {
        this.skuCommand = skuCommand;
    }


    public String getBarCode() {
        return barCode;
    }


    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    /**
     * 采购经销
     * 
     * @return
     */
    public String purchaseEntry() {
        inMode = dropDownManager.findStaInboundStoreModeChooseOptionList();
        return SUCCESS;
    }

    public String nikeRFIDByCode() {
        JSONObject obj = new JSONObject();
        logger.error("nikeRFIDByCode" + staId + barCode + " rfidCode" + rfidCode);
        Boolean msg = dropDownManager.nikeRFIDByCode(staId, barCode, rfidCode);
        try {
            obj.put("msg", msg);
            request.put(JSON, obj);
        } catch (Exception e) {
            logger.error("nikeRFIDByCodeError" + e);
        }
        return JSON;
    }

    /**
     * 
     * 查询是否是Jordon 商品
     */
    public String findIsJordonByVmiCode() {
        JSONObject obj = new JSONObject();
        boolean msg = false;
        try {
            obj.put("msg", msg);
            request.put(JSON, obj);
        } catch (Exception e) {

        }
        return JSON;
    }

    /**
     * sta.jqgrid
     * 
     * @return 不分页操作
     */
    // old
    public String staListJsonNoPagination() {
        if (sta != null) {
            setTableConfig();
            if (StockTransApplicationType.TRANSIT_CROSS.equals(sta.getType()) || StockTransApplicationType.SAME_COMPANY_TRANSFER.equals(sta.getType())) {
                request.put(JSON, toJson(wareHouseManager.findTranCossStaNotFinishedListByTypeNoPage(sta.getType(), userDetails.getCurrentOu(), tableConfig.getSorts())));
            } else {
                request.put(JSON, toJson(wareHouseManager.findStaNotFinishedListByTypeNoPage(sta.getType(), userDetails.getCurrentOu(), tableConfig.getSorts())));
            }
        }
        return JSON;
    }

    /**
     * @return 分页操作
     */
    public String staListJson() {
        if (sta != null) {
            setTableConfig();
            if (StockTransApplicationType.TRANSIT_CROSS.equals(sta.getType()) || StockTransApplicationType.SAME_COMPANY_TRANSFER.equals(sta.getType())) {

                request.put(JSON, toJson(wareHouseManager.findTranCossStaNotFinishedListByType(sta, userDetails.getCurrentOu(), FormatUtil.getDate(startTime), FormatUtil.getDate(endTime), FormatUtil.getDate(arriveStartTime),
                        FormatUtil.getDate(arriveEndTime), tableConfig.getStart(), tableConfig.getPageSize(), slipCode1, slipCode2, tableConfig.getSorts())));
            } else {
                List<StockTransApplicationCommand> stockTransApplicationList =
                        wareHouseManager.findStaNotFinishedListByType(sta, isNeedInvoice, lpcode, trackingNo, userDetails.getCurrentOu(), FormatUtil.getDate(startTime), FormatUtil.getDate(endTime), FormatUtil.getDate(arriveStartTime),
                                FormatUtil.getDate(arriveEndTime), tableConfig.getStart(), tableConfig.getPageSize(), slipCode1, slipCode2, tableConfig.getSorts()).getItems();
                for (StockTransApplicationCommand stockTransApplicationCommand : stockTransApplicationList) {
                    boolean b = false;
                    if (stockTransApplicationCommand.getOwner().equals("5Nike-Global Inline 官方商城") || stockTransApplicationCommand.getOwner().equals("5Nike-Global Swoosh 官方商城")) {
                        b = wareHouseManager.findInvoiceBySlipCode(stockTransApplicationCommand.getSlipCode1());
                        if (b) {
                            StaDeliveryInfoCommand staDeliveryInfo = new StaDeliveryInfoCommand();
                            staDeliveryInfo.setId(stockTransApplicationCommand.getId());
                            staDeliveryInfo.setStoreComIsNeedInvoice(true);
                            try {
                                wareHouseManager.updateStaDeliveryInfo(staDeliveryInfo);
                            } catch (Exception e) {
                                log.error(e.getMessage());
                            }
                        }
                    }
                }
                request.put(
                        JSON,
                        toJson(wareHouseManager.findStaNotFinishedListByType(sta, isNeedInvoice, lpcode, trackingNo, userDetails.getCurrentOu(), FormatUtil.getDate(startTime), FormatUtil.getDate(endTime), FormatUtil.getDate(arriveStartTime),
                                FormatUtil.getDate(arriveEndTime), tableConfig.getStart(), tableConfig.getPageSize(), slipCode1, slipCode2, tableConfig.getSorts())));
            }
        }
        return JSON;
    }

    /**
     * @return 分页操作
     */
    public String newStaListJson() {
        if (sta != null) {
            setTableConfig();
            request.put(
                    JSON,
                    toJson(wareHouseManager.findStaNotFinishedListByTypeNew(sta, isNeedInvoice, lpcode, trackingNo, userDetails.getCurrentOu(), FormatUtil.getDate(startTime), FormatUtil.getDate(endTime), FormatUtil.getDate(arriveStartTime),
                            FormatUtil.getDate(arriveEndTime), tableConfig.getStart(), tableConfig.getPageSize(), slipCode1, slipCode2, tableConfig.getSorts())));
        }
        return JSON;
    }

    /**
     * @return 分页操作
     */
    public String imperfectStaListJson() {
        if (sta != null) {
            setTableConfig();

            request.put(JSON, toJson(wareHouseManager.findTranCossStaNotFinishedListByType(sta, userDetails.getCurrentOu(), FormatUtil.getDate(startTime), FormatUtil.getDate(endTime), FormatUtil.getDate(arriveStartTime),
                    FormatUtil.getDate(arriveEndTime), tableConfig.getStart(), tableConfig.getPageSize(), slipCode1, slipCode2, tableConfig.getSorts())));
        }
        return JSON;
    }

    /**
     * 
     * @return
     */
    public String returnEntryUnlocked() {

        return SUCCESS;
    }

    public String listUnlockedStaJson() {
        setTableConfig();
        request.put(JSON, toJson(wareHouseManager.findlockedSta(sta, barCode, FormatUtil.getDate(startTime), FormatUtil.getDate(endTime), lpcode, trackingNo, userDetails.getCurrentOu(), receiver, receiverPhone, orderCode, taobaoOrderCode,
                tableConfig.getStart(), tableConfig.getPageSize(), tableConfig.getSorts())));
        return JSON;
    }


    public String updateStaUnlocked() throws Exception {
        JSONObject result = new JSONObject();
        try {
            Long id = sta.getId();
            wareHouseManager.updateStaUnlocked(id, lpcode, trackingNo, returnReasonType, returnReasonMemo, userDetails.getCurrentOu().getId(), userDetails.getUser().getLoginName());
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            result.put("result", ERROR);
            String errorMsg = getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs());
            result.put("msg", errorMsg);
        } catch (Exception e) {
            result.put("result", ERROR);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * staLine.jqgrid
     * 
     * @return
     * @throws JSONException
     */
    public String staLineListJson() {
        if (sta != null) {
            setTableConfig();
            List<StaLineCommand> list = wareHouseManager.findStaLineListByStaId(sta.getId(), tableConfig.getSorts());
            request.put(JSON, toJson(list));
        }
        return JSON;
    }

    /**
     * 退换货入库判断是否有发票
     * 
     * @return
     * @throws JSONException
     */
    public String whetherNeedInvoice() throws JSONException {
        if (staid != null) {
            boolean b = false;
            JSONObject result = new JSONObject();
            sta = wareHouseManager.getStaById(staid);
            b = wareHouseManager.findInvoiceBySlipCode(sta.getSlipCode1());
            if (b) {
                result.put("isNeedInvoice", "true");
            } else {
                result.put("isNeedInvoice", "false");
            }
            request.put(JSON, result);
        }
        return JSON;
    }

    /**
     * 库间移动 - 外包仓 入库明细
     * 
     * @return
     */
    public String stvLineListThreePl() {
        if (sta != null) {
            setTableConfig();
            request.put(JSON, toJson(wareHouseManager.findStaLineDetailByStaId(sta.getId(), tableConfig.getSorts())));
        }
        return JSON;
    }

    /**
     * 收货
     * 
     * @return
     * @throws Exception
     */
    public String purchaseReceiveStep1() throws Exception {
        if (stv != null && stv.getStvLines() != null) {
            try {
                JSONObject obj = new JSONObject();
                stv = wareHouseManager.purchaseReceiveStep1(stv.getSta().getId(), stv.getStvLines(), giftLineList, userDetails.getUser(), stv.getSta().getMemo(), stv.getIsPda(), false, snType);
                obj.put("stvId", stv.getId());
                obj.put("isPda", stv.getIsPda());
                request.put(JSON, obj);
            } catch (BusinessException e) {
                catchBusinessException(e);
            } catch (Exception e) {
                log.error("", e);
                if (e instanceof DataIntegrityViolationException) {
                    request.put(JSON, new JSONObject().put("SNmsg", "您输入的SN号有重复,请重新核对"));
                } else {
                    request.put(JSON, new JSONObject().put("SNmsg", "数据可能有错误"));
                }
                // catchException(e);
            }
        }
        return JSON;
    }

    // /**
    // * 根据条码或者商品编码查询sku 因为不存在计划外的收货所以失效
    // *
    // * @deprecated
    // * @return
    // */
    // public String findSkuByBarCodeOrProductCode() {
    // if (sku != null) {
    // try {
    // if (StringUtils.hasLength(sku.getBarCode())) {
    // request.put(JSON, JsonUtil.obj2json(wareHouseManager.findSkuByBarCode(sku.getBarCode())));
    // } else {
    // request.put(JSON,
    // JsonUtil.collection2json((wareHouseManager.findSkuListByJmCode(sku.getJmCode()))));
    // }
    // } catch (Exception e) {
    // log.error("sku{}{}不存在", StringUtils.hasLength(sku.getBarCode()) ? ("条码" + sku.getBarCode()) :
    // ("编码" + sku.getJmCode()));
    // }
    //
    // }
    // return JSON;
    // }

    /**
     * 取消当前收获
     * 
     * @return
     * @throws Exception
     */
    public String cancelSta() throws Exception {
        try {
        	wareHouseManagerCancel.cancelSta(sta.getId());
            request.put(JSON, new JSONObject().put("msg", SUCCESS));
        } catch (BusinessException e) {
            catchException(e);
            log.error("", e);
        } catch (JSONException e) {
            log.error("", e);
        }
        return JSON;
    }

    /**
     * sta导出
     * 
     * @throws Exception
     */
    public void staExportForPurchase() throws Exception {
        sta = wareHouseManager.findStaById(sta.getId());
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        if (sta.getType().getValue() == StockTransApplicationType.INBOUND_SETTLEMENT.getValue() || sta.getType().getValue() == StockTransApplicationType.INBOUND_CONSIGNMENT.getValue()
                || sta.getType().getValue() == StockTransApplicationType.INBOUND_GIFT.getValue() || sta.getType().getValue() == StockTransApplicationType.INBOUND_MOBILE.getValue()
                || sta.getType().getValue() == StockTransApplicationType.VMI_INBOUND_CONSIGNMENT.getValue()) {
            response.setHeader("Content-Disposition", "attachment;filename=sta_" + sta.getCode() + Constants.EXCEL_XLS);
        } else {
            response.setHeader("Content-Disposition", "attachment;filename=sta_" + sta.getRefSlipCode() + Constants.EXCEL_XLS);
        }
        ServletOutputStream outs = null;
        try {
            outs = response.getOutputStream();
            excelExportManager.staExportForPurchase(outs, sta);
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
     * 暂存区导出
     * 
     * @throws Exception
     */
    public void staGIExport() throws Exception {
        sta = wareHouseManager.findStaById(sta.getId());
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=STA_GI_" + sta.getRefSlipCode() + Constants.EXCEL_XLS);
        ServletOutputStream outs = null;
        try {
            outs = response.getOutputStream();
            excelExportManager.staExportForGI(outs, sta);
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
     * 暂存区sta导入
     * 
     * @return
     * @throws Exception
     */
    public String staImportForGI() throws Exception {
        String msg = SUCCESS;
        request.put("msg", msg);
        if (sta == null || staFile == null) {
            msg = (sta == null ? "sta.id must not be null" : "") + (staFile == null ? " The upload file must not be null" : "");
            log.error(msg);
            request.put("msg", msg);
        } else {
            try {
                ReadStatus rs = wareHouseManagerQuery.staImportForGI(sta.getId(), stv, staFile, userDetails.getUser(), null);
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
     * 收货上架模版导出
     * 
     * @throws Exception
     */
    public void invExportForPurchase() throws Exception {
        sta = wareHouseManager.findStaById(sta.getId());
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        String fileName = this.getMessage("excel.tplt_export_purchase_inbound", new Object[] {}, this.getLocale());
        fileName = new String(fileName.getBytes("GBK"), "ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + Constants.EXCEL_XLS);
        ServletOutputStream outs = null;
        try {
            outs = response.getOutputStream();
            excelExportManager.invExportForPurchase(outs, sta);
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
     * 收货上架导入
     * 
     * @throws Exception
     */
    public String importPurchaseInbound() {
        List<String> errorMsg = new ArrayList<String>();
        if (file == null) {
            return ERROR;
        } else {
            try {
                ReadStatus rs = excelReadManager.createPurchaseInboundImport(file, sta.getId(), stv.getId(), userDetails.getUser());
                request.put("result", ERROR);
                if (rs.getStatus() == ReadStatus.STATUS_SUCCESS) {
                    request.put("result", SUCCESS);
                } else if (rs.getStatus() > 0) {
                    List<String> list = ExcelKit.getInstance().getReadStatusMessages(rs, this.getLocale());
                    for (String s : list) {
                        errorMsg.add(s);
                    }
                }
            } catch (BusinessException e) {
                request.put("result", ERROR);
                String msg = this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs());
                BusinessException current = e;
                while (current.getLinkedException() != null) {
                    current = current.getLinkedException();
                    msg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + current.getErrorCode(), current.getArgs()) + Constants.HTML_LINE_BREAK;
                }
                errorMsg.add(msg);
            } catch (Exception e) {
                request.put("result", ERROR);
                log.error("", e);
            }
            request.put("message", JsonUtil.collection2json(errorMsg));
        }
        return SUCCESS;
    }

    /**
     * 导入sn号商品至前台
     * 
     * @return
     */
    public String staSnImportToWeb() throws JSONException {
        String msg = SUCCESS;
        request.put("msg", msg);
        if (staid == null || file == null) {
            msg = (staid == null ? "sta.id must not be null" : "") + (file == null ? " The upload file must not be null" : "");
            log.error(msg);
            request.put("msg", msg);
        } else {
            try {
                List<SkuSnLogCommand> snList = wareHouseManager.findOutboundSnBySta(staid);
                List<StaSnImportCommand> list = excelReadManager.importsnToWeb(file, userDetails.getCurrentOu().getId(), snList, snBarCode);
                if (list.size() > 0) {
                    request.put("msg", SUCCESS);
                    String lists = JsonUtil.collection2json(list).toString();
                    request.put("list", lists);
                } else {
                    request.put("msg", ERROR);
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
     * 采购sta导入
     * 
     * @return
     * @throws Exception
     */
    public String staImportForPurchase() throws Exception {
        String msg = SUCCESS;
        request.put("msg", msg);
        if (sta == null || staFile == null) {
            msg = (sta == null ? "sta.id must not be null" : "") + (staFile == null ? " The upload file must not be null" : "");
            log.error(msg);
            request.put("msg", msg);
        } else {
            try {
                ReadStatus rs = excelReadManager.staImportForPurchase(sta.getId(), stv, staFile, userDetails.getUser(), null);
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
     * sx 导入
     * 
     * @return
     * @throws Exception
     */
    public String staImportForRepair() throws Exception {
        String msg = SUCCESS;
        request.put("msg", msg);
        if (sta == null || staFile == null) {
            msg = (sta == null ? "sta.id must not be null" : "") + (staFile == null ? " The upload file must not be null" : "");
            log.error(msg);
            request.put("msg", msg);
        } else {
            try {
                ReadStatus rs = excelReadManager.staImportForRepair(sta.getId(), stv, staFile, userDetails.getUser(), null);
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
                sb.append(businessExceptionPost(e));
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
     * 数量确认-导入
     * 
     * @return
     * @throws Exception　
     */
    public String inboundAmountConfirmImport() throws Exception {
        String msg = SUCCESS;
        request.put("msg", msg);
        if (sta == null || goodsInfoFile == null) {
            msg = (sta == null ? "sta.id must not be null" : "") + (goodsInfoFile == null ? " The upload file must not be null" : "");
            log.error(msg);
            request.put("msg", msg);
        } else {
            try {
                Map<String, Object> result = excelReadManager.inboundAmountConfirmImport(sta.getId(), goodsInfoFile, userDetails.getUser());
                ReadStatus rs = (ReadStatus) result.get("readStatus");
                StockTransVoucher stv = (StockTransVoucher) result.get("stv");
                if (rs != null) if (rs.getStatus() > ReadStatus.STATUS_SUCCESS) {
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
                if (stv != null) {
                    request.put("stvId", stv.getId());
                    request.put("isPda", stv.getIsPda());
                    request.put("quantity", result.get("quantity"));
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
     * 进入确认收货编辑页面
     * 
     * @return
     * @throws Exception
     */
    public String stvInfo() throws Exception {
        try {
            // wareHouseManager.purchaseReceiveInSuggestion(stv.getId(), userDetails.getUser());
            wareHouseManager.suggestInboundLocation(stv.getId(), false);

            Map<String, List<StvLineCommand>> staLineMap = wareHouseManager.loadStvLineByStvId(stv.getId());
            List<JSONObject> array = new ArrayList<JSONObject>();
            for (Map.Entry<String, List<StvLineCommand>> entry : staLineMap.entrySet()) {
                StvLineCommand cmd = (StvLineCommand) BeanUtils.cloneBean(entry.getValue().get(0));
                cmd.setQuantity(0L);
                for (StvLineCommand tmp : entry.getValue()) {
                    cmd.setQuantity(cmd.getQuantity() + tmp.getQuantity());
                }
                JSONObject staLine = new JSONObject(cmd);
                staLine.put("stvLines", new JSONArray(entry.getValue()));
                array.add(staLine);
            }
            request.put(JSON, new JSONArray(array));
            return JSON;
        } catch (BusinessException e) {
            catchBusinessException(e);
        } catch (Exception e) {
            catchException(e);
        }

        return JSON;
    }

    public String suggestInboundLocation() {
        wareHouseManager.suggestInboundLocation(stv.getId(), false);
        return JSON;
    }

    /**
     * 进入确认收货编辑页面
     * 
     * @return
     * @throws Exception
     */
    public String stvInfoGroupBatchCode() throws Exception {
        try {
            wareHouseManager.suggestInboundLocation(stv.getId(), false);
            request.put(JSON, wareHouseManager.loadStvLineByStvIdGroupBatchCode(stv.getId()));
            return JSON;
        } catch (BusinessException e) {
            catchBusinessException(e);
        } catch (Exception e) {
            catchException(e);
        }

        return JSON;
    }

    /**
     * 执行本次入库
     * 
     * @return
     * @throws JSONException
     * @throws Exception
     * @throws Exception
     */
    public String executeInventory() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("result", ERROR);
        try {
            wareHouseManager.purchaseSetInvoiceNumber(stv, stvlineList, sta.getIntStatus() == StockTransApplicationStatus.FINISHED.getValue(), userDetails.getUser(), isForced);
            json.put("result", SUCCESS);
        } catch (BusinessException e) {
            String errorMsg = this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs());
            if (e.getErrorCode() == ErrorCode.RETURN_ORDER_ERROR) {
                json.put("result", "wait");
                json.put("message", errorMsg);
            } else {
                BusinessException current = e;
                while (current.getLinkedException() != null) {
                    current = current.getLinkedException();
                    errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + current.getErrorCode(), current.getArgs()) + Constants.HTML_LINE_BREAK;
                }
                json.put("message", errorMsg);
            }
        }
        request.put(JSON, json);
        return JSON;
    }

    /**
     * 采购上架sku信息打印
     * 
     * @return
     * @throws Exception
     */
    public String printPurchaseInfo() {
        if (stv.getId() == null) return null;
        try {
            JasperPrint jsprint = printManager.printPurchaseInfo(stv, userDetails.getCurrentOu().getId());
            return printObject(jsprint);
        } catch (Exception e) {
            log.error("", e);
            return null;
        }
    }

    public String cancelStv() throws Exception {
        try {
        	wareHouseManagerCancel.cancelStv(stv.getId(), userDetails.getUser().getId());
        } catch (BusinessException e) {
            catchBusinessException(e);
        } catch (Exception e) {
            catchException(e);
        }
        return JSON;
    }

    public String findOutBoundSn() throws JSONException {
        JSONObject rs = new JSONObject();
        List<SkuSnLogCommand> list = wareHouseManager.findOutboundSnBySta(staid);
        rs.put("sns", JsonUtil.collection2json(list));
        request.put(JSON, rs);
        return JSON;
    }

    /**
     * 作废卡（星巴克）
     */
    public String cancelCard() throws JSONException {
        JSONObject result = new JSONObject();
        String errorMsg = "";
        try {
            starbucksManagerProxy.cancelCard(staid, sn, skuId, interfaceType, snType, spType, userDetails.getCurrentOu().getId(), userDetails.getUser().getId());
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            result.put("result", ERROR);
            errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs());
            BusinessException current = e;
            while (current.getLinkedException() != null) {
                current = current.getLinkedException();
                errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + current.getErrorCode(), current.getArgs());
            }
            result.put("msg", errorMsg);
        } catch (Exception e) {
            errorMsg = "系统异常";
            result.put("result", ERROR);
            result.put("msg", errorMsg);
        }
        request.put(JSON, result);
        return JSON;
    }


    /**
     * 特殊商品品根据导入sku码作废
     * 
     * @author jinggang.chen
     * @return
     */
    public String skuImport() {
        List<String> skuBarcode = new ArrayList<String>();
        List<String> skuList = new ArrayList<String>();
        // 获取导入文件sku码
        try {
            skuList = excelReadManager.importParticularcommdySku(file);
        } catch (Exception e) {
            log.error(e.getMessage(), "文件解析失败");
        }
        if (skuList.size() > 0) {
            // 按照特殊商品barcode作废商品
            try {
                skuBarcode = skuManager.specialCommodityDelete(skuList, userDetails.getCurrentOu().getId(), userDetails.getUser().getId());
            } catch (Exception e) {
                log.error(e.getMessage(), "特使商品作废失败");
            }
        } else {
            request.put("msg", "导入文件内容为空");
            return SUCCESS;
        }
        if (!skuBarcode.isEmpty()) {
            request.put("msg", "导入失败sku  " + skuBarcode.toString());
        }
        return SUCCESS;
    }

    /**
     * 方法说明：退换货入库指令商品明细
     * 
     * @author LuYingMing
     * @return
     */
    public String returnInboundGoodsDetail() {
        if (sta != null) {
            setTableConfig();
            Pagination<SkuCommand> list = skuManager.returnInboundDirectiveDetail(tableConfig.getStart(), tableConfig.getPageSize(), sta.getId(), userDetails.getCurrentOu().getId());
            request.put(JSON, toJson(list));
        }
        return JSON;
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

    public List<StvLineCommand> getStvlineList() {
        return stvlineList;
    }

    public void setStvlineList(List<StvLineCommand> stvlineList) {
        this.stvlineList = stvlineList;
    }

    public WarehouseLocation getLocation() {
        return location;
    }

    public void setLocation(WarehouseLocation location) {
        this.location = location;
    }

    public StvLine getStvLine() {
        return stvLine;
    }

    public void setStvLine(StvLine stvLine) {
        this.stvLine = stvLine;
    }

    public List<ChooseOption> getInMode() {
        return inMode;
    }

    public void setInMode(List<ChooseOption> inMode) {
        this.inMode = inMode;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public Sku getSku() {
        return sku;
    }

    public void setSku(Sku sku) {
        this.sku = sku;
    }

    public File getStaFile() {
        return staFile;
    }

    public void setStaFile(File staFile) {
        this.staFile = staFile;
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

    private void errorPost(String key, String error) throws Exception {
        request.put(JSON, new JSONObject().put(key, error));
    }

    private void errorPost(String error) throws Exception {
        errorPost("msg", error);
    }

    private void catchBusinessException(BusinessException e) throws Exception {
        String error = businessExceptionPost(e);
        log.error(error);
        errorPost(error);
    }

    private void catchException(Exception e) throws Exception {
        log.error(e.getMessage());
        errorPost(e.getMessage());
    }

    public Long getStaid() {
        return staid;
    }

    public void setStaid(Long staid) {
        this.staid = staid;
    }

    public File getGoodsInfoFile() {
        return goodsInfoFile;
    }

    public void setGoodsInfoFile(File goodsInfoFile) {
        this.goodsInfoFile = goodsInfoFile;
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

    public String getArriveStartTime() {
        return arriveStartTime;
    }

    public void setArriveStartTime(String arriveStartTime) {
        this.arriveStartTime = arriveStartTime;
    }

    public String getArriveEndTime() {
        return arriveEndTime;
    }

    public void setArriveEndTime(String arriveEndTime) {
        this.arriveEndTime = arriveEndTime;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Integer getIsNeedInvoice() {
        return isNeedInvoice;
    }

    public void setIsNeedInvoice(Integer isNeedInvoice) {
        this.isNeedInvoice = isNeedInvoice;
    }

    public String getLpcode() {
        return lpcode;
    }

    public void setLpcode(String lpcode) {
        this.lpcode = lpcode;
    }

    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getTaobaoOrderCode() {
        return taobaoOrderCode;
    }

    public void setTaobaoOrderCode(String taobaoOrderCode) {
        this.taobaoOrderCode = taobaoOrderCode;
    }

    public List<GiftLineCommand> getGiftLineList() {
        return giftLineList;
    }

    public void setGiftLineList(List<GiftLineCommand> giftLineList) {
        this.giftLineList = giftLineList;
    }

    public String getSlipCode1() {
        return slipCode1;
    }

    public void setSlipCode1(String slipCode1) {
        this.slipCode1 = slipCode1;
    }

    public String getSlipCode2() {
        return slipCode2;
    }

    public void setSlipCode2(String slipCode2) {
        this.slipCode2 = slipCode2;
    }

    public Boolean getIsForced() {
        return isForced;
    }

    public void setIsForced(Boolean isForced) {
        this.isForced = isForced;
    }

    public Long getReturnReasonType() {
        return returnReasonType;
    }

    public void setReturnReasonType(Long returnReasonType) {
        this.returnReasonType = returnReasonType;
    }

    public String getReturnReasonMemo() {
        return returnReasonMemo;
    }

    public void setReturnReasonMemo(String returnReasonMemo) {
        this.returnReasonMemo = returnReasonMemo;
    }

    public String getSnBarCode() {
        return snBarCode;
    }

    public void setSnBarCode(String snBarCode) {
        this.snBarCode = snBarCode;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Integer getInterfaceType() {
        return interfaceType;
    }

    public void setInterfaceType(Integer interfaceType) {
        this.interfaceType = interfaceType;
    }

    public Integer getSnType() {
        return snType;
    }

    public void setSnType(Integer snType) {
        this.snType = snType;
    }

    public Integer getSpType() {
        return spType;
    }

    public void setSpType(Integer spType) {
        this.spType = spType;
    }

    public String getVmiCode() {
        return vmiCode;
    }

    public void setVmiCode(String vmiCode) {
        this.vmiCode = vmiCode;
    }


    public String getRfidCode() {
        return rfidCode;
    }


    public void setRfidCode(String rfidCode) {
        this.rfidCode = rfidCode;
    }

}
