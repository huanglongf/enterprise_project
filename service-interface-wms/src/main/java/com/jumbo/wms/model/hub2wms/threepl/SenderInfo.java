package com.jumbo.wms.model.hub2wms.threepl;

import java.io.Serializable;

public class SenderInfo implements Serializable {

    private static final long serialVersionUID = 3345163516978255576L;
    /**
     * 发件方邮编
     */
    private String senderZipCode;
    /**
     * 发件方国家
     */
    private String senderCountry;
    /**
     * 发件方省份 国际地址可能没有
     */
    private String senderProvince;
    /**
     * 发件方城市
     */
    private String senderCity;
    /**
     * 发件方区县
     */
    private String senderArea;
    /**
     * 发件方镇
     */
    private String senderTown;
    /**
     * 发件方地址
     */
    private String senderAddress;
    /**
     * 发件方最小区划ID
     */
    private String senderDivisionId;
    /**
     * 发件方名称（采购入库放供应商名称）， （销退入库填买家名称）， （调拨入库填写调拨出库仓库联系人名称）
     */
    private String senderName;
    /**
     * 发件方手机,手机和电话不能同时为空
     */
    private String senderMobile;
    /**
     * 发件方电话，手机和电话不能同时为空
     */
    private String senderPhone;

    public String getSenderZipCode() {
        return senderZipCode;
    }

    public void setSenderZipCode(String senderZipCode) {
        this.senderZipCode = senderZipCode;
    }

    public String getSenderCountry() {
        return senderCountry;
    }

    public void setSenderCountry(String senderCountry) {
        this.senderCountry = senderCountry;
    }

    public String getSenderProvince() {
        return senderProvince;
    }

    public void setSenderProvince(String senderProvince) {
        this.senderProvince = senderProvince;
    }

    public String getSenderCity() {
        return senderCity;
    }

    public void setSenderCity(String senderCity) {
        this.senderCity = senderCity;
    }

    public String getSenderArea() {
        return senderArea;
    }

    public void setSenderArea(String senderArea) {
        this.senderArea = senderArea;
    }

    public String getSenderTown() {
        return senderTown;
    }

    public void setSenderTown(String senderTown) {
        this.senderTown = senderTown;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    public String getSenderDivisionId() {
        return senderDivisionId;
    }

    public void setSenderDivisionId(String senderDivisionId) {
        this.senderDivisionId = senderDivisionId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderMobile() {
        return senderMobile;
    }

    public void setSenderMobile(String senderMobile) {
        this.senderMobile = senderMobile;
    }

    public String getSenderPhone() {
        return senderPhone;
    }

    public void setSenderPhone(String senderPhone) {
        this.senderPhone = senderPhone;
    }

}
