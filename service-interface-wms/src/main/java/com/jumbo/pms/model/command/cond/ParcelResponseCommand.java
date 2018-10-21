package com.jumbo.pms.model.command.cond;

import java.io.Serializable;
import java.util.Date;

public class ParcelResponseCommand  implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long shipmentId;
    
    /**
     * 物流服务商编码
     */
    private String lpCode;
    
    /**
     * 物流服务商名称
     */
    private String lpName;
    
    /**
     * 运单号
     */
    private String mailNo;

    /**
     * 收件人
     */
    private String receiver;

    /**
     * 收件人电话
     */
    private String receiverMobile;

    /**
     * 收件人地址
     */
    private String receiverAddress;
    
    /**
     * 寄件人
     */
    private String sender;
    
    /**
     * 寄件人地址
     */
    private String senderMobile;
    
    /**
     * 门店地址
     */
    private String shipperAddress;
    
    /**
     * 品名
     */
    private String shipmentContents;
    
    /**
     * 子母单数量
     */
    private Integer quantity;
    
    /**
     * 发货日期
     */
    private Date shippingDate;
    
    /**
     * 备注
     */
    private String remarks;
    
    /**
     * 相关单据号
     */
    private String slipCode;
    

    public Long getShipmentId() {
		return shipmentId;
	}

	public void setShipmentId(Long shipmentId) {
		this.shipmentId = shipmentId;
	}

    public String getLpCode() {
        return lpCode;
    }

    public void setLpCode(String lpCode) {
        this.lpCode = lpCode;
    }

    public String getLpName() {
		return lpName;
	}

	public void setLpName(String lpName) {
		this.lpName = lpName;
	}

	public String getMailNo() {
        return mailNo;
    }

    public void setMailNo(String mailNo) {
        this.mailNo = mailNo;
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

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
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

	public String getShipperAddress() {
        return shipperAddress;
    }

    public void setShipperAddress(String shipperAddress) {
        this.shipperAddress = shipperAddress;
    }

	public String getShipmentContents() {
		return shipmentContents;
	}

	public void setShipmentContents(String shipmentContents) {
		this.shipmentContents = shipmentContents;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Date getShippingDate() {
		return shippingDate;
	}

	public void setShippingDate(Date shippingDate) {
		this.shippingDate = shippingDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getSlipCode() {
		return slipCode;
	}

	public void setSlipCode(String slipCode) {
		this.slipCode = slipCode;
	}

}
