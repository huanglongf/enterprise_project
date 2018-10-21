package com.jumbo.rmi.warehouse.threePL;

import java.io.Serializable;

/**
 * 外包仓出库单据发货信息
 * 
 */
public class WmsOutBoundOrderDelivery implements Serializable {

    private static final long serialVersionUID = -4152887036182078169L;


    /**
     * 国家
     */
    private String country;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 区
     */
    private String district;
    /**
     * 送货地址
     */
    private String address;
    /**
     * 联系电话
     */
    private String telephone;
    /**
     * 手机
     */
    private String mobile;
    /**
     * 收货人
     */
    private String receiver;
    /**
     * 物流供应商编码
     */
    private String lpCode;
    /**
     * 快递单号
     */
    private String transNo;

    /**
     * 物流城市编码
     */
    private String cityCode;
    /**
     * 备注
     */
    private String remark;

    /**
     * 邮编
     */
    private String zipcode;

    /**
     * 扩展字段，json格式
     */
    private String extMemo;

    /**
     * 退货单号（nike）
     */
    private String returnTransNo;


    public String getReturnTransNo() {
        return returnTransNo;
    }

    public void setReturnTransNo(String returnTransNo) {
        this.returnTransNo = returnTransNo;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getLpCode() {
        return lpCode;
    }

    public void setLpCode(String lpCode) {
        this.lpCode = lpCode;
    }

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getExtMemo() {
        return extMemo;
    }

    public void setExtMemo(String extMemo) {
        this.extMemo = extMemo;
    }

}
