package com.jumbo.wms.model.lmis;

import java.util.Date;
import java.util.List;

/**
 * 出库单信息头
 * 
 * @author jinggang.chen
 * 
 */
public class OrderOutBoundInfo {

    private String soCode; // Y wms订单号
    private String storeCode; // Y 店铺编码
    private String storeName; // Y 店铺名称
    private String slipCode1; // N 平台订单号(相关单据号1)
    private String slipCode2;// N nike订单号(相关单据号2)
    private String relatedNo;// Y 相关单据号
    private Date planTime;// N date 平台订单时间
    private Date createTime; // Y date Wms创单时间
    private String batchNo; // N 配货批次
    private String lpCode;// Y 快递公司
    private String receiver; // Y 收货人姓名
    private String telephone; // N 联系电话
    private String mobile;// N 手机
    private String receiverAddress;// Y 收货地址（需拼接完整地址）
    private String zipCode;// N 邮编
    private String totalQty; // Y 总商品件数
    private String orderAmount;// Y 订单总金额
    private String totalAmount;// Y 商品总金额
    private String transferFee;// Y 运费
    private String invoiceNumber;// Y 发票数量
    private String rtnAddress;// N 退换货地址（订单所属店铺上的地址）
    private String shopTelephone;// N 售后联系电话（订单所属店铺上的电话）
    private String diliveryWhName;// Y 发货仓库
    private String rtnReceiver;// Y 退货人收件人（订单所属店铺上的联系人）
    private String wareHouse;//仓库（业务人员指定）
    private List<OrderOutBoundLineInfo> line; // 出库单明细

    private String qrCode;// 逆向物流二维码链接地址

    public String getSoCode() {
        return soCode;
    }

    public void setSoCode(String soCode) {
        this.soCode = soCode;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getSlipCode1() {
        return slipCode1;
    }

    public void setSlipCode1(String slipCode1) {
        this.slipCode1 = slipCode1;
    }

    public String getSlipCode2() {
        return slipCode2;
    }

    public void setSlipCode2(String slipCode2) {
        this.slipCode2 = slipCode2;
    }

    public String getRelatedNo() {
        return relatedNo;
    }

    public void setRelatedNo(String relatedNo) {
        this.relatedNo = relatedNo;
    }

    public Date getPlanTime() {
        return planTime;
    }

    public void setPlanTime(Date planTime) {
        this.planTime = planTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getLpCode() {
        return lpCode;
    }

    public void setLpCode(String lpCode) {
        this.lpCode = lpCode;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
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

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(String totalQty) {
        this.totalQty = totalQty;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTransferFee() {
        return transferFee;
    }

    public void setTransferFee(String transferFee) {
        this.transferFee = transferFee;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getRtnAddress() {
        return rtnAddress;
    }

    public void setRtnAddress(String rtnAddress) {
        this.rtnAddress = rtnAddress;
    }

    public String getShopTelephone() {
        return shopTelephone;
    }

    public void setShopTelephone(String shopTelephone) {
        this.shopTelephone = shopTelephone;
    }

    public String getDiliveryWhName() {
        return diliveryWhName;
    }

    public void setDiliveryWhName(String diliveryWhName) {
        this.diliveryWhName = diliveryWhName;
    }

    public String getRtnReceiver() {
        return rtnReceiver;
    }

    public void setRtnReceiver(String rtnReceiver) {
        this.rtnReceiver = rtnReceiver;
    }
    
    public String getWareHouse() {
        return wareHouse;
    }

    public void setWareHouse(String wareHouse) {
        this.wareHouse = wareHouse;
    }

    public List<OrderOutBoundLineInfo> getLine() {
        return line;
    }

    public void setLine(List<OrderOutBoundLineInfo> line) {
        this.line = line;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }


}
