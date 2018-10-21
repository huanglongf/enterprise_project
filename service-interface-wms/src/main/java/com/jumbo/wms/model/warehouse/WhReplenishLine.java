package com.jumbo.wms.model.warehouse;

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

import org.hibernate.annotations.Index;
import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.baseinfo.Sku;

/**
 * 库内补货报表明细行
 * 
 * @author jinlong.ke
 * 
 */
@Entity
@Table(name = "T_WH_REPLENISH_LINE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class WhReplenishLine extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = -5602485954210440815L;
    /**
     * PK
     */
    private Long id;
    /**
     * 需补货库位
     */
    private String inLocationCode;
    private String inLocationCode1;
    private String inLocationCode2;
    /**
     * 需补货库区
     */
    private String inDistrictCode;
    /**
     * 最大库容
     */
    private Long maxStore;
    /**
     * 当前库区库容
     */
    private Long inDisCurrentInv;
    private Long inQty;
    private BigDecimal warningPre;
    private String outLocationCode;
    private String outDistrictCode;
    private Long outLocCurrentInv;
    private Long outQty;
    private Sku sku;
    private WhReplenish repl;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_WH_REPLENISH_LINE", sequenceName = "S_T_WH_REPLENISH_LINE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WH_REPLENISH_LINE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "IN_LOCATION_CODE", length = 100)
    public String getInLocationCode() {
        return inLocationCode;
    }

    public void setInLocationCode(String inLocationCode) {
        this.inLocationCode = inLocationCode;
    }
    @Column(name = "IN_LOCATION_CODE1", length = 100)
    public String getInLocationCode1() {
        return inLocationCode1;
    }

    public void setInLocationCode1(String inLocationCode1) {
        this.inLocationCode1 = inLocationCode1;
    }
    @Column(name = "IN_LOCATION_CODE2", length = 100)
    public String getInLocationCode2() {
        return inLocationCode2;
    }

    public void setInLocationCode2(String inLocationCode2) {
        this.inLocationCode2 = inLocationCode2;
    }

    @Column(name = "IN_DIS_CODE", length = 100)
    public String getInDistrictCode() {
        return inDistrictCode;
    }

    public void setInDistrictCode(String inDistrictCode) {
        this.inDistrictCode = inDistrictCode;
    }

    @Column(name = "MAX_STORE")
    public Long getMaxStore() {
        return maxStore;
    }

    public void setMaxStore(Long maxStore) {
        this.maxStore = maxStore;
    }

    @Column(name = "IN_DIS_INV")
    public Long getInDisCurrentInv() {
        return inDisCurrentInv;
    }

    public void setInDisCurrentInv(Long inDisCurrentInv) {
        this.inDisCurrentInv = inDisCurrentInv;
    }

    @Column(name = "IN_QTY")
    public Long getInQty() {
        return inQty;
    }

    public void setInQty(Long inQty) {
        this.inQty = inQty;
    }

    @Column(name = "WARNING_PRE")
    public BigDecimal getWarningPre() {
        return warningPre;
    }

    public void setWarningPre(BigDecimal warningPre) {
        this.warningPre = warningPre;
    }

    @Column(name = "OUT_LOCATION_CODE", length = 100)
    public String getOutLocationCode() {
        return outLocationCode;
    }

    public void setOutLocationCode(String outLocationCode) {
        this.outLocationCode = outLocationCode;
    }

    @Column(name = "OUT_DIS_CODE", length = 100)
    public String getOutDistrictCode() {
        return outDistrictCode;
    }

    public void setOutDistrictCode(String outDistrictCode) {
        this.outDistrictCode = outDistrictCode;
    }

    @Column(name = "OUT_LOC_INV")
    public Long getOutLocCurrentInv() {
        return outLocCurrentInv;
    }

    public void setOutLocCurrentInv(Long outLocCurrentInv) {
        this.outLocCurrentInv = outLocCurrentInv;
    }

    @Column(name = "OUT_QTY")
    public Long getOutQty() {
        return outQty;
    }

    public void setOutQty(Long outQty) {
        this.outQty = outQty;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SKU_ID")
    @Index(name = "IDX_REPL_LINE_SKUID")
    public Sku getSku() {
        return sku;
    }

    public void setSku(Sku sku) {
        this.sku = sku;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WR_ID")
    @Index(name = "IDX_REPL_LINE_WRID")
    public WhReplenish getRepl() {
        return repl;
    }

    public void setRepl(WhReplenish repl) {
        this.repl = repl;
    }


}
