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
import com.jumbo.wms.manager.channel.ChannelManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerCancel;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExe;
import com.jumbo.wms.manager.warehouse.excel.ExcelReadManager;
import com.jumbo.wms.model.warehouse.InventoryCheck;
import com.jumbo.wms.model.warehouse.InventoryCheckCommand;

public class InventoryVMIAdjustmentHandelAction extends BaseJQGridProfileAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = 7334468358137004285L;

    @Autowired
    private WareHouseManager warehouseManager;
    @Autowired
    private WareHouseManagerCancel wareHouseManagerCancel;
    @Autowired
    private WareHouseManagerExe warehouseManagerExe;
    @Autowired
    private ExcelReadManager excelReadManager;
    @Autowired
    private ExcelExportManager excelExportManager;
    @Autowired
    private ChannelManager channelManager;
    private Long invcheckid;
    private File file;
    private InventoryCheck invcheck;

    private String slipCode;
    private String reasonCode;
    // 店铺id
    private Long ownerid;

    public String invCKHandelEntry() {
        return SUCCESS;
    }

    /**
     * 调整列表
     * 
     * @return
     */
    public String findVMIInventoryCheckList() {
        setTableConfig();
        List<InventoryCheckCommand> list = warehouseManager.findInventoryCheckListByType(this.userDetails.getCurrentOu().getId(), 2, this.tableConfig.getSorts());
        request.put(JSON, toJson(list));
        return JSON;
    }

    public String vmiOutboundAdjustCreateEntry() {
        return SUCCESS;
    }

    /****
     * vmi 出库调整 - 导入 创建：
     * 
     * @return
     */
    public String vmiOutboundAdjustImport() {
        String msg = SUCCESS;
        request.put("msg", msg);
        request.put("result", SUCCESS);
        if (file == null) {
            msg = "The upload file can not be null...";
            log.error(msg);
            request.put("msg", msg);
        } else {
            try {
                ReadStatus rs = excelReadManager.generateVMIInventoryAdjust(slipCode, reasonCode, file, ownerid, userDetails.getCurrentOu().getId(), userDetails.getUser().getId(), invcheck);
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

    /**
     * 导入
     * 
     * @return
     */
    public String importVMIInvCheck() {
        List<String> errorMsg = new ArrayList<String>();
        if (file == null) {
            return ERROR;
        }
        try {
            Long companyID = this.userDetails.getCurrentOu().getParentUnit().getParentUnit().getId();
            Long whOUID = this.userDetails.getCurrentOu().getId();
            ReadStatus rs = excelReadManager.vmiAdjustmentImport(file, invcheckid, companyID, whOUID);
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
        } catch (Exception e) {
            log.error("", e);
            request.put("result", ERROR);
            errorMsg.add(e.getCause() + " " + e.getMessage());
        }
        request.put("message", JsonUtil.collection2json(errorMsg));
        return SUCCESS;
    }

    public String generateSKU() throws Exception {
        JSONObject result = new JSONObject();
        try {
            warehouseManager.generateSkuByPo(slipCode, ownerid);
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            log.error("", e);
            result.put("result", ERROR);
            result.put("message", businessExceptionPost(e));
        }
        request.put(JSON, result);
        return JSON;
    }

    private String businessExceptionPost(BusinessException e) {
        StringBuilder sb = new StringBuilder();
        if (e.getErrorCode() > 0) {
            sb.append(getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()) + Constants.HTML_LINE_BREAK);
        }
        for (BusinessException be = e.getLinkedException(); be != null; be = be.getLinkedException()) {
            sb.append(getMessage(ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode(), be.getArgs()) + Constants.HTML_LINE_BREAK);
        }
        return sb.toString();
    }

    /**
     * 取消调整单据
     * 
     * @return
     * @throws JSONException
     */
    public String cancelInvCheck() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            wareHouseManagerCancel.cancelInvCheck(invcheckid, this.userDetails.getUser().getId());
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
     * 差异确认
     * 
     * @return
     * @throws JSONException
     */
    public String confirmVMIInvCKAdjustment() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            warehouseManagerExe.confirmVMIInvCKAdjustmentNew(invcheck);
            result.put("rs", SUCCESS);
        } catch (BusinessException e) {
            log.error(getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
            result.put("msg", getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        } catch (Exception e) {
            log.error("", e);
            result.put("msg", "操作失败," + e.getCause() + " " + e.getMessage());
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * VMI调整数据导出
     * 
     * @throws Exception
     */
    public void exportVMIInventoryCheck() throws Exception {
        response.setContentType("application/octet-stream;charset=" + Constants.UTF_8);
        String fileName = this.getMessage("excel.tplt_export_vmi_adjustment_invcheck", new Object[] {}, this.getLocale());
        fileName = new String(fileName.getBytes("GBK"), "ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + Constants.EXCEL_XLS);
        try {
            excelExportManager.exportVMIInventoryCheck(response.getOutputStream(), invcheckid);
        } catch (Exception e) {
            log.error("", e);
        }
        ServletOutputStream outs = response.getOutputStream();
        outs.flush();
        outs.close();
    }


    public String getShopInfoBy() throws JSONException {
        request.put(JSON, JsonUtil.obj2json(channelManager.getBiChannel(ownerid)));
        return JSON;
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

    public Long getOwnerid() {
        return ownerid;
    }

    public void setOwnerid(Long ownerid) {
        this.ownerid = ownerid;
    }

    public String getSlipCode() {
        return slipCode;
    }

    public void setSlipCode(String slipCode) {
        this.slipCode = slipCode;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }



}
