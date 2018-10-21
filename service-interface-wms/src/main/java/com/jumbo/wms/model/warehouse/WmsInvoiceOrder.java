package com.jumbo.wms.model.warehouse;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;

/**
 * 补寄发票
 * 
 * @author jinlong.ke
 * 
 */
@Entity
@Table(name = "T_WH_INVOICE_ORDER")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class WmsInvoiceOrder extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = -6388832520932095994L;
    /*
     * PK
     */
    private Long id;
    /*
     * 创建时间
     */
    private Date createTime;
    /*
     * 修改时间
     */
    private Date lastModifyTime;
    /*
     * 批次号
     */
    private String batchCode;
    /*
     * 版本
     */
    private int version;
    /*
     * 状态
     */
    private StockTransApplicationStatus status;
    /*
     * 批次顺序号
     */
    private Integer pgIndex;
    /*
     * 单据号
     */
    private String code;
    /*
     * 订单号
     */
    private String orderCode;
    /*
     * 物流商
     */
    private String lpCode;
    /*
     * 运单号
     */
    private String transNo;
    /*
     * 店铺
     */
    private String owner;
    /*
     * 省
     */
    private String province;
    /*
     * 市
     */
    private String city;
    /*
     * 区
     */
    private String district;
    /*
     * 地址
     */
    private String address;
    /*
     * 电话
     */
    private String telephone;
    /*
     * 手机
     */
    private String mobile;
    /*
     * 收件人
     */
    private String receiver;
    /*
     * 邮编
     */
    private String zipcode;
    /*
     * 系统来源标识
     */
    private String systemKey;
    /*
     * 完成时间
     */
    private Date finishTime;
    /*
     * 外部物流系统单号
     */
    private String extTransOrderId;
    /*
     * 快递城市编码
     */
    private String transCityCode;
    /*
     * 整型状态
     */
    private Integer intStatus;


    /**
     * 付款单位纳税人识别号
     */
    private String identificationNumber;

    /**
     * 地址
     */
    private String invoiceAddress;

    /**
     * 电话
     */
    private String invoiceTelephone;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_WH_INVOICE_ORDER", sequenceName = "S_T_WH_INVOICE_ORDER", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_WH_INVOICE_ORDER")
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

    @Column(name = "LAST_MODIFY_TIME")
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    @Column(name = "BATCH_CODE", length = 100)
    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    @Version
    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Column(name = "STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.StockTransApplicationStatus")})
    public StockTransApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(StockTransApplicationStatus status) {
        this.status = status;
    }

    @Column(name = "PG_INDEX")
    public Integer getPgIndex() {
        return pgIndex;
    }

    public void setPgIndex(Integer pgIndex) {
        this.pgIndex = pgIndex;
    }

    @Column(name = "CODE", length = 30)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "ORDER_CODE", length = 50)
    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    @Column(name = "LP_CODE", length = 30)
    public String getLpCode() {
        return lpCode;
    }

    public void setLpCode(String lpCode) {
        this.lpCode = lpCode;
    }

    @Column(name = "TRANS_NO", length = 30)
    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    @Column(name = "OWNER", length = 100)
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Column(name = "PROVINCE", length = 30)
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Column(name = "CITY", length = 30)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "DISTRICT", length = 30)
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

    @Column(name = "TELEPHONE", length = 30)
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Column(name = "MOBILE", length = 30)
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Column(name = "RECEIVER", length = 30)
    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    @Column(name = "ZIPCODE", length = 10)
    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    @Column(name = "SYSTEM_KEY", length = 30)
    public String getSystemKey() {
        return systemKey;
    }

    public void setSystemKey(String systemKey) {
        this.systemKey = systemKey;
    }

    @Column(name = "FINISH_TIME")
    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    @Column(name = "EXT_TRANS_ORDER_ID", length = 80)
    public String getExtTransOrderId() {
        return extTransOrderId;
    }

    public void setExtTransOrderId(String extTransOrderId) {
        this.extTransOrderId = extTransOrderId;
    }

    @Column(name = "TRANS_CITY_CODE", length = 45)
    public String getTransCityCode() {
        return transCityCode;
    }

    public void setTransCityCode(String transCityCode) {
        this.transCityCode = transCityCode;
    }

    @Transient
    public Integer getIntStatus() {
        return intStatus;
    }

    public void setIntStatus(Integer intStatus) {
        this.intStatus = intStatus;
    }

    @Column(name = "identification_Number")
    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    @Column(name = "invoice_Address")
    public String getInvoiceAddress() {
        return invoiceAddress;
    }

    public void setInvoiceAddress(String invoiceAddress) {
        this.invoiceAddress = invoiceAddress;
    }

    @Column(name = "invoice_Telephone")
    public String getInvoiceTelephone() {
        return invoiceTelephone;
    }

    public void setInvoiceTelephone(String invoiceTelephone) {
        this.invoiceTelephone = invoiceTelephone;
    }


}
