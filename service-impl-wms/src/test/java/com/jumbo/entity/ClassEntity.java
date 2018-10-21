package com.jumbo.entity;

import java.util.List;

public class ClassEntity {

	private String entityClass; // 类名
	private String app; // 项目
	private List<String> mothed; // 方法名
	private List<String> success; // 成功记录
	private List<String> failure; // 失败记录
	private List<String> avgElapsed;
	private List<String> maxElapsed;
	private List<String> maxConcurrent;

	public String getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(String entityClass) {
		this.entityClass = entityClass;
	}

	public List<String> getMothed() {
		return mothed;
	}

	public void setMothed(List<String> mothed) {
		this.mothed = mothed;
	}

	public List<String> getSuccess() {
		return success;
	}

	public void setSuccess(List<String> success) {
		this.success = success;
	}

	public List<String> getFailure() {
		return failure;
	}

	public void setFailure(List<String> failure) {
		this.failure = failure;
	}

	public List<String> getAvgElapsed() {
		return avgElapsed;
	}

	public void setAvgElapsed(List<String> avgElapsed) {
		this.avgElapsed = avgElapsed;
	}

	public List<String> getMaxElapsed() {
		return maxElapsed;
	}

	public void setMaxElapsed(List<String> maxElapsed) {
		this.maxElapsed = maxElapsed;
	}

	public List<String> getMaxConcurrent() {
		return maxConcurrent;
	}

	public void setMaxConcurrent(List<String> maxConcurrent) {
		this.maxConcurrent = maxConcurrent;
	}

	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}

}
