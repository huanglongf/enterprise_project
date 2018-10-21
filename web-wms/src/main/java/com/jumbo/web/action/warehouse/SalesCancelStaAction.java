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

import java.util.ArrayList;
import java.util.List;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerCancel;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;

/**
 * 
 * @author sjk
 * 
 */
public class SalesCancelStaAction extends BaseJQGridProfileAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = 2195458168598237992L;

    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private WareHouseManagerCancel wareHouseManagerCancel;

    private List<Long> staIds;

    private String shopId;
    private StockTransApplication sta;

    /**
     * 销售出库作业 配货清单操作列表
     * 
     * @return
     */
    public String cancelStaEntry() {
        return SUCCESS;
    }

    /**
     * 查询未完成sta
     * 
     * @return
     */

    public String staNoFinishList() {
        setTableConfig();
        request.put(JSON, toJson(wareHouseManager.findSalesCanCancelStaList(tableConfig.getStart(), tableConfig.getPageSize(), shopId, sta, userDetails.getCurrentOu().getId(), tableConfig.getSorts())));
        return JSON;
    }

    /**
     * 取消sta
     * 
     * @return
     * @throws JSONException
     */
    public String cancelSta() throws JSONException {
        List<String> list = new ArrayList<String>();
        JSONObject result = new JSONObject();
        result.put("result", SUCCESS);
        if (staIds != null) {
            for (Long staId : staIds) {
                try {
                	wareHouseManagerCancel.cancelSalesSta(staId, userDetails.getUser().getId());
                } catch (BusinessException e) {
                    result.put("result", ERROR);
                    list.add(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
                }
            }
        }
        result.put("message", JsonUtil.collection2json(list));
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 通过sta查询占用库位信息
     * 
     * @return
     */
    public String findStaLineInfo() {
        setTableConfig();
        Long staId = staIds.get(0);
        request.put(JSON, toJson(wareHouseManager.findStaLineCommandListByStaId(tableConfig.getStart(), tableConfig.getPageSize(), staId, tableConfig.getSorts())));
        return JSON;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public StockTransApplication getSta() {
        return sta;
    }

    public void setSta(StockTransApplication sta) {
        this.sta = sta;
    }

    public List<Long> getStaIds() {
        return staIds;
    }

    public void setStaIds(List<Long> staIds) {
        this.staIds = staIds;
    }

}
