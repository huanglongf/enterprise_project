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

package com.jumbo.wms.model.command;

import com.jumbo.wms.model.BaseModel;

public class SalesReportFormCommand extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = -3591799007507488573L;

    /**
     * 仓库
     */
    private String warehouse;

    /**
     * 店铺
     */
    private String shop;

    /**
     * 过仓订单数量
     */
    private Long toWHOrderQty;

    /**
     * 销售出库数量
     */
    private Long outboundQty;

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public Long getToWHOrderQty() {
        return toWHOrderQty;
    }

    public void setToWHOrderQty(Long toWHOrderQty) {
        this.toWHOrderQty = toWHOrderQty;
    }

    public Long getOutboundQty() {
        return outboundQty;
    }

    public void setOutboundQty(Long outboundQty) {
        this.outboundQty = outboundQty;
    }
}
