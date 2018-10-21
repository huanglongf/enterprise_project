package com.jumbo.wms.model.warehouse;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;

/**
 * 待创建队列-订单信息
 * 
 * @author jumbo
 * 
 */
@Entity
@Table(name = "T_WH_Q_STA")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class QueueSta extends BaseModel {
    /**
	 * 
	 */
    private static final long serialVersionUID = -6763686761757069986L;
    /**
     * PK
     */
    private Long id;
    /**
     * 前置单据号
     */
    private String ordercode;
    /**
     * 前置单据号1
     */
    private String slipcode1;
    /**
     * 前置单据号2
     */
    private String slipcode2;
    /**
     * 商城自主退货编码
     */
    private String slipCode3;
    /**
     * 主仓库ID
     */
    private Long mainwhouid;
    /**
     * 附属仓库ID
     */
    private Long addwhouid;
    /**
     * 创建收是否锁定
     */
    private Boolean iscreatedlocked;
    /**
     * 店铺
     */
    private String owner;
    /**
     * 附属店铺
     */
    private String addOwner;
    /**
     * 单据类型
     */
    /**
     * 销售类必填，规则“;”分隔
     */
    private String channelList;
    /**
     * 客户ID
     */
    private Long customerId;
    private int type;
    /**
     * 库存状态ID
     */
    private Long invstatusid;
    /**
     * 附属库存状态ID
     */
    private Long invaddstatusid;
    /**
     * 是否特殊处理
     */
    private Boolean isspecialpackaging;
    /**
     * 订单商品金额
     */
    private BigDecimal totalactual;
    /**
     * 订单商品金额
     */
    private BigDecimal ordertaotalactual;
    /**
     * 订单运费
     */
    private BigDecimal transferfee;
    /**
     * 订单运费
     */
    private BigDecimal ordertransferfree;
    /**
     * 订单创建时间
     */
    private Date ordercreatetime;
    /**
     * 批次号
     */
    private String batchcode;
    /**
     * 错误次数
     */
    private Integer errorcount;

    /**
     * MQ
     */
    private Integer errorMqCount;
    /**
     * 活动
     */
    private String activitysource;
    /**
     * 订单折前金额
     */
    private BigDecimal ordertotalbfdiscount;
    /**
     * 物流相关信息
     */
    private QueueStaDeliveryInfo queueStaDeliveryInfo;
    /**
     * 是否需要发票
     */
    private Boolean isInvoice;
    /**
     * 商品总数量
     */
    private Long skuqty;
    /**
     * 仓库组织编码
     */
    private String mainWhOuCode;
    /**
     * 附加仓库组织编码
     */
    private String addWhOuCode;
    /**
     * 特殊处理类型
     */
    private SpecialSkuType specialType;
    /**
     * 目的地编码
     */
    private String toLocation;
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
     * 重新推送到MQ
     */
    private Integer isAgainSendMq;

    /**
     * 订单源平台
     */
    private String orderSourcePlatform;
    /**
     * 门店标识
     */
    private String storecode;
    /**
     * 标识创单
     */
    private Boolean canflag;
    /**
     * 标识开始
     */
    // private Boolean beginflag;

    private Integer beginflag;
    /**
     * 失败原因
     */
    private String flagResult;

    /**
     * 是否预售 0/空 不是 1：是
     */
    private String isPreSale;

    /**
     * 装箱清单是否打印价格(gucci定制)
     */
    private Boolean isPrintPrice = false;

    /**
     * 是否澳门件
     */
    private Boolean isMacaoOrder = false;

    /**
     * 版本号
     */
    private int version;

    /**
     * MKPOS编码
     */
    private String mkPosCode;
    /**
     * 备注
     */
    private String memo;

    private String extMemo2;// 星巴克 定制 json

    /**
     * 是否申请电子发票
     */
    private Boolean isOnlineInvoice = false;

    @Column(name = "IS_ONLINE_INVOICE")
    public Boolean getIsOnlineInvoice() {
        return isOnlineInvoice;
    }

    public void setIsOnlineInvoice(Boolean isOnlineInvoice) {
        this.isOnlineInvoice = isOnlineInvoice;
    }
	
	
 @Column(name = "IS_MACAO_ORDER")
    public Boolean getIsMacaoOrder() {
        return isMacaoOrder;
    }

    public void setIsMacaoOrder(Boolean isMacaoOrder) {
        this.isMacaoOrder = isMacaoOrder;
    }

    @Column(name = "EXT_MEMO2")
    public String getExtMemo2() {
        return extMemo2;
    }

    public void setExtMemo2(String extMemo2) {
        this.extMemo2 = extMemo2;
    }

    @Column(name = "IS_PRE_SALE")
    public String getIsPreSale() {
        return isPreSale;
    }

    public void setIsPreSale(String isPreSale) {
        this.isPreSale = isPreSale;
    }

    @Version
    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Column(name = "main_ou_code")
    public String getMainWhOuCode() {
        return mainWhOuCode;
    }

    public void setMainWhOuCode(String mainWhOuCode) {
        this.mainWhOuCode = mainWhOuCode;
    }

    @Column(name = "add_ou_code")
    public String getAddWhOuCode() {
        return addWhOuCode;
    }

    public void setAddWhOuCode(String addWhOuCode) {
        this.addWhOuCode = addWhOuCode;
    }

    private BigDecimal codAmount;

    @Column(name = "cod_Amount")
    public BigDecimal getCodAmount() {
        return codAmount;
    }

    public void setCodAmount(BigDecimal codAmount) {
        this.codAmount = codAmount;
    }

    @Column(name = "sku_qty")
    public Long getSkuqty() {
        return skuqty;
    }

    public void setSkuqty(Long skuqty) {
        this.skuqty = skuqty;
    }

    private Date createtime;

    @Column(name = "customer_Id")
    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Column(name = "create_time")
    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    @Column(name = "channel_list")
    public String getChannelList() {
        return channelList;
    }

    public void setChannelList(String channelList) {
        this.channelList = channelList;
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_Q_STA", sequenceName = "S_T_WH_Q_STA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_Q_STA")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "order_code", length = 100)
    public String getOrdercode() {
        return ordercode;
    }

    public void setOrdercode(String ordercode) {
        this.ordercode = ordercode;
    }

    @Column(name = "slip_code1", length = 100)
    public String getSlipcode1() {
        return slipcode1;
    }

    public void setSlipcode1(String slipcode1) {
        this.slipcode1 = slipcode1;
    }

    @Column(name = "slip_code2", length = 100)
    public String getSlipcode2() {
        return slipcode2;
    }

    public void setSlipcode2(String slipcode2) {
        this.slipcode2 = slipcode2;
    }

    @Column(name = "main_wh_ou_id")
    public Long getMainwhouid() {
        return mainwhouid;
    }

    public void setMainwhouid(Long mainwhouid) {
        this.mainwhouid = mainwhouid;
    }

    @Column(name = "add_wh_ou_id")
    public Long getAddwhouid() {
        return addwhouid;
    }

    public void setAddwhouid(Long addwhouid) {
        this.addwhouid = addwhouid;
    }

    @Column(name = "is_created_locked")
    public Boolean getIscreatedlocked() {
        return iscreatedlocked;
    }

    public void setIscreatedlocked(Boolean iscreatedlocked) {
        this.iscreatedlocked = iscreatedlocked;
    }

    @Column(name = "owner", length = 100)
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Column(name = "addOwner", length = 100)
    public String getAddOwner() {
        return addOwner;
    }

    public void setAddOwner(String addOwner) {
        this.addOwner = addOwner;
    }

    @Column(name = "type")
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Column(name = "inv_status_id")
    public Long getInvstatusid() {
        return invstatusid;
    }

    public void setInvstatusid(Long invstatusid) {
        this.invstatusid = invstatusid;
    }

    @Column(name = "inv_add_status_id")
    public Long getInvaddstatusid() {
        return invaddstatusid;
    }

    public void setInvaddstatusid(Long invaddstatusid) {
        this.invaddstatusid = invaddstatusid;
    }

    @Column(name = "is_special_packaging")
    public Boolean getIsspecialpackaging() {
        return isspecialpackaging;
    }

    public void setIsspecialpackaging(Boolean isspecialpackaging) {
        this.isspecialpackaging = isspecialpackaging;
    }

    @Column(name = "total_actual", precision = 22, scale = 4)
    public BigDecimal getTotalactual() {
        return totalactual;
    }

    public void setTotalactual(BigDecimal totalactual) {
        this.totalactual = totalactual;
    }

    @Column(name = "order_taotal_actual", precision = 22, scale = 4)
    public BigDecimal getOrdertaotalactual() {
        return ordertaotalactual;
    }

    public void setOrdertaotalactual(BigDecimal ordertaotalactual) {
        this.ordertaotalactual = ordertaotalactual;
    }

    @Column(name = "transfer_fee", precision = 22, scale = 4)
    public BigDecimal getTransferfee() {
        return transferfee;
    }

    public void setTransferfee(BigDecimal transferfee) {
        this.transferfee = transferfee;
    }

    @Column(name = "order_transfer_free", precision = 22, scale = 4)
    public BigDecimal getOrdertransferfree() {
        return ordertransferfree;
    }

    public void setOrdertransferfree(BigDecimal ordertransferfree) {
        this.ordertransferfree = ordertransferfree;
    }

    @Column(name = "order_create_time")
    public Date getOrdercreatetime() {
        return ordercreatetime;
    }

    public void setOrdercreatetime(Date ordercreatetime) {
        this.ordercreatetime = ordercreatetime;
    }

    @Column(name = "batch_code", length = 100000000)
    @SequenceGenerator(name = "SEQ_Q_STA", sequenceName = "S_T_WH_Q_BATCH_CODE", allocationSize = 100000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_Q_STA")
    public String getBatchcode() {
        return batchcode;
    }

    public void setBatchcode(String batchcode) {
        this.batchcode = batchcode;
    }

    @Column(name = "error_count")
    public Integer getErrorcount() {
        return errorcount;
    }

    public void setErrorcount(Integer errorcount) {
        this.errorcount = errorcount;
    }

    @Column(name = "activity_source", length = 100)
    public String getActivitysource() {
        return activitysource;
    }

    public void setActivitysource(String activitysource) {
        this.activitysource = activitysource;
    }

    @OneToOne
    @PrimaryKeyJoinColumn
    public QueueStaDeliveryInfo getQueueStaDeliveryInfo() {
        return queueStaDeliveryInfo;
    }

    public void setQueueStaDeliveryInfo(QueueStaDeliveryInfo queueStaDeliveryInfo) {
        this.queueStaDeliveryInfo = queueStaDeliveryInfo;
    }

    @Column(name = "order_total_bf_discount")
    public BigDecimal getOrdertotalbfdiscount() {
        return ordertotalbfdiscount;
    }

    public void setOrdertotalbfdiscount(BigDecimal ordertotalbfdiscount) {
        this.ordertotalbfdiscount = ordertotalbfdiscount;
    }

    @Column(name = "isInvoice")
    public Boolean getIsInvoice() {
        return isInvoice;
    }

    public void setIsInvoice(Boolean isInvoice) {
        this.isInvoice = isInvoice;
    }

    @Column(name = "SPECIAL_TYPE", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.SpecialSkuType")})
    public SpecialSkuType getSpecialType() {
        return specialType;
    }

    public void setSpecialType(SpecialSkuType specialType) {
        this.specialType = specialType;
    }

    @Column(name = "TO_LOCATION")
    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    @Column(name = "SLIP_CODE3", length = 100)
    public String getSlipCode3() {
        return slipCode3;
    }

    public void setSlipCode3(String slipCode3) {
        this.slipCode3 = slipCode3;
    }

    @Column(name = "plan_outbound_time")
    public Date getPlanOutboundTime() {
        return planOutboundTime;
    }

    public void setPlanOutboundTime(Date planOutboundTime) {
        this.planOutboundTime = planOutboundTime;
    }

    @Column(name = "plan_arrive_time")
    public Date getPlanArriveTime() {
        return planArriveTime;
    }

    public void setPlanArriveTime(Date planArriveTime) {
        this.planArriveTime = planArriveTime;
    }

    @Column(name = "is_trans_upgrade")
    public Boolean getIsTransUpgrade() {
        return isTransUpgrade;
    }

    public void setIsTransUpgrade(Boolean isTransUpgrade) {
        this.isTransUpgrade = isTransUpgrade;
    }

    @Column(name = "arrive_time")
    public Date getArriveTime() {
        return ArriveTime;
    }

    public void setArriveTime(Date arriveTime) {
        ArriveTime = arriveTime;
    }

    @Column(name = "arrive_time_type")
    public String getArriveTimeType() {
        return ArriveTimeType;
    }

    public void setArriveTimeType(String arriveTimeType) {
        ArriveTimeType = arriveTimeType;
    }

    @Column(name = "ORDER_SOURCE_PLATFORM")
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

    @Column(name = "can_flag")
    public Boolean getCanflag() {
        return canflag;
    }

    public void setCanflag(Boolean canflag) {
        this.canflag = canflag;
    }

    // @Column(name = "begin_flag")
    // public Boolean getBeginflag() {
    // return beginflag;
    // }
    //
    // public void setBeginflag(Boolean beginflag) {
    // this.beginflag = beginflag;
    // }

    @Column(name = "flag_Result")
    public String getFlagResult() {
        return flagResult;
    }

    @Column(name = "begin_flag")
    public Integer getBeginflag() {
        return beginflag;
    }

    public void setBeginflag(Integer beginflag) {
        this.beginflag = beginflag;
    }

    public void setFlagResult(String flagResult) {
        this.flagResult = flagResult;
    }


    @Column(name = "mk_pos_code")
    public String getMkPosCode() {
        return mkPosCode;
    }

    public void setMkPosCode(String mkPosCode) {
        this.mkPosCode = mkPosCode;
    }

    @Column(name = "memo")
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Column(name = "is_Print_Price")
    public Boolean getIsPrintPrice() {
        return isPrintPrice;
    }

    public void setIsPrintPrice(Boolean isPrintPrice) {
        this.isPrintPrice = isPrintPrice;
    }

    @Column(name = "error_Mq_Count")
    public Integer getErrorMqCount() {
        return errorMqCount;
    }

    public void setErrorMqCount(Integer errorMqCount) {
        this.errorMqCount = errorMqCount;
    }

    @Column(name = "is_again_send_mq")
    public Integer getIsAgainSendMq() {
        return isAgainSendMq;
    }

    public void setIsAgainSendMq(Integer isAgainSendMq) {
        this.isAgainSendMq = isAgainSendMq;
    }

}
