package com.jumbo.wms.model.wmsInterface;

import java.io.Serializable;

/**
 * WMS通用出库物流相关信息
 *
 */
public class WmsOutBoundDeliveryInfo implements Serializable {

    private static final long serialVersionUID = -2218004949040826734L;

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
     * 电话
     */
    private String telephone;
    /**
     * 手机 
     */
    private String moblie;
    /**
     * 收货人
     */
    private String receiver;
    /**
     * 邮编
     */
    private String zipcode;
    /**
     * 下单用户邮箱
     */
    private String orderUserMail;
    /**
     * 下单用户编码
     */
    private String orderUserCode;
    /**
     * 物流商简称
     */
    private String transCode;
    /**
     * 物流商编码
     */
    private String lpCode;
    /**
     * 运单号
     */
    private String transNo;
    /**
     * 是否cod订单
     */
    private Boolean isCod;
    /**
     * 是否POS机付款
     */
    private Boolean isCodPos;
    /**
     * 物流时效 
     */
    private Integer transTimeType;
    /**
     * 物流服务
     */
    private Integer transType;
    /**
     * 面单备注
     */
    private String transMemo;

    private String addressEn;
    private String cityEn;
    private String countryEn;
    private String districtEn;
    private String provinceEn;
    private String receiverEn;
    private String remarkEn;

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

    public String getMoblie() {
        return moblie;
    }

    public void setMoblie(String moblie) {
        this.moblie = moblie;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getOrderUserMail() {
        return orderUserMail;
    }

    public void setOrderUserMail(String orderUserMail) {
        this.orderUserMail = orderUserMail;
    }

    public String getOrderUserCode() {
        return orderUserCode;
    }

    public void setOrderUserCode(String orderUserCode) {
        this.orderUserCode = orderUserCode;
    }

    public String getTransCode() {
        return transCode;
    }

    public void setTransCode(String transCode) {
        this.transCode = transCode;
    }

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    public Boolean getIsCod() {
        return isCod;
    }

    public void setIsCod(Boolean isCod) {
        this.isCod = isCod;
    }

    public Boolean getIsCodPos() {
        return isCodPos;
    }

    public void setIsCodPos(Boolean isCodPos) {
        this.isCodPos = isCodPos;
    }

    public Integer getTransTimeType() {
        return transTimeType;
    }

    public void setTransTimeType(Integer transTimeType) {
        this.transTimeType = transTimeType;
    }

    public Integer getTransType() {
        return transType;
    }

    public void setTransType(Integer transType) {
        this.transType = transType;
    }

    public String getTransMemo() {
        return transMemo;
    }

    public void setTransMemo(String transMemo) {
        this.transMemo = transMemo;
    }

    public String getAddressEn() {
        return addressEn;
    }

    public void setAddressEn(String addressEn) {
        this.addressEn = addressEn;
    }

    public String getCityEn() {
        return cityEn;
    }

    public void setCityEn(String cityEn) {
        this.cityEn = cityEn;
    }

    public String getCountryEn() {
        return countryEn;
    }

    public void setCountryEn(String countryEn) {
        this.countryEn = countryEn;
    }

    public String getDistrictEn() {
        return districtEn;
    }

    public void setDistrictEn(String districtEn) {
        this.districtEn = districtEn;
    }

    public String getProvinceEn() {
        return provinceEn;
    }

    public void setProvinceEn(String provinceEn) {
        this.provinceEn = provinceEn;
    }

    public String getReceiverEn() {
        return receiverEn;
    }

    public void setReceiverEn(String receiverEn) {
        this.receiverEn = receiverEn;
    }

    public String getRemarkEn() {
        return remarkEn;
    }

    public void setRemarkEn(String remarkEn) {
        this.remarkEn = remarkEn;
    }

    public String getLpCode() {
        return lpCode;
    }

    public void setLpCode(String lpCode) {
        this.lpCode = lpCode;
    }


}
