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
import org.hibernate.annotations.Index;
import org.hibernate.annotations.OptimisticLockType;

/**
 * 库存盘点明细
 * 
 * @author sjk
 * 
 */
@Entity
@Table(name = "T_WH_INV_CHECK_LINE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class InventoryCheckLine extends BaseModel {

    private static final long serialVersionUID = 8346907710411096273L;
    /**
     * Pk
     */
    private Long id;
    /**
     * version
     */
    private int version;
    /**
     * 相关库区
     */
    private WarehouseDistrict district;
    /**
     * 相关库位
     */
    private WarehouseLocation location;
    /**
     * 库存盘点批
     */
    private InventoryCheck inventoryCheck;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_PKLT", sequenceName = "S_T_WH_INV_CHECK_LINE", allocationSize = 1)
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
    @JoinColumn(name = "DISTRICT_ID")
    @Index(name = "IDX_INV_CK_LINE_WHD_ID")
    public WarehouseDistrict getDistrict() {
        return district;
    }

    public void setDistrict(WarehouseDistrict district) {
        this.district = district;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LOCATION_ID")
    @Index(name = "IDX_INV_CK_LINE_WHL_ID")
    public WarehouseLocation getLocation() {
        return location;
    }

    public void setLocation(WarehouseLocation location) {
        this.location = location;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INV_CHECK_ID")
    @Index(name = "IDX_INV_CK_ID")
    public InventoryCheck getInventoryCheck() {
        return inventoryCheck;
    }

    public void setInventoryCheck(InventoryCheck inventoryCheck) {
        this.inventoryCheck = inventoryCheck;
    }

}
