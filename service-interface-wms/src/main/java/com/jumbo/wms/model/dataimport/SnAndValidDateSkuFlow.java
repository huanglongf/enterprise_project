package com.jumbo.wms.model.dataimport;

import com.jumbo.wms.model.BaseModel;

public class SnAndValidDateSkuFlow extends BaseModel {

    private static final long serialVersionUID = -5708768056693690746L;
    /**
     * staId
     */
    private Long staId;

    /**
     * skuId
     */
    private Long skuId;

    /**
     * 平台单据号
     */
    private String slipCode1 = "";

    /**
     * 店铺名称
     */
    private String shopName;

    /**
     * 商品编码
     */
    private String skuCode;

    /**
     * 过期时间
     */
    private String expDate = "";

    /**
     * sn号
     */
    private String sn = "";

    /**
     * 数量
     */
    private Long qty;

    /**
     * 是否sn
     */
    private Boolean isSn = false;

    /**
     * 商品存放模式(是否效期的判断依据)
     */
    private String storeMode;

    public String getSlipCode1() {
        return slipCode1;
    }

    public void setSlipCode1(String slipCode1) {
        if ("NULL".equals(slipCode1) || slipCode1 == null) {
            this.slipCode1 = "";
        } else {
            this.slipCode1 = slipCode1;
        }
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        if ("NULL".equals(expDate) || expDate == null) {
            this.expDate = "";
        } else {
            this.expDate = expDate;
        }
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        if ("NULL".equals(sn) || sn == null) {
            this.sn = "";
        } else {
            this.sn = sn;
        }
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Boolean getIsSn() {
        if (isSn == null) {
            return false;
        } else {
            return isSn;
        }
    }

    public void setIsSn(Boolean isSn) {
        if (isSn == null) {
            this.isSn = false;
        }
        this.isSn = isSn;
    }

    public String getStoreMode() {
        return storeMode;
    }

    public void setStoreMode(String storeMode) {
        this.storeMode = storeMode;
    }

    @Override
    public String toString() {
        return slipCode1 + "," + shopName + "," + skuCode + "," + expDate + "," + sn + "," + qty;
    }
}
