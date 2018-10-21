package com.jumbo.wms.model;

import java.io.Serializable;
import java.util.List;

import com.jumbo.wms.model.warehouse.PickingListCheckMode;
import com.jumbo.wms.model.warehouse.SkuSizeConfig;

public class CreatePickingListByMQ implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6444983731393886758L;

    private String loc;

    private String isQs;

    private Boolean isOtwoo;

    private Long ouId;

    private Long userId;

    private String skusIdAndQty;

    private List<Long> staIdList;

    private Long id;

    private PickingListCheckMode checkMode;

    private Boolean isSp;

    private Integer isSn;

    private Long categoryId;

    private List<SkuSizeConfig> ssList;

    private Integer isCod;

    private List<String> cityList;

    private Boolean b;

    private Boolean isTransAfter;

    private Boolean isOtoPicking = false;


    public Boolean getIsTransAfter() {
        return isTransAfter;
    }

    public void setIsTransAfter(Boolean isTransAfter) {
        this.isTransAfter = isTransAfter;
    }

    public Boolean getB() {
        return b;
    }

    public void setB(Boolean b) {
        this.b = b;
    }

    public List<String> getCityList() {
        return cityList;
    }

    public void setCityList(List<String> cityList) {
        this.cityList = cityList;
    }

    public String getSkusIdAndQty() {
        return skusIdAndQty;
    }

    public void setSkusIdAndQty(String skusIdAndQty) {
        this.skusIdAndQty = skusIdAndQty;
    }


    public List<Long> getStaIdList() {
        return staIdList;
    }

    public void setStaIdList(List<Long> staIdList) {
        this.staIdList = staIdList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public PickingListCheckMode getCheckMode() {
        return checkMode;
    }

    public void setCheckMode(PickingListCheckMode checkMode) {
        this.checkMode = checkMode;
    }

    public Boolean getIsSp() {
        return isSp;
    }

    public void setIsSp(Boolean isSp) {
        this.isSp = isSp;
    }

    public Integer getIsSn() {
        return isSn;
    }

    public void setIsSn(Integer isSn) {
        this.isSn = isSn;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public List<SkuSizeConfig> getSsList() {
        return ssList;
    }

    public void setSsList(List<SkuSizeConfig> ssList) {
        this.ssList = ssList;
    }

    public Integer getIsCod() {
        return isCod;
    }

    public void setIsCod(Integer isCod) {
        this.isCod = isCod;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOuId() {
        return ouId;
    }

    public void setOuId(Long ouId) {
        this.ouId = ouId;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getIsQs() {
        return isQs;
    }

    public void setIsQs(String isQs) {
        this.isQs = isQs;
    }

    public Boolean getIsOtwoo() {
        return isOtwoo;
    }

    public void setIsOtwoo(Boolean isOtwoo) {
        this.isOtwoo = isOtwoo;
    }

    public Boolean getIsOtoPicking() {
        return isOtoPicking;
    }

    public void setIsOtoPicking(Boolean isOtoPicking) {
        this.isOtoPicking = isOtoPicking;
    }

}
