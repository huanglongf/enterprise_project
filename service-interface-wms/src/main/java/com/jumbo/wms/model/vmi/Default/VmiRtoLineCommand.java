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
package com.jumbo.wms.model.vmi.Default;

/**
 * @author lichuan
 * 
 */
public class VmiRtoLineCommand extends VmiRtoLineDefault {

    private static final long serialVersionUID = -8190373117003478425L;
    
    private String skuCode;
    private String skuBarcode;
    private String skuName;
    private Long num;
    private String slipCode;
    
    public Long getNum() {
		return num;
	}
	public void setNum(Long num) {
		this.num = num;
	}
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
    public String getSkuName() {
        return skuName;
    }
    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getSlipCode() {
        return slipCode;
    }

    public void setSlipCode(String slipCode) {
        this.slipCode = slipCode;
    }
    

}
