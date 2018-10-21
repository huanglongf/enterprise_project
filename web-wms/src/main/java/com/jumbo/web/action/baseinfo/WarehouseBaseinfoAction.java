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
package com.jumbo.web.action.baseinfo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.jumbo.Constants;
import com.jumbo.manager.ExcelExportManager;
import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.baseinfo.BaseinfoManager;
import com.jumbo.wms.manager.system.ChooseOptionManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerProxy;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.Customer;
import com.jumbo.wms.model.baseinfo.District;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.Transportator;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.command.automaticEquipment.ZoonCommand;
import com.jumbo.wms.model.pda.PdaLocationType;
import com.jumbo.wms.model.pda.PdaLocationTypeCommand;
import com.jumbo.wms.model.pda.PdaSkuLocTypeCapCommand;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.warehouse.AutoPlConfigCommand;
import com.jumbo.wms.model.warehouse.BiChannelImperfectCommand;
import com.jumbo.wms.model.warehouse.BiChannelImperfectLineCommand;
import com.jumbo.wms.model.warehouse.WhAddStatusSource;
import com.jumbo.wms.model.warehouse.WhAddStatusSourceCommand;

import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.support.excel.ExcelKit;
import loxia.support.excel.ReadStatus;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

public class WarehouseBaseinfoAction extends BaseJQGridProfileAction {


    /**
	 * 
	 */
    private static final long serialVersionUID = -829163863981734351L;

    @Autowired
    private BaseinfoManager baseinfoManager;
    @Autowired
    private ChooseOptionManager chooseOptionManager;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private WareHouseManagerProxy wareHouseManagerProxy;
    @Autowired
    private ExcelExportManager excelExportManager;
    private WhAddStatusSource wss;
    private OperationUnit ou;
    private Warehouse warehouse;
    private Customer customer;
    List<Customer> customerList;
    private String transIds;
    private AutoPlConfigCommand apc;
    private File file;
    private File fileType;
    private File fileWhy;
    private Long areaId;
    private Long ouId;
    private Long imperfectId;
    private PdaLocationType pdaLocationType;// 库位类型
    private String ids;// 库位类型id集合
    private PdaLocationTypeCommand pdaLocationTypeCommand;// 库位类型command
    private PdaSkuLocTypeCapCommand pdaSkuLocTypeCapCommand;
    private String name;
    private Long id;
    private String plCode;// 批次号
    private ZoonCommand zoonCommand;
    private File fileAd;

    public ZoonCommand getZoonCommand() {
        return zoonCommand;
    }

    public void setZoonCommand(ZoonCommand zoonCommand) {
        this.zoonCommand = zoonCommand;
    }

    public String getPlCode() {
        return plCode;
    }

    public void setPlCode(String plCode) {
        this.plCode = plCode;
    }

    /**
     * 库位类型查询和维护首页(页面初始化跳转)
     * 
     */
    public String locationTypeQueryMaintain() {
        return SUCCESS;
    }

    public String initZoonSort() {
        return SUCCESS;
    }



    /*
     * 库位类型查询和绑定维护首页(页面初始化跳转)
     */
    public String locationTypeBindingMaintain() {
        return SUCCESS;
    }

    public String querySkuList() {
        setTableConfig();
        Sku sku = new Sku();
        if (StringUtils.hasLength(name)) {
            sku.setName(name);
        }
        request.put(JSON, toJson(wareHouseManager.findProductForBoxByPage(tableConfig.getStart(), tableConfig.getPageSize(), sku, tableConfig.getSorts())));
        return JSON;
    }

    /**
     * 分页查询库位类型绑定
     * 
     * @return
     * @throws JSONException
     */
    public String queryLocationTypeBinding() {
        setTableConfig();
        Pagination<PdaLocationTypeCommand> list = baseinfoManager.getPdaLocationTypeBinding(tableConfig.getStart(), tableConfig.getPageSize(), userDetails.getCurrentOu().getId(), pdaLocationTypeCommand, tableConfig.getSorts());
        request.put(JSON, toJson(list));
        return JSON;
    }

    public String queryZoonSort() {
        setTableConfig();
        Pagination<ZoonCommand> list = baseinfoManager.queryZoonSort(tableConfig.getStart(), tableConfig.getPageSize(), userDetails.getCurrentOu().getId(), zoonCommand, tableConfig.getSorts());
        request.put(JSON, toJson(list));
        return JSON;
    }


    /**
     * 修改zoon
     * 
     * @return
     * @throws JSONException
     */
    public String commitZoon() throws JSONException {
        JSONObject result = new JSONObject();
        String brand = null;
        try {
            brand = baseinfoManager.commitZoon(zoonCommand, userDetails.getCurrentOu().getId());
        } catch (Exception e) {
            brand = "0";// 系统异常
            log.error("commitZoon", e);
        }
        result.put("brand", brand);
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 自动化库存数据
     */
    public String zdhReStoreData() throws JSONException {
        JSONObject result = new JSONObject();
        String brand = null;
        String msg = null;
        try {
            brand = baseinfoManager.zdhReStoreData(plCode);
        } catch (Exception e) {
            msg = e.getMessage();
            brand = "-1";// 系统异常
            log.error("zdhReStoreData", e);
        }
        result.put("brand", brand);
        result.put("msg", msg);
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 自动化单据状态还原
     */
    public String zdhReStoreStatus() throws JSONException {
        JSONObject result = new JSONObject();
        String brand = null;
        String msg = null;
        try {
            brand = baseinfoManager.zdhReStoreStatus(plCode);
        } catch (Exception e) {
            e.printStackTrace();
            msg = e.getMessage();
            brand = "-1";// 系统异常
            log.error("zdhReStoreData", e);
        }
        result.put("brand", brand);
        result.put("msg", msg);
        request.put(JSON, result);
        return JSON;
    }


    /**
     * 分页查询库位类型容量配置
     * 
     * @return
     * @throws JSONException
     */
    public String queryLocationVolumeTypeList() {
        setTableConfig();
        Pagination<PdaSkuLocTypeCapCommand> list = baseinfoManager.getPdaSkuLocTypeCapBinding(tableConfig.getStart(), tableConfig.getPageSize(), userDetails.getCurrentOu().getId(), pdaSkuLocTypeCapCommand, tableConfig.getSorts());
        request.put(JSON, toJson(list));
        return JSON;
    }

    public String queryLocationTypeNameList() {
        PdaLocationType pdaLocationType = baseinfoManager.findbyId(id);
        request.put(JSON, JsonUtil.obj2jsonIncludeAll(pdaLocationType));
        return JSON;
    }


    /**
     * 保存库位类型
     * 
     * @return
     * @throws JSONException
     */
    public String savePdaLocationType() throws JSONException {
        JSONObject result = new JSONObject();
        String brand = null;
        try {
            brand = baseinfoManager.saveLocationType(pdaLocationType, userDetails.getUser(), userDetails.getCurrentOu());
        } catch (Exception e) {
            brand = "-1";// 系统异常
            log.error("", e);
        }
        result.put("brand", brand);
        request.put(JSON, result);
        return JSON;
    }


    /**
     * 保存库位类型绑定中间表
     */
    public String savePdaLocationTypeBinding() throws JSONException {
        JSONObject result = new JSONObject();
        String brand = null;
        try {
            brand = baseinfoManager.saveLocationTypeBinding(pdaLocationTypeCommand, userDetails.getUser(), userDetails.getCurrentOu());
        } catch (Exception e) {
            // e.printStackTrace();
            if (log.isErrorEnabled()) {
                log.error("savePdaLocationTypeBinding Exception:", e);
            };
            brand = "-1";// 系统异常
            log.error("", e);
        }
        result.put("brand", brand);
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 导入保存库位类型绑定中间表
     * 
     * @return
     */
    public String savePdaLocationTypeBindingImport() {
        try {
            baseinfoManager.saveLocationTypeBindingImport(file, userDetails.getCurrentOu());
            request.put("msg", SUCCESS);
            // System.out.println(rs.getMessage());
        } catch (BusinessException be) {
            request.put("msg", be.getMessage());
        } catch (Exception e) {
            log.error("", e);
        }
        return SUCCESS;
    }


    /**
     * 导入保存库位类型绑定中间表
     * 
     * @return
     */
    public String savePdaSkuLocTypeCapeBindingImport() {
        try {
            baseinfoManager.savePdaSkuLocTypeCapeBindingImport(file, userDetails.getCurrentOu());
            request.put("msg", SUCCESS);
        } catch (BusinessException be) {
            request.put("msg", be.getMessage());
        } catch (Exception e) {
            log.error("", e);
        }
        return SUCCESS;
    }


    /**
     * 导入QS商品绑定中间表
     * 
     * @return
     */
    public String saveQsSkuBindingImport() {
        try {
            baseinfoManager.saveQsSkuBindingImport(file, userDetails.getCurrentOu(), userDetails.getUser());
            request.put("msg", SUCCESS);
        } catch (BusinessException be) {
            request.put("msg", be.getMessage());
        } catch (Exception e) {
            log.error("", e);
        }
        return SUCCESS;
    }

    /**
     * 导入商品产地信息
     * 
     * @return
     */
    public String saveSkuCountryOfOriginImport() {
        try {
            baseinfoManager.saveSkuCountryOfOriginImport(file, userDetails.getCurrentOu(), userDetails.getUser());
            request.put("msg", SUCCESS);
        } catch (BusinessException be) {
            request.put("msg", be.getMessage());
        } catch (Exception e) {
            log.error("", e);
        }
        return SUCCESS;
    }


    /**
     * 分页查询库位类型
     * 
     * @return
     * @throws JSONException
     */
    public String queryLocationType() {
        setTableConfig();
        Pagination<PdaLocationTypeCommand> list = baseinfoManager.getPdaLocationType(tableConfig.getStart(), tableConfig.getPageSize(), userDetails.getCurrentOu().getId(), null, tableConfig.getSorts());
        request.put(JSON, toJson(list));
        return JSON;
    }

    /**
     * 查询库位类型LIST
     * 
     * @return
     * @throws JSONException
     */
    public String queryLocationTypeList() throws JSONException {
        List<PdaLocationType> list = baseinfoManager.getPdaLocationTypeList(userDetails.getCurrentOu());
        request.put(JSON, JsonUtil.collection2json(list));
        return JSON;
    }

    /**
     * 查询库位类型（name,code,ouId）
     * 
     * @return
     * @throws JSONException
     */
    public String queryLocationTypeByNameCodeOuId() throws JSONException {
        JSONObject result = new JSONObject();
        PdaLocationType type = null;
        try {
            type = baseinfoManager.queryLocationTypeByNameCodeOuId(pdaLocationType, userDetails.getCurrentOu());
        } catch (Exception e) {
            // e.printStackTrace();
            if (log.isErrorEnabled()) {
                log.error("JSONObject Exception:", e);
            };
        }
        result.put("type", JsonUtil.obj2json(type));
        request.put(JSON, result);
        return JSON;
    }


    /*
     * 修改库位类型
     */
    public String updatePdaLocationType() throws JSONException {
        JSONObject result = new JSONObject();
        String brand = null;
        try {
            brand = baseinfoManager.updateLocationType(pdaLocationType, userDetails.getUser(), userDetails.getCurrentOu());
        } catch (Exception e) {
            // e.printStackTrace();
            if (log.isErrorEnabled()) {
                log.error("updatePdaLocationType Exception:", e);
            };
            brand = "-1";// 系统异常
            log.error("", e);
        }
        result.put("brand", brand);
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 批量删除
     * 
     * @return
     * @throws JSONException
     */
    public String delPdaLocationType() throws JSONException {
        JSONObject result = new JSONObject();
        List<String> list = Arrays.asList(ids.split(","));
        String brand = null;
        try {
            brand = baseinfoManager.delLocationType(list, userDetails.getUser());
        } catch (Exception e) {
            // e.printStackTrace();
            if (log.isErrorEnabled()) {
                log.error("delPdaLocationType Exception:", e);
            };
            brand = "-1";// 系统异常
        }
        result.put("brand", brand);
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 批量删除库位类型绑定
     * 
     * @return
     * @throws JSONException
     */
    public String delPdaLocationTypeBinding() throws JSONException {
        JSONObject result = new JSONObject();
        List<String> list = Arrays.asList(ids.split(","));
        String brand = null;
        try {
            brand = baseinfoManager.delLocationTypeBinding(list, userDetails.getUser());
        } catch (Exception e) {
            // e.printStackTrace();
            if (log.isErrorEnabled()) {
                log.error("delPdaLocationTypeBinding Exception:", e);
            };
            brand = "-1";// 系统异常
        }
        result.put("brand", brand);
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 删除库位类型容量
     * 
     * @return
     * @throws JSONException
     */
    public String delPdaSkuLocTypeCapBinding() throws JSONException {
        JSONObject result = new JSONObject();
        List<String> list = Arrays.asList(ids.split(","));
        try {
            baseinfoManager.delSkuLocTypeCap(list);
            result.put("flag", SUCCESS);
        } catch (Exception e) {
            // e.printStackTrace();
            if (log.isErrorEnabled()) {
                log.error("delPdaSkuLocTypeCapBinding Exception:", e);
            };
            result.put("flag", ERROR);
        }
        request.put(JSON, result);
        return JSON;
    }

    public String warehouseSetCustomer() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            Long customerId = customer.getId();
            baseinfoManager.warehouseSetCustomer(userDetails.getCurrentOu().getId(), customerId);
            result.put("result", SUCCESS);
        } catch (Exception e) {
            result.put("result", ERROR);
            log.error("", e);
        }
        request.put(JSON, result);
        return JSON;
    }

    public String skuWeightCount() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            result.put("result", baseinfoManager.skuWeightCount(userDetails.getCurrentOu().getId()));
        } catch (Exception e) {
            result.put("result", ERROR);
            log.error("", e);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 根据登录用户查找相应组织(仓库)信息
     * 
     * @return
     */
    @Override
    public String execute() {
        List<ChooseOption> opModeList;
        List<ChooseOption> manageModeList;
        ou = getCurrentOu();
        // 根据组织id查找 相应的仓库附加信息
        warehouse = baseinfoManager.findWarehouseByOuId(ou.getId());
        customer = baseinfoManager.getByWhouid(userDetails.getCurrentOu().getId());
        if (customer == null) {
            customerList = baseinfoManager.findAllCustomer();
        }
        opModeList = chooseOptionManager.findOptionListByCategoryCode(Constants.CHOOSEOPTION_CATEGORY_CODE_WAREHOUSE_WHOPMODE);
        manageModeList = chooseOptionManager.findOptionListByCategoryCode(Constants.CHOOSEOPTION_CATEGORY_CODE_WAREHOUSE_WHMANAGEMODE);
        List<District> provinces = baseinfoManager.findAllProvince();
        List<District> cities;
        List<District> districts;
        if (warehouse == null) {
            cities = new ArrayList<District>();
            districts = new ArrayList<District>();
        } else {
            cities = baseinfoManager.findCity(warehouse.getProvince());
            districts = baseinfoManager.findDistrictsByCity(warehouse.getProvince(), warehouse.getCity());
        }
        if (cities == null) {
            cities = new ArrayList<District>();
        }
        if (districts == null) {
            districts = new ArrayList<District>();
        }
        request.put("opModeList", opModeList);
        request.put("manageModeList", manageModeList);
        request.put("provinces", provinces);
        request.put("cities", cities);
        request.put("districts", districts);
        return SUCCESS;
    }

    /**
     * 编辑修改登录用户的组织(仓库)信息
     * 
     * @return
     */
    public String updateWarehouseInfo() {
        ou.setId(this.getCurrentOu().getId());
        // baseinfoManager.updateWarehouse(warehouse, ou, wss, transIds, apc);
        wareHouseManagerProxy.updateWarehouse(warehouse, ou, wss, transIds, apc);
        return JSON;
    }

    public String findWarehouseBaseInfo() {
        Warehouse warehouse = baseinfoManager.findWarehouseByOuId(this.userDetails.getCurrentOu().getId());
        if (warehouse != null) {
            JSONObject result = new JSONObject();
            try {
                result.put("result", SUCCESS);
                result.put("warehouse", JsonUtil.obj2json(warehouse));
            } catch (JSONException e) {
                log.error("", e);
            }
            request.put(JSON, result);
        }
        return JSON;
    }

    /**
     * 获取系统仓库可配状态列表 bin.hu
     * 
     * @return
     */
    public String getWarehouseStatus() {
        setTableConfig();
        List<WhAddStatusSourceCommand> wsscList = baseinfoManager.findWhStatusSourceListSql(1);
        request.put(JSON, toJson(wsscList));
        return JSON;
    }

    /**
     * 根据仓库ID找到对应所有的仓库状态 bin.hu
     * 
     * @return
     */
    public String getBiWarehouseStatus() {
        JSONObject result = new JSONObject();
        try {
            String statusValue = baseinfoManager.getBiWarehouseStatus(this.userDetails.getCurrentOu().getId());
            result.put("msg", statusValue);
        } catch (JSONException e) {
            log.error("", e);
        }
        request.put(JSON, result);
        return JSON;
    }

    public String findAllTransRef() {
        ou = getCurrentOu();
        List<Transportator> transList = baseinfoManager.findAllTransRef(ou);
        request.put(JSON, JsonUtil.collection2json(transList));
        return JSON;
    }

    public String getAotoPlConfig() {
        AutoPlConfigCommand apcc = baseinfoManager.getAotoPlConfig(this.userDetails.getCurrentOu().getId());
        if (apcc != null) {
            JSONObject result = new JSONObject();
            try {
                result.put("result", SUCCESS);
                result.put("msg", JsonUtil.obj2json(apcc));
            } catch (JSONException e) {
                log.error("", e);
            }
            request.put(JSON, result);
        }
        return JSON;
    }

    /**
     * 获取所有配货规则信息
     */
    public String findAutoPickingListRoleList() {
        setTableConfig();
        request.put(JSON, toJson(baseinfoManager.findAutoPickingListRoleList()));
        return JSON;
    }

    public String importCoverageArea() {
        String msg = SUCCESS;
        request.put("msg", msg);
        request.put("result", SUCCESS);
        if (file == null) {
            msg = "The upload file can not be null...";
            log.error(msg);
            request.put("msg", msg);
        } else {
            try {
                ReadStatus rs = baseinfoManager.importCoverageArea(file, userDetails.getCurrentOu().getId(), userDetails.getUser().getId());
                if (rs != null) {
                    if (rs.getExceptions().size() > 0) request.put("msg", rs.getExceptions().get(0).getMessage());
                }
                if (rs == null) {
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                } else if (rs.getStatus() > ReadStatus.STATUS_SUCCESS) {
                    request.put("result", ERROR);
                    request.put("msg", "");
                    List<String> list = ExcelKit.getInstance().getReadStatusMessages(rs, this.getLocale());
                    request.put("msg", list);
                } else if (rs.getStatus() < ReadStatus.STATUS_SUCCESS) {
                    request.put("result", ERROR);
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
                request.put("result", ERROR);
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
                request.put("result", ERROR);
                request.put("msg", ERROR);
                log.error("", e);
                log.error(e.getMessage());
                request.put("msg", e.getMessage());
            }
        }
        return SUCCESS;
    }

    public String coverImportCoverageArea() {
        String msg = SUCCESS;
        request.put("msg", msg);
        request.put("result", SUCCESS);
        if (file == null) {
            msg = "The upload file can not be null...";
            log.error(msg);
            request.put("msg", msg);
        } else {
            try {
                ReadStatus rs = baseinfoManager.coverImportCoverageArea(file, userDetails.getCurrentOu().getId(), userDetails.getUser().getId());
                if (rs != null) {
                    if (rs.getExceptions().size() > 0) request.put("msg", rs.getExceptions().get(0).getMessage());
                }
                if (rs == null) {
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                } else if (rs.getStatus() > ReadStatus.STATUS_SUCCESS) {
                    request.put("result", ERROR);
                    request.put("msg", "");
                    List<String> list = ExcelKit.getInstance().getReadStatusMessages(rs, this.getLocale());
                    request.put("msg", list);
                } else if (rs.getStatus() < ReadStatus.STATUS_SUCCESS) {
                    request.put("result", ERROR);
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
                request.put("result", ERROR);
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
                request.put("result", ERROR);
                request.put("msg", ERROR);
                log.error("", e);
                log.error(e.getMessage());
                request.put("msg", e.getMessage());
            }
        }
        return SUCCESS;
    }

    public String findCoverageAreaByOuId() {
        setTableConfig();
        request.put(JSON, toJson(baseinfoManager.findCoverageAreaByOuId(tableConfig.getStart(), tableConfig.getPageSize(), userDetails.getCurrentOu().getId(), tableConfig.getSorts())));
        return JSON;
    }

    /**
     * 查询仓库SF次晨达配置业务
     * 
     * @return
     */
    public String findSfNextMorningConfigByOuId() {
        setTableConfig();
        request.put(JSON, toJson(baseinfoManager.findSfNextMorningConfigByOuId(tableConfig.getStart(), tableConfig.getPageSize(), userDetails.getCurrentOu().getId(), tableConfig.getSorts())));
        return JSON;
    }

    /**
     * 查询仓库NIKE当日达配置业务
     * 
     * @return
     */
    public String findNIKETodaySendConfigByOuId() {
        setTableConfig();
        request.put(JSON, toJson(baseinfoManager.findNIKETodaySendConfigByOuId(tableConfig.getStart(), tableConfig.getPageSize(), userDetails.getCurrentOu().getId(), tableConfig.getSorts())));
        return JSON;
    }


    public String findAdPackageByOuIdPage() {
        setTableConfig();
        request.put(JSON, toJson(baseinfoManager.findAdPackageByOuIdPage(tableConfig.getStart(), tableConfig.getPageSize(), userDetails.getCurrentOu().getId(), tableConfig.getSorts())));
        return JSON;
    }
    /**
     * 删除覆盖区域
     * 
     * @return
     * @throws JSONException String
     * @throws
     */
    public String deleteCoverageArea() throws JSONException {
        JSONObject result = new JSONObject();
        List<String> errorMsg = new ArrayList<String>();
        if (areaId == null) {
            result.put("msg", "参数异常");
        }
        try {
            baseinfoManager.deleteCoverageArea(areaId, userDetails.getCurrentOu().getId());
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            BusinessException bex = e;
            do {
                String msg = this.getMessage(ErrorCode.BUSINESS_EXCEPTION + bex.getErrorCode(), bex.getArgs());
                log.error(msg);
                errorMsg.add(msg);
                bex = bex.getLinkedException();
            } while (bex != null);
            result.put("result", ERROR);
            result.put("msg", JsonUtil.collection2json(errorMsg));
        } catch (Exception e) {
            log.error("", e);
            result.put("result", ERROR);
            result.put("msg", e.getCause() + " " + e.getMessage());
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 获取渠道残次原因信息
     * 
     * @return
     */
    public String findWarehouseImperfectlList() {
        setTableConfig();
        Pagination<BiChannelImperfectCommand> biChannelList = baseinfoManager.findWarehouseImperfectlList(tableConfig.getStart(), tableConfig.getPageSize(), userDetails.getCurrentOu().getId(), new Sort[] {new Sort("ct.id asc")});
        request.put(JSON, toJson(biChannelList));
        return JSON;
    }

    /**
     * 获取渠道残次原因信息
     * 
     * @return
     */
    public String findWarehouseImperfectl() {
        List<BiChannelImperfectCommand> biChannelImperfectCommands = baseinfoManager.findWarehouseImperfectl(userDetails.getCurrentOu().getId());
        request.put(JSON, JsonUtil.collection2json(biChannelImperfectCommands));
        return JSON;
    }

    /**
     * 获取渠道残次原因信息
     * 
     * @return
     */
    public String findWarehouseImperfectlLineList() {
        setTableConfig();
        Pagination<BiChannelImperfectLineCommand> biChannelList = baseinfoManager.findWarehouseImperfectlLineList(tableConfig.getStart(), tableConfig.getPageSize(), imperfectId, userDetails.getCurrentOu().getId(), new Sort[] {new Sort("ct.id asc")});
        request.put(JSON, toJson(biChannelList));
        return JSON;
    }

    public String importWarehouseImperfect() {
        String msg = SUCCESS;
        request.put("msg", msg);
        request.put("result", SUCCESS);
        if (fileType == null) {
            msg = "The upload file can not be null...";
            log.error(msg);
            request.put("msg", msg);
        } else {
            try {
                ReadStatus rs = baseinfoManager.addSkuImperfect(fileType, this.getCurrentOu().getId());
                if (rs != null) {
                    if (rs.getExceptions().size() > 0) {
                        request.put("msg", rs.getExceptions().get(0).getMessage());
                        request.put("ouId", this.getCurrentOu().getId());
                    }
                }
                if (rs == null) {
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                } else if (rs.getStatus() > ReadStatus.STATUS_SUCCESS) {
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

    /**
     * 导入ad包裹
     */
    public String importAdPackage() {
        String msg = SUCCESS;
        request.put("msg", msg);
        request.put("flag", "adPackage");
        request.put("result", SUCCESS);
        if (fileAd == null) {
            msg = "The upload file can not be null...";
            log.error(msg);
            request.put("msg", msg);
        } else {
            try {
                ReadStatus rs = baseinfoManager.importAdPackage(fileAd, userDetails.getCurrentOu().getId(), userDetails.getUsername());
                if (rs != null) {
                    if (rs.getExceptions().size() > 0) request.put("msg", rs.getExceptions().get(0).getMessage());
                }
                if (rs == null) {
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                } else if (rs.getStatus() > ReadStatus.STATUS_SUCCESS) {
                    request.put("result", ERROR);
                    request.put("msg", "");
                    List<String> list = ExcelKit.getInstance().getReadStatusMessages(rs, this.getLocale());
                    request.put("msg", list);
                } else if (rs.getStatus() < ReadStatus.STATUS_SUCCESS) {
                    request.put("result", ERROR);
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
                request.put("result", ERROR);
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
                request.put("result", ERROR);
                request.put("msg", ERROR);
                log.error("", e);
                log.error(e.getMessage());
                request.put("msg", e.getMessage());
            }
        }
        return SUCCESS;
    }

    /**
     * 导出ad包裹
     */
    public void exportAdPackage() throws IOException {
        response.setContentType("application/octet-stream;charset=ISO8859-1");
        String fileName = "AD异常工单类型配置";
        fileName = new String(fileName.getBytes("GBK"), "ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + Constants.EXCEL_XLS);
        ServletOutputStream os = response.getOutputStream();
        try {
            excelExportManager.exportAdPackage(userDetails.getCurrentOu().getId(), os);
            os.flush();
            os.close();
        } catch (Exception e) {
            log.error("", e);
        }
    }


    /**
     * 保存库位类型容器配置
     */
    public String savePdaSkuLocTypeCapBinding() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            Boolean msg = baseinfoManager.savePdaSkuLocTypeCapBinding(pdaSkuLocTypeCapCommand, userDetails.getCurrentOu());
            if (msg) {
                result.put("flag", SUCCESS);
            } else {
                result.put("flag", NONE);
            }

        } catch (Exception e) {
            log.error("savePdaSkuLocTypeCapBinding", e);
            result.put("flag", ERROR);
        }
        request.put(JSON, result);
        return JSON;
    }



    public OperationUnit getOu() {
        return ou;
    }

    public void setOu(OperationUnit ou) {
        this.ou = ou;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public WhAddStatusSource getWss() {
        return wss;
    }

    public void setWss(WhAddStatusSource wss) {
        this.wss = wss;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
    }

    public String getTransIds() {
        return transIds;
    }

    public void setTransIds(String transIds) {
        this.transIds = transIds;
    }

    public AutoPlConfigCommand getApc() {
        return apc;
    }

    public void setApc(AutoPlConfigCommand apc) {
        this.apc = apc;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public Long getOuId() {
        return ouId;
    }

    public void setOuId(Long ouId) {
        this.ouId = ouId;
    }

    public Long getImperfectId() {
        return imperfectId;
    }

    public void setImperfectId(Long imperfectId) {
        this.imperfectId = imperfectId;
    }

    public File getFileType() {
        return fileType;
    }

    public void setFileType(File fileType) {
        this.fileType = fileType;
    }

    public File getFileWhy() {
        return fileWhy;
    }

    public void setFileWhy(File fileWhy) {
        this.fileWhy = fileWhy;
    }

    public PdaLocationType getPdaLocationType() {
        return pdaLocationType;
    }


    public void setPdaLocationType(PdaLocationType pdaLocationType) {
        this.pdaLocationType = pdaLocationType;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public PdaLocationTypeCommand getPdaLocationTypeCommand() {
        return pdaLocationTypeCommand;
    }

    public void setPdaLocationTypeCommand(PdaLocationTypeCommand pdaLocationTypeCommand) {
        this.pdaLocationTypeCommand = pdaLocationTypeCommand;
    }

    public PdaSkuLocTypeCapCommand getPdaSkuLocTypeCapCommand() {
        return pdaSkuLocTypeCapCommand;
    }

    public void setPdaSkuLocTypeCapCommand(PdaSkuLocTypeCapCommand pdaSkuLocTypeCapCommand) {
        this.pdaSkuLocTypeCapCommand = pdaSkuLocTypeCapCommand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public File getFileAd() {
        return fileAd;
    }

    public void setFileAd(File fileAd) {
        this.fileAd = fileAd;
    }



}
