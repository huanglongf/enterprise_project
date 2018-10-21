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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import loxia.support.jasperreport.JasperPrintFailureException;
import loxia.support.jasperreport.JasperReportNotFoundException;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.jumbo.Constants;
import com.jumbo.manager.ExcelExportManager;
import com.jumbo.util.BeanUtilSupport;
import com.jumbo.util.FormatUtil;
import com.jumbo.util.JsonUtil;
import com.jumbo.util.StringUtil;
import com.jumbo.util.zip.ZipUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.web.manager.MsgToWcsThread;
import com.jumbo.web.manager.print.PrintManager;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.DropDownListManager;
import com.jumbo.wms.manager.baseinfo.BaseinfoManager;
import com.jumbo.wms.manager.expressDelivery.TransOlManagerProxy;
import com.jumbo.wms.manager.sn.SnManager;
import com.jumbo.wms.manager.warehouse.OperationUnitManager;
import com.jumbo.wms.manager.warehouse.PickingListManager;
import com.jumbo.wms.manager.warehouse.SkuSizeConfigManager;
import com.jumbo.wms.manager.warehouse.TransportServiceManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerCancel;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExe;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExecute;
import com.jumbo.wms.manager.warehouse.WareHouseManagerQuery;
import com.jumbo.wms.manager.warehouse.WareHouseOutBoundManager;
import com.jumbo.wms.manager.warehouse.excel.ExcelWriterManager;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.authorization.OperationUnitType;
import com.jumbo.wms.model.automaticEquipment.WcsInterfaceType;
import com.jumbo.wms.model.automaticEquipment.Zoon;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.TransEmsInfo;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.command.GiftLineCommand;
import com.jumbo.wms.model.command.SkuBarcodeCommand;
import com.jumbo.wms.model.command.TransportatorCommand;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.warehouse.AllocateCargoOrderCommand;
import com.jumbo.wms.model.warehouse.ExpressOrderCommand;
import com.jumbo.wms.model.warehouse.PackageInfo;
import com.jumbo.wms.model.warehouse.PickingList;
import com.jumbo.wms.model.warehouse.PickingListCheckMode;
import com.jumbo.wms.model.warehouse.PickingListCommand;
import com.jumbo.wms.model.warehouse.PickingListStatus;
import com.jumbo.wms.model.warehouse.PickingMode;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StvLine;

/**
 * 
 * @author yitaofen
 * 
 */
/**
 * @author NCGZ-DZ-
 * 
 */
public class SalesDispatchListAction extends BaseJQGridProfileAction implements ServletResponseAware {

    private static final long serialVersionUID = -5701503637076291286L;

    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private WareHouseManagerCancel wareHouseManagerCancel;
    @Autowired
    private WareHouseManagerExecute wareHouseManagerExecute;
    @Autowired
    private DropDownListManager dropDownListManager;
    @Autowired
    private PickingListManager pickingListManager;
    @Autowired
    private ExcelWriterManager excelWriterManager;
    @Autowired
    private ExcelExportManager excelExportManager;
    @Autowired
    private WareHouseManagerQuery wareHouseManagerQuery;
    @Autowired
    private SkuSizeConfigManager skuSizeConfigManager;
    @Autowired
    private OperationUnitManager operationUnitManager;
    @Autowired
    private TransOlManagerProxy transOlManagerProxy;
    @Autowired
    private PrintManager printManager;
    @Autowired
    private WareHouseManagerExe wareHouseManagerExe;
    @Autowired
    private TransportServiceManager transportServiceManager;
    @Autowired
    private SnManager snManager;
    @Autowired
    private BaseinfoManager baseinfoManager;
    @Autowired
    private WareHouseOutBoundManager warehouseOutBoundManager;
    private HttpServletResponse response;
    private String lpcode;
    private List<Long> plList;
    private Long pickingListId;
    private Long wid;
    private Integer giftType;
    private Boolean isOnlyParent = false;
    private String mainWarehouseName;
    private Integer pickZoneId;
    private String pickingListArray;
    private String flag;
    private PickingListCommand plCmd;
    private List<ChooseOption> plStatus;
    private StockTransApplication sta;
    private List<StvLine> stvLineList;
    private List<PackageInfo> packageInfos;
    private List<StaLine> staLineList;
    private String trackingNo;
    private List<String> skuBarcodes;
    private String fileName;
    private String lineNo;
    private String staCode;
    private String transNo;
    private String barCode;
    private OperationUnit ou;
    private Warehouse warehouse;
    private String slipCode;
    private Integer cardStatus;
    private String plIds;
    private Long staLineId;// 作业单行id
    private Long staId;
    private String str;
    private String areaCode;



    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

    public Long getStaLineId() {
        return staLineId;
    }

    public void setStaLineId(Long staLineId) {
        this.staLineId = staLineId;
    }

    /**
     * 配货批编码
     */
    private String plCode;
    /**
     * 配货清单查询
     */
    private AllocateCargoOrderCommand allocateCargoCmd;

    /**
     * 快递单号修改
     */
    private ExpressOrderCommand expressOrderCmd;
    /**
     * 店铺
     */
    private List<BiChannel> shops;
    /**
     * 是否需要使用后置打印模板
     */
    private Boolean isPostPrint = false;

    private List<ExpressOrderCommand> expLineCmd = new ArrayList<ExpressOrderCommand>();

    /**
     * 批量
     */
    private String pickingListIds;



    /**
     * 销售出库作业 配货清单操作列表
     * 
     * @return
     */
    public String dispatchListEntry() {
        return SUCCESS;
    }



    /**
     * 配货清单明细
     * 
     * @return
     */
    public String detialList() {
        setTableConfig();
        List<Long> lists = new ArrayList<Long>();
        if (userDetails.getCurrentOu().getOuType().getName().equals(OperationUnitType.OUTYPE_OPERATION_CENTER)) {
            wid = null;
            List<OperationUnit> listopc = operationUnitManager.selectWarehouseByCenid(userDetails.getCurrentOu().getId());
            for (int i = 0; i < listopc.size(); i++) {
                OperationUnit opc = listopc.get(i);
                lists.add(opc.getId());
            }
        } else {
            wid = userDetails.getCurrentOu().getId();
        }

        List<StockTransApplicationCommand> stas = wareHouseManager.findStaListByPickingList(pickingListId, wid, lists, null);
        request.put(JSON, toJson(stas));
        return JSON;
    }

    /**
     * 拣货单打印，加载区域
     * 
     * @return
     */
    public String getPickingDistrict() {
        request.put(JSON, JsonUtil.collection2json(pickingListManager.findPickingDistrictByPickingId(pickingListId, userDetails.getCurrentOu().getId())));
        return JSON;
    }

    /**
     * 查询等待配会列表
     * 
     * @return
     */
    public String list() {
        setTableConfig();
        request.put(JSON, toJson(wareHouseManager.findPickingListByStatus(PickingMode.MODE1, PickingListStatus.WAITING, userDetails.getCurrentOu().getId(), tableConfig.getSorts())));
        return JSON;
    }

    /**
     * 拣货单打印，加载区域
     * 
     * @return
     */
    public String getPickingDistrictList() {
        List<Long> pickingListId = new ArrayList<Long>();
        String[] pList = pickingListArray.split(",");
        if (pickingListArray.length() > 0) {
            for (int i = 0; i < pList.length; i++) {
                pickingListId.add(Long.valueOf(pList[i]));
            }
        }
        List<Zoon> pickingList = pickingListManager.findPickingDistrictByPickingListId(pickingListId, userDetails.getCurrentOu().getId());
        request.put(JSON, JsonUtil.collection2json(pickingList));
        return JSON;
    }

    public String getPickingList() {
        JSONObject result = new JSONObject();
        try {
            PickingListCommand pl = wareHouseManager.getPickingListById(pickingListId);
            if (pl != null) {
                result.put("pickingList", JsonUtil.obj2json(pl));
            }
        } catch (JSONException e) {
            log.error("", e);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 查询正在配货列表
     * 
     * @return
     */
    public String operatorPlList() {
        setTableConfig();
        request.put(JSON, toJson(wareHouseManagerQuery.findAfDeLiveryList(PickingMode.MODE1, userDetails.getCurrentOu().getId(), tableConfig.getSorts())));
        return JSON;
    }

    /**
     * 取消配货清单
     * 
     * @return
     * @throws JSONException
     */
    public String cancelPickingList() throws JSONException {
        JSONObject result = new JSONObject();
        if (plList != null) {
            for (Long plId : plList) {
                try {
                    wareHouseManagerCancel.cancelPickingList(plId);
                    result.put("result", SUCCESS);
                } catch (BusinessException e) {
                    result.put("result", ERROR);
                    result.put("message", result.get("message") + this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
                }
            }
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 删除picking list释放库存
     * 
     * @throws JSONException
     */
    public String deletePickingList() {
        JSONObject result = new JSONObject();
        try {
            String msgId = wareHouseManagerCancel.deletePickingList(pickingListId, userDetails.getUser().getId(), userDetails.getCurrentOu().getId());
            // 自动化定制 取消播种和 货箱流向
            if (!"".equals(msgId)) {
                String boMsgId = msgId.substring(0, msgId.indexOf("_")); // 播种
                String hxMsgId = msgId.substring(msgId.indexOf("_") + 1, msgId.length());// 货箱流向
                // 播种
                if (boMsgId.length() > 0) {
                    MsgToWcsThread msg = new MsgToWcsThread();
                    msg.setMsgId(Long.parseLong(boMsgId));
                    msg.setType(WcsInterfaceType.OQuxiaoBoZhong.getValue());
                    Thread th = new Thread(msg);
                    th.start();
                }
                if (hxMsgId.length() > 0) {
                    // 货箱流向
                    String[] hx = hxMsgId.split(",");
                    List<Long> hxList = new ArrayList<Long>();
                    for (int i = 0; i < hx.length; i++) {
                        hxList.add(Long.parseLong(hx[i]));
                    }
                    MsgToWcsThread msgs = new MsgToWcsThread();
                    msgs.setMsgList(hxList);
                    msgs.setType(WcsInterfaceType.OQuxiaoRongQi.getValue());
                    Thread ths = new Thread(msgs);
                    ths.start();
                }
            }

            result.put("result", SUCCESS);
        } catch (JSONException e) {
            log.error("", e);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 确认配货
     * 
     * @return
     */
    public String confirmPickingList() {
        occupiedInv(null, null);
        return JSON;
    }

    /**
     * 失败sta从新占用库存
     * 
     * @return
     */
    public String reOccupiedInv() {
        occupiedInv(null, null);
        return JSON;
    }

    /**
     * 删除配货形单中失败与取消sta
     * 
     * @return
     */
    public String removeFailedSta() {
        JSONObject result = new JSONObject();
        PickingListCommand pl = wareHouseManagerCancel.removeFialedSalesSta(pickingListId);
        try {
            if (pl != null) {
                result.put("pickingList", JsonUtil.obj2json(pl));
            }
        } catch (JSONException e) {
            log.error("", e);
        }
        request.put(JSON, result);
        return JSON;
    }

    public String chgLpcodeRemoveFailedSta() {
        JSONObject result = new JSONObject();
        PickingList pl = wareHouseManager.chgLpcodeByPicking(pickingListId, lpcode);
        try {
            if (pl != null) {
                result.put("pickingList", JsonUtil.obj2json(pl));
                result.put("result", SUCCESS);
            } else {
                result.put("result", "remove");
            }
        } catch (JSONException e) {
            log.error("", e);
        }
        request.put(JSON, result);
        if (pl != null) {
            occupiedInv(null, null);
        }
        return JSON;
    }

    /**
     * 移除快递接口调用失败STA
     * 
     * @return
     * @throws JSONException
     */
    public String removeTransFailedSta() throws JSONException {
        JSONObject result = new JSONObject();
        PickingListCommand pl = wareHouseManager.removeTransFialedSalesSta(pickingListId);
        try {
            if (pl != null) {
                result.put("pickingList", JsonUtil.obj2json(pl));
                result.put("result", SUCCESS);
            } else {
                result.put("result", "remove");
            }
        } catch (JSONException e) {
            log.error("", e);
        }
        request.put(JSON, result);
        if (pl != null) {
            Long msgId = occupiedInv(userDetails.getCurrentOu().getId(), pl);
            if (msgId != null) {
                // 多件播种
                MsgToWcsThread wcs = new MsgToWcsThread();
                wcs.setMsgId(msgId);
                wcs.setType(WcsInterfaceType.SBoZhong.getValue());
                Thread thread = new Thread(wcs);
                thread.start();
            }
        }
        return JSON;
    }

    /**
     * pickinglist中指定状态的sta占用库存
     * 
     * @param status
     */
    private Long occupiedInv(Long ouId, PickingList pl) {
        Long msgId = null;
        String resultStr = SUCCESS;
        String errorMsg = "";
        if (plList != null) {
            for (Long plId : plList) {
                // 如果顺风调用接口
                try {
                    transOlManagerProxy.matchingTransNo(plId, null);
                } catch (BusinessException e) {
                    resultStr = ERROR;
                    errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()) + Constants.HTML_LINE_BREAK;
                    BusinessException current = e;
                    while (current.getLinkedException() != null) {
                        current = current.getLinkedException();
                        errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + current.getErrorCode(), current.getArgs()) + Constants.HTML_LINE_BREAK;
                    }
                    continue;
                }
                // 占用库存
                for (StockTransApplication sta : wareHouseManager.findStaByPickingList(plId, this.userDetails.getCurrentOu().getId())) {
                    if (StockTransApplicationStatus.FAILED.equals(sta.getStatus()) || StockTransApplicationStatus.CREATED.equals(sta.getStatus())) {
                        try {
                            wareHouseManager.createStvByStaId(sta.getId(), userDetails.getUser().getId(), null, false);
                        } catch (BusinessException e) {
                            wareHouseManager.setStaOccupaidFailed(sta.getId());
                            resultStr = ERROR;
                            errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs());
                            BusinessException current = e;
                            while (current.getLinkedException() != null) {
                                current = current.getLinkedException();
                                errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + current.getErrorCode(), current.getArgs());
                            }
                        }
                    }
                }
                wareHouseManager.confirmPickingList(plId, userDetails.getUser().getId(), userDetails.getCurrentOu().getId());
            }
        }
        JSONObject result = new JSONObject();
        try {
            result.put("result", resultStr);
            result.put("msg", errorMsg);
            Warehouse se = wareHouseManager.getByOuId(ouId);
            if (se != null && se.getIsAutoWh() != null && se.getIsAutoWh() == true) {
                try {
                    List<Long> staId = wareHouseManager.findStaListByPkId(pl.getId());
                    PickingList pls = wareHouseManager.getPickingListByid(pl.getId());
                    msgId = warehouseOutBoundManager.marryStaShiipingCode(pls.getCheckMode(), pl.getCode(), staId, ouId);
                } catch (Exception e) {
                    log.error("occupiedInv is error---", e);
                }
            }
        } catch (JSONException e) {
            log.error("", e);
        }
        request.put(JSON, result);
        return msgId;
    }

    /**
     * 导出税控发票
     * 
     * @throws UnsupportedEncodingException
     * 
     * @throws Exception
     */
    public String exportSoInvoice() {
        String[] ids = pickingListIds.split(",");
        if (null != ids && ids.length > 0) {
            for (int i = 0; i < ids.length; i++) {
                pickingListId = Long.parseLong(ids[i]);
                String fileName = excelWriterManager.getExportFileName(pickingListId);
                setFileName(fileName + Constants.EXCEL_XLS);
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                excelExportManager.exportSoInvoiceByPickingList(out, pickingListId, null);
                ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
                setInputStream(in);
            }
            return SUCCESS;
        } else {
            log.error("税控发票导出(批量): id数组为空数据");
            return null;
        }
    }


    /**
     * AGV导出
     * 
     * @return
     */

    public void getExportAgv() throws UnsupportedEncodingException, IOException {
        response.setContentType("application/octet-stream;charset=ISO8859-1");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        response.setHeader("Content-Disposition", "attachment;filename=" + plCode + sdf.format(new Date()) + Constants.EXCEL_XLS);
        ServletOutputStream outs = null;
        try {
            outs = response.getOutputStream();
            excelExportManager.getExportAgv(outs, plCode, areaCode);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("", e);
        } finally {
            if (outs != null) {
                outs.flush();
                outs.close();
            }
        }
    }


    /**
     * 导出税控发票压缩文件
     * 
     * @throws IOException
     * @throws UnsupportedEncodingException
     * 
     * @throws Exception
     */
    public String exportSoInvoiceZip() throws IOException {
        String fileName = excelWriterManager.getExportFileName(pickingListId);
        setFileName(fileName + Constants.ZIP);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        excelExportManager.exportSoInvoiceByPickingList(out, pickingListId, null);
        setInputStream(ZipUtil.zipFileDownloadForExcel(fileName, out));
        return SUCCESS;
    }

    /**
     * 物流面单导出
     * 
     * @throws Exception
     */
    public String exportDeliveryInfo() {
        String exepName = wareHouseManager.findexpNameByPlid(pickingListId);
        PickingList pl = wareHouseManager.getPickingListByid(pickingListId);
        String fileName = exepName + "物流面单_" + pl.getCode() + Constants.EXCEL_XLS;
        try {
            fileName = new String(fileName.getBytes("GBK"), "ISO8859-1");
        } catch (UnsupportedEncodingException e) {
            log.error("", e);
        }
        this.setFileName(fileName);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        excelExportManager.exportDeliveryInfo(out, pickingListId, this.userDetails.getCurrentOu().getId());
        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        setInputStream(in);
        return SUCCESS;
    }

    /**
     * 物流面单导出
     * 
     * @throws IOException
     * @throws UnsupportedEncodingException
     * @throws Exception
     */
    public String exportDeliveryInfoZip() throws UnsupportedEncodingException, IOException {
        PickingList pl = wareHouseManager.getPickingListByid(pickingListId);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        excelExportManager.exportDeliveryInfo(out, pickingListId, this.userDetails.getCurrentOu().getId());
        String fileName = pl.getCode() + Constants.ZIP;
        setInputStream(ZipUtil.zipFileDownloadForExcel(fileName, out));
        setFileName(fileName);
        return SUCCESS;
    }

    /**
     * 打印配货清单-old
     * 
     * @return
     * @throws Exception
     */
    public String printPickingListMode1() {
        JasperPrint jpJasperPrint = null;
        try {
            jpJasperPrint = printManager.printPickingListMode1(plCmd, userDetails.getCurrentOu().getId(), userDetails.getUser().getId(), flag);
            return printObject(jpJasperPrint);
        } catch (Exception e) {
            log.error("", e);
            return null;
        }
    }

    public void pickingListMode1Export() throws IOException {
        response.setContentType("application/octet-stream;charset=ISO8859-1");
        PickingList pl = wareHouseManager.getPickingListByid(plCmd.getId());
        String fileName = "配货清单_" + pl.getCode();
        fileName = new String(fileName.getBytes("GBK"), "ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + Constants.EXCEL_XLS);
        ServletOutputStream os = response.getOutputStream();
        try {
            String psizes = "";
            Long pcheckmode = pickingListManager.findPickingListByPickId(plCmd.getId());
            if (pcheckmode == PickingListCheckMode.PICKING_SECKILL.getValue()) {
                psizes = "秒";
            } else if (pcheckmode == PickingListCheckMode.PICKING_GROUP.getValue()) {
                psizes = "团";
            } else if (pcheckmode == PickingListCheckMode.PICKING_PACKAGE.getValue()) {
                psizes = "套";
            } else {
                psizes = skuSizeConfigManager.findBypicklistId(plCmd.getId());
            }
            excelExportManager.pickingListMode1Export(plCmd, userDetails.getCurrentOu().getId(), os, psizes);
            os.flush();
            os.close();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    // 拣货单打印-new
    public String printPickingListNewMode1() {
        JasperPrint jpJasperPrint = null;
        String psizes = "";
        try {
            Long pcheckmode = pickingListManager.findPickingListByPickId(pickingListId);
            Long special = pickingListManager.findPickingListByPickIdS(pickingListId);
            if (pcheckmode == PickingListCheckMode.PICKING_SECKILL.getValue()) {
                psizes = "秒";
            } else if (pcheckmode == PickingListCheckMode.PICKING_GROUP.getValue()) {
                psizes = "团";
            } else if (pcheckmode == PickingListCheckMode.PICKING_PACKAGE.getValue()) {
                psizes = "套";
            } else {
                if (special == null) {
                    psizes = skuSizeConfigManager.findBypicklistId(pickingListId);
                } else {
                    if (special == 1) {
                        psizes = skuSizeConfigManager.findBypicklistId(pickingListId);
                        psizes += "/特";
                    } else {
                        psizes = skuSizeConfigManager.findBypicklistId(pickingListId);
                        psizes += "/包";
                    }
                }
            }
            jpJasperPrint = printManager.printPickingListNewMode1(pickingListId, pickZoneId, userDetails.getCurrentOu().getId(), psizes, userDetails.getUser().getId(), flag);
            return printObject(jpJasperPrint);
        } catch (Exception e) {
            log.error("", e);
            return null;
        }
    }

    /**
     * 打印装箱清单列表
     * 
     * @return
     * @throws Exception
     */
    public String printTrunkPackingListMode1() {
        JasperPrint jp;
        try {
            // old:
            jp = printManager.printPackingPage(plCmd.getId(), plCmd.getStaId(), userDetails.getUser().getId(), isPostPrint, null);
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

    /*
     * 查寻导出次数
     */
    public String queryDispatchListOutputCount() throws Exception {
        JSONObject result = new JSONObject();
        try {
            BigDecimal countTemp = pickingListManager.findOutputCount(plCmd.getId());
            Long count = countTemp == null ? 0 : countTemp.longValue();
            result.put("msg", SUCCESS);
            result.put("count", count);
        } catch (Exception e) {
            log.error("", e);
            log.error(e.getMessage());
            result.put("msg", ERROR);
        }
        request.put(JSON, result);
        return JSON;
    }

    // 打印装箱清单和物流面单
    public String printTrankAndDelivery() {
        List<Object> jps = new ArrayList<Object>();
        try {
            jps.add(printManager.printPackingPage(plCmd.getId(), plCmd.getStaId(), userDetails.getUser().getId(), isPostPrint, null));// 打印装箱清单
            jps.add(printManager.printExpressBillBySta(plCmd.getStaId(), isOnlyParent, null, userDetails.getUser().getId()));// 打印物流面单
            return printObject(jps);
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

    // / 单张物流面单的打印
    public String printSingleDeliveryMode1() {
        JasperPrint jp;
        try {
            jp = printManager.printExpressBillBySta(sta.getId(), isOnlyParent, null, userDetails.getUser().getId());
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
     * 物流快递面单套打
     * 
     * @return
     * @throws Exception
     */
    public String printDeliveryInfoMode1() {
        try {
            PickingListCommand pl = pickingListManager.getByPrimaryKey(plCmd.getId());
            if (pl.getLpcode() != null && !pl.getLpcode().equals("")) {
                JasperPrint jp = printManager.printExpressBillByPickingListLpCode(plCmd.getId(), null, userDetails.getUser().getId());
                return printObject(jp);
            } else {
                List<JasperPrint> jp = new ArrayList<JasperPrint>();
                jp = printManager.printExpressBillByPickingList(plCmd.getId(), null, userDetails.getUser().getId());
                return printObject(jp);
            }
        } catch (JasperReportNotFoundException e) {
            log.error("", e);
        } catch (JasperPrintFailureException e) {
            log.error("", e);
        } catch (JRException e) {
            log.error("", e);
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    // ******************************************核对配货清单******************************************//
    /**
     * 核对配货清单
     * 
     * @return
     */
    public String plVerifyEntry() {
        plStatus = this.dropDownListManager.findPickingListStatusForVerify();
        return SUCCESS;
    }

    /**
     * 配货清单Json,配货中+部分出库
     * 
     * @return
     * @throws JSONException
     */
    public String plListForVerify() throws JSONException {
        setTableConfig();
        if (plCmd == null) {
            plCmd = new PickingListCommand();
        }
        if (plCmd != null) {
            plCmd.setPickingTime(FormatUtil.getDate(plCmd.getPickingTime1()));
            plCmd.setExecutedTime(FormatUtil.getDate(plCmd.getExecutedTime1()));
        }
        plCmd.setWarehouse(userDetails.getCurrentOu());
        plCmd.setPickingMode(PickingMode.MODE1);
        request.put(JSON, toJson(wareHouseManager.findPickingListForVerifyByCmd(tableConfig.getStart(), tableConfig.getPageSize(), plCmd, tableConfig.getSorts())));
        return JSON;
    }

    /**
     * 库存占用=配货中的作业单
     * 
     * @return
     * @throws JSONException
     */
    public String staOccupiedList() throws JSONException {
        setTableConfig();
        List<StockTransApplicationCommand> list = wareHouseManager.findStaOccupiedListByPickingList(PickingMode.MODE1, plCmd.getId(), userDetails.getCurrentOu().getId(), tableConfig.getSorts());
        request.put(JSON, toJson(list));
        return JSON;
    }

    /**
     * 已核对作业单
     * 
     * @return
     * @throws JSONException
     */
    public String staCheckedList() throws JSONException {
        setTableConfig();
        List<StockTransApplicationCommand> list = wareHouseManager.findStaCheckedListByPickingList(PickingMode.MODE1, plCmd.getId(), userDetails.getCurrentOu().getId(), tableConfig.getSorts());
        request.put(JSON, toJson(list));
        return JSON;
    }

    public String staByRefSlipCode() throws Exception {
        StockTransApplicationCommand cmd = wareHouseManager.findStaForVerifyByRefSlipCode(getPlCode(), PickingMode.MODE1, sta.getRefSlipCode(), userDetails.getCurrentOu().getId(), null, null);
        if (cmd != null) {
            Map<String, Object> result = new HashMap<String, Object>();
            BeanUtilSupport.describe(result, null, cmd);
            request.put(JSON, new JSONObject(result));
        }
        return JSON;
    }

    public String stvLineByStaId() {
        setTableConfig();
        request.put(JSON, toJson(wareHouseManager.findStvLineByStaIds(Arrays.asList(sta.getId()), tableConfig.getSorts())));
        return JSON;
    }

    public String staLineByStaId() {
        setTableConfig();
        request.put(JSON, toJson(wareHouseManager.findStaLineCommandListByStaId(tableConfig.getStart(), tableConfig.getPageSize(), sta.getId(), tableConfig.getSorts())));
        return JSON;
    }

    public String findskuBarcodeList() throws JSONException {
        JSONObject obj = new JSONObject();
        if (null != skuBarcodes) {
            Map<String, List<String>> map = new HashMap<String, List<String>>();
            List<SkuBarcodeCommand> bars = wareHouseManagerQuery.findAddSkuBarcodeByMainBarcode(skuBarcodes, userDetails.getCurrentOu().getId());
            for (SkuBarcodeCommand skubarcode : bars) {
                List<String> addbarcodes = map.get(skubarcode.getMainBarcode());
                if (addbarcodes == null) {
                    addbarcodes = new ArrayList<String>();
                    if (skubarcode.getBarcode() != null) {
                        addbarcodes.add(skubarcode.getBarcode());
                    }
                    map.put(skubarcode.getMainBarcode(), addbarcodes);
                } else {
                    if (skubarcode.getBarcode() != null) {
                        addbarcodes.add(skubarcode.getBarcode());
                    }
                    map.put(skubarcode.getMainBarcode(), addbarcodes);
                }
            }
            for (Entry<String, List<String>> ent : map.entrySet()) {
                obj.put(ent.getKey(), ent.getValue() == null ? new ArrayList<String>() : ent.getValue());
            }
        }
        request.put(JSON, obj);
        return JSON;
    }

    /**
     * 查询特殊处理数据行
     * 
     * @return
     * @throws JSONException
     */
    public String findStaGiftListLine() throws JSONException {
        JSONObject result = new JSONObject();
        GiftLineCommand giftLine = wareHouseManagerQuery.findGiftByStaLine(staLineId, giftType);
        if (giftLine == null) {
            result.put("msg", "no");
        } else {
            result.put("msg", "yes");
            result.put("giftLine", JsonUtil.obj2json(giftLine));
        }
        request.put(JSON, result);
        return JSON;
    }


    /**
     * 查询特殊处理数据
     * 
     * @return
     * @throws JSONException
     */
    public String findStaGiftList() throws JSONException {
        JSONObject obj = new JSONObject();
        if (null != sta && null != sta.getId()) {
            List<GiftLineCommand> giftLineList = wareHouseManagerQuery.findGiftBySta(sta.getId(), giftType);
            Map<String, List<GiftLineCommand>> map = new HashMap<String, List<GiftLineCommand>>();
            for (GiftLineCommand line : giftLineList) {
                String key = line.getStaLineId().toString();
                if (map.containsKey(key)) {
                    map.get(key).add(line);
                } else {
                    List<GiftLineCommand> list = new ArrayList<GiftLineCommand>();
                    list.add(line);
                    map.put(key, list);
                    obj.put(key, list);
                }
            }
            for (String key : map.keySet()) {
                obj.put(key, map.get(key));
            }
        }
        request.put(JSON, obj);
        return JSON;
    }

    /**
     * 查询
     * 
     * @return
     * @throws JSONException
     */
    public String queryStaSpecialExecute() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("data", wareHouseManagerQuery.queryStaSpecialExecute(sta.getId()));
        request.put(JSON, obj);
        return JSON;
    }

    /**
     * 打印装箱清单列表
     * 
     * @return
     * @throws Exception
     */
    public String printGift() {
        JasperPrint jp;
        try {
            jp = printManager.printGift(wid);
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
     * 执行核对
     * 
     * @return
     * @throws JSONException
     */
    public String checkDistributionList() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            wareHouseManager.checkDistributionList(PickingMode.MODE1, packageInfos, sta.getId(), staLineList, lineNo, userDetails.getUser().getId());
            result.put("result", SUCCESS);
            // this.asynReturnTrueValue();
        } catch (BusinessException e) {
            // result.put("result", ERROR);
            log.error("", e);
            result.put("msg", getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 核对快递单号
     * 
     * @return
     * @throws JSONException
     */
    public String checkTrackingNo() throws JSONException {
        Boolean rs = wareHouseManager.checkTrackingNo(trackingNo, sta.getId());
        if (rs) {
            JSONObject result = new JSONObject();
            result.put("result", SUCCESS);
            request.put(JSON, result);
        }
        return JSON;
    }

    /**
     * 检查配货批是否完成配货
     * 
     * @return
     * @throws JSONException
     */
    public String checkPickingisOver() throws JSONException {
        Boolean rs = wareHouseManager.checkPickingisOver(plCmd.getCode(), plCmd.getSlipCode(), userDetails.getCurrentOu().getId());
        if (rs) {
            wareHouseManager.deletePackageInfo(plCmd.getSlipCode());
            JSONObject result = new JSONObject();
            result.put("result", SUCCESS);
            request.put(JSON, result);
        }
        return JSON;
    }


    /**
     * 核对快递单号
     * 
     * @return
     * @throws JSONException
     */
    public String plCheckTrackingNo() throws JSONException {
        Boolean rs = wareHouseManager.checkTrackingNoByLpcode(trackingNo, lpcode);
        if (rs) {
            JSONObject result = new JSONObject();
            result.put("result", SUCCESS);
            request.put(JSON, result);
        }
        return JSON;
    }

    /**
     * 核对是否需要条码
     * 
     * @return
     * @throws JSONException
     */
    public String checkIsNeedBarcode() throws JSONException {
        ou = getCurrentOu();
        Boolean rs = false;
        // 根据组织id查找 相应的仓库附加信息
        warehouse = baseinfoManager.findWarehouseByOuId(ou.getId());
        if (warehouse != null) {
            rs = warehouse.getIsCheckedBarcode();
        }
        JSONObject result = new JSONObject();
        if (rs != null && rs.booleanValue()) {
            result.put("result", SUCCESS);
        } else {
            result.put("result", "");
        }
        request.put(JSON, result);

        return JSON;
    }

    /**
     * 配货清单查询(模式一)
     * 
     * @return
     */
    public String dispatchModel1QueryList() {
        plStatus = this.dropDownListManager.findPickingListStatusOptionList();
        return SUCCESS;
    }

    /**
     * 配货清单模式一查询
     * 
     * @return
     * @throws JSONException
     */
    public String matchListForModelOne() throws JSONException {
        setTableConfig();
        if (allocateCargoCmd == null) {
            allocateCargoCmd = new AllocateCargoOrderCommand();
        }
        if (allocateCargoCmd != null) {
            allocateCargoCmd.setFormCheckedTime(FormatUtil.getDate(allocateCargoCmd.getFormCheckedTime1()));
            allocateCargoCmd.setFormCrtime(FormatUtil.getDate(allocateCargoCmd.getFormCrtime1()));
            allocateCargoCmd.setFormOutBoundTime(FormatUtil.getDate(allocateCargoCmd.getFormOutBoundTime1()));
            allocateCargoCmd.setFormPickingTime(FormatUtil.getDate(allocateCargoCmd.getFormPickingTime1()));

            allocateCargoCmd.setToCheckedTime(FormatUtil.getDate(allocateCargoCmd.getToCheckedTime1()));
            allocateCargoCmd.setToCrtime(FormatUtil.getDate(allocateCargoCmd.getToCrtime1()));
            allocateCargoCmd.setToOutBoundTime(FormatUtil.getDate(allocateCargoCmd.getToOutBoundTime1()));
            allocateCargoCmd.setToPickingTime(FormatUtil.getDate(allocateCargoCmd.getToPickingTime1()));
        }
        allocateCargoCmd.setWarehouse(userDetails.getCurrentOu());
        allocateCargoCmd.setPickingMode(PickingMode.MODE1);
        if (wid != null) {
            plList = new ArrayList<Long>();
            plList.add(wid);
        }
        request.put(JSON, toJson(wareHouseManager.findPickingListForModel(tableConfig.getStart(), tableConfig.getPageSize(), allocateCargoCmd, tableConfig.getSorts(), plList, userDetails.getCurrentOu().getOuType().getName())));
        return JSON;
    }

    /**
     * 
     * @return
     * @throws JSONException
     */
    public String queryInvoiceForPickinglist() throws JSONException {
        setTableConfig();
        if (allocateCargoCmd == null) {
            allocateCargoCmd = new AllocateCargoOrderCommand();
        }
        if (allocateCargoCmd != null) {
            allocateCargoCmd.setFormCheckedTime(FormatUtil.getDate(allocateCargoCmd.getFormCheckedTime1()));
            allocateCargoCmd.setFormCrtime(FormatUtil.getDate(allocateCargoCmd.getFormCrtime1()));
            allocateCargoCmd.setFormOutBoundTime(FormatUtil.getDate(allocateCargoCmd.getFormOutBoundTime1()));
            allocateCargoCmd.setFormPickingTime(FormatUtil.getDate(allocateCargoCmd.getFormPickingTime1()));

            allocateCargoCmd.setToCheckedTime(FormatUtil.getDate(allocateCargoCmd.getToCheckedTime1()));
            allocateCargoCmd.setToCrtime(FormatUtil.getDate(allocateCargoCmd.getToCrtime1()));
            allocateCargoCmd.setToOutBoundTime(FormatUtil.getDate(allocateCargoCmd.getToOutBoundTime1()));
            allocateCargoCmd.setToPickingTime(FormatUtil.getDate(allocateCargoCmd.getToPickingTime1()));
        }
        allocateCargoCmd.setWarehouse(userDetails.getCurrentOu());
        request.put(JSON, toJson(wareHouseManager.findPickingListForModel(tableConfig.getStart(), tableConfig.getPageSize(), allocateCargoCmd, tableConfig.getSorts(), plList, userDetails.getCurrentOu().getOuType().getName())));
        return JSON;
    }

    /**
     * 配货清单模式二查询
     * 
     * @return
     * @throws JSONException
     */
    public String matchListForModelTwo() throws JSONException {
        setTableConfig();
        if (allocateCargoCmd == null) {
            allocateCargoCmd = new AllocateCargoOrderCommand();
        }
        if (allocateCargoCmd != null) {
            allocateCargoCmd.setFormCheckedTime(FormatUtil.getDate(allocateCargoCmd.getFormCheckedTime1()));
            allocateCargoCmd.setFormCrtime(FormatUtil.getDate(allocateCargoCmd.getFormCrtime1()));
            allocateCargoCmd.setFormOutBoundTime(FormatUtil.getDate(allocateCargoCmd.getFormOutBoundTime1()));
            allocateCargoCmd.setFormPickingTime(FormatUtil.getDate(allocateCargoCmd.getFormPickingTime1()));

            allocateCargoCmd.setToCheckedTime(FormatUtil.getDate(allocateCargoCmd.getToCheckedTime1()));
            allocateCargoCmd.setToCrtime(FormatUtil.getDate(allocateCargoCmd.getToCrtime1()));
            allocateCargoCmd.setToOutBoundTime(FormatUtil.getDate(allocateCargoCmd.getToOutBoundTime1()));
            allocateCargoCmd.setToPickingTime(FormatUtil.getDate(allocateCargoCmd.getToPickingTime1()));
        }
        allocateCargoCmd.setWarehouse(userDetails.getCurrentOu());
        allocateCargoCmd.setPickingMode(PickingMode.MODE2);
        request.put(JSON, toJson(wareHouseManager.findPickingListForModel(tableConfig.getStart(), tableConfig.getPageSize(), allocateCargoCmd, tableConfig.getSorts(), plList, userDetails.getCurrentOu().getOuType().getName())));
        return JSON;
    }

    /**
     * 根据批次号查找相关操作人和创建人
     * 
     * @return
     * @throws JSONException
     */
    public String findPackingByBatchCode() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            PickingList pl = wareHouseManager.findPackingByBatchCode(allocateCargoCmd.getId());
            if (pl != null) {
                result.put("pl", JsonUtil.obj2json(pl));
            }
        } catch (JSONException e) {
            log.error("", e);
        }
        request.put(JSON, result);
        return JSON;
    }


    public String checkPickingSkuByStaId() {
        JSONObject result = new JSONObject();
        String msg = wareHouseManager.checkPickingSkuByStaId(str, staId, userDetails.getCurrentOu().getId(), userDetails.getUser().getId());
        try {
            result.put("msg", msg);
        } catch (JSONException e) {
            // e.printStackTrace();
            if (log.isErrorEnabled()) {
                log.error("checkPickingSkuByStaId error:" + staId, e);
            };
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 查询配货批次by id
     * 
     * @return
     * @throws JSONException
     */
    public String findPackinglistById() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            PickingList pickinglist = wareHouseManager.getPickingListByid(pickingListId);
            if (pickinglist != null) {
                result.put("pl", JsonUtil.obj2json(pickinglist));
            }
        } catch (JSONException e) {
            log.error("", e);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 
     * @return
     */
    public String getWarehouseInfo() {
        if (ou == null || ou.getId() == null) {
            wid = userDetails.getCurrentOu().getId();
        } else {
            wid = ou.getId();
        }
        request.put(JSON, JsonUtil.obj2json(wareHouseManager.getByOuId(wid)));
        return JSON;
    }

    /**
     * 查询配货批次by code
     * 
     * @return
     * @throws JSONException
     */
    public String findPackinglistByCode() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            if (plCmd != null && plCmd.getCode() != null && !"".equals(plCmd.getCode())) {
                plCmd.setWarehouse(userDetails.getCurrentOu());
                plCmd.setExecutedTime(addOneDay(plCmd.getExecutedTime()));
                plCmd.setPickingMode(PickingMode.MODE1);
                PickingListCommand pl = wareHouseManager.findPackinglistByCode(plCmd);
                if (pl != null) {
                    result.put("pl", JsonUtil.obj2json(pl));
                }
            }
        } catch (JSONException e) {
            log.error("", e);
        }
        request.put(JSON, result);
        return JSON;
    }

    public String updatePickinglistToFinish() throws JSONException {
        JSONObject result = new JSONObject();
        // 配货批部分完成状态
        try {
            wareHouseManager.updatePickinglistToFinish(pickingListId, this.userDetails.getCurrentOu().getId(), userDetails.getUser().getId());
            result.put("result", SUCCESS);
        } catch (JSONException e) {
            result.put("result", ERROR);
            log.error("", e);
        } catch (BusinessException e) {
            result.put("result", ERROR);
            result.put("msg", getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
            log.error("", e);
        }
        request.put(JSON, result);
        return JSON;
    }

    // /**
    // * 快递单号修改(模式一)
    // *
    // * @return
    // */
    public String expressOrderEdit() {
        // shops = baseinfoManager.getCompanyShop();
        return SUCCESS;
    }

    /**
     * 快递单号修改查询(模式一)
     * 
     * @return
     */
    public String expressOrderQuery() {
        setTableConfig();
        if (expressOrderCmd == null) {
            expressOrderCmd = new ExpressOrderCommand();
        }
        if (expressOrderCmd != null) {
            expressOrderCmd.setFormCrtime(FormatUtil.getDate(expressOrderCmd.getFormCrtime1()));
            expressOrderCmd.setToCrtime(FormatUtil.getDate(expressOrderCmd.getToCrtime1()));
        }
        if (wid != null) {
            plList = new ArrayList<Long>();
            plList.add(wid);
        }
        expressOrderCmd.setMainWarehouse(userDetails.getCurrentOu());
        request.put(JSON, toJson(wareHouseManager.findExpressOrderSta(tableConfig.getStart(), tableConfig.getPageSize(), expressOrderCmd, tableConfig.getSorts(), plList, userDetails.getCurrentOu().getOuType().getName())));
        return JSON;
    }

    /**
     * 根据作业sta获取关系物流包裹快递单号
     * 
     * @return
     * @throws Exception
     */
    public String findPackageInfoList() throws Exception {
        request.put(JSON, JsonUtil.collection2json(wareHouseManager.findPackageInfoListByStaId(sta.getId())));
        return JSON;
    }

    /**
     * 编辑STA关联快递单号
     * 
     * @return
     * @throws Exception
     */
    public String editTrackingNos() throws Exception {
        JSONObject result = new JSONObject();
        List<String> errorMsg = new ArrayList<String>();
        try {
            wareHouseManagerExe.updatePackageInfoTrackingNo(expressOrderCmd, expLineCmd);
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
     * 获取所有物流供应商信息
     * 
     * @return
     * @throws Exception
     */
    public String getTransByPlId() throws Exception {
        List<TransportatorCommand> list = baseinfoManager.getTransByPlId(pickingListId);
        request.put(JSON, JsonUtil.collection2json(list));
        return JSON;
    }

    public String updateStaTransNo() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            result.put(RESULT, SUCCESS);
            wareHouseManagerExecute.updateStaTransNoForOccupied(staCode, transNo);
        } catch (Exception e) {
            log.error("", e);
            result.put(RESULT, ERROR);
        }
        request.put(JSON, result);
        return JSON;
    }

    public String updateStaOuId() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            result.put(RESULT, SUCCESS);
            wareHouseManagerExecute.updateStaOuId(staCode, mainWarehouseName);
        } catch (Exception e) {
            log.error("", e);
            result.put(RESULT, ERROR);
        }
        request.put(JSON, result);
        return JSON;
    }

    public String findEmsTrandInfo() throws JSONException {
        JSONObject result = new JSONObject();
        boolean isCod = wareHouseManagerQuery.isCodPickingList(pickingListId);
        TransEmsInfo emsInof = transportServiceManager.findByCmp(isCod);
        if (emsInof == null) {
            result.put(RESULT, ERROR);
        } else {
            result.put(RESULT, SUCCESS);
            result.put("ems", JsonUtil.obj2json(emsInof));
        }
        request.put(JSON, result);
        return JSON;
    }

    public String checkSalesSum() throws JSONException {
        JSONObject result = new JSONObject();
        String saleSum = wareHouseManagerQuery.checkSalesSum();
        if (StringUtil.isEmpty(saleSum)) {
            result.put(RESULT, ERROR);
        } else {
            result.put(RESULT, SUCCESS);
            result.put("saleSum", saleSum);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 根据slip_Code查询 YDX
     * 
     * @return
     */
    public String getByslipCode() {
        setTableConfig();
        List<AllocateCargoOrderCommand> list = wareHouseManager.getByslipCode(slipCode, tableConfig.getSorts());
        // request.put(JSON, JsonUtil.collection2json(list));
        request.put(JSON, toJson(list));
        return JSON;
    }

    /**
     * 通过barcode staid获取被绑定的卡号数量(激活成功)
     * 
     * @return
     * @throws JSONException
     */
    public String getSnCountForStvSku() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            Long count = snManager.getSnCountForStvSku(sta.getId(), barCode, cardStatus);
            result.put("result", SUCCESS);
            result.put("count", count);
        } catch (Exception e) {
            result.put("result", ERROR);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * PDA拣货条码打印
     * 
     * @return
     */
    public String printPdaBarCode() {
        List<JasperPrint> jp;
        try {
            jp = printManager.printPdaBarCode(plCmd.getId());
            return printObject(jp);
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    /**
     * PDA拣货条码打印
     * 
     * @return
     */
    public String printPdaBarCodeS() {
        List<JasperPrint> jp;
        try {
            String[] pls = plIds.split(",");
            jp = printManager.printPdaBarCodeS(pls);
            return printObject(jp);
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    /**
     * PDA退仓拣货条码打印
     * 
     * @return
     */
    public String printPdaBarCodeRtw() {
        List<JasperPrint> jp;
        try {
            String[] pls = plIds.split(",");
            jp = printManager.printPdaBarCodeRtw(pls);
            return printObject(jp);
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }


    /**
     * (配货清单待打印) 批量 校验作业单状态
     * 
     * @return
     */
    public String batchCheckForStaStatus() {
        JSONObject result = new JSONObject();
        List<Long> lists = new ArrayList<Long>();
        try {
            if (userDetails.getCurrentOu().getOuType().getName().equals(OperationUnitType.OUTYPE_OPERATION_CENTER)) {
                wid = null;
                List<OperationUnit> listopc = operationUnitManager.selectWarehouseByCenid(userDetails.getCurrentOu().getId());
                for (int i = 0; i < listopc.size(); i++) {
                    OperationUnit opc = listopc.get(i);
                    lists.add(opc.getId());
                }
            } else {
                wid = userDetails.getCurrentOu().getId();
            }
            String[] ids = pickingListIds.split(",");
            if (null != ids && ids.length > 0) {
                boolean rs = wareHouseManager.verifyStatusByPickingList(ids, wid, lists);
                if (rs) {
                    result.put("msg", rs);
                } else {
                    result.put("msg", rs);
                }
            } else {
                result.put("msg", "data_error");
            }
        } catch (JSONException e) {
            log.error("", e);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 方法说明：批量 查询导出次数
     * 
     * @author LuYingMing
     * @date 2016年7月13日 下午1:30:30
     * @return
     * @throws Exception
     */
    public String batchQueryOfOutputCount() throws Exception {
        JSONObject result = new JSONObject();
        int num = 0;
        Long count = 0L;
        String[] ids = pickingListIds.split(",");
        if (null != ids && ids.length > 0) {
            for (int i = 0; i < ids.length; i++) {
                Long plId = Long.parseLong(ids[i]);
                try {
                    BigDecimal countTemp = pickingListManager.findOutputCount(plId);
                    count = countTemp == null ? 0 : countTemp.longValue();
                    if (Integer.parseInt(count.toString()) > 0) {
                        num++;
                    }
                } catch (Exception e) {
                    log.error("", e);
                    log.error(e.getMessage());
                    result.put("msg", ERROR);
                }
            }
            if (num == 0) {
                result.put("msg", SUCCESS);
                result.put("count", count);
            } else {
                result.put("msg", SUCCESS);
            }
        } else {
            result.put("msg", ERROR);
        }
        request.put(JSON, result);
        return JSON;
    }



    public Integer getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(Integer cardStatus) {
        this.cardStatus = cardStatus;
    }

    public String getSlipCode() {
        return slipCode;
    }

    public void setSlipCode(String slipCode) {
        this.slipCode = slipCode;
    }

    public List<Long> getPlList() {
        return plList;
    }

    public void setPlList(List<Long> plList) {
        this.plList = plList;
    }

    public Long getPickingListId() {
        return pickingListId;
    }

    public void setPickingListId(Long pickingListId) {
        this.pickingListId = pickingListId;
    }

    public PickingListCommand getPlCmd() {
        return plCmd;
    }

    public void setPlCmd(PickingListCommand plCmd) {
        this.plCmd = plCmd;
    }

    public List<ChooseOption> getPlStatus() {
        return plStatus;
    }

    public void setPlStatus(List<ChooseOption> plStatus) {
        this.plStatus = plStatus;
    }

    public StockTransApplication getSta() {
        return sta;
    }

    public void setSta(StockTransApplication sta) {
        this.sta = sta;
    }

    public List<StvLine> getStvLineList() {
        return stvLineList;
    }

    public void setStvLineList(List<StvLine> stvLineList) {
        this.stvLineList = stvLineList;
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

    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    public void setServletResponse(HttpServletResponse response) {
        this.response = response;
    }

    public List<String> getSkuBarcodes() {
        return skuBarcodes;
    }

    public void setSkuBarcodes(List<String> skuBarcodes) {
        this.skuBarcodes = skuBarcodes;
    }

    public AllocateCargoOrderCommand getAllocateCargoCmd() {
        return allocateCargoCmd;
    }

    public void setAllocateCargoCmd(AllocateCargoOrderCommand allocateCargoCmd) {
        this.allocateCargoCmd = allocateCargoCmd;
    }

    public List<BiChannel> getShops() {
        return shops;
    }

    public void setShops(List<BiChannel> shops) {
        this.shops = shops;
    }

    public ExpressOrderCommand getExpressOrderCmd() {
        return expressOrderCmd;
    }

    public void setExpressOrderCmd(ExpressOrderCommand expressOrderCmd) {
        this.expressOrderCmd = expressOrderCmd;
    }

    public List<ExpressOrderCommand> getExpLineCmd() {
        return expLineCmd;
    }

    public void setExpLineCmd(List<ExpressOrderCommand> expLineCmd) {
        this.expLineCmd = expLineCmd;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPlCode() {
        if (StringUtils.hasText(plCode)) {
            return plCode;
        } else {
            return null;
        }
    }

    public void setPlCode(String plCode) {
        this.plCode = plCode;
    }

    public String getLpcode() {
        return lpcode;
    }

    public void setLpcode(String lpcode) {
        this.lpcode = lpcode;
    }

    public String getLineNo() {
        return lineNo;
    }

    public void setLineNo(String lineNo) {
        this.lineNo = lineNo;
    }

    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    public Integer getGiftType() {
        return giftType;
    }

    public void setGiftType(Integer giftType) {
        this.giftType = giftType;
    }

    public Boolean getIsPostPrint() {
        return isPostPrint;
    }

    public void setIsPostPrint(Boolean isPostPrint) {
        this.isPostPrint = isPostPrint;
    }

    public Boolean getIsOnlyParent() {
        return isOnlyParent;
    }

    public void setIsOnlyParent(Boolean isOnlyParent) {
        this.isOnlyParent = isOnlyParent;
    }

    public Integer getPickZoneId() {
        return pickZoneId;
    }

    public void setPickZoneId(Integer pickZoneId) {
        this.pickZoneId = pickZoneId;
    }

    public String getPickingListArray() {
        return pickingListArray;
    }

    public void setPickingListArray(String pickingListArray) {
        this.pickingListArray = pickingListArray;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getMainWarehouseName() {
        return mainWarehouseName;
    }

    public void setMainWarehouseName(String mainWarehouseName) {
        this.mainWarehouseName = mainWarehouseName;
    }

    public Long getWid() {
        return wid;
    }

    public void setWid(Long wid) {
        this.wid = wid;
    }

    public OperationUnit getOu() {
        return ou;
    }

    public void setOu(OperationUnit ou) {
        this.ou = ou;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public String getPickingListIds() {
        return pickingListIds;
    }

    public void setPickingListIds(String pickingListIds) {
        this.pickingListIds = pickingListIds;
    }



    public String getPlIds() {
        return plIds;
    }



    public void setPlIds(String plIds) {
        this.plIds = plIds;
    }



}
