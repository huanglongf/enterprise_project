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
 */
package com.jumbo.wms.model.vmi.espData;

/**
 * @author lichuan
 * 
 */
public class ESPDeliveryReceiveCommand extends ESPDeliveryReceive {

    private static final long serialVersionUID = -1475892865569365847L;
    private String extCode2;
    private String skuQty;

    public String getExtCode2() {
        return extCode2;
    }

    public void setExtCode2(String extCode2) {
        this.extCode2 = extCode2;
    }

    public String getSkuQty() {
        return skuQty;
    }

    public void setSkuQty(String skuQty) {
        this.skuQty = skuQty;
    }


}
