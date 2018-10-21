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
import javax.persistence.Version;

import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.warehouse.AutoPickingListRole;

/**
 * 
 * t_wh_auto_pl_config
 */
@Entity
@Table(name = "T_WH_AUTO_PL_CONFIG")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class AutoPlConfig extends BaseModel {


    private static final long serialVersionUID = -545904218800332419L;

    /**
     * PK
     */
    private Long id;

    /**
     * 下次执行时间
     */
    private Date nextExecuteTime;

    /**
     * 仓库ID FK
     */
    private OperationUnit ouId;

    /**
     * 规则ID
     */
    private AutoPickingListRole autoPlr;

    /**
     * 间隔分钟
     */
    private Integer intervalMinute;

    private Date createTime;

    private Date version;

    /**
     * 状态
     */
    private AutoPlStatus status;

    /**
     * 是否单独任务
     */
    private Boolean isSingleTask = false;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_CMPSHOP", sequenceName = "S_T_WH_AUTO_PL_CONFIG", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CMPSHOP")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "NEXT_EXECUTE_TIME")
    public Date getNextExecuteTime() {
        return nextExecuteTime;
    }

    public void setNextExecuteTime(Date nextExecuteTime) {
        this.nextExecuteTime = nextExecuteTime;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WH_OU_ID")
    public OperationUnit getOuId() {
        return ouId;
    }

    public void setOuId(OperationUnit ouId) {
        this.ouId = ouId;
    }

    @Column(name = "INTERVAL_MINUTE")
    public Integer getIntervalMinute() {
        return intervalMinute;
    }

    public void setIntervalMinute(Integer intervalMinute) {
        this.intervalMinute = intervalMinute;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Version
    @Column(name = "VERSION")
    public Date getVersion() {
        return version;
    }

    public void setVersion(Date version) {
        this.version = version;
    }

    @Column(name = "STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.AutoPlStatus")})
    public AutoPlStatus getStatus() {
        return status;
    }

    public void setStatus(AutoPlStatus status) {
        this.status = status;
    }

    @Column(name = "IS_SINGLE_TASK")
    public Boolean getIsSingleTask() {
        return isSingleTask;
    }

    public void setIsSingleTask(Boolean isSingleTask) {
        this.isSingleTask = isSingleTask;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROLE_ID")
    public AutoPickingListRole getAutoPlr() {
        return autoPlr;
    }

    public void setAutoPlr(AutoPickingListRole autoPlr) {
        this.autoPlr = autoPlr;
    }

}
