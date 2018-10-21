package com.jumbo.wms.model.baseinfo;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;

@Entity
@Table(name = "T_WH_VEHICLE_STANDARD")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class VehicleStandard extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 5974911004777018881L;

    /**
     * PK
     */
    private Long id;

    /**
     * 规格编码//
     */
    private String standardCode;

    /**
     * 车辆体积
     */
    private BigDecimal vehicleVolume;

    /**
     * version
     */
    private int version;


    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "S_T_WH_VEHICLE_STANDARD", sequenceName = "S_T_WH_VEHICLE_STANDARD", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_T_WH_VEHICLE_STANDARD")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "STANDARD_CODE")
    public String getStandardCode() {
        return standardCode;
    }

    public void setStandardCode(String standardCode) {
        this.standardCode = standardCode;
    }

    @Column(name = "VEHICLE_VOLUME")
    public BigDecimal getVehicleVolume() {
        return vehicleVolume;
    }

    public void setVehicleVolume(BigDecimal vehicleVolume) {
        this.vehicleVolume = vehicleVolume;
    }

    @Version
    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }



}
