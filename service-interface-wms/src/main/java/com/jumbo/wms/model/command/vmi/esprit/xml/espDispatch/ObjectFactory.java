package com.jumbo.wms.model.command.vmi.esprit.xml.espDispatch;

import java.io.Serializable;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each Java content interface and Java element interface
 * generated in the com.jumbo.wms.model.command.vmi.esprit.xml.espDispatch package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the Java representation
 * for XML content. The Java representation of XML content can consist of schema derived interfaces
 * and classes representing the binding of schema type definitions, element declarations and model
 * groups. Factory methods for each of these are provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory implements Serializable {



    private static final long serialVersionUID = 6760847222171055574L;
    private final static QName _ToGLN_QNAME = new QName("", "toGLN");
    private final static QName _OrderFlag_QNAME = new QName("", "orderFlag");
    private final static QName _Location_QNAME = new QName("", "location");
    private final static QName _CheckDigit_QNAME = new QName("", "checkDigit");
    private final static QName _GenerationDate_QNAME = new QName("", "generationDate");
    private final static QName _Remark4_QNAME = new QName("", "remark4");
    private final static QName _NoOfRecord_QNAME = new QName("", "noOfRecord");
    private final static QName _Remark1_QNAME = new QName("", "remark1");
    private final static QName _EdiStatus_QNAME = new QName("", "ediStatus");
    private final static QName _ToNode_QNAME = new QName("", "toNode");
    private final static QName _Remark3_QNAME = new QName("", "remark3");
    private final static QName _Remark2_QNAME = new QName("", "remark2");
    private final static QName _RouteCode_QNAME = new QName("", "routeCode");
    private final static QName _BatchNo_QNAME = new QName("", "batchNo");
    private final static QName _FromLocGLN_QNAME = new QName("", "fromLocGLN");
    private final static QName _LatestReqDeliveryDate_QNAME = new QName("", "latestReqDeliveryDate");
    private final static QName _ToLocGLN_QNAME = new QName("", "toLocGLN");
    private final static QName _FromLocation_QNAME = new QName("", "fromLocation");
    private final static QName _ReqDeliveryDate_QNAME = new QName("", "reqDeliveryDate");
    private final static QName _FromGLN_QNAME = new QName("", "fromGLN");
    private final static QName _Company_QNAME = new QName("", "company");
    private final static QName _FromNode_QNAME = new QName("", "fromNode");
    private final static QName _GenerationTime_QNAME = new QName("", "generationTime");
    private final static QName _PromotionStartDate_QNAME = new QName("", "promotionStartDate");
    private final static QName _PickType_QNAME = new QName("", "pickType");
    private final static QName _PickNo_QNAME = new QName("", "pickNo");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes
     * for package: com.jumbo.wms.model.command.vmi.esprit.xml.espDispatch
     * 
     */
    public ObjectFactory() {}

    /**
     * Create an instance of {@link Items }
     * 
     */
    public Items createItems() {
        return new Items();
    }

    /**
     * Create an instance of {@link Pick }
     * 
     */
    public Pick createPick() {
        return new Pick();
    }

    /**
     * Create an instance of {@link Header }
     * 
     */
    public Header createHeader() {
        return new Header();
    }

    /**
     * Create an instance of {@link EspDispatch }
     * 
     */
    public EspDispatch createEspDispatch() {
        return new EspDispatch();
    }

    /**
     * Create an instance of {@link List }
     * 
     */
    public List createList() {
        return new List();
    }

    /**
     * Create an instance of {@link Item }
     * 
     */
    public Item createItem() {
        return new Item();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "", name = "toGLN")
    public JAXBElement<String> createToGLN(String value) {
        return new JAXBElement<String>(_ToGLN_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "", name = "orderFlag")
    public JAXBElement<String> createOrderFlag(String value) {
        return new JAXBElement<String>(_OrderFlag_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "", name = "location")
    public JAXBElement<String> createLocation(String value) {
        return new JAXBElement<String>(_Location_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "", name = "checkDigit")
    public JAXBElement<String> createCheckDigit(String value) {
        return new JAXBElement<String>(_CheckDigit_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "", name = "generationDate")
    public JAXBElement<String> createGenerationDate(String value) {
        return new JAXBElement<String>(_GenerationDate_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "", name = "remark4")
    public JAXBElement<String> createRemark4(String value) {
        return new JAXBElement<String>(_Remark4_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "", name = "noOfRecord")
    public JAXBElement<String> createNoOfRecord(String value) {
        return new JAXBElement<String>(_NoOfRecord_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "", name = "remark1")
    public JAXBElement<String> createRemark1(String value) {
        return new JAXBElement<String>(_Remark1_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ediStatus")
    public JAXBElement<String> createEdiStatus(String value) {
        return new JAXBElement<String>(_EdiStatus_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "", name = "toNode")
    public JAXBElement<String> createToNode(String value) {
        return new JAXBElement<String>(_ToNode_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "", name = "remark3")
    public JAXBElement<String> createRemark3(String value) {
        return new JAXBElement<String>(_Remark3_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "", name = "remark2")
    public JAXBElement<String> createRemark2(String value) {
        return new JAXBElement<String>(_Remark2_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "", name = "routeCode")
    public JAXBElement<String> createRouteCode(String value) {
        return new JAXBElement<String>(_RouteCode_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "", name = "batchNo")
    public JAXBElement<String> createBatchNo(String value) {
        return new JAXBElement<String>(_BatchNo_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "", name = "fromLocGLN")
    public JAXBElement<String> createFromLocGLN(String value) {
        return new JAXBElement<String>(_FromLocGLN_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "", name = "latestReqDeliveryDate")
    public JAXBElement<String> createLatestReqDeliveryDate(String value) {
        return new JAXBElement<String>(_LatestReqDeliveryDate_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "", name = "toLocGLN")
    public JAXBElement<String> createToLocGLN(String value) {
        return new JAXBElement<String>(_ToLocGLN_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "", name = "fromLocation")
    public JAXBElement<String> createFromLocation(String value) {
        return new JAXBElement<String>(_FromLocation_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "", name = "reqDeliveryDate")
    public JAXBElement<String> createReqDeliveryDate(String value) {
        return new JAXBElement<String>(_ReqDeliveryDate_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "", name = "fromGLN")
    public JAXBElement<String> createFromGLN(String value) {
        return new JAXBElement<String>(_FromGLN_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "", name = "company")
    public JAXBElement<String> createCompany(String value) {
        return new JAXBElement<String>(_Company_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "", name = "fromNode")
    public JAXBElement<String> createFromNode(String value) {
        return new JAXBElement<String>(_FromNode_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "", name = "generationTime")
    public JAXBElement<String> createGenerationTime(String value) {
        return new JAXBElement<String>(_GenerationTime_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "", name = "promotionStartDate")
    public JAXBElement<String> createPromotionStartDate(String value) {
        return new JAXBElement<String>(_PromotionStartDate_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "", name = "pickType")
    public JAXBElement<String> createPickType(String value) {
        return new JAXBElement<String>(_PickType_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "", name = "pickNo")
    public JAXBElement<String> createPickNo(String value) {
        return new JAXBElement<String>(_PickNo_QNAME, String.class, null, value);
    }

}
