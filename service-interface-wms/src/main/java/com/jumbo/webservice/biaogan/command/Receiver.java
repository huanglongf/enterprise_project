package com.jumbo.webservice.biaogan.command;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Receiver")
public class Receiver implements Serializable {

    private static final long serialVersionUID = -4469007558765132764L;

    /**
     * 收货人
     */
    private String name = "";

    /**
     * 邮编
     */
    private String postcode = "";

    /**
     * 电话
     */
    private String phone = "";

    /**
     * 手机
     */
    private String mobile = "";

    /**
     * 国家
     */
    private String country = "";

    /**
     * 省
     */
    private String prov = "";

    /**
     * 市
     */
    private String city = "";

    /**
     * 区
     */
    private String district = "";

    /**
     * 送货地址
     */
    private String address = "";


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProv() {
        return prov;
    }

    public void setProv(String prov) {
        this.prov = prov;
    }



}
