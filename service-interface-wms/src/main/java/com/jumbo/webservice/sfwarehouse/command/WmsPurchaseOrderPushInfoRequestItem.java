package com.jumbo.webservice.sfwarehouse.command;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * 
 * @author jinlong.ke
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class WmsPurchaseOrderPushInfoRequestItem implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -3478721015059869164L;
    @XmlElement
    private Long id;
    @XmlElement
    private BigDecimal erp_order_line_num;
    @XmlElement
    private String sku_no;
    @XmlElement
    private BigDecimal qty;
    @XmlElement
    private String lot;
    @XmlElement
    private Date expiration_date;
    @XmlElement
    private String inventory_sts;
    @XmlElement
    private String vendor;
    @XmlElement
    private SerialNumber serialNumberList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getErp_order_line_num() {
        return erp_order_line_num;
    }

    public void setErp_order_line_num(BigDecimal erpOrderLineNum) {
        erp_order_line_num = erpOrderLineNum;
    }

    public String getSku_no() {
        return sku_no;
    }

    public void setSku_no(String skuNo) {
        sku_no = skuNo;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public Date getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(Date expirationDate) {
        expiration_date = expirationDate;
    }

    public String getInventory_sts() {
        return inventory_sts;
    }

    public void setInventory_sts(String inventorySts) {
        inventory_sts = inventorySts;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public SerialNumber getSerialNumberList() {
        return serialNumberList;
    }

    public void setSerialNumberList(SerialNumber serialNumberList) {
        this.serialNumberList = serialNumberList;
    }

}
