package com.jumbo.wms.model.vmi.Default;

public class VmiAsnCommand extends VmiAsnDefault {

    private static final long serialVersionUID = 1703779250146958115L;

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
