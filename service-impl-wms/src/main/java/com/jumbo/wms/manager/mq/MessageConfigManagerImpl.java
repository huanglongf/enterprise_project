package com.jumbo.wms.manager.mq;

import java.util.List;

import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jumbo.dao.msg.MessageConfigDao;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.util.TimeHashMap;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.msg.MessageConfig;
import com.jumbo.wms.model.system.ChooseOption;

@Service("messageConfigManager")
public class MessageConfigManagerImpl extends BaseManagerImpl implements MessageConfigManager {


    private static final long serialVersionUID = -366216106631880092L;

    static TimeHashMap<String, List<MessageConfig>> messageConfigByParameterMap = new TimeHashMap<String, List<MessageConfig>>();

    @Autowired
    private MessageConfigDao messageConfigDao;
    @Autowired
    private ChooseOptionDao chooseOptionDao;

    @Override
    public Pagination<MessageConfig> findMessageConfigPage(int start, int pageSize, Sort[] sorts, MessageConfig messageConfig) {
        String msgType = null;
        String topic = null;
        if (messageConfig != null) {
            if (StringUtils.hasText(messageConfig.getMsgType())) {
                msgType = messageConfig.getMsgType();
            }
            if (StringUtils.hasText(messageConfig.getTopic())) {
                topic = messageConfig.getTopic();
            }
        }
        return messageConfigDao.findMessageConfigPage(start, pageSize, sorts, msgType, topic, new BeanPropertyRowMapper<MessageConfig>(MessageConfig.class));

    }

    @Override
    public void switchMessageConfigById(MessageConfig messageConfig) {
        MessageConfig messageConfig1 = messageConfigDao.getByPrimaryKey(messageConfig.getId());
        messageConfig1.setIsOpen(messageConfig.getIsOpen());
        messageConfigDao.save(messageConfig1);
    }

    @Override
    public String updateChooseOptionValue(String optionValue) {

        String flag = "";
        MessageConfig messageConfig = messageConfigDao.getByPrimaryKey(181L);
        if (messageConfig != null && messageConfig.getIsOpen() != null && messageConfig.getIsOpen() == 1) {
            ChooseOption chooseOption = chooseOptionDao.findByCategoryCodeAndKey("SYS_WH", "OCP_MQ_OR_THREAD");
            if (chooseOption != null) {
                chooseOption.setOptionValue(optionValue);
                chooseOptionDao.save(chooseOption);
            }
        } else {
            flag = "修改失败！请看清红色字体提示";
        }


        return flag;
    }

    @Override
    public String getChooseOptionValue() {

        String flag = "";
        ChooseOption chooseOption = chooseOptionDao.findByCategoryCodeAndKey("SYS_WH", "OCP_MQ_OR_THREAD");
        if (chooseOption != null && !com.jumbo.util.StringUtil.isEmpty(chooseOption.getOptionValue()) && chooseOption.getOptionValue().equals("1")) {
            flag = "1";
        }


        return flag;
    }

    @Override
    public List<MessageConfig> findMessageConfigByParameter(String msgType, String topic, String tags) {
        String mapKey = msgType + "⊥" + topic + "⊥" + tags;
        List<MessageConfig> mc = messageConfigByParameterMap.get(mapKey);
        if (null == mc) {
            mc = messageConfigDao.findMessageConfigByParameter(msgType, topic, tags, new BeanPropertyRowMapper<MessageConfig>(MessageConfig.class));
            messageConfigByParameterMap.put(mapKey, mc, 30 * 60 * 1000);
        }
        return mc;
    }

}
