package com.jumbo.wms.model.warehouse;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.authorization.User;

/**
 * 配货清单
 * 
 * @author sjk
 * 
 */

@Entity
@Table(name = "T_WH_STA_PICKING_LIST")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class PickingList extends BaseModel {

    private static final long serialVersionUID = -2394944232789025172L;

    /**
     * Pk
     */
    private Long id;
    /**
     * version
     */
    private int version;
    /**
     * 拣货模式
     */
    private ParcelSortingMode sortingMode;
    /**
     * 批次号 格式sortingMode_YYMMDD_4位流水
     */
    private String code;
    /**
     * 创建时间
     */
    private Date createTime = new Date();
    /**
     * 计划picking单据数
     */
    private Integer planBillCount;
    /**
     * 已核对单据数
     */
    private Integer checkedBillCount = 0;
    /**
     * 计划picking商品件数
     */
    private Integer planSkuQty;
    /**
     * 已核对商品件数
     */
    private Integer checkedSkuQty = 0;
    /**
     * 关联仓库
     */
    private OperationUnit warehouse;
    /**
     * 最新核对时间
     */
    private Date checkedTime;
    /**
     * 最新执行时间
     */
    private Date executedTime;
    /**
     * 开始配货时间
     */
    private Date pickingTime;
    /**
     * 配货模式
     */
    private PickingMode pickingMode;
    /**
     * 创建人
     */
    private User creator;
    /**
     * 状态
     */
    private PickingListStatus status;
    /**
     * 配货清单操作员
     */
    private User operator;

    /**
     * 打印次数
     */
    private Long outputCount;

    /**
     * 配单单据集合
     */
    private List<StockTransApplication> staList;
    /**
     * 核对模式
     */
    private PickingListCheckMode checkMode = PickingListCheckMode.DEFAULE;
    /**
     * 是否特殊包装
     */
    private Boolean isSpecialPackaging;
    /**
     * 物流
     */
    private String lpcode;

    private Boolean isMqInvoice = false;

    /**
     * 是否包含大件商品
     */
    private Boolean isBigBox;
    /**
     * 是否陆运
     */
    private Boolean isRailway;
    /**
     * 是否团购
     */
    private Boolean isGroupBuying;

    /**
     * 秒杀生成的物流交接单
     */
    private HandOverList handOverList;
    /**
     * 标记配货清单大中小件 打印时用 取值：大、中、小、团、秒
     */
    private String flagName;
    /**
     * 是否包含SN号商品
     */
    private Boolean isSn;
    /**
     * 运送方式(快递附加服务)
     */
    private TransType transType;

    /**
     * 运单时限类型(快递附加服务)
     */
    private TransTimeType transTimeType;

    /**
     * 仓储附属状态
     */
    private WhAddStatusMode whStatus;

    /**
     * 仓储附属状态类型
     */
    private WhAddTypeMode whType;

    /**
     * 发票数
     */
    private Integer invoiceNum;

    /**
     * 是否后置装箱清单
     */
    private Boolean isPostpositionPackingPage = false;

    /**
     * 是否运单后置
     */
    private Boolean isPostpositionExpressBill = false;

    /**
     * 是否需要发票
     */
    private Boolean isInvoice = false;
    /**
     * 发货城市
     */
    private String city;
    /**
     * 商品分类
     */
    private Long categoryId;
    /**
     * 商品大小件分类ID
     */
    private Long skuSizeId;
    /**
     * 商品大小名称
     */
    private String skuSizeName;
    /**
     * 商品分类名称
     */
    private String categoryName;
    /**
     * 是否SNSTRIng
     */
    private String isSn1;

    /**
     * 是否需要发票String
     */
    private String isInvoice1;
    /**
     * 原关联CODE
     */
    private String cancelRemark;
    /**
     * 取消人
     */
    private Long cancelUserId;
    /**
     * 套装商品 格式 skuId;quantity|....
     */
    private String packageSku;

    /**
     * O2O门店编码
     */
    private String toLocation;

    /**
     * 特殊处理类型
     */
    private SpecialSkuType specialType;

    private Integer isCod;

    private String worker;

    /**
     * 是否后端核对SKU
     */
    private Boolean isBkCheck;

    /**
     * 包装类型； 1：礼盒包装
     */
    private Integer packingType;

    /**
     * 货箱数量
     */
    private Integer boxCount;

    /**
     * O2O标识
     */
    private Boolean isOTwoo;

    /**
     * 13,拣货开始、14,拣货结束
     */
    private Integer pickingStatus;

    /**
     * 拣货开始时间
     */
    private Date pickingStartTime;

    /**
     * 拣货结束时间
     */
    private Date pickingEndTime;

    /**
     * 拣货登录账号
     */
    private User pickingUser;
    /**
     * 是否开始打单拣货
     */
    private Boolean isHavePrint;

    /**
     * 配货模式类型。 1： 多件跨区域 2：单件 跨仓库区域 3：秒杀 跨仓库区域 4：团购 跨仓库区域 5； 套装组合商品 跨仓库区域 6：特殊处理 跨仓库区域 7： QS 跨仓库区域
     * 
     */
    private Integer pickModleType;

    /**
     * 集货类型 1:不集货 2:自动化集货 3:人工集货
     */
    private Integer goodsCollectionType;

    /**
     * 是否预售 0/空 不是 1：是
     */
    private String isPreSale;

    /**
     * 二次分拣是否完成
     */
    private Boolean twoPickingOver;


    /**
     * 一键创批 库位标识
     */
    private Long locId;

    /**
     * 是否OTO批次
     */
    private Boolean isOtoPicking = false;


    /**
     * 开始打印
     */
    private Boolean startPrint = false;

    @Column(name = "LOC_ID")
    public Long getLocId() {
        return locId;
    }

    public void setLocId(Long locId) {
        this.locId = locId;
    }

    @Column(name = "IS_PRE_SALE")
    public String getIsPreSale() {
        return isPreSale;
    }

    public void setIsPreSale(String isPreSale) {
        this.isPreSale = isPreSale;
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_PKLT", sequenceName = "S_T_WH_STA_PICKING_LIST", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PKLT")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Version
    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Column(name = "CODE", length = 50)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.PickingListStatus")})
    public PickingListStatus getStatus() {
        return status;
    }

    public void setStatus(PickingListStatus status) {
        this.status = status;
    }

    @Column(name = "PLAN_BILL_COUNT")
    public Integer getPlanBillCount() {
        return planBillCount;
    }

    public void setPlanBillCount(Integer planBillCount) {
        this.planBillCount = planBillCount;
    }

    @Column(name = "CHECK_BILL_COUNT")
    public Integer getCheckedBillCount() {
        return checkedBillCount;
    }

    public void setCheckedBillCount(Integer checkedBillCount) {
        this.checkedBillCount = checkedBillCount;
    }

    @Column(name = "PLAN_SKU_QTY")
    public Integer getPlanSkuQty() {
        return planSkuQty;
    }

    public void setPlanSkuQty(Integer planSkuQty) {
        this.planSkuQty = planSkuQty;
    }

    @Column(name = "CHECK_SKU_QTY")
    public Integer getCheckedSkuQty() {
        return checkedSkuQty;
    }

    public void setCheckedSkuQty(Integer checkedSkuQty) {
        this.checkedSkuQty = checkedSkuQty;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WH_ID")
    @Index(name = "IDX_PL_WH")
    public OperationUnit getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(OperationUnit warehouse) {
        this.warehouse = warehouse;
    }

    @Column(name = "CHECK_TIME")
    public Date getCheckedTime() {
        return checkedTime;
    }

    public void setCheckedTime(Date checkedTime) {
        this.checkedTime = checkedTime;
    }

    @Column(name = "PICKING_TIME")
    public Date getPickingTime() {
        return pickingTime;
    }

    public void setPickingTime(Date pickingTime) {
        this.pickingTime = pickingTime;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OUT_CREATER_ID")
    @Index(name = "IDX_PL_OUT_CREATER")
    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OUT_OPERATOR_ID")
    @Index(name = "IDX_PICKING_lIST_OPERATOR")
    public User getOperator() {
        return operator;
    }

    public void setOperator(User operator) {
        this.operator = operator;
    }

    @Column(name = "EXECUTED_TIME")
    public Date getExecutedTime() {
        return executedTime;
    }

    public void setExecutedTime(Date executedTime) {
        this.executedTime = executedTime;
    }

    @Column(name = "SORTING_MODE", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.ParcelSortingMode")})
    public ParcelSortingMode getSortingMode() {
        return sortingMode;
    }

    public void setSortingMode(ParcelSortingMode sortingMode) {
        this.sortingMode = sortingMode;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "pickingList", orphanRemoval = true)
    @OrderBy("id")
    public List<StockTransApplication> getStaList() {
        return staList;
    }

    public void setStaList(List<StockTransApplication> staList) {
        this.staList = staList;
    }

    public String codePrefix() {
        if (pickingMode == null) {
            // 新过仓逻辑编码
            if (checkMode == null) {
                throw new IllegalArgumentException();
            } else {
                switch (checkMode) {
                    case DEFAULE:
                        return "S";
                    case PICKING_CHECK:
                        return "P";
                    case PICKING_GROUP:
                        return "G";
                    case PICKING_SECKILL:
                        return "K";
                    case PICKING_PACKAGE:
                        return "T";
                    case PICKING_SPECIAL:
                        return "B";
                    default:
                        throw new IllegalArgumentException();
                }
            }
        } else if (pickingMode.getValue() == PickingMode.MODE1.getValue()) {
            return sortingMode.getValue() + "_";
        } else {
            return sortingMode.getValue() + "" + this.getPickingMode().getValue() + "_";
        }
    }

    @Column(name = "PICKING_MODE", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.PickingMode")})
    public PickingMode getPickingMode() {
        return pickingMode;
    }

    public void setPickingMode(PickingMode pickingMode) {
        this.pickingMode = pickingMode;
    }

    @Transient
    public Integer getIntStatus() {
        return status == null ? -1 : status.getValue();
    }

    public void setIntStatus(Integer intStatus) {
        if (intStatus != null) {
            setStatus(PickingListStatus.valueOf(intStatus));
        }
    }

    @Column(name = "OUTPUT_COUNT")
    public Long getOutputCount() {
        return outputCount;
    }

    public void setOutputCount(Long outputCount) {
        this.outputCount = outputCount;
    }

    @Column(name = "CHECK_MODE", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.PickingListCheckMode")})
    public PickingListCheckMode getCheckMode() {
        return checkMode;
    }

    public void setCheckMode(PickingListCheckMode checkMode) {
        this.checkMode = checkMode;
    }

    @Column(name = "IS_SPECIAL_PACKAGING")
    public Boolean getIsSpecialPackaging() {
        return isSpecialPackaging;
    }

    public void setIsSpecialPackaging(Boolean isSpecialPackaging) {
        this.isSpecialPackaging = isSpecialPackaging;
    }

    @Column(name = "LPCODE", length = 20)
    public String getLpcode() {
        return lpcode;
    }

    public void setLpcode(String lpcode) {
        this.lpcode = lpcode;
    }

    @Column(name = "IS_MQ_INVOICE")
    public Boolean getIsMqInvoice() {
        return isMqInvoice;
    }

    public void setIsMqInvoice(Boolean isMqInvoice) {
        this.isMqInvoice = isMqInvoice;
    }

    @Column(name = "IS_BIG_BOX")
    public Boolean getIsBigBox() {
        return isBigBox;
    }

    public void setIsBigBox(Boolean isBigBox) {
        this.isBigBox = isBigBox;
    }

    @Column(name = "IS_RAIL_WAY")
    public Boolean getIsRailway() {
        return isRailway;
    }

    public void setIsRailway(Boolean isRailway) {
        this.isRailway = isRailway;
    }

    @Column(name = "IS_GROUP_BUYING")
    public Boolean getIsGroupBuying() {
        return isGroupBuying;
    }

    public void setIsGroupBuying(Boolean isGroupBuying) {
        this.isGroupBuying = isGroupBuying;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HAND_ID")
    @Index(name = "IDX_PL_HOL")
    public HandOverList getHandOverList() {
        return handOverList;
    }

    public void setHandOverList(HandOverList handOverList) {
        this.handOverList = handOverList;
    }

    @Column(name = "FLAG_NAME", length = 10)
    public String getFlagName() {
        return flagName;
    }

    public void setFlagName(String flagName) {
        this.flagName = flagName;
    }

    @Column(name = "IS_SN")
    public Boolean getIsSn() {
        return isSn;
    }

    public void setIsSn(Boolean isSn) {
        this.isSn = isSn;
    }

    @Column(name = "TRANS_TYPE", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.TransType")})
    public TransType getTransType() {
        return transType;
    }

    public void setTransType(TransType transType) {
        this.transType = transType;
    }

    @Column(name = "TRANS_TIME_TYPE", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.TransTimeType")})
    public TransTimeType getTransTimeType() {
        return transTimeType;
    }

    public void setTransTimeType(TransTimeType transTimeType) {
        this.transTimeType = transTimeType;
    }

    @Column(name = "WH_ADD_STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.WhAddStatusMode")})
    public WhAddStatusMode getWhStatus() {
        return whStatus;
    }

    public void setWhStatus(WhAddStatusMode whStatus) {
        this.whStatus = whStatus;
    }

    @Column(name = "WH_ADD_TYPE", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.WhAddTypeMode")})
    public WhAddTypeMode getWhType() {
        return whType;
    }

    public void setWhType(WhAddTypeMode whType) {
        this.whType = whType;
    }

    @Column(name = "IS_INVOICE_NUM")
    public Integer getInvoiceNum() {
        return invoiceNum;
    }

    public void setInvoiceNum(Integer invoiceNum) {
        this.invoiceNum = invoiceNum;
    }

    @Column(name = "IS_POSTPOSITION_PACKING_PAGE")
    public Boolean getIsPostpositionPackingPage() {
        return isPostpositionPackingPage;
    }

    public void setIsPostpositionPackingPage(Boolean isPostpositionPackingPage) {
        this.isPostpositionPackingPage = isPostpositionPackingPage;
    }

    @Column(name = "IS_POSTPOSITION_EXPRESS_BILL")
    public Boolean getIsPostpositionExpressBill() {
        return isPostpositionExpressBill;
    }

    public void setIsPostpositionExpressBill(Boolean isPostpositionExpressBill) {
        this.isPostpositionExpressBill = isPostpositionExpressBill;
    }

    @Column(name = "IS_INVOICE")
    public Boolean getIsInvoice() {
        return isInvoice;
    }

    public void setIsInvoice(Boolean isInvoice) {
        this.isInvoice = isInvoice;
    }

    @Column(name = "SEND_CITY", length = 100)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "CATEGORY_ID")
    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    @Column(name = "SKU_SIZE_ID")
    public Long getSkuSizeId() {
        return skuSizeId;
    }

    public void setSkuSizeId(Long skuSizeId) {
        this.skuSizeId = skuSizeId;
    }

    @Transient
    public String getSkuSizeName() {
        return skuSizeName;
    }

    public void setSkuSizeName(String skuSizeName) {
        this.skuSizeName = skuSizeName;
    }

    @Transient
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Transient
    public String getIsSn1() {
        return isSn1;
    }

    public void setIsSn1(String isSn1) {
        this.isSn1 = isSn1;
    }

    @Transient
    public String getIsInvoice1() {
        return isInvoice1;
    }

    public void setIsInvoice1(String isInvoice1) {
        this.isInvoice1 = isInvoice1;
    }

    @Column(name = "cancel_remark")
    public String getCancelRemark() {
        return cancelRemark;
    }

    public void setCancelRemark(String cancelRemark) {
        this.cancelRemark = cancelRemark;
    }

    @Column(name = "cancel_user_id")
    public Long getCancelUserId() {
        return cancelUserId;
    }

    public void setCancelUserId(Long cancelUserId) {
        this.cancelUserId = cancelUserId;
    }

    @Column(name = "PACKAGE_SKU")
    public String getPackageSku() {
        return packageSku;
    }

    public void setPackageSku(String packageSku) {
        this.packageSku = packageSku;
    }

    @Column(name = "TO_LOCATION")
    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    @Column(name = "SPECIAL_TYPE", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.SpecialSkuType")})
    public SpecialSkuType getSpecialType() {
        return specialType;
    }

    public void setSpecialType(SpecialSkuType specialType) {
        this.specialType = specialType;
    }

    @Column(name = "IS_COD")
    public Integer getIsCod() {
        return isCod;
    }

    public void setIsCod(Integer isCod) {
        this.isCod = isCod;
    }

    @Transient
    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    @Column(name = "IS_BK_CHECK")
    public Boolean getIsBkCheck() {
        return isBkCheck;
    }

    public void setIsBkCheck(Boolean isBkCheck) {
        this.isBkCheck = isBkCheck;
    }

    @Column(name = "PACKING_TYPE")
    public Integer getPackingType() {
        return packingType;
    }

    public void setPackingType(Integer packingType) {
        this.packingType = packingType;
    }

    @Column(name = "is_otwoo")
    public Boolean getIsOTwoo() {
        return isOTwoo;
    }

    public void setIsOTwoo(Boolean isOTwoo) {
        this.isOTwoo = isOTwoo;
    }

    @Column(name = "BOX_COUNT")
    public Integer getBoxCount() {
        return boxCount;
    }

    public void setBoxCount(Integer boxCount) {
        this.boxCount = boxCount;
    }

    @Column(name = "PICKING_STATUS")
    public Integer getPickingStatus() {
        return pickingStatus;
    }

    public void setPickingStatus(Integer pickingStatus) {
        this.pickingStatus = pickingStatus;
    }

    @Column(name = "PICKING_START_TIME")
    public Date getPickingStartTime() {
        return pickingStartTime;
    }

    public void setPickingStartTime(Date pickingStartTime) {
        this.pickingStartTime = pickingStartTime;
    }

    @Column(name = "PICKING_END_TIME")
    public Date getPickingEndTime() {
        return pickingEndTime;
    }

    public void setPickingEndTime(Date pickingEndTime) {
        this.pickingEndTime = pickingEndTime;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PICKING_USER")
    public User getPickingUser() {
        return pickingUser;
    }

    public void setPickingUser(User pickingUser) {
        this.pickingUser = pickingUser;
    }

    @Column(name = "IS_HAVE_PRINT")
    public Boolean getIsHavePrint() {
        return isHavePrint;
    }

    public void setIsHavePrint(Boolean isHavePrint) {
        this.isHavePrint = isHavePrint;
    }

    @Column(name = "pick_modle_type")
    public Integer getPickModleType() {
        return pickModleType;
    }

    public void setPickModleType(Integer pickModleType) {
        this.pickModleType = pickModleType;
    }

    @Column(name = "goods_collection_type")
    public Integer getGoodsCollectionType() {
        return goodsCollectionType;
    }

    public void setGoodsCollectionType(Integer goodsCollectionType) {
        this.goodsCollectionType = goodsCollectionType;
    }

    @Column(name = "TWO_PICKING_OVER")
    public Boolean getTwoPickingOver() {
        return twoPickingOver;
    }

    public void setTwoPickingOver(Boolean twoPickingOver) {
        this.twoPickingOver = twoPickingOver;
    }

    @Column(name = "IS_OTO_PICKING")
    public Boolean getIsOtoPicking() {
        return isOtoPicking;
    }

    public void setIsOtoPicking(Boolean isOtoPicking) {
        this.isOtoPicking = isOtoPicking;
    }


    @Column(name = "START_PRINT")
    public Boolean getStartPrint() {
        return startPrint;
    }

    public void setStartPrint(Boolean startPrint) {
        this.startPrint = startPrint;
    }

}
