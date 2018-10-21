package com.bt.common.controller.param;

import java.io.Serializable;

import com.bt.common.base.LoadingType;
import com.bt.lmis.page.QueryParameter;

/**
 * @Title:commonParam
 * @Description: TODO(主页面传参)
 * @author Ian.Huang 
 * @date 2017年4月10日下午5:29:37
 */
public class CommonParam extends QueryParameter implements Serializable {
	
	/** 
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	 */
	private static final long serialVersionUID = 5963270486567481318L;
	// 查询参数编号
	private String uuid;
	// 加载类型-主页面加载（主页面刷新）数据加载（数据刷新）
	private LoadingType loadingType;
	// 查询列表收放true-收起false-放下
	private Boolean isCollapse;
	// 是否直接查询
	private Boolean isQuery;
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public LoadingType getLoadingType() {
		return loadingType;
	}
	public void setLoadingType(LoadingType loadingType) {
		this.loadingType = loadingType;
	}
	public Boolean getIsCollapse() {
		return isCollapse;
	}
	public void setIsCollapse(Boolean isCollapse) {
		this.isCollapse = isCollapse;
	}
	public Boolean getIsQuery() {
		return isQuery;
	}
	public void setIsQuery(Boolean isQuery) {
		this.isQuery = isQuery;
	}
	
}
