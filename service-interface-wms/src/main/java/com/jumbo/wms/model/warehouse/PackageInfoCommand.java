package com.jumbo.wms.model.warehouse;

import java.math.BigDecimal;
import java.util.Date;

public class PackageInfoCommand extends PackageInfo {

    /**
	 * 
	 */
    private static final long serialVersionUID = 6329681529750907524L;

    /**
     * 相关单据号
     */
    private String refSlipCode;

    /**
     * 收货人
     */
    private String receiver;

    /**
     * 店铺
     */
    private String owner;

    /**
     * 收货地址
     */
    private String address;

    /**
     * 扫描出库时间
     */
    private Date outboundTime;

    /**
     * sta状态
     */
    private Integer staStatus;
    /**
     * PackageInfo status
     */
    private Integer pgStatus;

    /**
     * 作业单 id
     */
    private Long staId;

    /**
     * &&物流对账信息导出
     */
    private String expName;
    private String warehouseName;
    private String handoverCode;
    private String productInfo;
    private String province;
    private String city;
    private String district;
    private String zipcode;
    private String phone;
    private String transTimeType;

    private Integer isCheckedInt;

    private Long staDeliveryInfoId;
    // 获取商品信息
    private String skuName; // 商品名称
    private Long skuId; // 商品Id
    private Long skuQuantity; // 数量

    private String skuCode; // 商品编码
    private String slipCode2;// 子订单号 WX
    private Long channelId; // 店铺ID
    private String skuLength; //
    private String skuWidth;
    private String skuHeight;
    private BigDecimal grossWeight;// 重量

    /**
     * 是否预售 0/空 不是 1：是
     */
    private String isPreSale;

    public String getIsPreSale() {
        return isPreSale;
    }

    public void setIsPreSale(String isPreSale) {
        this.isPreSale = isPreSale;
    }

    public String getSlipCode2() {
        return slipCode2;
    }

    public void setSlipCode2(String slipCode2) {
        this.slipCode2 = slipCode2;
    }

    public String getTransTimeType() {
        return transTimeType;
    }

    public void setTransTimeType(String transTimeType) {
        this.transTimeType = transTimeType;
    }

    private BigDecimal totalActual;

    public String getRefSlipCode() {
        return refSlipCode;
    }

    public void setRefSlipCode(String refSlipCode) {
        this.refSlipCode = refSlipCode;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Date getOutboundTime() {
        return outboundTime;
    }

    public void setOutboundTime(Date outboundTime) {
        this.outboundTime = outboundTime;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

    public Integer getStaStatus() {
        return staStatus;
    }

    public void setStaStatus(Integer staStatus) {
        this.staStatus = staStatus;
    }

    public String getExpName() {
        return expName;
    }

    public void setExpName(String expName) {
        this.expName = expName;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getHandoverCode() {
        return handoverCode;
    }

    public void setHandoverCode(String handoverCode) {
        this.handoverCode = handoverCode;
    }

    public String getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(String productInfo) {
        this.productInfo = productInfo;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BigDecimal getTotalActual() {
        return totalActual;
    }

    public void setTotalActual(BigDecimal totalActual) {
        this.totalActual = totalActual;
    }

    public Integer getPgStatus() {
        return pgStatus;
    }

    public void setPgStatus(Integer pgStatus) {
        this.pgStatus = pgStatus;
    }

    public Integer getIsCheckedInt() {
        return isCheckedInt;
    }

    public void setIsCheckedInt(Integer isCheckedInt) {
        this.isCheckedInt = isCheckedInt;
    }

    public Long getStaDeliveryInfoId() {
        return staDeliveryInfoId;
    }

    public void setStaDeliveryInfoId(Long staDeliveryInfoId) {
        this.staDeliveryInfoId = staDeliveryInfoId;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Long getSkuQuantity() {
        return skuQuantity;
    }

    public void setSkuQuantity(Long skuQuantity) {
        this.skuQuantity = skuQuantity;
    }



    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public String getSkuLength() {
        return skuLength;
    }

    public void setSkuLength(String skuLength) {
        this.skuLength = skuLength;
    }

    public String getSkuWidth() {
        return skuWidth;
    }

    public void setSkuWidth(String skuWidth) {
        this.skuWidth = skuWidth;
    }

    public String getSkuHeight() {
        return skuHeight;
    }

    public void setSkuHeight(String skuHeight) {
        this.skuHeight = skuHeight;
    }

    public BigDecimal getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(BigDecimal grossWeight) {
        this.grossWeight = grossWeight;
    }



}
