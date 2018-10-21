package com.jumbo.wms.model.warehouse;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OptimisticLockType;

import com.baozun.bizhub.model.BaseModel;

@Entity
@Table(name = "t_wh_inv_change_pacs")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class InvetoryChange extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 4142275339106442577L;

    private Long id;

    private Long staId;

    private Long stvId;

    private Long status;

    private Long errorCount;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "seq_t_wh_inv_change_pacs", sequenceName = "seq_t_wh_inv_change_pacs", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_t_wh_inv_change_pacs")
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

    @Column(name = "STV_ID")
    public Long getStvId() {
        return stvId;
    }

    public void setStvId(Long stvId) {
        this.stvId = stvId;
    }

    @Column(name = "STATUS")
    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    @Column(name = "ERROR_COUNT")
    public Long getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Long errorCount) {
        this.errorCount = errorCount;
    }

}
