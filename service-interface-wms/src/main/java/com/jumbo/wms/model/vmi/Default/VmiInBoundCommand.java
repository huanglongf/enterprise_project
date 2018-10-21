package com.jumbo.wms.model.vmi.Default;

public class VmiInBoundCommand extends VmiInBoundDefault {

    private static final long serialVersionUID = 1069495252537051533L;

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
