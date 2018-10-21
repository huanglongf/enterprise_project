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
import javax.persistence.Version;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.authorization.OperationUnit;

/**
 * 库存状态
 * 
 * @author benjamin
 * 
 */
@Entity
@Table(name = "T_WH_INVENTORY_STATUS")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class InventoryStatus extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = -8320158502586521463L;
    /**
     * NIKE退入作业单库存状态：良品
     */
    public static final String INVENTORY_STATUS_GOOD = "良品";
    /**
     * NIKE退入作业单库存状态：残次品
     */
    public static final String INVENTORY_STATUS_IMPERFECTION = "残次品";
    /**
     * NIKE退入作业单库存状态：待处理品
     */
    public static final String INVENTORY_STATUS_RETURN_PRODUCT = "待处理品";

    public InventoryStatus() {
        super();
    }

    public InventoryStatus(Long id) {
        super();
        this.id = id;
    }

    /**
     * PK
     */
    private Long id;

    /**
     * 库存状态名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 是否系统内置，系统默认会初始化一个良品库存状态和一个残次品库存状态用于系统逻辑控制。系统库存状态不能被编辑
     */
    private Boolean isSystem = false;

    /**
     * 是否计入销售可用量
     */
    private Boolean isForSale = true;

    /**
     * 是否参与成本计算
     */
    private Boolean isInCost = true;

    /**
     * 是否有效
     */
    private Boolean isAvailable = true;
    private int version;

    /**
     * 对应的公司组织节点
     */
    private OperationUnit ou;

    /**
     * 最后修改时间
     */
    private Date lastModifyTime;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_INVS", sequenceName = "S_T_WH_INV_STATUS", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_INVS")
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

    @Column(name = "DESCRIPTION", length = 255)
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

    @Column(name = "IS_FORSALE")
    public Boolean getIsForSale() {
        return isForSale;
    }

    public void setIsForSale(Boolean isForSale) {
        this.isForSale = isForSale;
    }

    @Column(name = "IS_INCOST")
    public Boolean getIsInCost() {
        return isInCost;
    }

    public void setIsInCost(Boolean isInCost) {
        this.isInCost = isInCost;
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
    @JoinColumn(name = "OU_ID")
    @Index(name = "IDX_INVENTORY_OU")
    public OperationUnit getOu() {
        return ou;
    }

    public void setOu(OperationUnit ou) {
        this.ou = ou;
    }

    @Column(name = "LAST_MODIFY_TIME")
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

}
