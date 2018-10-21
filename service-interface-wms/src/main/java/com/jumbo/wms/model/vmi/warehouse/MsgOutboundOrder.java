package com.jumbo.wms.model.vmi.warehouse;

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
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.DefaultStatus;

@Entity
@Table(name = "T_WH_MSG_OUTBOUND_ORDER")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class MsgOutboundOrder extends BaseModel {

    private static final long serialVersionUID = 2538026907683766650L;
    /**
     * PK
     */
    private Long id;

    private int version;
    /**
     * 批次号
     */
    private Long batchId;

    /**
     * 来源
     */
    private String source = "";



    /**
     * 来源仓库
     */
    private String sourceWh = "";

    /**
     * 作业单号
     */
    private String staCode = "";

    /**
     * 物流
     */
    private String lpCode = "";

    /**
     * 运费
     */
    private BigDecimal transferFee = new BigDecimal(0);

    /**
     * 收货人
     */
    private String receiver = "";

    /**
     * 邮编
     */
    private String zipcode = "";

    /**
     * 电话
     */
    private String telePhone = "";

    /**
     * 手机
     */
    private String mobile = "";

    /**
     * 国家
     */
    private String country = "";

    /**
     * 省
     */
    private String province = "";

    /**
     * 市
     */
    private String city = "";

    /**
     * 区
     */
    private String district = "";

    /**
     * 送货地址
     */
    private String address = "";

    /**
     * 金额
     */
    private BigDecimal totalActual = new BigDecimal(0);

    /**
     * 备注
     */
    private String remark = "";

    /**
     * 状态
     */
    private DefaultStatus status;

    /**
     * 类型
     */
    private String type = "";

    /**
     * 作业单类型
     */
    private Integer staType;

    /**
     * 创建时间
     */
    private Date createTime = new Date();

    /**
     * 创建时间Log
     */
    private Date createTimeLog = new Date();

    /**
     * 创建时间
     */
    private Date updateTime = new Date();

    /**
     * 物流宝单号
     */
    private String wlbCode;

    /**
     * 要求送货时间
     */
    private String releaseDate;

    private String paymentType;

    private String slipCode;

    private Long shopId;

    private Boolean isNeedInvoice = false;
    /**
     * 平台单据号
     */
    private String outerOrderCode;

    /**
     * 邮箱
     */
    private String memberEmail;

    /**
     * 下单时间
     */
    private Date orderCrateDate;


    /**
     * 是否货到付款
     */
    private Boolean isCodOrder;

    /**
     * 货到付款代收金额
     */
    private String codAmount;


    /**
     * 外部平台用户ID
     */
    private String userId;



    /**
     * 是否锁定
     */
    private Boolean isLocked;


    private String sfCityCode;


    /**
     * 快递单号（执行销售出库用到）
     */
    private String transNo;

    /**
     * Mq confirmId
     * 
     * @return
     */
    // private String confirmId;


    private String remarkEnglish;

    private String userIDEnglish;

    private String receiverEnglish;

    private String provinceEnglish;

    private String cityEnglish;

    private String districtEnglish;

    private String addressEnglish;

    /**
     * 是否发送邮件
     */
    private Long isMail;

    /**
     * 错误次数
     */
    private Long errorCount;

    /**
     * 扩展字段，json格式
     */
    private String extMemo;

    /**
     * 外包仓数据唯一标识
     */
    private Long uuid;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_WH_MSG_OUTBOUND_ORDER", sequenceName = "S_T_WH_MSG_OUTBOUND_ORDER", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_WH_MSG_OUTBOUND_ORDER")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "SOURCE", length = 30)
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Column(name = "SOURCEWH", length = 30)
    public String getSourceWh() {
        return sourceWh;
    }

    public void setSourceWh(String sourceWh) {
        this.sourceWh = sourceWh;
    }

    @Column(name = "STA_CODE", length = 50)
    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

    @Column(name = "LPCODE", length = 50)
    public String getLpCode() {
        return lpCode;
    }

    public void setLpCode(String lpCode) {
        this.lpCode = lpCode;
    }

    @Column(name = "TRANSFER_FEE", precision = 10, scale = 4)
    public BigDecimal getTransferFee() {
        return transferFee;
    }

    public void setTransferFee(BigDecimal transferFee) {
        this.transferFee = transferFee;
    }

    @Column(name = "RECEIVER", length = 50)
    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    @Column(name = "ZIPCODE", length = 6)
    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    @Column(name = "TELEPHONE", length = 50)
    public String getTelePhone() {
        return telePhone;
    }

    public void setTelePhone(String telePhone) {
        this.telePhone = telePhone;
    }

    @Column(name = "MOBILE", length = 20)
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    @Column(name = "CITY", length = 50)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "DISTRICT", length = 50)
    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @Column(name = "ADDRESS", length = 300)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "TOTALACTUAL", precision = 10, scale = 4)
    public BigDecimal getTotalActual() {
        return totalActual;
    }

    public void setTotalActual(BigDecimal totalActual) {
        this.totalActual = totalActual;
    }

    @Column(name = "REMARK", length = 1000)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.DefaultStatus")})
    public DefaultStatus getStatus() {
        return status;
    }

    public void setStatus(DefaultStatus status) {
        this.status = status;
    }

    @Column(name = "TYPE", length = 30)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "STA_TYPE")
    public Integer getStaType() {
        return staType;
    }

    public void setStaType(Integer staType) {
        this.staType = staType;
    }

    @Column(name = "BATCH_ID")
    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    @Version
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "UPDATE_TIME")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Column(name = "wlb_code", length = 30)
    public String getWlbCode() {
        return wlbCode;
    }

    public void setWlbCode(String wlbCode) {
        this.wlbCode = wlbCode;
    }

    @Column(name = "RELEASE_DATE", length = 50)
    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Column(name = "PAYMENT_TYPE", length = 50)
    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    @Column(name = "SLIP_CODE", length = 20)
    public String getSlipCode() {
        return slipCode;
    }

    public void setSlipCode(String slipCode) {
        this.slipCode = slipCode;
    }

    @Column(name = "SHOP_ID")
    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    @Column(name = "IS_NEED_INVOICE")
    public Boolean getIsNeedInvoice() {
        return isNeedInvoice;
    }

    public void setIsNeedInvoice(Boolean isNeedInvoice) {
        this.isNeedInvoice = isNeedInvoice;
    }

    @Column(name = "OUTER_ORDER_CODE")
    public String getOuterOrderCode() {
        return outerOrderCode;
    }

    public void setOuterOrderCode(String outerOrderCode) {
        this.outerOrderCode = outerOrderCode;
    }

    @Column(name = "MEMBER_EMAIL")
    public String getMemberEmail() {
        return memberEmail;
    }

    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail;
    }

    @Column(name = "ORDER_CRATEDATE")
    public Date getOrderCrateDate() {
        return orderCrateDate;
    }

    public void setOrderCrateDate(Date orderCrateDate) {
        this.orderCrateDate = orderCrateDate;
    }

    @Column(name = "IS_COD_ORDER")
    public Boolean getIsCodOrder() {
        return isCodOrder;
    }

    public void setIsCodOrder(Boolean isCodOrder) {
        this.isCodOrder = isCodOrder;
    }

    @Column(name = "COD_AMOUNT")
    public String getCodAmount() {
        return codAmount;
    }

    public void setCodAmount(String codAmount) {
        this.codAmount = codAmount;
    }

    @Column(name = "USER_ID", length = 200)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Column(name = "IS_LOCKED", length = 1)
    public Boolean getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(Boolean isLocked) {
        this.isLocked = isLocked;
    }

    @Column(name = "SF_CITY_CODE", length = 100)
    public String getSfCityCode() {
        return sfCityCode;
    }

    public void setSfCityCode(String sfCityCode) {
        this.sfCityCode = sfCityCode;
    }

    @Column(name = "TRANS_NO", length = 100)
    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    @Column(name = "REMARK_ENGLISH", length = 300)
    public String getRemarkEnglish() {
        return remarkEnglish;
    }

    public void setRemarkEnglish(String remarkEnglish) {
    	if(remarkEnglish != null && remarkEnglish.length() >= 300){
    		remarkEnglish = remarkEnglish.substring(0,299);
    	}
        this.remarkEnglish = remarkEnglish;
    }

    @Column(name = "USERID_ENGLISH", length = 200)
    public String getUserIDEnglish() {
        return userIDEnglish;
    }

    public void setUserIDEnglish(String userIDEnglish) {
        this.userIDEnglish = userIDEnglish;
    }

    @Column(name = "RECEIVER_ENGLISH", length = 100)
    public String getReceiverEnglish() {
        return receiverEnglish;
    }

    public void setReceiverEnglish(String receiverEnglish) {
        this.receiverEnglish = receiverEnglish;
    }

    @Column(name = "PROVINCE_ENGLISH", length = 50)
    public String getProvinceEnglish() {
        return provinceEnglish;
    }

    public void setProvinceEnglish(String provinceEnglish) {
        this.provinceEnglish = provinceEnglish;
    }

    @Column(name = "CITY_ENGLISH", length = 50)
    public String getCityEnglish() {
        return cityEnglish;
    }

    public void setCityEnglish(String cityEnglish) {
        this.cityEnglish = cityEnglish;
    }

    @Column(name = "DISTRICT_ENGLISH", length = 50)
    public String getDistrictEnglish() {
        return districtEnglish;
    }

    public void setDistrictEnglish(String districtEnglish) {
        this.districtEnglish = districtEnglish;
    }

    @Column(name = "ADDRESS_ENGLISH", length = 500)
    public String getAddressEnglish() {
        return addressEnglish;
    }

    public void setAddressEnglish(String addressEnglish) {
        this.addressEnglish = addressEnglish;
    }

    @Column(name = "IS_MAIL")
    public Long getIsMail() {
        return isMail;
    }

    public void setIsMail(Long isMail) {
        this.isMail = isMail;
    }

    @Column(name = "ERROR_COUNT", length = 50)
    public Long getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Long errorCount) {
        this.errorCount = errorCount;
    }

    // @Column(name = "CONFIRM_ID", length = 50)
    // public String getConfirmId() {
    // return confirmId;
    // }
    //
    // public void setConfirmId(String confirmId) {
    // this.confirmId = confirmId;
    // }

    @Column(name = "EXT_MEMO")
    public String getExtMemo() {
        return extMemo;
    }

    public void setExtMemo(String extMemo) {
        this.extMemo = extMemo;
    }

    @Column(name = "UUID")
    public Long getUuid() {
        return uuid;
    }

    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }

    @Column(name = "CREATE_TIME_LOG")
    public Date getCreateTimeLog() {
        return createTimeLog;
    }

    public void setCreateTimeLog(Date createTimeLog) {
        this.createTimeLog = createTimeLog;
    }

}
