package com.jumbo.wms.model;

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

import com.jumbo.wms.model.authorization.OperationUnit;

@Entity
@Table(name = "T_AD_PACKAGE")
public class AdPackage extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = -1658978385809938814L;
    /**
     * PK
     */
    private Long id;
    /**
     * 工单类型
     */
    private String adName;
    /**
     * 工单结果
     */
    private String adResult;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 相关仓库
     */
    private OperationUnit mainWarehouse;
    /**
     * 创建人
     */
    private String createPerson;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "S_T_AD_PACKAGE", sequenceName = "S_T_AD_PACKAGE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_T_AD_PACKAGE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "AD_NAME")
    public String getAdName() {
        return adName;
    }

    public void setAdName(String adName) {
        this.adName = adName;
    }

    @Column(name = "AD_RESULT")
    public String getAdResult() {
        return adResult;
    }

    public void setAdResult(String adResult) {
        this.adResult = adResult;
    }

    @Column(name = "CREATETIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WH_OU_ID")
    public OperationUnit getMainWarehouse() {
        return mainWarehouse;
    }

    public void setMainWarehouse(OperationUnit mainWarehouse) {
        this.mainWarehouse = mainWarehouse;
    }

    @Column(name = "CREATEPERSON")
    public String getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }



}
