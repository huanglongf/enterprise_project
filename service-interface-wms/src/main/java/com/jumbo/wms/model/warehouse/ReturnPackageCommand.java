package com.jumbo.wms.model.warehouse;

import java.util.Date;

public class ReturnPackageCommand extends ReturnPackage {

    /**
     * 
     */
    private static final long serialVersionUID = -3959902751706903144L;

    private Integer intStatus;

    private String strStatus;

    private String createDateStr;

    private String staCode;

    private Date endCreateTime;

    private String returnApplicationCode;

    private String staSlipCode;

    private String userName;

    private String warehouseName;

    private String physicalWarehouse;

    private String unlockUser;

    private String whName;



    /**
     * 登记类型
     */
    private String type;
    /**
     * 物理仓库ID
     */
    private Long phyWhId;

    public String getWhName() {
        return whName;
    }

    public void setWhName(String whName) {
        this.whName = whName;
    }
    public Integer getIntStatus() {
        return intStatus;
    }

    public void setIntStatus(Integer intStatus) {
        this.intStatus = intStatus;
    }

    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

    public Date getEndCreateTime() {
        return endCreateTime;
    }

    public void setEndCreateTime(Date endCreateTime) {
        this.endCreateTime = endCreateTime;
    }

    public String getReturnApplicationCode() {
        return returnApplicationCode;
    }

    public void setReturnApplicationCode(String returnApplicationCode) {
        this.returnApplicationCode = returnApplicationCode;
    }

    public String getStaSlipCode() {
        return staSlipCode;
    }

    public void setStaSlipCode(String staSlipCode) {
        this.staSlipCode = staSlipCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getPhysicalWarehouse() {
        return physicalWarehouse;
    }

    public void setPhysicalWarehouse(String physicalWarehouse) {
        this.physicalWarehouse = physicalWarehouse;
    }

    public String getStrStatus() {
        return strStatus;
    }

    public void setStrStatus(String strStatus) {
        this.strStatus = strStatus;
    }

    public String getCreateDateStr() {
        return createDateStr;
    }

    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }

    public Long getPhyWhId() {
        return phyWhId;
    }

    public void setPhyWhId(Long phyWhId) {
        this.phyWhId = phyWhId;
    }

    public String getUnlockUser() {
        return unlockUser;
    }

    public void setUnlockUser(String unlockUser) {
        this.unlockUser = unlockUser;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



}
