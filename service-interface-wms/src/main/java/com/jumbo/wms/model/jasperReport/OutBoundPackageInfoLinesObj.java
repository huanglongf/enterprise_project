package com.jumbo.wms.model.jasperReport;

import java.io.Serializable;

public class OutBoundPackageInfoLinesObj implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = -6052473938911994682L;
    
    private int ordinal;
    private String seQno;// 箱号
    private String code;// 箱号编码
    private String barCode;// 商品条形码
    private String supplierCode;// 货号属性
    private Double completeQty;// 总执行量
    private Double qty;// 单个执行量
    private String skuSize;//尺码


    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    
    public Double getQty() {
        return qty;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    public Double getCompleteQty() {
        return completeQty;
    }

    public void setCompleteQty(Double completeQty) {
        this.completeQty = completeQty;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    public String getSeQno() {
        return seQno;
    }

    public void setSeQno(String seQno) {
        this.seQno = seQno;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getSkuSize() {
        return skuSize;
    }

    public void setSkuSize(String skuSize) {
        this.skuSize = skuSize;
    }

}
