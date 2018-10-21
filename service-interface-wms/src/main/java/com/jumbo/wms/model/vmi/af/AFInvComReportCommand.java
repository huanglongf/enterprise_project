package com.jumbo.wms.model.vmi.af;

public class AFInvComReportCommand extends AFInventoryCompareReport {

    /**
     * 
     */
    private static final long serialVersionUID = 6520599594298142827L;

    
    /**
     * 库存状态名
     */
    private String invStatusName;

    
    public String getInvStatusName() {
        return invStatusName;
    }

    public void setInvStatusName(String invStatusName) {
    	this.invStatusName = invStatusName;
    }

}
