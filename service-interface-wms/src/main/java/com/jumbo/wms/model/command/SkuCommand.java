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

package com.jumbo.wms.model.command;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.jumbo.util.StringUtils;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.SkuSalesModel;

/**
 * SKU
 * 
 * @author benjamin
 * 
 */
public class SkuCommand extends Sku {
    /**
	 * 
	 */
    private static final long serialVersionUID = 2960655395582098457L;

    private Long skuCid;// ad
    private Long cuId;// 客户id
    private String barCode2;// ad
    private String jmCode2;

    private String auName;

    private Long skuId;

    private String owner;

    private Long ouId;

    private String ownerCode;

    private String vmiCode;


    /**
     * 库存状态
     */
    private String invStatus;

    /**
     * 计划库存量
     */
    private Long planQuantity;

    private String brandName;

    private String productCategory;

    private String productLine;

    private String consumerGroup;

    private String inseam;

    private String productDescription;

    private BigDecimal skuCost;

    private String skuCategoriesName;

    private Integer storeModeValue;

    private Integer warrantyCardTypeInt;

    private Integer storemodeInt;
    private Integer deliveryTypeInt;
    private Integer isSnSkuInt;
    private Integer IsGiftInt;

    // mq
    /**
     * 供应商ID
     */
    private Long supperlierId;

    /**
     * 品牌ID
     */
    private String brandCode;

    /**
     * 颜色描述
     */
    private String colorDesc;

    /**
     * 尺码
     */
    private String size;

    /**
     * 季节相关属性
     */
    private String seasonJSON;

    /**
     * 区
     */
    private String division;



    private String railwayStr;
    /**
     * 成本价
     */
    private BigDecimal fob;
    /**
     * 箱号编码
     */
    private String cartonCode;

    private Date skuVersion;

    private Integer salesModel; // 商品销售模式(经销Constants.SKU_SALES_MODEL_0,
    // 代销Constants.SKU_SALES_MODEL_1)
    private Integer quantity;

    private String kemu; // 库存科目代码

    private String kemuName; // 库存科目名称

    private String supplierName; // 供应商名称

    private Integer lifeCycleStatus;// 商品状态

    private Date createTime;// 创建时间

    private String seasonYear;

    private String seasonCode;
    /**
     * 商品净重（kg）
     */
    private BigDecimal netWeight;
    /**
     * 库区
     */
    private String districtCode;
    /**
     * sku补货安全警戒线
     */
    private BigDecimal warningPre;

    private Boolean isNewSku; // 是否为新创建sku标识

    /**
     * 动态属性1
     */
    private String dpProp1;

    /**
     * 动态属性2
     */
    private String dpProp2;

    /**
     * 动态属性3
     */
    private String dpProp3;

    /**
     * 动态属性4
     */
    private String dpProp4;

    /**
     * 动态属性5
     */
    private String dpProp5;

    /**
     * 动态属性6
     */
    private String dpProp6;

    /**
     * 动态属性7
     */
    private String dpProp7;

    /**
     * 动态属性8
     */
    private String dpProp8;

    /**
     * 动态属性9
     */
    private String dpProp9;

    /**
     * 动态属性10
     */
    private String dpProp10;

    /**
     * 商品类型
     */
    private String typeStr;

    private BigDecimal platformUnitPrice;

    /**
     * 用户订购数量
     */
    private Long requestedQty;
    /**
     * 商品分类名称
     */
    private String categoryName;
    /**
     * 是否陆运
     */
    private Boolean isRail;

    private String source;
    /**
     * 所属店铺 非共享仓补货时
     */
    private String shopOwner;

    /**
     * 商品大小 分为 大件、中件、小件
     */
    private String productSize;
    /**
     * 销售可用量
     */
    private Long salesQty;

    /**
     * 保修卡类型
     */
    private Integer intWarrantyCardType;

    private String remark1;

    private String customerName;

    private Long qty;
    private Long completeQty;
    /**
     * 店铺Id Edw
     */
    private Integer brandId;
    /**
     * 更新时间 Edw
     */
    private Date updateTime;
    private Integer customerId;
    /**
     * 箱型条码
     */
    private String packageBarCode;
    /**
     * 箱型名称
     */
    private String packageSkuName;

    private Integer snCheckModeInteger;

    /**
     * 商品类型名称
     */
    private String skuTypeName;

    private Long skuTypeId;



    /**
     * 是否可销售
     */
    private Integer marketAbility;

    private BigDecimal orderTotalBfDiscount;

    private String isConsumable;

    private String staCode;

    private String slipCode;

    private String slipCode1;

    private String staType;

    private String pinkingListId;

    private Integer isSendMsg;

    public Long getPlanQuantity() {
        return planQuantity;
    }

    public void setPlanQuantity(Long planQuantity) {
        this.planQuantity = planQuantity;
    }

    public String getInvStatus() {
        return invStatus;
    }

    public void setInvStatus(String invStatus) {
        this.invStatus = invStatus;
    }


    public String getJmCode2() {
        return jmCode2;
    }

    public void setJmCode2(String jmCode2) {
        this.jmCode2 = jmCode2;
    }

    public Long getCuId() {
        return cuId;
    }

    public void setCuId(Long cuId) {
        this.cuId = cuId;
    }

    public String getBarCode2() {
        return barCode2;
    }

    public void setBarCode2(String barCode2) {
        this.barCode2 = barCode2;
    }

    public Long getSkuCid() {
        return skuCid;
    }

    public void setSkuCid(Long skuCid) {
        this.skuCid = skuCid;
    }


    public String getIsConsumable() {
        return isConsumable;
    }

    public void setIsConsumable(String isConsumable) {
        this.isConsumable = isConsumable;
    }

    public BigDecimal getOrderTotalBfDiscount() {
        return orderTotalBfDiscount;
    }

    public void setOrderTotalBfDiscount(BigDecimal orderTotalBfDiscount) {
        this.orderTotalBfDiscount = orderTotalBfDiscount;
    }

    public Integer getSnCheckModeInteger() {
        return snCheckModeInteger;
    }

    public void setSnCheckModeInteger(Integer snCheckModeInteger) {
        this.snCheckModeInteger = snCheckModeInteger;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSeasonJSON() {
        return seasonJSON;
    }

    public void setSeasonJSON(String seasonJSON) {
        this.seasonJSON = seasonJSON;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Map<String, Object> getSkuInfoMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.hasText(this.getPackageBarCode())) {
            map.put("packageBarCode", this.getPackageBarCode() + "%");
        }
        if (StringUtils.hasText(this.getCode())) {
            map.put("code", this.getCode() + "%");
        }
        if (StringUtils.hasText(this.getJmCode())) {
            map.put("jmCode", this.getJmCode() + "%");
        }
        if (StringUtils.hasText(this.getBarCode())) {
            map.put("barCode", this.getBarCode() + "%");
        }
        if (StringUtils.hasText(this.getSupplierCode())) {
            map.put("supplierCode", this.getSupplierCode() + "%");
        }
        if (StringUtils.hasText(this.getKeyProperties())) {
            map.put("keyProperties", "%" + this.getKeyProperties() + "%");
        }
        if (StringUtils.hasText(this.getName())) {
            map.put("name", this.getName() + "%");
        }
        if (StringUtils.hasText(this.getBrandName())) {
            map.put("brandName", this.getBrandName() + "%");
        }
        if (this.getSupperlierId() != null) {
            map.put("supperlierId", this.getSupperlierId());
        }
        if (this.getSupplierName() != null) {
            map.put("supplierName", "%" + this.getSupplierName() + "%");
        }
        if (this.getLifeCycleStatus() != null) {
            map.put("lifeCycleStatus", this.getLifeCycleStatus());
        }
        if (this.getSalesModel() != null) {
            map.put("lifeCycleStatus", this.getSalesModel());
        }
        if (StringUtils.hasText(this.getKemu())) {
            map.put("kemu", this.getKemu() + "%");
        }
        if (this.isNewSku != null) {
            map.put("isNewSku", this.getIsNewSku().booleanValue() == true ? 1 : 0);
        }
        if (this.salesModel != null) {
            map.put("salesModel", this.getSalesModel());
        }

        if (StringUtils.hasText(this.getSkuTypeName())) {
            map.put("skuTypeName", this.getSkuTypeName() + "%");
        }
        if (StringUtils.hasText(this.getIsConsumable())) {
            map.put("isConsumable", this.getIsConsumable());
        }
        return map;
    }

    public Map<String, Object> getSkuInfoMapIsGift() {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.hasText(this.getCode())) {
            map.put("code", this.getCode() + "%");
        }
        if (StringUtils.hasText(this.getJmCode())) {
            map.put("jmCode", this.getJmCode() + "%");
        }
        if (StringUtils.hasText(this.getBarCode())) {
            map.put("barCode", this.getBarCode() + "%");
        }
        if (StringUtils.hasText(this.getSupplierCode())) {
            map.put("supplierCode", "%" + this.getSupplierCode() + "%");
        }
        if (StringUtils.hasText(this.getKeyProperties())) {
            map.put("keyProperties", "%" + this.getKeyProperties() + "%");
        }
        if (StringUtils.hasText(this.getName())) {
            map.put("name", this.getName() + "%");
        }
        if (StringUtils.hasText(this.getBrandName())) {
            map.put("brandName", this.getBrandName() + "%");
        }
        if (this.getSupperlierId() != null) {
            map.put("supperlierId", this.getSupperlierId());
        }
        if (this.getSupplierName() != null) {
            map.put("supplierName", "%" + this.getSupplierName() + "%");
        }
        if (this.getLifeCycleStatus() != null) {
            map.put("lifeCycleStatus", this.getLifeCycleStatus());
        }
        if (this.getSalesModel() != null) {
            map.put("lifeCycleStatus", this.getSalesModel());
        }
        if (StringUtils.hasText(this.getKemu())) {
            map.put("kemu", this.getKemu() + "%");
        }
        if (this.isNewSku != null) {
            map.put("isNewSku", this.getIsNewSku().booleanValue() == true ? 1 : 0);
        }
        if (this.salesModel != null) {
            map.put("salesModel", this.getSalesModel());
        }
        if (this.getIsGift() != null) {
            map.put("isGift", this.getIsGift());
        }

        return map;
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

    public Long getSupperlierId() {
        return supperlierId;
    }

    public void setSupperlierId(Long supperlierId) {
        this.supperlierId = supperlierId;
    }

    public String getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public BigDecimal getFob() {
        return fob;
    }

    public void setFob(BigDecimal fob) {
        this.fob = fob;
    }

    public Date getSkuVersion() {
        return skuVersion;
    }

    public void setSkuVersion(Date skuVersion) {
        this.skuVersion = skuVersion;
    }

    public Integer getSalesModel() {
        return salesModel;
    }

    public SkuSalesModel getSkuSalesModel() {
        if (salesModel == null) {
            return null;
        }
        return SkuSalesModel.valueOf(getSalesModel());
    }

    public void setSalesModel(Integer salesModel) {
        this.salesModel = salesModel;
    }

    public String getCartonCode() {
        return cartonCode;
    }

    public void setCartonCode(String cartonCode) {
        this.cartonCode = cartonCode;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getKemu() {
        return kemu;
    }

    public void setKemu(String kemu) {
        this.kemu = kemu;
    }

    public String getKemuName() {
        return kemuName;
    }

    public void setKemuName(String kemuName) {
        this.kemuName = kemuName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getLifeCycleStatus() {
        return lifeCycleStatus;
    }

    public void setLifeCycleStatus(Integer lifeCycleStatus) {
        this.lifeCycleStatus = lifeCycleStatus;
    }

    public BigDecimal getWarningPre() {
        return warningPre;
    }

    public void setWarningPre(BigDecimal warningPre) {
        this.warningPre = warningPre;
    }

    public Boolean getIsNewSku() {
        return isNewSku;
    }

    public void setIsNewSku(Boolean isNewSku) {
        this.isNewSku = isNewSku;
    }

    public BigDecimal getSkuCost() {
        return skuCost;
    }

    public void setSkuCost(BigDecimal skuCost) {
        this.skuCost = skuCost;
    }

    public String getSeasonYear() {
        return seasonYear;
    }

    public void setSeasonYear(String seasonYear) {
        this.seasonYear = seasonYear;
    }

    public String getSeasonCode() {
        return seasonCode;
    }

    public void setSeasonCode(String seasonCode) {
        this.seasonCode = seasonCode;
    }

    public String getDpProp1() {
        return dpProp1;
    }

    public void setDpProp1(String dpProp1) {
        this.dpProp1 = dpProp1;
    }

    public String getDpProp2() {
        return dpProp2;
    }

    public void setDpProp2(String dpProp2) {
        this.dpProp2 = dpProp2;
    }

    public String getDpProp3() {
        return dpProp3;
    }

    public void setDpProp3(String dpProp3) {
        this.dpProp3 = dpProp3;
    }

    public String getDpProp4() {
        return dpProp4;
    }

    public void setDpProp4(String dpProp4) {
        this.dpProp4 = dpProp4;
    }

    public String getDpProp5() {
        return dpProp5;
    }

    public void setDpProp5(String dpProp5) {
        this.dpProp5 = dpProp5;
    }

    public String getDpProp6() {
        return dpProp6;
    }

    public void setDpProp6(String dpProp6) {
        this.dpProp6 = dpProp6;
    }

    public String getDpProp7() {
        return dpProp7;
    }

    public void setDpProp7(String dpProp7) {
        this.dpProp7 = dpProp7;
    }

    public String getDpProp8() {
        return dpProp8;
    }

    public void setDpProp8(String dpProp8) {
        this.dpProp8 = dpProp8;
    }

    public String getDpProp9() {
        return dpProp9;
    }

    public void setDpProp9(String dpProp9) {
        this.dpProp9 = dpProp9;
    }

    public String getDpProp10() {
        return dpProp10;
    }

    public void setDpProp10(String dpProp10) {
        this.dpProp10 = dpProp10;
    }

    public String getColorDesc() {
        return colorDesc;
    }

    public void setColorDesc(String colorDesc) {
        this.colorDesc = colorDesc;
    }

    public BigDecimal getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(BigDecimal netWeight) {
        this.netWeight = netWeight;
    }


    public String getTypeStr() {
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    public BigDecimal getPlatformUnitPrice() {
        return platformUnitPrice;
    }

    public void setPlatformUnitPrice(BigDecimal platformUnitPrice) {
        this.platformUnitPrice = platformUnitPrice;
    }

    public Long getRequestedQty() {
        return requestedQty;
    }

    public void setRequestedQty(Long requestedQty) {
        this.requestedQty = requestedQty;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Boolean getIsRail() {
        return isRail;
    }

    public void setIsRail(Boolean isRail) {
        this.isRail = isRail;
    }

    public String getShopOwner() {
        return shopOwner;
    }

    public void setShopOwner(String shopOwner) {
        this.shopOwner = shopOwner;
    }

    public String getProductSize() {
        return productSize;
    }

    public void setProductSize(String productSize) {
        this.productSize = productSize;
    }

    public Long getSalesQty() {
        return salesQty;
    }

    public void setSalesQty(Long salesQty) {
        this.salesQty = salesQty;
    }


    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

    public Integer getIntWarrantyCardType() {
        return intWarrantyCardType;
    }

    public void setIntWarrantyCardType(Integer intWarrantyCardType) {
        this.intWarrantyCardType = intWarrantyCardType;
    }

    public String getSkuCategoriesName() {
        return skuCategoriesName;
    }

    public void setSkuCategoriesName(String skuCategoriesName) {
        this.skuCategoriesName = skuCategoriesName;
    }

    public String getRailwayStr() {
        return railwayStr;
    }

    public void setRailwayStr(String railwayStr) {
        this.railwayStr = railwayStr;
    }

    public Integer getStoreModeValue() {
        return storeModeValue;
    }

    public void setStoreModeValue(Integer storeModeValue) {
        this.storeModeValue = storeModeValue;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public Long getCompleteQty() {
        return completeQty;
    }

    public void setCompleteQty(Long completeQty) {
        this.completeQty = completeQty;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getWarrantyCardTypeInt() {
        return warrantyCardTypeInt;
    }

    public void setWarrantyCardTypeInt(Integer warrantyCardTypeInt) {
        this.warrantyCardTypeInt = warrantyCardTypeInt;
    }

    public Integer getStoremodeInt() {
        return storemodeInt;
    }

    public void setStoremodeInt(Integer storemodeInt) {
        this.storemodeInt = storemodeInt;
    }

    public Integer getDeliveryTypeInt() {
        return deliveryTypeInt;
    }

    public void setDeliveryTypeInt(Integer deliveryTypeInt) {
        this.deliveryTypeInt = deliveryTypeInt;
    }

    public Integer getIsSnSkuInt() {
        return isSnSkuInt;
    }

    public void setIsSnSkuInt(Integer isSnSkuInt) {
        this.isSnSkuInt = isSnSkuInt;
    }

    public Integer getIsGiftInt() {
        return IsGiftInt;
    }

    public void setIsGiftInt(Integer isGiftInt) {
        IsGiftInt = isGiftInt;
    }

    public String getPackageBarCode() {
        return packageBarCode;
    }

    public void setPackageBarCode(String packageBarCode) {
        this.packageBarCode = packageBarCode;
    }

    public String getPackageSkuName() {
        return packageSkuName;
    }

    public void setPackageSkuName(String packageSkuName) {
        this.packageSkuName = packageSkuName;
    }

    public Integer getMarketAbility() {
        return marketAbility;
    }

    public void setMarketAbility(Integer marketAbility) {
        this.marketAbility = marketAbility;
    }

    public String getSkuTypeName() {
        return skuTypeName;
    }

    public void setSkuTypeName(String skuTypeName) {
        this.skuTypeName = skuTypeName;
    }

    public Long getSkuTypeId() {
        return skuTypeId;
    }

    public void setSkuTypeId(Long skuTypeId) {
        this.skuTypeId = skuTypeId;
    }

    public String getAuName() {
        return auName;
    }

    public void setAuName(String auName) {
        this.auName = auName;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Long getOuId() {
        return ouId;
    }

    public void setOuId(Long ouId) {
        this.ouId = ouId;
    }

    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    public String getVmiCode() {
        return vmiCode;
    }

    public void setVmiCode(String vmiCode) {
        this.vmiCode = vmiCode;
    }

    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

    public String getSlipCode() {
        return slipCode;
    }

    public void setSlipCode(String slipCode) {
        this.slipCode = slipCode;
    }

    public String getSlipCode1() {
        return slipCode1;
    }

    public void setSlipCode1(String slipCode1) {
        this.slipCode1 = slipCode1;
    }

    public String getStaType() {
        return staType;
    }

    public void setStaType(String staType) {
        this.staType = staType;
    }

    public String getPinkingListId() {
        return pinkingListId;
    }

    public void setPinkingListId(String pinkingListId) {
        this.pinkingListId = pinkingListId;
    }

    public Integer getIsSendMsg() {
        return isSendMsg;
    }

    public void setIsSendMsg(Integer isSendMsg) {
        this.isSendMsg = isSendMsg;
    }



}
