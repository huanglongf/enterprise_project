package com.jumbo.wms.model.pickZone;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.jumbo.wms.model.BaseModel;


/**
 * The persistent class for the T_WH_LOCATION_LOG database table.
 * 
 */
@Entity
@Table(name="T_WH_LOCATION_LOG")
public class WhLocationLog extends BaseModel {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1501445178261759166L;
    private Long id;
	private String code;
	private Date lastModifyTime;
	private Long lastModifyUserId;
	private Integer sort;
	private Long whId;
	private Long zoonId;

	@Id
    @SequenceGenerator(name = "T_WH_LOCATION_LOG_ID_GENERATOR", sequenceName = "S_T_WH_LOCATION_LOG", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="T_WH_LOCATION_LOG_ID_GENERATOR")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="LAST_MODIFY_TIME")
	public Date getLastModifyTime() {
		return this.lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}


	@Column(name="LAST_MODIFY_USER_ID")
	public Long getLastModifyUserId() {
		return this.lastModifyUserId;
	}

	public void setLastModifyUserId(Long lastModifyUserId) {
		this.lastModifyUserId = lastModifyUserId;
	}


    @Column(name = "SORT")
	public Integer getSort() {
		return this.sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}


	@Column(name="WH_ID")
	public Long getWhId() {
		return this.whId;
	}

	public void setWhId(Long whId) {
		this.whId = whId;
	}


	@Column(name="ZOON_ID")
	public Long getZoonId() {
		return this.zoonId;
	}

	public void setZoonId(Long zoonId) {
		this.zoonId = zoonId;
	}

}