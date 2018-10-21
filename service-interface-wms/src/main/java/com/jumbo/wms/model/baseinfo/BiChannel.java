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

package com.jumbo.wms.model.baseinfo;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.vmi.Default.AsnOrderType;
import com.jumbo.wms.model.vmi.Default.RsnType;
import com.jumbo.wms.model.warehouse.ChannelSpecialType;

/**
 * 渠道
 * 
 * @author sjk
 * 
 */
@Entity
@Table(name = "T_BI_CHANNEL")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class BiChannel extends BaseModel {

    public static final String CHANNEL_VMICODE_NIKE_BZ_STORE = "3688";
    public static final String CHANNEL_VMICODE_GDV_TB = "GDV_TB";
    public static final String CHANNEL_VMICODE_GDV_STORE = "GDV_STORE";
    public static final String CHANNEL_VMICODE_CONVERSE_STORE = "4690";
    public static final String CHANNEL_VMICODE_CONVERSE_TMALL = "4691";
    public static final String CHANNEL_VMICODE_CACHECAHEC_CCH = "CCH";
    public static final String CHANNEL_VMICODE_CACHECAHEC_BNB = "BNB";
    public static final String CHANNEL_VMICODE_CONVERSE_YX = "converseyx";
    public static final String CHANNEL_VMICODE_LEVIS = "levisyx";



    private static final long serialVersionUID = -23672138957964519L;

    /**
     * PK
     */
    private Long id;
    /**
     * 店铺名称
     */
    private String name;
    /**
     * 对接唯一编码
     */
    private String code;
    /**
     * 店铺编码
     */
    private String shopCode;
    /**
     * 电话
     */
    private String telephone;
    /**
     * version
     */
    private int version;
    /**
     * 地址
     */
    private String address;
    /**
     * 邮编
     */
    private String zipcode;
    /**
     * 退货仓库地址
     */
    private String rtnWarehouseAddress;
    /**
     * 短信模板
     */
    private String smsTemplate;
    /**
     * 是否开通短信通知
     */
    private Boolean isSms;
    /**
     * 是否允许混合创建配货清单
     */
    private Boolean isMarger;
    /**
     * 品牌店铺映射编码
     */
    private String vmiCode;
    /**
     * 外包仓
     */
    private String vmiWHSource;

    /**
     * MQ 收货确认队列
     */
    private String mqASNReceive;

    /**
     * MQ 退大仓反馈
     */
    private String mqRTV;
    /**
     * 外包仓订单队列
     */
    private String mqOrder;
    /**
     * customer
     */
    private Customer customer;

    /**
     * 公司简称
     */
    private String companyName;

    /**
     * 公司简称（中文）
     */
    private String companyChName;

    /**
     * 入库时候是否须要导入发票数据
     */
    private Boolean isInboundInvoice;


    /**
     * 外包仓
     */
    private String vmiWHSource1;

    /**
     * 是否强制装箱
     */
    private Boolean isReturnNeedPackage;
    /**
     * 是否JD电子面单
     */
    private Boolean isJdolOrder;

    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 过期时间
     */
    private Date expTime;
    /**
     * 状态
     */
    private BiChannelStatus status;
    /**
     * 类型
     */
    private BiChannelType type;

    /**
     * 最近修改时间
     */
    private Date lastModifyTime;

    /**
     * 货主
     */
    private String sfFlag;

    /**
     * 品牌来源（为防止不同来源使用相同品牌店铺编码）
     */
    private String vmiSource;

    /**
     * 收货单据创建方式
     */
    private AsnOrderType asnOrderType;

    /**
     * 收货反馈方式
     */
    private RsnType rsnType;

    /**
     * 收件人省份 用于填充香港收货地址
     */
    private String province;

    /**
     * VMI操作权限 VmiOpType
     */
    private String opType;

    /**
     * 品牌物流月结编码
     */
    private String transAccountsCode;

    /**
     * 是否无须校验入库批次 默认是需要校验入库批次包括保质期商品
     */
    private Boolean isNotValInBoundBachCode = new Boolean(false);
    /**
     * 特殊定制类型
     */
    private ChannelSpecialType specialType;

    /**
     * 是否是快递雷达需要特殊处理的店铺
     */
    private Boolean specialExpressPadar;

    /**
     * 是否执行单件订单基于库位创批
     */
    private Boolean isPickinglistByLoc;

    /**
     * 销售出库取消状态限制类型
     */
    private BiChannelLimitType limitType;

    /**
     * 拆包金额限制
     */
    private BigDecimal limitAmount;
    /**
     * 升单是否锁定
     */
    private Boolean isLockRransUpgrade;
    /**
     * 是否快递升单
     */
    private Boolean isTransUpgrade;
    /**
     * 是否邮件警告快递升单操作
     */
    private Boolean isWarnMailTransUpgrade;
    /**
     * 是否短信到货延迟
     */
    private Boolean isSmsArriveDelay;
    /**
     * 延迟到货短信模版
     */
    private String smsTempArriveDelay;

    private Integer limitTypeInt;
    /**
     * 品牌通用接口标识是否品牌定制
     */
    private Boolean isVmiExt;

    /**
     * 是否默认入库店铺
     */
    private Boolean isDefInboundStore;

    /**
     * 品牌通用接口定制流程
     */
    private String defaultCode;
    /**
     * 是否管理残次品
     */
    private Boolean isImperfect;
    /**
     * 是否是品牌VMI或者采购按箱收货店铺
     */
    private Boolean isCartonStaShop;
    private Boolean isImperfectPoId;
    private Boolean isImperfectTime;
    /**
     * 顺丰结算月结账号
     */
    private String sfJcustid;
    /**
     * 顺丰客户编码
     */
    private String sfJcusttag;
    /**
     * 是否支持次晨达业务
     */
    private Boolean isSupportNextMorning;

    /**
     * 是否支持是否校验批次
     */
    private Boolean isReturnCheckBatch;

    /**
     * ems账号
     */
    private String emsAccount;
    /**
     * 过仓是否校验库存
     */
    private Boolean isNotCheckInventory;

    /**
     * 退货入指定店铺s
     */
    private String returnDefaultOwner;

    /**
     * 物流不可达转EMS
     */
    private Boolean transErrorToEms;
    /**
     * 定制装箱清单
     */
    private Boolean isPackingList;

    /**
     * 店铺销售商品分类
     */
    private String skuCategories;

    /**
     * vmi创入库单是否支持非取消作业单
     */
    private Boolean isSupportNoCancelSta;
    /**
     * 是否使用物流服务获取运单号
     */
    private Boolean isEms;
    /**
     * 是否使用物流服务出家确认
     */
    private Boolean isEmsConfirm;


    private Boolean isPda;// 按箱收货并且按实际反馈 用pda收货 设置为true

    /**
     * 是否打印海关单(澳门件)
     */
    private Boolean isPrintMaCaoHGD;
    /**
     * 打印海关单金额限定(单位：港元)
     */
    private Long moneyLmit;

    private String isHaoCai;// 耗材开关 （0：关）

    /**
     * 是否允许部分出库
     */
    private Boolean isPartOutbound = false;

    /**
     * 是否nikecrw
     */
    private Boolean isCrw = false;

    @Column(name = "IS_HAOCAI")
    public String getIsHaoCai() {
        return isHaoCai;
    }

    public void setIsHaoCai(String isHaoCai) {
        this.isHaoCai = isHaoCai;
    }

    @Column(name = "IS_PRINT_MACAO_HGD")
    public Boolean getIsPrintMaCaoHGD() {
        return isPrintMaCaoHGD;
    }

    public void setIsPrintMaCaoHGD(Boolean isPrintMaCaoHGD) {
        this.isPrintMaCaoHGD = isPrintMaCaoHGD;
    }

    @Column(name = "MONEY_LMIT")
    public Long getMoneyLmit() {
        return moneyLmit;
    }

    public void setMoneyLmit(Long moneyLmit) {
        this.moneyLmit = moneyLmit;
    }

    @Column(name = "IS_PDA")
    public Boolean getIsPda() {
        return isPda;
    }

    public void setIsPda(Boolean isPda) {
        this.isPda = isPda;
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_CMPSHOP", sequenceName = "S_T_BI_CHANNEL", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CMPSHOP")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "ems_account")
    public String getEmsAccount() {
        return emsAccount;
    }

    public void setEmsAccount(String emsAccount) {
        this.emsAccount = emsAccount;
    }

    @Column(name = "TELEPHONE", length = 50)
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Column(name = "ADDRESS", length = 300)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "ZIPCODE", length = 6)
    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    @Version
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Column(name = "RTN_WAREHOUSE_ADDRESS")
    public String getRtnWarehouseAddress() {
        return rtnWarehouseAddress;
    }

    public void setRtnWarehouseAddress(String rtnWarehouseAddress) {
        this.rtnWarehouseAddress = rtnWarehouseAddress;
    }

    @Column(name = "SMS_TEMPLATE", length = 350)
    public String getSmsTemplate() {
        return smsTemplate;
    }

    public void setSmsTemplate(String smsTemplate) {
        this.smsTemplate = smsTemplate;
    }

    @Column(name = "IS_SMS")
    public Boolean getIsSms() {
        return isSms;
    }

    public void setIsSms(Boolean isSms) {
        this.isSms = isSms;
    }

    @Column(name = "VMI_CODE", length = 30)
    public String getVmiCode() {
        return vmiCode;
    }

    public void setVmiCode(String vmiCode) {
        this.vmiCode = vmiCode;
    }

    @Column(name = "MQ_ASN_RECEIVE", length = 50)
    public String getMqASNReceive() {
        return mqASNReceive;
    }

    public void setMqASNReceive(String mqASNReceive) {
        this.mqASNReceive = mqASNReceive;
    }

    @Column(name = "MQ_RTV", length = 50)
    public String getMqRTV() {
        return mqRTV;
    }

    public void setMqRTV(String mqRTV) {
        this.mqRTV = mqRTV;
    }

    @Column(name = "VMI_WH_SOURCE", length = 30)
    public String getVmiWHSource() {
        return vmiWHSource;
    }

    public void setVmiWHSource(String vmiWHSource) {
        this.vmiWHSource = vmiWHSource;
    }

    @Column(name = "NAME", length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "SHOP_CODE", length = 30)
    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    @Column(name = "CODE", length = 30)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID")
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Column(name = "MQ_ORDER", length = 60)
    public String getMqOrder() {
        return mqOrder;
    }

    public void setMqOrder(String mqOrder) {
        this.mqOrder = mqOrder;
    }

    @Column(name = "IS_MARGER")
    public Boolean getIsMarger() {
        return isMarger;
    }

    public void setIsMarger(Boolean isMarger) {
        this.isMarger = isMarger;
    }

    @Column(name = "IS_INBOUND_INVOICE")
    public Boolean getIsInboundInvoice() {
        return isInboundInvoice;
    }

    public void setIsInboundInvoice(Boolean isInboundInvoice) {
        this.isInboundInvoice = isInboundInvoice;
    }

    @Column(name = "VMI_WH_SOURCE1", length = 30)
    public String getVmiWHSource1() {
        return vmiWHSource1;
    }

    public void setVmiWHSource1(String vmiWHSource1) {
        this.vmiWHSource1 = vmiWHSource1;
    }

    @Column(name = "IS_RETURN_NEED_PACKAGE")
    public Boolean getIsReturnNeedPackage() {
        return isReturnNeedPackage;
    }

    public void setIsReturnNeedPackage(Boolean isReturnNeedPackage) {
        this.isReturnNeedPackage = isReturnNeedPackage;
    }

    @Column(name = "is_jd_ol_order")
    public Boolean getIsJdolOrder() {
        return isJdolOrder;
    }

    public void setIsJdolOrder(Boolean isJdolOrder) {
        this.isJdolOrder = isJdolOrder;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "EXP_TIME")
    public Date getExpTime() {
        return expTime;
    }

    public void setExpTime(Date expTime) {
        this.expTime = expTime;
    }

    @Column(name = "STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.baseinfo.BiChannelStatus")})
    public BiChannelStatus getStatus() {
        return status;
    }

    public void setStatus(BiChannelStatus status) {
        this.status = status;
    }

    @Column(name = "TYPE", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.baseinfo.BiChannelType")})
    public BiChannelType getType() {
        return type;
    }

    public void setType(BiChannelType type) {
        this.type = type;
    }

    @Column(name = "LAST_MODIFY_TIME")
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    @Column(name = "SF_FLAG")
    public String getSfFlag() {
        return sfFlag;
    }

    public void setSfFlag(String sfFlag) {
        this.sfFlag = sfFlag;
    }

    @Column(name = "VMI_SOURCE")
    public String getVmiSource() {
        return vmiSource;
    }

    public void setVmiSource(String vmiSource) {
        this.vmiSource = vmiSource;
    }

    @Column(name = "ASN_ORDER_TYPE", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.vmi.Default.AsnOrderType")})
    public AsnOrderType getAsnOrderType() {
        return asnOrderType;
    }

    public void setAsnOrderType(AsnOrderType asnOrderType) {
        this.asnOrderType = asnOrderType;
    }

    @Column(name = "RSN_TYPE", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.vmi.Default.RsnType")})
    public RsnType getRsnType() {
        return rsnType;
    }

    public void setRsnType(RsnType rsnType) {
        this.rsnType = rsnType;
    }

    @Column(name = "PROVINCE")
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Column(name = "OP_TYPE")
    public String getOpType() {
        return opType;
    }

    public void setOpType(String opType) {
        this.opType = opType;
    }

    @Column(name = "TRANS_ACCOUNTS_CODE")
    public String getTransAccountsCode() {
        return transAccountsCode;
    }

    public void setTransAccountsCode(String transAccountsCode) {
        this.transAccountsCode = transAccountsCode;
    }

    @Column(name = "IS_NOT_VAL_IN_BATCHCODE")
    public Boolean getIsNotValInBoundBachCode() {
        return isNotValInBoundBachCode;
    }

    public void setIsNotValInBoundBachCode(Boolean isNotValInBoundBachCode) {
        this.isNotValInBoundBachCode = isNotValInBoundBachCode;
    }

    @Column(name = "SPECIAL_TYPE", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.ChannelSpecialType")})
    public ChannelSpecialType getSpecialType() {
        return specialType;
    }

    public void setSpecialType(ChannelSpecialType specialType) {
        this.specialType = specialType;
    }

    @Column(name = "SPECIAL_EXPRESS_RADAR")
    public Boolean getSpecialExpressPadar() {
        return specialExpressPadar;
    }

    public void setSpecialExpressPadar(Boolean specialExpressPadar) {
        this.specialExpressPadar = specialExpressPadar;
    }

    @Column(name = "LIMIT_SO_CHANNEL_TYPE", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.baseinfo.BiChannelLimitType")})
    public BiChannelLimitType getLimitType() {
        return limitType;
    }

    public void setLimitType(BiChannelLimitType limitType) {
        this.limitType = limitType;
    }

    @Column(name = "LIMIT_PG_AMOUNT")
    public BigDecimal getLimitAmount() {
        return limitAmount;
    }

    public void setLimitAmount(BigDecimal limitAmount) {
        this.limitAmount = limitAmount;
    }

    @Column(name = "COMPANY_NAME")
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Transient
    public String getCompanyChName() {
        return companyChName;
    }

    public void setCompanyChName(String companyChName) {
        this.companyChName = companyChName;
    }

    @Column(name = "is_lock_trans_upgrade")
    public Boolean getIsLockRransUpgrade() {
        return isLockRransUpgrade;
    }

    public void setIsLockRransUpgrade(Boolean isLockRransUpgrade) {
        this.isLockRransUpgrade = isLockRransUpgrade;
    }

    @Column(name = "is_sms_arrive_delay")
    public Boolean getIsSmsArriveDelay() {
        return isSmsArriveDelay;
    }

    public void setIsSmsArriveDelay(Boolean isSmsArriveDelay) {
        this.isSmsArriveDelay = isSmsArriveDelay;
    }

    @Column(name = "is_trans_upgrade")
    public Boolean getIsTransUpgrade() {
        return isTransUpgrade;
    }

    public void setIsTransUpgrade(Boolean isTransUpgrade) {
        this.isTransUpgrade = isTransUpgrade;
    }

    @Column(name = "is_warn_mail_trans_upgrade_exe")
    public Boolean getIsWarnMailTransUpgrade() {
        return isWarnMailTransUpgrade;
    }

    public void setIsWarnMailTransUpgrade(Boolean isWarnMailTransUpgrade) {
        this.isWarnMailTransUpgrade = isWarnMailTransUpgrade;
    }

    @Column(name = "SMS_TEMP_ARRIVE_DELAY")
    public String getSmsTempArriveDelay() {
        return smsTempArriveDelay;
    }

    public void setSmsTempArriveDelay(String smsTempArriveDelay) {
        this.smsTempArriveDelay = smsTempArriveDelay;
    }

    @Transient
    public Integer getLimitTypeInt() {
        return limitTypeInt;
    }

    public void setLimitTypeInt(Integer limitTypeInt) {
        this.limitTypeInt = limitTypeInt;
    }

    @Column(name = "IS_VMI_EXT")
    public Boolean getIsVmiExt() {
        return isVmiExt;
    }

    public void setIsVmiExt(Boolean isVmiExt) {
        this.isVmiExt = isVmiExt;
    }

    @Column(name = "IS_DEF_INBOUND_STORE")
    public Boolean getIsDefInboundStore() {
        return isDefInboundStore;
    }

    public void setIsDefInboundStore(Boolean isDefInboundStore) {
        this.isDefInboundStore = isDefInboundStore;
    }

    @Column(name = "DEFAULT_CODE")
    public String getDefaultCode() {
        return defaultCode;
    }

    public void setDefaultCode(String defaultCode) {
        this.defaultCode = defaultCode;
    }

    @Column(name = "IS_CARTONSTA_SHOP")
    public Boolean getIsCartonStaShop() {
        return isCartonStaShop;
    }

    public void setIsCartonStaShop(Boolean isCartonStaShop) {
        this.isCartonStaShop = isCartonStaShop;
    }

    @Column(name = "is_imperfect")
    public Boolean getIsImperfect() {
        return isImperfect;
    }

    public void setIsImperfect(Boolean isImperfect) {
        this.isImperfect = isImperfect;
    }

    @Column(name = "Is_Imperfect_Po_Id")
    public Boolean getIsImperfectPoId() {
        return isImperfectPoId;
    }

    public void setIsImperfectPoId(Boolean isImperfectPoId) {
        this.isImperfectPoId = isImperfectPoId;
    }

    @Column(name = "is_Imperfect_Time")
    public Boolean getIsImperfectTime() {
        return isImperfectTime;
    }

    public void setIsImperfectTime(Boolean isImperfectTime) {
        this.isImperfectTime = isImperfectTime;
    }

    @Column(name = "sf_jcustid")
    public String getSfJcustid() {
        return sfJcustid;
    }

    public void setSfJcustid(String sfJcustid) {
        this.sfJcustid = sfJcustid;
    }

    @Column(name = "sf_jcusttag")
    public String getSfJcusttag() {
        return sfJcusttag;
    }

    public void setSfJcusttag(String sfJcusttag) {
        this.sfJcusttag = sfJcusttag;
    }

    @Column(name = "IS_SUPPORT_NEXT_MORNING")
    public Boolean getIsSupportNextMorning() {
        return isSupportNextMorning;
    }

    public void setIsSupportNextMorning(Boolean isSupportNextMorning) {
        this.isSupportNextMorning = isSupportNextMorning;
    }

    @Column(name = "IS_RETURN_CHECK_BATCH")
    public Boolean getIsReturnCheckBatch() {
        return isReturnCheckBatch;
    }

    public void setIsReturnCheckBatch(Boolean isReturnCheckBatch) {
        this.isReturnCheckBatch = isReturnCheckBatch;
    }

    @Column(name = "IS_NOT_CHECK_INV")
    public Boolean getIsNotCheckInventory() {
        return isNotCheckInventory;
    }

    public void setIsNotCheckInventory(Boolean isNotCheckInventory) {
        this.isNotCheckInventory = isNotCheckInventory;
    }

    @Column(name = "RETURN_DEFAULT_OWNER")
    public String getReturnDefaultOwner() {
        return returnDefaultOwner;
    }

    public void setReturnDefaultOwner(String returnDefaultOwner) {
        this.returnDefaultOwner = returnDefaultOwner;
    }

    @Column(name = "TRANS_ERROR_TO_EMS")
    public Boolean getTransErrorToEms() {
        return transErrorToEms;
    }

    public void setTransErrorToEms(Boolean transErrorToEms) {
        this.transErrorToEms = transErrorToEms;
    }

    @Column(name = "is_Packing_List")
    public Boolean getIsPackingList() {
        return isPackingList;
    }

    public void setIsPackingList(Boolean isPackingList) {
        this.isPackingList = isPackingList;
    }

    @Column(name = "SKU_CATEGORIES")
    public String getSkuCategories() {
        return skuCategories;
    }

    public void setSkuCategories(String skuCategories) {
        this.skuCategories = skuCategories;
    }

    @Column(name = "IS_SUPPORT_NOCANCEL_STA")
    public Boolean getIsSupportNoCancelSta() {
        if (isSupportNoCancelSta == null) {
            isSupportNoCancelSta = false;
        }
        return isSupportNoCancelSta;
    }

    public void setIsSupportNoCancelSta(Boolean isSupportNoCancelSta) {
        this.isSupportNoCancelSta = isSupportNoCancelSta;
    }

    @Column(name = "is_ems")
    public Boolean getIsEms() {
        return isEms;
    }

    public void setIsEms(Boolean isEms) {
        this.isEms = isEms;
    }

    @Column(name = "is_ems_confirm")
    public Boolean getIsEmsConfirm() {
        return isEmsConfirm;
    }

    public void setIsEmsConfirm(Boolean isEmsConfirm) {
        this.isEmsConfirm = isEmsConfirm;
    }

    @Column(name = "is_pickinglist_by_loc")
    public Boolean getIsPickinglistByLoc() {
        return isPickinglistByLoc;
    }

    public void setIsPickinglistByLoc(Boolean isPickinglistByLoc) {
        this.isPickinglistByLoc = isPickinglistByLoc;
    }

    @Column(name = "is_part_outbound")
    public Boolean getIsPartOutbound() {
        return isPartOutbound;
    }

    public void setIsPartOutbound(Boolean isPartOutbound) {
        this.isPartOutbound = isPartOutbound;
    }

    @Column(name = "is_crw")
    public Boolean getIsCrw() {
        return isCrw;
    }

    public void setIsCrw(Boolean isCrw) {
        this.isCrw = isCrw;
    }



}
