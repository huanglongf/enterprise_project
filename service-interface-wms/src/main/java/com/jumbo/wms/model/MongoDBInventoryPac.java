package com.jumbo.wms.model;

/**
 * 
 * @author cheng.su
 * 
 */
public class MongoDBInventoryPac extends BaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = 4818852637724554245L;
    /*
     * SKUID
     */
    private String skuCode;
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
    /*
     * ext_code2
     */
    private String extCode;


    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
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

    public String getExtCode() {
        return extCode;
    }

    public void setExtCode(String extCode) {
        this.extCode = extCode;
    }

}
