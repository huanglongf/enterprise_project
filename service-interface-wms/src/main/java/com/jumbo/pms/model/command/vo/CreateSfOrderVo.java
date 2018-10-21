package com.jumbo.pms.model.command.vo;

import java.io.Serializable;

/**
 * 物流下单信息    
 */
public class CreateSfOrderVo implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4436732580198740734L;
	/** 快件产品类别 1 标准快递 */
    public static final String expressType_1 = "1";
    /** 快件产品类别 2 顺丰特惠 */
    public static final String expressType_2 = "2";
    /** 快件产品类别 3 电商特惠 */
    public static final String expressType_3 = "3";
    /** 快件产品类别 5 次晨达 */
    public static final String expressType_5 = "5";
    /** 快件产品类别 6 当日达 */
    public static final String expressType_6 = "6";

    /**
	 * 快件产品类别 快件产品类别: 1 标准快递 2 顺丰特惠 3 电商特惠 5 次晨达 6 当日达
	 */
	private String expressType;
	
    /**
     * 付款方式：1:寄方付 2:收方付 3:第三方付
     */
    private String payMethod;
    
    /**
     * 收货人
     */
    private String dContact;
    
    /**
     * 收货方 - 电话
     */
    private String dTel;

    /**
     * 收货方 – 手机
     */
    private String dMobile;
    
    /**
     * 收货方 – 国家
     */
    private String dCountry;
    
    /**
     * 收货方 - 省
     */
    private String dProvince;
    
    /**
     * 收货方 – 市
     */
    private String dCity;
    
    /**
     * 收货方 – 地址
     */
    private String dAddress;
    
    /**
     * 寄件人
     */
    private String jContact;
    
    /**
     * 寄件方 – 电话
     */
    private String jTel;
    
    /**
     * 寄件方 – 手机
     */
    private String jMobile;
    
    /**
     * 寄件方 – 省
     */
    private String jProvince;
    
    /**
     * 寄件方 – 市
     */
    private String jCity;
    
    /**
     * 寄件方 – 地址
     */
    private String jAddress;
    
    /**
     * 寄件方 – 公司，可传门店名称
     */
    private String jCompany;
    
    /**
     * 包裹商品数量
     */
    private String cargoCount;
    
    /**
     * 扩展字段1
     */
    private String ext_str1;
    
    /**
     * 扩展字段2
     */
    private String ext_str2;
    
    /**
     * 扩展字段3
     */
    private String ext_str3;

    public String getExpressType() {
        return expressType;
    }

    public void setExpressType(String expressType) {
        this.expressType = expressType;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public String getdContact() {
        return dContact;
    }

    public void setdContact(String dContact) {
        this.dContact = dContact;
    }

    public String getdTel() {
        return dTel;
    }

    public void setdTel(String dTel) {
        this.dTel = dTel;
    }

    public String getdMobile() {
        return dMobile;
    }

    public void setdMobile(String dMobile) {
        this.dMobile = dMobile;
    }

    public String getdCountry() {
        return dCountry;
    }

    public void setdCountry(String dCountry) {
        this.dCountry = dCountry;
    }

    public String getdProvince() {
        return dProvince;
    }

    public void setdProvince(String dProvince) {
        this.dProvince = dProvince;
    }

    public String getdCity() {
        return dCity;
    }

    public void setdCity(String dCity) {
        this.dCity = dCity;
    }

    public String getdAddress() {
        return dAddress;
    }

    public void setdAddress(String dAddress) {
        this.dAddress = dAddress;
    }

    public String getjContact() {
        return jContact;
    }

    public void setjContact(String jContact) {
        this.jContact = jContact;
    }

    public String getjTel() {
        return jTel;
    }

    public void setjTel(String jTel) {
        this.jTel = jTel;
    }

    public String getjMobile() {
        return jMobile;
    }

    public void setjMobile(String jMobile) {
        this.jMobile = jMobile;
    }

    public String getjProvince() {
        return jProvince;
    }

    public void setjProvince(String jProvince) {
        this.jProvince = jProvince;
    }

    public String getjCity() {
        return jCity;
    }

    public void setjCity(String jCity) {
        this.jCity = jCity;
    }

    public String getjAddress() {
        return jAddress;
    }

    public void setjAddress(String jAddress) {
        this.jAddress = jAddress;
    }

    public String getjCompany() {
        return jCompany;
    }

    public void setjCompany(String jCompany) {
        this.jCompany = jCompany;
    }

    public String getCargoCount() {
        return cargoCount;
    }

    public void setCargoCount(String cargoCount) {
        this.cargoCount = cargoCount;
    }

    public String getExt_str1() {
        return ext_str1;
    }

    public void setExt_str1(String ext_str1) {
        this.ext_str1 = ext_str1;
    }

    public String getExt_str2() {
        return ext_str2;
    }

    public void setExt_str2(String ext_str2) {
        this.ext_str2 = ext_str2;
    }

    public String getExt_str3() {
        return ext_str3;
    }

    public void setExt_str3(String ext_str3) {
        this.ext_str3 = ext_str3;
    }

}
