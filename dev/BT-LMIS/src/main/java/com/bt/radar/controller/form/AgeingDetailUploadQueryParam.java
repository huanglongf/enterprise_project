package com.bt.radar.controller.form;

import java.util.Date;

import com.bt.lmis.page.QueryParameter;

public class AgeingDetailUploadQueryParam extends QueryParameter{
    private Integer id;

    private String ageingId;

    private String batId;

    private Integer flag;
    
    private Date createTime;
    
    private String createUser;
    private String fileName;
    
    private Date updateTime;
    
    private String updateUser;

	private Integer errorNumber;
    
    private Integer totalNumber;
    
    

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Integer getErrorNumber() {
		return errorNumber;
	}

	public void setErrorNumber(Integer errorNumber) {
		this.errorNumber = errorNumber;
	}

	public Integer getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(Integer totalNumber) {
		this.totalNumber = totalNumber;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAgeingId() {
        return ageingId;
    }

    public void setAgeingId(String ageingId) {
        this.ageingId = ageingId == null ? null : ageingId.trim();
    }

    public String getBatId() {
        return batId;
    }

    public void setBatId(String batId) {
        this.batId = batId == null ? null : batId.trim();
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}