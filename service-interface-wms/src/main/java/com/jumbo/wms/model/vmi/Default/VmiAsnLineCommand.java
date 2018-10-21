package com.jumbo.wms.model.vmi.Default;

public class VmiAsnLineCommand extends VmiAsnLineDefault {

    private static final long serialVersionUID = -8745716026982207673L;

    private String statusString;

    private Long aId;

    public String getStatusString() {
        return statusString;
    }

    public void setStatusString(String statusString) {
        this.statusString = statusString;
    }

    public Long getaId() {
        return aId;
    }

    public void setaId(Long aId) {
        this.aId = aId;
    }


}
