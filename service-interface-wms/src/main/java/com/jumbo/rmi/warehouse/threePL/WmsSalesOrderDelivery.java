package com.jumbo.rmi.warehouse.threePL;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 销售出库单据发货信息
 * 
 */
public class WmsSalesOrderDelivery implements Serializable {

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
     * 详细地址
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
     * 快递单号
     */
    private String returnTransNo;

    /**
     * 物流城市编码
     */
    private String cityCode;

    /**
     * 是否货到付款
     */
    private Boolean isCod;

    /**
     * 运送方式(快递附加服务)，销售相关单据必填默认普通 1,普通 4,空运 6,陆运 7,特惠
     */
    private Integer transType;

    /**
     * 快递时间限制（快递附加服务）销售相关单据必填默认普通 1,普通 3,及时达 5,当日 6,次日
     */
    private Integer transTimeType;


    /**
     * 运单备注信息
     */
    private String transMemo;

    /**
     * 保价金额
     */
    private BigDecimal insuranceAmount;

    /**
     * 邮编
     */
    private String zipcode;
    /**
     * 英文地址
     */
    private String addressEn;
    /**
     * 英文国家
     */
    private String countryEn;
    /**
     * 英文省
     */
    private String provinceEn;
    /**
     * 英文市
     */
    private String cityEn;
    /**
     * 英文区
     */
    private String districtEn;
    /**
     * 英文收货人
     */
    private String receiverEn;
    /**
     * 英文备注
     */
    private String remarkEn;

    /**
     * 扩展字段，json格式
     */
    private String extMemo;

    /**
     * 备注
     */
    private String remark;

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

    public Boolean getIsCod() {
        return isCod;
    }

    public void setIsCod(Boolean isCod) {
        this.isCod = isCod;
    }

    public Integer getTransType() {
        return transType;
    }

    public void setTransType(Integer transType) {
        this.transType = transType;
    }

    public Integer getTransTimeType() {
        return transTimeType;
    }

    public void setTransTimeType(Integer transTimeType) {
        this.transTimeType = transTimeType;
    }

    public String getTransMemo() {
        return transMemo;
    }

    public void setTransMemo(String transMemo) {
        this.transMemo = transMemo;
    }

    public BigDecimal getInsuranceAmount() {
        return insuranceAmount;
    }

    public void setInsuranceAmount(BigDecimal insuranceAmount) {
        this.insuranceAmount = insuranceAmount;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getAddressEn() {
        return addressEn;
    }

    public void setAddressEn(String addressEn) {
        this.addressEn = addressEn;
    }

    public String getCountryEn() {
        return countryEn;
    }

    public void setCountryEn(String countryEn) {
        this.countryEn = countryEn;
    }

    public String getProvinceEn() {
        return provinceEn;
    }

    public void setProvinceEn(String provinceEn) {
        this.provinceEn = provinceEn;
    }

    public String getCityEn() {
        return cityEn;
    }

    public void setCityEn(String cityEn) {
        this.cityEn = cityEn;
    }

    public String getDistrictEn() {
        return districtEn;
    }

    public void setDistrictEn(String districtEn) {
        this.districtEn = districtEn;
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

    public String getExtMemo() {
        return extMemo;
    }

    public void setExtMemo(String extMemo) {
        this.extMemo = extMemo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getReturnTransNo() {
        return returnTransNo;
    }

    public void setReturnTransNo(String returnTransNo) {
        this.returnTransNo = returnTransNo;
    }


}
