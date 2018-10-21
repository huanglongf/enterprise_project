package com.jumbo.wms.model.vmi.Default;

public class VmiStatusAdjCommand extends VmiStatusAdjDefault {

    private static final long serialVersionUID = 3315511936587766840L;

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
