package com.jumbo.manager.channel.packingPage;

import java.util.Map;

import org.springframework.stereotype.Service;


@Service("packingPageNikeGw")
public class PackingPageNikeGw extends BasePackingPage {

    @Override
    public String getMainTemplate() {
        return PRINT_TEMPLATE_BASE_PATH + "packing_list_nike_gw_main_new.jasper";
    }

    @Override
    public String getSubTemplate() {
        return PRINT_TEMPLATE_BASE_PATH + "packing_list_nike_gw_detail_new.jasper";
    }

    @Override
    public void setUserDefined(Map<String, Object> map) {}

    @Override
    public boolean isDetialShowLocation() {
        return true;
    }

}
