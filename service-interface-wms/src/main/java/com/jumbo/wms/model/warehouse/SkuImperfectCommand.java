package com.jumbo.wms.model.warehouse;

public class SkuImperfectCommand extends SkuImperfect {

    /**
     * 
     */
    private static final long serialVersionUID = 8352524426714827889L;
    private String jmCode;
    private String barCode;
    private String skuCode;
    private String supplierCode;
    private String size;
    private String skuName;
    private String staCode;
    private Integer staType;
    private String cartonCode;
    private String endTime;
    private String createDate;
    private String refSlipCode;
    private String sn;
    private String userName;
    private String code;
    private Long lineQty;


    public Long getLineQty() {
        return lineQty;
    }

    public void setLineQty(Long lineQty) {
        this.lineQty = lineQty;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }



    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRefSlipCode() {
        return refSlipCode;
    }

    public void setRefSlipCode(String refSlipCode) {
        this.refSlipCode = refSlipCode;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }


    public Integer getStaType() {
        return staType;
    }

    public void setStaType(Integer staType) {
        this.staType = staType;
    }

    public String getCartonCode() {
        return cartonCode;
    }

    public void setCartonCode(String cartonCode) {
        this.cartonCode = cartonCode;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }



    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getJmCode() {
        return jmCode;
    }

    public void setJmCode(String jmCode) {
        this.jmCode = jmCode;
    }


}
