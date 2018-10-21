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
import java.util.List;

public class MetroInvoiceObj implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = -5892615768399796018L;

    // private Integer invoiceType = 1; // 发票类型, 默认是商业零售发票. Constants.SO_INVOICE_TYPE_RETAIL
    private String companyName; // 注册公司名字
    private String taxCode; // 税号

    private String registeredAddress; // 注册地址

    private String tellPhone; // 电话

    private String bankName; // 开户行

    private String bankCode; // 帐号
    private String invoiceTitle;
    private Integer invoiceType = 1; // 默认值1-普通商业零售发票. 可选:2-增值税专用发票
    private String strInvoiceType;

    private String soCode; // 相关单据号
    private String receiver; // 收货人
    private String receiverTel; // 联系电话
    private String receiverAddress; // 收货地址

    private BigDecimal sumTotalAmount; // 订单金额 sum(...)
    private BigDecimal totalPointUsed; // 积分抵扣金额 -nvl(so.total_point_used,0)/100
    private BigDecimal discount; // 折扣 (nvl(so.total_actual,0) - sumTotalAmount)
    private BigDecimal totalActualAmount; // 实际支付金额 nvl(so.total_actual,0) -
                                          // (nvl(so.total_point_used,0)/100)

    private BigDecimal transferFee; // 订单运费(d.transfer_fee)

    private String memo; // 备注
    // private BigDecimal orderAmount; // 订单金额 （totalActual + transferFee）

    private List<MetroInvoiceLineObj> lines; // 商品明细

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getRegisteredAddress() {
        return registeredAddress;
    }

    public void setRegisteredAddress(String registeredAddress) {
        this.registeredAddress = registeredAddress;
    }

    public String getTellPhone() {
        return tellPhone;
    }

    public void setTellPhone(String tellPhone) {
        this.tellPhone = tellPhone;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getReceiverTel() {
        return receiverTel;
    }

    public void setReceiverTel(String receiverTel) {
        this.receiverTel = receiverTel;
    }

    public BigDecimal getTransferFee() {
        return transferFee;
    }

    public void setTransferFee(BigDecimal transferFee) {
        this.transferFee = transferFee;
    }

    public List<MetroInvoiceLineObj> getLines() {
        return lines;
    }

    public void setLines(List<MetroInvoiceLineObj> lines) {
        this.lines = lines;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getSoCode() {
        return soCode;
    }

    public void setSoCode(String soCode) {
        this.soCode = soCode;
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public Integer getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(Integer invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getStrInvoiceType() {
        return strInvoiceType;
    }

    public void setStrInvoiceType(String strInvoiceType) {
        this.strInvoiceType = strInvoiceType;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public BigDecimal getTotalPointUsed() {
        return totalPointUsed;
    }

    public void setTotalPointUsed(BigDecimal totalPointUsed) {
        this.totalPointUsed = totalPointUsed;
    }

    public BigDecimal getSumTotalAmount() {
        return sumTotalAmount;
    }

    public void setSumTotalAmount(BigDecimal sumTotalAmount) {
        this.sumTotalAmount = sumTotalAmount;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getTotalActualAmount() {
        return totalActualAmount;
    }

    public void setTotalActualAmount(BigDecimal totalActualAmount) {
        this.totalActualAmount = totalActualAmount;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
