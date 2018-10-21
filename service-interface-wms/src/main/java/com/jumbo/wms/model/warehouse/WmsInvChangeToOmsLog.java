package com.jumbo.wms.model.warehouse;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;

/**
 * WMS库存变更通知PAC成功日志
 * 
 * @author jinlong.ke
 * 
 */
@Entity
@Table(name = "T_LG_INVCHANGE_TO_OMS")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class WmsInvChangeToOmsLog extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 9133423569722523066L;
    /*
     * PK
     */
    private Long id;
    /*
     * staCode
     */
    private String staCode;
    private Long staId;
    private String stvCode;
    private Long stvId;
    private StockTransApplicationStatus dataStatus;
    private Date logTime;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_LG_INVCHANGE_TO_OMS", sequenceName = "S_T_LG_INVCHANGE_TO_OMS", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_LG_INVCHANGE_TO_OMS")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "STA_CODE", length = 100)
    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

    @Column(name = "STA_ID")
    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

    @Column(name = "STV_CODE", length = 100)
    public String getStvCode() {
        return stvCode;
    }

    public void setStvCode(String stvCode) {
        this.stvCode = stvCode;
    }

    @Column(name = "STV_ID")
    public Long getStvId() {
        return stvId;
    }

    public void setStvId(Long stvId) {
        this.stvId = stvId;
    }

    @Column(name = "DATA_STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.StockTransApplicationStatus")})
    public StockTransApplicationStatus getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(StockTransApplicationStatus dataStatus) {
        this.dataStatus = dataStatus;
    }

    @Column(name = "LOG_TIME")
    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }


}
