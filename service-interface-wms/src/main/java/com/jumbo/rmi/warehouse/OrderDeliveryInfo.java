package com.jumbo.rmi.warehouse;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * 接口订单配送信息
 * 
 * @author jinlong.ke
 * 
 */
public class OrderDeliveryInfo implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -6307907635624331118L;
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
     * 电话
     */
    private String telephone;
    /**
     * 手机
     */
    private String mobile;
    /**
     * 收获人
     */
    private String receiver;
    /**
     * 邮编
     */
    private String zipcode;
    /**
     * 是否货到付款
     */
    private Boolean isCod;
    /**
     * 物流商简称
     */
    private String lpCode;
    /**
     * 运单号
     */
    private String trackingNo;
    /**
     * 发货备注
     */
    private String remark;
    /**
     * 7 入库备注
     */
    private String inboundRemark;
    /**
     * 运送方式(快递附加服务)，销售相关单据必填默认普通 1,普通 4,空运 6,陆运 7,电商特惠
     */
    private Integer transType;
    /**
     * 快递时间限制（快递附加服务）销售相关单据必填默认普通 1,普通 5,当日 6,次日
     */
    private Integer transTimeType;
    /**
     * 运单备注信息
     */
    private String transMemo;
    /**
     * 下单用户邮箱
     */
    private String orderUserMail;
    /**
     * 下单用户帐号
     */
    private String orderUserCode;
    /**
     * 退货发件人
     */
    private String sender;
    /**
     * 退货物流商
     */
    private String sendLpcode;
    /**
     * 退货人手机
     */
    private String sendMobile;
    /**
     * 退货运单号
     */
    private String sendTransNo;
    /**
     * 保价金额
     */
    private BigDecimal insuranceAmount;
    private String addressEn;
    private String cityEn;
    private String countryEn;
    private String districtEn;
    private String provinceEn;
    private String receiverEn;
    private String remarkEn;

    /**
     * 是否POS机刷卡
     */
    private Boolean isCodPos;
    private String convenienceStore;

    public Boolean getIsCodPos() {
        return isCodPos;
    }

    public void setIsCodPos(Boolean isCodPos) {
        this.isCodPos = isCodPos;
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

    public BigDecimal getInsuranceAmount() {
        return insuranceAmount;
    }

    public void setInsuranceAmount(BigDecimal insuranceAmount) {
        this.insuranceAmount = insuranceAmount;
    }



    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSendLpcode() {
        return sendLpcode;
    }

    public void setSendLpcode(String sendLpcode) {
        this.sendLpcode = sendLpcode;
    }

    public String getSendMobile() {
        return sendMobile;
    }

    public void setSendMobile(String sendMobile) {
        this.sendMobile = sendMobile;
    }

    public String getSendTransNo() {
        return sendTransNo;
    }

    public void setSendTransNo(String sendTransNo) {
        this.sendTransNo = sendTransNo;
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

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public Boolean getIsCod() {
        return isCod;
    }

    public void setIsCod(Boolean isCod) {
        this.isCod = isCod;
    }

    public String getLpCode() {
        return lpCode;
    }

    public void setLpCode(String lpCode) {
        this.lpCode = lpCode;
    }

    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getInboundRemark() {
        return inboundRemark;
    }

    public void setInboundRemark(String inboundRemark) {
        this.inboundRemark = inboundRemark;
    }

    public String getConvenienceStore() {
        return convenienceStore;
    }

    public void setConvenienceStore(String convenienceStore) {
        this.convenienceStore = convenienceStore;
    }

}
