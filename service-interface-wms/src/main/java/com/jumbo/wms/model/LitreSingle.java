package com.jumbo.wms.model;

import java.math.BigDecimal;
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

import org.hibernate.annotations.Index;
import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.authorization.OperationUnit;

/**
 * 物流时效升级
 * 
 * @author cheng.su
 * 
 */
@Entity
@Table(name = "t_sys_litre_single")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class LitreSingle extends BaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = 4442402272688219858L;
    /**
     * PK
     */
    private Long id;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 相关仓库
     */
    private OperationUnit mainWarehouse;
    /**
     * 相关店铺
     */
    private String owner;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 区
     */
    private String district;
    /**
     * 第一天下午的开始时间
     */
    private String startTime;
    /**
     * 第二天早上结束时间
     */
    private String endTime;
    /**
     * 升级金额
     */
    private BigDecimal totalActual;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_litre_single", sequenceName = "S_t_sys_litre_single", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_litre_single")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OU_ID")
    @Index(name = "IDX_STA_MAIN_WH")
    public OperationUnit getMainWarehouse() {
        return mainWarehouse;
    }

    public void setMainWarehouse(OperationUnit mainWarehouse) {
        this.mainWarehouse = mainWarehouse;
    }

    @Column(name = "owner")
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Column(name = "province")
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Column(name = "city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "district")
    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @Column(name = "start_time")
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @Column(name = "end_time")
    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Column(name = "totalActual")
    public BigDecimal getTotalActual() {
        return totalActual;
    }

    public void setTotalActual(BigDecimal totalActual) {
        this.totalActual = totalActual;
    }

}
