package com.jumbo.wms.model.vmi.espData;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;

@Entity
@Table(name = "T_ESPRIT_DELIVERY_DATA")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class ESPDelivery extends BaseModel {
    /**
	 * 
	 */
    private static final long serialVersionUID = 4751968846952577025L;

    public static final Integer STATUS_0 = 0;
    public static final Integer STATUS_1 = 1;// 收货单关闭反馈失败
    public static final Integer STATUS_DOING = 2;
    public static final Integer STATUS_CLOSE_DOING = 3;// 收货单关闭反馈
    public static final Integer STATUS_END = 5;
    public static final String DELIVERED_FROM_NODE = "CECOMEWW";
    public static final String DELIVERED_FROM_GLN = "4046655075778";
    public static final String DELIVERED_TO_NODE = "CECOM";
    public static final String DELIVERED_TO_GLN = "4046655075761";
    public static final String DELIVERED_STATUS = "D";
    public static final String DELIVERED_TYPE = "T";
    public static final String DISPATCH_FROM_NODE = "CECOMEWW";
    public static final String DISPATCH_FROM_GLN = "4046655075778";
    public static final String DISPATCH_TO_NODE = "CREDR";
    public static final String DISPATCH_TO_GLN = "4046655000480";


    private Long id;

    private String headerFromgln;

    private String headerTogln;

    private String headerFromnode;

    private String headerTonode;

    private String headerSequenceNumber;

    private String headerNumberofRecords;

    private String headerGenerationDate;

    private String headerGenerationTime;

    private String deliveryDeliveryNO;

    private String deliveryDeliveryDate;

    private String deliveryDeliverytype;

    private String deliveryDeliveryStatus;

    private String deliveryGoodsReceiptDate;

    private String deliveryDeliveredfromGLN;

    private String deliveryDeliveredtoGLN;

    private String itemSku;

    private String itemReceivedQTY;

    private Integer status;

    private Date version;
    
    private String staCode;

    private Date createTime;
    
    private Integer batchCode;
    
    private String batchCodes;
    
    private String remark;

    private String remark2;

    @Column(name = "REMARK2")
    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2;
    }

    @Column(name = "REMARK")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_ESPRIT_DELIVERY", sequenceName = "S_T_ESPRIT_DELIVERY_DATA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ESPRIT_DELIVERY")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "HEADER_FROMGLN", length = 20)
    public String getHeaderFromgln() {
        return headerFromgln;
    }

    public void setHeaderFromgln(String headerFromgln) {
        this.headerFromgln = headerFromgln;
    }

    @Column(name = "HEADER_TOGLN", length = 20)
    public String getHeaderTogln() {
        return headerTogln;
    }

    public void setHeaderTogln(String headerTogln) {
        this.headerTogln = headerTogln;
    }

    @Column(name = "HEADER_FROMNODE", length = 20)
    public String getHeaderFromnode() {
        return headerFromnode;
    }

    public void setHeaderFromnode(String headerFromnode) {
        this.headerFromnode = headerFromnode;
    }

    @Column(name = "HEADER_TONODE", length = 20)
    public String getHeaderTonode() {
        return headerTonode;
    }

    public void setHeaderTonode(String headerTonode) {
        this.headerTonode = headerTonode;
    }

    @Column(name = "HEADER_SEQUENCENUMBER", length = 20)
    public String getHeaderSequenceNumber() {
        return headerSequenceNumber;
    }

    public void setHeaderSequenceNumber(String headerSequenceNumber) {
        this.headerSequenceNumber = headerSequenceNumber;
    }

    @Column(name = "HEADER_NUMBEROFRECORDS", length = 20)
    public String getHeaderNumberofRecords() {
        return headerNumberofRecords;
    }

    public void setHeaderNumberofRecords(String headerNumberofRecords) {
        this.headerNumberofRecords = headerNumberofRecords;
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

    @Column(name = "DELIVERY_DELIVERYNO", length = 20)
    public String getDeliveryDeliveryNO() {
        return deliveryDeliveryNO;
    }

    public void setDeliveryDeliveryNO(String deliveryDeliveryNO) {
        this.deliveryDeliveryNO = deliveryDeliveryNO;
    }

    @Column(name = "DELIVERY_DELIVERYDATE", length = 20)
    public String getDeliveryDeliveryDate() {
        return deliveryDeliveryDate;
    }

    public void setDeliveryDeliveryDate(String deliveryDeliveryDate) {
        this.deliveryDeliveryDate = deliveryDeliveryDate;
    }

    @Column(name = "DELIVERY_DELIVERYTYPE", length = 20)
    public String getDeliveryDeliverytype() {
        return deliveryDeliverytype;
    }

    public void setDeliveryDeliverytype(String deliveryDeliverytype) {
        this.deliveryDeliverytype = deliveryDeliverytype;
    }

    @Column(name = "DELIVERY_DELIVERYSTATUS", length = 20)
    public String getDeliveryDeliveryStatus() {
        return deliveryDeliveryStatus;
    }

    public void setDeliveryDeliveryStatus(String deliveryDeliveryStatus) {
        this.deliveryDeliveryStatus = deliveryDeliveryStatus;
    }

    @Column(name = "DELIVERY_GOODSRECEIPTDATE", length = 20)
    public String getDeliveryGoodsReceiptDate() {
        return deliveryGoodsReceiptDate;
    }

    public void setDeliveryGoodsReceiptDate(String deliveryGoodsReceiptDate) {
        this.deliveryGoodsReceiptDate = deliveryGoodsReceiptDate;
    }

    @Column(name = "DELIVERY_DELIVEREDFROMGLN", length = 20)
    public String getDeliveryDeliveredfromGLN() {
        return deliveryDeliveredfromGLN;
    }

    public void setDeliveryDeliveredfromGLN(String deliveryDeliveredfromGLN) {
        this.deliveryDeliveredfromGLN = deliveryDeliveredfromGLN;
    }

    @Column(name = "DELIVERY_DELIVEREDTOGLN", length = 20)
    public String getDeliveryDeliveredtoGLN() {
        return deliveryDeliveredtoGLN;
    }

    public void setDeliveryDeliveredtoGLN(String deliveryDeliveredtoGLN) {
        this.deliveryDeliveredtoGLN = deliveryDeliveredtoGLN;
    }

    @Column(name = "ITEM_SKU", length = 20)
    public String getItemSku() {
        return itemSku;
    }

    public void setItemSku(String itemSku) {
        this.itemSku = itemSku;
    }

    @Column(name = "ITEM_RECEIVEDQTY", length = 20)
    public String getItemReceivedQTY() {
        return itemReceivedQTY;
    }

    public void setItemReceivedQTY(String itemReceivedQTY) {
        this.itemReceivedQTY = itemReceivedQTY;
    }

    @Column(name = "VERSION")
    public Date getVersion() {
        return version;
    }

    public void setVersion(Date version) {
        this.version = version;
    }

    @Column(name = "STATUS")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    
    @Column(name = "STA_CODE")
    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }
    
    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "BATCH_CODE")
    public Integer getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(Integer batchCode) {
        this.batchCode = batchCode;
    }

    @Column(name = "BATCHCODE")
    public String getBatchCodes() {
        return batchCodes;
    }

    public void setBatchCodes(String batchCodes) {
        this.batchCodes = batchCodes;
    }
    


}
