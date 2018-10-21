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
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jumbo.util.StringUtils;

/**
 * @author jumbo
 * 
 */
public class InventoryCommand extends Inventory {

    private static final long serialVersionUID = 2352134450599613212L;

    public String customerCode;


    /**
     * 店铺名称
     */
    private String shopName;

    private Long rowm;


    private Long staId;
    /**
     * PK
     */
    private String skuIdOwner;
    /**
     * sku id
     */
    private Long skuId;
    /**
     * 可用库存量
     */
    private Long availQty;
    /**
     * 库存成本
     */
    private BigDecimal skuCost;
    /**
     * 销售可用量
     */
    private Long salesAvailQty;
    /**
     * 是否显示库存为0记录
     */
    private Boolean isShowZero;
    /**
     * 锁定量
     */
    private Long lockQty;
    /**
     * 占用库存量
     */
    private Long occupyQty;
    /**
     * 库存变化量
     */
    private Long changeQty;
    /**
     * 期初库存
     */
    private Long initQty;
    /**
     * 库存状态（良品，残次品等）
     */
    private String invStatusName;
    /**
     * 商品条形码
     */
    private String barCode;
    /**
     * sku code
     */
    private String skuCode;
    /**
     * JMCode（商品编码）
     */
    private String jmCode;
    /**
     * SKU名称
     */
    private String skuName;
    /**
     * 供应商编码
     */
    private String supplierSkuCode;
    /**
     * 商品扩展属性
     */
    private String keyProperties;
    /**
     * 库存状态id
     */
    private Long inventoryStatusId;
    /**
     * 库存状态名称
     */
    private String inventoryStatusName;

    /**
     * 供应商商品编码(货号)
     */
    private String supplierCode;
    /**
     * 所属店铺
     */
    private String invOwner;
    /**
     * 库区
     */
    private String districtCode;
    /**
     * 库位
     */
    private String locationCode;
    /**
     * 库位
     */
    private Long locationId;

    private Long brandId;

    /**
     * 品牌
     */
    private String brandName;
    /**
     * 仓库名称
     */
    private String warehouseName;
    /**
     * 盘点结果
     */
    private Long confirmQty;
    /**
     * 
     * 仓库对应组织ID
     */
    private String whOuId;

    /**
     * sku外部编码
     */
    private String extCode1;

    /**
     * sku外部编码
     */
    private String extCode3;

    /**
     * sku品牌编码
     */
    private String extCode2;
    /**
     * sku sn号
     */
    private String sn;

    private String productCategory;

    private String productLine;

    private String consumerGroup;

    private String inseam;

    private String productDescription;

    private String indexId;

    private BigDecimal invAvgCost;

    private StaLine staLine;

    /**
     * 商品大小 分为 大件、中件、小件
     */
    private String productSize;

    private String KeyProperties2;

    private String warningDate;

    private String shelfLife;

    /**
     * 有效期天数
     */
    private Integer validDate;

    /**
     * 生产日期
     */
    private String pDate;

    /**
     * 过期日期
     */
    private String eDate;

    private String poductionDate;

    private String sexpireDate;

    private Date strPoductionDate;

    private Date strExpireDate;

    private Integer storeMode;

    private Date startDate;

    private Date endDate;

    private Date maxExpDate; // 最大过期时间

    private Date minExpDate;// 最小过期时间

    private Date maxPDate;// 最大生产时间

    private Date minPDate;// 最小生产时间

    private Integer isOccupiedInt;

    private Long districtId;

    private Long statusId;

    private Date versionDate;
    /**
     * 尺码
     */
    private String skuSize;
    /**
     * 数量起
     */
    private Long numberUp;
    /**
     * 数量至
     */
    private Long AmountTo;

    private Integer isZeroInventory;

    private Long salesQty;

    private Long salesLockQty;

    private String invIds;

    private Long shopId;


    public Integer getIsZeroInventory() {
        return isZeroInventory;
    }

    public void setIsZeroInventory(Integer isZeroInventory) {
        this.isZeroInventory = isZeroInventory;
    }

    public String getSkuSize() {
        return skuSize;
    }

    public void setSkuSize(String skuSize) {
        this.skuSize = skuSize;
    }

    public Long getRowm() {
        return rowm;
    }

    public void setRowm(Long rowm) {
        this.rowm = rowm;
    }

    /**
     * 公司运营仓库ouids
     */
    private List<String> OuIds = new ArrayList<String>();

    public List<String> getOuIds() {
        return OuIds;
    }

    public void addOuId(String OuId) {
        this.OuIds.add(OuId);
    }

    public String getWhOuId() {
        return whOuId;
    }

    public void setWhOuId(String whOuId) {
        this.whOuId = whOuId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getSkuIdOwner() {
        return skuIdOwner;
    }

    public void setSkuIdOwner(String skuIdOwner) {
        this.skuIdOwner = skuIdOwner;
    }

    public String getInvStatusName() {
        return invStatusName;
    }

    public void setInvStatusName(String invStatusName) {
        this.invStatusName = invStatusName;
    }

    public Long getInventoryStatusId() {
        return inventoryStatusId;
    }

    public void setInventoryStatusId(Long inventoryStatusId) {
        this.inventoryStatusId = inventoryStatusId;
    }

    public String getInvOwner() {
        return invOwner;
    }

    public void setInvOwner(String invOwner) {
        this.invOwner = invOwner;
    }

    public String getKeyProperties() {
        return keyProperties;
    }

    public void setKeyProperties(String keyProperties) {
        this.keyProperties = keyProperties;
    }

    public InventoryCommand() {
        super();
    }

    public Long getAvailQty() {
        return availQty;
    }

    public void setAvailQty(Long availQty) {
        this.availQty = availQty;
    }

    public Long getOccupyQty() {
        return occupyQty;
    }

    public void setOccupyQty(Long occupyQty) {
        this.occupyQty = occupyQty;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getJmCode() {
        return jmCode;
    }

    public void setJmCode(String jmCode) {
        this.jmCode = jmCode;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getSupplierSkuCode() {
        return supplierSkuCode;
    }

    public void setSupplierSkuCode(String supplierSkuCode) {
        this.supplierSkuCode = supplierSkuCode;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Long getChangeQty() {
        return changeQty;
    }

    public void setChangeQty(Long changeQty) {
        this.changeQty = changeQty;
    }

    public Long getInitQty() {
        return initQty;
    }

    public void setInitQty(Long initQty) {
        this.initQty = initQty;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
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

    public Long getLockQty() {
        return lockQty;
    }

    public void setLockQty(Long lockQty) {
        this.lockQty = lockQty;
    }

    public void setQueryLikeParam() {
        if (StringUtils.hasText(brandName)) {
            this.setBrandName(brandName + "%");
        } else {
            this.setBrandName(null);
        }
        if (StringUtils.hasText(locationCode)) {
            this.setLocationCode(locationCode + "%");
        } else {
            this.setLocationCode(null);
        }
        if (StringUtils.hasText(barCode)) {
            this.setBarCode(barCode + "%");
        } else {
            this.setBarCode(null);
        }
        if (StringUtils.hasText(jmCode)) {
            this.setJmCode(jmCode);
        } else {
            this.setJmCode(null);
        }
        if (StringUtils.hasText(skuName)) {
            this.setSkuName(skuName + "%");
        } else {
            this.setSkuName(null);
        }
        if (StringUtils.hasText(supplierSkuCode)) {
            this.setSupplierSkuCode(supplierSkuCode + "%");
        } else {
            this.setSupplierSkuCode(null);
        }
        if (StringUtils.hasText(invOwner)) {
            this.setInvOwner(invOwner + "%");
        } else {
            this.setInvOwner(null);
        }
        if (StringUtils.hasText(skuCode)) {
            this.setSkuCode(skuCode + "%");
        } else {
            this.setSkuCode(null);
        }
        /*
         * if (!StringUtils.hasText(skuCode)) { this.setSkuCode(null); }
         */

        if (StringUtils.hasText(this.getOwner())) {
            this.setOwner(invOwner + "%");
        } else {
            this.setOwner(null);
        }

    }

    public void setQueryLikeParam1() {
        if (StringUtils.hasText(brandName)) {
            this.setBrandName(brandName);
        } else {
            this.setBrandName(null);
        }
        if (StringUtils.hasText(locationCode)) {
            this.setLocationCode(locationCode + "%");
        } else {
            this.setLocationCode(null);
        }
        if (StringUtils.hasText(barCode)) {
            this.setBarCode(barCode);
        } else {
            this.setBarCode(null);
        }
        if (StringUtils.hasText(jmCode)) {
            this.setJmCode(jmCode);
        } else {
            this.setJmCode(null);
        }
        if (StringUtils.hasText(skuName)) {
            this.setSkuName(skuName + "%");
        } else {
            this.setSkuName(null);
        }
        if (StringUtils.hasText(supplierSkuCode)) {
            this.setSupplierSkuCode(supplierSkuCode + "%");
        } else {
            this.setSupplierSkuCode(null);
        }
        if (StringUtils.hasText(invOwner)) {
            this.setInvOwner(invOwner);
        } else {
            this.setInvOwner(null);
        }
        if (StringUtils.hasText(skuCode)) {
            this.setSkuCode(skuCode);
        } else {
            this.setSkuCode(null);
        }
        /*
         * if (!StringUtils.hasText(skuCode)) { this.setSkuCode(null); }
         */

        if (StringUtils.hasText(this.getOwner())) {
            this.setOwner(invOwner);
        } else {
            this.setOwner(null);
        }

    }

    public Map<String, Object> inventoryQueryMap() {
        setQueryLikeParam();
        Map<String, Object> m = new HashMap<String, Object>();
        if (StringUtils.hasText(barCode)) {
            m.put("barCode", barCode);
        }
        if (StringUtils.hasText(jmCode)) {
            m.put("jmCode", jmCode);
        }
        if (StringUtils.hasText(skuName)) {
            m.put("skuName", skuName);
        }
        if (StringUtils.hasText(supplierSkuCode)) {
            m.put("supplierSkuCode", supplierSkuCode);
        }
        if (StringUtils.hasText(invOwner)) {
            m.put("invOwner", invOwner);
        }
        if (inventoryStatusId != null) {
            m.put("inventoryStatusId", inventoryStatusId);
        }
        return m;
    }

    public Boolean getIsShowZero() {
        return isShowZero;
    }

    public void setIsShowZero(Boolean isShowZero) {
        this.isShowZero = isShowZero;
    }

    @Override
    public BigDecimal getSkuCost() {
        if (skuCost == null) {
            return null;
        }
        skuCost = skuCost.setScale(2, RoundingMode.HALF_UP);
        return skuCost;
    }

    @Override
    public void setSkuCost(BigDecimal skuCost) {
        this.skuCost = skuCost;
    }

    public Long getSalesAvailQty() {
        return salesAvailQty;
    }

    public void setSalesAvailQty(Long salesAvailQty) {
        this.salesAvailQty = salesAvailQty;
    }

    public String getInventoryStatusName() {
        return inventoryStatusName;
    }

    public void setInventoryStatusName(String inventoryStatusName) {
        this.inventoryStatusName = inventoryStatusName;
    }

    public String getIndexId() {
        return indexId;
    }

    public void setIndexId(String indexId) {
        this.indexId = indexId;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Long getConfirmQty() {
        return confirmQty;
    }

    public void setConfirmQty(Long confirmQty) {
        this.confirmQty = confirmQty;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
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

    public void setOuIds(List<String> ouIds) {
        OuIds = ouIds;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public BigDecimal getInvAvgCost() {
        if (invAvgCost == null) {
            return null;
        }
        invAvgCost = invAvgCost.setScale(2, RoundingMode.HALF_UP);
        return invAvgCost;
    }

    public void setInvAvgCost(BigDecimal invAvgCost) {
        this.invAvgCost = invAvgCost;
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

    public StaLine getStaLine() {
        return staLine;
    }

    public void setStaLine(StaLine staLine) {
        this.staLine = staLine;
    }

    public String getProductSize() {
        return productSize;
    }

    public void setProductSize(String productSize) {
        this.productSize = productSize;
    }

    public String getKeyProperties2() {
        return KeyProperties2;
    }

    public void setKeyProperties2(String keyProperties2) {
        KeyProperties2 = keyProperties2;
    }

    public String getExtCode2() {
        return extCode2;
    }

    public void setExtCode2(String extCode2) {
        this.extCode2 = extCode2;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getWarningDate() {
        return warningDate;
    }

    public void setWarningDate(String warningDate) {
        this.warningDate = warningDate;
    }

    public String getShelfLife() {
        return shelfLife;
    }

    public void setShelfLife(String shelfLife) {
        this.shelfLife = shelfLife;
    }

    @Override
    public Integer getValidDate() {
        return validDate;
    }

    @Override
    public void setValidDate(Integer validDate) {
        this.validDate = validDate;
    }

    public String getpDate() {
        return pDate;
    }

    public void setpDate(String pDate) {
        this.pDate = pDate;
    }

    public String geteDate() {
        return eDate;
    }

    public void seteDate(String eDate) {
        this.eDate = eDate;
    }

    public Date getStrPoductionDate() {
        return strPoductionDate;
    }

    public void setStrPoductionDate(Date strPoductionDate) {
        this.strPoductionDate = strPoductionDate;
    }

    public Date getStrExpireDate() {
        return strExpireDate;
    }

    public void setStrExpireDate(Date strExpireDate) {
        this.strExpireDate = strExpireDate;
    }

    public String getPoductionDate() {
        return poductionDate;
    }

    public void setPoductionDate(String poductionDate) {
        this.poductionDate = poductionDate;
    }

    public String getSexpireDate() {
        return sexpireDate;
    }

    public void setSexpireDate(String sexpireDate) {
        this.sexpireDate = sexpireDate;
    }

    public Integer getStoreMode() {
        return storeMode;
    }

    public void setStoreMode(Integer storeMode) {
        this.storeMode = storeMode;
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

    public Date getMaxExpDate() {
        return maxExpDate;
    }

    public void setMaxExpDate(Date maxExpDate) {
        this.maxExpDate = maxExpDate;
    }

    public Date getMinExpDate() {
        return minExpDate;
    }

    public void setMinExpDate(Date minExpDate) {
        this.minExpDate = minExpDate;
    }

    public Date getMaxPDate() {
        return maxPDate;
    }

    public void setMaxPDate(Date maxPDate) {
        this.maxPDate = maxPDate;
    }

    public Date getMinPDate() {
        return minPDate;
    }

    public void setMinPDate(Date minPDate) {
        this.minPDate = minPDate;
    }

    public Integer getIsOccupiedInt() {
        return isOccupiedInt;
    }

    public void setIsOccupiedInt(Integer isOccupiedInt) {
        this.isOccupiedInt = isOccupiedInt;
    }

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public Date getVersionDate() {
        return versionDate;
    }

    public void setVersionDate(Date versionDate) {
        this.versionDate = versionDate;
    }

    public Long getNumberUp() {
        return numberUp;
    }

    public void setNumberUp(Long numberUp) {
        this.numberUp = numberUp;
    }

    public Long getAmountTo() {
        return AmountTo;
    }

    public void setAmountTo(Long amountTo) {
        AmountTo = amountTo;
    }

    public Long getSalesQty() {
        return salesQty;
    }

    public void setSalesQty(Long salesQty) {
        this.salesQty = salesQty;
    }

    public Long getSalesLockQty() {
        return salesLockQty;
    }

    public void setSalesLockQty(Long salesLockQty) {
        this.salesLockQty = salesLockQty;
    }

    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getInvIds() {
        return invIds;
    }

    public void setInvIds(String invIds) {
        this.invIds = invIds;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }


}
