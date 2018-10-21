package com.jumbo.mq;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class FlowInvoicePrintMsg implements Serializable {


    /**
	 * 
	 */
    private static final long serialVersionUID = 5871367206708767987L;

    /**
     * 开票日期 税控机里发票打印日期
     */
    private String printDate;

    /**
     * 发票类型 正常: 表示蓝票 退票:表示红冲发票
     */
    private String invoiceType;

    /**
     * 发票代码 12位长度
     */
    private String invoiceCode;

    /**
     * 实际发票号 ８位长度且唯一
     */
    private String invoiceNo;

    /**
     * 收款单位 上海宝尊电子商务有限公司
     */
    private String payeeCompany;

    /**
     * 付款人 １）红冲发票该字段为：所红冲的蓝票发票代码＋该蓝票的发票号 ２）蓝票该列内容为：付款人
     */
    private String payer;

    /**
     * 开票金额 蓝票为正，红票为负
     */
    private BigDecimal amt;

    /**
     * 开票人
     */
    private String drawer;

    /**
     * 开票人
     */
    private String payee;

    /**
     * 开票机设备号
     */
    private String deviceNo;

    /**
     * 备注信息 红票：该字段为空 蓝票：该字段为系统发票编码/商品明细信息
     */
    private String memo;

    public String getPrintDate() {
        return printDate;
    }

    public void setPrintDate(String printDate) {
        this.printDate = printDate;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getPayeeCompany() {
        return payeeCompany;
    }

    public void setPayeeCompany(String payeeCompany) {
        this.payeeCompany = payeeCompany;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    public String getDrawer() {
        return drawer;
    }

    public void setDrawer(String drawer) {
        this.drawer = drawer;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

}
