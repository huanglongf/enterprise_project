package com.jumbo.wms.model.mongodb;

import javax.persistence.*;

import com.jumbo.wms.model.BaseModel;
import org.hibernate.annotations.OptimisticLockType;

/**
 * @author hsh10697
 */
@Entity
@Table(name = "t_wh_sta_carton_info")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class StaCartonInfo extends BaseModel {

    public StaCartonInfo(Long staId, Integer status, Integer errorCount) {
        this.staId = staId;
        this.status = status;
        this.errorCount = errorCount;
    }

    public StaCartonInfo() {
    }

    /**
     * PK
     */
    private Long id;

    private Long staId;

    /**
     * 状态，1：未处理，2：已发送
     */
    private Integer status;

    private Integer errorCount;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_STA_CartonInfo", sequenceName = "SEQ_T_WH_STA_CARTON_INFO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_STA_CartonInfo")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "STA_ID")
    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

    @Column(name = "STATUS")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "ERROR_COUNT")
    public Integer getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Integer errorCount) {
        this.errorCount = errorCount;
    }
}
