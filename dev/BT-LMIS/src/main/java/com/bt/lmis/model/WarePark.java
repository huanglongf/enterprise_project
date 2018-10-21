package com.bt.lmis.model;

import java.util.Date;

public class WarePark {
    private String id;

    private String parkCode;

    private String parkName;

    private String parkCostCenter;

    private String showStReFlag;

    private String showByCpFlag;
    
    private String wareFlag;
    
    private String disFlag;

    private String remark;

    private Date createTime;

    private String createUser;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getParkCode() {
        return parkCode;
    }

    public void setParkCode(String parkCode) {
        this.parkCode = parkCode == null ? null : parkCode.trim();
    }

    public String getParkName() {
        return parkName;
    }

    public void setParkName(String parkName) {
        this.parkName = parkName == null ? null : parkName.trim();
    }

    public String getParkCostCenter() {
        return parkCostCenter;
    }

    public void setParkCostCenter(String parkCostCenter) {
        this.parkCostCenter = parkCostCenter == null ? null : parkCostCenter.trim();
    }

    public String getShowStReFlag() {
        return showStReFlag;
    }

    public void setShowStReFlag(String showStReFlag) {
        this.showStReFlag = showStReFlag == null ? null : showStReFlag.trim();
    }

    public String getShowByCpFlag() {
        return showByCpFlag;
    }

    public void setShowByCpFlag(String showByCpFlag) {
        this.showByCpFlag = showByCpFlag == null ? null : showByCpFlag.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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
        this.createUser = createUser == null ? null : createUser.trim();
    }

	public String getWareFlag() {
		return wareFlag;
	}

	public void setWareFlag(String wareFlag) {
		this.wareFlag = wareFlag;
	}

	public String getDisFlag() {
		return disFlag;
	}

	public void setDisFlag(String disFlag) {
		this.disFlag = disFlag;
	}
}