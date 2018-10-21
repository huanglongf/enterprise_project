package com.jumbo.webservice.sf;

import java.io.Serializable;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each Java content interface and Java element interface
 * generated in the com.jumbo.webservice.sf package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the Java representation
 * for XML content. The Java representation of XML content can consist of schema derived interfaces
 * and classes representing the binding of schema type definitions, element declarations and model
 * groups. Factory methods for each of these are provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2348887194115380843L;
    private final static QName _QueryMailnoDetailServiceResponse_QNAME = new QName("http://service.serviceprovide.module.sf.com/", "queryMailnoDetailServiceResponse");
    private final static QName _OrderMailBindingService_QNAME = new QName("http://service.serviceprovide.module.sf.com/", "orderMailBindingService");
    private final static QName _OrderMailBindingServiceResponse_QNAME = new QName("http://service.serviceprovide.module.sf.com/", "orderMailBindingServiceResponse");
    private final static QName _CancelOrderService_QNAME = new QName("http://service.serviceprovide.module.sf.com/", "cancelOrderService");
    private final static QName _QueryMailnoDetailService_QNAME = new QName("http://service.serviceprovide.module.sf.com/", "queryMailnoDetailService");
    private final static QName _CancelOrderServiceResponse_QNAME = new QName("http://service.serviceprovide.module.sf.com/", "cancelOrderServiceResponse");
    private final static QName _LogisticQueryStandardResponse_QNAME = new QName("http://service.serviceprovide.module.sf.com/", "logisticQueryStandardResponse");
    private final static QName _RouteTrackingService_QNAME = new QName("http://service.serviceprovide.module.sf.com/", "routeTrackingService");
    private final static QName _RouteTrackingServiceResponse_QNAME = new QName("http://service.serviceprovide.module.sf.com/", "routeTrackingServiceResponse");
    private final static QName _OrderService_QNAME = new QName("http://service.serviceprovide.module.sf.com/", "orderService");
    private final static QName _OrderServiceResponse_QNAME = new QName("http://service.serviceprovide.module.sf.com/", "orderServiceResponse");
    private final static QName _LogisticQueryStandard_QNAME = new QName("http://service.serviceprovide.module.sf.com/", "logisticQueryStandard");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes
     * for package: com.jumbo.webservice.sf
     * 
     */
    public ObjectFactory() {}

    /**
     * Create an instance of {@link CancelOrderServiceResponse }
     * 
     */
    public CancelOrderServiceResponse createCancelOrderServiceResponse() {
        return new CancelOrderServiceResponse();
    }

    /**
     * Create an instance of {@link OrderMailBindingService }
     * 
     */
    public OrderMailBindingService createOrderMailBindingService() {
        return new OrderMailBindingService();
    }

    /**
     * Create an instance of {@link OrderService }
     * 
     */
    public OrderService createOrderService() {
        return new OrderService();
    }

    /**
     * Create an instance of {@link OrderServiceResponse }
     * 
     */
    public OrderServiceResponse createOrderServiceResponse() {
        return new OrderServiceResponse();
    }

    /**
     * Create an instance of {@link RouteTrackingServiceResponse }
     * 
     */
    public RouteTrackingServiceResponse createRouteTrackingServiceResponse() {
        return new RouteTrackingServiceResponse();
    }

    /**
     * Create an instance of {@link QueryMailnoDetailServiceResponse }
     * 
     */
    public QueryMailnoDetailServiceResponse createQueryMailnoDetailServiceResponse() {
        return new QueryMailnoDetailServiceResponse();
    }

    /**
     * Create an instance of {@link RouteTrackingService }
     * 
     */
    public RouteTrackingService createRouteTrackingService() {
        return new RouteTrackingService();
    }

    /**
     * Create an instance of {@link LogisticQueryStandard }
     * 
     */
    public LogisticQueryStandard createLogisticQueryStandard() {
        return new LogisticQueryStandard();
    }

    /**
     * Create an instance of {@link QueryMailnoDetailService }
     * 
     */
    public QueryMailnoDetailService createQueryMailnoDetailService() {
        return new QueryMailnoDetailService();
    }

    /**
     * Create an instance of {@link OrderMailBindingServiceResponse }
     * 
     */
    public OrderMailBindingServiceResponse createOrderMailBindingServiceResponse() {
        return new OrderMailBindingServiceResponse();
    }

    /**
     * Create an instance of {@link CancelOrderService }
     * 
     */
    public CancelOrderService createCancelOrderService() {
        return new CancelOrderService();
    }

    /**
     * Create an instance of {@link LogisticQueryStandardResponse }
     * 
     */
    public LogisticQueryStandardResponse createLogisticQueryStandardResponse() {
        return new LogisticQueryStandardResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryMailnoDetailServiceResponse }
     * {@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://service.serviceprovide.module.sf.com/", name = "queryMailnoDetailServiceResponse")
    public JAXBElement<QueryMailnoDetailServiceResponse> createQueryMailnoDetailServiceResponse(QueryMailnoDetailServiceResponse value) {
        return new JAXBElement<QueryMailnoDetailServiceResponse>(_QueryMailnoDetailServiceResponse_QNAME, QueryMailnoDetailServiceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OrderMailBindingService }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://service.serviceprovide.module.sf.com/", name = "orderMailBindingService")
    public JAXBElement<OrderMailBindingService> createOrderMailBindingService(OrderMailBindingService value) {
        return new JAXBElement<OrderMailBindingService>(_OrderMailBindingService_QNAME, OrderMailBindingService.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OrderMailBindingServiceResponse }
     * {@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://service.serviceprovide.module.sf.com/", name = "orderMailBindingServiceResponse")
    public JAXBElement<OrderMailBindingServiceResponse> createOrderMailBindingServiceResponse(OrderMailBindingServiceResponse value) {
        return new JAXBElement<OrderMailBindingServiceResponse>(_OrderMailBindingServiceResponse_QNAME, OrderMailBindingServiceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelOrderService }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://service.serviceprovide.module.sf.com/", name = "cancelOrderService")
    public JAXBElement<CancelOrderService> createCancelOrderService(CancelOrderService value) {
        return new JAXBElement<CancelOrderService>(_CancelOrderService_QNAME, CancelOrderService.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryMailnoDetailService }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://service.serviceprovide.module.sf.com/", name = "queryMailnoDetailService")
    public JAXBElement<QueryMailnoDetailService> createQueryMailnoDetailService(QueryMailnoDetailService value) {
        return new JAXBElement<QueryMailnoDetailService>(_QueryMailnoDetailService_QNAME, QueryMailnoDetailService.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelOrderServiceResponse }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://service.serviceprovide.module.sf.com/", name = "cancelOrderServiceResponse")
    public JAXBElement<CancelOrderServiceResponse> createCancelOrderServiceResponse(CancelOrderServiceResponse value) {
        return new JAXBElement<CancelOrderServiceResponse>(_CancelOrderServiceResponse_QNAME, CancelOrderServiceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LogisticQueryStandardResponse }
     * {@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://service.serviceprovide.module.sf.com/", name = "logisticQueryStandardResponse")
    public JAXBElement<LogisticQueryStandardResponse> createLogisticQueryStandardResponse(LogisticQueryStandardResponse value) {
        return new JAXBElement<LogisticQueryStandardResponse>(_LogisticQueryStandardResponse_QNAME, LogisticQueryStandardResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RouteTrackingService }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://service.serviceprovide.module.sf.com/", name = "routeTrackingService")
    public JAXBElement<RouteTrackingService> createRouteTrackingService(RouteTrackingService value) {
        return new JAXBElement<RouteTrackingService>(_RouteTrackingService_QNAME, RouteTrackingService.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RouteTrackingServiceResponse }
     * {@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://service.serviceprovide.module.sf.com/", name = "routeTrackingServiceResponse")
    public JAXBElement<RouteTrackingServiceResponse> createRouteTrackingServiceResponse(RouteTrackingServiceResponse value) {
        return new JAXBElement<RouteTrackingServiceResponse>(_RouteTrackingServiceResponse_QNAME, RouteTrackingServiceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OrderService }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://service.serviceprovide.module.sf.com/", name = "orderService")
    public JAXBElement<OrderService> createOrderService(OrderService value) {
        return new JAXBElement<OrderService>(_OrderService_QNAME, OrderService.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OrderServiceResponse }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://service.serviceprovide.module.sf.com/", name = "orderServiceResponse")
    public JAXBElement<OrderServiceResponse> createOrderServiceResponse(OrderServiceResponse value) {
        return new JAXBElement<OrderServiceResponse>(_OrderServiceResponse_QNAME, OrderServiceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LogisticQueryStandard }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://service.serviceprovide.module.sf.com/", name = "logisticQueryStandard")
    public JAXBElement<LogisticQueryStandard> createLogisticQueryStandard(LogisticQueryStandard value) {
        return new JAXBElement<LogisticQueryStandard>(_LogisticQueryStandard_QNAME, LogisticQueryStandard.class, null, value);
    }

}
