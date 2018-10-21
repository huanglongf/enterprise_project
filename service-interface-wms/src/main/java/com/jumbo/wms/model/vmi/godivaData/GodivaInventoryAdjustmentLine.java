package com.jumbo.wms.model.vmi.godivaData;

import java.util.Date;

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
import com.jumbo.wms.model.warehouse.InventoryStatus;

@Entity
@Table(name = "T_WH_GDV_INVENTORY_ADJ_LINE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class GodivaInventoryAdjustmentLine extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = -3096165401336125336L;


    private Long Id;

    private String adjustmentKey;

    private String warehouseCode;

    private String locationIdentifier;

    private String sku;

    private Date effectiveDate;

    private Long availableGood;

    private Long availableDamaged;

    private Long adjustmentQty;

    /**
     * 库存状态
     */
    private InventoryStatus invStatus;

    private Long availableGoodBase;

    private Long availableDamagedBase;

    private GodivaInventoryAdjustment godivaInventoryAdjustment;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_WH_GDV_INVENTORY_ADJUSTMENT_LINE", sequenceName = "S_WH_GDV_INVENTORY_ADJ_LINE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WH_GDV_INVENTORY_ADJUSTMENT_LINE")
    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    @Column(name = "ADJUSTMENT_KEY", length = 30)
    public String getAdjustmentKey() {
        return adjustmentKey;
    }

    public void setAdjustmentKey(String adjustmentKey) {
        this.adjustmentKey = adjustmentKey;
    }

    @Column(name = "WAREHOUSE_CODE", length = 30)
    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    @Column(name = "LOCATION_IDENTIFIER", length = 30)
    public String getLocationIdentifier() {
        return locationIdentifier;
    }

    public void setLocationIdentifier(String locationIdentifier) {
        this.locationIdentifier = locationIdentifier;
    }

    @Column(name = "SKU", length = 30)
    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    @Column(name = "EFFECTIVE_DATE")
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    @Column(name = "AVAILABLE_GOOD")
    public Long getAvailableGood() {
        return availableGood;
    }

    public void setAvailableGood(Long availableGood) {
        this.availableGood = availableGood;
    }

    @Column(name = "AVAILABLE_DAMAGED")
    public Long getAvailableDamaged() {
        return availableDamaged;
    }

    public void setAvailableDamaged(Long availableDamaged) {
        this.availableDamaged = availableDamaged;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GODV_ADJUSTMENT_ID")
    @Index(name = "IDX_GODV_ADJUSTMENT_LINE")
    public GodivaInventoryAdjustment getGodivaInventoryAdjustment() {
        return godivaInventoryAdjustment;
    }

    public void setGodivaInventoryAdjustment(GodivaInventoryAdjustment godivaInventoryAdjustment) {
        this.godivaInventoryAdjustment = godivaInventoryAdjustment;
    }

    @Column(name = "AVAILABLE_GOODBASE")
    public Long getAvailableGoodBase() {
        return availableGoodBase;
    }

    public void setAvailableGoodBase(Long availableGoodBase) {
        this.availableGoodBase = availableGoodBase;
    }

    @Column(name = "AVAILABLE_DAMAGEDBASE")
    public Long getAvailableDamagedBase() {
        return availableDamagedBase;
    }

    public void setAvailableDamagedBase(Long availableDamagedBase) {
        this.availableDamagedBase = availableDamagedBase;
    }

    @Column(name = "ADJUSTMENT_QTY")
    public Long getAdjustmentQty() {
        return adjustmentQty;
    }

    public void setAdjustmentQty(Long adjustmentQty) {
        this.adjustmentQty = adjustmentQty;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INV_STATUS_ID")
    @Index(name = "IDX_MSG_GINV_STATUS")
    public InventoryStatus getInvStatus() {
        return invStatus;
    }

    public void setInvStatus(InventoryStatus invStatus) {
        this.invStatus = invStatus;
    }

}
