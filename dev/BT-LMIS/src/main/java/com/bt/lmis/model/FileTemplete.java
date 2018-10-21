package com.bt.lmis.model;

import java.util.Date;

public class FileTemplete {
    private String id;

    private String templeteName;

    private String businessType;

    private String templeteType;
    
    private String businessGroup;

    private String templeteTitle;
    
    private String templeteMode;
    
    private String templeteModeUrl;
    
    private Integer titleLength;

    private String uploadUrl;

    private String historyVersion;

    private String remark;

    private String delFlag;

    private Date createTime;

    private Date updateTime;

    private String createBy;

    private String updateBy;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTempleteName() {
        return templeteName;
    }

    public void setTempleteName(String templeteName) {
        this.templeteName = templeteName == null ? null : templeteName.trim();
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType == null ? null : businessType.trim();
    }

    public String getTempleteType() {
        return templeteType;
    }

    public void setTempleteType(String templeteType) {
        this.templeteType = templeteType == null ? null : templeteType.trim();
    }

    public String getTempleteTitle() {
        return templeteTitle;
    }

    public void setTempleteTitle(String templeteTitle) {
        this.templeteTitle = templeteTitle == null ? null : templeteTitle.trim();
    }

    public String getUploadUrl() {
        return uploadUrl;
    }

    public void setUploadUrl(String uploadUrl) {
        this.uploadUrl = uploadUrl == null ? null : uploadUrl.trim();
    }

    public String getHistoryVersion() {
        return historyVersion;
    }

    public void setHistoryVersion(String historyVersion) {
        this.historyVersion = historyVersion == null ? null : historyVersion.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag == null ? null : delFlag.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

	public String getBusinessGroup() {
		return businessGroup;
	}

	public void setBusinessGroup(String businessGroup) {
		this.businessGroup = businessGroup == null ? null : businessGroup.trim();
	}

	public Integer getTitleLength() {
		return titleLength;
	}

	public void setTitleLength(Integer titleLength) {
		this.titleLength = titleLength;
	}

	public String getTempleteMode() {
		return templeteMode;
	}

	public void setTempleteMode(String templeteMode) {
		this.templeteMode = templeteMode == null ? null : templeteMode.trim();
	}

	public String getTempleteModeUrl() {
		return templeteModeUrl;
	}

	public void setTempleteModeUrl(String templeteModeUrl) {
		this.templeteModeUrl = templeteModeUrl == null ? null : templeteModeUrl.trim();
	}
}