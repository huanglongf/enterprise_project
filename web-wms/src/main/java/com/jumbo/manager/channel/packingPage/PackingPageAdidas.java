package com.jumbo.manager.channel.packingPage;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jumbo.wms.manager.channel.ChannelManager;
import com.jumbo.wms.model.jasperReport.PackingObj;
import com.jumbo.wms.model.jasperReport.PrintPackingPageParam;

@Service("packingPageAdidas")
public class PackingPageAdidas extends BasePackingPage {
	
	@Autowired
	private ChannelManager channelManager; 
	
    @Override
    public String getPostpositionalMainTemplate() {
        return PRINT_TEMPLATE_BASE_PATH + "packing_list_adidas_main.jasper";
    }

    @Override
    public String getPostpositionalSubTemplate() {
        return PRINT_TEMPLATE_BASE_PATH + "packing_list_adidas_detail.jasper";
    }

    @Override
    public String getMainTemplate() {
        return PRINT_TEMPLATE_BASE_PATH + "packing_list_adidas_main.jasper";
    }

    @Override
    public String getSubTemplate() {
        return PRINT_TEMPLATE_BASE_PATH + "packing_list_adidas_detail.jasper";
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
		return channelManager.getAdidasSpecialCustomValue(param.getPlId(),  param.getStaId());
	}
}
