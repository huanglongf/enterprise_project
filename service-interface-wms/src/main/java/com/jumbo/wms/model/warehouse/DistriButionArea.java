package com.jumbo.wms.model.warehouse;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;

import io.swagger.models.auth.In;
/***
 * 
 * @author lijinggong+2018年7月26日
 *
 *
 */
@Entity
@Table(name = "T_WH_DISTRIBUTION_AREA")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class DistriButionArea extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 61904992389498144L;
	
	
	private Long id;
	
	private String distriButionAreaCode;
	
	private String distriButionAreaName;
	
	private Long mainWhid;
	
	private Date createTime;
	
    private Long createId; 
    
    private String createUser;
    
    private Integer version;
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_WH_DISTRIBUTION_AREA", sequenceName = "S_T_WH_DISTRIBUTION_AREA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_WH_DISTRIBUTION_AREA")
    public Long getId() {
        return id;
    }

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "DISTRIBUTION_AREA_CODE")
	public String getDistriButionAreaCode() {
		return distriButionAreaCode;
	}

	public void setDistriButionAreaCode(String distriButionAreaCode) {
		this.distriButionAreaCode = distriButionAreaCode;
	}
    
	@Column(name = "DISTRIBUTION_AREA_NAME")
	public String getDistriButionAreaName() {
		return distriButionAreaName;
	}
    
	
	public void setDistriButionAreaName(String distriButionAreaName) {
		this.distriButionAreaName = distriButionAreaName;
	}
    
	@Column(name = "MAIN_WH_ID")
	public Long getMainWhid() {
		return mainWhid;
	}

	public void setMainWhid(Long mainWhid) {
		this.mainWhid = mainWhid;
	}
    
	@Column(name = "CREATE_TIME")
	public Date getCreatETime() {
		return createTime;
	}

	public void setCreatETime(Date creatETime) {
		createTime = creatETime;
	}
    
	@Column(name = "CREATE_ID")
	public Long getCreateId() {
		return createId;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}
    
	@Column(name = "CREATE_USER")
	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
    
	@Column(name = "VERSION")
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	
    
}
