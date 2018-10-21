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
 * 库存优先店铺配置
 */
@Entity
@Table(name = "T_WH_TRANSFER_OWNER_SOURCE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class TransferOwnerSource extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 4542274183873363864L;


    /**
     * id
     */
    private Long id;


    /**
     * 来源店铺
     */
    private String ownerSource;

    /**
     * 优先店铺
     */
    private String priorityOwner;

    /**
     * 目标店铺
     */
    private String targetOwner;

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

    @Column(name = "OWNER_SOURCE")
    public String getOwnerSource() {
        return ownerSource;
    }

    public void setOwnerSource(String ownerSource) {
        this.ownerSource = ownerSource;
    }

    @Column(name = "PRIORITY_OWNER")
    public String getPriorityOwner() {
        return priorityOwner;
    }

    public void setPriorityOwner(String priorityOwner) {
        this.priorityOwner = priorityOwner;
    }

    @Column(name = "TARGET_OWNER")
    public String getTargetOwner() {
        return targetOwner;
    }

    public void setTargetOwner(String targetOwner) {
        this.targetOwner = targetOwner;
    }

    @Column(name = "OU_ID")
    public Long getOuId() {
        return ouId;
    }

    public void setOuId(Long ouId) {
        this.ouId = ouId;
    }

}
