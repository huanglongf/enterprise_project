package com.jumbo.wms.model.vmi.Default;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;

/**
 * 转店商品店铺比例配置
 */
@Entity
@Table(name = "T_WH_TRANSFER_OWNER_TARGET")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class TransferOwnerTarget extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 7200059907070753847L;


    /**
     * id
     */
    private Long id;


    /**
     * 来源店铺
     */
    private String sourceOwner;

    /**
     * 目标店铺
     */
    private String targetOwner;
    
    /**
     * 目标店铺分配比例
     */
    private Integer targetRatio;
    
    /**
     * 商品
     */
    private Long skuId;
    
    private Long ouId;


    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_OWNER_TARGET", sequenceName = "S_WH_TRANSFER_OWNER_TARGET", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_OWNER_TARGET")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "SOURCE_OWNER")
    public String getSourceOwner() {
        return sourceOwner;
    }

    public void setSourceOwner(String sourceOwner) {
        this.sourceOwner = sourceOwner;
    }

    @Column(name = "TARGET_OWNER")
    public String getTargetOwner() {
        return targetOwner;
    }

    public void setTargetOwner(String targetOwner) {
        this.targetOwner = targetOwner;
    }

    @Column(name = "TARGET_RATIO")
    public Integer getTargetRatio() {
        return targetRatio;
    }

    public void setTargetRatio(Integer targetRatio) {
        this.targetRatio = targetRatio;
    }

    @Column(name = "SKU_ID")
    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    @Column(name = "OU_ID")
    public Long getOuId() {
        return ouId;
    }

    public void setOuId(Long ouId) {
        this.ouId = ouId;
    }


}
