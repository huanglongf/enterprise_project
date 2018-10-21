package com.jumbo.wms.model;

/**
 * 
 * @author jinlong.ke
 * 
 */
public class MongoDBInventory extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 2187300453931314255L;
    /*
     * SKUID
     */
    private Long skuId;
    /*
     * 仓库编码
     */
    private String whCode;
    /*
     * 渠道编码
     */
    private String owner;
    /*
     * 可用库存
     */
    private Long availQty;

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getWhCode() {
        return whCode;
    }

    public void setWhCode(String whCode) {
        this.whCode = whCode;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Long getAvailQty() {
        return availQty;
    }

    public void setAvailQty(Long availQty) {
        this.availQty = availQty;
    }
}
