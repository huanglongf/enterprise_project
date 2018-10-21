package com.jumbo.pms.model;


public enum SysInterfaceQueueStatus {

	 	STATUS_NEW(1), // 新建待处理
	 	STATUS_DOING(2), // 处理中
	    STATUS_FAILED(3), // 处理失败
	    STATUS_SUCCESS(10); // 处理成功

	    private int value;

	    private SysInterfaceQueueStatus(int value) {
	        this.value = value;
	    }

	    public int getValue() {
	        return value;
	    }

	    public static SysInterfaceQueueStatus valueOf(int value) {
	        switch (value) {
	            case 1:
	                return STATUS_NEW;
	            case 2:
	            	return STATUS_DOING;
	            case 3:
	                return STATUS_FAILED;
	            case 10:
	                return STATUS_SUCCESS;
	            default:
	                throw new IllegalArgumentException();
	        }
	    }
}
