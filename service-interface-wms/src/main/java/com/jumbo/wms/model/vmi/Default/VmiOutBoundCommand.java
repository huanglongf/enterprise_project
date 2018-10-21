package com.jumbo.wms.model.vmi.Default;

public class VmiOutBoundCommand extends VmiOutBoundDefault {
    
    private static final long serialVersionUID = 4702838864985233158L;

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
