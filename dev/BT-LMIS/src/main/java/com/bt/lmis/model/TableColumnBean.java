package com.bt.lmis.model;

import java.io.Serializable;

public class TableColumnBean implements Serializable{
	private static final long serialVersionUID = 1L;
	//列名称
	private String column_name;
	//列备注
	private String column_comment;
	//数据类型
	private String data_type;
	//表名称
	private String table_name;
	
	public String getColumn_name() {
		return column_name;
	}
	public void setColumn_name(String column_name) {
		this.column_name = column_name;
	}
	public String getColumn_comment() {
		return column_comment;
	}
	public void setColumn_comment(String column_comment) {
		this.column_comment = column_comment;
	}
	public String getData_type() {
		return data_type;
	}
	public void setData_type(String data_type) {
		this.data_type = data_type;
	}
	public String getTable_name() {
		return table_name;
	}
	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}


}
