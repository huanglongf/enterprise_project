package com.jumbo.mq;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 订单平台会员信息
 * 
 * @author dzz
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "mqplatformmembermsg")
public class MqPlatformMemberMsg implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = -2544496003922113369L;

    /**
     * 会员帐号
     */
    private String loginName;

    /**
     * 会员真实姓名
     */
    private String realName;

    /**
     * 性别
     */
    private String gender;

    /**
     * 出生日期
     */
    private String birthday;

    /**
     * 联系电话
     */
    private String telephone;

    /**
     * 联系手机
     */
    private String mobile;

    /**
     * 会员邮箱
     */
    private String email;

    /**
     * 所在国家
     */
    private String country;

    /**
     * 所在省
     */
    private String province;

    /**
     * 所在市
     */
    private String city;

    /**
     * 邮编
     */
    private String zipCode;

    /**
     * 详细地址
     */
    private String address;

    /**
     * vip会员卡号
     */
    private String vipCode;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getVipCode() {
        return vipCode;
    }

    public void setVipCode(String vipCode) {
        this.vipCode = vipCode;
    }


}
