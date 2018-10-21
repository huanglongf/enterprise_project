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
import javax.persistence.Transient;

import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.authorization.OperationUnit;


/**
 * 订单触发时间中间表
 * 
 * @author xiaolong.fei
 * 
 */
@Entity
@Table(name = "T_WH_INFO_TIME_REF")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class WhInfoTimeRef extends BaseModel {


    /**
     * 
     */
    private static final long serialVersionUID = -7439995155968366511L;
    /**
     * PK
     */
    private Long id;
    /**
     * 单据类型
     */
    private WhInfoTimeRefBillType billType;
    /**
     * 节点类型
     */
    private WhInfoTimeRefNodeType nodeType;
    /**
     * 相关单据号
     */
    private String slipCode;

    /**
     * 触发时间
     */
    private Date executionTime;
    /**
     * 创建用户
     */
    private Long createId;

    private Integer billTypeInt;

    private Integer nodeTypeInt;

    /**
     * 相关仓库
     */
    private OperationUnit warehouse;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_INFO_TIME_REF", sequenceName = "S_T_WH_INFO_TIME_REF", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_INFO_TIME_REF")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "BILL_TYPE", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.WhInfoTimeRefBillType")})
    public WhInfoTimeRefBillType getBillType() {
        return billType;
    }

    public void setBillType(WhInfoTimeRefBillType billType) {
        this.billType = billType;
    }

    @Column(name = "NODE_TYPE", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.WhInfoTimeRefNodeType")})
    public WhInfoTimeRefNodeType getNodeType() {
        return nodeType;
    }

    public void setNodeType(WhInfoTimeRefNodeType nodeType) {
        this.nodeType = nodeType;
    }

    @Column(name = "SLIP_CODE")
    public String getSlipCode() {
        return slipCode;
    }

    public void setSlipCode(String slipCode) {
        this.slipCode = slipCode;
    }

    @Column(name = "EXECUTION_TIME")
    public Date getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(Date executionTime) {
        this.executionTime = executionTime;
    }

    @Column(name = "CREATE_ID")
    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    @Transient
    public Integer getBillTypeInt() {
        return billTypeInt;
    }

    public void setBillTypeInt(Integer billTypeInt) {
        this.billTypeInt = billTypeInt;
    }

    @Transient
    public Integer getNodeTypeInt() {
        return nodeTypeInt;
    }

    public void setNodeTypeInt(Integer nodeTypeInt) {
        this.nodeTypeInt = nodeTypeInt;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OU_ID")
    public OperationUnit getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(OperationUnit warehouse) {
        this.warehouse = warehouse;
    }


}
