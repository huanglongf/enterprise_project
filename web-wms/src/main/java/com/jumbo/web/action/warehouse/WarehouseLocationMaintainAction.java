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
import javax.servlet.http.HttpServletResponse;

import loxia.support.json.JSONArray;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.jumbo.Constants;
import com.jumbo.manager.ExcelExportManager;
import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.DropDownListManager;
import com.jumbo.wms.manager.system.ChooseOptionManager;
import com.jumbo.wms.manager.warehouse.WareHouseLocationManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.excel.ExcelReadManager;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.warehouse.WarehouseDistrict;
import com.jumbo.wms.model.warehouse.WarehouseDistrictType;
import com.jumbo.wms.model.warehouse.WarehouseLocation;

/**
 * 库区库位--创建/编辑
 * 
 * @author wanghua
 * 
 */
public class WarehouseLocationMaintainAction extends BaseJQGridProfileAction implements ServletResponseAware {

    /**
	 * 
	 */
    private static final long serialVersionUID = 436391668542913040L;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private ExcelReadManager excelReadManager;
    @Autowired
    private DropDownListManager dropDownListManager;
    @Autowired
    private ChooseOptionManager chooseOptionManager;
    @Autowired
    private ExcelExportManager excelExportManager;

    @Autowired
    private WareHouseLocationManager wareHouseLocationManager;
    /**
     * 状态下拉列表
     */
    private List<ChooseOption> statusOptionList;

    /**
     * 创建/修改的model
     */
    private WarehouseDistrict district;
    /**
     * 新增的库位
     */
    private List<WarehouseLocation> locations;
    /**
     * 库区List,可用来页面显示/数据更改回传
     */
    private List<WarehouseDistrict> districtList;
    private HttpServletResponse response;
    private File districtFile;
    private String locationCode;

    /**
     * 库区库位--创建/编辑
     */
    public String locationMaintainEntry() {
        return SUCCESS;
    }

    /**
     * 当前仓库的所有库区列表
     * 
     * @return
     */
    public String districtList() {
        request.put(JSON, new JSONArray(wareHouseLocationManager.findDistrictListByOuId(userDetails.getCurrentOu().getId())));
        return JSON;
    }

    /**
     * 指定库区的所有库位列表
     * 
     * @return
     */
    public String getLocationsOfDistrict() {
        district = wareHouseLocationManager.findDistrictById(district.getId());
        statusOptionList = dropDownListManager.findStatusChooseOptionList();
        List<ChooseOption> districts = chooseOptionManager.findOptionListByCategoryCode(Constants.CHOOSEOPTION_CATEGORY_CODE_WAREHOUSE_DISTRICT_TYPE);
        List<ChooseOption> dt = new ArrayList<ChooseOption>();
        for (ChooseOption c : districts) {
            for (WarehouseDistrictType type : WarehouseDistrict.getSystemType()) {
                if (!c.getOptionKey().toString().equals(type.getValue() + "")) {
                    dt.add(c);
                }
            }
        }
        request.put("districtTypeList", dt);
        return SUCCESS;
    }

    public String getLocationsOfDistrictJson() {
        setTableConfig();
        request.put(JSON, toJson(wareHouseLocationManager.findLocationsListByDistrictId(tableConfig.getStart(), tableConfig.getPageSize(), district.getId(), tableConfig.getSorts())));
        return JSON;
    }

    public String disableLocations() throws Exception {
        try {
            wareHouseManager.disableLocations(locations);
        } catch (BusinessException e) {
            request.put(JSON, new JSONObject().put("msg", businessExceptionPost(e)));
        }
        return JSON;
    }

    public String getLocationByLocCode() throws JSONException {
        JSONObject obj = new JSONObject();
        try {
            WarehouseLocation loc = wareHouseManager.findLocationByCode(locationCode, userDetails.getCurrentOu().getId());
            if (loc != null) {
                obj.put("result", SUCCESS);
                obj.put("location", JsonUtil.obj2jsonIncludeAll(loc));
            } else {
                obj.put("result", ERROR);
            }
        } catch (BusinessException e) {
            obj.put("result", ERROR);
            obj.put("message", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, obj);
        return JSON;
    }

    /**
     * 当前仓库新增库区
     * 
     * @return
     * @throws Exception
     */
    public String createDistrict() throws Exception {
        if (district != null && StringUtils.hasLength(district.getCode()) && StringUtils.hasLength(district.getName())) {
            district.setOu(userDetails.getCurrentOu());
            district = wareHouseManager.createWarehouseDistrict(district);
        } else {
            district = null;
        }
        request.put(JSON, new JSONObject().put("id", district == null ? "failure" : district.getId()));
        return JSON;
    }

    /**
     * 修改当前仓库的指定库区
     * 
     * @return
     * @throws Exception
     */
    public String updateDistrict() throws Exception {
        if (district != null && StringUtils.hasLength(district.getName())) {
            district.setOu(userDetails.getCurrentOu());
            try {
                wareHouseLocationManager.updateWarehouseDistrict(district, locations);
            } catch (BusinessException e) {
                request.put(JSON, new JSONObject().put("msg", businessExceptionPost(e)));
            }
        }
        return JSON;
    }

    /**
     * 当前库位是否能被禁用
     * 
     * @return
     * @throws Exception
     */
    public String checkForDisableLocation() throws Exception {
        JSONObject json = new JSONObject();
        if (district != null && !wareHouseLocationManager.checkInventoryByDistrictOrLocation(null, district.getId())) {
            json.put("enable", "true");
        } else {
            json.put("enable", "false");
        }
        request.put(JSON, json);
        return JSON;
    }

    public String locationVolume() {
        return SUCCESS;
    }

    /**
     * 当前库区是否能被禁用
     * 
     * @return
     * @throws Exception
     */
    public String checkForDisableDistrict() throws Exception {
        JSONObject json = new JSONObject();
        if (district != null && !wareHouseLocationManager.checkInventoryByDistrictOrLocation(district.getId(), null)) {
            json.put("enable", "true");
        } else {
            json.put("enable", "false");
        }
        request.put(JSON, json);
        return JSON;
    }

    public String locationTPLExport() throws Exception {
        try {
            excelExportManager.locationTPLExport(response.getOutputStream());
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=tpl_location_import" + Constants.EXCEL_XLS);
        } catch (Exception e) {
            log.error("", e);
        } finally {
            ServletOutputStream outs = response.getOutputStream();
            outs.flush();
            outs.close();
        }
        return JSON;
    }

    public String locationImport() {
        if (districtFile == null) {
            log.error(" The upload file must not be null");
            request.put("msg", " The upload file must not be null");
        } else {
            try {
                excelReadManager.locationImport(district.getId(), districtFile);
                request.put("districtId", district.getId());
                request.put("msg", SUCCESS);
            } catch (BusinessException e) {
                log.error("", e);
                request.put("msg", businessExceptionPost(e));
            } catch (Exception e) {
                log.error("", e);
                log.error(e.getMessage());
                request.put("msg", e.getMessage());
            }
        }
        return SUCCESS;
    }

    public List<WarehouseDistrict> getDistrictList() {
        return districtList;
    }

    public void setDistrictList(List<WarehouseDistrict> districtList) {
        this.districtList = districtList;
    }

    public List<ChooseOption> getStatusOptionList() {
        return statusOptionList;
    }

    public void setStatusOptionList(List<ChooseOption> statusOptionList) {
        this.statusOptionList = statusOptionList;
    }

    public WarehouseDistrict getDistrict() {
        return district;
    }

    public void setDistrict(WarehouseDistrict district) {
        this.district = district;
    }

    public List<WarehouseLocation> getLocations() {
        return locations;
    }

    public void setLocations(List<WarehouseLocation> locations) {
        this.locations = locations;
    }

    public void setServletResponse(HttpServletResponse response) {
        this.response = response;
    }

    public File getDistrictFile() {
        return districtFile;
    }

    public void setDistrictFile(File districtFile) {
        this.districtFile = districtFile;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    private String businessExceptionPost(BusinessException e) {
        if (ErrorCode.ERROR_NOT_SPECIFIED == e.getErrorCode()) {
            Object[] object = e.getArgs();
            // BusinessException[] errors = (BusinessException[]) e.getArgs();
            StringBuilder sb = new StringBuilder();
            for (Object o : object) {
                BusinessException be = (BusinessException) o;
                sb.append(getMessage(ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode(), be.getArgs()) + Constants.HTML_LINE_BREAK);
            }
            return sb.toString();
        } else {
            return getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs());
        }
    }
}
