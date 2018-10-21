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
import java.util.HashMap;
import java.util.Map;

import com.jumbo.util.StringUtils;

/**
 * @author jumbo
 * 
 */
public class StvLineCommand extends StvLine {

    private static final long serialVersionUID = -3168762071877878596L;
    private Long skuId;
    private String stvLineKey;
    private Long stalineId;
    private Long totalQuantity;
    private Long stvId;
    private Long whId;
    private Long transtypeId;
    private String ouCode;
    private String sexpireDate;
    private String extCode2;
    private Long invS;
    private Boolean isRfid;
    
    private String isMixTime;

    /**
     * 编码，唯一标示Sku的编码
     */
    private String skuCode;
    /**
     * 供应商商品编码
     */
    private String supplierCode;
    /**
     * Sku条码，唯一标示Sku的编码
     */
    private String barCode;
    /**
     * Product中JMCode
     */
    private String jmCode;
    /**
     * 扩展属性
     */
    private String keyProperties;

    /**
     * Sku名称，一般来说这个信息=Product的名称
     */
    private String skuName;
    /**
     * 库区编码
     */
    private String districtCode;
    /**
     * 库区类型名称
     */
    private String districtTypeName;
    /**
     * 库位
     */
    private Long locationId;
    /**
     * 库位编码
     */
    private String locationCode;

    /**
     * 库区
     */
    private Integer districtId;
    /**
     * 可用库容
     */
    private Long locationAvailable;
    /**
     * 库位库存
     */
    private String locationInventory;
    /**
     * 库存状态
     */
    private Long intInvstatus;

    /**
     * 库存状态名称
     */
    private String intInvstatusName;

    /**
     * 作业类型名称
     */
    private String typeName;
    /**
     * 作业类型
     */
    private Long typeId;

    /**
     * 作业方向
     */
    private Integer directionInt;

    private Integer isSnSku;

    private Boolean isSnSkuBoolean;

    /**
     * 创建人
     */
    private String creater;

    private Date createDate;

    private Date finishDate;

    private String extCode1;

    /**
     * 序号
     */
    private Integer index;
    /**
     * 计量单位
     */
    private String unitName;

    /**
     * 计划收获数量
     */
    private BigDecimal planQty;

    /**
     * 剩余上架量
     */
    private Long overplusAddedQty;

    /**
     * 累计收获量
     */
    private BigDecimal totalQty;
    /**
     * 品牌
     */
    private String brand;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户名
     */
    private String operateName;

    /**
     * pdaID
     */
    private Long pdaId;

    /**
     * 
     */
    private Long pdaLineId;

    /**
     * 库存状态
     */
    private Long invStatusId;
    /**
     * 仓库组织Id
     */
    private Long ouId;
    /**
     * 店铺组织Id
     */
    private Long shopOuId;
    /**
     * 店铺名字 t_BI_channel name
     */
    private String shopName;

    /**
     * 生成日期
     */
    private String strPoductionDate;

    /**
     * 过期时间
     */
    private String strExpireDate;

    /**
     * 是否是保质期商品
     */
    private String isShelfManagement;
    /**
     * 作业单号
     */
    private String staCode;
    private String startNum;
    private String stopNum;
    private String snType;

    /**
     * 是否可销售
     */
    private Integer marketAbility;

    private String customerSkuCode;

    private Boolean isSn;

    private Integer effectSku;

    public Boolean getIsSn() {
        return isSn;
    }

    public void setIsSn(Boolean isSn) {
        this.isSn = isSn;
    }

    public Integer getEffectSku() {
        return effectSku;
    }

    public void setEffectSku(Integer effectSku) {
        this.effectSku = effectSku;
    }

    public Long getInvStatusId() {
        return invStatusId;
    }

    public void setInvStatusId(Long invStatusId) {
        this.invStatusId = invStatusId;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Long getStalineId() {
        return stalineId;
    }

    public void setStalineId(Long stalineId) {
        this.stalineId = stalineId;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public Integer getIsSnSku() {
        return isSnSku;
    }

    public void setIsSnSku(Integer isSnSku) {
        this.isSnSku = isSnSku;
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

    public String getKeyProperties() {
        return keyProperties;
    }

    public void setKeyProperties(String keyProperties) {
        this.keyProperties = keyProperties;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getLocationInventory() {
        return locationInventory;
    }

    public void setLocationInventory(String locationInventory) {
        this.locationInventory = locationInventory;
    }

    public Long getLocationAvailable() {
        return locationAvailable;
    }

    public void setLocationAvailable(Long locationAvailable) {
        this.locationAvailable = locationAvailable;
    }

    public Long getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Long totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public Long getIntInvstatus() {
        return intInvstatus;
    }

    public void setIntInvstatus(Long intInvstatus) {
        this.intInvstatus = intInvstatus;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getIntInvstatusName() {
        return intInvstatusName;
    }

    public void setIntInvstatusName(String intInvstatusName) {
        this.intInvstatusName = intInvstatusName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getDirectionInt() {
        return directionInt;
    }

    public void setDirectionInt(Integer directionInt) {
        this.directionInt = directionInt;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public Map<String, Object> getQueryMap() {
        Map<String, Object> m = new HashMap<String, Object>();
        if (StringUtils.hasText(barCode)) {
            m.put("barCode", barCode + "%");
        }
        if (StringUtils.hasText(jmCode)) {
            m.put("jmCode", jmCode + "%");
        }
        if (StringUtils.hasText(skuName)) {
            m.put("skuName", skuName + "%");
        }
        if (StringUtils.hasText(skuCode)) {
            m.put("skuCode", skuCode + "%");
        }
        if (StringUtils.hasText(supplierCode)) {
            m.put("supplierCode", supplierCode + "%");
        }
        if (StringUtils.hasText(creater)) {
            m.put("creater", creater + "%");
        }
        if (StringUtils.hasText(locationCode)) {
            m.put("locationCode", locationCode + "%");
        }
        if (directionInt != null) {
            m.put("direction", directionInt);
        }
        if (typeId != null) {
            m.put("typeId", typeId);
        }
        if (intInvstatus != null) {
            m.put("statusId", intInvstatus);
        }
        if (pdaId != null) {
            m.put("pdaId", pdaId);
        }
        return m;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public BigDecimal getPlanQty() {
        return planQty;
    }

    public void setPlanQty(BigDecimal planQty) {
        this.planQty = planQty;
    }

    public BigDecimal getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(BigDecimal totalQty) {
        this.totalQty = totalQty;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Boolean getIsSnSkuBoolean() {
        return isSnSkuBoolean;
    }

    public void setIsSnSkuBoolean(Boolean isSnSkuBoolean) {
        this.isSnSkuBoolean = isSnSkuBoolean;
    }

    public Long getStvId() {
        return stvId;
    }

    public void setStvId(Long stvId) {
        this.stvId = stvId;
    }

    public Long getWhId() {
        return whId;
    }

    public void setWhId(Long whId) {
        this.whId = whId;
    }

    public Long getTranstypeId() {
        return transtypeId;
    }

    public void setTranstypeId(Long transtypeId) {
        this.transtypeId = transtypeId;
    }

    public String getStvLineKey() {
        return stvLineKey;
    }

    public void setStvLineKey(String stvLineKey) {
        this.stvLineKey = stvLineKey;
    }

    public String getDistrictTypeName() {
        return districtTypeName;
    }

    public void setDistrictTypeName(String districtTypeName) {
        this.districtTypeName = districtTypeName;
    }

    public String getExtCode1() {
        return extCode1;
    }

    public void setExtCode1(String extCode1) {
        this.extCode1 = extCode1;
    }

    public Long getOverplusAddedQty() {
        return overplusAddedQty;
    }

    public void setOverplusAddedQty(Long overplusAddedQty) {
        this.overplusAddedQty = overplusAddedQty;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getPdaId() {
        return pdaId;
    }

    public void setPdaId(Long pdaId) {
        this.pdaId = pdaId;
    }

    public Long getPdaLineId() {
        return pdaLineId;
    }

    public void setPdaLineId(Long pdaLineId) {
        this.pdaLineId = pdaLineId;
    }

    public String getOperateName() {
        return operateName;
    }

    public void setOperateName(String operateName) {
        this.operateName = operateName;
    }

    public Long getOuId() {
        return ouId;
    }

    public void setOuId(Long ouId) {
        this.ouId = ouId;
    }

    public Long getShopOuId() {
        return shopOuId;
    }

    public void setShopOuId(Long shopOuId) {
        this.shopOuId = shopOuId;
    }

    public String getOuCode() {
        return ouCode;
    }

    public void setOuCode(String ouCode) {
        this.ouCode = ouCode;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getIsShelfManagement() {
        return isShelfManagement;
    }

    public void setIsShelfManagement(String isShelfManagement) {
        this.isShelfManagement = isShelfManagement;
    }

    public String getStrPoductionDate() {
        return strPoductionDate;
    }

    public void setStrPoductionDate(String strPoductionDate) {
        this.strPoductionDate = strPoductionDate;
    }

    public String getStrExpireDate() {
        return strExpireDate;
    }

    public void setStrExpireDate(String strExpireDate) {
        this.strExpireDate = strExpireDate;
    }

    public String getSexpireDate() {
        return sexpireDate;
    }

    public void setSexpireDate(String sexpireDate) {
        this.sexpireDate = sexpireDate;
    }

    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

    public String getExtCode2() {
        return extCode2;
    }

    public void setExtCode2(String extCode2) {
        this.extCode2 = extCode2;
    }

    public Long getInvS() {
        return invS;
    }

    public void setInvS(Long invS) {
        this.invS = invS;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
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

    public String getSnType() {
        return snType;
    }

    public void setSnType(String snType) {
        this.snType = snType;
    }

    public Integer getMarketAbility() {
        return marketAbility;
    }

    public void setMarketAbility(Integer marketAbility) {
        this.marketAbility = marketAbility;
    }

    public String getCustomerSkuCode() {
        return customerSkuCode;
    }

    public void setCustomerSkuCode(String customerSkuCode) {
        this.customerSkuCode = customerSkuCode;
    }

    public String getIsMixTime() {
        return isMixTime;
    }

    public void setIsMixTime(String isMixTime) {
        this.isMixTime = isMixTime;
    }

    public Boolean getIsRfid() {
        return isRfid;
    }

    public void setIsRfid(Boolean isRfid) {
        this.isRfid = isRfid;
    }

}
