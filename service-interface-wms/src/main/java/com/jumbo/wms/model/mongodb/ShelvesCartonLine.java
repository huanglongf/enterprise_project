package com.jumbo.wms.model.mongodb;

import java.util.Date;
import java.util.Set;

import com.jumbo.wms.model.BaseModel;


@SuppressWarnings("rawtypes")
public class ShelvesCartonLine extends BaseModel implements Comparable {

    /**
     * 
     */
    private static final long serialVersionUID = 4424046102663674296L;

    private Long planId;// 计划id

    private Long skuId;
    
    private String isXq;// 0:不是效期 1：是效期

    private Long remainQty;// 剩余执行量

    private Long qty;// 总执行量

    private Long locationId;// 库位id

    private String locationCode;// 库位

    private Integer sort;// 排序

    private Set<String> skubarcode;// 商品条码

    private Set<String> sns;// sns条码

    private Set<String> dmgBarcode;// 残次条码列表

    private Set<String> dmgBarcode2;// 残次条码列表原始

    private Date expDate;// 过期时间

    private String isOver;// 此行是否已经上架完成 0:未完成 1:已完成

    private Long lineId;// 行id

    public Long getLineId() {
        return lineId;
    }

    public void setLineId(Long lineId) {
        this.lineId = lineId;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public String getIsOver() {
        return isOver;
    }

    public void setIsOver(String isOver) {
        this.isOver = isOver;
    }

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Set<String> getSkubarcode() {
        return skubarcode;
    }

    public void setSkubarcode(Set<String> skubarcode) {
        this.skubarcode = skubarcode;
    }

    public Set<String> getSns() {
        return sns;
    }

    public void setSns(Set<String> sns) {
        this.sns = sns;
    }

    public Set<String> getDmgBarcode() {
        return dmgBarcode;
    }

    public void setDmgBarcode(Set<String> dmgBarcode) {
        this.dmgBarcode = dmgBarcode;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Long getRemainQty() {
        return remainQty;
    }

    public void setRemainQty(Long remainQty) {
        this.remainQty = remainQty;
    }

    public Set<String> getDmgBarcode2() {
        return dmgBarcode2;
    }

    public void setDmgBarcode2(Set<String> dmgBarcode2) {
        this.dmgBarcode2 = dmgBarcode2;
    }
    
    @Override
    public int compareTo(Object o) {
        ShelvesCartonLine s = (ShelvesCartonLine) o;
        if (this.sort == null) {
            this.sort = -1;
        }
        if (s.sort == null) {
            this.sort = -1;
        }
        if (this.sort.compareTo(s.sort) > 0) {
            return 1;
        }

        // if (this.locationCode.compareTo(s.locationCode) == 0) {
        // return 1;
        // }
        if (this.locationCode.compareTo(s.locationCode) < 0) {
            return -1;
        }
        return 1;

        // ShelvesCartonLine s = (ShelvesCartonLine) o;
        // if (this.locationCode.compareTo(s.locationCode) > 0) {
        // return 1;
        // }
        //
        // if (this.locationCode.compareTo(s.locationCode) == 0) {
        // throw new BusinessException("推荐有问题,locationCode=" + locationCode);
        // }
        // if (this.locationCode.compareTo(s.locationCode) < 0) {
        // return -1;
        // }
        // return 1;
    }

    public String getIsXq() {
        return isXq;
    }

    public void setIsXq(String isXq) {
        this.isXq = isXq;
    }

}
