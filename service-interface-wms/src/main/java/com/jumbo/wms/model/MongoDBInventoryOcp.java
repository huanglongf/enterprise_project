package com.jumbo.wms.model;

/**
 * 
 * @author xiaolong.fei
 * 
 */
public class MongoDBInventoryOcp extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 2187300453931314255L;
    /*
     * SKUID
     */
    private Long skuId;
    /*
     * 仓库组织ID
     */
    private Long ouId;
    /*
     * 渠道编码
     */
    private String owner;

    /**
     * 区域编码
     */
    private String zoonCode;
    /*
     * 可用库存
     */
    private Integer availQty;

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Long getOuId() {
        return ouId;
    }

    public void setOuId(Long ouId) {
        this.ouId = ouId;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Integer getAvailQty() {
        return availQty;
    }

    public void setAvailQty(Integer availQty) {
        this.availQty = availQty;
    }

    public String getZoonCode() {
        return zoonCode;
    }

    public void setZoonCode(String zoonCode) {
        this.zoonCode = zoonCode;
    }

    @Override
    public String toString() {
        return "MongoDBInventoryOcp [skuId=" + skuId + ", ouId=" + ouId + ", owner=" + owner + ", zoonCode=" + zoonCode + ", availQty=" + availQty + "]";
    }

}
