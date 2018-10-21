package com.lmis.setup.pageElement.model;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

import io.swagger.annotations.ApiModelProperty;

/** 
 * @ClassName: ViewSetupPageElement
 * @Description: TODO(页面元素视图)
 * @author Ian.Huang
 * @date 2018年1月3日 下午4:20:44 
 * 
 */
public class ViewSetupPageElement {

	/** 
	 * @Fields id : TODO(主键ID) 
	 */
	@ApiModelProperty(value = "系统主键，修改/删除时必输")
	private String id;
	/** 
	 * @Fields createTime : TODO(创建时间) 
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "创建时间，系统后台生成")
	private Date createTime;
	/** 
	 * @Fields createBy : TODO(创建对象ID) 
	 */
	@ApiModelProperty(value = "创建人，系统后台生成")
	private String createBy;
	/** 
	 * @Fields updateTime : TODO(更新时间) 
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "最后修改时间，系统后台生成")
	private Date updateTime;
	/** 
	 * @Fields updateBy : TODO(更新对象ID) 
	 */
	@ApiModelProperty(value = "最后修改人，系统后台生成")
	private String updateBy;
	/** 
	 * @Fields isDeleted : TODO(逻辑删除标志 1-已删除 0-未删除) 
	 */
	@ApiModelProperty(value = "是否删除")
	private Boolean isDeleted;
	/** 
	 * @Fields isDisabled : TODO(启停标志 1-已禁用 0-未禁用)
	 */
	@ApiModelProperty(value = "启停标志")
	private Boolean isDisabled;
	/** 
	 * @Fields isDisabled : TODO(版本号)
	 */
	@ApiModelProperty(value = "版本号")
	private Integer version;
	/** 
	 * @Fields isDisabled : TODO(所属机构)
	 */
	@ApiModelProperty(value = "所属机构")
	private String pwrOrg;
	
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
	
	@ApiModelProperty(value = "库名")
	private String  tableSchema;
	
	@ApiModelProperty(value = "表名")
	private String  tableName;
	
	@ApiModelProperty(value = "字段名")
	private String  columnName;
	
	@ApiModelProperty(value = "是否为空")
	private String isNullAble;
	
	@ApiModelProperty(value = "数据类型")
	private String dataType;
	
	@ApiModelProperty(value = "字符串类最大长度")
	private String characterMaximumLength;
	
	@ApiModelProperty(value = "数字类最大长度")
	private String numbericPrecision;
	
	@ApiModelProperty(value = "数字类小数最大长度")
	private String numbericScale;
	
	@ApiModelProperty(value = "字段长度")
	private Integer columnLen;
	
	@ApiModelProperty(value = "是否隐藏")
	private Boolean elementHide;
	
	@ApiModelProperty(value = "字符过滤")
	private Boolean isFilt;
	
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

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Boolean getIsDisabled() {
		return isDisabled;
	}

	public void setIsDisabled(Boolean isDisabled) {
		this.isDisabled = isDisabled;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getPwrOrg() {
		return pwrOrg;
	}

	public void setPwrOrg(String pwrOrg) {
		this.pwrOrg = pwrOrg;
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

	
	
	
	public String getTableSchema() {
		return tableSchema;
	}

	public void setTableSchema(String tableSchema) {
		this.tableSchema = tableSchema;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getIsNullAble() {
		return isNullAble;
	}

	public void setIsNullAble(String isNullAble) {
		this.isNullAble = isNullAble;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getCharacterMaximumLength() {
		return characterMaximumLength;
	}

	public void setCharacterMaximumLength(String characterMaximumLength) {
		this.characterMaximumLength = characterMaximumLength;
	}

	public String getNumbericPrecision() {
		return numbericPrecision;
	}

	public void setNumbericPrecision(String numbericPrecision) {
		this.numbericPrecision = numbericPrecision;
	}

	public String getNumbericScale() {
		return numbericScale;
	}

	public void setNumbericScale(String numbericScale) {
		this.numbericScale = numbericScale;
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
		return "{id:" + id
		+ ",createTime:" + createTime
		+ ",createBy:" + createBy
		+ ",updateTime:" + updateTime
		+ ",updateBy:" + updateBy
		+ ",isDeleted:" + isDeleted
		+ ",isDisabled:" + isDisabled
		+ ",version:" + version
		+ ",pwrOrg:" + pwrOrg
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
