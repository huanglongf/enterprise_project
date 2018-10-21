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

package com.jumbo.wms.model.warehouse;

import java.util.Date;

import com.jumbo.util.StringUtils;

public class StockTransTxLogCommand extends StockTransTxLog {

    private static final long serialVersionUID = 6125796236516439734L;
    private Long statusId;
    private Long whId;
    private Date tranTime;
    private String isMixTime;
    
    private Integer staType;

    private String whouCode;
    /**
     * 入库数量
     */
    private Long inQty;

    /**
     * 出库数量
     */
    private Long outQty;

    /**
     * 事务类型
     */
    private String transactionTypeName;

    /**
     * Sku code
     */
    private Long jmSkuCode;
    /**
     * jmcode
     */
    private String jmCode;
    /**
     * sku编码
     */
    private String skuCode;

    /**
     * 货号
     */
    private String supplierCode;
    /**
     * 事务类型id
     */
    private Long transactionTypeid;
    /**
     * 条形码
     */
    private String barCode;
    /**
     * 商品名称
     */
    private String skuName;
    /**
     * 库存状态
     */
    private String invStatus;
    /**
     * 操作员
     */
    private String operator;
    /**
     * 扩展属性
     */
    private String keyProperties;

    /**
     * 库区
     */
    private String districtCode;

    /**
     * 库位
     */
    private String locationCode;

    /**
     * 盘点表CODE
     */
    private String inventoryCheckCode;

    /**
     * 作业单起始时间
     */
    private Date stockStartTime;
    /**
     * 作业单截至时间
     */
    private Date stockEndTime;
    /**
     * 货号
     */
    private String supplierSkuCode;

    private String productCategory;

    private String productLine;

    private String consumerGroup;

    private String inseam;

    private String productDescription;

    private String warehouseName;

    private String refSlipCode;
    /**
     * 增加String字段，辅助完成查询时间精确化
     */
    private String stockStartTime1;
    private String stockEndTime1;

    /**
     * 商品大小 分为 大件、中件、小件
     */
    private String productSize;
    /**
     * 事务方向
     */
    private Integer intDirection;
    /**
     * 店铺组织ID
     */
    private Long shopOuId;

    /**
     * 是否可销售
     */
    private Integer marketAbility;

    /**
     * 相关单号
     * 
     * @return
     */
    private String extensionCode2;
    private Date staInboundTime;
    private Date staOutboundTime;
    private String storeCode;


    public Date getStaInboundTime() {
        return staInboundTime;
    }

    public void setStaInboundTime(Date staInboundTime) {
        this.staInboundTime = staInboundTime;
    }

    public Date getStaOutboundTime() {
        return staOutboundTime;
    }

    public void setStaOutboundTime(Date staOutboundTime) {
        this.staOutboundTime = staOutboundTime;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getExtensionCode2() {
        return extensionCode2;
    }

    public void setExtensionCode2(String extensionCode2) {
        this.extensionCode2 = extensionCode2;
    }

    public String getRefSlipCode() {
        return refSlipCode;
    }

    public Date getTranTime() {
        return tranTime;
    }

    public void setTranTime(Date tranTime) {
        this.tranTime = tranTime;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public Long getWhId() {
        return whId;
    }

    public void setWhId(Long whId) {
        this.whId = whId;
    }

    public void setRefSlipCode(String refSlipCode) {
        this.refSlipCode = refSlipCode;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public Date getStockStartTime() {
        return stockStartTime;
    }

    public void setStockStartTime(Date stockStartTime) {
        this.stockStartTime = stockStartTime;
    }

    public Date getStockEndTime() {
        return stockEndTime;
    }

    public void setStockEndTime(Date stockEndTime) {
        this.stockEndTime = stockEndTime;
    }

    public String getTransactionTypeName() {
        return transactionTypeName;
    }

    public void setTransactionTypeName(String transactionTypeName) {
        this.transactionTypeName = transactionTypeName;
    }

    public Long getJmSkuCode() {
        return jmSkuCode;
    }

    public void setJmSkuCode(Long jmSkuCode) {
        this.jmSkuCode = jmSkuCode;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getJmCode() {
        return jmCode;
    }

    public void setJmCode(String jmCode) {
        this.jmCode = jmCode;
    }

    public String getKeyProperties() {
        return keyProperties;
    }

    public void setKeyProperties(String keyProperties) {
        this.keyProperties = keyProperties;
    }

    public Long getInQty() {
        return inQty;
    }

    public void setInQty(Long inQty) {
        this.inQty = inQty;
    }

    public Long getOutQty() {
        return outQty;
    }

    public void setOutQty(Long outQty) {
        this.outQty = outQty;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getInvStatus() {
        return invStatus;
    }

    public void setInvStatus(String invStatus) {
        this.invStatus = invStatus;
    }

    public String getInventoryCheckCode() {
        return inventoryCheckCode;
    }

    public void setInventoryCheckCode(String inventoryCheckCode) {
        this.inventoryCheckCode = inventoryCheckCode;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public Long getTransactionTypeid() {
        return transactionTypeid;
    }

    public void setTransactionTypeid(Long transactionTypeid) {
        this.transactionTypeid = transactionTypeid;
    }

    /**
     * @return the skuCode
     */
    public String getSkuCode() {
        return skuCode;
    }

    /**
     * @param skuCode the skuCode to set
     */
    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public void setQueryLikeParam() {
        if (this.getStockStartTime() == null) {
            this.setStockStartTime(null);
        }
        if (this.getStockEndTime() == null) {
            this.setStockEndTime(null);
        }
        if (this.getTransactionTypeid() == null) {
            this.setTransactionTypeid(null);
        }
        if (StringUtils.hasText(this.getStaCode())) {
            this.setStaCode(this.getStaCode() + "%");
        } else {
            this.setStaCode(null);
        }
        if (StringUtils.hasText(this.getDistrictCode())) {
            this.setDistrictCode(districtCode + "%");
        } else {
            this.setDistrictCode(null);
        }
        if (StringUtils.hasText(this.getInventoryCheckCode())) {
            this.setInventoryCheckCode(this.getInventoryCheckCode() + "%");
        } else {
            this.setInventoryCheckCode(null);
        }
        if (StringUtils.hasText(this.getLocationCode())) {
            this.setLocationCode(locationCode + "%");
        } else {
            this.setLocationCode(null);
        }
        if (StringUtils.hasText(this.getBarCode())) {
            this.setBarCode(barCode + "%");
        } else {
            this.setBarCode(null);
        }

        if (StringUtils.hasText(this.getSkuCode())) {
            this.setSkuCode(skuCode + "%");
        } else {
            this.setSkuCode(null);
        }

        if (StringUtils.hasText(this.getJmCode())) {
            this.setJmCode(jmCode + "%");
        } else {
            this.setJmCode(null);
        }
        if (StringUtils.hasText(this.getSkuName())) {
            this.setSkuName(skuName + "%");
        } else {
            this.setSkuName(null);
        }
        if (StringUtils.hasText(this.getOperator())) {
            this.setOperator("%" + operator + "%");
        } else {
            this.setOperator(null);
        }
        if (!StringUtils.hasText(this.getOwner())) {
            this.setOwner(null);
        }
        if (StringUtils.hasText(this.getRefSlipCode())) {
            this.setRefSlipCode(refSlipCode + "%");
        } else {
            this.setRefSlipCode(null);
        }
        if (StringUtils.hasText(this.getSupplierCode())) {
            this.setSupplierCode(supplierCode + "%");
        } else {
            this.setSupplierCode(null);
        }
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public String getConsumerGroup() {
        return consumerGroup;
    }

    public void setConsumerGroup(String consumerGroup) {
        this.consumerGroup = consumerGroup;
    }

    public String getInseam() {
        return inseam;
    }

    public void setInseam(String inseam) {
        this.inseam = inseam;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getSupplierSkuCode() {
        return supplierSkuCode;
    }

    public void setSupplierSkuCode(String supplierSkuCode) {
        this.supplierSkuCode = supplierSkuCode;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getStockStartTime1() {
        return stockStartTime1;
    }

    public void setStockStartTime1(String stockStartTime1) {
        this.stockStartTime1 = stockStartTime1;
    }

    public String getStockEndTime1() {
        return stockEndTime1;
    }

    public void setStockEndTime1(String stockEndTime1) {
        this.stockEndTime1 = stockEndTime1;
    }

    public String getProductSize() {
        return productSize;
    }

    public void setProductSize(String productSize) {
        this.productSize = productSize;
    }

    public Integer getIntDirection() {
        return intDirection;
    }

    public void setIntDirection(Integer intDirection) {
        this.intDirection = intDirection;
    }

    public Long getShopOuId() {
        return shopOuId;
    }

    public void setShopOuId(Long shopOuId) {
        this.shopOuId = shopOuId;
    }

    public String getWhouCode() {
        return whouCode;
    }

    public void setWhouCode(String whouCode) {
        this.whouCode = whouCode;
    }

    public Integer getMarketAbility() {
        return marketAbility;
    }

    public void setMarketAbility(Integer marketAbility) {
        this.marketAbility = marketAbility;
    }

    public String getIsMixTime() {
        return isMixTime;
    }

    public void setIsMixTime(String isMixTime) {
        this.isMixTime = isMixTime;
    }

    public Integer getStaType() {
        return staType;
    }

    public void setStaType(Integer staType) {
        this.staType = staType;
    }
    

}
