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

/**
 * 过仓成功通知PAC
 * 
 */
@Entity
@Table(name = "T_WH_CREATE_ORDER_TO_PAC_LOG")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class CreateOrderToPacLog extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 4413449609097081837L;

    /**
     * PK
     */
    private Long id;

    /**
     * 请求信息
     */
    private String context;

    /**
     * 相关单据号
     */
    private String slipCode;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 反馈信息
     */
    private String result;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 过仓中间表ID
     */
    private Long qstaId;

    /**
     * 店铺
     */
    private String owner;

    private Date logTime;


    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_COTPL", sequenceName = "S_CREATE_ORDER_TO_PAC_LOG", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_COTPL")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    @Column(name = "CONTEXT")
    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    @Column(name = "SLIP_CODE")
    public String getSlipCode() {
        return slipCode;
    }

    public void setSlipCode(String slipCode) {
        this.slipCode = slipCode;
    }

    @Column(name = "STATUS")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "RESULT")
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "QSTA_ID")
    public Long getQstaId() {
        return qstaId;
    }

    public void setQstaId(Long qstaId) {
        this.qstaId = qstaId;
    }

    @Column(name = "OWNER")
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Column(name = "LOG_TIME")
    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }



}
