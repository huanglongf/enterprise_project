package com.jumbo.wms.model.compensation;

import java.io.Serializable;


/**
 * 索赔处理确认
 * 
 * @author lihui
 */
public class ClaimProcessAffirm implements Serializable{


    /**
     * 
     */
    private static final long serialVersionUID = 1775040746336529206L;


    private String systemCode;
    private int status;// 0:索赔单作废；1:索赔确认
    


    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    
}
