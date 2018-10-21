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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;

/**
 * 角色
 * 
 * @author sjk
 * 
 */
@Entity
@Table(name = "T_AU_ROLE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class Role extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = 147744869422909210L;

    /**
     * ID
     */
    private Long id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色描述
     */
    private String description;

    /**
     * 是否系统角色，系统角色只能初始化，不能通过界面功能维护和删除
     */
    private Boolean isSystem = false;

    /**
     * 角色状态
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
     * 角色所含权限列表
     */
    private List<Privilege> privileges = new ArrayList<Privilege>();

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_ROL", sequenceName = "S_T_AU_ROLE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ROL")
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

    @Column(name = "DESCRIPTION", length = 300)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "IS_SYSTEM")
    public Boolean getIsSystem() {
        return isSystem;
    }

    public void setIsSystem(Boolean isSystem) {
        this.isSystem = isSystem;
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
    @Index(name = "IDX_ROL_OUT")
    public OperationUnitType getOuType() {
        return ouType;
    }

    public void setOuType(OperationUnitType ouType) {
        this.ouType = ouType;
    }

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "T_AU_ROLE_PRIVILEGE", joinColumns = @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "PRIVILEGE_ACL", referencedColumnName = "ACL"))
    @OrderBy("sortNo")
    public List<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<Privilege> privileges) {
        this.privileges = privileges;
    }
}
