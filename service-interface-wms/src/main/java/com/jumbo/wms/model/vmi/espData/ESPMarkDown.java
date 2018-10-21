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
@Table(name = "T_ESPRIT_MARKDOWN")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class ESPMarkDown extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = 7186757076648959636L;

    public final static Integer STATUS_DOING = 2;
    public final static Integer STATUS_1 = 1;
    // 已反馈
    public final static Integer STATUS_5 = 5;

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

    private String style;

    private String effectiveDate;

    private String listedRetailPrice;

    private Integer status;

    private Long shopId;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_ESPRIT_MARK_DOWN", sequenceName = "S_T_ESPRIT_MARK_DOWN", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ESPRIT_MARK_DOWN")
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

    @Column(name = "MARKDOWN_BUYINGSEASONYEAR")
    public String getBuyingSeasonYear() {
        return buyingSeasonYear;
    }

    public void setBuyingSeasonYear(String buyingSeasonYear) {
        this.buyingSeasonYear = buyingSeasonYear;
    }

    @Column(name = "MARKDOWN_BUYINGSEASONCODE")
    public String getBuyingSeasonCode() {
        return buyingSeasonCode;
    }

    public void setBuyingSeasonCode(String buyingSeasonCode) {
        this.buyingSeasonCode = buyingSeasonCode;
    }

    @Column(name = "MARKDOWN_STYLE")
    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    @Column(name = "MARKDOWN_EFFECTIVEDATE")
    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    @Column(name = "MARKDOWN_LISTEDRETAILPRICE")
    public String getListedRetailPrice() {
        return listedRetailPrice;
    }

    public void setListedRetailPrice(String listedRetailPrice) {
        this.listedRetailPrice = listedRetailPrice;
    }

    @Column(name = "STATUS")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "SHOP_ID")
    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }



}
