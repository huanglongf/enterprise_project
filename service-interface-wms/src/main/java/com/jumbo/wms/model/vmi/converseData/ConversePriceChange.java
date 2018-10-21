package com.jumbo.wms.model.vmi.converseData;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.vmi.itData.ConversePriceChangeStatus;


@Entity
@Table(name = "T_CONVERSE_PRICE_CHANGE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class ConversePriceChange extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = -6818124467202152637L;

    private Long id;

    private ConversePriceChangeStatus status;

    private String countryCode;

    private String gridName;

    private String storeCode;

    private String productCode;

    private String colorCode;

    private String sizeCode;

    private String sku;

    private String priceType;

    private String currencyCode;

    private double price;

    private Date tsStart;

    private Date tsEnd;

    private Date version;


    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_CONVERSE_PRICE_CHANGE", sequenceName = "S_T_CONVERSE_PRICE_CHANGE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CONVERSE_PRICE_CHANGE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.vmi.itData.ConversePriceChangeStatus")})
    public ConversePriceChangeStatus getStatus() {
        return status;
    }

    public void setStatus(ConversePriceChangeStatus status) {
        this.status = status;
    }

    @Column(name = "COUNTRY_CODE")
    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Column(name = "GRID_NAME")
    public String getGridName() {
        return gridName;
    }

    public void setGridName(String gridName) {
        this.gridName = gridName;
    }

    @Column(name = "STORE_CODE")
    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    @Column(name = "PRODUCT_CODE")
    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
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

    @Column(name = "PRICE_TYPE")
    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

    @Column(name = "CURRENCY_CODE")
    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    @Column(name = "PRICE")
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Column(name = "TS_START")
    public Date getTsStart() {
        return tsStart;
    }

    public void setTsStart(Date tsStart) {
        this.tsStart = tsStart;
    }

    @Column(name = "TS_END")
    public Date getTsEnd() {
        return tsEnd;
    }

    public void setTsEnd(Date tsEnd) {
        this.tsEnd = tsEnd;
    }

    @Column(name = "VERSION")
    public Date getVersion() {
        return version;
    }

    public void setVersion(Date version) {
        this.version = version;
    }

}
