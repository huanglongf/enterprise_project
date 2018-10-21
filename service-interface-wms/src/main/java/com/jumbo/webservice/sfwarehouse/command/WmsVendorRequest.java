package com.jumbo.webservice.sfwarehouse.command;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author jinlong.ke
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "wmsVendorRequest")
public class WmsVendorRequest implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -4350563604412597197L;
    @XmlElement
    private String checkword;// <checkword>校验字段</checkword>
    @XmlElement
    private String company;// <company>货主</company>
    @XmlElement
    private String vendor;// <vendor>供应商编码</vendor>
    @XmlElement
    private String vendor_name;// <vendor_name>供应商名称</vendor_name>
    @XmlElement
    private String attention_to;// <attention_to>联系人</attention_to>
    @XmlElement
    private String phone_num;// <phone_num>电话</phone_num>
    @XmlElement
    private String address;// <address>地址</address>
    @XmlElement
    private String postal_code;// <postal_code>邮编</postal_code>
    @XmlElement
    private String fax_num;// <fax_num>传真</fax_num>
    @XmlElement
    private String state;// <state>省份</state>
    @XmlElement
    private String city;// <city>城市</city>
    @XmlElement
    private String country;// <country>国家</country>
    @XmlElement
    private String email_address;// <email_address>邮件地址</email_address>
    @XmlElement
    private String user_def1;// <user_def1>预留字段</user_def1>
    @XmlElement
    private String user_def2;// <user_def2>预留字段</user_def2>
    @XmlElement
    private String user_def3;// <user_def3>预留字段</user_def3>
    @XmlElement
    private String user_def4;// <user_def4>预留字段</user_def4>
    @XmlElement
    private String user_def5;// <user_def5>预留字段</user_def5>
    @XmlElement
    private String user_def6;// <user_def6>预留字段</user_def6>
    @XmlElement
    private BigDecimal user_def7;// <user_def7>预留字段</user_def7>
    @XmlElement
    private BigDecimal user_def8;// <user_def8>预留字段</user_def8>
    @XmlElement
    private String interface_action_code;// <interface_action_code>接口动作</interface_action_code>

    public String getCheckword() {
        return checkword;
    }

    public void setCheckword(String checkword) {
        this.checkword = checkword;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getVendor_name() {
        return vendor_name;
    }

    public void setVendor_name(String vendorName) {
        vendor_name = vendorName;
    }

    public String getAttention_to() {
        return attention_to;
    }

    public void setAttention_to(String attentionTo) {
        attention_to = attentionTo;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phoneNum) {
        phone_num = phoneNum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postalCode) {
        postal_code = postalCode;
    }

    public String getFax_num() {
        return fax_num;
    }

    public void setFax_num(String faxNum) {
        fax_num = faxNum;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail_address() {
        return email_address;
    }

    public void setEmail_address(String emailAddress) {
        email_address = emailAddress;
    }

    public String getUser_def1() {
        return user_def1;
    }

    public void setUser_def1(String userDef1) {
        user_def1 = userDef1;
    }

    public String getUser_def2() {
        return user_def2;
    }

    public void setUser_def2(String userDef2) {
        user_def2 = userDef2;
    }

    public String getUser_def3() {
        return user_def3;
    }

    public void setUser_def3(String userDef3) {
        user_def3 = userDef3;
    }

    public String getUser_def4() {
        return user_def4;
    }

    public void setUser_def4(String userDef4) {
        user_def4 = userDef4;
    }

    public String getUser_def5() {
        return user_def5;
    }

    public void setUser_def5(String userDef5) {
        user_def5 = userDef5;
    }

    public String getUser_def6() {
        return user_def6;
    }

    public void setUser_def6(String userDef6) {
        user_def6 = userDef6;
    }

    public BigDecimal getUser_def7() {
        return user_def7;
    }

    public void setUser_def7(BigDecimal userDef7) {
        user_def7 = userDef7;
    }

    public BigDecimal getUser_def8() {
        return user_def8;
    }

    public void setUser_def8(BigDecimal userDef8) {
        user_def8 = userDef8;
    }

    public String getInterface_action_code() {
        return interface_action_code;
    }

    public void setInterface_action_code(String interfaceActionCode) {
        interface_action_code = interfaceActionCode;
    }
}
