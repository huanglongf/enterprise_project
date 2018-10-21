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

import loxia.dao.Sort;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.manager.warehouse.TransactionTypeManager;
import com.jumbo.wms.manager.warehouse.WareHouseLocationManager;
import com.jumbo.wms.model.warehouse.WarehouseDistrict;

/**
 * 库位作业类型设定
 * 
 * @author wanghua
 * 
 */
public class WarehouseLocationTranTypeMaintainAction extends BaseJQGridProfileAction {


    /**
	 * 
	 */
    private static final long serialVersionUID = 5308302660409061018L;
    @Autowired
    private WareHouseLocationManager wareHouseLocationManager;
    @Autowired
    private TransactionTypeManager transManager;
    /**
     * 创建/修改的model
     */
    private WarehouseDistrict district;
    private Long districtId;
    private List<Long> locationIds;
    private List<Long> transIds;
    private Long locationId;

    // 库位作业类型设定
    public String locationTranMaintainEntry() {
        return SUCCESS;
    }

    public String locationsTransEntry() {
        return SUCCESS;
    }

    public String locationVolume() {
        return SUCCESS;
    }

    /**
     * 库位列表
     * 
     * @return
     */
    public String locationTransList() {
        setTableConfig();
        if (null != district && null != locationId) {
            request.put(JSON, toJson(wareHouseLocationManager.findLocationsTransCountListByDistrictAndLocId(tableConfig.getStart(), tableConfig.getPageSize(), district.getId(), locationId, tableConfig.getSorts())));
        } else {
            request.put(JSON, toJson(wareHouseLocationManager.findLocationsTransCountListByDistrictId(tableConfig.getStart(), tableConfig.getPageSize(), district.getId(), tableConfig.getSorts())));
        }
        return JSON;
    }

    /**
     * 库位绑定的作业类型列表
     * 
     * @return
     */
    public String locationTransDetail() {
        request.put(JSON, JsonUtil.collection2json((transManager.findTransactionTypeList(userDetails.getCurrentOu(), district.getId(), new Sort[] {new Sort("t.name asc")}))));
        return JSON;
    }

    /**
     * 作业类型
     * 
     * @return
     */
    public String transList() {
        setTableConfig();
        request.put(JSON, toJson(transManager.findTransactionTypeList(userDetails.getCurrentOu(), null, tableConfig.getSorts())));
        return JSON;
    }

    public String bindTrans() throws Exception {
        if (locationIds == null || locationIds.isEmpty() || transIds == null || transIds.isEmpty()) {

        } else {
            wareHouseLocationManager.createTransactionTypeForLocations(locationIds, transIds);
        }
        request.put(JSON, new JSONObject().put("msg", SUCCESS));
        return JSON;
    }

    public String unbindTrans() throws Exception {
        if (locationIds == null || locationIds.isEmpty() || transIds == null || transIds.isEmpty()) {

        } else {
            wareHouseLocationManager.deleteTransactionTypeForLocations(locationIds, transIds);
        }
        request.put(JSON, new JSONObject().put("msg", SUCCESS));
        return JSON;
    }

    /**
     * 根据库区绑定库位的作业类型
     * 
     * @return
     * @throws JSONException
     */
    public String bindDistrict() throws JSONException {
        if (districtId == null || transIds == null || transIds.isEmpty()) {

        } else {
            wareHouseLocationManager.createTransactionTypeByDistrict(districtId, transIds);
        }
        request.put(JSON, new JSONObject().put("msg", SUCCESS));
        return JSON;
    }

    /**
     * 根据库区取消库位作业类型
     * 
     * @return
     * @throws JSONException
     */
    public String unbindDistrict() throws JSONException {
        if (districtId == null || transIds == null || transIds.isEmpty()) {

        } else {
            wareHouseLocationManager.deleteTransactionTypeByDistrict(districtId, transIds);
        }
        request.put(JSON, new JSONObject().put("msg", SUCCESS));
        return JSON;
    }

    public WarehouseDistrict getDistrict() {
        return district;
    }

    public void setDistrict(WarehouseDistrict district) {
        this.district = district;
    }

    public List<Long> getLocationIds() {
        return locationIds;
    }

    public void setLocationIds(List<Long> locationIds) {
        this.locationIds = locationIds;
    }

    public List<Long> getTransIds() {
        return transIds;
    }

    public void setTransIds(List<Long> transIds) {
        this.transIds = transIds;
    }

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }


}
