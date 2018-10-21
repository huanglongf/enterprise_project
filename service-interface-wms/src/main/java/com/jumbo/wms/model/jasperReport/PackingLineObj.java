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
import java.math.BigDecimal;

public class PackingLineObj implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = -7770644605754480904L;

    private int ordinal;

    private String skuCode;

    private String barCode;

    private String skuName;

    private int qty;

    private String slipCode1;


    private String qty1;

    private String location;

    private String keyProperty;

    private String color;

    private String size;

    private String jmCode;

    private String rowNumber;

    private String rtnAddress;

    private String rtnContacts;// 退货收件人

    private String shopTelephone;

    private String printTime1;

    private String batchNo;

    private String lpCode;

    private String memo;
    /**
     * reebok detail联接字段(商品名称 颜色 尺码)
     */
    private String concatField;

    private String skuStyleCode;// Gucci货号

    private String gucciSkuName;// Gucci中文名称

    private BigDecimal skuListPrice;// 行吊牌价

    private String isPrintPrice;// Gucci是否打印价格

    public String getConcatField() {
        return concatField;
    }

    public void setConcatField(String concatField) {
        this.concatField = concatField;
    }

    public BigDecimal getTotalAmount2() {
        return totalAmount2;
    }

    public void setTotalAmount2(BigDecimal totalAmount2) {
        this.totalAmount2 = totalAmount2;
    }

    private String receiver;

    private String diliveryWhName;

    private String imgSemacode;

    private String lpCodeHk;

    private BigDecimal totalAmount;

    private BigDecimal transferFee;
    /**
     * 采购(采购行总金额)/销售(销售单行实际总金额)
     */
    private BigDecimal totalAmount2;


    private String sn;
    private String imgLogoTmall;

    private String ksm;

    private String soCode;

    private String keyPro;// ad定制属性

    private String skuCodeStr;

    private Long skuId;

    private String upcCode;// 商品upc码

    private String concatField2;

    public String getConcatField2() {
        return concatField2;
    }

    public void setConcatField2(String concatField2) {
        this.concatField2 = concatField2;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getKeyPro() {
        return keyPro;
    }

    public void setKeyPro(String keyPro) {
        this.keyPro = keyPro;
    }

    public String getKsm() {
        return ksm;
    }

    public void setKsm(String ksm) {
        this.ksm = ksm;
    }

    public String getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(String rowNumber) {
        this.rowNumber = rowNumber;
    }

    /**
     * 货号
     */
    private String supplierSkuCode;
    /**
     * 单价
     */
    private String unitPrice;

    private String orderTotal;

    private String orderUnitPrice;
    /**
     * 小计
     */
    private String statPrice;

    private String gcode;
    private String sal = "1";
    private String num = "102";
    private String money = "100.00";
    private String gName;
    private String slipCode2;
    private String printTimeHk;
    private String staMemo; // 备注
    private int totalQty; // 总计数量
    private String printTime2; // hh-MM-dd
    private String univalent;
    private BigDecimal totalskulistprice;// 商品吊牌价

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    public String getKeyProperty() {
        return keyProperty;
    }

    public void setKeyProperty(String keyProperty) {
        this.keyProperty = keyProperty;
    }

    public String getSupplierSkuCode() {
        return supplierSkuCode;
    }

    public void setSupplierSkuCode(String supplierSkuCode) {
        this.supplierSkuCode = supplierSkuCode;
    }

    public String getJmCode() {
        return jmCode;
    }

    public void setJmCode(String jmCode) {
        this.jmCode = jmCode;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getStatPrice() {
        return statPrice;
    }

    public void setStatPrice(String statPrice) {
        this.statPrice = statPrice;
    }

    public String getGcode() {
        return gcode;
    }

    public void setGcode(String gcode) {
        this.gcode = gcode;
    }

    public String getSal() {
        return sal;
    }

    public void setSal(String sal) {
        this.sal = sal;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getgName() {
        return gName;
    }

    public void setgName(String gName) {
        this.gName = gName;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(String orderTotal) {
        this.orderTotal = orderTotal;
    }

    public String getOrderUnitPrice() {
        return orderUnitPrice;
    }

    public void setOrderUnitPrice(String orderUnitPrice) {
        this.orderUnitPrice = orderUnitPrice;
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

    public String getPrintTime1() {
        return printTime1;
    }

    public void setPrintTime1(String printTime1) {
        this.printTime1 = printTime1;
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

    public String getDiliveryWhName() {
        return diliveryWhName;
    }

    public void setDiliveryWhName(String diliveryWhName) {
        this.diliveryWhName = diliveryWhName;
    }

    public String getImgSemacode() {
        return imgSemacode;
    }

    public void setImgSemacode(String imgSemacode) {
        this.imgSemacode = imgSemacode;
    }

    public String getSlipCode2() {
        return slipCode2;
    }

    public void setSlipCode2(String slipCode2) {
        this.slipCode2 = slipCode2;
    }

    public String getLpCodeHk() {
        return lpCodeHk;
    }

    public void setLpCodeHk(String lpCodeHk) {
        this.lpCodeHk = lpCodeHk;
    }

    public String getPrintTimeHk() {
        return printTimeHk;
    }

    public void setPrintTimeHk(String printTimeHk) {
        this.printTimeHk = printTimeHk;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTransferFee() {
        return transferFee;
    }

    public void setTransferFee(BigDecimal transferFee) {
        this.transferFee = transferFee;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getRtnContacts() {
        return rtnContacts;
    }

    public void setRtnContacts(String rtnContacts) {
        this.rtnContacts = rtnContacts;
    }

    public String getImgLogoTmall() {
        return imgLogoTmall;
    }

    public void setImgLogoTmall(String imgLogoTmall) {
        this.imgLogoTmall = imgLogoTmall;
    }

    public String getStaMemo() {
        return staMemo;
    }

    public void setStaMemo(String staMemo) {
        this.staMemo = staMemo;
    }

    public int getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(int totalQty) {
        this.totalQty = totalQty;
    }

    public String getPrintTime2() {
        return printTime2;
    }

    public void setPrintTime2(String printTime2) {
        this.printTime2 = printTime2;
    }

    public String getSoCode() {
        return soCode;
    }

    public void setSoCode(String soCode) {
        this.soCode = soCode;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getSkuCodeStr() {
        return skuCodeStr;
    }

    public void setSkuCodeStr(String skuCodeStr) {
        this.skuCodeStr = skuCodeStr;
    }

    public String getUpcCode() {
        return upcCode;
    }

    public void setUpcCode(String upcCode) {
        this.upcCode = upcCode;
    }

    public String getQty1() {
        return qty1;
    }

    public void setQty1(String qty1) {
        this.qty1 = qty1;
    }

    public String getUnivalent() {
        return univalent;
    }

    public void setUnivalent(String univalent) {
        this.univalent = univalent;
    }

    public String getSlipCode1() {
        return slipCode1;
    }

    public void setSlipCode1(String slipCode1) {
        this.slipCode1 = slipCode1;
    }

    public String getSkuStyleCode() {
        return skuStyleCode;
    }

    public void setSkuStyleCode(String skuStyleCode) {
        this.skuStyleCode = skuStyleCode;
    }

    public String getGucciSkuName() {
        return gucciSkuName;
    }

    public void setGucciSkuName(String gucciSkuName) {
        this.gucciSkuName = gucciSkuName;
    }

    public BigDecimal getTotalskulistprice() {
        return totalskulistprice;
    }

    public void setTotalskulistprice(BigDecimal totalskulistprice) {
        this.totalskulistprice = totalskulistprice;
    }

    public BigDecimal getSkuListPrice() {
        return skuListPrice;
    }

    public void setSkuListPrice(BigDecimal skuListPrice) {
        this.skuListPrice = skuListPrice;
    }

    public String getIsPrintPrice() {
        return isPrintPrice;
    }

    public void setIsPrintPrice(String isPrintPrice) {
        this.isPrintPrice = isPrintPrice;
    }



}
