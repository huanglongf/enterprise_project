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

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.authorization.OperationUnit;

/**
 * 预包装商品关联
 * 
 * @author jinlong.ke
 * 
 */
@Entity
@Table(name = "T_PRE_PACKAGED_SKU_REF")
public class PrePackagedSkuRef extends BaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = 4825513306396611680L;
    private Long Id;
    /**
     * 预包装主商品ID
     */
    private Long mainSkuId;
    /**
     * 预包装子商品ID
     */
    private Long subSkuId;
    /**
     * 需包装子商品个数
     */
    private Long qty;
    /**
     * 关联仓库组织
     */
    private OperationUnit ou;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_PRE_PACKAGED_SKU", sequenceName = "S_T_PRE_PACKAGED_SKU_REF", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PRE_PACKAGED_SKU")
    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    @Column(name = "MAIN_SKU_ID")
    public Long getMainSkuId() {
        return mainSkuId;
    }

    public void setMainSkuId(Long mainSkuId) {
        this.mainSkuId = mainSkuId;
    }

    @Column(name = "SUB_SKU_ID")
    public Long getSubSkuId() {
        return subSkuId;
    }

    public void setSubSkuId(Long subSkuId) {
        this.subSkuId = subSkuId;
    }

    @Column(name = "QTY")
    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OU_ID")
    @Index(name = "IDX_PRE_PACKAGE_OU")
    public OperationUnit getOu() {
        return ou;
    }

    public void setOu(OperationUnit ou) {
        this.ou = ou;
    }
}
