package com.jumbo.webservice;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jumbo.wms.model.BaseModel;

/**
 * @author jinlong.ke
 * @date 2016年7月14日下午3:03:24
 * 
 */
public class AdResponse extends BaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = -1485953313793080352L;
    @JsonProperty(value = "StoreLogisticsSendResponse")
    private StoreLogisticsSendResponse storeLogisticsSendResponse;

    public StoreLogisticsSendResponse getStoreLogisticsSendResponse() {
        return storeLogisticsSendResponse;
    }

    public void setStoreLogisticsSendResponse(StoreLogisticsSendResponse storeLogisticsSendResponse) {
        this.storeLogisticsSendResponse = storeLogisticsSendResponse;
    }
}
