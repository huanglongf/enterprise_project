package com.jumbo.wms.model.expressRadar;

// Generated 2015-5-25 15:27:48 by Hibernate Tools 3.4.0.CR1

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

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.authorization.User;


/**
 * @author gianni.zhang
 * 
 *         2015年5月25日 下午5:06:38
 */
@Entity
@Table(name = "T_OOC_RD_WARNING_REASON")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class RadarWarningReason extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1282118739294682360L;

    /**
     * id
     */
    private Long id;

    /**
     * 版本
     */
    private int version;

    /**
     * 系统预警级别
     */
    private SysRadarWarningLv sw;

    /**
     * 系统异常编码
     */
    private SysRadarErrorCode rec;

    /**
     * 原因
     */
    private String name;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date lastModifyTime;

    /**
     * 创建人
     */
    private User createUser;

    /**
     * 修改人
     */
    private User modifyUser;

    /**
     * 备注
     */
    private String remark;

    /**
     * 编码
     */
    private String code;

    /**
     * 状态
     */
    private Integer status;

    @Id
    @SequenceGenerator(name = "T_OOC_RD_WARNING_REASON_ID_GENERATOR", sequenceName = "S_T_OOC_RD_WARNING_REASON", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "T_OOC_RD_WARNING_REASON_ID_GENERATOR")
    @Column(name = "ID", unique = true, nullable = false, scale = 0)
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Version
    public int getVersion() {
        return version;
    }


    public void setVersion(int version) {
        this.version = version;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SW_ID")
    public SysRadarWarningLv getSw() {
        return this.sw;
    }

    public void setSw(SysRadarWarningLv sw) {
        this.sw = sw;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "REC_ID")
    public SysRadarErrorCode getRec() {
        return this.rec;
    }

    public void setRec(SysRadarErrorCode rec) {
        this.rec = rec;
    }

    @Column(name = "NAME", length = 100)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "LAST_MODIFY_TIME")
    public Date getLastModifyTime() {
        return this.lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATE_USER_ID")
    public User getCreateUser() {
        return this.createUser;
    }

    public void setCreateUser(User createUser) {
        this.createUser = createUser;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MODIFY_USER_ID")
    public User getModifyUser() {
        return this.modifyUser;
    }

    public void setModifyUser(User modifyUser) {
        this.modifyUser = modifyUser;
    }

    @Column(name = "REMARK", length = 400)
    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "CODE", length = 100)
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "STATUS")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
