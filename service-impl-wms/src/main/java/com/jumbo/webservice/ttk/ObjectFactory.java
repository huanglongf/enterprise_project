package com.jumbo.webservice.ttk;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.jumbo.webservice.ttk package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Reason_QNAME = new QName("", "reason");
    private final static QName _TxLogisticID_QNAME = new QName("", "txLogisticID");
    private final static QName _LogisticProviderID_QNAME = new QName("", "logisticProviderID");
    private final static QName _Success_QNAME = new QName("", "success");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.jumbo.webservice.ttk
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Responses }
     * 
     */
    public Responses createResponses() {
        return new Responses();
    }

    /**
     * Create an instance of {@link ResponseItems }
     * 
     */
    public ResponseItems createResponseItems() {
        return new ResponseItems();
    }

    /**
     * Create an instance of {@link Response }
     * 
     */
    public Response createResponse() {
        return new Response();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "reason")
    public JAXBElement<String> createReason(String value) {
        return new JAXBElement<String>(_Reason_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "txLogisticID")
    public JAXBElement<String> createTxLogisticID(String value) {
        return new JAXBElement<String>(_TxLogisticID_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "logisticProviderID")
    public JAXBElement<String> createLogisticProviderID(String value) {
        return new JAXBElement<String>(_LogisticProviderID_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "success")
    public JAXBElement<Boolean> createSuccess(Boolean value) {
        return new JAXBElement<Boolean>(_Success_QNAME, Boolean.class, null, value);
    }

}
