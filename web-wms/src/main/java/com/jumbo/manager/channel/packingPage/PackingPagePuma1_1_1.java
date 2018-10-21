package com.jumbo.manager.channel.packingPage;

import java.util.Map;

import org.springframework.stereotype.Service;


@Service("packingPagePuma1_1_1")
public class PackingPagePuma1_1_1 extends BasePackingPage {

    @Override
    public String getMainTemplate() {
        return PRINT_TEMPLATE_BASE_PATH + "packing_list_puma1_1_1_main.jasper";
    }

    @Override
    public String getSubTemplate() {
        return PRINT_TEMPLATE_BASE_PATH + "packing_list_puma1_detail.jasper";
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
