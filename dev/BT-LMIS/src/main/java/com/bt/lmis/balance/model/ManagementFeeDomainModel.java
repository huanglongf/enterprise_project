package com.bt.lmis.balance.model;

/** 
 * @ClassName: ManagementFeeDomainModel
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年5月17日 上午11:21:14 
 * 
 */
public class ManagementFeeDomainModel extends DomainModel {

	/** 
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	 */
	private static final long serialVersionUID = -3861675314381850553L;
	private boolean freight;
	private boolean insurance;
	private boolean specialService;
	private boolean cod;
	private boolean delegatedPickup;
	private int ladderType;
	public boolean isFreight() {
		return freight;
	}
	public void setFreight(boolean freight) {
		this.freight = freight;
	}
	public boolean isInsurance() {
		return insurance;
	}
	public void setInsurance(boolean insurance) {
		this.insurance = insurance;
	}
	public boolean isSpecialService() {
		return specialService;
	}
	public void setSpecialService(boolean specialService) {
		this.specialService = specialService;
	}
	public boolean isCod() {
		return cod;
	}
	public void setCod(boolean cod) {
		this.cod = cod;
	}
	public boolean isDelegatedPickup() {
		return delegatedPickup;
	}
	public void setDelegatedPickup(boolean delegatedPickup) {
		this.delegatedPickup = delegatedPickup;
	}
	public int getLadderType() {
		return ladderType;
	}
	public void setLadderType(int ladderType) {
		this.ladderType = ladderType;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
