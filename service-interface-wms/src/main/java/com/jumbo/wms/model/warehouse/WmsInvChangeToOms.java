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
 * WMS库存变更通知PAC
 * 
 * @author jinlong.ke
 * 
 * 
 */
@Entity
@Table(name = "T_WH_INVCHANGE_TO_OMS")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class WmsInvChangeToOms extends BaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = -7160369296350581664L;
    /**
     * 该实体用来记录所有的由WMS发起的出入库变更状态(出库的库存占用/取消,出入库的完成) 按照时间排序 执行定时任务如果为占用或者取消，则执行
     */
    /*
     * PK
     */
    private Long id;
    /*
     * 作业单号/盘点/VMI库存调整单号
     */
    private String staCode;
    /*
     * stvCode
     */
    private String stvCode;
    /*
     * 作业单ID/盘点/VMI库存调整单ID
     */
    private Long staId;
    /*
     * STVID
     */
    private Long stvId;
    /*
     * 执行错误次数
     */
    private Integer errorCount;
    /*
     * 通知数据状态2：配货中，10：出入库完成（销售出已转出），17：取消
     */
    private StockTransApplicationStatus dataStatus;
    /*
     * 数据执行状态1：新建 2：执行失败邮件已发送
     */
    private DefaultStatus exeStatus;
    /*
     * 单据类型1：作业单 2：盘点单
     */
    private Integer orderType;
    /*
     * 执行反馈信息
     */
    private String errorMsg;
    /*
     * 创建时间
     */
    private Date createTime;
    /*
     * 最后更新时间
     */
    private Date lastModifyTime;
    /*
     * 版本号
     */
    private int version;
    /*
     * 执行优先级 1：销售/退换货 2：采购入 3：其他单据类型
     */
    private int priority;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_WH_INVCHANGE_TO_OMS", sequenceName = "S_T_WH_INVCHANGE_TO_OMS", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WH_INVCHANGE_TO_OMS")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "STA_CODE", length = 100)
    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

    @Column(name = "STV_CODE", length = 100)
    public String getStvCode() {
        return stvCode;
    }

    public void setStvCode(String stvCode) {
        this.stvCode = stvCode;
    }

    @Column(name = "STA_ID")
    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

    @Column(name = "STV_ID")
    public Long getStvId() {
        return stvId;
    }

    public void setStvId(Long stvId) {
        this.stvId = stvId;
    }

    @Column(name = "ERROR_COUNT")
    public Integer getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Integer errorCount) {
        this.errorCount = errorCount;
    }

    @Column(name = "DATA_STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.StockTransApplicationStatus")})
    public StockTransApplicationStatus getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(StockTransApplicationStatus dataStatus) {
        this.dataStatus = dataStatus;
    }

    @Column(name = "EXE_STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.DefaultStatus")})
    public DefaultStatus getExeStatus() {
        return exeStatus;
    }

    public void setExeStatus(DefaultStatus exeStatus) {
        this.exeStatus = exeStatus;
    }

    @Column(name = "ORDER_TYPE")
    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    @Column(name = "ERROR_MSG", length = 1000)
    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
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

    @Version
    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Column(name = "PRIORITY")
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

}
