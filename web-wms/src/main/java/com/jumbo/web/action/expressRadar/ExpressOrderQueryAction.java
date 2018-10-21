package com.jumbo.web.action.expressRadar;

import java.util.List;

import loxia.dao.Pagination;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.manager.expressRadar.ExpressOocRadarManager;
import com.jumbo.wms.manager.expressRadar.ExpressOrderQueryManager;
import com.jumbo.wms.model.command.expressRadar.ExpressDetailCommand;
import com.jumbo.wms.model.command.expressRadar.ExpressOrderQueryCommand;
import com.jumbo.wms.model.expressRadar.RadarTransNo;
import com.jumbo.wms.model.expressRadar.TransRoute;

public class ExpressOrderQueryAction extends BaseJQGridProfileAction {

    /**
     * 
     */
    private static final long serialVersionUID = -2932526234950871581L;
    @Autowired
    private ExpressOrderQueryManager expressOrderQueryManager;
    
    @Autowired
    private ExpressOocRadarManager expressOocRadarManager;
    
    private ExpressOrderQueryCommand expressOrderQueryCommand;
    
    private Long id;
    
    private String pcode;
    
    private String expressCode;
    
    private Long warningTypeId;
    
    private String remark;
    
    private String orderCode;
    
    private String errorCode;
    
    private Long lvId;
    
    /**
     * 快递/订单信息查询界面
     * 
     * @return
     */
    public String initExpressOrderQueryPage() {
        return SUCCESS;
    }

    /**
     * 快递信息查询结果列表
     * 
     * @return
     */
    public String findAllExpressInfoList(){
        setTableConfig();
        Pagination<ExpressOrderQueryCommand> expressOrderList = expressOrderQueryManager.findExpressInfoList(tableConfig.getStart(), tableConfig.getPageSize(), expressOrderQueryCommand, tableConfig.getSorts());
        request.put(JSON, toJson(expressOrderList));
        return JSON;
    }
    
    /**
     * 指定快递明细信息查看
     * 
     * @return
     */
    public String findExpressDetailInfo(){
        if(null != id){
            RadarTransNo rt = expressOrderQueryManager.findRadarTransNoDetail(id);
            request.put(JSON, JsonUtil.obj2json(rt));
        }
        return JSON;
    }
    
    /**
     * 快递明细信息查看列表
     * 
     * @return
     */
    public String findExpressDetail(){
        setTableConfig();
        if(null != expressCode&&!"".equals(expressCode)){
            List<ExpressDetailCommand> expressDetailList = expressOrderQueryManager.findExpressDetail(expressCode);
            request.put(JSON, toJson(expressDetailList));
        }
        return JSON;
    }
    
    /**
     * 订单明细信息查看列表
     * 
     * @return
     */
    public String findOrderDetail(){
        setTableConfig();
        if(null != orderCode && !"".equals(orderCode)){
            List<ExpressDetailCommand> orderDetailList = expressOrderQueryManager.findOrderDetail(orderCode);
            request.put(JSON, toJson(orderDetailList));
        }
        return JSON;
    }
    
    /**
     * 物流明细信息查看列表
     * 
     * @return
     */
    public String findLogisticsDetail(){
        setTableConfig();
        if(null != expressCode&&!"".equals(expressCode)){
            List<TransRoute> transRouteList = expressOrderQueryManager.findLogisticsDetail(expressCode);
            request.put(JSON, toJson(transRouteList));
        }
        return JSON;
    }
    
    /**
     * 更新预警类型
     * 
     */
    public String updateWarningType(){
        String flag = null;
        JSONObject result = new JSONObject();
        Long userId = null;
        if(null != expressCode){
            try{
                userId = this.userDetails.getUser().getId();
                flag = expressOrderQueryManager.updateWarningType(expressCode, warningTypeId, remark, userId, lvId);
                if("SUCCESS".equals(flag)){
                    result.put("flag", "success");
                    request.put(JSON, result); 
                }
                else{
                    result.put("flag", "error");
                    request.put(JSON, result); 
                }
            } catch(Exception e){
                log.error(e.getMessage());
            }
        }
        return JSON;
    }
    
    public String findOrderErrorCode(){
        request.put(JSON, JsonUtil.collection2json(expressOocRadarManager.findOrderErrorCode()));
        return JSON;
    }
    
    public String findOrderWarningLv(){
        request.put(JSON, JsonUtil.collection2json(expressOrderQueryManager.findOrderWarningLv(errorCode)));
        return JSON;
    }
    
    
    public ExpressOrderQueryCommand getExpressOrderQueryCommand() {
        return expressOrderQueryCommand;
    }

    public void setExpressOrderQueryCommand(ExpressOrderQueryCommand expressOrderQueryCommand) {
        this.expressOrderQueryCommand = expressOrderQueryCommand;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExpressCode() {
        return expressCode;
    }

    public void setExpressCode(String expressCode) {
        this.expressCode = expressCode;
    }

    public Long getWarningTypeId() {
        return warningTypeId;
    }

    public void setWarningTypeId(Long warningTypeId) {
        this.warningTypeId = warningTypeId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public Long getLvId() {
        return lvId;
    }

    public void setLvId(Long lvId) {
        this.lvId = lvId;
    }

}
