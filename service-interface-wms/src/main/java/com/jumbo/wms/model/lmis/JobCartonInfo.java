package com.jumbo.wms.model.lmis;

import java.util.Date;

/**
 * @author chen.huang
 */
public class JobCartonInfo {

    /**
     * 数据来源
     */
    private String datasource;

    /**
     *
     */
    private Integer upperId;

    /**
     * 店铺编码
     */
    private String storeId;

    /**
     * 店铺名称
     */
    private String storeName;

    /**
     * 仓库编码
     */
    private String whsId;

    /**
     * 仓库名称
     */
    private String whsName;

    /**
     * 作业单号
     */
    private String jobOrder;

    /**
     * 作业类型
     */
    private String jobType;

    /**
     * 单据状态
     */
    private Integer jobStatus;

    /**
     * 作业单创建时间
     */
    private Date jobCreateTime;

    /**
     * 废纸箱耗材SKU编码
     */
    private String cartonId;

    /**
     * 废纸箱数量
     */
    private Integer cartonNum;

    public String getDatasource() {
        return datasource;
    }

    public void setDatasource(String datasource) {
        this.datasource = datasource;
    }

    public Integer getUpperId() {
        return upperId;
    }

    public void setUpperId(Integer upperId) {
        this.upperId = upperId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getWhsId() {
        return whsId;
    }

    public void setWhsId(String whsId) {
        this.whsId = whsId;
    }

    public String getWhsName() {
        return whsName;
    }

    public void setWhsName(String whsName) {
        this.whsName = whsName;
    }

    public String getJobOrder() {
        return jobOrder;
    }

    public void setJobOrder(String jobOrder) {
        this.jobOrder = jobOrder;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public Integer getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(Integer jobStatus) {
        this.jobStatus = jobStatus;
    }

    public Date getJobCreateTime() {
        return jobCreateTime;
    }

    public void setJobCreateTime(Date jobCreateTime) {
        this.jobCreateTime = jobCreateTime;
    }

    public String getCartonId() {
        return cartonId;
    }

    public void setCartonId(String cartonId) {
        this.cartonId = cartonId;
    }

    public Integer getCartonNum() {
        return cartonNum;
    }

    public void setCartonNum(Integer cartonNum) {
        this.cartonNum = cartonNum;
    }
}
