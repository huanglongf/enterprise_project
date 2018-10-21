package com.jumbo.manager.channel.packingPage;

import java.util.Map;
import org.springframework.stereotype.Service;


@Service("packingPageJackwolfskin")
public class PackingPageJackwolfskin extends BasePackingPage {

    @Override
    public String getMainTemplate() {
        return PRINT_TEMPLATE_BASE_PATH + "packing_list_jackwolfskin_main.jasper";
    }

    @Override
    public String getSubTemplate() {
        return PRINT_TEMPLATE_BASE_PATH + "packing_list_jackwolfskin_detail.jasper";
    }

    @Override
    public void setUserDefined(Map<String, Object> map) {}

    @Override
    public boolean isDetialShowLocation() {
        return true;
    }
}
