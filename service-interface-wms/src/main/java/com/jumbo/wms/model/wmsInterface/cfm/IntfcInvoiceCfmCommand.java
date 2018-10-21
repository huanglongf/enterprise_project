package com.jumbo.wms.model.wmsInterface.cfm;

import java.math.BigDecimal;
import java.util.List;

import com.jumbo.wms.model.BaseModel;

/**
 * 通用反馈发票头
 *
 */
public class IntfcInvoiceCfmCommand extends BaseModel {
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

    /**
     * 发票明细
     */
    private List<IntfcInvoiceLineCfmCommand> iilcList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public IntfcCfm getIntfcId() {
        return intfcId;
    }

    public void setIntfcId(IntfcCfm intfcId) {
        this.intfcId = intfcId;
    }

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public String getDrawer() {
        return drawer;
    }

    public void setDrawer(String drawer) {
        this.drawer = drawer;
    }

    public Integer getExecutecount() {
        return executecount;
    }

    public void setExecutecount(Integer executecount) {
        this.executecount = executecount;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public List<IntfcInvoiceLineCfmCommand> getIilcList() {
        return iilcList;
    }

    public void setIilcList(List<IntfcInvoiceLineCfmCommand> iilcList) {
        this.iilcList = iilcList;
    }



}
