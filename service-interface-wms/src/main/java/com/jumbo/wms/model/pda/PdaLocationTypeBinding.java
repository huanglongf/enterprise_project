package com.jumbo.wms.model.pda;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;


/**
 * 
 * @author lizaibiao
 * 
 */
@Entity
@Table(name = "T_WH_LOC_LOC_TYPE_REF")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class PdaLocationTypeBinding extends BaseModel {
    private static final long serialVersionUID = 4555953896487440573L;
    /**
     * PK
     */
    private Long id;
    /*
     * 库位id
     */
    private Long locationId;
    /**
     * 库位类型id
     */
    private Long locTypeId;


    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_WHL", sequenceName = "S_T_WH_LOC_LOC_TYPE_REF", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WHL")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "LOCATION_ID")
    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    @Column(name = "LOC_TYPE_ID")
    public Long getLocTypeId() {
        return locTypeId;
    }

    public void setLocTypeId(Long locTypeId) {
        this.locTypeId = locTypeId;
    }

}
