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

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author sjk
 * 
 */
public class StaLineCommand extends StaLine {
    /**
	 * 
	 */
    private static final long serialVersionUID = 3219253605057674798L;
    /**
     * sku id private Long id;
     */


    private Date createTime;

    private String code;

    private String slipCode;
    /**
     * sku jmcode
     */
    private String jmcode;
    /**
     * sku 扩展属性
     */
    private String keyProperties;
    /**
     * Sku中code
     */
    private String jmskuCode;


    private String supplierCode;
    /**
     * sku中barCode
     */
    private String barCode;
    /**
     * 库区
     */
    private String district;
    /**
     * 库位
     */
    private String location;

    private String skuCode;

    private String status;

    /**
	 * 
	 */
    private String intInvstatusName;

    private Long invInvstatusId;

    private Long receiptQty;

    private Integer intStatus;
    /**
     * 是否SN号商品
     */
    private Integer isSnSku = 0;

    private String batchCode;

    private String sn;

    private Integer direction;

    private Integer storeMode;

    private Long invStatusId;

    private String po;

    private String poreference;

    private String staCode;

    private String staSlipCode;

    private String lpcode;

    private Integer pgIndex;

    private String trackingNo;

    private String trackingNo1;

    private String receiver;

    private Boolean isGift;

    private Boolean skuRfid;

    private WarehouseLocation warehouseLocation;

    private String giftMemo;

    private Integer warrantyCardType;

    private String extensionCode3;
    private BigDecimal amt;

    private Integer index;
    private Long skuId;
    private String extCode1;
    private String extCode2;
    private Long categoriesId;

    private String isSku;
    private String isStore;
    private Long staId;
    private Integer interfaceType;
    private Integer snType;
    private Integer spType;
    private String startNum;
    private String stopNum;
    private String snCheckMode;

    /**
     * CHANNELNAME
     */
    private String channelName;
    /**
     * 推荐耗材条码
     */
    private String packageBarCode;

    private String isBkcheckString;

    private Long customerId;

    private Integer pickType;

    private Integer isPostpositionPackingPage;

    private Integer isPostpositionExpressBill;

    private Long plid;

    /**
     * 占用批创建失败（超过5次的不做处理,IT手工处理）
     */
    private Integer ocpErrorCreate;


    private Long weight;

    private Integer deliveryType;

    private Integer packageCount;

    private Boolean isHaveOccupation;

    private String mName;// 门店名称

    private String mCode;// 门店编码

    private String ruleCode;

    private Integer sumQty; // 数量
    
    private String  skuSize;//尺码
    

    private Integer effectSku;

    private Boolean isSn;

    public Integer getEffectSku() {
        return effectSku;
    }

    public void setEffectSku(Integer effectSku) {
        this.effectSku = effectSku;
    }

    public Boolean getIsSn() {
        return isSn;
    }

    public void setIsSn(Boolean isSn) {
        this.isSn = isSn;
    }

    public String getRuleCode() {
        return ruleCode;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }

    public Integer getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(Integer deliveryType) {
        this.deliveryType = deliveryType;
    }

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmCode() {
        return mCode;
    }

    public void setmCode(String mCode) {
        this.mCode = mCode;
    }

    public Integer getPackageCount() {
        return packageCount;
    }

    public void setPackageCount(Integer packageCount) {
        this.packageCount = packageCount;
    }

    public Long getPlid() {
        return plid;
    }

    public void setPlid(Long plid) {
        this.plid = plid;
    }

    public Integer getIsPostpositionPackingPage() {
        return isPostpositionPackingPage;
    }

    public void setIsPostpositionPackingPage(Integer isPostpositionPackingPage) {
        this.isPostpositionPackingPage = isPostpositionPackingPage;
    }

    public Integer getIsPostpositionExpressBill() {
        return isPostpositionExpressBill;
    }

    public void setIsPostpositionExpressBill(Integer isPostpositionExpressBill) {
        this.isPostpositionExpressBill = isPostpositionExpressBill;
    }

    public Integer getPickType() {
        return pickType;
    }

    public void setPickType(Integer pickType) {
        this.pickType = pickType;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getIsBkcheckString() {
        return isBkcheckString;
    }

    public void setIsBkcheckString(String isBkcheckString) {
        this.isBkcheckString = isBkcheckString;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    public Integer getPgIndex() {
        return pgIndex;
    }

    public void setPgIndex(Integer pgIndex) {
        this.pgIndex = pgIndex;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getLpcode() {
        return lpcode;
    }

    public Integer getIntStatus() {
        return intStatus;
    }

    public void setIntStatus(Integer intStatus) {
        this.intStatus = intStatus;
    }

    public void setLpcode(String lpcode) {
        this.lpcode = lpcode;
    }

    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

    public String getStaSlipCode() {
        return staSlipCode;
    }

    public void setStaSlipCode(String staSlipCode) {
        this.staSlipCode = staSlipCode;
    }

    public WarehouseLocation getWarehouseLocation() {
        return warehouseLocation;
    }

    public void setWarehouseLocation(WarehouseLocation warehouseLocation) {
        this.warehouseLocation = warehouseLocation;
    }

    public String getJmskuCode() {
        return jmskuCode;
    }

    public void setJmskuCode(String jmskuCode) {
        this.jmskuCode = jmskuCode;
    }


    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getJmcode() {
        return jmcode;
    }

    public void setJmcode(String jmcode) {
        this.jmcode = jmcode;
    }

    public String getKeyProperties() {
        return keyProperties;
    }

    public void setKeyProperties(String keyProperties) {
        this.keyProperties = keyProperties;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getIsSnSku() {
        return isSnSku;
    }

    public void setIsSnSku(Integer isSnSku) {
        this.isSnSku = isSnSku;
    }

    public String getSlipCode() {
        return slipCode;
    }

    public void setSlipCode(String slipCode) {
        this.slipCode = slipCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getIntInvstatusName() {
        return intInvstatusName;
    }

    public void setIntInvstatusName(String intInvstatusName) {
        this.intInvstatusName = intInvstatusName;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public Integer getStoreMode() {
        return storeMode;
    }

    public void setStoreMode(Integer storeMode) {
        this.storeMode = storeMode;
    }

    public Long getInvStatusId() {
        return invStatusId;
    }

    public void setInvStatusId(Long invStatusId) {
        this.invStatusId = invStatusId;
    }

    public String getPo() {
        return po;
    }

    public void setPo(String po) {
        this.po = po;
    }

    public String getPoreference() {
        return poreference;
    }

    public void setPoreference(String poreference) {
        this.poreference = poreference;
    }

    public Long getInvInvstatusId() {
        return invInvstatusId;
    }

    public void setInvInvstatusId(Long invInvstatusId) {
        this.invInvstatusId = invInvstatusId;
    }

    public String getExtensionCode3() {
        return extensionCode3;
    }

    public void setExtensionCode3(String extensionCode3) {
        this.extensionCode3 = extensionCode3;
    }

    public String getTrackingNo1() {
        return trackingNo1;
    }

    public void setTrackingNo1(String trackingNo1) {
        this.trackingNo1 = trackingNo1;
    }

    public Long getReceiptQty() {
        return receiptQty;
    }

    public void setReceiptQty(Long receiptQty) {
        this.receiptQty = receiptQty;
    }

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    public Boolean getIsGift() {
        return isGift;
    }

    public void setIsGift(Boolean isGift) {
        this.isGift = isGift;
    }

    public Integer getWarrantyCardType() {
        return warrantyCardType;
    }

    public void setWarrantyCardType(Integer warrantyCardType) {
        this.warrantyCardType = warrantyCardType;
    }

    public String getGiftMemo() {
        return giftMemo;
    }

    public void setGiftMemo(String giftMemo) {
        this.giftMemo = giftMemo;
    }


    public String getExtCode1() {
        return extCode1;
    }

    public void setExtCode1(String extCode1) {
        this.extCode1 = extCode1;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getIsSku() {
        return isSku;
    }

    public void setIsSku(String isSku) {
        this.isSku = isSku;
    }

    public String getIsStore() {
        return isStore;
    }

    public void setIsStore(String isStore) {
        this.isStore = isStore;
    }

    public Long getCategoriesId() {
        return categoriesId;
    }

    public void setCategoriesId(Long categoriesId) {
        this.categoriesId = categoriesId;
    }

    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

    public String getPackageBarCode() {
        return packageBarCode;
    }

    public void setPackageBarCode(String packageBarCode) {
        this.packageBarCode = packageBarCode;
    }

    public Integer getInterfaceType() {
        return interfaceType;
    }

    public void setInterfaceType(Integer interfaceType) {
        this.interfaceType = interfaceType;
    }

    public Integer getSnType() {
        return snType;
    }

    public void setSnType(Integer snType) {
        this.snType = snType;
    }

    public Integer getSpType() {
        return spType;
    }

    public void setSpType(Integer spType) {
        this.spType = spType;
    }

    public String getStartNum() {
        return startNum;
    }

    public void setStartNum(String startNum) {
        this.startNum = startNum;
    }

    public String getStopNum() {
        return stopNum;
    }

    public void setStopNum(String stopNum) {
        this.stopNum = stopNum;
    }

    public String getSnCheckMode() {
        return snCheckMode;
    }

    public void setSnCheckMode(String snCheckMode) {
        this.snCheckMode = snCheckMode;
    }

    public Integer getOcpErrorCreate() {
        return ocpErrorCreate;
    }

    public void setOcpErrorCreate(Integer ocpErrorCreate) {
        this.ocpErrorCreate = ocpErrorCreate;
    }

    public String getExtCode2() {
        return extCode2;
    }

    public void setExtCode2(String extCode2) {
        this.extCode2 = extCode2;
    }

    public Boolean getIsHaveOccupation() {
        return isHaveOccupation;
    }

    public void setIsHaveOccupation(Boolean isHaveOccupation) {
        this.isHaveOccupation = isHaveOccupation;
    }

    public Integer getSumQty() {
        return sumQty;
    }

    public void setSumQty(Integer sumQty) {
        this.sumQty = sumQty;
    }

    public String getSkuSize() {
        return skuSize;
    }

    public void setSkuSize(String skuSize) {
        this.skuSize = skuSize;
    }

    public Boolean getSkuRfid() {
        return skuRfid;
    }

    public void setSkuRfid(Boolean skuRfid) {
        this.skuRfid = skuRfid;
    }

}
