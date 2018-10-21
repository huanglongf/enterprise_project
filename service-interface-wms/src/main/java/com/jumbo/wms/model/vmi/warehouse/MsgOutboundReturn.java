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
@Table(name = "T_WH_MSG_OUTBOUND_RTN")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class MsgOutboundReturn extends BaseModel {


    private static final long serialVersionUID = 8861718959515425239L;

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
    private String source = " ";

    /**
     * 来源仓库
     */
    private String sourceWh = " ";

    /**
     * 作业单号
     */
    private String staCode = " ";

    /**
     * 物流
     */
    private String lpCode = " ";

    /**
     * 运费
     */
    private BigDecimal transferFee = new BigDecimal(0);

    /**
     * 收货人
     */
    private String receiver = " ";

    /**
     * 邮编
     */
    private String zipcode = " ";

    /**
     * 电话
     */
    private String telePhone = " ";

    /**
     * 手机
     */
    private String mobile = " ";

    /**
     * 国家
     */
    private String country = " ";

    /**
     * 省
     */
    private String province = " ";

    /**
     * 市
     */
    private String city = " ";

    /**
     * 区
     */
    private String district = " ";

    /**
     * 送货地址
     */
    private String address = " ";

    /**
     * 金额
     */
    private BigDecimal totalActual = new BigDecimal(0);

    /**
     * 备注
     */
    private String remark = " ";

    /**
     * 状态
     */
    private DefaultStatus status;

    /**
     * 类型
     */
    private String type = " ";

    /**
     * 作业单类型
     */
    private Integer staType;

    /**
     * 创建时间
     */
    private Date createTime = new Date();


    /**
     * 创建时间
     */
    private Date updateTime = new Date();

    /**
     * 外包仓数据唯一标识
     */
    private Long uuid;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_WH_MSG_OUTBOUND_RTN", sequenceName = "S_T_WH_MSG_OUTBOUND_RTN", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_WH_MSG_OUTBOUND_RTN")
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

    @Column(name = "UUID")
    public Long getUuid() {
        return uuid;
    }

    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }

}
