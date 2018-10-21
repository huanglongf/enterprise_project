package com.jumbo.wms.model.warehouse;

import java.util.List;

public class PickingListSqlCommand {

    private String postshopInputName1;
    private String postshopInputName;
    private String lpCode;
    private String refSlipCode;
    private String barCode;
    private String code1;
    private String code2;
    private String code3;
    private String code4;
    private Integer status;
    private Integer packingType; // 包装类型
    private Integer specialType;
    private Integer isSpecialPackaging;
    private Integer isMergeInt = 1; // 忽略已选拣货区域查询条件
    private Integer isMargeZoon = 1; // 忽略已选仓库区域查询条件
    private Integer pickingType;
    private Long packageSkuId;
    private Long skuQty;
    private String skus;
    private String toLocation;
    private List<String> shopInnerCodes;
    private List<String> codeList;
    private String pickZoneList;
    private String whZoneList;
    private String sList;
    private Long pickSubType;
    private String isPreSale;// 是否是预售订单
    private Integer orderType2;// 订单类型
    private String date;
    private String date2;
    private String date3;
    private String date4;
    private Integer isNeedInvoice;
    private Integer isCod;
    private Integer isSn;
    private Integer transTimeType;
    private String shoplist;
    private String shoplist1;
    private Long categoryId;
    private String AeraList1;
    private String whZoneList1;
    private Integer mergePickZoon;
    private Integer mergeWhZoon;


    public String getPostshopInputName() {
        return postshopInputName;
    }

    public void setPostshopInputName(String postshopInputName) {
        this.postshopInputName = postshopInputName;
    }

    public Integer getMergeWhZoon() {
        return mergeWhZoon;
    }

    public void setMergeWhZoon(Integer mergeWhZoon) {
        this.mergeWhZoon = mergeWhZoon;
    }

    public Integer getMergePickZoon() {
        return mergePickZoon;
    }

    public void setMergePickZoon(Integer mergePickZoon) {
        this.mergePickZoon = mergePickZoon;
    }

    public String getWhZoneList1() {
        return whZoneList1;
    }

    public void setWhZoneList1(String whZoneList1) {
        this.whZoneList1 = whZoneList1;
    }

    public String getAeraList1() {
        return AeraList1;
    }

    public void setAeraList1(String aeraList1) {
        AeraList1 = aeraList1;
    }

    public String getShoplist() {
        return shoplist;
    }

    public void setShoplist(String shoplist) {
        this.shoplist = shoplist;
    }

    public String getShoplist1() {
        return shoplist1;
    }

    public void setShoplist1(String shoplist1) {
        this.shoplist1 = shoplist1;
    }

    public Integer getTransTimeType() {
        return transTimeType;
    }

    public void setTransTimeType(Integer transTimeType) {
        this.transTimeType = transTimeType;
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

    public Integer getIsCod() {
        return isCod;
    }

    public void setIsCod(Integer isCod) {
        this.isCod = isCod;
    }

    public Integer getIsNeedInvoice() {
        return isNeedInvoice;
    }

    public void setIsNeedInvoice(Integer isNeedInvoice) {
        this.isNeedInvoice = isNeedInvoice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate2() {
        return date2;
    }

    public void setDate2(String date2) {
        this.date2 = date2;
    }

    public String getDate3() {
        return date3;
    }

    public void setDate3(String date3) {
        this.date3 = date3;
    }

    public String getDate4() {
        return date4;
    }

    public void setDate4(String date4) {
        this.date4 = date4;
    }

    public String getLpCode() {
        return lpCode;
    }

    public void setLpCode(String lpCode) {
        this.lpCode = lpCode;
    }

    public String getRefSlipCode() {
        return refSlipCode;
    }

    public void setRefSlipCode(String refSlipCode) {
        this.refSlipCode = refSlipCode;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getCode1() {
        return code1;
    }

    public void setCode1(String code1) {
        this.code1 = code1;
    }

    public String getCode2() {
        return code2;
    }

    public void setCode2(String code2) {
        this.code2 = code2;
    }

    public String getCode3() {
        return code3;
    }

    public void setCode3(String code3) {
        this.code3 = code3;
    }

    public String getCode4() {
        return code4;
    }

    public void setCode4(String code4) {
        this.code4 = code4;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPackingType() {
        return packingType;
    }

    public void setPackingType(Integer packingType) {
        this.packingType = packingType;
    }

    public Integer getSpecialType() {
        return specialType;
    }

    public void setSpecialType(Integer specialType) {
        this.specialType = specialType;
    }

    public Integer getIsSpecialPackaging() {
        return isSpecialPackaging;
    }

    public void setIsSpecialPackaging(Integer isSpecialPackaging) {
        this.isSpecialPackaging = isSpecialPackaging;
    }

    public Integer getIsMergeInt() {
        return isMergeInt;
    }

    public void setIsMergeInt(Integer isMergeInt) {
        this.isMergeInt = isMergeInt;
    }

    public Integer getIsMargeZoon() {
        return isMargeZoon;
    }

    public void setIsMargeZoon(Integer isMargeZoon) {
        this.isMargeZoon = isMargeZoon;
    }

    public Integer getPickingType() {
        return pickingType;
    }

    public void setPickingType(Integer pickingType) {
        this.pickingType = pickingType;
    }

    public Long getPackageSkuId() {
        return packageSkuId;
    }

    public void setPackageSkuId(Long packageSkuId) {
        this.packageSkuId = packageSkuId;
    }

    public Long getSkuQty() {
        return skuQty;
    }

    public void setSkuQty(Long skuQty) {
        this.skuQty = skuQty;
    }

    public String getSkus() {
        return skus;
    }

    public void setSkus(String skus) {
        this.skus = skus;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public List<String> getShopInnerCodes() {
        return shopInnerCodes;
    }

    public void setShopInnerCodes(List<String> shopInnerCodes) {
        this.shopInnerCodes = shopInnerCodes;
    }

    public List<String> getCodeList() {
        return codeList;
    }

    public void setCodeList(List<String> codeList) {
        this.codeList = codeList;
    }

    public String getPickZoneList() {
        return pickZoneList;
    }

    public void setPickZoneList(String pickZoneList) {
        this.pickZoneList = pickZoneList;
    }

    public String getWhZoneList() {
        return whZoneList;
    }

    public void setWhZoneList(String whZoneList) {
        this.whZoneList = whZoneList;
    }

    public Long getPickSubType() {
        return pickSubType;
    }

    public void setPickSubType(Long pickSubType) {
        this.pickSubType = pickSubType;
    }

    public String getIsPreSale() {
        return isPreSale;
    }

    public void setIsPreSale(String isPreSale) {
        this.isPreSale = isPreSale;
    }

    public Integer getOrderType2() {
        return orderType2;
    }

    public void setOrderType2(Integer orderType2) {
        this.orderType2 = orderType2;
    }

    public String getsList() {
        return sList;
    }

    public void setsList(String sList) {
        this.sList = sList;
    }

    public String getPostshopInputName1() {
        return postshopInputName1;
    }

    public void setPostshopInputName1(String postshopInputName1) {
        this.postshopInputName1 = postshopInputName1;
    }

}
