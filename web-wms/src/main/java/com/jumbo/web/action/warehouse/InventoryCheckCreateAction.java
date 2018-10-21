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

import loxia.dao.Pagination;
import loxia.support.excel.ExcelKit;
import loxia.support.excel.ReadStatus;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.manager.ExcelExportManager;
import com.jumbo.wms.manager.warehouse.WareHouseLocationManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExe;
import com.jumbo.wms.manager.warehouse.WareHouseManagerQuery;
import com.jumbo.wms.manager.warehouse.excel.ExcelReadManager;
import com.jumbo.wms.model.warehouse.InventoryCheck;
import com.jumbo.wms.model.warehouse.InventoryCheckCommand;
import com.jumbo.wms.model.warehouse.InventoryCheckDifTotalLineCommand;
import com.jumbo.wms.model.warehouse.InventoryCheckLineCommand;
import com.jumbo.wms.model.warehouse.InventoryCommand;
import com.jumbo.wms.model.warehouse.WarehouseDistrict;
import com.jumbo.wms.model.warehouse.WarehouseLocationCommand;
import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;

public class InventoryCheckCreateAction extends BaseJQGridProfileAction {

    private static final long serialVersionUID = -2378309681628400263L;
    @Autowired
    private WareHouseManager warehouseManager;
    @Autowired
    private WareHouseManagerExe wareHouseManagerExe;
    @Autowired
    private ExcelReadManager excelReadManager;
    @Autowired
    private WareHouseManagerQuery wareHouseManagerQuery;
    @Autowired
    private WareHouseLocationManager wareHouseLocationManager;
    @Autowired
    private ExcelExportManager excelExportManager;

    private WarehouseLocationCommand locationcmd;
    private List<InventoryCheckLineCommand> icclist = new ArrayList<InventoryCheckLineCommand>();
    private String remark;
    private Long invcheckid;
    private File file;
    private Boolean daily;

    public Boolean getDaily() {
        return daily;
    }

    public void setDaily(Boolean daily) {
        this.daily = daily;
    }

    public String invCkCreateEntry() {
        return SUCCESS;
    }

    public String findDistrictList() {
        setTableConfig();
        List<WarehouseDistrict> list = wareHouseLocationManager.findDistrictListByOuId(userDetails.getCurrentOu().getId(), tableConfig.getSorts());
        request.put(JSON, toJson(list));
        return JSON;
    }

    public String findLocationList() {
        setTableConfig();
        Pagination<WarehouseLocationCommand> locationList = wareHouseManagerQuery.findLocationList(tableConfig.getStart(), tableConfig.getPageSize(), locationcmd, userDetails.getCurrentOu().getId(), tableConfig.getSorts());
        request.put(JSON, toJson(locationList));
        return JSON;
    }

    /**
     * 创建库存盘点列表信息
     * 
     * @return
     */
    public String createInventoryCheck() throws Exception {
        JSONObject result = new JSONObject();
        List<String> msg = new ArrayList<String>();
        try {
            InventoryCheck entry = wareHouseManagerExe.createInventoryCheck(remark, this.userDetails.getCurrentOu().getId(), icclist, this.userDetails.getUser().getId(), daily);
            result.put("invcheckid", entry.getId());
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            result.put("result", ERROR);
            msg.add(getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
            BusinessException c = e;
            while (c.getLinkedException() != null) {
                c = c.getLinkedException();
                msg.add(getMessage(ErrorCode.BUSINESS_EXCEPTION + c.getErrorCode(), c.getArgs()));
            }
            result.put("msg", JsonUtil.collection2json(msg));
        } catch (Exception e) {
            log.error("", e);
            msg.add(e.getCause() + " " + e.getMessage());
            result.put("result", ERROR);
            result.put("msg", msg);
        }
        request.put(JSON, result);
        return JSON;
    }

    public String createInventoryCheckMode2() throws JSONException {
        JSONObject result = new JSONObject();
        List<String> msg = new ArrayList<String>();
        try {
            InventoryCheck entry = wareHouseManagerExe.createInventoryCheckMode2(remark, this.userDetails.getCurrentOu().getId(), icclist, this.userDetails.getUser().getId());
            result.put("invcheckid", entry.getId());
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            result.put("result", ERROR);
            msg.add(getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
            BusinessException c = e;
            while (c.getLinkedException() != null) {
                c = c.getLinkedException();
                msg.add(getMessage(ErrorCode.BUSINESS_EXCEPTION + c.getErrorCode(), c.getArgs()));
            }
            result.put("msg", JsonUtil.collection2json(msg));
        } catch (Exception e) {
            log.error("", e);
            msg.add(e.getCause() + " " + e.getMessage());
            result.put("result", ERROR);
            result.put("msg", msg);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 盘点库位列表
     * 
     * @return
     */
    public String findinvCheckLineDetial() {
        setTableConfig();
        Pagination<InventoryCheckLineCommand> list = warehouseManager.findinvCheckLineDetial(tableConfig.getStart(), tableConfig.getPageSize(), invcheckid, tableConfig.getSorts());
        request.put(JSON, toJson(list));
        return JSON;
    }

    /**
     * 盘点库位列表
     * 
     * @return
     */
    public String findVMIinvCheckLineDetial() {
        setTableConfig();
        Pagination<InventoryCheckDifTotalLineCommand> list = warehouseManager.findVMIinvCheckLineDetial(tableConfig.getStart(), tableConfig.getPageSize(), invcheckid, tableConfig.getSorts());
        request.put(JSON, toJson(list));
        return JSON;
    }

    /**
     * 盘点数据导出
     * 
     * @throws Exception
     */
    public void exportInventoryCheck() throws Exception {
        response.setContentType("application/octet-stream;charset=" + Constants.UTF_8);
        String fileName = this.getMessage("excel.tplt_export_inventory_check", new Object[] {}, this.getLocale());
        fileName = new String(fileName.getBytes("GBK"), "ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + Constants.EXCEL_XLS);
        try {
            excelExportManager.exportInventoryCheck(response.getOutputStream(), invcheckid);
        } catch (Exception e) {
            log.error("", e);
        }
        ServletOutputStream outs = response.getOutputStream();
        outs.flush();
        outs.close();
    }


    /**
     * 
     * @return
     */
    public String findinvCheckInfoById() {
        JSONObject result = new JSONObject();
        try {
            InventoryCheckCommand entry = warehouseManager.findInventoryCheckById(invcheckid);
            result.put("invcheck", JsonUtil.obj2json(entry));
        } catch (Exception e) {
            log.error("", e);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 
     * @return
     */
    public String findVMIinvCheckInfoById() {
        JSONObject result = new JSONObject();
        try {
            InventoryCheckCommand entry = warehouseManager.findVMIICById(invcheckid);
            result.put("invcheck", JsonUtil.obj2json(entry));
        } catch (Exception e) {
            log.error("", e);
        }
        request.put(JSON, result);
        return JSON;
    }

    public String findOwnerListByInv() {
        setTableConfig();
        List<InventoryCommand> list = warehouseManager.findOwnerListByInv(userDetails.getCurrentOu().getId());
        request.put(JSON, toJson(list));
        return JSON;
    }

    public String findBrandListByInv() {
        setTableConfig();
        List<InventoryCommand> list = warehouseManager.findBrandListByInv(userDetails.getCurrentOu().getId());
        request.put(JSON, toJson(list));
        return JSON;
    }

    /**
     * 
     JSONObject result=new JSONObject(); List<String> msg = new ArrayList<String>(); try {
     * InventoryCheck entry = warehouseManager.createInventoryCheck(remark,
     * this.userDetails.getCurrentOu().getId(), icclist, this.userDetails.getUser().getId());
     * result.put("invcheckid", entry.getId()); result.put("result", SUCCESS); } catch
     * (BusinessException e) { result.put("result", ERROR);
     * msg.add(getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
     * BusinessException c = e; while (c.getLinkedException() != null){ c = c.getLinkedException();
     * msg.add(getMessage(ErrorCode.BUSINESS_EXCEPTION + c.getErrorCode(), c.getArgs())); }
     * result.put("msg",JsonUtil.collection2json(msg)); }catch (Exception e) { log.error("",e);
     * msg.add(e.getCause()+ " "+e.getMessage()); result.put("result", ERROR);
     * result.put("msg",msg); } request.put(JSON, result); return JSON;
     * 
     * @return
     */
    // 创建 - 导入
    public String inventoryCheckByImport() {
        String msg = SUCCESS;
        request.put("msg", msg);
        request.put("result", SUCCESS);
        if (file == null) {
            msg = "The upload file can not be null...";
            log.error(msg);
            request.put("msg", msg);
        } else {
            try {
                // createStaForVMIReturn //locs
                Map<String, Object> resultMap = excelReadManager.createInventoryCheckByImportLocation(file, remark, userDetails.getCurrentOu().getId(), userDetails.getUser().getId());
                if (resultMap.isEmpty()) {
                    throw new BusinessException("error");
                }
                ReadStatus rs = (ReadStatus) resultMap.get("rs");
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
                //
                InventoryCheck inventoryCheck = (InventoryCheck) resultMap.get("inventoryCheck");
                if (inventoryCheck != null) {
                    request.put("invcheckid", inventoryCheck.getId());
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

    public WarehouseLocationCommand getLocationcmd() {
        return locationcmd;
    }

    public void setLocationcmd(WarehouseLocationCommand locationcmd) {
        this.locationcmd = locationcmd;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<InventoryCheckLineCommand> getIcclist() {
        return icclist;
    }

    public void setIcclist(List<InventoryCheckLineCommand> icclist) {
        this.icclist = icclist;
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
}
