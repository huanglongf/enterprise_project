package com.jumbo.web.action.automaticEquipment;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.Constants;
import com.jumbo.manager.ExcelExportManager;
import com.jumbo.util.DateUtil;
import com.jumbo.util.FormatUtil;
import com.jumbo.util.StringUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.automaticEquipment.AutoBaseInfoManager;
import com.jumbo.wms.manager.pda.PdaPickingManager;
import com.jumbo.wms.model.automaticEquipment.GoodsCollectionLog;
import com.jumbo.wms.model.command.automaticEquipment.PopUpAreaCommand;
import com.jumbo.wms.model.pda.PdaSortPickingCommand;

import loxia.dao.Pagination;
import loxia.support.excel.ReadStatus;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;


/**
 * @author lihui
 *
 * @createDate 2016年1月20日 下午3:29:54
 */
public class PopUpAreaAction extends BaseJQGridProfileAction {


    private static final long serialVersionUID = 4135139310496846146L;
    @Autowired
    private AutoBaseInfoManager autoBaseInfoManager;
    @Autowired
    private PdaPickingManager pdaPickingManager;
	
    private PopUpAreaCommand popUpAreaCommand;
    
    private String idStr;

    private Boolean status;

    private PdaSortPickingCommand pdaSortPickingCommand;

    private String startDateStr;

    private String endDateStr;
    @Autowired
    private ExcelExportManager excelExportManager;

    /**
     * 文件导入
     */
    private File importPopUpFile;

    private GoodsCollectionLog goodsCollectionLog;

    public String initPopUpAreaPage() {

        return SUCCESS;
    }
    
    public String findPopUpAreaByParams() {
        setTableConfig();
        Map<String, Object> params = new HashMap<String, Object>();
        if (popUpAreaCommand != null) {
            if (!"".equals(popUpAreaCommand.getCode())) {
                params.put("code", popUpAreaCommand.getCode());
            }
            if (!"".equals(popUpAreaCommand.getBarcode())) {
                params.put("barcode", popUpAreaCommand.getBarcode());
            }
            if (!"".equals(popUpAreaCommand.getName())) {
                params.put("name", popUpAreaCommand.getName());
            }
            if (!"".equals(popUpAreaCommand.getWscPopCode())) {
                params.put("wscPopCode", popUpAreaCommand.getWscPopCode());
            }
        }


        Pagination<PopUpAreaCommand> zoonList = autoBaseInfoManager.findPopUpAreaByParams(tableConfig.getStart(), tableConfig.getPageSize(), params);
        request.put(JSON, toJson(zoonList));
        return JSON;
    }

    public String savePopUpArea() throws JSONException {
        JSONObject result = new JSONObject();


        if (popUpAreaCommand != null) {
            try {

                String flag = autoBaseInfoManager.savePopUpArea(popUpAreaCommand);
                if (flag == null) {

                    result.put("flag", SUCCESS);
                } else {
                    result.put("flag", flag);
                }
            } catch (Exception e) {
                result.put("flag", ERROR);
                log.error(e.getMessage());
            }
        } else {
            result.put("flag", "信息不完整！");
        }


        request.put(JSON, result);
        return JSON;
    }

    public String updatePopUpAreaByIds() throws JSONException {
        if (idStr != null && !"".equals(idStr)) {
            JSONObject result = new JSONObject();
            try {

                Map<String, Object> params = new HashMap<String, Object>();
                List<Long> idList = new ArrayList<Long>();

                String[] ids = idStr.split(",");
                for (int i = 0; i < ids.length; i++) {
                    idList.add(Long.parseLong(ids[i]));
                }
                params.put("status", status);
                String msg = "";
                if (status) {
                    msg = autoBaseInfoManager.checkInBoundRoleUsePopUpArea(idList);
                    msg = msg + autoBaseInfoManager.checkLocationUsePopUpArea(idList);
                }
                if ("".equals(msg)) {

                    autoBaseInfoManager.updatePopUpAreaByIds(params, idList);
                    result.put("flag", SUCCESS);
                    request.put(JSON, result);
                } else {
                    result.put("flag", msg);
                    request.put(JSON, result);
                }
            } catch (Exception e) {
                result.put("flag", "系统异常" + e.getMessage());
                log.error(e.getMessage());
            }
        }
        return JSON;

    }

    public String updatePopUpArea() throws JSONException {
        JSONObject result = new JSONObject();


        if (popUpAreaCommand != null) {
            try {

                String flag = autoBaseInfoManager.updatePopUpArea(popUpAreaCommand);
                if (flag == null) {

                    result.put("flag", SUCCESS);
                } else {
                    result.put("flag", flag);
                }
            } catch (Exception e) {
                result.put("flag", ERROR);
                log.error(e.getMessage());
            }
        } else {
            result.put("flag", "信息不完整！");
        }


        request.put(JSON, result);
        return JSON;

    }

    /**
     * 库位与弹出口导入
     * 
     * @return
     */
    public String locationAndPopUpImport() throws Exception {
        String msg = SUCCESS;
        request.put("msg", msg);
        request.put("result", SUCCESS);
        if (importPopUpFile == null) {
            msg = "The upload file can not be null...";
            log.error(msg);
            request.put("msg", msg);
        } else {
            try {
                ReadStatus rs = autoBaseInfoManager.importLocationAndPopUp(importPopUpFile, userDetails.getCurrentOu().getId());
                if (rs == null) {
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                }
                if (rs != null) {
                    if (rs.getStatus() > 1) {
                        request.put("msg", rs.getMessage());
                    } else {
                        request.put("msg", SUCCESS);
                    }
                }

            } catch (BusinessException e) {
                request.put("msg", ERROR);
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
                request.put("msg", ERROR);
                log.error("", e);
                log.error(e.getMessage());
                request.put("msg", e.getMessage());
            }
        }
        return SUCCESS;
    }

    public String findSortPicking() {
        setTableConfig();
        Map<String, Object> params = new HashMap<String, Object>();
        if (pdaSortPickingCommand != null) {
            if (!StringUtil.isEmpty(pdaSortPickingCommand.getCode())) {
                params.put("code", pdaSortPickingCommand.getCode());
            }
            if (!StringUtil.isEmpty(pdaSortPickingCommand.getPickingCode())) {
                params.put("pickingCode", pdaSortPickingCommand.getPickingCode());
            }
            if (!StringUtil.isEmpty(pdaSortPickingCommand.getPickingZoonCode())) {
                params.put("pickingZoonCode", pdaSortPickingCommand.getPickingZoonCode());
            }
            if (!StringUtil.isEmpty(pdaSortPickingCommand.getSkuName())) {
                params.put("skuName", pdaSortPickingCommand.getSkuName());
            }
            if (!"".equals(startDateStr)) {
                params.put("startDate", dateFormat(startDateStr));
            }
            if (!StringUtil.isEmpty(pdaSortPickingCommand.getSupplierCode())) {
                params.put("supplierCode", pdaSortPickingCommand.getSupplierCode());
            }
            if (!StringUtil.isEmpty(pdaSortPickingCommand.getSkuCode())) {
                params.put("skuCode", pdaSortPickingCommand.getSkuCode());
            }
            if (!StringUtil.isEmpty(pdaSortPickingCommand.getBarCode())) {
                params.put("barCode", pdaSortPickingCommand.getBarCode());
            }
            if (!"".equals(endDateStr)) {
                params.put("endDate", dateFormat(endDateStr));
            }
        }
        params.put("ouId", userDetails.getCurrentOu().getId());


        Pagination<PdaSortPickingCommand> zoonList = pdaPickingManager.findSortPicking(tableConfig.getStart(), tableConfig.getPageSize(), params);
        request.put(JSON, toJson(zoonList));
        return JSON;
    }
    

    /**
     * PDA拣货短拣导出
     * 
     */
    public void padSortPickingExcel() throws Exception {
        response.setContentType("application/octet-stream;charset=ISO8859-1");
        Map<String, Object> params = new HashMap<String, Object>();
        if (pdaSortPickingCommand != null) {
            if (!StringUtil.isEmpty(pdaSortPickingCommand.getCode())) {
                params.put("code", pdaSortPickingCommand.getCode());
            }
            if (!StringUtil.isEmpty(pdaSortPickingCommand.getPickingCode())) {
                params.put("pickingCode", pdaSortPickingCommand.getPickingCode());
            }
            if (!StringUtil.isEmpty(pdaSortPickingCommand.getPickingZoonCode())) {
                params.put("pickingZoonCode", pdaSortPickingCommand.getPickingZoonCode());
            }
            if (!StringUtil.isEmpty(pdaSortPickingCommand.getSkuName())) {
                params.put("skuName", pdaSortPickingCommand.getSkuName());
            }
            if (!"".equals(startDateStr)) {
                params.put("startDate", dateFormat(startDateStr));
            }
            if (!StringUtil.isEmpty(pdaSortPickingCommand.getSupplierCode())) {
                params.put("supplierCode", pdaSortPickingCommand.getSupplierCode());
            }
            if (!StringUtil.isEmpty(pdaSortPickingCommand.getSkuCode())) {
                params.put("skuCode", pdaSortPickingCommand.getSkuCode());
            }
            if (!StringUtil.isEmpty(pdaSortPickingCommand.getBarCode())) {
                params.put("barCode", pdaSortPickingCommand.getBarCode());
            }
            if (!"".equals(endDateStr)) {
                params.put("endDate", dateFormat(endDateStr));
            }
        }
        params.put("ouId", userDetails.getCurrentOu().getId());
        String fileName = "PDA拣货短拣";
        fileName = new String(fileName.getBytes("GBK"), "ISO8859-1");
        
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + Constants.EXCEL_XLS);
        try {
            excelExportManager.pdaSortPickingExport(response.getOutputStream(),params);
        } catch (Exception e) {
            log.error("", e);
        }
        ServletOutputStream outs = response.getOutputStream();
        outs.flush();
        outs.close();
    }

    /**
     * 人工集货日志查询
     * 
     * @return
     */
    public String findGoodsCollectionLog() {
        setTableConfig();
        Map<String, Object> params = new HashMap<String, Object>();
        if (goodsCollectionLog != null) {
            if (!StringUtil.isEmpty(goodsCollectionLog.getCollectionCode())) {
                params.put("collectionCode", goodsCollectionLog.getCollectionCode());
            }
            if (!StringUtil.isEmpty(goodsCollectionLog.getPickingCode())) {
                params.put("pickingCode", goodsCollectionLog.getPickingCode());
            }
            if (!StringUtil.isEmpty(goodsCollectionLog.getContainerCode())) {
                params.put("containerCode", goodsCollectionLog.getContainerCode());
            }
            if (!StringUtil.isEmpty(goodsCollectionLog.getStatus())) {
                params.put("status", goodsCollectionLog.getStatus());
            }
            if (!"".equals(startDateStr)) {
                params.put("startDate", dateFormat(startDateStr));
            }
            if (!"".equals(endDateStr)) {
                params.put("endDate", dateFormat(endDateStr));
            }
            if (!StringUtil.isEmpty(goodsCollectionLog.getLoginName())) {
                params.put("loginName", goodsCollectionLog.getLoginName());
            }
        }
        params.put("ouId", userDetails.getCurrentOu().getId());


        Pagination<GoodsCollectionLog> zoonList = pdaPickingManager.findGoodsCollectionLog(tableConfig.getStart(), tableConfig.getPageSize(), params);
        request.put(JSON, toJson(zoonList));
        return JSON;
    }

    public String getIdStr() {
        return idStr;
    }

    public void setIdStr(String idStr) {
        this.idStr = idStr;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public PopUpAreaCommand getPopUpAreaCommand() {
        return popUpAreaCommand;
    }

    public void setPopUpAreaCommand(PopUpAreaCommand popUpAreaCommand) {
        this.popUpAreaCommand = popUpAreaCommand;
    }

    public File getImportPopUpFile() {
        return importPopUpFile;
    }

    public void setImportPopUpFile(File importPopUpFile) {
        this.importPopUpFile = importPopUpFile;
    }

    public String getStartDateStr() {
        return startDateStr;
    }

    public void setStartDateStr(String startDateStr) {
        this.startDateStr = startDateStr;
    }

    public String getEndDateStr() {
        return endDateStr;
    }

    public void setEndDateStr(String endDateStr) {
        this.endDateStr = endDateStr;
    }
   
    private Date dateFormat(String dateStr) {
        if (null == dateStr || "".equals(dateStr)) {
            return null;
        }
        Date sDat;
        try {
            sDat = FormatUtil.stringToDate(dateStr);
        } catch (ParseException e) {
            return null;
        }
        boolean flag = DateUtil.isYearDate(dateStr);
        if (flag) {
            // 日期为"yyy-mm-dd"默认补加一天
            sDat = DateUtil.addDays(sDat, 1);
        }
        return sDat;
    }

    public PdaSortPickingCommand getPdaSortPickingCommand() {
        return pdaSortPickingCommand;
    }

    public void setPdaSortPickingCommand(PdaSortPickingCommand pdaSortPickingCommand) {
        this.pdaSortPickingCommand = pdaSortPickingCommand;
    }

    public GoodsCollectionLog getGoodsCollectionLog() {
        return goodsCollectionLog;
    }

    public void setGoodsCollectionLog(GoodsCollectionLog goodsCollectionLog) {
        this.goodsCollectionLog = goodsCollectionLog;
    }
   

}
