package com.jumbo.webservice.sfOrder;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each Java content interface and Java element interface
 * generated in the com.jumbo.webservice.sfOrder package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the Java representation
 * for XML content. The Java representation of XML content can consist of schema derived interfaces
 * and classes representing the binding of schema type definitions, element declarations and model
 * groups. Factory methods for each of these are provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ConfirmOrderServiceResponse_QNAME = new QName("http://service.serviceprovide.module.sf.com/", "confirmOrderServiceResponse");
    private final static QName _ConfirmOrderService_QNAME = new QName("http://service.serviceprovide.module.sf.com/", "confirmOrderService");
    private final static QName _FilterOrderServiceForB2CResponse_QNAME = new QName("http://service.serviceprovide.module.sf.com/", "filterOrderServiceForB2CResponse");
    private final static QName _FilterOrderServiceForB2C_QNAME = new QName("http://service.serviceprovide.module.sf.com/", "filterOrderServiceForB2C");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes
     * for package: com.jumbo.webservice.sfOrder
     * 
     */
    public ObjectFactory() {}

    /**
     * Create an instance of {@link ConfirmOrderServiceResponse }
     * 
     */
    public ConfirmOrderServiceResponse createConfirmOrderServiceResponse() {
        return new ConfirmOrderServiceResponse();
    }

    /**
     * Create an instance of {@link FilterOrderServiceForB2CResponse }
     * 
     */
    public FilterOrderServiceForB2CResponse createFilterOrderServiceForB2CResponse() {
        return new FilterOrderServiceForB2CResponse();
    }

    /**
     * Create an instance of {@link ConfirmOrderService }
     * 
     */
    public ConfirmOrderService createConfirmOrderService() {
        return new ConfirmOrderService();
    }

    /**
     * Create an instance of {@link FilterOrderServiceForB2C }
     * 
     */
    public FilterOrderServiceForB2C createFilterOrderServiceForB2C() {
        return new FilterOrderServiceForB2C();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConfirmOrderServiceResponse }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://service.serviceprovide.module.sf.com/", name = "confirmOrderServiceResponse")
    public JAXBElement<ConfirmOrderServiceResponse> createConfirmOrderServiceResponse(ConfirmOrderServiceResponse value) {
        return new JAXBElement<ConfirmOrderServiceResponse>(_ConfirmOrderServiceResponse_QNAME, ConfirmOrderServiceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConfirmOrderService }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://service.serviceprovide.module.sf.com/", name = "confirmOrderService")
    public JAXBElement<ConfirmOrderService> createConfirmOrderService(ConfirmOrderService value) {
        return new JAXBElement<ConfirmOrderService>(_ConfirmOrderService_QNAME, ConfirmOrderService.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FilterOrderServiceForB2CResponse }
     * {@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://service.serviceprovide.module.sf.com/", name = "filterOrderServiceForB2CResponse")
    public JAXBElement<FilterOrderServiceForB2CResponse> createFilterOrderServiceForB2CResponse(FilterOrderServiceForB2CResponse value) {
        return new JAXBElement<FilterOrderServiceForB2CResponse>(_FilterOrderServiceForB2CResponse_QNAME, FilterOrderServiceForB2CResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FilterOrderServiceForB2C }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://service.serviceprovide.module.sf.com/", name = "filterOrderServiceForB2C")
    public JAXBElement<FilterOrderServiceForB2C> createFilterOrderServiceForB2C(FilterOrderServiceForB2C value) {
        return new JAXBElement<FilterOrderServiceForB2C>(_FilterOrderServiceForB2C_QNAME, FilterOrderServiceForB2C.class, null, value);
    }

}
