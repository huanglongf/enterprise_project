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

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.authorization.OperationUnit;

/**
 * 渠道信息合并
 * 
 * @author sjk
 * 
 */
@Entity
@Table(name = "T_BI_CHANNEL_COMBINE_REF")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class BiChannelCombineRef extends BaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * PK
     */
    private Long id;
    /**
     * 主渠道ID
     */
    private BiChannel channelId;
    /**
     * 仓库ID
     */
    private OperationUnit whOuId;
    /**
     * 子渠道ID
     */
    private BiChannel childChannelId;
    /**
     * 过期时间
     */
    private Date expTime;
    /**
     * 创建时间
     */
    private Date createTime;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "S_T_BI_CHANNEL_COMBINE_REF", sequenceName = "S_T_BI_CHANNEL_COMBINE_REF", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_T_BI_CHANNEL_COMBINE_REF")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id")
    public BiChannel getChannelId() {
        return channelId;
    }

    public void setChannelId(BiChannel channelId) {
        this.channelId = channelId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wh_ou_id")
    public OperationUnit getWhOuId() {
        return whOuId;
    }

    public void setWhOuId(OperationUnit whOuId) {
        this.whOuId = whOuId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "child_channel_id")
    public BiChannel getChildChannelId() {
        return childChannelId;
    }

    public void setChildChannelId(BiChannel childChannelId) {
        this.childChannelId = childChannelId;
    }

    @Column(name = "exp_time")
    public Date getExpTime() {
        return expTime;
    }

    public void setExpTime(Date expTime) {
        this.expTime = expTime;
    }

    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
