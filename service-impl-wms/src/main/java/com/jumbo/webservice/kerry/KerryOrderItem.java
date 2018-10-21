package com.jumbo.webservice.kerry;

import java.math.BigDecimal;
import java.util.Date;

public class KerryOrderItem {
    
    private String orderNo;         // 客户订单号
    private String deliveryNo;      // 运单号
    private BigDecimal quantity;    // 数量
    private BigDecimal volume;      // 体积
    private BigDecimal grossWeight; // 毛重
    private String cargoExpl;       // 货物描述
    private String memo;            // 备注
    private String senderName;      // 发件人
    private String senderCode;      // 发件人代码
    private String senderProvince;  // 发件省份
    private String senderCity;      // 发件城市
    private String senderDistrict;  // 发件区县
    private String senderAddress;   // 发件地址
    private String senderPhone;     // 发件人电话
    private String senderMobile;    // 发件手机
    private String senderPostcode;  // 发件人邮编
    private String receiverName;    // 收件人
    private String receiverCode;    // 收件人代码
    private String receiverProvince;// 收件省份
    private String receiverCity;    // 收件城市
    private String receiverDistrict;// 收件区县
    private String receiverAddress; // 收件地址
    private String receiverTel;     // 收件人电话
    private String receiverPhone;   // 收件手机
    private String receiverPostcode;// 收件人邮编
    private Date requireLeadtime;   // 要求取件日期
    private Date requireArrivaltime;// 要求到达时间
    private Boolean deliveryholiday;// 是否节假日递送
    private String payType;         // 支付方式
    private String tranprice;       // 托运申明价值
    private BigDecimal codAmt;      // 货到收款金额
    private BigDecimal insurePrice; // 保险金额
    
    public String getOrderNo() {
        return orderNo;
    }
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    public String getDeliveryNo() {
        return deliveryNo;
    }
    public void setDeliveryNo(String deliveryNo) {
        this.deliveryNo = deliveryNo;
    }
    public BigDecimal getQuantity() {
        return quantity;
    }
    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }
    public BigDecimal getVolume() {
        return volume;
    }
    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }
    public BigDecimal getGrossWeight() {
        return grossWeight;
    }
    public void setGrossWeight(BigDecimal grossWeight) {
        this.grossWeight = grossWeight;
    }
    public String getCargoExpl() {
        return cargoExpl;
    }
    public void setCargoExpl(String cargoExpl) {
        this.cargoExpl = cargoExpl;
    }
    public String getMemo() {
        return memo;
    }
    public void setMemo(String memo) {
        this.memo = memo;
    }
    public String getSenderName() {
        return senderName;
    }
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }
    public String getSenderCode() {
        return senderCode;
    }
    public void setSenderCode(String senderCode) {
        this.senderCode = senderCode;
    }
    public String getSenderProvince() {
        return senderProvince;
    }
    public void setSenderProvince(String senderProvince) {
        this.senderProvince = senderProvince;
    }
    public String getSenderCity() {
        return senderCity;
    }
    public void setSenderCity(String senderCity) {
        this.senderCity = senderCity;
    }
    public String getSenderDistrict() {
        return senderDistrict;
    }
    public void setSenderDistrict(String senderDistrict) {
        this.senderDistrict = senderDistrict;
    }
    public String getSenderAddress() {
        return senderAddress;
    }
    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }
    public String getSenderPhone() {
        return senderPhone;
    }
    public void setSenderPhone(String senderPhone) {
        this.senderPhone = senderPhone;
    }
    public String getSenderMobile() {
        return senderMobile;
    }
    public void setSenderMobile(String senderMobile) {
        this.senderMobile = senderMobile;
    }
    public String getSenderPostcode() {
        return senderPostcode;
    }
    public void setSenderPostcode(String senderPostcode) {
        this.senderPostcode = senderPostcode;
    }
    public String getReceiverName() {
        return receiverName;
    }
    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }
    public String getReceiverCode() {
        return receiverCode;
    }
    public void setReceiverCode(String receiverCode) {
        this.receiverCode = receiverCode;
    }
    public String getReceiverProvince() {
        return receiverProvince;
    }
    public void setReceiverProvince(String receiverProvince) {
        this.receiverProvince = receiverProvince;
    }
    public String getReceiverCity() {
        return receiverCity;
    }
    public void setReceiverCity(String receiverCity) {
        this.receiverCity = receiverCity;
    }
    public String getReceiverDistrict() {
        return receiverDistrict;
    }
    public void setReceiverDistrict(String receiverDistrict) {
        this.receiverDistrict = receiverDistrict;
    }
    public String getReceiverAddress() {
        return receiverAddress;
    }
    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }
    public String getReceiverTel() {
        return receiverTel;
    }
    public void setReceiverTel(String receiverTel) {
        this.receiverTel = receiverTel;
    }
    public String getReceiverPhone() {
        return receiverPhone;
    }
    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }
    public String getReceiverPostcode() {
        return receiverPostcode;
    }
    public void setReceiverPostcode(String receiverPostcode) {
        this.receiverPostcode = receiverPostcode;
    }
    public Date getRequireLeadtime() {
        return requireLeadtime;
    }
    public void setRequireLeadtime(Date requireLeadtime) {
        this.requireLeadtime = requireLeadtime;
    }
    public Date getRequireArrivaltime() {
        return requireArrivaltime;
    }
    public void setRequireArrivaltime(Date requireArrivaltime) {
        this.requireArrivaltime = requireArrivaltime;
    }
    public String getPayType() {
        return payType;
    }
    public void setPayType(String payType) {
        this.payType = payType;
    }
    public String getTranprice() {
        return tranprice;
    }
    public void setTranprice(String tranprice) {
        this.tranprice = tranprice;
    }
    public BigDecimal getCodAmt() {
        return codAmt;
    }
    public void setCodAmt(BigDecimal codAmt) {
        this.codAmt = codAmt;
    }
    public BigDecimal getInsurePrice() {
        return insurePrice;
    }
    public void setInsurePrice(BigDecimal insurePrice) {
        this.insurePrice = insurePrice;
    }
    public Boolean getDeliveryholiday() {
        return deliveryholiday;
    }
    public void setDeliveryholiday(Boolean deliveryholiday) {
        this.deliveryholiday = deliveryholiday;
    }
    
}
