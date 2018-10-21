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

import com.jumbo.wms.model.BaseModel;

/**
 * 通用接口反馈明细
 * 
 * @author hui.li
 *
 */
public class IntfcLineCfmCommand extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = -1917521257304981835L;

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
    
    private String skuCode;
    
    private String extCode1;
    
    private String extCode3;
    
    private String barCode;

    /**
     * 外部单据行号Str
     */
    private String extLineNo;

    /**
     * 备注
     */
    private String extMemo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public IntfcCfm getIntfcId() {
        return intfcId;
    }

    public void setIntfcId(IntfcCfm intfcId) {
        this.intfcId = intfcId;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public Integer getExtLineNum() {
        return extLineNum;
    }

    public void setExtLineNum(Integer extLineNum) {
        this.extLineNum = extLineNum;
    }

    public String getSkuStyle() {
        return skuStyle;
    }

    public void setSkuStyle(String skuStyle) {
        this.skuStyle = skuStyle;
    }

    public String getSkuColor() {
        return skuColor;
    }

    public void setSkuColor(String skuColor) {
        this.skuColor = skuColor;
    }

    public String getSkuSize() {
        return skuSize;
    }

    public void setSkuSize(String skuSize) {
        this.skuSize = skuSize;
    }

    public String getInvStatus() {
        return invStatus;
    }

    public void setInvStatus(String invStatus) {
        this.invStatus = invStatus;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
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

    public String getCartonNo() {
        return cartonNo;
    }

    public void setCartonNo(String cartonNo) {
        this.cartonNo = cartonNo;
    }

    public String getOutboundBoxCode() {
        return outboundBoxCode;
    }

    public void setOutboundBoxCode(String outboundBoxCode) {
        this.outboundBoxCode = outboundBoxCode;
    }

    public String getTransportServiceProvider() {
        return transportServiceProvider;
    }

    public void setTransportServiceProvider(String transportServiceProvider) {
        this.transportServiceProvider = transportServiceProvider;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public String getExtCode1() {
		return extCode1;
	}

	public void setExtCode1(String extCode1) {
		this.extCode1 = extCode1;
	}

	public String getExtCode3() {
		return extCode3;
	}

	public void setExtCode3(String extCode3) {
		this.extCode3 = extCode3;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

    public String getExtLineNo() {
        return extLineNo;
    }

    public void setExtLineNo(String extLineNo) {
        this.extLineNo = extLineNo;
    }

    public String getExtMemo() {
        return extMemo;
    }

    public void setExtMemo(String extMemo) {
        this.extMemo = extMemo;
    }



}
