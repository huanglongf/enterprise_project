package com.lmis.setup.pageLayout.model;

import java.util.Date;

import com.lmis.framework.baseModel.PersistentObject;

import io.swagger.annotations.ApiModelProperty;

/** 
 * @ClassName: SetupPageLayout
 * @Description: TODO(页面布局)
 * @author Ian.Huang
 * @date 2017年12月5日 下午7:50:19 
 * 
 */
public class SetupPageLayout extends PersistentObject {

	/** 
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	 */
	private static final long serialVersionUID = -3657955403614598096L;
	
	@ApiModelProperty(value = "布局ID，新增/修改/删除/查询对应业务信息时必输")
	private String layoutId;
	
	@ApiModelProperty(value = "所属页面，新增/修改/展示页面布局时必输")
	private String pageId;
	
	@ApiModelProperty(value = "布局名称，新增/修改时必输")
	private String layoutName;
	
	@ApiModelProperty(value = "显示顺序")
	private Integer layoutSeq;
	
	@ApiModelProperty(value = "宽度")
	private String layoutWidth;
	
	@ApiModelProperty(value = "高度")
	private String layoutHeight;
	
	@ApiModelProperty(value = "布局类型：原始布局/容器布局，新增/修改时必输")
	private String layoutType;
	
	@ApiModelProperty(value = "上级布局ID，layout_type=容器布局时启用")
	private String parentLayoutId;
	
	@ApiModelProperty(value = "配置按钮")
	private Boolean buttonFlag1;
	
	@ApiModelProperty(value = "导出按钮")
	private Boolean buttonFlag2;
	
	@ApiModelProperty(value = "导入按钮")
	private Boolean buttonFlag3;
	
	@ApiModelProperty(value = "按钮控制4")
	private Boolean buttonFlag4;
	
	@ApiModelProperty(value = "按钮控制5")
	private Boolean buttonFlag5;

    @ApiModelProperty(value = "操作类型，preview or normal")
	private String operationType;

	public SetupPageLayout() {}
	
	public SetupPageLayout(String id, Boolean isDeleted, String layoutId) {
		super.setId(id);
		super.setIsDeleted(isDeleted);
		this.layoutId = layoutId;
	}
	
	public SetupPageLayout(String id, Date createTime, String createBy, Date updateTime, String updateBy,
			Boolean isDeleted, Boolean isDisabled, Integer version, String pwrOrg, String layoutId,
			String pageId, String layoutName, String layoutType) {
		super(id, createTime, createBy, updateTime, updateBy, isDeleted, isDisabled, version, pwrOrg);
		this.layoutId = layoutId;
		this.pageId = pageId;
		this.layoutName = layoutName;
		this.layoutType = layoutType;
	}
	
	public String getLayoutId() {
		return layoutId;
	}

	public void setLayoutId(String layoutId) {
		this.layoutId = layoutId;
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getLayoutName() {
		return layoutName;
	}

	public void setLayoutName(String layoutName) {
		this.layoutName = layoutName;
	}

	public Integer getLayoutSeq() {
		return layoutSeq;
	}

	public void setLayoutSeq(Integer layoutSeq) {
		this.layoutSeq = layoutSeq;
	}

	public String getLayoutWidth() {
		return layoutWidth;
	}

	public void setLayoutWidth(String layoutWidth) {
		this.layoutWidth = layoutWidth;
	}

	public String getLayoutHeight() {
		return layoutHeight;
	}

	public void setLayoutHeight(String layoutHeight) {
		this.layoutHeight = layoutHeight;
	}

	public String getLayoutType() {
		return layoutType;
	}

	public void setLayoutType(String layoutType) {
		this.layoutType = layoutType;
	}

	public String getParentLayoutId() {
		return parentLayoutId;
	}

	public void setParentLayoutId(String parentLayoutId) {
		this.parentLayoutId = parentLayoutId;
	}

	public Boolean getButtonFlag1() {
		return buttonFlag1;
	}

	public void setButtonFlag1(Boolean buttonFlag1) {
		this.buttonFlag1 = buttonFlag1;
	}

	public Boolean getButtonFlag2() {
		return buttonFlag2;
	}

	public void setButtonFlag2(Boolean buttonFlag2) {
		this.buttonFlag2 = buttonFlag2;
	}

	public Boolean getButtonFlag3() {
		return buttonFlag3;
	}

	public void setButtonFlag3(Boolean buttonFlag3) {
		this.buttonFlag3 = buttonFlag3;
	}

	public Boolean getButtonFlag4() {
		return buttonFlag4;
	}

	public void setButtonFlag4(Boolean buttonFlag4) {
		this.buttonFlag4 = buttonFlag4;
	}

	public Boolean getButtonFlag5() {
		return buttonFlag5;
	}

	public void setButtonFlag5(Boolean buttonFlag5) {
		this.buttonFlag5 = buttonFlag5;
	}

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public static long getSerialversionuid() {
		return serialVersionUID;
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
		+ ",layoutId:" + layoutId
		+ ",pageId:" + pageId
		+ ",layoutName:" + layoutName
		+ ",layoutSeq:" + layoutSeq
		+ ",layoutWidth:" + layoutWidth
		+ ",layoutHeight:" + layoutHeight
		+ ",layoutType:" + layoutType
		+ ",parentLayoutId:" + parentLayoutId
		+ ",buttonFlag1:" + buttonFlag1
		+ ",buttonFlag2:" + buttonFlag2
		+ ",buttonFlag3:" + buttonFlag3
		+ ",buttonFlag4:" + buttonFlag4
		+ ",buttonFlag5:" + buttonFlag5
		+"}";
	}

}
