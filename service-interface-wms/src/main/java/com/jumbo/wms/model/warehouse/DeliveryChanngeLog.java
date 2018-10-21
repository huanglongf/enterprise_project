package com.jumbo.wms.model.warehouse;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.vmi.Default.VmiGeneralStatus;

/**
 * 物流商变更日志
 * 
 * @author jinggnag.chen
 *
 */
@Entity
@Table(name = "T_WH_STA_DELIVERY_CHANGE_LOG")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class DeliveryChanngeLog extends BaseModel {


    private static final long serialVersionUID = -1862842298847683294L;

    /**
     * pk
     */
    private Long id;
    /**
     * 作业单id
     */
    private Long staId;

    /**
     * 原始物流面单号
     */
    private String trackingNo;

    /**
     * 变更后的面单号
     */
    private String newTrackingNo;

    /**
     * 原始物流服务商
     */
    private String lpcode;

    /**
     * 变更后的物流商
     */
    private String newLpcode;

    /**
     * 包裹重量
     */
    private BigDecimal weight;

    /**
     * 体积
     */
    private BigDecimal volume;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 状态
     */
    private VmiGeneralStatus status;

    /**
     * 错误次数
     */
    private BigDecimal errorCount;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_STA_DELIVERY", sequenceName = "S_T_STA_DELIVERY_CHANGE_LOG", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_STA_DELIVERY")
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

    @Column(name = "TRACKING_NO")
    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    @Column(name = "NEWTRACKING_NO")
    public String getNewTrackingNo() {
        return newTrackingNo;
    }

    public void setNewTrackingNo(String newTrackingNo) {
        this.newTrackingNo = newTrackingNo;
    }

    @Column(name = "LPCODE")
    public String getLpcode() {
        return lpcode;
    }

    public void setLpcode(String lpcode) {
        this.lpcode = lpcode;
    }

    @Column(name = "NEWLPCODE")
    public String getNewLpcode() {
        return newLpcode;
    }

    public void setNewLpcode(String newLpcode) {
        this.newLpcode = newLpcode;
    }

    @Column(name = "WEIGHT")
    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    @Column(name = "VOLUME")
    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    @Column(name = "CREATE_USER")
    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.vmi.Default.VmiGeneralStatus")})
    public VmiGeneralStatus getStatus() {
        return status;
    }

    public void setStatus(VmiGeneralStatus status) {
        this.status = status;
    }

    @Column(name = "ERROR_COUNT")
    public BigDecimal getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(BigDecimal errorCount) {
        this.errorCount = errorCount;
    }

}
