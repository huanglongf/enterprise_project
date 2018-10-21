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
import java.util.HashMap;
import java.util.Map;

import com.jumbo.util.StringUtils;

/**
 * @author jumbo
 * 
 */
public class WarehouseLocationCommand extends WarehouseLocation {

    /**
	 * 
	 */
    private static final long serialVersionUID = 6670707800002978367L;
    private Long capacityNum;
    private Long inventoryNum;
    private String districtCode;
    private String districtName;
    private String owner;
    private String brand;
    private Long transtype_id;
    private Long qty;
    private Long skuId;

    private Long invStatusId;

    private String staCode;
    private String staSlipCode;
    private Date createDate;
    private Date endCreateDate;


    private String transactionName;

    private Long districtId;

    private String batchCode;

    private Long ouid;

    private Long capaRatioNum;

    /*
     * 新增String类型日期字段，辅助完成查询时间精确到时分秒
     */
    private String createDate1;
    private String endCreateDate1;

    private Integer isAvailableInt;

    private Integer isDisputableInt;

    private Integer isLockedInt;

    private Integer sortingModeInt;


    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Long getCapacityNum() {
        return capacityNum;
    }

    public void setCapacityNum(Long capacityNum) {
        this.capacityNum = capacityNum;
    }
    @Override
    public Long getInventoryNum() {
        return inventoryNum;
    }
    @Override
    public void setInventoryNum(Long inventoryNum) {
        this.inventoryNum = inventoryNum;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    public Long getInvStatusId() {
        return invStatusId;
    }

    public void setInvStatusId(Long invStatusId) {
        this.invStatusId = invStatusId;
    }

    public Long getTranstype_id() {
        return transtype_id;
    }

    public void setTranstype_id(Long transtypeId) {
        transtype_id = transtypeId;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public String getTransactionName() {
        return transactionName;
    }

    public void setTransactionName(String transactionName) {
        this.transactionName = transactionName;
    }

    public Long getOuid() {
        return ouid;
    }

    public void setOuid(Long ouid) {
        this.ouid = ouid;
    }

    public Long getCapaRatioNum() {
        return capaRatioNum;
    }

    public void setCapaRatioNum(Long capaRatioNum) {
        this.capaRatioNum = capaRatioNum;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getEndCreateDate() {
        return endCreateDate;
    }

    public void setEndCreateDate(Date endCreateDate) {
        this.endCreateDate = endCreateDate;
    }

    public Map<String, Object> setQueryLikeParam() {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.hasText(this.getCode())) {
            this.setCode(this.getCode() + "%");
            map.put("code", this.getCode());
        } else {
            this.setCode(null);
        }
        if (StringUtils.hasText(this.getDistrictCode())) {
            this.setDistrictCode(districtCode + "%");
            map.put("districtCode", this.getDistrictCode());
        } else {
            this.setDistrictCode(null);
        }
        if (StringUtils.hasText(this.getDistrictName())) {
            this.setDistrictName(districtName + "%");
            map.put("districtName", this.getDistrictName());
        } else {
            this.setDistrictName(null);
        }
        if (StringUtils.hasText(this.getOwner())) {
            this.setOwner(this.getOwner() + "%");
            map.put("owner", this.getOwner());
        } else {
            this.setOwner(null);
        }
        if (StringUtils.hasText(this.getBrand())) {
            this.setBrand(this.getBrand() + "%");
            map.put("brand", this.getBrand());
        } else {
            this.setBrand(null);
        }
        if (StringUtils.hasText(this.getStaCode())) {
            this.setStaCode(this.getStaCode() + "%");
            map.put("staCode", this.getStaCode());
        } else {
            this.setStaCode(null);
        }
        if (StringUtils.hasText(this.getStaSlipCode())) {
            this.setStaSlipCode(this.getStaSlipCode() + "%");
            map.put("staSlipCode", this.getStaSlipCode());
        } else {
            this.setStaSlipCode(null);
        }
        map.put("createDate", this.getCreateDate());
        map.put("endCreateDate", this.getEndCreateDate());
        return map;
    }

    public String getCreateDate1() {
        return createDate1;
    }

    public void setCreateDate1(String createDate1) {
        this.createDate1 = createDate1;
    }

    public String getEndCreateDate1() {
        return endCreateDate1;
    }

    public void setEndCreateDate1(String endCreateDate1) {
        this.endCreateDate1 = endCreateDate1;
    }

    public Integer getIsAvailableInt() {
        return isAvailableInt;
    }

    public void setIsAvailableInt(Integer isAvailableInt) {
        this.isAvailableInt = isAvailableInt;
    }

    public Integer getIsDisputableInt() {
        return isDisputableInt;
    }

    public void setIsDisputableInt(Integer isDisputableInt) {
        this.isDisputableInt = isDisputableInt;
    }

    public Integer getIsLockedInt() {
        return isLockedInt;
    }

    public void setIsLockedInt(Integer isLockedInt) {
        this.isLockedInt = isLockedInt;
    }

    public Integer getSortingModeInt() {
        return sortingModeInt;
    }

    public void setSortingModeInt(Integer sortingModeInt) {
        this.sortingModeInt = sortingModeInt;
    }
}
