package com.jumbo.wms.model.warehouse;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;

@Entity
@Table(name="T_WH_DISTRIBUTION_AREA_LOC")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class DwhDistriButionAreaLoc extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6672612735868124566L;
    
	private Integer id;
	
	
	private Integer  distriButionAreaId;
	
	
	private Integer locationId;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_WH_DISTRIBUTION_AREA_LOC", sequenceName = "S_T_WH_DISTRIBUTION_AREA_LOC", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_WH_DISTRIBUTION_AREA_LOC")
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "DISTRIBUTION_AREA_ID")
	public Integer getDistriButionAreaId() {
		return distriButionAreaId;
	}


	public void setDistriButionAreaId(Integer distriButionAreaId) {
		this.distriButionAreaId = distriButionAreaId;
	}

	@Column(name = "LOCATION_ID")
	public Integer getLocationId() {
		return locationId;
	}


	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}
	
	
	
	
}
