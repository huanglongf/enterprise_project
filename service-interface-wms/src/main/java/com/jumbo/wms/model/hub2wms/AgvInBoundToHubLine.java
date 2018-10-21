package com.jumbo.wms.model.hub2wms;

import java.util.HashMap;
import java.util.Map;

import com.baozun.bizhub.model.BaseModel;

public class AgvInBoundToHubLine extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 7889839626044486917L;
    
    private String boxNo;
    
    private String skuCode;
    
    private Long quantity;
    
    private String ownerCode;
    
    private Long skuId;
    
    private Map<String,String>batchProperties=new HashMap<String,String>();

    
    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getBoxNo() {
        return boxNo;
    }

    public void setBoxNo(String boxNo) {
        this.boxNo = boxNo;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    public Map<String, String> getBatchProperties() {
        return batchProperties;
    }

    public void setBatchProperties(Map<String, String> batchProperties) {
        this.batchProperties = batchProperties;
    }
    
}
