package com.jumbo.wms.model.warehouse;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.DefaultStatus;

@Entity
@Table(name = "T_WH_STA_CREATE_QUEUE", uniqueConstraints = {@UniqueConstraint(columnNames = {"SLIP_CODE", "SOURCE"})})
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class StaCreateQueue extends BaseModel {

    private static final long serialVersionUID = 5069729826196364241L;
    /**
     * PK
     */
    private Long id;
    /**
     * VERSION
     */
    private int version;
    /**
     * 订单号
     */
    private String slipCode;
    /**
     * 作业单类型
     */
    private StockTransApplicationType staType;
    /**
     * 备注
     */
    private String remark;
    /**
     * 发票抬头
     */
    private String invoiceTitle;
    /**
     * 发票内容
     */
    private String invoiceContent;
    /**
     * 发票金额
     */
    private BigDecimal invoiceTotalAmount;
    /**
     * 销售金额
     */
    private BigDecimal staTotalAmount;
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
     * 地址
     */
    private String address;
    /**
     * 电话
     */
    private String telephone;
    /**
     * 手机
     */
    private String mobile;
    /**
     * 收件人
     */
    private String receiver;
    /**
     * 邮编
     */
    private String zipcode;
    /**
     * 物流
     */
    private String lpCode;
    /**
     * 来源
     */
    private String source;
    /**
     * 状态
     */
    private DefaultStatus status;
    /**
     * 运费
     */
    private BigDecimal transferFee;

    /**
     * 过仓失败原因
     */
    private String failureInfo;

    /**
     * 运送方式(快递附加服务)
     */
    private TransType transType;

    /**
     * 运单备注(快递附加服务)
     */
    private String transMemo;

    /**
     * 运单时限类型(快递附加服务)
     */
    private TransTimeType transTimeType;

    /**
     * 订单创建时间
     */
    private Date orderCreateTime;

    /**
     * 订单预计送达时间
     */
    private Date orderPlanReceiptTime;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_SCQ", sequenceName = "S_T_WH_STA_CREATE_QUEUE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SCQ")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Version
    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Column(name = "SLIP_CODE")
    public String getSlipCode() {
        return slipCode;
    }

    public void setSlipCode(String slipCode) {
        this.slipCode = slipCode;
    }

    @Column(name = "TYPE", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.StockTransApplicationType")})
    public StockTransApplicationType getStaType() {
        return staType;
    }

    public void setStaType(StockTransApplicationType staType) {
        this.staType = staType;
    }

    @Column(name = "REMARK", length = 500)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "INVOICE_TITLE", length = 100)
    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    @Column(name = "INVOICE_CONTENT", length = 200)
    public String getInvoiceContent() {
        return invoiceContent;
    }

    public void setInvoiceContent(String invoiceContent) {
        this.invoiceContent = invoiceContent;
    }

    @Column(name = "COUNTRY", length = 50)
    public String getCountry() {
        return country;
    }

    @Column(name = "STA_TOTAL_AMOUNT")
    public BigDecimal getStaTotalAmount() {
        return staTotalAmount;
    }

    public void setStaTotalAmount(BigDecimal staTotalAmount) {
        this.staTotalAmount = staTotalAmount;
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

    @Column(name = "DISTRICT", length = 60)
    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @Column(name = "ADDRESS", length = 250)
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

    @Column(name = "ZIPCODE", length = 10)
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

    @Column(name = "SOURCE", length = 20)
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Column(name = "STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.DefaultStatus")})
    public DefaultStatus getStatus() {
        return status;
    }

    public void setStatus(DefaultStatus status) {
        this.status = status;
    }

    @Column(name = "TRANSFER_FEE")
    public BigDecimal getTransferFee() {
        return transferFee;
    }

    public void setTransferFee(BigDecimal transferFee) {
        this.transferFee = transferFee;
    }

    @Column(name = "INVOICE_TOTAL_AMOUNT")
    public BigDecimal getInvoiceTotalAmount() {
        return invoiceTotalAmount;
    }

    public void setInvoiceTotalAmount(BigDecimal invoiceTotalAmount) {
        this.invoiceTotalAmount = invoiceTotalAmount;
    }

    @Column(name = "FAILURE_INFO")
    public String getFailureInfo() {
        return failureInfo;
    }

    public void setFailureInfo(String failureInfo) {
        this.failureInfo = failureInfo;
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

    @Column(name = "ORDER_CREATE_TIME")
    public Date getOrderCreateTime() {
        return orderCreateTime;
    }

    public void setOrderCreateTime(Date orderCreateTime) {
        this.orderCreateTime = orderCreateTime;
    }

    @Column(name = "ORDER_PLAN_RECEIPT_TIME")
    public Date getOrderPlanReceiptTime() {
        return orderPlanReceiptTime;
    }

    public void setOrderPlanReceiptTime(Date orderPlanReceiptTime) {
        this.orderPlanReceiptTime = orderPlanReceiptTime;
    }
}
