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

import java.util.Date;

/**
 * 
 * 库位占用库存信息COMMAND
 * 
 * @author jumbo
 * 
 */
public class InventoryOccupyCommand extends Inventory {

    private static final long serialVersionUID = 4407697185256558795L;
    /**
     * 库区ID
     */
    private Long districtId;
    /**
     * 库位ID
     */
    private Long locationId;
    /**
     * 仓库组织ID
     */
    private Long ouId;
    /**
     * 商品ID
     */
    private Long skuId;
    /**
     * 库存状态ID
     */
    private Long statusId;
    /**
     * 计划占用总数
     */
    private Long planOccupyQty;
    /**
     * sta Owner
     */
    private String orderOwner;

    private String lineKey;
    
    private Date expDate;

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

    public Long getOuId() {
        return ouId;
    }

    public void setOuId(Long ouId) {
        this.ouId = ouId;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public Long getPlanOccupyQty() {
        return planOccupyQty;
    }

    public void setPlanOccupyQty(Long planOccupyQty) {
        this.planOccupyQty = planOccupyQty;
    }

    public String getOrderOwner() {
        return orderOwner;
    }

    public void setOrderOwner(String orderOwner) {
        this.orderOwner = orderOwner;
    }

    public String getLineKey() {
        return lineKey;
    }

    public void setLineKey(String lineKey) {
        this.lineKey = lineKey;
    }

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

}
