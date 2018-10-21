package com.jumbo.wms.model.warehouse;


public class RecieverInfoCommand extends RecieverInfo {

    /**
     * 
     */
    private static final long serialVersionUID = -8127851692941607777L;

    private String newLpCode;

    private String newTrackingNo;

    public String getNewLpCode() {
        return newLpCode;
    }

    public void setNewLpCode(String newLpCode) {
        this.newLpCode = newLpCode;
    }

    public String getNewTrackingNo() {
        return newTrackingNo;
    }

    public void setNewTrackingNo(String newTrackingNo) {
        this.newTrackingNo = newTrackingNo;
    }


}
