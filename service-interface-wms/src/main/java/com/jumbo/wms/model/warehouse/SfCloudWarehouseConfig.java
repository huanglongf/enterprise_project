package com.jumbo.wms.model.warehouse;

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

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.baseinfo.BiChannel;

/**
 * SF云仓可配地址维护
 * 
 * @author jinlong.ke
 * @date 2016年10月24日下午8:54:36
 * 
 */
@Entity
@Table(name = "T_BI_SF_CLOUD_WAREHOUSE")
public class SfCloudWarehouseConfig extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 4359927799353320016L;

    /*
     * PK
     */
    private Long id;
    /*
     * 省
     */
    private String province;
    /*
     * 市
     */
    private String city;
    /*
     * 区
     */
    private String district;
    /*
     * 仓库组织节点ID
     */
    private OperationUnit ou;
    /*
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     * 
     * @return
     */
    private Date updateTime;
    /**
     * 时效类型
     * 
     * @return
     */
    private Integer timeType;

    /**
     * 修改人
     * 
     * @return
     */
    private User user;

    /**
     * 店铺
     * 
     * @return
     */
    private BiChannel biChannel;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    @Index(name = "IDX_SF_USER_ID")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHANNEL_ID")
    @Index(name = "IDX_SF_CHANNEL_ID")
    public BiChannel getBiChannel() {
        return biChannel;
    }

    public void setBiChannel(BiChannel biChannel) {
        this.biChannel = biChannel;
    }

    @Column(name = "UPDATE_TIME")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Column(name = "TIME_TYPE")
    public Integer getTimeType() {
        return timeType;
    }

    public void setTimeType(Integer timeType) {
        this.timeType = timeType;
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_BI_SF_CLOUD_WAREHOUSE", sequenceName = "S_BI_SF_CLOUD_WAREHOUSE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BI_SF_CLOUD_WAREHOUSE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "PROVINCE", length = 100)
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Column(name = "CITY", length = 100)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "DISTRICT", length = 100)
    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OU_ID")
    @Index(name = "IDX_SF_CW_OU")
    public OperationUnit getOu() {
        return ou;
    }

    public void setOu(OperationUnit ou) {
        this.ou = ou;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
