package com.jumbo.web.action.warehouse;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.model.warehouse.AdPackageLineDealDto;
import com.jumbo.wms.model.warehouse.AdPackageLineDealLogDto;

import loxia.dao.Pagination;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

public class AdPackageReturnAction extends BaseJQGridProfileAction {
    /**
     * 
     */
    private static final long serialVersionUID = 4420992770503992737L;
    @Autowired
    private WareHouseManager wareHouseManager;
    private Long id;
    private String adOrderId1;
    private String wmsOrderId1;
    private String extended1;
    private String adOrderType1;
    private Integer status1;
    private String trankNo1;
    private String lpCode1;
    private String skuId1;
    private Date fromTime;
    private Date endTime;
    private Integer opStatus1;
    private String adOrderIdLog;
    private Long adOrderIdCommit;
    private String wmsStatusCommit;
    private String remarkCommit;

    public String adreturnmanage() {

        return SUCCESS;
    }

    /**
     * 查询退货申请
     * 
     * @return
     */
    public String adPackageList() {
        setTableConfig();
        Pagination<AdPackageLineDealDto> returnApp =
                wareHouseManager.adPackageList(adOrderId1, wmsOrderId1, extended1, adOrderType1, status1, trankNo1, lpCode1, fromTime, endTime, opStatus1, skuId1, tableConfig.getStart(), tableConfig.getPageSize(), tableConfig.getSorts());
        request.put(JSON, toJson(returnApp));
        return JSON;
    }

    public String adPackageDetail() throws Exception {
        request.put(JSON, JsonUtil.obj2json(wareHouseManager.adPackageDetail(id)));
        return JSON;
    }

    public String adPackageLog() {
        setTableConfig();
        Pagination<AdPackageLineDealLogDto> returnApp =
                wareHouseManager.adPackageLog(adOrderIdLog, tableConfig.getStart(), tableConfig.getPageSize(), tableConfig.getSorts());
        request.put(JSON, toJson(returnApp));
        return JSON;
    }


    public String adPackageCommit() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            wareHouseManager.adPackageCommit(adOrderIdCommit, wmsStatusCommit, remarkCommit, userDetails.getUsername());
            wareHouseManager.insertAdPackageLog(adOrderIdCommit);
            result.put("result", SUCCESS);
        } catch (Exception e) {
            log.error("adPackageCommit function is error!");
        }
        request.put(JSON, result);
        return JSON;
    }

    public String getAdOrderId1() {
        return adOrderId1;
    }

    public void setAdOrderId1(String adOrderId1) {
        this.adOrderId1 = adOrderId1;
    }

    public String getWmsOrderId1() {
        return wmsOrderId1;
    }

    public void setWmsOrderId1(String wmsOrderId1) {
        this.wmsOrderId1 = wmsOrderId1;
    }

    public String getExtended1() {
        return extended1;
    }

    public void setExtended1(String extended1) {
        this.extended1 = extended1;
    }

    public String getAdOrderType1() {
        return adOrderType1;
    }

    public void setAdOrderType1(String adOrderType1) {
        this.adOrderType1 = adOrderType1;
    }


    public Integer getStatus1() {
        return status1;
    }

    public void setStatus1(Integer status1) {
        this.status1 = status1;
    }

    public Integer getOpStatus1() {
        return opStatus1;
    }

    public void setOpStatus1(Integer opStatus1) {
        this.opStatus1 = opStatus1;
    }

    public String getTrankNo1() {
        return trankNo1;
    }

    public void setTrankNo1(String trankNo1) {
        this.trankNo1 = trankNo1;
    }

    public String getLpCode1() {
        return lpCode1;
    }

    public void setLpCode1(String lpCode1) {
        this.lpCode1 = lpCode1;
    }

    public Date getFromTime() {
        return fromTime;
    }

    public void setFromTime(Date fromTime) {
        this.fromTime = fromTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdOrderIdLog() {
        return adOrderIdLog;
    }

    public void setAdOrderIdLog(String adOrderIdLog) {
        this.adOrderIdLog = adOrderIdLog;
    }

    public Long getAdOrderIdCommit() {
        return adOrderIdCommit;
    }

    public void setAdOrderIdCommit(Long adOrderIdCommit) {
        this.adOrderIdCommit = adOrderIdCommit;
    }

    public String getWmsStatusCommit() {
        return wmsStatusCommit;
    }

    public void setWmsStatusCommit(String wmsStatusCommit) {
        this.wmsStatusCommit = wmsStatusCommit;
    }

    public String getRemarkCommit() {
        return remarkCommit;
    }

    public void setRemarkCommit(String remarkCommit) {
        this.remarkCommit = remarkCommit;
    }

    public String getSkuId1() {
        return skuId1;
    }

    public void setSkuId1(String skuId1) {
        this.skuId1 = skuId1;
    }




}
