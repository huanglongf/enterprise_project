package com.jumbo.wms.model.compensation;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 *  索赔单明细
 *  @author lihui
 */
public class ClaimLine implements Serializable{


    private static final long serialVersionUID = 7713731678215703082L;


    private String skuCode;
    private String skuName;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private int claimQty;
    private BigDecimal claimUnitPrice;
    private BigDecimal claimAmt;
    public String getSkuCode() {
        return skuCode;
    }
    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }
    public String getSkuName() {
        return skuName;
    }
    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getClaimQty() {
        return claimQty;
    }

    public void setClaimQty(int claimQty) {
        this.claimQty = claimQty;
    }

    public BigDecimal getClaimUnitPrice() {
        return claimUnitPrice;
    }

    public void setClaimUnitPrice(BigDecimal claimUnitPrice) {
        this.claimUnitPrice = claimUnitPrice;
    }

    public BigDecimal getClaimAmt() {
        return claimAmt;
    }

    public void setClaimAmt(BigDecimal claimAmt) {
        this.claimAmt = claimAmt;
    }



}
