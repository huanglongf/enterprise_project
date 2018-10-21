package com.jumbo.pms.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 包裹信息    
 */
public class ParcelBill implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6481813706775924965L;
    
    /**
     * 出发点编码
     */
    private String originCode;
    
    /**
     * 目的地编码
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
     * 收件人
     */
    private String receiver;

    /**
     * 收件人电话
     */
    private String receiverMobile;
    
    /**
     * 地址(如果是自提点,非必填)
     */
    private String address;
    
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
     */
    private Long parcelQuantity;
    
    /**
     * 品名(Clothing,Shoes, Snacks ... )
     */
    private String shipmentContents;
    
    /**
     * 包裹费用
     */
    private BigDecimal charges;
    
    /**
     * 扩展字段
     */
    private String extend;

    /**
     * 是否COD
     */
    private Boolean isCod;
    
    /**
     * 备注
     */
    private String remark;
    
	/**
	 * 操作代码
	 * 1.申请包裹运单号
		2.包裹出库
		3.在途(除开包裹送达之外的路由信息)[暂时不使用]
		4.门店已签收
		5.顾客已签收
	 */
	private Integer type;
	
	private List<ParcelLineBill> lineBills;

	public String getOriginCode() {
		return originCode;
	}

	public void setOriginCode(String originCode) {
		this.originCode = originCode;
	}

	public String getDestinationCode() {
		return destinationCode;
	}

	public void setDestinationCode(String destinationCode) {
		this.destinationCode = destinationCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMailNo() {
		return mailNo;
	}

	public void setMailNo(String mailNo) {
		this.mailNo = mailNo;
	}

	public String getLpcode() {
		return lpcode;
	}

	public void setLpcode(String lpcode) {
		this.lpcode = lpcode;
	}

	public Long getParcelQuantity() {
		return parcelQuantity;
	}

	public void setParcelQuantity(Long parcelQuantity) {
		this.parcelQuantity = parcelQuantity;
	}

	public String getShipmentContents() {
		return shipmentContents;
	}

	public void setShipmentContents(String shipmentContents) {
		this.shipmentContents = shipmentContents;
	}

	public BigDecimal getCharges() {
		return charges;
	}

	public void setCharges(BigDecimal charges) {
		this.charges = charges;
	}

	public String getExtend() {
		return extend;
	}

	public void setExtend(String extend) {
		this.extend = extend;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public List<ParcelLineBill> getLineBills() {
		return lineBills;
	}

	public void setLineBills(List<ParcelLineBill> lineBills) {
		this.lineBills = lineBills;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getReceiverMobile() {
		return receiverMobile;
	}

	public void setReceiverMobile(String receiverMobile) {
		this.receiverMobile = receiverMobile;
	}

	public Boolean getIsCod() {
		return isCod;
	}

	public void setIsCod(Boolean isCod) {
		this.isCod = isCod;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOmsOrderCode() {
		return omsOrderCode;
	}

	public void setOmsOrderCode(String omsOrderCode) {
		this.omsOrderCode = omsOrderCode;
	}

	public String getOuterOrderCode() {
		return outerOrderCode;
	}

	public void setOuterOrderCode(String outerOrderCode) {
		this.outerOrderCode = outerOrderCode;
	}

    public String getExtTransOrderId() {
        return extTransOrderId;
    }

    public void setExtTransOrderId(String extTransOrderId) {
        this.extTransOrderId = extTransOrderId;
    }
    
}
