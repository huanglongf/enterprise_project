package com.bt.workOrder.model;

import java.io.Serializable;
import java.util.Date;

public class WoProcessRecord implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

    private Date createTime;

    private String createBy;

    private Date updateTime;

    private String updateBy;

    private String woStoreLogId;
    
    private String processRemark;

    private String accessory;
    
    private String woTypeRe;

    public String getProcessRemark() {
        return processRemark;
    }

    public void setProcessRemark(String processRemark) {
        this.processRemark = processRemark == null ? null : processRemark.trim();
    }

    public String getAccessory() {
        return accessory;
    }

    public void setAccessory(String accessory) {
        this.accessory = accessory == null ? null : accessory.trim();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
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
        this.createBy = createBy == null ? null : createBy.trim();
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
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public String getWoStoreLogId() {
        return woStoreLogId;
    }

    public void setWoStoreLogId(String woStoreLogId) {
        this.woStoreLogId = woStoreLogId == null ? null : woStoreLogId.trim();
    }

	public String getWoTypeRe() {
		return woTypeRe;
	}

	public void setWoTypeRe(String woTypeRe) {
		this.woTypeRe = woTypeRe;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
}