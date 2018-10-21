package com.jumbo.wms.model.warehouse;

import java.util.Date;

import com.jumbo.wms.model.pda.StaOpLog;

public class StaOpLogCommand extends StaOpLog {

    /**
     * 
     */
    private static final long serialVersionUID = 7264690435013126313L;



    // private Long skuId;

    private String skuCode;

    private String barCode;

    private String name;

    private String supplierCode;

    private String keyProperties;

    private String statusName;

    private Date productionDate;

    // private Date expDate;

    private String userName;


    // private Long qty;



    // private String sn;

    // private String locationCode;



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



    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }



}
