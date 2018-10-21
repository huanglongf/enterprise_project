package com.jumbo.webservice.biaogan.client.base;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>
 * Java class for WmsResultInfoToERP complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="WmsResultInfoToERP">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="resultInfo" type="{http://server.webservices.chamayi.chamago.com}ArrayOfString" minOccurs="0"/>
 *         &lt;element name="returnCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="returnDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="returnFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WmsResultInfoToERP", namespace = "http://bean.server.webservices.chamayi.chamago.com", propOrder = {"resultInfo", "returnCode", "returnDesc", "returnFlag"})
public class WmsResultInfoToERP {

    @XmlElementRef(name = "resultInfo", namespace = "http://bean.server.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<ArrayOfString> resultInfo;
    @XmlElementRef(name = "returnCode", namespace = "http://bean.server.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> returnCode;
    @XmlElementRef(name = "returnDesc", namespace = "http://bean.server.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> returnDesc;
    @XmlElementRef(name = "returnFlag", namespace = "http://bean.server.webservices.chamayi.chamago.com", type = JAXBElement.class)
    protected JAXBElement<String> returnFlag;

    /**
     * Gets the value of the resultInfo property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link ArrayOfString }{@code >}
     * 
     */
    public JAXBElement<ArrayOfString> getResultInfo() {
        return resultInfo;
    }

    /**
     * Sets the value of the resultInfo property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link ArrayOfString }{@code >}
     * 
     */
    public void setResultInfo(JAXBElement<ArrayOfString> value) {
        this.resultInfo = value;
    }

    /**
     * Gets the value of the returnCode property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getReturnCode() {
        return returnCode;
    }

    /**
     * Sets the value of the returnCode property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setReturnCode(JAXBElement<String> value) {
        this.returnCode = value;
    }

    /**
     * Gets the value of the returnDesc property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getReturnDesc() {
        return returnDesc;
    }

    /**
     * Sets the value of the returnDesc property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setReturnDesc(JAXBElement<String> value) {
        this.returnDesc = value;
    }

    /**
     * Gets the value of the returnFlag property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getReturnFlag() {
        return returnFlag;
    }

    /**
     * Sets the value of the returnFlag property.
     * 
     * @param value allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setReturnFlag(JAXBElement<String> value) {
        this.returnFlag = value;
    }

}
