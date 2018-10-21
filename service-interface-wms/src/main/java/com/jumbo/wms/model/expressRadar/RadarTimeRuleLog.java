package com.jumbo.wms.model.expressRadar;

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

import com.jumbo.wms.model.BaseModel;


/**
 * @author lihui
 * 
 *         2015年5月25日
 */
@Entity
@Table(name = "T_OOC_RD_TIME_RULE_LOG")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class RadarTimeRuleLog extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = 4198095609930990428L;
    private Long id;
    private Date createTime;
    private Long createUserId;
    private Date lastModifyTime;
    private Long lastModifyUserId;
    private String overTakingTime;
    private Long pwhId;
    private Long sraId;
    private Integer standardDate;
    private Long tsId;
    private int version;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_OOC_RD_TIME_RULE_LOG", sequenceName = "S_T_OOC_RD_TIME_RULE_LOG", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_OOC_RD_TIME_RULE_LOG")
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    @Column(name = "CREATE_USER_ID")
    public Long getCreateUserId() {
        return this.createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }


    @Column(name = "LAST_MODIFY_TIME")
    public Date getLastModifyTime() {
        return this.lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }


    @Column(name = "LAST_MODIFY_USER_ID")
    public Long getLastModifyUserId() {
        return this.lastModifyUserId;
    }

    public void setLastModifyUserId(Long lastModifyUserId) {
        this.lastModifyUserId = lastModifyUserId;
    }


    @Column(name = "OVER_TAKING_TIME")
    public String getOverTakingTime() {
        return this.overTakingTime;
    }

    public void setOverTakingTime(String overTakingTime) {
        this.overTakingTime = overTakingTime;
    }


    @Column(name = "PWH_ID")
    public Long getPwhId() {
        return this.pwhId;
    }

    public void setPwhId(Long pwhId) {
        this.pwhId = pwhId;
    }


    @Column(name = "SRA_ID")
    public Long getSraId() {
        return this.sraId;
    }

    public void setSraId(Long sraId) {
        this.sraId = sraId;
    }


    @Column(name = "STANDARD_DATE")
    public Integer getStandardDate() {
        return this.standardDate;
    }

    public void setStandardDate(Integer standardDate) {
        this.standardDate = standardDate;
    }


    @Column(name = "TS_ID")
    public Long getTsId() {
        return this.tsId;
    }

    public void setTsId(Long tsId) {
        this.tsId = tsId;
    }

    @Version
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }


}
