package com.jumbo.wms.model.vmi.nikeData;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;







public class NikeCheckReceiveCommand extends NikeCheckReceive {

    /**
     * 
     */
    private static final long serialVersionUID = 1964445872183937976L;
    
    private String owner;
    
    private Date endCreateDate;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
    
    public Date getEndCreateDate() {
        return endCreateDate;
    }

    public void setEndCreateDate(Date endCreateDate) {
        this.endCreateDate = endCreateDate;
    }

    public Map<String,Object> getValuesMap(){
        Map<String,Object> map = new HashMap<String, Object>();
        if(this.getCheckCode() != null && this.getCheckCode().trim().length() > 0){
            map.put("checkCode", this.getCheckCode().trim()+"%");
        }
        if(this.getOwner() != null && this.getOwner().trim().length() > 0){
            map.put("owner", this.getOwner().trim()+"%");
        }
        if(this.getOwnerCode() != null && this.getOwnerCode().trim().length() > 0){
            map.put("ownerCode", this.getOwnerCode().trim()+"%");
        }
        if(this.getUpc() != null && this.getUpc().trim().length() > 0){
            map.put("upc", this.getUpc().trim()+"%");
        }
        if(this.getOperator() != null && this.getOperator().trim().length() > 0){
            map.put("operator", this.getOperator().trim()+"%");
        }
        if(this.getStatus() != null){
            map.put("status", this.getStatus());
        }
        if(this.getType() != null){
            map.put("type", this.getType());
        }
        if(this.getManualType() != null){
            map.put("manualType", this.getManualType());
        }
        if(this.getCreateDate() != null){
            map.put("createDate", this.getCreateDate());
        }
        if(endCreateDate != null){
            map.put("endCreateDate", endCreateDate);
        }
        return map;
    }
}
