/**
 * Copyright (c) 2015 Jumbomart All Rights Reserved.
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
package com.jumbo.wms.model.warehouse.O2oEsprit;

import java.io.Serializable;
import java.util.Date;

/**
 * lzb
 */
public class EspCommand implements Serializable {

    private static final long serialVersionUID = -5840977729529985849L;

    /** 门店的extCode */
    private String extCode;
    
    /** 门店的extProperties */
    private String extProperties;
    
    /** 对应商品销售出库量 */
    private int receivedQty;
    
    /** item_sku表中的ext_code2字段 */
    private String skuCode;
    
    /** 订单配货成功时间 */
    private Date deliTime;

    public String getExtCode() {
        return extCode;
    }

    public void setExtCode(String extCode) {
        this.extCode = extCode;
    }

    public String getExtProperties() {
        return extProperties;
    }

    public void setExtProperties(String extProperties) {
        this.extProperties = extProperties;
    }

    public int getReceivedQty() {
        return receivedQty;
    }

    public void setReceivedQty(int receivedQty) {
        this.receivedQty = receivedQty;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public Date getDeliTime() {
        return deliTime;
    }

    public void setDeliTime(Date deliTime) {
        this.deliTime = deliTime;
    }

    
}
