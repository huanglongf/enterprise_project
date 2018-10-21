package com.jumbo.wms.model.vmi.warehouse;

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
 * 外包仓退换货取消通知表
 * 
 * @author sjk
 * 
 */
@Entity
@Table(name = "T_WH_MSG_RA_CANCEL")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class MsgRaCancel extends BaseModel {
    private static final long serialVersionUID = -59901282210662632L;
    /**
     * PK
     */
    private Long id;
    /**
     * VERSION
     */
    private int version = 0;
    /**
     * 创建时间
     */
    private Date createTime = new Date();
    /**
     * 发送时间
     */
    private Date updateTime;
    /**
     * 状态
     */
    private DefaultStatus status;
    /**
     * 作业单编码
     */
    private String staCode;
    /**
     * 相关单号
     */
    private String slipCode;
    /**
     * 备注
     */
    private String remark;
    /**
     * 仓库目标(对应WAREHOUSE.SOURCE)
     */
    private String source;
    /**
     * 仓库目标(对应WAREHOUSE.SOURCEWH)
     */
    private String sourceWh;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_MSG_RAC", sequenceName = "S_T_WH_MSG_RA_CANCEL", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MSG_RAC")
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

    @Column(name = "UPDATE_TIME")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Column(name = "STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.DefaultStatus")})
    public DefaultStatus getStatus() {
        return status;
    }

    public void setStatus(DefaultStatus status) {
        this.status = status;
    }

    @Column(name = "STA_CODE", length = 50)
    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

    public String getSlipCode() {
        return slipCode;
    }

    @Column(name = "SLIP_CODE", length = 50)
    public void setSlipCode(String slipCode) {
        this.slipCode = slipCode;
    }

    @Column(name = "REMARK", length = 200)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "SOURCE", length = 30)
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Column(name = "SOURCE_WH", length = 30)
    public String getSourceWh() {
        return sourceWh;
    }

    public void setSourceWh(String sourceWh) {
        this.sourceWh = sourceWh;
    }

}
