package com.jumbo.webservice.etam.client;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;


/**
 * <p>
 * Java class for SecurityHeader complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SecurityHeader">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SecurityKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;anyAttribute/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SecurityHeader", propOrder = {"securityKey"})
public class SecurityHeader {

    @XmlElement(name = "SecurityKey")
    protected String securityKey;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    /**
     * Gets the value of the securityKey property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getSecurityKey() {
        return securityKey;
    }

    /**
     * Sets the value of the securityKey property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setSecurityKey(String value) {
        this.securityKey = value;
    }

    /**
     * Gets a map that contains attributes that aren't bound to any typed property on this class.
     * 
     * <p>
     * the map is keyed by the name of the attribute and the value is the string value of the
     * attribute.
     * 
     * the map returned by this method is live, and you can add new attribute by updating the map
     * directly. Because of this design, there's no setter.
     * 
     * 
     * @return always non-null
     */
    public Map<QName, String> getOtherAttributes() {
        return otherAttributes;
    }

}
