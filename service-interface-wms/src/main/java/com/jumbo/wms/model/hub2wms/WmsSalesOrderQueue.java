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
 * 过仓HUB2WMS 销售订单队列
 * 
 * @author jinlong.ke
 * 
 */
@Entity
@Table(name = "T_WH_SO_OR_RO")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class WmsSalesOrderQueue extends BaseModel {
    private static final long serialVersionUID = -4464156002233483900L;
    /*
     * PK
     */
    private Long id;
    /*
     * 系统来源标识
     */
    private String systemKey;
    /*
     * 订单来源 
     */
    private String orderSource;
    /*
     * NIKE
     */
    private String slipCode2;
    /*
     * 订单号(唯一对接标识)
     */
    private String orderCode;
    /*
     * 原始销售单号
     */
    private String sourceOrderCode;
    /*
     * 订单所属
     */
    private String owner;
    /*
     * 允许使用库存所属列表
     */
    private String acceptOwners;
    /*
     * 订单类型 
     */
    private int orderType;
    /*
     * 仓库编码
     */
    private String warehouseCode;
    /*
     * 是否锁定
     */
    private boolean isLocked;
    /*
     * 预计发货时间
     */
    private Date planOutboundTime;
    /*
     * 预计送达时间
     */
    private Date planArriveTime;
    /*
     * 是否快递升级
     */
    private boolean isTransUpgrade;
    /*
     * 发货备注
     */
    private String memo;
    /*
     * cod付款金额 
     */
    private BigDecimal codAmt;
    /*
     * 订单成金额 
     */
    private BigDecimal orderAmt;
    /*
     * 订单优惠金额 
     */
    private BigDecimal orderDiscount;
    /*
     * 运费
     */
    private BigDecimal freight;
    /*
     * 平台订单创建时间
     */
    private Date orderCreateTime;
    /*
     * 门店编码 
     */
    private String storeCode;
    /*
     * 自提便利店编码
     */
    private String convenienceStore;
    /*
     * 活动编码
     */
    private String activeCode;
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
     * 下单用户邮箱
     */
    private String orderUserMail;
    /*
     * 下单用户编码
     */
    private String orderUserCode;
    /*
     * 物流商简称
     */
    private String transCode;
    /*
     * 运单号
     */
    private String transNo;
    /*
     * 是否cod订单
     */
    private boolean isCod;
    /*
     * 是否POS机付款
     */
    private boolean isCodPos;
    /*
     * 物流时效 
     */
    private int transTimeType;
    /*
     * 物流服务
     */
    private int transType;
    /*
     * 面单备注
     */
    private String transMemo;
    /*
     * 是否允许分仓发货
     */
    private boolean isAllowDS;
    /*
     * 是否允许拆商品行 
     */
    private boolean isAllowDSL;
    /*
     * 是否分仓订单需确认
     */
    private boolean isDSLocked;
    /*
     * 接收时间
     */
    private Date receiveTime;
    /*
     * 版本version
     */
    private Date version;
    /*
     * 设置是否可创单定时任务的开始标识
     */
    private Integer beginFlag;
    /*
     * 是否可创建作业单
     */
    private Boolean canFlag;
    /*
     * 仓库组织节点ID
     */
    private Long ou_id;
    /*
     * 设置标识位成功失败的原因
     */
    private String flagResult;
    /*
     * 菜鸟逻辑外包仓单号
     */
    private String vmiOrderCode;
    /*
     * 辅助字段 仓储客户ID
     */
    private Long cus_id;

    /*
     * NIKE特殊逻辑
     */
    private Boolean ismeet;

    /*
     * 记录ERROR_CODE
     */
    // private Integer error_code;

    /**
     * 订单源平台
     */
    private String orderSourcePlatform;


    private BigDecimal points;// 积分

    private BigDecimal returnPoints;// 返点积分

    private Boolean isUrgent;// 是否紧急

    private Boolean isBfOutbountCheck;// 是否检验

    private Boolean isShortPicking;// 是否短配

    private String orderType2;// 订单生成方式

    private Boolean isInsurance;// 是否保价

    private Boolean isWeekendDelivery;// 是否节假日配送


    private String originalLpCode;// 原始物流商

    /**
     * nike 自提点编码
     */
    private String nikePickUpCode;

    /**
     * nike 自提点类型
     */
    private String nikePickUpType;

    /**
     * 是否是自提
     */
    private Boolean nikeIsPick = false;

    /**
     * 错误次数
     */
    private Integer errorCount;

    /**
     * 重新推送到MQ
     */
    private Integer isAgainSendMq;

    /**
     * 发送MQ 时间
     */
    private Date mqLogTime;


    @Column(name = "MQ_LOG_TIME")
    public Date getMqLogTime() {
        return mqLogTime;
    }

    public void setMqLogTime(Date mqLogTime) {
        this.mqLogTime = mqLogTime;
    }

    /**
     * 是否预售 0/空 不是 1：是
     */
    private String isPreSale;


    private Boolean highestPriorityOrder;

    @Column(name = "IS_PRE_SALE")
    public String getIsPreSale() {
        return isPreSale;
    }

    public void setIsPreSale(String isPreSale) {
        this.isPreSale = isPreSale;
    }



    @Column(name = "IS_INSURANCE")
    public Boolean getIsInsurance() {
        return isInsurance;
    }

    public void setIsInsurance(Boolean isInsurance) {
        this.isInsurance = isInsurance;
    }

    @Column(name = "IS_WEEKEND_DELIVERY")
    public Boolean getIsWeekendDelivery() {
        return isWeekendDelivery;
    }

    public void setIsWeekendDelivery(Boolean isWeekendDelivery) {
        this.isWeekendDelivery = isWeekendDelivery;
    }

    @Column(name = "ORDER_TYPE2")
    public String getOrderType2() {
        return orderType2;
    }

    public void setOrderType2(String orderType2) {
        this.orderType2 = orderType2;
    }

    @Column(name = "POINTS")
    public BigDecimal getPoints() {
        return points;
    }

    public void setPoints(BigDecimal points) {
        this.points = points;
    }

    @Column(name = "RETURN_POINTS")
    public BigDecimal getReturnPoints() {
        return returnPoints;
    }

    public void setReturnPoints(BigDecimal returnPoints) {
        this.returnPoints = returnPoints;
    }

    @Column(name = "IS_URGENT")
    public Boolean getIsUrgent() {
        return isUrgent;
    }

    public void setIsUrgent(Boolean isUrgent) {
        this.isUrgent = isUrgent;
    }

    @Column(name = "IS_BF_OUTBOUNT_CHECK")
    public Boolean getIsBfOutbountCheck() {
        return isBfOutbountCheck;
    }

    public void setIsBfOutbountCheck(Boolean isBfOutbountCheck) {
        this.isBfOutbountCheck = isBfOutbountCheck;
    }

    @Column(name = "IS_SHORT_PICKING")
    public Boolean getIsShortPicking() {
        return isShortPicking;
    }

    public void setIsShortPicking(Boolean isShortPicking) {
        this.isShortPicking = isShortPicking;
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_WH_SO_OR_RO", sequenceName = "S_T_WH_SO_OR_RO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_WH_SO_OR_RO")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "SYSTEM_KEY", length = 30)
    public String getSystemKey() {
        return systemKey;
    }

    public void setSystemKey(String systemKey) {
        this.systemKey = systemKey;
    }

    @Column(name = "ORDER_SOURCE", length = 30)
    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
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

    @Column(name = "ACCEPT_OWNERS", length = 500)
    public String getAcceptOwners() {
        return acceptOwners;
    }

    public void setAcceptOwners(String acceptOwners) {
        this.acceptOwners = acceptOwners;
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

    @Column(name = "IS_LOCKED")
    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }

    @Column(name = "PLAN_OUTBOUND_TIME")
    public Date getPlanOutboundTime() {
        return planOutboundTime;
    }

    public void setPlanOutboundTime(Date planOutboundTime) {
        this.planOutboundTime = planOutboundTime;
    }

    @Column(name = "PLAN_ARRIVE_TIME")
    public Date getPlanArriveTime() {
        return planArriveTime;
    }

    public void setPlanArriveTime(Date planArriveTime) {
        this.planArriveTime = planArriveTime;
    }

    @Column(name = "IS_TRANS_UPGRADE")
    public boolean isTransUpgrade() {
        return isTransUpgrade;
    }

    public void setTransUpgrade(boolean isTransUpgrade) {
        this.isTransUpgrade = isTransUpgrade;
    }

    @Column(name = "MEMO", length = 2000)
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Column(name = "COD_AMT", precision = 15, scale = 2)
    public BigDecimal getCodAmt() {
        return codAmt;
    }

    public void setCodAmt(BigDecimal codAmt) {
        this.codAmt = codAmt;
    }

    @Column(name = "ORDER_AMT", precision = 15, scale = 2)
    public BigDecimal getOrderAmt() {
        return orderAmt;
    }

    public void setOrderAmt(BigDecimal orderAmt) {
        this.orderAmt = orderAmt;
    }

    @Column(name = "ORDER_DISCOUNT", precision = 15, scale = 2)
    public BigDecimal getOrderDiscount() {
        return orderDiscount;
    }

    public void setOrderDiscount(BigDecimal orderDiscount) {
        this.orderDiscount = orderDiscount;
    }

    @Column(name = "FREIGHT", precision = 15, scale = 2)
    public BigDecimal getFreight() {
        return freight;
    }

    public void setFreight(BigDecimal freight) {
        this.freight = freight;
    }

    @Column(name = "ORDER_CREATE_TIME")
    public Date getOrderCreateTime() {
        return orderCreateTime;
    }

    public void setOrderCreateTime(Date orderCreateTime) {
        this.orderCreateTime = orderCreateTime;
    }

    @Column(name = "STORE_CODE", length = 30)
    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    @Column(name = "ACTIVE_CODE", length = 50)
    public String getActiveCode() {
        return activeCode;
    }

    public void setActiveCode(String activeCode) {
        this.activeCode = activeCode;
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

    @Column(name = "ADDRESS", length = 50)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "ADDRESS1", length = 50)
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

    @Column(name = "ORDER_USER_MAIL", length = 100)
    public String getOrderUserMail() {
        return orderUserMail;
    }

    public void setOrderUserMail(String orderUserMail) {
        this.orderUserMail = orderUserMail;
    }

    @Column(name = "ORDER_USER_CODE", length = 100)
    public String getOrderUserCode() {
        return orderUserCode;
    }

    public void setOrderUserCode(String orderUserCode) {
        this.orderUserCode = orderUserCode;
    }

    @Column(name = "TRANS_CODE", length = 20)
    public String getTransCode() {
        return transCode;
    }

    public void setTransCode(String transCode) {
        this.transCode = transCode;
    }

    @Column(name = "TRNAS_NO", length = 50)
    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    @Column(name = "IS_COD")
    public boolean isCod() {
        return isCod;
    }

    public void setCod(boolean isCod) {
        this.isCod = isCod;
    }

    @Column(name = "TRANS_TIME_TYPE")
    public int getTransTimeType() {
        return transTimeType;
    }

    public void setTransTimeType(int transTimeType) {
        this.transTimeType = transTimeType;
    }

    @Column(name = "TRANS_MEMO", length = 20)
    public String getTransMemo() {
        return transMemo;
    }

    public void setTransMemo(String transMemo) {
        this.transMemo = transMemo;
    }

    @Column(name = "IS_ALLOW_DS")
    public boolean isAllowDS() {
        return isAllowDS;
    }

    public void setAllowDS(boolean isAllowDS) {
        this.isAllowDS = isAllowDS;
    }

    @Column(name = "IS_ALLOW_DSL")
    public boolean isAllowDSL() {
        return isAllowDSL;
    }

    public void setAllowDSL(boolean isAllowDSL) {
        this.isAllowDSL = isAllowDSL;
    }

    @Column(name = "IS_DS_LOCKED")
    public boolean isDSLocked() {
        return isDSLocked;
    }

    public void setDSLocked(boolean isDSLocked) {
        this.isDSLocked = isDSLocked;
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



    @Column(name = "CAN_FLAG")
    public Boolean getCanFlag() {
        return canFlag;
    }

    @Column(name = "BEGIN_FLAG")
    public Integer getBeginFlag() {
        return beginFlag;
    }

    public void setBeginFlag(Integer beginFlag) {
        this.beginFlag = beginFlag;
    }

    public void setCanFlag(Boolean canFlag) {
        this.canFlag = canFlag;
    }

    @Column(name = "OU_ID")
    public Long getOu_id() {
        return ou_id;
    }

    public void setOu_id(Long ou_id) {
        this.ou_id = ou_id;
    }

    @Column(name = "FLAG_RESULT", length = 2000)
    public String getFlagResult() {
        return flagResult;
    }

    public void setFlagResult(String flagResult) {
        if (flagResult != null && flagResult.length() > 2000) {
            flagResult = flagResult.substring(0, 1999);
        }
        this.flagResult = flagResult;
    }

    @Column(name = "VMI_ORDER_CODE", length = 30)
    public String getVmiOrderCode() {
        return vmiOrderCode;
    }

    public void setVmiOrderCode(String vmiOrderCode) {
        this.vmiOrderCode = vmiOrderCode;
    }

    @Column(name = "CUS_ID")
    public Long getCus_id() {
        return cus_id;
    }

    public void setCus_id(Long cus_id) {
        this.cus_id = cus_id;
    }

    @Column(name = "CONVENIENCE_STORE", length = 30)
    public String getConvenienceStore() {
        return convenienceStore;
    }

    public void setConvenienceStore(String convenienceStore) {
        this.convenienceStore = convenienceStore;
    }

    @Column(name = "IS_COD_POS")
    public boolean isCodPos() {
        return isCodPos;
    }

    public void setCodPos(boolean isCodPos) {
        this.isCodPos = isCodPos;
    }

    @Column(name = "TRANS_TYPE")
    public int getTransType() {
        return transType;
    }

    public void setTransType(int transType) {
        this.transType = transType;
    }

    @Column(name = "is_meet")
    public Boolean getIsmeet() {
        return ismeet;
    }

    public void setIsmeet(Boolean ismeet) {
        this.ismeet = ismeet;
    }

    @Column(name = "slip_code2")
    public String getSlipCode2() {
        return slipCode2;
    }

    public void setSlipCode2(String slipCode2) {
        this.slipCode2 = slipCode2;
    }

    @Column(name = "ORDER_SOURCE_PLATFORM")
    public String getOrderSourcePlatform() {
        return orderSourcePlatform;
    }

    public void setOrderSourcePlatform(String orderSourcePlatform) {
        this.orderSourcePlatform = orderSourcePlatform;
    }

    @Column(name = "NIKE_PICKUP_TYPE")
    public String getNikePickUpType() {
        return nikePickUpType;
    }

    public void setNikePickUpType(String nikePickUpType) {
        this.nikePickUpType = nikePickUpType;
    }


    @Column(name = "NIKE_IS_Pick")
    public Boolean getNikeIsPick() {
        return nikeIsPick;
    }

    public void setNikeIsPick(Boolean nikeIsPick) {
        this.nikeIsPick = nikeIsPick;
    }

    @Column(name = "NIKE_PICKUP_CODE")
    public String getNikePickUpCode() {
        return nikePickUpCode;
    }

    public void setNikePickUpCode(String nikePickUpCode) {
        this.nikePickUpCode = nikePickUpCode;
    }

    @Column(name = "ORIGINAL_LP_CODE")
    public String getOriginalLpCode() {
        return originalLpCode;
    }

    public void setOriginalLpCode(String originalLpCode) {
        this.originalLpCode = originalLpCode;
    }

    @Column(name = "ERROR_COUNT")
    public Integer getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Integer errorCount) {
        this.errorCount = errorCount;
    }

    @Column(name = "is_again_send_mq")
    public Integer getIsAgainSendMq() {
        return isAgainSendMq;
    }

    public void setIsAgainSendMq(Integer isAgainSendMq) {
        this.isAgainSendMq = isAgainSendMq;
    }



    @Column(name = "highest_priority_order")
    public Boolean getHighestPriorityOrder() {
        return highestPriorityOrder;
    }

    public void setHighestPriorityOrder(Boolean highestPriorityOrder) {
        this.highestPriorityOrder = highestPriorityOrder;
    }

}
