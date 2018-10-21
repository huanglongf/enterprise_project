package com.jumbo.wms.model.warehouse;

import com.jumbo.wms.model.baseinfo.ChannelWhRef;

public class ChannelWhRefCommand extends ChannelWhRef {
    
    private static final long serialVersionUID = -1397980602704546866L;
    private Long id;
    private Long channelId;
    private Long ouId;
    private Integer isDefaultWh;
    private Integer isPostPage;
    private Integer isPostBill;
    @Override
    public Long getId() {
        return id;
    }
    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public Long getOuId() {
        return ouId;
    }

    public void setOuId(Long ouId) {
        this.ouId = ouId;
    }

    public Integer getIsDefaultWh() {
        return isDefaultWh;
    }

    public void setIsDefaultWh(Integer isDefaultWh) {
        this.isDefaultWh = isDefaultWh;
    }

    public Integer getIsPostPage() {
        return isPostPage;
    }

    public void setIsPostPage(Integer isPostPage) {
        this.isPostPage = isPostPage;
    }

    public Integer getIsPostBill() {
        return isPostBill;
    }

    public void setIsPostBill(Integer isPostBill) {
        this.isPostBill = isPostBill;
    }
    
}
