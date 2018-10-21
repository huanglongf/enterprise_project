package com.jumbo.pms.model;

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

import org.hibernate.annotations.Index;

import com.jumbo.wms.model.BaseModel;

/**
 * 物流供应商和网点的关系
 * 
 * @author ShengGe
 * 
 */
@Entity
@Table(name = "T_MA_LP_BRANCH_RELATION")
public class LogisticsProviderBranchRelation extends BaseModel {

	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1710017895191518819L;

	/**
     * PK
     */
    private Long id;

	/**
	 * 物流供应商ID
	 */
    private LogisticsProvider logisticsProvider;
    
    /**
     * 网点库ID
     */
    private BranchLibrary branchLibrary;
    
    /**
     * 优先级
     */
    private Integer priority;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_LP_BRANCH_RELATION", sequenceName = "S_T_MA_LP_BRANCH_RELATION", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_LP_BRANCH_RELATION")
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LP_ID")
    @Index(name = "IDX_LP_ID")
	public LogisticsProvider getLogisticsProvider() {
		return logisticsProvider;
	}

	public void setLogisticsProvider(LogisticsProvider logisticsProvider) {
		this.logisticsProvider = logisticsProvider;
	}

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BL_ID")
    @Index(name = "IDX_BL_ID")
	public BranchLibrary getBranchLibrary() {
		return branchLibrary;
	}

	public void setBranchLibrary(BranchLibrary branchLibrary) {
		this.branchLibrary = branchLibrary;
	}

    @Column(name = "PRIORITY")
	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	
}
