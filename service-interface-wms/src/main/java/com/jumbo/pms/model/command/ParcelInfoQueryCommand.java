package com.jumbo.pms.model.command;

import java.util.Date;


/**
 * 包裹主信息    
 */
public class ParcelInfoQueryCommand extends ShipmentCommand {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    
	private Long id;
	/**
	 * 出库时间
	 */
	private Date deliveryTime;
	
	private String code;
	
	private Integer status;
    /**
     * 物流服务商  SF,EMS ,STO...
     * 
     */
    private String lpcode;

    /**
     * 运单号
     */
    private String mailNo;
    
    /**
     * 包裹总数量
     */
    private Long totalQty;
    
    private String extTransOrderId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getLpcode() {
		return lpcode;
	}

	public void setLpcode(String lpcode) {
		this.lpcode = lpcode;
	}

	public String getMailNo() {
		return mailNo;
	}

	public void setMailNo(String mailNo) {
		this.mailNo = mailNo;
	}

	public String getExtTransOrderId() {
		return extTransOrderId;
	}

	public void setExtTransOrderId(String extTransOrderId) {
		this.extTransOrderId = extTransOrderId;
	}

	public Long getTotalQty() {
		return totalQty;
	}

	public void setTotalQty(Long totalQty) {
		this.totalQty = totalQty;
	}

}
