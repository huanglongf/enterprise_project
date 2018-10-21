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
@Table(name = "T_LEVIS_STKR")
public class LevisStkr extends BaseModel {

    private static final long serialVersionUID = -1276624026800481324L;

    private Long id;
    private Date createDate;
    private String currency;
    private String storeCode;
    private String binType = "ONHAND";
    private String productCode;
    private String productReference = "LEVIS";
    private String colorCode;
    private String sizeCode;
    private String sku;
    private String stockLevelDate;
    private String lastMovementDate;
    private String sign = "P";
    private String quantity;
    private String purchaseCost;
    private String retailPrice;
    private String averageCost;
    private String manufacturingCost;
    private String brand = "LEVI`S(r)";
    private String countryCode = "375";
    private String division = "CTB";
    private String extendedExternalSku;
    private String department;
    private String subDepartment;
    private String clazz;
    private String type = "0";

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_LVS_DO", sequenceName = "S_T_LEVIS_STKR", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_LVS_DO")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "CREATDATE")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "CURRENCY")
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Column(name = "STORE_CODE")
    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    @Column(name = "BIN_TYPE")
    public String getBinType() {
        return binType;
    }

    public void setBinType(String binType) {
        this.binType = binType;
    }

    @Column(name = "PRODUCT_CODE")
    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    @Column(name = "PRODUCT_REFERENCE")
    public String getProductReference() {
        return productReference;
    }

    public void setProductReference(String productReference) {
        this.productReference = productReference;
    }

    @Column(name = "COLOR_CODE")
    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    @Column(name = "SIZE_CODE")
    public String getSizeCode() {
        return sizeCode;
    }

    public void setSizeCode(String sizeCode) {
        this.sizeCode = sizeCode;
    }

    @Column(name = "SKU")
    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    @Column(name = "STOCK_LEVEL_DATE")
    public String getStockLevelDate() {
        return stockLevelDate;
    }

    public void setStockLevelDate(String stockLevelDate) {
        this.stockLevelDate = stockLevelDate;
    }

    @Column(name = "LAST_MOVEMENT_DATE")
    public String getLastMovementDate() {
        return lastMovementDate;
    }

    public void setLastMovementDate(String lastMovementDate) {
        this.lastMovementDate = lastMovementDate;
    }

    @Column(name = "SIGN")
    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Column(name = "QUANTITY")
    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Column(name = "PURCHASE_COST")
    public String getPurchaseCost() {
        return purchaseCost;
    }

    public void setPurchaseCost(String purchaseCost) {
        this.purchaseCost = purchaseCost;
    }

    @Column(name = "RETAIL_PRICE")
    public String getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(String retailPrice) {
        this.retailPrice = retailPrice;
    }

    @Column(name = "AVERAGE_COST")
    public String getAverageCost() {
        return averageCost;
    }

    public void setAverageCost(String averageCost) {
        this.averageCost = averageCost;
    }

    @Column(name = "MANUFACTURING_COST")
    public String getManufacturingCost() {
        return manufacturingCost;
    }

    public void setManufacturingCost(String manufacturingCost) {
        this.manufacturingCost = manufacturingCost;
    }

    @Column(name = "BRAND")
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Column(name = "COUNTRY_CODE")
    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Column(name = "EXTENDED_EXTERNAL_SKU")
    public String getExtendedExternalSku() {
        return extendedExternalSku;
    }

    public void setExtendedExternalSku(String extendedExternalSku) {
        this.extendedExternalSku = extendedExternalSku;
    }

    @Column(name = "DIVISION")
    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    @Column(name = "DEPARTMENT")
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Column(name = "SUB_DEPARTMENT")
    public String getSubDepartment() {
        return subDepartment;
    }

    public void setSubDepartment(String subDepartment) {
        this.subDepartment = subDepartment;
    }

    @Column(name = "CLASS")
    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    @Column(name = "TYPE")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
