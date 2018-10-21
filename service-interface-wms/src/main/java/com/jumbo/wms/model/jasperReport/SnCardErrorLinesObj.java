package com.jumbo.wms.model.jasperReport;

import java.io.Serializable;

/**
 * 激活失败卡信息bin.hu
 * 
 * @author jumbo
 * 
 */
public class SnCardErrorLinesObj implements Serializable {

    private static final long serialVersionUID = -4589805723604943738L;

    private String location;

    private String barCode;

    private String sn;

    private String sn1;

    private String qty;

    private String skuName;

    private int ordinal;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getSn1() {
        return sn1;
    }

    public void setSn1(String sn1) {
        this.sn1 = sn1;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }


}
