package com.lmis.sys.functionButton.model;

import java.util.List;

import com.lmis.framework.baseModel.PersistentObject;

import io.swagger.annotations.ApiModelProperty;

/** 
 * @ClassName: SysFunctionButton
 * @Description: TODO(功能菜单表)
 * @author codeGenerator
 * @date 2018-01-09 13:09:08
 * 
 */
public class SysFunctionButton extends PersistentObject {

    /** 
	 * @Fields serialVersionUID : TODO(序列号) 
	 */
	private static final long serialVersionUID = 1L;
	
    public static long getSerialversionuid() {
    	return serialVersionUID;	
    }
    
    @ApiModelProperty(value = "功能菜单/按钮ID")
	private String fbId;
	public String getFbId() {
		return fbId;
	}
	public void setFbId(String fbId) {
		this.fbId = fbId;
	}
	
    @ApiModelProperty(value = "功能菜单/按钮名称")
	private String fbName;
	public String getFbName() {
		return fbName;
	}
	public void setFbName(String fbName) {
		this.fbName = fbName;
	}
	
    @ApiModelProperty(value = "上级菜单ID")
	private String fbPid;
	public String getFbPid() {
		return fbPid;
	}
	public void setFbPid(String fbPid) {
		this.fbPid = fbPid;
	}
	
    @ApiModelProperty(value = "菜单按钮级别")
	private String fbLv;
	public String getFbLv() {
		return fbLv;
	}
	public void setFbLv(String fbLv) {
		this.fbLv = fbLv;
	}
	
    @ApiModelProperty(value = "类型：0菜单/1按钮")
	private String fbType;
	public String getFbType() {
		return fbType;
	}
	public void setFbType(String fbType) {
		this.fbType = fbType;
	}
	
    @ApiModelProperty(value = "显示顺序")
	private Integer fbSeq;
	public Integer getFbSeq() {
		return fbSeq;
	}
	public void setFbSeq(Integer fbSeq) {
		this.fbSeq = fbSeq;
	}
	
    @ApiModelProperty(value = "访问路径")
	private String fbUrl;
	public String getFbUrl() {
		return fbUrl;
	}
	public void setFbUrl(String fbUrl) {
		this.fbUrl = fbUrl;
	}
	
    @ApiModelProperty(value = "图片名称")
	private String fbGifId;
	public String getFbGifId() {
		return fbGifId;
	}
	public void setFbGifId(String fbGifId) {
		this.fbGifId = fbGifId;
	}
	
    @ApiModelProperty(value = "对应页面")
	private String fbPage;
	public String getFbPage() {
		return fbPage;
	}
	public void setFbPage(String fbPage) {
		this.fbPage = fbPage;
	}
	
	@ApiModelProperty(value = "子结构")
	private List<SysFunctionButton> childList;
	public List<SysFunctionButton> getChildList() {
		return childList;
	}
	public void setChildList(List<SysFunctionButton> childList) {
		this.childList = childList;
	}
	
	@ApiModelProperty(value = "用户ID")
	private String userId;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

}