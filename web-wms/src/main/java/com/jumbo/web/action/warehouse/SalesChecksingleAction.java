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
import java.util.ListIterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.jumbo.Constants;
import com.jumbo.util.BeanUtilSupport;
import com.jumbo.util.FormatUtil;
import com.jumbo.util.JsonUtil;
import com.jumbo.util.StringUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.web.manager.print.PrintManager;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.DropDownListManager;
import com.jumbo.wms.manager.authorization.AuthorizationManager;
import com.jumbo.wms.manager.outbound.AdCheckManager;
import com.jumbo.wms.manager.outbound.OutboundInfoManager;
import com.jumbo.wms.manager.sn.SnManager;
import com.jumbo.wms.manager.warehouse.PickingListPackageManager;
import com.jumbo.wms.manager.warehouse.PickingListPrintManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerQuery;
import com.jumbo.wms.manager.warehouse.excel.ExcelReadManager;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.authorization.OperationUnitType;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.SkuRfid;
import com.jumbo.wms.model.baseinfo.SkuSnType;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.mongodb.StaCheckRecord;
import com.jumbo.wms.model.mongodb.TwicePickingBarCode;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.warehouse.BiChannelSkuSupplies;
import com.jumbo.wms.model.warehouse.GiftLine;
import com.jumbo.wms.model.warehouse.PackageInfo;
import com.jumbo.wms.model.warehouse.PickingListCommand;
import com.jumbo.wms.model.warehouse.PickingListPackageCommand;
import com.jumbo.wms.model.warehouse.ReturnApplicationCommand;
import com.jumbo.wms.model.warehouse.SkuSnCommand;
import com.jumbo.wms.model.warehouse.SkuSnStatus;
import com.jumbo.wms.model.warehouse.StaAdditionalLine;
import com.jumbo.wms.model.warehouse.StaDeliveryInfoCommand;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.wms.model.warehouse.StarbucksIcePackage;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.WhAddStatusMode;

import loxia.support.excel.ReadStatus;
import loxia.support.jasperreport.JasperPrintFailureException;
import loxia.support.jasperreport.JasperReportNotFoundException;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

/**
 * 单件核对/分拣核对/二次分拣建议
 * 
 * @author fanht
 * 
 */
public class SalesChecksingleAction extends BaseJQGridProfileAction {

    private static final long serialVersionUID = 7303787769148135283L;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private WareHouseManagerQuery wareHouseManagerQuery;
    @Autowired
    private WareHouseManagerQuery whQuery;
    @Autowired
    private DropDownListManager dropDownListManager;
    @Autowired
    private ExcelReadManager excelReadManager;
    @Autowired
    private PickingListPrintManager pickingListPrintManager;
    @Autowired
    private AuthorizationManager authorizationManager;
    @Autowired
    private PickingListPackageManager pickingListPackageManager;
    @Autowired
    private SnManager snManager;
    @Autowired
    private PrintManager printManager;
    @Autowired
    private AdCheckManager adCheckManager;

    private PickingListCommand cmd;

    private StockTransApplicationCommand sta;

    private List<ChooseOption> plStatus;

    private String lineNo;

    private String barcode;

    private Long staId;

    private String iptPlCode; // 批次号扫描
    private String sn;
    private List<String> snlist = new ArrayList<String>();

    private String status;

    private List<GiftLine> glList;

    private List<PackageInfo> packageInfos;

    private List<StaLine> staLineList;

    private List<StockTransApplication> staList;

    private Long pickinglistId;

    private Long pickinglistPackageId;

    private String ruleCode;

    private File file;

    private Long wid;
    private String code;

    private Long skuId;

    private int diekNo;

    private String staCode;

    private String fileUrl;

    private String fileName;
    private String slipCodes;

    private List<String> barCodeList;
    private List<String> transNos;

    private Long stalineId;

    private Boolean isNeedCheck = true;

    private Long userId;
    @Autowired
    private OutboundInfoManager outboundInfoManager;

    private String skuOrigin;

    private List<String> rfid = new ArrayList<String>();

    private String skuBarCode;

    private String starbuckCode;
    public String getStarbuckCode() {
        return starbuckCode;
    }

    public void setStarbuckCode(String starbuckCode) {
        this.starbuckCode = starbuckCode;
    }


    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

    public List<String> getTransNos() {
        return transNos;
    }

    public void setTransNos(List<String> transNos) {
        this.transNos = transNos;
    }

    /**
     * 包材条码
     */
    private List<StaAdditionalLine> saddlines;

    public List<StaAdditionalLine> getSaddlines() {
        return saddlines;
    }

    public void setSaddlines(List<StaAdditionalLine> saddlines) {
        this.saddlines = saddlines;
    }

    /**
     * 配货清单ID
     */
    private Long pickId;

    public List<String> getBarCodeList() {
        return barCodeList;
    }

    public void setBarCodeList(List<String> barCodeList) {
        this.barCodeList = barCodeList;
    }

    public Long getWid() {
        return wid;
    }

    public void setWid(Long wid) {
        this.wid = wid;
    }

    /**
     * 单件核对
     * 
     * @return
     */
    public String entPlCheck() {
        plStatus = dropDownListManager.findPickingListStatusForVerify();
        return SUCCESS;
    }

    /**
     * O2O单件核对
     * 
     * @return
     */
    public String entPlCheckOtwoo() {
        plStatus = dropDownListManager.findPickingListStatusForVerify();
        return SUCCESS;
    }

    /**
     * 分拣核对
     * 
     * @return
     */
    public String entSortingCheck() {
        plStatus = dropDownListManager.findPickingListStatusForVerify();
        return SUCCESS;
    }

    /**
     * 单件核对/大件
     * 
     * @return
     */
    public String checksinglebig() {
        plStatus = dropDownListManager.findPickingListStatusForVerify();
        return SUCCESS;
    }

    /**
     * O2OQS订单批量核对
     * 
     * @return
     */
    public String entO2OQSSortingCheck() {
        plStatus = dropDownListManager.findPickingListStatusForVerify();
        return SUCCESS;
    }

    /**
     * 多件核对(手工)
     * 
     * @return String
     * @throws
     */
    public String entHandwrokSortingCheck() {
        plStatus = dropDownListManager.findPickingListStatusForVerify();
        return SUCCESS;
    }

    /**
     * 多件核对(后置送货单)
     * 
     * @return String
     * @throws
     */
    public String aCheckPostPrint() {
        plStatus = dropDownListManager.findPickingListStatusForVerify();
        return SUCCESS;
    }

    /**
     * 分拣核对
     * 
     * @return
     */
    public String entGiftSortingCheck() {
        plStatus = dropDownListManager.findPickingListStatusForVerify();
        return SUCCESS;
    }


    /**
     * 二次分拣意见
     * 
     * @return
     */
    public String pickingSuggestEntry() {
        return SUCCESS;

    }

    /**
     * 查询配货批核对详情
     * 
     * @return
     */
    public String getOcpStalByPl() {
        setTableConfig();
        List<StaLineCommand> list = wareHouseManagerQuery.findOccpiedStaLineByPlId(cmd.getId(), tableConfig.getSorts());
        request.put(JSON, toJson(list));
        return JSON;
    }

    /**
     * 单件核对 执行核对
     * 
     * @return
     * @throws JSONException
     */
    public String checkStaList() throws JSONException {
        JSONObject result = new JSONObject();
        result.put("result", SUCCESS);
        try {
            wareHouseManager.checkSingleSta(sta.getId(), sta.getTrackingNo(), lineNo, sn, userDetails.getUser().getId());
        } catch (BusinessException e) {
            result.put("result", ERROR);
            result.put("msg", getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
            if (e.getErrorCode() == ErrorCode.STA_CANCELED) {
                result.put("isCancel", true);
            }
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * O2O单件核对 执行核对
     * 
     * @return
     * @throws JSONException
     */
    public String checkStaListOtwoo() throws JSONException {
        JSONObject result = new JSONObject();
        result.put("result", SUCCESS);
        if (!StringUtils.hasText(slipCodes)) {
            request.put("result", ERROR);
            request.put("msg", "核对内容为空");
            request.put(JSON, result);
            return JSON;
        }
        try {
            Map<String, Object> orderId = wareHouseManager.checkSingleStaOtwoo(slipCodes, userDetails.getCurrentOu().getId(), userDetails.getUser().getId());
            result.put("orderId", orderId.get("orderId"));
            result.put("quickPl", orderId.get("quickPl"));
            result.put("trackingNo", orderId.get("trackingNo"));
            result.put("staIds", orderId.get("staIds"));
        } catch (BusinessException e) {
            result.put("result", ERROR);
            result.put("msg", getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
            if (e.getErrorCode() == ErrorCode.STA_CANCELED) {
                result.put("isCancel", true);
            }
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 大件复核称重/单件
     * 
     * @return
     * @throws JSONException
     */
    public String checkSingleStaAndSalesStaOutbound() throws JSONException {
        JSONObject result = new JSONObject();
        result.put("result", SUCCESS);
        try {
            wareHouseManager.checkSingleStaAndSalesStaOutbound(sta.getId(), sta.getTrackingNo(), lineNo, sn, userDetails.getUser().getId(), userDetails.getCurrentOu().getId(), sta.getWeight(), saddlines);
        } catch (BusinessException e) {
            result.put("result", ERROR);
            result.put("msg", getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
            if (e.getErrorCode() == ErrorCode.STA_CANCELED) {
                result.put("isCancel", true);
            }
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 分拣核对，执行核对
     * 
     * @return
     * @throws JSONException
     */
    public String staSortingCheck() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            StockTransApplicationCommand stas = wareHouseManager.findStaCmdById(sta.getId());
            if (StockTransApplicationStatus.CANCEL_UNDO.equals(stas.getStatus())) {
                wareHouseManager.updateisCheckQty(sta.getId());
                throw new BusinessException(ErrorCode.STA_CANCELED_QTY);
            }
            wareHouseManager.staSortingCheck(snlist, glList, packageInfos, sta.getId(), userDetails.getCurrentOu().getId(), lineNo, userDetails.getUser().getId());
            outboundInfoManager.addStaSkuOrigin(skuOrigin, sta.getId());
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            result.put("msg", getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 分拣核对，执行核对
     * 
     * @return
     * @throws JSONException
     */
    public String staSortingCheckAndsalesStaOutBoundHand() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            StockTransApplicationCommand stas = wareHouseManager.findStaCmdById(sta.getId());
            if (StockTransApplicationStatus.CANCEL_UNDO.equals(stas.getStatus())) {
                wareHouseManager.updateisCheckQty(sta.getId());
                throw new BusinessException(ErrorCode.STA_CANCELED_QTY);
            }
            if (packageInfos == null) {
                packageInfos = new ArrayList<PackageInfo>();
            }
            wareHouseManager.staSortingCheckAndsalesStaOutBoundHand(snlist, glList, sta.getTrackingNo(), packageInfos, sta.getId(), userDetails.getCurrentOu().getId(), lineNo, userDetails.getUser().getId(), sta.getWeight(), saddlines);

            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            result.put("msg", getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, result);
        return JSON;
    }

    public String operationUnitQuery() {
        JSONObject result = new JSONObject();
        Warehouse warehouse = wareHouseManager.operationUnitQuery(userDetails.getCurrentOu().getId());
        try {
            result.put("result", SUCCESS);
            result.put("warehouse", JsonUtil.obj2json(warehouse));
        } catch (JSONException e) {
            // e.printStackTrace();
            if (log.isErrorEnabled()) {
                log.error("operationUnitQuery error:", e);
            };
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * O2OQS批次分拣核对，执行核对
     * 
     * @return
     * @throws JSONException
     */
    public String o2oqsSortingCheck() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            for (StockTransApplication s : staList) {
                StockTransApplicationCommand stas = wareHouseManager.findStaCmdById(s.getId());
                if (StockTransApplicationStatus.CANCEL_UNDO.equals(stas.getStatus())) {
                    wareHouseManager.updateisCheckQty(sta.getId());
                    throw new BusinessException(ErrorCode.STA_CANCELED_QTY);
                }
            }
            wareHouseManager.o2oqsSortingCheck(pickinglistPackageId, packageInfos, pickinglistId, staList, userDetails.getCurrentOu().getId(), lineNo, userDetails.getUser().getId());
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            result.put("msg", getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 取消已处理的作业单，直接修改核对数量
     * 
     * @return
     * @throws JSONException
     */
    public String updateisCheckQty() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            wareHouseManager.updateisCheckQty(sta.getId());
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            result.put("msg", getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 判断是否已核对
     * 
     * @return
     * @throws JSONException
     */
    public String checkisCheckQty() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            wareHouseManager.checkisCheckQty(sta.getId());
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            result.put("msg", getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 判断是否大件复核称重
     * 
     * @return
     * @throws JSONException
     */
    public String checkIsWeigh() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            OperationUnit ou = getCurrentOu();
            if (ou != null) {
                boolean b = wareHouseManager.checkWeigh(ou.getId());
                if (b) {
                    result.put("result", SUCCESS);
                } else {
                    result.put("result", ERROR);
                }
            } else {
                result.put("result", ERROR);
            }
        } catch (BusinessException e) {
            result.put("result", "exception");
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 配货清单Json,配货中+部分出库 单件核对、分拣核对……
     * 
     * @return
     * @throws JSONException
     */
    public String searchCheckList() throws JSONException {
        setTableConfig();
        if (cmd == null) {
            cmd = new PickingListCommand();
        }
        if (cmd != null) {
            cmd.setPickingTime(FormatUtil.getDate(cmd.getPickingTime1()));
            cmd.setExecutedTime(FormatUtil.getDate(cmd.getExecutedTime1()));
        }
        cmd.setWarehouse(userDetails.getCurrentOu());
        request.put(JSON, toJson(wareHouseManager.findPickingListForVerifyByCmd1(tableConfig.getStart(), tableConfig.getPageSize(), cmd, tableConfig.getSorts())));
        return JSON;
    }

    /**
     * 配货清单Json,配货中+部分出库 单件核对、分拣核对……
     * 
     * @return
     * @throws JSONException
     */
    public String searchCheckListOtwoo() throws JSONException {
        setTableConfig();
        if (cmd == null) {
            cmd = new PickingListCommand();
        }
        if (cmd != null) {
            cmd.setPickingTime(FormatUtil.getDate(cmd.getPickingTime1()));
            cmd.setExecutedTime(FormatUtil.getDate(cmd.getExecutedTime1()));
        }
        cmd.setWarehouse(userDetails.getCurrentOu());
        request.put(JSON, toJson(wareHouseManager.findPickingListForVerifyByCmdOtwoo(tableConfig.getStart(), tableConfig.getPageSize(), cmd, tableConfig.getSorts())));
        return JSON;
    }

    /**
     * 配货清单Json,后置装箱清单、多件订单
     * 
     * @return
     * @throws JSONException
     */
    public String searchPickingList() throws JSONException {
        setTableConfig();
        if (cmd == null) {
            cmd = new PickingListCommand();
        }
        if (cmd != null) {
            cmd.setPickingTime(FormatUtil.getDate(cmd.getPickingTime1()));
            cmd.setExecutedTime(FormatUtil.getDate(cmd.getExecutedTime1()));
        }
        cmd.setWarehouse(userDetails.getCurrentOu());
        request.put(JSON, toJson(wareHouseManager.findPickingListForVerifyByCmd2(tableConfig.getStart(), tableConfig.getPageSize(), cmd, tableConfig.getSorts())));
        return JSON;
    }

    /**
     * 多件核对(后置送货单)
     */
    public String findPickingListForVerifyByCodeId() throws JSONException {
        JSONObject result = new JSONObject();
        if (cmd == null) {
            cmd = new PickingListCommand();
        }
        if (cmd != null) {
            cmd.setPickingTime(FormatUtil.getDate(cmd.getPickingTime1()));
            cmd.setExecutedTime(FormatUtil.getDate(cmd.getExecutedTime1()));
        }
        cmd.setWarehouse(userDetails.getCurrentOu());
        PickingListCommand pl = null;
        pl = wareHouseManager.findPickingListForVerifyByCodeId(cmd, iptPlCode);
        if (pl != null) {
            result.put("pl", JsonUtil.obj2json(pl));
            // result.put("msg", "success");
        }

        request.put(JSON, result);
        return JSON;
    }

    /**
     * 配货清单Json,后置装箱清单、多件订单
     * 
     * @return
     * @throws JSONException
     */
    public String searchPickingList2() throws JSONException {
        setTableConfig();
        if (cmd == null) {
            cmd = new PickingListCommand();
        }
        if (cmd != null) {
            cmd.setPickingTime(FormatUtil.getDate(cmd.getPickingTime1()));
            cmd.setExecutedTime(FormatUtil.getDate(cmd.getExecutedTime1()));
        }
        cmd.setWarehouse(userDetails.getCurrentOu());
        request.put(JSON, toJson(wareHouseManager.findPickingListForVerifyByCmd3(tableConfig.getStart(), tableConfig.getPageSize(), cmd, tableConfig.getSorts())));
        return JSON;
    }

    /**
     * 配货清单Json,大件、奢侈品仓订单
     * 
     * @return
     * @throws JSONException
     */
    public String searchPickingListByBig() throws JSONException {
        setTableConfig();
        if (cmd == null) {
            cmd = new PickingListCommand();
        }
        if (cmd != null) {
            cmd.setPickingTime(FormatUtil.getDate(cmd.getPickingTime1()));
            cmd.setExecutedTime(FormatUtil.getDate(cmd.getExecutedTime1()));
        }
        cmd.setWarehouse(userDetails.getCurrentOu());
        request.put(JSON, toJson(wareHouseManager.findPickingListForVerifyByBig(tableConfig.getStart(), tableConfig.getPageSize(), cmd, tableConfig.getSorts())));
        return JSON;
    }

    /**
     * 配货清单Json,后置装箱清单、多件订单、O2OQS订单
     * 
     * @return
     * @throws JSONException
     */
    public String searchO2OQSPickingList() throws JSONException {
        setTableConfig();
        if (cmd == null) {
            cmd = new PickingListCommand();
        }
        if (cmd != null) {
            cmd.setPickingTime(FormatUtil.getDate(cmd.getPickingTime1()));
            cmd.setExecutedTime(FormatUtil.getDate(cmd.getExecutedTime1()));
        }
        cmd.setWarehouse(userDetails.getCurrentOu());
        request.put(JSON, toJson(wareHouseManager.findPickingListForVerifyByCmd4(tableConfig.getStart(), tableConfig.getPageSize(), cmd, tableConfig.getSorts())));
        return JSON;
    }

    /**
     * 多件核对 后置打印 明细查询
     * 
     * @return
     * @throws JSONException
     */
    public String findPickingDetail() throws JSONException {
        setTableConfig();
        List<PickingListCommand> list = pickingListPrintManager.findPickListDetail(pickinglistId);
        request.put(JSON, toJson(list));
        return JSON;
    }

    /**
     * 多件核对 后置打印 根据配货批次查找作业单
     * 
     * @return
     * @throws JSONException
     */
    public String findStaByPinkId() throws JSONException {
        List<StockTransApplication> list = wareHouseManager.findStaByPickingList(pickinglistId, this.userDetails.getCurrentOu().getId());
        request.put(JSON, JsonUtil.collection2json(list));
        return JSON;
    }

    /**
     * 多件核对 后置打印优化
     * 
     * @return
     * @throws JSONException String
     * @throws
     */
    public String findAllPostCheckInfo() throws JSONException {
        List<StockTransApplicationCommand> list = wareHouseManager.findAllStaAndDeliveryInfoByPickingList(pickinglistId, this.userDetails.getCurrentOu().getId());
        ListIterator<StockTransApplicationCommand> iter = list.listIterator();
        while (iter.hasNext()) {
            StockTransApplicationCommand sta = iter.next();
            List<StaLineCommand> line = wareHouseManager.findStaLineCommandListByStaId(sta.getId());
            StringBuilder sb = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();
            sb.append(line.size()).append(":");
            for (int i = 0; i < line.size(); i++) {
                StaLineCommand li = line.get(i);
                if (0 == i) {
                    sb.append(li.getId()).append(";").append(li.getQuantity());
                    // 商品ID：计划量，核对量;
                    sb2.append(li.getId()).append(":").append(li.getQuantity()).append(",").append(li.getCompleteQuantity());
                } else {
                    sb.append(",").append(li.getId()).append(";").append(li.getQuantity());
                    sb2.append(";").append(li.getId()).append(":").append(li.getQuantity()).append(",").append(li.getCompleteQuantity());
                }
            }
            sta.setExtInfo(sb.toString());
            sta.setExtInfo2(sb2.toString());

            iter.set(sta);
        }
        request.put(JSON, JsonUtil.collection2json(list));
        return JSON;
    }


    /**
     * 配货清单Json,配货中+部分出库 单件核对、分拣核对……
     * 
     * @return
     * @throws JSONException
     */
    public String searchCheckGiftList() throws JSONException {
        setTableConfig();
        if (cmd == null) {
            cmd = new PickingListCommand();
        }
        if (cmd != null) {
            cmd.setPickingTime(FormatUtil.getDate(cmd.getPickingTime1()));
            cmd.setExecutedTime(FormatUtil.getDate(cmd.getExecutedTime1()));
        }
        cmd.setIsSpecialPackaging(true);
        cmd.setWarehouse(userDetails.getCurrentOu());
        request.put(JSON, toJson(wareHouseManager.findPickingListForVerifyByCmd1(tableConfig.getStart(), tableConfig.getPageSize(), cmd, tableConfig.getSorts())));
        return JSON;
    }

    /**
     * 分拣核对详情列表查询
     * 
     * @return
     * @throws JSONException
     */
    public String getPickCheckList() throws JSONException {
        setTableConfig();
        String[] statu = status.split(",");
        Integer[] statusInteger = new Integer[statu.length];
        for (int i = 0; i < statu.length; i++) {
            statusInteger[i] = Integer.valueOf(statu[i]);
        }
        List<StockTransApplicationCommand> list = wareHouseManager.getPickCheckList(cmd.getId(), userDetails.getCurrentOu().getId(), statusInteger);
        request.put(JSON, toJson(list));
        return JSON;
    }

    /**
     * O2OQS批量核对详情列表查询
     * 
     * @return
     * @throws JSONException
     */
    public String getO2OQSPickCheckList() throws JSONException {
        setTableConfig();
        String[] statu = status.split(",");
        Integer[] statusInteger = new Integer[statu.length];
        for (int i = 0; i < statu.length; i++) {
            statusInteger[i] = Integer.valueOf(statu[i]);
        }
        List<StockTransApplicationCommand> list = wareHouseManager.getO2OQSPickCheckList(cmd.getId(), userDetails.getCurrentOu().getId(), statusInteger);
        request.put(JSON, toJson(list));
        return JSON;
    }

    /**
     * O2OQS批量核对装箱列表查询
     * 
     * @return
     * @throws JSONException
     */
    public String getO2OQSPackCheckList() throws JSONException {
        setTableConfig();
        String[] statu = status.split(",");
        Integer[] statusInteger = new Integer[statu.length];
        for (int i = 0; i < statu.length; i++) {
            statusInteger[i] = Integer.valueOf(statu[i]);
        }
        List<PickingListPackageCommand> list = wareHouseManager.getO2OQSPackCheckList(cmd.getId(), userDetails.getCurrentOu().getId(), statusInteger);
        request.put(JSON, toJson(list));
        return JSON;
    }

    /**
     * 获取配货批对应的新建的批次包裹
     * 
     * @return String
     * @throws
     */
    public String getPickingListPackageByPlId() {
        List<PickingListPackageCommand> list = pickingListPackageManager.findByPlIdAndStatus(pickinglistId, DefaultStatus.CREATED);
        request.put(JSON, toJson(list));
        return JSON;
    }

    /**
     * 分拣核对检查逻辑
     * 
     * @return
     * @throws Exception
     */
    public String staSlipCodeCheck() throws Exception {
        if ("".equals(sta.getPickingCode())) {
            sta.setPickingCode(null);
        }
        List<Long> lists = new ArrayList<Long>();
        if (userDetails.getCurrentOu().getOuType().getName().equals("OperationCenter")) {
            List<OperationUnit> listopc = authorizationManager.selectWarehouseByCenid(userDetails.getCurrentOu().getId());
            for (int i = 0; i < listopc.size(); i++) {
                OperationUnit opc = listopc.get(i);
                lists.add(opc.getId());
            }
        } else {
            wid = userDetails.getCurrentOu().getId();
        }
        StockTransApplicationCommand cmd = wareHouseManager.findStaForVerifyByRefSlipCode(sta.getPickingCode(), null, sta.getRefSlipCode(), wid, lists, WhAddStatusMode.CHECK.getValue());
        if (cmd != null) {
            Map<String, Object> result = new HashMap<String, Object>();
            BeanUtilSupport.describe(result, null, cmd);
            request.put(JSON, new JSONObject(result));
        }
        return JSON;
    }

    /**
     * 大件奢侈品仓分拣核对检查逻辑
     * 
     * @return
     * @throws Exception
     */
    public String staSlipCodeCheckBig() throws Exception {
        if ("".equals(sta.getPickingCode())) {
            sta.setPickingCode(null);
        }
        List<Long> lists = new ArrayList<Long>();
        if (userDetails.getCurrentOu().getOuType().getName().equals("OperationCenter")) {
            List<OperationUnit> listopc = authorizationManager.selectWarehouseByCenid(userDetails.getCurrentOu().getId());
            for (int i = 0; i < listopc.size(); i++) {
                OperationUnit opc = listopc.get(i);
                lists.add(opc.getId());
            }
        } else {
            wid = userDetails.getCurrentOu().getId();
        }
        StockTransApplicationCommand cmd = wareHouseManager.findStaForVerifyByBigRefSlipCode(sta.getPickingCode(), null, sta.getRefSlipCode(), wid, lists, WhAddStatusMode.CHECK.getValue());
        if (cmd != null) {
            Map<String, Object> result = new HashMap<String, Object>();
            BeanUtilSupport.describe(result, null, cmd);
            request.put(JSON, new JSONObject(result));
        }
        return JSON;
    }

    /**
     * 退货申请检查逻辑
     * 
     * @return
     * @throws Exception
     */
    public String staTrackingNoCheck() throws Exception {
        if ("".equals(sta.getTrackingNo())) {
            sta.setTrackingNo(null);
        }
        wid = userDetails.getCurrentOu().getId();
        ReturnApplicationCommand cmd = wareHouseManager.findReturnAppByTrackNo(wid, sta.getTrackingNo());
        if (cmd != null) {
            Map<String, Object> result = new HashMap<String, Object>();
            BeanUtilSupport.describe(result, null, cmd);
            request.put(JSON, new JSONObject(result));
        }
        return JSON;
    }

    /**
     * O2OQS核对检查逻辑
     * 
     * @return
     * @throws Exception
     */
    public String plCodeCheck() throws Exception {
        if (cmd == null) {
            cmd = new PickingListCommand();
        }
        if (cmd != null) {
            cmd.setPickingTime(FormatUtil.getDate(cmd.getPickingTime1()));
            cmd.setExecutedTime(FormatUtil.getDate(cmd.getExecutedTime1()));
        }
        cmd.setWarehouse(userDetails.getCurrentOu());
        PickingListCommand plCmd = wareHouseManager.findPLForVerifyByPLCode(cmd);
        if (plCmd != null) {
            Map<String, Object> result = new HashMap<String, Object>();
            BeanUtilSupport.describe(result, null, plCmd);
            request.put(JSON, new JSONObject(result));
        }
        return JSON;
    }

    /**
     * 分拣核对，查询订单核对明细信息：考虑合单情况 fanht
     * 
     * @return
     */
    public String staLineInfoByStaId() {
        setTableConfig();
        // request.put(JSON, toJson(wareHouseManager.findStaLineCommandListByStaId(sta.getId())));
        request.put(JSON, toJson(whQuery.findGiftStaLineListByStaId(sta.getId())));
        return JSON;
    }

    /**
     * 分拣核对，查询订单核对明细信息：考虑合单情况 fanht
     * 
     * @return
     */
    public String findStaLineByStaId() {
        request.put(JSON, JsonUtil.collection2json(wareHouseManager.findStaLineCommandListByStaId(sta.getId())));
        return JSON;
    }

    /**
     * 根据作业单查找快递单号
     * 
     * @return
     */
    public String findTrankNoByStaId() {
        StaDeliveryInfoCommand stas = wareHouseManager.findTrankNoByStaId(sta.getId());
        request.put(JSON, JsonUtil.obj2json(stas));
        return JSON;
    }

    /*************************************** 二次分拣意见 *************************************************/

    /**
     * 配货中批次列表
     * 
     * @return
     */
    public String findPickingListForPickOut() {
        setTableConfig();
        if (cmd != null) {
            cmd.setCheckedTime(FormatUtil.getDate(cmd.getCreateTime1()));
            cmd.setExecutedTime(FormatUtil.getDate(cmd.getExecutedTime1()));
        }
        request.put(JSON, toJson(wareHouseManager.findPickingListInfo(tableConfig.getStart(), tableConfig.getPageSize(), cmd, this.userDetails.getCurrentOu().getId(), tableConfig.getSorts())));
        return JSON;
    }

    /**
     * 配货清单拣货列表
     * 
     * @return
     */
    public String findPickingListDieking() {
        setTableConfig();
        if (cmd != null) {
            cmd.setCheckedTime(FormatUtil.getDate(cmd.getCreateTime1()));
            cmd.setExecutedTime(FormatUtil.getDate(cmd.getExecutedTime1()));
        }
        request.put(JSON, toJson(wareHouseManager.findPickingListDiekingSeparation(tableConfig.getStart(), tableConfig.getPageSize(), WhAddStatusMode.DIEKING.getValue(), cmd, this.userDetails.getCurrentOu().getId(), tableConfig.getSorts())));
        return JSON;
    }

    /**
     * 配货清单分拣列表
     * 
     * @return
     */
    public String findPickingListSeparation() {
        setTableConfig();
        if (cmd != null) {
            cmd.setCheckedTime(FormatUtil.getDate(cmd.getCreateTime1()));
            cmd.setExecutedTime(FormatUtil.getDate(cmd.getExecutedTime1()));
        }
        request.put(JSON, toJson(wareHouseManager.findPickingListDiekingSeparation(tableConfig.getStart(), tableConfig.getPageSize(), WhAddStatusMode.SEPARATION.getValue(), cmd, this.userDetails.getCurrentOu().getId(), tableConfig.getSorts())));
        return JSON;
    }

    /**
     * 配货批，涉及的作业单明细
     * 
     * @return
     */
    public String findStaLineByPickingId() {
        setTableConfig();
        request.put(JSON, toJson(wareHouseManagerQuery.findStaLineByPickingId(pickinglistId, this.userDetails.getCurrentOu().getId(), tableConfig.getSorts())));
        return JSON;
    }

    public String findStaLineByPickingId2() {
        setTableConfig();
        request.put(JSON, toJson(wareHouseManagerQuery.findStaLineByPickingId2(pickinglistId, this.userDetails.getCurrentOu().getId(), null)));
        return JSON;
    }

    /**
     * 二次分拣明细查询
     * 
     * @return
     * @throws JSONException
     */
    @SuppressWarnings("unchecked")
    public String findStaLineBySuggestion() throws JSONException {
        JSONObject result = new JSONObject();
        Map<String, Object> map = null;
        try {
            map = wareHouseManagerQuery.findStaLineBySuggestion(pickinglistId, iptPlCode);
            if (StringUtil.isEmpty((String) map.get("pickingCodeError"))) {
                if (StringUtil.isEmpty((String) map.get("pickingErrorStatus"))) {
                    result.put("staLineCheckList", JsonUtil.collection2json((List<StaCheckRecord>) map.get("staLineCheckList")));
                    result.put("staLineList", JsonUtil.collection2json((List<StaLineCommand>) map.get("staLineList")));
                    result.put("barcodeMap", net.sf.json.JSONObject.fromObject((Map<String, String>) map.get("barcodeMap")));
                    result.put("ruleCodeMap", net.sf.json.JSONObject.fromObject(map.get("ruleCodeMap")).toString());
                    result.put("partCheck", net.sf.json.JSONObject.fromObject(map.get("partCheck")));
                    result.put("pickingData", JsonUtil.obj2json((PickingListCommand) map.get("pickingData")));

                } else {
                    result.put("pickingErrorStatus", (String) map.get("pickingErrorStatus"));
                }

            } else {
                result.put("pickingCodeError", ERROR);
            }
            result.put("result", SUCCESS);

        } catch (Exception e) {
            result.put("msg", ERROR);
            log.error("findStaLineBySuggestion error:", e);
        }
        request.put(JSON, result);
        return JSON;
    }


    public String findRuleCodeByPickingListId() {
        JSONObject json = new JSONObject();
        List<StockTransApplicationCommand> list = wareHouseManager.findRuleCodeByPickingId(pickinglistId);
        StringBuilder sb = new StringBuilder();
        if (null != list && list.size() > 0) {
            for (StockTransApplicationCommand l : list) {
                sb.append(l.getRuleCode() + ":" + l.getIntStaStatus() + ";");
            }
        }
        try {
            json.put("value", sb.toString());
        } catch (JSONException e) {
            // e.printStackTrace();
            if (log.isErrorEnabled()) {
                log.error("findRuleCodeByPickingListId error:" + pickinglistId, e);
            };
        }
        request.put(JSON, json);
        return JSON;
    }

    public String twicePickingByBarcode() throws Exception {
        JSONObject json = new JSONObject();
        try {
            if (userId == null) {
                userId = this.userDetails.getUser().getId();
            }
            if (isNeedCheck) {
                adCheckManager.storeLogisticsSendByTwoPicking(pickinglistId, stalineId, userId);
            }

            Map<String, Object> map = wareHouseManager.twicePickingByBarcode(pickinglistId, stalineId, code, userId, isNeedCheck);
            json.put("data", map);
            json.put("result", SUCCESS);
        } catch (BusinessException e) {
            String errorMsg = "";
            if (e.getErrorCode() != ErrorCode.ERROR_NOT_SPECIFIED) {
                errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()) + Constants.HTML_LINE_BREAK;
            }
            if (e.getMessage() != null) {
                errorMsg += e.getMessage();
            }
            BusinessException current = e;
            while (current.getLinkedException() != null) {
                current = current.getLinkedException();
                errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + current.getErrorCode(), current.getArgs()) + Constants.HTML_LINE_BREAK;
            }
            json.put("result", ERROR);
            json.put("msg", errorMsg);
        } catch (Exception e) {
            json.put("result", "systemError");
            log.error(e.getMessage(), e);
        }
        request.put(JSON, json);
        return JSON;
    }

    public String findTwicePickingInfoByPickingId() {
        try {
            List<TwicePickingBarCode> tpbcList = wareHouseManager.findTwicePickingInfoByPickingId(pickinglistId);
            request.put(JSON, JsonUtil.collection2json(tpbcList));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return JSON;
    }

    /**
     * 释放周转箱及人工集货库位
     * 
     * @return
     */
    public String resetBoxAndCollection() {
        try {
            wareHouseManager.resetBoxAndCollection(pickinglistId, userDetails.getUser().getId(), this.userDetails.getCurrentOu().getId());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return JSON;
    }


    public String findStaLineByPickingIdDiek() {
        setTableConfig();
        request.put(JSON, toJson(wareHouseManager.findStaLineByPickingIdDiek(pickinglistId, this.userDetails.getCurrentOu().getId(), tableConfig.getSorts())));
        return JSON;
    }

    /**
     * 二次分拣意见，配货批核对
     * 
     * @return
     */
    public String checkPinkingListByCode() throws Exception {
        JSONObject result = new JSONObject();
        List<PickingListCommand> list = wareHouseManager.findPickingListInfo(cmd, this.userDetails.getCurrentOu().getId(), diekNo, null);
        if (list != null && list.size() == 1) {
            result.put("result", SUCCESS);
            result.put("data", JsonUtil.obj2json(list.get(0)));
        }
        request.put(JSON, result);
        return JSON;
    }

    public String getSingleCheckOrder() throws JSONException {
        JSONObject result = new JSONObject();
        PickingListCommand pl = null;
        if (userDetails.getCurrentOu().getOuType().getName().equals(OperationUnitType.OUTYPE_OPERATION_CENTER)) {
            List<OperationUnit> ouList = authorizationManager.findOperationUnitList(userDetails.getCurrentOu().getId());
            List<Long> idList = new ArrayList<Long>();
            for (OperationUnit ou : ouList) {
                idList.add(ou.getId());
            }
            pl = wareHouseManager.getSingleCheckOrder(null, idList, code);
        } else {
            pl = wareHouseManager.getSingleCheckOrder(userDetails.getCurrentOu().getId(), null, code);
        }
        if (pl != null) {
            result.put("pl", JsonUtil.obj2json(pl));
        }
        request.put(JSON, result);
        return JSON;
    }


    public String findSlipCodeByid() throws JSONException {
        JSONObject result = new JSONObject();
        PickingListCommand pl = null;
        pl = wareHouseManager.findSlipCodeByid(userDetails.getCurrentOu().getId(), code);
        if (pl != null) {
            result.put("pl", JsonUtil.obj2json(pl));
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 根据staCode 查询出相关单据号和配货清单编码
     * 
     * @return
     * @throws JSONException
     */
    public String findSlipCodeAndPickingListCodeByStaCode() throws JSONException {
        JSONObject result = new JSONObject();
        PickingListCommand pls = null;
        pls = wareHouseManager.findSlipCodeAndPickingListCodeByStaCode(userDetails.getCurrentOu().getId(), code);
        if (pls != null) {
            result.put("pls", JsonUtil.obj2json(pls));
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 查询仓库
     * 
     * @return
     * @throws JSONException
     */
    public String findWareHouseById() throws JSONException {
        JSONObject result = new JSONObject();
        Warehouse warehouse = null;
        warehouse = wareHouseManager.findWareHouseById(userDetails.getCurrentOu().getId());
        if (warehouse != null) {
            result.put("warehouse", JsonUtil.obj2json(warehouse));
        }
        request.put(JSON, result);
        return JSON;
    }
    
    
    /**
     * 查询商品
     * 
     * @return
     * @throws JSONException
     */
    public String findSkuRfidByBarCode() throws JSONException {
        JSONObject result = new JSONObject();
        Sku sku = null;
        sku = wareHouseManager.findSkuRfidByBarCode(barcode, userDetails.getCurrentOu().getId());
        if (sku != null) {
            result.put("sku", JsonUtil.obj2json(sku));
        }
        request.put(JSON, result);
        return JSON;
    }

    public String findRfidResult() throws JSONException {
        JSONObject result = new JSONObject();
        List<SkuRfid> skuRfidList = null;
        skuRfidList = wareHouseManager.findRfidResult(skuBarCode, rfid);
        request.put(JSON, result);
        return JSON;
    }

    /***
     * 出库sn号导入
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    public String salesOutSnImport() {
        List<String> errorMsg = new ArrayList<String>();
        if (file == null) {
            return ERROR;
        } else {
            try {
                Map<String, Object> result = excelReadManager.salesOutSnImport(file, sta.getId(), userDetails.getCurrentOu().getId());
                ReadStatus rs = (ReadStatus) result.get("readStatus");
                request.put("result", ERROR);
                if (rs.getStatus() == ReadStatus.STATUS_SUCCESS) {
                    request.put("result", SUCCESS);
                    request.put("snList", JsonUtil.collection2json((List<SkuSnCommand>) result.get("snList")));
                } else {
                    request.put("snList", JsonUtil.collection2json(new ArrayList<SkuSnCommand>()));
                }
                errorMsg.addAll(getExcelImportErrorMsg(rs));
            } catch (BusinessException e) {
                request.put("result", ERROR);
                request.put("snList", JsonUtil.collection2json(new ArrayList<SkuSnCommand>()));
                String msg = this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs());
                errorMsg.add(msg);
            }
            request.put("message", JsonUtil.collection2json(errorMsg));
        }
        return SUCCESS;
    }

    /**
     * 新增运单
     * 
     * @return
     */
    public String staAddTrankNo() throws JSONException {
        List<String> errorMsg = new ArrayList<String>();
        JSONObject result = new JSONObject();
        if (wareHouseManager.isCodByStaId(sta.getId())) {
            result.put("msg", "COD订单不能拆包裹");
            result.put("errMsg", "COD订单不能拆包裹");
        } else {
            StockTransApplication obj = wareHouseManager.findStaCmdById(sta.getId());
            if (obj == null) {
                result.put("msg", "系统异常：找不到作业单！");
            }
            try {
                String packId = wareHouseManager.insertPackageBySta(obj);
                result.put("msg", SUCCESS);
                result.put("trankCode", packId);
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
        }

        request.put(JSON, result);
        return JSON;
    }

    public String findBarcodeBySn() throws JSONException {
        JSONObject result = new JSONObject();
        SkuSnCommand skusn = wareHouseManager.findSn(sn, SkuSnStatus.USING, userDetails.getCurrentOu().getId());
        if (skusn != null) {
            result.put("result", SUCCESS);
            result.put("barcode", skusn.getBarcode());
            result.put("sn", skusn.getSn());
            result.put("id", skusn.getId());
            result.put("staId", skusn.getStaId());
            result.put("snTypeMSR", skusn.getSpType());
            if (StringUtil.isEmpty(skusn.getSnType())) {
                result.put("snType", "typeerror");
            } else {
                if (SkuSnType.NO_BARCODE_SKU.equals(SkuSnType.valueOf(Integer.parseInt(skusn.getSnType())))) {
                    result.put("snType", "ok");
                } else {
                    result.put("snType", "typeerror");
                }
            }
        } else {
            result.put("result", ERROR);
        }
        request.put(JSON, result);
        return JSON;
    }

    public String findpaperSkuByBarCode() throws JSONException {
        JSONObject result = new JSONObject();
        BiChannelSkuSupplies biChannelSkuSupplies = wareHouseManager.findpaperSkuByBarCode(barcode, staId, userDetails.getCurrentOu().getId());
        if (biChannelSkuSupplies != null) {
            result.put("result", SUCCESS);
            result.put("msg", wareHouseManager.findSkuById(biChannelSkuSupplies.getPaperSku()));
        } else {
            result.put("result", ERROR);
        }
        request.put(JSON, result);
        return JSON;
    }


    public String findSnBySkuId() throws JSONException {
        JSONObject result = new JSONObject();
        SkuSnCommand skusn = wareHouseManagerQuery.findSnBySkuId(skuId, sn, SkuSnStatus.USING, userDetails.getCurrentOu().getId());
        if (skusn != null) {
            result.put("result", SUCCESS);
            result.put("skuId", skusn.getId());
            result.put("sn", skusn.getSn());
        } else {
            result.put("result", ERROR);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 保存STV拍照图片路径
     */
    public String creatStvCheckImg() throws Exception {
        JSONObject result = new JSONObject();
        try {
            wareHouseManager.creatStvCheckImg(staCode, fileUrl, fileName);
            // System.out.println(staCode + " " + fileUrl);
            result.put("result", SUCCESS);
        } catch (Exception e) {
            result.put("result", ERROR);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 验证SN卡号状态
     * 
     * @return
     * @throws JSONException
     */
    public String checkSnStatus() throws JSONException {
        JSONObject result = new JSONObject();
        result.put("result", SUCCESS);
        try {
            snManager.checkSnStatus(userDetails.getCurrentOu().getId(), sn);
        } catch (BusinessException e) {
            result.put("result", ERROR);
            result.put("msg", getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 通过sn号查找是否有绑定的stv
     * 
     * @return
     * @throws JSONException
     */
    public String getStaIdBySnStv() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            StockTransApplication sta = snManager.getStaIdBySnStv(userDetails.getCurrentOu().getId(), sn);
            if (sta == null) {
                result.put("staid", "null");
            } else {
                result.put("staid", sta.getId());
                result.put("stacode", sta.getCode());
            }
            result.put("result", SUCCESS);
        } catch (Exception e) {
            result.put("result", ERROR);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 卡绑定STV
     * 
     * @return
     * @throws JSONException
     */
    public String cardBindingStv() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            snManager.cardBindingStv(userDetails.getCurrentOu().getId(), sn, sta.getId());
            result.put("result", SUCCESS);
        } catch (Exception e) {
            result.put("result", ERROR);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 查询单件STA的STV是否绑定SN号
     * 
     * @return
     * @throws JSONException
     */
    public String checkStvBinding() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            SkuSnCommand sn = snManager.checkStvBinding(sta.getId());
            if (sn == null) {
                result.put("stv", "null");
            } else {
                result.put("stv", "notnull");
            }
            result.put("result", SUCCESS);
        } catch (Exception e) {
            result.put("result", ERROR);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 激活卡状态
     * 
     * @return
     * @throws JSONException
     */
    public String activateCardStatus() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            String msg = snManager.activateCardStatus(sta.getId(), userDetails.getUser().getId(), sn);
            result.put("result", SUCCESS);
            result.put("msg", msg);
        } catch (Exception e) {
            log.error("card activate  error:", e);
            // 接口异常
            result.put("msg", "unusual");
            result.put("result", ERROR);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 通过BarCode找到商品对应的mapping规则格式化卡号数据存入SN号表
     * 
     * @return
     * @throws JSONException
     */
    public String formatCardToSn() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            Map<String, String> msg = snManager.formatCardToSn(barCodeList, userDetails.getCurrentOu().getId(), sn, userDetails.getUser().getId());
            if (msg.containsKey("snok")) {
                result.put("rs", "snok");
                result.put("msg", "");
            } else {
                result.put("rs", "snerror");
                result.put("msg", msg.get("snerror"));
            }
            result.put("result", SUCCESS);
        } catch (Exception e) {
            result.put("result", ERROR);
        }
        request.put(JSON, result);
        return JSON;
    }

    public String printSlipCode() {
        JasperPrint jp;
        try {
            jp = printManager.printSlipCode(slipCodes);
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
     * 根据配货清单ID获取已取消的作业单数
     * 
     * @return
     * @throws JSONException
     */
    public String findCancelCountByPickId() throws JSONException {
        JSONObject result = new JSONObject();
        Long count = wareHouseManager.findCancelCountByPickId(pickId);
        result.put("count", count);
        request.put(JSON, result);
        return JSON;
    }
    
    
    /**
     * 星巴克冰袋配置
     */
    public String findStarbucksIcePackage() throws JSONException {
        JSONObject json = new JSONObject();
        List<StarbucksIcePackage> sList =wareHouseManager.findStarbucksIcePackage();
        json.put("sList", sList);
        request.put(JSON, json);
        return JSON;
    }

    /**
     * 星巴克冰袋配置
     */
    public String findStarbucksDetail() throws JSONException {
        request.put(JSON, JsonUtil.obj2json(wareHouseManager.findStarbucksDetail(starbuckCode)));
        return JSON;
    }


    public StockTransApplicationCommand getSta() {
        return sta;
    }

    public void setSta(StockTransApplicationCommand sta) {
        this.sta = sta;
    }

    public PickingListCommand getCmd() {
        return cmd;
    }

    public void setCmd(PickingListCommand cmd) {
        this.cmd = cmd;
    }

    public String getLineNo() {
        return lineNo;
    }

    public void setLineNo(String lineNo) {
        this.lineNo = lineNo;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public List<ChooseOption> getPlStatus() {
        return plStatus;
    }

    public void setPlStatus(List<ChooseOption> plStatus) {
        this.plStatus = plStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getSnlist() {
        return snlist;
    }

    public void setSnlist(List<String> snlist) {
        this.snlist = snlist;
    }

    public List<PackageInfo> getPackageInfos() {
        return packageInfos;
    }

    public void setPackageInfos(List<PackageInfo> packageInfos) {
        this.packageInfos = packageInfos;
    }

    public List<StaLine> getStaLineList() {
        return staLineList;
    }

    public void setStaLineList(List<StaLine> staLineList) {
        this.staLineList = staLineList;
    }

    public List<StockTransApplication> getStaList() {
        return staList;
    }

    public void setStaList(List<StockTransApplication> staList) {
        this.staList = staList;
    }

    public Long getPickinglistId() {
        return pickinglistId;
    }

    public void setPickinglistId(Long pickinglistId) {
        this.pickinglistId = pickinglistId;
    }

    public Long getPickinglistPackageId() {
        return pickinglistPackageId;
    }

    public void setPickinglistPackageId(Long pickinglistPackageId) {
        this.pickinglistPackageId = pickinglistPackageId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<GiftLine> getGlList() {
        return glList;
    }

    public void setGlList(List<GiftLine> glList) {
        this.glList = glList;
    }

    public int getDiekNo() {
        return diekNo;
    }

    public void setDiekNo(int diekNo) {
        this.diekNo = diekNo;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getIptPlCode() {
        return iptPlCode;
    }

    public void setIptPlCode(String iptPlCode) {
        this.iptPlCode = iptPlCode;
    }

    public Long getPickId() {
        return pickId;
    }

    public void setPickId(Long pickId) {
        this.pickId = pickId;
    }

    public String getSlipCodes() {
        return slipCodes;
    }

    public void setSlipCodes(String slipCodes) {
        this.slipCodes = slipCodes;
    }

    public String getRuleCode() {
        return ruleCode;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }

    public Long getStalineId() {
        return stalineId;
    }

    public void setStalineId(Long stalineId) {
        this.stalineId = stalineId;
    }

    public Boolean getIsNeedCheck() {
        return isNeedCheck;
    }

    public void setIsNeedCheck(Boolean isNeedCheck) {
        this.isNeedCheck = isNeedCheck;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

public List<String> getRfid() {
        return rfid;
    }

 public void setRfid(List<String> rfid) {
        this.rfid = rfid;
    }


    public String getSkuBarCode() {
        return skuBarCode;
    }

    public void setSkuBarCode(String skuBarCode) {
        this.skuBarCode = skuBarCode;
    }



}
