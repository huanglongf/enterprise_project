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

/**
 * 渠道残次品明细配置
 * 
 * @author cheng.su
 * 
 */
@Entity
@Table(name = "t_bi_channel_imperfect_line")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class BiChannelImperfectLine extends BaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = -1018311965707490922L;
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
    private BiChannelImperfect imperfectId;

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
    @JoinColumn(name = "imperfect_Id")
    public BiChannelImperfect getImperfectId() {
        return imperfectId;
    }

    public void setImperfectId(BiChannelImperfect imperfectId) {
        this.imperfectId = imperfectId;
    }


}
