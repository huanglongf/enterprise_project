package com.jumbo.wms.model.command;

import com.jumbo.wms.model.baseinfo.SkuWarehouseRef;

public class SkuWarehouseRefCommand extends SkuWarehouseRef {


    private static final long serialVersionUID = -1336794542711515859L;

    private String brandName; // 品牌名
    private Long brandId; //
    private String sourceWh; // 外包仓库编码
    private String channelName; // 店铺
    private Long channelId; // 店铺Id
    private String biannelSource; // 外包仓店铺编码

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getSourceWh() {
        return sourceWh;
    }

    public void setSourceWh(String sourceWh) {
        this.sourceWh = sourceWh;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public String getBiannelSource() {
        return biannelSource;
    }

    public void setBiannelSource(String biannelSource) {
        this.biannelSource = biannelSource;
    }

}
