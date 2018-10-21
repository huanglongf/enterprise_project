package com.jumbo.wms.model.warehouse;

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
import com.jumbo.wms.model.baseinfo.BiChannel;

/**
 * 渠道残次品配置
 * 
 * @author cheng.su
 * 
 */
@Entity
@Table(name = "t_bi_channel_imperfect")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class BiChannelImperfect extends BaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = 8001264283516930249L;
    /**
     * PK
     */
    private Long id;
    /**
     * 原因编号
     */
    private String code;
    /**
     * 原因名称
     */
    private String name;
    /**
     * 渠道ID
     */
    private BiChannel channelId;
    /**
     * 是否锁定
     */
    private Boolean isLocked;
    private OperationUnit ouId;
    private Long parentOuId;

    @Column(name = "is_Locked")
    public Boolean getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(Boolean isLocked) {
        this.isLocked = isLocked;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ou_id")
    public OperationUnit getOuId() {
        return ouId;
    }

    public void setOuId(OperationUnit ouId) {
        this.ouId = ouId;
    }

    @Column(name = "Parent_Ou_Id")
    public Long getParentOuId() {
        return parentOuId;
    }

    public void setParentOuId(Long parentOuId) {
        this.parentOuId = parentOuId;
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_channel_imperfect", sequenceName = "s_t_bi_channel_imperfect", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_channel_imperfect")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id")
    public BiChannel getChannelId() {
        return channelId;
    }

    public void setChannelId(BiChannel channelId) {
        this.channelId = channelId;
    }



}
