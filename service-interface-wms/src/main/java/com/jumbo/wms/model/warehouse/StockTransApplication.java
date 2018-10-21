/**
 * Copyright (c) 2010 Jumbomart All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Jumbomart. You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jumbo.
 * 
 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 * 
 */

package com.jumbo.wms.model.warehouse;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.SlipType;
import com.jumbo.wms.model.TransUpgradeType;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.automaticEquipment.WhPickingBatch;
import com.jumbo.wms.model.baseinfo.SkuCategories;
import com.jumbo.wms.model.pickZone.WhOcpOrder;

/**
 * 仓库作业申请单，所有仓库作业最终都表现为此作业单
 * 
 * @author benjamin
 * 
 */
@Entity
@Table(name = "T_WH_STA")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class StockTransApplication extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = 8456738983720551108L;

    public static final String SPLIT_CHANNEL_LIST = ";";
    public static final String ACTIVITY_SOURCE_SLIPT = ",";
    /**
     * PK
     */
    private Long id;

    /**
     * 业务批号..
     */
    private Long businessSeqNo;

    /**
     * 申请单编码
     */
    private String code;

    /**
     * 前置单据类型
     */
    private SlipType refSlipType;
    /**
     * 渠道列表
     */
    private String channelList;
    /**
     * 前置单据编码
     */
    private String refSlipCode;

    /**
     * 申请单类型
     */
    private StockTransApplicationType type;

    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 采购到货时间
     */
    private Date arriveTime;

    /**
     * 转出时间/库存占用时间/销售-出库时间
     */
    private Date outboundTime;

    /**
     * 最近转入时间
     */
    private Date inboundTime;

    /**
     * 结束时间
     */
    private Date finishTime;

    /**
     * 状态
     */
    private StockTransApplicationStatus status;

    /**
     * 货主，如果不指定则不限制(库内转店原始店铺)
     */
    private String owner;
    /**
     * 货主(库内转店目标店铺)
     */
    private String addiOwner;

    /**
     * 是否需要占用库存
     */
    private Boolean isNeedOccupied = false;

    /**
     * 配货员
     */
    private String pickingUser;

    /**
     * 打包员
     */
    private String packingUser;

    /**
     * 备注
     */
    private String memo;


    private String pumaType;

    /**
     * vmi 入库来源地址
     */
    private String fromLocation;

    /**
     * vmi 入库目标地址
     */
    private String toLocation;

    private int version;

    /**
     * 采购(采购总金额)/销售(销售单实际总金额)
     */
    private BigDecimal totalActual;
    /**
     * sta line sku列表，形式：sku种类数:sku1 id;sku1明细行总数,sku2 id;sku2明细行总数
     */
    private String skus;
    /**
     * 箱号
     */
    private Integer index;

    /**
     * 创建人
     */
    private User creator;

    /**
     * 转出操作人
     */
    private User outboundOperator;

    /**
     * 最近转入操作人
     */
    private User inboundOperator;

    /**
     * 相关仓库
     */
    private OperationUnit mainWarehouse;

    /**
     * 目标仓库（移库时有用）
     */
    private OperationUnit addiWarehouse;

    /**
     * 原库存状态（库存状态变更时有用）
     */
    private InventoryStatus mainStatus;

    /**
     * 目标库存状态（库存状态变更时有用）
     */
    private InventoryStatus addiStatus;

    /**
     * 物流相关信息
     */
    private StaDeliveryInfo staDeliveryInfo;

    /**
     * 配货单
     */
    private PickingList pickingList;

    /**
     * 出库交接清单
     */
    private HandOverList hoList;
    /**
     * VIM 是否反馈
     */
    private Boolean vmiRCStatus = false;
    /**
     * 是否PDA收货
     */
    private Boolean isPDA;
    /**
     * 发票号
     */
    private String invoiceNumber;
    /**
     * 货币
     */
    private String currency = "CNY";

    /**
     * 成本
     */
    private Double totalFOB;
    /**
     * total_GTP(ESPRITS使用)
     */
    private Double totalGTP = 0d;
    /**
     * 税收系数(ESPRITS使用)
     */
    private Double dutyPercentage;
    /**
     * 其他系数(ESPRITS使用)
     */
    private Double miscFeePercentage;
    /**
     * 核对操作时间
     */
    private Date checkTime;
    /**
     * 是否特殊包装
     */
    private Boolean isSpecialPackaging = false;
    /**
     * 商品数量
     */
    private Long skuQty;
    /**
     * 是否是需要SN号作业单
     */
    private Boolean isSn;
    /**
     * 活动
     */
    private String activitySource;

    /**
     * 取消时间
     */
    private Date cancelTime;

    private String slipCode1;

    private String slipCode2;
    /**
     * 商城自主退货编码
     */
    private String slipCode3;
    /**
     * 最后状态更新时间
     */
    private Date sysUpdateTime;
    /**
     * 商品体积单边最长值（用于区分大小件）
     */
    private BigDecimal skuMaxLength;

    /**
     * 是否秒杀订单
     */
    private Boolean isSedKill;
    // /**
    // * 是否陆运
    // */
    // private Boolean isRailway;
    /**
     * 运送类型..
     */
    private TransDeliveryType deliveryType;
    /**
     * 合并作业单
     */
    private StockTransApplication groupSta;
    /**
     * 作业单配货批核对模式
     */
    private ParcelSortingMode parcelSortingMode;
    /**
     * 是否锁定
     */
    private Boolean isLocked;

    /**
     * 库存锁定原因类型
     */
    private InventoryLockType lockType;

    /**
     * 快递单号及Sku
     */
    private String trackingAndSku;
    /**
     * 套装组合商品id
     */
    private Long packageSku;

    /**
     * 原订单创建时间
     */
    private Date orderCreateTime;

    /**
     * 计划最晚发货时间
     */
    private Date planLastOutboundTime;

    /**
     * 配货类型
     */
    private StockTransApplicationPickingType pickingType;

    /**
     * 商品分类
     */
    private SkuCategories skuCategoriesId;

    // 新增字段
    /**
     * 订单商品金额
     */
    private BigDecimal orderTotalActual;
    /**
     * 订单折前金额
     */
    private BigDecimal orderTotalBfDiscount;

    /**
     * 特殊处理类型
     */
    private SpecialSkuType specialType;
    /**
     * 订单运费
     */
    private BigDecimal orderTransferFree;

    /**
     * 核对人用户ID
     */
    private Long checkUserId;

    private String code1;

    private String code2;

    private String code3;

    private String code4;

    /**
     * 是否是合并后新订单
     */
    private Boolean isMerge = false;
    /**
     * 最后修改时间
     */
    private Date lastModifyTime;

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
     * 接口类型（区分品牌接口）
     */
    private String interfaceType;

    /**
     * 物流宝订单编号
     */
    private String wlbOrderCode;

    /**
     * 是否后端核对SKU
     */
    private Boolean isBkCheck;
    /**
     * 品牌备注
     */
    private String extMemo;

    /*
     * 系统来源标识
     */
    private String systemKey;
    /*
     * 订单来源
     */
    private String orderSource;
    /*
     * 订单折扣
     */
    private BigDecimal orderDiscount;

    /**
     * 配货子类型。 1:拣货模式1 、2:拣货模式2
     */
    private Long pickSubType;
    /**
     * 送货时间
     */
    private Date hkArriveTime;
    /**
     * 获取运单的记录时间
     */
    private Date nextGetTransnoTime;

    /**
     * 匹配运单号次数
     */
    private Long transMatchCount;

    /**
     * 是否开始核对
     */
    private Boolean isChecking = false;

    /**
     * 是否短配
     */
    private Boolean isShortPicking = false;
    /**
     * 积分
     */
    private BigDecimal points;
    /**
     * 返点积分
     */
    private BigDecimal returnPoints;
    /**
     * 是否紧急
     */
    private Boolean isUrgent = false;
    /**
     * 是否校验
     */
    private Boolean isOutboundCheck = false;
    /**
     * 订单类型
     */
    private String orderType;
    /**
     * 是否做过报缺
     */
    private Boolean isHaveReportMissing;

    /**
     * 强制过滤报缺(AD 拣货前部分取消 不用重复复核 true)
     */
    private Boolean noHaveReportMissing;

    /**
     * 门店标识
     */
    private String storecode;

    /**
     * 仓库区域列表
     */
    private String whZoonList;

    /**
     * 上架审核人
     * 
     * @return
     */
    private Long pdaUserId;


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
    private Boolean isNikePick = false;

    /**
     * 残次退仓类型
     * 
     */
    private String imperfectType;
    /**
     * MKPOS编码
     */
    private String mkPosCode;

    /**
     * 是否是在esprit转门店退仓执行
     * 
     * @return
     */
    private Integer isEsprit;// 1:是

    /**
     * esprit 门店code
     * 
     * @return
     */
    private String ctCode;//

    /**
     * 是否是唯品会批量销售业务 0/空 不是 1：是
     * 
     * @return
     */
    private String isPf;

    /**
     * 是否预售 0/空 不是 1：是
     */
    private String isPreSale;

    /**
     * 订单类型 (0 或 空:普通 ,1:MSR定制订单)
     */
    private Integer orderType2;

    /**
     * 下次占用库存时间， 当前时间+ 半小时
     */
    private Date nextOcpTime;

    /**
     * 是否澳门件
     */
    private Boolean isMacaoOrder = false;

    /**
     * 是否打印海关单
     */
    private Boolean isPrintMaCaoHGD = false;

    /**
     * 是否配货失败
     */
    private Boolean isDistributeFailed = false;
    
    
    /**
     * 入库单纸箱数量
     */
    private Integer cartonNum;

    /**
     * NIKE收货指令定制
     * (1:hub 0或空 ：原来逻辑)
     * @return
     */
    private String nikeVmiStockinSource;

    @Column(name = "nike_vmi_stockin_source")
    public String getNikeVmiStockinSource() {
        return nikeVmiStockinSource;
    }

    public void setNikeVmiStockinSource(String nikeVmiStockinSource) {
        this.nikeVmiStockinSource = nikeVmiStockinSource;
    }

    @Column(name = "IS_PRINT_MACAO_HGD")
    public Boolean getIsPrintMaCaoHGD() {
        if (isPrintMaCaoHGD == null) {
            return false;
        } else {
            return isPrintMaCaoHGD;
        }
    }

    public void setIsPrintMaCaoHGD(Boolean isPrintMaCaoHGD) {
        if (isPrintMaCaoHGD == null) {
            this.isPrintMaCaoHGD = false;
        } else {
            this.isPrintMaCaoHGD = isPrintMaCaoHGD;
        }
    }

    @Column(name = "IS_MACAO_ORDER")
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

    @Column(name = "ORDER_TYPE2")
    public Integer getOrderType2() {
        return orderType2;
    }

    public void setOrderType2(Integer orderType2) {
        this.orderType2 = orderType2;
    }

    /**
     * 区域占用库存状态 ； 空： 未计算，0：计算失败， 5：计算成功
     */
    private Integer areaOcpStatus;
    /**
     * 区域占用库存结果
     */
    private String areaOcpMemo;
    /**
     * 区域占用库存次数. 计算成功 次数=2. 错误则++
     */
    private String areaOcpErrorCount;

    @Column(name = "IS_PRE_SALE")
    public String getIsPreSale() {
        return isPreSale;
    }

    public void setIsPreSale(String isPreSale) {
        this.isPreSale = isPreSale;
    }



    @Column(name = "IS_PF")
    public String getIsPf() {
        return isPf;
    }

    public void setIsPf(String isPf) {
        this.isPf = isPf;
    }

    @Column(name = "CT_CODE")
    public String getCtCode() {
        return ctCode;
    }


    public void setCtCode(String ctCode) {
        this.ctCode = ctCode;
    }

    @Column(name = "IS_ESPRIT")
    public Integer getIsEsprit() {
        return isEsprit;
    }

    public void setIsEsprit(Integer isEsprit) {
        this.isEsprit = isEsprit;
    }

    @Column(name = "is_Short_Picking")
    public Boolean getIsShortPicking() {
        return isShortPicking;
    }

    public void setIsShortPicking(Boolean isShortPicking) {
        this.isShortPicking = isShortPicking;
    }

    @Column(name = "is_checking")
    public Boolean getIsChecking() {
        return isChecking;
    }

    public void setIsChecking(Boolean isChecking) {
        this.isChecking = isChecking;
    }

    private Integer localSort;

    /**
     * 库位编码（多件取拣货顺序最小的，且编码最小的库位）
     */
    private String localCode;

    @Transient
    public String getCode1() {
        return code1;
    }

    public void setCode1(String code1) {
        this.code1 = code1;
    }

    @Transient
    public String getCode2() {
        return code2;
    }

    public void setCode2(String code2) {
        this.code2 = code2;
    }

    @Transient
    public String getCode3() {
        return code3;
    }

    public void setCode3(String code3) {
        this.code3 = code3;
    }

    @Transient
    public String getCode4() {
        return code4;
    }

    public void setCode4(String code4) {
        this.code4 = code4;
    }

    /*
     * 新增String类型日期字段，辅助完成查询时间精确到时分秒
     */
    private String createTime1;
    private String finishTime1;
    /**
     * 关联O2O箱
     */
    private PickingListPackage packageId;


    @Transient
    public String getFinishTime1() {
        return finishTime1;
    }

    public void setFinishTime1(String finishTime1) {
        this.finishTime1 = finishTime1;
    }

    @Transient
    public String getCreateTime1() {
        return createTime1;
    }

    public void setCreateTime1(String createTime1) {
        this.createTime1 = createTime1;
    }

    /**
     * 占用批
     */
    private WhOcpOrder whOcpOrder;

    /**
     * 占用排序
     */
    private Integer ocpSort;

    /**
     * 拣货区ID列表(","分隔且升序排列 如 “123，345，222”)
     */
    private String zoonList;

    /**
     * 占用失败次数
     */
    private Integer ocpErrorQty;
    /**
     * 预计发货时间
     */
    private Date planOutboundTime;
    /**
     * 平台预计送达时间
     */
    private Date storePlanArriveTime;
    /**
     * WMS预计送达时间
     */
    private Date wmsPlanArriveTime;
    /**
     * 升单类型
     */
    private TransUpgradeType transUpgradeType;
    /**
     * 快递是否需要升级
     */
    private Boolean isTransUpgrade;

    /**
     * 占用批占用批次编码
     */
    private String ocpBatchCode;

    /**
     * 占用批创建失败（超过5次的不做处理,IT手工处理）
     */
    private Integer ocpErrorCreate;


    /**
     * 装箱清单是否打印价格(gucci定制)
     */
    private Boolean isPrintPrice = false;


    /**
     * 占用批编码
     */
    private String ocpCode;
    /**
     * 货物运送方式
     */
    private FreightMode freightMode;
    /**
     * 收货箱号或者出库周转箱号
     */
    private String containerCode;

    /**
     * 出库小批次序号 2-1中的 2
     */
    private String idx1;
    /**
     * 出库小批次号 2-1中的1
     */
    private String idx2;
    /**
     * 二级批次ID
     */
    private WhPickingBatch whPickingBatch;

    /**
     * 包装类型
     */
    private PackingType packingType;

    /**
     * 订单源平台
     */
    private String orderSourcePlatform;
    /**
     * 快递订单包裹
     */
    private TransOrder transOrder;

    /**
     * 集货口编码
     */
    private String shipmentCode;

    /**
     * 新增字段，记录解锁人信息
     */

    private String unlockUser;

    private Boolean resetToCreate;

    /**
     * 不是OMS或PACS的订单
     */
    private Boolean isNotPacsomsOrder = false;

    /**
     * 二次分拣编码规则
     */
    private String ruleCode;

    private String extMemo2;// 星巴克 定制 json

    private String dataSource;

    /**
     * 是否申请电子发票
     */
    private Boolean isOnlineInvoice;

    /**
     * 上位系统单据类型
     */
    private String extType;

    @Column(name = "IS_ONLINE_INVOICE")
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

    @Column(name = "EXT_MEMO2")
    public String getExtMemo2() {
        return extMemo2;
    }

    public void setExtMemo2(String extMemo2) {
        this.extMemo2 = extMemo2;
    }

    /************************/
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_STA", sequenceName = "S_T_WH_STA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_STA")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "BI_SEQ_NO")
    public Long getBusinessSeqNo() {
        return businessSeqNo;
    }

    public void setBusinessSeqNo(Long businessSeqNo) {
        this.businessSeqNo = businessSeqNo;
    }

    @Column(name = "CODE", length = 100)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "SLIP_TYPE", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.SlipType")})
    public SlipType getRefSlipType() {
        return refSlipType;
    }

    public void setRefSlipType(SlipType refSlipType) {
        this.refSlipType = refSlipType;
    }

    @Transient
    public int getIntSlipType() {
        return refSlipType == null ? -1 : refSlipType.getValue();
    }

    public void setIntSlipType(int intSlipType) {
        setRefSlipType(SlipType.valueOf(intSlipType));
    }

    @Column(name = "SLIP_CODE", length = 100)
    public String getRefSlipCode() {
        return refSlipCode;
    }

    public void setRefSlipCode(String refSlipCode) {
        this.refSlipCode = refSlipCode;
    }

    @Column(name = "TYPE", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.StockTransApplicationType")})
    public StockTransApplicationType getType() {
        return type;
    }

    public void setType(StockTransApplicationType type) {
        this.type = type;
    }

    public void setIntType(int intType) {
        setType(StockTransApplicationType.valueOf(intType));
    }

    @Transient
    public int getIntType() {
        return type == null ? -1 : type.getValue();
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "ARRIVE_TIME")
    public Date getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(Date arriveTime) {
        this.arriveTime = arriveTime;
    }

    @Column(name = "OUTBOUND_TIME")
    public Date getOutboundTime() {
        return outboundTime;
    }

    public void setOutboundTime(Date outboundTime) {
        this.outboundTime = outboundTime;
    }

    @Column(name = "INBOUND_TIME")
    public Date getInboundTime() {
        return inboundTime;
    }

    public void setInboundTime(Date inboundTime) {
        this.inboundTime = inboundTime;
    }

    @Column(name = "FINISH_TIME")
    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    @Column(name = "STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.StockTransApplicationStatus")})
    public StockTransApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(StockTransApplicationStatus status) {
        this.status = status;
        this.setSysUpdateTime(new Date());
    }

    @Transient
    public int getIntStatus() {
        return status == null ? -1 : status.getValue();
    }

    public void setIntStatus(int intStatus) {
        setStatus(StockTransApplicationStatus.valueOf(intStatus));
    }

    @Column(name = "OWNER", length = 100)
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Column(name = "IS_NEED_OCCUPIED")
    public Boolean getIsNeedOccupied() {
        return isNeedOccupied;
    }

    public void setIsNeedOccupied(Boolean isNeedOccupied) {
        this.isNeedOccupied = isNeedOccupied;
    }

    @Column(name = "MEMO", length = 255)
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        if (memo != null && memo.length() >= 500) {
            memo = memo.substring(0, 499);
        }
        this.memo = memo;
    }


    @Version
    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATOR_ID")
    @Index(name = "IDX_STA_CREATOR")
    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OUT_OPERATOR_ID")
    @Index(name = "IDX_STA_OUT_OPERATOR")
    public User getOutboundOperator() {
        return outboundOperator;
    }

    public void setOutboundOperator(User outboundOperator) {
        this.outboundOperator = outboundOperator;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IN_OPERATOR_ID")
    @Index(name = "IDX_STA_IN_OPERATOR")
    public User getInboundOperator() {
        return inboundOperator;
    }

    public void setInboundOperator(User inboundOperator) {
        this.inboundOperator = inboundOperator;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MAIN_WH_ID")
    @Index(name = "IDX_STA_MAIN_WH")
    public OperationUnit getMainWarehouse() {
        return mainWarehouse;
    }

    public void setMainWarehouse(OperationUnit mainWarehouse) {
        this.mainWarehouse = mainWarehouse;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ADDI_WH_ID")
    @Index(name = "IDX_STA_ADDI_WH")
    public OperationUnit getAddiWarehouse() {
        return addiWarehouse;
    }

    public void setAddiWarehouse(OperationUnit addiWarehouse) {
        this.addiWarehouse = addiWarehouse;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MAIN_STATUS_ID")
    @Index(name = "IDX_STA_MAIN_STATUS")
    public InventoryStatus getMainStatus() {
        return mainStatus;
    }

    public void setMainStatus(InventoryStatus mainStatus) {
        this.mainStatus = mainStatus;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ADDI_STATUS_ID")
    @Index(name = "IDX_STA_ADDI_STATUS")
    public InventoryStatus getAddiStatus() {
        return addiStatus;
    }

    public void setAddiStatus(InventoryStatus addiStatus) {
        this.addiStatus = addiStatus;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    public StaDeliveryInfo getStaDeliveryInfo() {
        return staDeliveryInfo;
    }

    public void setStaDeliveryInfo(StaDeliveryInfo staDeliveryInfo) {
        this.staDeliveryInfo = staDeliveryInfo;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PICKING_LIST_ID")
    @Index(name = "IDX_FK_PICKING_LIST_ID")
    public PickingList getPickingList() {
        return pickingList;
    }

    public void setPickingList(PickingList pickingList) {
        this.pickingList = pickingList;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HO_LIST_ID")
    @Index(name = "IDX_FK_HO_LIST_ID_P")
    public HandOverList getHoList() {
        return hoList;
    }

    public void setHoList(HandOverList hoList) {
        this.hoList = hoList;
    }

    @Column(name = "TOTAL_ACTUAL")
    public BigDecimal getTotalActual() {
        return totalActual;
    }

    public void setTotalActual(BigDecimal totalActual) {
        this.totalActual = totalActual;
    }

    @Column(name = "SKUS", length = 500)
    public String getSkus() {
        return skus;
    }

    public void setSkus(String skus) {
        this.skus = skus;
    }

    @Column(name = "PICKING_USER", length = 50)
    public String getPickingUser() {
        return pickingUser;
    }

    public void setPickingUser(String pickingUser) {
        this.pickingUser = pickingUser;
    }

    @Column(name = "PACKING_USER", length = 50)
    public String getPackingUser() {
        return packingUser;
    }

    public void setPackingUser(String packingUser) {
        this.packingUser = packingUser;
    }


    @Column(name = "PG_INDEX")
    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    @Column(name = "VMI_RC_STATUS")
    public Boolean getVmiRCStatus() {
        return vmiRCStatus;
    }

    @Column(name = "IS_PDA")
    public Boolean getIsPDA() {
        return isPDA;
    }

    public void setIsPDA(Boolean isPDA) {
        this.isPDA = isPDA;
    }

    public void setVmiRCStatus(Boolean vmiRCStatus) {
        this.vmiRCStatus = vmiRCStatus;
    }

    public String codePrefix() {
        switch (type) {
        // 订单号： S
            case ORDER_CODE:
                return "S";
                // 退换货申请单号： E
            case INBOUND_RETURN_REQUEST_CODE:
                return "E";
                // VMI库存调整入库： A
            case VMI_ADJUSTMENT_INBOUND:
                return "A";
                // VMI库存调整出库： B
            case VMI_ADJUSTMENT_OUTBOUND:
                return "B";
                // 结算经销入库 C
            case INBOUND_SETTLEMENT:
                return "C";
                // 代销入库：D
            case INBOUND_CONSIGNMENT:
                return "D";
                // 退换货入库： E
            case INBOUND_RETURN_REQUEST:
                return "E";
                // 换货出库： F
            case OUTBOUND_RETURN_REQUEST:
                return "F";
                // 采购出库（采购退货出库）
            case OUTBOUND_PURCHASE:
                return "G";
                // VMI代销入库：H
            case VMI_INBOUND_CONSIGNMENT:
                return "H";
                // 结算经销入库
            case OUTBOUND_SETTLEMENT:
                return "I";
                // 包材领用出库
            case OUTBOUND_PACKAGING_MATERIALS:
                return "J";
                // 代销 出库
            case OUTBOUND_CONSIGNMENT:
                return "K";
                // 赠品入库
            case INBOUND_GIFT:
                return "L";
                // 库内移动： M
            case TRANSIT_INNER:
                return "M";
                // 外部订单销售出库:N
            case OUT_SALES_ORDER_OUTBOUND_SALES:
                return "N";
                // 其他入库:O
            case INBOUND_OTHERS:
                return "O";
                // 其他出库： O
            case OUTBOUND_OTHERS:
                return "O";
                // 采购入库： P
            case INBOUND_PURCHASE:
                return "P";
                // VMI退大仓
            case VMI_RETURN:
                return "Q";
                // VMI转店退仓
            case VMI_TRANSFER_RETURN:
                return "R";
                // 销售出库： S
            case OUTBOUND_SALES:
                return "S";
                // 库间移动： T
            case TRANSIT_CROSS:
                return "T";
                // 同公司调拨
            case SAME_COMPANY_TRANSFER:
                return "U";
                // VMI转店： V
            case VMI_OWNER_TRANSFER:
                return "V";
                // 移库入库：W
            case INBOUND_MOBILE:
                return "W";
                // 库存状态修改： X
            case INVENTORY_STATUS_CHANGE:
                return "X";
                // GI 上架
            case GI_PUT_SHELVES:
                return "Y";
                // 不同公司调拨
            case DIFF_COMPANY_TRANSFER:
                return "Z";
                // 样品领用出库
            case SAMPLE_OUTBOUND:
                return "OA";
                // 商品置换出库
            case SKU_EXCHANGE_OUTBOUND:
                return "OB";
                // 送修出库
            case REAPAIR_OUTBOUND:
                return "OC";
                // 串号拆分出库
            case SERIAL_NUMBER_OUTBOUND:
                return "OD";
                // 串号组合出库
            case SERIAL_NUMBER_GROUP_OUTBOUND:
                return "OE";
                // 福利领用
            case WELFARE_USE:
                return "OF";
                // 固定资产领用
            case FIXED_ASSETS_USE:
                return "OG";
                // 报废出库
            case SCARP_OUTBOUND:
                return "OH";
                // 促销领用
            case SALES_PROMOTION_USE:
                return "OI";
                // 低值易耗品
            case LOW_VALUE_CONSUMABLE_USE:
                return "OJ";
                // 样品领用入库
            case SAMPLE_INBOUND:
                return "IA";
                // 商品置换入库
            case SKU_EXCHANGE_INBOUND:
                return "IB";
                // 送修入库
            case REAPAIR_INBOUND:
                return "IC";
                // 串号拆分入库
            case SERIAL_NUMBER_INBOUND:
                return "ID";
                // 串号组合入库
            case SERIAL_NUMBER_GROUP_INBOUND:
                return "IE";
                // 货返入库
            case SKU_RETURN_INBOUND:
                return "IF";
                // 库存出入调整（针对盘点调整）
            case INVENTORY_ADJUSTMENT_UPDATE:
                return "IG";
                // 库存锁定
            case INVENTORY_LOCK:
                return "ZY";
            case INBOUND_RETURN_PURCHASE:
                return "OR";
            default:
                throw new IllegalArgumentException();
        }
    }

    @Column(name = "ADDI_OWNER", length = 100)
    public String getAddiOwner() {
        return addiOwner;
    }

    public void setAddiOwner(String addiOwner) {
        this.addiOwner = addiOwner;
    }

    @Column(name = "INVOICE_NUMBER", length = 100)
    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    @Column(name = "CURRENCY", length = 10)
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Column(name = "TOTAL_FOB")
    public Double getTotalFOB() {
        return totalFOB;
    }

    public void setTotalFOB(Double totalFOB) {
        this.totalFOB = totalFOB;
    }

    @Column(name = "TOTAL_GTP")
    public Double getTotalGTP() {
        return totalGTP;
    }

    public void setTotalGTP(Double totalGTP) {
        this.totalGTP = totalGTP;
    }

    @Column(name = "PDA_USER_ID")
    public Long getPdaUserId() {
        return pdaUserId;
    }

    public void setPdaUserId(Long pdaUserId) {
        this.pdaUserId = pdaUserId;
    }

    @Column(name = "CHECK_TIME")
    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    @Column(name = "DUTY_PERCENTAGE")
    public Double getDutyPercentage() {
        return dutyPercentage;
    }

    public void setDutyPercentage(Double dutyPercentage) {
        this.dutyPercentage = dutyPercentage;
    }

    @Column(name = "MISC_FEE_PERCENTAGE")
    public Double getMiscFeePercentage() {
        return miscFeePercentage;
    }

    public void setMiscFeePercentage(Double miscFeePercentage) {
        this.miscFeePercentage = miscFeePercentage;
    }

    @Column(name = "FROM_LOCATION", length = 50)
    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    @Column(name = "TO_LOCATION", length = 50)
    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    @Column(name = "SKU_QTY")
    public Long getSkuQty() {
        return skuQty;
    }

    public void setSkuQty(Long skuQty) {
        this.skuQty = skuQty;
    }

    @Column(name = "IS_SN")
    public Boolean getIsSn() {
        return isSn;
    }

    public void setIsSn(Boolean isSn) {
        this.isSn = isSn;
    }

    @Column(name = "IS_SPECIAL_PACKAGING")
    public Boolean getIsSpecialPackaging() {
        return isSpecialPackaging;
    }

    public void setIsSpecialPackaging(Boolean isSpecialPackaging) {
        this.isSpecialPackaging = isSpecialPackaging;
    }

    @Column(name = "SLIP_CODE1", length = 400)
    public String getSlipCode1() {
        return slipCode1;
    }

    public void setSlipCode1(String slipCode1) {
        this.slipCode1 = slipCode1;
    }

    @Column(name = "SLIP_CODE2", length = 400)
    public String getSlipCode2() {
        return slipCode2;
    }

    public void setSlipCode2(String slipCode2) {
        this.slipCode2 = slipCode2;
    }

    @Column(name = "CANCEL_TIME")
    public Date getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }

    @Column(name = "SYS_UPDATE_TIME")
    public Date getSysUpdateTime() {
        return sysUpdateTime;
    }

    public void setSysUpdateTime(Date sysUpdateTime) {
        this.sysUpdateTime = sysUpdateTime;
    }

    @Column(name = "ACTIVITY_SOURCE", length = 100)
    public String getActivitySource() {
        return activitySource;
    }

    public void setActivitySource(String activitySource) {
        this.activitySource = activitySource;
    }

    @Column(name = "SKU_MAX_LENGTH")
    public BigDecimal getSkuMaxLength() {
        return skuMaxLength;
    }

    public void setSkuMaxLength(BigDecimal skuMaxLength) {
        this.skuMaxLength = skuMaxLength;
    }

    @Column(name = "IS_SED_KILL")
    public Boolean getIsSedKill() {
        return isSedKill;
    }

    public void setIsSedKill(Boolean isSedKill) {
        this.isSedKill = isSedKill;
    }

    // @Column(name = "DELIVERY_TYPE", columnDefinition = "integer")
    @Column(name = "IS_RAIL_WAY", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.TransDeliveryType")})
    public TransDeliveryType getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(TransDeliveryType deliveryType) {
        this.deliveryType = deliveryType;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GROUP_STA_ID")
    @Index(name = "IDX_GROUP_STA")
    public StockTransApplication getGroupSta() {
        return groupSta;
    }

    public void setGroupSta(StockTransApplication groupSta) {
        this.groupSta = groupSta;
    }

    @Column(name = "IS_LOCKED")
    public Boolean getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(Boolean isLocked) {
        this.isLocked = isLocked;
    }

    @Column(name = "LOCK_TYPE", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.InventoryLockType")})
    public InventoryLockType getLockType() {
        return lockType;
    }

    public void setLockType(InventoryLockType lockType) {
        this.lockType = lockType;
    }

    @Column(name = "PARCEL_SORTING_MODE", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.ParcelSortingMode")})
    public ParcelSortingMode getParcelSortingMode() {
        return parcelSortingMode;
    }

    public void setParcelSortingMode(ParcelSortingMode parcelSortingMode) {
        this.parcelSortingMode = parcelSortingMode;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SKU_CATEGORIES_ID")
    @Index(name = "IDX_STA_SKU_CATEGORIES_ID")
    public SkuCategories getSkuCategoriesId() {
        return skuCategoriesId;
    }

    public void setSkuCategoriesId(SkuCategories skuCategoriesId) {
        this.skuCategoriesId = skuCategoriesId;
    }

    @Column(name = "TRACKING_AND_SKU")
    public String getTrackingAndSku() {
        return trackingAndSku;
    }

    public void setTrackingAndSku(String trackingAndSku) {
        this.trackingAndSku = trackingAndSku;
    }

    @Column(name = "PACKAGE_SKU_ID")
    public Long getPackageSku() {
        return packageSku;
    }

    public void setPackageSku(Long packageSku) {
        this.packageSku = packageSku;
    }

    @Column(name = "PICKING_TYPE", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.StockTransApplicationPickingType")})
    public StockTransApplicationPickingType getPickingType() {
        return pickingType;
    }

    public void setPickingType(StockTransApplicationPickingType pickingType) {
        this.pickingType = pickingType;
    }

    @Column(name = "ORDER_CREATE_TIME")
    public Date getOrderCreateTime() {
        return orderCreateTime;
    }

    public void setOrderCreateTime(Date orderCreateTime) {
        this.orderCreateTime = orderCreateTime;
    }

    @Column(name = "PLAN_LAST_OUTBOUND_TIME")
    public Date getPlanLastOutboundTime() {
        return planLastOutboundTime;
    }

    public void setPlanLastOutboundTime(Date planLastOutboundTime) {
        this.planLastOutboundTime = planLastOutboundTime;
    }

    @Column(name = "order_total_actual")
    public BigDecimal getOrderTotalActual() {
        return orderTotalActual;
    }

    public void setOrderTotalActual(BigDecimal orderTotalActual) {
        this.orderTotalActual = orderTotalActual;
    }

    @Column(name = "order_total_bf_discount")
    public BigDecimal getOrderTotalBfDiscount() {
        return orderTotalBfDiscount;
    }

    public void setOrderTotalBfDiscount(BigDecimal orderTotalBfDiscount) {
        this.orderTotalBfDiscount = orderTotalBfDiscount;
    }

    @Column(name = "order_transfer_free")
    public BigDecimal getOrderTransferFree() {
        return orderTransferFree;
    }

    public void setOrderTransferFree(BigDecimal orderTransferFree) {
        this.orderTransferFree = orderTransferFree;
    }

    @Column(name = "channel_list", length = 500)
    public String getChannelList() {
        return channelList;
    }

    public void setChannelList(String channelList) {
        this.channelList = channelList;
    }

    @Column(name = "SPECIAL_TYPE", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.SpecialSkuType")})
    public SpecialSkuType getSpecialType() {
        return specialType;
    }

    public void setSpecialType(SpecialSkuType specialType) {
        this.specialType = specialType;
    }

    @Column(name = "IS_MERGE")
    public Boolean getIsMerge() {
        return isMerge;
    }

    public void setIsMerge(Boolean isMerge) {
        this.isMerge = isMerge;
    }

    @Column(name = "LAST_MODIFY_TIME")
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    @Column(name = "CHECKED_USER_ID")
    public Long getCheckUserId() {
        return checkUserId;
    }

    public void setCheckUserId(Long checkUserId) {
        this.checkUserId = checkUserId;
    }

    @Column(name = "SUPPLIER_NAME")
    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    @Column(name = "PAYMENT_TYPE")
    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    @Column(name = "PAYMENT_TIME")
    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    @Column(name = "INTERFACE_TYPE", length = 20)
    public String getInterfaceType() {
        return interfaceType;
    }

    public void setInterfaceType(String interfaceType) {
        this.interfaceType = interfaceType;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PICKING_PACKAGE_ID")
    @Index(name = "IDX_STA_PACKAGE_ID")
    public PickingListPackage getPackageId() {
        return packageId;
    }

    public void setPackageId(PickingListPackage packageId) {
        this.packageId = packageId;
    }

    @Column(name = "WLB_ORDER_CODE")
    public String getWlbOrderCode() {
        return wlbOrderCode;
    }

    public void setWlbOrderCode(String wlbOrderCode) {
        this.wlbOrderCode = wlbOrderCode;
    }

    @Column(name = "SLIP_CODE3", length = 100)
    public String getSlipCode3() {
        return slipCode3;
    }

    public void setSlipCode3(String slipCode3) {
        this.slipCode3 = slipCode3;
    }

    @Column(name = "IS_BK_CHECK")
    public Boolean getIsBkCheck() {
        return isBkCheck;
    }

    public void setIsBkCheck(Boolean isBkCheck) {
        this.isBkCheck = isBkCheck;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OCP_ID")
    public WhOcpOrder getWhOcpOrder() {
        return whOcpOrder;
    }

    public void setWhOcpOrder(WhOcpOrder whOcpOrder) {
        this.whOcpOrder = whOcpOrder;
    }

    @Column(name = "OCP_SORT")
    public Integer getOcpSort() {
        return ocpSort;
    }

    public void setOcpSort(Integer ocpSort) {
        this.ocpSort = ocpSort;
    }

    @Column(name = "ZOON_LIST")
    public String getZoonList() {
        return zoonList;
    }

    public void setZoonList(String zoonList) {
        this.zoonList = zoonList;
    }

    @Column(name = "OCP_ERROR_QTY")
    public Integer getOcpErrorQty() {
        return ocpErrorQty;
    }

    public void setOcpErrorQty(Integer ocpErrorQty) {
        this.ocpErrorQty = ocpErrorQty;
    }

    @Column(name = "plan_outbound_time")
    public Date getPlanOutboundTime() {
        return planOutboundTime;
    }

    public void setPlanOutboundTime(Date planOutboundTime) {
        this.planOutboundTime = planOutboundTime;
    }

    @Column(name = "store_plan_arrive_time")
    public Date getStorePlanArriveTime() {
        return storePlanArriveTime;
    }

    @Column(name = "OCP_BATCH_CODE")
    public String getOcpBatchCode() {
        return ocpBatchCode;
    }

    public void setStorePlanArriveTime(Date storePlanArriveTime) {
        this.storePlanArriveTime = storePlanArriveTime;
    }

    @Column(name = "wms_plan_arrive_time")
    public Date getWmsPlanArriveTime() {
        return wmsPlanArriveTime;
    }

    public void setWmsPlanArriveTime(Date wmsPlanArriveTime) {
        this.wmsPlanArriveTime = wmsPlanArriveTime;
    }

    @Column(name = "trans_upgrade_type", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.TransUpgradeType")})
    public TransUpgradeType getTransUpgradeType() {
        return transUpgradeType;
    }

    public void setTransUpgradeType(TransUpgradeType transUpgradeType) {
        this.transUpgradeType = transUpgradeType;
    }

    @Column(name = "is_trans_upgrade")
    public Boolean getIsTransUpgrade() {
        return isTransUpgrade;
    }

    public void setIsTransUpgrade(Boolean isTransUpgrade) {
        this.isTransUpgrade = isTransUpgrade;
    }

    public void setOcpBatchCode(String ocpBatchCode) {
        this.ocpBatchCode = ocpBatchCode;
    }

    @Column(name = "OCP_ERROR_CREATE")
    public Integer getOcpErrorCreate() {
        return ocpErrorCreate;
    }

    public void setOcpErrorCreate(Integer ocpErrorCreate) {
        this.ocpErrorCreate = ocpErrorCreate;
    }

    @Column(name = "OCP_CODE")
    public String getOcpCode() {
        return ocpCode;
    }

    public void setOcpCode(String ocpCode) {
        this.ocpCode = ocpCode;
    }

    @Column(name = "EXT_MEMO", length = 2000)
    public String getExtMemo() {
        return extMemo;
    }

    public void setExtMemo(String extMemo) {
        this.extMemo = extMemo;
    }

    @Column(name = "TO_FREIGHT_MODE", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.FreightMode")})
    public FreightMode getFreightMode() {
        return freightMode;
    }

    public void setFreightMode(FreightMode freightMode) {
        this.freightMode = freightMode;
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

    @Column(name = "ORDER_DISCOUNT")
    public BigDecimal getOrderDiscount() {
        return orderDiscount;
    }

    public void setOrderDiscount(BigDecimal orderDiscount) {
        this.orderDiscount = orderDiscount;
    }

    @Column(name = "PICK_SUB_TYPE", length = 19)
    public Long getPickSubType() {
        return pickSubType;
    }

    public void setPickSubType(Long pickSubType) {
        this.pickSubType = pickSubType;
    }

    @Column(name = "hkarrive_time")
    public Date getHkArriveTime() {
        return hkArriveTime;
    }

    public void setHkArriveTime(Date hkArriveTime) {
        this.hkArriveTime = hkArriveTime;
    }

    @Column(name = "next_get_transno_time")
    public Date getNextGetTransnoTime() {
        return nextGetTransnoTime;
    }

    public void setNextGetTransnoTime(Date nextGetTransnoTime) {
        this.nextGetTransnoTime = nextGetTransnoTime;
    }

    @Column(name = "trans_match_count")
    public Long getTransMatchCount() {
        return transMatchCount;
    }

    public void setTransMatchCount(Long transMatchCount) {
        this.transMatchCount = transMatchCount;
    }

    @Column(name = "CONTAINER_CODE", length = 30)
    public String getContainerCode() {
        return containerCode;
    }

    public void setContainerCode(String containerCode) {
        this.containerCode = containerCode;
    }

    @Column(name = "PACKING_TYPE")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.PackingType")})
    public PackingType getPackingType() {
        return packingType;
    }

    public void setPackingType(PackingType packingType) {
        this.packingType = packingType;
    }

    @Column(name = "ORDER_SOURCE_PLATFORM")
    public String getOrderSourcePlatform() {
        return orderSourcePlatform;
    }

    public void setOrderSourcePlatform(String orderSourcePlatform) {
        this.orderSourcePlatform = orderSourcePlatform;
    }

    @Column(name = "IDX1", length = 5)
    public String getIdx1() {
        return idx1;
    }

    public void setIdx1(String idx1) {
        this.idx1 = idx1;
    }

    @Column(name = "IDX2", length = 5)
    public String getIdx2() {
        return idx2;
    }

    public void setIdx2(String idx2) {
        this.idx2 = idx2;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PB_ID")
    @Index(name = "IDX_STA_PB_ID")
    public WhPickingBatch getWhPickingBatch() {
        return whPickingBatch;
    }

    public void setWhPickingBatch(WhPickingBatch whPickingBatch) {
        this.whPickingBatch = whPickingBatch;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tans_order_Id")
    @Index(name = "IDX_tans_order_Id")
    public TransOrder getTransOrder() {
        return transOrder;
    }

    public void setTransOrder(TransOrder transOrder) {
        this.transOrder = transOrder;
    }


    @Column(name = "SHIPMENT_CODE", length = 50)
    public String getShipmentCode() {
        return shipmentCode;
    }

    public void setShipmentCode(String shipmentCode) {
        this.shipmentCode = shipmentCode;
    }

    @Column(name = "points")
    public BigDecimal getPoints() {
        return points;
    }

    public void setPoints(BigDecimal points) {
        this.points = points;
    }

    @Column(name = "is_Urgent")
    public Boolean getIsUrgent() {
        return isUrgent;
    }

    public void setIsUrgent(Boolean isUrgent) {
        this.isUrgent = isUrgent;
    }

    @Column(name = "is_bf_outbound_check")
    public Boolean getIsOutboundCheck() {
        return isOutboundCheck;
    }

    public void setIsOutboundCheck(Boolean isOutboundCheck) {
        this.isOutboundCheck = isOutboundCheck;
    }

    @Column(name = "order_type")
    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    @Column(name = "returnPoints")
    public BigDecimal getReturnPoints() {
        return returnPoints;
    }

    public void setReturnPoints(BigDecimal returnPoints) {
        this.returnPoints = returnPoints;
    }

    @Column(name = "local_sort", length = 19)
    public Integer getLocalSort() {
        return localSort;
    }

    public void setLocalSort(Integer localSort) {
        this.localSort = localSort;
    }

    @Column(name = "local_code", length = 50)
    public String getLocalCode() {
        return localCode;
    }

    public void setLocalCode(String localCode) {
        this.localCode = localCode;
    }

    @Column(name = "unlock_user", length = 50)
    public String getUnlockUser() {
        return unlockUser;
    }

    public void setUnlockUser(String unlockUser) {
        this.unlockUser = unlockUser;
    }

    @Column(name = "IS_HAVE_RE_MISS")
    public Boolean getIsHaveReportMissing() {
        return isHaveReportMissing;
    }

    public void setIsHaveReportMissing(Boolean isHaveReportMissing) {
        this.isHaveReportMissing = isHaveReportMissing;
    }

    @Column(name = "store_code")
    public String getStorecode() {
        return storecode;
    }

    public void setStorecode(String storecode) {
        this.storecode = storecode;
    }

    @Column(name = "is_Print_Price")
    public Boolean getIsPrintPrice() {
        return isPrintPrice;
    }

    public void setIsPrintPrice(Boolean isPrintPrice) {
        this.isPrintPrice = isPrintPrice;
    }

    @Column(name = "wh_zoon_list")
    public String getWhZoonList() {
        return whZoonList;
    }

    public void setWhZoonList(String whZoonList) {
        this.whZoonList = whZoonList;
    }

    @Column(name = "imperfect_Type")
    public String getImperfectType() {
        return imperfectType;
    }

    public void setImperfectType(String imperfectType) {
        this.imperfectType = imperfectType;
    }

    @Column(name = "RESET_TO_CREATE")
    public Boolean getResetToCreate() {
        return resetToCreate;
    }

    public void setResetToCreate(Boolean resetToCreate) {
        this.resetToCreate = resetToCreate;
    }


    @Column(name = "NIKE_PICKUP_TYPE")
    public String getNikePickUpType() {
        return nikePickUpType;
    }

    public void setNikePickUpType(String nikePickUpType) {
        this.nikePickUpType = nikePickUpType;
    }

    @Column(name = "IS_NIKE_PICK")
    public Boolean getIsNikePick() {
        return isNikePick;
    }

    public void setIsNikePick(Boolean isNikePick) {
        this.isNikePick = isNikePick;
    }



    @Column(name = "NIKE_PICKUP_CODE")
    public String getNikePickUpCode() {
        return nikePickUpCode;
    }

    public void setNikePickUpCode(String nikePickUpCode) {
        this.nikePickUpCode = nikePickUpCode;
    }

    @Column(name = "mk_pos_code")
    public String getMkPosCode() {
        return mkPosCode;
    }

    public void setMkPosCode(String mkPosCode) {
        this.mkPosCode = mkPosCode;
    }

    @Column(name = "IS_NOT_PACSOMS_ORDER")
    public Boolean getIsNotPacsomsOrder() {
        return isNotPacsomsOrder;
    }

    public void setIsNotPacsomsOrder(Boolean isNotPacsomsOrder) {
        this.isNotPacsomsOrder = isNotPacsomsOrder;
    }

    @Column(name = "RULE_CODE")
    public String getRuleCode() {
        return ruleCode;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }

    @Column(name = "puma_Type")
    public String getPumaType() {
        return pumaType;
    }

    public void setPumaType(String pumaType) {
        this.pumaType = pumaType;
    }

    @Column(name = "NO_HAVE_RE_MISS")
    public Boolean getNoHaveReportMissing() {
        return noHaveReportMissing;
    }

    public void setNoHaveReportMissing(Boolean noHaveReportMissing) {
        this.noHaveReportMissing = noHaveReportMissing;
    }

    @Column(name = "area_ocp_status")
    public Integer getAreaOcpStatus() {
        return areaOcpStatus;
    }

    public void setAreaOcpStatus(Integer areaOcpStatus) {
        this.areaOcpStatus = areaOcpStatus;
    }

    @Column(name = "area_ocp_memo")
    public String getAreaOcpMemo() {
        return areaOcpMemo;
    }

    public void setAreaOcpMemo(String areaOcpMemo) {
        this.areaOcpMemo = areaOcpMemo;
    }

    @Column(name = "area_ocp_error_count")
    public String getAreaOcpErrorCount() {
        return areaOcpErrorCount;
    }

    public void setAreaOcpErrorCount(String areaOcpErrorCount) {
        this.areaOcpErrorCount = areaOcpErrorCount;
    }

    @Column(name = "next_ocp_time")
    public Date getNextOcpTime() {
        return nextOcpTime;
    }

    public void setNextOcpTime(Date nextOcpTime) {
        this.nextOcpTime = nextOcpTime;
    }

    @Column(name = "IS_DISTRIBUTE_FAILED")
    public Boolean getIsDistributeFailed() {
        return isDistributeFailed;
    }

    public void setIsDistributeFailed(Boolean isDistributeFailed) {
        this.isDistributeFailed = isDistributeFailed;
    }

    @Column(name = "DATA_SOURCE")
    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    @Column(name = "EXT_TYPE")
    public String getExtType() {
        return extType;
    }

    public void setExtType(String extType) {
        this.extType = extType;
    }

    
    @Column(name = "CARTON_NUM")
    public Integer getCartonNum(){
        return cartonNum;
    }

    
    public void setCartonNum(Integer cartonNum){
        this.cartonNum = cartonNum;
    }

    
}
