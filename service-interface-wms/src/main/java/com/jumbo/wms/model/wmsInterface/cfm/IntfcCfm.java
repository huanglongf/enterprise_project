/**
 * Copyright (c) 2010 Jumbomart All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Jumbomart. You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jumbo.
 * 
 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 * 
 */

package com.jumbo.wms.model.wmsInterface.cfm;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;

/**
 * 通用接口反馈头表
 * 
 * @author bin.hu
 *
 */
@Entity
@Table(name = "T_WH_INTFC_CFM")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class IntfcCfm extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 4957278750858309547L;
    /**
     * PK
     */
    private Long id;

    /**
     * 数据唯一标识
     */
    private String uuid;

    /**
     * WMS单据号
     */
    private String wmsCode;

    /**
     * 上位系统单据号
     */
    private String extCode;

    /**
     * 电商平台订单号
     */
    private String ecOrderCode;

    /**
     * 店铺CODE
     */
    private String storeCode;

    /**
     * WMS单据状态
     */
    private StockTransApplicationStatus wmsStatus;

    /**
     * WMS单据类型
     */
    private StockTransApplicationType wmsType;

    /**
     * 上位系统单据类型
     */
    private String extType;

    /**
     * 仓库编码
     */
    private String whCode;

    /**
     * 事务时间（入库时间&出库时间）
     */
    private Date transactionTime;

    /**
     * 事务类型0-入库 1-出库
     */
    private Integer transactionType;

    /**
     * 来源地
     */
    private String fromLocation;

    /**
     * 目的地
     */
    private String toLocation;

    /**
     * 计划数量
     */
    private Long planQty;

    /**
     * 实际数量
     */
    private Long actualQty = 0L;

    /**
     * 数据来源 区分上位系统
     */
    private String dataSource;

    /**
     * 扩展字段信息
     */
    private String extMemo;

    /**
     * 数据创建时间
     */
    private Date createTime = new Date();

    /**
     * 状态1-新建 2-同步成功 3-同步失败
     */
    private Integer status;

    private int version;

    /**
     * stvId 用来去重
     */
    private Long stvId;

    /**
     * 错误次数
     */
    private Integer errorCount;

    /**
     * 错误信息
     */
    private String errorMsg;

    /**
     * 是否完成
     */
    private Integer isFinished;


    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_WH_INTFC_CFM", sequenceName = "S_T_WH_INTFC_CFM", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WH_INTFC_CFM")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "UUID")
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Column(name = "WMS_CODE")
    public String getWmsCode() {
        return wmsCode;
    }

    public void setWmsCode(String wmsCode) {
        this.wmsCode = wmsCode;
    }

    @Column(name = "EXT_CODE")
    public String getExtCode() {
        return extCode;
    }

    public void setExtCode(String extCode) {
        this.extCode = extCode;
    }

    @Column(name = "EC_ORDER_CODE")
    public String getEcOrderCode() {
        return ecOrderCode;
    }

    public void setEcOrderCode(String ecOrderCode) {
        this.ecOrderCode = ecOrderCode;
    }

    @Column(name = "STORE_CODE")
    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    @Column(name = "WMS_STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.StockTransApplicationStatus")})
    public StockTransApplicationStatus getWmsStatus() {
        return wmsStatus;
    }

    public void setWmsStatus(StockTransApplicationStatus wmsStatus) {
        this.wmsStatus = wmsStatus;
    }

    @Column(name = "WMS_TYPE", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.StockTransApplicationType")})
    public StockTransApplicationType getWmsType() {
        return wmsType;
    }

    public void setWmsType(StockTransApplicationType wmsType) {
        this.wmsType = wmsType;
    }

    @Column(name = "EXT_TYPE")
    public String getExtType() {
        return extType;
    }

    public void setExtType(String extType) {
        this.extType = extType;
    }

    @Column(name = "WH_CODE")
    public String getWhCode() {
        return whCode;
    }

    public void setWhCode(String whCode) {
        this.whCode = whCode;
    }

    @Column(name = "TRANSACTION_TIME")
    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
    }

    @Column(name = "TRANSACTION_TYPE")
    public Integer getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(Integer transactionType) {
        this.transactionType = transactionType;
    }

    @Column(name = "FROM_LOCATION")
    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    @Column(name = "TO_LOCATION")
    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    @Column(name = "PLAN_QTY")
    public Long getPlanQty() {
        return planQty;
    }

    public void setPlanQty(Long planQty) {
        this.planQty = planQty;
    }

    @Column(name = "ACTUAL_QTY")
    public Long getActualQty() {
        return actualQty;
    }

    public void setActualQty(Long actualQty) {
        this.actualQty = actualQty;
    }

    @Column(name = "DATA_SOURCE")
    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    @Column(name = "EXT_MEMO")
    public String getExtMemo() {
        return extMemo;
    }

    public void setExtMemo(String extMemo) {
        this.extMemo = extMemo;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "STATUS")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Version
    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Column(name = "STV_ID")
    public Long getStvId() {
        return stvId;
    }

    public void setStvId(Long stvId) {
        this.stvId = stvId;
    }

    @Column(name = "ERROR_COUNT")
    public Integer getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Integer errorCount) {
        this.errorCount = errorCount;
    }

    @Column(name = "ERROR_MSG")
    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Column(name = "IS_FINISHED")
    public Integer getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(Integer isFinished) {
        this.isFinished = isFinished;
    }



}
