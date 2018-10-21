package com.bt.lmis.balance.model;

/** 
 * @ClassName: TotalFreightDiscountDomainModel
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年5月11日 下午1:54:22 
 * 
 */
public class TotalFreightDiscountDomainModel extends DomainModel {
	/** 
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	 */
	private static final long serialVersionUID = 1143966896358202126L;
	private int ladderType;
	private int insuranceContain;
	public int getLadderType() {
		return ladderType;
	}
	public void setLadderType(int ladderType) {
		this.ladderType = ladderType;
	}
	public int getInsuranceContain() {
		return insuranceContain;
	}
	public void setInsuranceContain(int insuranceContain) {
		this.insuranceContain = insuranceContain;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
