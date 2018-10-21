package com.jumbo.manager.channel.packingPage;

import java.util.Map;
import org.springframework.stereotype.Service;


@Service("packingPageMicrosoft")
public class PackingPageMicrosoft extends BasePackingPage {
    @Override
    public String getMainTemplate() {
        return PRINT_TEMPLATE_BASE_PATH + "trunk_main_for_microsoft.jasper";
    }

    @Override
    public String getSubTemplate() {
        return PRINT_TEMPLATE_BASE_PATH + "trunk_detail_for_microsoft.jasper";
    }

    @Override
    public void setUserDefined(Map<String, Object> map) {
        map.put("logoImg", "print_img/microsoft_logo.jpg");
        map.put("subReport", getSubTemplate());
    }

    @Override
    public boolean isDetialShowLocation() {
        return true;
    }

}
