package com.lmis.setup.pageElement.model;

import java.util.Date;

import com.lmis.framework.baseModel.PersistentObject;

import io.swagger.annotations.ApiModelProperty;

/** 
 * @ClassName: SetupPageElement
 * @Description: TODO(页面元素)
 * @author Ian.Huang
 * @date 2017年12月6日 下午9:02:54 
 * 
 */
public class SetupPageElement extends PersistentObject {

	/** 
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	 */
	private static final long serialVersionUID = -5835210175960005213L;
	
	@ApiModelProperty(value = "元素ID，新增/修改/删除时必输")
	private String elementId;
	
	@ApiModelProperty(value = "元素名称，新增/修改时必输")
	private String elementName;
	
	@ApiModelProperty(value = "所属布局ID，新增/修改时必输")
	private String layoutId;
	
	@ApiModelProperty(value = "显示顺序")
	private Integer elementSeq;
	
	@ApiModelProperty(value = "宽度")
	private String elementWidth;
	
	@ApiModelProperty(value = "高度")
	private String elementHeight;
	
	@ApiModelProperty(value = "元素类型，新增/修改时必输")
	private String elementType;
	
	@ApiModelProperty(value = "初始值")
	private String defaultValue;
	
	@ApiModelProperty(value = "是否能操作")
	private Boolean elementDisabled;
	
	@ApiModelProperty(value = "显示格式，数字，时间使用")
	private String elementFormat;
	
	@ApiModelProperty(value = "查询对应表名")
	private String tableId;
	
	@ApiModelProperty(value = "查询对应字段名")
	private String columnId;
	
	@ApiModelProperty(value = "字段类型")
	private String columnType;
	
	@ApiModelProperty(value = "查询连接符")
	private String whereType;
	
	@ApiModelProperty(value = "查询运算符")
	private String whereOperator;
	
	@ApiModelProperty(value = "新增只读")
	private Boolean addReadonly;
	
	@ApiModelProperty(value = "修改只读")
	private Boolean updateReadonly;
	
	@ApiModelProperty(value = "按钮快捷键")
	private String buttonClick;

	@ApiModelProperty(value = "必输（单选框显示0/1）")
	private Boolean notNull;
	
	@ApiModelProperty(value = "新增必输（单选框显示0/1）")
	private Boolean addNotNull;
	
	@ApiModelProperty(value = "修改必输（单选框显示0/1）")
	private Boolean updateNotNull;
	
	@ApiModelProperty(value = "按钮位置")
	private String buttonSet;
	
	@ApiModelProperty(value = "按钮图片")
	private String buttonPicture;
	
	@ApiModelProperty(value = "页面title")
	private String elementTitle;
	
	@ApiModelProperty(value = "字段长度")
	private Integer columnLen;
	
	@ApiModelProperty(value = "是否隐藏")
	private Boolean elementHide;
	
	@ApiModelProperty(value = "字符过滤")
	private Boolean isFilt;
	
	public SetupPageElement() {}
	
	public SetupPageElement(String id, Boolean isDeleted, String elementId) {
		super.setId(id);
		super.setIsDeleted(isDeleted);
		this.elementId = elementId;
	}
	
	public SetupPageElement(String id, Date createTime, String createBy, Date updateTime, String updateBy, Boolean isDeleted,
			Boolean isDisabled, Integer version, String pwrOrg, String elementId, String elementName, String layoutId,
			String elementType) {
		super(id, createTime, createBy, updateTime, updateBy, isDeleted, isDisabled, version, pwrOrg);
		this.elementId = elementId;
		this.elementName = elementName;
		this.layoutId = layoutId;
		this.elementType = elementType;
	}
	
	public SetupPageElement(String id, Date createTime, String createBy, Date updateTime, String updateBy, Boolean isDeleted,
			Boolean isDisabled, Integer version, String pwrOrg, String elementId, String elementName, String layoutId,
			String elementType, Integer columnLen) {
		super(id, createTime, createBy, updateTime, updateBy, isDeleted, isDisabled, version, pwrOrg);
		this.elementId = elementId;
		this.elementName = elementName;
		this.layoutId = layoutId;
		this.elementType = elementType;
		this.columnLen = columnLen;
	}
	
	public String getElementId() {
		return elementId;
	}

	public void setElementId(String elementId) {
		this.elementId = elementId;
	}

	public String getElementName() {
		return elementName;
	}

	public void setElementName(String elementName) {
		this.elementName = elementName;
	}

	public String getLayoutId() {
		return layoutId;
	}

	public void setLayoutId(String layoutId) {
		this.layoutId = layoutId;
	}

	public Integer getElementSeq() {
		return elementSeq;
	}

	public void setElementSeq(Integer elementSeq) {
		this.elementSeq = elementSeq;
	}

	public String getElementWidth() {
		return elementWidth;
	}

	public void setElementWidth(String elementWidth) {
		this.elementWidth = elementWidth;
	}

	public String getElementHeight() {
		return elementHeight;
	}

	public void setElementHeight(String elementHeight) {
		this.elementHeight = elementHeight;
	}

	public String getElementType() {
		return elementType;
	}

	public void setElementType(String elementType) {
		this.elementType = elementType;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public Boolean getElementDisabled() {
		return elementDisabled;
	}

	public void setElementDisabled(Boolean elementDisabled) {
		this.elementDisabled = elementDisabled;
	}

	public String getElementFormat() {
		return elementFormat;
	}

	public void setElementFormat(String elementFormat) {
		this.elementFormat = elementFormat;
	}

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public String getColumnId() {
		return columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	public String getWhereType() {
		return whereType;
	}

	public void setWhereType(String whereType) {
		this.whereType = whereType;
	}

	public String getWhereOperator() {
		return whereOperator;
	}

	public void setWhereOperator(String whereOperator) {
		this.whereOperator = whereOperator;
	}
	
	public Boolean getAddReadonly() {
		return addReadonly;
	}

	public void setAddReadonly(Boolean addReadonly) {
		this.addReadonly = addReadonly;
	}

	public Boolean getUpdateReadonly() {
		return updateReadonly;
	}

	public void setUpdateReadonly(Boolean updateReadonly) {
		this.updateReadonly = updateReadonly;
	}

	public String getButtonClick() {
		return buttonClick;
	}

	public void setButtonClick(String buttonClick) {
		this.buttonClick = buttonClick;
	}
	
	public Boolean getNotNull() {
		return notNull;
	}

	public void setNotNull(Boolean notNull) {
		this.notNull = notNull;
	}

	public Boolean getAddNotNull() {
		return addNotNull;
	}

	public void setAddNotNull(Boolean addNotNull) {
		this.addNotNull = addNotNull;
	}

	public Boolean getUpdateNotNull() {
		return updateNotNull;
	}

	public void setUpdateNotNull(Boolean updateNotNull) {
		this.updateNotNull = updateNotNull;
	}

	public String getButtonSet() {
		return buttonSet;
	}

	public void setButtonSet(String buttonSet) {
		this.buttonSet = buttonSet;
	}

	public String getButtonPicture() {
		return buttonPicture;
	}

	public void setButtonPicture(String buttonPicture) {
		this.buttonPicture = buttonPicture;
	}
	
	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public String getElementTitle() {
		return elementTitle;
	}

	public void setElementTitle(String elementTitle) {
		this.elementTitle = elementTitle;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public Integer getColumnLen() {
		return columnLen;
	}

	public void setColumnLen(Integer columnLen) {
		this.columnLen = columnLen;
	}

	public Boolean getElementHide() {
		return elementHide;
	}

	public void setElementHide(Boolean elementHide) {
		this.elementHide = elementHide;
	}

	public Boolean getIsFilt() {
		return isFilt;
	}

	public void setIsFilt(Boolean isFilt) {
		this.isFilt = isFilt;
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
		+ ",elementId:" + elementId
		+ ",elementName:" + elementName
		+ ",layoutId:" + layoutId
		+ ",elementSeq:" + elementSeq
		+ ",elementWidth:" + elementWidth
		+ ",elementHeight:" + elementHeight
		+ ",elementType:" + elementType
		+ ",defaultValue:" + defaultValue
		+ ",elementDisabled:" + elementDisabled
		+ ",elementFormat:" + elementFormat
		+ ",tableId:" + tableId
		+ ",columnId:" + columnId
		+ ",columnType:" + columnType
		+ ",whereType:" + whereType
		+ ",whereOperator:" + whereOperator
		+ ",addReadonly:" + addReadonly
		+ ",updateReadonly:" + updateReadonly
		+ ",buttonClick:" + buttonClick
		+ ",notNull:" + notNull
		+ ",addNotNull:" + addNotNull
		+ ",updateNotNull:" + updateNotNull
		+ ",buttonSet:" + buttonSet
		+ ",buttonPicture:" + buttonPicture
		+ ",elementTitle:" + elementTitle
		+ ",elementHide:" + elementHide
		+ ",columnLen:" + columnLen
		+ ",isFilt:" + isFilt
		+"}";
	}

}
