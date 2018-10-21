package com.jumbo.wms.model.vmi.converseData;

import java.math.BigDecimal;
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
import com.jumbo.wms.model.vmi.itData.VMIReceiveInfoStatus;


@Entity
@Table(name = "T_CONVERSE_VMI_ADJUSTMENT")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class ConverseVmiAdjustment extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = -6818124467202152637L;

    private Long id;

    private VMIReceiveInfoStatus status;

    private String recordType;

    private String autoReceive;

    private String storeCode;

    private Long quantity;

    private BigDecimal landedCost;

    private String quantitySign;

    private BigDecimal retailPrice;

    private String UPC;

    private Date shippmentDate;

    private String bin;

    private String reasonCode;

    private String adjCode;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_CONVERSE_VMI_ADJUSTMENT", sequenceName = "S_T_CONVERSE_VMI_ADJUSTMENT", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CONVERSE_VMI_ADJUSTMENT")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.vmi.itData.VMIReceiveInfoStatus")})
    public VMIReceiveInfoStatus getStatus() {
        return status;
    }

    public void setStatus(VMIReceiveInfoStatus status) {
        this.status = status;
    }

    @Column(name = "RECORD_TYPE", length = 10)
    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    @Column(name = "AUTO_RECEIVE", length = 10)
    public String getAutoReceive() {
        return autoReceive;
    }

    public void setAutoReceive(String autoReceive) {
        this.autoReceive = autoReceive;
    }

    @Column(name = "STORE_CODE", length = 10)
    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    @Column(name = "QUANTITY", length = 10)
    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    @Column(name = "LANDED_COST")
    public BigDecimal getLandedCost() {
        return landedCost;
    }

    public void setLandedCost(BigDecimal landedCost) {
        this.landedCost = landedCost;
    }

    @Column(name = "QUANTITY_SIGN", length = 10)
    public String getQuantitySign() {
        return quantitySign;
    }

    public void setQuantitySign(String quantitySign) {
        this.quantitySign = quantitySign;
    }

    @Column(name = "RETAIL_PRICE")
    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
    }

    @Column(name = "UPC", length = 25)
    public String getUPC() {
        return UPC;
    }

    public void setUPC(String uPC) {
        UPC = uPC;
    }

    @Column(name = "SHIPPMENT_DATE")
    public Date getShippmentDate() {
        return shippmentDate;
    }

    public void setShippmentDate(Date shippmentDate) {
        this.shippmentDate = shippmentDate;
    }

    @Column(name = "BIN", length = 10)
    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }

    @Column(name = "REASON_CODE")
    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    @Column(name = "ADJ_CODE")
    public String getAdjCode() {
        return adjCode;
    }

    public void setAdjCode(String adjCode) {
        this.adjCode = adjCode;
    }



}
