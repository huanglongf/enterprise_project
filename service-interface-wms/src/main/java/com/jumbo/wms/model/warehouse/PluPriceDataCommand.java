package com.jumbo.wms.model.warehouse;

import com.jumbo.wms.model.vmi.itData.PluPriceData;

public class PluPriceDataCommand extends PluPriceData {

    /**
	 * 
	 */
    private static final long serialVersionUID = -355281445646290385L;

    private String productCode;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

}
