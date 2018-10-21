package com.jumbo.wms.model.command.vmi.esprit.xml.espDispatch;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>
 * Java class for anonymous complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}company"/>
 *         &lt;element ref="{}location"/>
 *         &lt;element ref="{}pickType"/>
 *         &lt;element ref="{}pickNo"/>
 *         &lt;element ref="{}fromLocation"/>
 *         &lt;element ref="{}orderFlag"/>
 *         &lt;element ref="{}reqDeliveryDate"/>
 *         &lt;element ref="{}latestReqDeliveryDate"/>
 *         &lt;element ref="{}promotionStartDate"/>
 *         &lt;element ref="{}ediStatus"/>
 *         &lt;element ref="{}routeCode"/>
 *         &lt;element ref="{}remark1"/>
 *         &lt;element ref="{}remark2"/>
 *         &lt;element ref="{}remark3"/>
 *         &lt;element ref="{}remark4"/>
 *         &lt;element ref="{}fromLocGLN"/>
 *         &lt;element ref="{}toLocGLN"/>
 *         &lt;element ref="{}checkDigit"/>
 *         &lt;element ref="{}items"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"company", "location", "pickType", "pickNo", "fromLocation", "orderFlag", "reqDeliveryDate", "latestReqDeliveryDate", "promotionStartDate", "ediStatus", "routeCode", "remark1", "remark2", "remark3", "remark4",
        "fromLocGLN", "toLocGLN", "checkDigit", "items"})
@XmlRootElement(name = "pick")
public class Pick implements Serializable {


    private static final long serialVersionUID = -3509644986936311435L;
    @XmlElement(required = true)
    protected String company;
    @XmlElement(required = true)
    protected String location;
    @XmlElement(required = true)
    protected String pickType;
    @XmlElement(required = true)
    protected String pickNo;
    @XmlElement(required = true)
    protected String fromLocation;
    @XmlElement(required = true)
    protected String orderFlag;
    @XmlElement(required = true)
    protected String reqDeliveryDate;
    @XmlElement(required = true)
    protected String latestReqDeliveryDate;
    @XmlElement(required = true)
    protected String promotionStartDate;
    @XmlElement(required = true)
    protected String ediStatus;
    @XmlElement(required = true)
    protected String routeCode;
    @XmlElement(required = true)
    protected String remark1;
    @XmlElement(required = true)
    protected String remark2;
    @XmlElement(required = true)
    protected String remark3;
    @XmlElement(required = true)
    protected String remark4;
    @XmlElement(required = true)
    protected String fromLocGLN;
    @XmlElement(required = true)
    protected String toLocGLN;
    @XmlElement(required = true)
    protected String checkDigit;
    @XmlElement(required = true)
    protected Items items;

    /**
     * Gets the value of the company property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getCompany() {
        return company;
    }

    /**
     * Sets the value of the company property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setCompany(String value) {
        this.company = value;
    }

    /**
     * Gets the value of the location property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the value of the location property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setLocation(String value) {
        this.location = value;
    }

    /**
     * Gets the value of the pickType property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getPickType() {
        return pickType;
    }

    /**
     * Sets the value of the pickType property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setPickType(String value) {
        this.pickType = value;
    }

    /**
     * Gets the value of the pickNo property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getPickNo() {
        return pickNo;
    }

    /**
     * Sets the value of the pickNo property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setPickNo(String value) {
        this.pickNo = value;
    }

    /**
     * Gets the value of the fromLocation property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFromLocation() {
        return fromLocation;
    }

    /**
     * Sets the value of the fromLocation property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFromLocation(String value) {
        this.fromLocation = value;
    }

    /**
     * Gets the value of the orderFlag property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getOrderFlag() {
        return orderFlag;
    }

    /**
     * Sets the value of the orderFlag property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setOrderFlag(String value) {
        this.orderFlag = value;
    }

    /**
     * Gets the value of the reqDeliveryDate property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getReqDeliveryDate() {
        return reqDeliveryDate;
    }

    /**
     * Sets the value of the reqDeliveryDate property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setReqDeliveryDate(String value) {
        this.reqDeliveryDate = value;
    }

    /**
     * Gets the value of the latestReqDeliveryDate property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getLatestReqDeliveryDate() {
        return latestReqDeliveryDate;
    }

    /**
     * Sets the value of the latestReqDeliveryDate property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setLatestReqDeliveryDate(String value) {
        this.latestReqDeliveryDate = value;
    }

    /**
     * Gets the value of the promotionStartDate property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getPromotionStartDate() {
        return promotionStartDate;
    }

    /**
     * Sets the value of the promotionStartDate property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setPromotionStartDate(String value) {
        this.promotionStartDate = value;
    }

    /**
     * Gets the value of the ediStatus property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getEdiStatus() {
        return ediStatus;
    }

    /**
     * Sets the value of the ediStatus property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setEdiStatus(String value) {
        this.ediStatus = value;
    }

    /**
     * Gets the value of the routeCode property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getRouteCode() {
        return routeCode;
    }

    /**
     * Sets the value of the routeCode property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setRouteCode(String value) {
        this.routeCode = value;
    }

    /**
     * Gets the value of the remark1 property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getRemark1() {
        return remark1;
    }

    /**
     * Sets the value of the remark1 property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setRemark1(String value) {
        this.remark1 = value;
    }

    /**
     * Gets the value of the remark2 property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getRemark2() {
        return remark2;
    }

    /**
     * Sets the value of the remark2 property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setRemark2(String value) {
        this.remark2 = value;
    }

    /**
     * Gets the value of the remark3 property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getRemark3() {
        return remark3;
    }

    /**
     * Sets the value of the remark3 property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setRemark3(String value) {
        this.remark3 = value;
    }

    /**
     * Gets the value of the remark4 property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getRemark4() {
        return remark4;
    }

    /**
     * Sets the value of the remark4 property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setRemark4(String value) {
        this.remark4 = value;
    }

    /**
     * Gets the value of the fromLocGLN property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFromLocGLN() {
        return fromLocGLN;
    }

    /**
     * Sets the value of the fromLocGLN property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFromLocGLN(String value) {
        this.fromLocGLN = value;
    }

    /**
     * Gets the value of the toLocGLN property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getToLocGLN() {
        return toLocGLN;
    }

    /**
     * Sets the value of the toLocGLN property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setToLocGLN(String value) {
        this.toLocGLN = value;
    }

    /**
     * Gets the value of the checkDigit property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getCheckDigit() {
        return checkDigit;
    }

    /**
     * Sets the value of the checkDigit property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setCheckDigit(String value) {
        this.checkDigit = value;
    }

    /**
     * Gets the value of the items property.
     * 
     * @return possible object is {@link Items }
     * 
     */
    public Items getItems() {
        return items;
    }

    /**
     * Sets the value of the items property.
     * 
     * @param value allowed object is {@link Items }
     * 
     */
    public void setItems(Items value) {
        this.items = value;
    }

}
