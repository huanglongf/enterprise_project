package com.jumbo.wms.model.vmi.af;

import com.jumbo.wms.model.BaseModel;

public class AFinventoryCommand extends BaseModel{

    /**
     * 
     */
    private static final long serialVersionUID = 1682186618093917149L;
    
    
    private String storeCode;
    
    private String currentDate;
    
    private String upc;
    
    private String onHandUnits;
    
    private String damaged;

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String getOnHandUnits() {
        return onHandUnits;
    }

    public void setOnHandUnits(String onHandUnits) {
        this.onHandUnits = onHandUnits;
    }

    public String getDamaged() {
        return damaged;
    }

    public void setDamaged(String damaged) {
        this.damaged = damaged;
    }
    
    


}
