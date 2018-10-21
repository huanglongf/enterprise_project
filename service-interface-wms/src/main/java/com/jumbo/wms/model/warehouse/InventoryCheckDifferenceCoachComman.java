package com.jumbo.wms.model.warehouse;

import java.io.Serializable;


public class InventoryCheckDifferenceCoachComman implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 432952889290155401L;

	private String skuCode;

    private String quantity;

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
