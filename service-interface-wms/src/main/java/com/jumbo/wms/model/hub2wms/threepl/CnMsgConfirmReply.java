package com.jumbo.wms.model.hub2wms.threepl;

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
 * 出库通知表
 * 
 * @author hui.li
 * 
 */
@Entity
@Table(name = "T_WH_CN_MSG_CONFIRM_REPLY")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class CnMsgConfirmReply extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 5669661493778569128L;

    private Long id;// 主键

    /**
     * 作业单ID
     */
    private Long staId;

    /**
     * 作业的编码
     */
    private String staCode;

    /**
     * 错误次数
     */
    private Integer errorCount;

    /**
     * 错误信息
     */
    private String errorMsg;

    /**
     * 状态（0：未执行；1：已执行；99：执行错误）
     */
    private Integer status;


    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_CN_MSG_CR", sequenceName = "S_T_WH_CN_MSG_CONFIRM_REPLY", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CN_MSG_CR")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "STA_ID")
    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

    @Column(name = "STATUS")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "STA_CODE")
    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

    @Column(name = "ERROR_COUNT")
    public Integer getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Integer errorCount) {
        this.errorCount = errorCount;
    }

    @Column(name = "ERROR_MSG")
    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }



}
