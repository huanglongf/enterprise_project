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
import javax.persistence.Version;

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.authorization.OperationUnit;

@Entity
@Table(name = "T_WH_LICENSE_PLATE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class LicensePlate extends BaseModel{

    /**
     * 
     */
    private static final long serialVersionUID = 836171483199479115L;

    /**
     * PK
     */
    private Long id;
    /**
     * 车辆标识
     */
    private String vehicleCode;
    /**
     * 车牌号码
     */
    private String licensePlateNumber;
    /**
     * 车辆规格
     */
    private String vehicleStandard;
    /**
     * 使用时间
     */
    private Date useTime;
    /**
     * 状态：0禁用1可用
     */
    private int status;
    /**
     * 仓库
     */
    private OperationUnit ou;
    /**
     * version
     */
    private int version;
    /**
     * 优先级
     */
    private Integer sort;


    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "S_T_WH_LICENSE_PLATE", sequenceName = "S_T_WH_LICENSE_PLATE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_T_WH_LICENSE_PLATE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "VEHICLE_CODE")
    public String getVehicleCode() {
        return vehicleCode;
    }

    public void setVehicleCode(String vehicleCode) {
        this.vehicleCode = vehicleCode;
    }

    @Column(name = "LICENSE_PLATE_NUMBER")
    public String getLicensePlateNumber() {
        return licensePlateNumber;
    }

    public void setLicensePlateNumber(String licensePlateNumber) {
        this.licensePlateNumber = licensePlateNumber;
    }

    @Column(name = "VEHICLE_STANDARD")
    public String getVehicleStandard() {
        return vehicleStandard;
    }

    public void setVehicleStandard(String vehicleStandard) {
        this.vehicleStandard = vehicleStandard;
    }

    @Column(name = "USE_TIME")
    public Date getUseTime() {
        return useTime;
    }

    public void setUseTime(Date useTime) {
        this.useTime = useTime;
    }

    @Column(name = "STATUS")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MAIN_WH_ID")
    public OperationUnit getOu() {
        return ou;
    }

    public void setOu(OperationUnit ou) {
        this.ou = ou;
    }

    @Version
    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Column(name = "SORT")
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }


}
