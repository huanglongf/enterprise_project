package com.jumbo.wms.model.vmi.espData;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;


@Entity
@Table(name = "T_ESPRIT_RECEIVING")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class ESPReceiving extends BaseModel {

    public final static Integer STATUS_DOING = 2;
    public final static Integer STATUS_1 = 1;
    // 已反馈
    public final static Integer STATUS_5 = 5;
    /**
	 * 
	 */
    private static final long serialVersionUID = -843153354120705890L;

    private Long id;

    private String headerFromGLN;

    private String headerToGLN;

    private String headerFromNode;

    private String headerToNode;

    private String headerSequenceNumber;

    private String headerNumberOfrecords;

    private String headerGenerationDate;

    private String headerGenerationTime;

    private String receivingReceivingNo;

    private String receivingGoodsReceivedDate;

    private String receivingOrdernumber;

    private String receivingPoReference;

    private String receivingDutyPercentage;

    private String receivingMiscfeepercentage;
    
    private String receivingCommPercentage;

    private String invoiceInvoicenumber;

    private String invoiceCurrency;

    private String invoiceTotalqty;

    private String invoiceTotalfob;

    private String invoiceTotalgtp;

    private String receivingBuyergln;

    private String receivingDeliverypartygln;

    private String receivingWarehouse;

    private String itemSku;

    private String itemReceivedqty;

    private Date version;

    private String invoiceBatch;

    private String receivingWarehouseReference;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_ESPRIT_RECEIVING", sequenceName = "S_T_ESPRIT_RECEIVING", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ESPRIT_RECEIVING")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "HEADER_FROMGLN", length = 20)
    public String getHeaderFromGLN() {
        return headerFromGLN;
    }

    public void setHeaderFromGLN(String headerFromGLN) {
        this.headerFromGLN = headerFromGLN;
    }

    @Column(name = "HEADER_TOGLN", length = 20)
    public String getHeaderToGLN() {
        return headerToGLN;
    }

    public void setHeaderToGLN(String headerToGLN) {
        this.headerToGLN = headerToGLN;
    }

    @Column(name = "HEADER_FROMNODE", length = 20)
    public String getHeaderFromNode() {
        return headerFromNode;
    }

    public void setHeaderFromNode(String headerFromNode) {
        this.headerFromNode = headerFromNode;
    }

    @Column(name = "HEADER_TONODE", length = 20)
    public String getHeaderToNode() {
        return headerToNode;
    }

    public void setHeaderToNode(String headerToNode) {
        this.headerToNode = headerToNode;
    }

    @Column(name = "HEADER_SEQUENCENUMBER", length = 20)
    public String getHeaderSequenceNumber() {
        return headerSequenceNumber;
    }

    public void setHeaderSequenceNumber(String headerSequenceNumber) {
        this.headerSequenceNumber = headerSequenceNumber;
    }

    @Column(name = "HEADER_NUMBEROFRECORDS", length = 20)
    public String getHeaderNumberOfrecords() {
        return headerNumberOfrecords;
    }

    public void setHeaderNumberOfrecords(String headerNumberOfrecords) {
        this.headerNumberOfrecords = headerNumberOfrecords;
    }

    @Column(name = "HEADER_GENERATIONDATE", length = 20)
    public String getHeaderGenerationDate() {
        return headerGenerationDate;
    }

    public void setHeaderGenerationDate(String headerGenerationDate) {
        this.headerGenerationDate = headerGenerationDate;
    }

    @Column(name = "HEADER_GENERATIONTIME", length = 20)
    public String getHeaderGenerationTime() {
        return headerGenerationTime;
    }

    public void setHeaderGenerationTime(String headerGenerationTime) {
        this.headerGenerationTime = headerGenerationTime;
    }

    @Column(name = "RECEIVING_RECEIVINGNO", length = 20)
    public String getReceivingReceivingNo() {
        return receivingReceivingNo;
    }

    public void setReceivingReceivingNo(String receivingReceivingNo) {
        this.receivingReceivingNo = receivingReceivingNo;
    }

    @Column(name = "RECEIVING_GOODSRECEIVEDDATE", length = 20)
    public String getReceivingGoodsReceivedDate() {
        return receivingGoodsReceivedDate;
    }

    public void setReceivingGoodsReceivedDate(String receivingGoodsReceivedDate) {
        this.receivingGoodsReceivedDate = receivingGoodsReceivedDate;
    }

    @Column(name = "RECEIVING_ORDERNUMBER", length = 20)
    public String getReceivingOrdernumber() {
        return receivingOrdernumber;
    }

    public void setReceivingOrdernumber(String receivingOrdernumber) {
        this.receivingOrdernumber = receivingOrdernumber;
    }

    @Column(name = "RECEIVING_POREFERENCE", length = 20)
    public String getReceivingPoReference() {
        return receivingPoReference;
    }

    public void setReceivingPoReference(String receivingPoReference) {
        this.receivingPoReference = receivingPoReference;
    }

    @Column(name = "RECEIVING_DUTYPERCENTAGE", length = 20)
    public String getReceivingDutyPercentage() {
        return receivingDutyPercentage;
    }

    public void setReceivingDutyPercentage(String receivingDutyPercentage) {
        this.receivingDutyPercentage = receivingDutyPercentage;
    }

    @Column(name = "RECEIVING_MISCFEEPERCENTAGE", length = 20)
    public String getReceivingMiscfeepercentage() {
        return receivingMiscfeepercentage;
    }

    public void setReceivingMiscfeepercentage(String receivingMiscfeepercentage) {
        this.receivingMiscfeepercentage = receivingMiscfeepercentage;
    }
    
    @Column(name = "RECEIVING_COMMPERCENTAGE", length = 20)
    public String getReceivingCommPercentage() {
        return receivingCommPercentage;
    }

    public void setReceivingCommPercentage(String receivingCommPercentage) {
        this.receivingCommPercentage = receivingCommPercentage;
    }

    @Column(name = "INVOICE_INVOICENUMBER", length = 20)
    public String getInvoiceInvoicenumber() {
        return invoiceInvoicenumber;
    }

    public void setInvoiceInvoicenumber(String invoiceInvoicenumber) {
        this.invoiceInvoicenumber = invoiceInvoicenumber;
    }

    @Column(name = "INVOICE_CURRENCY", length = 20)
    public String getInvoiceCurrency() {
        return invoiceCurrency;
    }

    public void setInvoiceCurrency(String invoiceCurrency) {
        this.invoiceCurrency = invoiceCurrency;
    }

    @Column(name = "INVOICE_TOTALQTY", length = 20)
    public String getInvoiceTotalqty() {
        return invoiceTotalqty;
    }

    public void setInvoiceTotalqty(String invoiceTotalqty) {
        this.invoiceTotalqty = invoiceTotalqty;
    }

    @Column(name = "INVOICE_TOTALFOB", length = 20)
    public String getInvoiceTotalfob() {
        return invoiceTotalfob;
    }

    public void setInvoiceTotalfob(String invoiceTotalfob) {
        this.invoiceTotalfob = invoiceTotalfob;
    }

    @Column(name = "INVOICE_TOTALGTP", length = 20)
    public String getInvoiceTotalgtp() {
        return invoiceTotalgtp;
    }

    public void setInvoiceTotalgtp(String invoiceTotalgtp) {
        this.invoiceTotalgtp = invoiceTotalgtp;
    }

    @Column(name = "RECEIVING_BUYERGLN", length = 20)
    public String getReceivingBuyergln() {
        return receivingBuyergln;
    }

    public void setReceivingBuyergln(String receivingBuyergln) {
        this.receivingBuyergln = receivingBuyergln;
    }

    @Column(name = "RECEIVING_DELIVERYPARTYGLN", length = 20)
    public String getReceivingDeliverypartygln() {
        return receivingDeliverypartygln;
    }

    public void setReceivingDeliverypartygln(String receivingDeliverypartygln) {
        this.receivingDeliverypartygln = receivingDeliverypartygln;
    }

    @Column(name = "RECEIVING_WAREHOUSE", length = 20)
    public String getReceivingWarehouse() {
        return receivingWarehouse;
    }

    public void setReceivingWarehouse(String receivingWarehouse) {
        this.receivingWarehouse = receivingWarehouse;
    }

    @Column(name = "ITEM_SKU", length = 20)
    public String getItemSku() {
        return itemSku;
    }

    public void setItemSku(String itemSku) {
        this.itemSku = itemSku;
    }

    @Column(name = "ITEM_RECEIVEDQTY", length = 20)
    public String getItemReceivedqty() {
        return itemReceivedqty;
    }

    public void setItemReceivedqty(String itemReceivedqty) {
        this.itemReceivedqty = itemReceivedqty;
    }

    @Version
    @Column(name = "VERSION", length = 20)
    public Date getVersion() {
        return version;
    }

    public void setVersion(Date version) {
        this.version = version;
    }

    @Column(name = "INVOICE_BATCH", length = 50)
    public String getInvoiceBatch() {
        return invoiceBatch;
    }

    public void setInvoiceBatch(String invoiceBatch) {
        this.invoiceBatch = invoiceBatch;
    }

    @Column(name = "RECEIVING_WAREHOUSE_REFERENCE", length = 50)
    public String getReceivingWarehouseReference() {
        return receivingWarehouseReference;
    }

    public void setReceivingWarehouseReference(String receivingWarehouseReference) {
        this.receivingWarehouseReference = receivingWarehouseReference;
    }


}
