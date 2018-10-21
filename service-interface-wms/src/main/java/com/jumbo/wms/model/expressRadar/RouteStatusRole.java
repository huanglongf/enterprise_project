package com.jumbo.wms.model.expressRadar;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.baseinfo.Transportator;


/**
 * @author lihui
 *
 * 2015年5月25日 下午4:45:11
 */
@Entity
@Table(name="T_OOC_ROUTE_STATUS_ROLE")
public class RouteStatusRole extends BaseModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6354594000124385878L;
	private Long id;
	private String code;
	private String memo;
	private Transportator transportator;
	private SysRouteStatusCode SysRouteStatusCode;

	public RouteStatusRole() {
	}


	@Id
	@SequenceGenerator(name="T_OOC_ROUTE_STATUS_ROLE_ID_GENERATOR", sequenceName="S_T_SYS_ROUTE_STATUS_CODE", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="T_OOC_ROUTE_STATUS_ROLE_ID_GENERATOR")
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


	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "T_ID")
	public Transportator getTransportator() {
		return this.transportator;
	}

	public void setTransportator(Transportator transportator) {
		this.transportator = transportator;
	}


	//uni-directional many-to-one association to SysRouteStatusCode
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="SYS_RSC_ID")
	public SysRouteStatusCode getSysRouteStatusCode() {
		return this.SysRouteStatusCode;
	}

	public void setSysRouteStatusCode(SysRouteStatusCode SysRouteStatusCode) {
		this.SysRouteStatusCode = SysRouteStatusCode;
	}

}