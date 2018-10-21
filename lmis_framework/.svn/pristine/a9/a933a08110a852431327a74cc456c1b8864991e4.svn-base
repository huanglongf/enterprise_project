package com.lmis.setup.pageTable.model;

import java.util.Date;

import com.lmis.framework.baseModel.PersistentObject;

import io.swagger.annotations.ApiModelProperty;

/** 
 * @ClassName: SetupPageTable
 * @Description: TODO(页面查询结果字段)
 * @author Ian.Huang
 * @date 2017年12月8日 下午5:31:02 
 * 
 */
public class SetupPageTable extends PersistentObject {

	/** 
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	 */
	private static final long serialVersionUID = -3657955403614598096L;
	
	@ApiModelProperty(value = "列ID，新增/修改/删除时必输")
	private String columnId;
	
	@ApiModelProperty(value = "列名，新增/修改时必输")
	private String columnName;
	
	@ApiModelProperty(value = "所属布局ID，新增/修改时必输")
	private String layoutId;
	
	@ApiModelProperty(value = "显示顺序")
	private Integer columnSeq;
	
	@ApiModelProperty(value = "宽度")
	private String columnWidth;
	
	@ApiModelProperty(value = "高度")
	private String columnHeight;
	
	@ApiModelProperty(value = "显示格式，数字，时间使用")
	private String columnFormat;
	
	@ApiModelProperty(value = "对应表名")
	private String tableId;
	
	@ApiModelProperty(value = "对应字段名")
	private String tableColumnId;
	
	@ApiModelProperty(value = "字段类型")
	private String columnType;
	
	@ApiModelProperty(value = "排序类型")
	private String orderbyType;
	
	@ApiModelProperty(value = "排序顺序")
	private Integer orderbySeq;
	
	@ApiModelProperty(value = "统计类型")
	private String countType;
	
	@ApiModelProperty(value = "统计名称")
	private String countName;
	
	@ApiModelProperty(value = "是否隐藏")
	private Boolean columnHide;
	
	@ApiModelProperty(value = "列表类型(普通/按钮)")
	private String listType;

	public SetupPageTable() {}
	
	public SetupPageTable(String id, Boolean isDeleted, String columnId) {
		super.setId(id);
		super.setIsDeleted(isDeleted);
		this.columnId = columnId;
	}
	
	public SetupPageTable(String id, Date createTime, String createBy, Date updateTime, String updateBy,
			Boolean isDeleted, Boolean isDisabled, Integer version, String pwrOrg, String columnId,
			String columnName, String layoutId) {
		super(id, createTime, createBy, updateTime, updateBy, isDeleted, isDisabled, version, pwrOrg);
		this.columnId = columnId;
		this.columnName = columnName;
		this.layoutId = layoutId;
	}

	public String getColumnId() {
		return columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getLayoutId() {
		return layoutId;
	}

	public void setLayoutId(String layoutId) {
		this.layoutId = layoutId;
	}

	public Integer getColumnSeq() {
		return columnSeq;
	}

	public void setColumnSeq(Integer columnSeq) {
		this.columnSeq = columnSeq;
	}

	public String getColumnWidth() {
		return columnWidth;
	}

	public void setColumnWidth(String columnWidth) {
		this.columnWidth = columnWidth;
	}

	public String getColumnHeight() {
		return columnHeight;
	}

	public void setColumnHeight(String columnHeight) {
		this.columnHeight = columnHeight;
	}

	public String getColumnFormat() {
		return columnFormat;
	}

	public void setColumnFormat(String columnFormat) {
		this.columnFormat = columnFormat;
	}

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public String getTableColumnId() {
		return tableColumnId;
	}

	public void setTableColumnId(String tableColumnId) {
		this.tableColumnId = tableColumnId;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
	
	public String getOrderbyType() {
		return orderbyType;
	}
	
	public void setOrderbyType(String orderbyType) {
		this.orderbyType = orderbyType;
	}

	public Integer getOrderbySeq() {
		return orderbySeq;
	}

	public void setOrderbySeq(Integer orderbySeq) {
		this.orderbySeq = orderbySeq;
	}
	
	public String getCountType() {
		return countType;
	}

	public void setCountType(String countType) {
		this.countType = countType;
	}

	public String getCountName() {
		return countName;
	}

	public void setCountName(String countName) {
		this.countName = countName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public Boolean getColumnHide() {
		return columnHide;
	}

	public void setColumnHide(Boolean columnHide) {
		this.columnHide = columnHide;
	}

	public String getListType() {
		return listType;
	}

	public void setListType(String listType) {
		this.listType = listType;
	}

	public String toString() {
		return "{id:" + super.getId()
		+ ",createTime:" + super.getCreateTime()
		+ ",createBy:" + super.getCreateBy()
		+ ",updateTime:" + super.getUpdateTime()
		+ ",updateBy:" + super.getUpdateBy()
		+ ",isDeleted:" + super.getIsDeleted()
		+ ",isDisabled:" + super.getIsDisabled()
		+ ",version:" + super.getVersion()
		+ ",pwrOrg:" + super.getPwrOrg()
		+ ",columnId:" + columnId
		+ ",columnName:" + columnName
		+ ",layoutId:" + layoutId
		+ ",columnSeq:" + columnSeq
		+ ",columnWidth:" + columnWidth
		+ ",columnHeight:" + columnHeight
		+ ",columnFormat:" + columnFormat
		+ ",tableId:" + tableId
		+ ",tableColumnId:" + tableColumnId
		+ ",columnType:" + columnType
		+ ",orderbyType:" + orderbyType
		+ ",orderbySeq:" + orderbySeq
		+ ",countType:" + countType
		+ ",countName:" + countName
		+ ",columnHide:" + columnHide
		+ ",listType:" + listType
		+"}";
	}
	
}
