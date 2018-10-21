package com.jumbo.webservice.ems.command.New;

public class Address {


    private String name;// 用户姓名
    private String postCode;// 用户邮编
    private String phone;// 用户电话，包括区号、电话号码及分机号，中间用“-”分隔；
    private String mobile;// 用户电话，包括区号、电话号码及分机号，中间用“-”分隔；
    private String prov;// 用户所在省，使用国标全称
    private String city;// 用户所在市，使用国标全称
    private String county;// 用户所在县（区），使用国标全称
    private String address;// 用户详细地址


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getProv() {
        return prov;
    }

    public void setProv(String prov) {
        this.prov = prov;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
