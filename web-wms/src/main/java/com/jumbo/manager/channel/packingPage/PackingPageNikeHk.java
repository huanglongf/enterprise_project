package com.jumbo.manager.channel.packingPage;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jumbo.wms.manager.channel.ChannelManager;
import com.jumbo.wms.model.jasperReport.PackingObj;
import com.jumbo.wms.model.jasperReport.PrintPackingPageParam;


@Service("packingPageNikeHk")
public class PackingPageNikeHk extends BasePackingPage {
    @Autowired
    private ChannelManager channelManager;

    @Override
    public String getMainTemplate() {
        return PRINT_TEMPLATE_BASE_PATH + "trunk_main_for_nikehk.jasper";
    }

    @Override
    public String getSubTemplate() {
        return PRINT_TEMPLATE_BASE_PATH + "trunk_detail_for_nikehk.jasper";
    }

    @Override
    public void setUserDefined(Map<String, Object> map) {
        map.put("subReport", getSubTemplate());
    }

    @Override
    public boolean isDetialShowLocation() {
        return true;
    }

    @Override
    public boolean isSpecialCustom() {
        return true;
    }

    @Override
    public Map<Long, PackingObj> getSpecialCustomValue(PrintPackingPageParam param) {
        return channelManager.getNikeHKSpecialCustomValue(param.getPlId(), param.getStaId());
    }

}
