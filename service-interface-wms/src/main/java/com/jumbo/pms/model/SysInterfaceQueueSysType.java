package com.jumbo.pms.model;

public enum SysInterfaceQueueSysType {

	PACS(1), // PACS
	BZ_WMS(2), // 宝尊大陆WMS
	TM_OMS(3), // TMALLOMS
	BZ_HK_WMS(4),//宝尊香港WMS
	G_SHOPDOG(5), // 大陆SD
	LP(6); //物流商
	
    private int value;

    private SysInterfaceQueueSysType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SysInterfaceQueueSysType valueOf(int value) {
        switch (value) {
            case 1:
                return PACS;
            case 2:
                return BZ_WMS;
            case 3:
            	return TM_OMS;
            case 4:
                return BZ_HK_WMS;
            case 5:
            	return G_SHOPDOG;
            case 6:
            	return LP;
            default:
                throw new IllegalArgumentException();
        }
    }
    
    public static boolean checkTargetIsIegal(String sysCode) {
        try {
            SysInterfaceQueueSysType.valueOf(sysCode);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }
}
