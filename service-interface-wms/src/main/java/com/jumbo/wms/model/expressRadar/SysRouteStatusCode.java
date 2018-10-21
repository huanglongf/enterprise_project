package com.jumbo.wms.model.expressRadar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.jumbo.wms.model.BaseModel;

/**
 * @author lihui
 *
 *         2015年5月25日 下午4:45:20
 */
@Entity
@Table(name = "T_SYS_ROUTE_STATUS_CODE")
public class SysRouteStatusCode extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8674791602130916214L;
	private Long id;
	private String code;
	private String description;
	private String name;



	@Id
	@Column(name = "ID")
	@SequenceGenerator(name = "SEQ_SYS_ROUTE_STATUS_CODE", sequenceName = "S_T_SYS_ROUTE_STATUS_CODE", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SYS_ROUTE_STATUS_CODE")
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

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}