package com.jumbo.wms.model.vmi.Default;

public class VmiTfxCommand extends VmiTfxDefault {

    private static final long serialVersionUID = 6578439941753420978L;

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
