package com.jumbo.pms.model;


public enum SysInterfaceQueueType {
	PARCEL_DELIVERED_NOTIFY_PAC(1), //包裹出库通知PAC
	PARCEL_CHANGE_STATUS_NOTIFY_PAC(2), //包裹状态更新通知PAC
	PARCEL_PICK_UP_NOTIFY(3), //通知快递上门取件
	PARCEL_DELIVERED_NOTIFY_SHOPDOG(4), //包裹出库通知SD
	PARCEL_DELIVERED_NOTIFY_SD(5); //包裹出库通知SD
    
    private int value;

    private SysInterfaceQueueType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
		this.value = value;
	}

	public static SysInterfaceQueueType valueOf(int value) {
        switch (value) {
            case 1:
                return PARCEL_DELIVERED_NOTIFY_PAC;
            case 2:
            	return PARCEL_CHANGE_STATUS_NOTIFY_PAC;
            case 3:
                return PARCEL_PICK_UP_NOTIFY;
            case 4:
            	return PARCEL_DELIVERED_NOTIFY_SHOPDOG;
            case 5:
                return PARCEL_DELIVERED_NOTIFY_SD;
            default:
                throw new IllegalArgumentException();
        }
    }
}
