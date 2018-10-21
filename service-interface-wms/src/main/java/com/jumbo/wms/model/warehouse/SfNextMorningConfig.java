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
import javax.persistence.Transient;

import org.hibernate.annotations.Index;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.authorization.User;

@Entity
@Table(name = "T_BI_SF_NEXTMORNING")
public class SfNextMorningConfig extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 2984540434262348429L;
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
    /*
     * 维护人
     */
    private User creatorId;
    /*
     * 创建人
     */
    private String createUser;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_SF_NEXT_MORNING_CONFIG", sequenceName = "S_T_BI_SF_NEXTMORNING", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SF_NEXT_MORNING_CONFIG")
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
    @Index(name = "IDX_SF_NEXTMORNING_OU")
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    @Index(name = "IDX_SF_NEXTMORNING_USER")
    public User getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(User creatorId) {
        this.creatorId = creatorId;
    }

    @Transient
    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

}
