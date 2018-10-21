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

package com.jumbo.web.action.warehouse;

import java.util.List;

import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.wms.manager.warehouse.WareHouseLocationManager;
import com.jumbo.wms.model.warehouse.WarehouseDistrict;
import com.jumbo.wms.model.warehouse.WarehouseLocation;
import com.jumbo.web.action.BaseJQGridProfileAction;

/**
 * 库位容积设定
 * 
 * @author wanghua
 * 
 */
public class WarehouseLocationCapacityMaintainAction extends BaseJQGridProfileAction {


    /**
	 * 
	 */
    private static final long serialVersionUID = -4238329882457771099L;
    @Autowired
    private WareHouseLocationManager wareHouseLocationManager;
    /**
     * 指定库区的库位
     */
    private WarehouseDistrict district;
    /**
     * 修改的库位
     */
    private List<WarehouseLocation> locations;
    /**
     * 库区List
     */
    private List<WarehouseDistrict> districtList;

    /**
     * 库区库位容积管理
     */
    public String locationCapacityMaintainEntry() {
        return SUCCESS;
    }

    /**
     * Include
     * 
     * @return
     */
    public String locationsForUpdateEntry() {
        return SUCCESS;
    }

    /**
     * 更新库位容积、容积率
     * 
     * @return
     * @throws Exception
     */
    public String updateCapacityOfLocations() throws Exception {
        wareHouseLocationManager.updateCapacityOfLocations(locations);
        request.put(SUCCESS, new JSONObject().put(SUCCESS, SUCCESS));
        return JSON;
    }

    public List<WarehouseDistrict> getDistrictList() {
        return districtList;
    }

    public void setDistrictList(List<WarehouseDistrict> districtList) {
        this.districtList = districtList;
    }

    public WarehouseDistrict getDistrict() {
        return district;
    }

    public void setDistrict(WarehouseDistrict district) {
        this.district = district;
    }

    public List<WarehouseLocation> getLocations() {
        return locations;
    }

    public void setLocations(List<WarehouseLocation> locations) {
        this.locations = locations;
    }

}
