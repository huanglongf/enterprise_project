package com.jumbo.web.action.warehouse;



import loxia.dao.Pagination;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.wms.manager.warehouse.AddCounterMaintainManager;
import com.jumbo.wms.model.system.ChooseOptionCommand;
import com.jumbo.web.action.BaseJQGridProfileAction;

public class AddCounterMaintainAction extends BaseJQGridProfileAction {
   
    private static final long serialVersionUID = -3519358194362787531L;

    @Autowired
    private AddCounterMaintainManager addCounterMaintainManager;
    
   @Override 
   public String execute(){
       
       return SUCCESS;
   }
   
   private String optionValue;
   private Long id;
   
   /**
    * 新增秒杀订单计数器功能维护 查询
    * @return
    */
   public String findSecKillMaintain(){
       setTableConfig();
       Pagination<ChooseOptionCommand> roleList =addCounterMaintainManager.findSecKillMaintain(tableConfig.getStart(),tableConfig.getPageSize(),tableConfig.getSorts());
       request.put(JSON, toJson(roleList));
       return JSON;
   }
   
   /**
    * 新增秒杀订单计数器功能维护 修改
    * @return
    * @throws JSONException
    */
   public String updateOptionValue() throws JSONException{
     JSONObject result = new JSONObject();
       if (id != null) {
           addCounterMaintainManager.updateOptionValue(id, optionValue);
           result.put("result", SUCCESS);
       } else {
           result.put("result", ERROR);
       }
       request.put(JSON, result);
       return JSON;
   }

    public String getOptionValue() {
        return optionValue;
    }
    
    public void setOptionValue(String optionValue) {
        this.optionValue = optionValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

   
       
       
       
}
