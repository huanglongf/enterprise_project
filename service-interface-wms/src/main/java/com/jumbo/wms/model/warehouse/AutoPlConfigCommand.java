package com.jumbo.wms.model.warehouse;

import java.io.Serializable;

public class AutoPlConfigCommand implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1304928741216269535L;

	/**
	 * PK
	 */
	private Long id;

	/**
	 * 下次执行时间
	 */
	private String nextExecuteTime;

	/**
	 * 仓库ID FK
	 */
	private Long ouId;

	/**
	 * 规则ID
	 */
	private Long autoPlr;

	/**
	 * 间隔分钟
	 */
	private Integer intervalMinute;

	private String createTime;

	/**
	 * 状态
	 */
	private Integer status;

	/**
	 * 是否单独任务
	 */
	private Integer isSingleTask;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOuId() {
		return ouId;
	}

	public void setOuId(Long ouId) {
		this.ouId = ouId;
	}

	public Long getAutoPlr() {
		return autoPlr;
	}

	public void setAutoPlr(Long autoPlr) {
		this.autoPlr = autoPlr;
	}

	public Integer getIntervalMinute() {
		return intervalMinute;
	}

	public void setIntervalMinute(Integer intervalMinute) {
		this.intervalMinute = intervalMinute;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getIsSingleTask() {
		return isSingleTask;
	}

	public void setIsSingleTask(Integer isSingleTask) {
		this.isSingleTask = isSingleTask;
	}

	public String getNextExecuteTime() {
		return nextExecuteTime;
	}

	public void setNextExecuteTime(String nextExecuteTime) {
		this.nextExecuteTime = nextExecuteTime;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}
