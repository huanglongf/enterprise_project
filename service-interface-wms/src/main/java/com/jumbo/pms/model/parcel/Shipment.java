package com.jumbo.pms.model.parcel;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.jumbo.wms.model.BaseModel;

/**
 * 包裹主信息
 * 
 * @author ShengGe
 * 
 */
@Entity
@Table(name = "T_WH_SHIPMENT")
public class Shipment extends BaseModel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final Integer TYPE_SO = 1;// 销售出
	public static final Integer TYPE_RA = 2;// 退货入

	/**
     * PK
     */
    private Long id;
    
    /**
     * 渠道
     */
    private String channelCode;
    
    /**
     * 订单系统来源
            GOMS
            TmallOMS
            etc.
     */
    private String sourceSys;
    
    /**
     * TransTimeType
     * 运单时限类型(快递附加服务)
     *     ORDINARY(1), // 普通
            TIMELY(3), // 及时达
            SAME_DAY(5), // 当日
            THE_NEXT_DAY(6); // 次日
     */
    private Integer transTimeType;
    
    /**
     * 包裹创建时间
     */
    private Date createTime = new Date();
    
    /**
     * 包裹费用总计
     */
    private BigDecimal totalCharges;

    /**
     * 包裹数量
     */
    private Integer parcelCount;
    
    //***********************包裹寄件，收件相关字段
    /**
     * 配货点编码
     */
    private String originCode;

    /**
     * 寄件人
     */
    private String sender;

    /**
     * 寄件人电话
     */
    private String senderMobile;

    /**
     * 收件人
     */
    private String receiver;

    /**
     * 收件人电话
     */
    private String receiverMobile;
    
    /**
     *目的地编码
     */
    private String destinationCode;
    
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
     * 街道（暂时不使用）
     */
    private String street;
    
    /**
     *  地址(如果是自提点,非必填)
     */
    private String address;
    
    /**
     *  邮编
     */
    private String zipCode;
    
    //**************************包裹关联的订单相关字段
    /**
     * 外部平台订单号
     */
    private String platformOrderCode;
    
    /**
     * BZ订单号
     */
    private String omsOrderCode;
    
    //暂时冗余退单号
    /**
     * 外部平台退单号
     */
    private String platformRaCode;
    
    /**
     * 宝尊退单号
     */
    private String omsRaCode;
    
    /**
     * 是否COD
     */
    private Boolean isCod;
    
    /**
     * 类型
     * 1. 销售出
     * 2. 退货入
     */
    private Integer type;
    
    /**
     * 备注
     */
    private String remark;
    
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_WH_SHIPMENT", sequenceName = "S_T_WH_SHIPMENT", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WH_SHIPMENT")
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
    @Column(name = "CHANNEL_CODE")
    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    @Column(name = "SOURCE_SYS")
    public String getSourceSys() {
		return sourceSys;
	}

	public void setSourceSys(String sourceSys) {
		this.sourceSys = sourceSys;
	}

	@Column(name = "CREATE_TIME")
	public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "TRANS_TIME_TYPE")
	public Integer getTransTimeType() {
        return transTimeType;
    }

    public void setTransTimeType(Integer transTimeType) {
        this.transTimeType = transTimeType;
    }

    @Column(name = "TOTAL_CHARGES")
    public BigDecimal getTotalCharges() {
        return totalCharges;
    }

    public void setTotalCharges(BigDecimal totalCharges) {
        this.totalCharges = totalCharges;
    }

    @Column(name = "PARCEL_COUNT")
    public Integer getParcelCount() {
        return parcelCount;
    }

    public void setParcelCount(Integer parcelCount) {
        this.parcelCount = parcelCount;
    }

    @Column(name = "ORIGIN_CODE")
    public String getOriginCode() {
        return originCode;
    }

    public void setOriginCode(String originCode) {
        this.originCode = originCode;
    }

    @Column(name = "SENDER")
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    @Column(name = "SENDER_MOBILE")
    public String getSenderMobile() {
        return senderMobile;
    }

    public void setSenderMobile(String senderMobile) {
        this.senderMobile = senderMobile;
    }

    @Column(name = "RECEIVER")
    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    @Column(name = "RECEIVER_MOBILE")
    public String getReceiverMobile() {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    @Column(name = "DESTINATION_CODE")
    public String getDestinationCode() {
        return destinationCode;
    }

    public void setDestinationCode(String destinationCode) {
        this.destinationCode = destinationCode;
    }

    @Column(name = "COUNTRY")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Column(name = "PROVINCE")
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Column(name = "CITY")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "DISTRICT")
    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @Column(name = "STREET")
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Column(name = "ADDRESS")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "ZIP_CODE")
    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Column(name = "PLATFORM_ORDER_CODE")
    public String getPlatformOrderCode() {
        return platformOrderCode;
    }

    public void setPlatformOrderCode(String platformOrderCode) {
        this.platformOrderCode = platformOrderCode;
    }

    @Column(name = "OMS_ORDER_CODE")
    public String getOmsOrderCode() {
        return omsOrderCode;
    }

    public void setOmsOrderCode(String omsOrderCode) {
        this.omsOrderCode = omsOrderCode;
    }

    @Column(name = "PLATFORM_RA_CODE")
    public String getPlatformRaCode() {
        return platformRaCode;
    }

    public void setPlatformRaCode(String platformRaCode) {
        this.platformRaCode = platformRaCode;
    }

    @Column(name = "OMS_RA_CODE")
    public String getOmsRaCode() {
        return omsRaCode;
    }

    public void setOmsRaCode(String omsRaCode) {
        this.omsRaCode = omsRaCode;
    }

    @Column(name = "IS_COD")
    public Boolean getIsCod() {
        return isCod;
    }

    public void setIsCod(Boolean isCod) {
        this.isCod = isCod;
    }

    @Column(name = "TYPE")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Column(name = "REMARK")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
}
