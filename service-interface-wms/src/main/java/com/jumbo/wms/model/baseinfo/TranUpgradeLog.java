package com.jumbo.wms.model.baseinfo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.warehouse.TransTimeType;
import com.jumbo.wms.model.warehouse.TransType;

/**
 * 升单日志
 * 
 * @author cheng.su
 * 
 */
@Entity
@Table(name = "t_wh_sta_tran_upgrade_log")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class TranUpgradeLog extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1567657933531063702L;
	/**
	 * PK
	 */
	private Long id;      
	/**
	 * 创建时间
	 */
	private Date createTime;		
	/**
	 * 原路由
	 */
	private String fLpcode;        
	/**
	 * 升级后路由
	 */
	private String tLpcode;        
	/**
	 * 原快递类型
	 */
	private TransType fTransType;      
	/**
	 * 升级后快递类型
	 */
	private TransType tTransType;      
	/**
	 * 原快递时效类型
	 */
	private TransTimeType fTransTimeType;	
	/**
	 * 升级后快递时效类型
	 */
	private TransTimeType tTransTimeType;	
	/**
	 * 是否取消
	 */
	private Boolean Iscancel;	    
	/**
	 * 操作人
	 */
	private Long userId;	        
	/**
	 * 作业单ID
	 */
	private Long staId;	        
	
	@Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_UPGRADE", sequenceName = "S_t_wh_sta_tran_upgrade_log", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_UPGRADE")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Column(name = "f_lpcode")
	public String getfLpcode() {
		return fLpcode;
	}
	public void setfLpcode(String fLpcode) {
		this.fLpcode = fLpcode;
	}
	@Column(name = "t_lpcode")
	public String gettLpcode() {
		return tLpcode;
	}
	public void settLpcode(String tLpcode) {
		this.tLpcode = tLpcode;
	}
	@Column(name = "f_trans_type", columnDefinition = "integer")
	 @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.TransType")})
	public TransType getfTransType() {
		return fTransType;
	}
	public void setfTransType(TransType fTransType) {
		this.fTransType = fTransType;
	}
	@Column(name = "t_trans_type", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.TransType")})
	public TransType gettTransType() {
		return tTransType;
	}
	public void settTransType(TransType tTransType) {
		this.tTransType = tTransType;
	}
	@Column(name = "f_trans_time_type",columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.TransTimeType")})
	public TransTimeType getfTransTimeType() {
		return fTransTimeType;
	}
	public void setfTransTimeType(TransTimeType fTransTimeType) {
		this.fTransTimeType = fTransTimeType;
	}
	@Column(name = "t_trans_time_type",columnDefinition = "integer")
	@Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.TransTimeType")})
	public TransTimeType gettTransTimeType() {
		return tTransTimeType;
	}
	public void settTransTimeType(TransTimeType tTransTimeType) {
		this.tTransTimeType = tTransTimeType;
	}
	@Column(name = "Is_cancel")
	public Boolean getIscancel() {
		return Iscancel;
	}
	public void setIscancel(Boolean iscancel) {
		Iscancel = iscancel;
	}
	@Column(name = "user_id")
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	@Column(name = "sta_id")
	public Long getStaId() {
		return staId;
	}
	public void setStaId(Long staId) {
		this.staId = staId;
	}
	
}
