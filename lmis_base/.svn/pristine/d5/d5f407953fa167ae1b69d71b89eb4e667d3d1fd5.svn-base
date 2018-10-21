package com.lmis.setup.page.model;

import java.util.Date;

import com.lmis.framework.baseModel.PersistentObject;

import io.swagger.annotations.ApiModelProperty;

/** 
 * @ClassName: SetupPage
 * @Description: TODO(配置页面实体)
 * @author Ian.Huang
 * @date 2017年12月28日 下午3:41:55 
 * 
 */
public class SetupPage extends PersistentObject {

	/** 
	 * @Fields serialVersionUID : TODO(序列号) 
	 */
	private static final long serialVersionUID = -2783767099281774133L;

	@ApiModelProperty(value = "页面ID")
	private String pageId;

	@ApiModelProperty(value = "页面名称")
	private String pageName;

	@ApiModelProperty(value = "宽度")
	private String pageWidth;

	@ApiModelProperty(value = "高度")
	private String pageHeight;

	@ApiModelProperty(value = "页面类型")
	private String pageType;

	@ApiModelProperty(value = "所属页面")
	private String parentPageId;

	@ApiModelProperty(value = "页面URL")
	private String pageUrl;
	
	public SetupPage() {}
	
	public SetupPage(String id, Boolean isDeleted, String pageId) {
		super.setId(id);
		super.setIsDeleted(isDeleted);
		this.pageId = pageId;
	}
	
	public SetupPage(String id, Date createTime, String createBy, Date updateTime, String updateBy,
			Boolean isDeleted, Boolean isDisabled, Integer version, String pwrOrg, String pageId, String pageName) {
		super(id, createTime, createBy, updateTime, updateBy, isDeleted, isDisabled, version, pwrOrg);
		this.pageId = pageId;
		this.pageName = pageName;
	}
	
	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public String getPageWidth() {
		return pageWidth;
	}

	public void setPageWidth(String pageWidth) {
		this.pageWidth = pageWidth;
	}

	public String getPageHeight() {
		return pageHeight;
	}

	public void setPageHeight(String pageHeight) {
		this.pageHeight = pageHeight;
	}

	public String getPageType() {
		return pageType;
	}

	public void setPageType(String pageType) {
		this.pageType = pageType;
	}

	public String getParentPageId() {
		return parentPageId;
	}

	public void setParentPageId(String parentPageId) {
		this.parentPageId = parentPageId;
	}
	
	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
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
		+ ",pageId:" + pageId
		+ ",pageName:" + pageName
		+ ",pageWidth:" + pageWidth
		+ ",pageHeight:" + pageHeight
		+ ",pageType:" + pageType
		+ ",parentPageId:" + parentPageId
		+ ",pageUrl:" + pageUrl
		+"}";
	}
}
