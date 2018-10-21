package com.bt.common.controller.model;

import java.io.Serializable;
import java.util.Map;

/** 
 * @ClassName: TableFunctionConfig
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年8月22日 上午11:47:54 
 * 
 */
public class TableFunctionConfig implements Serializable {

	/** 
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	 */
	private static final long serialVersionUID = 4777557838839033861L;
	
	/** 
	 * @Fields tableName : TODO(表名) 
	 */
	private String tableName;
	/** 
	 * @Fields dbclickTr : TODO(是否开启行双击事件) 
	 */
	private boolean dbclickTr;
	
	/** 
	 * @Fields config : TODO(配置传参) 
	 */
	private Map<String, Object> config;

	public TableFunctionConfig() {}
	
	public TableFunctionConfig(String tableName, boolean dbclickTr, Map<String, Object> config) {
		super();
		this.tableName = tableName;
		this.dbclickTr = dbclickTr;
		this.config = config;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public boolean isDbclickTr() {
		return dbclickTr;
	}

	public void setDbclickTr(boolean dbclickTr) {
		this.dbclickTr = dbclickTr;
	}

	public Map<String, Object> getConfig() {
		return config;
	}

	public void setConfig(Map<String, Object> config) {
		this.config = config;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
