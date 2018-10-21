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
@Table(name = "T_ESPRIT_STYLE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class ESPStyle extends BaseModel {

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

    private String seasonYear;

    private String seasonCode;

    private String styleNo;

    private String line;

    private String group;

    private String category;

    private String categoryDesc;

    private String styleClass;

    private String localClass1;

    private String localClass2;

    private String productionDivision;

    private String countryOfOrigin;

    private String styleDesc1;

    private String styleDesc2;

    private String styleShortDesc;

    private String deliveryCapsule;

    private String sizeScale;

    private String keyItem;

    private String factory;

    private String supplier;

    private String originalListedRetail;

    private String planningFOBInLocalCurrency;

    private String planningFOBCurrency;

    private String planningFOBCurrencyAmount;

    private String referenceNo;

    private String sellingSeasonYear;

    private String sellingSeasonCode;

    private String defaultCommissionPercentage;

    private String defaultDutyPercentage;

    private String defaultMiscPercentage;

    private String defaultMiscAmount;

    private String vatCode;

    private String wholesaleTaxCode;

    private String grade;

    private String landedCost;

    private String gtpInLocalCurrency;

    private String harmonizedSystemCode;

    private String brand;

    private String afsStyle;

    private String importCompany;

    private String skuId;

    private String skuColor;

    private String skuSize;

    private Integer skuSortAsc;

    private Boolean flag;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_ESPRIT_STYLE", sequenceName = "S_T_ESPRIT_STYLE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ESPRIT_STYLE")
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

    @Column(name = "S_SEASONYEAR")
    public String getSeasonYear() {
        return seasonYear;
    }

    public void setSeasonYear(String seasonYear) {
        this.seasonYear = seasonYear;
    }

    @Column(name = "S_SEASONCODE")
    public String getSeasonCode() {
        return seasonCode;
    }

    public void setSeasonCode(String seasonCode) {
        this.seasonCode = seasonCode;
    }

    @Column(name = "S_STYLENO")
    public String getStyleNo() {
        return styleNo;
    }

    public void setStyleNo(String styleNo) {
        this.styleNo = styleNo;
    }

    @Column(name = "S_LINE")
    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    @Column(name = "S_GROUP")
    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Column(name = "S_CATEGORY")
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Column(name = "S_CATEGORYDESC")
    public String getCategoryDesc() {
        return categoryDesc;
    }

    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
    }

    @Column(name = "S_CLAZZ")
    public String getStyleClass() {
        return styleClass;
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    @Column(name = "S_LOCALCLASS1")
    public String getLocalClass1() {
        return localClass1;
    }

    public void setLocalClass1(String localClass1) {
        this.localClass1 = localClass1;
    }

    @Column(name = "S_LOCALCLASS2", length = 100)
    public String getLocalClass2() {
        return localClass2;
    }

    public void setLocalClass2(String localClass2) {
        this.localClass2 = localClass2;
    }

    @Column(name = "S_PRODUCTIONDIVISION")
    public String getProductionDivision() {
        return productionDivision;
    }

    public void setProductionDivision(String productionDivision) {
        this.productionDivision = productionDivision;
    }

    @Column(name = "S_COUNTRYOFORIGIN")
    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    @Column(name = "S_STYLEDESC1")
    public String getStyleDesc1() {
        return styleDesc1;
    }

    public void setStyleDesc1(String styleDesc1) {
        this.styleDesc1 = styleDesc1;
    }

    @Column(name = "S_STYLEDESC2")
    public String getStyleDesc2() {
        return styleDesc2;
    }

    public void setStyleDesc2(String styleDesc2) {
        this.styleDesc2 = styleDesc2;
    }

    @Column(name = "S_STYLESHORTDESC")
    public String getStyleShortDesc() {
        return styleShortDesc;
    }

    public void setStyleShortDesc(String styleShortDesc) {
        this.styleShortDesc = styleShortDesc;
    }

    @Column(name = "S_DELIVERYCAPSULE")
    public String getDeliveryCapsule() {
        return deliveryCapsule;
    }

    public void setDeliveryCapsule(String deliveryCapsule) {
        this.deliveryCapsule = deliveryCapsule;
    }

    @Column(name = "S_SIZESCALE")
    public String getSizeScale() {
        return sizeScale;
    }

    public void setSizeScale(String sizeScale) {
        this.sizeScale = sizeScale;
    }

    @Column(name = "S_KEYITEM")
    public String getKeyItem() {
        return keyItem;
    }

    public void setKeyItem(String keyItem) {
        this.keyItem = keyItem;
    }

    @Column(name = "S_FACTORY")
    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    @Column(name = "S_SUPPLIER")
    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    @Column(name = "S_ORIGINALLISTEDRETAIL")
    public String getOriginalListedRetail() {
        return originalListedRetail;
    }

    public void setOriginalListedRetail(String originalListedRetail) {
        this.originalListedRetail = originalListedRetail;
    }

    @Column(name = "S_PLANNINGFOBINLOCALCURRENCY")
    public String getPlanningFOBInLocalCurrency() {
        return planningFOBInLocalCurrency;
    }

    public void setPlanningFOBInLocalCurrency(String planningFOBInLocalCurrency) {
        this.planningFOBInLocalCurrency = planningFOBInLocalCurrency;
    }

    @Column(name = "S_PLANNINGFOBCURRENCY")
    public String getPlanningFOBCurrency() {
        return planningFOBCurrency;
    }

    public void setPlanningFOBCurrency(String planningFOBCurrency) {
        this.planningFOBCurrency = planningFOBCurrency;
    }

    @Column(name = "S_PLANNINGFOBCURRENCYAMOUNT")
    public String getPlanningFOBCurrencyAmount() {
        return planningFOBCurrencyAmount;
    }

    public void setPlanningFOBCurrencyAmount(String planningFOBCurrencyAmount) {
        this.planningFOBCurrencyAmount = planningFOBCurrencyAmount;
    }

    @Column(name = "S_REFERENCENO", length = 100)
    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    @Column(name = "S_SELLINGSEASONYEAR")
    public String getSellingSeasonYear() {
        return sellingSeasonYear;
    }

    public void setSellingSeasonYear(String sellingSeasonYear) {
        this.sellingSeasonYear = sellingSeasonYear;
    }

    @Column(name = "S_SELLINGSEASONCODE")
    public String getSellingSeasonCode() {
        return sellingSeasonCode;
    }

    public void setSellingSeasonCode(String sellingSeasonCode) {
        this.sellingSeasonCode = sellingSeasonCode;
    }

    @Column(name = "S_DEFAULTCOMMISSIONPERCENTAGE")
    public String getDefaultCommissionPercentage() {
        return defaultCommissionPercentage;
    }

    public void setDefaultCommissionPercentage(String defaultCommissionPercentage) {
        this.defaultCommissionPercentage = defaultCommissionPercentage;
    }

    @Column(name = "S_DEFAULTDUTYPERCENTAGE")
    public String getDefaultDutyPercentage() {
        return defaultDutyPercentage;
    }

    public void setDefaultDutyPercentage(String defaultDutyPercentage) {
        this.defaultDutyPercentage = defaultDutyPercentage;
    }

    @Column(name = "S_DEFAULTMISCPERCENTAGE")
    public String getDefaultMiscPercentage() {
        return defaultMiscPercentage;
    }

    public void setDefaultMiscPercentage(String defaultMiscPercentage) {
        this.defaultMiscPercentage = defaultMiscPercentage;
    }

    @Column(name = "S_DEFAULTMISCAMOUNT")
    public String getDefaultMiscAmount() {
        return defaultMiscAmount;
    }

    public void setDefaultMiscAmount(String defaultMiscAmount) {
        this.defaultMiscAmount = defaultMiscAmount;
    }

    @Column(name = "S_VATCODE")
    public String getVatCode() {
        return vatCode;
    }

    public void setVatCode(String vatCode) {
        this.vatCode = vatCode;
    }

    @Column(name = "S_WHOLESALETAXCODE")
    public String getWholesaleTaxCode() {
        return wholesaleTaxCode;
    }

    public void setWholesaleTaxCode(String wholesaleTaxCode) {
        this.wholesaleTaxCode = wholesaleTaxCode;
    }

    @Column(name = "S_GRADE", length = 100)
    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Column(name = "S_LANDEDCOST")
    public String getLandedCost() {
        return landedCost;
    }

    public void setLandedCost(String landedCost) {
        this.landedCost = landedCost;
    }

    @Column(name = "S_GTPINLOCALCURRENCY")
    public String getGtpInLocalCurrency() {
        return gtpInLocalCurrency;
    }

    public void setGtpInLocalCurrency(String gtpInLocalCurrency) {
        this.gtpInLocalCurrency = gtpInLocalCurrency;
    }

    @Column(name = "S_HARMONIZEDSYSTEMCODE", length = 100)
    public String getHarmonizedSystemCode() {
        return harmonizedSystemCode;
    }

    public void setHarmonizedSystemCode(String harmonizedSystemCode) {
        this.harmonizedSystemCode = harmonizedSystemCode;
    }

    @Column(name = "S_BRAND", length = 100)
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Column(name = "S_AFSSTYLE")
    public String getAfsStyle() {
        return afsStyle;
    }

    public void setAfsStyle(String afsStyle) {
        this.afsStyle = afsStyle;
    }

    @Column(name = "S_IMPORTCOMPANY")
    public String getImportCompany() {
        return importCompany;
    }

    public void setImportCompany(String importCompany) {
        this.importCompany = importCompany;
    }

    @Column(name = "SKU_ID")
    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    @Column(name = "SKU_COLOR")
    public String getSkuColor() {
        return skuColor;
    }

    public void setSkuColor(String skuColor) {
        this.skuColor = skuColor;
    }

    @Column(name = "SKU_SIZE")
    public String getSkuSize() {
        return skuSize;
    }

    public void setSkuSize(String skuSize) {
        this.skuSize = skuSize;
    }

    @Column(name = "SKU_SORTASC")
    public Integer getSkuSortAsc() {
        return skuSortAsc;
    }

    public void setSkuSortAsc(Integer skuSortAsc) {
        this.skuSortAsc = skuSortAsc;
    }

    @Column(name = "FLAG")
    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

}
