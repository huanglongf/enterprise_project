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
@Table(name = "T_ESPRIT_COLOR")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class EspColor extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1031936103918144818L;

    private Long id;

    private String headerFromGLN;

    private String headerToGLN;

    private String headerFromNode;

    private String headerToNode;

    private String headerSequenceNumber;

    private String headerNumberOfrecords;

    private String headerGenerationDate;

    private String headerGenerationTime;

    private String buyingSeasonYear;
    private String buyingSeasonCode;
    private String colorCode;
    private String shortDesc;
    private String fullDesc;
    private String colorGroup;
    private String basicColor;
    private String brand;
    private Date version;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_ESPRIT_COLOR", sequenceName = "S_T_ESPRIT_COLOR", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ESPRIT_COLOR")
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

    @Column(name = "COLOR_BUYINGSEASONYEAR")
    public String getBuyingSeasonYear() {
        return buyingSeasonYear;
    }

    public void setBuyingSeasonYear(String buyingSeasonYear) {
        this.buyingSeasonYear = buyingSeasonYear;
    }

    @Column(name = "COLOR_BUYINGSEASONCODE")
    public String getBuyingSeasonCode() {
        return buyingSeasonCode;
    }

    public void setBuyingSeasonCode(String buyingSeasonCode) {
        this.buyingSeasonCode = buyingSeasonCode;
    }

    @Column(name = "COLOR_COLORCODE")
    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    @Column(name = "COLOR_SHORTDESC")
    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    @Column(name = "COLOR_FULLDESC")
    public String getFullDesc() {
        return fullDesc;
    }

    public void setFullDesc(String fullDesc) {
        this.fullDesc = fullDesc;
    }

    @Column(name = "COLOR_COLORGROUP")
    public String getColorGroup() {
        return colorGroup;
    }

    public void setColorGroup(String colorGroup) {
        this.colorGroup = colorGroup;
    }

    @Column(name = "COLOR_BASICCOLOR")
    public String getBasicColor() {
        return basicColor;
    }

    public void setBasicColor(String basicColor) {
        this.basicColor = basicColor;
    }

    @Column(name = "COLOR_BRAND")
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Column(name = "VERSION")
    public Date getVersion() {
        return version;
    }

    public void setVersion(Date version) {
        this.version = version;
    }

}
