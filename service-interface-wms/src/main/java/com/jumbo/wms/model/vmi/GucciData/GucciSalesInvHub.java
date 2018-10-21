package com.jumbo.wms.model.vmi.GucciData;

import java.util.Date;

import com.jumbo.wms.model.BaseModel;

/**
 * Gucci每日同步可销售库存(Hub传输)
 */
public class GucciSalesInvHub extends BaseModel {

    private static final long serialVersionUID = 1998006470402610413L;

    /**
     * 商品upc
     */
    private String skuNumber;

    /**
     * 可销售数量
     */
    private Long invQty;

    /**
     * 库存时间
     */
    private Date invDate;

    /**
     * 库区库位
     */
    private String location;

    /**
     * JDA仓库编码
     */
    private String JDAWarehouseCode;

    /**
     * 品牌编码
     */
    private String brandCode;

    /**
     * 创建时间
     */
    private Date createTime;

    public String getSkuNumber() {
        return skuNumber;
    }

    public void setSkuNumber(String skuNumber) {
        this.skuNumber = skuNumber;
    }

    public Long getInvQty() {
        return invQty;
    }

    public void setInvQty(Long invQty) {
        this.invQty = invQty;
    }

    public Date getInvDate() {
        return invDate;
    }

    public void setInvDate(Date invDate) {
        this.invDate = invDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getJDAWarehouseCode() {
        return JDAWarehouseCode;
    }

    public void setJDAWarehouseCode(String jDAWarehouseCode) {
        JDAWarehouseCode = jDAWarehouseCode;
    }

    public String getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
