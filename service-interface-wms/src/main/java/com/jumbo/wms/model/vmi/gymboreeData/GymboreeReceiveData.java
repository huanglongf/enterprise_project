package com.jumbo.wms.model.vmi.gymboreeData;

import java.io.Serializable;
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
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.DefaultStatus;

/**
 * 金宝贝店存入库头
 * 
 * @author jinlong.ke
 * 
 */
@Entity
@Table(name = "T_GYMBOREE_RECEIVE_DATA")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class GymboreeReceiveData implements Serializable {

    private static final long serialVersionUID = 2590288712818262277L;
    private Long id;
    private String fchrInOutVouchID;
    private String fchrCode;
    private String fchrMaker;
    private String fdtmDate;
    private String fchrEmployeeID;
    private String fchrWarehouseId;
    private DefaultStatus status;
    private Date createTime;
    private Date finishTime;
    private Long staId;
    /**
     * 新增店铺字段 Edit by KJL 2015-12-21
     */
    private String owner;
    private int version;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_GYMBOREE_RECEIVE_DATA", sequenceName = "S_T_GYMBOREE_RECEIVE_DATA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_GYMBOREE_RECEIVE_DATA")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "FCHR_WAREHOUSE_ID", length = 60)
    public String getFchrWarehouseId() {
        return fchrWarehouseId;
    }

    public void setFchrWarehouseId(String fchrWarehouseId) {
        this.fchrWarehouseId = fchrWarehouseId;
    }

    @Column(name = "STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.DefaultStatus")})
    public DefaultStatus getStatus() {
        return status;
    }

    public void setStatus(DefaultStatus status) {
        this.status = status;
    }

    @Version
    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "FINISH_TIME")
    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    @Column(name = "STA_ID")
    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

    @Column(name = "FCHR_CODE", length = 100)
    public String getFchrCode() {
        return fchrCode;
    }

    public void setFchrCode(String fchrCode) {
        this.fchrCode = fchrCode;
    }

    @Column(name = "FCHR_MARKER", length = 100)
    public String getFchrMaker() {
        return fchrMaker;
    }

    public void setFchrMaker(String fchrMaker) {
        this.fchrMaker = fchrMaker;
    }

    @Column(name = "FDTM_DATE", length = 100)
    public String getFdtmDate() {
        return fdtmDate;
    }

    public void setFdtmDate(String fdtmDate) {
        this.fdtmDate = fdtmDate;
    }

    @Column(name = "FCHR_EMPLOYEE_ID", length = 100)
    public String getFchrEmployeeID() {
        return fchrEmployeeID;
    }

    public void setFchrEmployeeID(String fchrEmployeeID) {
        this.fchrEmployeeID = fchrEmployeeID;
    }

    @Column(name = "FCHR_INOUT_VOUCH_ID", length = 60)
    public String getFchrInOutVouchID() {
        return fchrInOutVouchID;
    }

    public void setFchrInOutVouchID(String fchrInOutVouchID) {
        this.fchrInOutVouchID = fchrInOutVouchID;
    }

    @Column(name = "OWNER", length = 20)
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

}
