
package com.jumbo.wms.model.warehouse.O2oEsprit;

import java.io.Serializable;

/**
 * 
 * @author lzb
 * 
 */

public class EspDeliveryItem implements Serializable {

    private static final long serialVersionUID = 6592192511578092338L;

    /** 商品编码(商品表的extCode2) */
    private String sku;
    
    /** 当前的出库量 */
    private int receivedQty;

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public int getReceivedQty() {
        return receivedQty;
    }

    public void setReceivedQty(int receivedQty) {
        this.receivedQty = receivedQty;
    }
}
