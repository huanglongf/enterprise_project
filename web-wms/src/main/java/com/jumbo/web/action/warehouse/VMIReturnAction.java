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
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;

import loxia.dao.Pagination;
import loxia.support.excel.ExcelKit;
import loxia.support.excel.ReadStatus;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;
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
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerCancel;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExe;
import com.jumbo.wms.manager.warehouse.excel.ExcelReadManager;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.vmi.Default.VmiRtoLineCommand;
import com.jumbo.wms.model.warehouse.FreightMode;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;


public class VMIReturnAction extends BaseJQGridProfileAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = -3174070359613322211L;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private WareHouseManagerExe wareHouseManagerExe;
    @Autowired
    private WareHouseManagerCancel wareHouseManagerCancel;
    @Autowired
    private ExcelReadManager excelReadManager;
    @Autowired
    private PrintManager printManager;

    @Autowired
    private ExcelExportManager Excexport;

    // 查询时间
    private String startTime;
    private String endTime;
    private Integer status;
    private String code;
    private String innerShopCode;


    private StockTransApplicationCommand staCommand;
    private Long staID;

    private File file;

    private Long ownerid;
    private int intType;
    private String toLocation;

    // private StaDeliveryInfo stadelivery;
    private String province;
    private String city;
    private String district;
    private String receiver;
    private String address;
    private String telphone;
    private String reasonCode;
    private String imperfectType;
    private String slipCode1;
    private String activitySource;
    private boolean espritFlag;
    private boolean espritTransferFlag;
    private String lpCode;
    private Integer freightMode;
    private Long id;
    private String orderCode;
    private String refSlipCode;
    private String owner;
    private String returnReason;
    private String resonCode;
    private String slipCode;
    private String toLoction;
    private Long rtoId;

    public String getLpCode() {
        return lpCode;
    }

    public void setLpCode(String lpCode) {
        this.lpCode = lpCode;
    }

    private List<ChooseOption> staStatus;

    // 装箱中
    public String vmiReturnPacking() throws Exception {
        JSONObject result = new JSONObject();
        try {
            wareHouseManager.vmiReturnPacking(staID);
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            result.put("result", ERROR);
            result.put("message", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, result);
        return JSON;
    }

    // 取消装箱
    public String cancelReturnPacking() throws Exception {
        JSONObject result = new JSONObject();
        try {
            wareHouseManagerCancel.cancelReturnPacking(staID);
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            result.put("result", ERROR);
            result.put("message", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, result);
        return JSON;
    }

    // 创建

    public String vmiReturnCreateEntry() {
        return SUCCESS;
    }

    public String vmireturnorderbinding() {
        return SUCCESS;
    }

    // 执行
    public String vmiReturnExecuteEntry() {
        // staStatus =
        // chooseOptionDao.findAllOptionListByCategoryCode(Constants.CHOOSEOPTION_CATEGORY_CODE_STA_STATUS);
        // request.put("staStatus", staStatus);
        return SUCCESS;
    }

    // print
    public String printVmiReturninfo() throws Exception {
        JasperPrint jpJasperPrint = new JasperPrint();
        try {
            jpJasperPrint = printManager.printVmiReturnInfo(staID, userDetails.getCurrentOu().getId());
            return printObject(jpJasperPrint);
        } catch (Exception e) {
            log.error("", e);
            return PRINT;
        }
    }



    // 创批导入
    public String zdhPiciImport() {
        String msg = SUCCESS;
        request.put("msg", msg);
        request.put("result", SUCCESS);
        if (file == null) {
            msg = "The upload file can not be null...";
            log.error(msg);
            request.put("msg", msg);
        } else {
            try {
                ReadStatus rs = excelReadManager.zdhPiciImport(file, userDetails.getCurrentOu().getId(), userDetails.getUser().getId());
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
                sb.append(e.getMessage());
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

    // 创建 - 批量导入
    public String vmiReturnPlImport() {
        String msg = SUCCESS;
        request.put("msg", msg);
        request.put("result", SUCCESS);
        if (file == null) {
            msg = "The upload file can not be null...";
            log.error(msg);
            request.put("msg", msg);
        } else {
            try {
                Long cmpOuid = userDetails.getCurrentOu().getParentUnit().getParentUnit().getId();
                StaDeliveryInfo stadelivery = new StaDeliveryInfo();
                ReadStatus rs = excelReadManager.createStaForVMIReturnPl(owner, stadelivery, file, innerShopCode, toLocation, userDetails.getCurrentOu().getId(), cmpOuid, userDetails.getUser().getId(), reasonCode, espritFlag, espritTransferFlag);
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
                sb.append(e.getMessage());
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


    // 创建 - 导入
    public String vmiReturnImport() {
        String msg = SUCCESS;
        request.put("msg", msg);
        request.put("result", SUCCESS);
        if (file == null) {
            msg = "The upload file can not be null...";
            log.error(msg);
            request.put("msg", msg);
        } else {
            try {
                Long cmpOuid = userDetails.getCurrentOu().getParentUnit().getParentUnit().getId();
                StockTransApplication sta = new StockTransApplication();
                sta.setType(StockTransApplicationType.valueOf(intType));
                sta.setSlipCode1(this.slipCode1);
                sta.setActivitySource(activitySource);
                sta.setImperfectType(this.imperfectType);
                sta.setRefSlipCode(orderCode);
                if (freightMode != null) {
                    sta.setFreightMode(FreightMode.valueOf(freightMode));
                }
                StaDeliveryInfo stadelivery = new StaDeliveryInfo();
                stadelivery.setProvince(this.getProvince());
                stadelivery.setCity(this.getCity());
                stadelivery.setDistrict(this.getDistrict());
                stadelivery.setAddress(this.getAddress());
                stadelivery.setReceiver(this.getReceiver());
                stadelivery.setTelephone(this.getTelphone());
                stadelivery.setLpCode(this.lpCode);

                ReadStatus rs = excelReadManager.createStaForVMIReturn(sta, stadelivery, file, innerShopCode, toLocation, userDetails.getCurrentOu().getId(), cmpOuid, userDetails.getUser().getId(), reasonCode, espritFlag, espritTransferFlag);
               if(rs!=null&&!"".equals(rs.getMessage())){
                request.put("msg", msg+rs.getMessage());
               }
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

    // GUCCI - 导入
    public String vmiReturnImportGucci() {
        String msg = SUCCESS;
        request.put("msg", msg);
        request.put("result", SUCCESS);
        if (file == null) {
            msg = "The upload file can not be null...";
            log.error(msg);
            request.put("msg", msg);
        } else {
            try {

                ReadStatus rs = excelReadManager.gucciRtoInvStatus(file, rtoId, userDetails.getCurrentOu().getId());
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
            } catch (BusinessException e) {
                request.put("msg", "error");
                log.error("vmiReturnImportGucci rtoId:" + rtoId + " errorCode:", e.getErrorCode());
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
     * 获取VMI退仓的sta 条件查询
     * 
     * @return
     */
    public String vmiReturnStaQuery() {
        setTableConfig();
        if (status != null && status != 0) {
            if (staCommand == null) {
                staCommand = new StockTransApplicationCommand();
            }
            staCommand.setStatus(StockTransApplicationStatus.valueOf(status));
            staCommand.setLastModifyTime(new Date());
        }
        request.put(JSON, toJson(wareHouseManager.findVMIReturnSta(tableConfig.getStart(), tableConfig.getPageSize(), userDetails.getCurrentOu().getId(), FormatUtil.getDate(startTime), FormatUtil.getDate(endTime), staCommand, tableConfig.getSorts())));
        return JSON;
    }

    /***
     * details
     * 
     * @return
     */
    public String vmiReturnDetails() {
        setTableConfig();
        request.put(JSON, toJson(wareHouseManager.findVmiReturnDetails(staID)));
        return JSON;
    }

    /**
     * 出库执行
     * 
     * @return
     * @throws Exception
     */

    public String executeVmiReturn() throws Exception {
        JSONObject result = new JSONObject();
        try {
            wareHouseManagerExe.executeVmiReturnOutBound(staID, userDetails.getUser().getId(), userDetails.getCurrentOu().getId());
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            result.put("result", ERROR);
            result.put("message", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 出入库取消
     * 
     * @return
     * @throws JSONException
     */

    public String cancelVmiReturn() throws Exception {
        JSONObject result = new JSONObject();
        try {
            wareHouseManagerCancel.cancelVmiReturnOutBound(staID, userDetails.getUser().getId());
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            result.put("result", ERROR);
            result.put("message", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 获取渠道vmiCode
     */
    public String getChannelVmiCode() throws Exception {
        request.put(JSON, JsonUtil.obj2json(wareHouseManager.getChannelVmiCode(code)));
        return JSON;
    }

    /**
     * 获取对应转店退仓退仓地址信息
     * 
     * @return
     * @throws Exception
     */
    public String getVmiReturnAddress() throws Exception {
        request.put(JSON, JsonUtil.obj2json(wareHouseManager.getVmiReturnAddress(staID)));
        return JSON;
    }

    /**
     * 判断订单是否有装箱
     * 
     * @return
     * @throws Exception
     */
    public String judgeCarton() throws Exception {
        JSONObject result = new JSONObject();
        boolean b = wareHouseManager.judgeCarton(staID);
        result.put("result", b);
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 修改退仓地址信息
     * 
     * @return
     * @throws Exception
     */
    public String editVmiReturnAddress() throws Exception {
        JSONObject result = new JSONObject();
        try {
            Long ouid = userDetails.getCurrentOu().getId();
            Long uid = userDetails.getUser().getId();
            StaDeliveryInfo stadelivery = new StaDeliveryInfo();
            stadelivery.setProvince(this.getProvince());
            stadelivery.setCity(this.getCity());
            stadelivery.setDistrict(this.getDistrict());
            stadelivery.setAddress(this.getAddress());
            stadelivery.setReceiver(this.getReceiver());
            stadelivery.setTelephone(this.getTelphone());
            stadelivery.setLpCode(this.getLpCode());
            wareHouseManager.editVmiReturnAddress(staID, ouid, uid, stadelivery);
            result.put("result", "success");
        } catch (Exception e) {
            result.put("result", "error");
            log.error("", e);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 验证作业单对应装箱表中是否有执行中&完成的单据
     * 
     * @return
     * @throws Exception
     */
    public String checkCartonstatus() throws Exception {
        JSONObject result = new JSONObject();
        try {
            int count = wareHouseManager.checkCartonstatus(staID);
            if (count > 0) {
                result.put("result", "errorcount");
            } else {
                result.put("result", "success");
            }
        } catch (Exception e) {
            log.error("", e);
            result.put("result", "error");
        }
        request.put(JSON, result);
        return JSON;
    }


    public String getChannelVmiCodeById() throws Exception {
        request.put(JSON, JsonUtil.obj2json(wareHouseManager.getChannelVmiCodeById(id)));
        return JSON;
    }

    /**
     * 绑定指令
     * 
     * @return
     * @throws Exception
     */
    public String vmireturnOrderbindingConfirm() throws Exception {
        JSONObject result = new JSONObject();
        try {
            wareHouseManager.vmiReturnOrderBinding(orderCode, refSlipCode);
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            result.put("result", ERROR);
            result.put("message", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 解除绑定指令
     * 
     * @return
     * @throws Exception
     */
    public String vmireturnRemorebinding() throws Exception {
        JSONObject result = new JSONObject();
        try {
            wareHouseManager.vmireturnRemoreOrderbinding(orderCode, refSlipCode);
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            result.put("result", ERROR);
            result.put("message", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * converse转店退仓批量创指令
     * 
     * @return
     */
    public String converseToShopAndShutout() {

        return SUCCESS;
    }

    public String vmiReturnImportConverse() {
        String msg = SUCCESS;
        request.put("msg", msg);
        request.put("result", SUCCESS);
        if (file == null) {
            msg = "The upload file can not be null...";
            log.error(msg);
            request.put("msg", msg);
        } else {
            try {
                Long cmpOuid = userDetails.getCurrentOu().getParentUnit().getParentUnit().getId();
                ReadStatus rs = excelReadManager.vmiReturnImportConverse(owner, returnReason, file, null, null, userDetails.getCurrentOu().getId(), cmpOuid, userDetails.getUser().getId(), resonCode, true, true);
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
                sb.append(e.getMessage());
                log.error(e.getMessage());
                request.put("msg", sb.toString());
            } catch (Exception e) {
                request.put("msg", "error");
                // e.printStackTrace();
                if (log.isErrorEnabled()) {
                    log.error("vmiReturnImportConverse Exception:", e);
                };
                log.error(e.getMessage());
                request.put("msg", e.getMessage());
            }
        }
        return SUCCESS;
    }

    /**
     * gucci退仓指令
     * 
     * @return
     * @throws JSONException
     */
    public String findGucciRtnList() throws JSONException {
        setTableConfig();
        if (StringUtil.isEmpty(slipCode)) {
            slipCode = null;
        }
        if (StringUtil.isEmpty(toLoction)) {
            toLoction = null;
        }
        request.put(JSON, toJson(wareHouseManager.findGucciRtnList(tableConfig.getStart(), tableConfig.getPageSize(), FormatUtil.getDate(startTime), FormatUtil.getDate(endTime), slipCode, toLoction)));
        return JSON;
    }

    /**
     * gucci退仓指令明细
     * 
     * @return
     * @throws JSONException
     */
    public String findGucciRtoLineList() throws JSONException {
        setTableConfig();
        request.put(JSON, toJson(wareHouseManager.findGucciRtoLineList(tableConfig.getStart(), tableConfig.getPageSize(), rtoId)));
        return JSON;
    }


    /**
     * 导出退仓信息
     * 
     * @throws Exception
     */
    public void exportGucciRtoLineInfo() throws Exception {
        Pagination<VmiRtoLineCommand> crlcP = wareHouseManager.findGucciRtoLineList(-1, -1, rtoId);
        //
        response.setContentType("application/octet-stream;charset=ISO8859-1");
        String fileName = "GUCCI退仓信息";
        fileName = fileName.replace(" ", "");
        fileName = new String(fileName.getBytes("GBK"), "ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + Constants.EXCEL_XLS);
        ServletOutputStream os = response.getOutputStream();
        try {
            Excexport.exportGucciRtoLineInfo(os, crlcP.getItems());
            os.flush();
            os.close();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    public StockTransApplicationCommand getStaCommand() {
        return staCommand;
    }

    public void setStaCommand(StockTransApplicationCommand staCommand) {
        this.staCommand = staCommand;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getStaID() {
        return staID;
    }

    public void setStaID(Long staID) {
        this.staID = staID;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Long getOwnerid() {
        return ownerid;
    }

    public void setOwnerid(Long ownerid) {
        this.ownerid = ownerid;
    }

    public String getInnerShopCode() {
        return innerShopCode;
    }

    public void setInnerShopCode(String innerShopCode) {
        this.innerShopCode = innerShopCode;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<ChooseOption> getStaStatus() {
        return staStatus;
    }

    public void setStaStatus(List<ChooseOption> staStatus) {
        this.staStatus = staStatus;
    }

    public int getIntType() {
        return intType;
    }

    public void setIntType(int intType) {
        this.intType = intType;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    /*
     * public StaDeliveryInfo getStadelivery() { return stadelivery; } public void
     * setStadelivery(StaDeliveryInfo stadelivery) { this.stadelivery = stadelivery; }
     */
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public boolean isEspritFlag() {
        return espritFlag;
    }

    public void setEspritFlag(boolean espritFlag) {
        this.espritFlag = espritFlag;
    }

    public boolean isEspritTransferFlag() {
        return espritTransferFlag;
    }

    public void setEspritTransferFlag(boolean espritTransferFlag) {
        this.espritTransferFlag = espritTransferFlag;
    }

    public String getSlipCode1() {
        return slipCode1;
    }

    public void setSlipCode1(String slipCode1) {
        this.slipCode1 = slipCode1;
    }

    public String getActivitySource() {
        return activitySource;
    }

    public void setActivitySource(String activitySource) {
        this.activitySource = activitySource;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public Integer getFreightMode() {
        return freightMode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public void setFreightMode(Integer freightMode) {
        this.freightMode = freightMode;
    }

    public String getRefSlipCode() {
        return refSlipCode;
    }

    public void setRefSlipCode(String refSlipCode) {
        this.refSlipCode = refSlipCode;
    }

    public String getImperfectType() {
        return imperfectType;
    }

    public void setImperfectType(String imperfectType) {
        this.imperfectType = imperfectType;
    }


    public String getResonCode() {
        return resonCode;
    }

    public void setResonCode(String resonCode) {
        this.resonCode = resonCode;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getReturnReason() {
        return returnReason;
    }

    public void setReturnReason(String returnReason) {
        this.returnReason = returnReason;
    }

    public String getSlipCode() {
        return slipCode;
    }

    public void setSlipCode(String slipCode) {
        this.slipCode = slipCode;
    }

    public String getToLoction() {
        return toLoction;
    }

    public void setToLoction(String toLoction) {
        this.toLoction = toLoction;
    }

    public Long getRtoId() {
        return rtoId;
    }

    public void setRtoId(Long rtoId) {
        this.rtoId = rtoId;
    }


}
