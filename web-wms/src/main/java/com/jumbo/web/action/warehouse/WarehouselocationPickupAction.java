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

import loxia.support.json.JSONArray;
import loxia.support.json.JSONException;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.Constants;
import com.jumbo.wms.manager.system.ChooseOptionManager;
import com.jumbo.wms.manager.warehouse.WareHouseLocationManager;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.warehouse.WarehouseDistrict;
import com.jumbo.wms.model.warehouse.WarehouseLocation;
import com.jumbo.web.action.BaseJQGridProfileAction;

/**
 * @author zhl
 * 
 */
public class WarehouselocationPickupAction extends BaseJQGridProfileAction {

    private static final long serialVersionUID = -6561923848716612530L;
    @Autowired
    private WareHouseLocationManager wareHouseLocationManager;
    private List<WarehouseLocation> whLocationList;
    @Autowired
    private ChooseOptionManager chooseOptionManager;


    private List<ChooseOption> pickUpOptionList;
    /**
     * 创建/修改的model
     */
    private WarehouseDistrict district;

    /**
     * 进入库区拣货模式编辑
     * 
     * @return
     */
    public String locationPickupMaintainEntry() {
        pickUpOptionList = chooseOptionManager.findOptionListByCategoryCode(Constants.CHOOSEOPTION_CATEGORY_CODE_WAREHOUSE_PICKUP);
        return SUCCESS;
    }

    public String includeLocationTranstype() {
        return SUCCESS;
    }

    /**
     * 修改库区的拣货模式
     * 
     * @return
     * @throws JSONException
     */
    public String modifyWarehouseLocationListPickupMode() throws JSONException {
        wareHouseLocationManager.updataWarehouseLocationListForSortingMode(whLocationList);
        this.asynReturnTrueValue();
        return JSON;
    }

    /**
     * 库区列表
     * 
     * @return
     */
    public String districtList() {
        request.put(JSON, new JSONArray(wareHouseLocationManager.findDistrictListByOuId(userDetails.getCurrentOu().getId())));
        return JSON;
    }

    public List<ChooseOption> getPickUpOptionList() {
        return pickUpOptionList;
    }

    public void setPickUpOptionList(List<ChooseOption> pickUpOptionList) {
        this.pickUpOptionList = pickUpOptionList;
    }

    public WarehouseDistrict getDistrict() {
        return district;
    }

    public void setDistrict(WarehouseDistrict district) {
        this.district = district;
    }

    public List<WarehouseLocation> getWhLocationList() {
        return whLocationList;
    }

    public void setWhLocationList(List<WarehouseLocation> whLocationList) {
        this.whLocationList = whLocationList;
    }

}
