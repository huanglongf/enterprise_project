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

import loxia.dao.Pagination;
import loxia.support.excel.ExcelKit;
import loxia.support.excel.ReadStatus;
import loxia.support.json.JSONArray;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.util.FormatUtil;
import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.DropDownListManager;
import com.jumbo.wms.manager.baseinfo.BaseinfoManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerCancel;
import com.jumbo.wms.manager.warehouse.WareHouseManagerQuery;
import com.jumbo.wms.manager.warehouse.excel.ExcelReadManager;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.command.SkuWarehouseRefCommand;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;

/**
 * 预定义入库
 * 
 * @author dly
 * 
 */
public class WarehousePredefinedAction extends BaseJQGridProfileAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = 987895313882544548L;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private WareHouseManagerQuery wareHouseManagerQuery;
    @Autowired
    private WareHouseManagerCancel wareHouseManagerCancel;
    @Autowired
    private ExcelReadManager excelReadManager;
    @Autowired
    private DropDownListManager dropDownManager;
    @Autowired
    private BaseinfoManager baseinfoManager;

    private List<BiChannel> shops;
    private List<InventoryStatus> invStatusList;
    private StockTransApplication sta;
    private String barcode;
    private String jmcode;
    private String keyProperties;
    private Long invStatus;
    private String owner;
    private Long transactionType;
    private File file;
    private String importMemo;
    private List<StaLineCommand> staLineCmd;
    private Boolean isVMI = false;
    private Boolean sample = false;

    private String startTime;
    private String endTime;
    private String arriveStartTime;
    private String arriveEndTime;
    private Integer types;
    private Integer status;
    private Integer intStaType;
    private Long ouId;
    private Integer intStaStatus;

    private Long brandId;
    private String source;
    private String sourcewh;
    private Long channelId;
    /**
     * 批次上架模式
     */
    private List<ChooseOption> inMode;


    public String operationPredefined() {
        shops = baseinfoManager.queryShopListMultiCheck(userDetails.getCurrentOu().getId());
        invStatusList = wareHouseManager.findInvStatusByOuid(userDetails.getCurrentOu().getParentUnit().getParentUnit().getId());
        return SUCCESS;
    }

    /**
     * 外包仓品牌仓库关联
     * 
     * @return
     */
    public String createOutsourcingWhAndBrandAssociation() {

        return SUCCESS;
    }

    /**
     * 获取外包仓品牌仓库数据
     * 
     * @return
     */
    public String findSkuWarehouseRefListAll() throws JSONException {
        setTableConfig();
        Pagination<SkuWarehouseRefCommand> list = wareHouseManager.findSkuWarehouseRefListAll(tableConfig.getStart(), tableConfig.getPageSize(), brandId, source, sourcewh, channelId, tableConfig.getSorts());
        request.put(JSON, toJson(list));
        return JSON;
    }

    /**
     * 
     * 删除外包仓关联
     * 
     * @throws JSONException
     */
    public String deleteSkuWarehouseRef() throws JSONException {
        JSONObject result = new JSONObject();
        try {
        	wareHouseManagerCancel.deleteSkuWarehouseRef(brandId, source, sourcewh, channelId);
            result.put("rs", "success");
        } catch (Exception e) {
            result.put("rs", "error");
        }
        request.put(JSON, result);
        return JSON;

    }

    /**
     * 获取品牌下拉框列表数据
     * 
     * @return
     */
    public String findByBrandName() {
        List<SkuWarehouseRefCommand> list = wareHouseManager.findByBrandName();
        request.put(JSON, JsonUtil.collection2json(list));
        return JSON;
    }

    /**
     * 获取店铺数据下拉框
     * 
     * @return
     */
    public String findByChannelName() {
        List<SkuWarehouseRefCommand> list = wareHouseManager.findByChannelName();
        request.put(JSON, JsonUtil.collection2json(list));
        return JSON;
    }


    public String findSkuWarehouseRefList() throws JSONException {
        JSONObject result = new JSONObject();
        SkuWarehouseRefCommand cmd = null;
        cmd = wareHouseManager.findSkuWarehouseRefList(sourcewh);
        if (cmd != null) {
            result.put("courcewh", JsonUtil.obj2json(cmd));
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 加载品牌下拉框列表数据
     * 
     * @return
     */
    public String findBrandNameAll() {
        JSONObject json = new JSONObject();
        JSONArray ja = new JSONArray();
        try {
            List<SkuWarehouseRefCommand> list = wareHouseManager.findBrandNameAll();

            for (int i = 0; i < list.size(); i++) {
                SkuWarehouseRefCommand b = list.get(i);
                JSONObject jo = new JSONObject();
                jo.put("name", b.getBrandName());
                jo.put("brandId", b.getBrandId());
                ja.put(jo);
            }

            json.put("brandlist", ja);
        } catch (Exception e) {
            log.debug("WarehousePredefinedAction.findBrandNameAll.exception");
        }
        request.put(JSON, json);
        return JSON;
    }

    /**
     * 加载品牌下拉框列表数据
     * 
     * @return
     */
    public String findChannelNameAll() {
        JSONObject json = new JSONObject();
        JSONArray ja = new JSONArray();
        try {
            List<BiChannel> list = wareHouseManager.findChannelNameAll();
            for (int i = 0; i < list.size(); i++) {
                BiChannel b = list.get(i);
                JSONObject jo = new JSONObject();
                jo.put("name", b.getName());
                jo.put("id", b.getId());
                ja.put(jo);
            }

            json.put("channellist", ja);
        } catch (Exception e) {
            log.debug("WarehousePredefinedAction.findChannelNameAll.exception");
        }
        request.put(JSON, json);
        return JSON;
    }

    /**
     * 
     * 插入外包仓关联
     * 
     * @throws JSONException
     */
    public String insertSkuWarehouseRef() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            wareHouseManager.insertSkuWarehouseRef(brandId, source, sourcewh, channelId);
            result.put("rs", "success");
        } catch (Exception e) {
            result.put("rs", "error");
        }
        request.put(JSON, result);
        return JSON;

    }

    // 根据条件查询商品
    public String findSkuInfoByParameter() throws JSONException {
        JSONObject obj = new JSONObject();
        try {
            Sku sku = wareHouseManagerQuery.findSkuByParameter(barcode, jmcode, keyProperties, userDetails.getCurrentOu().getId());
            obj.put("result", SUCCESS);
            obj.put("sku", JsonUtil.obj2json(sku));
        } catch (BusinessException e) {
            obj.put("result", ERROR);
            obj.put("message", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, obj);
        return JSON;
    }

    // 查询条件查询商品数量
    public String findSkuCountByParameter() throws JSONException {
        JSONObject obj = new JSONObject();
        try {
            int count = wareHouseManager.findCountSkuByParameter(barcode, jmcode, keyProperties, userDetails.getCurrentOu().getId());
            obj.put("result", SUCCESS);
            obj.put("count", count);
        } catch (BusinessException e) {
            obj.put("result", ERROR);
            obj.put("message", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, obj);
        return JSON;
    }

    /**
     * 创建预定义作业入库
     * 
     * @return
     * @throws JSONException
     */
    public String createPredefinedSta() throws JSONException {
        JSONObject result = new JSONObject();
        String errorMsg = "";
        try {
            mergerStvLine();
            wareHouseManager.createPredefinedIn(false, transactionType, userDetails.getUser(), userDetails.getCurrentOu(), sta.getOwner(), sta.getMemo(), staLineCmd);
            result.put("msg", SUCCESS);
        } catch (BusinessException e) {
            errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs());
            BusinessException current = e;
            while (current.getLinkedException() != null) {
                current = current.getLinkedException();
                errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + current.getErrorCode(), current.getArgs());
            }
            result.put("msg", errorMsg);
        } catch (Exception e) {
            log.error("", e);
            result.put("msg", e.getCause() + " " + e.getMessage());
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 导入
     * 
     * @return
     */
    public String prodefinedStaCreateImport() {
        List<String> errorMsg = new ArrayList<String>();
        if (file == null) {
            return ERROR;
        } else {
            try {
                ReadStatus rs = excelReadManager.predefinedStaImport(file, importMemo, owner, invStatus, transactionType, userDetails.getCurrentOu().getId(), userDetails.getUser().getId());
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
                request.put("result", ERROR);
                String msg = this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs());
                errorMsg.add(msg);
                BusinessException current = e;
                while (current.getLinkedException() != null) {
                    current = current.getLinkedException();
                    String cmsg = this.getMessage(ErrorCode.BUSINESS_EXCEPTION + current.getErrorCode(), current.getArgs());
                    log.debug(cmsg);
                    errorMsg.add(cmsg);
                }
            }
            request.put("message", JsonUtil.collection2json(errorMsg));
        }
        return SUCCESS;
    }

    /***
     * 查询预定义作业单 未分页操作
     * 
     * @return
     */
    @Deprecated
    public String findPredefinedSta() {
        setTableConfig();
        request.put(JSON, toJson(wareHouseManager.findPredefinedSta(userDetails.getCurrentOu(), tableConfig.getSorts())));
        return JSON;
    }

    /**
     * 查询预定义作业单 分页操作
     * 
     * @return
     */
    public String findPredefinedStaByPagination() {
        setTableConfig();
        if (types == null) types = 0;
        if (status == null) status = 0;
        request.put(JSON, toJson(wareHouseManager.findPredefinedStaByPagination(sta, userDetails.getCurrentOu(), FormatUtil.getDate(startTime), FormatUtil.getDate(endTime), FormatUtil.getDate(arriveStartTime), FormatUtil.getDate(arriveEndTime), isVMI,
                sample, types, status, tableConfig.getStart(), tableConfig.getPageSize(), tableConfig.getSorts())));
        return JSON;
    }

    /**
     * 查询预定义作业单 分页操作
     * 
     * @return
     */
    public String findPredeCancelStaByPagination() {
        setTableConfig();
        if (types == null) types = 0;
        if (status == null) status = 0;
        /* if(intStaType==null) intStaType=0; */
        request.put(JSON, toJson(wareHouseManager.findPredeCancelStaByPagination(sta, userDetails.getCurrentOu(), FormatUtil.getDate(startTime), FormatUtil.getDate(endTime), FormatUtil.getDate(arriveStartTime), FormatUtil.getDate(arriveEndTime), isVMI,
                sample, intStaStatus, intStaType, types, status, tableConfig.getStart(), tableConfig.getPageSize(), tableConfig.getSorts())));
        return JSON;
    }

    /**
     * 查询预定义作业单 取消 --修改status
     * 
     * @return
     * @throws JSONException
     */
    public String modifyroleStatus() throws JSONException {
        JSONObject result = new JSONObject();
        if (sta.getId() != null) {
            wareHouseManager.modifyroleStatus(sta.getId(), userDetails.getUser().getId());
            result.put("result", SUCCESS);
        } else {
            result.put("result", ERROR);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 查询预定义作业单 分页操作
     * 
     * @return
     */
    public String findPredefinedStaByPaginationRoot() {
        setTableConfig();
        if (types == null) types = 0;
        if (status == null) status = 0;
        request.put(JSON, toJson(wareHouseManager.findPredefinedStaByPaginationRoot(sta, userDetails.getCurrentOu(), FormatUtil.getDate(startTime), FormatUtil.getDate(endTime), FormatUtil.getDate(arriveStartTime), FormatUtil.getDate(arriveEndTime),
                isVMI, sample, types, status, tableConfig.getStart(), tableConfig.getPageSize(), ouId, tableConfig.getSorts())));
        return JSON;
    }

    /**
     * 根据 oms 接口 取消作业单
     * 
     * @return
     */
    public String cancelStaEasy() {
        JSONObject result = new JSONObject();
        try {
            if (sta != null) {
            	wareHouseManagerCancel.cancelStaEasy(sta.getId(), sta.getMemo());
            }
            result.put("result", "bingo");
        } catch (JSONException e) {
            log.error("", e);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 查询状态
     * 
     * @return
     */
    public String findStaStatusBystaid() {
        JSONObject result = new JSONObject();
        JSONArray ja = new JSONArray();
        try {
            Map<Integer, StockTransApplicationStatus> map = wareHouseManager.selectStatusByStaid();
            for (Object obj : map.keySet()) {
                JSONObject jo = new JSONObject();
                jo.put("name", wareHouseManager.findAllOptionListByOptionKey(obj.toString(), "whSTAStatus"));
                jo.put("id", Integer.parseInt(obj.toString()));
                ja.put(jo);
            }

            result.put("warelist", ja);
        } catch (JSONException e) {
            log.error("", e);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 查询类型
     * 
     * @return
     */
    public String findTypesByStaid() {
        JSONObject result = new JSONObject();
        JSONArray ja = new JSONArray();
        try {
            Map<Integer, StockTransApplicationType> map = wareHouseManager.selectTypesByStaid();
            for (Object obj : map.keySet()) {
                JSONObject jo = new JSONObject();
                jo.put("name", wareHouseManager.findAllOptionListByOptionKey(obj.toString(), "whSTAType"));
                jo.put("id", Integer.parseInt(obj.toString()));
                ja.put(jo);
            }

            result.put("warelist", ja);
        } catch (JSONException e) {
            log.error("", e);
        }
        request.put(JSON, result);
        return JSON;
    }

    /*******
     * 
     if (sta != null) { setTableConfig(); if
     * (StockTransApplicationType.TRANSIT_CROSS.equals(sta.getType()) ||
     * StockTransApplicationType.VMI_FLITTING.equals(sta.getType())) {
     * 
     * request.put(JSON, toJson(wareHouseManager.findTranCossStaNotFinishedListByType( sta,
     * userDetails.getCurrentOu(), startTime,endTime,arriveStartTime,arriveEndTime,
     * tableConfig.getStart(), tableConfig.getPageSize(), tableConfig.getSorts() )));
     * //request.put(JSON,
     * toJson(wareHouseManager.findTranCossStaNotFinishedListByType(sta.getType(),
     * userDetails.getCurrentOu(), tableConfig.getSorts()))); } else { request.put(JSON,
     * toJson(wareHouseManager.findStaNotFinishedListByType( sta, userDetails.getCurrentOu(),
     * startTime,endTime,arriveStartTime,arriveEndTime, tableConfig.getStart(),
     * tableConfig.getPageSize(), tableConfig.getSorts() ))); } } return JSON;
     * 
     * *
     ***/

    private void mergerStvLine() {
        List<StaLineCommand> list = new ArrayList<StaLineCommand>();
        for (StaLineCommand stal : this.getStaLineCmd()) {
            boolean hasSame = false;
            for (StaLineCommand tp : list) {
                if (stal.getSkuId().equals(tp.getSkuId()) && stal.getInvStatus().getId().equals(tp.getInvStatus().getId()) && ((stal.getSkuCode() == null && tp.getSkuCode() == null) || (stal.getSkuCode().equals(tp.getSkuCode())))) {
                    tp.setQuantity(tp.getQuantity() + stal.getQuantity());
                    tp.setSkuCode(tp.getSkuCode() == null ? stal.getSkuCode() : tp.getSkuCode());
                    hasSame = true;
                    break;
                }
            }
            if (!hasSame) {
                list.add(stal);
            }
        }
        setStaLineCmd(list);
    }


    public String operationPredefinedQuery() {
        inMode = dropDownManager.findStaInboundStoreModeChooseOptionList();
        return SUCCESS;
    }

    public String operationPredefinedCancel() {
        inMode = dropDownManager.findStaInboundStoreModeChooseOptionList();
        return SUCCESS;
    }

    public String vmiOperationQuery() {
        inMode = dropDownManager.findStaInboundStoreModeChooseOptionList();
        return SUCCESS;
    }

    /**
     * 导入模板批量收货 分页操作
     * 
     * @return
     */
    public String findBatchStaByPagination() {
        setTableConfig();
        if (types == null) types = 0;
        if (status == null) status = 0;
        request.put(JSON, toJson(wareHouseManager.findBatchStaByPagination(sta, userDetails.getCurrentOu(), FormatUtil.getDate(startTime), FormatUtil.getDate(endTime), FormatUtil.getDate(arriveStartTime), FormatUtil.getDate(arriveEndTime), types, status,
                tableConfig.getStart(), tableConfig.getPageSize(), tableConfig.getSorts())));
        return JSON;
    }

    /**
     * 预定义作业单 关闭/取消 --根据Id修改状态和完成时间
     * 
     * @return
     * @throws JSONException
     */
    public String updateTypeAndFinishTimeByid() throws JSONException {
        JSONObject result = new JSONObject();
        if (sta.getId() != null) {
            wareHouseManager.updateTypeAndFinishTimeByid(sta.getId(), userDetails.getUser().getId());
            result.put("result", SUCCESS);
        } else {
            result.put("result", ERROR);
        }
        request.put(JSON, result);
        return JSON;
    }

    public List<BiChannel> getShops() {
        return shops;
    }

    public void setShops(List<BiChannel> shops) {
        this.shops = shops;
    }

    public List<InventoryStatus> getInvStatusList() {
        return invStatusList;
    }

    public void setInvStatusList(List<InventoryStatus> invStatusList) {
        this.invStatusList = invStatusList;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getJmcode() {
        return jmcode;
    }

    public void setJmcode(String jmcode) {
        this.jmcode = jmcode;
    }

    public String getKeyProperties() {
        return keyProperties;
    }

    public void setKeyProperties(String keyProperties) {
        this.keyProperties = keyProperties;
    }

    public StockTransApplication getSta() {
        return sta;
    }

    public void setSta(StockTransApplication sta) {
        this.sta = sta;
    }

    public Long getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(Long transactionType) {
        this.transactionType = transactionType;
    }


    public List<StaLineCommand> getStaLineCmd() {
        return staLineCmd;
    }

    public void setStaLineCmd(List<StaLineCommand> staLineCmd) {
        this.staLineCmd = staLineCmd;
    }

    public List<ChooseOption> getInMode() {
        return inMode;
    }

    public void setInMode(List<ChooseOption> inMode) {
        this.inMode = inMode;
    }

    public File getFile() {
        return file;
    }

    public String getImportMemo() {
        return importMemo;
    }

    public void setImportMemo(String importMemo) {
        this.importMemo = importMemo;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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

    public Long getInvStatus() {
        return invStatus;
    }

    public void setInvStatus(Long invStatus) {
        this.invStatus = invStatus;
    }

    public Boolean getIsVMI() {
        return isVMI;
    }

    public Integer getTypes() {
        return types;
    }

    public void setTypes(Integer types) {
        this.types = types;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setIsVMI(Boolean isVMI) {
        this.isVMI = isVMI;
    }

    public Boolean getSample() {
        return sample;
    }

    public void setSample(Boolean sample) {
        this.sample = sample;
    }

    public Long getOuId() {
        return ouId;
    }

    public void setOuId(Long ouId) {
        this.ouId = ouId;
    }

    public Integer getIntStaType() {
        return intStaType;
    }

    public void setIntStaType(Integer intStaType) {
        this.intStaType = intStaType;
    }

    public Integer getIntStaStatus() {
        return intStaStatus;
    }

    public void setIntStaStatus(Integer intStaStatus) {
        this.intStaStatus = intStaStatus;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourcewh() {
        return sourcewh;
    }

    public void setSourcewh(String sourcewh) {
        this.sourcewh = sourcewh;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

}
