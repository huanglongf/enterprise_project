/**
 * Copyright (c) 2010 Jumbomart All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Jumbomart. You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jumbo.
 * 
 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 * 
 */

package com.jumbo.web.action;

import java.util.ArrayList;
import java.util.List;

import loxia.dao.Sort;
import loxia.support.json.JSONArray;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.jumbo.wms.manager.DropDownListManager;
import com.jumbo.wms.manager.baseinfo.BaseinfoManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.model.baseinfo.District;
import com.jumbo.util.JsonUtil;

public class DropdownListAction extends BaseProfileAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = 8371921989994716591L;

    @Autowired
    private DropDownListManager dropDownListManager;
    @Autowired
    private BaseinfoManager baseinfoManager;
    @Autowired
    private WareHouseManager warehouseManager;
    private String sortString;
    private String province;
    private String city;

    public String inventoryStatus() throws Exception {
        Sort[] sorts = null;
        if (StringUtils.hasLength(sortString)) {
            List<Sort> list = new ArrayList<Sort>();
            for (String sort : sortString.split(",")) {
                list.add(new Sort(sort));
            }
            sorts = list.toArray(new Sort[list.size()]);
        }
        request.put(JSON, new JSONArray(dropDownListManager.findInvStatusListByCompany(warehouseManager.findCompanyOUByOperationsCenterId(userDetails.getCurrentOu().getParentUnit().getId()).getId(), sorts)));
        return JSON;
    }

    public String getSortString() {
        return sortString;
    }

    public void setSortString(String sortString) {
        this.sortString = sortString;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
    
    public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	/**
     * 根据省市加载城市
     * 
     * @return
     */
    public String loadCityByProvince() throws JSONException {
        List<District> cityList = baseinfoManager.findCity(province);
        if (cityList == null) {
            cityList = new ArrayList<District>();
        }
        JSONObject result = new JSONObject();
        result.put("result", SUCCESS);
        result.put("list", JsonUtil.collection2json(cityList));
        request.put(JSON, result);
        return JSON;
    }
    
    /**
     * 根据城市加载区
     * 
     * @return
     */
	public String loadDistrictsByCity() throws JSONException {
		List<District> districtList = baseinfoManager.findDistrictsByCity(province, city);
		if (districtList == null) {
			districtList = new ArrayList<District>();
		}
		JSONObject result = new JSONObject();
		result.put("result", SUCCESS);
		result.put("list", JsonUtil.collection2json(districtList));
		request.put(JSON, result);
		return JSON;
	}

}
