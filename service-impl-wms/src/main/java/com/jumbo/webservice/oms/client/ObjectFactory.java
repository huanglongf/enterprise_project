package com.jumbo.webservice.oms.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each Java content interface and Java element interface
 * generated in the com.jumbo.webservice.oms.client package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the Java representation
 * for XML content. The Java representation of XML content can consist of schema derived interfaces
 * and classes representing the binding of schema type definitions, element declarations and model
 * groups. Factory methods for each of these are provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ApproveRAForOuterWMSArgs0_QNAME = new QName("http://scm.webservice.service.erry.com", "args0");
    private final static QName _ApproveRAForOuterWMSResponseReturn_QNAME = new QName("http://scm.webservice.service.erry.com", "return");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes
     * for package: com.jumbo.webservice.oms.client
     * 
     */
    public ObjectFactory() {}

    /**
     * Create an instance of {@link ApproveRAForOuterWMS }
     * 
     */
    public ApproveRAForOuterWMS createApproveRAForOuterWMS() {
        return new ApproveRAForOuterWMS();
    }

    /**
     * Create an instance of {@link ApproveRAForOuterWMSResponse }
     * 
     */
    public ApproveRAForOuterWMSResponse createApproveRAForOuterWMSResponse() {
        return new ApproveRAForOuterWMSResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://scm.webservice.service.erry.com", name = "args0", scope = ApproveRAForOuterWMS.class)
    public JAXBElement<String> createApproveRAForOuterWMSArgs0(String value) {
        return new JAXBElement<String>(_ApproveRAForOuterWMSArgs0_QNAME, String.class, ApproveRAForOuterWMS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://scm.webservice.service.erry.com", name = "return", scope = ApproveRAForOuterWMSResponse.class)
    public JAXBElement<String> createApproveRAForOuterWMSResponseReturn(String value) {
        return new JAXBElement<String>(_ApproveRAForOuterWMSResponseReturn_QNAME, String.class, ApproveRAForOuterWMSResponse.class, value);
    }

}
