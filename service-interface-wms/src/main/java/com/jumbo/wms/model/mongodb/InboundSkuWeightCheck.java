package com.jumbo.wms.model.mongodb;

import java.util.Date;
import java.util.List;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.command.SkuCommand;

public class InboundSkuWeightCheck extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 3951983444849183589L;

    private Long id;

    private List<SkuCommand> skuList;

    private Long staId;

    private Long ouId;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<SkuCommand> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<SkuCommand> skuList) {
        this.skuList = skuList;
    }

    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

    public Long getOuId() {
        return ouId;
    }

    public void setOuId(Long ouId) {
        this.ouId = ouId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }



}
