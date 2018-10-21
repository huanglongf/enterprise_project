package com.jumbo.webservice.ttk;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}ecCompanyId"/>
 *         &lt;element ref="{}logisticProviderID"/>
 *         &lt;element ref="{}customerId"/>
 *         &lt;element ref="{}txLogisticID"/>
 *         &lt;element ref="{}tradeNo"/>
 *         &lt;element ref="{}mailNo"/>
 *         &lt;element ref="{}totalServiceFee"/>
 *         &lt;element ref="{}codSplitFee"/>
 *         &lt;element ref="{}buyServiceFee"/>
 *         &lt;element ref="{}orderType"/>
 *         &lt;element ref="{}serviceType"/>
 *         &lt;element ref="{}sender"/>
 *         &lt;element ref="{}receiver"/>
 *         &lt;element ref="{}sendStartTime"/>
 *         &lt;element ref="{}sendEndTime"/>
 *         &lt;element ref="{}goodsValue"/>
 *         &lt;element ref="{}itemsValue"/>
 *         &lt;element ref="{}items"/>
 *         &lt;element ref="{}special"/>
 *         &lt;element ref="{}remark"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "ecCompanyId",
    "logisticProviderID",
    "customerId",
    "txLogisticID",
    "tradeNo",
    "mailNo",
    "totalServiceFee",
    "codSplitFee",
    "buyServiceFee",
    "orderType",
    "serviceType",
    "sender",
    "receiver",
    "sendStartTime",
    "sendEndTime",
    "goodsValue",
    "itemsValue",
    "items",
    "special",
    "remark"
})
@XmlRootElement(name = "RequestOrder")
public class RequestOrder {

    @XmlElement(required = true)
    protected String ecCompanyId;
    @XmlElement(required = true)
    protected String logisticProviderID;
    @XmlElement(required = true)
    protected String customerId;
    @XmlElement(required = true)
    protected String txLogisticID;
    @XmlElement(required = true)
    protected String tradeNo;
    @XmlElement(required = true)
    protected String mailNo;
    @XmlElement(required = true)
    protected BigDecimal totalServiceFee;
    @XmlElement(required = true)
    protected BigDecimal codSplitFee;
    @XmlElement(required = true)
    protected BigDecimal buyServiceFee;
    protected int orderType;
    protected Long serviceType;
    @XmlElement(required = true)
    protected Sender sender;
    @XmlElement(required = true)
    protected Receiver receiver;
    @XmlElement(required = true)
    protected String sendStartTime;
    @XmlElement(required = true)
    protected String sendEndTime;
    @XmlElement(required = true)
    protected BigDecimal goodsValue;
    @XmlElement(required = true)
    protected BigDecimal itemsValue;
    @XmlElement(required = true)
    protected Items items;
    protected int special;
    @XmlElement(required = true)
    protected String remark;

    /**
     * Gets the value of the ecCompanyId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEcCompanyId() {
        return ecCompanyId;
    }

    /**
     * Sets the value of the ecCompanyId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEcCompanyId(String value) {
        this.ecCompanyId = value;
    }

    /**
     * Gets the value of the logisticProviderID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLogisticProviderID() {
        return logisticProviderID;
    }

    /**
     * Sets the value of the logisticProviderID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLogisticProviderID(String value) {
        this.logisticProviderID = value;
    }

    /**
     * Gets the value of the customerId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * Sets the value of the customerId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerId(String value) {
        this.customerId = value;
    }

    /**
     * Gets the value of the txLogisticID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTxLogisticID() {
        return txLogisticID;
    }

    /**
     * Sets the value of the txLogisticID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTxLogisticID(String value) {
        this.txLogisticID = value;
    }

    /**
     * Gets the value of the tradeNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTradeNo() {
        return tradeNo;
    }

    /**
     * Sets the value of the tradeNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTradeNo(String value) {
        this.tradeNo = value;
    }

    /**
     * Gets the value of the mailNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMailNo() {
        return mailNo;
    }

    /**
     * Sets the value of the mailNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMailNo(String value) {
        this.mailNo = value;
    }

    /**
     * Gets the value of the totalServiceFee property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotalServiceFee() {
        return totalServiceFee;
    }

    /**
     * Sets the value of the totalServiceFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotalServiceFee(BigDecimal value) {
        this.totalServiceFee = value;
    }

    /**
     * Gets the value of the codSplitFee property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCodSplitFee() {
        return codSplitFee;
    }

    /**
     * Sets the value of the codSplitFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCodSplitFee(BigDecimal value) {
        this.codSplitFee = value;
    }

    /**
     * Gets the value of the buyServiceFee property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBuyServiceFee() {
        return buyServiceFee;
    }

    /**
     * Sets the value of the buyServiceFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBuyServiceFee(BigDecimal value) {
        this.buyServiceFee = value;
    }

    /**
     * Gets the value of the orderType property.
     * 
     */
    public int getOrderType() {
        return orderType;
    }

    /**
     * Sets the value of the orderType property.
     * 
     */
    public void setOrderType(int value) {
        this.orderType = value;
    }

    /**
     * Gets the value of the serviceType property.
     * 
     */
    public Long getServiceType() {
        return serviceType;
    }

    /**
     * Sets the value of the serviceType property.
     * 
     */
    public void setServiceType(Long value) {
        this.serviceType = value;
    }

    /**
     * Gets the value of the sender property.
     * 
     * @return
     *     possible object is
     *     {@link Sender }
     *     
     */
    public Sender getSender() {
        return sender;
    }

    /**
     * Sets the value of the sender property.
     * 
     * @param value
     *     allowed object is
     *     {@link Sender }
     *     
     */
    public void setSender(Sender value) {
        this.sender = value;
    }

    /**
     * Gets the value of the receiver property.
     * 
     * @return
     *     possible object is
     *     {@link Receiver }
     *     
     */
    public Receiver getReceiver() {
        return receiver;
    }

    /**
     * Sets the value of the receiver property.
     * 
     * @param value
     *     allowed object is
     *     {@link Receiver }
     *     
     */
    public void setReceiver(Receiver value) {
        this.receiver = value;
    }

    /**
     * Gets the value of the sendStartTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSendStartTime() {
        return sendStartTime;
    }

    /**
     * Sets the value of the sendStartTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSendStartTime(String value) {
        this.sendStartTime = value;
    }

    /**
     * Gets the value of the sendEndTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSendEndTime() {
        return sendEndTime;
    }

    /**
     * Sets the value of the sendEndTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSendEndTime(String value) {
        this.sendEndTime = value;
    }

    /**
     * Gets the value of the goodsValue property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getGoodsValue() {
        return goodsValue;
    }

    /**
     * Sets the value of the goodsValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setGoodsValue(BigDecimal value) {
        this.goodsValue = value;
    }

    /**
     * Gets the value of the itemsValue property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getItemsValue() {
        return itemsValue;
    }

    /**
     * Sets the value of the itemsValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setItemsValue(BigDecimal value) {
        this.itemsValue = value;
    }

    /**
     * Gets the value of the items property.
     * 
     * @return
     *     possible object is
     *     {@link Items }
     *     
     */
    public Items getItems() {
        return items;
    }

    /**
     * Sets the value of the items property.
     * 
     * @param value
     *     allowed object is
     *     {@link Items }
     *     
     */
    public void setItems(Items value) {
        this.items = value;
    }

    /**
     * Gets the value of the special property.
     * 
     */
    public int getSpecial() {
        return special;
    }

    /**
     * Sets the value of the special property.
     * 
     */
    public void setSpecial(int value) {
        this.special = value;
    }

    /**
     * Gets the value of the remark property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemark() {
        return remark;
    }

    /**
     * Sets the value of the remark property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemark(String value) {
        this.remark = value;
    }

}
