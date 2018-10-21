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
import javax.persistence.Version;
import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.baseinfo.Sku;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

/**
 * 库存盘点sn明细
 * 
 * @author sjk
 * 
 */

@Entity
@Table(name = "T_WH_INV_CHECK_DIF_SN_LINE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class InventoryCheckDifferenceSnLine extends BaseModel {
    /**
	 * 
	 */
    private static final long serialVersionUID = 5583720461243696287L;
    /**
     * Pk
     */
    private Long id;
    /**
     * version
     */
    private int version;
    /**
     * sn
     */
    private String sn;
    /**
     * 相关Sku
     */
    private Sku sku;
    /**
     * 行类型
     */
    private InventoryCheckDifferenceSnLineType type;

    /**
     * 库存盘点批
     */
    private InventoryCheck inventoryCheck;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_PKLT", sequenceName = "S_T_WH_INV_CK_DIF_SN_LINE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PKLT")
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
    @Index(name = "IDX_INV_CK_DIF_SN_L_SKU")
    public Sku getSku() {
        return sku;
    }

    public void setSku(Sku sku) {
        this.sku = sku;
    }

    @Column(name = "TYPE", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.InventoryCheckDifferenceSnLineType")})
    public InventoryCheckDifferenceSnLineType getType() {
        return type;
    }

    public void setType(InventoryCheckDifferenceSnLineType type) {
        this.type = type;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INV_CHECK_ID")
    @Index(name = "IDX_INV_CK_DIF_SN_L_ID")
    public InventoryCheck getInventoryCheck() {
        return inventoryCheck;
    }

    public void setInventoryCheck(InventoryCheck inventoryCheck) {
        this.inventoryCheck = inventoryCheck;
    }

    @Column(name = "SN", length = 50)
    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }
}
