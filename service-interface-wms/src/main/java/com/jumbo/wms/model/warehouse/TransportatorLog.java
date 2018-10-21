package com.jumbo.wms.model.warehouse;

import java.util.Date;

import javax.persistence.Column;


import com.jumbo.wms.model.BaseModel;

//@Entity
//@Table(name = "t_ma_transportator_log")
//@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class TransportatorLog extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7046152522561794275L;
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 状态
	 */
	private Long lifeCycleStatus;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 编码
	 */
	private String code;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 全称
	 */
	private String fullName;
	/**
	 * 仓库编码
	 */
	private String expCode;
	/**
	 * 是否支持COD
	 */
	private boolean isSupportCod;
	/**
	 * 最后修改时间
	 */
	private Date lastModifyTime;
	/**
	 * 平台编码
	 */
	private String platformCode;
	/**
	 * 电子面单模版
	 */
	private String jasperOnline;
	/**
	 * 纸张面单模版
	 */
	private String jasperNormal;
	/**
	 * 是否支持后置打印
	 */
	private boolean isTransAfter;
	/**
	 * 是否分区域
	 */
	private boolean isRegion;
	/**
	 * 最后修改人id
	 */
	private Long userId;
	@Column(name = "id")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name = "LIFE_CYCLE_STATUS")
	public Long getLifeCycleStatus() {
		return lifeCycleStatus;
	}
	public void setLifeCycleStatus(Long lifeCycleStatus) {
		this.lifeCycleStatus = lifeCycleStatus;
	}
	@Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Column(name = "CODE")
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@Column(name = "NAME")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name = "FULL_NAME")
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	@Column(name = "EXP_CODE")
	public String getExpCode() {
		return expCode;
	}
	public void setExpCode(String expCode) {
		this.expCode = expCode;
	}
	@Column(name = "IS_SUPPORT_COD")
	public boolean isSupportCod() {
		return isSupportCod;
	}
	public void setSupportCod(boolean isSupportCod) {
		this.isSupportCod = isSupportCod;
	}
	@Column(name = "LAST_MODIFY_TIME")
	public Date getLastModifyTime() {
		return lastModifyTime;
	}
	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}
	@Column(name = "PLATFORM_CODE")
	public String getPlatformCode() {
		return platformCode;
	}
	public void setPlatformCode(String platformCode) {
		this.platformCode = platformCode;
	}
	@Column(name = "JASPER_ONLINE")
	public String getJasperOnline() {
		return jasperOnline;
	}
	public void setJasperOnline(String jasperOnline) {
		this.jasperOnline = jasperOnline;
	}
	@Column(name = "JASPER_NORMAL")
	public String getJasperNormal() {
		return jasperNormal;
	}
	public void setJasperNormal(String jasperNormal) {
		this.jasperNormal = jasperNormal;
	}
	@Column(name = "IS_TRANS_AFTER")
	public boolean isTransAfter() {
		return isTransAfter;
	}
	public void setTransAfter(boolean isTransAfter) {
		this.isTransAfter = isTransAfter;
	}
	@Column(name = "IS_REGION")
	public boolean isRegion() {
		return isRegion;
	}
	public void setRegion(boolean isRegion) {
		this.isRegion = isRegion;
	}
	@Column(name = "user_id")
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	

}
