package com.bt.lmis.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.bt.lmis.base.TABLE_ROLE;

/**
 * @Title:SimpleTable
 * @Description: TODO(简单单表结构)
 * @author Ian.Huang 
 * @date 2017年3月17日下午7:19:00
 */
public class SimpleTable {
	// 角色 如：主/从
	private TABLE_ROLE tableRole;
	// 表名
	private String tableName;
	// 表头
	private LinkedHashMap<String, String> tableHeader;
	// 表内容
	private List<Map<String, Object>> tableContent;
	public SimpleTable() {}
	public SimpleTable(TABLE_ROLE tableRole, String tableName, LinkedHashMap<String, String> tableHeader, List<Map<String, Object>> tableContent) {
		this.tableRole = tableRole;
		this.tableName = tableName;
		this.tableHeader = tableHeader;
		this.tableContent = tableContent;
		
	}
	public TABLE_ROLE getTableRole() {
		return tableRole;
	}
	public void setTableRole(TABLE_ROLE tableRole) {
		this.tableRole = tableRole;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public LinkedHashMap<String, String> getTableHeader() {
		return tableHeader;
	}
	public void setTableHeader(LinkedHashMap<String, String> tableHeader) {
		this.tableHeader = tableHeader;
	}
	public List<Map<String, Object>> getTableContent() {
		return tableContent;
	}
	public void setTableContent(List<Map<String, Object>> tableContent) {
		this.tableContent = tableContent;
	}
	
}