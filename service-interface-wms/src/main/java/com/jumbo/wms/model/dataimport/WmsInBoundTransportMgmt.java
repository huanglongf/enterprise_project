package com.jumbo.wms.model.dataimport;

import com.jumbo.wms.model.BaseModel;

public class WmsInBoundTransportMgmt extends BaseModel {
    private static final long serialVersionUID = -5449839647506944800L;
    /** 运输服务商 */
    private String transportServiceProvider;
    /** 快递单号 */
    private String trackingNumber;
    /** 寄件人姓名 */
    private String senderTargetName;
    /** 寄件人手机 */
    private String senderTargetMobilePhone;
    /** 寄件人固定电话 */
    private String senderTargetTelephone;
    /** 寄件人国家 */
    private String senderTargetCountry;
    /** 寄件人省 */
    private String senderTargetProvince;
    /** 寄件人市 */
    private String senderTargetCity;
    /** 寄件人区 */
    private String senderTargetDistrict;
    /** 寄件人乡镇/街道 */
    private String senderTargetVillagesTowns;
    /** 寄件人详细地址 */
    private String senderTargetAddress;
    /** 寄件人邮箱 */
    private String senderTargetEmail;
    /** 寄件人邮编 */
    private String senderTargetZip;

    public String getTransportServiceProvider() {
        return transportServiceProvider;
    }

    public void setTransportServiceProvider(String transportServiceProvider) {
        this.transportServiceProvider = transportServiceProvider;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getSenderTargetName() {
        return senderTargetName;
    }

    public void setSenderTargetName(String senderTargetName) {
        this.senderTargetName = senderTargetName;
    }

    public String getSenderTargetMobilePhone() {
        return senderTargetMobilePhone;
    }

    public void setSenderTargetMobilePhone(String senderTargetMobilePhone) {
        this.senderTargetMobilePhone = senderTargetMobilePhone;
    }

    public String getSenderTargetTelephone() {
        return senderTargetTelephone;
    }

    public void setSenderTargetTelephone(String senderTargetTelephone) {
        this.senderTargetTelephone = senderTargetTelephone;
    }

    public String getSenderTargetCountry() {
        return senderTargetCountry;
    }

    public void setSenderTargetCountry(String senderTargetCountry) {
        this.senderTargetCountry = senderTargetCountry;
    }

    public String getSenderTargetProvince() {
        return senderTargetProvince;
    }

    public void setSenderTargetProvince(String senderTargetProvince) {
        this.senderTargetProvince = senderTargetProvince;
    }

    public String getSenderTargetCity() {
        return senderTargetCity;
    }

    public void setSenderTargetCity(String senderTargetCity) {
        this.senderTargetCity = senderTargetCity;
    }

    public String getSenderTargetDistrict() {
        return senderTargetDistrict;
    }

    public void setSenderTargetDistrict(String senderTargetDistrict) {
        this.senderTargetDistrict = senderTargetDistrict;
    }

    public String getSenderTargetVillagesTowns() {
        return senderTargetVillagesTowns;
    }

    public void setSenderTargetVillagesTowns(String senderTargetVillagesTowns) {
        this.senderTargetVillagesTowns = senderTargetVillagesTowns;
    }

    public String getSenderTargetAddress() {
        return senderTargetAddress;
    }

    public void setSenderTargetAddress(String senderTargetAddress) {
        this.senderTargetAddress = senderTargetAddress;
    }

    public String getSenderTargetEmail() {
        return senderTargetEmail;
    }

    public void setSenderTargetEmail(String senderTargetEmail) {
        this.senderTargetEmail = senderTargetEmail;
    }

    public String getSenderTargetZip() {
        return senderTargetZip;
    }

    public void setSenderTargetZip(String senderTargetZip) {
        this.senderTargetZip = senderTargetZip;
    }

}
