package com.jumbo.wms.model.warehouse;

public enum QueueStaLineStatus {
	LINE_STATUS_TURE(1),         //创建行
	LINE_STATUS_FALSE(0);        //非创建行
	 private int value;

	    private QueueStaLineStatus(int value) {
	        this.value = value;
	    }

	    public int getValue() {
	        return value;
	    }
	    public static QueueStaLineStatus valueOf(int value) {
	        switch (value) {
		        case 0:
		            return LINE_STATUS_FALSE;
		        case 1:
		        	return LINE_STATUS_TURE;
		        default:
	                return LINE_STATUS_TURE;

}
	    }
}
