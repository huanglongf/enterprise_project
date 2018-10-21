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
 * 通用反馈发票头表
 *
 */
@Entity
@Table(name = "T_WH_INTFC_INVOICE_CFM")
public class IntfcInvoiceCfm extends BaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = -4561004316606855801L;
    /**
     * PK
     */
    private Long id;
    /**
     * 反馈头表ID
     */
    private IntfcCfm intfcId;
    /**
     * 发票抬头
     */
    private String invoiceCode;
    /**
     * 开票日期
     */
    private String invoiceDate;
    /**
     * 付款单位（发票抬头）
     */
    private String payer;
    /**
     * 商品
     */
    private String item;
    /**
     * 数量
     */
    private Integer qty;
    /**
     * 单价
     */
    private BigDecimal unitPrice;
    /**
     * 总金额
     */
    private BigDecimal amt;
    /**
     * 发票备注
     */
    private String memo;
    /**
     * 收款人
     */
    private String payee;
    /**
     * 开票人
     */
    private String drawer;
    /**
     * 导出次数
     */
    private Integer executecount;
    /**
     * 公司
     */
    private String company;

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
    @SequenceGenerator(name = "SEQ_WH_INTFC_INVOICE_CFM", sequenceName = "S_T_WH_INTFC_INVOICE_CFM", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WH_INTFC_INVOICE_CFM")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INTFC_ID")
    @Index(name = "FK_T_WH_INTFC_INVOICE_CFM")
    public IntfcCfm getIntfcId() {
        return intfcId;
    }

    public void setIntfcId(IntfcCfm intfcId) {
        this.intfcId = intfcId;
    }

    @Column(name = "INVOICE_DATE", length = 100)
    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    @Column(name = "payer", length = 100)
    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    @Column(name = "item", length = 100)
    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
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

    @Column(name = "amt")
    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    @Column(name = "memo", length = 100)
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Column(name = "payee", length = 100)
    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    @Column(name = "drawer", length = 100)
    public String getDrawer() {
        return drawer;
    }

    public void setDrawer(String drawer) {
        this.drawer = drawer;
    }

    @Column(name = "execute_count")
    public Integer getExecutecount() {
        return executecount;
    }

    public void setExecutecount(Integer executecount) {
        this.executecount = executecount;
    }

    @Column(name = "INVOICE_CODE", length = 40)
    public String getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    @Column(name = "COMPANY", length = 100)
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Column(name = "identification_number")
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
