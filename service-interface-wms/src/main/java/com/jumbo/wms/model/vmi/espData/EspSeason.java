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
@Table(name = "T_ESPRIT_SEASON")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class EspSeason extends BaseModel {
    /**
	 * 
	 */
    private static final long serialVersionUID = 6712861417673185297L;

    private Long id;

    private String headerFromGLN;

    private String headerToGLN;

    private String headerFromNode;

    private String headerToNode;

    private String headerSequenceNumber;

    private String headerNumberOfrecords;

    private String headerGenerationDate;

    private String headerGenerationTime;

    private String buyingSeasonDesc;
    private String seasonYear;
    private String seasonCode;
    private String sellingFromDate;
    private String sellingToDate;
    private String sellingSeasonDesc;;

    private Date version;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_ESPRIT_SEASON", sequenceName = "S_T_ESPRIT_SEASON", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ESPRIT_SEASON")
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

    @Column(name = "SEASON_BUYINGSEASONDESC")
    public String getBuyingSeasonDesc() {
        return buyingSeasonDesc;
    }

    public void setBuyingSeasonDesc(String buyingSeasonDesc) {
        this.buyingSeasonDesc = buyingSeasonDesc;
    }

    @Column(name = "SEASON_SEASONYEAR")
    public String getSeasonYear() {
        return seasonYear;
    }

    public void setSeasonYear(String seasonYear) {
        this.seasonYear = seasonYear;
    }

    @Column(name = "SEASON_SEASONCODE")
    public String getSeasonCode() {
        return seasonCode;
    }

    public void setSeasonCode(String seasonCode) {
        this.seasonCode = seasonCode;
    }

    @Column(name = "SEASON_SELLINGFROMDATE")
    public String getSellingFromDate() {
        return sellingFromDate;
    }

    public void setSellingFromDate(String sellingFromDate) {
        this.sellingFromDate = sellingFromDate;
    }

    @Column(name = "SEASON_SELLINGTODATE")
    public String getSellingToDate() {
        return sellingToDate;
    }

    public void setSellingToDate(String sellingToDate) {
        this.sellingToDate = sellingToDate;
    }

    @Column(name = "SEASON_SELLINGSEASONDESC")
    public String getSellingSeasonDesc() {
        return sellingSeasonDesc;
    }

    public void setSellingSeasonDesc(String sellingSeasonDesc) {
        this.sellingSeasonDesc = sellingSeasonDesc;
    }

    @Column(name = "VERSION")
    public Date getVersion() {
        return version;
    }

    public void setVersion(Date version) {
        this.version = version;
    }
}
