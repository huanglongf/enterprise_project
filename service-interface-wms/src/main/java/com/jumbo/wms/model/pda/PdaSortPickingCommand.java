package com.jumbo.wms.model.pda;

import java.io.Serializable;
import java.util.Date;

public class PdaSortPickingCommand implements Serializable {

    private static final long serialVersionUID = 8544984342205871317L;

    private String code;

    private String pickingCode;

    private String pickingZoonCode;

    private String skuName;

    private String skuCode;

    private String supplierCode;

    private String keyProperties;

    private String barCode;

    private Long planQty;

    private Long qty;

    private Long sortQty;

    private Date executionTime;

    private String loginName;

    private String locCode;

    public String getPickingCode() {
        return pickingCode;
    }

    public void setPickingCode(String pickingCode) {
        this.pickingCode = pickingCode;
    }

    public String getPickingZoonCode() {
        return pickingZoonCode;
    }

    public void setPickingZoonCode(String pickingZoonCode) {
        this.pickingZoonCode = pickingZoonCode;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
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

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public Long getPlanQty() {
        return planQty;
    }

    public void setPlanQty(Long planQty) {
        this.planQty = planQty;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public Long getSortQty() {
        return sortQty;
    }

    public void setSortQty(Long sortQty) {
        this.sortQty = sortQty;
    }

    public Date getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(Date executionTime) {
        this.executionTime = executionTime;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLocCode() {
        return locCode;
    }

    public void setLocCode(String locCode) {
        this.locCode = locCode;
    }

}
