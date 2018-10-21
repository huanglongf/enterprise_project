package com.jumbo.web.action.automaticEquipment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.manager.priorityChannelOms.PriorityChannelOmsUtilManager;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.warehouse.PriorityChannelOms;

import loxia.dao.Pagination;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;


/**
 * @author 
 *
 * @createDate 
 */
public class PriorityChangelOmsAction extends BaseJQGridProfileAction {


    private static final long serialVersionUID = 4135139310496846146L;
    @Autowired
    private PriorityChannelOmsUtilManager priorityChannelOmsUtilManager;
	private PriorityChannelOms priorityChannelOms;
    
    private String optionValue;
    public String getOptionValue() {
		return optionValue;
	}
	public void setOptionValue(String optionValue) {
		this.optionValue = optionValue;
	}
	public PriorityChannelOms getPriorityChannelOms() {
		return priorityChannelOms;
	}
	public void setPriorityChannelOms(PriorityChannelOms priorityChannelOms) {
		this.priorityChannelOms = priorityChannelOms;
	}
	public String getAllPriorityChannelOms() {
    	setTableConfig();
    	Pagination<PriorityChannelOms> threadList=priorityChannelOmsUtilManager.getAllPriorityChannelOms(tableConfig.getStart(), tableConfig.getPageSize(), tableConfig.getSorts());
		request.put(JSON, toJson(threadList));
        
        return JSON;
    }
    public String addPriorityChannelOms() throws JSONException {
        JSONObject result = new JSONObject();
        if (priorityChannelOms != null) {
            try {
                String flag = priorityChannelOmsUtilManager.addPriorityChannelOms(priorityChannelOms);
                if ("".equals(flag)||flag == null) {
                    result.put("flag", SUCCESS);
                } else {
                    result.put("flag", flag);
                }
            } catch (Exception e) {
                result.put("flag", ERROR);
                log.error(e.getMessage());
            }
        } else {
            result.put("flag", "信息不完整！");
        }
        request.put(JSON, result);
        return JSON;
    }
    public String updateChooseOption() throws JSONException {
        JSONObject result = new JSONObject();
        if (optionValue != null&&!"".equals(optionValue)) {
            try {
                String flag = priorityChannelOmsUtilManager.updateChooseOption(optionValue);
                if ("".equals(flag)||flag == null) {
                    result.put("flag", SUCCESS);
                } else {
                    result.put("flag", flag);
                }
            } catch (Exception e) {
                result.put("flag", ERROR);
                log.error(e.getMessage());
            }
        } else {
            result.put("flag", "信息不完整！");
        }
        request.put(JSON, result);
        return JSON;
    }
    /**
     * 查询直连创单配置优先级比例
     * 
     * @return
     * @throws Exception
     */
    public String buildChooseOption() throws Exception {
        request.put(JSON, JsonUtil.obj2json(priorityChannelOmsUtilManager.buildChooseOption()));
        return JSON;
    }
}
