package com.jumbo.wms.model.warehouse;

public class QueueStaLineCommand extends QueueStaLine{

	/**
	 * 
	 */
	private static final long serialVersionUID = 663485377494382480L;
	private String owner;
	private String extCode;
	private Long ouid;
	
	public Long getOuid() {
		return ouid;
	}
	public void setOuid(Long ouid) {
		this.ouid = ouid;
	}
	public String getExtCode() {
		return extCode;
	}
	public void setExtCode(String extCode) {
		this.extCode = extCode;
	}
	@Override
	public String getOwner() {
		return owner;
	}
	@Override
	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	
	

}
