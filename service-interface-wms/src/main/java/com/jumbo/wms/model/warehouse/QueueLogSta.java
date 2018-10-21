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

import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;

/**
 * 待创建队列日志-订单信息
 * 
 * @author jumbo
 * 
 */
@Entity
@Table(name = "T_WH_QLG_STA")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class QueueLogSta extends BaseModel {
    /**
	 * 
	 */
    private static final long serialVersionUID = 4703907379562555719L;
    /**
     * PK
     */
    private long id;
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
    private long mainwhouid;
    /**
     * 附属仓库ID
     */
    private long addwhouid;
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
    private int type;
    /**
     * 库存状态ID
     */
    private long invstatusid;
    /**
     * 附属库存状态ID
     */
    private long invaddstatusid;
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
     * 日志时间
     */
    private Date logtime;
    /**
     * 过仓失败原因
     */
    private String errormsg;
    /**
     * 状态
     */
    private int status;
    /**
     * 活动
     */
    private String activitysource;
    /**
     * 物流信息
     */
    private QueueLogStaDeliveryInfo queueLogStaDeliveryInfo;
    /**
     * 订单折前金额
     */
    private BigDecimal ordertotalbfdiscount;
    /**
     * 是否需要发票
     */
    private Boolean isInvoice;

    /**
     * 装箱清单是否打印价格(gucci定制)
     */
    private Boolean isPrintPrice = false;

    /**
     * 客户ID
     */
    private Long customerId;
    /**
     * 渠道
     */
    private String channelList;

    private BigDecimal codAmount;
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
     * 队列创建时间
     * 
     * @return
     */
    private Date createtime;
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
    private boolean isTransUpgrade;
    /**
     * 送货时间
     */
    private Date ArriveTime;
    /**
     * 送货时间类型
     */
    private String ArriveTimeType;
    /**
     * 队列ID
     */
    private Long qstaId;
    /**
     * 队列批次号
     */
    private String batchCode;

    /**
     * 订单源平台
     */
    private String orderSourcePlatform;

    /**
     * 门店标识
     */
    private String storecode;
    /**
     * MKPOS编码
     */
    private String mkPosCode;
    /**
     * 备注
     */
    private String memo;

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


    @Column(name = "batch_Code")
    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    @Column(name = "qsta_Id")
    public Long getQstaId() {
        return qstaId;
    }

    public void setQstaId(Long qstaId) {
        this.qstaId = qstaId;
    }

    @Column(name = "create_time")
    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
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

    @Column(name = "cod_Amount")
    public BigDecimal getCodAmount() {
        return codAmount;
    }

    public void setCodAmount(BigDecimal codAmount) {
        this.codAmount = codAmount;
    }

    @Column(name = "customer_Id")
    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Column(name = "channel_list")
    public String getChannelList() {
        return channelList;
    }

    public void setChannelList(String channelList) {
        this.channelList = channelList;
    }

    @Column(name = "order_total_bf_discount", precision = 22, scale = 4)
    public BigDecimal getOrdertotalbfdiscount() {
        return ordertotalbfdiscount;
    }

    public void setOrdertotalbfdiscount(BigDecimal ordertotalbfdiscount) {
        this.ordertotalbfdiscount = ordertotalbfdiscount;
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_WH_QLG_STA", sequenceName = "S_T_WH_QLG_STA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_WH_QLG_STA")
    public long getId() {
        return id;
    }

    public void setId(long id) {
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
    public long getMainwhouid() {
        return mainwhouid;
    }

    public void setMainwhouid(long mainwhouid) {
        this.mainwhouid = mainwhouid;
    }

    @Column(name = "add_wh_ou_id")
    public long getAddwhouid() {
        return addwhouid;
    }

    public void setAddwhouid(long addwhouid) {
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
    public long getInvstatusid() {
        return invstatusid;
    }

    public void setInvstatusid(long invstatusid) {
        this.invstatusid = invstatusid;
    }

    @Column(name = "inv_add_status_id")
    public long getInvaddstatusid() {
        return invaddstatusid;
    }

    public void setInvaddstatusid(long invaddstatusid) {
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

    @Column(name = "log_time")
    public Date getLogtime() {
        return logtime;
    }

    public void setLogtime(Date logtime) {
        this.logtime = logtime;
    }

    @Column(name = "error_msg", length = 100)
    public String getErrormsg() {
        return errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }

    @Column(name = "status")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
    public QueueLogStaDeliveryInfo getQueueLogStaDeliveryInfo() {
        return queueLogStaDeliveryInfo;
    }

    public void setQueueLogStaDeliveryInfo(QueueLogStaDeliveryInfo queueLogStaDeliveryInfo) {
        this.queueLogStaDeliveryInfo = queueLogStaDeliveryInfo;
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
    public boolean isTransUpgrade() {
        return isTransUpgrade;
    }

    public void setTransUpgrade(boolean isTransUpgrade) {
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

    @Column(name = "mk_Pos_Code")
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

}
