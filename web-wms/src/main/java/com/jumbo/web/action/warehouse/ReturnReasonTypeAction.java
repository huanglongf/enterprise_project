package com.jumbo.web.action.warehouse;

import loxia.dao.Pagination;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.wms.manager.warehouse.ReturnReasonTypeManager;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.util.FormatUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;

public class ReturnReasonTypeAction extends BaseJQGridProfileAction{

    private static final long serialVersionUID = -4123424244310328034L;

    @Autowired
    private ReturnReasonTypeManager reasonTypeManager;
    private StockTransApplicationCommand sta;
    private String createTime;  //起始时间
    private String endCreateTime;  //结束时间
    private Integer returnReasonType;   //退货原因类型
    private String code;
    
    public String execute() {
        
        return SUCCESS;
    }
    
    public String findAllReturnGoods(){
        setTableConfig();
        Pagination<StockTransApplicationCommand> list= reasonTypeManager.findAllReturnGoods(tableConfig.getStart(),tableConfig.getPageSize(),userDetails.getCurrentOu().getId(),FormatUtil.getDate(createTime), FormatUtil.getDate(endCreateTime),returnReasonType,sta,tableConfig.getSorts());
        request.put(JSON,  toJson(list));
        return JSON;
    }

 

    public StockTransApplicationCommand getSta() {
        return sta;
    }

    public void setSta(StockTransApplicationCommand sta) {
        this.sta = sta;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEndCreateTime() {
        return endCreateTime;
    }

    public void setEndCreateTime(String endCreateTime) {
        this.endCreateTime = endCreateTime;
    }

        public Integer getReturnReasonType() {
        return returnReasonType;
    }

    public void setReturnReasonType(Integer returnReasonType) {
        this.returnReasonType = returnReasonType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

   
    
}
