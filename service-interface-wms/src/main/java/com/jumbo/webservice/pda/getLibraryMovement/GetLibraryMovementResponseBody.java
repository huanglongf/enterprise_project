package com.jumbo.webservice.pda.getLibraryMovement;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.jumbo.webservice.pda.LibraryMovementInOut;


/**
 * <p>
 * getLibraryMovementResponseBody complex type的 Java 类。
 * 
 * <p>
 * 以下模式片段指定包含在此类中的预期内容。
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
@XmlType(name = "getLibraryMovementResponseBody", propOrder = {"code", "inbound", "outbound"})
public class GetLibraryMovementResponseBody implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 9111119323472941120L;
    @XmlElement(required = true)
    protected String code;
    @XmlElement(required = true)
    protected LibraryMovementInOut inbound;
    @XmlElement(required = true)
    protected LibraryMovementInOut outbound;

    /**
     * 获取code属性的值。
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置code属性的值。
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setCode(String value) {
        this.code = value;
    }

    /**
     * 获取inbound属性的值。
     * 
     * @return possible object is {@link LibraryMovementInOut }
     * 
     */
    public LibraryMovementInOut getInbound() {
        return inbound;
    }

    /**
     * 设置inbound属性的值。
     * 
     * @param value allowed object is {@link LibraryMovementInOut }
     * 
     */
    public void setInbound(LibraryMovementInOut value) {
        this.inbound = value;
    }

    /**
     * 获取outbound属性的值。
     * 
     * @return possible object is {@link LibraryMovementInOut }
     * 
     */
    public LibraryMovementInOut getOutbound() {
        return outbound;
    }

    /**
     * 设置outbound属性的值。
     * 
     * @param value allowed object is {@link LibraryMovementInOut }
     * 
     */
    public void setOutbound(LibraryMovementInOut value) {
        this.outbound = value;
    }

}
