package com.jumbo.wms.model.automaticEquipment;

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

import org.hibernate.annotations.Index;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.warehouse.PickingList;

/**
 * 
 * @author jinlong.ke
 * 
 */
@Entity
@Table(name = "T_WH_CONTAINER")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class WhContainer extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 6873278428248371711L;
    /*
     * PK 主键
     */
    private Long id;
    /*
     * 配货批
     */
    private PickingList pickingList;
    /*
     * 二级批次
     */
    private WhPickingBatch whPickingBatch;
    /*
     * 周转箱编码
     */
    private String code;
    /*
     * 周转箱目前状态 1:可用 5:占用 2:已发送信息给WCS
     */
    private DefaultStatus status;
    /*
     * 作业单ID，入库使用
     */
    private Long staId;

    /**
     * 人工集货，此周转箱是否被接收
     */
    private Boolean isReceive;

    /**
     * 拣货批次ID
     */
    private Long diekingId;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_WH_CONTAINER", sequenceName = "S_T_WH_CONTAINER", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WH_CONTAINER")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "P_ID")
    @Index(name = "IDX_WC_P_ID")
    public PickingList getPickingList() {
        return pickingList;
    }

    public void setPickingList(PickingList pickingList) {
        this.pickingList = pickingList;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "P2_ID")
    @Index(name = "IDX_WC_P2_ID")
    public WhPickingBatch getWhPickingBatch() {
        return whPickingBatch;
    }

    public void setWhPickingBatch(WhPickingBatch whPickingBatch) {
        this.whPickingBatch = whPickingBatch;
    }

    @Column(name = "CODE", length = 20)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    @Column(name = "STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.DefaultStatus")})
    public DefaultStatus getStatus() {
        return status;
    }

    public void setStatus(DefaultStatus status) {
        this.status = status;
    }

    @Column(name = "STA_ID")
    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

    @Column(name = "IS_RECEIVE")
    public Boolean getIsReceive() {
        return isReceive;
    }

    public void setIsReceive(Boolean isReceive) {
        this.isReceive = isReceive;
    }

    @Column(name = "P3_ID")
    public Long getDiekingId() {
        return diekingId;
    }

    public void setDiekingId(Long diekingId) {
        this.diekingId = diekingId;
    }


}
