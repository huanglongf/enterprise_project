package com.jumbo.wms.model.jasperReport;

import java.io.Serializable;


/**
 * Coach小票订单明细
 * 
 * @author jinlong.ke
 * 
 */
public class CoachTicketOrderInfoLine implements Serializable{
	private static final long serialVersionUID = 5190806887920710190L;
	private String supplierCode;
    private String skuName;
    private String listPrice;
    private String qty;
    private String dic;
    private String payAmt;
    private String detail;

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getListPrice() {
        return listPrice;
    }

    public void setListPrice(String listPrice) {
        this.listPrice = listPrice;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getDic() {
        return dic;
    }

    public void setDic(String dic) {
        this.dic = dic;
    }

    public String getPayAmt() {
        return payAmt;
    }

    public void setPayAmt(String payAmt) {
        this.payAmt = payAmt;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
