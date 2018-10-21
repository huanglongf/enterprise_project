package com.jumbo.pms.model.command;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 包裹信息    
 */
public class ParcelInfoCommand implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Date deliveryTime;
    
    /**
     * 物流商平台订单号
     */
    private String extTransOrderId;

    /**
     * 品名(Clothing,Shoes, ... )
     */
    private String shipmentContents;
    
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
    
    private Integer status;
    
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
    
	private List<ParcelInfoLineCommand> parcelInfoLineCommands;

    public Date getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public String getExtTransOrderId() {
        return extTransOrderId;
    }

    public void setExtTransOrderId(String extTransOrderId) {
        this.extTransOrderId = extTransOrderId;
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

    public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getTotalQty() {
		return totalQty;
	}

	public void setTotalQty(Long totalQty) {
		this.totalQty = totalQty;
	}

	public String getExtStr1() {
        return extStr1;
    }

    public void setExtStr1(String extStr1) {
        this.extStr1 = extStr1;
    }

    public String getExtStr2() {
        return extStr2;
    }

    public void setExtStr2(String extStr2) {
        this.extStr2 = extStr2;
    }

    public String getExtStr3() {
        return extStr3;
    }

    public void setExtStr3(String extStr3) {
        this.extStr3 = extStr3;
    }

	public List<ParcelInfoLineCommand> getParcelInfoLineCommands() {
		return parcelInfoLineCommands;
	}

	public void setParcelInfoLineCommands(
			List<ParcelInfoLineCommand> parcelInfoLineCommands) {
		this.parcelInfoLineCommands = parcelInfoLineCommands;
	}

}
