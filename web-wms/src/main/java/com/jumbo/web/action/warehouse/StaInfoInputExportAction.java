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
import com.jumbo.wms.manager.DropDownListManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseOutBoundManager;
import com.jumbo.wms.manager.warehouse.excel.ExcelValidateManager;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.warehouse.PickingList;
import com.jumbo.wms.model.warehouse.PickingListCheckMode;

/**
 * 库内移动
 * 
 * @author dly
 * 
 */
public class StaInfoInputExportAction extends BaseJQGridProfileAction {

    /**
     * 
     */
    private static final long serialVersionUID = 3406958968393551897L;

    @Autowired
    private ExcelExportManager excelExportManager;
    @Autowired
    private WareHouseOutBoundManager warehouseOutBoundManager;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private DropDownListManager dropDownListManager;
    @Autowired
    private ExcelValidateManager excelValidateManager;

    private Long plId;
    private String plCode;
    private List<ChooseOption> plStatus;
    private File file;

    public String staInfoInputExport() {
        plStatus = dropDownListManager.findPickingListStatusForVerify();
        return SUCCESS;
    }

    /**
     * 销售（换货）发货表导出
     * 
     * @throws IOException
     * @throws UnsupportedEncodingException
     * @throws Exception
     */
    public void salesSendOutInfoExport() throws UnsupportedEncodingException, IOException {
        response.setContentType("application/octet-stream;charset=ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=" + plCode + Constants.EXCEL_XLS);
        ServletOutputStream outs = null;
        try {
            outs = response.getOutputStream();
            excelExportManager.salesSendOutInfoExport(outs, plId);
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
     * 退货登记表导出
     * 
     * @throws IOException
     * @throws UnsupportedEncodingException
     * @throws Exception
     */
    public void returnRegisterInfoExport() throws UnsupportedEncodingException, IOException {
        response.setContentType("application/octet-stream;charset=ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=Return_Register_Info" + Constants.EXCEL_XLS);
        ServletOutputStream outs = null;
        try {
            outs = response.getOutputStream();
            excelExportManager.returnRegisterInfoExport(outs, userDetails.getCurrentOu().getId());
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
     * 库存报表导出
     * 
     * @throws IOException
     * @throws UnsupportedEncodingException
     * @throws Exception
     */
    public void inventoryReportkExport() throws UnsupportedEncodingException, IOException {
        response.setContentType("application/octet-stream;charset=ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=Invenotry_Report" + Constants.EXCEL_XLS);
        ServletOutputStream outs = null;
        try {
            outs = response.getOutputStream();
            excelExportManager.inventoryReportkExport(outs, userDetails.getCurrentOu().getId());
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
     * 退货入库导入
     * 
     * @throws Exception
     */
    public String returnRequestInboundImport() throws Exception {
        List<String> errorMsg = new ArrayList<String>();
        if (file == null) {
            request.put("result", ERROR);
            return ERROR;
        } else {
            try {
                ReadStatus rs = excelValidateManager.importReturnRequestInbound(file, userDetails.getCurrentOu().getId(), userDetails.getUser());
                request.put("result", ERROR);
                if (rs.getStatus() == ReadStatus.STATUS_SUCCESS) {
                    request.put("result", SUCCESS);
                } else if (rs.getStatus() > 0) {
                    List<String> list = ExcelKit.getInstance().getReadStatusMessages(rs, this.getLocale());
                    for (String s : list) {
                        errorMsg.add(s);
                    }
                } else if (rs.getStatus() < 0) {
                    for (Exception ex : rs.getExceptions()) {
                        if (ex instanceof BusinessException) {
                            BusinessException bex = (BusinessException) ex;
                            String msg = this.getMessage(ErrorCode.BUSINESS_EXCEPTION + bex.getErrorCode(), bex.getArgs());
                            errorMsg.add(msg);
                        }
                    }
                }
            } catch (BusinessException e) {
                log.error("", e);
                request.put("result", ERROR);
                if (e.getErrorCode() > 0) {
                    errorMsg.add(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
                }
                String ss = null;
                BusinessException current = e;
                while (current.getLinkedException() != null) {
                    current = current.getLinkedException();
                    ss = this.getMessage(ErrorCode.BUSINESS_EXCEPTION + current.getErrorCode(), current.getArgs());
                    errorMsg.add(ss);
                }
            } catch (Exception e) {
                log.error("", e);
                if (e instanceof BusinessException) {
                    BusinessException be = (BusinessException) e;
                    request.put("result", ERROR);
                    String msg = this.getMessage(ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode(), be.getArgs());
                    errorMsg.add(msg);
                } else {
                    request.put("result", ERROR);
                    errorMsg.add(e.getMessage());
                }
            }
            request.put("message", JsonUtil.collection2json(errorMsg));
        }
        return SUCCESS;
    }

    /**
     * 生成配货清单
     * 
     * @return
     * @throws JSONException
     */
    public String createPickListByAllSta() throws JSONException {
        JSONObject result = new JSONObject();
        PickingList pl = null;
        List<Long> staIds = warehouseOutBoundManager.findPickingListByAllSta(userDetails.getCurrentOu().getId());
        result.put("staCount", staIds.size());
        if (staIds.size() == 0) {
            result.put("result", ERROR);
            result.put("isStaCountNull", "true");
            request.put(JSON, result);
            return JSON;
        }
        try {
            pl = warehouseOutBoundManager.createPickingList(null, staIds, userDetails.getCurrentOu().getId(), userDetails.getUser().getId(), PickingListCheckMode.DEFAULE, null, null, null, null, null, null, null, null, null, false, false, null, null);
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            log.error("", e);
            result.put("result", ERROR);
            result.put("message", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
            BusinessException el = e.getLinkedException();
            if (el != null) {
                result.put("message", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + el.getErrorCode(), el.getArgs()));
            }
            request.put(JSON, result);
            return JSON;
        } catch (Exception e) {
            log.error("", e);
            result.put("result", ERROR);
            result.put("message", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + ErrorCode.PDA_SYS_ERROR));
            request.put(JSON, result);
            return JSON;
        }
        result.put("result", SUCCESS);
        result.put("plCode", pl.getCode());
        String resultStr = SUCCESS;
        int i = 0;
        String errorMsg = "";
        if (pl != null) {
            // 以下逻辑占用库存
            for (Long staId : staIds) {
                try {
                    wareHouseManager.createStvByStaId(staId, userDetails.getUser().getId(), null, false);
                    i++;
                } catch (BusinessException e) {
                    log.error("", e);
                    wareHouseManager.setStaOccupaidFailed(staId);
                    resultStr = ERROR;
                    errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs());
                    BusinessException current = e;
                    while (current.getLinkedException() != null) {
                        current = current.getLinkedException();
                        errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + current.getErrorCode(), current.getArgs());
                    }
                }
            }
            warehouseOutBoundManager.confirmPickingList(pl.getId(), userDetails.getUser().getId(), userDetails.getCurrentOu().getId());
        }
        result.put("staSuccessCount", i);
        result.put("staErrorCount", staIds.size() - i);
        result.put("staSuccessMsg", resultStr);
        result.put("message", errorMsg);
        request.put(JSON, result);
        return JSON;
    }

    public Long getPlId() {
        return plId;
    }

    public void setPlId(Long plId) {
        this.plId = plId;
    }

    public String getPlCode() {
        return plCode;
    }

    public void setPlCode(String plCode) {
        this.plCode = plCode;
    }

    public List<ChooseOption> getPlStatus() {
        return plStatus;
    }

    public void setPlStatus(List<ChooseOption> plStatus) {
        this.plStatus = plStatus;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

}
