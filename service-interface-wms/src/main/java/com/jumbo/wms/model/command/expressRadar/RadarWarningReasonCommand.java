package com.jumbo.wms.model.command.expressRadar;

import com.jumbo.wms.model.expressRadar.RadarWarningReason;

public class RadarWarningReasonCommand extends RadarWarningReason {

    private static final long serialVersionUID = 53911080066504246L;

    private Long eid;

    private Long lvid;

    private String username;

    private String lvname;

    public Long getEid() {
        return eid;
    }

    public void setEid(Long eid) {
        this.eid = eid;
    }

    public Long getLvid() {
        return lvid;
    }

    public void setLvid(Long lvid) {
        this.lvid = lvid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLvname() {
        return lvname;
    }

    public void setLvname(String lvname) {
        this.lvname = lvname;
    }


}
