package com.jumbo.pms.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;

/**
 * 物流服务商
 * 
 * @author ShengGe
 * 
 */
@Entity
@Table(name = "T_MA_LOGISTICS_PROVIDER")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class LogisticsProvider extends BaseModel {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 8393682160175433165L;

	/**
     * PK
     */
    private Long id;

    /**
     * Version
     */
    private Date version;
	
    /**
     * 创建时间
     */
    private Date createTime = new Date();
    
    /**
     * 物流商对接编码
     */
    private String code;
    
    /**
     * 生命周期
     */
    private LifeCycleStatus lifeCycleStatus;

    /**
     * 物流商名称
     */
    private String name;

    /**
     * 物流商全称
     */
    private String fullName;

    /**
     * 是否支持COD
     */
    private Boolean isSupportCod;

    /**
     * 货到付款最大金额
     */
    private BigDecimal codMaxAmt;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_LOGISTICS_PROVIDER", sequenceName = "S_T_MA_LOGISTICS_PROVIDER", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_LOGISTICS_PROVIDER")
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
    @Version
    @Column(name = "VERSION")
	public Date getVersion() {
		return version;
	}
	
	public void setVersion(Date version) {
		this.version = version;
	}

    @Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "CODE", length = 50)
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
    @Column(name = "LIFE_CYCLE_STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.pms.model.LifeCycleStatus")})
    public LifeCycleStatus getLifeCycleStatus() {
		return lifeCycleStatus;
	}

	public void setLifeCycleStatus(LifeCycleStatus lifeCycleStatus) {
		this.lifeCycleStatus = lifeCycleStatus;
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

    @Column(name = "IS_SUPPORT_COD")
	public Boolean getIsSupportCod() {
		return isSupportCod;
	}

	public void setIsSupportCod(Boolean isSupportCod) {
		this.isSupportCod = isSupportCod;
	}

    @Column(name = "COD_MAX_AMT")
	public BigDecimal getCodMaxAmt() {
		return codMaxAmt;
	}

	public void setCodMaxAmt(BigDecimal codMaxAmt) {
		this.codMaxAmt = codMaxAmt;
	}

}
