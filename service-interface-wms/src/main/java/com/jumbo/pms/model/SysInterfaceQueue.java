package com.jumbo.pms.model;

import java.util.Calendar;
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
 * PMS通知队列
 * 	包裹出库通知
 * 	包裹状态变更通知
 * 	包裹上门取件通知
 * @author SG
 * 
 */
@Entity
@Table(name = "T_SYS_INTERFACE_QUEUE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class SysInterfaceQueue extends BaseModel {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 2032213388159809733L;

	/**
     * ID
     */
    private Long id;
    
    private int version;
    
    /**
     * 创建时间
     */
    private Date createTime = Calendar.getInstance().getTime();
    
    /**
     * parcel.code
     */
    private String code;

    /**
     * 状态
     */
    private SysInterfaceQueueStatus status;
    
    /**
     * 运单号
     */
    private String mailNo;
    
   /**
    * 物流服务商  SF,EMS ,STO...
    */
    private String lpcode;

    /**
     * BZ订单号
     */
    private String omsOrderCode;
    
    /**
     * 外部平台订单号
     */
    private String outerOrderCode;
    
    /**
     * 接口类型
     */
    private SysInterfaceQueueType type;
    
    /**
     * 处理时间
     */
    private Date opTime;
    
    /**
     * 目标系统
     */
    private SysInterfaceQueueSysType targetSys;
    
    /**
     * 接口反馈内容
     */
    private String content;
    
    /**
     * 操作备注
     */
    private String remark;
    
    /**
     * 错误次数
     */
    private Integer errorCount;
    
    /**
     * 错误编码
     */
    private Integer errorCode;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_SYS_INTERFACE_QUEUE", sequenceName = "S_T_SYS_INTERFACE_QUEUE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_SYS_INTERFACE_QUEUE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    @Version
    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
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

	@Column(name = "STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.pms.model.SysInterfaceQueueStatus")})
    public SysInterfaceQueueStatus getStatus() {
        return status;
    }

    public void setStatus(SysInterfaceQueueStatus status) {
        this.status = status;
    }

	@Column(name = "MAIL_NO", length = 50)
    public String getMailNo() {
		return mailNo;
	}

	public void setMailNo(String mailNo) {
		this.mailNo = mailNo;
	}

	@Column(name = "LPCODE", length = 50)
	public String getLpcode() {
		return lpcode;
	}

	public void setLpcode(String lpcode) {
		this.lpcode = lpcode;
	}

	@Column(name = "OMS_ORDER_CODE")
    public String getOmsOrderCode() {
		return omsOrderCode;
	}

	public void setOmsOrderCode(String omsOrderCode) {
		this.omsOrderCode = omsOrderCode;
	}

	@Column(name = "OUTER_ORDER_CODE")
	public String getOuterOrderCode() {
		return outerOrderCode;
	}

	public void setOuterOrderCode(String outerOrderCode) {
		this.outerOrderCode = outerOrderCode;
	}

	@Column(name = "TYPE", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.pms.model.SysInterfaceQueueType")})
    public SysInterfaceQueueType getType() {
        return type;
    }

    public void setType(SysInterfaceQueueType type) {
        this.type = type;
    }
    
    @Column(name = "OP_TIME")
    public Date getOpTime() {
		return opTime;
	}

	public void setOpTime(Date opTime) {
		this.opTime = opTime;
	}

	@Column(name = "TARGET_SYS", columnDefinition = "integer")
	@Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.pms.model.SysInterfaceQueueSysType")})
	public SysInterfaceQueueSysType getTargetSys() {
		return targetSys;
	}

	public void setTargetSys(SysInterfaceQueueSysType targetSys) {
		this.targetSys = targetSys;
	}

	@Column(name = "CONTENT")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "REMARK")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    @Column(name = "ERROR_COUNT")
    public Integer getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Integer errorCount) {
        this.errorCount = errorCount;
    }

    @Column(name = "ERROR_CODE")
	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}
}
