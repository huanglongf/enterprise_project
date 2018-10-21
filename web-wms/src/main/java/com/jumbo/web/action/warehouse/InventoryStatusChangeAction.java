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

import loxia.support.excel.ExcelKit;
import loxia.support.excel.ReadStatus;
import loxia.support.json.JSONArray;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.Constants;
import com.jumbo.util.FormatUtil;
import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.authorization.AuthorizationManager;
import com.jumbo.wms.manager.channel.ChannelManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerCancel;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExe;
import com.jumbo.wms.manager.warehouse.excel.ExcelReadManager;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.warehouse.InventoryCommand;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.StvLine;
import com.jumbo.wms.model.warehouse.StvLineCommand;
import com.jumbo.wms.model.warehouse.TransactionDirection;
import com.jumbo.wms.model.warehouse.WarehouseLocation;
import com.jumbo.wms.model.warehouse.WarehouseLocationCommand;

/**
 * 库存状态修改
 * 
 * @author sjk
 * 
 */
public class InventoryStatusChangeAction extends BaseJQGridProfileAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = 6032087481589662047L;

    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private WareHouseManagerExe wareHouseManagerExe;
    @Autowired
    private WareHouseManagerCancel wareHouseManagerCancel;
    @Autowired
    private ExcelReadManager excelReadManager;
    @Autowired
    private ChannelManager channelManager;
    @Autowired
    private AuthorizationManager authorizationManager;

    private StockTransApplicationCommand staCom;

    private File file;

    private String remork;

    private String skuCode;

    private Long skuId;

    private String locationCode;

    private Long statusId;

    private Long shopId;

    private List<BiChannel> shops;

    private Long qty;

    private String memo;

    private Long staId;

    private String term;

    private List<StvLine> stvLinelist;

    private List<StvLineCommand> stvlineCommandList;

    private String productionDate;

    private String expireDate;

    private Long inventoryStatusId;

    public String invStatusChangeEntry() {
        // shops = wareHouseManager.findCompanyShops();
        OperationUnit ou = authorizationManager.getOUByPrimaryKey(userDetails.getCurrentOu().getId());
        Long ouid = ou.getParentUnit().getParentUnit().getId();
        shops = channelManager.getAllWhRefChannelByCmpouId(ouid);
        return SUCCESS;
    }

    public String findExecuteInvStatusChangeSta() {
        setTableConfig();
        if (staCom != null) {
            staCom.setCreateTime(FormatUtil.getDate(staCom.getCreateTime1()));
            staCom.setFinishTime(FormatUtil.getDate(staCom.getFinishTime1()));
        }
        List<StockTransApplicationCommand> list = wareHouseManager.findExecuteInvStatusChangeSta(staCom, userDetails.getCurrentOu().getId(), tableConfig.getSorts());
        request.put(JSON, toJson(list));
        return JSON;
    }

    public String findImportExecuteInvStatusChangeSta() {
        setTableConfig();
        if (staCom != null) {
            staCom.setCreateTime(FormatUtil.getDate(staCom.getCreateTime1()));
            staCom.setFinishTime(FormatUtil.getDate(staCom.getFinishTime1()));
        }
        List<StockTransApplicationCommand> list = wareHouseManager.findExecuteMoveSta(staCom, StockTransApplicationType.INVENTORY_STATUS_CHANGE.getValue(), userDetails.getCurrentOu().getId(), tableConfig.getSorts());
        request.put(JSON, toJson(list));
        return JSON;
    }

    // 根据库位获取商品信息
    public String querySku() throws JSONException {
        setResultData(wareHouseManager.findByLocationCode(locationCode, shopId, userDetails.getCurrentOu().getId()));
        return JSON;
    }

    // 联想库位
    public String findAvailLocation() throws JSONException {
        List<WarehouseLocation> list = wareHouseManager.findLocationByLikeCode(term, userDetails.getCurrentOu().getId());
        JSONArray ja = new JSONArray();
        java.util.Iterator<WarehouseLocation> it = list.iterator();
        while (it.hasNext()) {
            WarehouseLocation w = it.next();
            JSONObject jo = new JSONObject();
            jo.put("id", w.getId());
            jo.put("label", w.getCode());
            jo.put("value", w.getCode());
            ja.put(jo);
        }
        request.put(JSON, ja);
        return JSON;
    }

    // 商品联
    public String findAvailSku() throws JSONException {
        List<Sku> list = wareHouseManager.findSkuByLikeCode(term, userDetails.getCurrentOu().getId());
        JSONArray ja = new JSONArray();
        java.util.Iterator<Sku> it = list.iterator();
        while (it.hasNext()) {
            Sku sku = it.next();
            JSONObject jo = new JSONObject();
            jo.put("id", sku.getId());
            jo.put("label", sku.getCode());
            jo.put("value", sku.getCode());
            ja.put(jo);
        }
        request.put(JSON, ja);
        return JSON;
    }

    // 获取库位信息
    public String queryLocationBylocationCode() throws JSONException {
        JSONObject result = new JSONObject();
        WarehouseLocationCommand wlc = wareHouseManager.findLocationByLocationCode(locationCode, userDetails.getCurrentOu().getId());
        if (wlc != null) {
            result.put("location", JsonUtil.obj2json(wlc));
            result.put("result", SUCCESS);
        } else {
            result.put("result", ERROR);
        }
        request.put(JSON, result);
        return JSON;
    }

    // 获取库内移动作业单信息
    public String queryStaById() throws JSONException {
        JSONObject result = new JSONObject();
        result.put("result", SUCCESS);
        result.put("sta", JsonUtil.obj2json(wareHouseManager.findStaByStaId(staId)));
        result.put("staLineList", JsonUtil.collection2json(wareHouseManager.findStvLineByStaId(staId)));
        request.put(JSON, result);
        return JSON;
    }

    // 取消本次库内移动
    public String cancelInvStatusChange() throws Exception {
        JSONObject result = new JSONObject();
        try {
        	wareHouseManagerCancel.cancelInvStatusChangeSta(staId, userDetails.getUser().getId());
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            log.error("", e);
            result.put("result", ERROR);
            result.put("message", getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, result);
        return JSON;
    }

    public String querySkuQty() throws JSONException {
        setResultData(wareHouseManager.findByLocationAdnSku(locationCode, skuCode, skuId, statusId, qty, userDetails.getCurrentOu().getId()));
        return JSON;
    }

    public String queryLocation() throws JSONException {
        setResultData(wareHouseManager.findBySkuCode(skuCode, shopId, userDetails.getCurrentOu().getId()));
        return JSON;
    }

    public String querySkuStatus() throws JSONException {
        setResultData(wareHouseManager.findByLocAndSku(locationCode, skuCode, shopId, skuId, userDetails.getCurrentOu().getId(), inventoryStatusId, productionDate, expireDate));
        return JSON;
    }

    private void setResultData(List<InventoryCommand> list) throws JSONException {
        JSONObject result = new JSONObject();
        result.put("size", list == null ? 0 : list.size());
        result.put("result", SUCCESS);
        result.put("list", JsonUtil.collection2json(list));
        request.put(JSON, result);
    }

    // 占用待转出
    public String createInvStatusChangeSta() throws JSONException {
        JSONObject result = new JSONObject();
        String errorMsg = "";
        try {
            StockTransApplication sta = wareHouseManager.createInvStatusChangeSta(false, memo, userDetails.getUser().getId(), userDetails.getCurrentOu().getId(), stvLinelist);
            result.put("result", SUCCESS);
            result.put("sta", JsonUtil.obj2json(sta));
        } catch (BusinessException e) {
            result.put("result", ERROR);
            errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs());
            BusinessException current = e;
            while (current.getLinkedException() != null) {
                current = current.getLinkedException();
                errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + current.getErrorCode(), current.getArgs());
            }
            result.put("message", errorMsg);
        }
        request.put(JSON, result);
        return JSON;
    }

    // 执行本次移动
    public String executionInvStatusChange() throws Exception {
        JSONObject result = new JSONObject();
        try {
        	wareHouseManagerExe.executeInvStatusChange(false, staId, stvlineCommandList, userDetails.getUser().getId());
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            String errorMsg = "";
            errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs());
            BusinessException current = e;
            while (current.getLinkedException() != null) {
                current = current.getLinkedException();
                errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + current.getErrorCode(), current.getArgs());
            }
            result.put("message", errorMsg);
            result.put("result", ERROR);
        }
        request.put(JSON, result);
        return JSON;
    }

    public String findAllInvStatus() throws JSONException {
        JSONObject result = new JSONObject();
        List<InventoryStatus> list = wareHouseManager.findInvStatusListByCompany(userDetails.getCurrentOu().getParentUnit().getParentUnit().getId());
        result.put("list", JsonUtil.collection2json(list));
        request.put(JSON, result);
        return JSON;
    }

    public String findInboundStvLine() {
        setTableConfig();
        request.put(JSON, toJson(wareHouseManager.findStvLineByDirection(staId, TransactionDirection.INBOUND.getValue(), tableConfig.getSorts())));
        return JSON;
    }

    public String findOutboundStvLine() {
        setTableConfig();
        request.put(JSON, toJson(wareHouseManager.findStvLineByDirection(staId, TransactionDirection.OUTBOUND.getValue(), tableConfig.getSorts())));
        return JSON;
    }

    // 执行本次导入
    public String executeImportInvStatusChange() throws Exception {
        JSONObject result = new JSONObject();
        try {
        	wareHouseManagerExe.executeInvStatusChangeForImpory(staId, userDetails.getUser().getId(), false, true);
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            log.error("", e);
            result.put("result", ERROR);
            result.put("message", businessExceptionPost(e));
        }
        request.put(JSON, result);
        return JSON;
    }


    public String importInvStatusChange() {
        List<String> errorMsg = new ArrayList<String>();
        if (file == null) {
            return ERROR;
        } else {
            try {
                ReadStatus rs = excelReadManager.inventoryStatusChangeImport(file, shopId, remork, userDetails.getCurrentOu().getId(), userDetails.getUser().getId());
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
                String ss = this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs());
                errorMsg.add(ss);
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

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getRemork() {
        return remork;
    }

    public void setRemork(String remork) {
        this.remork = remork;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public List<StvLine> getStvLinelist() {
        return stvLinelist;
    }

    public void setStvLinelist(List<StvLine> stvLinelist) {
        this.stvLinelist = stvLinelist;
    }

    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

    public List<StvLineCommand> getStvlineCommandList() {
        return stvlineCommandList;
    }

    public void setStvlineCommandList(List<StvLineCommand> stvlineCommandList) {
        this.stvlineCommandList = stvlineCommandList;
    }

    public StockTransApplicationCommand getStaCom() {
        return staCom;
    }

    public void setStaCom(StockTransApplicationCommand staCom) {
        this.staCom = staCom;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public List<BiChannel> getShops() {
        return shops;
    }

    public void setShops(List<BiChannel> shops) {
        this.shops = shops;
    }

    private String businessExceptionPost(BusinessException e) {
        if (ErrorCode.ERROR_NOT_SPECIFIED == e.getErrorCode()) {
            StringBuilder sb = new StringBuilder();
            for (BusinessException be = e.getLinkedException(); be != null; be = be.getLinkedException()) {
                sb.append(getMessage(ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode(), be.getArgs()) + Constants.HTML_LINE_BREAK);
            }
            return sb.toString();
        } else if (e.getLinkedException() != null) {
            BusinessException error = e.getLinkedException();
            StringBuilder sb = new StringBuilder();
            do {
                sb.append(getMessage(ErrorCode.BUSINESS_EXCEPTION + error.getErrorCode(), error.getArgs()) + Constants.HTML_LINE_BREAK);
                error = error.getLinkedException();
            } while (error != null);
            return sb.toString();
        } else {
            return getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs());
        }
    }

    public String getProductionDate(){
        return productionDate;
    }

    public void setProductionDate(String productionDate){
        this.productionDate = productionDate;
    }

    public String getExpireDate(){
        return expireDate;
    }

    public void setExpireDate(String expireDate){
        this.expireDate = expireDate;
    }

    public Long getInventoryStatusId(){
        return inventoryStatusId;
    }

    public void setInventoryStatusId(Long inventoryStatusId){
        this.inventoryStatusId = inventoryStatusId;
    }
}
