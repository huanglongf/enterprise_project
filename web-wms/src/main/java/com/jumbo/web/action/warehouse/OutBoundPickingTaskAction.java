package com.jumbo.web.action.warehouse;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.util.FormatUtil;
import com.jumbo.util.StringUtil;
import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.warehouse.RtwDieKingLineManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.model.command.RtwDiekingCommend;
import com.jumbo.wms.model.lf.StaLfCommand;
import com.jumbo.wms.model.warehouse.RtwDiekingLine;
import com.jumbo.wms.model.warehouse.RtwDiekingLineLineLogCommand;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;

import loxia.dao.Pagination;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

/**
 * 退仓出库指令生成拣货任务
 * 
 * @author zkm
 * 
 */

public class OutBoundPickingTaskAction extends BaseJQGridProfileAction {

    private static final long serialVersionUID = 1L;

    private StockTransApplicationCommand sta;
    private Long staid;
    private String splitRule;
    private Long taskNum;
    private String staCode;
    private Long lfId;
    
    private String crd;
    private String nfsStoreCode;
    
    private String city;
    private String zip;
    private String address1;
    private String address2;
    private String address3;
    private String address4;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private RtwDieKingLineManager rtwDieKingLineManager;


    public String outboundPickingTask() {
        return SUCCESS;
    }

    public String getoutboundPickingTasklist() {
        setTableConfig();
        if (sta != null) {
            sta.setStartCreateTime(FormatUtil.getDate(sta.getStartCreateTime1()));
            sta.setEndCreateTime(FormatUtil.getDate(sta.getEndCreateTime1()));
        }

        Pagination<StockTransApplicationCommand> outboundPackageStaList = wareHouseManager.findOutboundPickingTaskList(tableConfig.getStart(), tableConfig.getPageSize(), sta, userDetails.getCurrentOu().getId(), tableConfig.getSorts());
        request.put(JSON, toJson(outboundPackageStaList));
        return JSON;
    }

    // 出库明细
    public String getOutboundDetailList() {
        setTableConfig();
        Pagination<StaLineCommand> outboundDetailList = wareHouseManager.getOutboundDetailList(tableConfig.getStart(), tableConfig.getPageSize(), staid, tableConfig.getSorts());
        request.put(JSON, toJson(outboundDetailList));
        return JSON;
    }
    
    public String findOutBound(){
        JSONObject result = new JSONObject();
        try {
            StaLfCommand staLf=wareHouseManager.findOutBound(staid);
            if(null!=staLf){
                result.put("result", SUCCESS);
                result.put("msg", JsonUtil.obj2jsonIncludeAll(staLf));
            }else{
                result.put("result", ERROR);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.put(JSON, result);
        return JSON;
    }
    
    public String findCrwStaByStaId(){
        JSONObject result = new JSONObject();
        try {
             result.put("result",  wareHouseManager.findCrwStaByStaId(staid));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.put(JSON, result);
        return JSON;
    }
    
    public String saveCrwStaByStaId(){
        
        JSONObject result = new JSONObject();
        try {
             result.put("result",  wareHouseManager.saveCrwStaByStaId(staid,lfId,crd,nfsStoreCode,city,zip,address1,address2,address3,address4));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.put(JSON, result);
        return JSON;
    }

   
    // 生成拣货任务
    public String savePickingTask() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            String r = rtwDieKingLineManager.saveRtwDickingTask(staid, splitRule, taskNum, userDetails.getCurrentOu().getId(), userDetails.getUser().getId(), userDetails.getUser().getUserName());
            if (StringUtil.isEmpty(r)) {
                result.put("result", SUCCESS);
            } else {
                result.put("result", ERROR);
                result.put("message", r);
            }
        } catch (BusinessException e) {
            result.put("result", ERROR);
            result.put("message", getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, result);
        return JSON;
    }

    // 出库明细汇总
    public String getOutboundDetailListCollection() {
        setTableConfig();
        Pagination<RtwDiekingLine> outboundDetailListCollection = rtwDieKingLineManager.getOutboundDetailListCollection(tableConfig.getStart(), tableConfig.getPageSize(), userDetails.getCurrentOu().getId(), staid);
        request.put(JSON, toJson(outboundDetailListCollection));
        return JSON;
    }

    // 拣货任务明细
    public String getOutboundDickingTaskDetailList() {
        setTableConfig();
        Pagination<RtwDiekingCommend> outboundDickingTaskDetailList = rtwDieKingLineManager.getOutboundDickingTaskDetailList(tableConfig.getStart(), tableConfig.getPageSize(), tableConfig.getSorts(), userDetails.getCurrentOu().getId(), staid);
        request.put(JSON, toJson(outboundDickingTaskDetailList));
        return JSON;
    }

    // 拣货周转箱明细
    public String getOutboundDickingZzxDetailList() {
        setTableConfig();
        Pagination<RtwDiekingLineLineLogCommand> outboundDickingZzxDetailList = rtwDieKingLineManager.getOutboundDickingZzxDetailList(tableConfig.getStart(), tableConfig.getPageSize(), userDetails.getCurrentOu().getId(), staCode);
        request.put(JSON, toJson(outboundDickingZzxDetailList));
        return JSON;
    }

    /*
     * public String updateStaLpcode() throws JSONException { JSONObject 7sult = new JSONObject();
     * try { List<Long> lists = new ArrayList<Long>(); if
     * (userDetails.getCurrentOu().getOuType().getName().equals(OperationUnitType.
     * OUTYPE_OPERATION_CENTER)) { List<OperationUnit> listopc =
     * authorizationManager.selectWarehouseByCenid(userDetails.getCurrentOu().getId()); for (int i =
     * 0; i < listopc.size(); i++) { OperationUnit opc = listopc.get(i); lists.add(opc.getId()); } }
     * else { wid = userDetails.getCurrentOu().getId(); } String status =
     * wareHouseManagerExe.updateStaLpcode(wid, lpcode, getDateByString(createTime),
     * getDateByString(endCreateTime), sta, staIds, isSelectAll, lists);
     * if(StringUtils.hasText(status)){ result.put("result", ERROR); result.put("message",
     * getMessage("单据已经存在运单号不允许修改")); }else{ result.put("result", SUCCESS); }
     * 
     * } catch (BusinessException e) { result.put("result", ERROR); result.put("message",
     * getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs())); }
     * request.put(JSON, result); return JSON; }
     */
    public StockTransApplicationCommand getSta() {
        return sta;
    }

    public void setSta(StockTransApplicationCommand sta) {
        this.sta = sta;
    }

    public Long getStaid() {
        return staid;
    }

    public void setStaid(Long staid) {
        this.staid = staid;
    }



    public String getSplitRule() {
        return splitRule;
    }

    public void setSplitRule(String splitRule) {
        this.splitRule = splitRule;
    }

    public Long getTaskNum() {
        return taskNum;
    }

    public void setTaskNum(Long taskNum) {
        this.taskNum = taskNum;
    }

    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

    public Long getLfId() {
        return lfId;
    }

    public void setLfId(Long lfId) {
        this.lfId = lfId;
    }

    public String getCrd() {
        return crd;
    }

    public void setCrd(String crd) {
        this.crd = crd;
    }

    public String getNfsStoreCode() {
        return nfsStoreCode;
    }

    public void setNfsStoreCode(String nfsStoreCode) {
        this.nfsStoreCode = nfsStoreCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getAddress4() {
        return address4;
    }

    public void setAddress4(String address4) {
        this.address4 = address4;
    }

}
