package com.bt.lmis.balance.model;

/**
* @ClassName: WarehouseExpressDataStoreSettlement
* @Description: TODO(WarehouseExpressDataStoreSettlement)
* @author Yuriy.Jiang
* @date 2016年6月22日 上午10:19:25
*
*/
public class WarehouseExpressDataStoreSettlementEstimate {
	
	
	private Integer insurance_flag;
	private String time;
	private String batch_number;
	private String epistatic_order;//订单号 
	
	
		return epistatic_order;
	}
	public void setEpistatic_order(String epistatic_order) {
		this.epistatic_order = epistatic_order;
	}
	public String getBatch_number() {
		return batch_number;
	}
	public void setBatch_number(String batch_number) {
		this.batch_number = batch_number;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Integer getInsurance_flag() {
		return insurance_flag;
	}
	public void setInsurance_flag(Integer insurance_flag) {
		this.insurance_flag = insurance_flag;
	}
	public String getId() {
}