package com.jumbo.wms.model.mongodb;

import java.util.Date;
import java.util.List;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.command.SkuCommand;

public class MongDbNoThreeDimensional extends BaseModel{

    /**
     * 
     */
    private static final long serialVersionUID = -9019571601819929680L;

    private Long pinkingListId;

    private String pinkingCode;

    public String getPinkingCode() {
        return pinkingCode;
    }

    public void setPinkingCode(String pinkingCode) {
        this.pinkingCode = pinkingCode;
    }

    private List<SkuCommand> skuList;

    private String store;

    private Long ouId;

    private Date createTime;

    public Long getPinkingListId() {
        return pinkingListId;
    }

    public void setPinkingListId(Long pinkingListId) {
        this.pinkingListId = pinkingListId;
    }


    public List<SkuCommand> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<SkuCommand> skuList) {
        this.skuList = skuList;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
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
