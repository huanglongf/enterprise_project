package com.jumbo.mq;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 订单支付明细信息
 * 
 * @author dzz
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "mqsotendermsg")
public class MqSoTenderMsg implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = -2425672041006371871L;

    /**
     * 付款方式
     */
    private String paymentType;

    /**
     * 支付金额
     */
    private BigDecimal payTotal;

    /**
     * 实际支付金额
     */
    private BigDecimal payActual;

    /**
     * 支付帐号
     */
    private String payAccount;

    /**
     * 支付流水
     */
    private String payNo;

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public BigDecimal getPayTotal() {
        return payTotal;
    }

    public void setPayTotal(BigDecimal payTotal) {
        this.payTotal = payTotal;
    }

    public BigDecimal getPayActual() {
        return payActual;
    }

    public void setPayActual(BigDecimal payActual) {
        this.payActual = payActual;
    }

    public String getPayAccount() {
        return payAccount;
    }

    public void setPayAccount(String payAccount) {
        this.payAccount = payAccount;
    }

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }

}
