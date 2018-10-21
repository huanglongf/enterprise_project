package com.bt.common.model;

public class Schedule {
	// 触发器名
	private String name;
	// 触发器组
	private String group;
	// cron表达式
	private String cronExpression;
	// 是否并发
	private boolean isConcurrent;
	// 完整类名
	private String className;
	// 方法名
	private String methodName;
	// 无参构造
	public Schedule() {}
	// 有参构造
	public Schedule(String name, String group, String cronExpression, boolean isConcurrent, String className, String methodName) {
		this.name = name;
		this.group = group;
		this.cronExpression = cronExpression;
		this.isConcurrent = isConcurrent;
		this.className = className;
		this.methodName = methodName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getCronExpression() {
		return cronExpression;
	}
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	public boolean isConcurrent() {
		return isConcurrent;
	}
	public void setConcurrent(boolean isConcurrent) {
		this.isConcurrent = isConcurrent;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	
}
