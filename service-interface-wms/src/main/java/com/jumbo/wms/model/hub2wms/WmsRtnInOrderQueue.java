package com.jumbo.wms.model.hub2wms;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;

/**
 * 退换货订单
 * 
 * @author cheng.su
 * 
 */
@Entity
@Table(name = "T_WH_RO_IN")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class WmsRtnInOrderQueue extends BaseModel {
    /*
	 * 
	 */
    private static final long serialVersionUID = -8063011167428834513L;
    /*
     * PK
     */
    private Long id;
    /*
     * 订单类型
     */
    private int orderType;
    /*
     * 仓库编码
     */
    private String warehouseCode;
    /*
     * 仓库ID
     */
    private Long warehouseId;
    /*
     * 订单号(唯一对接标识)
     */
    private String orderCode;
    /*
     * 平台销售单号
     */
    private String sourceOrderCode;
    /*
     * 订单所属
     */
    private String owner;
    /*
     * 是否锁定
     */
    private Boolean isLocked;
    /*
     * 发货备注
     */
    private String memo;
    /*
     * 国家
     */
    private String country;
    /*
     * 省
     */
    private String province;
    /*
     * 多语言省
     */
    private String province1;
    /*
     * 市
     */
    private String city;
    /*
     * 多语言市
     */
    private String city1;
    /*
     * 区
     */
    private String district;
    /*
     * 多语言区
     */
    private String district1;
    /*
     * 送货地址
     */
    private String address;
    /*
     * 多语言送货地址
     */
    private String address1;
    /*
     * 电话
     */
    private String telephone;
    /*
     * 手机
     */
    private String moblie;
    /*
     * 收货人
     */
    private String receiver;
    /*
     * 多语言收货人
     */
    private String receiver1;
    /*
     * 邮编
     */
    private String zipcode;
    /*
     * 接收时间
     */
    private Date receiveTime;
    /*
     * 版本号
     */
    private Date version;
    /*
     * 系统对接码
     */
    private String systemKey;
    /*
     * 错误次数
     */
    private Integer errorCount;

    /*
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


    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_WH_RO_IN", sequenceName = "S_T_WH_RO_IN", allocationSize = 1)
    @GeneratedValue(generator = "SEQ_T_WH_RO_IN", strategy = GenerationType.SEQUENCE)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "order_type2")
    public String getOrderType2() {
        return orderType2;
    }

    public void setOrderType2(String orderType2) {
        this.orderType2 = orderType2;
    }

    @Column(name = "order_source_platform")
    public String getOrderSourcePlatform() {
        return orderSourcePlatform;
    }

    public void setOrderSourcePlatform(String orderSourcePlatform) {
        this.orderSourcePlatform = orderSourcePlatform;
    }

    @Column(name = "ORDER_TYPE")
    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    @Column(name = "WAREHOUSE_CODE", length = 20)
    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    @Column(name = "ORDER_CODE", length = 30)
    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    @Column(name = "SOURCE_ORDER_CODE", length = 50)
    public String getSourceOrderCode() {
        return sourceOrderCode;
    }

    public void setSourceOrderCode(String sourceOrderCode) {
        this.sourceOrderCode = sourceOrderCode;
    }

    @Column(name = "OWNER", length = 50)
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Column(name = "IS_LOCKED")
    public Boolean getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(Boolean isLocked) {
        this.isLocked = isLocked;
    }

    @Column(name = "MEMO", length = 200)
    public String getMemo() {
        return memo;
    }


    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Column(name = "COUNTRY", length = 50)
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Column(name = "PROVINCE", length = 50)
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Column(name = "PROVINCE1", length = 50)
    public String getProvince1() {
        return province1;
    }

    public void setProvince1(String province1) {
        this.province1 = province1;
    }

    @Column(name = "CITY", length = 50)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "CITY1", length = 50)
    public String getCity1() {
        return city1;
    }

    public void setCity1(String city1) {
        this.city1 = city1;
    }

    @Column(name = "DISTRICT", length = 50)
    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @Column(name = "DISTRICT1", length = 50)
    public String getDistrict1() {
        return district1;
    }

    public void setDistrict1(String district1) {
        this.district1 = district1;
    }

    @Column(name = "ADDRESS", length = 300)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "ADDRESS1", length = 300)
    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    @Column(name = "TELEPHONE", length = 50)
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Column(name = "MOBILE", length = 50)
    public String getMoblie() {
        return moblie;
    }

    public void setMoblie(String moblie) {
        this.moblie = moblie;
    }

    @Column(name = "RECEIVER", length = 50)
    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    @Column(name = "RECEIVER1", length = 50)
    public String getReceiver1() {
        return receiver1;
    }

    public void setReceiver1(String receiver1) {
        this.receiver1 = receiver1;
    }

    @Column(name = "ZIPCODE", length = 20)
    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    @Column(name = "RECEIVE_TIME")
    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    @Version
    @Column(name = "VERSION")
    public Date getVersion() {
        return version;
    }

    public void setVersion(Date version) {
        this.version = version;
    }

    @Column(name = "warehouse_id")
    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    @Column(name = "systemkey")
    public String getSystemKey() {
        return systemKey;
    }

    public void setSystemKey(String systemKey) {
        this.systemKey = systemKey;
    }

    @Column(name = "error_Count")
    public Integer getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Integer errorCount) {
        this.errorCount = errorCount;
    }

    @Column(name = "lp_code")
    public String getLpcode() {
        return lpcode;
    }

    public void setLpcode(String lpcode) {
        this.lpcode = lpcode;
    }

    @Column(name = "tracking_no")
    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    @Column(name = "points")
    public BigDecimal getPoints() {
        return points;
    }

    public void setPoints(BigDecimal points) {
        this.points = points;
    }

    @Column(name = "return_points")
    public BigDecimal getReturnPoints() {
        return returnPoints;
    }

    public void setReturnPoints(BigDecimal returnPoints) {
        this.returnPoints = returnPoints;
    }



    @Column(name = "is_urgent")
    public Boolean getIsUrgent() {
        return isUrgent;
    }

    public void setIsUrgent(Boolean isUrgent) {
        this.isUrgent = isUrgent;
    }

    @Column(name = "is_bf_outbount_check")
    public Boolean getIsBfOutbountCheck() {
        return isBfOutbountCheck;
    }

    public void setIsBfOutbountCheck(Boolean isBfOutbountCheck) {
        this.isBfOutbountCheck = isBfOutbountCheck;
    }


}
