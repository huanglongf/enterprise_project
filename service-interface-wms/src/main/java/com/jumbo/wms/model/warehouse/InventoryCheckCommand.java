package com.jumbo.wms.model.warehouse;

import java.util.Date;

import com.jumbo.util.StringUtils;

/**
 * 库存盘点
 * 
 * @author sjk
 * 
 */

public class InventoryCheckCommand extends InventoryCheck {

    private static final long serialVersionUID = -44152452379862424L;

    private Date startDate;
    private Date endDate;

    private Date startFinishTime;

    private Date endFinishTime;

    private String creatorName;

    private Integer intInvCheckStatus;
    private String owner;
    /*
     * 修改时间精确到时分秒添加辅助字段
     */
    private String startDate1;
    private String endDate1;
    private String startFinishTime1;
    private String endFinishTime1;
    private Date maxTransactionTime;
    private String createTimeStr;

    public Date getMaxTransactionTime() {
        return maxTransactionTime;
    }

    public void setMaxTransactionTime(Date maxTransactionTime) {
        this.maxTransactionTime = maxTransactionTime;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public void setQueryLikeParam() {
        if (this.getStartDate() == null) {
            this.setStartDate(null);
        }
        if (this.getEndDate() == null) {
            this.setEndDate(null);
        }
        if (this.getIntStatus() == null) {
            this.setIntStatus(null);
        }
        if (StringUtils.hasText(this.getCreatorName())) {
            this.setCreatorName("%" + this.creatorName + "%");
        } else {
            this.setCreatorName(null);
        }
        if (StringUtils.hasText(this.getConfirmUser())) {
            this.setConfirmUser("%" + this.getConfirmUser() + "%");
        } else {
            this.setConfirmUser(null);
        }
        if (StringUtils.hasText(this.getCode())) {
            this.setCode(this.getCode() + "%");
        } else {
            this.setCode(null);
        }
    }

    public Date getStartFinishTime() {
        return startFinishTime;
    }

    public void setStartFinishTime(Date startFinishTime) {
        this.startFinishTime = startFinishTime;
    }

    public Date getEndFinishTime() {
        return endFinishTime;
    }

    public void setEndFinishTime(Date endFinishTime) {
        this.endFinishTime = endFinishTime;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Integer getIntInvCheckStatus() {
        return intInvCheckStatus;
    }

    public void setIntInvCheckStatus(Integer intInvCheckStatus) {
        this.intInvCheckStatus = intInvCheckStatus;
    }

    public String getStartDate1() {
        return startDate1;
    }

    public void setStartDate1(String startDate1) {
        this.startDate1 = startDate1;
    }

    public String getEndDate1() {
        return endDate1;
    }

    public void setEndDate1(String endDate1) {
        this.endDate1 = endDate1;
    }

    public String getStartFinishTime1() {
        return startFinishTime1;
    }

    public void setStartFinishTime1(String startFinishTime1) {
        this.startFinishTime1 = startFinishTime1;
    }

    public String getEndFinishTime1() {
        return endFinishTime1;
    }

    public void setEndFinishTime1(String endFinishTime1) {
        this.endFinishTime1 = endFinishTime1;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

}
