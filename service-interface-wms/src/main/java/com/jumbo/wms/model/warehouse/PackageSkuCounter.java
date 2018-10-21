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
import com.jumbo.wms.model.baseinfo.Sku;

/**
 * 套装组合商品计数器
 * 
 * @author jinlong.ke
 * 
 */
@Entity
@Table(name = "T_WH_PACKAGE_SKU_COUNTER")
public class PackageSkuCounter extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = -4526665865331066725L;

    /**
     * id
     */
    private Long id;
    /**
     * 作业单商品总数量
     */
    private Long staTotalSkuQty;
    /**
     * 商品数量
     */
    private Long skuQty;
    /**
     * 作业单数量
     */
    private Long staQty;
    /**
     * 商品
     */
    private Sku sku;
    /**
     * 仓库组织节点id
     */
    private OperationUnit ou;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_PACKAGE_SKU_COUNTER", sequenceName = "S_T_WH_PACKAGE_SKU_COUNTER", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PACKAGE_SKU_COUNTER")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "STA_TOTAL_SKU_QTY")
    public Long getStaTotalSkuQty() {
        return staTotalSkuQty;
    }

    public void setStaTotalSkuQty(Long staTotalSkuQty) {
        this.staTotalSkuQty = staTotalSkuQty;
    }

    @Column(name = "SKU_QTY")
    public Long getSkuQty() {
        return skuQty;
    }

    public void setSkuQty(Long skuQty) {
        this.skuQty = skuQty;
    }

    @Column(name = "STA_QTY")
    public Long getStaQty() {
        return staQty;
    }

    public void setStaQty(Long staQty) {
        this.staQty = staQty;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SKU_ID")
    @Index(name = "IDX_PACKAGESKUCOUNTER_SKU")
    public Sku getSku() {
        return sku;
    }

    public void setSku(Sku sku) {
        this.sku = sku;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OU_ID")
    @Index(name = "IDX_PACKAGESKUCOUNTER_OU")
    public OperationUnit getOu() {
        return ou;
    }

    public void setOu(OperationUnit ou) {
        this.ou = ou;
    }

}
