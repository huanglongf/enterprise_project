package com.jumbo.wms.model.vmi.Default;

public class VmiAdjCommand extends VmiAdjDefault {

    private static final long serialVersionUID = 3315511936587766840L;

    private String statusString;

    private Long sId;

    private Long invckId;


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

    public Long getInvckId() {
        return invckId;
    }

    public void setInvckId(Long invckId) {
        this.invckId = invckId;
    }
}
