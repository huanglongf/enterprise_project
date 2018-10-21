package com.jumbo.manager.channel.packingPage;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service("packingPageNikePackingList")
public class PackingPageNikePackingList extends BasePackingPage {
    @Override
    public String getMainTemplate() {
        return PRINT_TEMPLATE_BASE_PATH + "packing_list_nike_main_new1.jasper";
    }

    @Override
    public String getSubTemplate() {
        return PRINT_TEMPLATE_BASE_PATH + "packing_list_nike_gw_new1.jasper";
    }

    @Override
    public void setUserDefined(Map<String, Object> map) {
        map.put("subReport", getSubTemplate());
    }

    @Override
    public boolean isDetialShowLocation() {
        return true;
    }

}
