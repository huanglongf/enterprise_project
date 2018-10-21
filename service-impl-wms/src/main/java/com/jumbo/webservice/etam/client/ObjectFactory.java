package com.jumbo.webservice.etam.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each Java content interface and Java element interface
 * generated in the com.jumbo.webservice.etam.client package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the Java representation
 * for XML content. The Java representation of XML content can consist of schema derived interfaces
 * and classes representing the binding of schema type definitions, element declarations and model
 * groups. Factory methods for each of these are provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _String_QNAME = new QName("http://www.bpl.com.cn/", "string");
    private final static QName _SecurityHeader_QNAME = new QName("http://www.bpl.com.cn/", "SecurityHeader");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes
     * for package: com.jumbo.webservice.etam.client
     * 
     */
    public ObjectFactory() {}

    /**
     * Create an instance of {@link OrderCancelResponse }
     * 
     */
    public OrderCancelResponse createOrderCancelResponse() {
        return new OrderCancelResponse();
    }

    /**
     * Create an instance of {@link SecurityHeader }
     * 
     */
    public SecurityHeader createSecurityHeader() {
        return new SecurityHeader();
    }

    /**
     * Create an instance of {@link OrderCancel }
     * 
     */
    public OrderCancel createOrderCancel() {
        return new OrderCancel();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://www.bpl.com.cn/http://www.bpl.com.cn/", name = "string")
    public JAXBElement<String> createString(String value) {
        return new JAXBElement<String>(_String_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SecurityHeader }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://www.bpl.com.cn/", name = "SecurityHeader")
    public JAXBElement<SecurityHeader> createSecurityHeader(SecurityHeader value) {
        return new JAXBElement<SecurityHeader>(_SecurityHeader_QNAME, SecurityHeader.class, null, value);
    }

}
