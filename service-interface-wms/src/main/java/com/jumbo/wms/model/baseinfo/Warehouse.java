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
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.warehouse.WarehouseSaleOcpType;
import com.jumbo.wms.model.warehouse.WarehouseSourceSkuType;

/**
 * 仓库，对应组织类型是Warehouse的组织实体，记录有仓库的附加信息
 * 
 * @author benjamin
 * 
 */
@Entity
@Table(name = "T_BI_WAREHOUSE", uniqueConstraints = {@UniqueConstraint(columnNames = {"OU_ID"})})
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class Warehouse extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = 7074461234517050400L;

    /**
     * PK
     */
    private Long id;
    /**
     * 客户
     */
    private Customer customer;

    /**
     * 运作模式
     */
    private WarehouseOpMode opMode = WarehouseOpMode.NORMAL;

    /**
     * 管理模式
     */
    private WarehouseManageMode manageMode = WarehouseManageMode.NORMAL;

    /**
     * 负责人姓名
     */
    private String pic;

    /**
     * 负责人联系方式
     */
    private String picContact;

    /**
     * 仓库电话
     */
    private String phone;

    /**
     * 仓库传真
     */
    private String fax;

    /**
     * 其他联系人及方式1
     */
    private String otherContact1;

    /**
     * 其他联系人及方式2
     */
    private String otherContact2;

    /**
     * 其他联系人及方式3
     */
    private String otherContact3;

    /**
     * 占地面积
     */
    private BigDecimal size;

    /**
     * 可用面积
     */
    private BigDecimal availSize;

    /**
     * 库内作业人员数量
     */
    private Integer workerNum;

    /**
     * 是否共享
     */
    private Boolean isShare = false;

    /**
     * Version
     */
    private int version;

    /**
     * 发运地
     */
    private String departure;

    /**
     * 相关组织
     */
    private OperationUnit ou;
    /**
     * 是否需要包材
     */
    private Boolean isNeedWrapStuff;

    /**
     * MQ队列名称-发票
     */
    private String invoiceTaxMqCode;

    /**
     * 外包仓-编码
     */
    private String vmiSource;
    /**
     * 外包仓-编码(对应同公司下多仓库)
     */
    private String vmiSourceWh;
    /**
     * 仓库地址
     */
    private String address;
    /**
     * 是否允许手工称重
     */
    private Boolean isManualWeighing = false;
    /**
     * 核对是否需要条码
     */
    private Boolean isCheckedBarcode = false;
    /**
     * 是否顺丰电子运单
     */
    private Boolean isSfOlOrder = false;
    /**
     * 是否中通电子运单
     */
    private Boolean isZtoOlOrder = false;
    /**
     * 是否EMS电子运单
     */
    private Boolean isEmsOlOrder = false;
    /**
     * 是否EMS电子运单
     */
    private Boolean isOlSto = false;
    /**
     * 是否TTK电子运单
     */
    private Boolean isTtkOlOrder = false;
    /**
     * 是否万象电子运单 万象只有电子面单 默认为true
     */
    private Boolean isWxOlOrder = true;
    /**
     * 是否CXC电子运单 CXC只有电子面单 默认为true
     */
    private Boolean isCxcOlOrder = true;
    /**
     * 是否YTO电子运单
     */
    private Boolean isYtoOlOrder = false;
    /**
     * 是否RFD电子运单
     */
    private Boolean isRfdOlOrder = false;
    /**
     * 是否DHL电子运单
     */
    private Boolean isDhlOlOrder = false;

    /**
     * 是否Kerrya电子面单
     */
    private Boolean isKerryaOlOrder;
    /**
     * 是否MQ传送发票
     */
    private Boolean isMqInvoice = false;
    /**
     * 是否需要外包仓开票(针对外包仓存在发票接口)
     */
    private Boolean isOutInvoice = false;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 区
     */
    private String district;
    /**
     * 邮编
     */
    private String zipcode;
    /**
     * 顺风仓库网店编码
     */
    private String sfWhCode;

    /**
     * 顺风仓库网店COD编码
     */
    private String sfWhCodeCod;
    /**
     * 城市编码
     */
    private String cityCode;

    /**
     * 仓库出库模式
     */
    private WarehouseSaleOcpType saleOcpType;

    /**
     * 是否计算秒杀
     */
    private Boolean isSupportSecKill;
    /**
     * 是否计算套装组合商品
     */
    private Boolean isSupportPackageSku;

    /**
     * 单独配置定制任务仓库
     */
    private Boolean isSingelToWhTask;

    /**
     * 最近修改时间
     */
    private Date lastModifyTime;

    /**
     * 外包仓商品对接类型
     */
    private WarehouseSourceSkuType whSourceSkuType;

    /**
     * 是否不校验保质期
     */
    private Boolean isNotExpireDate;

    /**
     * 是否不校验SN商品
     */
    private Boolean isNotSn;

    /**
     * 是否强制校验推荐耗材
     */
    private Boolean isCheckConsumptiveMaterial;

    /**
     * 是否校验拣货状态
     */
    private Boolean isCheckPickingStatus;

    /**
     * 占用库存商品线程数
     */
    private Integer ocpSkuThreadLimit;

    /**
     * 是否第三方付款
     */
    private Boolean isThirdPartyPaymentSF;

    /**
     * 是否自动配货
     */
    private Boolean isAutoOcp;

    /**
     * 配货失败次数限制
     */
    private Integer ocpErrorLimit;

    /**
     * 批次计算单据占用线程数
     */
    private Integer ocpBatchLimit;
    /**
     * 升单邮件通知列表
     */
    private String notifyTransUpgradeUser;

    /**
     * 大件复核称重
     */
    private Boolean isBigLuxuryWeigh;

    /**
     * 是否强制过单物流
     */
    private Boolean isTransMust;

    /**
     * 区分拣货模式的商品数量
     */
    private Long skuQty;


    /**
     * 是否是自动化仓库 默认否
     */
    private Boolean isAutoWh = false;

    private Integer outboundOrderNum;

    /**
     * 是否是压测仓库 默认否
     */
    private Boolean isTestWh = false;

    /**
     * 是否残次品
     */
    private Boolean isImperfect = false;
    /**
     * 是否推送物流单号给外包仓
     */
    private Boolean isOgistics;
    private Boolean isTurnEMS;
    /**
     * 配货批每批订单数，--自动化仓
     */
    private Integer orderCountLimit;

    /**
     * 播种区编码，--自动化仓
     */
    private String seedingAreaCode;

    /**
     * 复核区编码，--自动化仓
     */
    private String checkingAreaCode;
    /**
     * ems账号
     */
    private String emsAccount;


    /**
     * 小批次容量，--自动化仓
     */
    private Integer idx2MaxLimit;

    /**
     * 自动化仓批次拣货完成数量上限
     */
    private Integer autoPickinglistLimit;

    /**
     * 区域编码
     */
    private String regionCode;

    /**
     * PDA按箱收货仓库配置sku最大类数
     */
    private Long skuNum;


    /**
     * PDA按箱收货仓库配置sku总数
     */
    private Long skuTotal;
    /**
     * 交接批订单限制（目前自动化读取该配置）
     */
    private Integer handLimit;

    /**
     * 自动化仓播种墙分组
     */
    private String autoSeedGroup;

    /**
     * 直连是否再次推荐物流商
     * 
     * @return
     */
    private Boolean isSuggest;

    /**
     * 人工集货配货批次上限
     */
    private Integer manpowerPickinglistLimit;

    /**
     * 仅跨多个仓库区域的配货批次进入人工集货
     */
    private Boolean isManpowerConsolidation = false;

    /**
     * 拣货配货批次上限
     */
    private Integer totalPickinglistLimit;

    /**
     * 团购工作台编码
     */
    private String groupWorkbenchCode;

    /**
     * 特殊处理工作台编码
     */
    private String specialWorkbenchCode;

    /**
     * 当日时效工作台编码
     */
    private String sameDayWorkbenchCode;

    /**
     * 次日时效工作台编码
     */
    private String nextDayWorkbenchCode;

    /**
     * 次晨时效工作台编码
     */
    private String nextMorningWorkbenchCode;

    /**
     * 是否区域占用库存
     */
    private Boolean isAreaOcpInv;
    
    private Boolean isCancelTohub;

    private Boolean isAgv;
    
    private String isAdidas;//is_adidas   1:ad创单取消
    
    
    @Column(name = "is_adidas")
    public String getIsAdidas() {
        return isAdidas;
    }

    public void setIsAdidas(String isAdidas) {
        this.isAdidas = isAdidas;
    }
    /**
     * 是否跳过称重
     */
    private Boolean isSkipWeight=false;
    
    /**
     * 是否管理入库单到货箱数
     * false:不管理
     * true:管理
     */
    private Boolean isCartonManager=false;

    private Boolean isBondedWarehouse;

    @Column(name = "is_agv")
    public Boolean getIsAgv() {
        return isAgv;
    }

    public void setIsAgv(Boolean isAgv) {
        this.isAgv = isAgv;
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_WH", sequenceName = "S_T_BI_WAREHOUSE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WH")
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

    @Column(name = "IS_CHECKED_BARCODE")
    public Boolean getIsCheckedBarcode() {
        return isCheckedBarcode;
    }

    public void setIsCheckedBarcode(Boolean isCheckedBarcode) {
        this.isCheckedBarcode = isCheckedBarcode;
    }

    @Column(name = "OP_MODE", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.baseinfo.WarehouseOpMode")})
    public WarehouseOpMode getOpMode() {
        return opMode;
    }

    public void setOpMode(WarehouseOpMode opMode) {
        this.opMode = opMode;
    }

    @Column(name = "MANAGE_MODE", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.baseinfo.WarehouseManageMode")})
    public WarehouseManageMode getManageMode() {
        return manageMode;
    }

    public void setManageMode(WarehouseManageMode manageMode) {
        this.manageMode = manageMode;
    }

    @Column(name = "PIC", length = 50)
    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    @Column(name = "PIC_CONTACT", length = 255)
    public String getPicContact() {
        return picContact;
    }

    public void setPicContact(String picContact) {
        this.picContact = picContact;
    }

    @Column(name = "PHONE", length = 50)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "FAX", length = 50)
    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    @Column(name = "OTH_CONTACT1", length = 255)
    public String getOtherContact1() {
        return otherContact1;
    }

    public void setOtherContact1(String otherContact1) {
        this.otherContact1 = otherContact1;
    }

    @Column(name = "OTH_CONTACT2", length = 255)
    public String getOtherContact2() {
        return otherContact2;
    }

    public void setOtherContact2(String otherContact2) {
        this.otherContact2 = otherContact2;
    }

    @Column(name = "OTH_CONTACT3", length = 255)
    public String getOtherContact3() {
        return otherContact3;
    }

    public void setOtherContact3(String otherContact3) {
        this.otherContact3 = otherContact3;
    }

    @Column(name = "WH_SIZE", precision = 22, scale = 2)
    public BigDecimal getSize() {
        return size;
    }

    public void setSize(BigDecimal size) {
        this.size = size;
    }

    @Column(name = "WH_AVAIL_SIZE", precision = 22, scale = 2)
    public BigDecimal getAvailSize() {
        return availSize;
    }

    public void setAvailSize(BigDecimal availSize) {
        this.availSize = availSize;
    }

    @Column(name = "WORKER_NUM")
    public Integer getWorkerNum() {
        return workerNum;
    }

    public void setWorkerNum(Integer workerNum) {
        this.workerNum = workerNum;
    }

    @Column(name = "IS_SHARE")
    public Boolean getIsShare() {
        return isShare;
    }

    public void setIsShare(Boolean isShare) {
        this.isShare = isShare;
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
    @JoinColumn(name = "OU_ID")
    public OperationUnit getOu() {
        return ou;
    }

    public void setOu(OperationUnit ou) {
        this.ou = ou;
    }

    @Transient
    public int getIntOpMode() {
        return opMode.getValue();
    }

    public void setIntOpMode(int intOpMode) {
        setOpMode(WarehouseOpMode.valueOf(intOpMode));
    }

    @Column(name = "DEPARTURE", length = 100)
    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    @Transient
    public int getIntManageMode() {
        return manageMode.getValue();
    }

    public void setIntManageMode(int intManageMode) {
        setManageMode(WarehouseManageMode.valueOf(intManageMode));
    }

    @Column(name = "IS_NEED_WRAPSTUFF")
    public Boolean getIsNeedWrapStuff() {
        return isNeedWrapStuff;
    }

    public void setIsNeedWrapStuff(Boolean isNeedWrapStuff) {
        this.isNeedWrapStuff = isNeedWrapStuff;
    }

    @Column(name = "INVOICE_TAX_MQ_CODE", length = 20)
    public String getInvoiceTaxMqCode() {
        return invoiceTaxMqCode;
    }

    public void setInvoiceTaxMqCode(String invoiceTaxMqCode) {
        this.invoiceTaxMqCode = invoiceTaxMqCode;
    }

    @Column(name = "VMI_SOURCE", length = 30)
    public String getVmiSource() {
        return vmiSource;
    }

    public void setVmiSource(String vmiSource) {
        this.vmiSource = vmiSource;
    }

    @Column(name = "VMI_SOURCEWH", length = 30)
    public String getVmiSourceWh() {
        return vmiSourceWh;
    }

    public void setVmiSourceWh(String vmiSourceWh) {
        this.vmiSourceWh = vmiSourceWh;
    }

    @Column(name = "IS_MANUAL_WEIGHING")
    public Boolean getIsManualWeighing() {
        return isManualWeighing;
    }

    public void setIsManualWeighing(Boolean isManualWeighing) {
        this.isManualWeighing = isManualWeighing;
    }

    @Column(name = "ADDRESS", length = 300)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "IS_SF_OL_ORDER")
    public Boolean getIsSfOlOrder() {
        return isSfOlOrder;
    }

    public void setIsSfOlOrder(Boolean isSfOlOrder) {
        this.isSfOlOrder = isSfOlOrder;
    }

    @Column(name = "province", length = 30)
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Column(name = "city", length = 30)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "district", length = 30)
    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @Column(name = "zipcode", length = 30)
    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    @Column(name = "SF_WH_CODE", length = 30)
    public String getSfWhCode() {
        return sfWhCode;
    }

    public void setSfWhCode(String sfWhCode) {
        this.sfWhCode = sfWhCode;
    }

    @Column(name = "CITY_CODE", length = 30)
    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    @Column(name = "IS_MQ_INVOICE")
    public Boolean getIsMqInvoice() {
        return isMqInvoice;
    }

    public void setIsMqInvoice(Boolean isMqInvoice) {
        this.isMqInvoice = isMqInvoice;
    }

    @Column(name = "IS_OUT_INVOICE")
    public Boolean getIsOutInvoice() {
        return isOutInvoice;
    }

    public void setIsOutInvoice(Boolean isOutInvoice) {
        this.isOutInvoice = isOutInvoice;
    }

    @Column(name = "IS_EMS_OL_ORDER")
    public Boolean getIsEmsOlOrder() {
        return isEmsOlOrder;
    }

    public void setIsEmsOlOrder(Boolean isEmsOlOrder) {
        this.isEmsOlOrder = isEmsOlOrder;
    }

    @Column(name = "IS_OL_STO")
    public Boolean getIsOlSto() {
        return isOlSto;
    }

    public void setIsOlSto(Boolean isOlSto) {
        this.isOlSto = isOlSto;
    }

    @Column(name = "IS_TTK_OL_ORDER")
    public Boolean getIsTtkOlOrder() {
        return isTtkOlOrder;
    }

    public void setIsTtkOlOrder(Boolean isTtkOlOrder) {
        this.isTtkOlOrder = isTtkOlOrder;
    }

    @Column(name = "IS_YTO_OL_ORDER")
    public Boolean getIsYtoOlOrder() {
        return isYtoOlOrder;
    }

    public void setIsYtoOlOrder(Boolean isYtoOlOrder) {
        this.isYtoOlOrder = isYtoOlOrder;
    }

    @Column(name = "IS_RFD_OL_ORDER")
    public Boolean getIsRfdOlOrder() {
        return isRfdOlOrder;
    }

    public void setIsRfdOlOrder(Boolean isRfdOlOrder) {
        this.isRfdOlOrder = isRfdOlOrder;
    }

    @Column(name = "WH_SALE_OCP_TYPE", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.WarehouseSaleOcpType")})
    public WarehouseSaleOcpType getSaleOcpType() {
        return saleOcpType;
    }

    public void setSaleOcpType(WarehouseSaleOcpType saleOcpType) {
        this.saleOcpType = saleOcpType;
    }

    public void setIntSaleOcpType(int intSaleOcpType) {
        setSaleOcpType(WarehouseSaleOcpType.valueOf(intSaleOcpType));
    }

    @Column(name = "IS_SUPPORT_SECKILL")
    public Boolean getIsSupportSecKill() {
        return isSupportSecKill;
    }

    public void setIsSupportSecKill(Boolean isSupportSecKill) {
        this.isSupportSecKill = isSupportSecKill;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID")
    @Index(name = "IDX_WHC_CUSTOMER_ID")
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }


    @Column(name = "IS_SUPPORT_PACKAGE")
    public Boolean getIsSupportPackageSku() {
        return isSupportPackageSku;
    }

    public void setIsSupportPackageSku(Boolean isSupportPackageSku) {
        this.isSupportPackageSku = isSupportPackageSku;
    }

    @Column(name = "IS_SINGEL_TO_WH_TASK")
    public Boolean getIsSingelToWhTask() {
        return isSingelToWhTask;
    }

    public void setIsSingelToWhTask(Boolean isSingelToWhTask) {
        this.isSingelToWhTask = isSingelToWhTask;
    }

    @Column(name = "IS_ZTO_OL_ORDER")
    public Boolean getIsZtoOlOrder() {
        return isZtoOlOrder;
    }

    public void setIsZtoOlOrder(Boolean isZtoOlOrder) {
        this.isZtoOlOrder = isZtoOlOrder;
    }

    @Column(name = "LAST_MODIFY_TIME")
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    @Column(name = "WH_SOURCE_SKU_TYPE", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.WarehouseSourceSkuType")})
    public WarehouseSourceSkuType getWhSourceSkuType() {
        return whSourceSkuType;
    }

    public void setWhSourceSkuType(WarehouseSourceSkuType whSourceSkuType) {
        this.whSourceSkuType = whSourceSkuType;
    }

    @Column(name = "IS_NOT_EXPIRE_DATE")
    public Boolean getIsNotExpireDate() {
        return isNotExpireDate;
    }

    public void setIsNotExpireDate(Boolean isNotExpireDate) {
        this.isNotExpireDate = isNotExpireDate;
    }

    @Column(name = "IS_NOT_SN")
    public Boolean getIsNotSn() {
        return isNotSn;
    }

    public void setIsNotSn(Boolean isNotSn) {
        this.isNotSn = isNotSn;
    }

    @Column(name = "IS_DHL_OL_ORDER")
    public Boolean getIsDhlOlOrder() {
        return isDhlOlOrder;
    }


    public void setIsDhlOlOrder(Boolean isDhlOlOrder) {
        this.isDhlOlOrder = isDhlOlOrder;
    }

    @Column(name = "IS_WX_OL_ORDER")
    public Boolean getIsWxOlOrder() {
        return isWxOlOrder;
    }

    public void setIsWxOlOrder(Boolean isWxOlOrder) {
        this.isWxOlOrder = isWxOlOrder;
    }

    @Column(name = "IS_CHECK_CM")
    public Boolean getIsCheckConsumptiveMaterial() {
        return isCheckConsumptiveMaterial;
    }

    public void setIsCheckConsumptiveMaterial(Boolean isCheckConsumptiveMaterial) {
        this.isCheckConsumptiveMaterial = isCheckConsumptiveMaterial;
    }

    @Column(name = "IS_TP_PAYMENT_SF")
    public Boolean getIsThirdPartyPaymentSF() {
        return isThirdPartyPaymentSF;
    }

    public void setIsThirdPartyPaymentSF(Boolean isThirdPartyPaymentSF) {
        this.isThirdPartyPaymentSF = isThirdPartyPaymentSF;
    }

    @Column(name = "IS_CHECK_PICKING_STATUS")
    public Boolean getIsCheckPickingStatus() {
        return isCheckPickingStatus;
    }

    public void setIsCheckPickingStatus(Boolean isCheckPickingStatus) {
        this.isCheckPickingStatus = isCheckPickingStatus;
    }

    @Column(name = "OCP_SKU_THREAD_LIMIT")
    public Integer getOcpSkuThreadLimit() {
        return ocpSkuThreadLimit;
    }

    public void setOcpSkuThreadLimit(Integer ocpSkuThreadLimit) {
        this.ocpSkuThreadLimit = ocpSkuThreadLimit;
    }

    @Column(name = "IS_AUTO_OCP")
    public Boolean getIsAutoOcp() {
        return isAutoOcp;
    }

    public void setIsAutoOcp(Boolean isAutoOcp) {
        this.isAutoOcp = isAutoOcp;
    }

    @Column(name = "OCP_ERROR_LIMIT")
    public Integer getOcpErrorLimit() {
        return ocpErrorLimit;
    }

    public void setOcpErrorLimit(Integer ocpErrorLimit) {
        this.ocpErrorLimit = ocpErrorLimit;
    }

    @Column(name = "OCP_BATCH_LIMIT")
    public Integer getOcpBatchLimit() {
        return ocpBatchLimit;
    }

    public void setOcpBatchLimit(Integer ocpBatchLimit) {
        this.ocpBatchLimit = ocpBatchLimit;
    }

    @Column(name = "SF_WH_CODE_COD", length = 50)
    public String getSfWhCodeCod() {
        return sfWhCodeCod;
    }

    public void setSfWhCodeCod(String sfWhCodeCod) {
        this.sfWhCodeCod = sfWhCodeCod;
    }

    @Column(name = "notify_trans_upgrade_user")
    public String getNotifyTransUpgradeUser() {
        return notifyTransUpgradeUser;
    }

    public void setNotifyTransUpgradeUser(String notifyTransUpgradeUser) {
        this.notifyTransUpgradeUser = notifyTransUpgradeUser;
    }

    @Column(name = "IS_BIG_LUXURY_WEIGH")
    public Boolean getIsBigLuxuryWeigh() {
        return isBigLuxuryWeigh;
    }

    public void setIsBigLuxuryWeigh(Boolean isBigLuxuryWeigh) {
        this.isBigLuxuryWeigh = isBigLuxuryWeigh;
    }

    @Column(name = "IS_TRANS_MUST")
    public Boolean getIsTransMust() {
        return isTransMust;
    }

    public void setIsTransMust(Boolean isTransMust) {
        this.isTransMust = isTransMust;
    }

    @Column(name = "SKU_QTY", length = 19)
    public Long getSkuQty() {
        return skuQty;
    }

    public void setSkuQty(Long skuQty) {
        this.skuQty = skuQty;
    }

    @Column(name = "is_auto_wh")
    public Boolean getIsAutoWh() {
        return isAutoWh;
    }

    public void setIsAutoWh(Boolean isAutoWh) {
        this.isAutoWh = isAutoWh;
    }

    @Column(name = "is_Imperfect")
    public Boolean getIsImperfect() {
        return isImperfect;
    }

    public void setIsImperfect(Boolean isImperfect) {
        this.isImperfect = isImperfect;
    }

    @Column(name = "is_Ogistics")
    public Boolean getIsOgistics() {
        return isOgistics;
    }

    public void setIsOgistics(Boolean isOgistics) {
        this.isOgistics = isOgistics;
    }

    @Column(name = "is_turn_ems")
    public Boolean getIsTurnEMS() {
        return isTurnEMS;
    }

    public void setIsTurnEMS(Boolean isTurnEMS) {
        this.isTurnEMS = isTurnEMS;
    }

    @Column(name = "IS_CXC_OL_ORDER")
    public Boolean getIsCxcOlOrder() {
        return isCxcOlOrder;
    }

    public void setIsCxcOlOrder(Boolean isCxcOlOrder) {
        this.isCxcOlOrder = isCxcOlOrder;
    }

    @Column(name = "order_count_limit")
    public Integer getOrderCountLimit() {
        return orderCountLimit;
    }

    public void setOrderCountLimit(Integer orderCountLimit) {
        this.orderCountLimit = orderCountLimit;
    }

    @Column(name = "seeding_area_code")
    public String getSeedingAreaCode() {
        return seedingAreaCode;
    }

    public void setSeedingAreaCode(String seedingAreaCode) {
        this.seedingAreaCode = seedingAreaCode;
    }

    @Column(name = "checking_area_code")
    public String getCheckingAreaCode() {
        return checkingAreaCode;
    }

    public void setCheckingAreaCode(String checkingAreaCode) {
        this.checkingAreaCode = checkingAreaCode;
    }

    @Column(name = "idx2_max_limit")
    public Integer getIdx2MaxLimit() {
        return idx2MaxLimit;
    }

    public void setIdx2MaxLimit(Integer idx2MaxLimit) {
        this.idx2MaxLimit = idx2MaxLimit;
    }

    @Column(name = "auto_pickinglist_limit")
    public Integer getAutoPickinglistLimit() {
        return autoPickinglistLimit;
    }

    public void setAutoPickinglistLimit(Integer autoPickinglistLimit) {
        this.autoPickinglistLimit = autoPickinglistLimit;
    }

    @Column(name = "regionCode")
    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    @Column(name = "sku_num")
    public Long getSkuNum() {
        return skuNum;
    }

    public void setSkuNum(Long skuNum) {
        this.skuNum = skuNum;
    }

    @Column(name = "sku_total")
    public Long getSkuTotal() {
        return skuTotal;
    }

    public void setSkuTotal(Long skuTotal) {
        this.skuTotal = skuTotal;
    }

    @Column(name = "HAND_LIMIT")
    public Integer getHandLimit() {
        return handLimit;
    }

    public void setHandLimit(Integer handLimit) {
        this.handLimit = handLimit;
    }

    @Column(name = "AUTO_SEED_GROUP")
    public String getAutoSeedGroup() {
        return autoSeedGroup;
    }

    public void setAutoSeedGroup(String autoSeedGroup) {
        this.autoSeedGroup = autoSeedGroup;
    }

    @Column(name = "IS_SUGGEST")
    public Boolean getIsSuggest() {
        return isSuggest;
    }

    public void setIsSuggest(Boolean isSuggest) {
        this.isSuggest = isSuggest;
    }

    @Column(name = "MANPOWER_PICKINGLIST_LIMIT")
    public Integer getManpowerPickinglistLimit() {
        return manpowerPickinglistLimit;
    }

    public void setManpowerPickinglistLimit(Integer manpowerPickinglistLimit) {
        this.manpowerPickinglistLimit = manpowerPickinglistLimit;
    }

    @Column(name = "IS_MANPOWER_CONSOLIDATION")
    public Boolean getIsManpowerConsolidation() {
        return isManpowerConsolidation;
    }

    public void setIsManpowerConsolidation(Boolean isManpowerConsolidation) {
        this.isManpowerConsolidation = isManpowerConsolidation;
    }

    @Column(name = "TOTAL_PICKINGLIST_LIMIT")
    public Integer getTotalPickinglistLimit() {
        return totalPickinglistLimit;
    }

    public void setTotalPickinglistLimit(Integer totalPickinglistLimit) {
        this.totalPickinglistLimit = totalPickinglistLimit;
    }

    @Column(name = "IS_KERRYA_OL_OEDER")
    public Boolean getIsKerryaOlOrder() {
        if (isKerryaOlOrder == null) {
            isKerryaOlOrder = true;
        }
        return isKerryaOlOrder;
    }

    public void setIsKerryaOlOrder(Boolean isKerryaOlOrder) {
        this.isKerryaOlOrder = isKerryaOlOrder;
    }

    @Column(name = "GROUP_WORKBENCH_CODE")
    public String getGroupWorkbenchCode() {
        return groupWorkbenchCode;
    }

    public void setGroupWorkbenchCode(String groupWorkbenchCode) {
        this.groupWorkbenchCode = groupWorkbenchCode;
    }

    @Column(name = "SPECIAL_WORKBENCH_CODE")
    public String getSpecialWorkbenchCode() {
        return specialWorkbenchCode;
    }

    public void setSpecialWorkbenchCode(String specialWorkbenchCode) {
        this.specialWorkbenchCode = specialWorkbenchCode;
    }

    @Column(name = "SAME_DAY_WORKBENCH_CODE")
    public String getSameDayWorkbenchCode() {
        return sameDayWorkbenchCode;
    }

    public void setSameDayWorkbenchCode(String sameDayWorkbenchCode) {
        this.sameDayWorkbenchCode = sameDayWorkbenchCode;
    }

    @Column(name = "NEXT_DAY_WORKBENCH_CODE")
    public String getNextDayWorkbenchCode() {
        return nextDayWorkbenchCode;
    }

    public void setNextDayWorkbenchCode(String nextDayWorkbenchCode) {
        this.nextDayWorkbenchCode = nextDayWorkbenchCode;
    }

    @Column(name = "NEXT_MORNING_WORKBENCH_CODE")
    public String getNextMorningWorkbenchCode() {
        return nextMorningWorkbenchCode;
    }

    public void setNextMorningWorkbenchCode(String nextMorningWorkbenchCode) {
        this.nextMorningWorkbenchCode = nextMorningWorkbenchCode;
    }

    @Column(name = "IS_TEST_WH")
    public Boolean getIsTestWh() {
        return isTestWh;
    }

    public void setIsTestWh(Boolean isTestWh) {
        this.isTestWh = isTestWh;
    }

    @Column(name = "is_area_ocp_inv")
    public Boolean getIsAreaOcpInv() {
        return isAreaOcpInv;
    }

    public void setIsAreaOcpInv(Boolean isAreaOcpInv) {
        this.isAreaOcpInv = isAreaOcpInv;
    }

    @Column(name = "outbound_order_num")
    public Integer getOutboundOrderNum() {
        return outboundOrderNum;
    }

    public void setOutboundOrderNum(Integer outboundOrderNum) {
        this.outboundOrderNum = outboundOrderNum;
    }

    @Column(name="is_Cancel_Tohub")
    public Boolean getIsCancelTohub() {
        return isCancelTohub;
    }

    public void setIsCancelTohub(Boolean isCancelTohub) {
        this.isCancelTohub = isCancelTohub;
    }

    @Column(name = "IS_SKIP_WEIGHT")
    public Boolean getIsSkipWeight() {
        return isSkipWeight;
    }

    public void setIsSkipWeight(Boolean isSkipWeight) {
        this.isSkipWeight = isSkipWeight;
    }

    @Column(name = "is_Bonded_Warehouse")
    public Boolean getIsBondedWarehouse() {
        return isBondedWarehouse;
    }

    public void setIsBondedWarehouse(Boolean isBondedWarehouse) {
        this.isBondedWarehouse = isBondedWarehouse;
    }

  
   
    
    @Column(name = "IS_CARTON_MANAGER")
    public Boolean getIsCartonManager(){
        return isCartonManager;
    }

    public void setIsCartonManager(Boolean isCartonManager){
        this.isCartonManager = isCartonManager;
    }

}
