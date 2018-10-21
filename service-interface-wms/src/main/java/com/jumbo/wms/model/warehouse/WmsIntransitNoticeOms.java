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

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.authorization.OperationUnit;

/**
 * oms出库通知中间表 (非直连)
 * 
 * @author xiaolong.fei
 * 
 */
@Entity
@Table(name = "T_WH_INTRANSIT_TO_OMS")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class WmsIntransitNoticeOms extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = 8254445564069030456L;
    /**
     * PK
     */
    private Long id;
    /**
     * 出库作业单号
     */
    private String staCode;
    /**
     * 作业单ID
     */
    private Long staId;
    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后修改时间
     */
    private Date lastModifyTime;
    /**
     * 仓库组织ID
     */
    private OperationUnit whOuId;

    /**
     * 渠道code
     */
    private String owner;
    /**
     * 错误次数
     */
    private Long errorCount;
    /**
     * oms反馈错误信息
     */
    private String returnMsg;
    /**
     * 是否邮件通知 0：否，1：是
     */
    private Long isSend;
    /**
     * 批次号（定时任务用） 生成规则：当前时间转String
     */
    private String batchCode;

    /**
     * 是否锁定 1：是锁定
     * 
     * @return
     */
    private Boolean isLock;

    /**
     * 状态：1或者为null：新建；10推送成功
     * 
     * @return
     */
    private Integer status = 1;

    @Column(name = "IS_LOCK")
    public Boolean getIsLock() {
        return isLock;
    }

    public void setIsLock(Boolean isLock) {
        this.isLock = isLock;
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_IN_NOTI_OMS", sequenceName = "S_T_WH_INTRANSIT_TO_OMS", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_IN_NOTI_OMS")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "STA_CODE")
    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

    @Column(name = "STA_ID")
    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WH_OU_ID")
    public OperationUnit getWhOuId() {
        return whOuId;
    }

    public void setWhOuId(OperationUnit whOuId) {
        this.whOuId = whOuId;
    }

    @Column(name = "OWNER")
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Column(name = "ERROR_COUNT")
    public Long getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Long errorCount) {
        this.errorCount = errorCount;
    }

    @Column(name = "RETURN_MSG")
    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    @Column(name = "IS_SEND")
    public Long getIsSend() {
        return isSend;
    }

    public void setIsSend(Long isSend) {
        this.isSend = isSend;
    }

    @Column(name = "BATCH_CODE")
    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    @Column(name = "STATUS")
    public Integer getStatus() {
        if (status == null) {
            return 1;
        } else {
            return status;
        }
    }

    public void setStatus(Integer status) {
        if (status == null) {
            this.status = 1;
        } else {
            this.status = status;
        }
    }
}
