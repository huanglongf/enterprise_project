package com.jumbo.wms.model.vmi.Default;

public class VmiRtwCommand extends VmiRtwDefault {
    
    private static final long serialVersionUID = -7842321889874652100L;

    private String statusString;

    private Long sId;

    public String getStatusString() {
        return statusString;
    }

    public void setStatusString(String statusString) {
        this.statusString = statusString;
    }

    public Long getsId() {
        return sId;
    }

    public void setsId(Long sId) {
        this.sId = sId;
    }

}
