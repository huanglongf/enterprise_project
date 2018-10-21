package com.jumbo.wms.model.hub2wms;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.jumbo.wms.model.BaseModel;

/**
 * 过仓HUB2WMS 销售订单
 * 
 * @author jinlong.ke
 * 
 */
public class WmsSalesOrder extends BaseModel {

    private static final long serialVersionUID = -4464156002233483900L;
    /*
     * 订单来源 
     */
    private String orderSource;
    /*
     * 订单号(唯一对接标识)
     */
    private String orderCode;
    /*
     * 原始销售单号
     */
    private String sourceOrderCode;
    /*
     * NIKE
     */
    private String slipCode2;
    /*
     * 外包仓单据编码
     */
    private String vmiOrderCode;
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
     * 订单支付信息 
     */
    private List<WmsSalesOrderPayment> payments;
    /*
     * 发票信息
     */
    private List<WmsOrderInvoice> invoices;
    /*
     * 订单包装
     */
    private List<WmsOrderPacking> packingList;
    /*
     * 订单明细
     */
    private List<WmsSalesOrderLine> lines;
    /*
     * 增值服务列表
     */
    private List<ValueAddedService> vasList;

    /**
     * 订单源平台
     */
    private String orderSourcePlatform;

    private BigDecimal points;// 积分

    private BigDecimal returnPoints;// 返点积分

    private Boolean isUrgent;// 是否紧急

    private Boolean isBfOutbountCheck;// 是否检验

    private Boolean isShortPicking = false;// 是否短配

    private String refWarehouseCode;// 外部相关仓库编码

    private String orderType2;// 订单生成方式

    private Boolean isInsurance;// 是否保价

    private Boolean isWeekendDelivery;// 是否节假日配送

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
     * 是否预售 0/空 不是 1：是
     */
    private String isPreSale;
    
    private String sendTimeMq;// mq发送时间


    public String getSendTimeMq() {
        return sendTimeMq;
    }

    public void setSendTimeMq(String sendTimeMq) {
        this.sendTimeMq = sendTimeMq;
    }

    public String getIsPreSale() {
        return isPreSale;
    }

    public void setIsPreSale(String isPreSale) {
        this.isPreSale = isPreSale;
    }

    public Boolean getNikeIsPick() {
        return nikeIsPick;
    }

    public void setNikeIsPick(Boolean nikeIsPick) {
        this.nikeIsPick = nikeIsPick;
    }

    public String getNikePickUpType() {
        return nikePickUpType;
    }

    public void setNikePickUpType(String nikePickUpType) {
        this.nikePickUpType = nikePickUpType;
    }

    public String getNikePickUpCode() {
        return nikePickUpCode;
    }

    public void setNikePickUpCode(String nikePickUpCode) {
        this.nikePickUpCode = nikePickUpCode;
    }

    public Boolean getIsInsurance() {
        return isInsurance;
    }

    public void setIsInsurance(Boolean isInsurance) {
        this.isInsurance = isInsurance;
    }

    public Boolean getIsWeekendDelivery() {
        return isWeekendDelivery;
    }

    public void setIsWeekendDelivery(Boolean isWeekendDelivery) {
        this.isWeekendDelivery = isWeekendDelivery;
    }

    public String getOrderType2() {
        return orderType2;
    }

    public void setOrderType2(String orderType2) {
        this.orderType2 = orderType2;
    }

    public String getRefWarehouseCode() {
        return refWarehouseCode;
    }

    public void setRefWarehouseCode(String refWarehouseCode) {
        this.refWarehouseCode = refWarehouseCode;
    }

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


    public Boolean getIsShortPicking() {
        return isShortPicking;
    }

    public void setIsShortPicking(Boolean isShortPicking) {
        this.isShortPicking = isShortPicking;
    }

    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
    }

    public String getOrderCode() {
        return orderCode;
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

    public String getAcceptOwners() {
        return acceptOwners;
    }

    public void setAcceptOwners(String acceptOwners) {
        this.acceptOwners = acceptOwners;
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

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }

    public Date getPlanOutboundTime() {
        return planOutboundTime;
    }

    public void setPlanOutboundTime(Date planOutboundTime) {
        this.planOutboundTime = planOutboundTime;
    }

    public Date getPlanArriveTime() {
        return planArriveTime;
    }

    public void setPlanArriveTime(Date planArriveTime) {
        this.planArriveTime = planArriveTime;
    }

    public boolean isTransUpgrade() {
        return isTransUpgrade;
    }

    public void setTransUpgrade(boolean isTransUpgrade) {
        this.isTransUpgrade = isTransUpgrade;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public BigDecimal getCodAmt() {
        return codAmt;
    }

    public void setCodAmt(BigDecimal codAmt) {
        this.codAmt = codAmt;
    }

    public BigDecimal getOrderAmt() {
        return orderAmt;
    }

    public void setOrderAmt(BigDecimal orderAmt) {
        this.orderAmt = orderAmt;
    }

    public BigDecimal getOrderDiscount() {
        return orderDiscount;
    }

    public void setOrderDiscount(BigDecimal orderDiscount) {
        this.orderDiscount = orderDiscount;
    }

    public BigDecimal getFreight() {
        return freight;
    }

    public void setFreight(BigDecimal freight) {
        this.freight = freight;
    }

    public Date getOrderCreateTime() {
        return orderCreateTime;
    }

    public void setOrderCreateTime(Date orderCreateTime) {
        this.orderCreateTime = orderCreateTime;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getActiveCode() {
        return activeCode;
    }

    public void setActiveCode(String activeCode) {
        this.activeCode = activeCode;
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

    public String getOrderUserMail() {
        return orderUserMail;
    }

    public void setOrderUserMail(String orderUserMail) {
        this.orderUserMail = orderUserMail;
    }

    public String getOrderUserCode() {
        return orderUserCode;
    }

    public void setOrderUserCode(String orderUserCode) {
        this.orderUserCode = orderUserCode;
    }

    public String getTransCode() {
        return transCode;
    }

    public void setTransCode(String transCode) {
        this.transCode = transCode;
    }

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    public boolean isCod() {
        return isCod;
    }

    public void setCod(boolean isCod) {
        this.isCod = isCod;
    }

    public int getTransTimeType() {
        return transTimeType;
    }

    public void setTransTimeType(int transTimeType) {
        this.transTimeType = transTimeType;
    }

    public String getTransMemo() {
        return transMemo;
    }

    public void setTransMemo(String transMemo) {
        this.transMemo = transMemo;
    }

    public boolean isAllowDS() {
        return isAllowDS;
    }

    public void setAllowDS(boolean isAllowDS) {
        this.isAllowDS = isAllowDS;
    }

    public boolean isAllowDSL() {
        return isAllowDSL;
    }

    public void setAllowDSL(boolean isAllowDSL) {
        this.isAllowDSL = isAllowDSL;
    }

    public boolean isDSLocked() {
        return isDSLocked;
    }

    public void setDSLocked(boolean isDSLocked) {
        this.isDSLocked = isDSLocked;
    }

    public List<WmsSalesOrderPayment> getPayments() {
        return payments;
    }

    public void setPayments(List<WmsSalesOrderPayment> payments) {
        this.payments = payments;
    }

    public List<WmsOrderInvoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<WmsOrderInvoice> invoices) {
        this.invoices = invoices;
    }

    public List<WmsOrderPacking> getPackingList() {
        return packingList;
    }

    public void setPackingList(List<WmsOrderPacking> packingList) {
        this.packingList = packingList;
    }

    public List<WmsSalesOrderLine> getLines() {
        return lines;
    }

    public void setLines(List<WmsSalesOrderLine> lines) {
        this.lines = lines;
    }

    public List<ValueAddedService> getVasList() {
        return vasList;
    }

    public void setVasList(List<ValueAddedService> vasList) {
        this.vasList = vasList;
    }

    public String getVmiOrderCode() {
        return vmiOrderCode;
    }

    public void setVmiOrderCode(String vmiOrderCode) {
        this.vmiOrderCode = vmiOrderCode;
    }

    public String getConvenienceStore() {
        return convenienceStore;
    }

    public void setConvenienceStore(String convenienceStore) {
        this.convenienceStore = convenienceStore;
    }

    public int getTransType() {
        return transType;
    }

    public void setTransType(int transType) {
        this.transType = transType;
    }

    public boolean isCodPos() {
        return isCodPos;
    }

    public void setCodPos(boolean isCodPos) {
        this.isCodPos = isCodPos;
    }

    public String getSlipCode2() {
        return slipCode2;
    }

    public void setSlipCode2(String slipCode2) {
        this.slipCode2 = slipCode2;
    }

    public String getOrderSourcePlatform() {
        return orderSourcePlatform;
    }

    public void setOrderSourcePlatform(String orderSourcePlatform) {
        this.orderSourcePlatform = orderSourcePlatform;
    }



}
