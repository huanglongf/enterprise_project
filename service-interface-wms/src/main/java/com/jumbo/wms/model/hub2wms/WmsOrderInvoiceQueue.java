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
 * HUB2WMS过仓 订单发票信息
 * 
 * @author jinlong.ke
 * 
 */
@Entity
@Table(name = "T_WH_ORDER_INVOICE")
public class WmsOrderInvoiceQueue extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 2702767999050409063L;
    /*
     * PK
     */
    private Long id;
    /*
     * 开票日期
     */
    private String invoiceDate;
    /*
     * 发票抬头
     */
    private String payer;
    /*
     * 发票编码
     */
    private String invoiceCode;
    /*
     * 仓库发票编码
     */
    private String wmsInvoiceCode;
    /*
     * 商品
     */
    private String item;
    /*
     * 发票号
     */
    private String invoiceNo;
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
     * 发票备注
     */
    private String memo;
    /*
     * 收款人
     */
    private String payee;
    /*
     * 公司
     */
    private String company;
    /*
     * 开票人
     */
    private String drawer;
    /*
     * 销售订单头
     */
    private WmsSalesOrderQueue wmsSalesOrderQueue;
    /*
     * 退货红票
     */
    private WmsRtnInOrderQueue wmsRtnInOrderQueue;
    /*
     * 退货蓝票
     */
    private WmsRtnInOrderQueue wmsRtnInOrderQueue1;

    /**
     * 付款单位纳税人识别号
     */
    private String identificationNumber;

    /**
     * 地址
     */
    private String address;

    /**
     * 电话
     */
    private String telephone;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_WH_ORDER_INVOICE", sequenceName = "S_T_WH_ORDER_INVOICE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_WH_ORDER_INVOICE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "INVOICE_DATE", length = 20)
    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    @Column(name = "PAYER", length = 50)
    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    @Column(name = "INVOICE_CODE", length = 40)
    public String getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    @Column(name = "WMS_INVOICE_CODE", length = 50)
    public String getWmsInvoiceCode() {
        return wmsInvoiceCode;
    }

    public void setWmsInvoiceCode(String wmsInvoiceCode) {
        this.wmsInvoiceCode = wmsInvoiceCode;
    }

    @Column(name = "ITEM", length = 300)
    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    @Column(name = "INVOICE_NO", length = 100)
    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    @Column(name = "qty", precision = 15)
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

    @Column(name = "MEMO", length = 300)
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Column(name = "PAYEE", length = 100)
    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    @Column(name = "COMPANY", length = 100)
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Column(name = "DRAWER", length = 50)
    public String getDrawer() {
        return drawer;
    }

    public void setDrawer(String drawer) {
        this.drawer = drawer;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SO_ID")
    @Index(name = "IDX_INVOICE_SO_ID")
    public WmsSalesOrderQueue getWmsSalesOrderQueue() {
        return wmsSalesOrderQueue;
    }

    public void setWmsSalesOrderQueue(WmsSalesOrderQueue wmsSalesOrderQueue) {
        this.wmsSalesOrderQueue = wmsSalesOrderQueue;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RO_ID_B")
    @Index(name = "IDX_INVOICE_RO_ID_B")
    public WmsRtnInOrderQueue getWmsRtnInOrderQueue() {
        return wmsRtnInOrderQueue;
    }

    public void setWmsRtnInOrderQueue(WmsRtnInOrderQueue wmsRtnInOrderQueue) {
        this.wmsRtnInOrderQueue = wmsRtnInOrderQueue;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RO_ID_R")
    @Index(name = "IDX_INVOICE_RO_ID_R")
    public WmsRtnInOrderQueue getWmsRtnInOrderQueue1() {
        return wmsRtnInOrderQueue1;
    }

    public void setWmsRtnInOrderQueue1(WmsRtnInOrderQueue wmsRtnInOrderQueue1) {
        this.wmsRtnInOrderQueue1 = wmsRtnInOrderQueue1;
    }

    @Column(name = "identification_Number")
    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "telephone")
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

}
