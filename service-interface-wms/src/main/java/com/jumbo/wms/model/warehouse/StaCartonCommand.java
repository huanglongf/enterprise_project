package com.jumbo.wms.model.warehouse;

import java.util.Date;

import com.jumbo.wms.model.mongodb.StaCarton;

public class StaCartonCommand extends StaCarton {

    /**
     * 
     */
    private static final long serialVersionUID = -6167438940468172752L;

    private String cartonCode;

    private Long skuId;

    private String skuCode;

    private String barCode;

    private String name;

    private String supplierCode;

    private String keyProperties;

    private String statusName;

    private Date productionDate;

    private Date expDate;

    private String userName;


    private Long qty;

    private boolean isSnSku;

    private String dmgType; // 残次类型

    private String dmgReason; // 残次原因

    private String dmgCode; // 条码

    private String sn;

    private String locationCode;


    public String getLocationCode() {
        return locationCode;
    }


    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }



    public boolean isSnSku() {
        return isSnSku;
    }


    public void setSnSku(boolean isSnSku) {
        this.isSnSku = isSnSku;
    }


    public String getSn() {
        return sn;
    }


    public void setSn(String sn) {
        this.sn = sn;
    }


    public String getDmgType() {
        return dmgType;
    }


    public void setDmgType(String dmgType) {
        this.dmgType = dmgType;
    }


    public String getDmgReason() {
        return dmgReason;
    }


    public void setDmgReason(String dmgReason) {
        this.dmgReason = dmgReason;
    }


    public String getDmgCode() {
        return dmgCode;
    }


    public void setDmgCode(String dmgCode) {
        this.dmgCode = dmgCode;
    }


    public Long getSkuId() {
        return skuId;
    }


    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }



    public String getCartonCode() {
        return cartonCode;
    }


    public void setCartonCode(String cartonCode) {
        this.cartonCode = cartonCode;
    }


    public String getSkuCode() {
        return skuCode;
    }


    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }


    public String getBarCode() {
        return barCode;
    }


    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getSupplierCode() {
        return supplierCode;
    }


    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }


    public String getKeyProperties() {
        return keyProperties;
    }


    public void setKeyProperties(String keyProperties) {
        this.keyProperties = keyProperties;
    }


    public String getStatusName() {
        return statusName;
    }


    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }


    public Date getProductionDate() {
        return productionDate;
    }


    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }


    public Date getExpDate() {
        return expDate;
    }


    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }


    public String getUserName() {
        return userName;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }


    public Long getQty() {
        return qty;
    }


    public void setQty(Long qty) {
        this.qty = qty;
    }



}
