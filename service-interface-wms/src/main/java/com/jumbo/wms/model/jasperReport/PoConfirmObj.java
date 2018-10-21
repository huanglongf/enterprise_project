package com.jumbo.wms.model.jasperReport;

import java.io.Serializable;
import java.math.BigDecimal;

public class PoConfirmObj implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 3096774160829999055L;
    private Integer index;
    private String skuName;
    private String skuCode;
    private String brand;
    private String barCode;
    private BigDecimal planQty;
    private BigDecimal totalQty;
    private Integer quantity;
    private String unitName;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public BigDecimal getPlanQty() {
        return planQty;
    }

    public void setPlanQty(BigDecimal planQty) {
        this.planQty = planQty;
    }

    public BigDecimal getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(BigDecimal totalQty) {
        this.totalQty = totalQty;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
}
