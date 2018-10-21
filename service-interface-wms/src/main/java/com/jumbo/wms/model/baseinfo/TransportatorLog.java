package com.jumbo.wms.model.baseinfo;

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
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.DefaultLifeCycleStatus;
import com.jumbo.wms.model.authorization.User;

/**
 * 
 * 物流商
 * 
 * @author sjk
 * 
 */
@Entity
@Table(name = "T_MA_TRANSPORTATOR_LOG")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class TransportatorLog extends BaseModel {
    /**
	 * 
	 */
    private static final long serialVersionUID = -7812676025062855078L;

    /**
     * PK
     */
    private Long id;

    /**
     * 生命周期
     */
    private DefaultLifeCycleStatus lifeCycleStatus;

    /**
     * 创建时间
     */
    private Date createTime;


    /**
     * 物流商编码
     */
    private String code;

    /**
     * 物流商名称
     */
    private String name;

    /**
     * 物流商全称
     */
    private String fullName;


    /**
     * 内部平台对接编码，全局唯一
     */
    private String expCode;

    /**
     * 外部平台对接编码
     */
    private String platformCode;

    /**
     * 是否支持COD
     */
    private Boolean isSupportCod;
    /**
     * 最后修改时间
     */
    private Date lastModifyTime;

    /**
     * 电子面单模板
     */
    private String jasperOnLine;
    /**
     * 普通电子面单
     */
    private String jasperNormal;

    /**
     * 是否后置
     */
    private Boolean isTransAfter;

    /**
     * 是否分区域
     */
    private Boolean isRegion;

    /**
     * 最后修改人
     */
    private User userId;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_TRANSPORTATOR_LOG", sequenceName = "S_TRANSPORTATOR_LOG", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TRANSPORTATOR_LOG")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "LIFE_CYCLE_STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.DefaultLifeCycleStatus")})
    public DefaultLifeCycleStatus getLifeCycleStatus() {
        return lifeCycleStatus;
    }

    public void setLifeCycleStatus(DefaultLifeCycleStatus lifeCycleStatus) {
        this.lifeCycleStatus = lifeCycleStatus;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "IS_SUPPORT_COD")
    public Boolean getIsSupportCod() {
        return isSupportCod;
    }

    public void setIsSupportCod(Boolean isSupportCod) {
        this.isSupportCod = isSupportCod;
    }

    @Column(name = "CODE", length = 50)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "NAME", length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "FULL_NAME", length = 255)
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


    @Column(name = "EXP_CODE", length = 20)
    public String getExpCode() {
        return expCode;
    }

    public void setExpCode(String expCode) {
        this.expCode = expCode;
    }

    @Column(name = "PLATFORM_CODE", length = 20)
    public String getPlatformCode() {
        return platformCode;
    }

    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }

    @Column(name = "LAST_MODIFY_TIME")
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    @Column(name = "JASPER_ONLINE", length = 200)
    public String getJasperOnLine() {
        return jasperOnLine;
    }

    public void setJasperOnLine(String jasperOnLine) {
        this.jasperOnLine = jasperOnLine;
    }

    @Column(name = "JASPER_NORMAL", length = 200)
    public String getJasperNormal() {
        return jasperNormal;
    }

    public void setJasperNormal(String jasperNormal) {
        this.jasperNormal = jasperNormal;
    }

    @Column(name = "IS_TRANS_AFTER")
    public Boolean getIsTransAfter() {
        return isTransAfter;
    }

    public void setIsTransAfter(Boolean isTransAfter) {
        this.isTransAfter = isTransAfter;
    }

    @Column(name = "IS_REGION")
    public Boolean getIsRegion() {
        return isRegion;
    }

    public void setIsRegion(Boolean isRegion) {
        this.isRegion = isRegion;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

}
