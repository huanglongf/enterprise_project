package com.jumbo.wms.model.hub2wms;

import java.math.BigDecimal;
import java.util.List;

import com.jumbo.wms.model.BaseModel;

/**
 * 退换货订单
 * 
 * @author cheng.su
 * 
 */
public class WmsRtnInOrder extends BaseModel {
    /**
	 * 
	 */
    private static final long serialVersionUID = -8063011167428834513L;
    /**
     * 订单类型
     */
    private int orderType;
    /**
     * 仓库编码
     */
    private String warehouseCode;
    /**
     * 仓库Id
     */
    private Long warehouseId;
    /**
     * 订单号(唯一对接标识)
     */
    private String orderCode;
    /**
     * 平台销售单号
     */
    private String sourceOrderCode;
    /**
     * 订单所属
     */
    //
    private String owner;
    /**
     * 是否锁定
     */
    //
    private Boolean isLocked;
    /**
     * 发货备注
     */
    private String memo;
    /**
     * 国家
     */
    private String country;
    /**
     * 省
     */
    private String province;
    /**
     * 多语言省
     */
    private String province1;
    /**
     * 市
     */
    private String city;
    /**
     * 多语言市
     */
    private String city1;
    /**
     * 区
     */
    private String district;
    /**
     * 多语言区
     */
    private String district1;
    /**
     * 送货地址
     */
    private String address;
    /**
     * 多语言送货地址
     */
    private String address1;
    /**
     * 电话
     */
    private String telephone;
    /**
     * 手机
     */
    private String moblie;
    /**
     * 收货人
     */
    private String receiver;
    /**
     * 多语言收货人
     */
    private String receiver1;
    /**
     * 邮编
     */
    private String zipcode;

    /**
     * 买家会员名
     */
    private String orderUserCode;
    /**
     * 退入明细行
     */
    private List<WmsRtnOrderLine> rtnLines;
    /**
     * 退回发票(见1.销售订单发票)
     */
    private List<WmsOrderInvoice> rntInvoices;
    /**
     * 换出发票(见1.销售订单发票)
     */
    private List<WmsOrderInvoice> newinvoices;

    /**
     * 订单来源
     */
    private String orderSourcePlatform;

    /**
     * 退货运输公司
     */
    private String lpcode;

    /**
     * 退货运输单号
     */
    private String trackingNo;


    /**
     * 积分
     */
    private BigDecimal points;

    /**
     * 返点积分
     */
    private BigDecimal returnPoints;

    private Boolean isUrgent;// 是否紧急

    private Boolean isBfOutbountCheck;// 是否检验

    private String orderType2;// 订单生成方式

    public BigDecimal getPoints() {
        return points;
    }

    public void setPoints(BigDecimal points) {
        this.points = points;
    }

    public BigDecimal getReturnPoints() {
        return returnPoints;
    }

    public void setReturnPoints(BigDecimal returnPoints) {
        this.returnPoints = returnPoints;
    }

    public Boolean getIsUrgent() {
        return isUrgent;
    }

    public void setIsUrgent(Boolean isUrgent) {
        this.isUrgent = isUrgent;
    }

    public Boolean getIsBfOutbountCheck() {
        return isBfOutbountCheck;
    }

    public void setIsBfOutbountCheck(Boolean isBfOutbountCheck) {
        this.isBfOutbountCheck = isBfOutbountCheck;
    }

    public String getOrderType2() {
        return orderType2;
    }

    public void setOrderType2(String orderType2) {
        this.orderType2 = orderType2;
    }

    public String getLpcode() {
        return lpcode;
    }

    public void setLpcode(String lpcode) {
        this.lpcode = lpcode;
    }

    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    public String getOrderSourcePlatform() {
        return orderSourcePlatform;
    }

    public void setOrderSourcePlatform(String orderSourcePlatform) {
        this.orderSourcePlatform = orderSourcePlatform;
    }

    public String getOrderUserCode() {
        return orderUserCode;
    }

    public void setOrderUserCode(String orderUserCode) {
        this.orderUserCode = orderUserCode;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getSourceOrderCode() {
        return sourceOrderCode;
    }

    public void setSourceOrderCode(String sourceOrderCode) {
        this.sourceOrderCode = sourceOrderCode;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Boolean getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(Boolean isLocked) {
        this.isLocked = isLocked;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvince1() {
        return province1;
    }

    public void setProvince1(String province1) {
        this.province1 = province1;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity1() {
        return city1;
    }

    public void setCity1(String city1) {
        this.city1 = city1;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDistrict1() {
        return district1;
    }

    public void setDistrict1(String district1) {
        this.district1 = district1;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMoblie() {
        return moblie;
    }

    public void setMoblie(String moblie) {
        this.moblie = moblie;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getReceiver1() {
        return receiver1;
    }

    public void setReceiver1(String receiver1) {
        this.receiver1 = receiver1;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public List<WmsRtnOrderLine> getRtnLines() {
        return rtnLines;
    }

    public void setRtnLines(List<WmsRtnOrderLine> rtnLines) {
        this.rtnLines = rtnLines;
    }

    public List<WmsOrderInvoice> getRntInvoices() {
        return rntInvoices;
    }

    public void setRntInvoices(List<WmsOrderInvoice> rntInvoices) {
        this.rntInvoices = rntInvoices;
    }

    public List<WmsOrderInvoice> getNewinvoices() {
        return newinvoices;
    }

    public void setNewinvoices(List<WmsOrderInvoice> newinvoices) {
        this.newinvoices = newinvoices;
    }



}
