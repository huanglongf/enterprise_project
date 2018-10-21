package com.jumbo.wms.model.mongodb;

import java.util.Date;



public class StaCartonLineSnCommand extends StaCartonLineSn {

    /**
     * 
     */
    private static final long serialVersionUID = 4282071661073108102L;

    private Long cId;
    private Long skuId;
    private Date expDate;

    public Long getcId() {
        return cId;
    }

    public void setcId(Long cId) {
        this.cId = cId;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

}
