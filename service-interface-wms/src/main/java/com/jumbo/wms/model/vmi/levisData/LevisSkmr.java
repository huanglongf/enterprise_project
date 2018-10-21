package com.jumbo.wms.model.vmi.levisData;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.jumbo.wms.model.BaseModel;

@Entity
@Table(name = "T_LEVIS_SKMR")
public class LevisSkmr extends BaseModel {
    private static final long serialVersionUID = -4209321367627240768L;
    private Long id;
    private Date creatDate;
    private String productCode;
    private String colorCode;
    private String sizeCode;
    private String sku;
    private String storeCode;
    private String binType = "DEF";
    private String otherStoreCode;
    private String otherBinType;
    private String movementType;
    private String movementDate;
    private String movementTime;
    private String quantitySign;
    private String quantity;
    private String documentNumber;
    private String transNumber;
    private String movementReasonCode;
    private String comments;
    private String currencyCode = "RMB";
    private String averageCost;
    private String manufacturingCost;
    private String vendor = "LS";
    private String productRef = "LEVIS";
    private String brand = "LEVI`S(r)";
    private String countryCode = "375";
    private String landedCost;
    private String division = "LEG";
    private String department;
    private String subDepartment;
    private String clazz;
    private String amountSold;
    private String startWeekDate;
    private String productline = "10";
    private String buyerDescription;
    private String type = "0";
    private String sku1;

    
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_LVS_DO", sequenceName = "S_T_LEVIS_SKMR", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_LVS_DO")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "SKU_1")
    public String getSku1() {
        return sku1;
    }

    public void setSku1(String sku1) {
        this.sku1 = sku1;
    }

    @Column(name = "TYPE")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "BUYER_DESCRIPTION")
    public String getBuyerDescription() {
        return buyerDescription;
    }

    public void setBuyerDescription(String buyerDescription) {
        this.buyerDescription = buyerDescription;
    }

    @Column(name = "PRODUCTLINE")
    public String getProductline() {
        return productline;
    }

    public void setProductline(String productline) {
        this.productline = productline;
    }

    @Column(name = "START_WEEK_DATE")
    public String getStartWeekDate() {
        return startWeekDate;
    }

    public void setStartWeekDate(String startWeekDate) {
        this.startWeekDate = startWeekDate;
    }

    @Column(name = "AMOUNT_SOLD")
    public String getAmountSold() {
        return amountSold;
    }

    public void setAmountSold(String amountSold) {
        this.amountSold = amountSold;
    }

    @Column(name = "CLASS")
    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    @Column(name = "SUB_DEPARTMENT")
    public String getSubDepartment() {
        return subDepartment;
    }

    public void setSubDepartment(String subDepartment) {
        this.subDepartment = subDepartment;
    }

    @Column(name = "DEPARTMENT")
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Column(name = "DIVISION")
    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    @Column(name = "LANDED_COST")
    public String getLandedCost() {
        return landedCost;
    }

    public void setLandedCost(String landedCost) {
        this.landedCost = landedCost;
    }

    @Column(name = "COUNTRY_CODE")
    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Column(name = "BRAND")
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Column(name = "PRODUCT_REF")
    public String getProductRef() {
        return productRef;
    }

    public void setProductRef(String productRef) {
        this.productRef = productRef;
    }

    @Column(name = "VENDOR")
    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    @Column(name = "MANUFACTURING_COST")
    public String getManufacturingCost() {
        return manufacturingCost;
    }

    public void setManufacturingCost(String manufacturingCost) {
        this.manufacturingCost = manufacturingCost;
    }

    @Column(name = "AVERAGE_COST")
    public String getAverageCost() {
        return averageCost;
    }

    public void setAverageCost(String averageCost) {
        this.averageCost = averageCost;
    }

    @Column(name = "CURRENCY_CODE")
    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    @Column(name = "COMMENTS")
    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Column(name = "MOVEMENT_REASON_CODE")
    public String getMovementReasonCode() {
        return movementReasonCode;
    }

    public void setMovementReasonCode(String movementReasonCode) {
        this.movementReasonCode = movementReasonCode;
    }

    @Column(name = "TRANS_NUMBER")
    public String getTransNumber() {
        return transNumber;
    }

    public void setTransNumber(String transNumber) {
        this.transNumber = transNumber;
    }

    @Column(name = "DOCUMENT_NUMBER")
    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    @Column(name = "QUANTITY")
    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Column(name = "QUANTITY_SIGN")
    public String getQuantitySign() {
        return quantitySign;
    }

    public void setQuantitySign(String quantitySign) {
        this.quantitySign = quantitySign;
    }

    @Column(name = "MOVEMENT_TIME")
    public String getMovementTime() {
        return movementTime;
    }

    public void setMovementTime(String movementTime) {
        this.movementTime = movementTime;
    }

    @Column(name = "MOVEMENT_DATE")
    public String getMovementDate() {
        return movementDate;
    }

    public void setMovementDate(String movementDate) {
        this.movementDate = movementDate;
    }

    @Column(name = "MOVEMENT_TYPE")
    public String getMovementType() {
        return movementType;
    }

    public void setMovementType(String movementType) {
        this.movementType = movementType;
    }

    @Column(name = "OTHER_BIN_TYPE")
    public String getOtherBinType() {
        return otherBinType;
    }

    public void setOtherBinType(String otherBinType) {
        this.otherBinType = otherBinType;
    }

    @Column(name = "OTHER_STORE_CODE")
    public String getOtherStoreCode() {
        return otherStoreCode;
    }

    public void setOtherStoreCode(String otherStoreCode) {
        this.otherStoreCode = otherStoreCode;
    }

    @Column(name = "BIN_TYPE")
    public String getBinType() {
        return binType;
    }

    public void setBinType(String binType) {
        this.binType = binType;
    }

    @Column(name = "STORE_CODE")
    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    @Column(name = "SKU")
    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    @Column(name = "SIZE_CODE")
    public String getSizeCode() {
        return sizeCode;
    }

    public void setSizeCode(String sizeCode) {
        this.sizeCode = sizeCode;
    }

    @Column(name = "COLOR_CODE")
    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    @Column(name = "PRODUCT_CODE")
    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    @Column(name = "CREAT_DATE")
    public Date getCreatDate() {
        return creatDate;
    }

    public void setCreatDate(Date creatDate) {
        this.creatDate = creatDate;
    }

}
