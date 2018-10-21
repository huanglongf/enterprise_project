package com.jumbo.wms.model.hub2wms;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

import com.jumbo.wms.model.BaseModel;

/**
 * 发票信息
 * 
 * @author cheng.su
 * 
 */
@Entity
@Table(name = "T_WH_ORDER_INVOICE_LINE_OMS")
public class WmsOrderInvoiceLineOms extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = -2743032113406814424L;
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
    private WmsOrderInvoiceOms invoiceId;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_WH_ORDER_INVOICE_LINE_OMS", sequenceName = "S_T_WH_ORDER_INVOICE_LINE_OMS", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_WH_ORDER_INVOICE_LINE_OMS")
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "so_invoice_id")
    @Index(name = "IDX_SO_INVOICE_ID")
    public WmsOrderInvoiceOms getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(WmsOrderInvoiceOms invoiceId) {
        this.invoiceId = invoiceId;
    }


}
