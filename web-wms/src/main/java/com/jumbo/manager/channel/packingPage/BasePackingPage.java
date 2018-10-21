package com.jumbo.manager.channel.packingPage;

import java.util.Map;

import com.jumbo.wms.model.jasperReport.PackingObj;
import com.jumbo.wms.model.jasperReport.PrintPackingPageParam;


public class BasePackingPage implements PackingPageInterface {

    @Override
    public String getPostpositionalMainTemplate() {
        return PRINT_TEMPLATE_BASE_PATH + "packing_list_mainp.jasper";
    }

    @Override
    public String getPostpositionalSubTemplate() {
        return PRINT_TEMPLATE_BASE_PATH + "packing_list_detailp.jasper";
    }


    @Override
    public String getMainTemplate() {
        return PRINT_TEMPLATE_BASE_PATH + "packing_list_main_default.jasper";
    }

    @Override
    public String getSubTemplate() {
        return PRINT_TEMPLATE_BASE_PATH + "packing_list_detail_default.jasper";
    }


    @Override
    public void setUserDefined(Map<String, Object> map) {
        map.put("subReport", getSubTemplate());
    }

    /*
     * @Override public void setUserDefined(Map<String, Object> map) { map.put("subReport",
     * "jasperprint/zi.jasper"); }
     */

    @Override
    public boolean isDetialShowLocation() {
        return true;
    }

    @Override
    public boolean isDetialShowSN() {
        return true;
    }

	@Override
	public boolean isSpecialCustom() {
		return false;
	}

	@Override
	public Map<Long, PackingObj> getSpecialCustomValue(PrintPackingPageParam param) {
		return null;
	}
}
