package com.jumbo.wms.model.warehouse;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;

/**
 * 物流相关信息
 * 
 * @author sjk
 * 
 */

@Entity
@Table(name = "T_WH_STA_DELIVERY_INFO")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class StaDeliveryInfo extends BaseModel {
    private static final long serialVersionUID = -37253004824435236L;

    public static final BigDecimal DEFAULT_WEIGHT = new BigDecimal(1);

    /**
     * PK
     */
    private Long id;

    /**
     * 国家
     */
    private String country;
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
     * 送货地址
     */
    private String address;
    /**
     * 联系电话
     */
    private String telephone;
    /**
     * 手机
     */
    private String mobile;
    /**
     * 收货人
     */
    private String receiver;
    /**
     * 邮编
     */
    private String zipcode;

    /**
     * 是否货到付款
     */
    private Boolean isCod = false;

    /**
     * 物流供应商编码
     */
    private String lpCode;
    /**
     * 快递单号
     */
    private String trackingNo;
    /**
     * 退货单号
     */
    private String returnTransNo;
    /**
     * 备注
     */
    private String remark;
    /**
     * 收取运费
     */
    private BigDecimal transferFee;
    /**
     * 预估包裹重量
     */
    private BigDecimal weight;
    /**
     * 运费成本
     */
    private BigDecimal transferCost;

    /**
     * 是否需要发票
     */
    private Boolean storeComIsNeedInvoice;
    /**
     * 发票抬头
     */
    private String storeComInvoiceTitle;
    /**
     * 发票内容
     */
    private String storeComInvoiceContent;
    /**
     * 开票金额
     */
    private BigDecimal storeComTotalAmount;

    /**
     * 相关申请单
     */
    private StockTransApplication sta;

    private List<PackageInfo> packageInfos;
    /**
     * 物流商平台订单号
     */
    private String extTransOrderId;
    /**
     * 反向物流商平台订单号
     */
    private String retrunExtTransOrderId;

    /**
     * 快递城市编码
     */
    private String transCityCode;
    /**
     * 快递城市编码
     */
    private String returnTransCityCode;

    /**
     * 是否分包
     */
    private Boolean isMorePackage;

    /**
     * 运送方式(快递附加服务)
     */
    private TransType transType;

    /**
     * 运单时限类型(快递附加服务)
     */
    private TransTimeType transTimeType;

    /**
     * 运单备注(快递附加服务)
     */
    private String transMemo;
    // 新增字段
    /**
     * 下单用户邮箱
     */
    private String orderUserMail;
    /**
     * 下单用户帐号
     */
    private String orderUserCode;
    /**
     * 保价金额
     */
    private BigDecimal insuranceAmount;
    /**
     * 退货原因备注
     */
    private String returnReasonMemo;
    /**
     * 退货原因类型
     */
    private ReturnReasonType returnReasonType;

    /**
     * Nike逆向物流单号
     */
    private String returnTrackingNo;

    private String addressEn;
    private String cityEn;
    private String countryEn;
    private String districtEn;
    private String provinceEn;
    private String receiverEn;
    private String remarkEn;

    /**
     * 是否POS机刷卡
     */
    private Boolean isCodPos;

    /**
     * 最近修改时间
     */
    private Date lastModifyTime;

    /**
     * 运单大头笔
     */
    private String transBigWord;
    /**
     * 自提便利店编码
     */
    private String convenienceStore;

    /**
     * 阿里云栈包裹号
     */
    private String aliPackageNo;

    /**
     * 账号
     */
    private String transAccount;

    /**
     * 菜鸟配物流公司编码，用于发货回传
     */
    private String logisticsCode;
    /**
     * 集包地代码
     */
    private String packageCenterCode;

    /**
     * 集包地代码
     */
    private String packageCenterName;

    /**
     * 二级配送公司编码,用于发货回传
     */
    private String tmsCode;

    /**
     * 不为空则是通过MQ获取运单号或是已经获取完运单号
     */
    private Integer mqGetTransNo;

    @Column(name = "trans_account")
    public String getTransAccount() {
        return transAccount;
    }

    public void setTransAccount(String transAccount) {
        this.transAccount = transAccount;
    }

    @Column(name = "IS_TRANS_COD_POS")
    public Boolean getIsCodPos() {
        return isCodPos;
    }

    public void setIsCodPos(Boolean isCodPos) {
        this.isCodPos = isCodPos;
    }

    @Column(name = "ADDRESS_EN")
    public String getAddressEn() {
        return addressEn;
    }

    public void setAddressEn(String addressEn) {
        this.addressEn = addressEn;
    }

    @Column(name = "CITY_EN")
    public String getCityEn() {
        return cityEn;
    }

    public void setCityEn(String cityEn) {
        this.cityEn = cityEn;
    }

    @Column(name = "COUNTRY_EN")
    public String getCountryEn() {
        return countryEn;
    }

    public void setCountryEn(String countryEn) {
        this.countryEn = countryEn;
    }

    @Column(name = "DISTRICT_EN")
    public String getDistrictEn() {
        return districtEn;
    }

    public void setDistrictEn(String districtEn) {
        this.districtEn = districtEn;
    }

    @Column(name = "PROVINCE_EN")
    public String getProvinceEn() {
        return provinceEn;
    }

    public void setProvinceEn(String provinceEn) {
        this.provinceEn = provinceEn;
    }

    @Column(name = "RECEIVER_EN")
    public String getReceiverEn() {
        return receiverEn;
    }

    public void setReceiverEn(String receiverEn) {
        this.receiverEn = receiverEn;
    }

    @Column(name = "REMARK_EN")
    public String getRemarkEn() {
        return remarkEn;
    }

    public void setRemarkEn(String remarkEn) {
        if (remarkEn != null && remarkEn.length() >= 500) {
            remarkEn = remarkEn.substring(0, 499);
        }
        this.remarkEn = remarkEn;
    }

    @Column(name = "insurance_amount")
    public BigDecimal getInsuranceAmount() {
        return insuranceAmount;
    }

    public void setInsuranceAmount(BigDecimal insuranceAmount) {
        this.insuranceAmount = insuranceAmount;
    }

    @Id
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "COUNTRY", length = 100)
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Column(name = "PROVINCE", length = 100)
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Column(name = "CITY", length = 100)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "DISTRICT", length = 100)
    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @Column(name = "ADDRESS", length = 500)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "TELEPHONE", length = 50)
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Column(name = "MOBILE", length = 50)
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Column(name = "RECEIVER", length = 50)
    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    @Column(name = "WEIGHT")
    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    @Column(name = "ZIPCODE", length = 20)
    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    @Column(name = "LPCODE", length = 20)
    public String getLpCode() {
        return lpCode;
    }

    public void setLpCode(String lpCode) {
        this.lpCode = lpCode;
    }

    @Column(name = "TRACKING_NO", length = 100)
    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    @Column(name = "REMARK", length = 1000)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "TRANSFER_FEE")
    public BigDecimal getTransferFee() {
        return transferFee;
    }

    public void setTransferFee(BigDecimal transferFee) {
        this.transferFee = transferFee;
    }

    @Column(name = "IS_COD")
    public Boolean getIsCod() {
        return isCod;
    }

    public void setIsCod(Boolean isCod) {
        this.isCod = isCod;
    }

    @Column(name = "TRANSFER_COST")
    public BigDecimal getTransferCost() {
        return transferCost;
    }

    public void setTransferCost(BigDecimal transferCost) {
        this.transferCost = transferCost;
    }

    @Column(name = "STORE_COM_IS_NEED_INVOICE")
    public Boolean getStoreComIsNeedInvoice() {
        return storeComIsNeedInvoice;
    }

    public void setStoreComIsNeedInvoice(Boolean storeComIsNeedInvoice) {
        this.storeComIsNeedInvoice = storeComIsNeedInvoice;
    }

    @Column(name = "STORE_COM_INVOICE_TITLE", length = 100)
    public String getStoreComInvoiceTitle() {
        return storeComInvoiceTitle;
    }

    public void setStoreComInvoiceTitle(String storeComInvoiceTitle) {
        this.storeComInvoiceTitle = storeComInvoiceTitle;
    }

    @Column(name = "STORE_COM_INVOICE_CONTENT", length = 500)
    public String getStoreComInvoiceContent() {
        return storeComInvoiceContent;
    }

    public void setStoreComInvoiceContent(String storeComInvoiceContent) {
        this.storeComInvoiceContent = storeComInvoiceContent;
    }

    @OneToOne(mappedBy = "staDeliveryInfo", fetch = FetchType.LAZY)
    public StockTransApplication getSta() {
        return sta;
    }

    @Column(name = "STORE_COM_TOTAL_AMOUNT")
    public BigDecimal getStoreComTotalAmount() {
        return storeComTotalAmount;
    }

    public void setStoreComTotalAmount(BigDecimal storeComTotalAmount) {
        this.storeComTotalAmount = storeComTotalAmount;
    }

    public void setSta(StockTransApplication sta) {
        this.sta = sta;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "staDeliveryInfo", orphanRemoval = true)
    @OrderBy("id")
    public List<PackageInfo> getPackageInfos() {
        return packageInfos;
    }

    public void setPackageInfos(List<PackageInfo> packageInfos) {
        this.packageInfos = packageInfos;
    }

    @Column(name = "EXT_TRANS_ORDER_ID", length = 80)
    public String getExtTransOrderId() {
        return extTransOrderId;
    }

    public void setExtTransOrderId(String extTransOrderId) {
        this.extTransOrderId = extTransOrderId;
    }

    @Column(name = "trans_city_code", length = 45)
    public String getTransCityCode() {
        return transCityCode;
    }

    public void setTransCityCode(String transCityCode) {
        this.transCityCode = transCityCode;
    }

    @Column(name = "IS_MORE_PACKAGE")
    public Boolean getIsMorePackage() {
        return isMorePackage;
    }

    public void setIsMorePackage(Boolean isMorePackage) {
        this.isMorePackage = isMorePackage;
    }

    @Column(name = "TRANS_TYPE", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.TransType")})
    public TransType getTransType() {
        return transType;
    }

    public void setTransType(TransType transType) {
        this.transType = transType;
    }

    @Column(name = "TRANS_MEMO")
    public String getTransMemo() {
        return transMemo;
    }

    public void setTransMemo(String transMemo) {
        this.transMemo = transMemo;
    }

    @Column(name = "TRANS_TIME_TYPE", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.TransTimeType")})
    public TransTimeType getTransTimeType() {
        return transTimeType;
    }

    public void setTransTimeType(TransTimeType transTimeType) {
        this.transTimeType = transTimeType;
    }

    @Column(name = "order_user_mail")
    public String getOrderUserMail() {
        return orderUserMail;
    }

    public void setOrderUserMail(String orderUserMail) {
        this.orderUserMail = orderUserMail;
    }

    @Column(name = "order_user_code")
    public String getOrderUserCode() {
        return orderUserCode;
    }

    public void setOrderUserCode(String orderUserCode) {
        this.orderUserCode = orderUserCode;
    }

    @Column(name = "return_reason_memo")
    public String getReturnReasonMemo() {
        return returnReasonMemo;
    }

    public void setReturnReasonMemo(String returnReasonMemo) {
        this.returnReasonMemo = returnReasonMemo;
    }

    @Column(name = "return_reason_type", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.ReturnReasonType")})
    public ReturnReasonType getReturnReasonType() {
        return returnReasonType;
    }

    public void setReturnReasonType(ReturnReasonType returnReasonType) {
        this.returnReasonType = returnReasonType;
    }

    @Column(name = "LAST_MODIFY_TIME")
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    @Column(name = "TRANS_BIG_WORD", length = 256)
    public String getTransBigWord() {
        return transBigWord;
    }

    public void setTransBigWord(String transBigWord) {
        this.transBigWord = transBigWord;
    }

    @Column(name = "CONVENIENCE_STORE", length = 20)
    public String getConvenienceStore() {
        return convenienceStore;
    }

    public void setConvenienceStore(String convenienceStore) {
        this.convenienceStore = convenienceStore;
    }

    @Column(name = "ALI_PACKAGE_NO")
    public String getAliPackageNo() {
        return aliPackageNo;
    }

    public void setAliPackageNo(String aliPackageNo) {
        this.aliPackageNo = aliPackageNo;
    }

    @Column(name = "logistics_code")
    public String getLogisticsCode() {
        return logisticsCode;
    }

    public void setLogisticsCode(String logisticsCode) {
        this.logisticsCode = logisticsCode;
    }

    @Column(name = "package_center_code")
    public String getPackageCenterCode() {
        return packageCenterCode;
    }

    public void setPackageCenterCode(String packageCenterCode) {
        this.packageCenterCode = packageCenterCode;
    }

    @Column(name = "package_center_name")
    public String getPackageCenterName() {
        return packageCenterName;
    }

    public void setPackageCenterName(String packageCenterName) {
        this.packageCenterName = packageCenterName;
    }

    @Column(name = "tms_code")
    public String getTmsCode() {
        return tmsCode;
    }

    public void setTmsCode(String tmsCode) {
        this.tmsCode = tmsCode;
    }

    @Column(name = "return_TransNo")
    public String getReturnTransNo() {
        return returnTransNo;
    }

    public void setReturnTransNo(String returnTransNo) {
        this.returnTransNo = returnTransNo;
    }

    @Column(name = "return_trans_city_code")
    public String getReturnTransCityCode() {
        return returnTransCityCode;
    }

    public void setReturnTransCityCode(String returnTransCityCode) {
        this.returnTransCityCode = returnTransCityCode;
    }

    @Column(name = "retrun_Ext_Trans_Order_Id")
    public String getRetrunExtTransOrderId() {
        return retrunExtTransOrderId;
    }

    public void setRetrunExtTransOrderId(String retrunExtTransOrderId) {
        this.retrunExtTransOrderId = retrunExtTransOrderId;
    }

    @Column(name = "MQ_GET_TRANS_NO")
    public Integer getMqGetTransNo() {
        return mqGetTransNo;
    }

    public void setMqGetTransNo(Integer mqGetTransNo) {
        this.mqGetTransNo = mqGetTransNo;
    }
    
    @Column(name = "RETURN_TRACKING_NO")
    public String getReturnTrackingNo() {
        return returnTrackingNo;
    }

    public void setReturnTrackingNo(String returnTrackingNo) {
        this.returnTrackingNo = returnTrackingNo;
    }

}
