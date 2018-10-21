
package com.jumbo.wms.webservice.pda;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getLibraryMovementResponseBody complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getLibraryMovementResponseBody">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="inbound" type="{http://webservice.jumbo.com/pda/}libraryMovementInOut"/>
 *         &lt;element name="outbound" type="{http://webservice.jumbo.com/pda/}libraryMovementInOut"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getLibraryMovementResponseBody", propOrder = {
    "code",
    "inbound",
    "outbound"
})
public class GetLibraryMovementResponseBody {

    @XmlElement(required = true)
    protected String code;
    @XmlElement(required = true)
    protected LibraryMovementInOut inbound;
    @XmlElement(required = true)
    protected LibraryMovementInOut outbound;

    /**
     * Gets the value of the code property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCode(String value) {
        this.code = value;
    }

    /**
     * Gets the value of the inbound property.
     * 
     * @return
     *     possible object is
     *     {@link LibraryMovementInOut }
     *     
     */
    public LibraryMovementInOut getInbound() {
        return inbound;
    }

    /**
     * Sets the value of the inbound property.
     * 
     * @param value
     *     allowed object is
     *     {@link LibraryMovementInOut }
     *     
     */
    public void setInbound(LibraryMovementInOut value) {
        this.inbound = value;
    }

    /**
     * Gets the value of the outbound property.
     * 
     * @return
     *     possible object is
     *     {@link LibraryMovementInOut }
     *     
     */
    public LibraryMovementInOut getOutbound() {
        return outbound;
    }

    /**
     * Sets the value of the outbound property.
     * 
     * @param value
     *     allowed object is
     *     {@link LibraryMovementInOut }
     *     
     */
    public void setOutbound(LibraryMovementInOut value) {
        this.outbound = value;
    }

}
