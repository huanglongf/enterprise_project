package com.jumbo.wms.model.compensation;

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

import com.jumbo.wms.model.BaseModel;

/**
 *  索赔单明细
 *  @author lihui
 */
@Entity
@Table(name = "T_WH_COMPENSATION_DETAILS")
public class WhCompensationDetails extends BaseModel {

    private static final long serialVersionUID = -4543276202029492206L;

    private Long id;
    private String skuCode;
    private String skuName;
    private Integer quantity;
    private Double unitPrice;
    private Double totalPrice;
    private Integer claimQty;
    private Double claimUnitPrice;
    private Double claimAmt;
    private WhCompensation whCompensation;


    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_WH_COMPENSATION_DETAILS", sequenceName = "S_T_WH_COMPENSATION_DETAILS", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_WH_COMPENSATION_DETAILS")
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

    @Column(name = "SKU_CODE")
    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    @Column(name = "SKU_NAME")
    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    @Column(name = "QUANTITY")
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Column(name = "UNIT_PRICE")
    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Column(name = "TOTAL_PRICE")
    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Column(name = "CLAIM_QTY")
    public Integer getClaimQty() {
        return claimQty;
    }

    public void setClaimQty(Integer claimQty) {
        this.claimQty = claimQty;
    }

    @Column(name = "CLAIM_UNIT_PRICE")
    public Double getClaimUnitPrice() {
        return claimUnitPrice;
    }

    public void setClaimUnitPrice(Double claimUnitPrice) {
        this.claimUnitPrice = claimUnitPrice;
    }

    @Column(name = "CLAIM_AMT")
    public Double getClaimAmt() {
        return claimAmt;
    }

    public void setClaimAmt(Double claimAmt) {
        this.claimAmt = claimAmt;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPENSATION_ID")
    public WhCompensation getWhCompensation() {
        return whCompensation;
    }

    public void setWhCompensation(WhCompensation whCompensation) {
        this.whCompensation = whCompensation;
    }
	
}
