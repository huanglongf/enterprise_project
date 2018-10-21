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
 * 包裹主档信息
 * 
 * @author ShengGe
 * 
 */
@Entity
@Table(name = "T_WH_PARCEL_INFO")
public class ParcelInfo extends BaseModel {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * PK
     */
    private Long id;
    
    /**
     * shipment Id
     */
    private Long shipmentId;
    
    /**
     * 渠道
     */
    private String channelCode;
    
    /**
     * 包裹编码
     */
    private String code;
    
    /**
     * ParcelStatus
     * 包裹路由状态
            1.申请包裹运单号
            2.包裹出库
            3.在途(除开包裹送达之外的路由信息)[暂时不使用]
            4.门店已签收
            5.顾客已签收
     */
    private Integer status;
    
    /**
     * 包裹创建时间
     */
    private Date createTime;
    
    /**
     * 出库时间
     */
    private Date deliveryTime;
    
    /**
     * 门店签收时间
     */
    private Date signedTime;
    
    /**
     * 顾客签收时间(非o2o订单signedTime&finishedTime一致)
     */
    private Date finishedTime;
    
    /**
     * 包裹费用
     */
    private BigDecimal charges;
    
    /**
     * 物流服务商  SF,EMS ,STO...
     */
    private String lpcode;

    /**
     * 运单号
     */
    private String mailNo;
    
    /**
     * 物流商平台订单号
     */
    private String extTransOrderId;
    
    /**
     * 揽件人
     */
    private String courier;
    
    /**
     * 品名(Clothing,Shoes ... )
     */
    private String shipmentContents;
    
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 包裹总数量
     */
    private Long totalQty;
    
    /**
     * 扩展字段
     */
    private String extStr1;
    /**
     * 扩展字段
     */
    private String extStr2;
    /**
     * 扩展字段
     */
    private String extStr3;
    
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_PARCEL_INFO", sequenceName = "S_T_WH_PARCEL_INFO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PARCEL_INFO")
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

    @Column(name = "SHIPMENT_ID")
    public Long getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(Long shipmentId) {
        this.shipmentId = shipmentId;
    }

    @Column(name = "CHANNEL_CODE")
    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    @Column(name = "CREATE_TIME")
	public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "DELIVERY_TIME")
    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    @Column(name = "CODE", length = 50)
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}

    @Column(name = "STATUS")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "SIGNED_TIME")
    public Date getSignedTime() {
        return signedTime;
    }

    public void setSignedTime(Date signedTime) {
        this.signedTime = signedTime;
    }

    @Column(name = "FINISHED_TIME")
    public Date getFinishedTime() {
        return finishedTime;
    }

    public void setFinishedTime(Date finishedTime) {
        this.finishedTime = finishedTime;
    }

    @Column(name = "CHARGES")
    public BigDecimal getCharges() {
        return charges;
    }

    public void setCharges(BigDecimal charges) {
        this.charges = charges;
    }

    @Column(name = "LPCODE")
    public String getLpcode() {
        return lpcode;
    }

    public void setLpcode(String lpcode) {
        this.lpcode = lpcode;
    }

    @Column(name = "MAIL_NO")
    public String getMailNo() {
        return mailNo;
    }

    public void setMailNo(String mailNo) {
        this.mailNo = mailNo;
    }

    @Column(name = "EXT_TRANS_ORDER_ID")
    public String getExtTransOrderId() {
        return extTransOrderId;
    }

    public void setExtTransOrderId(String extTransOrderId) {
        this.extTransOrderId = extTransOrderId;
    }

    @Column(name = "COURIER")
    public String getCourier() {
        return courier;
    }

    public void setCourier(String courier) {
        this.courier = courier;
    }

    @Column(name = "SHIPMENT_CONTENTS")
    public String getShipmentContents() {
        return shipmentContents;
    }

    public void setShipmentContents(String shipmentContents) {
        this.shipmentContents = shipmentContents;
    }

    @Column(name = "REMARK")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

	@Column(name = "TOTAL_QTY")
    public Long getTotalQty() {
		return totalQty;
	}

	public void setTotalQty(Long totalQty) {
		this.totalQty = totalQty;
	}

	@Column(name = "EXT_STR1")
    public String getExtStr1() {
        return extStr1;
    }

    public void setExtStr1(String extStr1) {
        this.extStr1 = extStr1;
    }

    @Column(name = "EXT_STR2")
    public String getExtStr2() {
        return extStr2;
    }

    public void setExtStr2(String extStr2) {
        this.extStr2 = extStr2;
    }

    @Column(name = "EXT_STR3")
    public String getExtStr3() {
        return extStr3;
    }

    public void setExtStr3(String extStr3) {
        this.extStr3 = extStr3;
    }
    
}
