package com.jumbo.wms.model.vmi.warehouse;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.warehouse.WarehouseLocation;

@Entity
@Table(name = "T_WH_LOC_INVSTATUS")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class LocationInvStatus extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = -9112450594706206554L;

    private Long id;

    private WarehouseLocation locID;

    private Long invStatusID;

    private Boolean isForSales;

    private String source;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_WH_LOC_INVSTATUS", sequenceName = "S_T_WH_LOC_INVSTATUS", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_WH_LOC_INVSTATUS")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // LOC_ID
    @Column(name = "INV_STATUS_ID")
    public Long getInvStatusID() {
        return invStatusID;
    }

    public WarehouseLocation getLocID() {
        return locID;
    }

    public void setLocID(WarehouseLocation locID) {
        this.locID = locID;
    }

    public void setInvStatusID(Long invStatusID) {
        this.invStatusID = invStatusID;
    }

    @Column(name = "SOURCE")
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Column(name = "IS_FOR_SALES")
    public Boolean getIsForSales() {
        return isForSales;
    }

    public void setIsForSales(Boolean isForSales) {
        this.isForSales = isForSales;
    }

}
