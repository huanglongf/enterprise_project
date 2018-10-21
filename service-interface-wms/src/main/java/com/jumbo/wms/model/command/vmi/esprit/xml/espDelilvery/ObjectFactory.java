package com.jumbo.wms.model.command.vmi.esprit.xml.espDelilvery;

import java.io.Serializable;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each Java content interface and Java element interface
 * generated in the com.jumbo.wms.model.command.vmi.esprit.xml.espDelilvery package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the Java representation
 * for XML content. The Java representation of XML content can consist of schema derived interfaces
 * and classes representing the binding of schema type definitions, element declarations and model
 * groups. Factory methods for each of these are provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory implements Serializable {


    private static final long serialVersionUID = -8268400646868848393L;
    private final static QName _FromNode_QNAME = new QName("http://www.esprit.com.cn/XMLSchema/eShopInterface/espDelivery.xsd", "fromNode");
    private final static QName _FromGLN_QNAME = new QName("http://www.esprit.com.cn/XMLSchema/eShopInterface/espDelivery.xsd", "fromGLN");
    private final static QName _GoodsReceiptDate_QNAME = new QName("http://www.esprit.com.cn/XMLSchema/eShopInterface/espDelivery.xsd", "goodsReceiptDate");
    private final static QName _DeliveredToGLN_QNAME = new QName("http://www.esprit.com.cn/XMLSchema/eShopInterface/espDelivery.xsd", "deliveredToGLN");
    private final static QName _DeliveryStatus_QNAME = new QName("http://www.esprit.com.cn/XMLSchema/eShopInterface/espDelivery.xsd", "deliveryStatus");
    private final static QName _GenerationTime_QNAME = new QName("http://www.esprit.com.cn/XMLSchema/eShopInterface/espDelivery.xsd", "generationTime");
    private final static QName _DeliveryDate_QNAME = new QName("http://www.esprit.com.cn/XMLSchema/eShopInterface/espDelivery.xsd", "deliveryDate");
    private final static QName _DeliveryType_QNAME = new QName("http://www.esprit.com.cn/XMLSchema/eShopInterface/espDelivery.xsd", "deliveryType");
    private final static QName _DeliveryNo_QNAME = new QName("http://www.esprit.com.cn/XMLSchema/eShopInterface/espDelivery.xsd", "deliveryNo");
    private final static QName _NumberOfRecords_QNAME = new QName("http://www.esprit.com.cn/XMLSchema/eShopInterface/espDelivery.xsd", "numberOfRecords");
    private final static QName _ToGLN_QNAME = new QName("http://www.esprit.com.cn/XMLSchema/eShopInterface/espDelivery.xsd", "toGLN");
    private final static QName _DeliveredFromGLN_QNAME = new QName("http://www.esprit.com.cn/XMLSchema/eShopInterface/espDelivery.xsd", "deliveredFromGLN");
    private final static QName _ToNode_QNAME = new QName("http://www.esprit.com.cn/XMLSchema/eShopInterface/espDelivery.xsd", "toNode");
    private final static QName _SequenceNumber_QNAME = new QName("http://www.esprit.com.cn/XMLSchema/eShopInterface/espDelivery.xsd", "sequenceNumber");
    private final static QName _GenerationDate_QNAME = new QName("http://www.esprit.com.cn/XMLSchema/eShopInterface/espDelivery.xsd", "generationDate");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes
     * for package: com.jumbo.wms.model.command.vmi.esprit.xml.espDelilvery
     * 
     */
    public ObjectFactory() {}

    /**
     * Create an instance of {@link Header }
     * 
     */
    public Header createHeader() {
        return new Header();
    }

    /**
     * Create an instance of {@link EspDelivery }
     * 
     */
    public EspDelivery createEspDelivery() {
        return new EspDelivery();
    }

    /**
     * Create an instance of {@link Item }
     * 
     */
    public Item createItem() {
        return new Item();
    }

    /**
     * Create an instance of {@link Delivery }
     * 
     */
    public Delivery createDelivery() {
        return new Delivery();
    }

    /**
     * Create an instance of {@link Items }
     * 
     */
    public Items createItems() {
        return new Items();
    }

    /**
     * Create an instance of {@link Deliveries }
     * 
     */
    public Deliveries createDeliveries() {
        return new Deliveries();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://www.esprit.com.cn/XMLSchema/eShopInterface/espDelivery.xsd", name = "fromNode")
    public JAXBElement<String> createFromNode(String value) {
        return new JAXBElement<String>(_FromNode_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://www.esprit.com.cn/XMLSchema/eShopInterface/espDelivery.xsd", name = "fromGLN")
    public JAXBElement<String> createFromGLN(String value) {
        return new JAXBElement<String>(_FromGLN_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://www.esprit.com.cn/XMLSchema/eShopInterface/espDelivery.xsd", name = "goodsReceiptDate")
    public JAXBElement<String> createGoodsReceiptDate(String value) {
        return new JAXBElement<String>(_GoodsReceiptDate_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://www.esprit.com.cn/XMLSchema/eShopInterface/espDelivery.xsd", name = "deliveredToGLN")
    public JAXBElement<String> createDeliveredToGLN(String value) {
        return new JAXBElement<String>(_DeliveredToGLN_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://www.esprit.com.cn/XMLSchema/eShopInterface/espDelivery.xsd", name = "deliveryStatus")
    public JAXBElement<String> createDeliveryStatus(String value) {
        return new JAXBElement<String>(_DeliveryStatus_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://www.esprit.com.cn/XMLSchema/eShopInterface/espDelivery.xsd", name = "generationTime")
    public JAXBElement<String> createGenerationTime(String value) {
        return new JAXBElement<String>(_GenerationTime_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://www.esprit.com.cn/XMLSchema/eShopInterface/espDelivery.xsd", name = "deliveryDate")
    public JAXBElement<String> createDeliveryDate(String value) {
        return new JAXBElement<String>(_DeliveryDate_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://www.esprit.com.cn/XMLSchema/eShopInterface/espDelivery.xsd", name = "deliveryType")
    public JAXBElement<String> createDeliveryType(String value) {
        return new JAXBElement<String>(_DeliveryType_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://www.esprit.com.cn/XMLSchema/eShopInterface/espDelivery.xsd", name = "deliveryNo")
    public JAXBElement<String> createDeliveryNo(String value) {
        return new JAXBElement<String>(_DeliveryNo_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://www.esprit.com.cn/XMLSchema/eShopInterface/espDelivery.xsd", name = "numberOfRecords")
    public JAXBElement<String> createNumberOfRecords(String value) {
        return new JAXBElement<String>(_NumberOfRecords_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://www.esprit.com.cn/XMLSchema/eShopInterface/espDelivery.xsd", name = "toGLN")
    public JAXBElement<String> createToGLN(String value) {
        return new JAXBElement<String>(_ToGLN_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://www.esprit.com.cn/XMLSchema/eShopInterface/espDelivery.xsd", name = "deliveredFromGLN")
    public JAXBElement<String> createDeliveredFromGLN(String value) {
        return new JAXBElement<String>(_DeliveredFromGLN_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://www.esprit.com.cn/XMLSchema/eShopInterface/espDelivery.xsd", name = "toNode")
    public JAXBElement<String> createToNode(String value) {
        return new JAXBElement<String>(_ToNode_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://www.esprit.com.cn/XMLSchema/eShopInterface/espDelivery.xsd", name = "sequenceNumber")
    public JAXBElement<String> createSequenceNumber(String value) {
        return new JAXBElement<String>(_SequenceNumber_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://www.esprit.com.cn/XMLSchema/eShopInterface/espDelivery.xsd", name = "generationDate")
    public JAXBElement<String> createGenerationDate(String value) {
        return new JAXBElement<String>(_GenerationDate_QNAME, String.class, null, value);
    }

}
