package com.jumbo.wms.model.hub2wms.threepl;

import java.io.Serializable;

public class ReceiverInfo implements Serializable {

    private static final long serialVersionUID = -2790102583102867364L;
    /**
     * 收件方邮编
     */
    private String receiverZipCode;
    /**
     * 收件方国家
     */
    private String receiverCountry;
    /**
     * 收件方省份 国际地址可能没有
     */
    private String receiverProvince;
    /**
     * 收件方城市，市与区不会同时为空
     */
    private String receiverCity;
    /**
     * 收件方区县，市与区不会同时为空
     */
    private String receiverArea;
    /**
     * 收件方镇
     */
    private String receiveTown;
    /**
     * 收件方最小区划ID，四级地址ID
     */
    private String receiverDivisionId;
    /**
     * 收件方详情地址，不包含省、市、区、街道信息
     */
    private String receiverAddress;
    /**
     * 收件人名称
     */
    private String receiverName;
    /**
     * 收件人昵称
     */
    private String receiverNick;
    /**
     * 收件人手机，手机和电话不会同时为空
     */
    private String receiverMobile;
    /**
     * 收件人电话，手机和电话不会同时为空
     */
    private String receiverPhone;

    public String getReceiverZipCode() {
        return receiverZipCode;
    }

    public void setReceiverZipCode(String receiverZipCode) {
        this.receiverZipCode = receiverZipCode;
    }

    public String getReceiverCountry() {
        return receiverCountry;
    }

    public void setReceiverCountry(String receiverCountry) {
        this.receiverCountry = receiverCountry;
    }

    public String getReceiverProvince() {
        return receiverProvince;
    }

    public void setReceiverProvince(String receiverProvince) {
        this.receiverProvince = receiverProvince;
    }

    public String getReceiverCity() {
        return receiverCity;
    }

    public void setReceiverCity(String receiverCity) {
        this.receiverCity = receiverCity;
    }

    public String getReceiverArea() {
        return receiverArea;
    }

    public void setReceiverArea(String receiverArea) {
        this.receiverArea = receiverArea;
    }

    public String getReceiveTown() {
        return receiveTown;
    }

    public void setReceiveTown(String receiveTown) {
        this.receiveTown = receiveTown;
    }

    public String getReceiverDivisionId() {
        return receiverDivisionId;
    }

    public void setReceiverDivisionId(String receiverDivisionId) {
        this.receiverDivisionId = receiverDivisionId;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverNick() {
        return receiverNick;
    }

    public void setReceiverNick(String receiverNick) {
        this.receiverNick = receiverNick;
    }

    public String getReceiverMobile() {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

}
