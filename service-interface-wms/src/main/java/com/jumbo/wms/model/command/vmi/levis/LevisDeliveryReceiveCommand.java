package com.jumbo.wms.model.command.vmi.levis;

import com.jumbo.wms.model.vmi.levisData.LevisDeliveryReceive;

public class LevisDeliveryReceiveCommand extends LevisDeliveryReceive {
    private static final long serialVersionUID = 3309150872372905740L;

    private String supplierCode;
    private String keyProperties;

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



}
