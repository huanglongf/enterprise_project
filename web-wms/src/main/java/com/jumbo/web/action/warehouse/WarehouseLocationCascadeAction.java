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
import loxia.support.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.model.warehouse.WarehouseLocation;
import com.jumbo.wms.model.warehouse.WarehouseLocationCommand;
import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;

/**
 * 
 * @author jumbo
 * 
 */
public class WarehouseLocationCascadeAction extends BaseJQGridProfileAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = 6401331234869555404L;
    @Autowired
    private WareHouseManager wareHouseManager;

    private String locationCode;
    private Long stvid;

    private Long locationId;
    private Long transactionId;
    /**
     * 级联查询
     */
    private String term;
    private Long skuId;

    /**
     * 查询有效的、未锁定的库位
     * 
     * @return
     * @throws JSONException
     */
    public String findAllAvailLocations() throws JSONException {
        JSONArray ja = new JSONArray();
        List<WarehouseLocation> list = wareHouseManager.findAvailLocations(userDetails.getCurrentOu().getId(), term);
        java.util.Iterator<WarehouseLocation> it = list.iterator();
        while (it.hasNext()) {
            WarehouseLocation w = it.next();
            JSONObject jo = new JSONObject();
            jo.put("id", w.getId());
            jo.put("label", w.getCode());
            jo.put("value", w.getCode());
            ja.put(jo);
        }
        request.put(JSON, ja);
        return JSON;
    }

    /**
     * 采购 库间移动 预定义 库内移动 退换货 ： 上架 ： 库位级联 + 校验
     */

    /**
     * 校验此库位是否支持此作业类型
     */
    public String findLocationTranstypeBycode() throws JSONException {
        JSONObject obj = new JSONObject();
        try {
            WarehouseLocationCommand loc = wareHouseManager.checkLocationTranstypeByCode(locationCode, stvid, userDetails.getCurrentOu().getId());
            obj.put("result", SUCCESS);
            obj.put("location", JsonUtil.obj2json(loc));
        } catch (BusinessException e) {
            obj.put("result", ERROR);
            obj.put("message", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, obj);
        return JSON;
    }


    /**
     * 库位编码校验
     * 
     * @return
     * @throws JSONException
     */
    public String findLocationByCode() throws JSONException {
        if (!StringUtils.hasText(locationCode)) return null;
        JSONObject obj = new JSONObject();
        try {
            // 是否存在库位
            WarehouseLocation whlocation = wareHouseManager.findLocationByCode(locationCode, this.userDetails.getCurrentOu().getId());
            if (whlocation != null) {
                obj.put("result", SUCCESS);
                obj.put("whlocation", JsonUtil.obj2json(whlocation));
            } else {
                obj.put("result", ERROR);
            }
        } catch (BusinessException e) {
            obj.put("result", ERROR);
            obj.put("message", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, obj);
        return JSON;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Long getStvid() {
        return stvid;
    }

    public void setStvid(Long stvid) {
        this.stvid = stvid;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }
}
