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
 * HUB2WMS过仓 发票明细
 * 
 * @author jinlong.ke
 * 
 */
@Entity
@Table(name = "T_WH_ORDER_INVOICE_LINE")
public class WmsOrderInvoiceLineQueue extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = -1067713867448208966L;
    /*
     * PK
     */
    private Long id;
    /*
     * 商品
     */
    private String item;
    /*
     * 数量
     */
    private Long qty;
    /*
     * 单价
     */
    private BigDecimal unitPrice;
    /*
     * 总金额
     */
    private BigDecimal amt;
    /*
     * 平台行号 
     */
    private String lineNo;
    /*
     * 订单发票头
     */
    private WmsOrderInvoiceQueue wmsOrderInvoiceQueue;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_WH_INVOICE_LINE", sequenceName = "S_T_WH_ORDER_INVOICE_LINE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_WH_INVOICE_LINE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "ITEM", length = 300)
    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    @Column(name = "QTY", precision = 15)
    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    @Column(name = "UNIT_PRICE", precision = 15, scale = 2)
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Column(name = "AMT", precision = 15, scale = 2)
    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    @Column(name = "LINE_NO", length = 30)
    public String getLineNo() {
        return lineNo;
    }

    public void setLineNo(String lineNo) {
        this.lineNo = lineNo;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SO_INVOICE_ID")
    @Index(name = "IDX_LINE_SO_INVOICE_ID")
    public WmsOrderInvoiceQueue getWmsOrderInvoiceQueue() {
        return wmsOrderInvoiceQueue;
    }

    public void setWmsOrderInvoiceQueue(WmsOrderInvoiceQueue wmsOrderInvoiceQueue) {
        this.wmsOrderInvoiceQueue = wmsOrderInvoiceQueue;
    }



}
