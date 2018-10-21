package com.jumbo.wms.model.vmi.warehouse;

import java.math.BigDecimal;
import java.util.Date;

public class MsgOutboundOrderCommand extends MsgOutboundOrder {

    /**
	 * 
	 */
    private static final long serialVersionUID = 5509069370398448063L;

    private String staOwner;
    private String staRefSlipCode;
    private Date staCreateTime;
    private BigDecimal staTotalActual;

    private String owner;

    private String tbCode;

    private String receiverAddres;

    private String whAddress;
    private String invoiceTitle;
    private String invoiceMemo;



    private String locationCode;

    private String reserve1;

    private String reserve2;

    private String reserve3;

    private String isCashsale;

    private int countSum;

    public String getStaOwner() {
        return staOwner;
    }

    public void setStaOwner(String staOwner) {
        this.staOwner = staOwner;
    }

    public String getStaRefSlipCode() {
        return staRefSlipCode;
    }

    public void setStaRefSlipCode(String staRefSlipCode) {
        this.staRefSlipCode = staRefSlipCode;
    }

    public Date getStaCreateTime() {
        return staCreateTime;
    }

    public void setStaCreateTime(Date staCreateTime) {
        this.staCreateTime = staCreateTime;
    }

    public BigDecimal getStaTotalActual() {
        return staTotalActual;
    }

    public void setStaTotalActual(BigDecimal staTotalActual) {
        this.staTotalActual = staTotalActual;
    }

    private Date startDate;

    private Date endDate;

    private int intStatus;

    private String skuBarCode;

    private Long qty = 0L;

    private BigDecimal unitPrice = new BigDecimal(0);

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getIntStatus() {
        return intStatus;
    }

    public void setIntStatus(int intStatus) {
        this.intStatus = intStatus;
    }

    public String getSkuBarCode() {
        return skuBarCode;
    }

    public void setSkuBarCode(String skuBarCode) {
        this.skuBarCode = skuBarCode;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getTbCode() {
        return tbCode;
    }

    public void setTbCode(String tbCode) {
        this.tbCode = tbCode;
    }

    public String getReceiverAddres() {
        return receiverAddres;
    }

    public void setReceiverAddres(String receiverAddres) {
        this.receiverAddres = receiverAddres;
    }

    public String getWhAddress() {
        return whAddress;
    }

    public void setWhAddress(String whAddress) {
        this.whAddress = whAddress;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public String getInvoiceMemo() {
        return invoiceMemo;
    }

    public void setInvoiceMemo(String invoiceMemo) {
        this.invoiceMemo = invoiceMemo;
    }

    public String getReserve1() {
        return reserve1;
    }

    public void setReserve1(String reserve1) {
        this.reserve1 = reserve1;
    }

    public String getReserve2() {
        return reserve2;
    }

    public void setReserve2(String reserve2) {
        this.reserve2 = reserve2;
    }

    public String getReserve3() {
        return reserve3;
    }

    public void setReserve3(String reserve3) {
        this.reserve3 = reserve3;
    }

    public String getIsCashsale() {
        return isCashsale;
    }

    public void setIsCashsale(String isCashsale) {
        this.isCashsale = isCashsale;
    }

    public int getCountSum() {
        return countSum;
    }

    public void setCountSum(int countSum) {
        this.countSum = countSum;
    }

}
