package com.jumbo.wms.model.warehouse;

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
import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;

@Entity
@Table(name = "t_wh_sta_invoice_line")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class StaInvoiceLine extends BaseModel {
    /**
	 * 
	 */
    private static final long serialVersionUID = -1604764846644803746L;
    /**
     * ID
     */
    private Long id;
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
     * 发票ID
     */
    private StaInvoice staInvoice;
    /*
     * 行号
     */
    private String lineNO;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_sta_invoice_line", sequenceName = "S_t_wh_sta_invoice_line", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_sta_invoice_line")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id")
    @Index(name = "IDX_invoice_id")
    public StaInvoice getStaInvoice() {
        return staInvoice;
    }

    public void setStaInvoice(StaInvoice staInvoice) {
        this.staInvoice = staInvoice;
    }

    @Column(name = "LINE_NO", length = 30)
    public String getLineNO() {
        return lineNO;
    }

    public void setLineNO(String lineNO) {
        this.lineNO = lineNO;
    }



}
