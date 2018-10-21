package com.jumbo.wms.model.command;

import com.jumbo.wms.model.baseinfo.SkuSnCheckCfg;

public class SkuSnCheckCfgCommand extends SkuSnCheckCfg {

    private static final long serialVersionUID = -8544025889355182749L;

    private Integer typeInt;

    private Integer snCheckModeInt;

    public Integer getTypeInt() {
        return typeInt;
    }

    public void setTypeInt(Integer typeInt) {
        this.typeInt = typeInt;
    }

    public Integer getSnCheckModeInt() {
        return snCheckModeInt;
    }

    public void setSnCheckModeInt(Integer snCheckModeInt) {
        this.snCheckModeInt = snCheckModeInt;
    }


}
