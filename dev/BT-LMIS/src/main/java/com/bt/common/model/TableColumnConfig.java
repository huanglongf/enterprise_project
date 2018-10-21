package com.bt.common.model;

import java.io.Serializable;
import java.util.Date;

/** 
 * @ClassName: TableColumnConfig
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年8月18日 上午11:12:35 
 * 
 */
public class TableColumnConfig implements Serializable {

	/** 
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	 */
	private static final long serialVersionUID = 72548016587554155L;
	private String id;
	private Date createTime;
	private String createBy;
	private Date updateTime;
	private String updateBy;
	private String submitTo;
	private String tableName;
	private String tableColumnId;
	private int sort;
	public TableColumnConfig() {}
	
	public TableColumnConfig(String createBy, String submitTo, String tableName, String tableColumnId, int sort) {
		super();
		this.createBy = createBy;
		this.submitTo = submitTo;
		this.tableName = tableName;
		this.tableColumnId = tableColumnId;
		this.sort = sort;
		
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public String getSubmitTo() {
		return submitTo;
	}
	public void setSubmitTo(String submitTo) {
		this.submitTo = submitTo;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getTableColumnId() {
		return tableColumnId;
	}
	public void setTableColumnId(String tableColumnId) {
		this.tableColumnId = tableColumnId;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
