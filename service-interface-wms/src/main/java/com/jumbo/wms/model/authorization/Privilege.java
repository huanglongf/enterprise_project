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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

import com.jumbo.wms.model.BaseModel;

/**
 * 权限
 * 
 * @author benjamin
 * 
 */
@Entity
@Table(name = "T_AU_PRIVILEGE")
public class Privilege extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = -124934458949154397L;

    /**
     * ID
     */
    private String acl;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 组织类型
     */
    private OperationUnitType ouType;

    /**
     * 权限序号
     */
    private int sortNo;

    /**
     * 系统名称
     */
    private String systemName;

    /**
     * 所属角色列表
     */
    private List<Role> roles = new ArrayList<Role>();

    @Id
    @Column(name = "ACL", length = 255)
    public String getAcl() {
        return acl;
    }

    public void setAcl(String acl) {
        this.acl = acl;
    }

    @Column(name = "NAME", length = 300)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OU_TYPE_ID")
    @Index(name = "IDX_PRI_OUT")
    public OperationUnitType getOuType() {
        return ouType;
    }

    public void setOuType(OperationUnitType ouType) {
        this.ouType = ouType;
    }

    @Column(name = "SORT_NO")
    public int getSortNo() {
        return sortNo;
    }

    public void setSortNo(int sortNo) {
        this.sortNo = sortNo;
    }

    @Column(name = "SYSTEM_NAME", length = 20)
    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "privileges")
    @OrderBy(value = "id")
    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
