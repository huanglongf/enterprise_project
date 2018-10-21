package com.jumbo.wms.model.trans;


public class TransAreaGroupCommand extends TransAreaGroup {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    private String areaStatus;// 状态

    public String getAreaStatus() {
        return areaStatus;
    }

    public void setAreaStatus(String areaStatus) {
        this.areaStatus = areaStatus;
    }
}
