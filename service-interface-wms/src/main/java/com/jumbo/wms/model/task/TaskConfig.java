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
package com.jumbo.wms.model.task;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import com.jumbo.wms.model.BaseModel;

/**
 * 定时任务配置文件
 * 
 * @author sjk
 * 
 */
@Entity
@Table(name = "T_SYS_TASK_CONFIG", uniqueConstraints = {@UniqueConstraint(columnNames = {"JOB_CODE"}), @UniqueConstraint(columnNames = {"TRIGGER_CODE"})})
public class TaskConfig extends BaseModel {

    private static final long serialVersionUID = 8896613047182200352L;

    public static String ARGUMENTS_SPLIT_CODE = ";";
    /**
     * PK
     */
    private Long id;
    /**
     * 定时任务类名
     */
    private String className;
    /**
     * 方法名
     */
    private String method;
    /**
     * 是否并行
     */
    private Boolean isConcurrent = false;
    /**
     * 任务名称
     */
    private String jobCode;
    /**
     * 调度名称
     */
    private String triggerCode;
    /**
     * 定时任务组
     */
    private String group = "DEFAULT";
    /**
     * 描述
     */
    private String describe;
    /**
     * 定时配置
     */
    private String cronExpression;
    /**
     * 是否使用
     */
    private Boolean isAvailable = true;
    /**
     * 定时任务参数，如果无则不填写，如有多个则按分号分隔
     */
    private String arguments;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_STC", sequenceName = "S_T_SYS_TASK_CONFIG", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_STC")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "CLASS_NAME", length = 250, nullable = false)
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Column(name = "METHOD_NAME", length = 150, nullable = false)
    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Column(name = "IS_CONCURRENT")
    public Boolean getIsConcurrent() {
        return isConcurrent;
    }

    public void setIsConcurrent(Boolean isConcurrent) {
        this.isConcurrent = isConcurrent;
    }

    @Column(name = "JOB_CODE", length = 30, nullable = false)
    public String getJobCode() {
        return jobCode;
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }

    @Column(name = "TRIGGER_CODE", length = 30, nullable = false)
    public String getTriggerCode() {
        return triggerCode;
    }

    public void setTriggerCode(String triggerCode) {
        this.triggerCode = triggerCode;
    }

    @Column(name = "GROUP_NAME", length = 30, nullable = false)
    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Column(name = "DESCRIBE", length = 350, nullable = true)
    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    @Column(name = "CRON_EXPRESSION", length = 100, nullable = false)
    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    @Column(name = "IS_AVAILABLE")
    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    @Column(name = "ARGUMENTS")
    public String getArguments() {
        return arguments;
    }

    public void setArguments(String arguments) {
        this.arguments = arguments;
    }

}
