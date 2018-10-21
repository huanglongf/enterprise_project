package com.jumbo.manager.channel.packingPage;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service("packingPageEmporioArmani")
public class PackingPageEmporioArmani extends BasePackingPage{
    
    @Override
    public String getMainTemplate() {
        return PRINT_TEMPLATE_BASE_PATH + "packing_list_armani_main.jasper";
    }

    @Override
    public String getSubTemplate() {
        return PRINT_TEMPLATE_BASE_PATH + "packing_list_armani_detail.jasper";
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
