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
@Table(name = "T_WH_ORDER_INVOICE_OMS")
public class WmsOrderInvoiceOms extends BaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = -4021551873625462645L;
    /**
     * ID
     */
    private Long id;
    /**
     * 总金额
     */
    private BigDecimal amt;
    /**
     * 公司
     */
    private String company;
    /**
     * 开票人
     */
    private String drawer;
    /**
     * 发票编码
     */
    private String invoiceCode;
    /**
     * 开票日期
     */
    private String invoiceDate;
    /**
     * 发票号
     */
    private String invoiceNo;
    /**
     * 商品
     */
    private String item;
    /**
     * 发票备注
     */
    private String memo;
    /**
     * 收款人
     */
    private String payee;
    /**
     * 发票抬头
     */
    private String payer;
    /**
     * 数量
     */
    private Integer qty;
    /**
     * 单价
     */
    private BigDecimal unitPrice;
    /**
     * 仓库发票编码
     */
    private String wmsInvoiceCode;
    /**
     * 出入库状态
     */
    private WmsOrderStatusOms orderStatusOms;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_WH_ORDER_INVOICE_OMS", sequenceName = "S_T_WH_ORDER_INVOICE_OMS", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_WH_ORDER_INVOICE_OMS")
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

    @Column(name = "company")
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Column(name = "drawer")
    public String getDrawer() {
        return drawer;
    }

    public void setDrawer(String drawer) {
        this.drawer = drawer;
    }

    @Column(name = "invoice_Code")
    public String getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    @Column(name = "invoice_Date")
    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    @Column(name = "invoice_No")
    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    @Column(name = "item")
    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    @Column(name = "memo")
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Column(name = "payee")
    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    @Column(name = "payer")
    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    @Column(name = "qty")
    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    @Column(name = "unit_Price")
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Column(name = "wms_Invoice_Code")
    public String getWmsInvoiceCode() {
        return wmsInvoiceCode;
    }

    public void setWmsInvoiceCode(String wmsInvoiceCode) {
        this.wmsInvoiceCode = wmsInvoiceCode;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderstatus")
    @Index(name = "IDX_ORDER_STATUS")
    public WmsOrderStatusOms getOrderStatusOms() {
        return orderStatusOms;
    }

    public void setOrderStatusOms(WmsOrderStatusOms orderStatusOms) {
        this.orderStatusOms = orderStatusOms;
    }



}
