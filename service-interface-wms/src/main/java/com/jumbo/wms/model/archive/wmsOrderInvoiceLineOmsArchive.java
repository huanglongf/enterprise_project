package com.jumbo.wms.model.archive;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


import javax.persistence.Table;

import com.jumbo.wms.model.BaseModel;
/**
 * 发票明细信息归档
 * 
 * @author cheng.su
 * 
 */
@Entity
@Table(name = "T_WH_INVOICE_LINE_ARCHIVE")
public class wmsOrderInvoiceLineOmsArchive extends BaseModel {
	 /**
	 * 
	 */
	private static final long serialVersionUID = -1919993432212230934L;
	/**
     * ID
     */
    private Long id;
    /**
     * 总金额
     */
    private BigDecimal amt;
    /**
     * 商品
     */
    private String item;
    /**
     * 平台号
     */
    private String lineNo;
    /**
     * 数量
     */
    private Integer qty;
    /**
     * 单价
     */
    private BigDecimal unitPrice;
    /**
     * 发票
     */
    private Long invoiceId;

    @Id
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "amt")
    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    @Column(name = "item")
    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    @Column(name = "line_no")
    public String getLineNo() {
        return lineNo;
    }

    public void setLineNo(String lineNo) {
        this.lineNo = lineNo;
    }

    @Column(name = "qty")
    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    @Column(name = "unit_price")
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Column(name = "so_invoice_id")
    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }


}
