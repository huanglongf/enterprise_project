package com.jumbo.manager.channel.packingPage;

import java.util.Map;
import org.springframework.stereotype.Service;


@Service("packingPageEsprit")
public class PackingPageESPRIT extends BasePackingPage {

    @Override
    public String getMainTemplate() {
        return PRINT_TEMPLATE_BASE_PATH + "trunk_main_for_sprit_shop.jasper";
    }

    @Override
    public String getSubTemplate() {
        return PRINT_TEMPLATE_BASE_PATH + "trunk_detail_for_sprit_shop.jasper";
    }

    @Override
    public void setUserDefined(Map<String, Object> map) {
        map.put("logoImg", "print_img/sprit_logo.jpg");
    }

    @Override
    public boolean isDetialShowLocation() {
        return false;
    }

}
