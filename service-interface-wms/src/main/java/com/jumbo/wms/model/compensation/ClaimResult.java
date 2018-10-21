package com.jumbo.wms.model.compensation;

import java.io.Serializable;


/**
 * 索赔结果
 * 
 * @author lihui
 */
public class ClaimResult implements Serializable{

    private static final long serialVersionUID = 5782154544016492254L;

    private String systemCode;
    private int status;
    
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }
    
    
    
}
