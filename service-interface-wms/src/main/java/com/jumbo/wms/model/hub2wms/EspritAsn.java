package com.jumbo.wms.model.hub2wms;

import com.baozun.bizhub.model.BaseModel;

public class EspritAsn extends BaseModel {



    /**
     * 
     */
    private static final long serialVersionUID = 553026071522983942L;

    private String sku;

    private Long qty;

    private String cartonNo;

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public String getCartonNo() {
        return cartonNo;
    }

    public void setCartonNo(String cartonNo) {
        this.cartonNo = cartonNo;
    }

}
