package com.bt.lmis.model;

import java.util.Date;
/** 
* @ClassName: ScheduleJob 
* @Description: TODO(计划任务信息) 
* @author Yuriy.Jiang
* @date 2016年7月13日 上午11:35:37 
*  
*/
public class ScheduleJob {

	/**
	 * 任务开启
	 */
	public static final String STATUS_START = "1";
	/**
	 * 任务停止
	 */
	public static final String STATUS_STOP = "0";
	/**
	 * 任务异步-并发
	 */
	public static final String CONCURRENT_IS = "1";
	/**
	 * 任务同步-阻塞
	 */
	public static final String CONCURRENT_NOT = "0";
	private Long jobId;
	private Date createTime;
	private Date updateTime;
	/**
	 * 任务状态 是否启动任务
	 */
	private String jobStatus;
	/**
	 * 运行状态 是否正在运行任务
	 */
	private String runningStatus;
	/**
	 * 任务分组
	 */
	private String jobGroup;
	/**
	 * 任务名称
	 */
	private String jobName;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 1-异步/2-同步
	 */
	private String isConcurrent;
	/**
	 * 任务执行时调用哪个类的方法 包名+类名
	 */
	private String beanClass;
	/**
	 * 任务调用的方法名
	 */
	private String methodName;
	/**
	 * cron表达式
	 */
	private String cronExpression;
	/**
	 * spring bean
	 */
	private String springId;
	/**
	 * 定时器任务类型
	 */
	private String jobType;
	public Long getJobId() {
		return jobId;
	}
	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getJobStatus() {
		return jobStatus;
	}
	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}
	public String getRunningStatus() {
		return runningStatus;
	}
	public void setRunningStatus(String runningStatus) {
		this.runningStatus = runningStatus;
	}
	public String getJobGroup() {
		return jobGroup;
	}
	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getIsConcurrent() {
		return isConcurrent;
	}
	public void setIsConcurrent(String isConcurrent) {
		this.isConcurrent = isConcurrent;
	}
	public String getBeanClass() {
		return beanClass;
	}
	public void setBeanClass(String beanClass) {
		this.beanClass = beanClass;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getCronExpression() {
		return cronExpression;
	}
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	public String getSpringId() {
		return springId;
	}
	public void setSpringId(String springId) {
		this.springId = springId;
	}
	public String getJobType() {
		return jobType;
	}
	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
}