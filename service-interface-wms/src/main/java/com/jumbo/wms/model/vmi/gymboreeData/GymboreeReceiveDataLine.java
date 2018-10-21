package com.jumbo.wms.model.vmi.gymboreeData;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.OptimisticLockType;

/**
 * 金宝贝店存入库明细
 * 
 * @author jinlong.ke
 * 
 */
@Entity
@Table(name = "T_GYMBOREE_RECEIVE_DATA_LINE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class GymboreeReceiveDataLine implements Serializable {


    private static final long serialVersionUID = -3155904422034784272L;
    private Long id;
    private String fchrItemID;
    private Long flotQuantity;
    private String fchrBarCode;
    private BigDecimal flotQuotePrice;
    private BigDecimal flotMoney;
    private String fchrOutVouchDetailID;
    private GymboreeReceiveData data;
    private String fchrSourceCode;
    private String fchrWarehouseID;
    private String fchrFree2;
    private String fchrInOutVouchID;
    private String owner;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_GYMBOREE_RECEIVE_DATA_LINE", sequenceName = "S_T_GYMBOREE_RECEIVE_DATA_LINE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_GYMBOREE_RECEIVE_DATA_LINE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RR_ID")
    @Index(name = "IDX_RD_LINE_RD")
    public GymboreeReceiveData getData() {
        return data;
    }

    public void setData(GymboreeReceiveData data) {
        this.data = data;
    }

    @Column(name = "FCHR_ITEM_ID", length = 100)
    public String getFchrItemID() {
        return fchrItemID;
    }

    public void setFchrItemID(String fchrItemID) {
        this.fchrItemID = fchrItemID;
    }

    @Column(name = "FLOT_QUANTITY")
    public Long getFlotQuantity() {
        return flotQuantity;
    }

    public void setFlotQuantity(Long flotQuantity) {
        this.flotQuantity = flotQuantity;
    }

    @Column(name = "FCHR_BAR_CODE", length = 100)
    public String getFchrBarCode() {
        return fchrBarCode;
    }

    public void setFchrBarCode(String fchrBarCode) {
        this.fchrBarCode = fchrBarCode;
    }

    @Column(name = "FLOT_QUOTE_PRICE")
    public BigDecimal getFlotQuotePrice() {
        return flotQuotePrice;
    }

    public void setFlotQuotePrice(BigDecimal flotQuotePrice) {
        this.flotQuotePrice = flotQuotePrice;
    }

    @Column(name = "FLOT_MONEY")
    public BigDecimal getFlotMoney() {
        return flotMoney;
    }

    public void setFlotMoney(BigDecimal flotMoney) {
        this.flotMoney = flotMoney;
    }

    @Column(name = "FD_ID", length = 100)
    public String getFchrOutVouchDetailID() {
        return fchrOutVouchDetailID;
    }

    public void setFchrOutVouchDetailID(String fchrOutVouchDetailID) {
        this.fchrOutVouchDetailID = fchrOutVouchDetailID;
    }

    @Transient
    public String getFchrSourceCode() {
        return fchrSourceCode;
    }

    public void setFchrSourceCode(String fchrSourceCode) {
        this.fchrSourceCode = fchrSourceCode;
    }

    @Transient
    public String getFchrWarehouseID() {
        return fchrWarehouseID;
    }

    public void setFchrWarehouseID(String fchrWarehouseID) {
        this.fchrWarehouseID = fchrWarehouseID;
    }

    @Transient
    public String getFchrFree2() {
        return fchrFree2;
    }

    public void setFchrFree2(String fchrFree2) {
        this.fchrFree2 = fchrFree2;
    }

    @Transient
    public String getFchrInOutVouchID() {
        return fchrInOutVouchID;
    }

    public void setFchrInOutVouchID(String fchrInOutVouchID) {
        this.fchrInOutVouchID = fchrInOutVouchID;
    }

    @Transient
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
