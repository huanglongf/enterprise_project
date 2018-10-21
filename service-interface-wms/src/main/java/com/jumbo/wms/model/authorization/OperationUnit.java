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
 * 
 */

package com.jumbo.wms.model.authorization;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;

/**
 * 组织
 * 
 * @author benjamin
 * 
 */
@Entity
@Table(name = "T_AU_OPERATION_UNIT")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class OperationUnit extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = 2141774961700250964L;

    public OperationUnit() {
        super();
    }

    public OperationUnit(Long id) {
        super();
        this.id = id;
    }

    /**
     * ID
     */
    private Long id;

    /**
     * 组织编码
     */
    private String code;

    /**
     * 组织名称
     */
    private String name;

    /**
     * 组织全称
     */
    private String fullName;

    /**
     * 组织是否可用
     */
    private Boolean isAvailable = true;

    /**
     * VERSION
     */
    private int version;

    /**
     * 组织类型
     */
    private OperationUnitType ouType;

    /**
     * 父组织
     */
    private OperationUnit parentUnit;

    /**
     * 子组织列表
     */
    private List<OperationUnit> childrenUnits = new ArrayList<OperationUnit>();

    /**
     * 备注
     */
    private String comment;

    /**
     * 最后修改时间
     */
    private Date lastModifyTime;
    
    
    private Integer isAvailableInt;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_OU", sequenceName = "S_T_AU_OPERATION_UNIT", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_OU")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "CODE", length = 50)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "NAME", length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "FULL_NAME", length = 300)
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Column(name = "IS_AVAILABLE")
    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    @Version
    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OU_TYPE_ID")
    @Index(name = "IDX_OU_OUT")
    public OperationUnitType getOuType() {
        return ouType;
    }

    public void setOuType(OperationUnitType ouType) {
        this.ouType = ouType;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_OU_ID")
    @Index(name = "IDX_OU_POU")
    public OperationUnit getParentUnit() {
        return parentUnit;
    }

    public void setParentUnit(OperationUnit parentUnit) {
        this.parentUnit = parentUnit;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parentUnit", orphanRemoval = true)
    @OrderBy("id")
    public List<OperationUnit> getChildrenUnits() {
        return childrenUnits;
    }

    public void setChildrenUnits(List<OperationUnit> childrenUnits) {
        this.childrenUnits = childrenUnits;
    }

    @Column(name = "OU_COMMENT", length = 500)
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    
    @Column(name = "LAST_MODIFY_TIME")
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }
    
    @Transient
    public Integer getIsAvailableInt() {
        return isAvailableInt;
    }

    public void setIsAvailableInt(Integer isAvailableInt) {
        this.isAvailableInt = isAvailableInt;
    }
}
