package com.jumbo.webservice.nike.command;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by IntelliJ IDEA. User: hlz Date: 2010-8-16 Time: 13:33:57 To change this template use
 * File | Settings | File Templates.
 */
public class NikeDeliveryInfo implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -8750820706011800526L;
    private String country;
    private String province;
    private String city;
    private String district;
    private String address;
    private String telephone;
    private String mobile;
    private String receiver;
    private String zipcode;
    private String lpCode;
    private String remark;
    private BigDecimal totalAmount; // 总金额 = 运费 +　销售金额

    private Integer transType; // 运送方式(快递附加服务)
    private Integer transTimeType;// 运单时限类型(快递附加服务)
    private String transMemo; // 运单备注(快递附加服务)
    private Date orderCreateTime; // 订单创建时间
    private Date orderPlanReceiptTime;// 订单预计送达时间

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

    public String getLpCode() {
        return lpCode;
    }

    public void setLpCode(String lpCode) {
        this.lpCode = lpCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
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

    public Date getOrderCreateTime() {
        return orderCreateTime;
    }

    public void setOrderCreateTime(Date orderCreateTime) {
        this.orderCreateTime = orderCreateTime;
    }

    public Date getOrderPlanReceiptTime() {
        return orderPlanReceiptTime;
    }

    public void setOrderPlanReceiptTime(Date orderPlanReceiptTime) {
        this.orderPlanReceiptTime = orderPlanReceiptTime;
    }

}
