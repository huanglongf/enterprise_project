/**
 * Copyright (c) 2013 Baozun All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Baozun. You shall not disclose
 * such Confidential Information and shall use it only in accordance with the terms of the license
 * agreement you entered into with Baozun.
 * 
 * BAOZUN MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. BAOZUN SHALL NOT BE LIABLE FOR ANY DAMAGES
 * SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 * 
 */
package com.jumbo.wms.model.system;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.jumbo.wms.model.BaseModel;


/**
 * 
 * @author lark
 * 
 */
@Entity
@Table(name = "T_SYS_SCHEDULER_TASK")
public class SysSchedulerTask extends BaseModel {

    private static final long serialVersionUID = 1695266976841319206L;

    /**
     * 正常
     */
    public static final Integer LIFECYCLE_NORMAL = 1;
    /**
     * 禁用
     */
    public static final Integer LIFECYCLE_DISABLE = 0;
    /**
     * 已删除
     */
    public static final Integer LIFECYCLE_DELETED = 2;
    // alias
    public static final String TABLE_ALIAS = "SysSchedulerTask";
    public static final String ALIAS_BEAN_NAME = "beanName";
    public static final String ALIAS_CODE = "code";
    public static final String ALIAS_DESCRIPTION = "description";
    public static final String ALIAS_LIFECYCLE = "lifecycle";
    public static final String ALIAS_METHOD_NAME = "methodName";
    public static final String ALIAS_TIME_TYPE = "timeType";
    public static final String ALIAS_TIME_EXP = "timeExp";
    public static final String ALIAS_ARGS = "参数（使用,号分隔多个）";
    public static final String ALIAS_NEED_COMPENSATE = "needCompensate";

    // date formats

    // columns START
    private Long id;
    private java.lang.String beanName;
    private java.lang.String code;
    private java.lang.String description;
    private java.lang.Integer lifecycle;
    private java.lang.String methodName;
    private java.lang.Integer timeType;
    private java.lang.String timeExp;
    private java.lang.String args;
    private java.lang.Boolean needCompensate;
    private Integer node; // 节点

    // columns END
    public SysSchedulerTask() {}

    public SysSchedulerTask(Long id) {
        this.id = id;
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_SS", sequenceName = "S_SYS_SCHEDULER_TASK", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SS")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "BEAN_NAME", length = 500)
    public String getBeanName() {
        return this.beanName;
    }

    public void setBeanName(String value) {
        this.beanName = value;
    }

    @Column(name = "CODE", length = 100)
    public String getCode() {
        return this.code;
    }

    public void setCode(String value) {
        this.code = value;
    }

    @Column(name = "DESCRIPTION", length = 500)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    @Column(name = "LIFE_CYCLE")
    public java.lang.Integer getLifecycle() {
        return this.lifecycle;
    }

    public void setLifecycle(java.lang.Integer value) {
        this.lifecycle = value;
    }

    @Column(name = "METHOD_NAME", length = 500)
    public java.lang.String getMethodName() {
        return this.methodName;
    }

    public void setMethodName(java.lang.String value) {
        this.methodName = value;
    }

    @Column(name = "TIME_TYPE")
    public java.lang.Integer getTimeType() {
        return this.timeType;
    }

    public void setTimeType(java.lang.Integer value) {
        this.timeType = value;
    }

    @Column(name = "TIME_EXP", length = 100)
    public java.lang.String getTimeExp() {
        return this.timeExp;
    }

    public void setTimeExp(java.lang.String value) {
        this.timeExp = value;
    }

    @Column(name = "ARGS", length = 100)
    public java.lang.String getArgs() {
        return args;
    }

    public void setArgs(java.lang.String args) {
        this.args = args;
    }

    @Column(name = "NEED_COMPENSATE")
    public java.lang.Boolean getNeedCompensate() {
        return this.needCompensate;
    }

    public void setNeedCompensate(java.lang.Boolean value) {
        this.needCompensate = value;
    }

    @Column(name = "node")
    public Integer getNode() {
        return node;
    }

    public void setNode(Integer node) {
        this.node = node;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("Id", getId()).append("BeanName", getBeanName()).append("Code", getCode()).append("Description", getDescription()).append("Lifecycle", getLifecycle()).append("MethodName", getMethodName())
                .append("TimeType", getTimeType()).append("TimeExp", getTimeExp()).append("Args", getArgs()).append("NeedCompensate", getNeedCompensate()).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(getId()).append(getBeanName()).append(getCode()).append(getDescription()).append(getLifecycle()).append(getMethodName()).append(getTimeType()).append(getTimeExp()).append(getArgs()).append(getNeedCompensate())
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SysSchedulerTask == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        SysSchedulerTask other = (SysSchedulerTask) obj;
        return new EqualsBuilder().append(getId(), other.getId()).append(getBeanName(), other.getBeanName()).append(getCode(), other.getCode()).append(getDescription(), other.getDescription()).append(getLifecycle(), other.getLifecycle())
                .append(getMethodName(), other.getMethodName()).append(getTimeType(), other.getTimeType()).append(getTimeExp(), other.getTimeExp()).append(getArgs(), other.getArgs()).append(getNeedCompensate(), other.getNeedCompensate()).isEquals();
    }
}
