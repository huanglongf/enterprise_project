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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;

/**
 * 客户
 * 
 * @author sjk
 * 
 */
@Entity
@Table(name = "T_BI_CUSTOMER")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class Customer extends BaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = 2837325421248273541L;
    /**
     * PK
     */
    private Long id;
    /**
     * version
     */
    private int version;
    /**
     * 品牌编码code
     */
    private String code;
    /**
     * 名称
     */
    private String name;
    /**
     * 是否可用
     */
    private Boolean isAvailable;
    /**
     * 描述
     */
    private String memo;

    /**
     * 是否是对接外部的oms （主要区别增量库存）
     * 
     * @return
     */
    private String isOms; // 1:是外部的

    @Column(name = "IS_OMS")
    public String getIsOms() {
        return isOms;
    }

    public void setIsOms(String isOms) {
        this.isOms = isOms;
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_SKU", sequenceName = "S_T_BI_CUSTOMER", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SKU")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Version
    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Column(name = "CODE", length = 30)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "NAME", length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "IS_AVAILABLE", length = 50)
    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    @Column(name = "MEMO", length = 200)
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

}
