package com.lmis.setup.page.model;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

import io.swagger.annotations.ApiModelProperty;

/** 
 * @ClassName: ViewSetupPage
 * @Description: TODO(配置页面视图实体)
 * @author Ian.Huang
 * @date 2018年1月2日 下午5:51:14 
 * 
 */
public class ViewSetupPage {
	
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

	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
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
