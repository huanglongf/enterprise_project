package com.jumbo.wms.model.pda;

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


/**
 * 
 * @author lizaibiao
 * 
 */
@Entity
@Table(name = "t_wh_sku_loc_type_cap")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class PdaSkuLocTypeCap extends BaseModel {
    private static final long serialVersionUID = -2524316072308781346L;
    /**
     * PK
     */
    private Long id;

    /**
     * 数量
     */
    private Long qty;


    /**
     * 商品编码
     */
    private String skuCode;
    /**
     * 货号
     */
    private String supplierCode;
    /**
     * 库位类型
     */
    private PdaLocationType pdaLocationType;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_WHL", sequenceName = "S_t_wh_sku_loc_type_cap", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WHL")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "QTY")
    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }


    @Column(name = "SKU_CODE")
    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    @Column(name = "SUPPLIER_CODE")
    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LOC_TYPE_ID")
    @Index(name = "IDX_FK_LOC_TYPE_ID")
    public PdaLocationType getPdaLocationType() {
        return pdaLocationType;
    }

    public void setPdaLocationType(PdaLocationType pdaLocationType) {
        this.pdaLocationType = pdaLocationType;
    }

}
