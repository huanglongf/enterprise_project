package com.lmis.pos.whs.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class WhsDepotVo implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 8267648558637618463L;
    /**
     * 仓库id
     */
    private String id;
    /**
     * 仓库编码
     */
    private String whsCode;
    /**
     * 仓名
     */
    private String whsName;
    /**
     * 到期后延期天数
     */
    private BigDecimal arrivalLeadDays;
    public BigDecimal getArrivalLeadDays() {
        return arrivalLeadDays;
    }
    public void setArrivalLeadDays(BigDecimal arrivalLeadDays) {
        this.arrivalLeadDays = arrivalLeadDays;
    }
    
    public String getId(){
        return id;
    }
    
    public void setId(String id){
        this.id = id;
    }
    
    public String getWhsCode(){
        return whsCode;
    }
    
    public void setWhsCode(String whsCode){
        this.whsCode = whsCode;
    }
    
    public String getWhsName(){
        return whsName;
    }
    
    public void setWhsName(String whsName){
        this.whsName = whsName;
    }
    
    
    
}
