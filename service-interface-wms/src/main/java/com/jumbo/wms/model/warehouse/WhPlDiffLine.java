package com.jumbo.wms.model.warehouse;

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
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.baseinfo.Sku;

/**
 * 配货清单差异明细表 bin.hu
 * 
 * @author jumbo
 * 
 */
@Entity
@Table(name = "T_WH_PL_DIFF_LINE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class WhPlDiffLine extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = -2273134079188041599L;

    /**
     * PK
     */
    private Long id;

    /**
     * 配货清单表外键
     */
    private PickingList pickingList;

    /**
     * 配货清单状态
     */
    private WhAddStatusMode addStatus;

    /**
     * 商品ID 外键
     */
    private Sku skuId;

    /**
     * 计划量
     */
    private Integer planQty;

    /**
     * 执行量
     */
    private Integer qty;

    /**
     * 箱号
     */
    private Integer pgIndex;


    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_WH_PL_DIFF_LINE", sequenceName = "S_T_WH_PL_DIFF_LINE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WH_PL_DIFF_LINE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PICK_LIST_ID")
    @Index(name = "IDX_WH_STA_PICKING_LIST_WPDL")
    public PickingList getPickingList() {
        return pickingList;
    }

    public void setPickingList(PickingList pickingList) {
        this.pickingList = pickingList;
    }

    @Column(name = "ADD_STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.WhAddStatusMode")})
    public WhAddStatusMode getAddStatus() {
        return addStatus;
    }

    public void setAddStatus(WhAddStatusMode addStatus) {
        this.addStatus = addStatus;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SKU_ID")
    @Index(name = "IDX_BI_INV_SKU_WPDL")
    public Sku getSkuId() {
        return skuId;
    }

    public void setSkuId(Sku skuId) {
        this.skuId = skuId;
    }

    @Column(name = "PLAN_QTY")
    public Integer getPlanQty() {
        return planQty;
    }

    public void setPlanQty(Integer planQty) {
        this.planQty = planQty;
    }

    @Column(name = "QTY")
    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    @Column(name = "PG_INDEX")
    public Integer getPgIndex() {
        return pgIndex;
    }

    public void setPgIndex(Integer pgIndex) {
        this.pgIndex = pgIndex;
    }



}
