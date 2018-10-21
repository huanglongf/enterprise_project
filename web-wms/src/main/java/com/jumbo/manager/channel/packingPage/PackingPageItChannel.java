package com.jumbo.manager.channel.packingPage;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service("packingPageItChannel")
public class PackingPageItChannel extends BasePackingPage{

	 @Override
	    public String getMainTemplate() {
	        return PRINT_TEMPLATE_BASE_PATH + "packing_list_main_itchannel.jasper";
	    }

	    @Override
	    public String getSubTemplate() {
	        return PRINT_TEMPLATE_BASE_PATH + "packing_list_detail_itchannel.jasper";
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
