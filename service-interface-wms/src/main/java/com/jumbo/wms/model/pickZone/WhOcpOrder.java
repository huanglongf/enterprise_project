package com.jumbo.wms.model.pickZone;

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
import com.jumbo.wms.model.authorization.OperationUnit;


/**
 * The persistent class for the T_WH_OCP_ORDER database table.
 * 
 */
@Entity
@Table(name="T_WH_OCP_ORDER")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class WhOcpOrder extends BaseModel {
    
    /**
     * 
     */
    private static final long serialVersionUID = -7966787313209051707L;
    private Long id;
    /**
     * 占用批编码
     */
	private String code;
    /**
     * 创建时间
     */
	private Date createTime;
    /**
     * 按作业单占用失败次数
     */
	private Integer errorCount;
    /**
     * 最后修改时间
     */
	private Date lastModifyTime;
    /**
     * 状态（1：新建2：处理中5：占用完成10：完成99：异常）
     */
	private Integer status;
    /**
     * 版本号
     */
	private Integer version;
    /**
     * 仓库组织
     */
    private OperationUnit operationUnit;

    /**
     * 占用批占用批次编码
     */
    private String ocpBatchCode;



	@Id
    @SequenceGenerator(name = "T_WH_OCP_ORDER_ID_GENERATOR", sequenceName = "S_T_WH_OCP_ORDER", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="T_WH_OCP_ORDER_ID_GENERATOR")
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


	@Column(name="CREATE_TIME")
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	@Column(name="ERROR_COUNT")
	public Integer getErrorCount() {
		return this.errorCount;
	}

	public void setErrorCount(Integer errorCount) {
		this.errorCount = errorCount;
	}


	@Column(name="LAST_MODIFY_TIME")
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


	@Version
	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WH_ID")
    public OperationUnit getOperationUnit() {
        return operationUnit;
    }


    public void setOperationUnit(OperationUnit operationUnit) {
        this.operationUnit = operationUnit;
    }


    @Column(name = "OCP_BATCH_CODE")
    public String getOcpBatchCode() {
        return ocpBatchCode;
    }

    public void setOcpBatchCode(String ocpBatchCode) {
        this.ocpBatchCode = ocpBatchCode;
    }
    @Override
    public String toString() {
        return "WhOcpOrder [id=" + id + ", code=" + code + ", createTime=" + createTime + ", errorCount=" + errorCount + ", lastModifyTime=" + lastModifyTime + ", status=" + status + ", version=" + version + ", operationUnit=" + operationUnit + "]";
    }


	

}