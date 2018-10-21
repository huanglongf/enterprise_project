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

package com.jumbo.wms.model.odo;

/**
 * 临时库间移动明细表
 * 
 * @author hui.li
 * 
 */
public class OdoLineCommand extends OdoLine {

    /**
     * 
     */
    private static final long serialVersionUID = 1724715924241817448L;

    private String skuBarcode;

    private String invStatusStr;

    private String skuCode;

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getSkuBarcode() {
        return skuBarcode;
    }

    public void setSkuBarcode(String skuBarcode) {
        this.skuBarcode = skuBarcode;
    }

    public String getInvStatusStr() {
        return invStatusStr;
    }

    public void setInvStatusStr(String invStatusStr) {
        this.invStatusStr = invStatusStr;
    }


}
