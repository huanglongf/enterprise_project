package com.jumbo.pms.model;

import java.io.Serializable;
import java.util.List;


/**
 * @author ShengGe
 * 
 */
public class ParcelMailNoGettingCommand implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7194849328375488875L;
	
	/**
	 * 退货单号
	 */
	private String orderCode;
	
    /**
     * 寄件人
     */
    private String sender;

    /**
     * 寄件人电话
     */
    private String senderMobile;

    /**
     * 出发地编码(门店)
     */
    private String o2oShopCode;
    
    /**
     * 目的地编码
     */
    private String destinationCode;
    
    /**
     * 揽件人编码
     */
    private String courier;

    /**
     * 包裹内含商品数量
     */
    private String skuCount;

    /**
     * 品名
     */
    private String shipmentContents;
    
    /**
     * 是否COD
     */
    private Boolean isCod;

    /**
     * 备注(退货说明)
     */
    private String remark;
    
    /**
     * 超期件包裹标识(if (isParcelTimeOut) slipMailNo & slipLpcode必填)
     */
    private Boolean isParcelTimeOut;
    
    /**
     * 超期件包裹号
     */
    private String slipMailNo;
    
    /**
     * 超期件物流商
     */
    private String slipLpcode;
    
    /**
     * 包裹明细
     */
    private List<ParcelLineCommand> lineCommands;

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getSenderMobile() {
		return senderMobile;
	}

	public void setSenderMobile(String senderMobile) {
		this.senderMobile = senderMobile;
	}

	public String getO2oShopCode() {
		return o2oShopCode;
	}

	public void setO2oShopCode(String o2oShopCode) {
		this.o2oShopCode = o2oShopCode;
	}

	public String getDestinationCode() {
		return destinationCode;
	}

	public void setDestinationCode(String destinationCode) {
		this.destinationCode = destinationCode;
	}

	public String getCourier() {
		return courier;
	}

	public void setCourier(String courier) {
		this.courier = courier;
	}

	public String getSkuCount() {
		return skuCount;
	}

	public void setSkuCount(String skuCount) {
		this.skuCount = skuCount;
	}

	public String getShipmentContents() {
		return shipmentContents;
	}

	public void setShipmentContents(String shipmentContents) {
		this.shipmentContents = shipmentContents;
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

	public Boolean getIsParcelTimeOut() {
        return isParcelTimeOut;
    }

    public void setIsParcelTimeOut(Boolean isParcelTimeOut) {
        this.isParcelTimeOut = isParcelTimeOut;
    }

    public String getSlipMailNo() {
        return slipMailNo;
    }

    public void setSlipMailNo(String slipMailNo) {
        this.slipMailNo = slipMailNo;
    }

    public String getSlipLpcode() {
        return slipLpcode;
    }

    public void setSlipLpcode(String slipLpcode) {
        this.slipLpcode = slipLpcode;
    }

    public List<ParcelLineCommand> getLineCommands() {
		return lineCommands;
	}

	public void setLineCommands(List<ParcelLineCommand> lineCommands) {
		this.lineCommands = lineCommands;
	}
	
}
