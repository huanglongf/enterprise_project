package com.jumbo.web.action.system;



import loxia.dao.Pagination;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.manager.mq.MessageConfigManager;
import com.jumbo.wms.model.msg.MessageConfig;

/**
 * 
 * @author jinggang.chen
 * 
 */
public class MessageConfigAction extends BaseJQGridProfileAction {

    private static final long serialVersionUID = -2620901111716948581L;

    @Autowired
    private MessageConfigManager messageConfigManager;

    private MessageConfig messageConfig;
    
    private String optionValue;

    public String getOptionValue() {
		return optionValue;
	}

	public void setOptionValue(String optionValue) {
		this.optionValue = optionValue;
	}

	public String mqSwitchManager() {

        return SUCCESS;
    }

    public String getMessageConfigList() {
        setTableConfig();
        Pagination<MessageConfig> messageConfigList = messageConfigManager.findMessageConfigPage(tableConfig.getStart(), tableConfig.getPageSize(), tableConfig.getSorts(), messageConfig);
        request.put(JSON, toJson(messageConfigList));
        return JSON;
    }

    public String switchMessageConfig() throws JSONException {
        JSONObject result = new JSONObject();
        if (messageConfig != null) {
            
            try {
                messageConfigManager.switchMessageConfigById(messageConfig);
            } catch (Exception e) {
                result.put("result", "error:"+e.getMessage());
                request.put(JSON, result);
            }
            result.put("result", "success");
            request.put(JSON, result);
        }
        return JSON;
    }
    public String updateChooseOptionValue() throws JSONException {
        JSONObject result = new JSONObject();
        if (optionValue != null&&!"".equals(optionValue)) {
            try {
                String flag = messageConfigManager.updateChooseOptionValue(optionValue);
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
    public String getChooseOptionValue() throws JSONException {
        JSONObject result = new JSONObject();
                String flag = messageConfigManager.getChooseOptionValue();
                if ("".equals(flag)||flag == null) {
                    result.put("flag", SUCCESS);
                } 
        request.put(JSON, result);
        return JSON;
    }
    public MessageConfig getMessageConfig() {
        return messageConfig;
    }

    public void setMessageConfig(MessageConfig messageConfig) {
        this.messageConfig = messageConfig;
    }



}
