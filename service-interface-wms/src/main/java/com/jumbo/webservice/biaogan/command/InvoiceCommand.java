package com.jumbo.webservice.biaogan.command;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "invoice")
public class InvoiceCommand implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -8602933908709380243L;
    @XmlElement(required = true, name = "invoicedate")
    private String invoicedate = "";
    @XmlElement(required = true, name = "paycompany")
    private String paycompany = "";
    @XmlElement(required = true, name = "project1")
    private String project1 = "";
    @XmlElement(required = true, name = "price1")
    private BigDecimal price1 = new BigDecimal(0);
    @XmlElement(required = true, name = "num1")
    private Long num1 = 0L;
    @XmlElement(required = true, name = "sum1")
    private BigDecimal sum1 = new BigDecimal(0);
    @XmlElement(required = true, name = "project2")
    private String project2 = "";
    @XmlElement(required = true, name = "price2")
    private BigDecimal price2 = new BigDecimal(0);
    @XmlElement(required = true, name = "num2")
    private Long num2 = 0L;
    @XmlElement(required = true, name = "sum2")
    private BigDecimal sum2 = new BigDecimal(0);
    @XmlElement(required = true, name = "project3")
    private String project3 = "";
    @XmlElement(required = true, name = "price3")
    private BigDecimal price3 = new BigDecimal(0);
    @XmlElement(required = true, name = "num3")
    private Long num3 = 0L;
    @XmlElement(required = true, name = "sum3")
    private BigDecimal sum3 = new BigDecimal(0);
    @XmlElement(required = true, name = "remark")
    private String remark = "";
    @XmlElement(required = true, name = "payee")
    private String payee = "";
    @XmlElement(required = true, name = "invoicewho")
    private String invoicewho = "";

    public String getInvoicedate() {
        return invoicedate;
    }

    public void setInvoicedate(String invoicedate) {
        this.invoicedate = invoicedate;
    }

    public String getPaycompany() {
        return paycompany;
    }

    public void setPaycompany(String paycompany) {
        this.paycompany = paycompany;
    }

    public String getProject1() {
        return project1;
    }

    public void setProject1(String project1) {
        this.project1 = project1;
    }

    public BigDecimal getPrice1() {
        return price1;
    }

    public void setPrice1(BigDecimal price1) {
        this.price1 = price1;
    }

    public Long getNum1() {
        return num1;
    }

    public void setNum1(Long num1) {
        this.num1 = num1;
    }

    public BigDecimal getSum1() {
        return sum1;
    }

    public void setSum1(BigDecimal sum1) {
        this.sum1 = sum1;
    }

    public String getProject2() {
        return project2;
    }

    public void setProject2(String project2) {
        this.project2 = project2;
    }

    public BigDecimal getPrice2() {
        return price2;
    }

    public void setPrice2(BigDecimal price2) {
        this.price2 = price2;
    }

    public Long getNum2() {
        return num2;
    }

    public void setNum2(Long num2) {
        this.num2 = num2;
    }

    public BigDecimal getSum2() {
        return sum2;
    }

    public void setSum2(BigDecimal sum2) {
        this.sum2 = sum2;
    }

    public String getProject3() {
        return project3;
    }

    public void setProject3(String project3) {
        this.project3 = project3;
    }

    public BigDecimal getPrice3() {
        return price3;
    }

    public void setPrice3(BigDecimal price3) {
        this.price3 = price3;
    }

    public Long getNum3() {
        return num3;
    }

    public void setNum3(Long num3) {
        this.num3 = num3;
    }

    public BigDecimal getSum3() {
        return sum3;
    }

    public void setSum3(BigDecimal sum3) {
        this.sum3 = sum3;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public String getInvoicewho() {
        return invoicewho;
    }

    public void setInvoicewho(String invoicewho) {
        this.invoicewho = invoicewho;
    }

}
