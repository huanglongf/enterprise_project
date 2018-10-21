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
 *         &lt;element ref="{}header"/>
 *         &lt;element ref="{}list"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"header", "list"})
@XmlRootElement(name = "espDispatch")
public class EspDispatch implements Serializable {


    private static final long serialVersionUID = -5298043404030268460L;
    @XmlElement(required = true)
    protected Header header;
    @XmlElement(required = true)
    protected List list;

    /**
     * Gets the value of the header property.
     * 
     * @return possible object is {@link Header }
     * 
     */
    public Header getHeader() {
        return header;
    }

    /**
     * Sets the value of the header property.
     * 
     * @param value allowed object is {@link Header }
     * 
     */
    public void setHeader(Header value) {
        this.header = value;
    }

    /**
     * Gets the value of the list property.
     * 
     * @return possible object is {@link List }
     * 
     */
    public List getList() {
        return list;
    }

    /**
     * Sets the value of the list property.
     * 
     * @param value allowed object is {@link List }
     * 
     */
    public void setList(List value) {
        this.list = value;
    }

}
