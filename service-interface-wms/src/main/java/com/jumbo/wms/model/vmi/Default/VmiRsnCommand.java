package com.jumbo.wms.model.vmi.Default;

public class VmiRsnCommand extends VmiRsnDefault {
    
    private static final long serialVersionUID = 9142923910783715491L;

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
