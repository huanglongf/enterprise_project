package com.jumbo.webservice.ems.command.New;

import java.util.ArrayList;
import java.util.List;

public class NewWaybill {
    private String txLogisticID;// 物流订单号(一票多单必填)
    private String orderNo;// 电商订单号
    private String mailNum;// 物流运单号(一票多单主单号)
    private String subMails;// 一票多单子单号，以“,”(半角逗号)分隔，非一票多单不填
    private int ypdjpayment;// 一票多件付费方式（1-集中主单计费 2-平均重量计费 3-分单免首重4-主分单单独计费）
    private String orderType;// 订单类型(1-普通订单)
    private long serviceType;// 产品代码，0-经济快递 1-标准快递
    private String remark;// 备注
    private long weight;// 实际重量，单位：克
    private long volumeWeight;// 体积重量，单位：克
    private long feeWeight;// 计费重量，单位：克
    private long insuredAmount;// 保险金额，单位：分
    private String custCode;// EMS客户代码
    private String deliveryTime;// 投递时间(yyyy-mm-dd hh:mm:ss)
    private Long receiverPay;// 收件人付费
    private Long collectionMoney;// 代收货款
    private String revertBill;// 是否返单，0：返单，1：不返单
    private String revertMailNo;// 反向运单号
    private Long postage;// 邮费
    private String sendType;// 寄递类型，0：单程寄递，1：双程后置去程，2：双程后置返程(公安项目专用)
    private Long commodityMoney;// 商品金额(工本费)
    private String state;// 自定义标识
    private Address sender;// 寄件人信息
    private Address receiver;// 收件人信息
    private List<Item> items = new ArrayList<Item>();

    public String getTxLogisticID() {
        return txLogisticID;
    }

    public void setTxLogisticID(String txLogisticID) {
        this.txLogisticID = txLogisticID;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getMailNum() {
        return mailNum;
    }

    public void setMailNum(String mailNum) {
        this.mailNum = mailNum;
    }

    public String getSubMails() {
        return subMails;
    }

    public void setSubMails(String subMails) {
        this.subMails = subMails;
    }

    public int getYpdjpayment() {
        return ypdjpayment;
    }

    public void setYpdjpayment(int ypdjpayment) {
        this.ypdjpayment = ypdjpayment;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public long getServiceType() {
        return serviceType;
    }

    public void setServiceType(long serviceType) {
        this.serviceType = serviceType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public long getWeight() {
        return weight;
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }

    public long getVolumeWeight() {
        return volumeWeight;
    }

    public void setVolumeWeight(long volumeWeight) {
        this.volumeWeight = volumeWeight;
    }

    public long getFeeWeight() {
        return feeWeight;
    }

    public void setFeeWeight(long feeWeight) {
        this.feeWeight = feeWeight;
    }

    public long getInsuredAmount() {
        return insuredAmount;
    }

    public void setInsuredAmount(long insuredAmount) {
        this.insuredAmount = insuredAmount;
    }

    public String getCustCode() {
        return custCode;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Long getReceiverPay() {
        return receiverPay;
    }

    public void setReceiverPay(Long receiverPay) {
        this.receiverPay = receiverPay;
    }

    public Long getCollectionMoney() {
        return collectionMoney;
    }

    public void setCollectionMoney(Long collectionMoney) {
        this.collectionMoney = collectionMoney;
    }

    public String getRevertBill() {
        return revertBill;
    }

    public void setRevertBill(String revertBill) {
        this.revertBill = revertBill;
    }

    public String getRevertMailNo() {
        return revertMailNo;
    }

    public void setRevertMailNo(String revertMailNo) {
        this.revertMailNo = revertMailNo;
    }

    public Long getPostage() {
        return postage;
    }

    public void setPostage(Long postage) {
        this.postage = postage;
    }

    public String getSendType() {
        return sendType;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType;
    }

    public Long getCommodityMoney() {
        return commodityMoney;
    }

    public void setCommodityMoney(Long commodityMoney) {
        this.commodityMoney = commodityMoney;
    }

    public String getState() {
        return state;
    }

    public Address getSender() {
        return sender;
    }

    public void setSender(Address sender) {
        this.sender = sender;
    }

    public Address getReceiver() {
        return receiver;
    }

    public void setReceiver(Address receiver) {
        this.receiver = receiver;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

}
