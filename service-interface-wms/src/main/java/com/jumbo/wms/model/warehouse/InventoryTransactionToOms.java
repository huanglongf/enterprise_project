package com.jumbo.wms.model.warehouse;

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
import com.jumbo.wms.model.DefaultStatus;

/**
 * WMS出入库事务通知PACS
 * 
 * @author jinlong.ke
 * 
 */
@Entity
@Table(name = "T_WH_INVTS_TO_OMS")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class InventoryTransactionToOms extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 179700126660454346L;
    /*
     * PK
     */
    private Long id;
    /*
     * 作业单号
     */
    private String staCode;
    /*
     * 执行批次(stvCode)
     */
    private String stvCode;
    /*
     * 冗余字段 stvID
     */
    private Long stvId;
    /*
     * 创建时间
     */
    private Date createTime;
    /*
     * 数据版本号
     */
    private int version;
    /*
     * 最后更新时间
     */
    private Date lastModifyTime;
    /*
     * 执行批次(考虑多线程使用)
     */
    private String batchCode;
    /*
     * 执行状态
     */
    private DefaultStatus status;
    /*
     * 是否发送邮件通知
     */
    private Boolean isSend;
    /*
     * 记录接口反馈信息
     */
    private String memo;
    /*
     * 错误次数
     */
    private Long errorCount;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_WH_INVTS_TO_OMS", sequenceName = "S_T_WH_INVTS_TO_OMS", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WH_INVTS_TO_OMS")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "STA_CODE", length = 30)
    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

    @Column(name = "STV_CODE", length = 30)
    public String getStvCode() {
        return stvCode;
    }

    public void setStvCode(String stvCode) {
        this.stvCode = stvCode;
    }

    @Column(name = "STV_ID")
    public Long getStvId() {
        return stvId;
    }

    public void setStvId(Long stvId) {
        this.stvId = stvId;
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
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Column(name = "LAST_MODIFY_TIME")
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    @Column(name = "BATCH_CODE")
    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    @Column(name = "STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.DefaultStatus")})
    public DefaultStatus getStatus() {
        return status;
    }

    public void setStatus(DefaultStatus status) {
        this.status = status;
    }

    @Column(name = "IS_SEND")
    public Boolean getIsSend() {
        return isSend;
    }

    public void setIsSend(Boolean isSend) {
        this.isSend = isSend;
    }

    @Column(name = "MEMO", length = 1000)
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Column(name = "ERROR_COUNT")
    public Long getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Long errorCount) {
        this.errorCount = errorCount;
    }



}
