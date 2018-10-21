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

import java.math.BigDecimal;
import java.util.List;

import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.model.command.SkuCommand;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.web.action.BaseJQGridProfileAction;

public class SkuGroupCreateAction extends BaseJQGridProfileAction {
    private static final long serialVersionUID = -5481633704998014541L;
    @Autowired
    private WareHouseManager wareHouseManager;

    private String skuCode;

    private Long souceSkuId;
    private Long qty;
    private List<SkuCommand> skuList;
    private Long invStsId;

    private Long shopId;

    private Boolean isGroup;

    private BigDecimal skuCost;

    private List<InventoryStatus> invStatusList;

    private String locCode;

    public String skuGroupCreateEntry() {
        invStatusList = wareHouseManager.findInvStatusByOuid(userDetails.getCurrentOu().getParentUnit().getParentUnit().getId());
        return SUCCESS;
    }

    /**
     * 查询商品成本
     * 
     * @return
     * @throws JSONException
     */
    public String findSkuCost() throws JSONException {
        JSONObject result = new JSONObject();
        Long cmpId = userDetails.getCurrentOu().getParentUnit().getParentUnit().getId();
        SkuCommand sku = wareHouseManager.findSkuCostByCode(skuCode, cmpId);
        if (sku == null) {
            log.debug("sku is null");
            result.put(RESULT, ERROR);
        } else {
            log.debug("sku id : {} , cost : {}", sku.getId(), sku.getSkuCost());
            result.put(RESULT, SUCCESS);
            result.put("skuId", sku.getId());
            result.put("skuCost", sku.getSkuCost() == null ? 0 : sku.getSkuCost());
        }
        request.put(JSON, result);
        return JSON;
    }

    public String createSkuGroup() throws JSONException {
        JSONObject result = new JSONObject();
        String errorMsg = "";
        try {
            wareHouseManager.createSkuGroup(skuCode, qty, shopId, invStsId, locCode, skuCost, skuList, userDetails.getUser(), userDetails.getCurrentOu().getId(), isGroup);
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            result.put("result", ERROR);
            if (e.getErrorCode() > 0) {
                errorMsg = this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs());
            }
            BusinessException current = e;
            while (current.getLinkedException() != null) {
                current = current.getLinkedException();
                errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + current.getErrorCode(), current.getArgs());
            }
            result.put("message", errorMsg);
        }
        request.put(JSON, result);
        return JSON;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public Long getSouceSkuId() {
        return souceSkuId;
    }

    public void setSouceSkuId(Long souceSkuId) {
        this.souceSkuId = souceSkuId;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public List<SkuCommand> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<SkuCommand> skuList) {
        this.skuList = skuList;
    }

    public Long getInvStsId() {
        return invStsId;
    }

    public void setInvStsId(Long invStsId) {
        this.invStsId = invStsId;
    }

    public List<InventoryStatus> getInvStatusList() {
        return invStatusList;
    }

    public void setInvStatusList(List<InventoryStatus> invStatusList) {
        this.invStatusList = invStatusList;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Boolean getIsGroup() {
        return isGroup;
    }

    public void setIsGroup(Boolean isGroup) {
        this.isGroup = isGroup;
    }

    public String getLocCode() {
        return locCode;
    }

    public void setLocCode(String locCode) {
        this.locCode = locCode;
    }

    public BigDecimal getSkuCost() {
        return skuCost;
    }

    public void setSkuCost(BigDecimal skuCost) {
        this.skuCost = skuCost;
    }

}
