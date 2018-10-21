package com.jumbo.webservice;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jumbo.wms.model.BaseModel;

/**
 * @author jinlong.ke
 * @date 2016年7月14日下午3:03:24
 * 
 */
public class AdReturnResponse extends BaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = -1485953313793080352L;
    @JsonProperty(value = "StoreExchangeConfirmResponse")
    private StoreExchangeConfirmResponse storeExchangeConfirmResponse;

    public StoreExchangeConfirmResponse getStoreExchangeConfirmResponse() {
        return storeExchangeConfirmResponse;
    }

    public void setStoreExchangeConfirmResponse(StoreExchangeConfirmResponse storeExchangeConfirmResponse) {
        this.storeExchangeConfirmResponse = storeExchangeConfirmResponse;
    }

}
