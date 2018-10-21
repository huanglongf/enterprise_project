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

import javax.servlet.http.HttpServletRequest;

import loxia.dao.Pagination;
import loxia.support.excel.ExcelKit;
import loxia.support.excel.ReadStatus;
import loxia.support.json.JSONArray;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.Constants;
import com.jumbo.util.FormatUtil;
import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.baseinfo.BaseinfoManager;
import com.jumbo.wms.manager.warehouse.TransactionTypeManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerCancel;
import com.jumbo.wms.manager.warehouse.WareHouseManagerQuery;
import com.jumbo.wms.manager.warehouse.excel.ExcelReadManager;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StvLineCommand;
import com.jumbo.wms.model.warehouse.TransactionType;
import com.jumbo.wms.model.warehouse.WarehouseLocation;
import com.jumbo.wms.model.warehouse.WarehouseLocationCommand;

/**
 * 仓库其他出入库操作
 * 
 * @author sjk
 * 
 */
public class WarehouseOperationOthersAction extends BaseJQGridProfileAction implements ServletRequestAware {

    /**
	 * 
	 */
    private HttpServletRequest requestt;
    private static final long serialVersionUID = 6401331234869555404L;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private WareHouseManagerQuery wareHouseManagerQuery;
    @Autowired
    private WareHouseManagerCancel wareHouseManagerCancel;
    @Autowired
    private ExcelReadManager excelReadManager;
    @Autowired
    private TransactionTypeManager transactionTypeManager;
    @Autowired
    private BaseinfoManager baseinfoManager;

    private File file;

    private List<InventoryStatus> invStatusList;
    private List<TransactionType> transactionTypes;
    private List<BiChannel> shops;
    private StockTransApplication sta;
    private List<StvLineCommand> stvLineCmd = new ArrayList<StvLineCommand>();

    private String locationCode;
    private String jmcode;
    private String keyProperties;
    private String barcode;
    private String importMemo;

    private Long locationId;
    private String createDate;
    private String endCreateDate;
    private String startDate;
    private String offStartDate;
    private StockTransApplicationCommand staCommand;
    private Integer status;
    /**
     * 保存并执行
     */
    private boolean execute;
    private Long staID;
    private String owner;
    /**
     * 级联查询
     */
    private String term;

    /**
     * 作业类型
     */
    private Long transactionId;
    private Long skuId;
    // 其他出入库的sn序列号
    private String sns;

    // 物流信息
    private StaDeliveryInfo stadelivery;

    public String getSns() {
        return sns;
    }

    public void setSns(String sns) {
        this.sns = sns;
    }

    public HttpServletRequest getRequest() {
        return requestt;
    }

    public void setServletRequest(HttpServletRequest request) {
        this.requestt = request;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String operationOthersEntry() {
        shops = baseinfoManager.queryShopListMultiCheck(userDetails.getCurrentOu().getId());
        transactionTypes = transactionTypeManager.findListByOu(userDetails.getCurrentOu().getParentUnit().getId(), false);
        invStatusList = wareHouseManager.findInvStatusByOuid(userDetails.getCurrentOu().getParentUnit().getParentUnit().getId());
        return SUCCESS;
    }

    public String otherStaCreateImport() {
        List<String> errorMsg = new ArrayList<String>();
        if (file == null) {
            return ERROR;
        } else {
            try {
                ReadStatus rs = excelReadManager.otherStaImport(sta.getRefSlipCode(), file, owner, stadelivery, importMemo, transactionId, userDetails.getCurrentOu().getId(), userDetails.getUser().getId());
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
                    errorMsg.add(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + current.getErrorCode(), current.getArgs()));
                }
            }
            request.put("message", JsonUtil.collection2json(errorMsg));
        }
        return SUCCESS;
    }

    /**
     * 根据ID获取作业类型对象
     * 
     * @return
     * @throws Exception
     */
    public String getTransactionType() throws Exception {
        JSONObject obj = new JSONObject();
        try {
            TransactionType transactionType = transactionTypeManager.findTransactionById(transactionId);
            obj.put("result", SUCCESS);
            obj.put("opType", JsonUtil.obj2json(transactionType));
        } catch (BusinessException e) {
            obj.put("result", ERROR);
            obj.put("message", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, obj);
        return JSON;
    }

    /**
     * 获取sku
     * 
     * @return
     * @throws Exception
     */
    public String findCountSkuByParameter() throws Exception {
        JSONObject obj = new JSONObject();
        try {
            int count = wareHouseManager.findCountSkuByParameter(barcode, jmcode, keyProperties,userDetails.getCurrentOu().getId());
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
     * 获取sku
     * 
     * @return
     * @throws Exception
     */
    public String findSkuByParameter() throws Exception {
        JSONObject obj = new JSONObject();
        try {
            Sku sku = wareHouseManagerQuery.findSkuByParameter(barcode, jmcode, keyProperties,userDetails.getCurrentOu().getId());
            obj.put("result", SUCCESS);
            obj.put("sku", JsonUtil.obj2json(sku));
        } catch (BusinessException e) {
            obj.put("result", ERROR);
            obj.put("message", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, obj);
        return JSON;
    }

    /**
     * 合并重复行
     */
    private void mergerStvLine() {
        List<StvLineCommand> list = new ArrayList<StvLineCommand>();
        for (StvLineCommand stvl : this.getStvLineCmd()) {
            boolean hasSame = false;
            for (StvLineCommand tp : list) {
                if (stvl.getLocationCode().equals(tp.getLocationCode()) && stvl.getSkuId().equals(tp.getSkuId()) && stvl.getInvStatus().getId().equals(tp.getInvStatus().getId())
                        && ((stvl.getSkuCode() == null && tp.getSkuCode() == null) || (stvl.getSkuCode().equals(tp.getSkuCode())))) {
                    tp.setQuantity(tp.getQuantity() + stvl.getQuantity());
                    tp.setSkuCode(tp.getSkuCode() == null ? stvl.getSkuCode() : tp.getSkuCode());
                    hasSame = true;
                    break;
                }
            }
            if (!hasSame) {
                list.add(stvl);
            }
        }
        setStvLineCmd(list);
    }

    /**
     * 创建其他出入库作业单
     * 
     * @return
     * @throws Exception
     */
    public String createOthersSta() throws Exception {
        JSONObject result = new JSONObject();
        String errorMsg = "";
        try {
            mergerStvLine();
            wareHouseManager.createOthersInOrOutBoundSta(sta.getRefSlipCode(), false, transactionId, userDetails.getUser(), stadelivery, userDetails.getCurrentOu(), sta.getOwner(), sta.getMemo(), stvLineCmd, execute, sns);
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
            log.error("",e);
            result.put("msg", e.getCause() + " " + e.getMessage());
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 根据库位查找库存状态
     * 
     * @return
     * @throws JSONException
     */
    public String getInvStatusByInventory() throws JSONException {
        JSONObject obj = new JSONObject();
        try {
            Sku sku = wareHouseManagerQuery.findSkuByParameter(barcode, jmcode, keyProperties,userDetails.getCurrentOu().getId());
            List<InventoryStatus> list = wareHouseManager.findInvStatusByInventory(transactionId, userDetails.getCurrentOu().getId(), locationCode, skuId, owner);
            log.debug(" list : " + list.size());
            obj.put("result", SUCCESS);
            obj.put("sku", JsonUtil.obj2json(sku));
            obj.put("list", JsonUtil.collection2json(list));
        } catch (BusinessException e) {
            obj.put("result", ERROR);
            obj.put("message", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, obj);
        return JSON;
    }

    /**
     * 校验此库位是否有此作业类型
     */
    public String findLocationTranstype() throws JSONException {
        JSONObject obj = new JSONObject();
        try {
            // 是否存在库位
            Integer count = wareHouseManager.findLocationTranstype(locationId, transactionId);
            if (count.intValue() == 1) { // 此库位有此类型
                obj.put("result", SUCCESS);
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
     * 入库模糊code+作业类型 查询出可用库位列表
     * 
     * @return
     * @throws JSONException
     */
    public String findAvailLocationForOther() throws JSONException {
        JSONArray ja = new JSONArray();
        if (transactionId != null) {
            List<WarehouseLocation> list = wareHouseManager.findMateWLListbyCode(userDetails.getCurrentOu().getId(), new Long(transactionId), term);
            java.util.Iterator<WarehouseLocation> it = list.iterator();
            while (it.hasNext()) {
                WarehouseLocation w = it.next();
                JSONObject jo = new JSONObject();
                jo.put("id", w.getId());
                jo.put("label", w.getCode());
                jo.put("value", w.getCode());
                ja.put(jo);
            }
        }
        request.put(JSON, ja);
        return JSON;
    }

    /**
     * 出库模糊code+作业类型 查询出可用库位列表
     * 
     * @return
     * @throws JSONException
     */
    public String findAvailLocationByOwner() throws JSONException {
        JSONArray ja = new JSONArray();
        if (transactionId != null) {
            List<WarehouseLocation> list = wareHouseManager.findMateWLListbyOwnerCode(userDetails.getCurrentOu().getId(), new Long(transactionId), term, owner);
            java.util.Iterator<WarehouseLocation> it = list.iterator();
            while (it.hasNext()) {
                WarehouseLocation w = it.next();
                JSONObject jo = new JSONObject();
                jo.put("id", w.getId());
                jo.put("label", w.getCode());
                jo.put("value", w.getCode());
                ja.put(jo);
            }
        }
        request.put(JSON, ja);
        return JSON;
    }

    // 库位编码校验
    public String findLocationByCode() throws JSONException {
        JSONObject obj = new JSONObject();
        try {
            WarehouseLocationCommand loc = wareHouseManager.checkLocationTranstypeByCode(locationCode, userDetails.getCurrentOu().getId());
            obj.put("result", SUCCESS);
            obj.put("location", JsonUtil.obj2json(loc));
        } catch (BusinessException e) {
            obj.put("result", ERROR);
            obj.put("message", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, obj);
        return JSON;
    }

    /**
     * 仓库其他出入库操作查询
     * 
     * @return
     */
    public String operationOthersQuery() {
        return SUCCESS;
    }

    /**
     * 获取其他出入库的sta
     * 
     * @return
     */
    public String operationOthersOperateQuery() {
        // Long companyid =
        // baseinfoManager.findCompanyByWarehouse(this.userDetails.getCurrentOu().getId()).getId();
        setTableConfig();
        if (status != null && status != 0) {
            if (staCommand == null) {
                staCommand = new StockTransApplicationCommand();
            }
            staCommand.setStatus(StockTransApplicationStatus.valueOf(status));
            staCommand.setLastModifyTime(new Date());
        }
        Pagination<StockTransApplicationCommand> stas =
                wareHouseManager.findElseComeInAndGoOut(tableConfig.getStart(), tableConfig.getPageSize(), userDetails.getCurrentOu().getId(), FormatUtil.getDate(createDate), FormatUtil.getDate(endCreateDate), FormatUtil.getDate(startDate),
                        FormatUtil.getDate(offStartDate), staCommand, tableConfig.getSorts());
        request.put(JSON, toJson(stas));
        return JSON;
    }

    /**
     * 根据staID 获取 staLine详细信息
     * 
     * @return
     */

    public String operationOthersOperateQueryDetails() {
        setTableConfig();
        request.put(JSON, toJson(wareHouseManager.findStaLineOthersOperate(tableConfig.getStart(),tableConfig.getPageSize(),staID,tableConfig.getSorts())));
        return JSON;
    }

    /**
     * id
     * @return
     * @throws JSONException
     */
    public String operationOthersOperateQueryDetails2() throws JSONException {
        List<String> errorMsg = new ArrayList<String>();
        JSONObject result = new JSONObject();
        try {                                            
             List<StaLineCommand> list =wareHouseManager.operationOthersOperateQueryDetails2(staID);
             if(list.isEmpty()) {
                 result.put("result", ERROR);
           }else {
               result.put("result", SUCCESS);
           }
        }catch (BusinessException e) {
           BusinessException bex = e;
           do {
               String msg = this.getMessage(ErrorCode.BUSINESS_EXCEPTION + bex.getErrorCode(), bex.getArgs());
               log.error(msg);
               errorMsg.add(msg);
               bex = bex.getLinkedException();
           } while (bex != null);
           result.put("result", ERROR);
           result.put("errMsg", JsonUtil.collection2json(errorMsg));
       } catch (Exception e) {
           log.error("",e);
           result.put("result", e.getCause() + " " + e.getMessage());
       }
        request.put(JSON, result);
        return JSON;
    }
    
    /**
     * 
     * @return
     */
    public String findSnSkuQtyByStaId() {
        JSONObject obj = new JSONObject();
        Long qty = wareHouseManager.findSnSkuQtyByStaId(staID, userDetails.getCurrentOu().getId());
        if (qty == null) qty = 0L;
        try {
            obj.put("qty", qty);
        } catch (JSONException e) {
            log.error("",e);
        }
        request.put(JSON, obj);
        return JSON;
    }

    /**
     * 其他出入库执行
     * 
     * @return
     * @throws Exception
     */

    public String operateExecution() throws Exception {
        JSONObject result = new JSONObject();
        try {
            wareHouseManager.othersStaInOutbound(staID, userDetails.getUser().getId(), userDetails.getCurrentOu().getId(), sns);
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            result.put("result", ERROR);
            result.put("message", businessExceptionPost(e));
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 其他出入库取消
     * 
     * @return
     * @throws JSONException
     */

    public String operateCanceled() throws Exception {
        JSONObject result = new JSONObject();
        try {
        	wareHouseManagerCancel.cancelOthersStaInOutbound(staID, userDetails.getUser().getId());
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            result.put("result", ERROR);
            result.put("message", businessExceptionPost(e));
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 采购sta导入
     * 
     * @return
     * @throws Exception
     */

    public String snImports() throws Exception {
        try {
            ReadStatus rs =  excelReadManager.importSNnumber(file, userDetails.getCurrentOu().getId());
            if (rs.getStatus() == ReadStatus.STATUS_SUCCESS) {
                request.put("result", SUCCESS);
            } else if (rs.getStatus() > 0) {
                String msg = "";
                List<String> list = ExcelKit.getInstance().getReadStatusMessages(rs, this.getLocale());
                for (String s : list) {
                    msg += s;
                }
                request.put("msg", msg);
            } else if (ReadStatus.STATUS_SUCCESS == rs.getStatus()) {

            }
        }catch (BusinessException e) {
            request.put("msg", getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        } catch (Exception e) {
            log.error("",e);
            request.put("msg", "error");
        }
        return SUCCESS;
    }

    public String snsImport() throws Exception {
        try {
            ReadStatus rs = excelReadManager.snsImport(file, staID, userDetails.getCurrentOu().getId());
            if (rs.getStatus() == ReadStatus.STATUS_SUCCESS) {
                request.put("result", SUCCESS);
            } else if (rs.getStatus() > 0) {
                String msg = "";
                List<String> list = ExcelKit.getInstance().getReadStatusMessages(rs, this.getLocale());
                for (String s : list) {
                    msg += s;
                }
                request.put("msg", msg);
            } else if (ReadStatus.STATUS_SUCCESS == rs.getStatus()) {

            }
        } catch (BusinessException e) {
            request.put("msg", getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        } catch (Exception e) {
            log.error("",e);
            request.put("msg", "error");
        }
        return SUCCESS;
    }

    
    private String businessExceptionPost(BusinessException e) {
        if (ErrorCode.ERROR_NOT_SPECIFIED == e.getErrorCode()) {
            BusinessException[] errors = (BusinessException[]) e.getArgs();
            StringBuilder sb = new StringBuilder();
            for (BusinessException be : errors) {
                sb.append(getMessage(ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode(), be.getArgs()) + Constants.HTML_LINE_BREAK);
            }
            return sb.toString();
        }else if(e.getLinkedException() != null){
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

    /**
     * 方法说明：出入库SN号导入(菜鸟仓)
     * 
     * @author LuYingMing
     * @date 2016年9月12日 下午2:26:51
     * @return
     * @throws Exception
     */
    public String caiNiaoWhSnImport() throws Exception{
        try{
            ReadStatus rs = excelReadManager.outOfStorageSnImport(file, userDetails.getCurrentOu().getId());
            if (rs.getStatus() == ReadStatus.STATUS_SUCCESS){
                request.put("result", SUCCESS);
            }else if (rs.getStatus() > 0){
                String msg = "";
                List<String> list = ExcelKit.getInstance().getReadStatusMessages(rs, this.getLocale());
                for (String s : list){
                    msg += s;
                }
                request.put("msg", msg);
            }
        }catch (BusinessException e){
            request.put("msg", getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }catch (Exception e){
            log.error("", e);
            request.put("msg", "error");
        }
        return SUCCESS;
    }
    

    public List<BiChannel> getShops() {
        return shops;
    }

    public void setShops(List<BiChannel> shops) {
        this.shops = shops;
    }

    public List<StvLineCommand> getStvLineCmd() {
        return stvLineCmd;
    }

    public void setStvLineCmd(List<StvLineCommand> stvLineCmd) {
        this.stvLineCmd = stvLineCmd;
    }

    public List<InventoryStatus> getInvStatusList() {
        return invStatusList;
    }

    public void setInvStatusList(List<InventoryStatus> invStatusList) {
        this.invStatusList = invStatusList;
    }

    public StockTransApplication getSta() {
        return sta;
    }

    public void setSta(StockTransApplication sta) {
        this.sta = sta;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
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

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public List<TransactionType> getTransactionTypes() {
        return transactionTypes;
    }

    public void setTransactionTypes(List<TransactionType> transactionTypes) {
        this.transactionTypes = transactionTypes;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getEndCreateDate() {
        return endCreateDate;
    }

    public void setEndCreateDate(String endCreateDate) {
        this.endCreateDate = endCreateDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getOffStartDate() {
        return offStartDate;
    }

    public void setOffStartDate(String offStartDate) {
        this.offStartDate = offStartDate;
    }

    public StockTransApplicationCommand getStaCommand() {
        return staCommand;
    }

    public void setStaCommand(StockTransApplicationCommand staCommand) {
        this.staCommand = staCommand;
    }

    public Long getStaID() {
        return staID;
    }

    public void setStaID(Long staID) {
        this.staID = staID;
    }

    public boolean isExecute() {
        return execute;
    }

    public void setExecute(boolean execute) {
        this.execute = execute;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
        // try {
        // this.owner = new String(owner.getBytes("iso8859_1"), "UTF-8");
        // } catch (UnsupportedEncodingException e) {
        // log.error("",e);
        // }
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getImportMemo() {
        return importMemo;
    }

    public void setImportMemo(String importMemo) {
        this.importMemo = importMemo;
    }

    public StaDeliveryInfo getStadelivery() {
        return stadelivery;
    }

    public void setStadelivery(StaDeliveryInfo stadelivery) {
        this.stadelivery = stadelivery;
    }

}
