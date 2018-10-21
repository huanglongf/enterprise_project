package com.jumbo.wms.model.mongodb;

import java.util.Date;

import com.jumbo.wms.model.BaseModel;

public class AdOutBoundCheck extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = -7763049186960422305L;

    private Long id;

    private String staCode;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }



}
