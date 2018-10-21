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
import com.jumbo.wms.model.trans.TransportService;
import com.jumbo.wms.model.warehouse.PhysicalWarehouse;


/**
 * @author gianni.zhang
 * 
 *         2015年5月25日 下午5:06:21
 */
@Entity
@Table(name = "T_OOC_RD_TIME_RULE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class RadarTimeRule extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = -1564195652854119536L;

    /**
     * id
     */
    private Long id;

    /**
     * 版本
     */
    private Integer version;

    /**
     * 区域
     */
    private SysRadarArea sra;

    /**
     * 物理仓ID
     */
    private PhysicalWarehouse pwh;

    /**
     * 标准时效
     */
    private Integer standardDate;

    /**
     * 截至揽件时间
     */
    private String overTakingTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后时间
     */
    private Date lastModifyTime;

    /**
     * 创建人
     */
    private User createUser;

    /**
     * 最后修改人
     */
    private User lastModifyUser;

    /**
     * 物流商服务
     */
    private TransportService ts;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_OOC_RD_TIME_RULE", sequenceName = "S_T_OOC_RD_TIME_RULE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_OOC_RD_TIME_RULE")
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SRA_ID")
    public SysRadarArea getSra() {
        return this.sra;
    }

    public void setSra(SysRadarArea sra) {
        this.sra = sra;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PWH_ID")
    public PhysicalWarehouse getPwh() {
        return this.pwh;
    }

    public void setPwh(PhysicalWarehouse pwh) {
        this.pwh = pwh;
    }

    @Column(name = "STANDARD_DATE")
    public Integer getStandardDate() {
        return this.standardDate;
    }

    public void setStandardDate(Integer standardDate) {
        this.standardDate = standardDate;
    }

    @Column(name = "OVER_TAKING_TIME")
    public String getOverTakingTime() {
        return this.overTakingTime;
    }

    public void setOverTakingTime(String overTakingTime) {
        this.overTakingTime = overTakingTime;
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
    @JoinColumn(name = "LAST_MODIFY_USER_ID")
    public User getLastModifyUser() {
        return this.lastModifyUser;
    }

    public void setLastModifyUser(User lastModifyUser) {
        this.lastModifyUser = lastModifyUser;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TS_ID")
    public TransportService getTs() {
        return this.ts;
    }

    public void setTs(TransportService ts) {
        this.ts = ts;
    }

    @Version
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

}
