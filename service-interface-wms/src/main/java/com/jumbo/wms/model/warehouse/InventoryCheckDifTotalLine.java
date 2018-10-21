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
import javax.persistence.Version;
import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.baseinfo.Sku;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.OptimisticLockType;

/**
 * 库存盘点差异汇总
 * 
 * @author sjk
 * 
 */

@Entity
@Table(name = "T_WH_INV_CK_DIF_TOTAL_LINE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class InventoryCheckDifTotalLine extends BaseModel {

    private static final long serialVersionUID = -6255408108206382834L;
    /**
     * Pk
     */
    private Long id;
    /**
     * version
     */
    private int version;
    /**
     * 相关Sku
     */
    private Sku sku;
    /**
     * 差异量
     */
    private Long quantity;

    /**
     * 相关库存状态
     */
    private InventoryStatus status;
    /**
     * sku成本
     */
    private BigDecimal skuCost;
    /**
     * 库存盘点批
     */
    private InventoryCheck inventoryCheck;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_ICDTL", sequenceName = "S_T_WH_INV_CK_DIF_TOTAL_LINE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ICDTL")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Version
    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SKU_ID")
    @Index(name = "IDX_INV_CK_D_T_L_SKU")
    public Sku getSku() {
        return sku;
    }

    public void setSku(Sku sku) {
        this.sku = sku;
    }

    @Column(name = "QUANTITY")
    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STATUS_ID")
    @Index(name = "IDX_INV_CK_D_T_L_INVS")
    public InventoryStatus getStatus() {
        return status;
    }

    public void setStatus(InventoryStatus status) {
        this.status = status;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INV_CHECK_ID")
    @Index(name = "IDX_INV_CK_D_T_L_ID")
    public InventoryCheck getInventoryCheck() {
        return inventoryCheck;
    }

    public void setInventoryCheck(InventoryCheck inventoryCheck) {
        this.inventoryCheck = inventoryCheck;
    }

    @Column(name = "SKU_COST", precision = 22, scale = 8)
    public BigDecimal getSkuCost() {
        return skuCost;
    }

    public void setSkuCost(BigDecimal skuCost) {
        this.skuCost = skuCost;
    }

}
