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
package com.jumbo.wms.model.warehouse;

import java.util.HashMap;

import com.jumbo.wms.model.BaseModel;

/**
 * 
 * @author jumbo
 * 
 */
public class OccupationInfoCommand extends BaseModel {

    private static final long serialVersionUID = 4575569006266344906L;

    /**
     * 占用数量
     */
    private Long quantity;
    /**
     * 作业单号
     */
    private String code;
    /**
     * 前置业务单号
     */
    private String slipCode;
    /**
     * SKUid
     */
    private Long skuId;
    /**
     * 仓库id
     */
    private Long warehouseId;
    /**
     * 库存状态id
     */
    private Long invStatusId;
    /**
     * 所属店铺信息
     */
    private String owner;
    /**
     * 批次号
     */
    private String batchCode;


    // GETTER && SETTER
    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSlipCode() {
        return slipCode;
    }

    public void setSlipCode(String slipCode) {
        this.slipCode = slipCode;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Long getInvStatusId() {
        return invStatusId;
    }

    public void setInvStatusId(Long invStatusId) {
        this.invStatusId = invStatusId;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public HashMap<String, Object> occupationInfoMap() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("skuId", skuId);
        map.put("warehouseId", warehouseId);
        if (invStatusId != null) {
            map.put("invStatusId", invStatusId);
        }
        if (owner != null && !"".equals(owner)) {
            map.put("owner", owner + "%");
        }
        if (batchCode != null && !"".equals(batchCode)) {
            map.put("batchCode", batchCode + "%");
        }
        return map;
    }
}
/*
 * private Long skuId; private Long warehouseId;
 * 
 * private Long invStatusId; private String owner; private String batchCode;
 */
