package com.jumbo.pms.model;

/**
 * 
 * 包裹状态
 * @author ShengGe
 *
 */
public enum ParcelStatus {
	apply_parcel_code(1),//1.申请包裹运单号
	parcel_delivered(2),//2.包裹出库
	parcel_on_the_way(3),//3.在途(除开包裹送达之外的路由信息)
	parcel_Received_by_Store(4),//4.门店已签收
	parcel_Picked_by_Customer(5),//5.顾客已签收
	parcel_Time_Out(5),//6.超期件包裹
	parcel_abnormal(10);//10.包裹异常更新
	
	
	private int value;

	
	private ParcelStatus(int value) {
		this.value = value;
	}

	
	public int getValue() {
		return value;
	}

	public static ParcelStatus valueOf(int value) {
		switch (value) {
			case (1):
				return apply_parcel_code;
			case (2):
				return parcel_delivered;
			case (3):
				return parcel_on_the_way;
			case (4):
				return parcel_Received_by_Store;
			case (5):
				return parcel_Picked_by_Customer;
			default:
				throw new IllegalArgumentException();
		}
	}

}
