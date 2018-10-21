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

import org.hibernate.annotations.Index;

import com.jumbo.wms.model.BaseModel;

/**
 * 组织类型
 * 
 * @author benjamin
 * 
 */
@Entity
@Table(name = "T_AU_OPERATION_UNIT_TYPE")
public class OperationUnitType extends BaseModel {

    /**
     * 集团类型
     */
    public static final String OUTYPE_ROOT = "Root";

    /**
     * 公司类型
     */
    public static final String OUTYPE_COMPANY = "Company";

    /**
     * 运营中心类型
     */
    public static final String OUTYPE_OPERATION_CENTER = "OperationCenter";

    /**
     * 仓库类型
     */
    public static final String OUTYPE_WAREHOUSE = "Warehouse";

    /**
     * 销售事业部
     */
    public static final String OUTYPE_DIVISION = "Division";


    /**
     * 店铺
     */
    public static final String OUTYPE_COMPANY_SHOP = "CompanyShop";
    /**
	 * 
	 */
    private static final long serialVersionUID = 6613686886535649399L;

    /**
     * ID
     */
    private Long id;

    /**
     * 组织类型简称/编码
     */
    private String name;

    /**
     * 组织类型全称
     */
    private String displayName;

    /**
     * 是否可用
     */
    private Boolean isAvailable = true;

    /**
     * 组织类型描述
     */
    private String description;

    /**
     * 当前组织类型下已有组织列表
     */
    private List<OperationUnit> ous = new ArrayList<OperationUnit>();
    private OperationUnitType parentUnitType;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_OUT", sequenceName = "S_T_AU_OPERATION_UNIT_TYPE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_OUT")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "NAME", length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "DISPLAY_NAME", length = 300)
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Column(name = "IS_AVAILABLE")
    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    @Column(name = "DESCRIPTION", length = 300)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "ouType", orphanRemoval = true)
    @OrderBy("id")
    public List<OperationUnit> getOus() {
        return ous;
    }

    public void setOus(List<OperationUnit> ous) {
        this.ous = ous;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_OUT_ID")
    @Index(name = "IDX_OUT_POUT")
    public OperationUnitType getParentUnitType() {
        return parentUnitType;
    }

    public void setParentUnitType(OperationUnitType parentUnitType) {
        this.parentUnitType = parentUnitType;
    }

}
