package com.jumbo.wms.manager.mq;


import java.util.List;

import loxia.dao.Pagination;
import loxia.dao.Sort;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.msg.MessageConfig;

public interface MessageConfigManager extends BaseManager {

    public Pagination<MessageConfig> findMessageConfigPage(int start, int pageSize, Sort[] sorts, MessageConfig messageConfig);

    public void switchMessageConfigById(MessageConfig messageConfig);

	public String updateChooseOptionValue(String optionValue);
	
    List<MessageConfig> findMessageConfigByParameter(String msgType, String topic, String tags);
	
	public String getChooseOptionValue();
}
