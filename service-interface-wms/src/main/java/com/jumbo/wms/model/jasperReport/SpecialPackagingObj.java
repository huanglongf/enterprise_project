/**
 * Copyright (c) 2010 Jumbomart All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Jumbomart. You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jumbo.
 * 
 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 * 
 */
package com.jumbo.wms.model.jasperReport;

import java.io.Serializable;
import java.util.List;

public class SpecialPackagingObj implements Serializable {
    private static final long serialVersionUID = -9183745478166471950L;

    private String orderCode; // sta作业单号
    private String owner;
    private String orderDate; // 订单日期
    private String outboundDate; // 出库时间
    private String seller;// 销售姓名
    private String customer; // 顾客
    private String ShopName;// 订单所属店铺名称
    private String vipCode;// 会员卡编号
    private String memo;// 备注

    private String totalActual; // 订单实际总金额
    private String payActual;// 支付总金额
    private String payType;// 支付方式

    private Long commodityQty;// 商品数量

    private String country; // 国家
    private String province; // 省
    private String city;// 市
    private String district;// 区
    private String address;// 送货地址

    private List<SpecialPackagingLineObj> lines; // 明细

    private String imgSemacode;// 二维码地址


    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
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

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public List<SpecialPackagingLineObj> getLines() {
        return lines;
    }

    public void setLines(List<SpecialPackagingLineObj> lines) {
        this.lines = lines;
    }

    public String getOutboundDate() {
        return outboundDate;
    }

    public void setOutboundDate(String outboundDate) {
        this.outboundDate = outboundDate;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String shopName) {
        ShopName = shopName;
    }

    public String getPayActual() {
        return payActual;
    }

    public void setPayActual(String payActual) {
        this.payActual = payActual;
    }

    public Long getCommodityQty() {
        return commodityQty;
    }

    public void setCommodityQty(Long commodityQty) {
        this.commodityQty = commodityQty;
    }

    public String getTotalActual() {
        return totalActual;
    }

    public void setTotalActual(String totalActual) {
        this.totalActual = totalActual;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getVipCode() {
        return vipCode;
    }

    public void setVipCode(String vipCode) {
        this.vipCode = vipCode;
    }

    public String getImgSemacode() {
        return imgSemacode;
    }

    public void setImgSemacode(String imgSemacode) {
        this.imgSemacode = imgSemacode;
    }

}
