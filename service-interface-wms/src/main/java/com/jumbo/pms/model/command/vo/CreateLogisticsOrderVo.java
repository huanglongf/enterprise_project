package com.jumbo.pms.model.command.vo;

import java.util.List;

import com.jumbo.pms.model.command.ParcelInfoLineCommand;
import com.jumbo.pms.model.command.ShipmentCommand;

/**
 * 物流下单信息    
 */
public class CreateLogisticsOrderVo extends ShipmentCommand {

	private static final long serialVersionUID = 1L;

	/**
	 * 货物总计
	 */
	private Integer cargoCount;
	
	private List<ParcelInfoLineCommand> parcelInfoLineCommands;

    /**
     * 品名(Clothing,Shoes, ... )
     */
    private String shipmentContents;
    
    /**
     * 运单号
     */
    private String mailNo;
    
    /**
     * 物流商编码
     */
    private String lpCode;
    
    /**
     * 联系电话
     */
    private String telephone;

    /**
     * 联系人
     */
    private String contact;
    
    /**
     * 相关单据号
     */
    private String slipCode;

    
	public Integer getCargoCount() {
		return cargoCount;
	}

	public void setCargoCount(Integer cargoCount) {
		this.cargoCount = cargoCount;
	}

	public List<ParcelInfoLineCommand> getParcelInfoLineCommands() {
		return parcelInfoLineCommands;
	}

	public void setParcelInfoLineCommands(
			List<ParcelInfoLineCommand> parcelInfoLineCommands) {
		this.parcelInfoLineCommands = parcelInfoLineCommands;
	}

	public String getShipmentContents() {
		return shipmentContents;
	}

	public void setShipmentContents(String shipmentContents) {
		this.shipmentContents = shipmentContents;
	}

	public String getMailNo() {
		return mailNo;
	}

	public void setMailNo(String mailNo) {
		this.mailNo = mailNo;
	}

	public String getLpCode() {
		return lpCode;
	}

	public void setLpCode(String lpCode) {
		this.lpCode = lpCode;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getSlipCode() {
		return slipCode;
	}

	public void setSlipCode(String slipCode) {
		this.slipCode = slipCode;
	}

}
