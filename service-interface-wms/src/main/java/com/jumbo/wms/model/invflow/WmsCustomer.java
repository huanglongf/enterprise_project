package com.jumbo.wms.model.invflow;

import java.io.Serializable;

/**
 * WMS3.0通知上位系统客户信息
 *
 */
public class WmsCustomer implements Serializable {

    private static final long serialVersionUID = -4477119864514680825L;
    /**
     * 客户编码
     */
    private String customerCode;
    /**
     * 客户名称
     */
    private String customerName;
    /**
     * 描述
     */
    private String description;
    /**
     * 联系人
     */
    private String pic;
    /**
     * 联系人电话
     */
    private String picContact;
    /**
     * 客户类型
     */
    private String customerType;
    /**
     * 发票类型
     */
    private String invoiceType;
    /**
     * 结算方式
     */
    private String paymentTerm;
    /**
     * 联系手机
     */
    private String picMobileTelephone;
    /**
     * 国家名称
     */
    private String countryName;
    /**
     * 国家编码
     */
    private String countryCode;
    /**
     * 省名称
     */
    private String provinceName;
    /**
     * 省编码
     */
    private String provinceCode;
    /**
     * 市名称
     */
    private String cityName;
    /**
     * 市编码
     */
    private String cityCode;
    /**
     * 区名称
     */
    private String districtName;
    /**
     * 区编码
     */
    private String districtCode;
    /**
     * 乡镇/街道名称
     */
    private String villagesTownsName;
    /**
     * 乡镇/街道编码
     */
    private String villagesTownsCode;
    /**
     * 详细地址
     */
    private String address;
    /**
     * 邮政编码
     */
    private String zipCode;
    /**
     * 邮箱
     */
    private String email;

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPicContact() {
        return picContact;
    }

    public void setPicContact(String picContact) {
        this.picContact = picContact;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getPaymentTerm() {
        return paymentTerm;
    }

    public void setPaymentTerm(String paymentTerm) {
        this.paymentTerm = paymentTerm;
    }

    public String getPicMobileTelephone() {
        return picMobileTelephone;
    }

    public void setPicMobileTelephone(String picMobileTelephone) {
        this.picMobileTelephone = picMobileTelephone;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getVillagesTownsName() {
        return villagesTownsName;
    }

    public void setVillagesTownsName(String villagesTownsName) {
        this.villagesTownsName = villagesTownsName;
    }

    public String getVillagesTownsCode() {
        return villagesTownsCode;
    }

    public void setVillagesTownsCode(String villagesTownsCode) {
        this.villagesTownsCode = villagesTownsCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
