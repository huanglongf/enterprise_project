package com.jumbo.wms.model.command.vmi.levis;

import java.math.BigDecimal;

import com.jumbo.wms.model.vmi.levisData.LevisStkr;

public class LevisStkrCommand extends LevisStkr {
    private static final long serialVersionUID = -3509673829373285091L;

    private String supplierSkuCode;
    private String keyprop;
    private Long qty;
    private BigDecimal listPrice;

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public BigDecimal getListPrice() {
        return listPrice;
    }

    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
    }

    public String getSupplierSkuCode() {
        return supplierSkuCode;
    }

    public void setSupplierSkuCode(String supplierSkuCode) {
        this.supplierSkuCode = supplierSkuCode;
    }

    public String getKeyprop() {
        return keyprop;
    }

    public void setKeyprop(String keyprop) {
        this.keyprop = keyprop;
    }


}
