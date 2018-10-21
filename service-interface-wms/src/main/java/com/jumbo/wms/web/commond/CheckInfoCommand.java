package com.jumbo.wms.web.commond;

import com.jumbo.wms.model.BaseModel;

/**
 * @author jinlong.ke
 * @date 2016年6月12日下午6:59:59
 * 
 */
public class CheckInfoCommand extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 1398258802543489074L;
    // 配货清单Id
    private Long plId;
    // 配货清单code
    private String plCode;
    // 配货清单状态
    private Integer status;
    // 是否后置运单
    private Boolean isPostpositionExpressBill;
    // 作业单ID
    private Long staId;
    // 作业单号
    private String staCode;
    // 单据号(slip_code)
    private String orderCode;
    // 平台订单号(slip_code1)
    private String slipCode1;
    // 作业单状态
    private String staStatus;
    // 作业单状态
    private Integer intStatus;
    // 店铺
    private String owner;
    // 物流商
    private String lpcode;
    // 运单号
    private String transNo;
    // 箱号
    private String pgIndex;
    // 批次1
    private String idx1;
    // 批次2
    private String idx2;
    // 配货类型
    private Integer pickingType;
    // 推荐耗材条码
    private String cmCode;
    // 计划商品总数量
    private Long skuQty;
    // 行唯一ID
    private Long uniqueId;
    // 计划量
    private Long qty;
    // 执行量
    private Long cQty;
    // 商品编码
    private String code;
    // 商品名称
    private String name;
    // 条码
    private String barcode;
    // 货号
    private String supCode;
    // 关键属性
    private String keyProp;
    // 颜色尺码
    private String colorSize;
    // 是否Sn
    private Boolean isSn;
    // 多条码
    private String bcCode;
    // 是否是特殊处理
    private Integer isGift;

    public Integer getIsGift() {
        return isGift;
    }

    public void setIsGift(Integer isGift) {
        this.isGift = isGift;
    }

    public Long getPlId() {
        return plId;
    }

    public void setPlId(Long plId) {
        this.plId = plId;
    }

    public String getPlCode() {
        return plCode;
    }

    public void setPlCode(String plCode) {
        this.plCode = plCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getSlipCode1() {
        return slipCode1;
    }

    public void setSlipCode1(String slipCode1) {
        this.slipCode1 = slipCode1;
    }

    public String getStaStatus() {
        return staStatus;
    }

    public void setStaStatus(String staStatus) {
        this.staStatus = staStatus;
    }

    public Integer getIntStatus() {
        return intStatus;
    }

    public void setIntStatus(Integer intStatus) {
        this.intStatus = intStatus;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getLpcode() {
        return lpcode;
    }

    public void setLpcode(String lpcode) {
        this.lpcode = lpcode;
    }

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    public String getPgIndex() {
        return pgIndex;
    }

    public void setPgIndex(String pgIndex) {
        this.pgIndex = pgIndex;
    }

    public String getIdx1() {
        return idx1;
    }

    public void setIdx1(String idx1) {
        this.idx1 = idx1;
    }

    public String getIdx2() {
        return idx2;
    }

    public void setIdx2(String idx2) {
        this.idx2 = idx2;
    }

    public Long getSkuQty() {
        return skuQty;
    }

    public void setSkuQty(Long skuQty) {
        this.skuQty = skuQty;
    }

    public Long getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(Long uniqueId) {
        this.uniqueId = uniqueId;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public Long getcQty() {
        return cQty;
    }

    public void setcQty(Long cQty) {
        this.cQty = cQty;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getSupCode() {
        return supCode;
    }

    public void setSupCode(String supCode) {
        this.supCode = supCode;
    }

    public String getKeyProp() {
        return keyProp;
    }

    public void setKeyProp(String keyProp) {
        this.keyProp = keyProp;
    }

    public String getColorSize() {
        return colorSize;
    }

    public void setColorSize(String colorSize) {
        this.colorSize = colorSize;
    }

    public Boolean getIsSn() {
        return isSn;
    }

    public void setIsSn(Boolean isSn) {
        this.isSn = isSn;
    }

    public String getBcCode() {
        return bcCode;
    }

    public void setBcCode(String bcCode) {
        this.bcCode = bcCode;
    }

    public Integer getPickingType() {
        return pickingType;
    }

    public void setPickingType(Integer pickingType) {
        this.pickingType = pickingType;
    }

    public String getCmCode() {
        return cmCode;
    }

    public void setCmCode(String cmCode) {
        this.cmCode = cmCode;
    }

    public Boolean getIsPostpositionExpressBill() {
        return isPostpositionExpressBill;
    }

    public void setIsPostpositionExpressBill(Boolean isPostpositionExpressBill) {
        this.isPostpositionExpressBill = isPostpositionExpressBill;
    }
}
