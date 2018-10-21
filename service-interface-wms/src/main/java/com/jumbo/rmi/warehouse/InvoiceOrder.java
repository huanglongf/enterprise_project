package com.jumbo.rmi.warehouse;

import java.io.Serializable;
import java.util.List;

import com.jumbo.wms.model.hub2wms.WmsOrderInvoice;

/**
 * 补开发票接口实体
 * 
 * @author jinlong.ke
 * 
 */
public class InvoiceOrder implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8401046430755858223L;

    /*
     * 单据号
     */
    private String orderCode;
    /*
     * 店铺
     */
    private String owner;
    /*
     * 省
     */
    private String province;
    /*
     * 市
     */
    private String city;
    /*
     * 区
     */
    private String district;
    /*
     * 送货地址
     */
    private String address;
    /*
     * 电话
     */
    private String telephone;
    /*
     * 手机
     */
    private String mobile;
    /*
     * 收货人
     */
    private String receiver;
    /*
     * 邮编
     */
    private String zipcode;
    /*
     * 物流商简称
     */
    private String transCode;
    /*
     * 发票内容
     */
    private List<WmsOrderInvoice> invoices;

    /**
     * 付款单位纳税人识别号
     */
    private String identificationNumber;

    /**
     * 地址
     */
    private String invoiceAddress;

    /**
     * 电话
     */
    private String invoiceTelephone;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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

    public String getTransCode() {
        return transCode;
    }

    public void setTransCode(String transCode) {
        this.transCode = transCode;
    }

    public List<WmsOrderInvoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<WmsOrderInvoice> invoices) {
        this.invoices = invoices;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getInvoiceAddress() {
        return invoiceAddress;
    }

    public void setInvoiceAddress(String invoiceAddress) {
        this.invoiceAddress = invoiceAddress;
    }

    public String getInvoiceTelephone() {
        return invoiceTelephone;
    }

    public void setInvoiceTelephone(String invoiceTelephone) {
        this.invoiceTelephone = invoiceTelephone;
    }

}
