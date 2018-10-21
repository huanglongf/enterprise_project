package com.jumbo.wms.model.vmi.espData;

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
@Table(name = "T_ESPRIT_STOCK_COUNT")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class ESPStockCount extends BaseModel {

    public final static Integer STATUS_DOING = 2;
    public final static Integer STATUS_1 = 1;
    // 已反馈
    public final static Integer STATUS_5 = 5;
    /**
	 * 
	 */
    private static final long serialVersionUID = 7186757076648959636L;

    private Long id;

    private String headerFromGLN;

    private String headerToGLN;

    private String headerFromNode;

    private String headerToNode;

    private String headerSequenceNumber;

    private String headerNumberOfrecords;

    private String headerGenerationDate;

    private String headerGenerationTime;

    private String locationGLN;

    private String skuId;

    private String qty;

    private Integer status;


    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_ESPRIT_STOCK_COUNT", sequenceName = "S_T_ESPRIT_STOCK_COUNT", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ESPRIT_STOCK_COUNT")
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

    @Column(name = "HEADER_NUMBEROFRECORDS", length = 10)
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

    @Column(name = "STATUS")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "STOCKCOUNT_LOCATIONGLN")
    public String getLocationGLN() {
        return locationGLN;
    }

    public void setLocationGLN(String locationGLN) {
        this.locationGLN = locationGLN;
    }

    @Column(name = "STOCKCOUNT_SKUID")
    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    @Column(name = "STOCKCOUNT_QTY")
    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }



}
