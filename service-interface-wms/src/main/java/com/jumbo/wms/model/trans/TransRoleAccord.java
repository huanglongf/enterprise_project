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

package com.jumbo.wms.model.trans;


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

import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.authorization.User;

/**
 * 渠道快递规则状态变更
 * 
 * @author lingyun.dai
 * 
 */
@Entity
@Table(name = "T_BI_TRANS_ROLE_ACCORD")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class TransRoleAccord extends BaseModel {
	private static final long serialVersionUID = -6184537875696200552L;

    /**
     * PK
     */
    private Long id;

    /**
     * 规则id
     */
    private TransRole roleId;

    /**
     * 执行时间
     */
    private Date changeTime;

    /**
     * 是否可用
     */
    private Boolean roleIsAvailable;
    
    /**
     * 优先级(越小越大)
     */
    private Integer priority;

    /**
     * 状态
     */
    private TransRoleAccordStatus status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后操作时间
     */
    private Date lastOperation;

    /**
     * 最后操作人
     */
    private User lastOperationer;
    
    /**
     * 创建用户
     */
    private User createUser;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_TRANS_ROLE_ACCORD", sequenceName = "S_T_BI_TRANS_ROLE_ACCORD", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TRANS_ROLE_ACCORD")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROLE_ID")
    public TransRole getRoleId() {
		return roleId;
	}

	public void setRoleId(TransRole roleId) {
		this.roleId = roleId;
	}

    
	@Column(name = "CHANGE_TIME")
	public Date getChangeTime() {
		return changeTime;
	}
	
	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}

	@Column(name = "ROLE_IS_AVAILABLE")
	public Boolean getRoleIsAvailable() {
		return roleIsAvailable;
	}

	public void setRoleIsAvailable(Boolean roleIsAvailable) {
		this.roleIsAvailable = roleIsAvailable;
	}

	@Column(name = "PRIORITY")
	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	@Column(name = "STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.trans.TransRoleAccordStatus")})
	public TransRoleAccordStatus getStatus() {
		return status;
	}

	public void setStatus(TransRoleAccordStatus status) {
		this.status = status;
	}

	@Column(name = "CARETE_TIME")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "LAST_OPERATION")
	public Date getLastOperation() {
		return lastOperation;
	}

	public void setLastOperation(Date lastOperation) {
		this.lastOperation = lastOperation;
	}

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LAST_OPERATIONER")
	public User getLastOperationer() {
		return lastOperationer;
	}

	public void setLastOperationer(User lastOperationer) {
		this.lastOperationer = lastOperationer;
	}

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATE_USER")
	public User getCreateUser() {
		return createUser;
	}

	public void setCreateUser(User createUser) {
		this.createUser = createUser;
	}

}
