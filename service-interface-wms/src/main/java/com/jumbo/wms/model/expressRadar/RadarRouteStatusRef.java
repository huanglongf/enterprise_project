package com.jumbo.wms.model.expressRadar;

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
import javax.persistence.Version;

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.baseinfo.Transportator;

/**
 * @author lihui 快递运单路由状态关联 2015年5月25日
 */
@Entity
@Table(name = "T_OOC_RD_ROUTE_STATUS_REF")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class RadarRouteStatusRef extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -10570790366167790L;
	/**
	 * id
	 */
	private Long id;
	/**
	 * 创建时间
	 */
	private Date creareTime = new Date();
	/**
	 * 创建人
	 */
	private User user;
	/**
	 * 最后修改时间
	 */
	private Date lastModifyTime = new Date();
	/**
	 * 数据状态（1：使用中 10:禁用）
	 */
	private Integer status;
	/**
	 * 物流商
	 */
	private Transportator transportator;
	/**
	 * 版本
	 */
	private Integer version;
	/**
	 * 系统路由状态
	 */
	private SysRouteStatusCode sysRouteStatusCode;
	/**
	 * 备注
	 */
	private String remark;

	public RadarRouteStatusRef() {
	}

	@Id
	@Column(name = "ID")
	@SequenceGenerator(name = "SEQ_OOC_RD_ROUTE_STATUS_REF", sequenceName = "S_T_OOC_RD_ROUTE_STATUS_REF", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_OOC_RD_ROUTE_STATUS_REF")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "CREARE_TIME")
	public Date getCreareTime() {
		return this.creareTime;
	}

	public void setCreareTime(Date creareTime) {
		this.creareTime = creareTime;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CREATE_ID")
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	@Column(name = "LAST_MODIFY_TIME")
	public Date getLastModifyTime() {
		return this.lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TP_ID")
	public Transportator getTransportator() {
		return this.transportator;
	}

	public void setTransportator(Transportator transportator) {
		this.transportator = transportator;
	}

	@Version
	@Column(name = "version")
	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	// uni-directional many-to-one association to SysRouteStatusCode
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SYS_RSC_ID")
	public SysRouteStatusCode getSysRouteStatusCode() {
		return this.sysRouteStatusCode;
	}

	public void setSysRouteStatusCode(SysRouteStatusCode sysRouteStatusCode) {
		this.sysRouteStatusCode = sysRouteStatusCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}