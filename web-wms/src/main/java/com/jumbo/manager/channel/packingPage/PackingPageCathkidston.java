package com.jumbo.manager.channel.packingPage;

import java.util.Map;
import org.springframework.stereotype.Service;


@Service("packingPageCathkidston")
public class PackingPageCathkidston extends BasePackingPage {

    @Override
    public String getMainTemplate() {
        return PRINT_TEMPLATE_BASE_PATH + "trunk_main_for_cathkidston_shop.jasper";
    }

    @Override
    public String getSubTemplate() {
        return PRINT_TEMPLATE_BASE_PATH + "trunk_detail_for_cathkidston_shop.jasper";
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
