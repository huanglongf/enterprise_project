package com.jumbo.wms.model.vmi.etamData;

import java.math.BigDecimal;

public class EtamSalesCommand extends EtamSales {

    /**
     * 
     */
    private static final long serialVersionUID = -8781519802569738908L;

    private String bar; // skuCode

    private String size; // sSize

    private Long quantity; // qty

    private BigDecimal netPrice; // netRetail;

    private Long shopNo; // shopNo

    private String dT;

    private String billTime;

    private BigDecimal sumTotal; // totalActual;

    private String salesChannel;

    public String getBar() {
        return bar;
    }

    public void setBar(String bar) {
        this.bar = bar;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getNetPrice() {
        return netPrice;
    }

    public void setNetPrice(BigDecimal netPrice) {
        this.netPrice = netPrice;
    }

    public Long getShopNo() {
        return shopNo;
    }

    public void setShopNo(Long shopNo) {
        this.shopNo = shopNo;
    }

    public String getdT() {
        return dT;
    }

    public void setdT(String dT) {
        this.dT = dT;
    }

    public String getBillTime() {
        return billTime;
    }

    public void setBillTime(String billTime) {
        this.billTime = billTime;
    }

    public BigDecimal getSumTotal() {
        return sumTotal;
    }

    public void setSumTotal(BigDecimal sumTotal) {
        this.sumTotal = sumTotal;
    }

    public String getSalesChannel() {
        return salesChannel;
    }

    public void setSalesChannel(String salesChannel) {
        this.salesChannel = salesChannel;
    }

}
