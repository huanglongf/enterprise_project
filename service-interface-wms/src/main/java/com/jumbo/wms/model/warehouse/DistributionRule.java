package com.jumbo.wms.model.warehouse;

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
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.authorization.User;

/**
 *  配货规则维护
 *  @author NCGZ-DZ-SJ
 */
@Entity
@Table(name = "T_WH_DISTRIBUTION_RULE")
public class DistributionRule extends BaseModel {
	
    private static final long serialVersionUID = -8250229447683053063L;
	
    private Long id;
    private String name;//规则名称
   
    private OperationUnit ouId;//所属仓库
   
    private User creatorId;//创建人
   
    private Date createTime;//创建时间
   
    private Date lastModifyTime;//最后修改时间
   
    private DistributionRuleStatus status;//状态
   
    private int version;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_WH_DISTRIBUTION_RULE", sequenceName = "S_T_WH_DISTRIBUTION_RULE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_WH_DISTRIBUTION_RULE")
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "NAME")
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OU_ID")
	public OperationUnit getOuId() {
		return ouId;
	}
	
	public void setOuId(OperationUnit ouId) {
		this.ouId = ouId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATOR_ID")
	public User getCreatorId() {
		return creatorId;
	}
	
	public void setCreatorId(User creatorId) {
		this.creatorId = creatorId;
	}
	
	@Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Column(name = "LAST_MODIFY_TIME")
	public Date getLastModifyTime() {
		return lastModifyTime;
	}
	
	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	@Column(name = "STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.DistributionRuleStatus")})
	public DistributionRuleStatus getStatus() {
		return status;
	}

	public void setStatus(DistributionRuleStatus status) {
		this.status = status;
	}

	@Column(name = "VERSION")
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	
}
