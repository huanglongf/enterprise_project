package com.jumbo.manager.channel.packingPage;

import java.util.Map;
import org.springframework.stereotype.Service;


@Service("packingPageAmazon")
public class PackingPageAmazon extends BasePackingPage {

    @Override
    public String getMainTemplate() {
        return PRINT_TEMPLATE_BASE_PATH + "amazon_main.jasper";
    }

    @Override
    public String getSubTemplate() {
        return PRINT_TEMPLATE_BASE_PATH + "amazon_detail.jasper";
    }

    @Override
    public void setUserDefined(Map<String, Object> map) {
        map.put("subReport", getSubTemplate());
        map.put("logImage", "print_img/amazon_logo.jpg");
    }

    @Override
    public boolean isDetialShowLocation() {
        return true;
    }
}
