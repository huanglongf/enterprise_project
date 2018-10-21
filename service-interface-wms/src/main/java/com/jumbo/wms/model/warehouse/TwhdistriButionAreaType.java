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
@Table(name = "T_WH_DISTRIBUTION_AREA_TYPE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class TwhdistriButionAreaType extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -777365908288803781L;
	private Long id;
	private Long distriButionAreaId;
	private Long transActionTypeId;
	
	@Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_WH_DISTRIBUTION_AREA_TYPE", sequenceName = "S_T_WH_DISTRIBUTION_AREA_TYPE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_WH_DISTRIBUTION_AREA_TYPE")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "DISTRIBUTION_AREA_ID")
	public Long getDistriButionAreaId() {
		return distriButionAreaId;
	}
	public void setDistriButionAreaId(Long distriButionAreaId) {
		this.distriButionAreaId = distriButionAreaId;
	}
	
	@Column(name = "TRANSACTION_TYPE_ID")
	public Long getTransActionTypeId() {
		return transActionTypeId;
	}
	public void setTransActionTypeId(Long transActionTypeId) {
		this.transActionTypeId = transActionTypeId;
	}
	
	
    
}
