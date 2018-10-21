package com.jumbo.manager.channel.packingPage;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jumbo.wms.manager.channel.ChannelManager;

@Service("packingPageRalphLauren")
public class PackingPageRalphLauren extends BasePackingPage {
    @Autowired
    private ChannelManager channelManager;

    @Override
    public String getPostpositionalMainTemplate() {
        return PRINT_TEMPLATE_BASE_PATH + "packing_list_rl_main.jasper";
    }

    @Override
    public String getPostpositionalSubTemplate() {
        return PRINT_TEMPLATE_BASE_PATH + "packing_list_rl_detail.jasper";
    }

    @Override
    public String getMainTemplate() {
        return PRINT_TEMPLATE_BASE_PATH + "packing_list_rl_main.jasper";
    }

    @Override
    public String getSubTemplate() {
        return PRINT_TEMPLATE_BASE_PATH + "packing_list_rl_detail.jasper";
    }

    @Override
    public void setUserDefined(Map<String, Object> map) {
        map.put("subReport", getSubTemplate());
    }

    @Override
    public boolean isDetialShowLocation() {
        return false;
    }

    @Override
    public boolean isSpecialCustom() {
        return false;
    }
}
