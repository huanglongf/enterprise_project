package com.jumbo.webservice.biaogan.command;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"warehouseid", "customerId", "orderCode", "systemId", "orderType", "shipping", "issuePartyId", "issuePartyName", "customerName", "payment", "orderTime", "website", "freight", "serviceCharge", "payTime", "isCashsale",
        "priority", "expectedTime", "requiredTime", "name", "postcode", "phone", "mobile", "prov", "city", "district", "address", "itemsValue", "items", "remark"})
public class RequestOrderInfo implements Serializable {


    private static final long serialVersionUID = 2905633383274938762L;

    /**
     * 仓库编号
     */
    private String warehouseid;

    /**
     * 客户ID
     */
    private String customerId;

    /**
     * 管理号
     */
    private String systemId = "";

    /**
     * 订单类型
     */
    private String orderType = "";


    /**
     * 配送方式
     */
    private String shipping = "";

    /**
     * 销售渠道id
     */
    private String issuePartyId = "";

    /**
     * 销售渠道name
     */
    private String issuePartyName = "";


    /**
     * 客户昵称
     */
    private String customerName = "";

    /**
     * 支付方式
     */
    private String payment = "";

    /**
     * 订购时间
     */
    private String orderTime;

    /**
     * 店铺网址
     */
    private String website = "";


    /**
     * 运费
     */
    private BigDecimal freight = new BigDecimal(0);

    /**
     * 货到付款服务费
     */
    private BigDecimal serviceCharge = new BigDecimal(0);


    /**
     * 作业单号
     */
    private String orderCode = "";

    /**
     * 金额
     */
    private BigDecimal itemsValue = new BigDecimal(0);

    /**
     * 姓名
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
     * 地址
     */
    private String address = "";

    /**
     * 卖家备注
     */
    private String remark = "";

    /**
     * 付款
     */
    private String payTime = "";

    /**
     * 是否须要开票
     */
    private String isCashsale = "";

    /**
     * 订单优先级
     */
    private String priority = "";

    /**
     * 预期发货时间
     */
    private String expectedTime = "";

    /**
     * 要求发货时间
     */
    private String requiredTime = "";

    @XmlElementWrapper(name = "items")
    @XmlElements({@XmlElement(name = "spuCode", type = Item.class), @XmlElement(name = "itemName", type = Item.class), @XmlElement(name = "itemCount", type = Item.class), @XmlElement(name = "itemValue", type = Item.class),
            @XmlElement(name = "item", type = Item.class)})
    private List<Object> items;



    public String getWarehouseid() {
        return warehouseid;
    }


    public void setWarehouseid(String warehouseid) {
        this.warehouseid = warehouseid;
    }


    public String getOrderCode() {
        return orderCode;
    }


    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }



    public String getRemark() {
        return remark;
    }


    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<Object> getItems() {
        return items;
    }


    public void setItems(List<Object> items) {
        this.items = items;
    }


    public String getCustomerId() {
        return customerId;
    }


    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }


    public String getSystemId() {
        return systemId;
    }


    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }


    public String getOrderType() {
        return orderType;
    }


    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }


    public String getShipping() {
        return shipping;
    }


    public void setShipping(String shipping) {
        this.shipping = shipping;
    }


    public String getIssuePartyId() {
        return issuePartyId;
    }


    public void setIssuePartyId(String issuePartyId) {
        this.issuePartyId = issuePartyId;
    }


    public String getIssuePartyName() {
        return issuePartyName;
    }


    public void setIssuePartyName(String issuePartyName) {
        this.issuePartyName = issuePartyName;
    }


    public String getCustomerName() {
        return customerName;
    }


    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }


    public String getPayment() {
        return payment;
    }


    public void setPayment(String payment) {
        this.payment = payment;
    }


    public String getOrderTime() {
        return orderTime;
    }


    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }


    public String getWebsite() {
        return website;
    }


    public void setWebsite(String website) {
        this.website = website;
    }


    public BigDecimal getFreight() {
        return freight;
    }


    public void setFreight(BigDecimal freight) {
        this.freight = freight;
    }


    public BigDecimal getServiceCharge() {
        return serviceCharge;
    }


    public void setServiceCharge(BigDecimal serviceCharge) {
        this.serviceCharge = serviceCharge;
    }


    public BigDecimal getItemsValue() {
        return itemsValue;
    }


    public void setItemsValue(BigDecimal itemsValue) {
        this.itemsValue = itemsValue;
    }


    public String getPayTime() {
        return payTime;
    }


    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }


    public String getIsCashsale() {
        return isCashsale;
    }


    public void setIsCashsale(String isCashsale) {
        this.isCashsale = isCashsale;
    }


    public String getPriority() {
        return priority;
    }


    public void setPriority(String priority) {
        this.priority = priority;
    }


    public String getExpectedTime() {
        return expectedTime;
    }


    public void setExpectedTime(String expectedTime) {
        this.expectedTime = expectedTime;
    }


    public String getRequiredTime() {
        return requiredTime;
    }


    public void setRequiredTime(String requiredTime) {
        this.requiredTime = requiredTime;
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


    public String getMobile() {
        return mobile;
    }


    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

}
