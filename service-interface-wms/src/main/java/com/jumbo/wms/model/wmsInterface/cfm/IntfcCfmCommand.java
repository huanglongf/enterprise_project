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
import java.util.List;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;

/**
 * 通用接口反馈头
 * 
 * @author hui.li
 *
 */
public class IntfcCfmCommand extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 6374253684163088352L;

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
     * 是否完成
     */
    private Integer isFinished;

    /**
     * 明细行
     */
    private List<IntfcLineCfmCommand> ilcList;
    
    /**
     * 发票
     */
    private List<IntfcInvoiceCfmCommand> invoiceList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getWmsCode() {
        return wmsCode;
    }

    public void setWmsCode(String wmsCode) {
        this.wmsCode = wmsCode;
    }

    public String getExtCode() {
        return extCode;
    }

    public void setExtCode(String extCode) {
        this.extCode = extCode;
    }

    public String getEcOrderCode() {
        return ecOrderCode;
    }

    public void setEcOrderCode(String ecOrderCode) {
        this.ecOrderCode = ecOrderCode;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public StockTransApplicationStatus getWmsStatus() {
        return wmsStatus;
    }

    public void setWmsStatus(StockTransApplicationStatus wmsStatus) {
        this.wmsStatus = wmsStatus;
    }

    public StockTransApplicationType getWmsType() {
        return wmsType;
    }

    public void setWmsType(StockTransApplicationType wmsType) {
        this.wmsType = wmsType;
    }

    public String getExtType() {
        return extType;
    }

    public void setExtType(String extType) {
        this.extType = extType;
    }

    public String getWhCode() {
        return whCode;
    }

    public void setWhCode(String whCode) {
        this.whCode = whCode;
    }

    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
    }

    public Integer getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(Integer transactionType) {
        this.transactionType = transactionType;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public Long getPlanQty() {
        return planQty;
    }

    public void setPlanQty(Long planQty) {
        this.planQty = planQty;
    }

    public Long getActualQty() {
        return actualQty;
    }

    public void setActualQty(Long actualQty) {
        this.actualQty = actualQty;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getExtMemo() {
        return extMemo;
    }

    public void setExtMemo(String extMemo) {
        this.extMemo = extMemo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Long getStvId() {
        return stvId;
    }

    public void setStvId(Long stvId) {
        this.stvId = stvId;
    }

    public List<IntfcLineCfmCommand> getIlcList() {
        return ilcList;
    }

    public void setIlcList(List<IntfcLineCfmCommand> ilcList) {
        this.ilcList = ilcList;
    }

    public List<IntfcInvoiceCfmCommand> getInvoiceList() {
        return invoiceList;
    }

    public void setInvoiceList(List<IntfcInvoiceCfmCommand> invoiceList) {
        this.invoiceList = invoiceList;
    }

    public Integer getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(Integer isFinished) {
        this.isFinished = isFinished;
    }



}
