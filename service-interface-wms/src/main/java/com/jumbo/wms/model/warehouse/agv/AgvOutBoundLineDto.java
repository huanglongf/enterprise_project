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

package com.jumbo.wms.model.warehouse.agv;


/**
 * AGV出库明细中间表DTO
 * 
 * @author lzb
 * 
 */
public class AgvOutBoundLineDto extends AgvOutBoundLine {

    private static final long serialVersionUID = -9221025981940402217L;

    private Long fulfillQuantity;// 实际拣货数量
    
    private String skuCode;

    private String expireDateStr;


    public Long getFulfillQuantity() {
        return fulfillQuantity;
    }

    public void setFulfillQuantity(Long fulfillQuantity) {
        this.fulfillQuantity = fulfillQuantity;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getExpireDateStr() {
        return expireDateStr;
    }

    public void setExpireDateStr(String expireDateStr) {
        this.expireDateStr = expireDateStr;
    }

}
