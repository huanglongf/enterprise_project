package com.jumbo.web.action.baseinfo;

import java.util.HashMap;
import java.util.Map;

import loxia.dao.Pagination;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.wms.manager.baseinfo.SkuModifyLogManager;
import com.jumbo.wms.model.command.SkuModifyLogCommand;
import com.jumbo.web.action.BaseJQGridProfileAction;

/**
 * 商品信息变更记录
 * 
 * @author hui.li
 * 
 */
public class SkuModifyLogAction extends BaseJQGridProfileAction {

    

    /**
     * 
     */
    private static final long serialVersionUID = 2225386799729376889L;
    
    @Autowired
    private SkuModifyLogManager skuModifyLogManager;
    
    public String code;
    public String barCode;
    public String beginDate;
    public String endDate;
    

    /**
     * 初始跳转页面，只进行页面跳转，不进行其他 操作。
     * 
     * @return
     */
    public String initSkuModifyLogInfo() {
        return SUCCESS;
    }

    /**
     * 查询商品修改日志
     * @return
     */
    public String findModifyLogList(){
        Map<String,Object> params=new HashMap<String,Object>();
        if(code!=null&&!"".equals(code)){
            
            params.put("code", code);
        }
        if(barCode!=null&&!"".equals(barCode)){
            
            params.put("barCode", barCode);
        }
        if(beginDate!=null&&!"".equals(beginDate)){
            
            params.put("bDate", beginDate);
        }
        if(endDate!=null&&!"".equals(endDate)){
            
            params.put("eDate", endDate);
        }
        
        setTableConfig();
        Pagination<SkuModifyLogCommand> p=skuModifyLogManager.findSkuModifyLogAll(tableConfig.getStart(), tableConfig.getPageSize(), params, tableConfig.getSorts());
        JSONObject j=toJson(p);
       // net.sf.json.JSONArray f=net.sf.json.JSONArray.fromObject(p.getItems().toArray());
        
        
        request.put(JSON, j);
        return JSON;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    
    
    
}
