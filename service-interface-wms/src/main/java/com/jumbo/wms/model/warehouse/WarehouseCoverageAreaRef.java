/**
 * Copyright (c) 2010 Jumbomart All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Jumbomart. You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jumbo.
 * 
 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 */
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
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.authorization.OperationUnit;

/**
 * @author lichuan
 * 
 */
@Entity
@Table(name = "T_WH_COVERAGE_AREA_REF", uniqueConstraints = {@UniqueConstraint(columnNames = {"OU_ID"})})
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class WarehouseCoverageAreaRef extends BaseModel {

    private static final long serialVersionUID = 9203715709641281793L;
    /**
     * PK
     */
    private Long id;
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
     * 创建人
     */
    private Long creatorId;

    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 相关仓库组织
     */
    private OperationUnit ou;
    /**
     * 优先级
     */
    private int priority;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_WH_COVERAGE_AREA_REF", sequenceName = "S_T_WH_COVERAGE_AREA_REF", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_WH_COVERAGE_AREA_REF")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "province", length = 50)
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Column(name = "city", length = 50)
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

    @Column(name = "creator_id")
    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
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
    public OperationUnit getOu() {
        return ou;
    }

    public void setOu(OperationUnit ou) {
        this.ou = ou;
    }

    @Column(name = "PRIORITY")
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }


}
