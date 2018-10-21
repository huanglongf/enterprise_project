package com.jumbo.wms.model.wmsInterface.cfm;

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
 * 通用反馈发票明细表
 *
 */
@Entity
@Table(name = "T_WH_INTFC_INVOICE_LINE_CFM")
public class IntfcInvoiceLineCfm extends BaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = -8112942228444525764L;
    /**
     * ID
     */
    private Long id;
    /**
     * 通用反馈发票头ID
     */
    private IntfcInvoiceCfm intfcInvoiceId;
    /**
     * 类别
     */
    private String item;
    /**
     * 单价
     */
    private BigDecimal unitPrice;
    /**
     * 数量
     */
    private Integer qty;
    /**
     * 合计
     */
    private BigDecimal amt;
    /**
     * 行号
     */
    private String lineNO;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_WH_INTFC_INVOICE_LINE_CFM", sequenceName = "S_T_WH_INTFC_INVOICE_LINE_CFM", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WH_INTFC_INVOICE_LINE_CFM")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INTFC_INVOICE_ID")
    @Index(name = "FK_T_WH_INTFC_INVOICE_LINE_CFM")
    public IntfcInvoiceCfm getIntfcInvoiceId() {
        return intfcInvoiceId;
    }

    public void setIntfcInvoiceId(IntfcInvoiceCfm intfcInvoiceId) {
        this.intfcInvoiceId = intfcInvoiceId;
    }

    @Column(name = "item", length = 100)
    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    @Column(name = "unit_price", precision = 22, scale = 4)
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Column(name = "qty")
    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    @Column(name = "amt", precision = 22, scale = 4)
    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    @Column(name = "LINE_NO", length = 30)
    public String getLineNO() {
        return lineNO;
    }

    public void setLineNO(String lineNO) {
        this.lineNO = lineNO;
    }



}
