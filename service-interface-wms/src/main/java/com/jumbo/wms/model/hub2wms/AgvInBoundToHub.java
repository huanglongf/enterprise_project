package com.jumbo.wms.model.hub2wms;

import java.util.List;
import java.util.Map;

import com.baozun.bizhub.model.BaseModel;

public class AgvInBoundToHub extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = -646108614327062487L;
    
    private String billNumber;
    
    private String billType;
    
    private String billDate;
    
    private String remark;
    
    private Map<String,String> extendedProperties;
    
    private List<AgvInBoundToHubLine> details;

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Map<String, String> getExtendedProperties() {
        return extendedProperties;
    }

    public void setExtendedProperties(Map<String, String> extendedProperties) {
        this.extendedProperties = extendedProperties;
    }

    public List<AgvInBoundToHubLine> getDetails() {
        return details;
    }

    public void setDetails(List<AgvInBoundToHubLine> details) {
        this.details = details;
    }
    
}
