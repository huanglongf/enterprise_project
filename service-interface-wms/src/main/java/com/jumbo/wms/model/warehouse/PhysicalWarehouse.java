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

package com.jumbo.wms.model.warehouse;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.authorization.OperationUnit;

/**
 * 物理仓
 * 
 */
@Entity
@Table(name = "T_WH_PHYSICAL_WAREHOUSE", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class PhysicalWarehouse extends BaseModel {

    private static final long serialVersionUID = -4368744558281269000L;
    /**
     * PK
     */
    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 仓库列表
     */
    private List<OperationUnit> whou = new ArrayList<OperationUnit>();

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_CARTON", sequenceName = "S_T_WH_CARTON", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CARTON")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "NAME", length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "t_wh_phy_warehouse_ref", joinColumns = @JoinColumn(name = "phy_wh_id", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "OU_wh_ID", referencedColumnName = "ID"))
    @OrderBy("id")
    public List<OperationUnit> getWhou() {
        return whou;
    }

    public void setWhou(List<OperationUnit> whou) {
        this.whou = whou;
    }

}
