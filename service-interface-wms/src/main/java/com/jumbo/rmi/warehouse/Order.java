package com.jumbo.rmi.warehouse;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;


/**
 * EBS 接口订单信息
 * 
 * @author jinlong.ke
 * 
 */
public class Order implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -1948569177610713342L;
    /**
     * 客户编码
     */
    private String customerCode;

    private Long customerId;
    /**
     * 前置单据号
     */
    private String code;
    /**
     * 相关单据号1
     */
    private String slipCode1;
    /**
     * 相关单据号2
     */
    private String slipCode2;
    /**
     * 商城自主退货编码
     */
    private String slipCode3;
    /**
     * 共享渠道列表
     */
    private String channelList;
    /**
     * 仓库组织Id
     */
    private Long mainWhOuId;
    /**
     * 仓库组织编码
     */
    private String mainWhOuCode;
    /**
     * 附加仓库组织编码
     */
    private String addWhOuCode;
    /**
     * 附加仓库组织Id
     */
    private Long addWhOuId;
    /**
     * 是否创建后锁定
     */
    private Boolean isLocked;
    /**
     * 店铺
     */
    private String owner;
    /**
     * 附件店铺用于转店入库
     */
    private String addOwner;
    /**
     * 单据类型： 201，福利领用 202，固定资产领用
     */
    private int type;
    /**
     * 库存状态
     */
    private String invStatus;
    /**
     * 附加库存状态
     */
    private String invAddStatus;
    /**
     * 库存状态ID
     */
    private Long invStatusId;
    /**
     * 附加库存状态ID
     */
    private Long invAddStatusId;
    /**
     * 备注
     */
    private String memo;
    /**
     * 是否陆运
     */
    private Boolean isRailWay;
    /**
     * 是否特殊包装
     */
    private Boolean isSpecialPackaging;
    /**
     * 前置单据类型
     */
    private int slipType;
    /**
     * 运费
     */
    private BigDecimal orderTranserFree;
    /**
     * 是否需要发票
     */
    private Boolean isInvoice;
    /**
     * 订单明细
     */
    private List<OrderLine> lines;
    /**
     * 订单配送信息
     */
    private OrderDeliveryInfo deliveryInfo;

    // 新增字段
    /**
     * COD订单用户需支付运费
     */
    private BigDecimal transferFee;
    /**
     * 单据总金额, COD订单用户需支付商品金额, 销售订单必填
     */
    private BigDecimal totalActual;

    /**
     * 订单成交金额 销售订单必填
     */
    private BigDecimal orderTotalActual;
    /**
     * 订单创建时间
     */
    private Date orderCreateTime;
    /**
     * 活动
     */
    private String activitySource;

    /**
     * 发票列表(isInvoive=true时比填)
     */
    private List<Invoice> invoiceList;
    /**
     * 订单折扣前总金额
     */
    private BigDecimal orderTotalBfDiscount;

    private BigDecimal codAmount;

    /**
     * 供应商
     */
    private String supplierName;

    /**
     * 付款方式
     */
    private String paymentType;

    /**
     * 付款时间
     */
    private Date paymentTime;

    /**
     * 特殊处理类型
     */
    private Integer SpecialType;

    /**
     * 目的地编码
     */
    private String toLocation;

    /**
     * 特殊类型
     */
    private OrderSpecialExecute orderSpecialExecute;
    
    
    /**
     * 特殊类型
     */
    private List<OrderSpecialExecute> orderSpecialExecuteList;
    /**
     * 预计发货时间
     */
    private Date planOutboundTime;
    /**
     * 预计送达时间
     */
    private Date planArriveTime;
    /**
     * 快递是否需要升级
     */
    private Boolean isTransUpgrade;
    /**
     * 送货时间
     */
    private Date ArriveTime;
    /**
     * 送货时间类型
     */
    private String ArriveTimeType;
    /**
     * 门店标识
     */
    private String storecode;

    /**
     * 订单源平台
     */
    private String orderSourcePlatform;
    /**
     * MKPOS编码
     */
    private String mkPosCode;

    /**
     * 费用列表
     * 
     * @return
     */
    private List<OrderStaPayment> orderPays;

    /**
     * <<<<<<< HEAD nike 自提点编码
     */
    private String nikePickUpCode;

    /**
     * nike 自提点类型
     */
    private String nikePickUpType;

    /**
     * 是否是自提
     */
    private Boolean isNikePick = false;

    /**
     * 不是OMS或PACS的订单
     */
    private Boolean isNotPacsomsOrder = false;

    /**
     * 是否是唯品会批量销售业务 0/空 不是 1：是
     */
    private String isPf;

    /**
     * 装箱清单是否打印价格
     */
    private Boolean isPrintPrice = false;
    /**
     * 是否预售 0/空 不是 1：是
     */
    private String isPreSale;

    private String extMemo2;// 星巴克 定制 json

    /**
     * 是否澳门件
     */
    private Boolean isMacaoOrder = false;

    /**
     * 是否申请电子发票
     */
    private Boolean isOnlineInvoice = false;

    private String sendTimeMq;

    public String getSendTimeMq() {
        return sendTimeMq;
    }

    public void setSendTimeMq(String sendTimeMq) {
        this.sendTimeMq = sendTimeMq;
    }

    public Boolean getIsOnlineInvoice() {
        if (isOnlineInvoice == null) {
            return false;
        } else {
            return isOnlineInvoice;
        }
    }

    public void setIsOnlineInvoice(Boolean isOnlineInvoice) {
        if (isOnlineInvoice == null) {
            this.isOnlineInvoice = false;
        } else {
            this.isOnlineInvoice = isOnlineInvoice;
        }
    }

    public Boolean getIsMacaoOrder() {
        if (isMacaoOrder == null) {
            return false;
        } else {
            return isMacaoOrder;
        }
    }

    public void setIsMacaoOrder(Boolean isMacaoOrder) {
        if (isMacaoOrder == null) {
            this.isMacaoOrder = false;
        } else {
            this.isMacaoOrder = isMacaoOrder;
        }
    }


    public String getExtMemo2() {
        return extMemo2;
    }

    public void setExtMemo2(String extMemo2) {
        this.extMemo2 = extMemo2;
    }

    public String getIsPreSale() {
        return isPreSale;
    }

    public void setIsPreSale(String isPreSale) {
        this.isPreSale = isPreSale;
    }

    public String getIsPf() {
        return isPf;
    }

    public void setIsPf(String isPf) {
        this.isPf = isPf;
    }

    public String getNikePickUpCode() {
        return nikePickUpCode;
    }

    public void setNikePickUpCode(String nikePickUpCode) {
        this.nikePickUpCode = nikePickUpCode;
    }

    public String getNikePickUpType() {
        return nikePickUpType;
    }

    public void setNikePickUpType(String nikePickUpType) {
        this.nikePickUpType = nikePickUpType;
    }

    public Boolean getIsNikePick() {
        return isNikePick;
    }

    public void setIsNikePick(Boolean isNikePick) {
        this.isNikePick = isNikePick;
    }


    public List<OrderStaPayment> getOrderPays() {
        return orderPays;
    }

    public void setOrderPays(List<OrderStaPayment> orderPays) {
        this.orderPays = orderPays;
    }

    public BigDecimal getCodAmount() {
        return codAmount;
    }

    public void setCodAmount(BigDecimal codAmount) {
        this.codAmount = codAmount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSlipCode1() {
        return slipCode1;
    }

    public void setSlipCode1(String slipCode1) {
        this.slipCode1 = slipCode1;
    }

    public String getSlipCode2() {
        return slipCode2;
    }

    public void setSlipCode2(String slipCode2) {
        this.slipCode2 = slipCode2;
    }

    public Long getMainWhOuId() {
        return mainWhOuId;
    }

    public void setMainWhOuId(Long mainWhOuId) {
        this.mainWhOuId = mainWhOuId;
    }

    public Long getAddWhOuId() {
        return addWhOuId;
    }

    public void setAddWhOuId(Long addWhOuId) {
        this.addWhOuId = addWhOuId;
    }



    public Boolean getIsLocked() {
        return isLocked;
    }

    public String getMainWhOuCode() {
        return mainWhOuCode;
    }

    public void setMainWhOuCode(String mainWhOuCode) {
        this.mainWhOuCode = mainWhOuCode;
    }

    public String getAddWhOuCode() {
        return addWhOuCode;
    }

    public void setAddWhOuCode(String addWhOuCode) {
        this.addWhOuCode = addWhOuCode;
    }

    public void setIsLocked(Boolean isLocked) {
        this.isLocked = isLocked;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAddOwner() {
        return addOwner;
    }

    public void setAddOwner(String addOwner) {
        this.addOwner = addOwner;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Long getInvAddStatusId() {
        return invAddStatusId;
    }

    public void setInvAddStatusId(Long invAddStatusId) {
        this.invAddStatusId = invAddStatusId;
    }

    public Long getInvStatusId() {
        return invStatusId;
    }

    public void setInvStatusId(Long invStatusId) {
        this.invStatusId = invStatusId;
    }

    public String getInvStatus() {
        return invStatus;
    }

    public void setInvStatus(String invStatus) {
        this.invStatus = invStatus;
    }

    public String getInvAddStatus() {
        return invAddStatus;
    }

    public void setInvAddStatus(String invAddStatus) {
        this.invAddStatus = invAddStatus;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Boolean getIsRailWay() {
        return isRailWay;
    }

    public void setIsRailWay(Boolean isRailWay) {
        this.isRailWay = isRailWay;
    }

    public Boolean getIsSpecialPackaging() {
        return isSpecialPackaging;
    }

    public void setIsSpecialPackaging(Boolean isSpecialPackaging) {
        this.isSpecialPackaging = isSpecialPackaging;
    }

    public int getSlipType() {
        return slipType;
    }

    public void setSlipType(int slipType) {
        this.slipType = slipType;
    }

    public BigDecimal getTransferFee() {
        return transferFee;
    }

    public void setTransferFee(BigDecimal transferFee) {
        this.transferFee = transferFee;
    }

    public BigDecimal getTotalActual() {
        return totalActual;
    }

    public void setTotalActual(BigDecimal totalActual) {
        this.totalActual = totalActual;
    }

    public Boolean getIsInvoice() {
        return isInvoice;
    }

    public void setIsInvoice(Boolean isInvoice) {
        this.isInvoice = isInvoice;
    }

    public List<OrderLine> getLines() {
        return lines;
    }

    public void setLines(List<OrderLine> lines) {
        this.lines = lines;
    }

    public OrderDeliveryInfo getDeliveryInfo() {
        return deliveryInfo;
    }

    public void setDeliveryInfo(OrderDeliveryInfo deliveryInfo) {
        this.deliveryInfo = deliveryInfo;
    }

    public BigDecimal getOrderTranserFree() {
        return orderTranserFree;
    }

    public void setOrderTranserFree(BigDecimal orderTranserFree) {
        this.orderTranserFree = orderTranserFree;
    }

    public BigDecimal getOrderTotalActual() {
        return orderTotalActual;
    }

    public void setOrderTotalActual(BigDecimal orderTotalActual) {
        this.orderTotalActual = orderTotalActual;
    }

    public Date getOrderCreateTime() {
        return orderCreateTime;
    }

    public void setOrderCreateTime(Date orderCreateTime) {
        this.orderCreateTime = orderCreateTime;
    }

    public List<Invoice> getInvoiceList() {
        return invoiceList;
    }

    public void setInvoiceList(List<Invoice> invoiceList) {
        this.invoiceList = invoiceList;
    }

    public String getActivitySource() {
        return activitySource;
    }

    public void setActivitySource(String activitySource) {
        this.activitySource = activitySource;
    }

    public BigDecimal getOrderTotalBfDiscount() {
        return orderTotalBfDiscount;
    }

    public void setOrderTotalBfDiscount(BigDecimal orderTotalBfDiscount) {
        this.orderTotalBfDiscount = orderTotalBfDiscount;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getChannelList() {
        return channelList;
    }

    public void setChannelList(String channelList) {
        this.channelList = channelList;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    public Integer getSpecialType() {
        return SpecialType;
    }

    public void setSpecialType(Integer specialType) {
        SpecialType = specialType;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public OrderSpecialExecute getOrderSpecialExecute() {
        return orderSpecialExecute;
    }

    public void setOrderSpecialExecute(OrderSpecialExecute orderSpecialExecute) {
        this.orderSpecialExecute = orderSpecialExecute;
    }

    public String getSlipCode3() {
        return slipCode3;
    }

    public void setSlipCode3(String slipCode3) {
        this.slipCode3 = slipCode3;
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

    public Boolean getIsTransUpgrade() {
        return isTransUpgrade;
    }

    public void setIsTransUpgrade(Boolean isTransUpgrade) {
        this.isTransUpgrade = isTransUpgrade;
    }

    public Date getArriveTime() {
        return ArriveTime;
    }

    public void setArriveTime(Date arriveTime) {
        ArriveTime = arriveTime;
    }

    public String getArriveTimeType() {
        return ArriveTimeType;
    }

    public void setArriveTimeType(String arriveTimeType) {
        ArriveTimeType = arriveTimeType;
    }

    public String getOrderSourcePlatform() {
        return orderSourcePlatform;
    }

    public void setOrderSourcePlatform(String orderSourcePlatform) {
        this.orderSourcePlatform = orderSourcePlatform;
    }

    @Column(name = "store_code")
    public String getStorecode() {
        return storecode;
    }

    public void setStorecode(String storecode) {
        this.storecode = storecode;
    }

    public String getMkPosCode() {
        return mkPosCode;
    }

    public void setMkPosCode(String mkPosCode) {
        this.mkPosCode = mkPosCode;
    }

    public Boolean getIsNotPacsomsOrder() {
        if (isNotPacsomsOrder != null) {
            return isNotPacsomsOrder;
        } else {
            return false;
        }
    }

    public void setIsNotPacsomsOrder(Boolean isNotPacsomsOrder) {
        if (isNotPacsomsOrder != null) {
            this.isNotPacsomsOrder = isNotPacsomsOrder;
        } else {
            this.isNotPacsomsOrder = false;
        }
    }

    public Boolean getIsPrintPrice() {
        if (isPrintPrice == null) {
            return false;
        }
        return isPrintPrice;
    }

    public void setIsPrintPrice(Boolean isPrintPrice) {
        if (isPrintPrice == null) {
            this.isPrintPrice = false;
        }
        this.isPrintPrice = isPrintPrice;
    }

    public List<OrderSpecialExecute> getOrderSpecialExecuteList() {
        return orderSpecialExecuteList;
    }

    public void setOrderSpecialExecuteList(List<OrderSpecialExecute> orderSpecialExecuteList) {
        this.orderSpecialExecuteList = orderSpecialExecuteList;
    }

}
