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

package com.jumbo.wms.model.baseinfo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Index;

import com.jumbo.wms.model.BaseModel;

/**
 * 菜单项，用于构造系统功能菜单
 * 
 * @author benjamin
 * 
 */
@Entity
@Table(name = "T_BI_MENU_ITEM")
public class MenuItem extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = 310981964041290689L;

    /**
     * ID, 格式为 M-[父menuId]-当前编码，如将 系统功能 设定为 M-SYSTEM，则 系统功能-->选择角色 可以设定为 M-SYSTEM-SELECTROLE
     */
    private String menuId;

    /**
     * 菜单显示名称
     */
    private String name;

    /**
     * 序号，仅对同级菜单排序用
     */
    private Integer sortNo;
    /**
     * 是否有子菜单[菜单组]
     */
    private Boolean isGroup = false;

    /**
     * 是否是分隔符
     */
    private Boolean isSeperator = false;

    /**
     * 对应ACL，如为空说明此功能无权限限制，任意用户都可以访问，对于菜单组/分隔符忽略此数据
     */
    private String acl;

    /**
     * 入口URL，对于菜单组/分隔符忽略此数据
     */
    private String entryURL;

    /**
     * 父菜单项，如果为空说明是顶层菜单
     */
    private MenuItem parent;

    /**
     * 系统模块编号:wms,oms等
     */
    private String systemCode;

    /**
     * 子菜单列表
     */
    private List<MenuItem> children = new ArrayList<MenuItem>();

    @Id
    @Column(name = "MENU_ID", length = 255)
    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    @Column(name = "NAME", length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "SORT_NO")
    public Integer getSortNo() {
        return sortNo;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

    @Column(name = "IS_GROUP")
    public Boolean getIsGroup() {
        return isGroup;
    }

    public void setIsGroup(Boolean isGroup) {
        this.isGroup = isGroup;
    }

    @Column(name = "IS_SEPERATOR")
    public Boolean getIsSeperator() {
        return isSeperator;
    }

    public void setIsSeperator(Boolean isSeperator) {
        this.isSeperator = isSeperator;
    }

    @Column(name = "ACL", length = 255)
    public String getAcl() {
        return acl;
    }

    public void setAcl(String acl) {
        this.acl = acl;
    }

    @Column(name = "ENTRY_URL", length = 255)
    public String getEntryURL() {
        return entryURL;
    }

    public void setEntryURL(String entryURL) {
        this.entryURL = entryURL;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_MENU_ID")
    @Index(name = "IDX_MI_PMI")
    public MenuItem getParent() {
        return parent;
    }

    public void setParent(MenuItem parent) {
        this.parent = parent;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parent", orphanRemoval = true)
    @OrderBy("sortNo")
    public List<MenuItem> getChildren() {
        return children;
    }

    public void setChildren(List<MenuItem> children) {
        this.children = children;
    }

    @Transient
    private Boolean checked;

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    @Column(name = "SYSTEM_CODE")
    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

}
