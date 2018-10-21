package com.jumbo.pms.model;

import java.math.BigDecimal;
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
 * 包裹主档信息
 * 
 * @author ShengGe
 * 
 */
@Entity
@Table(name = "T_WH_PARCEL")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class Parcel extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8725436555216354383L;
	
    /**
     * PK
     */
    private Long id;

    /**
     * Version
     */
    private Date version;
	
    /**
     * 包裹创建时间
     */
    private Date shippingDate = new Date();
    
    /**
     * 包裹编码
     */
    private String code;
    
    /**
     * 出发点编码
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
     * BZ订单号
     */
    private String omsOrderCode;
    
    /**
     * 外部平台订单号
     */
    private String outerOrderCode;
    
    /**
     * 物流商平台订单号
     */
    private String extTransOrderId;
    
    /**
     * 地址(如果是自提点,非必填)
     */
    private String address;
    
    /**
     * 包裹路由状态
	 		1.申请包裹运单号
			2.包裹出库
			3.在途(除开包裹送达之外的路由信息)[暂时不使用]
			4.门店已签收
			5.顾客已签收
     */
    private ParcelStatus status;
    
    /**
     * 运单号
     */
    private String mailNo;
    
   /**
    * 物流服务商  SF,EMS ,STO...
    */
    private String lpcode;
    
    /**
     * 包裹数量
     * 用于子母单
     * 1-1
     * 1-2
     * ...
     */
    private Long parcelQuantity;
    
    /**
     * 揽件人
     */
    private String Courier;
    
    /**
     * 品名(Clothing,Shoes, Snacks ... )
     */
    private String shipmentContents;
    
    /**
     * 是否COD
     */
    private Boolean isCod;
    
    /**
     * 包裹费用
     */
    private BigDecimal charges;
    
    /**
     * 备注
     */
    private String remark;
    
    private Integer statusInteger;
    
    //物流商附属属性
    /**
     * 运单时限类型(快递附加服务)
     */
//    private TransTimeType transTimeType;
    
    /**
     * 运送类型
     */
//    private Integer deliveryType;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_PARCEL", sequenceName = "S_T_WH_PARCEL", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PARCEL")
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
    @Version
    @Column(name = "VERSION")
	public Date getVersion() {
		return version;
	}
	
	public void setVersion(Date version) {
		this.version = version;
	}

    @Column(name = "SHIPPING_DATE")
    public Date getShippingDate() {
		return shippingDate;
	}

	public void setShippingDate(Date shippingDate) {
		this.shippingDate = shippingDate;
	}

	@Column(name = "CODE", length = 50)
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "ORIGIN_CODE")
	public String getOriginCode() {
		return originCode;
	}

	public void setOriginCode(String originCode) {
		this.originCode = originCode;
	}

	@Column(name = "DESTINATION_CODE")
	public String getDestinationCode() {
		return destinationCode;
	}

	public void setDestinationCode(String destinationCode) {
		this.destinationCode = destinationCode;
	}

	@Column(name = "OMS_ORDER_CODE")
	public String getOmsOrderCode() {
		return omsOrderCode;
	}

	public void setOmsOrderCode(String omsOrderCode) {
		this.omsOrderCode = omsOrderCode;
	}

	@Column(name = "OUTER_ORDER_CODE")
	public String getOuterOrderCode() {
		return outerOrderCode;
	}

	public void setOuterOrderCode(String outerOrderCode) {
		this.outerOrderCode = outerOrderCode;
	}

    @Column(name = "EXT_TRANS_ORDER_ID")
	public String getExtTransOrderId() {
        return extTransOrderId;
    }

    public void setExtTransOrderId(String extTransOrderId) {
        this.extTransOrderId = extTransOrderId;
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

	@Column(name = "ADDRESS", length = 100)
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}

    @Column(name = "STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.pms.model.ParcelStatus")})
	public ParcelStatus getStatus() {
		return status;
	}
	
	public void setStatus(ParcelStatus status) {
		this.status = status;
	}

    @Column(name = "MAIL_NO", length = 100)
	public String getMailNo() {
		return mailNo;
	}
	
	public void setMailNo(String mailNo) {
		this.mailNo = mailNo;
	}

    @Column(name = "LPCODE")
	public String getLpcode() {
		return lpcode;
	}

	public void setLpcode(String lpcode) {
		this.lpcode = lpcode;
	}
	
    @Column(name = "PARCEL_QUANTITY")
	public Long getParcelQuantity() {
		return parcelQuantity;
	}

	public void setParcelQuantity(Long parcelQuantity) {
		this.parcelQuantity = parcelQuantity;
	}

    @Column(name = "COURIER")
	public String getCourier() {
		return Courier;
	}

	public void setCourier(String courier) {
		Courier = courier;
	}

    @Column(name = "SHIPMENT_CONTENTS")
	public String getShipmentContents() {
		return shipmentContents;
	}

	public void setShipmentContents(String shipmentContents) {
		this.shipmentContents = shipmentContents;
	}

    @Column(name = "IS_COD")
    public Boolean getIsCod() {
		return isCod;
	}

	public void setIsCod(Boolean isCod) {
		this.isCod = isCod;
	}

	@Column(name = "CHARGES")
	public BigDecimal getCharges() {
		return charges;
	}

	public void setCharges(BigDecimal charges) {
		this.charges = charges;
	}

    @Column(name = "REMARK")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Transient
	public Integer getStatusInteger() {
		return statusInteger;
	}

	public void setStatusInteger(Integer statusInteger) {
		this.statusInteger = statusInteger;
	}
/**
    @Column(name = "TRANS_TIME_TYPE")
    public TransTimeType getTransTimeType() {
        return transTimeType;
    }

    public void setTransTimeType(TransTimeType transTimeType) {
        this.transTimeType = transTimeType;
    }

    @Column(name = "DELIVERY_TYPE")
    public Integer getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(Integer deliveryType) {
        this.deliveryType = deliveryType;
    }*/
}
