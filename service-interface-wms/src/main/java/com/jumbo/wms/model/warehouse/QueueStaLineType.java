package com.jumbo.wms.model.warehouse;

public enum QueueStaLineType {
	
	LINE_TYPE_TURE(1),   //主卖品
	LINE_TYPE_FALSE(5);   //非主卖品
	
	
	  private int value;

	    private QueueStaLineType(int value) {
	        this.value = value;
	    }

	    public int getValue() {
	        return value;
	    }
	    public static QueueStaLineType valueOf(int value) {
	        switch (value) {
		        case 5:
		            return LINE_TYPE_FALSE;
		        case 1:
	                return LINE_TYPE_TURE;
		        default:
		        	return LINE_TYPE_TURE;

}
	    }
}
