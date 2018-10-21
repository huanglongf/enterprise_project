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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

import com.jumbo.wms.model.BaseModel;

/**
 * 通用接口反馈明细表
 * 
 * @author bin.hu
 *
 */
@Entity
@Table(name = "T_WH_INTFC_LINE_CFM")
public class IntfcLineCfm extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = -2154449982941611424L;

    /**
     * PK
     */
    private Long id;

    /**
     * 反馈头表ID
     */
    private IntfcCfm intfcId;

    /**
     * 商品唯一编码
     */
    private String upc;

    /**
     * 店铺CODE
     */
    private String storeCode;

    /**
     * 外部单据行号
     */
    private Integer extLineNum;

    /**
     * 款式
     */
    private String skuStyle;

    /**
     * 颜色
     */
    private String skuColor;

    /**
     * 尺码
     */
    private String skuSize;

    /**
     * 库存状态
     */
    private String invStatus;

    /**
     * 批次
     */
    private String batchNumber;

    /**
     * 计划数量
     */
    private Long planQty;

    /**
     * 实际数量
     */
    private Long actualQty = 0L;

    /**
     * 箱号
     */
    private String cartonNo;

    /**
     * 出库箱号
     */
    private String outboundBoxCode;

    /**
     * 运输服务商
     */
    private String transportServiceProvider;

    /**
     * 快递单号
     */
    private String trackingNumber;

    /**
     * 外部单据行号Str
     */
    private String extLineNo;

    /**
     * 备注
     */
    private String extMemo;



    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_WH_INTFC_LINE_CFM", sequenceName = "S_T_WH_INTFC_LINE_CFM", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WH_INTFC_LINE_CFM")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INTFC_ID")
    @Index(name = "FK_T_WH_INTFC_LINE_CFM")
    public IntfcCfm getIntfcId() {
        return intfcId;
    }

    public void setIntfcId(IntfcCfm intfcId) {
        this.intfcId = intfcId;
    }

    @Column(name = "UPC")
    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    @Column(name = "STORE_CODE")
    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    @Column(name = "EXT_LINE_NUM")
    public Integer getExtLineNum() {
        return extLineNum;
    }

    public void setExtLineNum(Integer extLineNum) {
        this.extLineNum = extLineNum;
    }

    @Column(name = "SKU_STYLE")
    public String getSkuStyle() {
        return skuStyle;
    }

    public void setSkuStyle(String skuStyle) {
        this.skuStyle = skuStyle;
    }

    @Column(name = "SKU_COLOR")
    public String getSkuColor() {
        return skuColor;
    }

    public void setSkuColor(String skuColor) {
        this.skuColor = skuColor;
    }

    @Column(name = "SKU_SIZE")
    public String getSkuSize() {
        return skuSize;
    }

    public void setSkuSize(String skuSize) {
        this.skuSize = skuSize;
    }

    @Column(name = "INV_STATUS")
    public String getInvStatus() {
        return invStatus;
    }

    public void setInvStatus(String invStatus) {
        this.invStatus = invStatus;
    }

    @Column(name = "BATCH_NUMBER")
    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
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

    @Column(name = "CARTON_NO")
    public String getCartonNo() {
        return cartonNo;
    }

    public void setCartonNo(String cartonNo) {
        this.cartonNo = cartonNo;
    }

    @Column(name = "OUTBOUND_BOX_CODE")
    public String getOutboundBoxCode() {
        return outboundBoxCode;
    }

    public void setOutboundBoxCode(String outboundBoxCode) {
        this.outboundBoxCode = outboundBoxCode;
    }

    @Column(name = "TRANSPORT_SERVICE_PROVIDER")
    public String getTransportServiceProvider() {
        return transportServiceProvider;
    }

    public void setTransportServiceProvider(String transportServiceProvider) {
        this.transportServiceProvider = transportServiceProvider;
    }

    @Column(name = "TRACKING_NUMBER")
    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    @Column(name = "EXT_LINE_NO")
    public String getExtLineNo() {
        return extLineNo;
    }

    public void setExtLineNo(String extLineNo) {
        this.extLineNo = extLineNo;
    }

    @Column(name = "EXT_MEMO")
    public String getExtMemo() {
        return extMemo;
    }

    public void setExtMemo(String extMemo) {
        this.extMemo = extMemo;
    }



}
